package util

import net.mamoe.mirai.Bot
import java.io.File

lateinit var bot: Bot

//指定配置文件目录 打包时可更改
const val deviceInfo =
    "{\"display\":[111,112,114,49,46,49,55,48,54,50,51,46,48,50,54],\"product\":[110,111,118,97,32,52],\"device\":[115,97,103,105,116],\"board\":[109,115,109,56,57,57,56],\"brand\":[72,117,97,119,101,105],\"model\":[110,111,118,97,32,52],\"bootloader\":[117,110,107,110,111,119],\"fingerprint\":[],\"bootId\":[],\"procVersion\":[],\"baseBand\":[],\"version\":{},\"simInfo\":[],\"osType\":[97,110,100,114,111,105,100],\"macAddress\":[],\"wifiBSSID\":[],\"wifiSSID\":[99,108,101,97,114,108,111,118,101,55,55,55],\"imsiMd5\":[],\"imei\":\"\",\"apn\":[]}"

object Utils {

    const val Package_Path = "A_Setubot_Data"
    const val FILE_PATH = "A_Setubot_Data/"
    const val DeviceInfo_Path = "${FILE_PATH}myDeviceInfo.json"
    const val BotConfig_Path = "${FILE_PATH}bot_config.json"
    const val Config_Path = "${FILE_PATH}config.json"
    const val Pic_Path = "${FILE_PATH}pic"


    fun UtilsInit() {
        val dataDir = File(Package_Path)
        val deviceInfoFile = File(DeviceInfo_Path)
        val botConfigFile = File(BotConfig_Path)
        if (!dataDir.exists()) {
            dataDir.mkdir()
            println("\n+++++++\n资源文件的位置->${dataDir.absolutePath}\n+++++++\n")
        }
        if (!deviceInfoFile.exists()) {
            deviceInfoFile.createNewFile()
            deviceInfoFile.writeText(deviceInfo)
        }
        if (!botConfigFile.exists()) {
            botConfigFile.exists()
            botConfigFile.writeText("{\"apikey\":\"12345668\",\"r18\":\"0\"}")
        }
    }
}
