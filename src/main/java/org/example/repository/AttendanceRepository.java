package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Attendance;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    @Query("SELECT a FROM Attendance a WHERE a.account.accountID = :accountId AND MONTH(a.date) = :month AND a.status =:status")
    List<Attendance> findAttendanceByAccountAndMonth(@Param("accountId") int accountId, @Param("month") int month, @Param("status") Status status);

    Attendance findAttendanceByAccount_AccountIDAndDateAndStatus(int id, LocalDateTime localDateTime, Status status);
}
