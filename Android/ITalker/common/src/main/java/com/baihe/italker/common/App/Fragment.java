package com.baihe.italker.common.App;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by baihe on 2017/10/17.
 */

public abstract class Fragment extends android.support.v4.app.Fragment {


    private View mRoot;
    private Unbinder mRootUnbinder;


    /**
     * 当 Fragment 添加到 Activity 上首先触发的方法
     *
     * @param context Activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        if (mRoot == null) {
            int layId = getContentLayoutId();
            // 初始化当前的布局，但是不在创建的时候就添加到 container 上
            View root = inflater.inflate(layId, container, false);
            initWidget(root);

            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                // 把当前 Root 从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // View 创建完成后，初始化数据
        initData();
    }

    /**
     * 初始化相关参数
     */
    protected void initArgs(Bundle bundle) {

    }

    /**
     * 得到当前界面的资源文件 Id
     *
     * @return 资源文件 Id
     */

    protected abstract int getContentLayoutId();


    /**
     * 初始化控件
     *
     * @param root
     */
    protected void initWidget(View root) {
        mRootUnbinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 返回按钮触发时调用的方法
     *
     * @return 返回 True 表示Fragment 已处理返回逻辑，Activity 不需要自己 finish
     * 返回 False 表示 Fragment 没有处理返回逻辑，Activity 需要走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }


}
