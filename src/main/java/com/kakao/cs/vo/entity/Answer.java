package com.kakao.cs.vo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_ANSWER")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String counselorId;

    @Column(nullable = false)
    private String counselorName;

    @Column(nullable = false)
    private String content;

    @Column(insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Builder
    public Answer(String counselorId, String counselorName, String content) {
        this.counselorId = counselorId;
        this.counselorName = counselorName;
        this.content = content;
    }
}
