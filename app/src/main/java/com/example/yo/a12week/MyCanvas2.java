package com.example.yo.a12week;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.R.attr.startX;
import static android.R.attr.startY;

/**
 * Created by yo on 2017-05-18.
 */

public class MyCanvas2 extends View{
    String operationType = "";

    public MyCanvas2(Context context) {
        super(context);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }
    public MyCanvas2(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint paint = new Paint();
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Bitmap bigimg = Bitmap.createScaledBitmap(img, img.getWidth()*2,img.getHeight()*2,false);

        int cenX = canvas.getWidth() - img.getWidth()/2;
        int cenY = canvas.getHeight() - img.getHeight()/2;
//
//        if(operationType.equals("rotate"))
//        canvas.rotate(45,this.getWidth()/2,getWidth()/2);
//
//        BlurMaskFilter blur = new BlurMaskFilter(100, BlurMaskFilter.Blur.NORMAL);
//        paint.setMaskFilter(blur);
        float[] matrixarray = {
                2f, 0f, 0f, 0f, -25f,
                0f, 2f, 0f, 0f, -25f,
                0f, 0f, 2f, 0f, -25f,
                0f, 0f, 0f, 1f, 0f,
        };
        ColorMatrix matrix = new ColorMatrix(matrixarray);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));

        canvas.drawBitmap(bigimg, 100  ,100,null);
    }

    public void setOperationType(String operationType){
        this.operationType = operationType;

    }
}
