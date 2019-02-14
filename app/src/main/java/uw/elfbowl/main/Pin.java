package uw.elfbowl.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import uw.elfbowl.R;


public class Pin extends Object {

    static double pos_x = 0.505;
    static double pos_y = 0.46;
    static double pin_size = 0.1;
    static double delta_x = 0.025;
    static double delta_y = 0.012;
    static double delta_time = 0.3;
    Bitmap pin_normal, pin_lay_left, pin_lay_right, pin_fly_left, pin_fly_right;
    int state;
    int number;
    int state_count;
    Rect dst_rect;
    Rect dst_rect_org;

    public Pin(Context c, int w, int h, int n) {
        number = n;
        pin_normal = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_normal);
        pin_lay_left = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_lay_left);
        pin_lay_right = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_lay_right);
        pin_fly_left = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_fly_left);
        pin_fly_right = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_fly_right);
        state = 0;
        dst_rect = new Rect((int)(w * (pos_x - pin_size / 2)), (int)(h * pos_y - w * pin_size / 2),
                (int)(w * (pos_x + pin_size / 2)), (int)(h * pos_y + w * pin_size / 2));

        switch (n) {
            case 1:
                dst_rect.offset(-(int)(w * delta_x), -(int)(h * delta_y));
                break;
            case 2:
                dst_rect.offset((int)(w * delta_x), -(int)(h * delta_y));
                break;
            case 3:
                dst_rect.offset(-(int)(w * delta_x * 2), -(int)(h * delta_y * 2));
                break;
            case 4:
                dst_rect.offset(0, -(int)(h * delta_y * 2));
                break;
            case 5:
                dst_rect.offset((int)(w * delta_x * 2), -(int)(h * delta_y * 2));
                break;
            case 6:
                dst_rect.offset(-(int)(w * delta_x * 3), -(int)(h * delta_y * 3));
                break;
            case 7:
                dst_rect.offset(-(int)(w * delta_x), -(int)(h * delta_y * 3));
                break;
            case 8:
                dst_rect.offset((int)(w * delta_x), -(int)(h * delta_y * 3));
                break;
            case 9:
                dst_rect.offset((int)(w * delta_x * 3), -(int)(h * delta_y * 3));
                break;
        }

        dst_rect_org = new Rect(dst_rect.left, dst_rect.top, dst_rect.right, dst_rect.bottom);
    }

    void draw(Canvas canvas) {
        switch (state) {
            case 0:
                canvas.drawBitmap(pin_normal, new Rect(0, 0, pin_normal.getWidth(), pin_normal.getHeight()), dst_rect, null);
                break;
            case 1:
                canvas.drawBitmap(pin_fly_left, new Rect(0, 0, pin_lay_left.getWidth(), pin_lay_left.getHeight()), dst_rect, null);
                break;
            case 2:
                canvas.drawBitmap(pin_fly_right, new Rect(0, 0, pin_lay_right.getWidth(), pin_lay_right.getHeight()), dst_rect, null);
                break;
            case 3:
                canvas.drawBitmap(pin_lay_left, new Rect(0, 0, pin_fly_left.getWidth(), pin_fly_left.getHeight()), dst_rect, null);
                break;
            case 4:
                canvas.drawBitmap(pin_lay_right, new Rect(0, 0, pin_fly_right.getWidth(), pin_fly_right.getHeight()), dst_rect, null);
                break;
        }
    }

    void clear() {
        state = 0;
        state_count = 0;
        dst_rect = new Rect(dst_rect_org.left, dst_rect_org.top, dst_rect_org.right, dst_rect_org.bottom);
    }

    void update() {
        if (state == 1) {
            dst_rect = new Rect(dst_rect_org.left, dst_rect_org.top, dst_rect_org.right, dst_rect_org.bottom);
            double offset_x = 0;
            double offset_y = 0;

            if (state >= 12)
                offset_y = - delta_time * (144 - Math.pow(state_count - 12, 2));
            else
                offset_y = - delta_time * (144 - Math.pow(12 - state_count, 2));
            dst_rect.offset((int)offset_x, (int)offset_y);

            if (state_count >= 0)
                state_count --;
            else {
                state = 3;
                state_count = 24;
            }
        } else if (state == 2) {
            dst_rect = new Rect(dst_rect_org.left, dst_rect_org.top, dst_rect_org.right, dst_rect_org.bottom);
            double offset_x = 0;
            double offset_y = 0;

            if (state >= 12)
                offset_y = - delta_time * (144 - Math.pow(state_count - 12, 2));
            else
                offset_y = - delta_time * (144 - Math.pow(12 - state_count, 2));
            dst_rect.offset((int)offset_x, (int)offset_y);

            if (state_count >= 0)
                state_count --;
            else {
                state = 4;
                state_count = 24;
            }
        } else if (state == 3 || state == 4) {
            if (state_count >= 0)
                state_count --;
            else
                state = 5;
        }
    }
}
