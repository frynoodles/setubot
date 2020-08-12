package com.kyuubiran.setubot.method;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BotConfig {
    //获取bot配置文件
    private static String getBotConfig() {
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(new FileInputStream("config/bot_config.json"), StandardCharsets.UTF_8);
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(sb);
    }

    //写入配置文件
    private static void setBotConfig(String type, String value) {
        //读取json配置文件
        JSONObject config = JSON.parseObject(getBotConfig());
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("config/bot_config.json"), StandardCharsets.UTF_8);
            JSONArray masterList = config.getJSONArray("master");
            JSONArray adminList = config.getJSONArray("admin");
            switch (type) {
                //设置名字
                case "setBotName": {
                    config.put("name", value);
                    break;
                }
                //添加主人
                case "addMaster": {
                    //数组增加
                    masterList.add(value);
                    config.put("master", masterList);
                    break;
                }
                //删除主人
                case "delMaster": {
                    masterList = config.getJSONArray("master");
                    //数组减少
                    masterList.remove(value);
                    config.put("master", masterList);
                    break;
                }
                //添加管理
                case "addAdmin": {
                    adminList.add(value);
                    config.put("admin", adminList);
                    break;
                }
                //删除主人
                case "delAdmin": {
                    adminList = config.getJSONArray("admin");
                    adminList.remove(value);
                    config.put("admin", adminList);
                    break;
                }
                case "setApiKey": {
                    config.put("apikey", value);
                    break;
                }
                case "setR18": {
                    config.put("r18", value);
                    break;
                }
            }
            //执行写入 刷新 关闭文件
            osw.write(config.toString());
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取bot昵称
    public static String getBotName() {
        JSONObject jsonObject = JSONObject.parseObject(getBotConfig());
        return jsonObject.getString("name");
    }

    //获取权限列表 master主人级 admin管理级
    private static List<String> getQQList(String type) {
        JSONArray jsonArray = JSONObject.parseObject(getBotConfig()).getJSONArray(type);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    //获取主人级权限列表
    public static List<String> getMasterList() {
        return getQQList("master");
    }

    //获取管理级权限列表
    public static List<String> getAdminList() {
        return getQQList("admin");
    }

    //修改bot名字
    public static void setBotName(String name) {
        setBotConfig("setBotName", name);
    }

    //添加主人级权限
    public static void addMaster(String qq) {
        setBotConfig("addMaster", qq);
    }

    //删除主人级权限
    public static void delMaster(String qq) {
        setBotConfig("delMaster", qq);
    }

    //添加管理级权限
    public static void addAdmin(String qq) {
        setBotConfig("addAdmin", qq);
    }

    //删除管理级权限
    public static void delAdmin(String qq) {
        setBotConfig("delAdmin", qq);
    }

    //设置api key  api.lolicon.app
    public static void setApiKey(String apiKey) {
        setBotConfig("setApiKey", apiKey);
    }

    //获取api key
    public static String getApiKey() {
        JSONObject jsonObject = JSONObject.parseObject(getBotConfig());
        return jsonObject.getString("apikey");
    }


    //设置R-18 0=off 1=on 2=both
    public static void setR18(String i) {
        setBotConfig("setR18", i);
    }

    //获取R-18
    public static String getR18() {
        JSONObject jsonObject = JSONObject.parseObject(getBotConfig());
        return jsonObject.getString("r18");
    }
}