package com.snsproject.snsproj.calendar_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;


import com.snsproject.snsproj.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/6/30.
 * 活动日历选取页面
 */
public class SelectDateActivity extends AppCompatActivity {

    @Bind(R.id.date_tips)
    TextView dateTips;
    @Bind(R.id.tool_bar)
    Toolbar toolBar;
    @Bind(R.id.calendar_list)
    ListView mCalendarList;
    private int year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        ButterKnife.bind(this);

        Calendar cc = Calendar.getInstance();
        year = cc.get(Calendar.YEAR);
        dateTips.setText(String.valueOf(year) + "年");
        initView();
        mCalendarList.setAdapter(new CalendarListAdapter(this));
        mCalendarList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > 11) {
                    dateTips.setText(String.valueOf(year + 1) + "年");
                } else {
                    dateTips.setText(String.valueOf(year) + "年");
                }
            }
        });
    }

    private void initView() {
        setSupportActionBar(toolBar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.mipmap.ic_backarrow_black);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar cc = Calendar.getInstance();
        int month = cc.get(Calendar.MONTH);
        mCalendarList.setSelected(true);
        mCalendarList.setSelection(month);
    }

    public class CalendarListAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private Context mContext;

        public CalendarListAdapter(Context context) {
            this.mContext = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 24;
        }

        @Override
        public Object getItem(int position) {
            return 24;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            RecommendViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.layout_month_list, null);
                viewHolder = new RecommendViewHolder();
                viewHolder.month = (TextView) convertView.findViewById(R.id.month);
                viewHolder.calendarView = (GridView) convertView.findViewById(R.id.gv_calendar);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RecommendViewHolder) convertView.getTag();
            }
            viewHolder.month.setText((position % 12 + 1) + "月");
            Calendar calendar = Calendar.getInstance();
            calendar.set(position / 12 + 2016, position % 12, 1);
            final int week = calendar.get(Calendar.DAY_OF_WEEK);
            final int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            final List<CalendarBean> calendarBeanList = new ArrayList<>();
            for (int i = 1; i < week; i++) {
                calendarBeanList.add(new CalendarBean(0, 0));
            }
            for (int e = 1; e <= maxDay; e++) {
                int type = 0;
                if (e == 7 || e == 14 || e == 21) {
                    type = 1;
                }
                calendarBeanList.add(new CalendarBean(e, type));
            }
            CalendarAdapter calendarAdapter = new CalendarAdapter(mContext, calendarBeanList);
            viewHolder.calendarView.setAdapter(calendarAdapter);
            viewHolder.calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    int month = (position % 12);
                    int year = position / 12 + 2016;
                    if (calendarBeanList.size() > pos) {
                        CalendarBean calendarBean = calendarBeanList.get(pos);
                        int type = calendarBean.getType();
                        if (type == 1) {
                            int day = calendarBean.getDate();
                            Intent intent = new Intent();
                            intent.putExtra("year", year);
                            intent.putExtra("month", month);
                            intent.putExtra("day", day);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                }
            });
            return convertView;
        }

        class RecommendViewHolder {
            TextView month;
            GridView calendarView;
        }

    }

    public static class CalendarAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private List<CalendarBean> list;
        private int selected = 10000;

        public CalendarAdapter(Context context, List<CalendarBean> list) {
            layoutInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setSelected(int position) {
            this.selected = position;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CalendarBean bean = list.get(position);
            ViewHolder viewHolder = null;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.calendar_item_normal, parent, false);
                viewHolder.tvView = (TextView) convertView;
                convertView.setTag(viewHolder);
            }
            switch (bean.getType()) {
                case 0:
                    if (bean.getDate() == 0) {
                        return convertView;
                    } else {
                        viewHolder.tvView.setText(String.valueOf(bean.getDate()));
                    }
                    break;
                case 1:
                    viewHolder.tvView.setText(String.valueOf(bean.getDate()));
                    viewHolder.tvView.setTextColor(Color.WHITE);
                    viewHolder.tvView.setBackgroundResource(R.drawable.calendar_day_bg);
                    break;
            }
            return convertView;
        }

        class ViewHolder {
            TextView tvView;
        }
    }

    public static class CalendarBean {
        private int date;
        private int type;

        public CalendarBean(int date, int type) {
            this.date = date;
            this.type = type;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
