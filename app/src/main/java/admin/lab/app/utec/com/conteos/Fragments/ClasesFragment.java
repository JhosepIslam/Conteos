package admin.lab.app.utec.com.conteos.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import admin.lab.app.utec.com.conteos.Models.Edificios;
import admin.lab.app.utec.com.conteos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClasesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinner;


    Edificios edificios = new Edificios();
    private List<String> Materia,Aula,Docente,Horas,Dias,Seccion;
    public ClasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clases, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        final Edificios edificios = new Edificios();

        Async async = new Async();
        async.execute();

    }
    public class Async extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            edificios.getEdificioFromServer();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try{
            final ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, edificios.getNOMBRE());
            spinner.setAdapter(spinnerAdapter);
            }
            catch (Exception ex){
                AlertConexion().show();
            }
        }
    }
    private AlertDialog AlertConexion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setMessage("Error de Conexion, Compruebe su conexion e intente de nuevo.")
                .setTitle("Error").setIcon(R.drawable.ic_wifi_check);

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog

            }
        });
// Set other dialog properties
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;

    }


    public AlertDialog DialogI() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();



        View v = inflater.inflate(R.layout.editar_aula_clase_alert_layout, null);


        builder.setView(v);
        final AlertDialog alertDialog =builder.create();

        Button btnEditar = v.findViewById(R.id.btn_edit_alert);
        Button btnCancealr = v.findViewById(R.id.btnCancel_alertEdit);

        btnCancealr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });





        return alertDialog ;
    }

}
