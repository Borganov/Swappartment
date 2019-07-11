package ch.hevs.swap.ui.swipe;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.GestureDetector;
import android.view.GestureDetector.*;
import android.view.MotionEvent;
import android.view.View.OnClickListener;

import ch.hevs.swap.R;

public class SwipeActivity extends AppCompatActivity{

    private static final int SWIPE_MIN_DISTANCE = 10;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe);

        final ImageView imageView = findViewById(R.id.imageView);


        imageView.setOnTouchListener(new OnSwipeTouchListener(SwipeActivity.this) {

            public void onSwipeTop() {
                Toast.makeText(SwipeActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                imageView.setImageResource(R.drawable.home2);
                Toast.makeText(SwipeActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                imageView.setImageResource(R.drawable.home1);
                Toast.makeText(SwipeActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(SwipeActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }
}

/*
        imageView.setOnClickListener(this);
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        imageView.setOnTouchListener(gestureListener);
    }

        class MyGestureDetector extends SimpleOnGestureListener {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final ImageView imageView = findViewById(R.id.imageView);
                try {
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Toast.makeText(SwipeActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
                        imageView.setTranslationX(e2.getX());
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Toast.makeText(SwipeActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
                        imageView.setTranslationX(e1.getX());
                    }
                } catch (Exception e) {
                    // nothing
                }
                return false;

            }
 */
