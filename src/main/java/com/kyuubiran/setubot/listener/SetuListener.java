package com.kyuubiran.setubot.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.kyuubiran.setubot.method.Setu;
import com.simplerobot.modules.utils.KQCodeUtils;

@Beans
public class SetuListener {
    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!setu")
    public void sendSetuGM(MsgGet msg, MsgSender sender) {
        Setu setu = new Setu();
        String info = setu.get();
        if (msg instanceof PrivateMsg) {
            if (info == null) {
                sender.SENDER.sendPrivateMsg((PrivateMsg) msg, KQCodeUtils.INSTANCE.toCq("image", "file=" + setu.getPicLocation()) + setu.getFormat());
            } else {
                sender.SENDER.sendPrivateMsg((PrivateMsg) msg, info);
            }
        } else if (msg instanceof GroupMsg) {
            if (info == null) {
                sender.SENDER.sendGroupMsg((GroupMsg) msg, KQCodeUtils.INSTANCE.toCq("image", "file=" + setu.getPicLocation()) + setu.getFormat());
            } else {
                sender.SENDER.sendGroupMsg((GroupMsg) msg, info);
            }
        }
    }
}
