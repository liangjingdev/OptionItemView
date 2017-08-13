package com.liangjing.optionitemview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.liangjing.library.OptionItemView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OptionItemView oiv = (OptionItemView) findViewById(R.id.oiv);

        oiv.setSpliteMode(true);
        oiv.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
            @Override
            public void leftOnClick() {
                Toast.makeText(getApplicationContext(), "左边被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void centerOnClick() {
                Toast.makeText(getApplicationContext(), "中间被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightOnClick() {
                Toast.makeText(getApplicationContext(), "右边被点击", Toast.LENGTH_SHORT).show();
            }
        });

        oiv.setSpliteMode(false);
        oiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "整体被点击", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
