package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.R;

public class ParcialesAdapter extends RecyclerView.Adapter<ParcialesAdapter.ViewHolder> {
    private ArrayList Parcial,ID_Fecha,Inicio,Fin,Ciclo;
    private ParcialesAdapter.OnItemClickListener itemClickListener;
    private ParcialesAdapter.OnLongItemClickListener onLongItemClickListener;
    private int layout;


    public ParcialesAdapter(ArrayList Parcial,ArrayList ID_Fecha,ArrayList Inicio,ArrayList Fin,ArrayList Ciclo,
                            int layout, OnItemClickListener itemClickListener,OnLongItemClickListener onLongItemClickListener ){

        this.Parcial =Parcial;
        this.ID_Fecha=ID_Fecha;
        this.Inicio = Inicio;
        this.Fin = Fin;
        this.Ciclo = Ciclo;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.onLongItemClickListener = onLongItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout ,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        int ciclo= Integer.parseInt(Ciclo.get(i).toString());
        int ID_fecha= Integer.parseInt(ID_Fecha.get(i).toString());

        viewHolder.bind(Parcial.get(i).toString(),ciclo,Inicio.get(i).toString(),Fin.get(i).toString(),ID_fecha,
                itemClickListener, onLongItemClickListener);
    }

    @Override
    public int getItemCount() {
        try {
            return Parcial.size();
        }
        catch (Exception ex){
            return 0;
        }
    }
    public static class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView Parcial;
        public TextView Inicio;
        public TextView Fin;
        public TextView Ciclo;


        public ViewHolder(View view){
            super(view);
            this.Parcial = view.findViewById(R.id.textViewParcial_List);
            this.Inicio = view.findViewById(R.id.textViewFecha_Inicio_List);
            this.Fin = view.findViewById(R.id.textViewFecha_Fin_List);
            this.Ciclo = view.findViewById(R.id.textViewCiclo_List);

        }
        public  void bind(final String Parcial , final int Ciclo , final String Fecha_Inicio , final  String Fecha_Fin,
                          final int ID_Fecha_Parcial, final OnItemClickListener listener ,final OnLongItemClickListener listenerLong){

            this.Parcial.setText("Parcial: "+Parcial);
            this.Inicio.setText("Inicio: "+Fecha_Inicio);
            this.Fin.setText("Finalizacion: "+Fecha_Fin);
            final String anio = Fecha_Inicio.substring(0,4);
            int ciclo=0;
            switch (Ciclo){
                case 1:
                    ciclo =(1);
                    break;
                case 2:
                    ciclo =(3);

                    break;
                case  3:
                    ciclo =(2);
                    break;
            }
            this.Ciclo.setText("Ciclo: 0"+ciclo+"-"+anio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(ID_Fecha_Parcial,Ciclo,getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listenerLong.onLongItemClick(ID_Fecha_Parcial,Parcial,Fecha_Inicio,Fecha_Fin,"0"+Ciclo+"-"+anio,getAdapterPosition());
                    return false;
                }
            });

        }
    }
    public  interface  OnItemClickListener{
        void onItemClick(int ID_Fecha_Parcial, int Ciclo,int position);
    }
    public  interface  OnLongItemClickListener{
        void onLongItemClick(int ID_Fecha_Parcial,String Parcial ,String Inicio, String Fin,String Ciclo,int position);
    }
}
