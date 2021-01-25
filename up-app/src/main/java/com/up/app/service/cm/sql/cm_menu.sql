###菜单管理
#namespace("cm_menu")
#sql("queryByNameAndVisible")
    select * from cm_menu where 1=1
    #if(name!=null && name !='')
    and name like concat('%', #(name), '%')
    #end
    #if(visible)
    and visible = #(visible)
    #end
    order by sort asc
#end
##########查询子模块数量
#sql("queryChildCount")
select count(*) from cm_menu where p_id=#para(0)
#end

#sql("treeSelect")
    select id,p_id,name as label from cm_menu order by sort asc
#end

##########
#sql("queryDirectory")
select * from cm_menu where type =1 and name =#para(0) and path=#para(1) limit 1
#end
#sql("queryMenu")
select * from cm_menu where controller=#para(0) and (action='' or action is null) limit 1
#end
#sql("queryAction")
select * from cm_menu where controller=#para(0) and action=#para(1) limit 1
#end
###获取角色对应路由
#sql("queryRouter")
select cm_menu.* from cm_menu where (cm_menu.type=1 OR cm_menu.type=2)
#if(roleIds.length>0) and id in (select menu_id from cm_role_menu where role_id in (#for(x:roleIds)#para(x)#(for.last?'':',')#end))#end
order by sort asc
#end
###获取角色对应路由
#sql("queryAllRouter")
select cm_menu.* from cm_menu where (cm_menu.type=1 OR cm_menu.type=2)
order by sort asc
#end
#end

