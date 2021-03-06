package org.o7planning.android2dgame;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private final List<ChibiCharacter>chibiList=new ArrayList<ChibiCharacter>();

    public GameSurface(Context context) {
        super(context);

        this.setFocusable(true);

        this.getHolder().addCallback(this);
    }

    public void update() {
        for (ChibiCharacter chibi : chibiList) {
            chibi.update();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            int x=(int)event.getX();
            int y=(int)event.getY();

            for (ChibiCharacter chibi :chibiList){
                int movingVectorX=x-chibi.getX();
                int movingVectorY=y-chibi.getY();
                chibi.setMovingVector(movingVectorX,movingVectorY);
            }
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for(ChibiCharacter chibi:chibiList){
            chibi.draw(canvas);
        }
    }
    public void surfaceCreated(SurfaceHolder holder){
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        ChibiCharacter chibi1 = new ChibiCharacter(this,chibiBitmap1,100,50);

        Bitmap chibiBitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi2);
        ChibiCharacter chibi2 = new ChibiCharacter(this,chibiBitmap2,300,150);

        this.chibiList.add(chibi1);
        this.chibiList.add(chibi2);
        this.gameThread=new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int widht, int height){

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry=true;
        while (retry){
            try{
                this.gameThread.setRunning(false);
                this.gameThread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            } retry=true;
        }
    }

}
