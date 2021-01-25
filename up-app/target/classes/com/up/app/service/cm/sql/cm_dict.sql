###字典
#namespace("cm_dict")
#sql("queryByPId")
select value,name from cm_dict where p_id=#para(pId) and state=0 order by sort asc
#end
##########查询字典列表
#sql("queryList")
select * from cm_dict where 1=1
    #if(p_id)
        and p_id=#para(p_id)
    #else
        and (p_id='' or p_id is null)
    #end
    #if(name)
        and name like concat('%', #para(name), '%')
    #end
    #if(id)
        and id like concat('%', #para(id), '%')
    #end
    #if(editable)
        and editable=#para(editable)
    #end
    #if(state)
        and state=#para(state)
    #end
    #if(start_create_time)
        and create_time>=#para(start_create_time)
    #end
    #if(end_create_time)
        and create_time<=#para(end_create_time)
    #end
   order by sort asc
#end
##########查询指定ID的数量中系统内置的数量
#sql("queryCountByIdAndEditable")
select count(*) from cm_dict where id in(#for(x:ids)#para(x)#(for.last ? "": ",")#end) and editable=0
#end
##########查询指定父ID的子集合数量
#sql("queryChildCount")
select count(*) from cm_dict where p_id in(#for(x:ids)#para(x)#(for.last ? "": ",")#end)
#end
##########删除指定ID记录
#sql("deleteByIds")
delete from cm_dict where id in(#for(x:ids)#para(x)#(for.last ? "": ",")#end)
#end

#sql("treeSelect")
    select id,p_id,name as label from cm_dict order by sort asc
#end
#end
