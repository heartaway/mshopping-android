package com.taobao.tae.Mshopping.demo.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import com.taobao.tae.Mshopping.demo.R;


public class HomeActivity extends TabActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private RadioGroup mTabButtonGroup;
    private TabHost mTabHost;

    public static final String TAB_MAIN = "MAIN_ACTIVITY";
    public static final String TAB_MY = "MY_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_activity);
        findViewById();
        initView();
    }

    private void findViewById() {
        mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
    }

    private void initView() {

        mTabHost = getTabHost();

        Intent i_main = new Intent(this, IndexActivity.class);
        Intent i_my = new Intent(this, MyActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN).setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MY).setIndicator(TAB_MY).setContent(i_my));
        final RadioButton mainRadioButton = (RadioButton)findViewById(R.id.home_tab_main);
        final RadioButton myRadioButton = (RadioButton)findViewById(R.id.home_tab_my);

        int fromActivity = getIntent().getIntExtra("ACTIVITY_NAME_KEY", 0);
        if (R.string.title_activity_qq_login == fromActivity || R.string.title_activity_taobao_login == fromActivity || R.string.title_activity_weibo_login == fromActivity) {
            myRadioButton.setSelected(true);
            mTabHost.setCurrentTabByTag(TAB_MY);
        } else {
            mainRadioButton.setSelected(true);
            mTabHost.setCurrentTabByTag(TAB_MAIN);
        }

        mTabButtonGroup
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.home_tab_main:
                                myRadioButton.setSelected(false);
                                mTabHost.setCurrentTabByTag(TAB_MAIN);
                                break;

                            case R.id.home_tab_my:
                                mainRadioButton.setSelected(false);
                                mTabHost.setCurrentTabByTag(TAB_MY);
                                break;
                            default:
                                break;
                        }
                    }
                });

    }

}
