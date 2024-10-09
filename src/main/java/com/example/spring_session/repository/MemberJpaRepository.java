package com.example.spring_session.repository;

import com.example.spring_session.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsername(String username);

    Page<Member> findByAgeGreaterThanEqual(int age, Pageable pageable); // 나이가 입력값보다 크거나 같은 요소
    Page<Member> findByUsernameStartingWith(String word, Pageable pageable); // 이름이 주어진 값으로 시작하는 경우
}
