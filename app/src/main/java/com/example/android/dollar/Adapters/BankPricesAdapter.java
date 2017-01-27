package com.example.android.dollar.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.dollar.Activities.PopActivity;
import com.example.android.dollar.Models.BankPriceInfo;
import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

import java.util.ArrayList;

/**
 * Created by samuel on 12/10/2016.
 */

public class BankPricesAdapter  extends  RecyclerView.Adapter<BankPricesAdapter.ViewHolder> {

    private  final Activity myActivity;
    private  ArrayList<BankPriceInfo> bankPriceList = new ArrayList<>();

    public BankPricesAdapter(Activity myActivity, ArrayList<BankPriceInfo> bankPriceList) {
        this.myActivity = myActivity;
        this.bankPriceList = bankPriceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = myActivity.getLayoutInflater().inflate(R.layout.custom_bank_item ,parent,false);

        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final BankPriceInfo bankPrice = bankPriceList.get(position);
        String [] values = bankPrice.bank.split(" ");
        if (values.length>3 && !bankPrice.bank.equals("بنك التعمير و الإسكان"))
        {
            String line1 = values[0]+" "+values[1]+" "+values[2];
            String line2="";
            for (int i=3 ;i<values.length;i++)
                line2=line2+values[i]+" ";
            String str = line1
                    + System.getProperty ("line.separator")
                    + line2
                    + System.getProperty ("line.separator");
            holder.bankNameTextView.setText(str);

        }
        else
            holder.bankNameTextView.setText(bankPrice.bank);
        holder.sellTextView.setText(bankPrice.sell_price);
        holder.buyTextView.setText(bankPrice.buy_price);
        holder.bankIcon.setImageDrawable(myActivity.getResources().getDrawable(myActivity.getResources().getIdentifier("b" +bankPrice.bank_id , "drawable", myActivity.getPackageName())));

        if(Utilities.checkInternetConnection(myActivity))
        {

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utilities.selectedBankPriceInfo = bankPriceList.get(position);
                    Intent intent = new Intent(myActivity, PopActivity.class);
                    myActivity.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return bankPriceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView bankIcon ;
        TextView  bankNameTextView ;
        TextView  sellTextView;
        TextView  buyTextView;
        RelativeLayout relativeLayout ;
        public ViewHolder (View itemView)
        {
            super(itemView);
            bankIcon = (ImageView) itemView.findViewById(R.id.bank_item_icon);
            bankNameTextView = (TextView) itemView.findViewById(R.id.bank_item_name);
            sellTextView = (TextView) itemView.findViewById(R.id.bank_item_sell);
            buyTextView = (TextView) itemView.findViewById(R.id.bank_item_buy);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.bank_item_RelativeLayout);


        }
    }
}
