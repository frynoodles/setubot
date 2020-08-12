package com.kyuubiran.setubot.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.kyuubiran.setubot.method.BotConfig;
import com.kyuubiran.setubot.method.Check;

@Beans
public class ConfigListener {
    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!sudo am .*")
    //增加主人级权限
    public void addMaster(MsgGet msg, MsgSender sender) {
        String s = msg.getMsg().replace("!sudo aa ", "");
        if (msg instanceof PrivateMsg) {
            if (Check.authLevel(((PrivateMsg) msg).getQQ()) >= 3) {
                if (Check.isQQ(s)) {
                    BotConfig.addAdmin(s);
                    sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "添加主人" + s + "成功!");
                } else {
                    sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "这不是QQ号哦!");
                }
            }
        } else if (msg instanceof GroupMsg) {
            if (Check.authLevel(((GroupMsg) msg).getQQ()) >= 3) {
                if (Check.isQQ(s)) {
                    BotConfig.addAdmin(s);
                    sender.SENDER.sendGroupMsg((GroupMsg) msg, "添加主人" + s + "成功!");
                } else {
                    sender.SENDER.sendGroupMsg((GroupMsg) msg, "这不是QQ号哦!");
                }
            }
        }
    }

    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!sudo dm .*")
    //删除主人级权限
    public void delMaster(MsgGet msg, MsgSender sender) {
        String s = msg.getMsg().replace("!sudo da ", "");
        if (msg instanceof PrivateMsg) {
            if (Check.authLevel(((PrivateMsg) msg).getQQ()) >= 3) {
                if (Check.isQQ(s)) {
                    BotConfig.delMaster(s);
                    sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "删除主人" + s + "成功!");
                } else {
                    sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "这不是QQ号哦!");
                }
            }
        } else if (msg instanceof GroupMsg) {
            if (Check.authLevel(((GroupMsg) msg).getQQ()) >= 3) {
                if (Check.isQQ(s)) {
                    BotConfig.delMaster(s);
                    sender.SENDER.sendGroupMsg((GroupMsg) msg, "删除主人" + s + "成功!");
                } else {
                    sender.SENDER.sendGroupMsg((GroupMsg) msg, "这不是QQ号哦!");
                }
            }
        }
    }

    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!sudo aa .*")
    //增加管理级权限
    public void addAdmin(MsgGet msg, MsgSender sender) {
        String s = msg.getMsg().replace("!sudo aa ", "");
        if (msg instanceof PrivateMsg) {
            if (Check.authLevel(((PrivateMsg) msg).getQQ()) >= 3) {
                if (Check.isQQ(s)) {
                    BotConfig.addAdmin(s);
                    sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "添加管理员" + s + "成功!");
                } else {
                    sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "这不是QQ号哦!");
                }
            }
        } else if (msg instanceof GroupMsg) {
            if (Check.authLevel(((GroupMsg) msg).getQQ()) >= 3) {
                if (Check.isQQ(s)) {
                    BotConfig.addAdmin(s);
                    sender.SENDER.sendGroupMsg((GroupMsg) msg, "添加管理员" + s + "成功!");
                } else {
                    sender.SENDER.sendGroupMsg((GroupMsg) msg, "这不是QQ号哦!");
                }
            }
        }
    }

    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!sudo da .*")
    //删除管理级权限
    public void delAdmin(MsgGet msg, MsgSender sender) {
        String s = msg.getMsg().replace("!sudo da ", "");
        if (msg instanceof PrivateMsg) {
            if (Check.authLevel(((PrivateMsg) msg).getQQ()) >= 3) {
                if (Check.isQQ(s)) {
                    BotConfig.delAdmin(s);
                    sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "删除管理员" + s + "成功!");
                } else {
                    sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "这不是QQ号哦!");
                }
            }
        } else if (msg instanceof GroupMsg) {
            if (Check.authLevel(((GroupMsg) msg).getQQ()) >= 3) {
                if (Check.isQQ(s)) {
                    BotConfig.delAdmin(s);
                    sender.SENDER.sendGroupMsg((GroupMsg) msg, "删除管理员" + s + "成功!");
                } else {
                    sender.SENDER.sendGroupMsg((GroupMsg) msg, "这不是QQ号哦!");
                }
            }
        }
    }

    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!sudo setname .*")
    //设置bot的名字
    public void setBotName(MsgGet msg, MsgSender sender) {
        String s = msg.getMsg().replace("!sudo setname ", "");
        if (msg instanceof PrivateMsg) {
            if (Check.authLevel(((PrivateMsg) msg).getQQ()) == 3) {
                BotConfig.setBotName(s);
                sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "现在开始我就叫\"" + s + "\"啦!");
            }
        } else if (msg instanceof GroupMsg) {
            if (Check.authLevel(((GroupMsg) msg).getQQ()) == 3) {
                BotConfig.setBotName(s);
                sender.SENDER.sendGroupMsg((GroupMsg) msg, "现在开始我就叫\"" + s + "\"啦!");
            }
        }

    }

    //0为off 1为on 2为both
    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!sudo setH .*")
    public void setR18(MsgGet msg, MsgSender sender) {
        String s = msg.getMsg().replace("!sudo setH ", "");
        int i = Integer.parseInt(s);
        if (msg instanceof PrivateMsg) {
            if (Check.authLevel(((PrivateMsg) msg).getQQ()) == 3) {
                if (i <= 2 && i >= 0) {
                    BotConfig.setR18(i + "");
                }
            }
        } else if (msg instanceof GroupMsg) {
            if (Check.authLevel(((GroupMsg) msg).getQQ()) == 3) {
                if (i <= 2 && i >= 0) {
                    BotConfig.setR18(i + "");
                }
            }
        }
    }

    //存活确认
    @Listen({MsgGetTypes.privateMsg, MsgGetTypes.groupMsg})
    @Filter("!test")
    public void aliveTest(MsgGet msg, MsgSender sender) {
        if (msg instanceof PrivateMsg) {
            if (Check.authLevel(((PrivateMsg) msg).getQQ()) >= 2) {
                sender.SENDER.sendPrivateMsg((PrivateMsg) msg, "私聊消息测试通过");
            }
        } else if (msg instanceof GroupMsg) {
            if (Check.authLevel(((GroupMsg) msg).getQQ()) >= 2) {
                sender.SENDER.sendGroupMsg((GroupMsg) msg, "群聊消息测试通过");
            }
        }
    }
}