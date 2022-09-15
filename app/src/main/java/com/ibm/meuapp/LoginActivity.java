package com.ibm.meuapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.ibm.meuapp.data.remote.UserLoginObject;

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

                SharedPreferences pref = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                String emailShared = pref.getString("email", null);
                String senhaShared = pref.getString("senha", null);


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
    public void login(String email, String password){

            Call<RegisterResponse> registerResponseCall = RegisterUserRepository.getUserService().loginUsers(new UserLoginObject(email, password));
            registerResponseCall.enqueue(new Callback <RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if(response.isSuccessful()){
                       // Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                        if (email.equals(response.body().getEmail()) && senha.equals(response.body().getPassword())){
                            Intent intent = new Intent(LoginActivity.this, TelaPrincipalActivity.class);
                            intent.putExtra("KeyEmail", email);
                            intent.putExtra("KeySenha", senha);
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
                .setMessage("Você não possui cadastro, deseja se cadastrar?")
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                        intent.putExtra("keyEmail", email);
                        intent.putExtra("keySenha", senha);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Acesso negado!", Toast.LENGTH_LONG).show();
                    }
                })
                .setIcon(R.drawable.ic_alert)
                .show();
    }
}