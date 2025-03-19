package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.BlogCommentDTO;
import org.example.dto.BlogInfoDTO;
import org.example.dto.ProductDTO;
import org.example.entity.*;
import org.example.repository.BlogRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
    private final IBlogService iBlogService;
    private final IBlogImageService iBlogImageService;
    private final IBlogInteractService iBlogInteractService;
    private final IBlogCommentService iBlogCommentService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IBlogFlowerService iBlogFlowerService;
    private final IFlowerService flowerService;
    private final IEventFlowerService eventFlowerService;
    @GetMapping("")
    public ResponseEntity<?> getBlog() {
        int accountid = getIDAccountFromAuthService.common();
        List<BlogInfoDTO> blogInfoDTOS = new ArrayList<>();
        List<Blog> blogs = iBlogService.findAll();
        int id = getIDAccountFromAuthService.common();
        for (Blog blog : blogs) {
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
            Collections.reverse(blogCommentDTOS);

            BlogInfoDTO blogInfoDTO = new BlogInfoDTO();
            List<BlogImage> blogImages = iBlogImageService.findBlogImagesByBlogID(blog.getBlogid());
            List<BlogFlower> blogFlowers = iBlogFlowerService.findBlogFlowerByBlogID(blog.getBlogid());
            List<Integer> flowerIds = blogFlowers.stream()
                    .map(bf -> bf.getFlower().getFlowerID())
                    .collect(Collectors.toList());

            List<ProductDTO> flowerDTOList = flowerService.getFlowerDTOsByFlowerIds(flowerIds);
            for (ProductDTO productDTO : flowerDTOList) {
                EventFlower eventFlower = eventFlowerService.findEventFlowerByFlowerSizeID(productDTO.getFlowerSizeID());
                if (eventFlower != null && eventFlower.getSaleoff() != null) {
                    BigDecimal discountAmount = productDTO.getPrice().multiply(eventFlower.getSaleoff().divide(BigDecimal.valueOf(100)));
                    productDTO.setPriceEvent(productDTO.getPrice().subtract(discountAmount));
                    productDTO.setSaleOff(eventFlower.getSaleoff());
                }
            }
            List<BlogInteract> blogInteracts = iBlogInteractService.findLikeBlogYet(blog.getBlogid(), accountid);
            BlogInteract pinBlog = iBlogInteractService.findBlogInteractByAccountIDAndBlogpinID(accountid,blog.getBlogid());
            blogInfoDTO.setBlog(blog);
            blogInfoDTO.setBlogCommentDTOS(blogCommentDTOS);
            blogInfoDTO.setBlogImages(blogImages);
            blogInfoDTO.setBlogFlower(flowerDTOList);

            // Sử dụng đúng cách tính tổng số comment: comment cha + tất cả comment con
            int totalComments = blogCommentDTOS.size(); // Số comment cha
            for (BlogCommentDTO blogCommentDTO : blogCommentDTOS) {
                totalComments += blogCommentDTO.getNumberCommentChild(); // Cộng số comment con
            }
            blogInfoDTO.setNumberComments(totalComments);
            if (pinBlog != null)
            {
                blogInfoDTO.setPinBlog(Boolean.TRUE);
            }
            else
            {
                blogInfoDTO.setPinBlog(Boolean.FALSE);
            }
            blogInfoDTO.setLikeBlog(blogInteracts != null && !blogInteracts.isEmpty());
            blogInfoDTOS.add(blogInfoDTO);
        }
        Collections.reverse(blogInfoDTOS);

        Map<String, Object> response = new HashMap<>();
        response.put("BlogInfo", blogInfoDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogID(@PathVariable int id) {
        int accountid = getIDAccountFromAuthService.common();
        List<BlogInfoDTO> blogInfoDTOList = new ArrayList<>();
        BlogInfoDTO blogInfoDTO = new BlogInfoDTO();
        List<Flower> flowers = flowerService.findAll();

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
        for (ProductDTO productDTO : flowerDTOList) {
            EventFlower eventFlower = eventFlowerService.findEventFlowerByFlowerSizeID(productDTO.getFlowerSizeID());
            if (eventFlower != null && eventFlower.getSaleoff() != null) {
                BigDecimal discountAmount = productDTO.getPrice().multiply(eventFlower.getSaleoff().divide(BigDecimal.valueOf(100)));
                productDTO.setPriceEvent(productDTO.getPrice().subtract(discountAmount));
                productDTO.setSaleOff(eventFlower.getSaleoff());
            }
        }
        BlogInteract pinBlog = iBlogInteractService.findBlogInteractByAccountIDAndBlogpinID(accountid,blog.getBlogid());
        if (pinBlog != null)
        {
            blogInfoDTO.setPinBlog(Boolean.TRUE);
        }
        else
        {
            blogInfoDTO.setPinBlog(Boolean.FALSE);
        }
        blogInfoDTO.setBlog(blog);
        Collections.reverse(blogCommentDTOS);
        blogInfoDTO.setBlogCommentDTOS(blogCommentDTOS);
        blogInfoDTO.setBlogImages(blogImages);
        blogInfoDTO.setBlogFlower(flowerDTOList);

        int totalComments = blogCommentDTOS.size(); // Số comment cha
        for (BlogCommentDTO blogCommentDTO : blogCommentDTOS) {
            totalComments += blogCommentDTO.getNumberCommentChild(); // Cộng số comment con
        }
        blogInfoDTO.setNumberComments(totalComments);

        blogInfoDTO.setLikeBlog(blogInteracts != null && !blogInteracts.isEmpty());
        blogInfoDTOList.add(blogInfoDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("BlogInfo", blogInfoDTOList);
        response.put("flowers", flowers);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getComment(@PathVariable int id)
    {
        int accountid = getIDAccountFromAuthService.common();
        BlogComment blogComment = iBlogCommentService.findBlogCommentByBlogCommentID(id);
        List<BlogCommentDTO> blogCommentDTOList = new ArrayList<>();
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
        blogCommentDTOList.add(blogCommentDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("blogCommentDTO", blogCommentDTOList);
        return ResponseEntity.ok(response);
    }
}
