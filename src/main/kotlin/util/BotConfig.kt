package util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import util.Utils.BotConfig_Path
import java.io.*
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets

//
// Created by frynoodles on 2021/3/30 23:43
//
object BotConfig {
    //获取bot配置文件
    private val botConfig: String
        private get() {
            val sb = StringBuilder()
            try {
                val reader: Reader = InputStreamReader(
                    FileInputStream(BotConfig_Path),
                    StandardCharsets.UTF_8
                )
                var ch: Int
                while (reader.read().also { ch = it } != -1) {
                    sb.append(ch.toChar())
                }
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return sb.toString()
        }

    //写入配置文件
    private fun setBotConfig(type: String, value: String) {
        //读取json配置文件
        val config = JSON.parseObject(botConfig)
        try {
            val osw = OutputStreamWriter(
                FileOutputStream(BotConfig_Path), StandardCharsets.UTF_8
            )
            when (type) {
                "setApiKey" -> {
                    config["apikey"] = value
                }
                "setR18" -> {
                    config["r18"] = value
                }
            }
            //执行写入 刷新 关闭文件
            osw.write(config.toString())
            osw.flush()
            osw.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //设置api key  api.lolicon.app
    //获取api key
    var apiKey: String
        get() {
            val jsonObject = JSONObject.parseObject(botConfig)
            return jsonObject.getString("apikey")
        }
        set(apiKey) {
            setBotConfig("setApiKey", apiKey)
        }

    //设置R-18 0=off 1=on 2=both
    //获取R-18
    var r18: String
        get() {
            val jsonObject = JSONObject.parseObject(botConfig)
            return jsonObject.getString("r18")
        }
        set(i) {
            setBotConfig("setR18", i)
        }
}