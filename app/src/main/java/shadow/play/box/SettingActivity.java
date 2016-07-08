package shadow.play.box;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import shadow.play.box.utils.FindView;
import shadow.play.box.utils.SharedUtil;

public class SettingActivity extends BaseActivity {
    protected final int MY_PERMISSION_REQUEST_STORAGE = 100;

    @FindView(id = R.id.title)
    private TextView title;
    @FindView(id = R.id.home_btn, onClick = "onClickHome")
    private ImageButton home_btn;
    @FindView(id = R.id.info_btn, onClick = "onClickInfo")
    private ImageButton info_btn;
    @FindView(id = R.id.seekbar)
    private SeekBar seekbar;
    @FindView(id = R.id.sound_on_nff)
    private Switch sound_on_nff;
    @FindView(id = R.id.flashlight_on_nff)
    private Switch flashlight_on_nff;
    @FindView(id = R.id.text_play)
    private TextView text_play;
    @FindView(id = R.id.light_text)
    private TextView light_text;
    @FindView(id = R.id.play_start_btn, onClick = "onClickStart")
    private TextView play_start_btn;
    @FindView(id = R.id.text1)
    private TextView text1;

    private Content content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        content = (Content) getIntent().getSerializableExtra("content");

        int progress = SharedUtil.getSharedIntValue(this, Const.APP_NAME, Const.TEXT_PROGRESS);
        seekbar.setProgress(progress);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    Const.textSize = 15;
                } else if (progress == 1) {
                    Const.textSize = 19;
                } else {
                    Const.textSize = 24;
                }
                SharedUtil.putShared(getApplicationContext(), Const.APP_NAME, Const.SHARED_TEXT_SIZE, Const.textSize);
//                SharedUtil.putShared(getApplicationContext(), Const.APP_NAME, Const.SHARED_TEXT_VIEW, Const.textView);
                SharedUtil.putShared(getApplicationContext(), Const.APP_NAME, Const.TEXT_PROGRESS, progress);

                init();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        boolean b1 = SharedUtil.getSharedBooleanValue(this, Const.APP_NAME, Const.SHARED_PLAY);
        boolean b2 = SharedUtil.getSharedBooleanValue(this, Const.APP_NAME, Const.SHARED_LIGHT);

        sound_on_nff.setChecked(b1);
        flashlight_on_nff.setChecked(b2);

        sound_on_nff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedUtil.putShared(getApplicationContext(), Const.APP_NAME, Const.SHARED_PLAY, isChecked);
            }
        });

        flashlight_on_nff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedUtil.putShared(getApplicationContext(), Const.APP_NAME, Const.SHARED_LIGHT, isChecked);
            }
        });

        init();
    }

    private void init() {
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textView);
        text1.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textView);
        text_play.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textView);
        light_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textView);
        play_start_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textView);

        title.setText(content.getName());
    }

    public void onClickHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    public void onClickInfo(View view) {
        startActivity(new Intent(this, InfomationActivity.class));
        finish();
    }
    public void onClickStart(View view) {
        Intent intent = new Intent(getApplicationContext(), ContentsActivity.class);
        intent.putExtra("content", content);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        onClickHome(null);
    }
}
