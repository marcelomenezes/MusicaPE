package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

public class LoginActivity extends AppCompatActivity {

    private EditText editLoginUsuario;
    private EditText editLoginSenha;
    private Button botaoLogar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //campos de Login
        editLoginUsuario = (EditText) findViewById(R.id.editLoginUsuarioId);
        editLoginSenha = (EditText) findViewById(R.id.editLoginSenhaId);
        botaoLogar = (Button) findViewById(R.id.botaoLogarId);

        ParseUser.logOut();
        //Verificar se o usuario está logado
        verificarUsuarioLogado();


        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = editLoginUsuario.getText().toString();
                String senha = editLoginSenha.getText().toString();

                verificarLogin(usuario, senha);
            }
        });

    }

    private void verificarLogin(String usuario, String senha){
        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Toast.makeText(LoginActivity.this, "Login feito com sucesso", Toast.LENGTH_SHORT).show();
                    abrirAreaPrincipal();
                }
                else{
                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(LoginActivity.this, erro , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }


    private void verificarUsuarioLogado(){

        if(ParseUser.getCurrentUser() != null){
            abrirAreaPrincipal();

        }
    }

    private void abrirAreaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity( intent);
        finish();
    }
}
