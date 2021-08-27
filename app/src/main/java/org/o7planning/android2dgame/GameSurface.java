package org.o7planning.android2dgame;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private ChibiCharacter chibi1;

    public GameSurface(Context context) {
        super(context);

        this.setFocusable(true);

        this.getHolder().addCallback(this);
    }

    public void update() {
        this.chibi1.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.chibi1.draw(canvas);
    }
    public void surfaceCreated(SurfaceHolder holder){
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        this.chibi1=new ChibiCharacter(this,chibiBitmap1,100,50);

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
