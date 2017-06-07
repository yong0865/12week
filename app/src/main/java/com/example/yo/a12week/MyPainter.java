package com.example.yo.a12week;

import android.content.ComponentName;
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
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yo on 2017-05-18.
 */

public class MyPainter extends View{
    int pen_color = Color.BLACK;
    int Size;
    int check;
    String Move = "";
    String Scale = "";
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint = new Paint();

    public MyPainter(Context context) {
        super(context);
        mPaint.setColor(Color.BLACK);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    public MyPainter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);

    }

    private void drawStamp(int x, int y) {
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        if (Scale.equals("scale")) {
            Bitmap bigimg = Bitmap.createScaledBitmap(img, (int) (img.getWidth() * 1.5), (int) (img.getHeight() * 1.5), false);
            img = bigimg;
        }
        if (Move.equals("move")) {
            x += 10;
            y += 10;
        }
        mCanvas.drawBitmap(img, x, y, mPaint);
    }

    int oldX = -1, oldY = -1;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null)
            canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mPaint.setStrokeWidth(Size);
        mPaint.setColor(pen_color);
        int X = (int) event.getX();
        int Y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldX = X;
            oldY = Y;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (oldX != -1) {
                if (check != 1) {
                    mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                }
                invalidate();
                oldX = X;
                oldY = Y;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (oldX != -1) {
                if (check == 1) {
                    drawStamp(X, Y);
                } else {
                    mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                }
                invalidate();
            }
            oldX = -1;
            oldY = -1;
            invalidate();
        }
        return true;
    }
    public void checking(int check){
        this.check = check;
    }

    public void Bluring(int Blur) {
        if (Blur == 1) {
            BlurMaskFilter blur = new BlurMaskFilter(100, BlurMaskFilter.Blur.NORMAL);
            mPaint.setMaskFilter(blur);
        } else if (Blur == 0) {
            mPaint.setMaskFilter(null);
        }
    }

    public void Coloring(int Color) {
        if (Color == 1) {
            float[] matrixarray = {
                    1f, 0f, 0f, 0f, -25f,
                    0f, 2f, 0f, 0f, -25f,
                    0f, 0f, 0f, 0f, -25f,
                    0f, 0f, 0f, 1f, 0f,
            };
            ColorMatrix matrix = new ColorMatrix(matrixarray);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            mPaint.setColorFilter(filter);
        } else if (Color == 0) {
            mPaint.setColorFilter(null);
        }
    }

    public void Scaling(String Scale) {
        this.Scale = Scale;
    }

    public void Rotating(String Rotate) {
        int angle = 0;
        if (Rotate.equals("rotate")) {
            angle = 45;
        } else {
            angle -= 45;
        }
        mCanvas.rotate(angle, mCanvas.getWidth() / 2, mCanvas.getHeight() / 2);
        invalidate();
    }

    public void Moving(String Move) {
        this.Move = Move;
    }

    public void Skewing(String Skew) {
        float x = 0;
        float y = 0;
        if (Skew.equals("skew")) {
            x = 0.2f;
        } else {
            x -= 0.2f;
        }
        mCanvas.skew(x, y);
    }

    public void setPensize(int Size) {
        this.Size = Size;
    }
    public void setPenColor(int pen_color) {
        this.pen_color = pen_color;
    }

    public boolean Save(String file_name) {
        try {
            FileOutputStream out = new FileOutputStream(file_name);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Toast.makeText(getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return false;
    }

    public void Openfile(String filename) {
        try {
            String imgpath = filename;
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            bm = Bitmap.createScaledBitmap(bm, bm.getWidth() / 2, bm.getHeight() / 2, false);
            mCanvas.drawBitmap(bm, mCanvas.getWidth() / 2 - bm.getWidth() / 2, mCanvas.getHeight() / 2 - bm.getHeight() / 2, mPaint);
            invalidate();
            Toast.makeText(getContext(), "파일이 열렸습니다", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "파일이 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear() {
        mBitmap.eraseColor(Color.YELLOW);
        pen_color = Color.BLACK;
        invalidate();
    }
}
