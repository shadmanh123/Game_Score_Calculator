package com.example.cmpt276project;
import android.view.animation.Interpolator;

public class BubbleInterpolation implements Interpolator {

    private double A = 1, f = 10;

    public BubbleInterpolation(double a, double f) {
        A = a;
        this.f = f;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (-1*Math.pow(Math.E, -input/A)*Math.cos(f*input)+1);
    }
}
