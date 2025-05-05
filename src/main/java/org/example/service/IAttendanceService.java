package org.example.service;

import org.example.entity.Attendance;

import java.time.LocalDateTime;
import java.util.List;

public interface IAttendanceService {
    List<Attendance> findAttendanceByAccountAndMonth (int id, int month);

    Attendance findAttendanceByAccountAndDate(int account, LocalDateTime localDateTime);

    List<Attendance> findAttendanceByAccount (int id);

}
