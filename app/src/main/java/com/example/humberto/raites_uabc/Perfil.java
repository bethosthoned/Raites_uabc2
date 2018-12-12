package com.example.humberto.raites_uabc;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Perfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<JSONObject> mVehicles = new ArrayList<>();

    public Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    Button agregarAuto;

    String name,email;

    TextView nameTv,emailTv;

    final String url = "https://uabc-raites.herokuapp.com/api/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_perfil, container, false);
        final View view = inflater.inflate(R.layout.fragment_perfil,container,false);

        agregarAuto = (Button) view.findViewById(R.id.agregarAuto);
        agregarAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] textArray = {"One", "Two", "Three", "Four"};
                final RecyclerView Ly = (RecyclerView) view.findViewById(R.id.autosLayout);
                for( int i = 0; i < textArray.length; i++ )
                {
                    TextView textView = new TextView(getActivity());
                    textView.setText(textArray[i]);
                    Ly.addView(textView);
                }

            }
        });
        RequestHelper.request(getActivity(), Request.Method.GET, url+"me", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("PERFIL", response.toString());
                try{
                    name = (String) response.getString("name");
                    email = (String) response.getString("email");
                    nameTv = (TextView) view.findViewById(R.id.nameTv);
                    emailTv = (TextView) view.findViewById(R.id.emailTv);

                    nameTv.setText(name);
                    emailTv.setText(email);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

        final RecyclerView vehiclesView = (RecyclerView) view.findViewById(R.id.autosLayout);
        RecyclerViewHelper.config(getActivity(), vehiclesView, R.layout.fragment_auto, mVehicles, new RecyclerViewHelper.ItemRenderer() {
            @Override
            public void render(View root, JSONObject object) throws JSONException {
                TextView autoTv = (TextView) root.findViewById(R.id.autoTv);
                autoTv.setText(object.getString("brand"));
            }
        });
        RequestHelper.requestArray(getActivity(), Request.Method.GET, url+"vehicles", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("VEHICULOS",response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        mVehicles.add(response.getJSONObject(i));
                        vehiclesView.getAdapter().notifyItemChanged(mVehicles.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        RequestHelper.requestArray(getActivity(),Request.Method.GET,url+"routes", new Response.Listener<JSONArray>(){
            @Override
            public  void onResponse(JSONArray response){
                Log.d("RUTAS",response.toString());
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
