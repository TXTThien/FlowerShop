package org.example.repository;

import org.example.entity.CommentType;
import org.example.entity.Customize;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizeRepository extends JpaRepository<Customize, Integer> {
    Customize findCustomizeByCustomID(int id);
    List<Customize> findCustomizesByAccountID_AccountIDAndStatus(int id, Status status);
}
