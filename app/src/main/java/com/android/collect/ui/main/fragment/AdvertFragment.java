package com.android.collect.ui.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.collect.R;
import com.android.collect.bean.AdvertDetail;
import com.android.collect.library.base.BaseFragment;
import com.android.collect.library.util.BitmapUtil;
import com.android.collect.library.util.DpPxUtils;
import com.android.collect.library.widget.FixedSpeedScroller;
import com.android.collect.library.widget.MyScrollViewPager;
import com.android.collect.ui.main.adapter.AdvertViewPagerAdapter;
import com.android.collect.ui.main.contract.AdvertContract;
import com.android.collect.ui.main.presenter.AdvertPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 滑动广告模块
 *
 * @author anzai
 */
public class AdvertFragment extends BaseFragment implements AdvertContract.View {

    private List<ImageView> iv_list;
    private ImageView[] pots_iv;
    private MyScrollViewPager vp_main;
    private AdvertViewPagerAdapter adapter;
    private LinearLayout ll_pots;

    /**
     * viewpager项的倍数
     */
    public static final int VIEWPAGER_ITEM_TIMES = 100;
    /**
     * 请求更新显示的View。
     */
    protected static final int MSG_UPDATE_IMAGE = 1;
    /**
     * 轮播时滑动时间时间
     */
    protected static final int SCROLL_TIME = 1500;
    /**
     * 轮播间隔时间
     */
    protected static final long MSG_DELAY = 4000;
    /**
     * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
     * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
     */
    private int currentItem = 0;
    /**
     * 广告图数量
     */
    private int bitmapCount = 0;
    /**
     * 是否可以轮播
     */
    private boolean isViewPagerCanLoop = true;
    /**
     * 防止重复点击
     */
    public static boolean isViewPagerIvClick = false;

