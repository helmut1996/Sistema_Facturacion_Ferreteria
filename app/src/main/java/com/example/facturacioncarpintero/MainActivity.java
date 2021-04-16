package com.example.facturacioncarpintero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.facturacioncarpintero.ConexionBD.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btn_login;
    EditText pass;
    Spinner user;
    String NombreVendedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initView();
        permisos();
    }

    private void permisos() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void initView() {
        user=findViewById(R.id.editUsers);
        pass=findViewById(R.id.editPassword);
        btn_login=findViewById(R.id.btn_Login);
        btn_login.setOnClickListener(this);
        //user.setAdapter();
    }

    @Override
    public void onClick(View v) {

        Conectar();
    }


    private void Conectar() {

        if (pass.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "debe ingresar su pin ", Toast.LENGTH_LONG).show();
            pass.requestFocus();
        } else {
            try {
                DBConnection dbConnection = new DBConnection();
                dbConnection.conectar();
                Statement stm = dbConnection.getConnection().createStatement();
                ResultSet rs = stm.executeQuery("Select pin, name from users where pin='" + pass.getText().toString() + "'");
                if (rs.next()) {
                    NombreVendedor= rs.getString(2);
                    int id=rs.getInt(1);
                    Intent i = new Intent(this,MainClientes.class);
                    i.putExtra("IdVendedor",id);
                    i.putExtra("NombreUsuario",NombreVendedor);
                    startActivity(i);
                    finish();
                } else {

                    Toast.makeText(getApplicationContext(), "Pin incorrecto", Toast.LENGTH_LONG).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(intent);
    }
}