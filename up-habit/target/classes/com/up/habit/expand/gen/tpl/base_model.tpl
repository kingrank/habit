#####数据库model模版
package #(modelPackageName);

import com.up.habit.expand.db.model.DBModel;
import com.jfinal.plugin.activerecord.IBean;

/**
 * #(tableMeta.remarks)
 * @author 王剑洪
 */
@SuppressWarnings("serial")
public class #(tableMeta.modelName) extends DBModel<#(tableMeta.modelName)> implements IBean {
    #if (generateDaoInModel)
	public static final #(tableMeta.modelName) dao = new #(tableMeta.modelName)().dao();
	#end
	/**表名*/
    public final static String TABLE_NAME="#(tableMeta.name)";
    /**主键*/
    public final static String TABLE_PKS="#(tableMeta.primaryKey)";
    #for(cm : tableMeta.columnMetas)
    /**#(cm.remarks)*/
    public final static String #(toUpperCase(toUnderScoreCase(cm.attrName)))="#(cm.name)";
    #end
    /**swagger文档生成说明*/
    public final static String TABLE_INFO="<table style='font-size:14px; font-style:normal'>"
            #for(cm : tableMeta.columnMetas)
            +("<tr><td>#(cm.name)</td><td>#if(cm.remarks)#(cm.remarks)#end</td></tr>")
            #end
            +"</table>";
#for(cm : tableMeta.columnMetas)

	/**
	 * #(cm.remarks)
	 */
	#set(argName = javaKeyword.contains(cm.attrName) ? '_' + cm.attrName : cm.attrName)
	public #(tableMeta.modelName) set#(firstCharToUpperCase(cm.attrName))(#(cm.javaType) #(argName)) {
		set("#(cm.name)", #(argName));
		return this;
	}

	/**
	 * #(cm.remarks)
	 */
	#set(getterOfModel = getterTypeMap.get(cm.javaType))
	#if (isBlank(getterOfModel))
		#set(getterOfModel = 'get')
	#end
	public #(cm.javaType) get#(firstCharToUpperCase(cm.attrName))() {
		return #(getterOfModel)("#(cm.name)");
	}
#end


}

