#namespace("gen_directory")

#sql("queryList")
select * from gen_directory where 1=1
    #if(name)
    and name  like concat('%', #para(name), '%')
    #end
    #if(prefix)
    and prefix  like concat('%', #para(prefix), '%')
    #end
    #if(start_create_time)
    and create_time >= #para(start_create_time)
    #end
    #if(end_create_time)
    and create_time <= #para(end_create_time)
    #end
    order by id asc
#end

##########获取表前缀
#sql("queryPrefix")
select id,name,path,icon,prefix from gen_directory where is_del=0 group by prefix
#end
#end