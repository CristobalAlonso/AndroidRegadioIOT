package com.example.app_mqtt_conexion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SHOWTABLES extends AppCompatActivity {

    private RequestQueue rq;
    private TextView txt;
    private String[] strList;
    //private ArrayList<String> strList = new ArrayList<String>(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtables);
        rq = Volley.newRequestQueue(this);
        txt = findViewById(R.id.textView);
        txt.setText("");
        getData();
        txt.setMovementMethod(new ScrollingMovementMethod());


        Toast.makeText(getApplicationContext(), txt.getText(), Toast.LENGTH_SHORT).show();
        //this.txt.setText(txt);
    }

    public  void getData()
    {
        //LinearLayout linearLayout = new LinearLayout(this);
        //setContentView(linearLayout);
        //linearLayout.setOrientation(LinearLayout.VERTICAL);
        String url = "http://192.168.1.85/sensor/consultar.php";
        ArrayList<String> respomse = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for( int i = 0; i < response.length(); i++ )
                    {
                        //fecha,hum_tierra,co2,co,hum_ambiental,tem_ambiental
                        JSONObject objeto = new JSONObject(response.get(i).toString());
                        objeto = new JSONObject(response.get(i).toString());
                        txt.append("Regadio n°"+objeto.getString("id")+":\n");
                        txt.append("Fecha: "+objeto.getString("fecha")+"\n");
                        txt.append("Humedad tierra :"+objeto.getString("hum_tierra")+"%\n");
                        txt.append("CO2 :"+objeto.getString("co2")+" PPM\n");
                        txt.append("CO :"+objeto.getString("co")+" PPM\n");
                        txt.append("Humedad ambiental :"+objeto.getString("hum_ambiental")+"%\n");
                        txt.append("Temperatura ambiental :"+objeto.getString("tem_ambiental")+"%\n");
                        txt.append("__________________________________________"+"\n");
                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        rq.add(req);
        //
        //

        //this.txt.setText(txt);
    }
}