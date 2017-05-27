package com.iiordanov.bVNC.waishon;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.iiordanov.bVNC.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Sören on 12.04.2017.
 */

public class MenuTouchEventHandler {

    private static final int MIN_MOVEMENT = 40;

    private Context context;
    private View movableView;
    private View touchView;
    private View.OnClickListener onClickListener;

    private WindowManager windowManager;
    private int displayWidth;
    private float dX;
    private boolean hasMoved = false;
    private float startX = 0;

    public MenuTouchEventHandler(Context context, View touchView, View movableView, View.OnClickListener onClickListener) {
        this.context = context;
        this.touchView = touchView;
        this.movableView = movableView;
        this.onClickListener = onClickListener;

        this.touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return onTouchEvent(motionEvent);
            }
        });

        initalize();
    }

    public void initalize() {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point size = new Point();
            display.getRealSize(size);

            displayWidth = size.x;
        } else {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");

                displayWidth = (Integer) mGetRawW.invoke(display);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                hasMoved = false;
                dX = this.movableView.getX() - event.getRawX();
                startX = event.getRawX();

                // Background setzen
                this.touchView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorMenuBackgroundHighlight));
                return true;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(startX - event.getRawX()) > MIN_MOVEMENT)
                    hasMoved = true;

                if (event.getRawX() + dX < 0 || event.getRawX() + dX + this.movableView.getMeasuredWidth() > displayWidth)
                    return true;

                this.movableView.animate().x(event.getRawX() + dX).setDuration(0).start();
                return true;

            case MotionEvent.ACTION_UP:
                if (!hasMoved)
                    onClickListener.onClick(this.touchView);

                this.touchView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
                return true;

            default:
                return false;
        }
    }
}
