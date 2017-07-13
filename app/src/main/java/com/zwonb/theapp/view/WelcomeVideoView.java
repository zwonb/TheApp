package com.zwonb.theapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 最大化VideoView宽高
 * Created by zyb on 2017/7/12.
 */

public class WelcomeVideoView extends VideoView {

    public WelcomeVideoView(Context context) {
        super(context);
    }

    public WelcomeVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WelcomeVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重点。
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

}
