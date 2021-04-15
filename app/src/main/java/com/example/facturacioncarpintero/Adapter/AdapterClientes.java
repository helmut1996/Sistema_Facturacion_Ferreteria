package com.example.facturacioncarpintero.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facturacioncarpintero.MainClientes;
import com.example.facturacioncarpintero.MainListaProductos;
import com.example.facturacioncarpintero.R;
import com.example.facturacioncarpintero.model.itemList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterClientes extends RecyclerView.Adapter<AdapterClientes.RecyclerHolder>{

    private List<itemList> items;
    private List<itemList>originalItems;
    private RecyclerItemClick itemClick;





    public AdapterClientes(List<itemList> items) {
        this.items = items;
        this.originalItems=new ArrayList<>();
        originalItems.addAll(items);
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clientes,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        final itemList item= items.get(position);
        holder.codigo.setText(String.valueOf(item.getCodigo()));
        holder.nombre.setText(item.getNombre());
        holder.zona.setText(item.getZona());
        holder.idcliente.setText(String.valueOf(item.getIdCliente()));
        holder.limitecredito.setText(String.valueOf(item.getLimiteCredito()));


    }



    @Override
    public int getItemCount() {
        return items.size();
    }


    public void filterList(ArrayList<itemList> filteredlist) {
        items=filteredlist;
        notifyDataSetChanged();
    }
    public static class RecyclerHolder extends RecyclerView.ViewHolder{

        TextView codigo,nombre,zona,idcliente,limitecredito;
        Button seleccionar;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            codigo=itemView.findViewById(R.id.CodigoCliente);
            nombre=itemView.findViewById(R.id.nombreCliente);
            zona=itemView.findViewById(R.id.cliente_zona);
            idcliente=itemView.findViewById(R.id.id_cliente);
            seleccionar=itemView.findViewById(R.id.btn_select);
            limitecredito=itemView.findViewById(R.id.LimiteCredito);

            seleccionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainClientes datos = new MainClientes();
                    Intent intent=new Intent(context, MainListaProductos.class);
                    intent.putExtra("Idvendedor",datos.id);
                    intent.putExtra("Nombrecliente",nombre.getText());
                    intent.putExtra("Codigocliente",codigo.getText());
                    intent.putExtra("Zonacliente",zona.getText());
                    intent.putExtra("Idcliente",idcliente.getText());
                    intent.putExtra("LimiteCredito",limitecredito.getText());
                    context.startActivity(intent);
                }
            });

        }
    }

    public interface RecyclerItemClick{
        void itemClick(itemList item);
    }
}
