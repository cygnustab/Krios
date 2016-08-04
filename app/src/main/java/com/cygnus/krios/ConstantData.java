package com.cygnus.krios;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.util.Log;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ConstantData {

    public static ProgressDialog dialog;
    public static String Login_User;
    public static String ServerUrl = "www.google.com";
    //    public static List<UserData> All_Menu_list = new ArrayList<>();
    public static String Entity_id;
    SimpleDateFormat dateforamate = new SimpleDateFormat("dd MMM yyyy");
//    public static int imgHeight, imgWidth;

    public static void DisplayAlert(String title, String message,
                                    Context context) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null)
            return false;
            // return true;

        else
            return true;// return isInternetAvailable(context);

    }

    public static String changeDateFromate(String webserviceDate) {

        String returnDate = "";
        String inputPattern = "yyyy-dd-MM'T'HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(webserviceDate);
            returnDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
//
        return returnDate;


    }

    public static boolean isInternetAvailable(Context context) {
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(ConstantData.ServerUrl);
            httppost.addHeader("Content-Type", "multipart/form-data");
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            Log.i("exce", "" + e.toString());
        }
        return false;

    }

    public static void progressdialogshow(Context context) {
        dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Log.e("BadTokenException 1 : ", e.toString());
        }

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);

        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

            Log.e("BadTokenException 2 : ", e.toString());
        }

    }

    public static void progressdialogclose(Context context) {
        dialog.dismiss();
    }

    public static void CheckNetwork(Context context) {
        if (ConstantData.isNetworkConnected(context)) {

        } else {
            ConstantData.DisplayAlert("Network Error !!!",
                    "Network Not Found , Please check your  Network And Try Again",
                    context);
            return;
        }
    }
}
