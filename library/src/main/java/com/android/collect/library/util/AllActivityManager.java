package com.android.collect.library.util;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author anzai
 *         activity堆栈管理
 */
public enum AllActivityManager {

    INSTANCE;

    public static AllActivityManager getInstance() {
        return INSTANCE;
    }

    private static LinkedList<Activity> activities = new LinkedList<Activity>();

    public List<Activity> getActivitys() {
        return activities;
    }

    /**
     * 溢出堆栈的前一个acitivity
     *
     * @return
     */
    public Activity popActivity() {
        if (Util.isListEmpty(activities)) {
            return null;
        }
        Activity activity = activities.get(activities.size() - 1);
        activities.remove(activity);
        if (activity != null && !activity.isFinishing())
            activity.finish();
        return activity;
    }

    /**
     * 向堆栈中添加activity
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activities == null) {
            activities = new LinkedList<Activity>();
        }

        if (!activities.contains(activity.getClass())) {
            activities.add(activity);
        }

    }

    public void removeActivity(Activity activity) {
        if (activities != null) {
            if (activities.contains(activity)) ;
            int index = activities.indexOf(activity);
            activities.set(index, null);
        }
    }

    /**
     * 清空activity堆栈
     */
    public void clearActivityStack() {
        if (activities != null) {
            for (Activity activity : activities) {
                if (activity != null && !activity.isFinishing())
                    activity.finish();
            }
            activities.clear();
        }
    }

    /**
     * 清空activity堆栈
     */
    public void clearActivityStackNotFinish() {
        if (activities != null)
            activities.clear();
    }

    public int getSize() {
        return activities.size();
    }

}
