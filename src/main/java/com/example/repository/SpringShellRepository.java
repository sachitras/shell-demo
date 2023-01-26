package com.example.repository;

import com.example.domain.CommandEventLogDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringShellRepository  extends JpaRepository<CommandEventLogDomain, Integer> {
}
