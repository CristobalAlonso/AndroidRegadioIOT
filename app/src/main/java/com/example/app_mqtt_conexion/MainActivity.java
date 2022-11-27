package com.example.app_mqtt_conexion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Build;//para obtener el nombre del dispositivo
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String nombre_Dispositivo;   //string para obtener el nombre del dispositivo
    String publicaste;                         //string para mostras el mensaje apublicar
    boolean permiso_publicar=false;          //para permitir o no hacer publicaciones
    boolean intento_publicar=false;           //para saber si intento publicar
    //private TextView tvNombreDispositivo;      //TexView para monitorear

    //parametros del broker la siguiente variable con el broker de shiftr.io
    static String MQTTHOST = "tcp://68.183.119.177"; //el broker
    //static String USERNAME = "accesobroker";          //el token de acceso.
    //static String PASSWORD = "zxcvbnmz";             //la contraceña del token.

    MqttAndroidClient client;              //  clienteMQTT este dispositivo
    MqttConnectOptions options;            // para meter parametros a la conexion

    private TextView textV1;                  //text view para mostrar en interfacve
    private TextView textV2;                  //text view para mostrar en interfacve
    private TextView textV3;                  //text view para mostrar en interfacve
    private TextView textV4;                  //text view para mostrar en interfacve
    private TextView textV5;                  //text view para mostrar en interfacve

    String msg1;
    String msg2;
    String msg3;
    String msg4;
    String msg5;

    Button btnSend;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //obtenemos el nombre del Dispositivo
       //obtenemos el nombre del Dispositivo
       obtener_nombre_Dispositivo();
       //para conextar al broker   //generamos un clienteMQTT
       String clientId = nombre_Dispositivo;//MqttClient.generateClientId();//noombre del celular
       client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
       //para agregar los parametros
       options = new MqttConnectOptions();
       //options.setUserName(USERNAME);
       //options.setPassword(PASSWORD.toCharArray());
       checar_conexion();//revisamos la conexion
       textV1 = findViewById(R.id.humedadTierr);
       textV2 = findViewById(R.id.CO2);
       textV3 = findViewById(R.id.CO);
       textV4 = findViewById(R.id.humedadAmbiental);
       textV5 = findViewById(R.id.temperaturaAmbiente);

       msg1 ="";
       msg2 ="";
       msg3 ="";
       msg4 ="";
       msg5 ="";

       btnSend = findViewById(R.id.btnSend);

    }
    private void obtener_nombre_Dispositivo() {
        String fabricante = Build.MANUFACTURER;
        String modelo = Build.MODEL;
        nombre_Dispositivo=fabricante+" "+modelo;
        //tvNombreDispositivo =(TextView) findViewById(R.id.tv_g);//para mostrar el modelo del celular
        //tvNombreDispositivo.setText(nombre_Dispositivo);//para mostrar en el tv_g e modelo del celular

    }

    public void conexionBroker() {

        //para conextar al broker   //generamos un clienteMQTT
        String clientId = nombre_Dispositivo; //MqttClient.generateClientId();//noombre del celular
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
        //para agregar los parametros
        MqttConnectOptions options = new MqttConnectOptions();
        //options.setUserName(USERNAME);
        //options.setPassword(PASSWORD.toCharArray());
        try {
            IMqttToken token = client.connect(options);//intenta la conexion
            token.setActionCallback(new IMqttActionListener() {

                @Override//metodo de conectado con exito
                public void onSuccess(IMqttToken asyncActionToken) {
                    // mensaje de conectado
                    Toast.makeText(getBaseContext(), "Conectado ", Toast.LENGTH_SHORT).show();
                    sub("RIOT/humT", "RIOT/MQ135","RIOT/MQ7","RIOT/DHT22H","RIOT/DHT22T");
                }

                @Override//si falla la conexion
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // mensaje de que no se conecto
                    Toast.makeText(getBaseContext(), "NO Conectado ", Toast.LENGTH_SHORT).show();
                }


            });


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public  void checar_conexion(){
//si el cliente esta desconectado se conecta falso=no conectado
        if(!client.isConnected()) {
            permiso_publicar=false;// no tienes permiso para publiar
            conexionBroker();//intenta conectarce

        }else{permiso_publicar=true;}//si puedes publicar


    }
    private void publish(String topic, String menssage)
    {
        //String tema="st";///corrrespode al tema de LED
        //String  menssage="ON";
        publicaste= "publicaras " + topic + " " + menssage; //concatenamos el dato a publicar
        intento_publicar=true;//si intento publicar
        checar_conexion();//revisamos la conexion

        if (permiso_publicar){

            try {
                int qos=0;//indica la prioridad del mensaje.
                // 0:envio una vez,
                // 1:se envia hasta garantizar la entrega en caso de fallo resive duplicados
                //2: se  garantiza que se entrege al subcribtor unicamente una vez
                //retenid=false;//true es que el mensaje se quede guardado en el broker asta su actualizacion
                client.publish(topic, menssage.getBytes(),qos, false);
                Toast.makeText(getBaseContext(), publicaste, Toast.LENGTH_SHORT).show();
            }catch (Exception e){e.printStackTrace();}
        }
    }

    public void sub(String topic1, String topic2, String topic3, String topic4, String topic5)
    {
        try
        {
            client.subscribe(topic1, 0);
            client.subscribe(topic2, 0);
            client.subscribe(topic3, 0);
            client.subscribe(topic4, 0);
            client.subscribe(topic5, 0);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {


                    if(topic.matches(topic1))
                    {
                         msg1 =  new String(message.getPayload());
                        textV1.setText(msg1+" %");
                    }
                    if(topic.matches(topic2))
                    {
                         msg2 =  new String(message.getPayload());
                        textV2.setText(msg2+" PPM");
                    }
                    if(topic.matches(topic3))
                    {
                         msg3 =  new String(message.getPayload());
                        textV3.setText(msg3+" PPM");
                    }
                    if(topic.matches(topic4))
                    {
                         msg4 =  new String(message.getPayload());
                        textV4.setText(msg4+" %");
                    }
                    if(topic.matches(topic5))
                    {
                         msg5 =  new String(message.getPayload());
                        textV5.setText(msg5+" °C");
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        }
        catch (MqttException e){
            e.printStackTrace();
        }



    }

    public void insertPHP(String URL, String humTierra, String co2, String co, String humAmbiental, String tempAmbiental)
    {
        String url = "http://192.168.1.85/sensor/insertar.php";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

       StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               if(!response.isEmpty()){
                   Toast.makeText(MainActivity.this, "Dato guardado", Toast.LENGTH_LONG).show();
               }

               else{
                   Toast.makeText(MainActivity.this, "Dato no guardado", Toast.LENGTH_LONG).show();
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(MainActivity.this, error.toString()+" "+URL, Toast.LENGTH_SHORT).show();

           }
       }){
           @NonNull
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params =  new HashMap<String,String>();
               //sub("RIOT/humT", "RIOT/MQ135","RIOT/MQ7","RIOT/DHT22H","RIOT/DHT22T");
               params.put("hum_tierra", humTierra.toString());
               params.put("co2", co2.toString());
               params.put("co", co.toString());
               params.put("hum_ambiental", humAmbiental.toString());
               params.put("temp_ambiental",tempAmbiental.toString());

               return params;
           }
       };


        requestQueue.add(stringRequest);
    }

    public void sendData(View view)
    {
        insertPHP("http://192.168.1.85/sensor/insertar.php",msg1,msg2,msg3,msg4,msg5);
    }



    /*
    //Sistema regadio inteligente
    public void publicarD1oN(View view)
    {
        publish("RIOT/01","1");
    }
    public void publicarD1Off(View view)
    {
        publish("RIOT/01","0");
    }

    //Bomba de agua
    public void publicarD2On(View view)
    {
        publish("RIOT/02","0");
    }
    public void publicarD2Off(View view)
    {
        publish("RIOT/02","1");
    }

     */

}