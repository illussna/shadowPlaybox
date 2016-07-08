package shadow.play.box.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceUtils {

	private static PowerManager.WakeLock screenWakeLock;

	public static void acquireWakeLock(Context context) {
		PowerManager pm = (PowerManager)context.getSystemService
				(Context.POWER_SERVICE);
		screenWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				, context.getClass().getName());

		if (screenWakeLock != null) {
			screenWakeLock.acquire();
		}
	}

	public static void releaseWakeLock() {
		if (screenWakeLock != null) {
			screenWakeLock.release();
			screenWakeLock = null;
		}
	}

	public static boolean isTablet (Context context) {
		int portrait_width_pixel=Math.min(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels);
		int dots_per_virtual_inch=context.getResources().getDisplayMetrics().densityDpi;
		float virutal_width_inch=portrait_width_pixel/dots_per_virtual_inch;
		if (virutal_width_inch <= 2) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 디바이스 진동
	 * @param context
	 * @param time
	 */
	public static void vibrate(Context context, long time) {
		Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		mVibrator.vibrate(time);
	}
	
	/**
	 * 화면 자동회전 설정
	 * @param context
	 * @param enabled
	 */
	public static void screenRotation(Context context, boolean enabled) {
		try{
			if  (Settings.System.getInt(context.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION, 0) == 1){
				Settings.System.putInt(context.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION, 0);
			}
			else{
				Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 블루투스 활성화
	 * @param context
	 * @param enabled
	 */
	/*public static void bluetoothEnable(Context context, boolean enabled) {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

		if(enabled) {
			adapter.enable();
		}else {
			adapter.disable();
		}
	}*/

	/**
	 * 와이파이 활성화
	 * @param context
	 * @param enabled
	 */
	/*public static void setDeviceWiFiEnabled(Context context, boolean enabled) {
		WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		mWifiManager.setWifiEnabled(enabled);
	}*/

	/**
	 * 디바이스의 전체 매모리 반환
	 * @param context
	 * @return
	 */
	public static long getTotalMemory(Context context) {
		String memInfoPath = "/proc/meminfo";
		String readTemp = "";
		String memTotal = "";
		long memory = 0;
		try {
			FileReader fr = new FileReader(memInfoPath);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			while ((readTemp = localBufferedReader.readLine()) != null) {
				if (readTemp.contains("MemTotal")) {
					String[] total = readTemp.split(":");
					memTotal = total[1].trim();
				}
			}
			localBufferedReader.close();
			String[] memKb = memTotal.split(" ");
			memTotal = memKb[0].trim();
			memory = Long.parseLong(memTotal);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return memory;
	}

	/**
	 * 디바이스의 사용가능한 메모리 반환
	 * @param context
	 * @return
	 */
	public static long getAvailMemory(Context context) {
		ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		am.getMemoryInfo(outInfo);
		long avaliMem = outInfo.availMem;
		return avaliMem / 1024;
	}

	/**
	 * 메모리값 -> 문자열로 변환
	 * 1024kb 이상일경우 G표시
	 * @param size
	 * @return
	 */
	public static String formatMemorySize(double size) {
		String str = null;

		if(size >= 1000) {
			str = "MB";
			size /= 1024;
			if(size >= 1000) {
				str = "GB";
				size /= 1024;
				if(size >= 1000) {
					str = "TB";
					size /= 1024;
				}
			}
		}

		StringBuilder sb = new StringBuilder(String.format("%.1f", size));
		sb.append(str);
		return sb.toString();
	}

	/**
	 * sd카드가 마운트 되어 있는지.
	 * @return
	 */
	public static boolean externalMemoryAvilable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 사용가능한 내부 디스크용량
	 * @return
	 */
	public static long getAvailableInternalStorageSize() {
		File path = Environment.getDataDirectory();
		StatFs statFs = new StatFs(path.getPath());
		long blodSize = statFs.getBlockSize();
		long availableBlocks = statFs.getAvailableBlocks();
		return availableBlocks * blodSize;
	}

	/**
	 * 내부디스크 총용량
	 * @return
	 */
	public static long getTotalInternalStorageSize() {
		File path = Environment.getDataDirectory();
		StatFs statFs = new StatFs(path.getPath());
		long blodSize = statFs.getBlockSize();
		long totalBlocks = statFs.getBlockCount();
		return totalBlocks * blodSize;
	}

	/**
	 * 사용가능한 외부디스크 용량
	 * @return
	 */
	public static long getAvailableExternalStorageSize() {
		if(externalMemoryAvilable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs statFs = new StatFs(path.getPath());
			long blodSize = statFs.getBlockSize();
			long availableBlocks = statFs.getAvailableBlocks();
			return availableBlocks * blodSize;
		}else {
			return 0;
		}
	}

	/**
	 * 외부 디스크 총용량
	 * @return
	 */
	public static long getTotalExternalStorageSize() {
		if(externalMemoryAvilable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs statFs = new StatFs(path.getPath());
			long blodSize = statFs.getBlockSize();
			long totalBlocks = statFs.getBlockCount();
			return totalBlocks * blodSize;
		}else {
			return 0;
		}
	}

	public static String formatStorageSize(double size) {
		String str = null;

		if(size >= 1000) {
			str = "KB";
			size /= 1024;
			if(size >= 1000) {
				str = "MB";
				size /= 1024;
				if(size >= 1000) {
					str = "GB";
					size /= 1024;
					if(size >= 1000) {
						str = "TB";
						size /= 1024;
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder(String.format("%.1f", size));
		sb.append(str);
		return sb.toString();
	}

	/**
	 * 디바이스 ID조회
	 * @param context
	 * @return
	 */
	public static String getPhoneID(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
		return mTelephonyMgr.getDeviceId();
	}

	/**
	 * 디바이스 높이
	 * @param context
	 * @return
	 */
	public static int getDeviceHeight(Context context) {
		try {
			WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics metrics = new DisplayMetrics();
			manager.getDefaultDisplay().getMetrics(metrics);
			return metrics.heightPixels;
		}catch(Exception e){}
		return 0;
	}

	/**
	 * 디바이스 폭
	 * @param context
	 * @return
	 */
	public static int getDeviceWidth(Context context) {
		try {
			WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics metrics = new DisplayMetrics();
			manager.getDefaultDisplay().getMetrics(metrics);
			return metrics.widthPixels;
		}catch(Exception e){}
		return 0;
	}


	/**
	 * 휴대폰 번호
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
		String phone = mTelephonyMgr.getLine1Number();
		if(phone != null && phone.contains("+82")) {
			phone = "0" + phone.substring(3);
		}
		return phone;
	}
}
