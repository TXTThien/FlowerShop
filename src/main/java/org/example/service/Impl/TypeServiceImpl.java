package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Type;
import org.example.entity.enums.Status;
import org.example.repository.TypeRepository;
import org.example.service.ITypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements ITypeService {
    private final TypeRepository typeRepository;
    @Override
    public Type findTypeById(int id) {
        return typeRepository.findTypeByTypeIDAndStatus(id, Status.ENABLE);
    }

    @Override
    public List<Type> findAllOrderByMinConsumeAsc() {
        return typeRepository.findTypesByStatusOrderByMinConsumeAsc(Status.ENABLE);
    }
}
