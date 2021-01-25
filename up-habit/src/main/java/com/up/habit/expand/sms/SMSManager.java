package com.up.habit.expand.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.jfinal.log.Log;
import com.up.habit.kit.StrKit;

import java.rmi.ServerException;
import java.util.Map;

/**
 * TODO:
 * <p>
 * Created by 王剑洪 on 2020/1/29 22:37
 */
public class SMSManager {

    private Log log = Log.getLog("SMSManager");

    public static SMSManager me=new SMSManager();




    /***
     * TODO:阿里云短信发送
     * @param accessKeyId
     * @param accessSecret
     * @param signName
     * @param templateCode
     * @param tel
     * @param param
     * @return
     */
    public boolean aliSend(String accessKeyId, String accessSecret, String signName, String templateCode, String tel, Map<String, String> param) {
        return aliSend(accessKeyId, accessSecret, null, null, null, signName, templateCode, tel, param);
    }

    /**
     * TODO:阿里云短信发送
     *
     * @param accessKeyId  主账号AccessKey的ID。
     * @param accessSecret
     * @param domain       域名
     * @param version      版本
     * @param action       系统规定参数。取值：SendSms。
     * @param signName     短信签名名称
     * @param templateCode 短信模板ID
     * @param tel          接收短信的手机号码
     * @param param        短信模板变量对应的实际值，JSON格式。
     */
    public boolean aliSend(String accessKeyId, String accessSecret, String domain, String version, String action,
                           String signName, String templateCode, String tel, Map<String, String> param) {
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(StrKit.isBlank(domain) ? "dysmsapi.aliyuncs.com" : domain);
        request.setVersion(StrKit.isBlank(version) ? "2017-05-25" : version);
        request.setAction(StrKit.isBlank(action) ? "SendSms" : action);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        Gson gson = new Gson();
        request.putQueryParameter("TemplateParam", gson.toJson(param));
        if (tel.startsWith("+86")) {
            /**国内短信：11位手机号码*/
            tel = tel.substring(3);
        } else if (tel.startsWith("+")) {
            /**国际/港澳台消息：国际区号+号码*/
            tel = tel.substring(1);
        }
        request.putQueryParameter("PhoneNumbers", tel);
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map res = gson.fromJson(response.getData(), Map.class);
            if (res.containsKey("Code") && "OK".equals(String.valueOf(res.get("code")))) {
                log.info("\n[accessKeyId]=" + accessKeyId + ",[accessSecret]=" + accessSecret
                        + "\n,[signName]=" + signName + ",[templateCode]=" + templateCode
                        + "\n[phone]=" + tel
                        + "\n[param]=" + gson.toJson(param));
                return true;
            } else {
                log.error("\n[accessKeyId]=" + accessKeyId + ",[accessSecret]=" + accessSecret
                        + "\n,[signName]=" + signName + ",[templateCode]=" + templateCode
                        + "\n[phone]=" + tel
                        + "\n[param]=" + gson.toJson(param));
            }
        } catch (Exception e) {
            log.error("\n[accessKeyId]=" + accessKeyId + ",[accessSecret]=" + accessSecret
                    + "\n,[signName]=" + signName + ",[templateCode]=" + templateCode
                    + "\n[phone]=" + tel
                    + "\n[param]=" + gson.toJson(param), e);
        }
        return false;

    }


}
