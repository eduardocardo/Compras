package com.proconsi.leondecompras.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class CustomScrollView extends ScrollView {

    private OnScrollViewListener mOnScrollViewListener;

    public void setOnScrollViewListener(OnScrollViewListener l) {
        this.mOnScrollViewListener = l;
    }

    public interface OnScrollViewListener {
        void onScrollChanged(CustomScrollView v, int l, int t, int oldl, int oldt);
    }

    // ********************************************************************************

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mOnScrollViewListener != null) {
            mOnScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
