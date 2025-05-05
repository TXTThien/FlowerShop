package org.example.service;

import org.example.entity.RollBar;

import java.util.List;

public interface IRollBarService {
    RollBar findRollBarById(int id);

    List<RollBar> findAll();

    RollBar findRollBarByIdByAdmin(int id);
}
