package com.up.habit.expand.template;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO:
 * <p>
 *
 * @author 王剑洪 on 2019/12/25 14:10
 */
public class ColumnDirective extends Directive {


    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        Object value = exprList.eval(scope);
        if (value != null) {
            String column = String.valueOf(value);
            if ("name".equals(column)) {
                write(writer, column + " like");
            } else if ("createTime".equals(column)) {
                write(writer, column + " like");
            } else {
                write(writer, column + "=");
            }
        }
    }
}
