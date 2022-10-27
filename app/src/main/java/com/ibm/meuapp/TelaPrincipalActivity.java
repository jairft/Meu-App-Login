package com.ibm.meuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaPrincipalActivity extends AppCompatActivity {

    private Button bt_sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        bt_sair = findViewById(R.id.bt_sair);

        TextView  nomeUsuario = findViewById(R.id.textNomeUsuario);
        TextView  emailUsuario = findViewById(R.id.textEmailUsusario);


        String name = getIntent().getStringExtra("keyName");
        String email = getIntent().getStringExtra("keyEmail");
        nomeUsuario.setText(name);
        emailUsuario.setText(email);



        bt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipalActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);

            }
        });
    }

}

