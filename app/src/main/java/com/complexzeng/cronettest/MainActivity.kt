package com.complexzeng.cronettest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.complexzeng.cronettest.data.ImageRepository
import org.chromium.net.CronetEngine
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val tv = findViewById<Button>(R.id.button)
    tv.setOnClickListener {
      val cronet = cronetEngine()
//      val outputFile: File
//      try {
//        outputFile = File.createTempFile(
//          "cronet", ".log",
//          cacheDir
//        )
//        cronet.startNetLogToFile(outputFile.toString(), false)
//        Log.i("cplx", "log file: ${outputFile.absolutePath}")
//      } catch (e: IOException) {
//        Log.e("cplx", e.toString())
//      }
      val requestBuilder = cronet.newUrlRequestBuilder(
        ImageRepository.getImage(0),
        object : ReadToMemoryCronetCallback() {
          override fun onSucceeded(
            request: UrlRequest?,
            info: UrlResponseInfo?,
            bodyBytes: ByteArray?,
            latencyNanos: Long
          ) {
            val ms = latencyNanos /1000000
            Log.i("cplx", "success ${bodyBytes?.size} cost ${ms}ms")
          }
        }, getExecutor()
      )
      requestBuilder.build().start()
      Log.i("cplx", "start")
    }
  }

  private fun cronetEngine(): CronetEngine {
    return (application as CronetApplication).cronetEngine
  }

  private fun getExecutor(): Executor {
    return (application as CronetApplication).cronetCallbackExecutorService
  }

  override fun onPause() {
    super.onPause()
    cronetEngine().stopNetLog()
  }
}
