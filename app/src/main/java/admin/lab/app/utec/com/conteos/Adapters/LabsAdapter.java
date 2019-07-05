package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.R;

public class LabsAdapter extends RecyclerView.Adapter<LabsAdapter.ViewHolder> {

    private ArrayList Nombre,Edificio,Capacidad,Abreviatura,ID;
    private int layout;
    private OnItemClickListener itemClickListener;
    public LabsAdapter(ArrayList Nombre,ArrayList Edificio,ArrayList Capacidad,ArrayList Abreviatura
            ,ArrayList ID,int layout, OnItemClickListener itemClickListener)
    {
        this.Abreviatura = Abreviatura;
        this.Capacidad = Capacidad;
        this.Edificio = Edificio;
        this.ID = ID;
        this.Nombre = Nombre;
        this.itemClickListener = itemClickListener;
        this.layout = layout;
    }

    @NonNull
    @Override
    public LabsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
        LabsAdapter.ViewHolder viewHolder = new LabsAdapter.ViewHolder(view);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull LabsAdapter.ViewHolder viewHolder, int i) {

        viewHolder.bind(Integer.parseInt(ID.get(i).toString()),Nombre.get(i).toString(),Edificio.get(i).toString(),
                Integer.parseInt(Capacidad.get(i).toString()),Abreviatura.get(i).toString(),itemClickListener);
    }

    @Override
    public int getItemCount() {
        try {
            return ID.size();
        }catch (Exception e){
            return 0;
        }

    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre,textViewEdificio,textViewCapacidad,textViewAbreviatura;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewNombre=itemView.findViewById(R.id.txtNombreLab);
            this.textViewEdificio=itemView.findViewById(R.id.txtEdificio);
            this.textViewCapacidad=itemView.findViewById(R.id.txtCapacidad);
            this.textViewAbreviatura=itemView.findViewById(R.id.txtAbreviatura);
        }

        public void bind(final int ID, final String Nombre, final String Edificio, final int Capacidad ,
                         final String Abreviatura, final OnItemClickListener itemClickListener){
            textViewNombre.setText("Nombre: "+Nombre);
            textViewCapacidad.setText("Capacidad: "+Capacidad);
            textViewAbreviatura.setText("Abreviatura: "+Abreviatura);
            textViewEdificio.setText("Edificio: "+Edificio);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnItemClick(ID,Nombre,Capacidad,Edificio,Abreviatura);
                }
            });
        }


    }
    public interface OnItemClickListener{
        void  OnItemClick(int id,String Nombre, int Capacidad, String Edificio, String Abreviatura);
    }
}
