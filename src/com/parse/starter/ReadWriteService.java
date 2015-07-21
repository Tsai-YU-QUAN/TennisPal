package com.parse.starter;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

//繼承android.app.Service
public class ReadWriteService extends Service {   // 隨時會收到有人寄好友信或是新的聊天訊息
    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        handler.postDelayed(showTime, 1000);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }
    
    private Runnable showTime = new Runnable() {
        @Override
		public void run() {
            //log目前時間
        	//System.out.println("toString())"+new Date().toString());
            //Log.i("time:", new Date().toString());
            handler.postDelayed(this, 1000);
        }
    };
}
