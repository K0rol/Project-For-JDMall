package com.m520it.jdmall03.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.utils.ActivityUtil;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class SplashActivity extends BaseActivity {
    private ImageView mLogoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initSplashAnim();
    }

    private void initSplashAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mLogoIv, "alpha", 0.1f, 1.0f);
        animator.setDuration(3000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ActivityUtil.startNew(SplashActivity.this,LoginActivity.class,true);
            }
        });
        animator.start();
    }

    @Override
    protected void initView() {
        mLogoIv = (ImageView) findViewById(R.id.logo_iv);
    }
}
