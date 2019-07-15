package admin.lab.app.utec.com.conteos.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import admin.lab.app.utec.com.conteos.Adapters.ConteosAdapter;
import admin.lab.app.utec.com.conteos.Models.Clases;
import admin.lab.app.utec.com.conteos.Models.Facultades;
import admin.lab.app.utec.com.conteos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerClasesFragment extends Fragment {


    private Calendar calendar = Calendar.getInstance();
    private Spinner spFacultad,spCiclo;
    private EditText editTextAnio;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public VerClasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_clases, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spFacultad = getView().findViewById(R.id.spinnerFacultadFiltroMaterias);
        spCiclo =  getView().findViewById(R.id.spinnerCicloMateria);
        editTextAnio= getView().findViewById(R.id.txtAnio);
        AsyncGetFacultades asyncGetFacultades = new AsyncGetFacultades();
        asyncGetFacultades.execute();

        int anio =  calendar.get(Calendar.YEAR);

            editTextAnio.setText(anio+"");



        spCiclo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String anio= editTextAnio.getText().toString().trim();
                    String facultad = spFacultad.getSelectedItem().toString();
                    int ciclo = Integer.parseInt(spCiclo.getSelectedItem().toString());
                    AsyncGetClases asyncGetClases = new AsyncGetClases(anio,facultad,ciclo);
                    asyncGetClases.execute();

                }catch (Exception ex){
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        spFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String anio= editTextAnio.getText().toString().trim();
                    String facultad = spFacultad.getSelectedItem().toString();
                    int ciclo = Integer.parseInt(spCiclo.getSelectedItem().toString());
                    AsyncGetClases asyncGetClases = new AsyncGetClases(anio,facultad,ciclo);
                    asyncGetClases.execute();

                }catch (Exception ex){
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editTextAnio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //
                try {
                    String anio= editTextAnio.getText().toString().trim();
                    String facultad = spFacultad.getSelectedItem().toString();
                    int ciclo = Integer.parseInt(spCiclo.getSelectedItem().toString());
                    AsyncGetClases asyncGetClases = new AsyncGetClases(anio,facultad,ciclo);
                    asyncGetClases.execute();

                }catch (Exception ex){
                }
            }
        });
    }

    public class AsyncGetFacultades extends AsyncTask<Void,Void,Boolean>{
        Facultades facultades = new Facultades();
        @Override
        protected Boolean doInBackground(Void... voids) {
            facultades.getFacultadesFromServer();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            try {
                final ArrayAdapter spinnerAdapterFacultades = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, facultades.getFacultad());
                spFacultad.setAdapter(spinnerAdapterFacultades);
            }catch (Exception ex){}

        }
    }
    public class AsyncGetClases extends AsyncTask<Void,Void,Boolean>{

        Clases clases = new Clases();
        String anio,facultad;
        int ciclo;
        public  AsyncGetClases(String anio ,String facultad, int Ciclo){
            this.anio= anio;
            this.ciclo = Ciclo;
            this.facultad = facultad;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {

            clases.Get_Clases__Anteriores_FromServer(facultad,anio,ciclo);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                recyclerView = getView().findViewById(R.id.recyclerViewMaterias);
                layoutManager = new LinearLayoutManager(getContext());

                myAdapter = new ConteosAdapter(clases.getID_CLASES(),clases.getINCRITOS(),clases.getMATERIAS(), clases.getAULAS(), clases.getDOCENTE(),
                        clases.getHORA(), clases.getDIAS(), clases.getSECCION(),clases.getID_MATERIA_CONTRO(),clases.getCANTIDAD_CONTEO(),clases.getID_MATERIA_FALTA(),clases.getDETALLE(), R.layout.card_view_conteo, new ConteosAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int id, int incritos, int Position) {
                   }
                });
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(layoutManager);

            }
            catch (Exception ex){

            }
        }
    }
}
