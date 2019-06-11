package admin.lab.app.utec.com.conteos.Fragments;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;

import admin.lab.app.utec.com.conteos.Adapters.HoraParcialAdapter;
import admin.lab.app.utec.com.conteos.Models.Parciales;
import admin.lab.app.utec.com.conteos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiasHorasFragment extends Fragment {


    private String fecha_;
    Parciales parciales = new Parciales();
    private int id_parcial_;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int Ciclo_;
    private TextView textViewFecha;
    private String usuario_;
    EditText editTextInicio;
    AlertDialog alertDialog;
    EditText editTextFin ;
    private FloatingActionButton floatingActionButton;

    public DiasHorasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dias_horas, container, false);
        fecha_= getArguments().getString("Fecha");
        usuario_= getArguments().getString("usuario");
        id_parcial_= getArguments().getInt("ID");
        Ciclo_= getArguments().getInt("Ciclo");

        return view;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Async_Get_Horas async_get_horas = new Async_Get_Horas();
        async_get_horas.execute();
        textViewFecha= getView().findViewById(R.id.txtFecha_Parcial_V);
        recyclerView = getView().findViewById(R.id.recyclerViewHorasDiaParcial);
        textViewFecha.setText(fecha_);
        floatingActionButton = getView().findViewById(R.id.floatingActionButton_Add_Hora);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertInsert().show();
            }
        });


    }
    private AlertDialog AlertInsert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.agregar_hora_parcial_layout,null);
        builder.setView(v);
        alertDialog =builder.create();


       editTextInicio= v.findViewById(R.id.txtSetInicio_Hora);
       editTextFin = v.findViewById(R.id.txtSetFinal_Hora);
        editTextInicio.setInputType(InputType.TYPE_NULL);
        editTextFin.setInputType(InputType.TYPE_NULL);
        editTextFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextFin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar cldr = Calendar.getInstance();
                        int hour = cldr.get(Calendar.HOUR_OF_DAY);
                        int minutes = cldr.get(Calendar.MINUTE);
                        // time picker dialog
                        TimePickerDialog picker = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                        if (sMinute <= 9) {
                                            editTextFin.setText(sHour + ":0" + sMinute);
                                        } else
                                            editTextFin.setText(sHour + ":" + sMinute);
                                    }
                                }, hour, minutes, true);
                        picker.show();
                    }
                });
            }
        });
       editTextInicio.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               editTextInicio.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       final Calendar cldr = Calendar.getInstance();
                       int hour = cldr.get(Calendar.HOUR_OF_DAY);
                       int minutes = cldr.get(Calendar.MINUTE);
                       // time picker dialog
                       TimePickerDialog picker = new TimePickerDialog(getActivity(),
                               new TimePickerDialog.OnTimeSetListener() {
                                   @Override
                                   public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                       if (sMinute <= 9) {
                                           editTextInicio.setText(sHour + ":0" + sMinute);
                                       } else
                                           editTextInicio.setText(sHour + ":" + sMinute);
                                   }
                               }, hour, minutes, true);
                       picker.show();
                   }
               });
           }
       });
        Button btnEnviar = v.findViewById(R.id.btnSaveIH);
        Button btnCancelar = v.findViewById(R.id.btnCancelIH);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextInicio.getText().toString().trim().isEmpty()){
                    editTextInicio.setError("Campo Obligatorio");
                }else if (editTextFin.getText().toString().trim().isEmpty())
                {
                    editTextFin.setError("Campo Obligatorio");
                }
                else {
                    String Inicio = editTextInicio.getText().toString().trim();
                    String Fin =(editTextFin.getText().toString().trim());
                    Async_Set_Hora async_set_hora = new Async_Set_Hora(Inicio,Fin);
                    async_set_hora.execute();


                }
            }
        });

        return alertDialog ;
    }
    private AlertDialog AlertUD(final int ID_Hora){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.edit_delete_dialog,null);
        builder.setView(v);
        alertDialog =builder.create();


        RadioButton radioButtonEdit = v.findViewById(R.id.rdbEditar);
        final RadioButton radioButtonDelete = v.findViewById(R.id.rdbEliminar);
        final LinearLayout linearLayoutEdit = v.findViewById(R.id.LLEditHoras_Parcial);

        radioButtonEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b){
                    linearLayoutEdit.setVisibility(View.GONE);
                }else {
                    linearLayoutEdit.setVisibility(View.VISIBLE);
                }
            }
        });
        editTextInicio= v.findViewById(R.id.editTextInicio_Parciales);
        editTextFin = v.findViewById(R.id.editTextFin_Parciales);
        editTextInicio.setInputType(InputType.TYPE_NULL);
        editTextFin.setInputType(InputType.TYPE_NULL);
        editTextFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextFin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar cldr = Calendar.getInstance();
                        int hour = cldr.get(Calendar.HOUR_OF_DAY);
                        int minutes = cldr.get(Calendar.MINUTE);
                        // time picker dialog
                        TimePickerDialog picker = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                        if (sMinute <= 9) {
                                            editTextFin.setText(sHour + ":0" + sMinute);
                                        } else
                                            editTextFin.setText(sHour + ":" + sMinute);
                                    }
                                }, hour, minutes, true);
                        picker.show();
                    }
                });
            }
        });
        editTextInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextInicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar cldr = Calendar.getInstance();
                        int hour = cldr.get(Calendar.HOUR_OF_DAY);
                        int minutes = cldr.get(Calendar.MINUTE);
                        // time picker dialog
                        TimePickerDialog picker = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                        if (sMinute <= 9) {
                                            editTextInicio.setText(sHour + ":0" + sMinute);
                                        } else
                                            editTextInicio.setText(sHour + ":" + sMinute);
                                    }
                                }, hour, minutes, true);
                        picker.show();
                    }
                });
            }
        });
        Button btnEnviar = v.findViewById(R.id.btnSaveEIH);
        Button btnCancelar = v.findViewById(R.id.btnCancelEIH);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonDelete.isChecked()){
                    Async_Delete_Hora async_delete_hora= new Async_Delete_Hora(ID_Hora);
                    async_delete_hora.execute();


                }else {
                    if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Campo Obligatorio");
                    }else if (editTextFin.getText().toString().trim().isEmpty())
                    {
                        editTextFin.setError("Campo Obligatorio");
                    }
                    else {
                        String Inicio = editTextInicio.getText().toString().trim();
                        String Fin =(editTextFin.getText().toString().trim());
                        Async_Update_Hora async_update_hora = new Async_Update_Hora(Inicio,Fin,ID_Hora);
                        async_update_hora.execute();
                    }
                }
            }
        });

        return alertDialog ;
    }


    public class Async_Get_Horas extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            parciales.Get_Horas_From_Server(id_parcial_,fecha_);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            try {
                layoutManager = new LinearLayoutManager(getContext());
                myAdapter =  new HoraParcialAdapter(parciales.getHORA_INICIO(), parciales.getHORA_FIN(), parciales.getID_HORA(), R.layout.card_view_horas_parcial, new HoraParcialAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String Fecha, int ID) {

                    }
                }, new HoraParcialAdapter.OnLongItemClickListener() {
                    @Override
                    public void onLongItemClick(String Fecha, int ID) {
                        AlertUD(ID).show();

                    }
                });
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(layoutManager);

            }catch (Exception ex){

            }
        }
    }
    public class Async_Set_Hora extends AsyncTask<Void,Void,Boolean> {

        int result;
        String Inicio,Fin;
        public Async_Set_Hora(String Inicio,String Fin){
           this.Fin=Fin;
           this.Inicio=Inicio;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            result =parciales.Set_Horas_From_Server(id_parcial_,fecha_,Inicio,Fin,usuario_,Ciclo_);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            switch (result){
                case 0:
                    break;
                case -1:
                    editTextFin.setError("Rango de Hora Ocupado");
                    editTextInicio.setError("Rango de Hora Ocupado");
                    break;
                case -2:
                    editTextFin.setError("Rango de Hora No Valido");
                    editTextInicio.setError("Rango de Hora No Valido");
                    break;
                case -3:
                    editTextFin.setError("Rango de Hora No Valido");
                    editTextInicio.setError("Rango de Hora No Valido");
                    break;
                case 1:
                    alertDialog.dismiss();
                    Toast.makeText(getContext(),"Insertado",Toast.LENGTH_SHORT).show();
                    Async_Get_Horas async_get_horas = new Async_Get_Horas();
                    async_get_horas.execute();
                    break;
            }
        }
    }
    public class Async_Update_Hora extends AsyncTask<Void,Void,Boolean> {

        int result,id;
        String Inicio,Fin;
        public Async_Update_Hora(String Inicio,String Fin,int id){
            this.Fin=Fin;
            this.id=id;
            this.Inicio=Inicio;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            result =parciales.Update_Horas_From_Server(id,fecha_,Inicio,Fin,usuario_);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            switch (result){
                case 0:
                    break;
                case -1:
                    editTextFin.setError("Rango de Hora Ocupado");
                    editTextInicio.setError("Rango de Hora Ocupado");
                    break;
                case -2:
                    editTextFin.setError("Rango de Hora No Valido");
                    editTextInicio.setError("Rango de Hora No Valido");
                    break;
                case -3:
                    editTextFin.setError("Rango de Hora No Valido");
                    editTextInicio.setError("Rango de Hora No Valido");
                    break;
                case 1:
                    alertDialog.dismiss();
                    Toast.makeText(getContext(),"Actualizado",Toast.LENGTH_SHORT).show();
                    Async_Get_Horas async_get_horas = new Async_Get_Horas();
                    async_get_horas.execute();
                    break;
            }
        }
    }
    public class Async_Delete_Hora extends AsyncTask<Void,Void,Boolean> {

        int result,id;
        public Async_Delete_Hora(int id){

            this.id=id;

        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            result =parciales.Delete_Horas_From_Server(id,usuario_);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            switch (result){
                case 0:
                    Toast.makeText(getContext(),"Error de Conexion",Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(getContext(),"Error de Conexion",Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    alertDialog.dismiss();
                    Toast.makeText(getContext(),"Eliminado",Toast.LENGTH_SHORT).show();
                    Async_Get_Horas async_get_horas = new Async_Get_Horas();
                    async_get_horas.execute();
                    break;
            }
        }
    }
}
