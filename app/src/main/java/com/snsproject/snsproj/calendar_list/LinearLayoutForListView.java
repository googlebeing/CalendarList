package com.snsproject.snsproj.calendar_list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by admin on 16/3/31.
 */
public class LinearLayoutForListView extends LinearLayout {
    private BaseAdapter adapter;
    private OnClickListener onClickListener = null;
    private OnItemClickListener onItemClickListener;

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        bindLinearLayout();
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }
    /**
     * 绑定布局
     */
    public void bindLinearLayout() {
        if (adapter == null) {
            return;
        }
        this.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            final View v = adapter.getView(i,null,null);
            final int tmp = i;
            final Object obj = adapter.getItem(i);

            // view 点击事件触发时回调我们自己的接口
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClicked(v, obj, tmp);
                    }
                }
            });

            addView(v);
        }
    }

    public LinearLayoutForListView(Context context) {
        super(context);
    }

    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     *
     * 回调接口
     */
    public interface OnItemClickListener {
        /**
         *
         * @param v
         *            点击的 view
         * @param obj
         *            点击的 view 所绑定的对象
         * @param position
         *            点击位置的 index
         */
        void onItemClicked(View v, Object obj, int position);
    }
}