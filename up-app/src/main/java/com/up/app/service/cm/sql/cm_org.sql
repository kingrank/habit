###机构管理
#namespace("cm_org")
###查询列表
#sql("queryList")
    select * from cm_org where is_del=0
        #if(p_id)
            and p_id=#para(p_id)
        #end
        #if(name)
            and name like concat('%', #para(name), '%')
        #end
        #if(leader)
            and leader like concat('%', #para(leader), '%')
        #end
        #if(phone)
            and phone=#para(phone)
        #end
        #if(email)
            and email=#para(email)
        #end
        #if(state)
            and state=#para(state)
        #end
        #if(is_del)
            and is_del=#para(is_del)
        #end
    order by sort asc
#end
###下拉列表
#sql("treeSelect")
    select id,p_id,name as label from cm_org where state=0 and is_del=0 order by sort asc
#end
###移除部门
#sql("removeById")
    update cm_org set is_del=1 where id=#para(0)
#end
#end