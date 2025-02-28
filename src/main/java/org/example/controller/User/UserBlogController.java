package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.BlogCommentDTO;
import org.example.dto.BlogInfoDTO;
import org.example.dto.ProductDTO;
import org.example.dto.RequestBodyBlog;
import org.example.entity.*;
import org.example.repository.BlogCommentRepository;
import org.example.repository.BlogImageRepository;
import org.example.repository.BlogInteractRepository;
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
@RequestMapping("/blog")
@RequiredArgsConstructor
public class UserBlogController {
    private final BlogRepository blogRepository;
    private final IBlogService iBlogService;
    private final IBlogInteractService iBlogInteractService;
    private final IBlogCommentService iBlogCommentService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    private final BlogInteractRepository blogInteractRepository;
    private final BlogCommentRepository blogCommentRepository;
    private final BlogImageRepository blogImageRepository;



    @RequestMapping("/like")
    public ResponseEntity<?> likePostOrComment(@RequestBody RequestBodyBlog requestBodyBlog) {
        int accountId = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountId);

        // Xử lý trường hợp like blog
        if (requestBodyBlog.getBlogid() != null && requestBodyBlog.getCommentid() == null) {
            Blog blog = iBlogService.findBlogByBlogID(requestBodyBlog.getBlogid());
            if (blog == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
            }
            BlogInteract existBlogInteract = iBlogInteractService.findBlogInteractByAccountIDAndBlogID(accountId,requestBodyBlog.getBlogid());
            if (existBlogInteract!=null)
            {
                blogInteractRepository.delete(existBlogInteract);
                blog.setLike(blog.getLike().subtract(BigInteger.ONE));
                blogRepository.save(blog);
                return ResponseEntity.ok("Blog disliked successfully");

            }
            blog.setLike(blog.getLike().add(BigInteger.ONE));
            BlogInteract blogInteract = new BlogInteract();
            blogInteract.setAccount(account);
            blogInteract.setBloglike(blog);

            blogRepository.save(blog); // Lưu blog với số like tăng
            blogInteractRepository.save(blogInteract); // Lưu thông tin tương tác

            return ResponseEntity.ok("Blog liked successfully");
        }
        else if (requestBodyBlog.getBlogid() == null && requestBodyBlog.getCommentid() != null) {
            BlogComment blogComment = iBlogCommentService.findBlogCommentByBlogCommentID(requestBodyBlog.getCommentid());

            if (blogComment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
            }
            BlogInteract existBlogInteract = iBlogInteractService.findBlogInteractByAccountIDAndCommentID(accountId,requestBodyBlog.getCommentid());
            if (existBlogInteract!=null)
            {
                blogInteractRepository.delete(existBlogInteract);
                blogComment.setLike(blogComment.getLike().subtract(BigInteger.ONE));
                blogCommentRepository.save(blogComment);
                return ResponseEntity.ok("Comment disliked successfully");
            }
            blogComment.setLike(blogComment.getLike().add(BigInteger.ONE));
            BlogInteract blogInteract = new BlogInteract();
            blogInteract.setAccount(account);
            blogInteract.setBlogComment(blogComment);

            blogCommentRepository.save(blogComment); // Lưu comment với số like tăng
            blogInteractRepository.save(blogInteract); // Lưu thông tin tương tác

            return ResponseEntity.ok("Comment liked successfully");
        }
        // Xử lý trường hợp không hợp lệ
        else {
            return ResponseEntity.badRequest().body("Invalid request: Either blogid or commentid must be provided, not both");
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> commentBlogOrComment(@RequestBody RequestBodyBlog requestBodyBlog) {
        int accountId = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountId);

        if (requestBodyBlog.getBlogid() != null && requestBodyBlog.getCommentid() == null) {
            // Commenting on a blog
            return handleBlogComment(requestBodyBlog, account);
        } else if (requestBodyBlog.getBlogid() == null && requestBodyBlog.getCommentid() != null) {
            // Replying to a comment
            return handleCommentReply(requestBodyBlog, account);
        } else {
            return ResponseEntity.badRequest().body("Invalid request: Provide either blogid or commentid, not both.");
        }
    }

    private ResponseEntity<?> handleBlogComment(RequestBodyBlog requestBodyBlog, Account account) {
        Blog blog = iBlogService.findBlogByBlogID(requestBodyBlog.getBlogid());
        if (blog == null) {
            return ResponseEntity.badRequest().body("Blog not found");
        }

        BlogComment blogComment = new BlogComment();
        blogComment.setComment(requestBodyBlog.getComment());
        blogComment.setAccount(account);
        blogComment.setBlog(blog);
        blogComment.setDate(LocalDateTime.now());
        blogComment.setLike(BigInteger.ZERO);
        blogCommentRepository.save(blogComment);

        saveBlogImages(requestBodyBlog.getImageurl(), blogComment);

        return ResponseEntity.ok("Comment added successfully");
    }

    private ResponseEntity<?> handleCommentReply(RequestBodyBlog requestBodyBlog, Account account) {
        BlogComment parentComment = iBlogCommentService.findBlogCommentByBlogCommentID(requestBodyBlog.getCommentid());
        if (parentComment == null) {
            return ResponseEntity.badRequest().body("Parent comment not found");
        }
        while (parentComment.getFatherComment()!=null)
        {
            parentComment = parentComment.getFatherComment();
        }
        BlogComment blogComment = new BlogComment();
        blogComment.setComment(requestBodyBlog.getComment());
        blogComment.setAccount(account);
        blogComment.setFatherComment(parentComment);
        blogComment.setDate(LocalDateTime.now());
        blogComment.setLike(BigInteger.ZERO);
        blogCommentRepository.save(blogComment);

        saveBlogImages(requestBodyBlog.getImageurl(), blogComment);

        return ResponseEntity.ok("Response comment added successfully");
    }

    private void saveBlogImages(List<String> imageUrls, BlogComment blogComment) {
        if (imageUrls != null) {
            for (String imageUrl : imageUrls) {
                BlogImage blogImage = new BlogImage();
                blogImage.setBlogComment(blogComment);
                blogImage.setImage(imageUrl);
                blogImageRepository.save(blogImage);
            }
        }
    }

    @RequestMapping("/pin")
    public ResponseEntity<?> pinBlog(@RequestBody RequestBodyBlog requestBodyBlog){
        int accountId = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountId);
        if (requestBodyBlog.getBlogid() != null)
        {
            Blog blog = iBlogService.findBlogByBlogID(requestBodyBlog.getBlogid());
            BlogInteract existBlogInteract = iBlogInteractService.findBlogInteractByAccountIDAndBlogpinID(accountId,requestBodyBlog.getBlogid());
            if (existBlogInteract!=null)
            {
                blogInteractRepository.delete(existBlogInteract);
                return ResponseEntity.ok("Blog unpin successfully");
            }
            BlogInteract blogInteract = new BlogInteract();
            blogInteract.setAccount(account);
            blogInteract.setBlogpin(blog);

            blogInteractRepository.save(blogInteract);
            return ResponseEntity.ok("Pin Blog  successfully");
        }
        else {
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }
}
