package com.kakao.cs.vo.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_INQUIRT")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Column(insertable = false)
    private String counselorId;

    @Column(insertable = false)
    private Long answerSeq;

    @Builder
    public Inquiry(String customerId, String title, String content) {
        this.customerId = customerId;
        this.title = title;
        this.content = content;
    }
}
