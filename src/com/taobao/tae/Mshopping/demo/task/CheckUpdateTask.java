package com.taobao.tae.Mshopping.demo.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.fegment.ItemsListFragment;
import com.taobao.tae.Mshopping.demo.model.TaobaoItemBasicInfo;
import com.taobao.tae.Mshopping.demo.update.UpdateManager;
import com.taobao.tae.Mshopping.demo.update.VersionInfo;
import com.taobao.tae.Mshopping.demo.update.XMLParserUtil;
import com.taobao.tae.Mshopping.demo.util.Helper;
import com.taobao.tae.Mshopping.demo.util.SecurityKey;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 检查客户端是否为最新包信息
 * Created by xinyuan on 14/7/25.
 */
public class CheckUpdateTask extends AsyncTask<String, Integer, VersionInfo> {

    private UpdateManager updateManager;

    public CheckUpdateTask(UpdateManager updateManager) {
        this.updateManager = updateManager;
    }

    @Override
    protected VersionInfo doInBackground(String... params) {
        try {
            String respone = Helper.getStringFromUrl(Constants.APK_UPDATE_VERSION);
            VersionInfo versionInfo = XMLParserUtil.getUpdateInfo(new ByteArrayInputStream(respone.getBytes()));
            return versionInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(VersionInfo versionInfo) {
        super.onPostExecute(versionInfo);
        updateManager.setVersionInfo(versionInfo);
        updateManager.check();
    }

}
