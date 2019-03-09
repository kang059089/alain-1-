package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.service.MailService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class EMailResource {

    private final Logger log = LoggerFactory.getLogger(EMailResource.class);

    public static String EMAIL_CAPTCHA_CACHE = "emailCaptcha";

    private final MailService mailService;

    private final CacheManager cacheManager;

    public EMailResource(MailService mailService, CacheManager cacheManager) {
        this.mailService = mailService;
        this.cacheManager = cacheManager;
    }

    /**
     *  生成验证码向需绑定的新邮箱发送验证码
     * @param newEmail 需绑定的新邮箱
     * @return 返回新邮箱及验证码的map集合
     */
    @GetMapping("/sendEmail/{newEmail}")
    @Timed
    public Map<String, String> sendEmail(@PathVariable String newEmail) {
        log.debug("待发送验证码的邮箱为: {}", newEmail);
        Map<String, String> map = new HashMap<>();
        // 生成6位邮箱验证码
        String captcha = String
            .valueOf(new Random().nextInt(899999) + 100000);
        mailService.sendCaptchaEmail(newEmail, captcha);
        Cache cache = cacheManager.getCache(EMAIL_CAPTCHA_CACHE);
        cache.put("emailCaptcha", captcha);
        map.put("email", newEmail);
        map.put("captcha", captcha);
        return map;
    }

}
