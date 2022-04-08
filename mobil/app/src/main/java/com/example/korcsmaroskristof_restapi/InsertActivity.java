package com.example.korcsmaroskristof_restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class InsertActivity extends AppCompatActivity {

    private EditText editTextNev, editTextOrszag, editTextLakossag;
    private Button buttonHozzaad, buttonVissza2;

    private String nev, orszag;
    private int lakossag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();

        buttonVissza2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonHozzaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validalas()) {
                    RequestTask requestTask = new RequestTask();
                    requestTask.execute();
                }
                else {
                    Toast.makeText(InsertActivity.this, "Hibás adatok", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validalas() {
        boolean helyes = true;

        try {
            nev = editTextNev.getText().toString().trim();
            orszag = editTextOrszag.getText().toString().trim();
            lakossag = Integer.parseInt(editTextLakossag.getText().toString().trim());
            if(nev.trim().isEmpty() || orszag.trim().isEmpty() || lakossag < 100000 || lakossag > 10000000) {
                helyes = false;
            }
        } catch (Exception e) {
            helyes = false;
            e.printStackTrace();
        }

        return helyes;
    }

    private void init() {
        editTextNev = findViewById(R.id.editTextNev);
        editTextOrszag = findViewById(R.id.editTextOrszag);
        editTextLakossag = findViewById(R.id.editTextLakossag);
        buttonHozzaad = findViewById(R.id.buttonHozzaad);
        buttonVissza2 = findViewById(R.id.buttonVissza2);

        nev = "";
        orszag = "";
        lakossag = 0;
    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... voids) {

            Response response = null;
            try {
                String data = String.format("{\"nev\":\"%s\",\"orszag\":\"%s\",\"lakossag\":\"%d\"}", nev, orszag, lakossag);
                response = RequestHandler.post("http://10.0.2.2:8000/api/varosok", data);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (response == null || response.getResponseCode() >= 400){
                Toast.makeText(InsertActivity.this, "Hiba történt a hozzáadás során", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(InsertActivity.this, "Sikeres hozzáadás!", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
}