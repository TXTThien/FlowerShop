package org.example.repository;

import org.example.entity.BlogComment;
import org.example.entity.BlogInteract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogInteractRepository extends JpaRepository<BlogInteract, Integer> {
    List<BlogInteract> findBlogInteractByBlogComment_BlogcommentidAndAccount_AccountID(int cmtid, int accountid);
    List<BlogInteract> findBlogInteractByBloglike_BlogidAndAccount_AccountID(int blogid, int accountid);

    BlogInteract findBlogInteractByAccount_AccountIDAndBloglike_Blogid(int accountid, int blogid);
    BlogInteract findBlogInteractByAccount_AccountIDAndBlogComment_Blogcommentid(int accountid, int blogid);

    BlogInteract findBlogInteractByAccount_AccountIDAndBlogpin_Blogid(int accountid, int blogid);

    List<BlogInteract> findByAccount_AccountIDAndBlogpinIsNotNull(int accountid);



}
