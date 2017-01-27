package com.example.android.dollar.Fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.dollar.Adapters.BankPricesAdapter;
import com.example.android.dollar.Adapters.GoldPricesAdapter;
import com.example.android.dollar.BuildConfig;
import com.example.android.dollar.HttpHandler;
import com.example.android.dollar.Models.GoldPrice;
import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoldFragment extends Fragment {

    RecyclerView goldPriceRecyclerView;
    GoldPricesAdapter goldPricesAdapter ;
    View rootView ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         rootView  =inflater.inflate(R.layout.fragment_gold, container, false);
        goldPriceRecyclerView = (RecyclerView) rootView.findViewById(R.id.GridViewLayout);
        goldPriceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1 ));

        if(Utilities.checkInternetConnection(getActivity())) {
            new GetGoldPricesTask().execute();
        }


        return  rootView ;
    }




    private class GetGoldPricesTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog ;

        protected Void doInBackground(Void... params) {
            String jsonString = new HttpHandler().makeServiceCall(getResources().getString(R.string.getGoldPricesURL) + "?ts=" + (System.currentTimeMillis() / 1000));
            Utilities.goldPricesList = new ArrayList<>();
            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);
                    GoldPrice goldPriceDto = new GoldPrice();
                    String price = jsonObj.getString("24");
                    goldPriceDto.key =("\u0639\u064a\u0627\u0631 24");
                    goldPriceDto.value=(price);
                    Utilities.goldPricesList.add(goldPriceDto);
                    goldPriceDto = new GoldPrice();
                    price = jsonObj.getString("22");
                    goldPriceDto.key =("\u0639\u064a\u0627\u0631 22");
                    goldPriceDto.value=(price);
                    Utilities.goldPricesList.add(goldPriceDto);

                    goldPriceDto = new GoldPrice();
                    price = jsonObj.getString("21");
                    goldPriceDto.key =("\u0639\u064a\u0627\u0631 21");
                    goldPriceDto.value=(price);
                    Utilities.goldPricesList.add(goldPriceDto);
                    goldPriceDto = new GoldPrice();
                    price = jsonObj.getString("18");
                    goldPriceDto.key =("\u0639\u064a\u0627\u0631 18");
                    goldPriceDto.value=(price);
                    Utilities.goldPricesList.add(goldPriceDto);
                    goldPriceDto = new GoldPrice();
                    price = jsonObj.getString("14");
                    goldPriceDto.key =("\u0639\u064a\u0627\u0631 14");
                    goldPriceDto.value=(price);
                    Utilities.goldPricesList.add(goldPriceDto);
                    goldPriceDto = new GoldPrice();
                    price = jsonObj.getString("12");
                    goldPriceDto.key =("\u0639\u064a\u0627\u0631 12");
                    goldPriceDto.value=(price);
                    Utilities.goldPricesList.add(goldPriceDto);
                    goldPriceDto = new GoldPrice();
                    price = jsonObj.getString("pound");
                    goldPriceDto.key =("\u0627\u0644\u062c\u0646\u064a\u0647 \u0627\u0644\u0630\u0647\u0628");
                    goldPriceDto.value=(price);
                    Utilities.goldPricesList.add(goldPriceDto);
                    goldPriceDto = new GoldPrice();

                    price = jsonObj.getString("kg");
                    goldPriceDto.key =("\u0627\u0644\u0643\u064a\u0644\u0648");
                    goldPriceDto.value=(price);
                    Utilities.goldPricesList.add(goldPriceDto);
                    Utilities.goldUpdateDate = jsonObj.getString("updated");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            this.dialog.setMessage("تحميل ...");
            this.dialog.show();
            super.onPreExecute();
        }
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            goldPricesAdapter =new GoldPricesAdapter(getActivity() ,Utilities.goldPricesList);
            goldPriceRecyclerView.setAdapter(goldPricesAdapter);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

}
