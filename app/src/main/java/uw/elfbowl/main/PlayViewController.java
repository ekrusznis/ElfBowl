package uw.elfbowl.main;

import android.view.MotionEvent;
import android.view.View;


public class PlayViewController implements View.OnTouchListener{
    PlayView parentView;

    public PlayViewController(PlayView view) {
        parentView = view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                parentView.onThrow();
                break;
        }

        parentView.invalidate();
        return true;
    }
}
