package util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import util.Utils.Config_Path
import java.io.File

object ConfigManager {
    const val CFG_QQ_ACCOUNT = "account"               //T:Long        D:0L
    const val CFG_QQ_PASSWORD = "password"             //T:String      D:""
    const val CFG_OWNER = "owner"                      //T:Long        D:0L
    const val CFG_DEF_MUTE_TIME = "defMuteTime"        //T:Int         D:3600

    private val cfgFile = File(Config_Path)
    private lateinit var config: JSONObject

    init {
        configInit()
    }

    //初始化
    private fun configInit() {
        if (!cfgFile.exists()) {
            cfgFile.createNewFile()
            save(JSONObject().toJSONString())
        }
        load()
        fun input(msg: String): String {
            while (true) {
                Log.i("请输入$msg:")
                val str = readLine().toString()
                if (str.isEmpty()) {
                    Log.i("输入的内容为空 请重新输入")
                    continue
                }
                Log.i("输入的${msg}为:$str 是否输入正确?y/n")
                while (true) {
                    val confirm = readLine().toString().toLowerCase()
                    if (confirm == "y" || confirm == "yes") {
                        return str
                    } else if (confirm == "n" || confirm == "no") {
                        Log.i("请重新输入")
                        break
                    } else {
                        Log.i("输入有误 请输入yes/no")
                    }
                }
            }
        }
        if (getLong(CFG_QQ_ACCOUNT) == 0L) {
            Log.i("未检测到bot账号请先输入账号密码")
            while (true) {
                try {
                    val str = input("账号")
                    if (str.length in 6..10) {
                        putLong(CFG_QQ_ACCOUNT, str.toLong())
                        break
                    } else {
                        Log.i("输入的账号不正确!请重新输入!")
                    }
                } catch (e: Exception) {
                    Log.i("出错了!请重新输入!")
                }
            }
            val password = input("密码")
            putString(CFG_QQ_PASSWORD, password)
        }
        if (getLong(CFG_OWNER) == 0L) {
            Log.i("还没有设置Bot所有者 赶紧设置一个吧!")
            while (true) {
                try {
                    val str = input("所有者账号")
                    if (str.length in 6..10) {
                        putLong(CFG_OWNER, str.toLong())
                        Log.i("设置成功! 已将$str 设置为所有者")
                        break
                    } else {
                        Log.i("输入的账号不正确!请重新输入!")
                    }
                } catch (e: Exception) {
                    Log.i("出错了!请重新输入!")
                }
            }
        }
        if (getInt(CFG_DEF_MUTE_TIME) == 0) putInt(CFG_DEF_MUTE_TIME, 3600)
        save()
    }


    /**
     * @param key 键值 要拿什么
     * @param defValue 指如果不存在 默认返回什么
     * @return 返回config中的key值
     */

    fun getIntOrDef(key: String, defValue: Int): Int {
        return try {
            config.getInteger(key)
        } catch (e: Exception) {
            defValue
        }
    }

    fun getLongOrDef(key: String, defValue: Long): Long {
        return try {
            config.getLong(key)
        } catch (e: Exception) {
            defValue
        }
    }


    fun getStringOrDef(key: String, defValue: String): String {
        return try {
            config.getString(key)
        } catch (e: Exception) {
            defValue
        }
    }

    fun getInt(key: String): Int {
        return getIntOrDef(key, 0)
    }

    fun getLong(key: String): Long {
        return getLongOrDef(key, 0L)
    }

    fun getString(key: String): String {
        return getStringOrDef(key, "")
    }

    /**
     * @param key 键值 要设置什么
     * @param value 要设置的值
     */


    fun putInt(key: String, value: Int) {
        config[key] = value
        save()
    }

    fun putLong(key: String, value: Long) {
        config[key] = value
        save()
    }

    fun putString(key: String, value: String) {
        config[key] = value
        save()
    }

    private fun load() {
        try {
            config = JSON.parseObject(cfgFile.readText())
        } catch (e: Exception) {
            Log.e(e)
        }
    }


    private fun save() {
        save(config.toJSONString())
    }

    private fun save(text: String) {
        try {
            cfgFile.writeText(text)
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}