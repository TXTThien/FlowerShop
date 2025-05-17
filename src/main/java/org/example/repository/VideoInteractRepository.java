package org.example.repository;

import org.example.entity.VideoInteract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoInteractRepository extends JpaRepository<VideoInteract, Integer> {
    List<VideoInteract> findVideoInteractsByVideoComment_IdAndAccountID_AccountID(int id, int accountid);
    List<VideoInteract> findVideoInteractsByVideo_IdAndAccountID_AccountID(int id, int accountid);

}
