package com.cygnus.krios;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Customer_Registration extends Activity {

    EditText EditText_First_Name, EditText_Last_Name, EditText_Emial_ID, EditText_Password, EditText_Confirm_Password;
    Button Button_Create_Account;
    String Email_ID, First_Name, Last_Name, Password, Confirm_Password;
    String JSON_String;

    private boolean check_fill_or_blank() {
        if (hasText(EditText_First_Name)) {
        } else {
            return false;
        }
        if (hasText(EditText_Last_Name)) {
        } else {
            return false;
        }
        if (hasText(EditText_Emial_ID)) {
        } else {
            return false;
        }
        if (hasText(EditText_Password)) {
        } else {
            return false;
        }
        if (hasText(EditText_Confirm_Password)) {
        } else {
            return false;
        }
        return true;
    }

    private void GetValue() {

        Email_ID = getValue(EditText_Emial_ID);
        First_Name = getValue(EditText_First_Name);
        Last_Name = getValue(EditText_Last_Name);
        Password = getValue(EditText_Password);
        Confirm_Password = getValue(EditText_Confirm_Password);
    }

    private void Create_Json() {

        JSONObject Obj = new JSONObject();
        try {
            Obj.put("firstname", "" + First_Name);
            Obj.put("lastname", "" + Last_Name);
            Obj.put("email", "" + Email_ID);
            Obj.put("password", "" + Password);
            JSON_String = Obj.toString();
        } catch (JSONException e) {

            Log.v("JSONException", e.toString());
        }
    }


    private boolean CheckPassword() {
        if (Password.equalsIgnoreCase(Confirm_Password)) {
            return true;
        } else {
            ConstantData.DisplayAlert("Password Not Match....", "Confirm Password  Not Match..", Customer_Registration.this);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create__account);
        findid();

        Button_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_fill_or_blank()) {
                    GetValue();
                    if (CheckPassword()) {
                        Create_Json();

                        onsubmit(JSON_String);
                    } else {
                    }
                }

            }
        });
    }

    private void findid() {
        EditText_First_Name = (EditText) findViewById(R.id.EditText_First_Name);
        EditText_Last_Name = (EditText) findViewById(R.id.EditText_Last_Name);
        EditText_Emial_ID = (EditText) findViewById(R.id.EditText_Emial_ID);
        EditText_Password = (EditText) findViewById(R.id.EditText_Password);
        EditText_Confirm_Password = (EditText) findViewById(R.id.EditText_Confirm_Password);
        Button_Create_Account = (Button) findViewById(R.id.Button_Create_Account);

    }

    private boolean hasText(EditText editText) {
        // TODO Auto-generated method stub
        String text = editText.getText().toString().trim();
        editText.setError(null);
        String hint = editText.getHint().toString();
        if (text.length() == 0) {
            editText.setError("Please Insert " + hint + "...");
            editText.setFocusable(true);
            return false;
        } else {
//            editText.setError(null);
        }
        return true;
    }


    private String getValue(EditText editText) {
        // TODO Auto-generated method stub
        String text = editText.getText().toString().trim();
        return text;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public void onsubmit(final String jsonParameterss) {
        class LoadWebPageASYNC extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                ConstantData.progressdialogshow(Customer_Registration.this);
            }

            @Override
            protected String doInBackground(String... urls) {
                String responceStr = "";
                try {

                    HttpParams httpParameters = new BasicHttpParams();

                    int timeoutConnection = 5000;
                    HttpConnectionParams.setConnectionTimeout(httpParameters,
                            timeoutConnection);

                    int timeoutSocket = 7000;
                    HttpConnectionParams.setSoTimeout(httpParameters,
                            timeoutSocket);

                    HttpClient httpClient = new DefaultHttpClient(
                            httpParameters);

                    HttpPost httppost = new HttpPost("http://www.kriosdirect.com/api/rest/customer/");

                    httppost.addHeader("Content-Type", "application/json");
                    httppost.setEntity(new StringEntity(jsonParameterss));

                    HttpResponse response = httpClient.execute(httppost);

                    Log.v("status code", ""
                            + response.getStatusLine().getStatusCode());
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            responceStr = EntityUtils.toString(entity);

                        }
                    }
                    Log.i("res", responceStr);
                } catch (Exception e) {
                    responceStr = "error: " + e.toString();
                }
                Log.v("Customer_Registration", responceStr);
                return responceStr;
            }

            @Override
            protected void onPostExecute(String result) {

                ConstantData.progressdialogclose(Customer_Registration.this);
                if (result != null) {
                    if (!result.startsWith("error:")) {
                        try {

                            Gson gson = new GsonBuilder().setPrettyPrinting()
                                    .create();
                            Serviceresponce Service_Responce = gson.fromJson(result,
                                    Serviceresponce.class);

                            if (Service_Responce != null) {
                                if (Service_Responce.getSuccess().equalsIgnoreCase("1")) {
                                } else {

                                    ConstantData
                                            .DisplayAlert(
                                                    "Alert...!!",
                                                    "msg",
                                                    Customer_Registration.this);

                                }

                            }

                        } catch (JsonSyntaxException e) {

                            ConstantData
                                    .progressdialogclose(Customer_Registration.this);
                            Log.e("respoce error", e.toString());
                        }
                    }
                }
                ConstantData
                        .DisplayAlert(
                                "Error",
                                "Error ",
                                Customer_Registration.this);

            }

        }
        LoadWebPageASYNC task = new LoadWebPageASYNC();
        task.execute(new String[]{jsonParameterss});
    }

    private class Submit_Registration_Data extends
            AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            ConstantData.progressdialogshow(Customer_Registration.this);


        }

        @Override
        protected String doInBackground(String... urls) {
            String responceStr = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://www.kriosdirect.com/api/rest/customer/");
                httpGet.setHeader("Accept", "application/json");
                HttpResponse response = httpclient.execute(httpGet);

                Log.d("WebInvoke", "Saving: "
                        + response.getStatusLine().getStatusCode());

                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        responceStr = EntityUtils.toString(entity);
                    }
                }
            } catch (Exception e) {
                responceStr = "error:" + e.toString();
            }

            Log.v("responceStr", responceStr);
            return responceStr;
        }

        @Override
        protected void onPostExecute(String result) {
            ConstantData.progressdialogclose(Customer_Registration.this);
            if (result != null) {
                if (result.startsWith("error:")) {
                    ConstantData.progressdialogclose(Customer_Registration.this);
                    return;

                } else {
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONObject json_data_obj = new JSONObject();
                        Iterator<String> keys1 = obj.keys();
                        while (keys1.hasNext()) {

                            String key = (String) keys1.next();
                            json_data_obj = obj.getJSONObject(key);
//                            JSONObject json_data_obj = obj.getJSONObject(obj2.toString());

                            UserData userdata = new UserData();

                            userdata.setEntity_id(json_data_obj.getString("entity_id").replace("'", ""));

                        }


                    } catch (Exception e) {
                        Log.e("Exception 1 ", e.toString());
                    }


                }

            }
        }
    }
}
