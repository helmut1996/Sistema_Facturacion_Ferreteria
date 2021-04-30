package com.example.facturacioncarpintero;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.facturacioncarpintero.ConexionBD.DBConnection;
import com.example.facturacioncarpintero.SQLite.conexionSQLiteHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dialog_pin_save extends AppCompatDialogFragment {
    EditText editPinSave;
    String pin_save;
    String  NombreVendedor;
    Dialog_pin_save.DialogListennerPinSave listennerPinSave;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_pin_save_factura,null);
        builder.setView(view)
                .setTitle("Ingresa pin")

                .setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pin_save= editPinSave.getText().toString();
                        Conectarpin();

                        listennerPinSave.appliyTexts_pin(pin_save);
                    }
                });

        editPinSave = view.findViewById(R.id.edit_pin_save);
        return builder.create();


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listennerPinSave= (Dialog_pin_save.DialogListennerPinSave) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+"se debe Implementar el DialogListenner");
        }
    }


    private void Conectarpin() {
        conexionSQLiteHelper conn= new conexionSQLiteHelper(getContext(),"bd_productos",null,1);
        SQLiteDatabase db= conn.getWritableDatabase();

        if (editPinSave.getText().toString().equals("")) {
            Toast.makeText(getContext(), "debe ingresar su pin ", Toast.LENGTH_LONG).show();
            editPinSave.requestFocus();
        } else {
            try {
                DBConnection dbConnection = new DBConnection();
                dbConnection.conectar();
                Statement stm = dbConnection.getConnection().createStatement();
                ResultSet rs = stm.executeQuery("Select pin, name from users where pin='" + editPinSave.getText().toString() + "'");
                if (rs.next()) {
                    NombreVendedor= rs.getString(2);
                    int id=rs.getInt(1);

                } else {
                        dismiss();
                    Toast.makeText(getContext(), "Pin incorrecto", Toast.LENGTH_LONG).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public interface DialogListennerPinSave{
        void appliyTexts_pin(String pin_s);
    }

}
