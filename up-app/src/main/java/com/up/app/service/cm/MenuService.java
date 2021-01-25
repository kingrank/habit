package com.up.app.service.cm;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.ehcache.CacheKit;
import com.up.app.service.cm.kit.MenuKit;
import com.up.habit.Habit;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.ConfigKit;
import com.up.habit.controller.render.To;
import com.up.habit.expand.db.kit.TreeKit;
import com.up.habit.kit.NumberKit;
import com.up.habit.kit.StrKit;
import com.up.habit.service.HabitService;
import com.up.app.model.cm.Menu;

import java.lang.reflect.Method;
import java.util.*;

/**
 * TODO:
 *
 * @author 王剑洪 on 2019/12/19 20:34
 */
public class MenuService extends HabitService<Menu> {

    public static MenuService me = new MenuService();

    /**
     * TODO:获取全部模块树
     *
     * @param menu
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2019/12/19 21:42
     **/
    @Override
    public To list(Menu menu) {
        List<Menu> list = dao.template("queryByNameAndVisible", menu).find();
        list = TreeKit.buildTree(list, "p_id", "id");
        return To.ok(list);
    }

    /**
     * TODO:前端选择数数据结构
     *
     * @param
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2019/12/19 22:56
     **/
    public To treeSelect() {
        List<Menu> list = dao.template("treeSelect").find();
        list = TreeKit.buildTree(list, "p_id", "id");
        return To.ok(list);
    }

    /**
     * TODO:根据ID获取详情
     *
     * @param id
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2019/12/19 21:46
     **/
    public To infoById(String id) {
        return To.ok(dao.findById(id));
    }

    /**
     * TODO:新增菜单
     *
     * @param menu
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2019/12/19 21:48
     **/
    public To add(Menu menu) {
        if (StrKit.isBlank(menu.getName())) {
            return To.fail("菜单名称不能为空");
        }
        if (!NumberKit.has(menu.getType(), 1, 2, 3)) {
            return To.fail("菜单类型不正确!");
        }
        if (!NumberKit.isScope(menu.getVisible(), 0, 1)) {
            return To.fail("菜单状态不正确!");
        }
        if (menu.getSort() == null) {
            menu.setSort(1);
        }
        if (menu.getType().intValue() == 1 || menu.getType().intValue() == 2) {
            //目录，菜单
            if (StrKit.isBlank(menu.getIcon())) {
                return To.fail("图标不能为空!");
            }
            if (StrKit.isBlank(menu.getPath())) {
                return To.fail("路由地址不能为空!");
            }
            if (menu.getType().intValue() == 2 && StrKit.isBlank(menu.getComponent())) {
                return To.fail("组件路径不能为空!");
            }
        } else if (menu.getType().intValue() == 3) {
            if (StrKit.isBlank(menu.getAction())) {
                return To.fail("权限标识不能为空");
            }
        }
        if (menu.getPId() != null && menu.getPId().intValue() != 0) {
            Menu parentMenu = dao.findById(menu.getPId());
            if (parentMenu == null) {
                return To.fail("上级菜单不存在!");
            }
        } else {
            menu.setPId(0);
        }
        return To.to(menu.save());
    }

    /**
     * TODO:编辑菜单
     *
     * @param menu
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:07
     **/
    public To edit(Menu menu) {
        if (menu.getId() == null) {
            return To.fail("菜单不存在!");
        }
        Menu old = dao.findById(menu.getId());
        if (old == null) {
            return To.fail("菜单不存在!");
        }
        old.setPId(menu.getPId() == null ? old.getPId() : menu.getPId());
        old.setIcon(StrKit.isBlank(menu.getIcon()) ? old.getIcon() : menu.getIcon());
        old.setName(StrKit.isBlank(menu.getName()) ? old.getName() : menu.getName());
        old.setIsLink(NumberKit.has(menu.getType(), 1, 2, 3) ? menu.getIsLink() : old.getIsLink());
        old.setIsLink(NumberKit.isSwitch(menu.getIsLink()) ? old.getIsLink() : menu.getIsLink());
        old.setPath(StrKit.isBlank(menu.getPath()) ? old.getPath() : menu.getPath());
        old.setComponent(StrKit.isBlank(menu.getComponent()) ? old.getComponent() : menu.getComponent());
        old.setVisible(NumberKit.isSwitch(menu.getVisible()) ? menu.getVisible() : old.getVisible());
        old.setAction(StrKit.isBlank(menu.getAction()) ? old.getAction() : menu.getAction());
        old.setSort(menu.getSort() == null ? old.getSort() : menu.getSort());
        old.setRemark(StrKit.isBlank(menu.getRemark()) ? old.getRemark() : menu.getRemark());
        if (menu.getPId() != null && menu.getPId().intValue() != 0) {
            Menu parentMenu = dao.findById(menu.getPId());
            if (parentMenu == null) {
                return To.fail("上级菜单不存在!");
            }
        }
        return To.to(old.update());
    }

    /**
     * TODO:删除菜单
     *
     * @param id
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2020/1/5 16:07
     **/
    public To delete(String id) {
        Menu menu = dao.findById(id);
        if (menu == null) {
            return To.fail("记录不存在!");
        }
        int count = dao.template("queryChildCount", menu.getId()).queryInt();
        if (count > 0) {
            return To.fail("请先删除子模块菜单!");
        }
        return super.delete(new String[]{id});
    }

    /**
     * TODO:获取vue路由
     *
     * @return com.up.habit.controller.render.To
     * @Author 王剑洪 on 2019/12/22 10:35
     **/
    public To getRouters(Integer[] roleIds) {
        List<Menu> menuList = new ArrayList<>();
        if (roleIds != null && roleIds.length > 0) {
            if (roleIds != null && Arrays.asList(roleIds).contains(1)) {
                menuList = dao.template("queryAllRouter").find();
            } else {
                menuList = dao.template("queryRouter", Kv.by("roleIds", roleIds)).find();
            }
            menuList = TreeKit.buildTree(menuList, "p_id", "id");
        }
        List<Menu> list = MenuKit.builderMenu(menuList);
        return To.ok(list);
    }

    /**
     * TODO:菜单权限自动同步
     *
     * @param
     * @return void
     * @Author 王剑洪 on 2020/1/5 16:08
     **/
    public void sync() {
        Set<Class<?>> classes = ConfigKit.getClazz(ConfigKit.getPackages(ConfigKit.PACKAGE_ADMIN));
        if (classes != null && !classes.isEmpty()) {
            int index = 0;
            for (Class<?> clazz : classes) {
                Ctr ctr = clazz.getAnnotation(Ctr.class);
                //类不是继承Controller则不算控制器,没有控制器注解,过滤,
                if (ctr == null || ctr.auth() == AuthType.NULL||StrKit.isBlank(ctr.name())) {
                    continue;
                }
                index++;
                //目录
                Menu dire = MenuKit.editDirector(clazz, index);
                Menu menu = MenuKit.editMenu(clazz, ctr, dire, index);
                Method[] methods = clazz.getMethods();
                for (int i = 0; i < methods.length; i++) {
                    MenuKit.editButton(clazz, methods[i], ctr, menu, i + 1);
                }
            }
        }
    }


}
