
package com.taobao.tae.Mshopping.demo.util;

import android.graphics.*;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import com.taobao.tae.Mshopping.demo.R;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteImageHelper {

    //sd卡图片保存路径
    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/download_test/";

    public void loadImage(final ImageView imageView, final String urlString) {
        final String subUrl = urlString.replaceAll("[^\\w]", "");
        //imageView.setImageResource(R.drawable.image_indicator);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                imageView.setImageBitmap((Bitmap) message.obj);
            }
        };

        Runnable runnable = new Runnable() {
            public void run() {
                Bitmap bitmap = null;
                if (isFileExist(subUrl)) {
                    bitmap = getDiskBitmap(subUrl);
                    //Log.v("本地读取", subUrl);
                } else {// 不存在就下载
                    try {
                        InputStream is = download(urlString);
                        bitmap = BitmapFactory.decodeStream(is);
                        if (bitmap != null) {
                            // 保存远程图片
                            saveFile(bitmap, subUrl);
                        }
                    } catch (Exception e) {
                        bitmap = BitmapFactory.decodeResource(imageView.getResources(), R.drawable.image_fail);
                    }
                }
                Message msg = handler.obtainMessage(1, bitmap);
                handler.sendMessage(msg);
            }
        };
        new Thread(runnable).start();
    }

    /**
     * 加载 图片为圆形
     * @param imageView
     * @param urlString
     */
    public void loadRoundImage(final ImageView imageView, final String urlString) {
        // 正则为文件名
        final String subUrl = urlString.replaceAll("[^\\w]", "");
        //imageView.setImageResource(R.drawable.image_indicator);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                imageView.setImageBitmap((Bitmap) message.obj);
            }
        };

        Runnable runnable = new Runnable() {
            public void run() {
                Bitmap bitmap = null;
                if (isFileExist(subUrl)) {
                    bitmap = getDiskRoundBitmap(subUrl);
                    //Log.v("本地读取", subUrl);
                } else {// 不存在就下载
                    try {
                        InputStream is = download(urlString);
                        bitmap = BitmapFactory.decodeStream(is);
                        if (bitmap != null) {
                            // 保存远程图片
                            saveFile(bitmap, subUrl);
                        }
                    } catch (Exception e) {
                        bitmap = BitmapFactory.decodeResource(imageView.getResources(), R.drawable.image_fail);
                    }
                }
                Message msg = handler.obtainMessage(1, bitmap);
                handler.sendMessage(msg);
            }
        };
        new Thread(runnable).start();
    }

    private InputStream download(String urlString)
            throws MalformedURLException, IOException {
        InputStream inputStream = (InputStream) new URL(urlString).getContent();
        return inputStream;
    }

    public void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        if (!myCaptureFile.exists()) {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } else {
            //Log.v("msg", fileName + "存在不下载");
        }
    }

    // 检查文件是否存在
    public boolean isFileExist(String FileName) {
        File myFile = new File(ALBUM_PATH + FileName);
        if (!myFile.exists()) {
            return false;
        }
        return true;
    }

    // 读取图片
    private Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(ALBUM_PATH + pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(ALBUM_PATH + pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    private Bitmap getDiskRoundBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(ALBUM_PATH + pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(ALBUM_PATH + pathString);
                bitmap = toRoundBitmap(bitmap);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }


    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }
}
