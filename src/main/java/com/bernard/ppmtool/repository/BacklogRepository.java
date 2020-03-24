package com.bernard.ppmtool.repository;

import com.bernard.ppmtool.domain.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {
    Backlog findByProjectIdentifier(String id);
}
