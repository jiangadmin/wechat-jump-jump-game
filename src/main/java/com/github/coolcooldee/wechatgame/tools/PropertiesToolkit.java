package com.github.coolcooldee.wechatgame.tools;

import java.io.*;
import java.util.Properties;

/**
 * @author Dee1024 <coolcooldee@gmail.com>
 * @version 1.0
 * @description
 * @date 2018/1/4
 * @since 1.0
 */

public abstract class PropertiesToolkit {

    static String jarPath;
    final static String PRO_FILE_NAME = "setting.properties";
    final static String PRO_ADB_PATH = "adb_path"; //adb工具路径
    final static String PRO_UI_RATIO = "ui_ratio"; //界面的缩放比率
    final static String PRO_JUMP_COEFFICIENT = "jump_coefficient";

    public static void init() {
        jarPath = System.getProperty("user.dir") + File.separator;
        LogToolKit.println("执行应用目录为：" + jarPath);
        File file = new File(PRO_FILE_NAME);
        if (!file.exists()) {
            try {
                boolean r = file.createNewFile();
                if (r) {
                    FileOutputStream newFileOutputStream = new FileOutputStream(PRO_FILE_NAME);
                    Properties properties = new Properties();
                    properties.setProperty(PRO_ADB_PATH, "E:\\software\\android-platform-tools\\adb");
                    properties.setProperty(PRO_UI_RATIO, "1");
                    properties.setProperty(PRO_JUMP_COEFFICIENT, "1");
                    properties.store(newFileOutputStream, "init setting");
                    newFileOutputStream.flush();
                    newFileOutputStream.close();
                    LogToolKit.println("成功创建并初始化配置文件 " + jarPath + PRO_FILE_NAME);
                } else {
                    LogToolKit.println(" 配置文件创建失败！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getSettingADBPath() {
        return getSettingProperties().getProperty(PRO_ADB_PATH);
    }

    public static void setSettingADBPath(String path) {
        Properties pro = getSettingProperties();
        pro.setProperty(PRO_ADB_PATH, path);
        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream(PRO_FILE_NAME);
            pro.store(oFile, "app setting");
            oFile.close();
            LogToolKit.println("保存ADB地址配置信息到 " + PRO_FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getSettingProperties() {
        Properties pro = new Properties();
        File file = new File(PRO_FILE_NAME);
        if (file.exists()) {
            LogToolKit.println("读取配置文件 " + PRO_FILE_NAME);
            try {
                FileInputStream in = new FileInputStream(PRO_FILE_NAME);
                pro.load(in);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pro;
        } else {
            try {
                boolean r = file.createNewFile();
                if (r) {
                    FileInputStream in = new FileInputStream(PRO_FILE_NAME);
                    pro.load(in);
                    in.close();
                    LogToolKit.println("成功创建配置文件 " + PRO_FILE_NAME);
                } else {
                    LogToolKit.println(" 配置文件创建失败！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pro;
        }
    }
}
