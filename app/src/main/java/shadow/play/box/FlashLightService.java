package shadow.play.box;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import shadow.play.box.utils.Finder;

public class FlashLightService extends Service {

    protected Handler handler;
    private Camera camera;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        camera = Camera.open();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flashLightOn();
            }
        }, 1500);
    }

    public void flashLightOn() {
        camera = Camera.open();
        if (camera != null) {
            Camera.Parameters params = camera.getParameters();
            camera.setParameters(params);
        }
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
    }

    public void flashLightOff() {
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(p);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void onDestroy() {
        flashLightOff();
        super.onDestroy();
    }
}
