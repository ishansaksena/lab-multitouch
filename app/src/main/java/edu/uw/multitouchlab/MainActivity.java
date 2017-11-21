package edu.uw.multitouchlab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    private DrawingSurfaceView view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (DrawingSurfaceView)findViewById(R.id.drawingView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionIndex = event.getActionIndex();
        int fingerPointer = event.getPointerId(actionIndex);
        Log.v(TAG, "The motionEvent ID is: " + actionIndex);
        Log.v(TAG, "The motionEvent ID is: " + fingerPointer);

        float x = event.getX(fingerPointer);
        float y = event.getY(fingerPointer) - getSupportActionBar().getHeight(); //closer to center...

        int action = event.getActionMasked();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) : //Put first finger down
                Log.v(TAG, "First finger down");
                view.ball.cx = x;
                view.ball.cy = y;
                view.addTouch(fingerPointer, x, y, 100);
                return true;
            case (MotionEvent.ACTION_POINTER_DOWN) : // Subsequent finger down
                Log.v(TAG, "subsequent finger down");
                view.addTouch(fingerPointer, x, y, 100);
                return true;

            case (MotionEvent.ACTION_MOVE) : //move finger
                Log.v(TAG, "finger move");
                view.ball.cx = x;
                view.ball.cy = y;
                view.moveTouch(fingerPointer, x, y);
                return true;

            case (MotionEvent.ACTION_POINTER_UP) : //lift not last finger up
                Log.v(TAG, "non last finger up");
                view.removeTouch(fingerPointer);
                return true;
            case (MotionEvent.ACTION_UP) : //lift last finger up
                Log.v(TAG, "last finger up");
                view.removeTouch(fingerPointer);
                return true;

            case (MotionEvent.ACTION_CANCEL) : //aborted gesture
            case (MotionEvent.ACTION_OUTSIDE) : //outside bounds
            default :
                return super.onTouchEvent(event);
        }
    }
}