package uw.elfbowl.main;

import android.content.Context;
import android.util.Log;


public class PlayModel {
    int total_state;
    int total_state_count;
    int[][][] pins_vector = {{{6}},
            {{3, 7}, {6}},
            {{1, 4, 8}, {3, 6}, {7}},
            {{0, 2}, {1, 3, 6}, {4, 8}, {7}},
            {{0, 2, 5, 9}, {1, 3, 6}, {4, 8}, {7}},
            {{0, 1, 2}, {1, 3}, {2, 5}, {4, 7, 8}, {7}, {8}},
            {{0, 1, 3, 6}, {2 ,5, 9}, {4, 7}, {8}},
            {{0, 1}, {2, 5, 9}, {4, 7}, {8}},
            {{2, 4, 7}, {5, 9}, {8}},
            {{5, 8}, {9}},
            {{9}}};
    boolean pins_clear;
    Score score;
    ArrowBar arrowbar;
    Pin[] pins;
    Santa santa;
    Ball ball;
    Context context;

    public PlayModel(Context c, int w, int h) {
        context = c;
        pins_clear = false;
        pins = new Pin[10];
        arrowbar = new ArrowBar(w, h);
        score = new Score(w, h);
        for (int i = 0; i < 10; i ++)
            pins[i] = new Pin(context, w, h, i);
        santa = new Santa(context, w, h);
        ball = new Ball(context, w, h);
    }

    public void update() {
        if (total_state == Global.FINISH_STATE)
            return;
        else if (total_state == Global.READY_STATE) {
            arrowbar.update();
        } else if(total_state == Global.ROLLING_STATE) {
            if (!ball.started) {
                Global.gutter_ball = false;
                MusicPlayer.play_rolling_sound();
                ball.started = true;
            }
            ball.update();
            if (ball.arrived) {
                ball.started = false;
                total_state = Global.CALC_STATE;
                Log.d("Alert", "CRASHING");
            }
        } else if (total_state == Global.CALC_STATE) {
            int count = 0;

            if (arrowbar.arrow >= 9 && arrowbar.arrow <= 19) {
                int arrow_index = arrowbar.arrow - 9;
                for (int j = pins_vector[arrow_index].length - 1; j >= 0 ; j --) {
                    if (pins[pins_vector[arrow_index][j][0]].state == 0) {

                        for (int k = 0; k < pins_vector[arrow_index][j].length; k++) {
                            if (pins[pins_vector[arrow_index][j][k]].state == 0) {
                                if ((int)(Math.random() * 10) % 2 == 0)
                                    pins[pins_vector[arrow_index][j][k]].state = 1;
                                else
                                    pins[pins_vector[arrow_index][j][k]].state = 2;
                                pins[pins_vector[arrow_index][j][k]].state_count = 24;
                                count++;
                            }
                        }
                    }
                }
            }

            int pointer = score.pointer;
            score.score[pointer / 10][pointer % 10] = count;

            if (pointer / 10 < 9) {
                if (pointer % 10 == 0 && count == 10) {
                    pointer = pointer - pointer % 10 + 10;
                    pins_clear = true;
                } else if (pointer % 10 == 1) {
                    pointer = pointer - pointer % 10 + 10;
                    pins_clear = true;
                } else {
                    pointer ++;
                }
            } else {
                if (pointer % 10 == 0) {
                    pointer ++;
                    if (score.score[9][0] == 10)
                        pins_clear = true;
                } else if (pointer % 10 == 1 && (score.score[9][0] == 10 || score.score[9][1] == 10 || score.score[9][0] + score.score[9][1] == 10)) {
                    pointer ++;
                    if (score.score[9][1] == 10 || score.score[9][0] + score.score[9][1] == 10)
                        pins_clear = true;
                } else {
                    pointer += 2;
                }
            }
            score.calc();
            score.pointer = pointer;
            ball.clear();

            if (count == 0) {
                if (Global.gutter_ball)
                    MusicPlayer.play_laugh_sound();
                else
                    MusicPlayer.play_fart_sound();
                total_state = Global.LAYING_STATE;
                total_state_count = 24;
            } else {
                MusicPlayer.play_crashing_sound();
                total_state = Global.FLYING_STATE;
            }
        } else if (total_state == Global.FLYING_STATE){
            int count = 0;
            for (int i = 0; i < 10; i ++) {
                pins[i].update();
                if (pins[i].state != 0 && pins[i].state != 5)
                    count ++;
            }

            if (count == 0) {
                if (score.pointer >= 93) {
                    total_state = Global.FINISH_STATE;
                    ((PlayActivity)context).finish_play();
                    return;
                }

                total_state = Global.READY_STATE;
                arrowbar.clear();
                if (pins_clear) {
                    clearPins();
                    pins_clear = false;
                }
            }
        } else {
            total_state_count --;

            if (total_state_count <= 0) {
                if (score.pointer >= 93) {
                    total_state = Global.FINISH_STATE;
                    ((PlayActivity)context).finish_play();
                    return;
                }

                total_state = Global.READY_STATE;
                arrowbar.clear();
                if (pins_clear) {
                    clearPins();
                    pins_clear = false;
                }
            }
        }
    }

    void clearPins() {
        for (int i = 0; i < 10; i ++) {
            pins[i].clear();
        }
    }

    int get_score() {
        if (score.score[9][3] == -1)
            return 0;
        return score.score[9][3];
    }

    void clear() {
        total_state = Global.READY_STATE;
        total_state_count = 0;
        pins_clear = false;
        score.clear();
        arrowbar.clear();
        clearPins();
        ball.clear();
    }
}
