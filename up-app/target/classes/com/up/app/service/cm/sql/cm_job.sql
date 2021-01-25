###任务调度
#namespace("cm_job")
#sql("queryList")
    select * from cm_job where 1=1
    #if(name)
    and cm_job.name like concat('%', #para(name), '%')
    #end
    #if(group)
    and cm_job.group like concat('%', #para(group), '%')
    #end
    #if(start_create_time)
    and create_time>=#para(start_create_time)
    #end
    #if(end_create_time)
    and create_time<=#para(end_create_time)
    #end
    order by id desc
#end
#sql("queryByNameAndGroup")
    select * from cm_job where cm_job.name=#para(0) and cm_job.group=#para(1)
#end
#sql("queryRunJob")
 select * from cm_job where state=1
#end
#sql("queryRunCountByIds")
    select count(*) from cm_job where id in(#for(x:ids)#para(x)#(for.last?'':',')#end) and state=1
#end
#sql("deleteByIds")
    delete from cm_job where id in (#for(x:ids)#para(x)#(for.last?'':',')#end)
#end
#sql("refreshLastTime")
    update cm_job set last_time=NOW() where cm_job.name=#para(0) and cm_job.group=#para(1)
#end
#end