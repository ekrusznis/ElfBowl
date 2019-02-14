package uw.elfbowl.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import uw.elfbowl.R;

/**
 * Created by Astro on 2018-11-14.
 */
public class Ball extends Object {
    Bitmap ball;
    Point pos;
    boolean arrived;
    boolean started;
    int radius;
    int radius_org;
    double delta_x;
    double delta_y;
    double delta_dy;
    static int width;
    static int height;
    static Point pos_org;
    static Point finish_line_left;
    static Point finish_line_right;
    static Point start_line_left;
    static Point start_line_right;
    static double ball_size = 0.1;

    public Ball(Context c, int w, int h) {
        ball = BitmapFactory.decodeResource(c.getResources(), R.drawable.ball);
        arrived = false;
        started = false;

        radius = (int)(w * ball_size);
        radius_org = radius;
        height = h;
        width = w;
        pos = new Point((int)(w * 0.5), (int)(h + radius));
        pos_org = new Point((int)(w * 0.5), (int)(h + radius));
        finish_line_left = new Point((int)(width * 0.4), (int)(height * 0.45));
        finish_line_right = new Point((int)(width * 0.61), (int)(height * 0.45));
        start_line_left = new Point(0, height);
        start_line_right = new Point(width, height);
    }

    void clear() {
        pos = new Point(pos_org.x, pos_org.y);
        radius = radius_org;
        arrived = false;
    }

    void draw(Canvas canvas) {
        if (!arrived) {
            int r = (int)((double)radius * (0.2 + 0.8 * (double)(pos.y - finish_line_left.y) / (double)(pos_org.y - finish_line_left.y)));
            canvas.drawBitmap(ball, new Rect(0, 0, ball.getWidth(), ball.getHeight()), new Rect(pos.x - r, pos.y - r, pos.x + r, pos.y + r), null);
        }
    }

    void set_delta(int a) {
        double unit = width * 0.0005;
        delta_x = width * 0.001 + unit * (a - 14);
        delta_y = -width * 0.045;
        delta_dy = -delta_y / 60;
    }

    void update() {
        pos.y += delta_y;
        delta_y += delta_dy;

        pos.x += delta_x;
        double x_min = finish_line_left.x - finish_line_left.x * (double)(pos.y - finish_line_left.y) / (height - finish_line_left.y);
        double x_max = finish_line_right.x + (width - finish_line_right.x) * (double)(pos.y - finish_line_left.y) / (height - finish_line_left.y);

        if (pos.x < x_min) {
            pos.x = (int)x_min;
            Global.gutter_ball = true;
        }
        else if (pos.x > x_max) {
            pos.x = (int)x_max;
            Global.gutter_ball = true;
        }

        if (pos.y < finish_line_left.y) {
            arrived = true;
        }
    }
}
