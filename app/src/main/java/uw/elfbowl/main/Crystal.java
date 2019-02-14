package javaelfbowling.com.elfbowling;

import android.content.Context;

/**
 * Created by Astro on 2018-11-19.
 */
public class Crystal extends Object {
/*    Bitmap crystal;
    Point pos;
    double size;
    double speed;
    int width;
    int height;
    boolean finished;*/

    public Crystal(Context c, int w, int h) {
/*        crystal = BitmapFactory.decodeResource(c.getResources(), R.drawable.crystal);
        width = w;
        height = h;
        size = h * (Math.random() * 0.04 + 0.01);
        speed = h * (Math.random() * 0.004 + 0.001);
        pos = new Point((int)(Math.random() * w), - (int)size / 2);
        finished = false;*/
    }

/*    public void update() {
        if (pos.y > crystal.getHeight() / 2 + height)
            finished = true;
        else
            pos.y += speed;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(crystal, new Rect(0, 0, crystal.getWidth(), crystal.getHeight()),
                new Rect((int)(pos.x - size / 2), (int)(pos.y - size / 2), (int)(pos.x + size / 2), (int)(pos.y + size / 2)), null);
    }*/
}
