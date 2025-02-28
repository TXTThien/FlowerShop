package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.BlogImage;
import org.example.entity.BlogInteract;
import org.example.repository.BlogInteractRepository;
import org.example.service.IBlogInteractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogInteractServiceImpl implements IBlogInteractService {
    private final BlogInteractRepository blogInteractRepository;
    @Override
    public List<BlogInteract> findLikeCommentYet(Integer blogcommentid, Integer accountid) {
        return blogInteractRepository.findBlogInteractByBlogComment_BlogcommentidAndAccount_AccountID(blogcommentid,accountid);
    }

    @Override
    public List<BlogInteract> findLikeBlogYet(Integer blogid, int accountid) {
        return blogInteractRepository.findBlogInteractByBloglike_BlogidAndAccount_AccountID(blogid,accountid);
    }

    @Override
    public BlogInteract findBlogInteractByAccountIDAndBlogID(int accountId, Integer blogid) {
        return blogInteractRepository.findBlogInteractByAccount_AccountIDAndBloglike_Blogid(accountId,blogid);
    }

    @Override
    public BlogInteract findBlogInteractByAccountIDAndCommentID(int accountId, Integer commentid) {
        return blogInteractRepository.findBlogInteractByAccount_AccountIDAndBlogComment_Blogcommentid(accountId,commentid);
    }

    @Override
    public BlogInteract findBlogInteractByAccountIDAndBlogpinID(int accountId, Integer blogid) {
        return blogInteractRepository.findBlogInteractByAccount_AccountIDAndBlogpin_Blogid(accountId,blogid);
    }

    @Override
    public List<BlogInteract> findBlogPin(int common) {
        return blogInteractRepository.findByAccount_AccountIDAndBlogpinIsNotNull(common);
    }
}
