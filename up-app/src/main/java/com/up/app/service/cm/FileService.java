package com.up.app.service.cm;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.upload.UploadFile;
import com.up.habit.controller.render.To;
import com.up.habit.kit.*;
import com.up.habit.service.HabitService;
import com.up.app.model.cm.File;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * TODO:
 *
 * @author 王剑洪 on 2020/1/19 16:43
 */
public class FileService extends HabitService<File> {

    public static FileService me = new FileService();

    public static String KEY_FILE_TYPE_LIST = "file-type-list";
    public static String KEY_FILE_MAX_SIZE = "file-max-size";
    public static String KEY_IMAGE_MAX_SIZE = "image-max-size";

    /**
     * 获取类型列表
     *
     * @return
     */
    public List<String> getFileFolder() {
        String list = ConfigService.me.getById(KEY_FILE_TYPE_LIST);
        if (StrKit.notBlank(list)) {
            String[] tmp = list.split(",");
            return Arrays.asList(tmp);
        } else {
            return null;
        }
    }

    /**
     * 获取文件限制最大尺寸
     *
     * @param isImage
     * @return
     */
    public Integer getMaxSize(boolean isImage) {
        String id = isImage ? KEY_IMAGE_MAX_SIZE : KEY_FILE_MAX_SIZE;
        String size = ConfigService.me.getById(id);
        try {
            return Integer.parseInt(size);
        } catch (Exception e) {
            return null;
        }

    }

    public To batchUpload(HttpServletRequest request, List<UploadFile> files, String type) {
        if (files == null && files.size() < 1) {
            return To.fail("文件不能为空");
        }
        List<String> fileFolder = getFileFolder();
        if (ArrayKit.isNotEmpty(fileFolder) && fileFolder.contains(type)) {
            return To.fail("文件类型不正确!");
        }
        final String[] msg = {"系统异常"};
        String host = RequestKit.getHost(request);
        List<File> fileList = new ArrayList<>();
        boolean isSuccess = db().tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                for (int i = 0; i < files.size(); i++) {
                    UploadFile file = files.get(i);
                    java.io.File uploadFile = file.getFile();
                    boolean isImage = FileKit.isImage(file.getFile());
                    Integer size = getMaxSize(isImage);
                    if (size != null) {
                        double length = uploadFile.length();
                        if (new BigDecimal(size).multiply(new BigDecimal(1024)).multiply(new BigDecimal(1024)).compareTo(new BigDecimal(length)) < 0) {
                            msg[0] = (isImage ? "图片" : "文件" + "大小不能超过" + size + "M");
                            return false;
                        }
                    }
                    String uploadFileName = file.getFileName();
                    int lastIndex = uploadFileName.lastIndexOf(".");
                    lastIndex = lastIndex == -1 ? uploadFileName.length() : lastIndex;
                    String suffixName = uploadFileName.substring(lastIndex);
                    String uploadPath = file.getUploadPath();
                    String name = "";
                    name += "/" + (isImage ? "image" : "file");
                    java.io.File fileTypeFolder = new java.io.File(uploadPath + name);
                    if (!fileTypeFolder.exists()) {
                        fileTypeFolder.mkdir();
                    }
                    name = "/" + type;
                    java.io.File typeFolder = new java.io.File(uploadPath + name);
                    if (!typeFolder.exists()) {
                        typeFolder.mkdir();
                    }
                    name += "/" + DateKit.toStr(new Date(), DateKit.datePattern);
                    java.io.File dateFolder = new java.io.File(uploadPath + name);
                    if (!dateFolder.exists()) {
                        dateFolder.mkdir();
                    }
                    name = name + "/" + StrKit.getRandomUUID() + suffixName;
                    java.io.File newFile = new java.io.File(uploadPath + name);
                    boolean flag = file.getFile().renameTo(newFile);
                    if (flag) {
                        File tmp = new File().setPath("upload" + name).setType(suffixName.substring(1)).setUsed(0);
                        tmp.save();
                        tmp.setPath(host +"/"+ tmp.getPath());
                        fileList.add(tmp);
                    } else {
                        msg[0] = "系统异常!";
                        return false;
                    }
                }
                return true;
            }
        });
        return isSuccess ? To.ok(fileList) : To.fail(msg[0]);
    }

    public boolean used(Integer newId, Integer oldId) {
        if (newId == null || newId.equals(oldId)) {
            return true;
        }
        return dao.template("updateUsed", Kv.by("newId", newId).set("oldId", oldId)).batch();
    }

    public boolean usedNonTransaction(Integer newId, Integer oldId) {
        if (newId == null || newId.equals(oldId)) {
            return true;
        }
        return dao.template("updateUsed", Kv.by("newId", newId).set("oldId", oldId)).batchNonTransaction();
    }

    public boolean has(int id) {
        return dao.findById(id) != null;
    }

}
