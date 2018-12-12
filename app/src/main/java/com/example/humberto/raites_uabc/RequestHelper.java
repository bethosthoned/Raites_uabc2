package com.example.humberto.raites_uabc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestHelper {

    private static final String MY_PREFS_NAME = "user_data";

    public static void request(Activity activity, final int method, String url, Response.Listener<JSONObject> listener) {
        SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME,Context.MODE_PRIVATE);
        final String restoredText = prefs.getString("token",null);
        JsonObjectRequest request = new JsonObjectRequest(url, null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", "Error al cargar perfil: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", restoredText);
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public int getMethod() {
                return method;
            }
        };

        Volley.newRequestQueue(activity).add(request);
    }

    public static void requestArray(Activity activity, final int method, String url, Response.Listener<JSONArray> listener) {
        SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME,Context.MODE_PRIVATE);
        final String restoredText = prefs.getString("token",null);
        JsonArrayRequest request = new JsonArrayRequest(url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", "Error al cargar perfil: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", restoredText);
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public int getMethod() {
                return method;
            }
        };

        Volley.newRequestQueue(activity).add(request);
    }

}
