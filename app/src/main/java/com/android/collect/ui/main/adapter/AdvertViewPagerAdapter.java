package com.android.collect.ui.main.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.android.collect.ui.main.fragment.AdvertFragment;

import java.util.List;

public class AdvertViewPagerAdapter extends PagerAdapter {

    private List<ImageView> data;

    public AdvertViewPagerAdapter(List<ImageView> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data.size() > 1) {//大于一才轮播
            /*
			 * 这个值直接关系到ViewPager的“边界”，因此当我们把它设置为Integer.MAX_VALUE之后，
			 * 用户基本就看不到这个边界了（估计滑到这里的时候电池已经挂了吧o_O）。
			 * 当然，通常情况下设置为100倍实际内容个数也是可以的，之前看的某个实现就是这么干的。
			 */
            // return Integer.MAX_VALUE;
            return data.size() * AdvertFragment.VIEWPAGER_ITEM_TIMES;
        } else {
            return 1;
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
		/*
		 * 由于我们在instantiateItem()方法中已经处理了remove的逻辑，因此这里并不需要处理。实际上，
		 * 实验表明这里如果加上了remove的调用，则会出现ViewPager的内容为空的情况。
		 */
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // 对ViewPager页号求模取出View列表中要显示的项
        position %= data.size();
        if (position < 0) {
            position += data.size();
        }
        ImageView view = data.get(position);
        // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);

		/*
		 * 1.由于我们设置了count为
		 * Integer.MAX_VALUE，因此这个position的取值范围很大很大，但我们实际要显示的内容肯定没这么多
		 * （往往只有几项），所以这里肯定会有求模操作
		 * 。但是，简单的求模会出现问题：考虑用户向左滑的情形，则position可能会出现负值。所以我们需要对负值再处理一次，使其落在正确的区间内;
		 * 2.通常我们会直接addView，但这里如果直接这样写，则会抛出IllegalStateException。假设一共有三个view，
		 * 则当用户滑到第四个的时候就会触发这个异常
		 * ，原因是我们试图把一个有父组件的View添加到另一个组件。但是，如果直接写成下面这样:(ViewGroup
		 * )view.getParent().removeView(view);
		 * 则又会因为一开始的时候组件并没有父组件而抛出NullPointerException。因此，需要进行一次判断。也就是上面的代码
		 */

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

}
