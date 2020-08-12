package com.kyuubiran.setubot;

import com.forte.qqrobot.SimpleRobotApplication;
import com.simbot.component.mirai.MiraiApplication;

import java.io.*;

@SimpleRobotApplication(resources = "conf.properties")
public class Main {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws IOException {
        File cfgDir = new File("config").getAbsoluteFile();
        File loginCfg = new File("config/conf.properties");
        File botCfg = new File("config/bot_config.json");
        if (!cfgDir.exists()) {
            cfgDir.mkdirs();
        }
        if (!loginCfg.exists()) {
            loginCfg.createNewFile();
            InputStream loginCfgIn = Main.class.getClassLoader().getResourceAsStream("conf.properties");
            FileOutputStream loginCfgOut = new FileOutputStream(loginCfg);
            byte[] buf = new byte[128];
            int i;
            while ((i = loginCfgIn.read(buf)) > 0) {
                loginCfgOut.write(buf, 0, i);
            }
            loginCfgOut.flush();
            loginCfgOut.close();
            loginCfgIn.close();
        }
        if (!botCfg.exists()) {
            InputStream botCfgIn = Main.class.getClassLoader().getResourceAsStream("bot_config.json");
            FileOutputStream botCfgOut = new FileOutputStream(botCfg);
            byte[] buf = new byte[128];
            int i;
            while ((i = botCfgIn.read(buf)) > 0) {
                botCfgOut.write(buf, 0, i);
            }
            botCfgOut.flush();
            botCfgOut.close();
            botCfgIn.close();
        }
        MiraiApplication application = new MiraiApplication();
        application.run(Main.class, args);
    }
}