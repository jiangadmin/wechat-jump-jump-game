package com.github.coolcooldee.wechatgame.service;

import com.github.coolcooldee.wechatgame.tools.AdbToolKit;
import com.github.coolcooldee.wechatgame.tools.LogToolKit;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 跳跃的核心逻辑
 *
 * @author Dee1024 <coolcooldee@gmail.com>
 * @Description
 * @Version 1.0
 * @Since 1.0
 * @Date 2018/1/3
 */

public abstract class JumpService {

    private static Double distance2timeRatio = null;
    private static final String SCREENCAP_PATH = "jumpgame.png";

    /**
     * 分辨率对应的按压时长系数
     */
    private static final Map<String, Double> resolutionMapDistance2timeRatio = new HashMap();

    /**
     * 装载系数
     */
    static {
        resolutionMapDistance2timeRatio.put("1600*2560", 0.92 * 4);
        resolutionMapDistance2timeRatio.put("1440*2960", 1.04 * 4);
        resolutionMapDistance2timeRatio.put("1440*2560", 1.039 * 2.5);
        resolutionMapDistance2timeRatio.put("1080*2220", 1.3903 * 2.5);
        resolutionMapDistance2timeRatio.put("1080*1920", 1.3903 * 2);//ok
        resolutionMapDistance2timeRatio.put("720*1280", 2.078 * 2);//ok
    }

    //分辨率与显示界面的实际长宽的比率
    /**
     * 分辨率对应的
     */
    private static final Map<String, Double> resolutionMapUIRate = new HashMap();

    static {
        resolutionMapUIRate.put("1600*2560", 0.25);
        resolutionMapUIRate.put("1440*2960", 0.25);
        resolutionMapUIRate.put("1440*2560", 0.4);
        resolutionMapUIRate.put("1080*2220", 0.4);
        resolutionMapUIRate.put("1080*1920", 0.5); //ok
        resolutionMapUIRate.put("720*1280", 0.5);  //ok
    }

    //起跳点
    private static Point beginPoint = new Point(0, 0);

    //目标点
    private static Point endPoint = new Point(0, 0);

    /**
     * 进行跳跃，同时等待一会，等到其停止，方便下一步截屏
     *
     * @param beginPoint
     * @param endPoint
     */
    public static boolean jump(Point beginPoint, Point endPoint) {
        int d = getDistance(beginPoint, endPoint);
        if (d != 0) {
            LogToolKit.println("跳跃距离 " + d);
//            if (d < 20) {
//                LogToolKit.println("距离太小，重新跳跃 " + d);
//                return false;
//            }
            AdbToolKit.screentouch(Math.floor(d * getDistance2timeRatio()));
            try {
                Thread.sleep(950);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private static int getDistance(Point a, Point b) {
        double _x = Math.abs(a.x - b.x);
        double _y = Math.abs(a.y - b.y);
        return (int) Math.sqrt(_x * _x + _y * _y);
    }

    public static String getScreencapPath() {
        return SCREENCAP_PATH;
    }

    public static Point getBeginPoint() {
        return beginPoint;
    }

    public static void setBeginPoint(Point beginPoint) {
        if (beginPoint != null) {
            JumpService.beginPoint.setLocation(beginPoint.getX(), beginPoint.getY());
            if (beginPoint.getX() > 0 && beginPoint.getY() >= 0) {
                LogToolKit.println("起点[" + (int) beginPoint.getX() + "/" + (int) beginPoint.getY() + "]");
            }
        } else {
            JumpService.beginPoint.setLocation(0, 0);
        }
    }

    public static Double getDistance2timeRatio() {
        return distance2timeRatio;
    }

    public static void setDistance2timeRatio(double distance2timeRatio) {
        JumpService.distance2timeRatio = distance2timeRatio;
    }

    public static Double getDistance2timeRatioByResolution(String resolution) {
        return resolutionMapDistance2timeRatio.get(resolution);
    }

    public static Double getUIRatioByResolution(String resolution) {
        return resolutionMapUIRate.get(resolution);
    }

    public static Point getEndPoint() {
        return endPoint;
    }

    public static void setEndPoint(Point endPoint) {
        if (endPoint != null) {
            JumpService.endPoint.setLocation(endPoint.getX(), endPoint.getY());
            if (endPoint.getX() > 0 && endPoint.getY() >= 0) {
                LogToolKit.println("落点[" + (int)endPoint.getX() + "/" + (int)endPoint.getY() + "]");
            }
        } else {
            JumpService.endPoint.setLocation(0, 0);
        }

    }
}
