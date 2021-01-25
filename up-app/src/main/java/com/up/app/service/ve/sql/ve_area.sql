###场地管理
#namespace("ve_area")

###查询列表
#sql("queryList")
    select * from ve_area where 1=1
    #if(id)
    and id=#para(id)
    #end
    #if(orgid) and (orgid=#para(orgid) or orgid in (select id from cm_org where FIND_IN_SET(#para(orgid),ancestors)))#end
    #if(name)
    and name like concat('%', #para(name), '%')
    #end
    #if(type)
    and type=#para(type)
    #end
    #if(status)
    and status=#para(status)
    #end
    #if(isshow)
    and isshow=#para(isshow)
    #end
    #if(feeid)
    and feeid=#para(feeid)
    #end
    #if(pic)
    and pic like concat('%', #para(pic), '%')
    #end
    #if(des)
    and des like concat('%', #para(des), '%')
    #end
    #if(businesstime)
    and businesstime like concat('%', #para(businesstime), '%')
    #end
order by id asc
#end

#end

