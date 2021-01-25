###会员信息
#namespace("dt_member")

###查询列表
#sql("queryList")
    select * from dt_member where 1=1
    #if(id)
    and id=#para(id)
    #end
    #if(orgid) and (orgid=#para(orgid) or orgid in (select id from cm_org where FIND_IN_SET(#para(orgid),ancestors)))#end
    #if(openid)
    and openid=#para(openid)
    #end
    #if(name)
    and name like concat('%', #para(name), '%')
    #end
    #if(nickname)
    and nickname like concat('%', #para(nickname), '%')
    #end
    #if(tel)
    and tel like concat('%', #para(tel), '%')
    #end
    #if(cardid)
    and cardid like concat('%', #para(cardid), '%')
    #end
    #if(gender)
    and gender=#para(gender)
    #end
    #if(level)
    and level=#para(level)
    #end
    #if(avatar)
    and avatar like concat('%', #para(avatar), '%')
    #end
    #if(start_birth!=null&&start_birth!=""&&end_birth!=null&&end_birth!="")
    and birth between #para(start_birth) and #para(end_birth)
    #end
    #if(points)
    and points=#para(points)
    #end
    #if(balance)
    and balance=#para(balance)
    #end
    #if(status)
    and status=#para(status)
    #end
    #if(start_logdate!=null&&start_logdate!=""&&end_logdate!=null&&end_logdate!="")
    and logdate between #para(start_logdate) and #para(end_logdate)
    #end
order by id asc
#end

#end

