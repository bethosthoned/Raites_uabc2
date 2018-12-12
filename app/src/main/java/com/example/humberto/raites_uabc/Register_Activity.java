package com.example.humberto.raites_uabc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register_Activity extends AppCompatActivity {

    EditText nameET,emailET,passET,passconfirmET;
    Button registrBTN;
    String name,email,pass,passconfirm,body,mensaje;
    final String url = "https://uabc-raites.herokuapp.com/api/register";

    JSONObject obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        nameET = (EditText) findViewById(R.id.nameET);
        emailET = (EditText) findViewById(R.id.emailET);
        passET = (EditText) findViewById(R.id.passET);
        passconfirmET = (EditText) findViewById(R.id.passconfirmET);

        addListenerOnRegisterButton();

    }


    public void addListenerOnRegisterButton(){
        registrBTN = (Button) findViewById(R.id.registerBTN);

        registrBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValueFromEditText();

                Map<String, Object> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("password",pass);
                params.put("password_confirmation",passconfirm);
                params.put("is_driver",true);

                JSONObject user = new JSONObject(params);

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, user, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Respuesta:",response.toString());
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                               //String statusCode = String.valueOf(error.networkResponse.statusCode);
                               if(error.networkResponse.data!=null){
                                   try {
                                       body = new String(error.networkResponse.data,"UTF-8");
                                       //Log.e("Cuerpo",body);
                                       try {
                                           obj = new JSONObject(body);
                                           Log.d("My App", obj.toString());
                                           mensaje = obj.getString("message");
                                       } catch (Throwable t) {
                                           Log.e("My App", "Could not parse malformed JSON: \"" + body + "\"");
                                       }
                                       Toast.makeText(Register_Activity.this, mensaje ,Toast.LENGTH_LONG).show();

                                   } catch (UnsupportedEncodingException e){
                                       e.printStackTrace();
                                   }
                               }

                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("accept","application/json");
                        return headers;
                    }
                };


                Volley.newRequestQueue(Register_Activity.this).add(jsObjRequest);
                Toast.makeText(Register_Activity.this, "Registrado",Toast.LENGTH_LONG).show();



            }
        });
    }

    public void getValueFromEditText(){
        name = nameET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        pass = passET.getText().toString().trim();
        passconfirm = passconfirmET.getText().toString().trim();

    }
}
