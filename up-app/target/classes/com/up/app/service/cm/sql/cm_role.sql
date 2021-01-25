###角色管理
#namespace("cm_role")
###查询列表
#sql("queryList")
    select * from cm_role where
    #if(is_del) is_del=#para(is_del)    #else   is_del=0    #end
    #if(name)   and name like concat('%', #para(name), '%') #end
    #if(state)  and state=#para(state)  #end
    #if(satrt_time) and create_time>=#para(start_create_time)  #end
    #if(end_create_time)   and create_time<=#para(end_create_time)    #end
    order by sort asc
#end
###选择角色数据
#sql("select")
    select id,name from cm_role where is_del=0 order by sort asc
#end
###获取角色菜单权限
#sql("queryAuthMenu")
   select cm_menu.id from cm_menu
   left join cm_role_menu on cm_menu.id=cm_role_menu.menu_id
   where cm_role_menu.role_id=#para(0)
   and cm_menu.id not in(select p_id from cm_menu inner join cm_role_menu on cm_menu.id=cm_role_menu.menu_id and cm_role_menu.role_id=#para(0))
   order by cm_menu.p_id,cm_menu.sort asc
#end
#sql("queryCountByIds")
    select count(*) from cm_role where is_del=0 and  id in(#for(x:ids)#para(x)#(for.last?'':',')#end)
#end
###获取角色对应的用户数量
#sql("queryRoleUserCount")
    select count(*) from cm_role_user
    left join cm_user on cm_user.id=cm_role_user.user_id
    where cm_user.is_del=0 and
    cm_role_user.role_id in (#for(x:ids)#para(x)#(for.last?'':',')#end)
#end
###获取角色数据权限
#sql("queryAuthOrg")
   select cm_org.id from cm_org
   left join cm_role_org on cm_org.id=cm_role_org.org_id
   where cm_role_org.role_id=#para(0)
   and cm_org.id not in(select p_id from cm_org inner join cm_role_org on cm_org.id=cm_role_org.org_id and cm_role_org.role_id=#para(0))
   order by cm_org.p_id,cm_org.sort asc
#end
###获取角色按钮权限
#sql("queryAuthButtonAction")
   SELECT cm_menu.action FROM cm_menu WHERE type=3 AND id IN(SELECT menu_id FROM cm_role_menu WHERE role_id IN(#for(x:roles)#para(x)#(for.last?'':',')#end))
#end
###更新角色数据权限
#sql("editAuth")
    update cm_role set scope=#para(scope) where id=#para(roleId);
    delete from cm_role_menu where role_id=#para(roleId);
    delete from cm_role_org where role_id=#para(roleId);
    #if(menus)
    insert into  cm_role_menu (role_id,menu_id) values #for(x:menus)(#para(roleId),#para(x))#(for.last?";":",")#end
    #end
    #if(scope==2&&orgs)
    insert into cm_role_org (role_id,org_id) values #for(x:orgs)(#para(roleId),#para(x))#(for.last?";":",")#end
    #end
#end
#end