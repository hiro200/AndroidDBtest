package com.example.hiro.dbtest;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    ////////////////////////DB 데이터 전송/////////////////////////////////////////////
    private EditText editTextName;
    private EditText editTextAdd;
    private String name;
    private String address;
    public  String status;

/////////////////////////////////////////////////////////////////////////////////////
///////DB 데이터 받기////////////////////////////////////////////////
    ArrayList<ListItem> listItem = new ArrayList<ListItem>();
    TextView txtView3;
    phpDown task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////////////////////////DB 데이터 전송///////////////////////////////////////
        editTextName = (EditText) findViewById(R.id.name);
        editTextAdd = (EditText) findViewById(R.id.address);

////////////////////////////////////////////////////////////////////////////////////
        ///////DB 데이터 받기////////////////////////////////////////////////
        task = new phpDown();
        txtView3 = (TextView)findViewById(R.id.textView3);
        task.execute("http://hyunho80.cafe24.com/dbget.php"); //나의 dp주소와  연동시킬 php
    }


///////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////DB 데이터 전송/////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    public void insert(View view) {
        String name = editTextName.getText().toString();
        String address = editTextAdd.getText().toString();

        insertToDatabase(name, address);
    }


    private void insertToDatabase(String name, String address) {


        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String name = (String) params[0];
                    String address = (String) params[1];

                    String link = "http://hyunho80.cafe24.com/user_signup/signup_user_information.php";
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
                    Log.e("DB start", "start");
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");

                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    BufferedReader rd = null;
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line = null;
                    while ((line = rd.readLine()) != null) {
                        Log.d("BufferedReader.", line);
                    }
                } catch (IOException e) {
                    return new String("Exception: " + e.getMessage());
                }

                return null;
            }

        }
        InsertData task = new InsertData();
        task.execute(name, address);
    }
////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////다른 액티비티로 이동////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////
    public void moveSub(View view)
    {
        //Intent intent = new Intent(this, SubActivity.class);
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra("jsonStr", status);
        startActivity(intent);
    }

/////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////DB 데이터 받기//////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    private class phpDown extends AsyncTask<String, Integer,String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            String line = br.readLine();
                            if(line == null) break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str){

            String name = null;
            String address = null;
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);

                    name = jo.getString("name");
                    address = jo.getString("address");
                    listItem.add(new ListItem(name,address));
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            status = str;
            txtView3.setText(str); //게시판 내용을 다가져옴
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
