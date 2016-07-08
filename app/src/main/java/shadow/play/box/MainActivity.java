package shadow.play.box;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import shadow.play.box.utils.FindView;
import shadow.play.box.utils.Finder;

public class MainActivity extends BaseActivity {

    @FindView(id = R.id.list)
    private ListView list;
    @FindView(id = R.id.info_btn, onClick = "onClickInfo")
    private ImageButton info_btn;
    private ArrayList<Content> contents;
    private ContentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contents = new ArrayList<>();
        Content content = new Content();
        content.setImageurl("@drawable/icon_01");
        content.setName("해와 달이된 오누이");
        content.setFileName("1.html");
        content.setBgName("1.mp3");
        contents.add(content);

        content = new Content();
        content.setImageurl("@drawable/icon_02");
        content.setName("빨간망토");
        content.setFileName("2.html");
        content.setBgName("1.mp3");
        contents.add(content);

        content = new Content();
        content.setImageurl("@drawable/icon_03");
        content.setName("개구리 왕자");
        content.setFileName("3.html");
        content.setBgName("1.mp3");
        contents.add(content);

        adapter = new ContentListAdapter();
        list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void onClickHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    public void onClickInfo(View view) {
        startActivity(new Intent(this, InfomationActivity.class));
        finish();
    }
    private class ContentListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contents.size();
        }

        @Override
        public Object getItem(int position) {
            return contents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Content content = (Content) getItem(position);
            Holder holder;
            if(convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.main_list_item, null);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }

            holder.text.setText(content.getName());
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
            holder.run_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, Const.textSize);
            holder.run_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    intent.putExtra("content", content);
                    startActivity(intent);
                    finish();
                }
            });
            return convertView;
        }

        private class Holder {
            @FindView(id = R.id.text)
            private TextView text;
            @FindView(id = R.id.run_btn)
            private TextView run_btn;

            Holder(View view) {
                Finder.findView(this, view);
            }
        }
    }
}
