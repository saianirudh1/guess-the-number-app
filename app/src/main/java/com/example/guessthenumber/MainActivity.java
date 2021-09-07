package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private RadioButton twoDigit, threeDigit, fourDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        twoDigit = findViewById(R.id.twoDigit);
        threeDigit = findViewById(R.id.threeDigit);
        fourDigit = findViewById(R.id.fourDigit);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);

                if(!twoDigit.isChecked() && !threeDigit.isChecked() && !fourDigit.isChecked()){
                    Snackbar.make(v, "Please select the number of digits", Snackbar.LENGTH_LONG).show();
                }else{
                    if(twoDigit.isChecked()){
                        intent.putExtra("digits", 2);
                    }

                    if(threeDigit.isChecked()){
                        intent.putExtra("digits", 3);
                    }

                    if(fourDigit.isChecked()){
                        intent.putExtra("digits", 4);
                    }

                    startActivity(intent);
                }
            }
        });
    }
}