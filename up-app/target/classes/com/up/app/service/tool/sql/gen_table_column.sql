###表格列生成
#namespace("gen_table_column")
###https://blog.csdn.net/qq_35583089/article/details/102478336
#sql("queryTableColumnByTable")
SELECT column_name as name,column_comment as comment,is_nullable ,column_key,data_type ,extra
FROM information_schema.columns WHERE table_schema = (SELECT DATABASE()) AND table_name = #para(0) ORDER BY ORDINAL_POSITION ASC
#end
#sql("updateColumns")
    #for(x:list)
    update gen_table_column set business=#para(x.business),java_type=#para(x.java_type),is_insert=#para(x.is_insert),is_edit=#para(x.is_edit),is_list=#para(x.is_list),is_query=#para(x.is_query),query_type=#para(x.query_type),html_type=#para(x.html_type),dict_type=#para(x.dict_type)
    where id=#para(x.id);
    #end
#end
#end