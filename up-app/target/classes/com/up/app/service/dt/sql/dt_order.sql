###订单总览
#namespace("dt_order")

###查询列表
#sql("queryList")
    select * from dt_order where 1=1
    #if(id)
    and id=#para(id)
    #end
    #if(orgid) and (orgid=#para(orgid) or orgid in (select id from cm_org where FIND_IN_SET(#para(orgid),ancestors)))#end
    #if(memberid)
    and memberid like concat('%', #para(memberid), '%')
    #end
    #if(membername)
    and membername like concat('%', #para(membername), '%')
    #end
    #if(membertel)
    and membertel like concat('%', #para(membertel), '%')
    #end
    #if(producttype)
    and producttype=#para(producttype)
    #end
    #if(payment)
    and payment=#para(payment)
    #end
    #if(paidin)
    and paidin=#para(paidin)
    #end
    #if(discount)
    and discount=#para(discount)
    #end
    #if(status)
    and status=#para(status)
    #end
    #if(paystate)
    and paystate=#para(paystate)
    #end
    #if(start_logdate!=null&&start_logdate!=""&&end_logdate!=null&&end_logdate!="")
    and logdate between #para(start_logdate) and #para(end_logdate)
    #end
    #if(start_overdate!=null&&start_overdate!=""&&end_overdate!=null&&end_overdate!="")
    and overdate between #para(start_overdate) and #para(end_overdate)
    #end
order by id asc
#end

#end

