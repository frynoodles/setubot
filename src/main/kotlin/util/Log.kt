package util

import java.text.SimpleDateFormat

/**
 * 日志工具类
 */
object Log {
    //dump
    private val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val time: Long
        get() {
            return System.currentTimeMillis()
        }

    fun d(msg: String) {
        println("${fmt.format(time)} / DUMP:$msg")
    }

    //消息
    fun i(msg: String) {
        println("${fmt.format(time)} / INFO:$msg")
    }

    //异常
    fun e(e: Exception, msg: String = "") {
        println("${fmt.format(time)} / ERROR:$msg\n${e.printStackTrace()}")
    }

    fun e(e: Error, msg: String = "") {
        println("${fmt.format(time)} / ERROR:$msg\n${e.printStackTrace()}")
    }

    //throw
    fun t(thr: Throwable, msg: String = "") {
        println("${fmt.format(time)} / THROW:$msg\n${thr.printStackTrace()}")
    }
}