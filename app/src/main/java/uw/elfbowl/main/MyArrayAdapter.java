package uw.elfbowl.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import uw.elfbowl.R;

public class MyArrayAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] score_array;

    public MyArrayAdapter(Activity context, String[] array) {
        super(context, R.layout.listview, array);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.score_array = array;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview, null,true);

        TextView scoreText = (TextView) rowView.findViewById(R.id.label);
        scoreText.setText(score_array[position]);
        return rowView;
    };
}