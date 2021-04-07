package com.example.facturacioncarpintero;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class Dialog_TasaCambio extends AppCompatDialogFragment {
    private EditText editTasaCambio;
    private DialogListenner listenner;
    String T_Cambio;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_tasa_dolar,null);
        builder.setView(view)
                .setTitle("Tasa Cambio")

                .setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        T_Cambio= editTasaCambio.getText().toString();

                        listenner.appliyTexts(T_Cambio);
                    }
                });

        editTasaCambio = view.findViewById(R.id.edit_tasa_cambio);
        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listenner= (DialogListenner) context;
        }catch (ClassCastException e){
           throw  new ClassCastException(context.toString()+"se debe Implementar el DialogListenner");
        }
    }

    public interface DialogListenner{
        void appliyTexts(String cambio);
    }
}
