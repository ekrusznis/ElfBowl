package uw.elfbowl.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import uw.elfbowl.R;


public class ScoreActivity extends AppCompatActivity{

    ListView score_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        try {
            InputStream inputStream = this.openFileInput("score.config");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();

                String[] lines = stringBuilder.toString().split("&");
                ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.label, lines);
                score_list = (ListView)findViewById(R.id.score_list);
                score_list.setAdapter(adapter);

                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                int height = Resources.getSystem().getDisplayMetrics().heightPixels;
                ListView list_view = (ListView)findViewById(R.id.score_list);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)list_view.getLayoutParams();
                params.setMargins((int) (width * 0.05), (int) (height * 0.1), (int) (width * 0.05), (int) (height * 0.3));
                list_view.setLayoutParams(params);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }
}
