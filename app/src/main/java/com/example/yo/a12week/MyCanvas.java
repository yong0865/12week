package com.example.yo.a12week;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by yo on 2017-05-18.
 */

public class MyCanvas extends View {
    Rect rect;

    public MyCanvas(Context context) {
        super(context);
    }
    public MyCanvas(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }
    protected void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        rect = new Rect(10,10,100,100);
        canvas.drawRect(10,10,100,100,paint);

        int width = canvas.getWidth()/2-45;
        int height = canvas.getHeight()/2-45;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        canvas.drawRect(width,height,width+90,height+90,paint);

        paint.setTextSize(70);
        canvas.drawText("Click Me !!",300,300,paint);

        Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        canvas.drawBitmap(img,300,350,paint);
        canvas.drawBitmap(img,400,350,paint);

        Bitmap smallimg = Bitmap.createScaledBitmap(img,img.getWidth()/2,img.getHeight()/2,false);
        canvas.drawBitmap(smallimg,400,350,paint);

        Bitmap bigimg = Bitmap.createScaledBitmap(img,img.getWidth()*2,img.getHeight()*2,false);
        canvas.drawBitmap(bigimg,100,150,paint);
        img.recycle();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();

//        if(rect.contains(x,y))
        if(x >= 10 && x <=100 && y>=10 && y<=100){
            Toast.makeText(getContext(), "RED BUTTON", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(), "background" , Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
