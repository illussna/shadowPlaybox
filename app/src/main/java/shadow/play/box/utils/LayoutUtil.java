package shadow.play.box.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import shadow.play.box.Const;

public class LayoutUtil {

	private LayoutUtil() {}

	/**
	 * X축 비율
	 * @param context
	 * @return
	 */
	public static float getXValueRatio(Context context) {
		if(DeviceUtils.isTablet(context))
			return DeviceUtils.getDeviceWidth(context) / 800.0f;
		else
			return DeviceUtils.getDeviceWidth(context) / 800.0f;
	}

	/**
	 * Y축
	 * @param context
	 * @return
	 */
	public static float getYValueRatio(Context context) {
		return DeviceUtils.getDeviceHeight(context) / 1280.0f;
	}

	/**
	 * 텍스트 넓이
	 * @param context
	 * @param txt
	 * @return
	 */
	public static final float getTextWidth(Context context, String txt) {
		Rect bounds = new Rect();
		TextView textView = new TextView(context);
		Paint textPaint = textView.getPaint();
		textPaint.getTextBounds(txt, 0, txt.length(), bounds);
		return bounds.width();
	}

	/**
	 * 텍스트 높이
	 * @param context
	 * @param txt
	 * @return
	 */
	public static final float getTextHeight(Context context, String txt) {
		Rect bounds = new Rect();
		TextView textView = new TextView(context);
		Paint textPaint = textView.getPaint();
		textPaint.getTextBounds(txt, 0, txt.length(), bounds);
		return bounds.height();
	}
	/**
	 * 뷰 리사이징
	 * @param v
	 * @param context
	 */
	public static void recursiveViewReSize(View v, Context context) {
		if (v instanceof ViewGroup) {
			ViewGroup group = (ViewGroup)v;
			int count = group.getChildCount();
			for (int i = 0; i < count; i++) {
				recursiveViewReSize(group.getChildAt(i), context);
			}
		}
		ViewGroup.LayoutParams view = v.getLayoutParams();
		if(view instanceof RelativeLayout.LayoutParams) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view;

			layoutParams.leftMargin = Math.round(layoutParams.leftMargin * getXValueRatio(context));
			layoutParams.topMargin = Math.round(layoutParams.topMargin * getYValueRatio(context));
			layoutParams.rightMargin = Math.round(layoutParams.rightMargin * getXValueRatio(context));
			layoutParams.bottomMargin = Math.round(layoutParams.bottomMargin * getYValueRatio(context));

			if(layoutParams.width != layoutParams.height) {
				if(layoutParams.width > 0) layoutParams.width = Math.round(layoutParams.width * getXValueRatio(context));
				if(layoutParams.height > 0) layoutParams.height = Math.round(layoutParams.height * getYValueRatio(context));
			}else {
				if(layoutParams.width > 0 && 
						layoutParams.height > 0) {
					layoutParams.width = Math.round(layoutParams.width * getXValueRatio(context));
					layoutParams.height = layoutParams.width;
				}

			}
		}
	}

	public static void keyboardHide(View v, final Activity context) {
		TimerTask myTask = new TimerTask(){
			public void run(){
				if(context == null) return;
				InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
			}
		};

		Timer timer = new Timer();
		timer.schedule(myTask, 100);
	}

	public static interface OnKeyboardHide {
		public void onKeyboardHide();
	}

	public static Bitmap getScreenshot(View v) {
		v.getResources().getDisplayMetrics().density = 0.6f;
		Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
		Canvas c = new Canvas(b);
		v.draw(c);
		return b;
	}
}
