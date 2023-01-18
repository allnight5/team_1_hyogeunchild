package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromoteRepository extends JpaRepository<Promote, Long> {

    Optional<Promote> findByUsername(String username);
    Optional<Promote> deleteByUsername(String username);

    //기준없이 전부다 가져온다.
    Page<Promote> findAll(Pageable pageable);
    Page<Promote> findByUsername(String username, int i , Pageable pageable);
}
