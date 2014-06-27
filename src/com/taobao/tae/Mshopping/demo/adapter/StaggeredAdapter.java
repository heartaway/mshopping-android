package com.taobao.tae.Mshopping.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.image.ImageFetcher;
import com.taobao.tae.Mshopping.demo.model.TaobaoItemBasicInformation;
import com.taobao.tae.Mshopping.demo.view.RefreshableListView;
import com.taobao.tae.Mshopping.demo.widget.ScaleImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xinyuan on 14/6/25.
 */
public class StaggeredAdapter extends BaseAdapter {
    private Context context;
    private LinkedList<TaobaoItemBasicInformation> taobaoItemBasicInformations;
    private RefreshableListView refreshableListView;
    private ImageFetcher imageFetcher;

    public StaggeredAdapter(Context context, RefreshableListView refreshableListView, ImageFetcher imageFetcher) {
        this.context = context;
        taobaoItemBasicInformations = new LinkedList<TaobaoItemBasicInformation>();
        this.refreshableListView = refreshableListView;
        this.imageFetcher = imageFetcher;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        TaobaoItemBasicInformation taobaoItemBasicInformation = taobaoItemBasicInformations.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
            convertView = layoutInflator.inflate(R.layout.item_list, null);
            holder = new ViewHolder();
            holder.imageView = (ScaleImageView) convertView.findViewById(R.id.item_pic);
            holder.titleView = (TextView) convertView.findViewById(R.id.item_title);
            holder.priceView = (TextView) convertView.findViewById(R.id.item_price);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.imageView.setImageWidth(taobaoItemBasicInformation.getWidth());
        holder.imageView.setImageHeight(taobaoItemBasicInformation.getHeight());
        if (taobaoItemBasicInformation.getTitle().length() > 20) {
            holder.titleView.setText(taobaoItemBasicInformation.getTitle().substring(0, 20).concat("..."));
        } else {
            holder.titleView.setText(taobaoItemBasicInformation.getTitle());
        }
        holder.priceView.setText(taobaoItemBasicInformation.getPrice().toString());
        imageFetcher.loadImage(taobaoItemBasicInformation.getPicUrl(), holder.imageView);
        return convertView;
    }

    class ViewHolder {
        ScaleImageView imageView;
        TextView titleView;
        TextView priceView;
    }

    @Override
    public int getCount() {
        return taobaoItemBasicInformations.size();
    }

    @Override
    public Object getItem(int arg0) {
        return taobaoItemBasicInformations.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    public void addItemLast(List<TaobaoItemBasicInformation> datas) {
        taobaoItemBasicInformations.addAll(datas);
    }

    /**
     * 倒序添加，最后推送的商品展示在最上面
     *
     * @param datas
     */
    public void addItemTop(List<TaobaoItemBasicInformation> datas) {
        for (int i = datas.size() - 1; i >= 0; i--) {
            taobaoItemBasicInformations.addFirst(datas.get(i));
        }
    }
}
