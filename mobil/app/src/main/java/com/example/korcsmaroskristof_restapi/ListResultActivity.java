package com.example.korcsmaroskristof_restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListResultActivity extends AppCompatActivity {

    private Button buttonVissza;
    private ListView listViewVarosok;
    private List<Varos> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        init();

        RequestTask requestTask = new RequestTask();
        requestTask.execute();
    }

    private void init() {
        buttonVissza = findViewById(R.id.buttonVissza1);
        listViewVarosok = findViewById(R.id.listViewVarosok);
    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                response = RequestHandler.get("http://10.0.2.2:8000/api/varosok");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Gson converter = new Gson();
            if (response == null || response.getResponseCode() >= 400){
                Toast.makeText(ListResultActivity.this, "Hiba történt a kérés feldolgozása során", Toast.LENGTH_SHORT).show();
            }
            else {
                Varos[] varosok = converter.fromJson(response.getContent(), Varos[].class);
                list.clear();
                list.addAll(Arrays.asList(varosok));
                ArrayAdapter<Varos> arrayAdapter = new ArrayAdapter<Varos>(ListResultActivity.this, R.layout.activity_listitem, R.id.textViewListItem, list);
                listViewVarosok.setAdapter(arrayAdapter);
            }

        }
    }

}