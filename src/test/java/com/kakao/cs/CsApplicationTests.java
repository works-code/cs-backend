package com.kakao.cs;

import com.kakao.cs.service.AnswerService;
import com.kakao.cs.service.InquiryService;
import com.kakao.cs.service.UserService;
import com.kakao.cs.vo.entity.Answer;
import com.kakao.cs.vo.entity.Inquiry;
import com.kakao.cs.vo.entity.User;
import com.kakao.cs.vo.validation.AnswerValidation;
import com.kakao.cs.vo.validation.InquiryValidation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class CsApplicationTests {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private UserService userService;

    @Test
    void 문의글_등록_조회_테스트() {
        // 문의 등록
        String customerId = "kakaopay";
        inquiryService.setInquiry(Inquiry.builder()
                .customerId(customerId)
                .title("제목")
                .content("내용")
                .build());
        // 문의 조회 (문의 등록한 이력이 는 사용자 ID 조회)
        List<Inquiry> results = inquiryService.getInquirysByCostomerId(customerId);
        Assert.assertFalse(CollectionUtils.isEmpty(results));
        Assert.assertEquals(results.get(0).getCustomerId(),customerId);
        Assert.assertEquals(results.get(0).getTitle(),"제목");
        Assert.assertEquals(results.get(0).getContent(),"내용");
        Assert.assertEquals(results.get(0).getCounselorId(),null);
        Assert.assertEquals(results.get(0).getAnswerSeq(),null);
        Assert.assertEquals(results.get(0).getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")));

        // 문의 조회 (문의 등록한 이력이 없는 사용자 ID 조회)
        customerId = "empty";
        results = inquiryService.getInquirysByCostomerId(customerId);
        Assert.assertNotEquals(results, null);
        Assert.assertEquals(results.size(), 0);
    }

    @Test
    void 문의글_상담사_등록_테스트() {
        String customerId = "testID";
        Inquiry inquiry = Inquiry.builder()
                .customerId(customerId)
                .title("제목")
                .content("내용")
                .build();
        // 문의글 등록
        inquiryService.setInquiry(inquiry);
        // 문의글 조회
        Inquiry list = inquiryService.getInquirysByCostomerId("testID").get(0);

        // 상담사 등록 (상담사가 지정되지 않은 문의글 일시)
        InquiryValidation.CounselorSet counselor = new InquiryValidation.CounselorSet();
        counselor.setInquirySeq(list.getSeq());
        counselor.setCounselorId("counselor");
        Assert.assertTrue(inquiryService.checkAndInquiryCounselor(counselor));

        // 상담사 등록 (상담사가 지정된 문의글 일시)
        inquiry.setCounselorId("counselor");
        inquiryService.setCounselorId(inquiry);
        Assert.assertFalse(inquiryService.checkAndInquiryCounselor(counselor));
    }

    @Test
    void 문의글_답변_등록_조회_테스트() {
        String customerId = "yoolee";
        Inquiry inquiry = Inquiry.builder()
                .customerId(customerId)
                .title("제목")
                .content("내용")
                .build();
        // 문의글 등록
        inquiryService.setInquiry(inquiry);

        // 답변 등록 (아직 답변 없는 경우)
        AnswerValidation.AnswerRegister answer = new AnswerValidation.AnswerRegister();
        answer.setInquirySeq(1L);
        answer.setContent("답변");
        answer.setCounselorId("admin");
        answer.setCounselorName("상담사");

        Assert.assertTrue(answerService.checkAndSetAnswerByQueue(answer));

        // 답변 등록 (답변이 이미 등록된 경우)
        answerService.setAnswer(inquiry, Answer.builder()
                .counselorName("상담사")
                .counselorId("admin")
                .content("답변").build());
        Assert.assertFalse(answerService.checkAndSetAnswerByQueue(answer));

        // 답변 조회
        Answer info = answerService.getAnswersBySeq(1L);
        Assert.assertEquals(info.getSeq(), (Long)1L);
        Assert.assertEquals(info.getCounselorId(), "admin");
        Assert.assertEquals(info.getCounselorName(), "상담사");
        Assert.assertEquals(info.getContent(), "답변");
        Assert.assertEquals(info.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")));
    }

    @Test
    public void 미답변문의글_조회_테스트(){
        // 문의글 추가 등록 및 미답변 문의글만 조회
        inquiryService.setInquiry(Inquiry.builder()
                .customerId("testId")
                .title("제목")
                .content("내용")
                .build());

        Inquiry inquiry = Inquiry.builder()
                .customerId("answerId")
                .title("제목")
                .content("내용")
                .build();
        inquiryService.setInquiry(inquiry);
        answerService.setAnswer(inquiry, Answer.builder()
                .counselorName("상담사")
                .counselorId("admin")
                .content("답변").build());

        List<Inquiry> inquiries = inquiryService.getInquirysNotExistAnswer();
        List<String> noAnswerCustomerIds = inquiries.stream().map(s -> s.getCustomerId()).collect(Collectors.toList());
        Assert.assertTrue(noAnswerCustomerIds.contains("testId"));
        Assert.assertFalse(noAnswerCustomerIds.contains("answerId"));
    }


    @Test
    public void 사용자조회_테스트(){
        userService.join(User.builder().username("admin").password("admin").build());

        Assert.assertTrue(userService.login("admin","admin"));
        Assert.assertFalse(userService.login("admin","test"));
        Assert.assertFalse(userService.login("test","admin"));
    }
}
