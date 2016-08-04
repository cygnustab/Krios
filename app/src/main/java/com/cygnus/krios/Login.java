package com.cygnus.krios;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends Activity {

    Button Button_Create_Account, Button_Login;
    EditText EditText_Emailid, EditText_Password;
    String username, password;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findid();


        Button_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Login.this, Customer_Registration.class);
                startActivity(i);


            }
        });
        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = EditText_Emailid.getText().toString();
                password = EditText_Password.getText().toString();

                if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {

                    ConstantData.DisplayAlert("Alert !!!", "Please Enter UserID Or Password ..", Login.this);
                    ConstantData.Login_User = "";
                } else {
                    ConstantData.Login_User = username;
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                }


            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void findid() {

        Button_Create_Account = (Button) findViewById(R.id.btnCreate_Account);
        Button_Login = (Button) findViewById(R.id.btnSubmit);
        EditText_Emailid = (EditText) findViewById(R.id.etEmial_ID);
        EditText_Password = (EditText) findViewById(R.id.etPassword);
    }

    public void asyncJson() {


        //perform a Google search in just a few lines of code

        JSONObject postJson = new JSONObject();

        JSONObject obj = new JSONObject();

        JSONArray array = new JSONArray();

        TelephonyManager telephonyManager = (TelephonyManager) Login.this
                .getSystemService(Context.TELEPHONY_SERVICE);


        try {

            obj.put("userid", "" + EditText_Emailid.getText());
            obj.put("password", "" + EditText_Password.getText());
            obj.put("deviceId", "" + telephonyManager.getDeviceId());


            Log.i("json", obj.toString());


        } catch (Exception e) {
            // TODO: handle exception
        }


        final AQuery aq = new AQuery(Login.this);


        final AQuery post = aq.post("", obj, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {


                if (json != null) {
                    try {
//                        ConstantData.progressclose(Login.this);

                        JsonResult jsonResult = new JsonResult();
//                        JsonElement json2 = new JsonParser()
//                                .parse(json.toString());
//
//                        Type type = new TypeToken<JsonResult>() {
//                        }.getType();
//                        jsonResult = new Gson().fromJson(json2,
//                                type);

//                        if (jsonResult.getResult().equalsIgnoreCase("success")) {
//                        }

                    } catch (Exception ex) {
                        Toast.makeText(aq.getContext(), "Error :" + json.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
//                    ConstantData.progressclose(Login.this);
                    //ajax error, show error code
                    Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cygnus.krios/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cygnus.krios/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
