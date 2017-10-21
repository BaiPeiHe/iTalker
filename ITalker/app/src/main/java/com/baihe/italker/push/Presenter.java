package com.baihe.italker.push;

import android.text.TextUtils;

/**
 * Created by baihe on 2017/10/21.
 */

public class Presenter implements IPresenter {

    private IView mView;

    public Presenter(IView view) {
        mView = view;
    }


    @Override
    public void search() {
        String inputString = mView.getInputString();

        if (TextUtils.isEmpty(inputString)) {
            // 为空时直接返回
            return;
        }

        int hashCode = inputString.hashCode();

        IUserService service = new UserService();
        String serviceResult = service.search(hashCode);

        String result = "result：" + inputString + "-" + serviceResult;

        mView.setResultString(result);
    }



}
