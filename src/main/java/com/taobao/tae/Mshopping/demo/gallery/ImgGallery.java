package com.taobao.tae.Mshopping.demo.gallery;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

public class ImgGallery extends android.widget.Gallery
{
    /**
     * 这里的数值，限制了每次滚动的最大长度，图片宽度为480PX。这里设置600效果好一些。 这个值越大，滚动的长度就越大。
     * 也就是会出现一次滚动跨多个Image。这里限制长度后，每次滚动只能跨一个Image
     */
    private static final int timerAnimation = 1;
    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case timerAnimation:
                    int position = getSelectedItemPosition();
                    Log.i("msg", "position:" + position + ", getCount:"+getCount());
                    if (position < (getCount() - 1)) {
                        onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                    } else {
                        onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
                    }
                    if(position == (getCount() -1)){
                        setSelection(0);
                    }
                    break;

                default:
                    break;
            }
        };
    };

    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask()
    {
        public void run()
        {
            mHandler.sendEmptyMessage(timerAnimation);
        }
    };

    public ImgGallery(Context paramContext)
    {
        super(paramContext);
        timer.schedule(task, 5000, 5000);
    }

    public ImgGallery(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        timer.schedule(task, 5000, 5000);

    }

    public ImgGallery(Context paramContext, AttributeSet paramAttributeSet,
                      int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        timer.schedule(task, 5000, 5000);

    }

    private boolean isScrollingLeft(MotionEvent paramMotionEvent1,
                                    MotionEvent paramMotionEvent2)
    {
        float f2 = paramMotionEvent2.getX();
        float f1 = paramMotionEvent1.getX();
        if (f2 > f1)
            return true;
        return false;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (velocityX > 0) {
            // 往左边滑动
            super.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
        } else {
            // 往右边滑动
            super.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
        }
        return false;
    }

    public void destroy()
    {
        timer.cancel();
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        } else {
            return false;
        }
    }


}
