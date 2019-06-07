package com.dighisoft.physicsfloatingactionbar;

import android.content.Context;
import android.graphics.Canvas;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.design.widget.FloatingActionButton;

public class PhysicsFloatingActionBar extends FloatingActionButton {

    public PhysicsFloatingActionBar(Context context) {
        super(context);
        initAnimation();
    }

    private void initAnimation() {
        SpringAnimation animation = new SpringAnimation(getRootView(), DynamicAnimation.TRANSLATION_Y);
        animation.setSpring(createForce());

        while (true) {
            animation.start();
        }
    }

    private SpringForce createForce() {

        SpringForce force = new SpringForce(0).

                setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_MEDIUM);


        return force;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
