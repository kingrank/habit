package com.up.app.service.tool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.up.habit.controller.render.To;
import com.up.habit.expand.db.listener.Batch;
import com.up.habit.expand.gen.HabitGenerator;
import com.up.habit.expand.gen.model.Directory;
import com.up.habit.expand.gen.model.Table;
import com.up.habit.expand.gen.model.TableColumn;
import com.up.habit.kit.ArrayKit;
import com.up.habit.service.HabitService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/14 1:56
 */
public class GenService extends HabitService<Table> {

    public static GenService me = new GenService();

    TableColumn columnDao = new TableColumn().dao();


    /**
     * TODO:获取全部的数据连接的表
     *
     * @param table
     * @param pageNo
     * @param pageSize
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2019/12/15 22:06
     **/
    public To getAllTable(Table table, int pageNo, int pageSize) {
        Page<Table> page = dao.template("queryAllTable", table).paginate(pageNo, pageSize);
        return To.ok(page);
    }

    /**
     * @param tables
     */
    public To addBatch(String[] tables) {
        List<Directory> directoryList = DirService.me.getTablePrefix();
        boolean res = db().tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                List<Table> list = dao.template("queryTableListByNames", Kv.by("names", tables)).find();
                for (Table table : list) {
                    String comment = StrKit.isBlank(table.getComment()) ? "" : (table.getComment().endsWith("表") ? table.getComment().substring(0, table.getComment().length() - 1) : table.getComment());
                    table.setBusiness(comment);
                    String modelName = table.getName();
                    Directory directory = new Directory().setIcon("icon iconfont icon-setting-fill").setName("").setPath("").setPrefix("");
                    for (int i = 0; i < directoryList.size(); i++) {
                        String tmpPrefix = directoryList.get(i).getPrefix();
                        if (modelName.startsWith(tmpPrefix)) {
                            directory = directoryList.get(i);
                            break;
                        }
                    }
                    if (directory != null) {
                        table.setModule(directory.getPath()).setModuleName(directory.getName()).setModuleIcon(directory.getIcon());
                    }
                    modelName = StrKit.firstCharToUpperCase(StrKit.toCamelCase(modelName.replace(directory.getPrefix(), "")));
                    table.setModelName(modelName);
                    HabitGenerator.setTable(table);
                    if (table.save()) {
                        List<TableColumn> tableColumns = columnDao.template("queryTableColumnByTable", table.getName()).find();
                        String pks = "";
                        for (TableColumn column : tableColumns) {
                            column.setTableId(table.getId());
                            HabitGenerator.setTableColumn(column);
                            if (column.getIsPk().intValue() == 0) {
                                pks += column.getName() + ",";
                            }
                            if (!column.save()) {
                                return false;
                            }
                        }
                        if (StrKit.notBlank(pks)) {
                            pks = pks.endsWith(",") ? pks.substring(0, pks.length() - 1) : pks;
                            table.setPks(pks);
                            table.update();
                        }

                    } else {
                        return false;
                    }
                }
                return true;
            }
        });
        return To.to(res);
    }

    public To tableInfo(int tableId) {
        Table table = dao.findById(tableId);
        if (table == null) {
            return To.fail("记录不存在!");
        }
        List<TableColumn> columns = new TableColumn().dao().find("select * from gen_table_column where table_id=? order by id asc", tableId);
        return To.ok(Kv.by("table", table).set("columns", columns));
    }

    public To edit(Table table, String columns) {
        if (table.getId() == null) {
            return To.fail("记录不存在!");
        }
        Table old = dao.findById(table.getId());
        if (old == null) {
            return To.fail("记录不存在!");
        }
        old.setBusiness(StrKit.isBlank(table.getBusiness()) ? old.getBusiness() : table.getBusiness());
        old.setModule(StrKit.isBlank(table.getModule()) ? old.getModule() : table.getModule());
        old.setModuleName(StrKit.isBlank(table.getModuleName()) ? old.getModuleName() : table.getModuleName());
        old.setModuleIcon(StrKit.isBlank(table.getModuleIcon()) ? old.getModuleIcon() : table.getModuleIcon());
        old.setModelPkg(StrKit.isBlank(table.getModelPkg()) ? old.getModelPkg() : table.getModelPkg());
        old.setSrvPkg(StrKit.isBlank(table.getSrvPkg()) ? old.getSrvPkg() : table.getSrvPkg());
        old.setCtrPkg(StrKit.isBlank(table.getCtrPkg()) ? old.getCtrPkg() : table.getCtrPkg());
        old.setCtrKey(StrKit.isBlank(table.getCtrKey()) ? old.getCtrKey() : table.getCtrKey());
        old.setTpl(StrKit.isBlank(table.getTpl()) ? old.getTpl() : table.getTpl());
        old.setRemark(StrKit.isBlank(table.getRemark()) ? old.getRemark() : table.getRemark());
        List<Map<String, String>> columnList = new Gson().fromJson(columns, new TypeToken<List<Map<String, String>>>() {
        }.getType());
        boolean res = new TableColumn().dao().template("updateColumns", Kv.by("list", columnList)).batch(new Batch() {
            @Override
            public boolean batchBefore() {
                return old.update();
            }
        });
        return To.to(res);
    }

    public To preview(int tableId) {
        Ret ret = genCodeString(tableId);
        return To.ok(ret);
    }

    public Ret genCodeString(int tableId) {
        Table table = dao.findById(tableId);
        List<TableColumn> list = new TableColumn().dao().find("select * from gen_table_column where table_id=?", tableId);
        Ret content = HabitGenerator.me.build(table, list);
        return content;
    }

    public byte[] genCode(String tableIds) {
        String[] tableIdArray = ArrayKit.toStrArray(tableIds, ",");
        Table[] tables = new Table[tableIdArray.length];
        List<TableColumn>[] columns = new List[tableIdArray.length];
        for (int i = 0; i < tableIdArray.length; i++) {
            Table table = dao.findById(tableIdArray[i]);
            List<TableColumn> list = new TableColumn().dao().find("select * from gen_table_column where table_id=?", tableIdArray[i]);
            tables[i] = table;
            columns[i] = list;
        }

        return HabitGenerator.me.genZip(tables, columns);
    }


}
