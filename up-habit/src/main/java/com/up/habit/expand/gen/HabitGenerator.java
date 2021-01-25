package com.up.habit.expand.gen;

import com.jfinal.kit.JavaKeyword;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.template.Engine;
import com.up.habit.Habit;
import com.up.habit.HabitConstant;
import com.up.habit.expand.db.DbConstants;
import com.up.habit.expand.db.model.DBModel;
import com.up.habit.expand.gen.model.Table;
import com.up.habit.expand.gen.model.TableColumn;
import com.up.habit.kit.DateKit;
import com.up.habit.kit.StrKit;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/9 0:50
 */
public class HabitGenerator {
    /**
     * 默认包名
     */
    public final static String BASE_PACKAGE_NAME = "com.up.app";
    /**
     * 默认作者
     */
    public final static String author = "盛楠";

    private Engine engine;

    public static HabitGenerator me = new HabitGenerator();

    /**
     * model模版
     */
    protected String modelTemplate = "/com/up/habit/expand/gen/tpl/habit_model.tpl";
    /**
     * service模版
     */
    protected String serviceTemplate = "/com/up/habit/expand/gen/tpl/habit_service.tpl";
    /**
     * sql模版
     */
    protected String sqlTemplate = "/com/up/habit/expand/gen/tpl/habit_sql.tpl";
    /**
     * controller模版
     */
    protected String controllerTemplate = "/com/up/habit/expand/gen/tpl/habit_controller.tpl";
    /**
     * vue模版
     */
    protected String vueTemplate = "/com/up/habit/expand/gen/tpl/habit_vue_table.tpl";
    /**
     * js模版
     */
    protected String jsTemplate = "/com/up/habit/expand/gen/tpl/habit_vue_js.tpl";

    private static final String KEY_MODEL = "model";
    private static final String KEY_SRV = "service";
    private static final String KEY_SQL = "sql";
    private static final String KEY_CTR = "controller";
    private static final String KEY_JS = "js";
    private static final String KEY_VUE = "vue";

    /**
     * 数据类型
     */
    private static final String ATTR_DATA_TYPE = "DATA_TYPE";
    /**
     * 是否主键
     */
    private static final String ATTR_KEY = "COLUMN_KEY";
    /**
     * 是主键值
     */
    private static final String ATTR_KEY_VALUE_YES = "PRI";
    /**
     * 字段描述
     */
    private static final String ATTR_EXTAR = "EXTAR";
    /**
     * 字段描述,自增
     */
    private static final String ATTR_EXTAR_VALUE_AUTO = "auto_increment";
    /**
     * 是否允许为空
     */
    private static final String ATTR_IS_NULLABLE = "IS_NULLABLE";
    /**
     * 是否允许为空,是
     */
    private static final String ATTR_NULLABLE_VALUE_YES = "YES";

    /**
     * 时间后缀
     */
    private static final String SUFFIX_TIME = "time";


    private Map<String, String> getterTypeMap = new HashMap<String, String>() {
        {
            this.put("java.lang.String", "getStr");
            this.put("java.lang.Integer", "getInt");
            this.put("java.lang.Long", "getLong");
            this.put("java.lang.Double", "getDouble");
            this.put("java.lang.Float", "getFloat");
            this.put("java.lang.Short", "getShort");
            this.put("java.lang.Byte", "getByte");
        }
    };

