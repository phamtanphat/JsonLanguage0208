package com.ptp.phamtanphat.jsonlanguage0208;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    TextView txtjson;
    Button btnvn,btnen;
    String dulieu ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtjson = (TextView) findViewById(R.id.textviewjson);
        btnen = (Button) findViewById(R.id.buttonen);
        btnvn = (Button) findViewById(R.id.buttonvn);

        btnen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DocNgonNgu("en");
            }
        });
        btnvn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocNgonNgu("vn");
            }
        });
        new GetJsonArray().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo3.json");
    }
    private class GetJsonArray extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null){
                dulieu = s;
            }
            DocNgonNgu("en");
            super.onPostExecute(s);
        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
    public void DocNgonNgu(String ngonngu){
        try {
            JSONObject jsonObjectlanguage = new JSONObject(dulieu);
            JSONObject jsonObjectngongu = jsonObjectlanguage.getJSONObject("language");

            JSONObject jsonObjecten = jsonObjectngongu.getJSONObject(ngonngu);

            String name = jsonObjecten.getString("name");
            String address = jsonObjecten.getString("address");
            String course1 = jsonObjecten.getString("course1");
            String course2 = jsonObjecten.getString("course2");
            String course3 = jsonObjecten.getString("course3");

            txtjson.setText(name + "\n" + address + "\n" + course1 + "\n" + course2 + "\n" + course3 + "\n");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
}
