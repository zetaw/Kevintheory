package my_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.ImageView;

/*
 * Copyright (c) 2015.
 * Coded by Tesla.
 * All rights reserved.
 */
public class PointOnTheScreen extends ImageView {

    private Paint paint = new Paint();
    private int action;
    private float x = 0;
    private float y = 0;

    public PointOnTheScreen(Context context) {
        super(context);
        action = MotionEvent.ACTION_UP;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint localPaint = paint;
//        canvas.drawColor(Color.BLUE);

        switch (action){
            case MotionEvent.ACTION_DOWN:
                localPaint.setColor(Color.GREEN);
                break;
            case MotionEvent.ACTION_UP:
                localPaint.setColor(Color.YELLOW);
                break;
            case MotionEvent.ACTION_MOVE:
                localPaint.setColor(Color.RED);
                break;
            default:
                break;
        }

        canvas.drawCircle(x,y,10,localPaint);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        action = event.getAction();
        x = event.getX();
        y = event.getY();
        invalidate();
        return super.onTouchEvent(event);
    }
}
