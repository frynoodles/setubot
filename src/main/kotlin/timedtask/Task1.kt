package timedtask

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import util.Log
import util.Utils.Pic_Path
import util.bot
import java.io.File


//
// Created by frynoodles on 2021/4/4 17:47
//
class Task1 {
    init {
        InitTask()
    }

    private fun InitTask() {
        bot.launch {
            while (true) {
                try {
                    val picDir = File(Pic_Path)
                    if (picDir.exists()) {
                        val files = picDir.listFiles()
                        for (file in files) {
                            println("开始删除图片${file.absoluteFile}")
                            file.delete()
                        }
                        Log.i("定时任务1：清理文件夹${Pic_Path}已完成")
                    }
                } catch (e: Exception) {

                }
                delay(1000 * 60 * 24)
            }
        }
    }
}