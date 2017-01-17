package com.example.android.dollar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.android.dollar.Models.BankPriceInfo;
import com.example.android.dollar.Models.Currencies;
import com.example.android.dollar.Models.GoldPrice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by samuel on 12/8/2016.
 */

public class Utilities {
    public static  ArrayList<Currencies> currenciesList ;
    public static  String selectedCurrencyId="1" ;
    public static  ArrayList<BankPriceInfo> bankPriceCurrencyList ;
    public static  ArrayList<GoldPrice> goldPricesList ;
    public static  String goldUpdateDate ;
    public static  BankPriceInfo selectedBankPriceInfo;
    public static  Boolean isFirst ;
    public static  Boolean filesIsUsed=false;
    public static  String appNameDirectory="/SamuelApp";
    public static  String screenShotsDirectory="/ScreenShots";
    public static  String filesDirectory="/Files";
    public static Currencies selectedCurrencies;

    public static  final String photoDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + appNameDirectory+screenShotsDirectory;
    public static  final String fillDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + appNameDirectory+filesDirectory;



    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }


    public static void store(Bitmap bm, String fileName){
        File dir = new File(photoDirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(photoDirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareImage(File file , Activity activity){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/png");
        try {
            activity.startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "لا توجد مساحة كافية علي جهازك", Toast.LENGTH_SHORT).show();
        }

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean checkInternetConnection(Activity context)
    {

        ConnectivityManager con_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static void connectionAlart1 (final Activity activity)
    {



        AlertDialog alertDialog1 =  new AlertDialog.Builder(activity)
                .setTitle( activity.getResources().getString(R.string.connection_fild) )
                .setMessage(activity.getResources().getString(R.string.dailog_mass1))
                .setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();

                    }
                })
                .show();
    }

    public static void connectionAlart2 (final Activity activity)
    {



        AlertDialog alertDialog1 =  new AlertDialog.Builder(activity)
                .setTitle( activity.getResources().getString(R.string.connection_fild) )
                .setMessage(activity.getResources().getString(R.string.dailog_mass1)
                        +" "+activity.getResources().getString(R.string.dailog_mass2))
                .setPositiveButton(activity.getResources().getString(R.string.contin), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .show();
    }

/////////////////////////////////////////////////////////////////////////////////////

    public  static void banksWriteFile(ArrayList<BankPriceInfo> bankList)throws IOException {

        String fileName ="bankText.txt";
        File dir = new File(fillDirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(fillDirPath, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(bankList);
        objectOutputStream.close();

    }

    public  static void currencyWriteFile(Currencies currency)throws IOException {

        String fileName ="currencyText.txt";
        File dir = new File(fillDirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(fillDirPath, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(currency);
        objectOutputStream.close();

    }


    /*public  static void goldWriteFile(ArrayList<GoldPrice> GoldPrice)throws IOException {

        String fileName ="currencyText.txt";
        File dir = new File(fillDirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(fillDirPath, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(GoldPrice);
        objectOutputStream.close();

    }*/

    //********************************************************************************//

    public static void banksReadFile() throws IOException, ClassNotFoundException {

        String fileName ="bankText.txt";
        File dir = new File(fillDirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(fillDirPath, fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Utilities.bankPriceCurrencyList = ( ArrayList<BankPriceInfo> ) objectInputStream.readObject();

        objectInputStream.close();

    }

    public  static void currencyReadFile( ) throws IOException, ClassNotFoundException {

        String fileName ="currencyText.txt";
        File dir = new File(fillDirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(fillDirPath, fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Utilities.selectedCurrencies = ( Currencies ) objectInputStream.readObject();

        objectInputStream.close();

    }

/*
    public  static void goldReadFile() throws IOException, ClassNotFoundException {

        String fileName ="currencyText.txt";
        File dir = new File(fillDirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(fillDirPath, fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Utilities.goldPricesList = ( ArrayList<GoldPrice> ) objectInputStream.readObject();
        objectInputStream.close();

    }
*/


    public static String decode (String str, String str2) {
        byte[] doFinal;
        try {
            Key secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(2, secretKeySpec);
            doFinal = instance.doFinal(Base64.decode(str, 0));
        } catch (Exception e) {
            System.out.println(e.toString());
            doFinal = null;
        }
        return doFinal != null ? new String(doFinal) : null;
    }



}
