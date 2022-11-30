package com.example.app_mqtt_conexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Config extends AppCompatActivity
{
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

    EditText etd;
    EditText eth;

    Button btnSave;
    Button btnBack;

    String msg1;
    String msg2;

    TextView txt1;
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

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

        msg1 = "";
        msg2 = "";

        etd = findViewById(R.id.days);
        eth = findViewById(R.id.hours);

        txt1 = findViewById(R.id.actualDays);
        txt2 = findViewById(R.id.actualR);

        btnSave = findViewById(R.id.btnSave);
        //btnBack = findViewById(R.id.btnBack);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean d = false;
                boolean h = false;
                String responsed = "";
                String responseh = "";

                if(eth.getText().toString().equals(null) && eth.getText().toString().equals(null) )
                {
                    Toast.makeText(Config.this, "Error las horas y los dias estan vacios", Toast.LENGTH_SHORT).show();
                }
                else if(etd.getText().toString().equals(null))
                {
                    Toast.makeText(Config.this, "Error los dias estan vacios", Toast.LENGTH_SHORT).show();
                }
                else if(eth.getText().toString().equals(null))
                {
                    Toast.makeText(Config.this, "Error las horas estan vacias", Toast.LENGTH_SHORT).show();
                }else
                {
                    if(Integer.parseInt(etd.getText().toString())>7)
                    {
                        etd.setText("7");
                        responsed = "El numero de dias es superior a la cantidad de dias de una semana";
                        d=false;

                    }
                    else if(Integer.parseInt(etd.getText().toString())<=0)
                    {
                        etd.setText("0");
                        responsed = "El numero de dias es demasiado pequeño";
                        d=false;

                    }else
                    {
                        d = true;
                    }

                    if(Integer.parseInt(eth.getText().toString())>23)
                    {
                        eth.setText("23");
                        responseh = "El numero de horas es superior a la cantidad de horas en un dia";
                        h=false;

                    }
                    else if(Integer.parseInt(etd.getText().toString())<0)
                    {
                        etd.setText("0");
                        responseh = "El numero de horas es demasiado pequeño";
                        h=false;

                    }else{
                        h=true;
                    }

                    if(h && d)
                    {
                        Toast.makeText(getBaseContext(), "Guardado", Toast.LENGTH_SHORT).show();
                        publish("RIOT/BT",eth.getText().toString());
                        publish("RIOT/BTD",etd.getText().toString());


                    }else{
                        Toast.makeText(getBaseContext(), "ERROR: "+responsed+", "+responseh, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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
                    sub("RIOT/BTD","RIOT/BT");
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

    public void sub(String topic1, String topic2)
    {
        try
        {
            client.subscribe(topic1, 0);
            client.subscribe(topic2, 0);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception
                {
                    //

                    if(topic.matches(topic1))
                    {
                        msg1 =  new String(message.getPayload());
                        txt1.setText("Regadio el dia "+msg1);
                    }
                    if(topic.matches(topic2))
                    {
                        msg2 =  new String(message.getPayload());
                        if(Integer.parseInt(msg2)>9)
                        {
                            txt2.setText("Regadio a las "+msg2+":00 hrs");
                        }else{
                            txt2.setText("Regadio a las 0"+msg2+":00 hrs");
                        }

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

}