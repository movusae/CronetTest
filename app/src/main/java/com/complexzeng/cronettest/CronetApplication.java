package com.complexzeng.cronettest;

import android.app.Application;
import android.content.Context;

import org.chromium.net.CronetEngine;
import org.chromium.net.DnsOptions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CronetApplication extends Application {

  // We recommend that each application uses a single, global CronetEngine. This allows Cronet
  // to maximize performance. This can either be achieved using a global static . In this example,
  // we initialize it in an Application class to manage lifecycle of the network log.
  private CronetEngine cronetEngine;

  private ExecutorService cronetCallbackExecutorService;

  @Override
  public void onCreate() {
    super.onCreate();
    cronetEngine = createDefaultCronetEngine(this);
    cronetCallbackExecutorService = Executors.newFixedThreadPool(4);
  }

  public CronetEngine getCronetEngine() {
    return cronetEngine;
  }

  public ExecutorService getCronetCallbackExecutorService() {
    return cronetCallbackExecutorService;
  }

  private static CronetEngine createDefaultCronetEngine(Context context) {

    return new CronetEngine.Builder(context)
            .setStoragePath(context.getCacheDir().getAbsolutePath())
            .enableHttpCache(CronetEngine.Builder.HTTP_CACHE_DISK, 500 * 1024 * 1024)
            .enableBrotli(true)
            .enableHttp2(true)
            .enableQuic(true)
            .build();
  }
}
