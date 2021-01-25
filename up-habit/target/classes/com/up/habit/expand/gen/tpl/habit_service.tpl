###代码生成service
package #(table.srv_pkg);

import #(table.model_pkg).#(table.model_name);
import com.up.habit.service.HabitService;
import com.jfinal.log.Log;
import com.up.habit.controller.render.To;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.kit.ValidatorKit;


/**
 * #(table.business) 业务类
 * @author #(table.business) on #(dateTime)
 */
public class #(table.srv_name) extends HabitService<#(table.model_name)> {
    private final Log log=Log.getLog(#(table.srv_name).class);

    public static #(table.srv_name) me = new #(table.srv_name)();
    ###设置model参数名
    #set(paraModelName = firstCharToLowerCase(table.model_name))

    /**
     * TODO:新增#(table.business)
     *
     * @param #(paraModelName)
     * @return
     */
    public To add(#(table.model_name) #(paraModelName)) {
#for(cm:columns)
#set(getMethod='.get'+firstCharToUpperCase(toCamelCase(cm.name))+'()')
    ###是否插入字段必须
    #if(cm.is_insert==0)
        #if(cm.java_type=='java.lang.String')
        if(StrKit.isBlank(#(paraModelName+getMethod))){
            return To.fail("#(cm.business)不能为空!");
        }
        #else
        if(#(paraModelName+getMethod)==null){
            return To.fail("#(cm.business)不能为空!");
        }
        #end
    #end
#end
        return To.to(#(paraModelName).save());
    }

    /**
     * TODO:更新
     *
     * @param #(paraModelName)
     * @return
     */
    public To edit(#(table.model_name) #(paraModelName)) {
#set(pks='')
#for(cm:columns)
    #set(getMethod='.get'+firstCharToUpperCase(toCamelCase(cm.name))+'()')
    #if(cm.is_pk==0)
    #set(pks=pks+paraModelName+getMethod+',')
        #if(cm.java_type=='java.lang.String')
        if(StrKit.isBlank(#(paraModelName+getMethod))){
            return To.fail("#(cm.business)不能为空!");
        }
        #else
        if(#(paraModelName+getMethod)==null){
            return To.fail("#(cm.business)不能为空!");
        }
        #end
    #end
#end
        #set(pks=subWithoutLastOne(pks))
        #(table.model_name) old=dao.findById(#(pks));
        if(old==null){
            return To.fail("记录不存在!");
        }
#for(cm:columns)
    #if(cm.is_pk!=0)
        #set(getMethod='.get'+firstCharToUpperCase(toCamelCase(cm.name))+'()')
        #set(setMethod='set'+firstCharToUpperCase(toCamelCase(cm.name)))
        #if(cm.java_type=='java.lang.String')
        old.#(setMethod)(StrKit.isBlank(#(paraModelName+getMethod))?old#(getMethod):#(paraModelName+getMethod));
        #else
        old.#(setMethod)(#(paraModelName+getMethod)==null?old#(getMethod):#(paraModelName+getMethod));
        #end
    #end
#end
        return To.to(old.update());
    }

}

