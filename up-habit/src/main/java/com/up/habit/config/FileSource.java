package com.up.habit.config;

import com.jfinal.template.source.ClassPathSource;
import com.jfinal.template.source.ISource;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * TODO:
 * <p>
 * Created by 王剑洪 on 2020/2/1 20:43
 */
public class FileSource extends ClassPathSource {

    public FileSource(String fileName) {
        super(fileName);
    }

    public File getFile() {
        return new File(this.url.getFile());
    }
}
