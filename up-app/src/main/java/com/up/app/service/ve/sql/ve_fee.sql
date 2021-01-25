###收费规则
#namespace("ve_fee")

###查询列表
#sql("queryList")
    select * from ve_fee where 1=1
    #if(name)
    and name like concat('%', #para(name), '%')
    #end
    #if(orgid) and (orgid=#para(orgid) or orgid in (select id from cm_org where FIND_IN_SET(#para(orgid),ancestors)))#end
    #if(status)
    and status=#para(status)
    #end
order by id asc
#end

#end

