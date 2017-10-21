package com.baihe.italker.common.widget.recycler;

/**
 * Created by baihe on 2017/10/17.
 */

public interface AdapterCallback<Data> {

    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);

}
