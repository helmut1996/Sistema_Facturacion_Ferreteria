 package com.example.facturacioncarpintero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facturacioncarpintero.ConexionBD.DBConnection;
import com.example.facturacioncarpintero.SQLite.conexionSQLiteHelper;
import com.example.facturacioncarpintero.SQLite.ulilidades.utilidades;
import com.example.facturacioncarpintero.model.ModelItemsProducto;
import com.example.facturacioncarpintero.model.itemList;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.facturacioncarpintero.MainFactura.idInventario;
import static com.example.facturacioncarpintero.MainFactura.tasaCambio;

 public class MainDetalleProducto extends AppCompatActivity implements Dialog_pin_precio.DialogListennerPinPrecio{

    /*variables de los componentes de la vista*/

    private TextView tvexiscaja, tvmostrarP,tvCambioD, textCIdInventario, textPin, tvnombreproducto,textcontar,textinfo1,textinfo2,textinfo3,textinfo4,textinfo5,tvunidadmedida,tvtipoprecio,tvimagenBD,tvIDproducto,tvUnidadMedida;
    private Spinner precios,monedas;
    private ImageView image;
    private EditText editcantidad;
    private LinearLayout cuerpoProductCliente;
    int TotalP;
    private ModelItemsProducto itemDatail;
    /* variables globales */
    String NombreCliente;
    String CodigoCliente;
    String ZonaCliente;
    String IdCliente;
    int IdVendedor;
    int IdInventario;
    double conversion;
    int unidadpaquete;
    double convercioncaja;


    private String producto;
    double precioEscogido;
    int idResultante;


    private String URL_IMAGES="http://ferreteriaelcarpintero.com/images/carpintero/";
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

        textCIdInventario=findViewById(R.id.capturaidInventario);
        editcantidad=findViewById(R.id.editTextCantidad);
        ////////////imagen producto
        image=findViewById(R.id.imgProducto);
        /////////// campos de texto
        tvexiscaja=findViewById(R.id.text_cajas);
        tvmostrarP=findViewById(R.id.mostrarP);
        textPin=findViewById(R.id.pin_text);
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
        tvCambioD=findViewById(R.id.mostrandoPrecio);
        ////////// Spinmer

        precios = findViewById(R.id.spinerPrecios);
        monedas = findViewById(R.id.spinner_tipo_moneda);



//////////////////////////////pasando datos por parametros entre activitys/////////////////////////////////

        String NombrePreducto;
        Bundle extra=getIntent().getExtras();

        if (extra !=null){
            NombreCliente = extra.getString("NombreCliente");
            System.out.println("Nombre Cliente Activity ProductosClientea----->"+NombreCliente);

            CodigoCliente = extra.getString("CodigoCliente");
            System.out.println("Codigo Cliente Activity ProductosClientea----->"+CodigoCliente);

            textCIdInventario.setText(extra.getString("idinventario"));
            IdInventario = extra.getInt("idinventario");
            System.out.println("IDINVENTARIO Activity ProductosClientea----->"+IdInventario);

            IdCliente = extra.getString("IdCliente");
            System.out.println("ID Cliente Activity ProductosClientea----->"+IdCliente);

            IdVendedor = extra.getInt("IdVendedor");
            System.out.println("ID Vendedor Activity ProductosClientea----->"+IdVendedor);

            ZonaCliente = extra.getString("ZonaCliente");
            System.out.println("Zona Cliente Activity ProductosClientea----->"+ZonaCliente);
            NombrePreducto= extra.getString("NombreP");
            producto = extra.getString("NombreP");
            tvnombreproducto.setText(NombrePreducto);
            tvunidadmedida.setText(extra.getString("UnidadMed"));
            tvUnidadMedida.setText(extra.getString("UnidadMed"));
            String info1,info2,info3,info4,info5;

            info1=extra.getString("info1");
            info2=extra.getString("info2");
            info3=extra.getString("info3");
            info4=extra.getString("info4");
            info5=extra.getString("info5");


            if (info1 == null){
                textinfo1.setText("");
            }
            else{
                textinfo1.setText(info1);
            }

            if (info2 == null){
                textinfo2.setText("");
            }
            else{
                textinfo2.setText(info2);
            }

            if (info3 == null){
                textinfo3.setText("");
            }
            else{
                textinfo3.setText(info3);
            }

            if (info4 == null){
                textinfo4.setText("");
            }
            else{
                textinfo4.setText(info4);
            }

            if (info5 == null){
                textinfo5.setText("");
            }
            else{
                textinfo5.setText(info5);
            }


            if (info1!=null || info2!=null || info3!=null || info4!=null || info5!=null){
                textinfo1.setText(info1);
                textinfo2.setText(info2);
                textinfo3.setText(info3);
                textinfo4.setText(info4);
                textinfo5.setText(info5);
            }else{

                textinfo1.setText("");
                textinfo2.setText("");
                textinfo3.setText("");
                textinfo4.setText("");
                textinfo5.setText("");

            }


            textcontar.setText(extra.getString("stock"));

            tvimagenBD.setText(extra.getString("imagenproducto"));


            tvIDproducto.setText(extra.getString("idproducto"));
//////////////////////////////pasando datos por parametros entre activitys/////////////////////////////////

        }

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
                double datos= Double.parseDouble(precios.getSelectedItem().toString());
                if (position==0){

                    tvtipoprecio.setText("1");
                    tvCambioD.setText(precios.getSelectedItem().toString());

                    if (monedas.getSelectedItem().toString().equals("Dolar")){
                        conversion=datos*tasaCambio;
                        System.out.println("precioCapturado:-->"+conversion);
                        tvmostrarP.setText(String.valueOf(conversion));
                    }else{
                        tvmostrarP.setText("");
                    }


                }else if(position==1){
                    tvtipoprecio.setText("2");
                    tvCambioD.setText(precios.getSelectedItem().toString());

                    if (monedas.getSelectedItem().toString().equals("Dolar")){
                        conversion=datos*tasaCambio;
                        System.out.println("precioCapturado:-->"+conversion);
                        tvmostrarP.setText(String.valueOf(conversion));
                    }else{
                        tvmostrarP.setText("");
                    }
                }else if (position==2){
                    tvtipoprecio.setText("3");
                    tvCambioD.setText(precios.getSelectedItem().toString());

                    if (monedas.getSelectedItem().toString().equals("Dolar")){
                        conversion=datos*tasaCambio;
                        System.out.println("precioCapturado:-->"+conversion);
                        tvmostrarP.setText(String.valueOf(conversion));
                    }else{
                        tvmostrarP.setText("");
                    }

                }else if (position==3){
                    tvtipoprecio.setText("4");
                    tvCambioD.setText(precios.getSelectedItem().toString());

                    if (monedas.getSelectedItem().toString().equals("Dolar")){
                        conversion=datos*tasaCambio;
                        System.out.println("precioCapturado:-->"+conversion);
                        tvmostrarP.setText(String.valueOf(conversion));
                    }else{
                        tvmostrarP.setText("");
                    }

                }else if (position==4){
                    tvtipoprecio.setText("5");
                    tvCambioD.setText(precios.getSelectedItem().toString());

                    if (monedas.getSelectedItem().toString().equals("Dolar")){
                        conversion=datos*tasaCambio;
                        System.out.println("precioCapturado:-->"+conversion);
                        tvmostrarP.setText(String.valueOf(conversion));
                    }else{
                        tvmostrarP.setText("");
                    }


                }
            }




            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CargarImagen();
        TasaDolar();
        UnidadPaquete();
        tvexiscaja.setText(String.valueOf(convercioncaja));
        if (textPin.getText().toString().equals("2233")){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        precioEscogido= (Double.parseDouble(precios.getSelectedItem().toString()));
        switch (item.getItemId()){
            case R.id.Mbtn_MenuAdd:

                System.out.println("Valor del precio===========>"+precioEscogido);
                if (editcantidad.getText().toString().isEmpty()){
                    editcantidad.setError("Debe Ingresar una cantidad");
                }else if(Integer.parseInt(editcantidad.getText().toString())==0){

                    editcantidad.setError("la cantidad no puede ser 0");
                }else if (precioEscogido == 0){
                    Snackbar snackbar= Snackbar.make(cuerpoProductCliente,"Precio seleccionado es  0!!",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if(Integer.parseInt(editcantidad.getText().toString())>Integer.parseInt(textcontar.getText().toString())){
                    Toast.makeText(this,"no hay inventario suficiente  de este producto ",Toast.LENGTH_LONG).show();
                }else if(idResultante==30){
                    Toast.makeText(this,"Ya no Puedes Ingresar mas de 30 productos",Toast.LENGTH_LONG).show();

                }
                else {

                    GuardarProductos();

                    Intent intent2 = new Intent(getApplicationContext(),MainListaProductos.class);
                    startActivity(intent2);
                }

                break;
            case R.id.Mbtn_pin:

                openDialogPrecio();
        }

        return super.onOptionsItemSelected(item);
    }


    private void  openDialogPrecio(){
        Dialog_pin_precio dialog = new Dialog_pin_precio();
        dialog.show(getSupportFragmentManager(),"Pin para activar precio");
    }

    private void CargarImagen() {
        Picasso.get().load(URL_IMAGES+tvimagenBD.getText())
                //.error(R.drawable.error)
                .into(image);

    }

    public ArrayAdapter precioDolar()
    {
        ArrayAdapter NoCoreAdapter=null;
        DBConnection dbConnection= new DBConnection();
        dbConnection.conectar();

        try {
            if (dbConnection!=null){
                String query = "select PrecioDolar1, PrecioDolar2,PrecioDolar3,PrecioDolar4,PrecioDolar5 from Inventario where Nombre='" + producto + "'";
                Statement stm = dbConnection.getConnection().createStatement();
                ResultSet rs = stm.executeQuery(query);

                ArrayList<String> data = new ArrayList<>();
                while (rs.next()) {
                    data.add(rs.getString("PrecioDolar2"));
                    data.add(rs.getString("PrecioDolar3"));
                    data.add(rs.getString("PrecioDolar4"));
                    data.add(rs.getString("PrecioDolar5"));

                }
                System.out.println("Capturando nombre Producto====>"+producto);
                NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
                stm.close();
            }else {
                Toast.makeText(getApplicationContext(), "Connection to server failed!", Toast.LENGTH_LONG).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return NoCoreAdapter;

    }


    public ArrayAdapter precioDolar2()
    {
        ArrayAdapter NoCoreAdapter=null;
        DBConnection dbConnection= new DBConnection();
        dbConnection.conectar();
        try {
            if (dbConnection!=null){
                String query = "select PrecioDolar1, PrecioDolar2,PrecioDolar3,PrecioDolar4,PrecioDolar5 from Inventario where Nombre='" + producto + "'";

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
            }else{
                Toast.makeText(getApplicationContext(), "Connection to server failed!", Toast.LENGTH_LONG).show();
            }

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
    public ArrayAdapter precioCordoba2()
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


    private void GuardarProductos() {


        /*mandando a llamar conexion a SQLite */
        conexionSQLiteHelper conn= new conexionSQLiteHelper(this,"bd_productos",null,1);
        /*abrir la conexion a SQLite*/
        SQLiteDatabase db= conn.getWritableDatabase();

        Cursor cantidad_registrado=db.rawQuery("SELECT count(*) as cantidad from producto", null);

        if (cantidad_registrado.moveToFirst()) {
            if (cantidad_registrado.getInt(cantidad_registrado.getColumnIndex("cantidad")) == 30) {
                Toast.makeText(this,"Limite de Registros permitidos "+idResultante+" Productos",Toast.LENGTH_LONG).show();
                return;
            }
        }
        cantidad_registrado.close();

        Cursor c=db.rawQuery("SELECT * FROM producto WHERE id='"+textCIdInventario.getText()+"'", null);
        if(c.moveToFirst()) {
            Toast.makeText(this,"Error ya seleccionaste este Producto",Toast.LENGTH_LONG).show();
        }
        else { // Inserting record
            ContentValues values= new ContentValues();
            values.put(utilidades.CAMPO_ID,textCIdInventario.getText().toString());
            values.put(utilidades.CAMPO_NOMBRE,tvnombreproducto.getText().toString());
            values.put(utilidades.CAMPO_CANTIDAD,editcantidad.getText().toString());
            if (monedas.getSelectedItem().toString().equals("Dolar")){
                values.put(utilidades.CAMPO_PRECIO,tvmostrarP.getText().toString());
            }else{
                values.put(utilidades.CAMPO_PRECIO,precios.getSelectedItem().toString());
            }
            values.put(utilidades.CAMPO_IMAGEN,tvimagenBD.getText().toString());
            values.put(utilidades.CAMPO_TIPOPRECIO,tvtipoprecio.getText().toString());
            idResultante= (int) db.insert(utilidades.TABLA_PRODUCTO,utilidades.CAMPO_ID,values);
            Toast.makeText(this,"CANTIDAD INGRESADA: " + idResultante,Toast.LENGTH_LONG).show();
        }
        c.close();
    }

    @Override
    public void appliyTexts(String cambio) {
        textPin.setText(cambio);
        if (textPin.getText().toString().equals("033")){
            precios.setAdapter(precioCordoba2());
            monedas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(monedas.getSelectedItem().toString().equals("Cordoba"))
                    {
                        precios.setAdapter(precioCordoba2());
                    }
                    else
                    {
                        precios.setAdapter(precioDolar2());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else if (textPin.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Debes de ingresar pin",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(),"pin Incorrecto",Toast.LENGTH_LONG).show();
        }
    }

    public void TasaDolar(){
        DBConnection dbConnection=new DBConnection();
        dbConnection.conectar();
        try {
            Statement st2 = dbConnection.getConnection().createStatement();
            ResultSet rs2 = st2.executeQuery("\n" +
                    "select top 1 Cambio from Cambio_Dolar order by idDolar desc");
            while (rs2.next()) {
                tasaCambio = rs2.getDouble("Cambio");

                System.out.println("==============> Ultimo tasa de cambio en dolares:" + tasaCambio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



     public void UnidadPaquete(){
         DBConnection dbConnection=new DBConnection();
         dbConnection.conectar();
         double existencia= Double.parseDouble(textcontar.getText().toString());
         try {
             Statement st2 = dbConnection.getConnection().createStatement();
             ResultSet rs2 = st2.executeQuery("\n" +
                     "select Unidad_de_Paquete from Inventario where idInventario = '"+textCIdInventario.getText().toString()+"' ");
             while (rs2.next()) {
                 unidadpaquete = rs2.getInt("Unidad_de_Paquete");


                 System.out.println("==============> la unidad de paquete de este Producto es:" + unidadpaquete);

                 convercioncaja= existencia/unidadpaquete;
                 System.out.println("Cajas Existentes:==>"+convercioncaja);
             }

         } catch (SQLException e) {
             e.printStackTrace();
         }
     }


}