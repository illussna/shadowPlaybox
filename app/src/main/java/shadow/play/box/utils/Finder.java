package shadow.play.box.utils;

import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Finder {
	private Finder(){};

	public static void findView(Object object, View rootView) {
		try {
			find(object, rootView);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void find(final Object object, final View rootView)throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		Resources res = rootView.getResources();
		Field[] fields = object.getClass().getDeclaredFields();
		for(Field field : fields) {
			FindView findView = field.getAnnotation(FindView.class);
			if(findView == null )continue;
			int identifier = findView.id();
			final String onClick = findView.onClick();
			final String onLongClick = findView.onLongClick();
			final String onTouchEvent = findView.onTouchEvent();
			final String onKeyListener = findView.onKeyListener();

			if(identifier == 0) {
				String identifierString = field.getName();
				identifier = res.getIdentifier(identifierString, "id", rootView.getContext().getPackageName());
			}
			View view = rootView.findViewById(identifier);
			if(onClick.length() > 0) setOnClick(object, view, onClick);
			if(onLongClick.length() > 0) setOnLongClick(object, view, onLongClick);
			if(onTouchEvent.length() > 0) setOnTouch(object, view, onTouchEvent);
			if(onKeyListener.length() > 0) setOnKeyEvent(object, view, onKeyListener);
			//if(view.getClass() == field.getType()) {
				field.setAccessible(true);
				field.set(object, view);
			//}
		}
	}

	private static void setOnClick(final Object object, View view, String listener) throws NoSuchMethodException {
		final Method method = object.getClass().getDeclaredMethod(listener, new Class<?>[]{View.class});
		view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					method.invoke(object, v);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void setOnLongClick(final Object object, View view, String listener) throws NoSuchMethodException {
		final Method method = object.getClass().getDeclaredMethod(listener, new Class<?>[]{View.class});
		view.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				Boolean b = false;
				try {
					b = (Boolean) method.invoke(object, v);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return b;
			}
		});
	}

	private static void setOnTouch(final Object object, View view, String listener) throws NoSuchMethodException {
		final Method method = object.getClass().getDeclaredMethod(listener, new Class<?>[]{View.class, MotionEvent.class});
		view.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				Boolean b = false;
				try {
					b = (Boolean) method.invoke(object, v, event);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return b;
			}
		});
	}

	private static void setOnKeyEvent(final Object object, View view, String listener) throws NoSuchMethodException {
		final Method method = object.getClass().getDeclaredMethod(listener, new Class<?>[]{View.class, int.class, KeyEvent.class});
		view.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Boolean b = false;
				try {
					b = (Boolean) method.invoke(object, v, keyCode, event);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return b;
			}
		});
	}
}