    /**
     * MYSQL数据类型对应java类型
     */
    public static final Map<String, String> MYSQL_TYPE = new HashMap<String, String>() {{
        //https://www.cnblogs.com/hellocz/p/6393500.html
        this.put("char", "java.lang.String");
        this.put("varchar", "java.lang.String");
        this.put("tinytext", "java.lang.String");
        this.put("longtext", "java.lang.String");
        this.put("text", "java.lang.String");
        this.put("mediumtext", "java.lang.String");


        this.put("binary", "java.lang.byte[]");
        this.put("bit", "java.lang.byte[]");
        this.put("blob", "java.lang.byte[]");
        this.put("longblob", "java.lang.byte[]");
        this.put("mediumblob", "java.lang.byte[]");
        this.put("tinyblob", "java.lang.byte[]");
        this.put("varbinary", "java.lang.byte[]");

        this.put("datetime", "java.util.Date");
        this.put("date", "java.util.Date");
        this.put("time", "java.util.Date");
        this.put("timestamp", "java.util.Date");
        this.put("year", "java.util.Date");

        this.put("int", "java.lang.Integer");
        this.put("bool", "java.lang.Integer");
        this.put("boolean", "java.lang.Integer");
        this.put("mediumint", "java.lang.Integer");
        this.put("tinyint", "java.lang.Integer");

        this.put("decimal", "java.math.BigDecimal");
        this.put("numeric", "java.math.BigDecimal");

        this.put("double", "java.lang.Double");
        this.put("real", "java.lang.Double");

        this.put("bigint", "java.lang.Long");
        this.put("float", "java.lang.Float");
        this.put("smallint", "java.lang.Short");
    }};

    /**
     * 默认查询类型对应
     */
    public static final Map<String, String> QUERY_DEF_TYPE = new HashMap<String, String>() {{
        this.put("java.lang.String", "like");
        this.put("java.lang.byte[]", "=");
        this.put("java.util.Date", "between");
        this.put("java.lang.Integer", "=");
        this.put("java.math.BigDecimal", "=");
        this.put("java.lang.Double", "=");
        this.put("java.lang.Long", "=");
        this.put("java.lang.Float", "=");
        this.put("java.lang.Short", "=");
    }};

    /**
     * 默认字段类型对应html类型
     */
    public static final Map<String, String> HTML_DEF_TYPE = new HashMap<String, String>() {{
        this.put("java.lang.String", "input");
        this.put("java.lang.byte[]", "input");
        this.put("java.util.Date", "datetime");
        this.put("java.lang.Integer", "input-number");
        this.put("java.math.BigDecimal", "input");
        this.put("java.lang.Double", "input");
        this.put("java.lang.Long", "input");
        this.put("java.lang.Float", "input");
        this.put("java.lang.Short", "input");
    }};

    public HabitGenerator() {
        engine = Engine.use().setToClassPathSourceFactory().addSharedMethod(new StrKit())
                .addSharedObject("javaKeyword", JavaKeyword.me)
                .addSharedObject("getterTypeMap", getterTypeMap);
    }

    public Ret build(Table table, List<TableColumn> columns) {
        Kv kv = Kv.by("table", table).set("columns", columns).set("dateTime", DateKit.toStr(new Date(), DateKit.timeStampPattern));
        String modelContent = this.engine.getTemplate(modelTemplate).renderToString(kv);
        String serviceContent = this.engine.getTemplate(serviceTemplate).renderToString(kv);
        String sqlContent = this.engine.getTemplate(sqlTemplate).renderToString(kv);
        String controllerContent = this.engine.getTemplate(controllerTemplate).renderToString(kv);
        String jsContent = this.engine.getTemplate(jsTemplate).renderToString(kv);
        String vueContent = this.engine.getTemplate(vueTemplate).renderToString(kv);
        return Ret.create().set(KEY_MODEL, modelContent).set(KEY_SQL, sqlContent).set(KEY_SRV, serviceContent).set(KEY_CTR, controllerContent).set(KEY_JS, jsContent).set(KEY_VUE, vueContent);
    }

    /**
     * 设置生成的表信息
     *
     * @param table
     */
    public static void setTable(Table table) {
        String module = table.getModule();
        String modulePackage = StrKit.isBlank(module) ? "" : ("." + module);
        String modulePath = StrKit.isBlank(module) ? "" : (module + "/");
        String basePackage = BASE_PACKAGE_NAME;
        String modelName = table.getModelName();
        String key = StrKit.firstCharToLowerCase(modelName);

        table.setModelPkg(basePackage + ".model" + modulePackage);
        table.setSrvPkg(basePackage + ".service" + modulePackage);
        table.setCtrPkg(basePackage + ".controller.admin" + modulePackage);
        table.setSrvName(modelName + "Service");
        table.setCtrName(modelName + "Controller");

        table.setCtrKey(key);
        table.setJsPath("src/api/" + modulePath + key + ".js");
        table.setVuePath("src/views/" + modulePath + key + "/index.vue");

        table.setAuthor(author);

        table.setTpl("table");

    }


