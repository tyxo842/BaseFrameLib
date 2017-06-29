package com.tyxo.testbaseframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView text_baseframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_baseframe = (TextView) findViewById(R.id.text_baseframe);


        text_baseframe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_baseframe:
                Intent intent = new Intent(this,TestBaseFrame.class);
                startActivity(intent);
                break;
        }
    }
}
