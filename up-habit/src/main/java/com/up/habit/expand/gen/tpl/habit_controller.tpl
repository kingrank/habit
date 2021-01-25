###代码生成controller
package #(table.ctr_pkg);

import #(table.model_pkg+'.'+table.model_name);
import #(table.srv_pkg+'.'+table.srv_name);
import com.up.habit.config.anno.*;
import com.up.app.controller.admin.AdminController;

/**
* TODO:#(table.business)控制器
*
* @author #(table.author) on #(dateTime)
*/
@Directory(value = "#(table.module_name)", icon = "#(table.module_icon)")
@Ctr(name = "#(table.business)", icon = "#(table.icon)")
public class #(table.ctr_name) extends AdminController {

    @ApiMethod(value = "新增#(table.business)", auth = AuthType.BUTTON)
    public void add() {
        render(#(table.srv_name+'.me.add(getModel('+table.model_name+'.class)')));
    }

    @ApiMethod(value = "删除#(table.business)", auth = AuthType.BUTTON)
    public void delete() {
        render(#(table.srv_name+'.me.delete(getArray("ids")')));
    }

    @ApiMethod(value = "编辑#(table.business)", auth = AuthType.BUTTON)
    public void edit() {
        render(#(table.srv_name+'.me.edit(getModel('+table.model_name+'.class)')));
    }

    @ApiMethod("#(table.business)列表-分页")
    public void page(){
        render(#(table.srv_name+'.me.page(getModelAddTime('+table.model_name+'.class),num(),size()')));
    }

    @ApiMethod("#(table.business)详情")
    public void info(){
        render(#(table.srv_name+'.me.info(get("id"))'));
    }
}
