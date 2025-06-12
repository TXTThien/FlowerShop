package org.example.service;

import org.example.entity.Customize;

import java.util.List;

public interface ICustomizeService {
    Customize findCustomizeByID(int customizeid);

    List<Customize> findMyCustomize(int accountid);
}
