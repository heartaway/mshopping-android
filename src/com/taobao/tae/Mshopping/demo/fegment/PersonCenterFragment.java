package com.taobao.tae.Mshopping.demo.fegment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.taobao.tae.Mshopping.demo.MshoppingApplication;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.activity.SettingActivity;
import com.taobao.tae.Mshopping.demo.login.auth.TaobaoUser;
import com.taobao.tae.Mshopping.demo.util.RemoteImageHelper;

/**
 * Created by xinyuan on 14/7/3.
 */
public class PersonCenterFragment extends Fragment {

    private View view;
    private TaobaoUser taobaoUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.my_home, null);
        TextView headerTextView = (TextView) getActivity().findViewById(R.id.personal_header_txt);
        headerTextView.setText("我的");
        ImageView avatarImageView = (ImageView) view.findViewById(R.id.tb_user_avatar_img);
        taobaoUser = ((MshoppingApplication)getActivity().getApplication()).getTaobaoUser();
        RemoteImageHelper remoteImageHelper = new RemoteImageHelper();
        remoteImageHelper.loadRoundImage(avatarImageView, taobaoUser.getAvatar());
        TextView nickTextView = (TextView) view.findViewById(R.id.tb_user_nick_txt);
        nickTextView.setText(taobaoUser.getNick());
        setButtonListen();
        return view;
    }

    /**
     * 设置 按钮的监听器
     */
    public void setButtonListen() {
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.personal_center_setting_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("ACTIVITY_NAME_KEY",R.string.title_activity_personal);
                startActivity(intent);
            }
        });
    }

}
