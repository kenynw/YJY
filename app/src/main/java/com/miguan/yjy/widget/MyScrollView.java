package com.miguan.yjy.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @作者 cjh
 * @日期 2017/6/6 14:48
 * @描述
 */

public class MyScrollView extends ScrollView {
    private OnScrollListener onScrollListener;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null){
            onScrollListener.onScroll(t);
        }
    }



    /**
     *
     * 滚动的回调接口
     *
     * @author xiaanming
     *
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         *              、
         */
        public void onScroll(int scrollY);
    }



}



//public class MyScrollView extends ScrollView {
//    private OnScrollListener onScrollListener;
//    /**
//     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
//     */
//    private int lastScrollY;
//
//    public MyScrollView(Context context) {
//        this(context, null);
//    }
//
//    public MyScrollView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    /**
//     * 设置滚动接口
//     * @param onScrollListener
//     */
//    public void setOnScrollListener(OnScrollListener onScrollListener) {
//        this.onScrollListener = onScrollListener;
//    }
//
//
//    /**
//     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
//     */
//    private Handler handler = new Handler() {
//
//        public void handleMessage(android.os.Message msg) {
//            int scrollY = MyScrollView.this.getScrollY();
//
//            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
//            if(lastScrollY != scrollY){
//                lastScrollY = scrollY;
//                handler.sendMessageDelayed(handler.obtainMessage(), 5);
//            }
//            if(onScrollListener != null){
//                onScrollListener.onScroll(scrollY);
//            }
//
//        };
//
//    };
//
//    /**
//     * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候，
//     * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
//     * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
//     * MyScrollView滑动的距离
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if(onScrollListener != null){
//            onScrollListener.onScroll(lastScrollY = this.getScrollY());
//        }
//        switch(ev.getAction()){
//            case MotionEvent.ACTION_UP:
//                handler.sendMessageDelayed(handler.obtainMessage(), 5);
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }
//
//
//    /**
//     *
//     * 滚动的回调接口
//     *
//     * @author xiaanming
//     *
//     */
//    public interface OnScrollListener{
//        /**
//         * 回调方法， 返回MyScrollView滑动的Y方向距离
//         * @param scrollY
//         *              、
//         */
//        public void onScroll(int scrollY);
//    }
//
//
//
//}




//public class MyScrollView extends ScrollView {
//    private int downX;
//    private int downY;
//    private int mTouchSlop;
//
//    public MyScrollView(Context context) {
//        super(context);
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//    }
//
//    public MyScrollView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//    }
//
//    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        int action = e.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                downX = (int) e.getRawX();
//                downY = (int) e.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int moveY = (int) e.getRawY();
//                if (Math.abs(moveY - downY) > mTouchSlop) {
//                    return true;
//                }
//        }
//        return super.onInterceptTouchEvent(e);
//    }
//}


//public class MyScrollView extends ScrollView {
//    private int downX;
//    private int downY;
//    private int mTouchSlop;
//    private int type;
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    private ScrollViewListener scrollViewListener = null;
//    private OnScrollViewToBottomLiatener onScrollViewToBottomLiatener = null;
//
//
//    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
//        this.scrollViewListener = scrollViewListener;
//    }
//
//    public void setOnScrollViewToBottomLiatener(OnScrollViewToBottomLiatener onScrollViewToBottomLiatener) {
//        this.onScrollViewToBottomLiatener = onScrollViewToBottomLiatener;
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//
//        View view = (View) getChildAt(getChildCount() - 1);
//
//        int d = view.getBottom();
//
//        d -= (getHeight() + getScrollY());
//
////        Log.e("---------->","d"+d);
//        if (d == 0) {
//
//            if (onScrollViewToBottomLiatener != null) {
//                onScrollViewToBottomLiatener.onScrollViewToBottomListener(type);
//
//            }
//
//        } else {
//
//            if (scrollViewListener != null) {
//                scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
//            }
//        }
//    }
//
//    public interface OnScrollViewToBottomLiatener {
//        void onScrollViewToBottomListener(int type);
//    }
//
//    public interface ScrollViewListener {
//
//        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);
//
//    }
//
//
//    public MyScrollView(Context context) {
//        super(context);
//    }
//
//    public MyScrollView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @TargetApi(21)
//    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int
//            defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
//
//    private float mDownPosX = 0;
//    private float mDownPosY = 0;
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final float x = ev.getX();
//        final float y = ev.getY();
//
//        final int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mDownPosX = x;
//                mDownPosY = y;
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                final float deltaX = Math.abs(x - mDownPosX);
//                final float deltaY = Math.abs(y - mDownPosY);
//                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
//                if (deltaX > deltaY) {
//                    return false;
//                }
//        }
//
//        return super.onInterceptTouchEvent(ev);
//    }
//
//
//}

