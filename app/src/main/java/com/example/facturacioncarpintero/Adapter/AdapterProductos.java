package com.example.facturacioncarpintero.Adapter;

import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    private String URL_IMAGE="http://ferreteriaelcarpintero.com/images/carpintero/";

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
        holder.tvexistencia.setText(String.valueOf(item.getStock()));
        holder.tvPrecio.setText(item.getImg());

       /* Picasso.get().load(URL_IMAGE+item.getImg())
                .error(R.drawable.error)
                .into(holder.image);


        */
        /*
        File file= new File("///storage/emulated/0/MARNOR/"+item.getImg()+".jpg");
        Picasso.get().load(file)
                //.placeholder(R.drawable.bucandoimg)
                .error(R.drawable.error)
                .into(holder.image);
         */
        if (holder.tvexistencia.getText().toString().equals("100")){
            holder.tvexistencia.setVisibility(View.VISIBLE);
            holder.tvexistencia.setText(" hay poco inventario");

        }else{
            holder.tvexistencia.setText(String.valueOf(item.getStock()));
            holder.tvexistencia.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(item);
            }
        });



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

        TextView tvNombre,tvPrecio,tvinfo1,tvinfo2,tvinfo3,tvinfo4,tvunidad_medida,tvproducto,tvprecio_d,tvinfo5,tvexistencia;
        ImageView image;
        public static CheckBox check;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre=itemView.findViewById(R.id.NombreProducto);
            tvPrecio=itemView.findViewById(R.id.precio);
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
        }
    }

    public interface RecyclerItemClick{
        void itemClick(ModelItemsProducto item);
    }
}
