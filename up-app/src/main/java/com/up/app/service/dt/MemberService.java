package com.up.app.service.dt;

import com.up.app.model.dt.Member;
import com.up.app.util.CodeFactory;
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.kit.ValidatorKit;


/**
 * 会员信息 业务类
 * @author 会员信息 on 2021-01-20 09:54:45
 */
public class MemberService extends HabitService<Member> {
    private final Log log=Log.getLog(MemberService.class);

    public static MemberService me = new MemberService();

    /**
     * TODO:新增会员信息
     *
     * @param member
     * @return
     */
    public To add(Member member) {
        /*if(StrKit.isBlank(member.getId())){
            return To.fail("会员编号不能为空!");
        }*/
        member.setId(CodeFactory.getMemeberID());
        if(StrKit.isBlank(member.getOrgid())){
            return To.fail("请选择组织机构!");
        }
        Member m = member.findById(member.getId());
        if(m!=null){
            return To.fail("会员编号已存在!");
        }
        /*if(StrKit.isBlank(member.getOpenid())){
            return To.fail("微信标识不能为空!");
        }*/
        if(StrKit.isBlank(member.getName())){
            return To.fail("姓名不能为空!");
        }
        /*if(StrKit.isBlank(member.getNickname())){
            return To.fail("昵称不能为空!");
        }*/
        if(StrKit.isBlank(member.getTel())){
            return To.fail("手机号码不能为空!");
        }
        if(StrKit.isBlank(member.getCardid())){
            return To.fail("身份证号不能为空!");
        }
        if(member.getGender()==null){
            return To.fail("性别不能为空!");
        }
        if(member.getLevel()==null){
            return To.fail("级别不能为空!");
        }
        /*if(StrKit.isBlank(member.getAvatar())){
            return To.fail("头像不能为空!");
        }*/
        if(member.getBirth()==null){
            return To.fail("生日不能为空!");
        }
        /*if(member.getPoints()==null){
            return To.fail("剩余积分不能为空!");
        }*/
        /*if(member.getBalance()==null){
            return To.fail("余额不能为空!");
        }*/
        if(member.getStatus()==null){
            return To.fail("状态不能为空!");
        }
        /*if(member.getLogdate()==null){
            return To.fail("开户时间不能为空!");
        }*/

        return To.to(member.save());
    }

    /**
     * TODO:更新
     *
     * @param member
     * @return
     */
    public To edit(Member member) {
        if(StrKit.isBlank(member.getId())){
            return To.fail("会员编号不能为空!");
        }
        Member old=dao.findById(member.getId());
        if(old==null){
            return To.fail("记录不存在!");
        }
        old.setOpenid(StrKit.isBlank(member.getOpenid())?old.getOpenid():member.getOpenid());
        old.setName(StrKit.isBlank(member.getName())?old.getName():member.getName());
        old.setNickname(StrKit.isBlank(member.getNickname())?old.getNickname():member.getNickname());
        old.setTel(StrKit.isBlank(member.getTel())?old.getTel():member.getTel());
        old.setCardid(StrKit.isBlank(member.getCardid())?old.getCardid():member.getCardid());
        old.setGender(member.getGender()==null?old.getGender():member.getGender());
        old.setLevel(member.getLevel()==null?old.getLevel():member.getLevel());
        old.setAvatar(StrKit.isBlank(member.getAvatar())?old.getAvatar():member.getAvatar());
        old.setBirth(member.getBirth()==null?old.getBirth():member.getBirth());
        old.setPoints(member.getPoints()==null?old.getPoints():member.getPoints());
        old.setBalance(member.getBalance()==null?old.getBalance():member.getBalance());
        old.setStatus(member.getStatus()==null?old.getStatus():member.getStatus());
        old.setLogdate(member.getLogdate()==null?old.getLogdate():member.getLogdate());
        return To.to(old.update());
    }

}
