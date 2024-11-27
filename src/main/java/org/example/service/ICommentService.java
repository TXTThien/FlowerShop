package org.example.service;

import org.example.entity.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> findCommentByAccountIDEnable(int idAccount);
    Comment findCommentByID(int id);
}
