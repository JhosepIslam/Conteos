package admin.lab.app.utec.com.conteos.Fragments;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Calendar;

import admin.lab.app.utec.com.conteos.Adapters.ConteosAdapter;
import admin.lab.app.utec.com.conteos.Models.Clases;
import admin.lab.app.utec.com.conteos.Models.Edificios;
import admin.lab.app.utec.com.conteos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarConteoFragment extends Fragment {

    ArrayList faltas = new ArrayList();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    AlertDialog alertDialog;
    EditText txtAsistencia;
    Boolean flag= false;
    ShimmerFrameLayout shimmerFrameLayout;
    int ID_ASISTENCIA;
    private LinearLayout LLfiltroEdificio;
    Edificios edificios = new Edificios();

    Button btnSave;
    private Spinner spinnerEdificios,spinnerHoras;
    SwipeRefreshLayout swipeRefreshLayout;
    TimePickerDialog picker;
    int nivel_;
    String usuario_;
    Clases clases =  new Clases();
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButton6;
    private RadioButton radioButton7;
    private RadioButton radioButton8;


    public AgregarConteoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_agregar_conteo, container, false);
        usuario_= getArguments().getString("usuario");
        nivel_= Integer.parseInt(getArguments().getString("nivel"));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shimmerFrameLayout = getView().findViewById(R.id.shimmerLayoutConteo);
        swipeRefreshLayout = getView().findViewById(R.id.refreshLayout);
        spinnerEdificios = getView().findViewById(R.id.spEdificioConteoFiltro);
        spinnerHoras = getView().findViewById(R.id.txtHoraContFiltro);
        LLfiltroEdificio = getView().findViewById(R.id.LLFiltroEdificioClases);


        if (nivel_==4){
            LLfiltroEdificio.setVisibility(View.GONE);
        }
            Async_get_edif async_get_edif = new Async_get_edif();
            async_get_edif.execute();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (nivel_==4){
                    Async_get_Clases async_get_clases = new Async_get_Clases(usuario_,spinnerHoras.getSelectedItem().toString().trim());
                    async_get_clases.execute();
                }else {

                    try {
                        String edi=spinnerEdificios.getSelectedItem().toString().trim();
                        Async_get_Clases async_get_clases = new Async_get_Clases(edi,spinnerHoras.getSelectedItem().toString().trim());
                        async_get_clases.execute();
                    }catch (Exception ex){

                    }

                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        spinnerEdificios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item ;
                String hora;
                try {

                    item = spinnerEdificios.getSelectedItem().toString();
                    hora=spinnerHoras.getSelectedItem().toString();
                    Async_get_Clases async_get_clases = new Async_get_Clases(item,hora);
                    async_get_clases.execute();

                }catch (Exception ex){}


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item ;
                String hora;
                try {
                    if (nivel_==4){
                    Async_get_Clases async_get_clases = new Async_get_Clases(usuario_,spinnerHoras.getSelectedItem().toString().trim());
                    async_get_clases.execute();
                }else {
                    item = spinnerEdificios.getSelectedItem().toString();
                    hora=spinnerHoras.getSelectedItem().toString();
                    Async_get_Clases async_get_clases = new Async_get_Clases(item,hora);
                    async_get_clases.execute();
                    }

                }catch (Exception ex){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public AlertDialog DialogInsertCount(final int id_clase, final int inscritos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.ingreso_asistencia, null);

        builder.setView(v);
        alertDialog=builder.create();
        txtAsistencia  = v.findViewById(R.id.txtAsistencia);
        final RadioGroup radioGroup = v.findViewById(R.id.radioGroup);
           radioButton1=v.findViewById(R.id.radio1);
           radioButton2=v.findViewById(R.id.radio2);
           radioButton3=v.findViewById(R.id.radio3);
           radioButton4=v.findViewById(R.id.radio4);
           radioButton5=v.findViewById(R.id.radio5);
           radioButton6=v.findViewById(R.id.radio6);
           radioButton7=v.findViewById(R.id.radio7);
           radioButton8=v.findViewById(R.id.radio8);
           try {

               radioButton1.setText(faltas.get(0).toString());
               radioButton2.setText(faltas.get(1).toString());
               radioButton3.setText(faltas.get(2).toString());
               radioButton4.setText(faltas.get(3).toString());
               radioButton5.setText(faltas.get(4).toString());
               radioButton6.setText(faltas.get(5).toString());
               radioButton7.setText(faltas.get(6).toString());
               radioButton8.setText(faltas.get(7).toString());

           }catch (Exception ex){

           }

         final CheckBox checkBox =v.findViewById(R.id.checkboxSinAsistencia);
         Button btnCancelar=v.findViewById(R.id.btnCancel_alert);
          btnSave =v.findViewById(R.id.btncSave_alert);
        final  EditText txtComentario = v.findViewById(R.id.txtComentarios);
        final EditText editTextHora = v.findViewById(R.id.txtSelecHora);
        editTextHora.setInputType(InputType.TYPE_NULL);
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        if (minutes <= 9){
            editTextHora.setHint(hour + ":0" + minutes);
        }else{
            editTextHora.setHint(hour + ":" + minutes);
        }
        editTextHora.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                editTextHora.setError(null);
            }
        });

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setVisibility(View.GONE);
                editTextHora.setVisibility(View.GONE);
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setVisibility(View.GONE);
                editTextHora.setVisibility(View.GONE);

            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setVisibility(View.GONE);
                editTextHora.setVisibility(View.GONE);
            }
        });
        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setVisibility(View.GONE);
                editTextHora.setVisibility(View.GONE);
            }
        });
        radioButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setVisibility(View.GONE);
                editTextHora.setVisibility(View.VISIBLE);
            }
        });
        radioButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setVisibility(View.GONE);
                editTextHora.setVisibility(View.GONE);
            }
        });
        radioButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setVisibility(View.GONE);
                editTextHora.setVisibility(View.GONE);
            }
        });
        radioButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtComentario.setVisibility(View.VISIBLE);
                editTextHora.setVisibility(View.GONE);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    if (!checkBox.isChecked()) {
                        if (!txtAsistencia.getText().toString().trim().isEmpty()) {
                            int asitencia = Integer.parseInt(txtAsistencia.getText().toString());
                            if (asitencia <= 0) {
                                txtAsistencia.setError("Si no se contabilizo nada selecciones la opcion Sin Asistencia");
                            } else if (asitencia > inscritos) {
                                txtAsistencia.setError("La asistencia debe ser menor o igual a la cantidad de inscrito");
                            } else {
                                if (btnSave.getText().equals("MODIFICAR") || btnSave.getText().equals("Modificar")) {
                                    if (nivel_ != 4) {
                                        Async_Conteo_Update async_conteo_update = new Async_Conteo_Update(ID_ASISTENCIA, asitencia, usuario_, spinnerEdificios.getSelectedItem().toString(), spinnerHoras.getSelectedItem().toString());
                                        async_conteo_update.execute();
                                    } else {
                                        Async_Conteo_Update async_conteo_update = new Async_Conteo_Update(ID_ASISTENCIA, asitencia, usuario_, "", spinnerHoras.getSelectedItem().toString());
                                        async_conteo_update.execute();
                                    }
                                } else {


                                    if (nivel_ != 4) {
                                        Async_Conteo_set async_conteo_set = new Async_Conteo_set(id_clase, asitencia, usuario_, spinnerEdificios.getSelectedItem().toString(), spinnerHoras.getSelectedItem().toString());
                                        async_conteo_set.execute();
                                        alertDialog.dismiss();
                                    } else {
                                        Async_Conteo_set async_conteo_set = new Async_Conteo_set(id_clase, asitencia, usuario_,"", spinnerHoras.getSelectedItem().toString());
                                        async_conteo_set.execute();
                                        alertDialog.dismiss();
                                    }

                                }
                            }
                        } else
                            txtAsistencia.setError("Ingrese Asitencia o seleccione Sin Asistencia");
                    } else {
                        Async_Falta_set async_falta_set;
                        if (!(radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked() || radioButton4.isChecked() ||
                                radioButton5.isChecked() || radioButton6.isChecked() || radioButton7.isChecked() || radioButton8.isChecked())) {
                            Toast.makeText(getContext(), "Seleccione una Opcion", Toast.LENGTH_SHORT).show();
                        } else {
                            String detall = null;
                            if (radioButton1.isChecked()) {
                                detall = radioButton1.getText().toString().trim();
                            } else if (radioButton2.isChecked()) {
                                detall = radioButton2.getText().toString().trim();
                            } else if (radioButton3.isChecked()) {
                                detall = radioButton3.getText().toString().trim();
                            } else if (radioButton4.isChecked()) {
                                detall = radioButton4.getText().toString().trim();
                            } else if (radioButton6.isChecked()) {
                                detall = radioButton6.getText().toString().trim();
                            } else if (radioButton7.isChecked()) {
                                detall = radioButton7.getText().toString().trim();
                            }
                            if (radioButton5.isChecked()) {
                                if (editTextHora.getText().toString().trim().isEmpty()) {
                                    editTextHora.setError("Seleccione La Hora");
                                } else {
                                    async_falta_set = new Async_Falta_set(id_clase, usuario_, "Falta", radioButton5.getText().toString().trim(), editTextHora.getText().toString().trim());
                                    async_falta_set.execute();
                                }
                            } else if (radioButton8.isChecked()) {
                                if (txtComentario.getText().toString().trim().isEmpty()) {
                                    txtComentario.setError("Requerido");
                                } else {
                                    async_falta_set = new Async_Falta_set(id_clase, usuario_, txtComentario.getText().toString().trim(), radioButton8.getText().toString().trim(), "");
                                    async_falta_set.execute();
                                    alertDialog.dismiss();
                                }
                            } else {
                                async_falta_set = new Async_Falta_set(id_clase, usuario_, "Falta", detall, "");
                                async_falta_set.execute();
                                alertDialog.dismiss();
                            }
                        }

                    }


                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    radioGroup.setVisibility(View.VISIBLE);
                    txtAsistencia.setVisibility(View.GONE);
                }else{
                    radioGroup.setVisibility(View.GONE);
                    txtComentario.setVisibility(View.GONE);
                    editTextHora.setVisibility(View.GONE);
                    txtAsistencia.setVisibility(View.VISIBLE);
                }
            }
        });

        editTextHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if (sMinute <= 9){
                                    editTextHora.setText(sHour + ":0" + sMinute);
                                }else
                                    editTextHora.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });


        return alertDialog ;
    }
    public class Async_get_edif extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            if (nivel_!=4){
            edificios.Get_Edificios_Dias_fromServer(usuario_, nivel_);
            }
            clases.Get_Horas_Clases_fromServer(nivel_,usuario_);
            clases.Get_Faltas_fromServer();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            try {
                if (nivel_!=4){
                    final ArrayAdapter spinnerAdapterEdificio = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, edificios.getNOMBRE());
                    spinnerEdificios.setAdapter(spinnerAdapterEdificio);
                }
                final ArrayAdapter spinnerAdapterHoras = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,clases.getHORAS_CLASES());
                spinnerHoras.setAdapter(spinnerAdapterHoras);
            }catch (Exception ex){}
        }
    }
    public class Async_get_Clases extends AsyncTask<Void,Void,Boolean> {
        String edifio,hora;
        public  Async_get_Clases(String edifio, String hora){

            this.edifio=edifio;
            this.hora= hora;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            if (nivel_==4){
                clases.Get_Clases_Docente_FromServer(usuario_,hora);
            }else {
                clases.Get_Clases_FromServer(edifio, hora,usuario_,nivel_);
            }
                clases.Get_All_Conteo(hora);
                clases.Get_Faltas_fromServer();
            try {
                Thread.sleep(2000);
            }catch (Exception ex){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                recyclerView = getView().findViewById(R.id.recyclerView);
                layoutManager = new LinearLayoutManager(getContext());

                myAdapter = new ConteosAdapter(clases.getID_CLASES(),clases.getINCRITOS(),clases.getMATERIAS(), clases.getAULAS(), clases.getDOCENTE(),
                        clases.getHORA(), clases.getDIAS(), clases.getSECCION(),clases.getID_MATERIA_CONTRO(),clases.getCANTIDAD_CONTEO(), R.layout.card_view_conteo, new ConteosAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int id, int incritos, int Position) {
                        Async_Conteo_get async_conteo_get = new Async_Conteo_get(id);
                        async_conteo_get.execute();
                        DialogInsertCount(id,incritos).show();
                    }
                });
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(layoutManager);
                faltas = clases.getFALTAS();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
            catch (Exception ex){
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        }
    }
    private AlertDialog AlertConteoExist(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setMessage("Esta Clase ya Fue Contada Desea Modificarlo?")
                .setTitle("Advertencia");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }
        });
