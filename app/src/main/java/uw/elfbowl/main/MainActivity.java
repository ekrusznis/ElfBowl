package uw.elfbowl.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import uw.elfbowl.R;

/**
 * Created by Astro on 2018-11-12.
 */
public class MainActivity extends AppCompatActivity{
    MainView mainView;
    PopupWindow level;
    boolean popup_showing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        RelativeLayout Layout = (RelativeLayout)findViewById(R.id.main_layout);

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View level_view = inflater.inflate(R.layout.level, null);
        // create the popup window
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        level = new PopupWindow(level_view, (int)(height * 0.2), (int)(height * 0.3), focusable);
        level.setFocusable(false);
        popup_showing = false;
        level_view.setPadding((int)(height * 0.02), (int)(height * 0.03), (int)(height * 0.02), (int)(height * 0.03));

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken

        // dismiss the popup window when touched

        Button easy_button = (Button)level_view.findViewById(R.id.easy_button);
        easy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlayActivity.class);
                Global.difficulty = Global.EASY_MODE;
                startActivity(intent);
                level.dismiss();
                popup_showing = false;
            }
        });

        Button normal_button = (Button)level_view.findViewById(R.id.normal_button);
        normal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlayActivity.class);
                Global.difficulty = Global.NORMAL_MODE;
                startActivity(intent);
                level.dismiss();
                popup_showing = false;
            }
        });

        Button hard_button = (Button)level_view.findViewById(R.id.hard_button);
        hard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlayActivity.class);
                Global.difficulty = Global.HARD_MODE;
                startActivity(intent);
                level.dismiss();
                popup_showing = false;
            }
        });

        Button playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Alert!", "Play");
                level.showAtLocation(mainView, Gravity.CENTER, 0, 0);
                popup_showing = true;
                //Intent intent = new Intent(getBaseContext(), PlayActivity.class);
                //startActivity(intent);
            }
        });

        Button scoreButton = (Button) findViewById(R.id.score_button);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Alert!", "Score");
                Intent intent = new Intent(getBaseContext(), ScoreActivity.class);
                startActivity(intent);
            }
        });

        Button quitButton = (Button) findViewById(R.id.quit_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Alert!", "Quit");
                finish();
            }
        });

        MusicPlayer.context = this;
        MusicPlayer.play_back_sound();

        mainView = new MainView(this);
        Layout.addView(mainView);
    }

    @Override
    public void onBackPressed() {
        if (popup_showing) {
            level.dismiss();
            popup_showing = false;
        }
        else
            super.onBackPressed();
    }
}
