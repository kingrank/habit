###系统配置
#namespace("log_oper_log")
#sql("queryList")
    select * from log_oper_log where 1=1
    #if(module)  and log_oper_log.module=#para(module)  #end
    #if(name)   and log_oper_log.name like concat('%', #para(name), '%') #end
    #if(action)   and log_oper_log.action like concat('%', #para(action), '%') #end
    #if(controller)   and log_oper_log.controller like concat('%', #para(controller), '%') #end
    #if(start_create_time)   and log_oper_log.request_time >=#para(start_create_time) #end
    #if(end_create_time)   and log_oper_log.request_time <= #para(end_create_time) #end
    order by log_oper_log.request_time desc
#end

#end