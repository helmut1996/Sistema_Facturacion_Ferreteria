package com.example.facturacioncarpintero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.facturacioncarpintero.ConexionBD.DBConnection;
import com.example.facturacioncarpintero.model.ModelItemsProducto;
import com.squareup.picasso.Picasso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainDetalleProducto extends AppCompatActivity implements View.OnClickListener{

    /*variables de los componentes de la vista*/
    private ImageButton IbuttonSiguiente;
    private TextView tvnombreproducto,textcontar,textinfo1,textinfo2,textinfo3,textinfo4,textinfo5,tvunidadmedida,tvtipoprecio,tvimagenBD,tvIDproducto,tvUnidadMedida;
    private Spinner precios,monedas;
    private ImageView img;
    private EditText editcantidad;
    private LinearLayout cuerpoProductCliente;
    int TotalP;

    /* variables globales */
    String NombreCliente;
    String CodigoCliente;
    String ZonaCliente;
    String IdCliente;
    int IdVendedor;
    int IdInventario;

    private String producto;
    double precioEscogido;
    int idResultante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detalle_producto);
        getSupportActionBar().setTitle("Productos");

        /////////////////////////////////Metodo para permisos de las imagenes/////////////////////////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Verifica permisos para Android 6.0+
            checkExternalStoragePermission(); }

        cuerpoProductCliente=findViewById(R.id.linearLayoutClienteProducto);
        ///////// Botones

        IbuttonSiguiente = findViewById(R.id.btn_siguente);

        editcantidad=findViewById(R.id.editTextCantidad);
        ////////////imagen producto
        img=findViewById(R.id.imgProducto);
        /////////// campos de texto
        tvnombreproducto=findViewById(R.id.tvnombreP);
        textcontar=findViewById(R.id.text_contar);
        textinfo1=findViewById(R.id.text_info1);
        textinfo2=findViewById(R.id.text_info2);
        textinfo3=findViewById(R.id.text_info3);
        textinfo4=findViewById(R.id.text_info4);
        textinfo5=findViewById(R.id.text_info5);
        tvimagenBD=findViewById(R.id.imagenBD);
        tvunidadmedida=findViewById(R.id.text_unidadM);
        tvIDproducto=findViewById(R.id.IDProduto);
        tvtipoprecio=findViewById(R.id.tipoPrecio);
        tvUnidadMedida=findViewById(R.id.tvUnidadMedida);
        ////////// Spinmer

        precios = findViewById(R.id.spinerPrecios);
        monedas = findViewById(R.id.spinner_tipo_moneda);

        IbuttonSiguiente.setOnClickListener(this);


        /*Spinner del tipo de moneda*/
        ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.tipo_moneda, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monedas.setAdapter(adapter);

        /*spinner de los precios*/

        monedas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(monedas.getSelectedItem().toString().equals("Cordoba"))
                {

                    precios.setAdapter(precioCordoba());
                }
                else
                {
                    precios.setAdapter(precioDolar());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        precios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    tvtipoprecio.setText("1");
                }else if(position==1){
                    tvtipoprecio.setText("2");
                }else if (position==2){
                    tvtipoprecio.setText("3");
                }else if (position==3){
                    tvtipoprecio.setText("4");
                }else if (position==4){
                    tvtipoprecio.setText("5");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onClick(View v) {

    }

    public ArrayAdapter precioDolar()
    {
        ArrayAdapter NoCoreAdapter=null;
        DBConnection dbConnection= new DBConnection();
        dbConnection.conectar();
        String query = "select PrecioDolar1, PrecioDolar2,PrecioDolar3,PrecioDolar4,PrecioDolar5 from Inventario where Nombre='" + producto + "'";
        try {
            Statement stm = dbConnection.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(query);

            ArrayList<String> data = new ArrayList<>();
            while (rs.next()) {
                data.add(rs.getString("PrecioDolar1"));
                data.add(rs.getString("PrecioDolar2"));
                data.add(rs.getString("PrecioDolar3"));
                data.add(rs.getString("PrecioDolar4"));
                data.add(rs.getString("PrecioDolar5"));

            }
            System.out.println("Capturando nombre Producto====>"+producto);
            NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return NoCoreAdapter;

    }

    public ArrayAdapter precioCordoba()
    {
        ArrayAdapter NoCoreAdapter=null;
        DBConnection dbConnection = new DBConnection();
        dbConnection.conectar();
        String query = "select Precio1, Precio2,Precio3,Precio4,Precio5 from Inventario where Nombre= '" +producto+" ' ";
        try {
            Statement stm = dbConnection.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(query);

            ArrayList<String> data = new ArrayList<>();
            while (rs.next()) {
                data.add(rs.getString("Precio1"));
                data.add(rs.getString("Precio2"));
                data.add(rs.getString("Precio3"));
                data.add(rs.getString("Precio4"));
                data.add(rs.getString("Precio5"));
            }
            System.out.println("Nombre:"+producto);

            System.out.println("Capturando nombre Producto====>"+producto);
            NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return NoCoreAdapter;

    }


    private void checkExternalStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer!");
        }
    }

    public void initValues(){
        itemDatail= (ModelItemsProducto) getIntent().getExtras().getSerializable("itemDetail");
        Picasso.get().load(img+itemDatail.getImagen())
                .error(R.drawable.error)
                .into(imageView);

        codigo.setText(itemDatail.getCodigo());
        NombreDetalle.setText(itemDatail.getNombre());
        Descripcion.setText(itemDatail.getMarca());
        UnidadMedida.setText("UM: "+itemDatail.getUnidad_Med());
        Presentacion.setText(itemDatail.getPresentacion());
        Precio.setText("P1: "+ "C$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioC())));
        Precio2.setText("P2: "+"C$"+String.valueOf(String.format("%,.2f", itemDatail.getPrecioC2())));
        Precio3.setText("P3: "+"C$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioC3())));
        Precio4.setText("P4:"+"C$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioC4())));
        Precio5.setText("P5: "+"C$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioC5())));
        PrecioD.setText("PD1: "+"$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioD())));
        PrecioD2.setText("PD2: "+"$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioD2())));
        PrecioD3.setText("PD3: "+"$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioD3())));
        PrecioD4.setText("PD4: "+"$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioD4())));
        PrecioD5.setText("PD5: "+"$"+String.valueOf(String.format("%,.2f",itemDatail.getPrecioD5())));
        Existencias.setText(String.valueOf("Cantidad disponible: "+itemDatail.getExistencia()));
        Estados.setText("Estado: "+itemDatail.getEstado());
    }
}