package com.up.habit.expand.gen;


import com.jfinal.kit.JavaKeyword;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.DataDictionaryGenerator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.up.habit.kit.FileKit;
import com.up.habit.kit.StrKit;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO:生成工具类
 *
 * @author 王剑洪 on 2019/12/14 1:03
 */
public class ModelGenerator {

    protected Engine engine;

    protected DataSource dataSource;
    protected MetaBuilder metaBuilder;
    protected String dbName;
    protected List<TableMeta> tableMetas;

    /**
     * 文档生成路径
     */
    protected String dictionaryOutputPath = PathKit.getWebRootPath() + "/src/main/doc/";
    protected String modelPackageName;
    protected String mappingClassName = "_MappingKit";

    protected String modelTemplate = "/com/up/habit/expand/gen/tpl/base_model.tpl";
    protected String dicHtmlTemplate = "/com/up/habit/expand/gen/tpl/habit_sql_dic_html.tpl";

    protected DataDictionaryGenerator dataDictionaryGenerator;

    private Set<String> modelPackageSet=new HashSet<>();

    public ModelGenerator(String url, String user, String password, String modelPackageName) {
        String[] tmp = url.split("/");
        dbName = tmp[tmp.length - 1].split("\\?")[0];
        url = url + (url.contains("?") ? "&useSSL=false" : "?useSSL=false")+"&characterEncoding=UTF-8";
        DruidPlugin druid = new DruidPlugin(url, user, password);
        druid.setConnectionProperties("useInformationSchema=true;remarks=true");
        druid.start();
        ActiveRecordPlugin activeRecord = new ActiveRecordPlugin(druid);
        activeRecord.start();
        this.modelPackageName=modelPackageName;
        dataSource = druid.getDataSource();
        metaBuilder = new MetaBuilder(dataSource);
        metaBuilder.setDialect(new MysqlDialect());
        metaBuilder.setGenerateRemarks(true);
        dataDictionaryGenerator = new DataDictionaryGenerator(dataSource, dictionaryOutputPath) {
            @Override
            public void generateTable(TableMeta tableMeta, StringBuilder ret) {
                super.generateTable(tableMeta, ret);
            }
        };
        engine = Engine.use().setToClassPathSourceFactory().addSharedMethod(new StrKit())
                .addSharedObject("javaKeyword", JavaKeyword.me)
                .addSharedObject("getterTypeMap", new HashMap<String, String>() {
                    {
                        this.put("java.lang.String", "getStr");
                        this.put("java.lang.Integer", "getInt");
                        this.put("java.lang.Long", "getLong");
                        this.put("java.lang.Double", "getDouble");
                        this.put("java.lang.Float", "getFloat");
                        this.put("java.lang.Short", "getShort");
                        this.put("java.lang.Byte", "getByte");
                    }
                });
    }

    /**
     * TODO:移除表前缀
     *
     * @param removedTableNamePrefixes
     * @return
     */
    public ModelGenerator setRemovedTableNamePrefixes(String... removedTableNamePrefixes) {
        metaBuilder.setRemovedTableNamePrefixes(removedTableNamePrefixes);
        return this;
    }

    /**
     * TODO:无需生成的表
     *
     * @param excludedTables
     * @return
     */
    public ModelGenerator setExcludedTables(String... excludedTables) {
        metaBuilder.addExcludedTable(excludedTables);
        return this;
    }

    public ModelGenerator build(){
        tableMetas=metaBuilder.build();
        return this;
    }
    /**
     * TODO:MODEL生成
     *
     * @return
     */
    public ModelGenerator genModel() {
        for (TableMeta meta : tableMetas) {
            String lastModelPackageName=modelPackageName+getNewModelName(meta);
            modelPackageSet.add(lastModelPackageName);
            Kv kv = Kv.by("modelPackageName", lastModelPackageName).set("tableMeta", meta);
            String content = this.engine.getTemplate(modelTemplate).renderToString(kv);
            String modelOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + lastModelPackageName.replace(".", "/");
            FileKit.renderByString(content, modelOutputDir + "/" + meta.modelName + ".java", true);
        }
        return this;
    }

    public ModelGenerator genDBInfo() {
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append("CREATE DATABASE `" + dbName + "`;\n");
        sqlSb.append("USE  `" + dbName + "`;\n");
        for (int i = 0; i < tableMetas.size(); i++) {
            sqlSb.append(String.format("\n\n\n/**\n * table name :<%s><%s>\n *\n */\nDROP TABLE IF EXISTS `%s`;\n\n%s;\n", tableMetas.get(i).name, tableMetas.get(i).remarks, tableMetas.get(i).name, Db.findFirst(String.format("SHOW CREATE TABLE %s", tableMetas.get(i).name)).getStr("Create Table")));
        }
        String sql=sqlSb.toString().replaceAll("COLLATE=utf8mb4_0900_ai_ci ","");
        //数据库语句
        FileKit.renderByString(sql, dictionaryOutputPath + dbName + ".sql", true);
        //doc文档
        DataDictionaryGenerator dataDictionaryGenerator = new DataDictionaryGenerator(dataSource, dictionaryOutputPath);
        dataDictionaryGenerator.setDataDictionaryFileName(dbName + ".txt");
        dataDictionaryGenerator.generate(metaBuilder.build());
        //html文档
        String html = engine.getTemplate(dicHtmlTemplate).renderToString(Kv.create().set("tables", tableMetas).set("dbName", dbName));
        FileKit.renderByString(html, dictionaryOutputPath + dbName + ".html", true);
        return this;
    }

    /**
     * TODO:MODEL生成
     *
     * @return
     */
    public ModelGenerator genModel(String prefixes) {
        for (TableMeta meta : tableMetas) {
            if (!meta.name.startsWith(prefixes)){
                continue;
            }
            String lastModelPackageName=modelPackageName+getNewModelName(meta);
            modelPackageSet.add(lastModelPackageName);
            Kv kv = Kv.by("modelPackageName", lastModelPackageName).set("tableMeta", meta);
            String content = this.engine.getTemplate(modelTemplate).renderToString(kv);
            String modelOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + lastModelPackageName.replace(".", "/");
            FileKit.renderByString(content, modelOutputDir + "/" + meta.modelName + ".java", true);
        }
        return this;
    }

    private String getNewModelName(TableMeta meta){
        String name=meta.name.toLowerCase();
        String modelName=StrKit.toUnderScoreCase(meta.modelName);
        String prefixes=name.replace(modelName,"");
        if (!prefixes.contains("_")){
            return "";
        }
        String[] tmp=prefixes.split("_");
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<tmp.length;i++){
            if (StrKit.notBlank(tmp[i])){
                sb.append(".");
                sb.append(tmp[i]);
            }
        }
        return sb.toString();
    }


}
