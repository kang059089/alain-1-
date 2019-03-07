package com.bigcloud.alain.service.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.bigcloud.alain.config.AliCommonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

// 阿里云发送短信工具类
@Component
public class SendSmsUtil {

    @Autowired
    private AliCommonProperties properties;

    private static SendSmsUtil sendSmsUtil;

    /**
     * PostContruct是spring框架的注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
     */
    @PostConstruct
    public void init() {
        sendSmsUtil = this;
        sendSmsUtil.properties= this.properties;
    }

    /**
     * 发送短信验证码
     * @param phone 手机号码
     * @param code 验证码
     * @return
     */
    public static CommonResponse sendCode(String phone, String code) {
        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(
            "default",          // 地域ID
            sendSmsUtil.properties.getAccessKeyId(),      // RAM账号的AccessKey ID
            sendSmsUtil.properties.getAccessKeySecret()); // RAM账号Access Key Secret
        IAcsClient client = new DefaultAcsClient(profile);

        // 组装请求对象
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        // 必填：待发送手机号
        request.putQueryParameter("PhoneNumbers", phone);
        // 必填：短信签名名称（在阿里云短信服务控制台查看）
        request.putQueryParameter("SignName", "阿里云");
        // 必填：短信模板ID（在阿里云短信服务控制台查看）
        request.putQueryParameter("TemplateCode", "SMS_153055065");
        // 可选：短信模板变量对应的实际值（这里即验证码）
        request.putQueryParameter("TemplateParam", code);

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看短信发送记录和发送状态
     * @param newPhone 接收短信的手机号码
     * @param date 短信发送日期
     * @param bizId 发送回执ID
     * @return
     */
    public static CommonResponse querySendDetails(String newPhone, String date, String bizId) {
        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(
            "default",          // 地域ID
            sendSmsUtil.properties.getAccessKeyId(),      // RAM账号的AccessKey ID
            sendSmsUtil.properties.getAccessKeySecret()); // RAM账号Access Key Secret
        IAcsClient client = new DefaultAcsClient(profile);

        // 组装请求对象
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("QuerySendDetails");
        // 必填：接收短信的手机号码
        request.putQueryParameter("PhoneNumber", newPhone);
        // 必填：短信发送日期，支持查询最近30天的记录（格式为yyyyMMdd）
        request.putQueryParameter("SendDate", date);
        // 必填：分页查看发送记录，指定每页显示的短信记录数量
        request.putQueryParameter("PageSize", "10");
        // 必填：分页查看发送记录，指定发送记录的的当前页码
        request.putQueryParameter("CurrentPage", "1");
        // 选填：发送回执ID，即发送流水号
        request.putQueryParameter("BizId", bizId);

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
