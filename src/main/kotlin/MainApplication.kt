import listener.InitListener
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.join
import timedtask.InitTask
import util.ConfigManager
import util.ConfigManager.CFG_QQ_ACCOUNT
import util.ConfigManager.CFG_QQ_PASSWORD
import util.Utils
import util.Utils.DeviceInfo_Path
import util.bot

suspend fun main() {
    Utils.UtilsInit()
    val qqId = ConfigManager.getLong(CFG_QQ_ACCOUNT)
    val password = ConfigManager.getString(CFG_QQ_PASSWORD)
    val miraiBot = Bot(qqId, password) {
        fileBasedDeviceInfo(DeviceInfo_Path)
        inheritCoroutineContext()
    }
    miraiBot.alsoLogin()
    botInit(miraiBot)
    miraiBot.join()
}

private fun botInit(miraiBot: Bot) {
    bot = miraiBot
    InitListener()
    InitTask()
}