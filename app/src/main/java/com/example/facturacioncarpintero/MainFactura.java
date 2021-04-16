package com.example.facturacioncarpintero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facturacioncarpintero.ConexionBD.DBConnection;
import com.example.facturacioncarpintero.SQLite.conexionSQLiteHelper;
import com.example.facturacioncarpintero.SQLite.entidades.ProductosAdd;
import com.example.facturacioncarpintero.SQLite.ulilidades.utilidades;
import com.example.facturacioncarpintero.Utils.BytesUtil;
import com.example.facturacioncarpintero.Utils.HandlerUtils;
import com.google.android.material.expandable.ExpandableTransformationWidget;
import com.google.android.material.snackbar.Snackbar;
import com.iposprinter.iposprinterservice.*;
import com.iposprinter.iposprinterservice.IPosPrinterService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class MainFactura extends AppCompatActivity implements Dialog_nombre_nuevo.DialogListennerNombreNuevo,Dialog_pin_save.DialogListennerPinSave{

    TextView  tv_idVendedorSpinner,textV_Codigo,textV_Cliente,textV_zona,textV_credito_disponible, textV_total,textIdcliente,textIdvendedor,tvtotalproducto;
    Spinner T_factura,T_ventas,List_Vendedores,Estados;
    ListView lista_factura;
    LinearLayout cuerpo;
    Button imprimirC;
    ArrayList<String> listainformacion;
    ArrayList<ProductosAdd>listaproducto;

    int NumeroTikets;
    ////////////////////////////////////////////////////////////
    public static String nombre ="HOLA MUNDO";
    public static int cantidadProducto;
    public static String idProd;
    public static double precioProducto;
    public static String nombreImagen,valor,idInventario;
    double TotalFact;
    double TotalDolar;
    ///////////////////variables Dialog detalle producto/////////////////////////////////////
    conexionSQLiteHelper conn;
    private static final String TAG ="MainFactura";
    double TotalComision= 0;
    /////////////////////////////Variables por parametros////////////////////////////////////////
    public static String NombreCliente;
    public static String CodigoCliente;
    public static String ZonaCliente;
    public static String IDCliente;
    public static int IDVendedor;
    public static int IDInventario;
    public static double LimiteCreditosC;
    public static String nombreUsuarioC;
    public static double tasaCambio;
    MainClientes id = new MainClientes();




    ///////////////////////Impresora//////////////////////////////////


    /* Demo 版本号*/

    private static final String VERSION = "V1.1.0";

    private IPosPrinterService mIPosPrinterService;
    private IPosPrinterCallback callback = null;
    private Random random = new Random();
    private HandlerUtils.MyHandler handler;




    /*Definir el estado de la impresora*/


    private final int PRINTER_NORMAL = 0;
    private final int PRINTER_PAPERLESS = 1;
    private final int PRINTER_THP_HIGH_TEMPERATURE = 2;
    private final int PRINTER_MOTOR_HIGH_TEMPERATURE = 3;
    private final int PRINTER_IS_BUSY = 4;
    private final int PRINTER_ERROR_UNKNOWN = 5;
    /*El estado actual de la impresora*/
    private int printerStatus = 0;

    private final String PRINTER_NORMAL_ACTION = "com.iposprinter.iposprinterservice.NORMAL_ACTION";
    private final String PRINTER_PAPERLESS_ACTION = "com.iposprinter.iposprinterservice.PAPERLESS_ACTION";
    private final String PRINTER_PAPEREXISTS_ACTION = "com.iposprinter.iposprinterservice.PAPEREXISTS_ACTION";
    private final String PRINTER_THP_HIGHTEMP_ACTION = "com.iposprinter.iposprinterservice.THP_HIGHTEMP_ACTION";
    private final String PRINTER_THP_NORMALTEMP_ACTION = "com.iposprinter.iposprinterservice.THP_NORMALTEMP_ACTION";
    private final String PRINTER_MOTOR_HIGHTEMP_ACTION = "com.iposprinter.iposprinterservice.MOTOR_HIGHTEMP_ACTION";
    private final String PRINTER_BUSY_ACTION = "com.iposprinter.iposprinterservice.BUSY_ACTION";
    private final String PRINTER_CURRENT_TASK_PRINT_COMPLETE_ACTION = "com.iposprinter.iposprinterservice.CURRENT_TASK_PRINT_COMPLETE_ACTION";



    /*Mensaje*/

    private final int MSG_TEST = 1;
    private final int MSG_IS_NORMAL = 2;
    private final int MSG_IS_BUSY = 3;
    private final int MSG_PAPER_LESS = 4;
    private final int MSG_PAPER_EXISTS = 5;
    private final int MSG_THP_HIGH_TEMP = 6;
    private final int MSG_THP_TEMP_NORMAL = 7;
    private final int MSG_MOTOR_HIGH_TEMP = 8;
    private final int MSG_MOTOR_HIGH_TEMP_INIT_PRINTER = 9;
    private final int MSG_CURRENT_TASK_PRINT_COMPLETE = 10;

    /*El tipo de imprecion circular*/

    private final int MULTI_THREAD_LOOP_PRINT = 1;
    private final int INPUT_CONTENT_LOOP_PRINT = 2;
    private final int DEMO_LOOP_PRINT = 3;
    private final int PRINT_DRIVER_ERROR_TEST = 4;
    private final int DEFAULT_LOOP_PRINT = 0;



    // Ciclo a través de la broca de la bandera

  private int loopPrintFlag = DEFAULT_LOOP_PRINT;
    private byte loopContent = 0x00;
    private int printDriverTestCount = 0;



    private final HandlerUtils.IHandlerIntent iHandlerIntent = new HandlerUtils.IHandlerIntent() {
        @Override
        public void handlerIntent(Message msg) {
            switch (msg.what) {
                case MSG_TEST:
                    break;
                case MSG_IS_NORMAL:
                    if (getPrinterStatus() == PRINTER_NORMAL) {
                        loopPrint(loopPrintFlag);
                    }
                   break;
                case MSG_IS_BUSY:
                    Toast.makeText(MainFactura.this, R.string.printer_is_working, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_PAPER_LESS:
                    loopPrintFlag = DEFAULT_LOOP_PRINT;
                    Toast.makeText(MainFactura.this, R.string.out_of_paper, Toast.LENGTH_SHORT).show();
                    break;
                    case MSG_PAPER_EXISTS:
                    Toast.makeText(MainFactura.this, R.string.exists_paper, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_THP_HIGH_TEMP:
                    Toast.makeText(MainFactura.this, R.string.printer_high_temp_alarm, Toast.LENGTH_SHORT).show();
                    break;
                 case MSG_MOTOR_HIGH_TEMP:
                     loopPrintFlag = DEFAULT_LOOP_PRINT;
                    Toast.makeText(MainFactura.this, R.string.motor_high_temp_alarm, Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(MSG_MOTOR_HIGH_TEMP_INIT_PRINTER, 180000);  //马达高温报警，等待3分钟后复位打印机
                    break;
               case MSG_MOTOR_HIGH_TEMP_INIT_PRINTER:
                    printerInit();
                    break;
                case MSG_CURRENT_TASK_PRINT_COMPLETE:
                    Toast.makeText(MainFactura.this, R.string.printer_current_task_print_complete, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };



    private void setButtonEnable(boolean flag) {
        imprimirC.setEnabled(flag);
    }


    private BroadcastReceiver IPosPrinterStatusListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                Log.d(TAG, "IPosPrinterStatusListener onReceive action = null");
                return;
            }
            Log.d(TAG, "IPosPrinterStatusListener action = " + action);
           /* if(action.equals(PRINTER_NORMAL_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_IS_NORMAL,0);
            }
            else if (action.equals(PRINTER_PAPERLESS_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_PAPER_LESS,0);
            }
            else if (action.equals(PRINTER_BUSY_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_IS_BUSY,0);
            }
            else if (action.equals(PRINTER_PAPEREXISTS_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_PAPER_EXISTS,0);
            }
            else if (action.equals(PRINTER_THP_HIGHTEMP_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_THP_HIGH_TEMP,0);
            }
            else if (action.equals(PRINTER_THP_NORMALTEMP_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_THP_TEMP_NORMAL,0);
            }
            else if (action.equals(PRINTER_MOTOR_HIGHTEMP_ACTION))  //此时当前任务会继续打印，完成当前任务后，请等待2分钟以上时间，继续下一个打印任务
            {
                handler.sendEmptyMessageDelayed(MSG_MOTOR_HIGH_TEMP,0);
            }
            else if(action.equals(PRINTER_CURRENT_TASK_PRINT_COMPLETE_ACTION))
            {
                handler.sendEmptyMessageDelayed(MSG_CURRENT_TASK_PRINT_COMPLETE,0);
            }
            else
            {
                handler.sendEmptyMessageDelayed(MSG_TEST,0);
            }*/
        }
    };



    private ServiceConnection connectService = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIPosPrinterService = IPosPrinterService.Stub.asInterface(service);
            setButtonEnable(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIPosPrinterService = null;
        }
    };


    ///////////////////////Impresora//////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_factura);

        getSupportActionBar().setTitle("Facturacion");





        callback = new IPosPrinterCallback.Stub() {

            @Override
            public void onRunResult(final boolean isSuccess) throws RemoteException {
                Log.i(TAG, "result:" + isSuccess + "\n");
            }

            @Override
            public void onReturnString(final String value) throws RemoteException {
                Log.i(TAG, "result:" + value + "\n");
            }
        };





        Intent intent = new Intent();
        intent.setPackage("com.iposprinter.iposprinterservice");
        intent.setAction("com.iposprinter.iposprinterservice.IPosPrintService");
        bindService(intent, connectService, Context.BIND_AUTO_CREATE);


        IntentFilter printerStatusFilter = new IntentFilter();
        printerStatusFilter.addAction(PRINTER_NORMAL_ACTION);
        printerStatusFilter.addAction(PRINTER_PAPERLESS_ACTION);
        printerStatusFilter.addAction(PRINTER_PAPEREXISTS_ACTION);
        printerStatusFilter.addAction(PRINTER_THP_HIGHTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_THP_NORMALTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_MOTOR_HIGHTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_BUSY_ACTION);

        registerReceiver(IPosPrinterStatusListener, printerStatusFilter);





        imprimirC=findViewById(R.id.btn_imprimir);
        tv_idVendedorSpinner=findViewById(R.id.idVendedorSpinner);
        textV_Codigo = findViewById(R.id.textViewCodigo);
        textV_Cliente = findViewById(R.id.textViewcliente);
        textV_zona = findViewById(R.id.textView_Zona);
        textV_credito_disponible = findViewById(R.id.textView_C_Disponible);
        textV_total = findViewById(R.id.textV_total);
        textIdcliente= findViewById(R.id.textV_idcliente);
        textIdvendedor= findViewById(R.id.textV_idvendedor);
        List_Vendedores=findViewById(R.id.list_vendedores);
        cuerpo=findViewById(R.id.cuerpo);
        Estados=findViewById(R.id.spinner_estadoº);

        T_ventas = findViewById(R.id.spinner_tventas);
        T_factura = findViewById(R.id.spinner_facura);
        lista_factura=findViewById(R.id.lista_Factura);
        /////////Spinner del tipo de ventas
        ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.tipo_ventas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        T_ventas.setAdapter(adapter);



        List_Vendedores.setAdapter(Lista_Vendedores());

        List_Vendedores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ////////////spinner Estados de Facturas
        ArrayAdapter<CharSequence> adapter2 =ArrayAdapter.createFromResource(this, R.array.tipo_estados, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Estados.setAdapter(adapter2);

        ////////////spinner tipo factura
        ArrayAdapter<CharSequence> adapter1 =ArrayAdapter.createFromResource(this, R.array.tipo_moneda, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        T_factura.setAdapter(adapter1);

        ////////////////////////////// ListView///////////////////////////////
        //conexion de SQLite
        conn=new conexionSQLiteHelper(getApplicationContext(),"bd_productos",null,1);

        ConsultarlistaProducto();
        CalcularTotalFactura();
        ArrayAdapter adaptador= new ArrayAdapter(this, android.R.layout.simple_list_item_1,listainformacion);
        lista_factura.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        lista_factura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Tratando de descubrir la informacion: ------> " +lista_factura.getAdapter().getItem(position));

                nombre = listaproducto.get(position).getNombreproduc();
                cantidadProducto=listaproducto.get(position).getCantidad();
                precioProducto=listaproducto.get(position).getPrecios();
                idProd=listaproducto.get(position).getId_producto();
                nombreImagen=listaproducto.get(position).getImagenProducto();

                Intent i =new Intent(getApplicationContext(),MainEliminar_Actualizar.class);
                i.putExtra("NombreProducto",nombre);
                i.putExtra("CantidadProducto",cantidadProducto);
                i.putExtra("PrecioProducto",precioProducto);
                i.putExtra("IdProducto",idProd);
                i.putExtra("NombreImagen",nombreImagen);
                startActivity(i);
            }
        });

        /////pasando los datos del cliente
        Bundle extra=getIntent().getExtras();

        if (extra != null){

        NombreCliente = extra.getString("NombreCliente");
        CodigoCliente= extra.getString("CodigoCliente");
        ZonaCliente= extra.getString("ZonaCliente");
        IDCliente= extra.getString("IdCliente");
        IDVendedor = extra.getInt("IdVendedor");
        IDInventario= extra.getInt("idinventario");
        LimiteCreditosC= extra.getDouble("limitecredito");
        nombreUsuarioC= extra.getString("nombreUsuario");


            textV_Cliente.setText(NombreCliente);
            textV_Codigo.setText(CodigoCliente);
            textV_zona.setText(ZonaCliente);
            textIdcliente.setText(IDCliente);
            textIdvendedor.setText(String.valueOf(id.id));

            System.out.println("----> NombreCliente activity Factura: "+NombreCliente);
            System.out.println("----> IDCliente activity Factura: "+IDCliente);
            System.out.println("-----> IDVENDEDOR Factura: "+IDVendedor);
            System.out.println("----> ZONA CLIENTE: "+ZonaCliente);
            System.out.println("------> CODIGO CLIENTE: "+ CodigoCliente);
            System.out.println("------> CODIGO IDINVENTARIO: "+ IDInventario);
            System.out.println("------> CREDITO DISPONIBLE CLIENTE: "+ LimiteCreditosC);
            System.out.println("------> NOMBRE USUARIO:" +nombreUsuarioC);

        }
        /////pasando los datos del cliente
        TasaDolar();
        CalcularTotalFactura();
        ConversionDolares();

        textV_Cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialoNombreNuevo();
            }
        });

        imprimirC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    GenerarTickets();
                    IdFactura();
                if (getPrinterStatus() == PRINTER_NORMAL)
                    printText();


                open_pin_save();
            }
        });

    }


    /*
     *Funciones de la imprsora
     * */


    public int getPrinterStatus() {

        Log.i(TAG, "***** printerStatus" + printerStatus);
        try {
            printerStatus = mIPosPrinterService.getPrinterStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "#### printerStatus" + printerStatus);
        return printerStatus;
    }




    /**
     * La impresora se inicializa
     */


    public void printerInit() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    mIPosPrinterService.printerInit(callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loopPrint(int flag) {
        switch (flag) {

        }
    }

    public void printText() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    mIPosPrinterService.printSpecifiedTypeText("EL CARPINTERO\n", "ST", 48, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    mIPosPrinterService.PrintSpecFormatText("Numero Cliente\n", "ST", 32, 1, callback);
                    mIPosPrinterService.printSpecifiedTypeText("********************************", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText(" \t\t\t\t"+NumeroTikets+"\n", "ST", 48, callback);
                    mIPosPrinterService.printSpecifiedTypeText("********************************", "ST", 24, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                   mIPosPrinterService.printSpecifiedTypeText("**********END***********\n\n", "ST", 32, callback);

                    mIPosPrinterService.printerPerformPrint(160,  callback);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * Funciones de la imprsora
     */



    public ArrayAdapter Lista_Vendedores() {
        ArrayAdapter NoCoreAdapter=null;
        DBConnection dbConnection = new DBConnection();
        dbConnection.conectar();
        String query = "select Nombre,idVendedor,Estado from Vendedores where Estado='activo'";
        try {
            Statement stm = dbConnection.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(query);

            ArrayList<String> data = new ArrayList<>();
            while (rs.next()) {
                data.add(rs.getString("Nombre"));
                data.add(rs.getString("idVendedor"));

            }

            NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return NoCoreAdapter;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Mbtn_guardar:
                conexionSQLiteHelper conn= new conexionSQLiteHelper(MainFactura.this,"bd_productos",null,1);
                SQLiteDatabase db= conn.getWritableDatabase();
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainFactura.this);
                alerta.setMessage("Quieres Guardar")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Cursor verificar_prefactura=db.rawQuery("select count(*) as cantidad from producto;", null);

                                if (verificar_prefactura.moveToFirst()) {
                                    if (verificar_prefactura.getInt(verificar_prefactura.getColumnIndex("cantidad")) == 0) {
                                        Snackbar snackbar = Snackbar.make(cuerpo, "No puedes Guardar Prefactura Vacia", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        return;
                                    }else{
                                        try {
                                            AgregarDatosSQLSEVER();
                                        }
                                        catch (SQLException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                                borrardatosTabla();
                                Toast.makeText(getApplicationContext(),"Guardar",Toast.LENGTH_SHORT).show();
                                Intent refresh = new Intent(getApplicationContext(), MainClientes.class);
                                refresh.putExtra("IdVendedor",IDVendedor);
                                startActivity(refresh);
                                finish();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog= alerta.create();
                alertDialog.setTitle("Guardar Factura");
                alertDialog.show();

                break;

            case R.id.Mbtn_addProducto:
                Intent intent2 = new Intent(getApplicationContext(),MainListaProductos.class);
                startActivity(intent2);
                break;
            case R.id.Mbtn_Home:


                AlertDialog.Builder alerta2 = new AlertDialog.Builder(MainFactura.this);
                alerta2.setMessage("Regresar al menu principal")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent3 = new Intent(getApplicationContext(),MainClientes.class);
                                intent3.putExtra("IdVendedor",IDVendedor);
                                startActivity(intent3);
                                borrardatosTabla();
                                finish();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog titulo= alerta2.create();
                titulo.setTitle("Advertencia");
                titulo.show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void ConsultarlistaProducto() {
        SQLiteDatabase db=conn.getReadableDatabase();
        ProductosAdd productosAdd = null;
        listaproducto=new ArrayList<ProductosAdd>();
        //query SQLite
        Cursor cursor=db.rawQuery("select * from "+ utilidades.TABLA_PRODUCTO,null);
        while (cursor.moveToNext()){
            productosAdd=new ProductosAdd();
            productosAdd.setId_producto(cursor.getString(cursor.getColumnIndex("id")));
            productosAdd.setNombreproduc(cursor.getString(1));
            productosAdd.setCantidad(cursor.getInt(2));
            productosAdd.setPrecios(cursor.getDouble(3));
            productosAdd.setImagenProducto(cursor.getString(4));
            listaproducto.add(productosAdd);
        }
        obtenerLista();
    }

    public void obtenerLista() {
        listainformacion=new ArrayList<String>();
        for (int i=0; i<listaproducto.size();i++){

            listainformacion.add(listaproducto.get(i).getNombreproduc()+"\n \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|\t\t\t\t\t\t\t "+listaproducto.get(i).getCantidad()+"\n C$"+listaproducto.get(i).getPrecios());
            System.out.println("MOSTRANDO LA CANTIDAD GUARDADA "+listaproducto.get(i).getCantidad());

        }

    }

    public void CalcularTotalFactura(){
        SQLiteDatabase db= conn.getReadableDatabase();
        String total="select sum(precio * cantidad ) as Total from producto";
        Cursor query =  db.rawQuery(total,null);
        if (query.moveToFirst()){
            TotalFact= query.getDouble(query.getColumnIndex("Total"));
            System.out.println("TOTAL DE LA FACTURA ACTUALMENTE ES : ----> "+TotalFact);

            textV_total.setText(String.format("%,.2f",TotalFact));
        }
        db.close();
    }

    public void borrardatosTabla(){
        SQLiteDatabase db= conn.getReadableDatabase();
        db.execSQL("delete from producto");
        db.close();
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


    public void IdFactura(){
        DBConnection dbConnection=new DBConnection();
        dbConnection.conectar();
        try {
            Statement st2 = dbConnection.getConnection().createStatement();
            ResultSet rs2 = st2.executeQuery("\n" +
                    "select top 1 idFactura from Facturas order by idFactura desc");
            while (rs2.next()) {
                valor  = rs2.getString("idFactura");
                System.out.println("==============> Ultimo Registro:"+valor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public  void AgregarDatosSQLSEVER() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.conectar();

        try {
            dbConnection.getConnection().setAutoCommit(false);
            PreparedStatement pst= dbConnection.getConnection().prepareStatement("exec sp_insertar_Facturas ?,?,?,?,?,?,?,?,?,?,?");
            pst.setInt(1, Integer.parseInt(List_Vendedores.getSelectedItem().toString()));
            pst.setInt(2, Integer.parseInt(textIdcliente.getText().toString()));
            pst.setString(3, Estados.getSelectedItem().toString());
            pst.setDouble(4, Double.parseDouble(String.valueOf(LimiteCreditosC)));
            pst.setDouble(5, Double.parseDouble(String.valueOf(tasaCambio)));
            pst.setString(6,T_ventas.getSelectedItem().toString());
            pst.setDouble(7, Double.parseDouble(String.valueOf(TotalFact)));
            pst.setString(8, nombreUsuarioC);
            pst.setString(9,T_factura.getSelectedItem().toString());
            pst.setDouble(10,TotalDolar);
            pst.setString(11,textV_Cliente.getText().toString());
            pst.executeUpdate();

            for (int i=0; i<listaproducto.size();i++){
                PreparedStatement pst2 = dbConnection.getConnection().prepareStatement( "exec sp_insertar_Detalle_FacturasMovil ?,?,?,?,?,?");
                pst2.setInt(1, Integer.parseInt(valor));
                pst2.setInt(2, IDInventario);//idInventario
                pst2.setDouble(3, listaproducto.get(i).getPrecios());//precio cordobas
                pst2.setDouble(4,0.0);//precio Dolar
                pst2.setFloat(5,listaproducto.get(i).getCantidad());// cantidad
                pst2.setDouble(6,5.00);//PorcComision
                pst2.executeUpdate();
            }

        }catch (SQLException e){
            dbConnection.getConnection().rollback();
            System.out.println("ERROR: ======> "+e);
            Toast.makeText(this," No Registrado en SQLServer",Toast.LENGTH_LONG).show();
        }finally {

            dbConnection.getConnection().setAutoCommit(true);
        }

    }

    @Override
    public void appliyTexts(String nombre) {
        textV_Cliente.setText(nombre);
    }
    private void  openDialoNombreNuevo(){
        Dialog_nombre_nuevo dialog =  new Dialog_nombre_nuevo();
        dialog.show(getSupportFragmentManager(),"Pin para activar precio");
    }

    private void open_pin_save(){
        Dialog_pin_save dialog_pin_save=  new Dialog_pin_save();

        dialog_pin_save.show(getSupportFragmentManager(),"pin para Guardar");
    }



    public void ConversionDolares(){


       TotalDolar= TotalFact / tasaCambio;

        System.out.println("cambio de total cordobas a dolar:---->"+TotalDolar);

    }

    @Override
    public void appliyTexts_pin(String pin_s) {
        Dialog_pin_save  pin= new Dialog_pin_save();
        tv_idVendedorSpinner.setText(pin_s);
        conexionSQLiteHelper conn= new conexionSQLiteHelper(MainFactura.this,"bd_productos",null,1);
        SQLiteDatabase db= conn.getWritableDatabase();

            Cursor verificar_prefactura=db.rawQuery("select count(*) as cantidad from producto;", null);

            if (verificar_prefactura.moveToFirst()) {
                if (verificar_prefactura.getInt(verificar_prefactura.getColumnIndex("cantidad")) == 0) {
                    Snackbar snackbar = Snackbar.make(cuerpo, "No puedes Guardar Prefactura Vacia", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }else{
                    try {
                        AgregarDatosSQLSEVER();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }


            borrardatosTabla();
            Toast.makeText(getApplicationContext(),"Guardar",Toast.LENGTH_SHORT).show();
            Intent refresh = new Intent(getApplicationContext(), MainClientes.class);
            refresh.putExtra("IdVendedor",IDVendedor);
            startActivity(refresh);
            finish();

    }

    public void GenerarTickets(){
        Random NumeroAleatorio= new Random();
        NumeroTikets= NumeroAleatorio.nextInt(300);
        System.out.println("Numero de tikets es: ---->"+NumeroTikets);
    }
}