package com.taobao.tae.Mshopping.demo.update;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.task.CheckUpdateTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p></p>
 * User: <a href="mailto:xinyuan.ymm@alibaba-inc.com">心远</a>
 * Date: 14/7/24
 * Time: 下午4:45
 */
public class UpdateManager {
    // 上下文对象
    private Context mContext;
    //更新版本信息对象
    private VersionInfo versionInfo = null;
    // 下载进度条
    private ProgressBar progressBar;
    // 是否终止下载
    private boolean isInterceptDownload = false;
    //进度条显示数值
    private int progress = 0;


    /**
     * 参数为Context(上下文activity)的构造函数
     *
     * @param context
     */
    public UpdateManager(Context context) {
        this.mContext = context;
    }

    public void checkUpdate() {
        getRemoteVersionInfo();
    }

    public void check() {
        if (versionInfo != null) {
            try {
                PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
                int versionCode = pi.versionCode;
                if (versionCode < versionInfo.getVersionCode()) {
                    showUpdateDialog();
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从服务端获取版本信息
     *
     * @return
     */
    private void getRemoteVersionInfo() {
        CheckUpdateTask checkUpdateTask = new CheckUpdateTask(this);
        checkUpdateTask.execute();
    }

    /**
     * 提示更新对话框
     */
    private void showUpdateDialog() {
        UpdateDialog.Builder builder = new UpdateDialog.Builder(mContext);
        builder.setTitle("版本更新");
        builder.setMessage(versionInfo.getDisplayMessage());
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 弹出下载框
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 弹出下载框
     */
    private void showDownloadDialog() {
        UpdateDialog.Builder builder = new UpdateDialog.Builder(mContext);
        builder.setTitle("版本更新中...");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_prgress, null);
        progressBar = (ProgressBar) v.findViewById(R.id.mshopping_update_progress);
        builder.setContentView(v);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //终止下载
                isInterceptDownload = true;
            }
        });
        builder.create().show();
        //下载apk
        downloadApk();
    }

    /**
     * 下载apk
     */
    private void downloadApk() {
        Thread downLoadThread = new Thread(downApkRunnable);
        downLoadThread.start();
    }

    /**
     * 从服务器下载新版apk的线程
     */
    private Runnable downApkRunnable = new Runnable() {
        @Override
        public void run() {
            if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                //如果没有SD卡
                UpdateDialog.Builder builder = new UpdateDialog.Builder(mContext);
                builder.setTitle("提示");
                builder.setMessage("当前设备无SD卡，数据无法下载");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return;
            } else {
                Boolean isNotGetApk = true;
                int retry = 3;
                while (isNotGetApk && retry > 0) {
                    try {
                        //服务器上新版apk地址
                        URL url = new URL(versionInfo.getDownloadURL());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.connect();
                        int length = conn.getContentLength();
                        InputStream is = conn.getInputStream();
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/");
                        if (!file.exists()) {
                            //如果文件夹不存在,则创建
                            file.mkdir();
                        }
                        //下载服务器中新版本软件（写文件）
                        String apkFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + versionInfo.getApkName();
                        File ApkFile = new File(apkFile);
                        FileOutputStream fos = new FileOutputStream(ApkFile);
                        int count = 0;
                        byte buf[] = new byte[1024];
                        isNotGetApk = false;
                        do {
                            int numRead = is.read(buf);
                            count += numRead;
                            //更新进度条
                            progress = (int) (((float) count / length) * 100);
                            handler.sendEmptyMessage(1);
                            if (numRead <= 0) {
                                handler.sendEmptyMessage(0);
                                break;
                            }
                            fos.write(buf, 0, numRead);
                        } while (!isInterceptDownload);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        retry = retry -1;
                    }
                }
            }
        }
    };

    /**
     * 声明一个handler来跟进进度条
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setProgress(progress);
                    break;
                case 0:
                    progressBar.setVisibility(View.INVISIBLE);
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + versionInfo.getApkName());
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        // 安装，如果签名不一致，可能出现程序未安装提示
        i.setDataAndType(Uri.fromFile(new File(apkfile.getAbsolutePath())), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        this.versionInfo = versionInfo;
    }
}
