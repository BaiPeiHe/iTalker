package com.baihe.italker.push;

import android.widget.TextView;

import com.baihe.italker.common.App.Activity;

import butterknife.BindView;

public class MainActivity extends Activity {
    @BindView(R.id.txt_test)
    TextView mTestView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTestView.setText("Test Hello");
    }
}
