###任务日志
#namespace("log_job_log")
#sql("queryList")
    select * from log_job_log where 1=1
    #if(group)  and log_job_log.module=#para(group)  #end
    #if(name)   and log_job_log.name like concat('%', #para(name), '%') #end
    #if(start_create_time)   and log_job_log.start_time >=#para(start_create_time) #end
    #if(end_create_time)   and log_job_log.start_time <= #para(end_create_time) #end
    order by log_job_log.start_time desc
#end

#end