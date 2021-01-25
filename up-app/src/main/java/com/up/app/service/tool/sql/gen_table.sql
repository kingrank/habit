###表格生成
#namespace("gen_table")
####获取当前连接的数据库除生成表格的所有表
#sql("queryAllTable")
    select table_name as name , table_comment as comment, create_time , update_time as modify_time
    from information_schema.tables
	where table_schema = (select database())
	AND  table_name NOT LIKE 'gen_%'
	AND table_name NOT IN (select name from gen_table)
    #if(name)
    AND lower(table_name) like concat('%', #para(name), '%')
    #end
    #if(comment!=null&&comment!='')
    AND lower(table_comment) like concat('%', #para(comment), '%')
    #end
    #if(start_create_time!=null&&start_create_time!='')
    AND date_format(create_time,'%y%m%d') >= date_format(#para(start_create_time),'%y%m%d')
    #end
    #if(end_create_time!=null&&end_create_time!='')
     AND date_format(create_time,'%y%m%d') <= date_format(#para(endTime),'%y%m%d')
    #end
#end
#####通过名称
#sql("queryTableListByNames")
        select table_name as name, table_comment as comment, create_time , update_time as modify_time
        from information_schema.tables
		where table_name NOT LIKE 'gen_%' and table_schema = (select database())
		and table_name in (#for(x:names)#para(x)#(for.last ? "": ",")#end)
#end
#####查询已导入生成的所有表
#sql("queryList")
    select * from gen_table where 1=1
    #if(name)
    AND name like concat('%', #para(name), '%')
    #end
    #if(business)
    AND business like concat('%', #para(business), '%')
    #end
    #if(start_create_time)
    AND create_time >= #para(start_create_time)
    #end
    #if(end_create_time)
     AND create_time <= #para(end_create_time)
    #end
#end
#end
