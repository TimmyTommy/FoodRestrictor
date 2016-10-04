package de.dthomalla.foodrestrictor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by dthom on 04.10.2016.
 */
public class DailyActivityButton extends ImageButton {

    private final int MAX_STATES=3;
    int state;
    Drawable srcEmpty;
    Drawable srcHalf;
    Drawable srcFull;
    Context context;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    public DailyActivityButton(Context context) {
        super(context);
        this.context=context;

    }

    public DailyActivityButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DailyActionButton);

        try {

            state = a
                    .getInteger(R.styleable.DailyActionButton_action_state, 0);
            srcEmpty = a
                    .getDrawable(R.styleable.DailyActionButton_src_empty);
            srcHalf = a
                    .getDrawable(R.styleable.DailyActionButton_src_half);
            srcFull = a
                    .getDrawable(R.styleable.DailyActionButton_src_full);

        } catch (Exception e) {

        } finally {
            a.recycle();
        }

        switch (state) {
            case 0:
                this.setImageDrawable(srcEmpty);
                break;
            case 1:
                this.setImageDrawable(srcHalf);
                break;
            case 2:
                this.setImageDrawable(srcFull);
                break;
            default:
                break;

        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        nextState();
        setStateBackground();
        return true;

    }

    private void nextState() {
        state++;

        if (state == MAX_STATES) {
            state = 0;
        }
    }

    private void setStateBackground() {

        switch (state) {
            case 0:
                this.setImageDrawable(srcEmpty);
                showButtonText("Repeat Empty");
                break;
            case 1:
                this.setImageDrawable(srcHalf);
                showButtonText("Repeat Half");
                break;
            case 2:
                this.setImageDrawable(srcFull);
                showButtonText("Repeat Full");

                break;
            default:
                break;

        }
    }
    public void showButtonText(String text) {

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }
    public DailyActivityState getRepeatState() {

        switch (state) {
            case 0:
                return DailyActivityState.EMPTY;
            case 1:
                return DailyActivityState.HALF;
            case 2:
                return DailyActivityState.FULL;
            default:
                return DailyActivityState.EMPTY;

        }
    }

    public void setRepeatState(DailyActivityState repeatState) {

        switch (repeatState) {
            case EMPTY:
                state=0;

                break;
            case HALF:
                state=1;
                break;
            case FULL:
                state=2;
                break;
            default:
                break;
        }

        setStateBackground();
    }

}
