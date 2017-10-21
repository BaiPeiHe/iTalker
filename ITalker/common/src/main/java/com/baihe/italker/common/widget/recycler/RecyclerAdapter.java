package com.baihe.italker.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baihe.italker.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by baihe on 2017/10/17.
 */

@SuppressWarnings("unchecked")
public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {

    private final List<Data> mDataList;

    private AdapterListener<Data> mListener;

    /**
     * 构造函数
     */
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }


    /**
     * 复写默认的布局类型返回
     *
     * @param position 坐标
     * @return 布局的类型，其实复写后返回的都是 XML 文件的 ID
     */
    @Override
    public int getItemViewType(int position) {

        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到类型的布局
     *
     * @param position 坐标
     * @param data     当前的数据
     * @return XML 文件的 ID，用于创建 ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 根据 viewType 创建 ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 界面的类型，约定为 XML 布局的 Id
     * @return ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        // 得到 LayoutInflater 用于把 XML 文件初始化为 View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // 把 XML Id 为 viewType 的文件初始化为一个 root View
        View root = inflater.inflate(viewType, parent, false);
        // 通过子类必须实现的方法，得到一个 ViewHolder
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);


        //  设置View 的 Tag 为 ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        // 设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        // 进行界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);

        // 绑定 callback
        holder.callback = this;

        return holder;
    }

    /**
     * 创建一个新的 ViewHolder
     *
     * @param root     根布局
     * @param viewType viewType 布局类型，就是 XML 的 ID
     * @return ViewHolder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到 Holder 上
     *
     * @param holder   ViewHolder
     * @param position 数据的坐标
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {

        // 获取数据
        Data data = mDataList.get(position);

        // 绑定数据
        holder.bind(data);
    }


    /**
     * 获得数据集的长度
     *
     * @return 数据集的长度
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据，并通知插入
     *
     * @param data Data
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeChanged(startPos, dataList.length);
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPos, dataList.size());
        }
    }

    /**
     * 清空操作
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     *
     * @param dataList
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0) {
            return;
        }

        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            // 得到 ViewHolder 在适配器中对应的坐标
            int pos = viewHolder.getAdapterPosition();
            // 回调方法
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }

    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            // 得到 ViewHolder 在适配器中对应的坐标
            int pos = viewHolder.getAdapterPosition();
            // 回调方法
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));

            return true;
        }
        return false;
    }

    /**
     * 设置适配器的监听
     *
     * @param adapterListener adapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 自定义监听器
     *
     * @param <Data> 泛型
     */
    public interface AdapterListener<Data> {

        // 当点击 Cell 时触发
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        // 当长按 Cell 时触发
        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);

    }


    /**
     * 自定义的 ViewHolder
     *
     * @param <Data> 泛型类型
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {

        private Unbinder unbinder;
        private AdapterCallback<Data> callback;
        protected Data mData;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据
         *
         * @param data 绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 绑定完数据后的回调，必须实现
         *
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

        /**
         * Holder 自己对自己对应的 Data 进行更新操作
         *
         * @param data 需要更新的数据
         */
        public void updateData(Data data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }

        }
    }
}
