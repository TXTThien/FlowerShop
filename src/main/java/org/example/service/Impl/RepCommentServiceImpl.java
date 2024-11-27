package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.RepComment;
import org.example.entity.enums.Status;
import org.example.repository.RepCommentRepository;
import org.example.service.IRepCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepCommentServiceImpl implements IRepCommentService {
    private final RepCommentRepository repCommentRepository;
    @Override
    public List<RepComment> findRepCommentByCommentID(int id) {
        return repCommentRepository.findRepCommentByComment_CommentIDAndStatus(id, Status.ENABLE);
    }
}
