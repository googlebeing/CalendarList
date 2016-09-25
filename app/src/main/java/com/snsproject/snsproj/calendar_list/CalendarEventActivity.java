package com.snsproject.snsproj.calendar_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;


import com.snsproject.snsproj.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CalendarEventActivity extends AppCompatActivity {

    private SectionIndex mIndexer;
    private List<EventsByDay> eventsByDays = new ArrayList<>();
    private EventByDaysAdapter mAdapter;
    private String[] sections = {"X", "W", "V", "U", "T", "S", "R", "Q", "P", "O", "N", "M",
            "L", "K", "J", "I", "H", "G", "F", "E", "D", "C", "B", "A"};
    private int[] counts;
    private PinnedHeaderListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mListView = (PinnedHeaderListView) findViewById(R.id.mListView);

        requestData();
    }

    private static final int SELECT_DATE = 1212;

    //日历图标的点击事件
    public void selectDate(View view) {
        startActivityForResult(new Intent(this, SelectDateActivity.class), SELECT_DATE);
    }

    private int jumpIndex = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int year = data.getIntExtra("year", 2016);
            int month = data.getIntExtra("month", 1);
            int day = data.getIntExtra("day", 1);
            EventsByDay eventsByDay = new EventsByDay(year, month, day);
            int i;
            jumpIndex = -1;
            if (eventsByDays.get(0).getYear() == year) {
                for (i = 0; i < eventsByDays.size(); i++) {
                    EventsByDay eventsByDay1 = eventsByDays.get(i);
                    if (eventsByDay.equals(eventsByDay1)) {
                        jumpIndex = i + 36;
                        break;
                    }
                }
            } else {
                for (i = 0; i < eventsByDays.size(); i++) {
                    EventsByDay eventsByDay1 = eventsByDays.get(i);
                    if (eventsByDay.equals(eventsByDay1)) {
                        jumpIndex = i;
                        break;
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (jumpIndex != -1) {
            mListView.setSelected(true);
            mListView.setSelection(jumpIndex);
        } else {
            Calendar cc = Calendar.getInstance();
            int month = cc.get(Calendar.MONTH);
            mListView.setSelected(true);
            mListView.setSelection((11 - month) * 3 + 36);
        }
    }

    private void requestData() {
        //获取数据：
        List<EventsByDay> all = getEventDatas();
        if (all != null) {
            Collections.sort(all, new LetterComparator());    //排序
            eventsByDays.addAll(all);
            //初始化每个月份下有多少个item
            counts = new int[24];

            for (EventsByDay singleMonth : all) {
                int index = singleMonth.getIndex();
                counts[index]++;
            }
            mIndexer = new SectionIndex(sections, counts);
            mAdapter = new EventByDaysAdapter(eventsByDays, mIndexer, this);
            mListView.setAdapter(mAdapter);
            mListView.setOnScrollListener(mAdapter);
            mListView.setPinnedHeaderView(LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.list_section_item, mListView, false));
        }
    }

    public List<EventsByDay> getEventDatas() {
        List<EventsByDay> eventsByDay = new ArrayList<>();
        EventsByDay eventMonth;
        for (int index = 23; index >= 0; index--) {
            List<EventByItem> eventDayDatas = new ArrayList<>();
            EventByItem day = null;
            for (int days = 21; days > 6; days -= 7) {
                int ran = (int) (Math.random() * 2);
                for (int nums = 0; nums <= ran; nums++) {
                    day = new EventByItem(days);
                    eventDayDatas.add(day);
                }
                eventMonth = new EventsByDay(eventDayDatas, index, days);
                eventsByDay.add(eventMonth);
            }
        }
        return eventsByDay;
    }

    public static class LetterComparator implements Comparator<EventsByDay> {
        @Override
        public int compare(EventsByDay c1, EventsByDay c2) {
            return c2.getIndex().compareTo(c1.getIndex());
        }
    }

}
