package com.miguan.yjy.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @作者 cjh
 * @日期 2017/6/7 15:06
 * @描述
 */

public class SignViewPager extends ViewPager {
    private int current;
    private int height = 0;
    /**
     * 保存position与对于的View
     */
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

    private boolean noScroll = true;
    public SignViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SignViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override

    public void setCurrentItem(int item) {

        super.setCurrentItem(item, false);//表示切换的时候，不需要切换时间。

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildrenViews.size() > current) {
            View child = mChildrenViews.get(current);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int current) {
        this.current = current;
        if (mChildrenViews.size() > current) {

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
            } else {
                layoutParams.height = height;
            }
            setLayoutParams(layoutParams);
        }
    }

    /**
     * 保存position与对于的View
     */
    public void setObjectForPosition(View view, int position) {
        mChildrenViews.put(position, view);
    }


}
//接下来需要注意的是：
//        条件1：
//        pager.setOffscreenPageLimit(3);
//        条件2：
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
//@Override
//public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){
//
//        }
//@Override
//public void onPageSelected(int position){
//        pager.resetHeight(position);//重置viewpager的高度
//        //num.setText((position+1)+"/"+imageInfo.size());
//        pager.resetHeight(0);
//        }
//@Overridepublic void onPageScrollStateChanged(int state){}});
////        条件3：在instantiateItem()方法中pager.setObjectForPosition(view1,position);setObjectForPosition方法是为了调用存放你的view和他对应的position这样就完美解决了。
////        方式二：ViewPager中放Fragment
////        条件1：
////        setObjectForPosition（）方法中是为了调用存放你的view和他对应的position，这个是参考了鸿洋大神的一篇文章，ic_product_detail_link：http://blog.csdn.net/lmj623565791/article/details/38026503
////        ，为了防止预加载导致的高度不匹配，我们加自身的fragment和position对应起来放在linkedmap里。
////        好了，剩下的就是调用vp.setObjectForPosition(view,1);
////        setObjectForPosition（）这个方法了，请看我的一个SecurityInfoFragment
//public SecurityInfoFragment(CustomViewpager pager){  //CustomViewpager 的引用传到Fragment中，用来调用
//        this.pager=pager;
//        }
//
//
//@Nullable
//@Override
//public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
//        View view=inflater.inflate(R.layout.fg_sc_filght_info,null);
//
//        pager.setObjectForPosition(view,1);
//        return view;
//        }
//        条件2：
//        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
//@Override
//public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){
//
//        }
//
//@Override
//public void onPageSelected(int position){
//        activityScdetailsBottomVp.resetHeight(position);
//        }
//@Override
//public void onPageScrollStateChanged(int state){
//
//        }
//        });
//        pager.resetHeight(0);  //默认设置第0个页面或view高度，当滑动的时候<span style="font-family: Arial, Helvetica, sans-serif;">setOnPageChangeListener</span><span style="font-family: Arial, Helvetica, sans-serif;">调用</span><span style="font-family: Arial, Helvetica, sans-serif;">activityScdetailsBottomVp.resetHeight(position);  </span><span style="font-family: Arial, Helvetica, sans-serif;">
//</span>

