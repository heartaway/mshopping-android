package com.taobao.tae.Mshopping.demo.activity.imgupload;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.google.gson.reflect.TypeToken;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.activity.BaseActivity;
import com.taobao.tae.Mshopping.demo.config.AppConfig;
import com.taobao.tae.Mshopping.demo.model.BaseApiResult;
import com.taobao.tae.Mshopping.demo.util.BitmapUtil;
import com.taobao.tae.Mshopping.demo.util.GsonUtil;
import com.taobao.tae.Mshopping.demo.util.ImageUploadUtil;
import com.taobao.tae.Mshopping.demo.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ImageGridActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";

    // ArrayList<Entity> dataList;//鐢ㄦ潵瑁呰浇鏁版嵁婧愮殑鍒楄〃
    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
    AlbumHelper helper;
//    Button bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(ImageGridActivity.this, "最多选择9张图片", 400).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_upload_grid);

        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        dataList = (List<ImageItem>) getIntent().getSerializableExtra(
                EXTRA_IMAGE_LIST);

        initView();
//        bt = (Button) findViewById(R.id.bt);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.images_back_layout_btn);
        rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
                mHandler);
        gridView.setAdapter(adapter);

        final TextView textView = (TextView) findViewById(R.id.upload_text_btn);
        textView.setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        ArrayList<String> list = new ArrayList<String>();
                        Collection<String> c = adapter.map.values();
                        Iterator<String> it = c.iterator();
                        for (; it.hasNext(); ) {
                            list.add(it.next());
                        }


                        List<String> paths = new ArrayList<String>();
                        for (int i = 0; i < list.size(); i++) {
                            paths.add(list.get(i));
                        }
                        final String tmp = System.currentTimeMillis() + "";
                        try {
                            List<String> tmpPaths = new ArrayList<String>();
                            final List<File> files = new ArrayList<File>();
                            for (String path : paths) {
                                String fileName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
                                Bitmap bitmap = BitmapUtil.revitionImageSize(path);
                                String tmpPath = BitmapUtil.saveBitmap(bitmap, fileName, tmp);
                                tmpPaths.add(tmpPath);
                                files.add(new File(tmpPath));
                            }
                            final Handler handler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what) {
                                        case 0:
                                            toast("上传图片到jae workstation成功");
                                            finish();
                                            break;
                                        case 1:
                                            BaseApiResult<Map<String, String>> result = (BaseApiResult<Map<String, String>>) msg.obj;
                                            toast(result.getMsg());
                                        default:
                                            break;
                                    }
                                }
                            };
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String rRusult = ImageUploadUtil.upload(files, "files");
                                        LogUtil.i(rRusult);
                                        if (rRusult == null) {
                                            handler.sendMessage(handler.obtainMessage(1, new BaseApiResult(-200, "sys err")));
                                        } else {
                                            BaseApiResult<Map<String, String>> baseApiResult = GsonUtil.g.fromJson(rRusult, new TypeToken<BaseApiResult<Map<String, String>>>() {
                                            }.getType());
                                            if (baseApiResult.isSuccess()) {
                                                handler.sendMessage(handler.obtainMessage(0));
                                            } else {
                                                handler.sendMessage(handler.obtainMessage(1, baseApiResult));
                                            }
                                        }
                                    } catch (Throwable e) {
                                        LogUtil.e(e.getMessage(), e);
                                        handler.sendMessage(handler.obtainMessage(1, new BaseApiResult(-200, "sys err")));
                                    }
                                    try {
                                        BitmapUtil.rmDir(tmp);
                                    } catch (IOException e) {
                                        Log.e("com.taobao", e.getMessage(), e);
                                    }
                                }
                            };
                            Thread thread = new Thread(runnable);
                            thread.start();


                        } catch (IOException e) {
                            Log.e("com.taobao", e.getMessage(), e);
                            toast("上传文件错误");
                        }


                        Log.i("com.taobao", GsonUtil.toJson(Bimp.drr));
//                finish();
                    }

                }

        );
        adapter.setTextCallback(
                new ImageGridAdapter.TextCallback()

                {
                    public void onListen(int count) {
//                bt.setText("完成" + "(" + count + ")");
                        if (count > 0)
                            textView.setText("上传" + "(" + count + ")");
                        else
                            textView.setText("上传");
                    }
                }

        );


        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // if(dataList.get(position).isSelected()){
                        // dataList.get(position).setSelected(false);
                        // }else{
                        // dataList.get(position).setSelected(true);
                        // }
                        adapter.notifyDataSetChanged();
                    }

                }

        );

    }
}
