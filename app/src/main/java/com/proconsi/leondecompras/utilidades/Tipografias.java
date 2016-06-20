package com.proconsi.leondecompras.utilidades;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by rodrigo.gomez on 16/09/2015.
 */
public class Tipografias {

    private static HashMap<String, Typeface> cache;

    public static Typeface getTypeface(Context context, String idTypeface) {
        if (cache == null) {
            cache = new HashMap<>();
        }

        if (cache.containsKey(idTypeface)) {
            return cache.get(idTypeface);
        }

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + idTypeface);
        cache.put(idTypeface, typeface);

        return typeface;
    }
}
