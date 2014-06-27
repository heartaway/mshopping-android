
package com.taobao.tae.Mshopping.demo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/download_test/";
	public void loadImage(final ImageView imageView, final String urlString) {
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
            
}
