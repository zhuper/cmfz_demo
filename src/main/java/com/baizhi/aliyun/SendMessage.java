package com.baizhi.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SendMessage {

    public static void senMessage (Integer code) throws Exception{
//设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
       // final String accessKeyId = "LTAIaEI24AEjv8Xr";//你的accessKeyId,参考本文档步骤2
        final String accessKeyId = "LTAI4FuycnLZTisE3o6ZBTdm";//你的accessKeyId,参考本文档步骤2

        //final String accessKeySecret = "P9CEJSnvgfNXDSbKpdu97tW61KgbSN";//你的accessKeySecret，参考本文档步骤2

        final String accessKeySecret = "Q5rEEriC0M8Kx9GRz2iroisRogb5YQ";

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，
        //15263311208,13464368269,18905151717,18012000342
        request.setPhoneNumbers("15124158139");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("天青色等烟雨");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_176936151");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{'code':"+code+"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println(sendSmsResponse.getCode());
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            System.out.println("请求成功");
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("开始发送短信");
                //创建随机数
            for (int i = 1; i <7 ; i++) {
                int number=(int)(Math.random()*1000000)+1;
                senMessage(number);
            }
            System.out.println("短信发送成功");
        } catch (Exception e) {
            System.out.println("短信发送失败");
            e.printStackTrace();
        }
    }



/*

    //工具类
    public static String getCode() {
        return "{\"code\":\""+((int)((Math.random()*9+1)*100000))+"\"}";
    }
*/

}

