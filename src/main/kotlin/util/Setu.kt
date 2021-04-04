package util


import com.alibaba.fastjson.JSONObject
import okhttp3.OkHttpClient
import okhttp3.Request
import util.BotConfig.apiKey
import util.BotConfig.r18
import util.Utils.FILE_PATH
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.util.*


//
// Created by frynoodles on 2021/4/4 19:00
//
class Setu {

    private val CODE_ERROR = -1
    private val CODE_SUCCESS = 0
    private val CODE_API_ERROR = 401
    private val CODE_REFUSE = 403
    private val CODE_KEYWORD_PIC_NOT_FOUND = 404
    private val CODE_LIMITED_CALL = 429

    private val client = OkHttpClient()
    private var url: String? = null
    private var p = 0
    private var uid = 0
    private var pid = 0
    private var title: String? = null
    private var author: String? = null
    private var location: String? = null
    private var jObject: JSONObject? = null

    fun get(): String? {
        val request: Request
        request = Request.Builder()
            .url("https://api.lolicon.app/setu/?apikey=${apiKey}&r18=${r18}")
            .build()
        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                jObject = JSONObject.parseObject(response.body?.string())
                when (jObject!!.getIntValue("code")) {
                    CODE_SUCCESS -> {
                        set()
                        downloadPic()
                    }
                    CODE_ERROR -> return "内部错误，请向 i@loli.best 反馈"
                    CODE_API_ERROR -> return "APIKEY 不存在或被封禁"
                    CODE_REFUSE -> return "由于不规范的操作而被拒绝调用"
                    CODE_KEYWORD_PIC_NOT_FOUND -> return "找不到符合关键字的色图"
                    CODE_LIMITED_CALL -> return "达到调用额度限制"
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun set() {
        val datas = jObject!!.getJSONArray("data")
        val data = datas[0] as JSONObject
        p = data.getInteger("p")
        uid = data.getInteger("uid")
        pid = data.getInteger("pid")
        url = data.getString("url")
        title = data.getString("title")
        author = data.getString("author")
    }

    @Throws(IOException::class)
    private fun downloadPic() {
        val picDir = File(FILE_PATH + "pic").absoluteFile
        if (!picDir.exists()) {
            picDir.mkdir()
        }
        val pic = File(picDir, getShortName(url))

        if (!pic.exists()) {
            println(pic.absoluteFile)
            pic.createNewFile()
        }
        val request: Request = Request.Builder()
            .url(url.orEmpty())
            .get()
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val data = Objects.requireNonNull(response.body)!!.bytes()
            val fout = FileOutputStream(pic)
            fout.write(data)
            fout.flush()
            fout.close()
            location = pic.absolutePath
        } else {
            location = null
            throw IOException(response.code.toString() + ":" + response.message)
        }
    }

    fun getShortName(url: String?): String? {
        if (url != null) {
            return url.substring(url.length-10,url.length)
        }else {
            return null
        }
    }

    fun getPicLocation(): String? {
        return location
    }

    fun getFormat(): String? {
        return """
           标题:$title
           Pid:$pid
           Page:$p
           Uid:$uid
           画师:$author
           """.trimIndent()
    }
}