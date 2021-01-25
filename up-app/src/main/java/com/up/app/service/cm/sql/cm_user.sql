###用户管理
#namespace("cm_user")
##########通过账号查询用户
#sql("queryByAccount")
    select * from cm_user where account=#para(0) AND is_del=0
#end
#sql("queryById")
    SELECT cm_user.*,CONCAT(#para(host),cm_file.path) as path
FROM cm_user
LEFT JOIN cm_file ON cm_file.id=cm_user.avatar
WHERE cm_user.id=#para(id) AND cm_user.is_del=0
#end
##########用户列表
#sql("queryList")
    select cm_user.*
    from cm_user
    where
    #if(is_del) is_del=#para(is_del)    #else   is_del=0    #end
    #if(nickname)  and cm_user.nickname like concat('%', #para(nickname), '%') #end
    #if(email)  and cm_user.email=#para(email)  #end
    #if(phone)  and cm_user.phone=#para(phone)  #end
    #if(sex)  and cm_user.sex=#para(sex)  #end
    #if(state)  and cm_user.state=#para(state)  #end
    #if(start_create_time)  and cm_user.create_time>=#para(start_create_time)  #end
    #if(end_create_time)  and cm_user.create_time<=#para(end_create_time)  #end
    #if(org_id) and (org_id=#para(org_id) or org_id in (select id from cm_org where FIND_IN_SET(#para(org_id),ancestors)))#end
    order by id desc
#end
##########批量设置用户状态为删除

##########查找用户的角色列表
#sql("queryRoles")
    select role_id from cm_role_user where user_id=#para(0)
#end
##########查找用户岗位列表
#sql("queryPosts")
    select post_id from cm_user_post where user_id=#para(0)
#end
##########更新用户的角色和机构
#sql("editUserRoleAndPost")
    delete from cm_role_user where user_id=#para(id);
    delete from cm_user_post where user_id=#para(id);
    #if(roles&&roles.length>0&&id!=1)
    insert into cm_role_user (role_id,user_id) values #for(x:roles)(#para(x),#para(id))#(for.last?";":",")#end
    #end
    #if(posts&&posts.length>0)
    insert into cm_user_post (post_id,user_id) values #for(x:posts)(#para(x),#para(id))#(for.last?";":",")#end
    #end
#end
#sql("queryUserRoleName")
    select cm_role.name from cm_role_user left join cm_role on cm_role_user.role_id=cm_role.id where cm_role_user.user_id=#para(0) and cm_role.is_del=0 order by cm_role.sort asc;
#end
#sql("queryUserPostName")
    select cm_post.name from cm_user_post left join cm_post on cm_user_post.post_id=cm_post.id where cm_user_post.user_id=#para(0) and cm_post.is_del=0 order by cm_post.sort asc;
#end
#end
