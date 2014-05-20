package com.netease.json;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class Log {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-mm-dd HH:mm:ss");
	private static final String format = "%-20s %-8s\t- %s\n";

	private static final int CURRENT_LEVEL = Level.DEBUG;

	public static void d(Object o) {
		if (Level.DEBUG >= CURRENT_LEVEL)
			System.out.printf(format, sdf.format(new Date()), "DEBUG",
					o.toString());
	}

	public static void i(Object o) {
		if (Level.INFO >= CURRENT_LEVEL)
			System.out.printf(format, sdf.format(new Date()), "INFO",
					o.toString());
	}

	public static void w(Object o) {
		if (Level.WARN >= CURRENT_LEVEL)
			System.out.printf(format, sdf.format(new Date()), "WARN",
					o.toString());

	}

	public static void e(Object o) {
		if (Level.ERROR >= CURRENT_LEVEL)
			System.err.printf(format, sdf.format(new Date()), "ERROR",
					o.toString());
	}

	public static interface Level {
		int DEBUG = 0;
		int INFO = 1;
		int WARN = 2;
		int ERROR = 3;
		int CLOSE = 5;
	}
	
	public static void main(String[] args) {
		Log.d("hello");
		Log.i("hello");
		Log.w("hello");
		Log.e("hello");
	}
}