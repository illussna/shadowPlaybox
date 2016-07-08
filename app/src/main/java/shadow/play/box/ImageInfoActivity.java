package shadow.play.box;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import shadow.play.box.utils.FindView;

public class ImageInfoActivity extends BaseActivity {

    @FindView(id = R.id.image)
    private ImageView image;
    @FindView(id = R.id.image2)
    private ImageView image2;
    @FindView(id = R.id.image3)
    private ImageView image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro_2);

        if(getIntent().getIntExtra("flag", 0) == 0) {
            image.setImageResource(R.drawable.info_1);
            image2.setImageResource(R.drawable.info_2);
            image3.setImageResource(R.drawable.info_3);
        }else {
            image.setImageResource(R.drawable.tip_1);
  //          image2.setImageResource(R.drawable.tip_2);
            image3.setVisibility(View.GONE);
        }
    }
}
