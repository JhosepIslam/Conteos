package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.R;

public class EdificiosAdapter extends RecyclerView.Adapter<EdificiosAdapter.ViewHolder> {
    private ArrayList Nombre,num_planta,num_aulas,id;
    private EdificiosAdapter.OnItemClickListener itemClickListener;
    private int layout;


    public EdificiosAdapter(ArrayList Nombre,ArrayList num_planta,ArrayList num_aulas,ArrayList id,
        int layout, OnItemClickListener itemClickListener){

        this.Nombre =Nombre;
        this.num_aulas=num_aulas;
        this.num_planta = num_planta;
        this.id = id;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
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


        viewHolder.bind(Nombre.get(i).toString(),num_planta.get(i).toString(),num_aulas.get(i).toString()
                ,id.get(i).toString(),itemClickListener);
    }

    @Override
    public int getItemCount() {
        try {
            return Nombre.size();
        }
        catch (Exception ex){
            return 0;
        }
    }
    public static class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public TextView plantas;
        public TextView aulas;


        public ViewHolder(View view){
            super(view);
            this.nombre = view.findViewById(R.id.txtNombreEdificio);
            this.plantas = view.findViewById(R.id.txtPlantasEdificio);
            this.aulas = view.findViewById(R.id.txtAulasEdificio);
        }
        public  void bind(final String nombre , final String num_plantas , final String num_aulas ,
                          final String id, final OnItemClickListener listener){

            this.nombre.setText("Nombre :"+nombre);
            this.plantas.setText("Plantas: "+num_plantas);
            this.aulas.setText("Aulas: "+num_aulas);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(Integer.parseInt(id), nombre ,Integer.parseInt(num_plantas),Integer.parseInt(num_aulas), getAdapterPosition());
                }
            });

        }
    }
    public  interface  OnItemClickListener{
        void onItemClick(int id ,String nombre , int Plantas, int Aulas ,int  position);
    }
}
