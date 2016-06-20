package com.proconsi.leondecompras.actividades;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.proconsi.leondecompras.R;

public class ActivitySplashScreen extends Activity {

    private boolean lanzarActividadDirectamente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View temp = findViewById(R.id.temp);
        temp.animate().alpha(1).setDuration(1000).setListener(new Animator.AnimatorListener() {

            @Override public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!lanzarActividadDirectamente) {
                    startActivity(new Intent(ActivitySplashScreen.this, ActivityPortada.class));
                }
            }

            @Override public void onAnimationCancel(Animator animator) { }

            @Override public void onAnimationRepeat(Animator animator) { }
        });
    }

    @Override
    protected void onPause() {
        lanzarActividadDirectamente = true;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (lanzarActividadDirectamente) {
            startActivity(new Intent(ActivitySplashScreen.this, ActivityPortada.class));
        }
    }

//    private void lanzarLogin() {
//        Intent maleta = new Intent(ActivitySplash.this, ActivityLogin.class);
//
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this,
//                    new Pair<View, String>(logoLogin, getString(R.string.animation_logo_login_name)));
//
//            startActivity(maleta, transitionActivityOptions.toBundle());
//        } else {
//            startActivity(maleta);
//        }
//    }
}
