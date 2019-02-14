package uw.elfbowl.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;


public class ArrowBar extends Object {
    int arrow;
    int delta;
    int delta_org;
    Rect dst_rect;

    public ArrowBar(int w, int h) {
        arrow = 0;
        if (Global.difficulty == Global.EASY_MODE)
            delta = 1;
        else if (Global.difficulty == Global.NORMAL_MODE)
            delta = 2;
        else
            delta = 3;
        delta_org = delta;
        dst_rect = new Rect((int)(w * 0.25), (int)(h * 0.75 - w * 0.1), (int)(w * 0.75), (int)(h * 0.75));
    }
    void clear() {
        arrow = 0;
        delta = delta_org;
    }
    void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 89, 7, 2));
        paint.setStyle(Paint.Style.FILL);

        double arrow_width = (double)dst_rect.width() / 29;
        double arrow_height = (double)dst_rect.height() / 3;

        Path path = new Path();

        path.reset();
        path.moveTo((int) (dst_rect.left + arrow_width * 14), (int) (dst_rect.top + 1.5 * arrow_height));
        path.lineTo((int)(dst_rect.left + arrow_width * 14.5), (int)(dst_rect.top + 0.5 * arrow_height));
        path.lineTo((int) (dst_rect.left + arrow_width * 15), (int) (dst_rect.top + 1.5 * arrow_height));
        canvas.drawPath(path, paint);

        for (int i = 0; i < 15; i ++) {
            path.reset();
            path.moveTo((int) (dst_rect.left + arrow_width * i * 2), (int) dst_rect.bottom);
            path.lineTo((int) (dst_rect.left + arrow_width * (i * 2 + 0.5)), (int) (dst_rect.bottom - arrow_height));
            path.lineTo((int)(dst_rect.left + arrow_width * (i * 2 + 1)), (int)dst_rect.bottom);
            canvas.drawPath(path, paint);
        }

        if (arrow % 2 == 0) {
            paint.setColor(Color.YELLOW);
            path.reset();
            path.moveTo((int) (dst_rect.left + arrow_width * arrow), (int) dst_rect.bottom);
            path.lineTo((int) (dst_rect.left + arrow_width * (arrow + 0.5)), (int) (dst_rect.bottom - arrow_height));
            path.lineTo((int)(dst_rect.left + arrow_width * (arrow + 1)), (int)dst_rect.bottom);
            canvas.drawPath(path, paint);
        } else {
            paint.setColor(Color.argb(255, 252, 79, 4));

            path.reset();
            path.moveTo((int) (dst_rect.left + arrow_width * (arrow - 1)), (int) dst_rect.bottom);
            path.lineTo((int) (dst_rect.left + arrow_width * ((arrow - 1) + 0.5)), (int) (dst_rect.bottom - arrow_height));
            path.lineTo((int)(dst_rect.left + arrow_width * ((arrow - 1)  + 1)), (int)dst_rect.bottom);
            canvas.drawPath(path, paint);

            path.reset();
            path.moveTo((int) (dst_rect.left + arrow_width * (arrow + 1)), (int) dst_rect.bottom);
            path.lineTo((int) (dst_rect.left + arrow_width * ((arrow + 1) + 0.5)), (int) (dst_rect.bottom - arrow_height));
            path.lineTo((int)(dst_rect.left + arrow_width * ((arrow + 1) + 1)), (int)dst_rect.bottom);
            canvas.drawPath(path, paint);
        }
    }

    public void update() {
        arrow += delta;
        if (arrow > 28 || arrow < 0) {
            delta *= -1;
            arrow += delta;
        }
    }
}
