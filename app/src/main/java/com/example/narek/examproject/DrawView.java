package com.example.narek.examproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Narek on 4/2/16.
 */
public class DrawView extends View {
    Bitmap backgroundBitmap;


    List<Circle> circles = new ArrayList<>();
    private Paint mPaint;
    private int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW };


    private Paint textPaint;


    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();

        int pointerId = event.getPointerId(pointerIndex);


        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {

                Random random = new Random();
                int tmp = random.nextInt(10);

                Circle circle = new Circle(event.getX(), event.getY(), tmp*10+20, colors[tmp]);

                circles.add(pointerId, circle);





                break;
            }
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
        }

        invalidate();


        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (backgroundBitmap != null) {

            Rect rect = new Rect(0,0,getWidth(),getHeight());
            canvas.drawBitmap(backgroundBitmap, null, rect, null);
        }

        for (int size = circles.size(), i = 0; i < size; i++) {
            Circle circle = circles.get(i);
            if (circle != null) {
                mPaint.setColor(circle.getColor());

                canvas.drawCircle(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), mPaint);
            }
        }

    }
    public void setBackgroundBitmap(Bitmap bitmap) {
        backgroundBitmap = bitmap;
    }


    public Bitmap asBitmap(){
        setDrawingCacheEnabled(true);
        buildDrawingCache();
        Bitmap drawingCache = Bitmap.createBitmap(getDrawingCache());
        setDrawingCacheEnabled(false);
        return drawingCache;
    }
}
