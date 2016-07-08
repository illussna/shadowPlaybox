package shadow.play.box;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import shadow.play.box.utils.Finder;

public class BaseActivity extends AppCompatActivity {

    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Finder.findView(this, getWindow().getDecorView());
    }
}
