package uw.elfbowl.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;



public class PlayView extends View {
    Timer timer;
    PlayModel playModel;
    InteractionModel interModel;
    PlayViewController viewController;
    int width;
    int height;
    Context context;

    public PlayView(Context c) {
        super(c);

        context = c;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        playModel = new PlayModel(context, width, height);
        interModel = new InteractionModel();
        viewController = new PlayViewController(this);
        setOnTouchListener(viewController);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 40);
    }

    public void clear() {

    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        playModel.santa.draw(canvas);
        playModel.score.draw(canvas);
        playModel.arrowbar.draw(canvas);
        playModel.ball.draw(canvas);

        for (int i = 9; i >= 0; i --)
            playModel.pins[i].draw(canvas);
    }

    public void onThrow() {
        if (playModel.total_state == Global.READY_STATE) {
            playModel.ball.clear();
            playModel.ball.set_delta((int) playModel.arrowbar.arrow);
            playModel.total_state = Global.ROLLING_STATE;
        }
    }

    public void tick() {
        playModel.update();
        this.postInvalidate();
    }
}
