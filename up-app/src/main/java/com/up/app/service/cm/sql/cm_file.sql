###参数配置
#namespace("cm_file")
#sql("updateUsed")
    #if(newId)
    update cm_file set used=1 where id=#para(newId);
    #end
    #if(oldId&&oldId!=1)
    update cm_file set used=0 where id=#para(oldId);
    #end
#end
#end