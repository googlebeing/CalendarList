package com.snsproject.snsproj.calendar_list;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/1.
 */
public class EventByItem {
    private String title;
    private int commentedNums;
    private int wantedNums;
    private boolean isSigned = false;
    private ArrayList<String> userNames = new ArrayList<>();
    private ArrayList<String> userIds = new ArrayList<>();
    private String eventId;
    private int day = 1;

    public EventByItem(int day){
        this.day = day;
        double ran = Math.random();
        if(ran > 0.8){
            this.title = "未来年度盛宴：致敬爱因斯坦，致敬科学";
        }else if(ran > 0.6){
            this.title = "6月13号，北方小乌镇－密云[古北口水镇--司马台长城]一日登山休闲活动";
        }else if(ran > 0.4){
            this.title = "陶瓷釉下彩绘，清华美院老师亲自指导";
        }else if(ran > 0.2){
            this.title = "西方绘画直通车第一季，水彩写生夏日玫瑰—水彩，水彩纸";
        }else{
            this.title = "一节课玩转尤克里里，你的私人定制曲目";
        }
        this.commentedNums = 2018;
        this.wantedNums = 1013;
        if(ran > 0.5){
            isSigned = true;
        }else{
            this.userNames.add("李十八,");
            this.userIds.add("1000461");
            this.userNames.add("李四十,");
            this.userIds.add("1000466");
            this.userNames.add("李超楠,");
            this.userIds.add("1000521");
            this.userNames.add("李一一,");
            this.userIds.add("1000444");
            this.userNames.add("徐加顺,");
            this.userIds.add("1000533");
            this.userNames.add("谁改了我的名字");
            this.userIds.add("1000044");
        }
        this.eventId = "23";
    }
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(ArrayList<String> userNames) {
        this.userNames = userNames;
    }

    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    public int getWantedNums() {
        return wantedNums;
    }

    public void setWantedNums(int wantedNums) {
        this.wantedNums = wantedNums;
    }

    public int getCommentedNums() {
        return commentedNums;
    }

    public void setCommentedNums(int commentedNums) {
        this.commentedNums = commentedNums;
    }
}
