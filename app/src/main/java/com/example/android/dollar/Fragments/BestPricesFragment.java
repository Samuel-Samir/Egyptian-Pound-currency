package com.example.android.dollar.Fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

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
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestPricesFragment extends Fragment {
    ImageView sellImageView ;
    TextView sellBankName ;
    TextView sellBamkAmount;
    TextView sellBankUpdate ;
    ImageView buyImageView ;
    TextView  buyBankName ;
    TextView  buyBamkAmount;
    TextView  buyBankUpdate ;
    ArrayList <BankPriceInfo> bestPriceList ;
    BankPriceInfo bestSell ;
    BankPriceInfo bestBuy ;
    Spinner spinnerCurrency;
    CurrencySpinnerCustomAdapter currencySpinnerCustomAdapter ;
    String currencyID="1";
    TextView a3lase3r ;
    TextView a2alsa3r;
    String a3lase3rString = "يقدم اعلي سعر لشراء " ;
    String a2alsa3rString = "يقدم اقل سعر لبيع ";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_best_prices, container, false);

        sellImageView  = (ImageView) rootView.findViewById(R.id.sell_icon_bank);
        sellBankName = (TextView) rootView.findViewById(R.id.bankName1) ;
        sellBamkAmount = (TextView) rootView.findViewById(R.id.bankCurrency1) ;
        sellBankUpdate  = (TextView) rootView.findViewById(R.id.bankUpdate) ;
        buyImageView  = (ImageView) rootView.findViewById(R.id.buy_icon_bank);
        buyBankName = (TextView) rootView.findViewById(R.id.bankName2) ;
        buyBamkAmount = (TextView) rootView.findViewById(R.id.bankCurrency2) ;
        buyBankUpdate  = (TextView) rootView.findViewById(R.id.bankUpdate2) ;
        a3lase3r = (TextView) rootView.findViewById(R.id.a3la_se3r);
        a2alsa3r =(TextView)rootView.findViewById(R.id.a2l_se3r);
        spinnerCurrency = (Spinner) rootView.findViewById(R.id.currency_Spinner);
        if(Utilities.checkInternetConnection(getActivity())) {

            new GetCurrenciesTask().execute(new Void[0]);
            spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    currencyID = Utilities.currenciesList.get(position).id;
                    new GetPricesTask().execute(currencyID);
                    a2alsa3r.setText(a2alsa3rString + Utilities.currenciesList.get(position).name);
                    a3lase3r.setText(a3lase3rString + Utilities.currenciesList.get(position).name);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else {

            if(Utilities.bankPriceCurrencyList!=null && Utilities.selectedCurrencies!=null) {

                ArrayList <Currencies> list = new ArrayList<Currencies>();
                list.add(Utilities.selectedCurrencies);
                currencySpinnerCustomAdapter = new CurrencySpinnerCustomAdapter(getActivity(), list);
                spinnerCurrency.setAdapter(currencySpinnerCustomAdapter);
                bestPriceList = Utilities.bankPriceCurrencyList;
                getTheBest();
           }

        }



        return rootView ;
    }




    private class GetPricesTask extends AsyncTask<String, Void, Void> {
        private ProgressDialog dialog ;
        @Override
        protected Void doInBackground(String... params) {

            String url = getResources().getString(R.string.getPricesURL) + "?allNewBanks3=Y&encrypt=Y&"+"id=" + params[0] + "&orderBy=" + "buy" + "&orderType=" + "D" + "&light=Y" + "&ts=" + (System.currentTimeMillis() / 5000);
            String jsonString1 = new HttpHandler().makeServiceCall( url );
            String jsonString = Utilities.decode(jsonString1 ,"4D64884D64884D64");
            if (jsonString != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    bestPriceList = new ArrayList<>();
                    for (int i = 2; i < jsonArray.length(); i++) {
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
                        bestPriceList.add(bankPrice);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
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

            getTheBest();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }


    public void getTheBest()
    {
        Double bestBuy2 =1000.0;
        Double comber =0.0;
        int index=0;
        String templetUpdate="";

        for (int i =0 ;i<bestPriceList.size() ;i++)
        {
            comber = Double.valueOf(bestPriceList.get(i).sell_price)  ;
            if (comber<bestBuy2)
            {
                bestBuy2=comber;
                index=i;
                if (bestPriceList.get(i).updated.length()>5)
                {
                    templetUpdate =bestPriceList.get(i).updated ;
                }

            }
        }

        bestSell = bestPriceList.get(0);
        sellImageView.setImageDrawable(getActivity().getResources().getDrawable(getActivity().getResources().getIdentifier("b"+bestSell.bank_id , "drawable", getActivity().getPackageName())));
        sellBankName.setText(bestSell.bank);
        sellBamkAmount.setText(bestSell.buy_price);
        if (bestSell.updated.length()>5)
            sellBankUpdate.setText(bestSell.updated);
        else
            sellBankUpdate.setText(templetUpdate);

        bestBuy=bestPriceList.get(index);

        buyImageView.setImageDrawable(getActivity().getResources().getDrawable(getActivity().getResources().getIdentifier("b"+bestBuy.bank_id , "drawable", getActivity().getPackageName())));
        buyBankName.setText(bestBuy.bank);
        buyBamkAmount.setText(bestBuy.sell_price);
        if (bestBuy.updated.length() > 5)
            buyBankUpdate.setText(bestBuy.updated);
        else{
            buyBankUpdate.setText(templetUpdate);
        }

    }
    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    private class GetCurrenciesTask extends AsyncTask<Void, Void, Void> {



        protected Void doInBackground(Void... params) {
            String jsonString = new HttpHandler().makeServiceCall(getResources().getString(R.string.uriFetchCurrencies));
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
                } catch (JSONException e) {
                }
            } else {
            }
            return null;
        }



        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            currencySpinnerCustomAdapter = new CurrencySpinnerCustomAdapter(getActivity() ,Utilities.currenciesList);
            spinnerCurrency.setAdapter(currencySpinnerCustomAdapter);


        }
    }




}
