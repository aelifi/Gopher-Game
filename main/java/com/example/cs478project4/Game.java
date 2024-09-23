package com.example.cs478project4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private TextView log;
    private gamePlayer thread1;
    private gamePlayer thread2;
    private int gopherX;
    private int gopherY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initialize all the relevant widgets
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        log = findViewById(R.id.log);

        //Starts the game on click
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        //Stops the threads
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopGame();
            }
        });
    }

    //Function to start the game
    private void startGame() {
        log.setText(""); //Clear the log screen
        gopherX = (int) (Math.random() * 10); //Set location for the gopher
        gopherY = (int) (Math.random() * 10);
        appendToLog("Gopher spawned at: (" + gopherX + ", " + gopherY + ")");

        thread1 = new gamePlayer("Player 1"); //Define each thread
        thread2 = new gamePlayer("Player 2");

        thread1.start(); //Start the threads
        thread2.start();
    }

    //Function to interrupt threads
    private void stopGame() {
        if (thread1 != null)
            thread1.interrupt();
        if (thread2 != null)
            thread2.interrupt();
    }

    //Player class
    private class gamePlayer extends Thread {
        private String player;

        public gamePlayer(String player) { //Constructor for players name
            this.player = player;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(2000);
                    //Thread.sleep(100); //Uncomment for quick solution
                } catch (InterruptedException e) {
                    return; //If throw an exception then it crashes idk why tbh
                }

                //Make a guess at coordinate
                int guessAtX = (int) (Math.random() * 10);
                int guessAtY = (int) (Math.random() * 10);

                //Add to log player guess
                appendToLog(player + " tried: (" + guessAtX + ", " + guessAtY + ")");

                //Check weather the guess was correct
                if (guessAtX == gopherX && guessAtY == gopherY) {
                    String message = player + " smacked gopher on (" + guessAtX + ", " + guessAtY + ")";
                    appendToLog(message);
                    stopGame();
                    return;
                }
            }
        }
    }

    //Function to add message to log, gotta make it synchronize to not crash
    private synchronized void appendToLog(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                log.append(message + "\n"); //Add message to log
            }
        });
    }
}