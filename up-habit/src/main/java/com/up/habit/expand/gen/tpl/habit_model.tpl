###代码生成model
package #(table.model_pkg);

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * #(table.business) 实体类
 * @author 王剑洪 on #(dateTime)
 */
@SuppressWarnings("serial")
public class #(table.model_name) extends DBModel<#(table.model_name)> implements IBean {
    #if (generateDaoInModel)
	public static final #(table.model_name) dao = new #(table.model_name)().dao();
	#end
	/**表名*/
    public final static String TABLE_NAME="#(table.name)";

    #set(pks='')
    #for(cm : columns)
    /**#(cm.business)*/
    public final static String #(toUpperCase(toUnderScoreCase(cm.name)))="#(cm.name)";
    #if(cm.is_pk==0)
        #set(pks=cm.name+',')
    #end
    #end
    /**主键*/
    public final static String TABLE_PKS="#(subWithoutLastOne(pks))";

    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            #for(cm : columns)
            +("<tr><td>#(cm.name)</td><td>#if(cm.des)#(cm.des)#end</td></tr>")
            #end
            +"</table>";
    #for(cm : columns)

	/**设置#(cm.business)*/
	#set(argName = javaKeyword.contains(cm.name) ? '_' + toCamelCase(cm.name) : toCamelCase(cm.name))
	public #(table.model_name) set#(firstCharToUpperCase(toCamelCase(cm.name)))(#(cm.javaType) #(argName)) {
		set("#(cm.name)", #(argName));
		return this;
	}

	/**获取#(cm.business)*/
	#set(getterOfModel = getterTypeMap.get(cm.javaType))
	#if (isBlank(getterOfModel))
		#set(getterOfModel = 'get')
	#end
	public #(cm.javaType) get#(firstCharToUpperCase(toCamelCase(cm.name)))() {
		return #(getterOfModel)("#(cm.name)");
	}
    #end


}

