package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.R;

public class HoraParcialAdapter extends RecyclerView.Adapter<HoraParcialAdapter.ViewHolder> {

    private ArrayList Inicio, Fin,ID;
    private HoraParcialAdapter.OnItemClickListener itemClickListener;
    private HoraParcialAdapter.OnLongItemClickListener longItemClickListener;

    private int layout;

    public  HoraParcialAdapter(ArrayList Inicio , ArrayList Fin,ArrayList ID, int layout, HoraParcialAdapter.OnItemClickListener itemClickListener,HoraParcialAdapter.OnLongItemClickListener longItemClickListener){
        this.longItemClickListener = longItemClickListener;
        this.Inicio = Inicio;
        this.Fin = Fin;
        this.ID = ID;
        this.layout=layout;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public HoraParcialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout ,viewGroup,false);
        HoraParcialAdapter.ViewHolder vh = new HoraParcialAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HoraParcialAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(Inicio.get(i).toString(),Fin.get(i).toString(),Integer.parseInt(ID.get(i).toString()),itemClickListener, longItemClickListener);
    }


    @Override
    public int getItemCount() {
        try {
            return Fin.size();
        }catch (Exception ex){
            return 0;
        }

    }


    public static class  ViewHolder extends RecyclerView.ViewHolder {
        public TextView Fin;
        public TextView Inicio;



        public ViewHolder(View view){
            super(view);
            this.Fin = view.findViewById(R.id.txtFin_Parcial_Dia);
            this.Inicio = view.findViewById(R.id.txtInicio_PArcial_Dia);
        }
        public  void bind(final String Inicio, final String Fecha, final int ID, final HoraParcialAdapter.OnItemClickListener listener,
                          final HoraParcialAdapter.OnLongItemClickListener longItemClickListener){

            this.Inicio.setText("Inicio: "+Inicio.substring(0,5));
            this.Fin.setText("Finalización: "+Fecha.substring(0,5));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(Fecha,ID );

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longItemClickListener.onLongItemClick(Fecha,ID);
                    return true;
                }
            });

        }



    }
    public  interface  OnItemClickListener{
        void onItemClick(String Fecha,int ID );
    }
    public  interface  OnLongItemClickListener{
        void onLongItemClick(String Fecha,int ID);
    }
}