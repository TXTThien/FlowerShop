package org.example.service;

import org.example.entity.CustomDetail;

import java.util.List;

public interface ICustomDetailService {
    List<CustomDetail> findByCustomID(int id);
}