// Set other dialog properties
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;
    }
    private AlertDialog AlertConteoDocente(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setMessage("No puede modificar la Asistencia, el Docente insertó los Datos")
                .setTitle("Advertencia").setCancelable(false);


        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }
        });
// Set other dialog properties
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;
    }
    public class Async_Conteo_get extends AsyncTask<Void,Void,Boolean> {
        int id ;

        public Async_Conteo_get(int id){
            this.id=id;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            clases.Get_conteo_fromServer(id);
            flag =false;

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                if (clases.getCantida() !=0){
                    txtAsistencia.setText(""+clases.getCantida());
                    ID_ASISTENCIA = clases.getId_conteo();
                    if (!txtAsistencia.getText().toString().trim().isEmpty()){
                        btnSave.setText("Modificar");
                        if (clases.getNivel().equals("DOCENTE") && nivel_!=4){
                            AlertConteoDocente().show();
                        }else {
                            AlertConteoExist().show();
                        }
                    }
                }

            }catch (Exception ex){
                Log.d("e",ex.getMessage());
            }
            flag = true;
        }
    }
    public class Async_Conteo_Update extends AsyncTask<Void,Void,Boolean> {
        boolean resp;
        int id_Asistencia,NuevaCantidad;
        String nuevo_usuario;
        String usuario,edif,hora;
        public Async_Conteo_Update(int id_Asistencia ,int nuevaCantidad, String nuevo_usuario , String edif , String Hora){
            this.id_Asistencia= id_Asistencia;
            this.nuevo_usuario = nuevo_usuario;
            this.NuevaCantidad = nuevaCantidad;
            this.edif = edif;
            this.hora = Hora;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            resp =clases.Update_conteo(id_Asistencia,nuevo_usuario, NuevaCantidad);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (resp){
                Toast.makeText(getContext(),"Se Actualizó el conteo",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }else {
                Toast.makeText(getContext(),"No se Actualizó el conteo",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
            Async_get_Clases async_get_clases = new Async_get_Clases(edif,hora);
            async_get_clases.execute();
        }
    }
    public class Async_Falta_set extends AsyncTask<Void,Void,Boolean> {
        int id_clase;
        String usuario,detalle,comentario,hora;
        boolean resp;
        public Async_Falta_set(int id_clase,String usuario, String comentario, String detalle ,String hora){
            this.comentario=comentario;
            this.detalle= detalle;
            this.hora = hora;
            this.usuario = usuario;
            this.id_clase= id_clase;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
           resp= clases.Set_Falta(id_clase,usuario,detalle,comentario,hora);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (resp){
                Toast.makeText(getContext(),"Se insertó la Inasistencia",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(),"No se insertó la Inasistencia",Toast.LENGTH_SHORT).show();
            }
            Async_get_Clases async_get_clases = new Async_get_Clases(spinnerEdificios.getSelectedItem().toString(),spinnerHoras.getSelectedItem().toString());
            async_get_clases.execute();
        }
    }
    public class Async_Conteo_set extends AsyncTask<Void,Void,Boolean> {

        int id_clase , cantidad;
        String usuario,edif,hora;
        boolean resp;
        public  Async_Conteo_set(int id_clase , int cantidad ,String usuario, String edificio ,String hora){
            this.cantidad=cantidad;
            this.id_clase=id_clase;
            this.usuario=usuario;
            this.hora = hora;
            this.edif= edificio;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            resp =clases.Set_conteo(id_clase,usuario,cantidad);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Async_get_Clases async_get_clases = new Async_get_Clases(edif,hora);
            async_get_clases.execute();
            if (resp){
                Toast.makeText(getContext(),"Se insertó el conteo",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(),"No se insertó el conteo",Toast.LENGTH_SHORT).show();
            }

        }
    }





}
