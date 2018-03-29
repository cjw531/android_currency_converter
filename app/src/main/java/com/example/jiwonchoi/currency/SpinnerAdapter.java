package com.example.jiwonchoi.currency;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jiwonchoi on 2018. 3. 23..
 */

public class SpinnerAdapter extends ArrayAdapter<CountryData> {

    private int groupId;
    Activity context;
    ArrayList<CountryData> countries;
    LayoutInflater inflater;

    public SpinnerAdapter(Activity context, int groupId, int id, ArrayList<CountryData> countries) {
        super(context, id, countries);
        this.countries = countries;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupId = groupId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupId, parent, false);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.flag);
        imageView.setImageResource(countries.get(position).getImageId());
        TextView textView = (TextView)itemView.findViewById(R.id.country_code);
        textView.setText(countries.get(position).getCountry());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
