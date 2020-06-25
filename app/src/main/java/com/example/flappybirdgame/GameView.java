package com.example.flappybirdgame;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {
//custom view class
    Handler handler;//schedulea runnable after delay
    Runnable runnable;
    final int  UPDATE_MILLIS=30;
    Bitmap background;
    Bitmap toptube,bottomtube;
    Display display;
    Point point;
    int dWidth,dHeight;
    Rect rect;
    Bitmap[] birds;
    int birdFrame=0;
    int velocity=0,gravity=3;
    int birdx,birdy;
    boolean gameState=false;
    int gap=400;
    int minTubeOffset,maxTubeOffset;
    int numberOfTubes=4;
    int distanceBetweenTubes;
int[] tubex=new int[numberOfTubes];
int[] topTubeY=new int[numberOfTubes];
Random random;
int tubevelocity=8;
    public GameView(Context context) {
        super(context);
        handler=new Handler();
        runnable=new Runnable(){
            @Override
            public void run(){
                invalidate();//calls onDraw();
            }
        };
        background= BitmapFactory.decodeResource(getResources(),R.drawable.bg_5);
        toptube=BitmapFactory.decodeResource(getResources(),R.drawable.vbig);
        bottomtube=BitmapFactory.decodeResource(getResources(),R.drawable.vbig);
        display=((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point=new Point();
        display.getSize(point);
        dWidth=point.x;
        dHeight=point.y;
        rect=new Rect(0,0,dWidth,dHeight);
        birds=new Bitmap[2];
        birds[0]=BitmapFactory.decodeResource(getResources(),R.drawable.l1);
        birds[1]=BitmapFactory.decodeResource(getResources(),R.drawable.l2);
birdx=(dWidth/2-birds[0].getWidth()/2);
birdy=(dHeight/2-birds[0].getHeight()/2);
distanceBetweenTubes=dWidth*3/4;
minTubeOffset=gap/2;
maxTubeOffset=dHeight-minTubeOffset-gap;
random=new Random();
for(int i=0;i<numberOfTubes;i++){
    tubex[i]=dWidth+i*distanceBetweenTubes;
    topTubeY[i]=minTubeOffset+random.nextInt(maxTubeOffset-minTubeOffset+1);

}


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background,null,rect,null);
        if(birdFrame==0)
            birdFrame=1;
        else
            birdFrame=0;
        if(gameState) {
            if (birdy < dHeight - birds[0].getHeight() || velocity < 0) {
                velocity += gravity;
                birdy += velocity;
            }

            for (int i = 0; i < numberOfTubes; i++) {
                tubex[i]-=tubevelocity;
                if(tubex[i]< -toptube.getWidth()) {
                    tubex[i] += numberOfTubes * distanceBetweenTubes;
                    topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1);

                }
                canvas.drawBitmap(toptube, tubex[i], topTubeY[i] - toptube.getWidth(), null);
                canvas.drawBitmap(bottomtube, tubex[i], topTubeY[i] + gap, null);
            }
        }
canvas.drawBitmap(birds[birdFrame],birdx,birdy,null);
        handler.postDelayed(runnable,UPDATE_MILLIS);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        if(action==MotionEvent.ACTION_DOWN){
            velocity=-30;
     gameState=true;


        }
        return true;

    }
}
