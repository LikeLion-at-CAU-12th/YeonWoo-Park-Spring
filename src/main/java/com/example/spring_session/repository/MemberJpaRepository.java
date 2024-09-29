package com.example.spring_session.repository;

import com.example.spring_session.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsername(String username);
}
