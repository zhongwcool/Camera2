package com.alex.camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSumsung(View view) {
        Intent intent = new Intent(this, SamsungActivity.class);
        startActivity(intent);
    }

    public void openCamera2(View view) {
        Intent intent = new Intent(this, Camera2Activity.class);
        startActivity(intent);
    }
}