    /**
     * 设置生成的表的列属性
     *
     * @param column
     */
    public static void setTableColumn(TableColumn column) {
        column.setBusiness(column.getComment());
        try {
            String type=column.getStr(ATTR_DATA_TYPE);
            if (StrKit.isBlank(type)){
                type=column.getStr(ATTR_DATA_TYPE.toLowerCase());
            }
            column.setType(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        column.setJavaType(javaType(column.getType()));
        String pk = StrKit.isBlank(column.getStr(ATTR_KEY))?column.getStr(ATTR_KEY.toLowerCase()):column.getStr(ATTR_KEY);
        String extar = StrKit.isBlank(column.getStr(ATTR_EXTAR))?column.getStr(ATTR_EXTAR.toLowerCase()):column.getStr(ATTR_EXTAR);
        String nullable = StrKit.isBlank(column.getStr(ATTR_IS_NULLABLE))?column.getStr(ATTR_IS_NULLABLE.toLowerCase()):column.getStr(ATTR_IS_NULLABLE);
        column.setIsPk(ATTR_KEY_VALUE_YES.equals(pk) ? 0 : 1);
        column.setIsIncrement(ATTR_EXTAR_VALUE_AUTO.equals(extar) ? 0 : 1);
        column.setIsRequired(ATTR_NULLABLE_VALUE_YES.equals(nullable) ? 0 : 1);
        column.setIsInsert(column.getIsIncrement().intValue() == 0 ? 1 : 0);
        column.setIsList(0);
        column.setIsEdit(0);
        column.setIsQuery(0);
        if (DbConstants.DEF_COLUMNS.contains(column.getName())) {
            column.setIsInsert(1);
            column.setIsEdit(1);
            column.setIsList(1);
            column.setIsQuery(1);
            if (column.getName().equals(DbConstants.column_created)) {
                column.setIsList(0);
            }
        }
        column.setQueryType(HabitGenerator.QUERY_DEF_TYPE.get(column.getJavaType()));
        column.setHtmlType(HabitGenerator.HTML_DEF_TYPE.get(column.getJavaType()));
        if (column.getName().endsWith(SUFFIX_TIME)) {
            column.setIsEdit(1);
            column.setIsInsert(1);
        }
    }

    /**
     * 生成ZIP byte数组
     *
     * @param tables
     * @param columnList
     * @return
     */
    public byte[] genZip(Table[] tables, List<TableColumn>[] columnList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (int i = 0; i < tables.length; i++) {
            Table table = tables[i];
            List<TableColumn> columns = columnList[i];
            Ret ret = build(table, columns);
            String modelFile = "src/" + table.getModelPkg().replace(".", "/") + "/" + table.getModelName() + ".java";
            String serviceFile = "src/" + table.getSrvPkg().replace(".", "/") + "/" + table.getSrvName() + ".java";
            String sqlFile = "src/" + table.getSrvPkg().replace(".", "/") + "/sql/" + table.getName() + ".sql";
            String controllerFile = "src/" + table.getCtrPkg().replace(".", "/") + "/" + table.getCtrName() + ".java";
            gen(zip, modelFile, ret.getStr(KEY_MODEL));
            gen(zip, serviceFile, ret.getStr(KEY_SRV));
            gen(zip, sqlFile, ret.getStr(KEY_SQL));
            gen(zip, controllerFile, ret.getStr(KEY_CTR));
            gen(zip, table.getJsPath(), ret.getStr(KEY_JS));
            gen(zip, table.getVuePath(), ret.getStr(KEY_VUE));
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();

    }

    public void gen(ZipOutputStream zip, String fileName, String content) {
        try {
            zip.putNextEntry(new ZipEntry(fileName));
            IOUtils.write(content, zip, HabitConstant.CHART_UTF8);
            zip.flush();
            zip.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String javaType(String type) {
        if (MYSQL_TYPE.containsKey(type.toLowerCase())) {
            return MYSQL_TYPE.get(type.toLowerCase());
        } else {
            return "java.lang.String";
        }
    }


}
