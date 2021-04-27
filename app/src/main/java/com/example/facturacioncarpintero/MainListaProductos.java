package com.example.facturacioncarpintero;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.facturacioncarpintero.Adapter.AdapterProductos;
import com.example.facturacioncarpintero.ConexionBD.DBConnection;
import com.example.facturacioncarpintero.model.ModelItemsProducto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainListaProductos extends AppCompatActivity {

    EditText search2;
    Button btnBuscar;
    ProgressBar loader;
    AdapterProductos adaptadorProducto;
    public static List<ModelItemsProducto> listaProducto;
    public static String nombrecliente;
    public static String codigocliente;
    public static String zonacliente;
    public static String idcliente;
    public static int idvendedor;
    public static int stock;
    public static int IdInventario;
    public static String limiteCredito;
    public static String nombreusuario;
    public String CapturaBuscador="";
    public RecyclerView recyclerlistproducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lista_productos);
        getSupportActionBar().setTitle(" Lista Productos");
        listaProducto=new ArrayList<>();
        init();
        CapturaDatosCliente();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search2.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Debes ingresar un nombre",Toast.LENGTH_SHORT).show();
                }else{
                    CapturaBuscador=  search2.getText().toString();

                    filter2(CapturaBuscador);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search2.getWindowToken(), 0);

                    System.out.println("Mostrando lo capturado=====>"+CapturaBuscador);

                }

            }
        });
    }

    private void CapturaDatosCliente() {
        Bundle extra=getIntent().getExtras();
        if (extra != null){
            nombrecliente= extra.getString("Nombrecliente");
            System.out.println("----> NombreCliente: "+nombrecliente);

            nombreusuario=extra.getString("Nombreusuario");
            System.out.println("------> NombreUsuario: "+nombreusuario);

            codigocliente= extra.getString("Codigocliente");
            System.out.println("----> CodigoCliente: "+codigocliente);

            zonacliente= extra.getString("Zonacliente");
            System.out.println("----> Nombre Zona Cliente: "+zonacliente);

            idcliente= extra.getString("Idcliente");
            System.out.println("----> ID Cliente: "+idcliente);

            idvendedor= extra.getInt("Idvendedor");
            System.out.println("----> ID Vendedor lista Producto: "+idvendedor);

            limiteCredito= extra.getString("LimiteCredito");
            System.out.println("-----> Limite Credito lista Producto: "+limiteCredito);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu4,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Mbtn_Factura:

                Intent intent2 = new Intent(getApplicationContext(),MainFactura.class);
                intent2.putExtra("NombreCliente",nombrecliente);
                intent2.putExtra("CodigoCliente",codigocliente);
                intent2.putExtra("ZonaCliente",zonacliente);
                intent2.putExtra("IdCliente",idcliente);
                intent2.putExtra("IdVendedor",idvendedor);
                intent2.putExtra("idinventario",IdInventario);
                intent2.putExtra("limitecredito",limiteCredito);
                intent2.putExtra("nombreUsuario",nombreusuario);

                startActivity(intent2);




                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private void filter2(String text) {
        text = search2.getText().toString().toUpperCase();
        if(!text.isEmpty()){
            initVlaues(text);

            ArrayList<ModelItemsProducto> filterlistP = new ArrayList<>();
            for (ModelItemsProducto item:listaProducto){
                if (item.getNombreP().toUpperCase().contains(text)){
                    filterlistP.add(item);
                }
            }
            adaptadorProducto.filterListProducto(filterlistP);
        }else{
            Toast.makeText(getApplicationContext(),"Producto no encontrado",Toast.LENGTH_LONG).show();
        }
    }

    public List<ModelItemsProducto> llenarProductosBD(String Buscar){

        try {

            DBConnection dbConnection = new DBConnection();
            dbConnection.conectar();

            Statement st = dbConnection.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("\n" +
                    "select  concat(i.Nombre, ' C$ ', i.Precio1,' ',um.Nombre) as Nombre,i.Nombre as Producto,um.Nombre as UM,i.idInventario, i.ImagenApk, i.Precio1,ad.info1,ad.info2,ad.info3,ad.info4,ad.info5,i.Stock from Inventario i inner join Unidad_Medida um on i.idUndMedida=um.idUnidadMedida inner join InventarioInfoAdic ad on i.idInventario= ad.idInventario where i.Estado = 'Activo' and i.Nombre like '%"+Buscar+"%' and Stock >0 ");


            System.out.println("capturando texto Busqueda===>"+Buscar);
            while (rs.next()){

                listaProducto.add(new ModelItemsProducto(rs.getString("Nombre"),
                        rs.getString("UM"),
                        rs.getDouble("Precio1"),
                        rs.getString("Producto"),
                        rs.getString("info1"),
                        rs.getString("info2"),
                        rs.getString("info3"),
                        rs.getString("info4"),
                        rs.getString("info5"),
                        rs.getString("ImagenApk"),
                        stock= rs.getInt("Stock"),
                        rs.getInt("idInventario")));


            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return listaProducto;

    }

    private void init(){

        recyclerlistproducto= findViewById(R.id.list_producto);
        search2 = findViewById(R.id.search2);
        btnBuscar=findViewById(R.id.btn_Buscar);

    }
    public void initVlaues(String Buscar){
        LinearLayoutManager manager= new LinearLayoutManager(this);
        recyclerlistproducto.setLayoutManager(manager);
        listaProducto=llenarProductosBD(Buscar);
        adaptadorProducto= new AdapterProductos((ArrayList<ModelItemsProducto>) listaProducto);
        recyclerlistproducto.setAdapter(adaptadorProducto);


    }



}