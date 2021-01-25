###订单明细
#namespace("dt_orderdetail")

###查询列表
#sql("queryList")
    select * from dt_orderdetail where 1=1
    #if(id)
    and id like concat('%', #para(id), '%')
    #end
    #if(orderid)
    and orderid like concat('%', #para(orderid), '%')
    #end
    #if(productid)
    and productid=#para(productid)
    #end
    #if(productname)
    and productname like concat('%', #para(productname), '%')
    #end
    #if(price)
    and price=#para(price)
    #end
    #if(num)
    and num=#para(num)
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
    #if(discountdesc)
    and discountdesc=#para(discountdesc)
    #end
    #if(pointpayment)
    and pointpayment=#para(pointpayment)
    #end
    #if(pointadd)
    and pointadd=#para(pointadd)
    #end
    #if(status)
    and status=#para(status)
    #end
    #if(paystate)
    and paystate=#para(paystate)
    #end
    #if(start_overtime!=null&&start_overtime!=""&&end_overtime!=null&&end_overtime!="")
    and overtime between #para(start_overtime) and #para(end_overtime)
    #end
    #if(start_logdate!=null&&start_logdate!=""&&end_logdate!=null&&end_logdate!="")
    and logdate between #para(start_logdate) and #para(end_logdate)
    #end
    #if(desc)
    and desc like concat('%', #para(desc), '%')
    #end
order by id asc
#end

#end

