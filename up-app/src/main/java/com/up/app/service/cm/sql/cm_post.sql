###岗位信息
#namespace("cm_post")

###查询列表
#sql("queryList")
    select * from cm_post where 1=1
    #if(code)
    and code like concat('%', #para(code), '%')
    #end
    #if(name)
    and name like concat('%', #para(name), '%')
    #end
    #if(sort)
    and sort=#para(sort)
    #end
    #if(state)
    and state=#para(state)
    #end
    #if(remark)
    and remark like concat('%', #para(remark), '%')
    #end
order by id asc
#end
#sql("select")
    select id,name from cm_post where is_del=0 order by sort asc
#end

#sql("queryCountByIds")
    select count(*) from cm_post where is_del=0 and id in(#for(x:ids)#para(x)#(for.last?'':',')#end)
#end

#end