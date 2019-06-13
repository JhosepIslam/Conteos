package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.R;


public class ClasesInfoAdapter extends RecyclerView.Adapter<ClasesInfoAdapter.ViewHolder> {

    private ArrayList Materia,Aula,Docente,Horas,Dias,Seccion,Id_clase,Inscritos;
    private ArrayList cantiadad_contada;
    private ClasesInfoAdapter.OnItemClickListener itemClickListener;
    private int layout;

    public ClasesInfoAdapter(ArrayList Materia,ArrayList Aula,ArrayList Docente,ArrayList Horas,
                             ArrayList Dias,ArrayList Seccion,ArrayList Id_clase,ArrayList Inscritos,
                             ArrayList cantiadad_contada,int layout , OnItemClickListener itemClickListener){

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

        viewHolder.bind(Integer.parseInt(Id_clase.get(i).toString()),Materia.get(i).toString(),
                Docente.get(i).toString(),Aula.get(i).toString(),Horas.get(i).toString(),Dias.get(i).toString(),
                Seccion.get(i).toString(),Integer.parseInt(Inscritos.get(i).toString()),
                cantiadad_contada.get(i).toString(), itemClickListener);
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
        private TextView txtMateria,txtAula,txtDocente,txtHoras,txtDias,txtSeccion,txtIncritos,txtConteo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtMateria = itemView.findViewById(R.id.txtMateria);
            this.txtAula = itemView.findViewById(R.id.txtAula);
            this.txtDocente = itemView.findViewById(R.id.txtDocente);
            this.txtHoras = itemView.findViewById(R.id.txtHora);
            this.txtDias = itemView.findViewById(R.id.txtDias);
            this.txtSeccion = itemView.findViewById(R.id.txtSeccion);
            this.txtIncritos = itemView.findViewById(R.id.txtInscitos);
            this.txtConteo = itemView.findViewById(R.id.txtUltimoConteo);

        }

        public  void  bind(final int id, String Materia, String Docente, String Aula , String Hora, String Dias , String Seccion
        , int Inscritos, String Cantidad_Contada, final OnItemClickListener listener){
            this.txtMateria.setText("Materia: "+Materia);
            this.txtAula.setText("Aula: "+Aula);
            this.txtDocente.setText("Docente: "+Docente);
            this.txtHoras .setText("Horas: "+Hora);
            this.txtDias.setText("Dias:"+Dias);
            this.txtSeccion.setText("Seccion:"+Seccion);
            this.txtIncritos.setText("Inscritos: "+Inscritos);
            this.txtConteo.setText("Ultimo Conteo: "+Cantidad_Contada);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(id);
                }
            });
        }

    }
    public interface OnItemClickListener{
        void  OnItemClick(int id);
    }
}
