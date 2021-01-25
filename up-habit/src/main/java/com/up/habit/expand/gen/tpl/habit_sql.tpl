#('###'+table.business)
#('#namespace("'+table.name+'")')

#('###查询列表')
#('#sql("queryList")')
    #('select * from '+table.name+' where 1=1')
#for(cm:columns)
        #if(cm.is_query==0)
            #if(cm.query_type=='='||cm.query_type=='!='||cm.query_type=='>'||cm.query_type=='>='||cm.query_type=='<'||cm.query_type=='<=')
    #('#if('+cm.name+')')
    #('and '+cm.name+cm.query_type+'#para('+cm.name+')')
    #('#end')
            #else if(cm.query_type=='between')
    #('#if(start_'+cm.name+'!=null&&start_'+cm.name+'!=""&&end_'+cm.name+'!=null&&end_'+cm.name+'!="")')
    #('and '+cm.name+' between #para(start_'+cm.name+') and #para(end_'+cm.name+')')
    #('#end')
            #else if(cm.query_type=='like')
    #('#if('+cm.name+')')
    #("and "+cm.name+" "+cm.query_type+" concat('%', #para("+cm.name+"), '%')")
    #('#end')
            #end
        #end
#end
#('order by id asc')
#('#end')

#('#end')


