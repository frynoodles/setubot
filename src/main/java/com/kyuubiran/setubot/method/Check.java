package com.kyuubiran.setubot.method;

import java.util.List;

public class Check {
    //检测是否为qq 范围[10000-9999999999]
    public static boolean isQQ(String qq) {
        return qq.matches("^[1-9][0-9]{4,9}");
    }

    public static int authLevel(String qq) {
        //读取权限列表
        List<String> masterList = BotConfig.getMasterList();
        List<String> adminList = BotConfig.getAdminList();
        //主人级权限检测 等级3
        for (String s : masterList) {
            if (qq.equals(s)) {
                return 3;
            }
        }
        //管理级权限检测 等级2
        for (String s : adminList) {
            if (qq.equals(s)) {
                return 2;
            }
        }
        //其他用户均为等级1
        return 1;
    }

    //检测是否为添加tag的格式
    public static boolean isTag(String s) {
        return s.matches("^[1-9][0-9]{0,11}\\s+");
    }
}