package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Attendance;
import org.example.entity.enums.Status;
import org.example.repository.AttendanceRepository;
import org.example.service.IAttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements IAttendanceService {
    private final AttendanceRepository attendanceRepository;
    @Override
    public List<Attendance> findAttendanceByAccountAndMonth(int id, int month) {
        return attendanceRepository.findAttendanceByAccountAndMonth(id,month, Status.ENABLE);
    }

    @Override
    public Attendance findAttendanceByAccountAndDate(int account, LocalDateTime localDateTime) {
        return attendanceRepository.findAttendanceByAccount_AccountIDAndDateAndStatus(account,localDateTime,Status.ENABLE);
    }
}
