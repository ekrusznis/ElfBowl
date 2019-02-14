package uw.elfbowl.main;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

import uw.elfbowl.R;


public class PlayActivity extends AppCompatActivity {
    Button exitButton;
    PlayView playView;
    final String MY_PREFS_NAME = "MyPrefsName";
    final String MY_KEY_STRING = "MyKeyString";
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    String testAdID = "ca-app-pub-3940256099942544~3347511713";
    String liveID = "ca-app-pub-4136106796733611~8269280250";
    String livepopupID = "ca-app-pub-4136106796733611/8000831022";
    String testPopup = "ca-app-pub-3940256099942544/1033173712";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        FrameLayout Layout = (FrameLayout)findViewById(R.id.frame_layout);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, liveID);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(livepopupID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



        playView = new PlayView(this);
        Layout.addView(playView);
    }

    public void finish_play() {
        try {
            Date date = Calendar.getInstance().getTime();
            String data = "Score: " + String.format("%03d", playView.playModel.get_score()) + "  Date: " + String.format("%d/%02d/%02d %02d:%02d:%02d", date.getYear() + 1900, date.getMonth(), date.getDay(),
                    date.getHours(), date.getMinutes(), date.getSeconds()) + "  Mode: ";
            if (Global.difficulty == Global.EASY_MODE)
                data += "EASY&";
            else if (Global.difficulty == Global.NORMAL_MODE)
                data += "NORMAL&";
            else
                data += "HARD&";

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getBaseContext().openFileOutput("score.config", Context.MODE_APPEND));
            outputStreamWriter.append(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View finish_view = inflater.inflate(R.layout.finish, null);

                // create the popup window
                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                int height = Resources.getSystem().getDisplayMetrics().heightPixels;
                boolean focusable = true; // lets taps outside the popup also dismiss it

                int finish_width = (int)(height * 0.4);
                int finish_height = (int)(height * 0.267);
                final PopupWindow finish = new PopupWindow(finish_view, finish_width, finish_height, focusable);

                finish_view.setPadding((int) (finish_height * 0.0625), (int) (finish_height * 0.0625), (int) (finish_height * 0.0625), (int) (finish_height * 0.0625));

                TextView score_view = (TextView) finish_view.findViewById(R.id.score_view);
                score_view.setTextSize((float)(finish_height * 0.045));
                score_view.setText(String.format("%03d", playView.playModel.get_score()));

                TextView label_view = (TextView) finish_view.findViewById(R.id.label_view);
                label_view.setTextSize((float)(finish_height * 0.06));

                Button play_again_button = (Button) finish_view.findViewById(R.id.play_again_button);
                play_again_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Alert!", "Play again");
         //               finish();
                        finish.dismiss();
                        playView.playModel.clear();
                    }
                });
                ViewGroup.LayoutParams params = play_again_button.getLayoutParams();
                play_again_button.setTextSize((float)(finish_height * 0.045));
                params.width = (int)(finish_height * 0.5);
                params.height = (int)(finish_height * 0.25);
                play_again_button.setLayoutParams(params);

                Button play_finish_button = (Button) finish_view.findViewById(R.id.play_finish_button);
                play_finish_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Alert!", "Play finish");
                        finish.dismiss();

                        finish();
                        //         ((Activity) getBaseContext()).finish();
                    }
                });
                ViewGroup.LayoutParams params1 = play_finish_button.getLayoutParams();
                play_finish_button.setTextSize((int)(finish_height * 0.045));
                params1.width = (int)(finish_height * 0.5);
                params1.height = (int)(finish_height * 0.25);
                play_finish_button.setLayoutParams(params1);

                finish.showAtLocation(playView, Gravity.CENTER, 0, 0);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });
    }
}
