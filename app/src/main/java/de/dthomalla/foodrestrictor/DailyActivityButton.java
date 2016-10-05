package de.dthomalla.foodrestrictor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by dthom on 04.10.2016.
 */
public class DailyActivityButton extends ImageButton {

    private final int MAX_STATES= DailyActivityState.values().length;
    int state = DailyActivityState.EMPTY.getValue();

    public DailyActivityButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (state != 0){
            float width = getMeasuredWidth();
            float height = getMeasuredHeight();
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(7);
            paint.setStyle(Paint.Style.STROKE);
            int offset = 5;
            int offset2 = offset+3;

            Path path1 = new Path();
            path1.reset();
            path1.moveTo(offset2, offset2);
            path1.lineTo(width - offset2, offset2);
            path1.lineTo(width - offset2, height - offset2);
            path1.lineTo(offset2, offset2);

            Path path2 = new Path();
            path2.reset();
            path2.moveTo(offset2, offset2);
            path2.lineTo(offset2, height - offset2);
            path2.lineTo(width - offset2, height - offset2);
            path2.lineTo(offset2, offset2);

            paint.setColor(Color.DKGRAY);
            paint.setStyle(Paint.Style.FILL);

            paint.setAlpha(128);
            if (state == 2) {
                paint.setAlpha(220);
                canvas.drawLine(offset2, offset2, width - offset2, height - offset2, paint);
                canvas.drawLine(width - offset2, offset2, offset2, height - offset2, paint);
                canvas.drawPath(path1, paint);
            }
            canvas.drawPath(path2, paint);
            paint.setAlpha(255);

            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(offset, offset, width - offset, height - offset, 15, 15, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        nextState();
        return true;
    }

    private void nextState() {
        state++;
        if (state == MAX_STATES) {
            state = 0;
        }
        invalidate();
    }

    public DailyActivityState getState() {
        return DailyActivityState.parseInt(state);
//        switch (state) {
//            case 0:
//                return DailyActivityState.EMPTY;
//            case 1:
//                return DailyActivityState.HALF;
//            case 2:
//                return DailyActivityState.FULL;
//            default:
//                return DailyActivityState.EMPTY;
//        }
    }

    public void setState(DailyActivityState state) {
        this.state = state.getValue();
//        switch (state) {
//            case EMPTY:
//                this.state=0;
//                break;
//            case HALF:
//                this.state=1;
//                break;
//            case FULL:
//                this.state=2;
//                break;
//            default:
//                break;
//        }
        invalidate();
    }

}
