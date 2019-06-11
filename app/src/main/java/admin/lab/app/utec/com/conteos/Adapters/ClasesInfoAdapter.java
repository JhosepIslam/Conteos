package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ClasesInfoAdapter extends RecyclerView.Adapter<ClasesInfoAdapter.ViewHolder> {

    private ArrayList Materia,Aula,Docente,Horas,Dias,Seccion,Id_clase,Inscritos;
    ArrayList cantiadad_contada;
    private ConteosAdapter.OnItemClickListener itemClickListener;
    private int layout;

    public ClasesInfoAdapter(ArrayList Materia,ArrayList Aula,ArrayList Docente,ArrayList Horas,
                             ArrayList Dias,ArrayList Seccion,ArrayList Id_clase,ArrayList Inscritos,
                             ArrayList cantiadad_contada,int layout , ConteosAdapter.OnItemClickListener itemClickListener){

        this.Materia = Materia;
        this.Aula = Aula;
        this.Docente= Docente;
        this.Horas = Horas;
        this.Dias = Dias;
        this.Seccion = Seccion;
        this.Id_clase = Id_clase;
        this.Inscritos = Inscritos;
        this.cantiadad_contada = cantiadad_contada;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ClasesInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
        ClasesInfoAdapter.ViewHolder viewHolder = new ClasesInfoAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClasesInfoAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {

        try {
            return Materia.size();
        }catch (Exception ex){
            return  0;
        }
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public  void  bind(int id,String Materi, String Docente){}
    }

    public interface OnItemClickListener{
        void  OnItemClick(int id);
    }
}
