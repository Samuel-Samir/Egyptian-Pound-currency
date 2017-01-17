package com.example.android.dollar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dollar.Models.Currencies;

import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

import java.util.ArrayList;


/**
 * Created by samuel on 12/8/2016.
 */

public class CurrencySpinnerCustomAdapter extends BaseAdapter {


    Context context;
    ArrayList<Currencies> currenciesList ;
    LayoutInflater inflter;

    public CurrencySpinnerCustomAdapter(Context context, ArrayList<Currencies> currenciesList) {
        this.context = context;
        this.currenciesList = currenciesList;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {

        return currenciesList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        view = inflter.inflate(R.layout.custom_currencyspinner_item, null);
        ImageView icon = (ImageView) view.findViewById(R.id.currency_Spinner_imagView);
        TextView names = (TextView) view.findViewById(R.id.currency_Spinner_textView);
        names.setText(currenciesList.get(position).name );
        icon.setImageDrawable(this.context.getResources().getDrawable(this.context.getResources().getIdentifier(currenciesList.get(position).code.toLowerCase(), "drawable", this.context.getPackageName())));


        return view;
    }


}


