package com.proconsi.leondecompras.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.proconsi.leondecompras.utilidades.Tipografias;

/**
 * Created by rodrigo.gomez on 16/07/2015.
 */
public class ButtonTypeface extends Button {

    public ButtonTypeface(Context context) {
        super(context);
        setBaseTypeface(context);
    }

    public ButtonTypeface(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBaseTypeface(context);
    }

    public ButtonTypeface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBaseTypeface(context);
    }

    private void setBaseTypeface(Context context) {
//        Typeface type = Tipografias.getTypeface(context, "HelveticaCnIt.ttf");
//        setTypeface(type);
    }
}
