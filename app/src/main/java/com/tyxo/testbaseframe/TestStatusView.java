package com.tyxo.testbaseframe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tyxo.baseframelib.functions.statusbar.StatusBarCompat;

/**
 * Created by jsyfb-liy on 2017/11/14.
 */

public class TestStatusView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusview_translucent);

        initStatusView();
    }

    private void initStatusView() {
        StatusBarCompat.translucentStatusBar(this, false);
    }
}
