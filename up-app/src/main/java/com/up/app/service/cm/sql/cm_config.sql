###参数配置
#namespace("cm_config")
#sql("queryList")
    select * from cm_config where 1=1
    #if(group)  and cm_config.group=#para(group)  #end
    #if(name)   and name like concat('%', #para(name), '%') #end
    #if(editable)   and editable=#para(editable)    #end
    #if(start_create_time)   and create_time>=#para(start_create_time)    #end
    #if(end_create_time)   and create_time<=#para(end_create_time)    #end
    order by cm_config.group asc,sort asc
#end
#sql("queryByName")
    select * from cm_config where name=#para(name)
#end
#sql("deleteByIds")
    delete from cm_config where id in (#for(x:ids)#para(x)#(for.last?'':',')#end)
#end
#end