package com.android.collect.library.util;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * activity堆栈管理
 */
public enum ActivityManager {

    INSTANCE;

    public static ActivityManager getInstance() {
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
        if (activities == null)
            return null;

        if (activities.size() == 0) {
            return null;
        }
        Activity activity = activities.get(activities.size() - 1);
        activities.remove(activity);
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
            activities = null;
        }
    }

    /**
     * 清空activity堆栈
     */
    public void clearActivityStackNotFinish() {
        if (activities != null)
            activities.clear();
        activities = null;
    }

    public int getSize() {
        return activities.size();
    }

}
