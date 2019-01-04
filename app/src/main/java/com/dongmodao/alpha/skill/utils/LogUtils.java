package com.dongmodao.alpha.skill.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author : tangqihao
 * Log 工具，读写入本地文件
 * @time : 2019/1/3
 * @project : AlphaSkill
 */
public class LogUtils {

    private static String LOG_FILE = "log_utils.txt";

    public static void log(Context context, String message) {
        File cacheDir = context.getCacheDir();
        File file = checkOrNew(cacheDir, LOG_FILE);

        String time = ms2DateOnlyDay(System.currentTimeMillis());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(time + " " + message + "\r\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String read(Context context) {
        File cacheDir = context.getCacheDir();
        File file = checkOrNew(cacheDir, LOG_FILE);
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                builder.append(line + "\r\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String log = builder.toString();
        Log.e("---", "read: log = " + log  );
        return log;

    }

    public static void clean(Context context) {
        File cacheDir = context.getCacheDir();
        File file = new File(cacheDir, LOG_FILE);
        if (file.exists())
            file.delete();
    }

    private static File checkOrNew(File cacheDir, String name) {
        File file = new File(cacheDir, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * long值转为string（年月日）
     * */
    public static String ms2DateOnlyDay(long _ms){
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

}
