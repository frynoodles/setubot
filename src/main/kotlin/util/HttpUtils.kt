package util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * 处理http请求的工具类
 */
object HttpUtils {
    private const val API = "https://api.vtbs.moe/v1/info"
    private val client = OkHttpClient.Builder()
        .callTimeout(180, TimeUnit.SECONDS)
        .connectTimeout(180, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .build()

    /**
     * @param request 传入请求
     * @return 返回响应
     */
    fun execNewCall(request: Request): Response? {
        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful)
                response
            else null
        } catch (e: Exception) {
            Log.e(e)
            null
        }
    }

    /**
     * @return jsonArray? 返回已近转为JSONArray的API数据
     */
    fun getAPIInfo(): JSONArray {
        var jsonArray = JSONArray(0)
        val request = Request.Builder()
            .url(API)
            .build()
        val response = execNewCall(request)
        try {
            val data = JSON.parseArray(response?.body?.string())
            jsonArray = data
        } catch (e: Exception) {
            Log.e(e)
        }
        return jsonArray
    }
}