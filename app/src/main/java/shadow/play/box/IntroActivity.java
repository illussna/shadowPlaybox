package shadow.play.box;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;

import java.io.IOException;

import shadow.play.box.utils.SharedUtil;

public class IntroActivity extends BaseActivity {
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        Const.textSize = SharedUtil.getSharedIntValue(this, Const.APP_NAME, Const.SHARED_TEXT_SIZE);
        if(Const.textSize == 0) Const.textSize = 15;


    }

    @Override
    public void onAttachedToWindow() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 5000);
    }


}
