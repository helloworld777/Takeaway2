package com.lu.takeaway.util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by asus on 2016/8/3.
 */
public class IOUtil {

    public static void writeStrToFile(String msg,String filePath) {
        File file = new File(filePath);
        Log.d("MyLog", "getAbsolutePath:" + file.getAbsolutePath());
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(msg);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            Log.d("MyLog", e.getMessage());
        }
    }
}
