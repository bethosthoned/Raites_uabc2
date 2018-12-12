package com.example.humberto.raites_uabc;

import android.support.design.widget.TabItem;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {


    Button perfilBtn, publicacionesBtn, publicarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Perfil fr1 = new Perfil();

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.contenedor,fr1);
        tx.commit();

        perfilBtn = (Button)findViewById(R.id.perfilBtn);
        publicacionesBtn = (Button)findViewById(R.id.publicacionesBtn);
        publicarBtn = (Button)findViewById(R.id.publicarBtn);




        perfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Perfil fr1 = new Perfil();
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.contenedor,fr1);
                tx.commit();
            }
        });

        publicacionesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publicaciones fr2 = new Publicaciones();
                FragmentTransaction tx1 = getSupportFragmentManager().beginTransaction();
                tx1.replace(R.id.contenedor,fr2);
                tx1.commit();
            }
        });

        publicarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publicar fr3 = new Publicar();
                FragmentTransaction tx2 = getSupportFragmentManager().beginTransaction();
                tx2.replace(R.id.contenedor,fr3);
                tx2.commit();
            }
        });
    }


}
