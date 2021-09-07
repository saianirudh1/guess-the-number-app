package com.example.guessthenumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private TextView lastGuess, lives, guessHint;
    private EditText userGuess;
    private Button guessButton;

    private Integer myGuess;
    private Integer userLives = 10;
    private ArrayList<Integer> guesses = new ArrayList<>();
    private Integer previousGuess = 0, previousDifference = Integer.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        lastGuess = findViewById(R.id.lastGuess);
        lives = findViewById(R.id.lives);
        guessHint = findViewById(R.id.hint);
        userGuess = findViewById(R.id.guess);
        guessButton = findViewById(R.id.guessSubmit);

        lives.setText(getResources().getText(R.string.lives) + " " + userLives);
        guessHint.setVisibility(View.INVISIBLE);
        lastGuess.setVisibility(View.INVISIBLE);
        myGuess = randomGuess();

        Log.d("guess", myGuess+"");

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessHint.setVisibility(View.VISIBLE);
                lastGuess.setVisibility(View.VISIBLE);
                if(String.valueOf(userGuess.getText()).isEmpty()){
                    Toast.makeText(GameActivity.this, "Please guess the number!", Toast.LENGTH_LONG).show();
                    return;
                }

                Integer currGuess = Integer.parseInt(String.valueOf(userGuess.getText()));
                Integer currDifference = myGuess - currGuess;
                if(currGuess.equals(myGuess)){
                    AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this);
                    alert.setTitle("Guess The Number")
                            .setCancelable(false)
                            .setMessage("Congratulations you guessed the right number! " + myGuess +
                                    "\n \n You guessed in " + (10 - userLives) + " attempts " +
                                    "\n \n Your guess were : " + String.valueOf(guesses)+
                                    "\n \n Would you like to play again?");

                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    });

                    alert.create().show();
                }else{
                    userLives--;
                    if(userLives == 0){
                        AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this);
                        alert.setTitle("Guess The Number")
                                .setCancelable(false)
                                .setMessage("You Lost all your chances! The Correct guess was  " + myGuess +
                                        "\n \n Your guess were : " + String.valueOf(guesses)+
                                        "\n \n Would you like to play again?");

                        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        alert.create().show();
                    }
                    if(currDifference > previousDifference) {
                        guessHint.setText("Colder");
                        guessHint.setBackgroundColor(getResources().getColor(R.color.cold));
                    }else{
                        guessHint.setText("Hotter");
                        guessHint.setBackgroundColor(getResources().getColor(R.color.hot));
                    }
                }

                previousGuess = currGuess;
                guesses.add(currGuess);
                previousDifference = currDifference;
                lastGuess.setText(getResources().getText(R.string.last_guess) + ""+ previousGuess);
                lives.setText(getResources().getText(R.string.lives) + "" + userLives);
                userGuess.setText("");
            }
        });
    }

    private Integer randomGuess(){
        Integer digits = getIntent().getIntExtra("digits", 2);
        int min = 1, max = 99;
        switch (digits){
            case 3:
                min = 100;
                max = 999;
                break;
            case 4:
                min = 1000;
                max = 9999;
                break;
        }

        return Integer.valueOf ((int) Math.random() * (max - min + 1) + min);
    }
}