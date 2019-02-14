package uw.elfbowl.main;

import android.content.Context;
import android.media.MediaPlayer;

import uw.elfbowl.R;


public class MusicPlayer {
    static Context context;
    static MediaPlayer mp_back;
    static MediaPlayer mp_fore;

    static void play_back_sound() {
        mp_back = MediaPlayer.create(context, R.raw.jinglebells);
        mp_back.setLooping(true);
        mp_back.start();
    }

    static void play_rolling_sound() {
        mp_fore = MediaPlayer.create(context, R.raw.rolling);
        mp_fore.start();
    }

    static void play_crashing_sound() {
        mp_fore = MediaPlayer.create(context, R.raw.pinscrashing);
        mp_fore.start();
    }

    static void play_fart_sound() {
        mp_fore = MediaPlayer.create(context, R.raw.fart);
        mp_fore.start();
    }

    static void play_laugh_sound() {
        mp_fore = MediaPlayer.create(context, R.raw.crowdlaugh);
        mp_fore.start();
    }
}
