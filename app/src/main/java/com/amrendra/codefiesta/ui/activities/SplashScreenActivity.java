package com.amrendra.codefiesta.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.utils.Debug;
import com.amrendra.codefiesta.utils.TrackingConstants;

import butterknife.Bind;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public class SplashScreenActivity extends BaseActivity {

    public static final int STARTUP_DELAY = 150;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 250;
    public static final int ITEM_DELAY_OFFSET = 400;
    public static final int ANIM_TEXT_DURATION = 1000;
    public static final int LOGO_Y_TRANSLATION = -150;
    public static final int TEXT_Y_TRANSLATION = 50;

    private boolean animationStarted = false;

    @Bind(R.id.img_logo)
    ImageView logoImageView;

    @Bind(R.id.container)
    LinearLayout linearLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.c();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setContentView(R.layout.splash_activity);

    }

    private void startMainListActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus || animationStarted) {
            return;
        }
        animate();
        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        animationStarted = true;
        ViewGroup container = linearLayoutContainer;
        ViewCompat.animate(logoImageView)
                .translationY(LOGO_Y_TRANSLATION)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator = ViewCompat.animate(v)
                    .translationY(TEXT_Y_TRANSLATION).alpha(1)
                    .setStartDelay((ITEM_DELAY * i) + ITEM_DELAY_OFFSET)
                    .setDuration(ANIM_TEXT_DURATION);

            if (v.getId() == R.id.hello_world_tv) {
                viewAnimator.withEndAction(runnable);
            }
            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startMainListActivity();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        trackActivity(TrackingConstants.SPLASH_ACTIVITY_SCREEN_NAME);
    }
}