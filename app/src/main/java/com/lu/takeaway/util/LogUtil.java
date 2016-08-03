package com.lu.takeaway.util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil{
    /**
     * 日志打印开关
     */
    public static boolean ENABLE_DEBUG = true;

    static final boolean DEBUG = true;
    static String className;
    static String methodName;
    static int lineNumber;

    public static Boolean MYLOG_WRITE_TO_FILE = false;
    private static String SDCARD_DIR = "/sdcard/";
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 0;
    private static String MYLOGFILEName = "musiclog.txt";
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");
    public static void w(String tag, Object msg) {
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) {
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(tag, text, 'w');
    }

    public static void e(String tag, String text) {
        log(tag, text, 'e');
    }

    public static void d(String tag, String text) {
        log(tag, text, 'd');
    }

    public static void i(String tag, String text) {
        log(tag, text, 'i');
    }

    public static void v(String tag, String text) {
        log(tag, text, 'v');
    }

    /**
     * 日志打印实现
     *
     * @param tag
     * @param msg
     * @param level
     */
    private static void log(String tag, String msg, char level) {
        // log print switcher
        if (ENABLE_DEBUG) {
            if ('e' == level) {
                Log.e(tag, msg);
            } else if ('w' == level) {
                Log.w(tag, msg);
            } else if ('d' == level) {
                Log.d(tag, msg);
            } else if ('i' == level) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }
        }
        if (MYLOG_WRITE_TO_FILE) {
            writeLogtoFile(String.valueOf(level), tag, msg);
        }
    }
    public static void writeLogtoFile(String mylogtype, String tag, String text) {
        Date nowtime = new Date();
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype + "    " + tag + "    " + text;
        File file = new File(SDCARD_DIR, MYLOGFILEName);
        Log.d("MyLog", "getAbsolutePath:" + file.getAbsolutePath());
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            Log.d("MyLog", needWriteMessage);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            Log.d("MyLog", e.getMessage());
        }
    }


    public static void d(Class<?> class1, String msg) {
        if (DEBUG) {
            log(class1.getSimpleName(), msg, 'd');
        }

    }
    public static void d(Object o, String msg) {
        if (DEBUG) {
            log(o.getClass().getSimpleName(), msg, 'd');
        }

    }

    public static void e(Class<?> class1, String msg) {
        if (DEBUG) {
            Log.e(class1.getSimpleName(), msg);
        }
    }

    public static void i(Class<?> class1, String msg) {
        if (DEBUG) {
            Log.i(class1.getSimpleName(), msg);
        }
    }

    public static void v(Class<?> class1, String msg) {
        if (DEBUG) {
            Log.v(class1.getSimpleName(), msg);
        }
    }

    public static void w(Class<?> class1, String msg) {
        if (DEBUG) {
            Log.w(class1.getSimpleName(), msg);
        }
    }




    public static boolean isDebuggable() {
        return DEBUG;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }
}
