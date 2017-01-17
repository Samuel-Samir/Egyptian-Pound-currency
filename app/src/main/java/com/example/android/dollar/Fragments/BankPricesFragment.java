package com.example.android.dollar.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.android.dollar.Adapters.BankPricesAdapter;
import com.example.android.dollar.Adapters.CurrencySpinnerCustomAdapter;
import com.example.android.dollar.HttpHandler;
import com.example.android.dollar.Models.BankPriceInfo;
import com.example.android.dollar.Models.Currencies;
import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BankPricesFragment extends Fragment  {
    Spinner spinnerCurrency;
    Spinner spinnerOrder;
    CurrencySpinnerCustomAdapter currencySpinnerCustomAdapter ;
    boolean connected = true;
    RecyclerView pankPriceRecyclerView;
    BankPricesAdapter bankPricesAdapter ;
    String orderBy ="buy";
    String orderType = "D";
    TextView generalUpdateTextView ;
    ImageView buyImageView;
    ImageView sellImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView =inflater.inflate(R.layout.fragment_bank_prices, container, false);
        spinnerCurrency = (Spinner) rootView.findViewById(R.id.currency_Spinner);
        spinnerOrder = (Spinner) rootView.findViewById(R.id.orde_Spinner);
        pankPriceRecyclerView = (RecyclerView) rootView.findViewById(R.id.GridViewLayout);
        pankPriceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1 ));
        generalUpdateTextView = (TextView)rootView.findViewById(R.id.generalUpdate);
        buyImageView= (ImageView) rootView.findViewById(R.id.buy_arrow);
        sellImageView =(ImageView) rootView.findViewById(R.id.sell_arrow);
        if(Utilities.checkInternetConnection(getActivity()))
        {
            findSpnnerViews(rootView);
            new GetPricesTask().execute();
        }

        else if (Utilities.checkInternetConnection(getActivity())==false  )
        {
            if(Utilities.isFirst==true)
                Utilities.connectionAlart1(getActivity());
            else if(Utilities.isFirst==false)
            {

                AlertDialog alertDialog1 =  new AlertDialog.Builder(getActivity())
                        .setTitle( getActivity().getResources().getString(R.string.connection_fild) )
                        .setMessage(getActivity().getResources().getString(R.string.dailog_mass1)
                                +" "+getActivity().getResources().getString(R.string.dailog_mass2))
                        .setPositiveButton(getActivity().getResources().getString(R.string.contin), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(Utilities.filesIsUsed==false) {
                                    try {
                                        Utilities.currencyReadFile();
                                        Utilities.banksReadFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(Utilities.bankPriceCurrencyList!=null && Utilities.selectedCurrencies!=null)
                                {
                                    Utilities.filesIsUsed = true;
                                    findOrserSpnner(rootView);
                                    ArrayList <Currencies> list = new ArrayList<Currencies>();
                                    list.add(Utilities.selectedCurrencies);
                                    currencySpinnerCustomAdapter = new CurrencySpinnerCustomAdapter(getActivity(), list);
                                    spinnerCurrency.setAdapter(currencySpinnerCustomAdapter);
                                    bankPricesAdapter = new BankPricesAdapter(getActivity(), Utilities.bankPriceCurrencyList);
                                    pankPriceRecyclerView.setAdapter(bankPricesAdapter);
                                    String update = "\u062a\u062d\u062f\u064a\u062b: " + Utilities.bankPriceCurrencyList.get(0).updated + " \u0628\u062a\u0648\u0642\u064a\u062a \u0627\u0644\u0642\u0627\u0647\u0631\u0629";
                                    generalUpdateTextView.setText(update);
                                }

                            }
                        })
                        .setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        })
                        .show();
            }

        }
        return rootView;
    }

    public  void  findSpnnerViews (View rootView)
    {
        new GetCurrenciesTask().execute(new Void[0]);

        spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utilities.selectedCurrencyId = Utilities.currenciesList.get(position).id;
                Utilities.selectedCurrencies =Utilities.currenciesList.get(position);
                new GetPricesTask().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Utilities.selectedCurrencyId="1";

            }
        });
        ////////////Order Spanner////////////////
        findOrserSpnner(rootView);


    }

    public  void  findOrserSpnner (View rootView)
    {
        String [] spannerOrder = getResources().getStringArray(R.array.viewOrder);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spannerOrder); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrder.setAdapter(spinnerArrayAdapter);
        spinnerOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                {
                    orderBy ="buy";
                    orderType = "D";
                    sellImageView.setImageResource(R.drawable.dimmed);
                    buyImageView.setImageResource(R.drawable.down);
                }
                else if (position==1)
                {
                    orderBy ="sell";
                    orderType = "D";
                    buyImageView.setImageResource(R.drawable.dimmed);
                    sellImageView.setImageResource(R.drawable.down);
                }
                else if(position==2){
                    orderBy ="buy";
                    orderType = "A";
                    sellImageView.setImageResource(R.drawable.dimmed);
                    buyImageView.setImageResource(R.drawable.up);
                }
                else if(position==3)
                {
                    orderBy ="sell";
                    orderType = "A";
                    buyImageView.setImageResource(R.drawable.dimmed);
                    sellImageView.setImageResource(R.drawable.up);
                }

                if(Utilities.filesIsUsed==false)
                     new GetPricesTask().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Utilities.selectedCurrencyId="1";

            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    private class GetCurrenciesTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog ;

        protected Void doInBackground(Void... params) {
            String jsonString = new HttpHandler().makeServiceCall( getResources().getString(R.string.uriFetchCurrencies) );
            if (jsonString != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    Utilities.currenciesList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                        Currencies currency = new Currencies();
                        currency.id=(jsonObj.getString("id"));
                        currency.name=(jsonObj.getString("name"));
                        currency.code=(jsonObj.getString("code"));
                        Utilities.currenciesList.add(currency);
                    }
                    connected = true;
                } catch (JSONException e) {
                    connected = false;
                }
            } else {
                connected = false;
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
            currencySpinnerCustomAdapter = new CurrencySpinnerCustomAdapter(getActivity() ,Utilities.currenciesList);
            spinnerCurrency.setAdapter(currencySpinnerCustomAdapter);
            Utilities.selectedCurrencies =Utilities.currenciesList.get(0);

            try {
                Utilities.currencyWriteFile(Utilities.selectedCurrencies);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private class GetPricesTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog ;
        @Override
        protected Void doInBackground(Void... params) {


            String url = getResources().getString(R.string.getPricesURL) + "?allNewBanks3=Y&encrypt=Y&"+"id=" + Utilities.selectedCurrencyId + "&orderBy=" + orderBy + "&orderType=" + orderType + "&light=Y" + "&ts=" + (System.currentTimeMillis() / 5000);
            String jsonString1 = new HttpHandler().makeServiceCall( url );
            String jsonString = Utilities.decode(jsonString1 ,"4D64884D64884D64");
            if (jsonString != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    Utilities.bankPriceCurrencyList =new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                        BankPriceInfo bankPrice= new BankPriceInfo();

                        bankPrice.bank  =  jsonObj.getString("bank");
                        bankPrice.bank_en = jsonObj.getString("bank_en");
                        bankPrice.buy_price  = jsonObj.getString("buy_price");
                        bankPrice.sell_price = jsonObj.getString("sell_price");
                        bankPrice.created = jsonObj.getString("created");
                        bankPrice.updated = jsonObj.getString("updated");
                        bankPrice.hotLine= jsonObj.getString("hotLine");
                        bankPrice.url =jsonObj.getString("url");
                        bankPrice.bank_id=jsonObj.getString("bank_id");
                        Utilities.bankPriceCurrencyList.add(bankPrice);
                    }
                    connected = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    connected = false;
                    return null;
                }
            }
            connected = false;
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
            bankPricesAdapter =new BankPricesAdapter(getActivity() ,Utilities.bankPriceCurrencyList);
            pankPriceRecyclerView.setAdapter(bankPricesAdapter);
            String update = "\u062a\u062d\u062f\u064a\u062b: " + Utilities.bankPriceCurrencyList.get(0).updated + " \u0628\u062a\u0648\u0642\u064a\u062a \u0627\u0644\u0642\u0627\u0647\u0631\u0629";
            generalUpdateTextView.setText(update);

            try {
                Utilities.banksWriteFile(Utilities.bankPriceCurrencyList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

}
