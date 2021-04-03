package listener

import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.content
import util.BotConfig
import util.Log
import util.bot
import util.Setu


class SetuListener {
    init {
        subscribeEvent()
    }

    /**
     * 这玩意有bug mirai的锅
     */
    private fun subscribeEvent() {
        Log.i("消息监听${this.javaClass.name}已创建")
        bot.subscribeAlways<GroupMessageEvent> {
            val msg = message.content
            when {
                msg == "!setu" -> {
                    try {
                        val setu = Setu()
                        val info = setu.get()
                        if (info == null) {
                            reply(Image("${setu.getPicLocation()}"))
                            reply(setu.getFormat())
                        } else {
                            reply("总之就是失败了")
                        }
                    } catch (e: Exception) {
                        Log.e(e)
                    }
                }
                msg.startsWith("!setAPIKey ") -> {
                    BotConfig.apiKey = msg.replace("!setAPIKey ", "")
                    reply("设置成功")
                }
                msg.startsWith("!setR18 ") -> {
                    BotConfig.r18 = msg.replace("!setR18 ", "")
                    reply("设置成功")
                }
                msg == "test" -> {
                    reply("测试通过")
                }
                msg == "help" -> {
                    reply("指令：\n" +
                            "1、!setu   随机涩图\n" +
                            "2、!setAPIKey (your apikey）  设置apikey\n" +
                            "3、!setR18 (0/1/2)    设置r18，0=off 1=on 2=both\n" +
                            "备注：获取loliconAPI请参考https://api.lolicon.app/#/setu?id=apikey")
                }
            }
        }
    }
}