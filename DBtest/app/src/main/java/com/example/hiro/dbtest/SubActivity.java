package com.example.hiro.dbtest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hiro on 2017-01-19.
 */

public class SubActivity extends Activity {

    public  String data;
    private  TextView nameTxt;
    private  TextView addressTxt;
    View line;

    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        ////////////////////////////////////////////////////////////////////////
        ////////////////테이블 레이아웃 ////////////////////////////////////////
        tl =  (TableLayout) findViewById(R.id.table);


        Intent intent = getIntent();
        data = intent.getStringExtra("jsonStr");
        Log.e("Data ==>",  data );

        /*
        nameTxt = new TextView(this);
        addressTxt = new TextView(this);
       // nameTxt = (TextView)findViewById(R.id.nameText);
       // addressTxt = (TextView)findViewById(R.id.addressText);
        */

//////////////////////////////////////////////////////////////////////////////////



        doJSONParser();
    }

    void doJSONParser(){
        StringBuffer sb = new StringBuffer();
        StringBuffer add_b = new StringBuffer();


        try {
            JSONObject root = new JSONObject(data);
            JSONArray jarray = root.getJSONArray("results");  // JSONArray 생성

            for(int i=0; i < jarray.length(); i++){
                TableRow tr = new TableRow(this);


                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                String address = jObject.getString("ADDRESS");
                String name = jObject.getString("NAME");

                //sb.append(name);     //append  스트링형을 합쳐 주는 명령어
                //add_b.append(address);
                nameTxt = new TextView(this);
                addressTxt = new TextView(this);
                nameTxt.setText(name + "  ");
                addressTxt.setText(address);
                //nameTxt.setText(sb.toString());
                //addressTxt.setText(add_b.toString());
                //    lLayout.addView(nameTxt);
                //     lLayout.addView(addressTxt);


                nameTxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                addressTxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(nameTxt);
                tr.addView(addressTxt);

                //tr.setBackground(Drawable.createFromPath("@drawable/border"));
                tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

              //  line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,15 ));
              //  setContentView(line);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //  setContentView(lLayout);
    } // end doJSONParser()

    /*
    void doJSONParser(){
        StringBuffer sb = new StringBuffer();
        StringBuffer add_b = new StringBuffer();


        try {
            JSONObject root = new JSONObject(data);
            JSONArray jarray = root.getJSONArray("results");  // JSONArray 생성

            for(int i=0; i < jarray.length(); i++){
                TableRow tr = new TableRow(this);


                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                String address = jObject.getString("ADDRESS");
                String name = jObject.getString("NAME");

                //sb.append(name);     //append  스트링형을 합쳐 주는 명령어
                //add_b.append(address);
                nameTxt = new TextView(this);
                addressTxt = new TextView(this);
                nameTxt.setText(name + "  ");
                addressTxt.setText(address);
                //nameTxt.setText(sb.toString());
                //addressTxt.setText(add_b.toString());
                //    lLayout.addView(nameTxt);
                //     lLayout.addView(addressTxt);


                nameTxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                addressTxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(nameTxt);
                tr.addView(addressTxt);

                tr.setBackgroundColor(Color.parseColor("#00ff00"));
                tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                LinearLayout line = new LinearLayout(this);
                line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 6));
                line.setOrientation(LinearLayout.HORIZONTAL);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


      //  setContentView(lLayout);
    } // end doJSONParser()
    */

    /*
    public void moveMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

      //  finish(); //액티비티 종료
    }
    */
}
