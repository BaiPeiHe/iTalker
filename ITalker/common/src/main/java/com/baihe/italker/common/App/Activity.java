package com.baihe.italker.common.App;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by baihe on 2017/10/17.
 */

public abstract class Activity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 在界面未初始化前调用初始化窗口
        initWindows();

        if(initArgs(getIntent().getExtras())){

            getContentLayoutId();
            initWidget();
            initData();
        }
        else{
            finish();
        }

    }

    /**
     * 初始化窗口
     */
    protected void initWindows(){

    }

    /**
     * 初始化相关参数
     * @param bundle 参数 Bundle
     * @return 如果参数正确返回 True，错误返回 False
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    /**
     * 获取当前页面的资源文件 Id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(){
        ButterKnife.bind(this);
    }


    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     * 点击导航栏上的返回按钮
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        // 当点击导航栏上的返回时，finish 当前的界面
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 点击了手机的 Home 键
     */
    @Override
    public void onBackPressed() {

        // 得到当前 Activity 下的所以 Fragment
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        // 判断是否为空
        if(fragments != null && fragments.size() > 0){
            for (Fragment fragment : fragments) {
                // 判断是否为我们自定义的 Fragment
                if(fragment instanceof com.baihe.italker.common.App.Fragment){
                    // 判断 当前 Fragment 对象是否实现了 懒觉返回按钮的方法
                    // 如果 Fragment 已经实现了就不需要 Activity 实现，否则需要出发 finish 方法
                    if(((com.baihe.italker.common.App.Fragment) fragment).onBackPressed()){
                        return;
                    }
                }
            }
        }

        super.onBackPressed();

        finish();

    }
}


