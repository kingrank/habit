package com.up.habit.controller.validator;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.validate.Validator;
import com.up.habit.controller.HabitController;
import com.up.habit.controller.render.To;

import java.util.Date;

/**
 * TODO:Api验证
 * <p>
 * @author 王剑洪 on 2019/11/20 15:28
 */
public abstract class HabitApiValidator extends Validator {
    private final Log log = Log.getLog(HabitApiValidator.class);
    private String KEY_ERROR = "errorMessage";

    @Override
    protected abstract void validate(Controller c);

    @Override
    protected void handleError(Controller c) {
        ((HabitController) c).render(To.fail(c.get(KEY_ERROR)));

    }

    /**
     * 判断参数是否为空
     *
     * @param field
     * @param errorMessage
     */
    protected void validateRequired(String field, String errorMessage) {
        super.validateRequired(field, KEY_ERROR, errorMessage);
    }

    /**
     * 判断参数是否为空
     *
     * @param index
     * @param errorMessage
     */
    protected void validateRequired(int index, String errorMessage) {
        super.validateRequired(index, KEY_ERROR, errorMessage);
    }

    /**
     * 判断参数是否为指定类型
     *
     * @param type
     * @param field
     * @param errorMessage
     */
    protected void validate(Class<?> type, String field, String errorMessage) {
        if (type == String.class) {
            super.validateRequiredString(field, KEY_ERROR, errorMessage);
        } else if (type == Integer.class || type == int.class) {
            super.validateInteger(field, KEY_ERROR, errorMessage);
        } else if (type == Long.class || type == long.class) {
            super.validateLong(field, KEY_ERROR, errorMessage);
        } else if (type == Double.class || type == double.class) {
            super.validateDouble(field, KEY_ERROR, errorMessage);
        } else if (type == Date.class) {
            super.validateDate(field, KEY_ERROR, errorMessage);
        } else {
            log.warn("para validate type error");
        }
    }



}
