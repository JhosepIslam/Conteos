package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.R;


public class  DiaParcialAdapter extends RecyclerView.Adapter<DiaParcialAdapter.ViewHolder> {

    private ArrayList Dias, Fechas;
    private DiaParcialAdapter.OnItemClickListener itemClickListener;
    private int layout;

    public  DiaParcialAdapter(ArrayList Dias , ArrayList Fechas, int layout, OnItemClickListener itemClickListener){
        this.Dias = Dias;
        this.Fechas = Fechas;
        this.layout=layout;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public DiaParcialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout ,viewGroup,false);
        DiaParcialAdapter.ViewHolder vh = new DiaParcialAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaParcialAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(Dias.get(i).toString(),Fechas.get(i).toString(),itemClickListener);
    }

    @Override
    public int getItemCount() {
        try {
            return Dias.size();
        }catch (Exception ex){
            return 0;
        }

    }


    public static class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView Dia;
        public TextView Fecha;



        public ViewHolder(View view){
            super(view);
            this.Fecha = view.findViewById(R.id.txtDia_Fecha_Parcial);
            this.Dia = view.findViewById(R.id.txtFecha_Parcial);

        }
        public  void bind(final String Dia,final String Fecha,final DiaParcialAdapter.OnItemClickListener listener){

            this.Dia.setText(Dia);
            this.Fecha.setText(Fecha);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(Fecha);
                }
            });

        }
    }
    public  interface  OnItemClickListener{
        void onItemClick(String Fecha);
    }
}
