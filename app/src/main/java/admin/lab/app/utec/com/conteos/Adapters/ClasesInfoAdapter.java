package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ClasesInfoAdapter extends RecyclerView.Adapter<ClasesInfoAdapter.ViewHolder> {

    private ArrayList Materia,Aula,Docente,Horas,Dias,Seccion,Id_clase,Inscritos;
    ArrayList cantiadad_contada;
    private ConteosAdapter.OnItemClickListener itemClickListener;
    private int layout;

    public ClasesInfoAdapter(ArrayList Materia,ArrayList Aula,ArrayList Docente,ArrayList Horas,
                             ArrayList Dias,ArrayList Seccion,ArrayList Id_clase,ArrayList Inscritos,
                             ArrayList cantiadad_contada,int layout , ConteosAdapter.OnItemClickListener listener){



    }

    @NonNull
    @Override
    public ClasesInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ClasesInfoAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
