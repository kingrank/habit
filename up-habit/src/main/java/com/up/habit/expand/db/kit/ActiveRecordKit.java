package com.up.habit.expand.db.kit;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.up.habit.kit.ClassKit;
import com.up.habit.kit.StrKit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * TODO:数据库
 *
 * @author 王剑洪 on 2020/1/13 23:03
 */
public class ActiveRecordKit {


    public static final String SQL_SUFFIX = ".sql";
    public static final String CLASS_SUFFIX = ".class";
    public static final String FILE_SEPARATOR = "/";


    /**
     * TODO:添加sql模版
     *
     * @param arp
     * @param sqlPackageNames
     * @return void
     * @Author 王剑洪 on 2020/1/14 1:32
     **/
    public static void addSqlTemplate(ActiveRecordPlugin arp, String sqlPackageNames) {
        if (StrKit.notBlank(sqlPackageNames)) {
            String[] packageArray = sqlPackageNames.split(",");
            for (String packageName : packageArray) {
                if (StrKit.isBlank(packageName)) {
                    continue;
                }
                try {
                    scan(arp, packageName, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * TODO:添加数据映射
     *
     * @param arp
     * @param modelPackageName
     * @return void
     * @Author 王剑洪 on 2020/1/14 1:29
     **/
    public static void addMapping(ActiveRecordPlugin arp, String modelPackageName) {
        if (StrKit.notBlank(modelPackageName)) {
            String[] packageArray = modelPackageName.split(",");
            for (String packageName : packageArray) {
                if (StrKit.isBlank(packageName)) {
                    continue;
                }
                try {
                    scan(arp, packageName, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * TODO:扫描包名路径下的文件
     *
     * @param arp
     * @param packageName
     * @param isAddMapping
     * @return void
     * @Author 王剑洪 on 2020/1/14 1:31
     **/
    public static void scan(ActiveRecordPlugin arp, String packageName, boolean isAddMapping) throws IOException {
        String path = packageName.replace(".", FILE_SEPARATOR);
        Enumeration<URL> urls = ClassKit.getContextClassLoader().getResources(path);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if (url != null) {
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String pathTemp = path;
                    if (!pathTemp.startsWith(FILE_SEPARATOR)) {
                        pathTemp = FILE_SEPARATOR + pathTemp;
                    }
                    if (!pathTemp.endsWith(FILE_SEPARATOR)) {
                        pathTemp = pathTemp + FILE_SEPARATOR;
                    }
                    recursionScan(packageName, pathTemp, new File(url.getFile()), arp, isAddMapping);
                    break;
                } else {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    if (jarURLConnection != null) {
                        JarFile jarFile = jarURLConnection.getJarFile();
                        if (jarFile != null) {
                            Enumeration<JarEntry> jarEntries = jarFile.entries();
                            while (jarEntries.hasMoreElements()) {
                                JarEntry jarEntry = jarEntries.nextElement();
                                String jarEntryName = jarEntry.getName();
                                if (isAddMapping) {
                                    if (jarEntryName.endsWith(CLASS_SUFFIX)) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        addReflectMapping(arp, className);
                                    }
                                } else {
                                    if (jarEntryName.endsWith(SQL_SUFFIX)) {
                                        if (!jarEntryName.startsWith(FILE_SEPARATOR)) {
                                            jarEntryName = FILE_SEPARATOR + jarEntryName;
                                        }
                                        arp.addSqlTemplate(jarEntryName);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * TODO:递归扫描文件
     *
     * @param file
     * @return void
     * @Author 王剑洪 on 2020/1/13 23:31
     **/
    public static void recursionScan(String packageName, String path, File file, ActiveRecordPlugin arp, boolean isAddMapping) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tmp : files) {
                recursionScan(packageName, path, tmp, arp, isAddMapping);
            }
        } else {
            String name = file.getName();
            if (name.endsWith(SQL_SUFFIX) && !isAddMapping) {
                String temp = file.getPath().replace("\\", ".").replace(FILE_SEPARATOR, ".").split(path.replace(FILE_SEPARATOR, "."))[1].replace(name, "").replace(".", FILE_SEPARATOR);
                String template = path + temp + name;
                arp.addSqlTemplate(template);
            } else if (name.endsWith(CLASS_SUFFIX) && isAddMapping) {
                String temp = file.getPath().replace("\\", ".").replace(FILE_SEPARATOR, ".").split(path.replace(FILE_SEPARATOR, "."))[1].replace(CLASS_SUFFIX, "");
                addReflectMapping(arp, packageName + "." + temp);
            }
        }
    }

    private static void addReflectMapping(ActiveRecordPlugin arp, String clazzName) {
        try {
            Class<?> clazz = Class.forName(clazzName);
            if (Model.class.isAssignableFrom(clazz)) {
                Field tableNameFiled = clazz.getDeclaredField("TABLE_NAME");
                Field tablePksFiled = clazz.getDeclaredField("TABLE_PKS");
                String tableName = (String) tableNameFiled.get(clazz);
                String tablePks = (String) tablePksFiled.get(clazz);
                if (StrKit.notBlank(tablePks)) {
                    arp.addMapping(tableName, tablePks, (Class<? extends Model<?>>) clazz);
                }
            }
        } catch (Exception e) {
            System.err.println(clazzName);
            e.printStackTrace();
        }
    }
}
