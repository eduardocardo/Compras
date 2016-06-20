package com.proconsi.leondecompras.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.larvalabs.svgandroid.SVGParser;

/**
 * Created by diego.salas on 22/4/15.
 */
public class ImageView extends android.widget.ImageView {

    public ImageView(Context context) {
        super(context);
        init(null);
    }

    public ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    @Override
    public void setImageResource(int resId) {
        if(setSVGImageResource(resId)){
            return;
        }
        super.setImageResource(resId);
    }

    private boolean setSVGImageResource(int svgId) {
        Drawable svg = null;
        try {
            svg = SVGParser.getSVGFromResource(getContext().getResources(), svgId).createPictureDrawable();
        } catch (Exception ignored){}
        if (svg != null) {
            setImageDrawable(svg);
            return true;
        }
        setImageDrawable(null);
        return false;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if(Build.VERSION.SDK_INT >= 11) {
            if (drawable instanceof PictureDrawable) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            } else {
                setLayerType(View.LAYER_TYPE_NONE, null);
            }
        }
        super.setImageDrawable(drawable);
    }

    private void init(AttributeSet attrs) {
        if(getDrawable() == null && attrs != null){
            int svgId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
            setSVGImageResource(svgId);
        }
    }
}
