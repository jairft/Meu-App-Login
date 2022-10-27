package com.ibm.meuapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ibm.meuapp.data.RegisterUserRepository;
import com.ibm.meuapp.data.remote.RegisterRequest;
import com.ibm.meuapp.data.remote.RegisterResponse;
import com.ibm.meuapp.data.remote.RegisterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CadastroActivity extends AppCompatActivity {
    private EditText etNome, etEmail, etSenha, etSenhaConf;
    private ImageButton btn_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        iniciarComponentes();

        Button btnCadastro = findViewById(R.id.btnCadastrar);


        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                String nome = etNome.getText().toString();
                String senhaConf = etSenhaConf.getText().toString();

                if(nome.isEmpty() | email.isEmpty() | senha.isEmpty() | senhaConf.isEmpty()){
                    new AlertDialog.Builder(CadastroActivity.this)
                            .setTitle("ATENÇÃO!")
                            .setMessage("Preencha todos os campos!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(R.drawable.ic_alert)
                            .show();
                }else {
                    if(senha.equals(senhaConf)){
                        salveUser(createdRequest());



                    }else {
                        alerta("As senhas não são indênticas!");
                    }
                }
            }
        });
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    public RegisterRequest createdRequest(){
        RegisterRequest request = new RegisterRequest();
        request.setName(etNome.getText().toString());
        request.setEmail(etEmail.getText().toString());
        request.setPassword(etSenha.getText().toString());

        return request;
    }
    public void salveUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = RegisterUserRepository.getUserService().saveUsers(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    dialog();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha no cadastro!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void alerta(String msg) {
        new AlertDialog.Builder(CadastroActivity.this)
                .setTitle("ATENÇÃO!")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(R.drawable.ic_alert)
                .show();
    }

    public void iniciarComponentes(){
        etNome = findViewById(R.id.cadsNome);
        etEmail = findViewById(R.id.cadsEmail);
        etSenha = findViewById(R.id.cadsSenha);
        etSenhaConf = findViewById(R.id.cadsConfSenha);
        btn_voltar = findViewById(R.id.btn_voltar);

    }
    private void dialog(){
        new AlertDialog.Builder(CadastroActivity.this)
                .setTitle("ATENÇÃO!")
                .setMessage("Email já cadastrado!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(R.drawable.ic_alert)
                .show();
    }
}