    private AdvertContract.Presenter mPresenter;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    if (currentItem == adapter.getCount() - 1) {// 到达尽头开始回退
                        vp_main.setCurrentItem(currentItem - 1);
                    } else {
                        vp_main.setCurrentItem(currentItem + 1);
                    }
                    break;
            }
        }

        ;
    };

    public static AdvertFragment newInstance(Bundle bundle) {
        AdvertFragment fragment = new AdvertFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_advert;
    }

    @Override
    protected void initView() {
        initViewPager();
    }

    @Override
    protected void setData() {
        mPresenter = new AdvertPresenter(getActivity(), this);
        mPresenter.subscribe(null);

        bitmapCount = 3;
        setViewPager(null);
        setVpScrollTime();
        setPots();
        if (iv_list != null && iv_list.size() > 1) {// 两张以上才循环
            currentItem = vp_main.getCurrentItem();
            handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
        }
    }

    private void initViewPager() {
        vp_main = (MyScrollViewPager) view.findViewById(R.id.vp_main);
        ll_pots = (LinearLayout) view.findViewById(R.id.ll_pots);

        vp_main.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int i) {
                if (pots_iv != null) {
                    i %= pots_iv.length;
                    if (i < 0) {
                        i += pots_iv.length;
                    }
                    // 两张图片特殊(viewpager保留的是左中右三个对象，而当你的图片只有两张的时候他要加载第三个就会出现空白页*
                    // 所以解决方法是当资源为2的时候可以重复添加一遍到4的大小，指示点等一些地方做特殊处理)
                    if (bitmapCount == 2) {
                        i %= 2;
                    }
                    for (int j = 0; j < bitmapCount; j++) {
                        if (j == i) {
                            pots_iv[j].setSelected(true);
                        } else {
                            pots_iv[j].setSelected(false);
                        }
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                switch (i) {
                    case ViewPager.SCROLL_STATE_DRAGGING:// 滑动
                        // 滑动中清除掉循环播放
                        if (handler.hasMessages(MSG_UPDATE_IMAGE)) {
                            handler.removeMessages(MSG_UPDATE_IMAGE);
                        }
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:// 滑动结束

                        break;
                    case ViewPager.SCROLL_STATE_IDLE:// 闲置
                        if (isViewPagerCanLoop) {
                            if (handler.hasMessages(MSG_UPDATE_IMAGE)) {
                                handler.removeMessages(MSG_UPDATE_IMAGE);
                            }
                            currentItem = vp_main.getCurrentItem();
                            handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
                                    MSG_DELAY);
                        }
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void setViewPager(List<AdvertDetail> list) {
        iv_list = new ArrayList<ImageView>();
        /*
         * viewpager保留的是左中右三个对象，而当你的图片只有两张的时候他要加载第三个就会出现空白页
		 * 所以解决方法是当资源为2的时候可以重复添加一遍到4的大小，指示点等一些地方做特殊处理
		 */
        if (bitmapCount == 2) {
//            list.add(list.get(0));
//            list.add(list.get(1));
        }

        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_advert_vp, null);
            ImageView iv = (ImageView) view.findViewById(R.id.imageView);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isViewPagerIvClick)
                        return;
                    isViewPagerIvClick = true;

                }
            });
            /*String advertName = list.get(i).getAdvertName();
            File file = new File(FileConstant.APP_FILE_SD_ADVERT_IMG_DIR, advertName);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (file.exists()) {// 本地已经下载就直接读文件
                ImageLoaderUtils.loadImageWithFile(getActivity(), file, iv,
                        displayMetrics.widthPixels, DpPxUtils.dip2px(getActivity(), 120));
            } else {
                ImageLoaderUtils.loadImageWithUrl(getActivity(), list.get(i).getAdvertImage(), iv,
                        displayMetrics.widthPixels, DpPxUtils.dip2px(getActivity(), 120));
            }*/
            switch (i) {
                case 0:
                    iv.setImageBitmap(BitmapUtil.decodeResource(getActivity(), R.drawable.ic_home_advert1));
                    break;

                case 1:
                    iv.setImageBitmap(BitmapUtil.decodeResource(getActivity(), R.drawable.ic_home_advert2));
                    break;

                case 2:
                    iv.setImageBitmap(BitmapUtil.decodeResource(getActivity(), R.drawable.ic_home_advert3));
                    break;

                default:

                    break;

            }
            iv_list.add(iv);
        }
        adapter = new AdvertViewPagerAdapter(iv_list);
        vp_main.setAdapter(adapter);

		/* 默认在中间或者iv_list.size()的几十倍以上，使用户看不到边界，否者currentItem=0时是没法往左边滑的 */
        vp_main.setCurrentItem(iv_list.size() * VIEWPAGER_ITEM_TIMES / 2);

    }

    /**
     * 设置滑的时间和样式
     */
    private void setVpScrollTime() {
        Field field = null;
        try {
            field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getActivity(),
                    new AccelerateInterpolator());
            field.set(vp_main, scroller);
            scroller.setmDuration(SCROLL_TIME);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置点
     */
    private void setPots() {
        if (bitmapCount == 1) {// 一张不设置点
            return;
        }
        pots_iv = new ImageView[bitmapCount];
        ll_pots.removeAllViews();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                DpPxUtils.dip2px(getActivity(), 8), DpPxUtils.dip2px(getActivity(), 8));
        lp.setMargins(DpPxUtils.dip2px(getActivity(), 7), 0, 0, 0);

        for (int i = 0; i < bitmapCount; i++) {

            ImageView dot = new ImageView(getActivity());
            dot.setBackgroundResource(R.drawable.selector_advert_vp_dots);
            if (i == 0) {
                dot.setSelected(true);
            } else {
                dot.setSelected(false);
            }

            dot.setLayoutParams(lp);

            pots_iv[i] = dot;
            ll_pots.addView(dot);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (iv_list != null && iv_list.size() > 1 && !isViewPagerCanLoop) {// 两张以上才循环
            currentItem = vp_main.getCurrentItem();
            handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
        }
        isViewPagerCanLoop = true;
        isViewPagerIvClick = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        // 清除掉循环播放
        if (handler.hasMessages(MSG_UPDATE_IMAGE)) {
            handler.removeMessages(MSG_UPDATE_IMAGE);
        }
        isViewPagerCanLoop = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        mPresenter.unSubscribe();
    }

    @Override
    public void onGetAdertBitmapResult(List<AdvertDetail> list) {
        /*if (!Util.isListEmpty(list)) {
            bitmapCount = list.size();
            setViewPager(list);
            setVpScrollTime();
            setPots();
            if (iv_list != null && iv_list.size() > 1) {// 两张以上才循环
                currentItem = vp_main.getCurrentItem();
                handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
            }
        }*/
    }

}
