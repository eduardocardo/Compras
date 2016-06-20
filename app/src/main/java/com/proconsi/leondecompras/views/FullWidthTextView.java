package com.proconsi.leondecompras.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * Created by practicassoftware3 on 26/04/2016.
 */
public class FullWidthTextView extends TextView {
    public FullWidthTextView(Context context) {
        super(context);
    }

    public FullWidthTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullWidthTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        View parentScrollView=((View)(getParent().getParent()));

        if (parentScrollView!=null) {
            // check the container of the container is an HorizontallScrollView
            if (parentScrollView instanceof HorizontalScrollView) {
                // Yes it is, so change width to HSV's width
                widthMeasureSpec=parentScrollView.getMeasuredWidth();
            }
        }
        setMeasuredDimension(widthMeasureSpec - 80, heightMeasureSpec);
    }
}
