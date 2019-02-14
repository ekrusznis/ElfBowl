package uw.elfbowl.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;


public class Score extends Object{
    int score[][];
    int pointer;
    Rect dst_rect;

    public Score(int w, int h) {

        score = new int[10][4];
        clear();

        dst_rect = new Rect();
        dst_rect.left = 0;
        dst_rect.right = w;
        dst_rect.top = (int)(h * 0.083);
        dst_rect.bottom = (int)(h * 0.083 + w * 0.125);
    }

    void clear() {
        for (int i = 0; i < 10; i ++)
            score[i][0] = score[i][1] = score[i][2] = score[i][3] = -1;
        pointer = 0;
    }

    void draw(Canvas canvas) {
        if (score[0][0] == -1)
            return;

        double grid_width = (double)dst_rect.width() * 0.099;
        double grid_height = (double)dst_rect.height() * 2 / 3;
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create("Cooper", Typeface.BOLD));
        paint.setColor(Color.BLACK);
        paint.setTextSize(27);

        for (int i = 0; i < 9; i ++) {
            if (score[i][0] == -1)
                break;
            if (score[i][0] == 0) {
                canvas.drawText("-", (float)(dst_rect.left + grid_width * i + grid_width / 2), (float)(dst_rect.top + grid_height / 2), paint);
            } else if (score[i][0] == 10) {
                canvas.drawText("X", (float)(dst_rect.left + grid_width * i + grid_width * 5 / 6), (float)(dst_rect.top + grid_height / 2), paint);
                continue;
            } else {
                canvas.drawText(String.format("%d", score[i][0]), (float)(dst_rect.left + grid_width * i + grid_width / 3), (float)(dst_rect.top + grid_height / 2), paint);
            }

            if (score[i][1] == -1)
                break;

            if (score[i][1] == 0) {
                canvas.drawText("-", (float)(dst_rect.left + grid_width * i + grid_width * 5 / 6), (float)(dst_rect.top + grid_height / 2), paint);
            } else if (score[i][0] + score[i][1] == 10){
                canvas.drawText("/", (float)(dst_rect.left + grid_width * i + grid_width * 5 / 6), (float)(dst_rect.top + grid_height / 2), paint);
            } else {
                canvas.drawText(String.format("%d", score[i][1]), (float)(dst_rect.left + grid_width * i + grid_width * 5 / 6), (float)(dst_rect.top + grid_height / 2), paint);
            }
        }

        for (int i = 0; i < 9; i ++) {
            if (score[i][3] != -1) {
                canvas.drawText(String.format("%d", score[i][3]), (float)(dst_rect.left + grid_width * i + grid_width / 6), (float)(dst_rect.top + grid_height), paint);
            }
        }

        if (score[9][0] >= 0) {
            if (score[9][0] == 0) {
                canvas.drawText("-", (float)(dst_rect.left + grid_width * 9 + grid_width / 6), (float)(dst_rect.top + grid_height / 2), paint);
            } else if (score[9][0] == 10) {
                canvas.drawText("X", (float)(dst_rect.left + grid_width * 9 + grid_width / 6), (float)(dst_rect.top + grid_height / 2), paint);
            } else {
                canvas.drawText(String.format("%d", score[9][0]), (float)(dst_rect.left + grid_width * 9 + grid_width / 6), (float)(dst_rect.top + grid_height / 2), paint);
            }
        }

        if (score[9][1] >= 0) {
            if (score[9][1] == 0) {
                canvas.drawText("-", (float)(dst_rect.left + grid_width * 9 + grid_width / 2), (float)(dst_rect.top + grid_height / 2), paint);
            } else if (score[9][1] == 10) {
                canvas.drawText("X", (float)(dst_rect.left + grid_width * 9 + grid_width / 2), (float)(dst_rect.top + grid_height / 2), paint);
            } else if (score[9][0] + score[9][1] == 10) {
                canvas.drawText("/", (float)(dst_rect.left + grid_width * 9 + grid_width / 2), (float)(dst_rect.top + grid_height / 2), paint);
            } else {
                canvas.drawText(String.format("%d", score[9][1]), (float)(dst_rect.left + grid_width * 9 + grid_width / 2), (float)(dst_rect.top + grid_height / 2), paint);
            }
        }

        if (score[9][2] >= 0) {
            if (score[9][2] == 0) {
                canvas.drawText("-", (float)(dst_rect.left + grid_width * 9 + grid_width * 5 / 6), (float)(dst_rect.top + grid_height / 2), paint);
            } else if (score[9][2] == 10) {
                canvas.drawText("X", (float)(dst_rect.left + grid_width * 9 + grid_width * 5 / 6), (float)(dst_rect.top + grid_height / 2), paint);
            } else if (score[9][1] + score[9][2] == 10) {
                canvas.drawText("/", (float)(dst_rect.left + grid_width * 9 + grid_width * 5 / 6), (float)(dst_rect.top + grid_height / 2), paint);
            } else {
                canvas.drawText(String.format("%d", score[9][2]), (float)(dst_rect.left + grid_width * 9 + grid_width * 5 / 6), (float)(dst_rect.top + grid_height / 2), paint);
            }
        }

        if (score[9][3] != -1) {
            canvas.drawText(String.format("%d", score[9][3]), (float)(dst_rect.left + grid_width * 9 + grid_width / 6), (float)(dst_rect.top + grid_height), paint);
        }
    }

    void calc() {
        for (int i = 0; i < 10; i ++) {
            if (i == 9) {
                if (score[i][0] == -1 || score[i][1] == -1 || ((score[i][0] == 10 || score[i][0] + score[i][1] == 10) && score[i][2] == -1))
                    break;
                if (score[i][0] == 10 || score[i][0] + score[i][1] == 10) {
                    score[i][3] = score[i - 1][3] + score[i][0] + score[i][1] + score[i][2];
                } else {
                    score[i][3] = score[i - 1][3] + score[i][0] + score[i][1];
                }
            }

            if (score[i][3] != -1)
                continue;
            if (score[i][0] == -1 || (score[i][0] != 10 &&score[i][1] == -1))
                break;
            if (score[i][0] == 10) {
                int bonus = 0;
                if (score[i + 1][0] == 10) {
                    if (i < 8) {
                        if (score[i + 1][0] == -1 || score[i + 2][0] == -1)
                            break;
                        bonus = score[i + 1][0] + score[i + 2][0];
                    }
                    else {
                        if (score[i + 1][0] == -1 || score[i + 1][1] == -1)
                            break;
                        bonus = score[i + 1][0] + score[i + 1][1];
                    }
                } else {
                    if (score[i + 1][0] == -1 || score[i + 1][1] == -1)
                        break;
                    bonus = score[i + 1][0] + score[i + 1][1];
                }
                score[i][3] = (i > 0? score[i - 1][3]: 0) + 10 + bonus;
            } else if (score[i][0] + score[i][1] == 10) {
                int bonus = 0;
                if (score[i + 1][0] == -1)
                    break;
                bonus = score[i + 1][0];
                score[i][3] = (i > 0? score[i - 1][3]: 0) + 10 + bonus;
            } else {
                score[i][3] = (i > 0? score[i - 1][3]: 0) + score[i][0] + score[i][1];
            }
        }
    }
}
