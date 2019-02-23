package com.druidelf.novelmain;

import com.druidelf.novelmain.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NovelMainApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailService mailService;
    @Test
    public void contextLoads() {
       /* List<String> list =new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("v");
        stringRedisTemplate.opsForValue().set("abc", "测试");
        stringRedisTemplate.opsForList().leftPushAll("qq",list); // 向redis存入List
        stringRedisTemplate.opsForList().range("qwe",0,-1).forEach(value ->{
            System.out.println(value);

        });*/
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("druid_elf@163.com");
//        message.setTo("844748180@qq.com");
//        message.setSubject("主题：简单邮件");
//        message.setText("测试邮件内容000");
//        mailSender.send(message);
    }

}
