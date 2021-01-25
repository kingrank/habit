package com.up.app.service.cm.kit;

import com.jfinal.kit.Kv;
import com.up.app.model.cm.Menu;
import com.up.habit.config.anno.ApiMethod;
import com.up.habit.config.anno.AuthType;
import com.up.habit.config.anno.Ctr;
import com.up.habit.config.anno.Directory;
import com.up.habit.config.ConfigKit;
import com.up.habit.kit.ArrayKit;
import com.up.habit.kit.StrKit;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * TODO:菜单工具类
 *
 * @author 王剑洪 on 2020/1/13 0:38
 */
public class MenuKit {


    /**
     * 路由菜单生成
     *
     * @param menuList
     * @return
     */
    public static List<Menu> builderMenu(List<Menu> menuList) {
        for (int i = 0; i < menuList.size(); i++) {
            boolean hasParentDirectory = menuList.get(i).getPId() != null && menuList.get(i).getPId().intValue() != 0 && menuList.get(i).getType().intValue() == 1;
            String component = menuList.get(i).getComponent();
            if (hasParentDirectory) {
                menuList.get(i).setComponent("Directory");
                if (StrKit.isBlank(menuList.get(i).getPath())) {
                    menuList.get(i).setPath("/dire");
                }
            } else {
                menuList.get(i).setComponent(StrKit.isBlank(component) ? "Layout" : component);
            }
            menuList.get(i).setPath(getRouterPath(menuList.get(i)));
            menuList.get(i).put("hidden", menuList.get(i).getVisible() != null && menuList.get(i).getVisible().intValue() == 1);
            menuList.get(i).put("meta", Kv.by("icon", menuList.get(i).getIcon()).set("title", menuList.get(i).getName()));
            List<Menu> children = menuList.get(i).get("children");
            if (ArrayKit.isNotEmpty(children) && menuList.get(i).getType().intValue() == 1) {
                menuList.get(i).put("alwaysShow", true);
                menuList.get(i).put("redirect", "noRedirect");
                menuList.get(i).put("children", builderMenu(children));
            }
            menuList.get(i).remove("controller", "visible", "create_time", "create_by", "modify_by", "modify_time", "hasChildren", "icon", "remark", "sort", "type", "is_link", "action", "id", "p_id");
        }
        return menuList;
    }

    /**
     * 获取路由地址
     *
     * @param menu
     * @return
     */
    private static String getRouterPath(Menu menu) {
        String path = menu.getPath();
        if (menu.getPId().intValue() == 0 && menu.getIsLink().intValue() == 0) {
            path = "/" + menu.getPath();
        }
        return path;
    }

    /**
     * TODO:生成目录
     *
     * @param clazz
     * @return
     */
    public static Menu editDirector(Class<?> clazz, int index) {
        Menu dao = new Menu().dao();
        Directory directory = clazz.getAnnotation(Directory.class);
        if (directory != null) {
            String path = StrKit.isBlank(directory.path()) ? ConfigKit.getLastPackage(clazz).replace(".", "") : directory.path();
            Menu dire = dao.template("queryDirectory", directory.value(), path).findFirst();
            if (dire == null) {
                String component=StrKit.isBlank(directory.component())?"Layout" : directory.component();
                if (component.startsWith("/admin/")){
                    component=component.substring(7);
                }
                if (component.startsWith("/")){
                    component=component.substring(1);
                }
                dire = new Menu().setCreateBy(1).setCreateTime(new Date()).setVisible(0).setIsLink(0).setType(AuthType.DIRECTOR.getValue()).setPId(0).setSort(index)
                        .setIcon(directory.icon())
                        .setName(directory.value())
                        .setPath(path)
                        .setComponent(component)
                        .setRemark(directory.value());
                dire.save();
            }
            return dire;
        }
        return null;
    }

    /**
     * TODO:自动生成菜单
     *
     * @param clazz
     * @param ctr
     * @param director
     * @param index
     * @return com.up.model.cm.Menu
     * @Author 王剑洪 on 2020/1/5 16:07
     **/
    public static Menu editMenu(Class<?> clazz, Ctr ctr, Menu director, int index) {
        Menu dao = new Menu().dao();
        String controllerKey = ConfigKit.getAction(ctr, clazz);
        Menu menu = dao.template("queryMenu", clazz.getName()).findFirst();
        if (menu == null) {
            String component=controllerKey;
            if (component.startsWith("/admin/")){
                component=component.substring(7);
            }
            if (component.startsWith("/")){
                component=component.substring(1);
            }
            menu = new Menu().setCreateBy(1).setCreateTime(new Date()).setVisible(0).setIsLink(0).setAction(null).setType(AuthType.MENU.getValue())
                    .setPId(director == null ? 0 : director.getId())
                    .setIcon(ctr.icon())
                    .setName(ctr.name())
                    .setPath(controllerKey.substring(controllerKey.lastIndexOf("/")).substring(1))
                    .setComponent(component)
                    .setController(clazz.getName())
                    .setSort(index)
                    .setRemark(ctr.name());
            if (StrKit.notBlank(ctr.view())) {
                menu.setComponent(ctr.view());
            }
            menu.save();
        }
        return menu;
    }

    /**
     * TODO:自动生成按钮
     *
     * @param clazz
     * @param method
     * @param ctr
     * @param menu
     * @param index
     * @return void
     * @Author 王剑洪 on 2020/1/5 16:08
     **/
    public static void editButton(Class<?> clazz, Method method, Ctr ctr, Menu menu, int index) {
        Menu dao = new Menu().dao();
        ApiMethod apiMethod = method.getAnnotation(ApiMethod.class);
        if (apiMethod != null && apiMethod.auth() != AuthType.NULL) {
            String controllerKey = ConfigKit.getAction(ctr, clazz);

            String action = controllerKey + "/" + method.getName();
            if (action.startsWith("/admin/")){
                action=action.substring(7);
            }
            if (action.startsWith("/")){
                action=action.substring(1);
            }
            Menu button = dao.template("queryAction", clazz.getName(), action).findFirst();
            if (button == null) {
                button = new Menu().setCreateBy(1).setCreateTime(new Date()).setVisible(0).setIsLink(0).setSort(index).setIcon(null).setPath(null).setComponent(null)
                        .setPId(menu.getId())
                        .setName(apiMethod.value())
                        .setType(apiMethod.auth().getValue())
                        .setController(clazz.getName())
                        .setAction(action)
                        .setRemark(apiMethod.value());
                button.save();
            }
        }
    }


}
