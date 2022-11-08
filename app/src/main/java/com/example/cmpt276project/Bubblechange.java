package com.example.cmpt276project;
import android.view.animation.Interpolator;
/**
 * Bubblechange: changes the size of the bubble to create an animation
 */
public class Bubblechange implements Interpolator {

    private double  x= 1, y = 10;

    public Bubblechange(double a, double f) {
        x = a;
        this.y = f;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (-1*Math.pow(Math.E, -input/x)*Math.cos(y*input)+1);
    }
}
