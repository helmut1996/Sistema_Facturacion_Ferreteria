package com.example.facturacionlibreria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.facturacionlibreria.Adapter.AdapterClientes;
import com.example.facturacionlibreria.ConexionBD.DBConnection;
import com.example.facturacionlibreria.model.itemList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainClientes extends AppCompatActivity{
    // VARIABLE PARA RECYCLEVIEW
    RecyclerView recyclerViewCliente;
    AdapterClientes adapterCliente;
    EditText search;
    Button btn_buscador_cliente;
    List<itemList> itemCList;
    DBConnection sesion;
    String CapturandoClientes;
    public static int id;
    public static String NombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_clientes);
        getSupportActionBar().setTitle(" Lista Clientes");

        id=getIntent().getIntExtra("IdVendedor",0);

        System.out.println("ID VENDEDOR =====>"+id);

        Bundle extra=getIntent().getExtras();

        if (extra !=null) {
            NombreUsuario = extra.getString("NombreUsuario");
            System.out.println("Nombre Usuario Activity Clientes----->" + NombreUsuario);
        }


        initview();
        filter2("00000-CLIENTE VARIOS");

        btn_buscador_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (search.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingrese un cliente",Toast.LENGTH_SHORT).show();
                } else {
                    CapturandoClientes= search.getText().toString();

                    filter(CapturandoClientes);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                }

            }
        });


    }


    private void filter(String text) {
        initValues();
        ArrayList<itemList> filteredlist = new ArrayList<>();
        for (itemList item : itemCList) {
            if (item.getNombre().toUpperCase().contains(search.getText().toString().toUpperCase())) {
                filteredlist.add(item);
            }
        }
        adapterCliente.filterList(filteredlist);

    }


    private void filter2(String text ) {
        initValues();
        text="CLIENTE VARIOS";
        ArrayList<itemList> filteredlist = new ArrayList<>();
        for (itemList item : itemCList) {
            if (item.getNombre().toUpperCase().contains(search.getText().toString().toUpperCase())) {
                filteredlist.add(item);
            }
        }
        adapterCliente.filterList(filteredlist);

    }
    public void initview() {
        recyclerViewCliente = findViewById(R.id.listaClientes);
        search = findViewById(R.id.search);
        btn_buscador_cliente=findViewById(R.id.btnBuscadorCliente);
    }

    public void initValues() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewCliente.setLayoutManager(manager);
        recyclerViewCliente.setHasFixedSize(true);
        itemCList = obtenerclientesBD();
        adapterCliente = new AdapterClientes(itemCList);
        recyclerViewCliente.setAdapter(adapterCliente);
    }


    private List<itemList> obtenerclientesBD() {
        List<itemList> listCliiente = new ArrayList<>();
            DBConnection dbConnection = new DBConnection();
            dbConnection.conectar();

                try {

                    if (dbConnection!=null){

                        Statement st = dbConnection.getConnection().createStatement();

                        ResultSet rs = st.executeQuery("select CONCAT (Codigo, '-',Nombre) as Nombre,Direccion,Codigo,idCliente,LimiteCredito from Clientes where Estado = 'Activo' order by Nombre asc");
                        while (rs.next()) {
                            listCliiente.add(new itemList(rs.getString("Nombre"),
                                    rs.getString("Direccion"),
                                    rs.getInt("Codigo"),
                                    rs.getInt("idCliente"),
                                    rs.getDouble("LimiteCredito")));
                        }
                        st.close();
                    }else{
                        System.out.println("Consulta realizada!!!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

        return listCliiente;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Mbtn_exit:
                try {
                    Intent i= new Intent(this,MainActivity.class);
                    startActivity(i);
                    sesion = DBConnection.getDbConnection();
                    sesion.getConnection().close();
                    finishAffinity();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}