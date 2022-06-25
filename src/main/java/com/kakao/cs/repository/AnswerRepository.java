package com.kakao.cs.repository;

import com.kakao.cs.vo.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
