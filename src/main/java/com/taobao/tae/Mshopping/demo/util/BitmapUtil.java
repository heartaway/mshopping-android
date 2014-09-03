package com.taobao.tae.Mshopping.demo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * mulou.zzy
 * taedemo
 */
public class BitmapUtil {


    public static String tempPathPrefix = Environment.getExternalStorageDirectory()
            + "/formats/tmp/";


    public static String saveBitmap(Bitmap bm, String picName, String tmpPathSuffix) {
        String dir = tempPathPrefix + tmpPathSuffix;
        Log.w("", String.format("保存图片, dir : %s, picName : %s", dir, picName));
        try {
            if (!isFileExist(tempPathPrefix)) {
                createSDDir(tempPathPrefix);
            }
            if (!isFileExist(dir)) {
                File tempf = createSDDir(dir);
            }
            File f = new File(dir, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
            } finally {
                if (out != null)
                    out.close();
            }
            Log.w("", String.format("已经保存 dir : %s, picName : %s", dir, picName));
            return f.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isFileExist(String s) {
        return new File(s).exists();
    }


    public static void rmDir(String dirSuffix) throws IOException {
        FileUtils.deleteDirectory(new File(tempPathPrefix + dirSuffix));
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }


    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }
}
