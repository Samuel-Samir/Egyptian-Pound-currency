package com.example.android.dollar.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.dollar.Models.GoldPrice;
import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

import java.util.ArrayList;

/**
 * Created by samuel on 12/11/2016.
 */

public class GoldPricesAdapter  extends  RecyclerView.Adapter<GoldPricesAdapter.ViewHolder> {

    private  final Activity myActivity;
    private  final ArrayList<GoldPrice> goldPricesList ;

    public GoldPricesAdapter(Activity myActivity, ArrayList<GoldPrice> goldPricesList) {
        this.myActivity = myActivity;
        this.goldPricesList = goldPricesList;
    }

    @Override
    public GoldPricesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = myActivity.getLayoutInflater().inflate(R.layout.custom_gold_item ,parent,false);
        return new GoldPricesAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(GoldPricesAdapter.ViewHolder holder, int position) {

        GoldPrice goldPrice = goldPricesList.get(position);
        holder.keyTextView.setText(goldPrice.key);
        holder.valueTextView.setText(goldPrice.value+" جنيه");
        holder.updateTextView.setText(Utilities.goldUpdateDate);

    }

    @Override
    public int getItemCount() {
        return goldPricesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView  keyTextView;
        TextView  valueTextView;
        TextView  updateTextView;
        public ViewHolder (View itemView)
        {
            super(itemView);
            keyTextView = (TextView) itemView.findViewById(R.id.gold_tybe);
            valueTextView = (TextView) itemView.findViewById(R.id.gold_brce);
            updateTextView = (TextView) itemView.findViewById(R.id.update);


        }
    }
}
