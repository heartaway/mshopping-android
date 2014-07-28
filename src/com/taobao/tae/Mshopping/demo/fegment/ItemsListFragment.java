package com.taobao.tae.Mshopping.demo.fegment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.activity.ItemDetailActivity;
import com.taobao.tae.Mshopping.demo.adapter.StaggeredAdapter;
import com.taobao.tae.Mshopping.demo.image.ImageFetcher;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.model.TaobaoItemBasicInfo;
import com.taobao.tae.Mshopping.demo.task.ItemContentTask;
import com.taobao.tae.Mshopping.demo.view.RefreshableListView;
import com.taobao.tae.Mshopping.demo.view.pinterest.PinterestAdapterView;

import java.util.Date;

public class ItemsListFragment extends Fragment implements RefreshableListView.IRefreshableListViewListener {

    public ImageFetcher imageFetcher;
    public RefreshableListView refreshableListView = null;
    public StaggeredAdapter staggeredAdapter = null;
    //客户端接收到的 最后一次商品推送时间
    public Date lastPushedItemsTime = null;
    private Context context;
    private View view;
    private int currentPage = 0;
    private ItemContentTask task = null;
    private FragmentManager fragmentManager;
    private Boolean onPause = false;


    private String categoryId;
    private String defaultCategoryId = Constants.NEW_CATEGORY;

    public static ItemsListFragment newInstance(String categoryId) {
        ItemsListFragment newFragment = new ItemsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        Bundle args = getArguments();
        categoryId = args != null ? args.getString("categoryId") : defaultCategoryId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        view = inflater.inflate(R.layout.pull_to_refresh_items, container, false);
        refreshableListView = (RefreshableListView) view.findViewById(R.id.items);
        refreshableListView.setPullLoadEnable(true);
        refreshableListView.setXListViewListener(this);
        imageFetcher = new ImageFetcher(context, 240);
        imageFetcher.setLoadingImage(R.drawable.empty_photo);

        staggeredAdapter = new StaggeredAdapter(context, refreshableListView, imageFetcher);
        task = new ItemContentTask(context, Constants.VIEW_MORE_ITEMS_ACTION, this);

        itemOnClick();
        return view;
    }

    /**
     * 用户点击商品图片，进入商品详情页
     */
    private void itemOnClick() {
        refreshableListView.setOnItemClickListener(new PinterestAdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PinterestAdapterView<?> parent, View view,
                                    int position, long id) {
                RefreshableListView gv = (RefreshableListView) parent;
                TaobaoItemBasicInfo taobaoItemBasicInfo = (TaobaoItemBasicInfo) gv.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("itemId", taobaoItemBasicInfo.getItemId().toString());
                bundle.putInt("ACTIVITY_NAME_KEY", R.string.title_activity_index);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }

        });
    }

    /**
     * 添加商品
     *
     * @param pageindex
     * @param type
     */
    private void addItemToContainer(int pageindex, int type) {
        if (task != null && task.getStatus() != AsyncTask.Status.RUNNING) {
            String url = "";
            if (type == Constants.PULL_REFRESH_ACTION) {
                if (lastPushedItemsTime == null) {
                    url = Constants.SERVER_DOMAIN.concat("/api/itemlist/new/" + categoryId + "/0");
                } else {
                    url = Constants.SERVER_DOMAIN.concat("/api/itemlist/new/" + categoryId + "/" + lastPushedItemsTime.getTime());
                }
            }
            if (type == Constants.VIEW_MORE_ITEMS_ACTION) {
                url = Constants.SERVER_DOMAIN.concat("/api/itemlist/more/" + categoryId + "/" + pageindex);
            }
            if (type == Constants.OTHER_ACTION) {
                return;
            }
            ItemContentTask task = new ItemContentTask(context, type, this);
            task.execute(url);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        imageFetcher.setExitTasksEarly(false);
        if (!onPause) {
            refreshableListView.setAdapter(staggeredAdapter);
            addItemToContainer(currentPage, Constants.PULL_REFRESH_ACTION);
        }
        onPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当下拉刷新时，重置页面，去最新的20条记录。
     */
    @Override
    public void onRefresh() {
        addItemToContainer(0, Constants.PULL_REFRESH_ACTION);
    }

    @Override
    public void onLoadMore() {
        addItemToContainer(++currentPage, Constants.VIEW_MORE_ITEMS_ACTION);
    }


}
