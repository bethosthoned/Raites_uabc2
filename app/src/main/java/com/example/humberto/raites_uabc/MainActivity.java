package com.example.humberto.raites_uabc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class MainActivity extends AppCompatActivity {

    EditText userET, passET;
    String user, pass, body,mensaje;
    Button loginBT;

    JSONObject obj;

    static final String MY_PREFS_NAME = "user_data";

    final String url = "https://uabc-raites.herokuapp.com/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView registerET = (TextView) findViewById(R.id.registerTV);

        userET = (EditText) findViewById(R.id.userET);
        passET = (EditText) findViewById(R.id.passET);

        registerET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register_Activity.class));
            }
        });

        addListenerOnLoginButton();

    }

    public void addListenerOnLoginButton(){
        loginBT = (Button) findViewById(R.id.loginBT);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValueFromEditText();
                Map<String,String> params = new HashMap<>();
                params.put("email",user);
                params.put("password",pass);


                JSONObject user = new JSONObject(params);

                JsonObjectRequest jsObjectRequest = new JsonObjectRequest(POST, url, user, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Respuesta:", response.toString());
                        startActivity(new Intent(MainActivity.this,Dashboard.class));
                        String token = null;
                        try {
                            token = (String) response.getString("access_token");
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                            editor.putString("token","Bearer" + " " + token);
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(MainActivity.this,token, Toast.LENGTH_SHORT).show();
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(error.networkResponse.data!=null){
                                    try{
                                        body = new String(error.networkResponse.data,"UTF-8");
                                        Log.e("Body",body);
                                        try{
                                            obj = new JSONObject(body);
                                            Log.d("My app", obj.toString());
                                            mensaje = obj.getString("message");
                                        } catch (Throwable t){
                                            Log.e("My app", "Could not parse malformed JSON: \"" + body + "\"");
                                        }
                                        Toast.makeText(MainActivity.this,mensaje, Toast.LENGTH_SHORT).show();
                                    } catch (UnsupportedEncodingException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }


                ) {
                    @Override
                    public Map<String,String> getHeaders(){
                        Map<String,String> headers = new HashMap<>();
                        headers.put("accept","application/json");
                        return headers;
                    }
                };

                Volley.newRequestQueue(MainActivity.this).add(jsObjectRequest);
            }
        });



    }

    public void getValueFromEditText(){
        user = userET.getText().toString().trim();
        pass = passET.getText().toString().trim();
    }


}
