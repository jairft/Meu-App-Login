package com.ibm.meuapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.ibm.meuapp.data.RegisterUserRepository;
import com.ibm.meuapp.data.remote.RegisterResponse;
import com.ibm.meuapp.data.remote.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private EditText editeEmail, editeSenha;
    String email, senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editeEmail = findViewById(R.id.editTextEmail);
        editeSenha = findViewById(R.id.editTextSenha);
        Button butao = findViewById(R.id.btnEntrar);
        TextView txtCadastro = findViewById(R.id.txt_cadastro);

        butao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                email = editeEmail.getText().toString();
                senha = editeSenha.getText().toString();


                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("ATENÇÃO!")
                            .setMessage("Preencha todos os campos!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(R.drawable.ic_alert)
                            .show();
                }else {
                        login(email, senha);
                }
            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

    }
    public void login(String email2, String password){

            Call<RegisterResponse> registerResponseCall = RegisterUserRepository.getUserService().loginUsers(new UserLogin(email2, password));

            registerResponseCall.enqueue(new Callback <RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if(response.isSuccessful()){
                        if (email.equals(response.body().getEmail()) && senha.equals(response.body().getPassword())){
                            Intent intent = new Intent(LoginActivity.this, TelaPrincipalActivity.class);
                            String name = response.body().getName();
                            String email = response.body().getEmail();
                            intent.putExtra("keyName", name);
                            intent.putExtra("keyEmail", email);
                            Toast.makeText(getApplicationContext(), "login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            //mensagem de usuario nao existente
                        }
                    }else  {
                        dialog();
                    }
                }
                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Falha no cadastro!"+ t.getLocalizedMessage() + ";" + t.getCause(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    private void dialog(){
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("ATENÇÃO!")
                .setMessage("Email ou senha incorretos!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(R.drawable.ic_alert)
                .show();
    }
}