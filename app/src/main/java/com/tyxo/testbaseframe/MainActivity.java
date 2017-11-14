package com.tyxo.testbaseframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_baseframe:
                Intent intent = new Intent(this, TestBaseFrame.class);
                startActivity(intent);
                break;
            case R.id.text_statusview:
                Intent intent2 = new Intent(this, TestStatusView.class);
                startActivity(intent2);
                break;
        }
    }
}
