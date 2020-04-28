package sawca.support;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class SupportApplicationTests {

    @Resource
    JavaMailSender javaMailSender;

    @Test
    void contextLoads() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject("这是一封测试邮件");
//        message.setFrom("abc@bc.com");
//        message.setTo("abc@bc.com");
//        message.setCc("abc@bc.com");
//        message.setBcc("abc@bc.com");
//        message.setSentDate(new Date());
//        message.setText("Test Mail");
//        javaMailSender.send(message);
    }

}
