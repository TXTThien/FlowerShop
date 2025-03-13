package org.example.service;

import org.example.entity.BlogInteract;

import java.util.List;

public interface IBlogInteractService {
    List<BlogInteract> findLikeCommentYet(Integer blogcommentid, Integer accountid);

    List<BlogInteract> findLikeBlogYet(Integer blogid, int accountid);

    BlogInteract findBlogInteractByAccountIDAndBlogID(int accountId, Integer blogid);

    BlogInteract findBlogInteractByAccountIDAndCommentID(int accountId, Integer commentid);

    BlogInteract findBlogInteractByAccountIDAndBlogpinID(int accountId, Integer blogid);

    List<BlogInteract> findBlogPin(int common);

    int countPinBlog(int blogid);
}
