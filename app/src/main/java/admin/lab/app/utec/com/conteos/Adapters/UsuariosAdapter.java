package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import admin.lab.app.utec.com.conteos.R;
import java.util.ArrayList;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder>{

    private ArrayList Nombre,Nombre_Usuario,Facultad,Nivel,Id_Usuario;
    private UsuariosAdapter.OnItemClickListener itemClickListener;
    private int layout;


    public UsuariosAdapter(ArrayList Nombre ,ArrayList Nombre_Usuario, ArrayList Facultad,
                           ArrayList Nivel ,ArrayList Id_Usuario,int layout,OnItemClickListener itemClickListener){
        this.Facultad=Facultad;
        this.Id_Usuario=Id_Usuario;
        this.Nivel = Nivel;
        this.Nombre =Nombre;
        this.Nombre_Usuario = Nombre_Usuario;
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
        viewHolder.bind(Integer.parseInt(Id_Usuario.get(i).toString()),Nombre_Usuario.get(i).toString(),
                Nombre.get(i).toString(),Nivel.get(i).toString(),Facultad.get(i).toString(),itemClickListener);
    }

    @Override
    public int getItemCount() {
        try {
            return Id_Usuario.size();
        }
        catch (Exception ex){ return  0;}
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNombre;
        public TextView txtUsuario;
        public TextView txtNivel;
        public TextView txtFacultad;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.textViewNombre_ListUsuario);
            txtUsuario = itemView.findViewById(R.id.textViewNombre_Usuario_ListUsuario);
            txtNivel = itemView.findViewById(R.id.textViewNivel_ListUsuario);
            txtFacultad = itemView.findViewById(R.id.textViewFacultad_ListUsuario);
        }
        public  void bind(final int id_usuario ,final String nombreUsuario ,final String nombre ,
                          final String nivel,final String facultad,final UsuariosAdapter.OnItemClickListener listener){

            this.txtNombre.setText(nombre);
            this.txtUsuario.setText(nombreUsuario);
            this.txtNivel.setText(nivel);
            this.txtFacultad.setText(facultad);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(id_usuario,nivel, getAdapterPosition());
                }
            });

        }
    }
    public  interface  OnItemClickListener{
        void onItemClick(int id_usuario ,String Nivel, int  position);
    }
}
