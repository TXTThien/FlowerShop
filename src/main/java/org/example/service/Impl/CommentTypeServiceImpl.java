package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.CommentType;
import org.example.entity.enums.Status;
import org.example.repository.CommentTypeRepository;
import org.example.service.ICommentTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentTypeServiceImpl implements ICommentTypeService {
    private final CommentTypeRepository commentTypeRepository;
    @Override
    public List<CommentType> findAllEnable() {
        return commentTypeRepository.findAllByStatus(Status.ENABLE);
    }

    @Override
    public CommentType findTypebyID(int id) {
        return commentTypeRepository.findCommentTypeByCommenttypeID(id);
    }
}
