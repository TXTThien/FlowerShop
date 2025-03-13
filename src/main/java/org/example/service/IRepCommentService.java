package org.example.service;

import org.example.entity.Account;
import org.example.entity.RepComment;

import java.util.List;

public interface IRepCommentService {
    List<RepComment> findRepCommentByCommentID(int id);
    RepComment findRepCommentByRepCommentID (int id);

    Account findStaffRepAccount(int commentid);
}
