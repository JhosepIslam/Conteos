package admin.lab.app.utec.com.conteos.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import admin.lab.app.utec.com.conteos.R;

public class ConteosAdapter extends RecyclerView.Adapter<ConteosAdapter.ViewHolder> {

    private List<String> Materia,Aula,Docente,Horas,Dias,Seccion,Id_clase,Inscritos;
    ArrayList ID_materia_contada,cantiadad_contada;
    private OnItemClickListener itemClickListener;
    private int layout;

    public ConteosAdapter(List<String> Id_clase,List<String> Inscritos,List<String> Materia,List<String> Aula,List<String>
            Docente,List<String> Horas,List<String> Dias,List<String> Seccion,ArrayList ID_materia_contada,ArrayList cantiadad_contada,
                          int layout, OnItemClickListener ItemClickListener){
        
        this.Materia=Materia;
        this.Aula =Aula ;
        this.Docente=Docente;
        this.Horas =Horas ;
        this.Dias =Dias ;
        this.Seccion=Seccion;
        this.layout =layout;
        this.Id_clase = Id_clase;
        this.ID_materia_contada = ID_materia_contada;
        this.cantiadad_contada = cantiadad_contada;
        this.Inscritos = Inscritos;
        this.itemClickListener=ItemClickListener;
    }
    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

         View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
         ViewHolder viewHolder = new ViewHolder(view);
         return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.bind(Integer.parseInt(Id_clase.get(position)),Integer.parseInt(Inscritos.get(position)),Materia.get(position),Aula.get(position),
                Docente.get(position),Horas.get(position),Dias.get(position),Seccion.get(position),ID_materia_contada,cantiadad_contada,itemClickListener);
    }
    @Override
    public int getItemCount() {
        try {
            return Materia.size();
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            return 0;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtMateria,txtAula,txtDocente,txtHoras,txtDias,txtSeccion,txtIncritos,txtConteo;


        public  ViewHolder(View view){
            super(view);
            this.txtMateria = view.findViewById(R.id.txtMateria);
            this.txtAula = view.findViewById(R.id.txtAula);
            this.txtDocente = view.findViewById(R.id.txtDocente);
            this.txtHoras = view.findViewById(R.id.txtHora);
            this.txtDias = view.findViewById(R.id.txtDias);
            this.txtSeccion = view.findViewById(R.id.txtSeccion);
            this.txtIncritos = view.findViewById(R.id.txtInscitos);
            this.txtConteo = view.findViewById(R.id.txtUltimoConteo);
        }

        public void bind(final int id, final int inscritos , final String Materia, final String Aula, final String Docente,
                         final String Horas, final String Dias, final String Seccion, final ArrayList ID_materia_contada ,final ArrayList Cantidad ,
                         final OnItemClickListener listener ){
            this.txtMateria.setText("Materia: "+Materia);
            this.txtAula.setText("Aula: "+Aula);
            this.txtDocente.setText("Docente: "+Docente);
            this.txtHoras .setText("Horas: "+Horas);
            this.txtDias.setText("Dias:"+Dias);
            this.txtSeccion.setText("Seccion:"+Seccion);
            this.txtIncritos.setText("Inscritos: "+inscritos);
            try {
                int index = ID_materia_contada.indexOf(""+id);
                if (index != -1){
                    String cant =Cantidad.get(index).toString();
                    this.txtConteo.setText("Ultimo Conteo: "+cant);
                }
                else {
                    this.txtConteo.setText("Ultimo Conteo: -");
                }

            }catch (Exception ex){
                this.txtConteo.setText("");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(id,inscritos,getAdapterPosition());
                }
            });
        }

    }
    public interface OnItemClickListener{
         void OnItemClick(int id ,int incritos,int Position);
    }
}
