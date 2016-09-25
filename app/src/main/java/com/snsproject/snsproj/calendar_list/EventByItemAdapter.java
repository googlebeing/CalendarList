package com.snsproject.snsproj.calendar_list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snsproject.snsproj.R;
import com.snsproject.snsproj.calendar_list.flowlayout.FlowLayout;
import com.snsproject.snsproj.calendar_list.flowlayout.TagAdapter;
import com.snsproject.snsproj.calendar_list.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class EventByItemAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<EventByItem> childList;
    private Activity mContext;

    public EventByItemAdapter(Activity context, List<EventByItem> childList) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.childList = childList;
    }

    @Override
    public int getCount() {
        return childList.size();
    }

    @Override
    public Object getItem(int position) {
        return childList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        EventByItem dayEvent = childList.get(position);
        final EventViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_event_item_child, parent, false);
            viewHolder = new EventViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.res_title);
            viewHolder.bottomLine = (TextView) convertView.findViewById(R.id.bottom_line);
            viewHolder.commentedNums = (TextView) convertView.findViewById(R.id.commented_nums);
            viewHolder.wantedNums = (TextView) convertView.findViewById(R.id.wanted_nums);
            viewHolder.wantedJoinContainer = (LinearLayout) convertView.findViewById(R.id.wanted_join_container);
            viewHolder.isSigned = (TextView) convertView.findViewById(R.id.is_signed);
            viewHolder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.tags_layout);
            viewHolder.tagFlowLayout.setGravity(TagFlowLayout.LEFT);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(dayEvent.getTitle());
        if (dayEvent.isSigned()) {
            viewHolder.isSigned.setVisibility(View.VISIBLE);
        } else {
            viewHolder.isSigned.setVisibility(View.GONE);
        }
        viewHolder.commentedNums.setText(String.valueOf(dayEvent.getCommentedNums()));
        viewHolder.wantedNums.setText(String.valueOf(dayEvent.getWantedNums()));
        if (position == childList.size() - 1) {
            viewHolder.bottomLine.setVisibility(View.GONE);
        }
        viewHolder.tagFlowLayout.setMaxSelectCount(1);
        final LayoutInflater mInflater = LayoutInflater.from(mContext);

        final ArrayList<String> userNames = dayEvent.getUserNames();
        if (userNames.size() == 0) {
            viewHolder.wantedJoinContainer.setVisibility(View.GONE);
        } else {
            final ArrayList<String> userIds = dayEvent.getUserIds();
            viewHolder.tagFlowLayout.setAdapter(new TagAdapter<String>(userNames) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.tag_tv,
                            viewHolder.tagFlowLayout, false);
                    tv.setText(s);
                    return tv;
                }

                @Override
                public boolean setSelected(int position, String s) {
                    String value = userNames.get(position);
                    return s.equals(value != null ? value : "");
                }
            });

            viewHolder.tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    String userId = userIds.get(position);
                    Toast.makeText(mContext,userId,Toast.LENGTH_LONG).show();
                    return true;
                }
            });
        }

        return convertView;
    }

    class EventViewHolder {
        TextView title,bottomLine;
        TextView commentedNums;
        TextView wantedNums;
        TextView isSigned;
        LinearLayout wantedJoinContainer;
        TagFlowLayout tagFlowLayout;
    }
}
