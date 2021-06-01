package com.example.facturacionlibreria;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.facturacionlibreria.SQLite.conexionSQLiteHelper;
import com.squareup.picasso.Picasso;

public class ClassDialogFactura extends DialogFragment {
    TextView producto,cantidad,precio_C,precio_D,tvimagenSQLite;
    ImageView imgProductoDetalle;
    EditText editcantidad2;
    MainDetalleProducto variable = new MainDetalleProducto();
    MainFactura datos =new MainFactura();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_detalle_factura,null);

        builder.setView(view)
                .setTitle("Detalle Producto")
                .setPositiveButton("Actualizar",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent refresh = new Intent(getContext(), MainFactura.class);
                        refresh.putExtra("NombreCliente",variable.NombreCliente);
                        refresh.putExtra("CodigoCliente",variable.CodigoCliente);
                        refresh.putExtra("ZonaCliente",variable.ZonaCliente);
                        refresh.putExtra("IdCliente",variable.IdCliente);
                        refresh.putExtra("IdVendedor",variable.IdVendedor);
                        startActivity(refresh);
                        ActualizarDatosSQLite();

                    }
                })
                .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent refresh = new Intent(getContext(), MainFactura.class);
                                refresh.putExtra("NombreCliente",variable.NombreCliente);
                                refresh.putExtra("CodigoCliente",variable.CodigoCliente);
                                refresh.putExtra("ZonaCliente",variable.ZonaCliente);
                                refresh.putExtra("IdCliente",variable.IdCliente);
                                refresh.putExtra("IdVendedor",variable.IdVendedor);
                                startActivity(refresh);

                                EliminarDatosSQLite();

                            }
                        }

                );

        producto=view.findViewById(R.id.id_producto);
        producto.setText(datos.nombre);

        cantidad=view.findViewById(R.id.editTextCantidad2);
        cantidad.setText(String.valueOf(datos.cantidadProducto));

        precio_C=view.findViewById(R.id.id_precio_cordobas);
        precio_C.setText(String.valueOf(datos.precioProducto));

        precio_D=view.findViewById(R.id.id_precio_dolares);
        precio_D.setText(String.valueOf(datos.idProd));

        // btn_delete=view.findViewById(R.id.imageButton_Eliminar);

        imgProductoDetalle=view.findViewById(R.id.imageProducto);
        String imagen= datos.nombreImagen;
        String URL="http://ferreteriaelcarpintero.com/images/productos/";
        Picasso.get().load(URL+imagen+".jpg")
                .error(R.drawable.error)
                .into(imgProductoDetalle);

        tvimagenSQLite=view.findViewById(R.id.tvimagenBD_SQLlite);
        tvimagenSQLite.setText(datos.nombreImagen);

        editcantidad2=view.findViewById(R.id.editTextCantidad2);
        return builder.create();

    }


    private void ActualizarDatosSQLite() {
        /*mandando a llamar conexion a SQLite */
        conexionSQLiteHelper conn= new conexionSQLiteHelper(getContext(),"bd_productos",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        if (editcantidad2.getText().toString().isEmpty()){
            Toast.makeText(getContext(),"No se Actualizo en cantidad vacia",Toast.LENGTH_LONG).show();
        } else if(Integer.parseInt(editcantidad2.getText().toString())==0){

            Toast.makeText(getContext(),"No se Actualizo en cantidad 0",Toast.LENGTH_LONG).show();
        }else {
            db.execSQL("update producto set cantidad ="+cantidad.getText().toString()+" where id = "+precio_D.getText().toString()+" ");
            db.close();

            Toast.makeText(getContext(),"Actualizado!!!",Toast.LENGTH_SHORT).show();
        }

    }

    private void EliminarDatosSQLite() {
        conexionSQLiteHelper conn = new conexionSQLiteHelper(getContext(),"bd_productos",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        db.execSQL("DELETE FROM producto WHERE id = "+precio_D.getText().toString()+" ");
        db.close();
        Toast.makeText(getContext(),"Registro Eliminado!!!",Toast.LENGTH_SHORT).show();
    }

    public static class ClassDialogLoading extends MainFactura {
        private Activity activity;
        private AlertDialog dialog;

        ClassDialogLoading(Activity myActivity){
            activity=myActivity;

        }

        public void iniciarCarga(){
            AlertDialog.Builder builder=new AlertDialog.Builder(activity);
            LayoutInflater inflater= activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.loading_dialog,null));
            builder.setCancelable(false);
            dialog=builder.create();
            dialog.show();
        }

        public void cerrarCarga(){
    //        LimpiarCampos();
            dialog.dismiss();
        }
    }
}
