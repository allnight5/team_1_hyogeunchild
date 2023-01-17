package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromoteRepository extends JpaRepository<Promote, Long> {

    Optional<Promote> findByUsername(String username);
    Optional<Promote> deleteByUsername(String username);
}
