package com.kyuubiran.setubot.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;

@Beans
public class HelpListener {
    private static final String help = "指令列表:\n" +
            "[主人权限]\n" +
            "!sudo am/dm <QQ> 添加/删除主人级权限\n" +
            "!sudo aa/da <QQ> 添加/删除管理级权限\n" +
            "!sudo setname <名字> 设置bot的名字\n" +
            "[管理权限]\n" +
            "!test 存活确认\n" +
            "[群员权限]\n" +
            "!setu \"来点色图!\"";

    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!help")
    public void getHelpTips(MsgGet msg, MsgSender sender) {
        if (msg instanceof PrivateMsg) {
            sender.SENDER.sendPrivateMsg(((PrivateMsg) msg).getQQ(), help);
        } else if (msg instanceof GroupMsg) {
            sender.SENDER.sendGroupMsg((GroupMsg) msg, "指令已私发~请查收!");
            sender.SENDER.sendPrivateMsg(((GroupMsg) msg).getQQ(), help);
        }
    }
}