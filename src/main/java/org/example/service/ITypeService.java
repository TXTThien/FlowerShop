package org.example.service;

import org.example.entity.Type;

import java.util.List;

public interface ITypeService {
    Type findTypeById(int id);
    List<Type> findAllOrderByMinConsumeAsc();
}
