package admin.lab.app.utec.com.conteos.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import admin.lab.app.utec.com.conteos.Adapters.ClasesInfoAdapter;
import admin.lab.app.utec.com.conteos.Adapters.ConteosAdapter;
import admin.lab.app.utec.com.conteos.Models.Clases;
import admin.lab.app.utec.com.conteos.Models.Facultades;
import admin.lab.app.utec.com.conteos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ver_Info_ConteosFragment extends Fragment {

    Facultades facultades = new Facultades();
    private Spinner SpFacultad,SpTipo;
    Clases clases = new Clases();
    private LinearLayout LLFacultad;
    EditText editTextFiltroHora, editTextFiltroFecha;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH)+1;
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private int nivel_;
    private String usuario_;

    public Ver_Info_ConteosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        usuario_= getArguments().getString("usuario");
        nivel_= Integer.parseInt(getArguments().getString("nivel"));
        return inflater.inflate(R.layout.fragment_ver__info__conteos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SpFacultad = getView().findViewById(R.id.spinnerFacultadFiltro);
        Async_get_Facultades async_get_facultades = new Async_get_Facultades();
        async_get_facultades.execute();
        SpTipo = getView().findViewById(R.id.spinnerTipoFiltro);
        editTextFiltroHora= getView().findViewById(R.id.txtFiltroHora);
        editTextFiltroFecha= getView().findViewById(R.id.txtFiltroFecha);
        LLFacultad= getView().findViewById(R.id.LLFacultad);
        if (nivel_ == 3){
            LLFacultad.setVisibility(View.GONE);
        }
        editTextFiltroHora.setInputType(InputType.TYPE_NULL);
        editTextFiltroFecha.setInputType(InputType.TYPE_NULL);
        editTextFiltroFecha.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editTextFiltroHora.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editTextFiltroHora.setText("6:30");
        if (dia <= 9 &&  mes <=9) {
            editTextFiltroFecha.setText( "0" +dia + "-0" + mes+"-"+anio);
        } else if (dia <= 9 ){
            editTextFiltroFecha.setText( "-0" +dia + "-" + mes+"-"+anio);
        }else if (mes <=9){
            editTextFiltroFecha.setText( dia + "-0" + mes+"-"+anio);
        }else {
            editTextFiltroFecha.setText( dia + "-" + mes+"-"+anio);
        }



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int mes = monthOfYear+1;
                if (dayOfMonth <= 9 &&  monthOfYear <=9) {
                    editTextFiltroFecha.setText( "0" +dayOfMonth + "-0" + mes+"-"+year);
                 } else if (dayOfMonth <= 9 ){
                   editTextFiltroFecha.setText( "-0" +dayOfMonth + "-" + mes+"-"+year);
                }else if (mes <=9){
                    editTextFiltroFecha.setText( dayOfMonth + "-0" + mes+"-"+year);
                }else {
                    editTextFiltroFecha.setText( dayOfMonth + "-" + mes+"-"+year);
                }

            }

        };

        editTextFiltroFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(getContext(), date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        editTextFiltroHora.setOnClickListener(new View.OnClickListener() {
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
                                    editTextFiltroHora.setText(sHour + ":0" + sMinute);
                                } else
                                    editTextFiltroHora.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        SpTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Buscar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Buscar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editTextFiltroHora.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Buscar();
            }
        });
        editTextFiltroFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Buscar();
            }
        });
    }

    private void Buscar(){
        try {
            String fecha = editTextFiltroFecha.getText().toString().trim();
            String hora = editTextFiltroHora.getText().toString().trim();
            String facultad= null;
            if (nivel_ !=3){
                 facultad= SpFacultad.getSelectedItem().toString().trim();
            }

            int tipo = SpTipo.getSelectedItemPosition();

            Async_get_Clases async_get_clases = new Async_get_Clases(tipo,fecha,facultad,hora);
            async_get_clases.execute();

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }



    }

    public class Async_get_Facultades extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            facultades.getFacultadesConIDFromServer();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                final ArrayAdapter spinnerAdapterFacultades = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, facultades.getFacultad());
                SpFacultad.setAdapter(spinnerAdapterFacultades);
            }catch (Exception e){}

        }
    }

    public class Async_get_Clases extends AsyncTask<Void,Void,Boolean> {
        int Tipo;
        String Fecha, Hora,Facultad;



        public Async_get_Clases (int Tipo, String Fecha, String Facultad , String Hora ){

            this.Tipo = Tipo;
            this.Facultad = Facultad;
            this.Fecha = Fecha;
            this.Hora = Hora;

        }
        @Override
        protected Boolean doInBackground(Void... voids) {

            switch (Tipo){


                case 0:
                    clases.Get_Clases_Contadas_FromServer(Fecha,Hora,Facultad,nivel_,usuario_);
                    break;
                case 1:
                    clases.Get_Clases_Sin_Contar_FromServer(Fecha,Hora,Facultad,nivel_,usuario_);
                    break;
                case 2:
                    clases.Get_Clases_Con_Falta_FromServer(Fecha,Hora,Facultad,nivel_,usuario_);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            try {
                recyclerView = getView().findViewById(R.id.recyclerViewClasesInfo);
                layoutManager = new LinearLayoutManager(getContext());

                myAdapter = new ClasesInfoAdapter(clases.getMATERIAS(), clases.getAULAS(), clases.getDOCENTE(), clases.getHORA(), clases.getDIAS(),
                        clases.getSECCION(), clases.getID_CLASES(), clases.getINCRITOS(), clases.getCANTIDAD_CONTEO(), R.layout.card_view_conteo, new ClasesInfoAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int id) {

                    }
                });
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(layoutManager);

            }catch (Exception ex){
                Log.d("Error",ex.getMessage());
            }



        }
    }

}
