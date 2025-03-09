package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.dto.BlogCommentDTO;
import org.example.dto.BlogInfoDTO;
import org.example.dto.CreateBlogDTO;
import org.example.dto.ProductDTO;
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
@RequestMapping("/api/v1/admin/blog")
@RequiredArgsConstructor
public class AdminBlogController {
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
        List<Blog> blogs = blogRepository.findAll();
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
            Blog blog = new Blog();

            blog.setDate(LocalDateTime.now());
            blog.setTitle(newblog.getTitle());
            blog.setContent(newblog.getContent());
            blog.setStatus(newblog.getStatus());
            blog.setLike(BigInteger.ZERO);
            blog.setAccount(account);
            blogRepository.save(blog);

            for (int i = 0 ; i< newblog.getImageurl().size(); i++)
            {
                BlogImage blogImage = new BlogImage();

                blogImage.setImage(newblog.getImageurl().get(i));
                blogImage.setBlog(blog);

                blogImageRepository.save(blogImage);
            }

            for (int i = 0 ; i<newblog.getBlogFlowers().size();i++)
            {
                BlogFlower blogFlower = new BlogFlower();

                blogFlower.setBlog(blog);
                blogFlower.setFlower(flowerService.getProductById(newblog.getBlogFlowers().get(i).getFlower().getFlowerID()));

                blogFlowerRepository.save(blogFlower);

            }
            return ResponseEntity.ok("Blog created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating blog: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> putBlogID(@RequestBody CreateBlogDTO newblog, @PathVariable int id) {
        try {
            Blog existingBlog = iBlogService.findBlogByBlogID(id);
            if (existingBlog == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
            }

            existingBlog.setTitle(newblog.getTitle());
            existingBlog.setContent(newblog.getContent());
            existingBlog.setStatus(newblog.getStatus());
            blogRepository.save(existingBlog);
            if (!newblog.getImageurl().isEmpty())
            {
                for (int i = 0 ; i< newblog.getImageurl().size(); i++)
                {
                    BlogImage blogImage = new BlogImage();

                    blogImage.setImage(newblog.getImageurl().get(i));
                    blogImage.setBlog(existingBlog);

                    blogImageRepository.save(blogImage);
                }
            }
            if (!newblog.getImageIDDelete().isEmpty())
            {
                for (int i = 0 ; i< newblog.getImageIDDelete().size(); i++)
                {
                    blogImageRepository.delete(iBlogImageService.findBlogImageByBlogImageID(newblog.getImageIDDelete().get(i)));
                }
            }
            if (!newblog.getBlogFlowers().isEmpty())
            {
                List<BlogFlower> blogFlowerList = iBlogFlowerService.findBlogFlowerByBlogID(id);
                List<BlogFlower> haveBlogFlower = new ArrayList<>();
                for (int i = 0 ; i< newblog.getBlogFlowers().size(); i++)
                {
                    BlogFlower blogFlower = iBlogFlowerService.findBlogFlowerByBlogFlowerID(newblog.getBlogFlowers().get(i).getFlowerblogid());
                    if (blogFlower!= null)
                    {
                        blogFlower.setFlower(newblog.getBlogFlowers().get(i).getFlower());
                        haveBlogFlower.add(blogFlower);
                    }
                    else
                    {
                        BlogFlower newBlogFlower = new BlogFlower();
                        newBlogFlower.setFlower(newblog.getBlogFlowers().get(i).getFlower());
                        newBlogFlower.setBlog(existingBlog);
                        haveBlogFlower.add(newBlogFlower);
                    }
                }
                List<BlogFlower> toDelete = blogFlowerList.stream()
                        .filter(blogFlower -> haveBlogFlower.stream()
                                .noneMatch(have -> have.getFlowerblogid().equals(blogFlower.getFlowerblogid())))
                        .toList();

                blogFlowerRepository.deleteAll(toDelete);
                blogFlowerRepository.saveAll(haveBlogFlower);

            }
            return ResponseEntity.ok("Blog updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating blog: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable int id) {
        try {
            Blog existingBlog = iBlogService.findBlogByBlogIDForStaff(id);
            if (existingBlog == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
            }

            if (existingBlog.getStatus() == Status.ENABLE)
            {
                existingBlog.setStatus(Status.DISABLE);
            }
            else
            {
                existingBlog.setStatus(Status.ENABLE);
            }
            blogRepository.save(existingBlog);
            return ResponseEntity.ok("Blog updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating blog: " + e.getMessage());
        }
    }
}
