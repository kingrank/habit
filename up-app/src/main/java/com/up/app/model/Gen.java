package com.up.app.model;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/2/16 17:49
 */
public class Gen {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/habit";
        String user = "root";
        String password = "Zxc123456@";
        String modelPackageName = "com.up.app.model";
        new com.up.habit.expand.gen.ModelGenerator(url, user, password, modelPackageName)
                .setRemovedTableNamePrefixes("cm_", "gen_", "info_", "log_", "trade_","p_","plan_")
                .setExcludedTables("gen_table","gen_table_column","gen_directory")
                .build()
                .genModel("cm_file");
    }
}
