package com.example.cs478project4;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_GAME_PLAYER = 100; //Permission code for GAME_PLAYER

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton); //Start and quit buttons
        Button quitButton = findViewById(R.id.quitButton);

        //When start button is pressed to start the game
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, "com.example.cs478project4.permission.GAME_PLAYER");
                if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
                    Intent intent = new Intent(MainActivity.this, Game.class);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{"com.example.cs478project4.permission.GAME_PLAYER"}, PERMISSION_REQUEST_GAME_PLAYER);
                }
            }
        });

        //When quit is pressed close the app
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Check permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_GAME_PLAYER) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //If permission was allowed then open the game
                Intent intent = new Intent(MainActivity.this, Game.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Need permission to start!!!", Toast.LENGTH_SHORT).show(); //Show a toast message saying the permission was denied
            }
        }
    }
}