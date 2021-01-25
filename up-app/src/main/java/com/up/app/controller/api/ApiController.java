package com.up.app.controller.api;

import com.up.habit.config.anno.Ctr;
import com.up.habit.controller.HabitController;
import com.up.habit.controller.render.To;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/13 15:33
 */
@Ctr
public class ApiController extends HabitController {
    public void index() {
        render(To.ok(get("i")));
    }
}
