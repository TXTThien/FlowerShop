package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.BlogFlowerRepository;
import org.example.repository.BlogImageRepository;
import org.example.repository.BlogRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/staff/blog")
@RequiredArgsConstructor
public class StaffBlogController {
    private final IBlogService iBlogService;
    private final IBlogImageService iBlogImageService;
    private final IBlogInteractService iBlogInteractService;
    private final IBlogCommentService iBlogCommentService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IBlogFlowerService iBlogFlowerService;
    private final BlogRepository blogRepository;
    private final BlogImageRepository blogImageRepository;
    private final BlogFlowerRepository blogFlowerRepository;
    private final IFlowerService flowerService;
    private final IAccountService accountService;
    @GetMapping("")
    public ResponseEntity<?> getBlog() {
        List<Blog> blogs = iBlogService.findStaffBlog(getIDAccountFromAuthService.common());
        Map<String, Object> response = new HashMap<>();
        response.put("blogs", blogs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogID(@PathVariable int id) {
        int accountid = getIDAccountFromAuthService.common();
        BlogInfoDTO blogInfoDTO = new BlogInfoDTO();

        // Tìm blog theo ID
        Blog blog = iBlogService.findBlogByBlogID(id);
        if (blog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
        }

        // Lấy danh sách comment của blog
        List<BlogCommentDTO> blogCommentDTOS = new ArrayList<>();
        List<BlogComment> blogComments = iBlogCommentService.findBlogCommentsByBlogID(blog.getBlogid());

        for (BlogComment blogComment : blogComments) {
            BlogCommentDTO blogCommentDTO = new BlogCommentDTO();
            List<BlogImage> blogImages = iBlogImageService.findBlogImagesByCommentID(blogComment.getBlogcommentid());
            List<BlogComment> commentChildren = iBlogCommentService.findCommentChild(blogComment.getBlogcommentid());
            List<BlogCommentDTO> childCommentsDTO = new ArrayList<>();
            List<BlogInteract> commentInteracts = iBlogInteractService.findLikeCommentYet(blogComment.getBlogcommentid(), accountid);

            // Xử lý comment child
            for (BlogComment child : commentChildren) {
                BlogCommentDTO childCommentDTO = new BlogCommentDTO();
                List<BlogInteract> childCommentInteracts = iBlogInteractService.findLikeCommentYet(child.getBlogcommentid(), accountid);
                List<BlogImage> imageChildComment = iBlogImageService.findBlogImagesByCommentID(child.getBlogcommentid());

                childCommentDTO.setBlogComment(child);
                childCommentDTO.setBlogImages(imageChildComment);
                childCommentDTO.setLikeComment(childCommentInteracts != null && !childCommentInteracts.isEmpty());
                childCommentsDTO.add(childCommentDTO);
            }

            blogCommentDTO.setBlogComment(blogComment);
            blogCommentDTO.setBlogImages(blogImages);
            blogCommentDTO.setNumberCommentChild(commentChildren.size()); // Gán số lượng comment con
            blogCommentDTO.setChildComment(childCommentsDTO);
            blogCommentDTO.setLikeComment(commentInteracts != null && !commentInteracts.isEmpty());
            blogCommentDTOS.add(blogCommentDTO); // Thêm comment vào danh sách
        }

        // Lấy thông tin khác của blog
        List<BlogImage> blogImages = iBlogImageService.findBlogImagesByBlogID(blog.getBlogid());
        List<BlogFlower> blogFlowers = iBlogFlowerService.findBlogFlowerByBlogID(blog.getBlogid());
        List<BlogInteract> blogInteracts = iBlogInteractService.findLikeBlogYet(blog.getBlogid(), accountid);
        List<Integer> flowerIds = blogFlowers.stream()
                .map(bf -> bf.getFlower().getFlowerID())
                .collect(Collectors.toList());

        List<ProductDTO> flowerDTOList = flowerService.getFlowerDTOsByFlowerIds(flowerIds);

        blogInfoDTO.setBlog(blog);
        blogInfoDTO.setBlogCommentDTOS(blogCommentDTOS);
        blogInfoDTO.setBlogImages(blogImages);
        blogInfoDTO.setBlogFlower(flowerDTOList);

        int totalComments = blogCommentDTOS.size(); // Số comment cha
        for (BlogCommentDTO blogCommentDTO : blogCommentDTOS) {
            totalComments += blogCommentDTO.getNumberCommentChild(); // Cộng số comment con
        }
        blogInfoDTO.setNumberComments(totalComments);

        blogInfoDTO.setLikeBlog(blogInteracts != null && !blogInteracts.isEmpty());

        return ResponseEntity.ok(blogInfoDTO);
    }

    @PostMapping("")
    public ResponseEntity<?> postBlogID(@RequestBody CreateBlogDTO newblog) {
        int accountid = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountid);
        try {
            Blog blog = createOrUpdateBlog(new Blog(), newblog,account);
            return ResponseEntity.ok("Blog created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating blog: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/flower")
    public ResponseEntity<?> postBlogFlower(@RequestBody BlogFlowerDTO blogFlowerDTO, @PathVariable int id) {
        Blog existingBlog = iBlogService.findBlogByBlogID(id);

        if (existingBlog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
        }

        try {
            saveBlogFlowers(existingBlog, blogFlowerDTO.getListFlower());
            return ResponseEntity.ok("Blog Flowers created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Blog Flowers: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putBlogID(@RequestBody CreateBlogDTO newblog, @PathVariable int id) {
        try {
            Blog existingBlog = iBlogService.findBlogByBlogID(id);
            if (existingBlog == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
            }
            Blog updatedBlog = createOrUpdateBlog(existingBlog, newblog,existingBlog.getAccount());
            return ResponseEntity.ok("Blog updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating blog: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/flower")
    public ResponseEntity<?> putBlogFlower(@RequestBody BlogFlowerDTO blogFlowerDTO) {
        if (blogFlowerDTO.getListBlogFlowerID() == null || blogFlowerDTO.getListFlower() == null ||
                blogFlowerDTO.getListBlogFlowerID().size() != blogFlowerDTO.getListFlower().size()) {
            return ResponseEntity.badRequest().body("Invalid input: IDs and Flowers must match in size");
        }

        try {
            updateBlogFlowers(blogFlowerDTO.getListBlogFlowerID(), blogFlowerDTO.getListFlower());
            return ResponseEntity.ok("Blog Flowers updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Blog Flowers: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable int id) {
        try {
            Blog existingBlog = iBlogService.findBlogByBlogID(id);
            if (existingBlog == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
            }
            existingBlog.setStatus(Status.DISABLE);
            blogRepository.save(existingBlog);
            return ResponseEntity.ok("Blog updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating blog: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/flower")
    public ResponseEntity<?> deleteBlogFlower(@RequestBody BlogFlowerDTO blogFlowerDTO) {
        if (blogFlowerDTO.getListBlogFlowerID() == null || blogFlowerDTO.getListBlogFlowerID().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input: No Blog Flower IDs provided");
        }

        try {
            deleteBlogFlowers(blogFlowerDTO.getListBlogFlowerID());
            return ResponseEntity.ok("Blog Flowers deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Blog Flowers: " + e.getMessage());
        }
    }


    private void updateBlogFlowers(List<Integer> blogFlowerIds, List<Integer> flowerIds) {
        for (int i = 0; i < blogFlowerIds.size(); i++) {
            BlogFlower existingBlogFlower = iBlogFlowerService.findBlogFlowerByBlogFlowerID(blogFlowerIds.get(i));
            if (existingBlogFlower != null) {
                Flower flower = flowerService.getProductById(flowerIds.get(i));
                if (flower != null) {
                    existingBlogFlower.setFlower(flower);
                    blogFlowerRepository.save(existingBlogFlower);
                }
            }
        }
    }

    private void deleteBlogFlowers(List<Integer> blogFlowerIds) {
        blogFlowerIds.forEach(blogFlowerId -> {
            BlogFlower existingBlogFlower = iBlogFlowerService.findBlogFlowerByBlogFlowerID(blogFlowerId);
            if (existingBlogFlower != null) {
                blogFlowerRepository.delete(existingBlogFlower);
            }
        });
    }

    private Blog createOrUpdateBlog(Blog blog, CreateBlogDTO newblog, Account account) {
        blog.setDate(LocalDateTime.now());
        blog.setTitle(newblog.getBlog().getTitle());
        blog.setContent(newblog.getBlog().getContent());
        blog.setStatus(newblog.getBlog().getStatus());
        blog.setLike(BigInteger.ZERO);
        blog.setAccount(account);
        blogRepository.save(blog);
        saveBlogImages(blog, newblog.getImageurl());
        return blog;
    }

    private void saveBlogImages(Blog blog, List<String> imageUrls) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            imageUrls.forEach(imageUrl -> {
                BlogImage blogImage = new BlogImage();
                blogImage.setBlog(blog);
                blogImage.setImage(imageUrl);
                blogImageRepository.save(blogImage);
            });
        }
    }

    private void saveBlogFlowers(Blog blog, List<Integer> flowerIds) {
        if (flowerIds != null && !flowerIds.isEmpty()) {
            flowerIds.forEach(flowerId -> {
                Flower flower = flowerService.getProductById(flowerId);
                if (flower != null) {
                    BlogFlower blogFlower = new BlogFlower();
                    blogFlower.setBlog(blog);
                    blogFlower.setFlower(flower);
                    blogFlowerRepository.save(blogFlower);
                }
            });
        }
    }

}
