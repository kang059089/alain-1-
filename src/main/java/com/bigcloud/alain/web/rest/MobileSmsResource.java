package com.bigcloud.alain.web.rest;

import com.aliyuncs.CommonResponse;
import com.bigcloud.alain.service.util.SendSmsUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class MobileSmsResource {

    private final Logger log = LoggerFactory.getLogger(MobileSmsResource.class);

    @GetMapping("/sendSms/{newPhone}")
    @Timed
    public String sendSms(@PathVariable String newPhone) {
        log.debug("待发送短信的手机号码为: {}", newPhone);
        // 生成6位短信验证码
        String captcha = String
            .valueOf(new Random().nextInt(899999) + 100000);
        // 调用阿里云短信服务向用户发送验证码（未测试）
        CommonResponse response = SendSmsUtil.sendCode(newPhone, captcha);
        String data = response.getData();
        // 返回短信调用状态
        return "发送短信成功";
    }

}
