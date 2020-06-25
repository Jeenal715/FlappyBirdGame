package com.example.flappybirdgame;
import android.app.Activity;
import android.os.Bundle;

import org.jetbrains.annotations.Nullable;


public class StartGame extends Activity{
    GameView gameView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gameView=new GameView(this);
        setContentView(gameView);
    }
}
