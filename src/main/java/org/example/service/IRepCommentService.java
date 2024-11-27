package org.example.service;

import org.example.entity.RepComment;

import java.util.List;

public interface IRepCommentService {
    List<RepComment> findRepCommentByCommentID(int id);
}
