package com.android.collect.ui.view.other_view.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

import com.android.collect.R;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.widget.circleprogress.CircleProgress;

/**
 * 圆形进度条
 */
public class CircleProgressTestActivity extends SwipeBackTitleActivity {

    private CircleProgress mCircleProgress;
    private AppCompatSeekBar mAppCompatSeekBar;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_circle_progress_test);
        initTitle(Constant.TITLE1, R.string.circle_progress_title);
    }

    @Override
    protected void initView() {
        mCircleProgress = (CircleProgress) findViewById(R.id.circleProgress);
        mAppCompatSeekBar = (AppCompatSeekBar) findViewById(R.id.appCompatSeekBar);

        mAppCompatSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCircleProgress.setValue(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void setData() {

        int[] COLORS = new int[]{ContextCompat.getColor(this, R.color.circle_progress_bg),
                ContextCompat.getColor(this, R.color.circle_progress_bg)};
        mCircleProgress.setGradientColors(COLORS);
        mCircleProgress.setMaxValue(100);
        mCircleProgress.setAnimTime(2000);

    }

}
