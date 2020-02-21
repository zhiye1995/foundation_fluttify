package me.yohom.foundation_fluttify

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import io.flutter.plugin.common.MethodChannel

fun PlatformService(method: String, args: Map<String, Any>, methodResult: MethodChannel.Result, activity: Activity?) {
    when (method) {
        "PlatformService::enableLog" -> {
            enableLog = args["enable"] as Boolean
            methodResult.success("success")
        }
        "PlatformService::getandroid_app_Application" -> {
            methodResult.success(activity?.application?.apply { HEAP[hashCode()] = this }?.hashCode())
        }
        "PlatformService::getandroid_app_Activity" -> {
            methodResult.success(activity?.apply { HEAP[hashCode()] = this }?.hashCode())
        }
        "PlatformService::createandroid_os_Bundle" -> {
            methodResult.success(Bundle().apply { HEAP[hashCode()] = this }.hashCode())
        }
        "PlatformService::createandroid_graphics_Bitmap" -> {
            val bitmapBytes = args["bitmapBytes"] as ByteArray
            val bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.size)

            HEAP[bitmap.hashCode()] = bitmap

            methodResult.success(bitmap.hashCode())
        }
        "PlatformService::createandroid_graphics_Point" -> {
            val x = args["x"] as Int
            val y = args["y"] as Int
            val point = Point(x, y)

            HEAP[point.hashCode()] = point

            methodResult.success(point.hashCode())
        }
        "PlatformService::release" -> {
            if (enableLog)
                Log.d("PlatformFactory", "释放对象: ${HEAP[args["refId"] as Int]?.javaClass}@${args["refId"]}")

            HEAP.remove(args["refId"] as Int)

            methodResult.success("success")

            // print current HEAP
            if (enableLog) Log.d("PlatformFactory", "HEAP: $HEAP")
        }
        "PlatformService::clearHeap" -> {
            if (enableLog)
                Log.d("PlatformFactory", "CLEAR HEAP")

            HEAP.clear()
            methodResult.success("success")

            // print current HEAP
            if (enableLog)
                Log.d("PlatformFactory", "HEAP: $HEAP")
        }
        "PlatformService::pushStack" -> {
            val name = args["name"] as String
            val refId = args["refId"] as Int

            Log.d("PlatformFactory", "PUSH OBJECT: ${HEAP[refId]?.javaClass}@${refId}")

            HEAP[refId]?.run { STACK[name] = this }

            methodResult.success("success")

            // print current STACK
            if (enableLog) Log.d("PlatformFactory", "STACK: $STACK")
        }
        "PlatformService::pushStackJsonable" -> {
            val name = args["name"] as String
            val data = args["data"]

            Log.d("PlatformFactory", "压入jsonable: ${data?.javaClass}@${data}")

            STACK[name] = data!!

            methodResult.success("success")

            // 打印当前STACK
            if (enableLog) Log.d("PlatformFactory", "STACK: $STACK")
        }
        "PlatformService::clearStack" -> {
            STACK.clear()

            methodResult.success("success")

            // print current STACK
            if (enableLog) Log.d("PlatformFactory", "STACK: $STACK")
        }
    }
}