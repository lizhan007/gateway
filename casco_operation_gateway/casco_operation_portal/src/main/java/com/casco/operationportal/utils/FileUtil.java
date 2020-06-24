package com.casco.operationportal.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

public class FileUtil {

    //生成文件
    public static void createFile(String filePath, String context){
        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(context.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //复制文件
    public static String copeFile(String source, String purposePath) {

        String nawFilepath = "";

        File sourceFile = new File(source);
        if(!sourceFile.getParentFile().exists()){
            sourceFile.getParentFile().mkdirs();
        }

        try {
            FileInputStream fis = new FileInputStream(sourceFile);

            //创建新的文件，保存复制内容，文件名称与源文件名称一致
            File newFile = new File(purposePath + "/" + sourceFile.getName());
            if(!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs();
            }
            if(!newFile.exists()){
                newFile.createNewFile();
            }

            nawFilepath = newFile.getPath();

            FileOutputStream fos = new FileOutputStream(newFile);
            // 读写数据
            // 定义数组
            byte[] b = new byte[1024];
            // 定义长度
            int len;
            // 循环读取
            while ((len = fis.read(b))!=-1) {
                // 写出数据
                fos.write(b, 0 , len);

            }

            //关闭资源
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nawFilepath;
    }
}
