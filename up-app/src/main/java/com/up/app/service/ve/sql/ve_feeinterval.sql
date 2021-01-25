###计时费率
#namespace("ve_feeinterval")

###查询列表
#sql("queryList")
    select * from ve_feeinterval where 1=1
    #if(id)
    and id=#para(id)
    #end
    #if(feeid)
    and feeid=#para(feeid)
    #end
    #if(type)
    and type=#para(type)
    #end
    #if(start_startdate!=null&&start_startdate!=""&&end_startdate!=null&&end_startdate!="")
    and startdate between #para(start_startdate) and #para(end_startdate)
    #end
    #if(start_enddate!=null&&start_enddate!=""&&end_enddate!=null&&end_enddate!="")
    and enddate between #para(start_enddate) and #para(end_enddate)
    #end
    #if(starttiime)
    and starttiime=#para(starttiime)
    #end
    #if(endtime)
    and endtime=#para(endtime)
    #end
    #if(freetime)
    and freetime=#para(freetime)
    #end
    #if(feecut)
    and feecut=#para(feecut)
    #end
    #if(unit)
    and unit=#para(unit)
    #end
    #if(unitprice)
    and unitprice=#para(unitprice)
    #end
    #if(vipunitprice)
    and vipunitprice=#para(vipunitprice)
    #end
    #if(pointstype)
    and pointstype=#para(pointstype)
    #end
    #if(pointsval)
    and pointsval=#para(pointsval)
    #end
    #if(pointstop)
    and pointstop=#para(pointstop)
    #end
    #if(pointdeduction)
    and pointdeduction=#para(pointdeduction)
    #end
    #if(pointdeductionval)
    and pointdeductionval=#para(pointdeductionval)
    #end
    #if(pointdeductiontop)
    and pointdeductiontop=#para(pointdeductiontop)
    #end
    #if(status)
    and status=#para(status)
    #end
order by id asc
#end

#end

