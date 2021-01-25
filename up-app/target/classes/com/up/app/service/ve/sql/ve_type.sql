###场地类型
#namespace("ve_type")

###查询列表
#sql("queryList")
    select * from ve_type where 1=1
    #if(id)
    and id=#para(id)
    #end
    #if(vename)
    and vename like concat('%', #para(vename), '%')
    #end
    #if(orgid) and (orgid=#para(orgid) or orgid in (select id from cm_org where FIND_IN_SET(#para(orgid),ancestors)))#end
    #if(pic)
    and pic like concat('%', #para(pic), '%')
    #end
    #if(status)
    and status=#para(status)
    #end
    #if(desc)
    and desc like concat('%', #para(desc), '%')
    #end
    #if(start_logdate!=null&&start_logdate!=""&&end_logdate!=null&&end_logdate!="")
    and logdate between #para(start_logdate) and #para(end_logdate)
    #end
order by id asc
#end

#end

