package com.patelheggere.hamsa.threesixty.cardstack;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.patelheggere.hamsa.threesixty.R;

public class CardsDataAdapter extends ArrayAdapter<DataModel> {

    public CardsDataAdapter(Context context) {
        super(context, R.layout.card_content);
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
       TextView v = (TextView)(contentView.findViewById(R.id.content));
        v.setText(getItem(position).getName());
        return contentView;
    }

}

