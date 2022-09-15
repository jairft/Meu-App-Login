package com.ibm.meuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaPrincipalActivity extends AppCompatActivity {

    private TextView nomeUsuario, emailUsuario, senhaUsuario;
    private Button bt_sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        iniciarComponentes();

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            String name = intent.getStringExtra("name");
            nomeUsuario.setText(name);
        }


        bt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipalActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);

            }
        });
    }

    private void iniciarComponentes(){
        nomeUsuario = findViewById(R.id.textNomeUsuario);
        emailUsuario = findViewById(R.id.textEmailUsusario);
        senhaUsuario = findViewById(R.id.txt_senhaUsuario);
        bt_sair = findViewById(R.id.bt_sair);
    }
}

