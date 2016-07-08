package shadow.play.box;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import shadow.play.box.utils.FindView;
import shadow.play.box.utils.Finder;
import shadow.play.box.utils.SharedUtil;

public class ContentsActivity extends BaseActivity {
    private MediaPlayer mp;
    @FindView(id = R.id.title)
    private TextView title;
    @FindView(id = R.id.arrow_left_btn, onClick = "onClickLeft")
    private TextView arrow_left_btn;
    @FindView(id = R.id.arrow_right_btn, onClick = "onClickRight")
    private TextView arrow_right_btn;
    @FindView(id = R.id.home_btn, onClick = "onClickHome")
    private ImageButton home_btn;
    @FindView(id = R.id.info_btn, onClick = "onClickInfo")
    private ImageButton info_btn;
    @FindView(id = R.id.text1)
    private TextView text1;
    @FindView(id = R.id.text2)
    private TextView text2;
    @FindView(id = R.id.pager)
    private ViewPager pager;

    private Content content;
    private ContentPagerAdapter pagerAdapter;
    private ArrayList<String> contents;
    private Camera camera;

    private int currentPosition;
    private int textLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);

        content = (Content) getIntent().getSerializableExtra("content");
        contents = new ArrayList<String>();

        if(Const.textSize == 15) {
            textLine = 15;
        }else if(Const.textSize == 19) {
            textLine = 8;
        }else {
            textLine = 3;
        }


        init();

        boolean flashLightOn = SharedUtil.getSharedBooleanValue(getApplicationContext(),
                Const.APP_NAME, Const.SHARED_LIGHT);
        if(flashLightOn) {
            flashLightOn();
        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void init() {
        pagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        contents = new ArrayList<String>();
        pager.setAdapter(pagerAdapter);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
        title.setText(content.getName());
        arrow_left_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
        arrow_right_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
        boolean sound = SharedUtil.getSharedBooleanValue(this, Const.APP_NAME, Const.SHARED_PLAY);
        if(sound) {
            try {
                AssetFileDescriptor afd1 = getAssets().openFd("1.mp3");
                AssetFileDescriptor afd2 = getAssets().openFd("2.mp3");
                AssetFileDescriptor afd3 = getAssets().openFd("3.mp3");
                mp = new MediaPlayer();
                if(title.getText().toString().equals("해와 달이된 오누이")) {
                    mp.setDataSource(afd1.getFileDescriptor(), afd1.getStartOffset(), afd1.getLength());
                    mp.prepare();
                    afd1.close();
                }
                else if(title.getText().toString().equals("빨간망토")){
                    mp.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());
                    mp.prepare();
                    afd2.close();
                }
                else {
                    mp.setDataSource(afd3.getFileDescriptor(), afd3.getStartOffset(), afd3.getLength());
                    mp.prepare();
                    afd3.close();
                }


                mp.start();
            } catch (IOException e) {
            }
        }
        getText();
    }

    public void flashLightOn() {
        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        camera.setParameters(params);
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

    public void onClickLeft(View view) {
        if (currentPosition == 0) {
            Toast.makeText(ContentsActivity.this, R.string.first_contents, Toast.LENGTH_SHORT).show();
            return;
        }
        currentPosition--;
        pager.setCurrentItem(currentPosition);
    }

    public void onClickRight(View view) {
        if (currentPosition == contents.size() - 1) {
            startActivity(new Intent(this, InfomationActivity.class));
            finish();
            return;
        }
        currentPosition++;
        pager.setCurrentItem(currentPosition);
    }

    private String getText() {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            AssetManager assetMgr = getAssets();
            InputStream is = assetMgr.open(content.getFileName());
            br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            int count = 0;
            while ((str = br.readLine()) != null) {
                sb.append(str.trim() + "\n");
                count++;
                if(count % textLine == 0) {
                    contents.add(sb.toString());
                    sb.setLength(0);
                    count = 0;
                }
            }
            if(sb.length() > 0) {
                contents.add(sb.toString());
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            pagerAdapter.notifyDataSetChanged();
        }
        return sb.toString();
    }

    public void onClickHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void onClickInfo(View view) {
        startActivity(new Intent(this, InfomationActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {
        onClickHome(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(camera != null) {
            flashLightOff();
        }
    }

    private class ContentPagerAdapter extends FragmentPagerAdapter {

        private HashMap<Integer, Fragment> fragments;

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new HashMap<Integer, Fragment>();
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments.containsKey(position)) {
                return fragments.get(position);
            } else {
                Fragment fragment = new ContentFragment(contents.get(position));
                fragments.put(position, fragment);
                return fragment;
            }
        }

        @Override
        public int getCount() {
            return contents.size();
        }
    }

    @SuppressLint("ValidFragment")
    private static class ContentFragment extends Fragment {

        @FindView(id = R.id.text)
        private TextView text;

        private String content;

        public ContentFragment(String text) {
            super();
            this.content = text;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.pager_item, null);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            String[] co = {"Microsoft", "Apple", "Google", "Dell", "HP"};
            String[] str = {"I", "love", "you"};
            Finder.findView(this, getView());

            text.setText(Html.fromHtml(content));
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textView);

        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mp != null) {
            mp.stop();
            mp.release();
        }
    }

    void getText2()
    {
        //progressDialog.show();

//        temp.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            int lineHeight = temp.getLineHeight();
//                            int viewHeight = (int) (temp.getHeight()
//                                    - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
//                            int lineCount = viewHeight / lineHeight;
//
//                            Layout layout = temp.getLayout();
//                            int end = 0;
//                            if (layout.getLineCount() <= lineCount) {
//                                end = layout.getLineEnd(layout.getLineCount());
//                            } else {
//                                end = layout.getLineEnd(lineCount - 1);
//                            }
//                            String displayed = html.substring(0, end);
//                            Log.e(Const.APP_NAME, displayed);
//                            contents.add(displayed);
//
//                            html = html.substring(displayed.length());
//                            temp.setText(html);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//                            temp.setVisibility(View.GONE);
//                            pagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
//                            pager.setAdapter(pagerAdapter);
//                        }
//                    }
//                }, 200);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        temp.setText(html);
    }
}
