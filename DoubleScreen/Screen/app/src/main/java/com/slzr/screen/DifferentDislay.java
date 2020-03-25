package com.slzr.screen;

import android.app.Presentation;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

import androidx.annotation.RequiresApi;

/**
 * Created by pxl on 2020/3/17 0017 下午 2:55.
 * Describe:
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class DifferentDislay extends Presentation {
    public DifferentDislay(Context outerContext, Display display) {
        super(outerContext, display);
    }

    public DifferentDislay(Context outerContext, Display display, int theme) {
        super(outerContext, display, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
    }
}
