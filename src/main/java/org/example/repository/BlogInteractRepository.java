package org.example.repository;

import org.example.entity.BlogComment;
import org.example.entity.BlogInteract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogInteractRepository extends JpaRepository<BlogInteract, Integer> {
}
