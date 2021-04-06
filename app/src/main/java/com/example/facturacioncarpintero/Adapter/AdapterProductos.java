package com.example.facturacioncarpintero.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facturacioncarpintero.MainClientes;
import com.example.facturacioncarpintero.MainDetalleProducto;
import com.example.facturacioncarpintero.MainFactura;
import com.example.facturacioncarpintero.MainListaProductos;
import com.example.facturacioncarpintero.R;
import com.example.facturacioncarpintero.model.ModelItemsProducto;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.RecyclerHolder> {
    private List<ModelItemsProducto> items;
    private List<ModelItemsProducto>originalItems;
    private RecyclerItemClick itemClick;
    private boolean modoSeleccion;
    private SparseBooleanArray seleccionados;
    private String URL_IMAGE="http://ferreteriaelcarpintero.com/images/productos/";

    private int cantidad;

    public AdapterProductos(List<ModelItemsProducto> items) {
        this.items = items;
        this.itemClick=itemClick;
        this.originalItems=new ArrayList<>();
        originalItems.addAll(items);
        seleccionados = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        final ModelItemsProducto item= items.get(position);
        holder.tvNombre.setText(item.getNombreP());
        holder.tvprecio_d.setText("C$"+String.valueOf( item.getPrecioP()));
        holder.tvinfo1.setText(String.valueOf( item.getInfo1()));
        holder.tvinfo2.setText(String.valueOf( item.getInfo2()));
        holder.tvinfo3.setText(String.valueOf( item.getInfo3()));
        holder.tvinfo4.setText(String.valueOf( item.getInfo4()));
        holder.tvinfo5.setText(String.valueOf( item.getInfo5()));
        holder.tvunidad_medida.setText(item.getUnidadmedidaP());
        holder.tvproducto.setText(item.getProducto());
        holder.tvexistencia.setText("Cantidad Disponible"+String.valueOf(item.getStock()));
        holder.tvImagen.setText(item.getImg());


        Picasso.get().load(URL_IMAGE+item.getImg()+".jpg")
                .error(R.drawable.error)
                .into(holder.image);


        if (holder.tvexistencia.getText().toString().equals("0")){
            holder.tvexistencia.setVisibility(View.VISIBLE);
            holder.tvexistencia.setText(" hay poco inventario");

        }else{
            holder.tvexistencia.setText(String.valueOf(item.getStock()));
            holder.tvexistencia.setVisibility(View.GONE);
        }

 


    }



    @Override
    public int getItemCount() {
        return items.size();
    }



    public void filterListProducto(ArrayList<ModelItemsProducto> filterlistp) {
        items=filterlistp;
        notifyDataSetChanged();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{

        TextView tvNombre,tvImagen,tvinfo1,tvinfo2,tvinfo3,tvinfo4,tvunidad_medida,tvproducto,tvprecio_d,tvinfo5,tvexistencia;
        ImageView image;
        public static CheckBox check;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            tvNombre=itemView.findViewById(R.id.NombreProducto);
            tvImagen=itemView.findViewById(R.id.precio);
            tvinfo1=itemView.findViewById(R.id.tvinfo1);
            tvinfo2=itemView.findViewById(R.id.tvinfo2);
            tvinfo3=itemView.findViewById(R.id.tvinfo3);
            tvinfo4=itemView.findViewById(R.id.tvinfo4);
            image=itemView.findViewById(R.id.imagen);
            tvunidad_medida=itemView.findViewById(R.id.unidad_medida);
            tvproducto=itemView.findViewById(R.id.CodigoProducto);
            tvprecio_d=itemView.findViewById(R.id.precio_D);
            tvinfo5=itemView.findViewById(R.id.tvinfo5);
            tvexistencia =itemView.findViewById(R.id.Existencia);

            check=itemView.findViewById(R.id.check);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainListaProductos datos = new MainListaProductos();
                    MainClientes datos2= new MainClientes();
                    int stock= Integer.parseInt(tvexistencia.getText().toString());
                    Intent intent=new Intent(context, MainDetalleProducto.class);

                    intent.putExtra("NombreCliente",datos.nombrecliente);
                    intent.putExtra("CodigoCliente",datos.codigocliente);
                    intent.putExtra("ZonaCliente",datos.zonacliente);
                    intent.putExtra("IdCliente",datos.idcliente);
                    intent.putExtra("IdVendedor",datos2.id);




                    intent.putExtra("NombreP",tvproducto.getText());
                    intent.putExtra("UnidadMed",tvunidad_medida.getText());
                    intent.putExtra("info1",tvinfo1.getText());
                    intent.putExtra("info2",tvinfo2.getText());
                    intent.putExtra("info3",tvinfo3.getText());
                    intent.putExtra("info4",tvinfo4.getText());
                    intent.putExtra("info5",tvinfo5.getText());
                    intent.putExtra("imagenproducto",tvImagen.getText());
                    intent.putExtra("stock",tvexistencia.getText());
                    intent.putExtra("idproducto",tvproducto.getText());
                    intent.putExtra("idinventario",datos.IdInventario);

                    context.startActivity(intent);


                }
            });
        }
    }

    public interface RecyclerItemClick{
        void itemClick(ModelItemsProducto item);
    }
}
