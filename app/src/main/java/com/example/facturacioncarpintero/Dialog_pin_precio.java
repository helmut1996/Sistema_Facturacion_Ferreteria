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

public class Dialog_pin_precio extends AppCompatDialogFragment {
    EditText editPinPrecio;
    String pin_precio;
    DialogListennerPinPrecio listennerPinPrecio;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_pin_precio,null);
        builder.setView(view)
                .setTitle("Ingresa pin")

                .setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pin_precio= editPinPrecio.getText().toString();

                        listennerPinPrecio.appliyTexts(pin_precio);
                    }
                });

        editPinPrecio = view.findViewById(R.id.edit_pin_precio);
        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listennerPinPrecio= (Dialog_pin_precio.DialogListennerPinPrecio) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+"se debe Implementar el DialogListenner");
        }
    }
    public interface DialogListennerPinPrecio{
        void appliyTexts(String cambio);
    }
}
