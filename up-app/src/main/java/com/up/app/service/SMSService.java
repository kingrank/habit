package com.up.app.service;

import com.jfinal.kit.Kv;
import com.up.app.model.cm.Config;
import com.up.app.service.cm.ConfigService;
import com.up.habit.Habit;
import com.up.habit.controller.render.To;
import com.up.habit.expand.sms.SMSManager;
import com.up.habit.kit.StrKit;
import com.up.habit.service.HabitService;

/**
 * TODO:
 * <p>
 * Created by 王剑洪 on 2020/1/30 20:00
 */
public class SMSService {

    public static SMSService me = new SMSService();

    public static final String CAPTCHA_NAME = "_sms_captcha";
    /**
     * 过期时间,默认5分钟
     */
    public static final int EXPIRE_SECOND = 60 * 5;

    public static final String ID_ACCESS_KEY = "ali-access-key";
    public static final String ID_ACCESS_SECRET = "ali-access-secret";
    public static final String ID_ACCESS_SIGN = "ali-sms-sign";
    public static final String ID_ACCESS_TEMPLATE_CAPTCHA = "ali-sms-template-captcha";


    /**
     * TODO:发送短信验证码
     *
     * @param prefix
     * @param phone
     * @return
     */
    public To captcha(String prefix, String phone) {
        if (StrKit.isBlank(phone)) {
            return To.fail("手机号码不能为空!");
        }
        phone = prefix + phone;
        String captcha = Habit.getCache().get(CAPTCHA_NAME, phone);
        if (StrKit.isBlank(captcha)) {
            String accessKeyId = ConfigService.me.getById(ID_ACCESS_KEY);
            String accessSecret = ConfigService.me.getById(ID_ACCESS_SECRET);
            String signName = ConfigService.me.getById(ID_ACCESS_SIGN);
            String templateCode = ConfigService.me.getById(ID_ACCESS_TEMPLATE_CAPTCHA);
            captcha = StrKit.getRandomString(true, 6);
            boolean res = SMSManager.me.aliSend(accessKeyId, accessSecret, signName, templateCode, prefix + phone, Kv.by("code", captcha));
            if (!res) {
                return To.fail("验证码发送失败!");
            }
        }
        Habit.getCache().put(CAPTCHA_NAME, phone, captcha, EXPIRE_SECOND);
        return To.ok("验证码发送成功", Habit.dev() ? Kv.by("code", captcha) : null);
    }

    /**
     * TODO:验证码验证
     *
     * @param prefix
     * @param phone
     * @param code
     * @return
     */
    public boolean validateCaptcha(String prefix, String phone, String code) {
        if (StrKit.isBlank(code)) {
            return false;
        }
        phone = prefix + phone;
        String captcha = Habit.getCache().get(CAPTCHA_NAME, phone);
        return code.equals(captcha);
    }


}
