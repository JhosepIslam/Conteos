package admin.lab.app.utec.com.conteos.Fragments;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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
import android.widget.Spinner;
import android.widget.Toast;

import admin.lab.app.utec.com.conteos.Adapters.EdificiosAdapter;
import admin.lab.app.utec.com.conteos.Adapters.LabsAdapter;
import admin.lab.app.utec.com.conteos.Models.Edificios;
import admin.lab.app.utec.com.conteos.Models.Laboratorios;
import admin.lab.app.utec.com.conteos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaboratoriosFragment extends Fragment {
    Laboratorios laboratorios = new Laboratorios();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private Spinner spinnerEdificio;
    private FloatingActionButton floatingActionButton;
    private RecyclerView.LayoutManager layoutManager;
    private String usuario_;
    private String nivel_;

    public LaboratoriosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laboratorios, container, false);
        try {
            usuario_= getArguments().getString("usuario");
            nivel_= getArguments().getString("nivel");
        }catch (Exception ex){
            usuario_="";
        }
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton = getView().findViewById(R.id.fabAddLAb);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertAdd().show();
            }
        });
        Async async = new Async();
        async.execute();
    }

    private AlertDialog Alert(final int id, String nombre, final String Edificio, String Abrevitura, final int Capacidad){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.alert_dialog_edit_lab,null);
        builder.setView(v);
        final AlertDialog alertDialog =builder.create();


        final LinearLayout textInputLayout = v.findViewById(R.id.textInput);
        CheckBox checkBox = v.findViewById(R.id.checkboxEditarEdif);
        final EditText editTextNombre = v.findViewById(R.id.txtNombreLab);
        final EditText editTextCapacidad = v.findViewById(R.id.txtCapacidad);
        final EditText txtEdificioLab = v.findViewById(R.id.txtEdificioLab);
        final EditText editTextAbreviatura = v.findViewById(R.id.txtAbreviatura);

        editTextAbreviatura.setText(Abrevitura);
        editTextCapacidad.setText(Capacidad+"");
        editTextNombre.setText(nombre);
        txtEdificioLab.setText(Edificio);
        spinnerEdificio = v.findViewById(R.id.spinnerEdificio);
        AsyncOpen asyncOpen = new AsyncOpen();
        asyncOpen.execute();
        txtEdificioLab.setInputType(InputType.TYPE_NULL);
        Button btnEnviar = v.findViewById(R.id.btnSaveED);
        Button btnCancelar = v.findViewById(R.id.btnCancelED);
        spinnerEdificio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtEdificioLab.setText(spinnerEdificio.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    textInputLayout.setVisibility(View.VISIBLE);
                }else {
                    textInputLayout.setVisibility(View.GONE);
                    txtEdificioLab.setText(Edificio);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNombre.getText().toString().trim().isEmpty()){
                    editTextNombre.setError("Campo Obligatorio");
                }else if (editTextAbreviatura.getText().toString().trim().isEmpty()){
                    editTextAbreviatura.setError("Campo Obligatorio");
                }else if (editTextCapacidad.getText().toString().trim().isEmpty()){
                    editTextCapacidad.setError("Campo Obligatorio");
                }else if(txtEdificioLab.getText().toString().trim().isEmpty()){
                    txtEdificioLab.setError("Campo Obligatorio");
                } else {

                    String Nombre, Abrev,Edif;
                    int Capacida;
                    Capacida = Integer.parseInt(editTextCapacidad.getText().toString().trim());
                    Nombre =editTextNombre.getText().toString().trim();
                    Abrev=editTextAbreviatura.getText().toString().trim();
                    Edif = txtEdificioLab.getText().toString().trim();

                    AsyncUpdate asyncUpdate = new AsyncUpdate(Nombre,Abrev,Edif,Capacida,id);
                    asyncUpdate.execute();
                    alertDialog.dismiss();
                }

            }
        });

        return alertDialog ;
    }

    private AlertDialog AlertAdd(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.alert_dialog_add_lab,null);
        builder.setView(v);
        final AlertDialog alertDialog =builder.create();

        spinnerEdificio = v.findViewById(R.id.spinnerEdificioadd);
        AsyncOpen asyncOpen = new AsyncOpen();
        asyncOpen.execute();
        Button btnEnviar = v.findViewById(R.id.btnSaveED);
        Button btnCancelar = v.findViewById(R.id.btnCancelED);
        final EditText editTextNombreadd = v.findViewById(R.id.txtNombreLab);
        final EditText editTextCapacidadadd = v.findViewById(R.id.txtCapacidad);
        final EditText editTextAbreviaturaadd= v.findViewById(R.id.txtAbreviatura);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNombreadd.getText().toString().trim().isEmpty()){
                    editTextNombreadd.setError("Campo Requerido");
                }else if (editTextAbreviaturaadd.getText().toString().trim().isEmpty()){
                    editTextAbreviaturaadd.setError("Campo Requerido");
                }else if (editTextCapacidadadd.getText().toString().trim().isEmpty()){
                    editTextCapacidadadd.setError("Campo Requerido");
                }else {
                    try {
                            String edif = spinnerEdificio.getSelectedItem().toString();
                            String nombre = editTextNombreadd.getText().toString().trim();
                            String abrev = editTextAbreviaturaadd.getText().toString().trim();
                            int capacidad = Integer.parseInt(editTextCapacidadadd.getText().toString().trim());
                        AsyncAdd asyncAdd = new AsyncAdd(nombre,abrev,edif,capacidad);
                        asyncAdd.execute();
                    }catch (Exception e){}
                }
                alertDialog.dismiss();
            }
        });

        return alertDialog ;
    }
    public class Async extends AsyncTask<Void,Void,Boolean> {


        @Override
        protected Boolean doInBackground(Void... voids) {

           laboratorios.getLaboratorioFromServerCompleto();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                recyclerView = getView().findViewById(R.id.recyclerViewLaboratorios);
                layoutManager = new LinearLayoutManager(getContext());
                myAdapter = new LabsAdapter(laboratorios.getLaboratorio(), laboratorios.getEdificio(), laboratorios.getCapaciad(),
                        laboratorios.getAbreviatura(), laboratorios.getID(), R.layout.card_view_labs, new LabsAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int id, String Nombre, int Capacidad, String Edificio, String Abreviatura) {
                        Alert(id,Nombre,Edificio,Abreviatura,Capacidad).show();
                    }
                });
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(layoutManager);


            }
            catch (Exception ex){
            }
        }

    }

    public class AsyncOpen extends AsyncTask<Void,Void,Boolean> {
        Edificios edificios = new Edificios();

        @Override
        protected Boolean doInBackground(Void... voids) {
            edificios.getEdificioFromServer();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            final ArrayAdapter spinnerAdapterEdificio = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, edificios.getNOMBRE());
            spinnerEdificio.setAdapter(spinnerAdapterEdificio);
        }
    }
    public class AsyncUpdate extends AsyncTask<Void,Void,Boolean>{
        String Nombre, Abrev,Edif;
        int ID,Capacida;
        public AsyncUpdate(String Nombre, String Abrev , String Edif, int Capacidad, int ID){
            this.Abrev= Abrev;
            this.Capacida = Capacidad;
            this.Edif = Edif;
            this.ID = ID;
            this.Nombre = Nombre;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {

            return laboratorios.Update(ID,usuario_,Nombre,Capacida,Abrev,Edif);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                Toast.makeText(getContext(),"Actualizado",Toast.LENGTH_SHORT).show();
            }
            Async async = new Async();
            async.execute();
        }
    }
    public class AsyncAdd extends AsyncTask<Void,Void,Boolean>{
        private String Nombre,Abrev,Edificio;
        private int Capacidad;
        public  AsyncAdd(String Nombre, String Abrev, String Edificio, int Capacidad){
            this.Abrev = Abrev;
            this.Capacidad = Capacidad;
            this.Edificio =Edificio;
            this.Nombre = Nombre;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            return laboratorios.Add(usuario_,Nombre,Capacidad,Abrev,Edificio);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                Toast.makeText(getContext(),"Se inserto en Laboratorio",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(),"NO se inserto en Laboratorio",Toast.LENGTH_SHORT).show();
            }
            Async async = new Async();
            async.execute();

        }
    }
}
