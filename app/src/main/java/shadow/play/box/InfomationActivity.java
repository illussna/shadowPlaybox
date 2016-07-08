package shadow.play.box;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import shadow.play.box.utils.FindView;
import shadow.play.box.utils.SharedUtil;

public class InfomationActivity extends BaseActivity {
    @FindView(id = R.id.home_btn, onClick = "onClickHome")
    private ImageButton home_btn;
    @FindView(id = R.id.info_btn_1, onClick = "onClickInfo")
    private TextView info_btn_1;
    @FindView(id = R.id.info_btn_2, onClick = "onClickInfo")
    private TextView info_btn_2;
    @FindView(id = R.id.info_btn_3, onClick = "onClickInfo")
    private TextView info_btn_3;
    @FindView(id = R.id.info_bottom)
    private TextView info_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info);

        init();
    }

    private void init() {
        info_btn_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
        info_btn_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
        info_btn_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
        info_bottom.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
    }

    public void onClickHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void onClickInfo(View view) {
        Intent intent = null;
        if(view.getId() == R.id.info_btn_1) {
            intent = new Intent(this, ImageInfoActivity.class);
            intent.putExtra("flag", 1);
        }else if(view.getId() == R.id.info_btn_2) {
            Toast.makeText(InfomationActivity.this, "서비스 준비 중입니다.", Toast.LENGTH_SHORT).show();
            return;
        }else {
            intent = new Intent(this, ImageInfoActivity.class);
            intent.putExtra("flag", 0);
        }
        startActivity(intent);
    }
}
