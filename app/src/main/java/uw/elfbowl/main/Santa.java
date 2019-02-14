package uw.elfbowl.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import uw.elfbowl.R;


public class Santa extends Object {
    Bitmap santa_normal;
    int state;
    Rect dst_rect;

    public Santa(Context c, int w, int h) {

        santa_normal = BitmapFactory.decodeResource(c.getResources(), R.drawable.santa_normal);
        state = 0;
        dst_rect = new Rect();
        dst_rect.left = 0;
        dst_rect.top = (int)(h * 0.75);
        dst_rect.right = (int)(h * (double)santa_normal.getWidth() / santa_normal.getHeight() * 0.25);
        dst_rect.bottom = h;
    }


    void draw(Canvas canvas) {

        canvas.drawBitmap(santa_normal, new Rect(0, 0, santa_normal.getWidth(), santa_normal.getHeight()), dst_rect, null);

    }
}
