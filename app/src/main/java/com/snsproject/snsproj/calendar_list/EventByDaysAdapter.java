package com.snsproject.snsproj.calendar_list;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.snsproject.snsproj.R;

import java.util.Calendar;
import java.util.List;

public class EventByDaysAdapter extends BaseAdapter implements
        PinnedHeaderListView.PinnedHeaderAdapter, OnScrollListener {
    private List<EventsByDay> mList;
    private SectionIndex mIndexer;
    private Activity mContext;
    private int mLocationPosition = -1;
    private LayoutInflater mInflater;

    public EventByDaysAdapter(List<EventsByDay> mList, SectionIndex mIndexer,
                              Activity mContext) {
        this.mList = mList;
        this.mIndexer = mIndexer;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_event_item, parent,false);
            holder = new ViewHolder();
            holder.group_title = (TextView) convertView.findViewById(R.id.group_title);
            holder.day = (TextView) convertView.findViewById(R.id.day);
            holder.week = (TextView) convertView.findViewById(R.id.week);
            holder.today = (TextView) convertView.findViewById(R.id.today);
            holder.listItems = (LinearLayoutForListView) convertView.findViewById(R.id.list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EventsByDay singleMonth = mList.get(position);
        holder.day.setText(String.valueOf(singleMonth.getDay()));
        Calendar cc = Calendar.getInstance();
        cc.set(singleMonth.getYear(),singleMonth.getMonth(),singleMonth.getDay());
        holder.week.setText(singleMonth.getWeek(cc.get(Calendar.DAY_OF_WEEK)));

        int section = mIndexer.getSectionForPosition(position);
        if (mIndexer.getPositionForSection(section) == position) {
            holder.group_title.setVisibility(View.VISIBLE);
            holder.group_title.setText(EventsByDay.getParallMonth(String.valueOf(singleMonth.getSortKey())));
        } else {
            holder.group_title.setVisibility(View.GONE);
        }
        final List<EventByItem> dayDatas = singleMonth.getEventByItemList();
        holder.listItems.setAdapter(new EventByItemAdapter(mContext, dayDatas));
        holder.listItems.setOnItemClickListener(new LinearLayoutForListView.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, Object obj, int position) {
                Toast.makeText(mContext,dayDatas.get(position).getEventId(),Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public TextView group_title;
        public LinearLayoutForListView listItems;
        public TextView day,week,today;
    }

    @Override
    public int getPinnedHeaderState(int position) {
        int realPosition = position;
        if (realPosition < 0
                || (mLocationPosition != -1 && mLocationPosition == realPosition)) {
            return PINNED_HEADER_GONE;
        }
        mLocationPosition = -1;
        int section = mIndexer.getSectionForPosition(realPosition);
        int nextSectionPosition = mIndexer.getPositionForSection(section + 1);
        if (nextSectionPosition != -1
                && realPosition == nextSectionPosition - 1) {
            return PINNED_HEADER_PUSHED_UP;
        }
        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        int realPosition = position;
        int section = mIndexer.getSectionForPosition(realPosition);
        String title = (String) mIndexer.getSections()[section];
        ((TextView) header.findViewById(R.id.group_title)).setText(EventsByDay.getParallMonth(title));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
        }

    }
}
