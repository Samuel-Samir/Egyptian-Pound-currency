package com.example.android.dollar.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PopActivity extends AppCompatActivity {
    ImageView bank_icon ;
    TextView bank_name;
    TextView bank_phone_nymber;
    ImageView call_icon;
    ImageView website_icon;
    Button sellButton;
    Button buyButton;
    TextView boundTextView;
    EditText dollorEditText;
    TextView dollorTextView;
    String selectedPrice="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int hight = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8) ,((int)(hight*.55)));


        bank_icon =(ImageView) findViewById(R.id.pop_bank_icon);
        bank_name = (TextView) findViewById(R.id.pop_bank_name);
        bank_phone_nymber =(TextView) findViewById(R.id.pop_phone_num) ;
        call_icon = (ImageView) findViewById(R.id.pop_phone_icon);
        website_icon = (ImageView) findViewById(R.id.pop_websit);
        sellButton =(Button) findViewById(R.id.pop_sell_button);
        buyButton = (Button) findViewById(R.id.pop_buy_button);
        dollorTextView =(TextView) findViewById(R.id.pop_dollerTextView) ;
        boundTextView =(TextView) findViewById(R.id.pop_pound) ;
        dollorEditText = (EditText) findViewById(R.id.pop_doller);



        bank_icon.setImageDrawable(this.getResources().getDrawable(this.getResources().getIdentifier("b" + Utilities.selectedBankPriceInfo.bank_id , "drawable", this.getPackageName())));
        bank_name.setText(Utilities.selectedBankPriceInfo.bank);
        String str ="الخط الساخن : " ;
        bank_phone_nymber.setText(str + Utilities.selectedBankPriceInfo.hotLine );
        buyButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        sellButton.setBackgroundColor(getResources().getColor(R.color.red));
        selectedPrice=Utilities.selectedBankPriceInfo.buy_price;
        dollorTextView.setText(Utilities.currenciesList.get(Integer.valueOf(Utilities.selectedCurrencyId)-1).name);
        dollorEditText.setText("1");
        boundTextView.setText(selectedPrice);

        dollorEditText.requestFocus();

        call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + Utilities.selectedBankPriceInfo.hotLine ));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });

        website_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri webpage = Uri.parse(Utilities.selectedBankPriceInfo.url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });



        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                buyButton.setBackgroundColor(getResources().getColor(R.color.red));
                selectedPrice=Utilities.selectedBankPriceInfo.sell_price;
                dollorEditText.setText("1");
                boundTextView.setText(selectedPrice);

            }
        });
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                sellButton.setBackgroundColor(getResources().getColor(R.color.red));
                selectedPrice=Utilities.selectedBankPriceInfo.buy_price;
                dollorEditText.setText("1");
                boundTextView.setText(selectedPrice);

            }
        });






        dollorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str="";
                if(s.length()==0) {
                    dollorEditText.setText("0");
                }
                else {
                    str = String.valueOf(s);
                    Double reciveMony = Double.parseDouble(str);
                    reciveMony = reciveMony * Double.valueOf(selectedPrice);
                    NumberFormat formatter = new DecimalFormat("###.#####");

                    String value = formatter.format(reciveMony);
                    boundTextView.setText(value);
                }
            }
        });



    }
}
/*

                Double truncatedDouble = BigDecimal.valueOf(reciveMony)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue();
 */