package com.example.facturacioncarpintero;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_nombre_nuevo extends AppCompatDialogFragment {
    EditText editN_Nombre;
    String n_nombre;
    DialogListennerNombreNuevo listennerNombreNuevo;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_cambiar_nombre,null);
        builder.setView(view)
                .setTitle("Nuevo Nombre")

                .setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        n_nombre= editN_Nombre.getText().toString();

                        listennerNombreNuevo.appliyTexts(n_nombre);
                    }
                });

        editN_Nombre = view.findViewById(R.id.edit_nombre_cliente);
        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listennerNombreNuevo= (Dialog_nombre_nuevo.DialogListennerNombreNuevo) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+"se debe Implementar el DialogListenner");
        }
    }
    public interface DialogListennerNombreNuevo{
        void appliyTexts(String nombre);
    }
}
