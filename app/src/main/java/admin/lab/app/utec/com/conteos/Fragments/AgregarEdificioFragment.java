package admin.lab.app.utec.com.conteos.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.Adapters.EdificiosAdapter;
import admin.lab.app.utec.com.conteos.Models.Edificios;
import admin.lab.app.utec.com.conteos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarEdificioFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    AsyncGet asyncGet = new AsyncGet();
    final Edificios edificios = new Edificios();


    FloatingActionButton floatingActionButton ;
    private String usuario_;
    private String nivel_;
    private ShimmerFrameLayout shimmerFrameLayout;

    public AgregarEdificioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_edifcio, container, false);
        // Inflate the layout for this fragment
        usuario_= getArguments().getString("usuario");
        nivel_= getArguments().getString("nivel");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton = getView().findViewById(R.id.fabAdd);
        shimmerFrameLayout = getView().findViewById(R.id.shimmerLayout);
        asyncGet.execute();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert().show();
            }
        });
    }
    private AlertDialog Alert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.agregar_edifcio_layout,null);
        builder.setView(v);
        final AlertDialog alertDialog =builder.create();


        final EditText editTextNombre = v.findViewById(R.id.txtSetNombreEdif);
        final EditText editTextPisos = v.findViewById(R.id.txtSetNumPlantasEdif);
        final EditText editTextAulas = v.findViewById(R.id.txtSetNumAulasEdif);

        Button btnEnviar = v.findViewById(R.id.btnSaveED);
        Button btnCancelar = v.findViewById(R.id.btnCancelED);

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
                }else if (editTextPisos.getText().toString().trim().isEmpty())
                {
                    editTextPisos.setError("Campo Obligatorio");
                }else if (editTextAulas.getText().toString().trim().isEmpty()){
                    editTextAulas.setError("Campo Obligatorio");
                }
                else {
                    String nombre = editTextNombre.getText().toString().trim();
                    int pisos = Integer.parseInt(editTextPisos.getText().toString().trim());
                    int aulas = Integer.parseInt(editTextAulas.getText().toString().trim());
                    AsyncSet asyncSet = new AsyncSet(nombre,pisos,aulas);
                    asyncSet.execute();
                    alertDialog.dismiss();
                }
            }
        });

            return alertDialog ;
    }
    private AlertDialog AlertModificar(final int ID , String nombre, int Aulas , int Pisos){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.editar_edificio_alert_layout, null);
        builder.setView(v);
        final AlertDialog alertDialog =builder.create();
        TextView textViewNombre = v.findViewById(R.id.textViewNombreEdificio_dialog);
        TextView textViewAulas = v.findViewById(R.id.textViewAulasEdificio_dialog);
        TextView textViewPlantas = v.findViewById(R.id.textViewPlantasEdificio_dialog);
        textViewNombre.setText(nombre);
        textViewAulas.setText("Aulas: "+Aulas);
        textViewPlantas.setText("Plantas: "+Pisos);

        final LinearLayout linearLayoutNombre = v.findViewById(R.id.LLEditNombreEdificio);
        final LinearLayout linearLayoutAulas = v.findViewById(R.id.LLEditAulasEdificio);
        final LinearLayout linearLayoutPlantas = v.findViewById(R.id.LLEditPlantasEdificio);

        final CheckBox checkBoxNombre = v.findViewById(R.id.checkboxEditNombreEdificios_dialog);
        final CheckBox checkBoxAulas = v.findViewById(R.id.checkboxEditAulasEdificios_dialog);
        final CheckBox checkBoxPlantas = v.findViewById(R.id.checkboxEditPlantasEdificios_dialog);

        final EditText editTextNombre = v.findViewById(R.id.txtNuevoNombreEdificio_Edit);
        final EditText editTextPisos = v.findViewById(R.id.txtNuevasPlantas_Edit);
        final EditText editTextAulas = v.findViewById(R.id.txtNuevasAulasEdificio_Edit);

        checkBoxNombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    linearLayoutNombre.setVisibility(View.VISIBLE);
                }else {
                    linearLayoutNombre.setVisibility(View.GONE);
                }
            }
        });
        checkBoxPlantas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    linearLayoutPlantas.setVisibility(View.VISIBLE);
                }else {
                    linearLayoutPlantas.setVisibility(View.GONE);
                }
            }
        });
        checkBoxAulas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    linearLayoutAulas.setVisibility(View.VISIBLE);
                }else {
                    linearLayoutAulas.setVisibility(View.GONE);
                }
            }
        });
        Button btnEnviar = v.findViewById(R.id.btnEnviarEdificio_dialog);
        Button btnCancelar = v.findViewById(R.id.btnCacelarEdificio_dialog);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int caso;
                String Nombre;
                int Aulas,Pisos;

                if (checkBoxAulas.isChecked() && checkBoxNombre.isChecked() && checkBoxPlantas.isChecked()){
                    if (editTextNombre.getText().toString().trim().isEmpty()){
                        editTextNombre.setError("Comple este Campo");
                    }else if (editTextAulas.getText().toString().trim().isEmpty()){
                        editTextAulas.setError("Comple este Campo");
                    }else if (editTextPisos.getText().toString().trim().isEmpty()){
                        editTextPisos.setError("Comple este Campo");
                    }else {
                        Pisos=Integer.parseInt(editTextPisos.getText().toString().trim());
                        Aulas =Integer.parseInt(editTextAulas.getText().toString().trim());
                        Nombre =editTextNombre.getText().toString().trim();
                        caso=0;
                        AsyncUpdate asyncUpdate = new AsyncUpdate(Nombre,Aulas,Pisos,ID,caso);
                        asyncUpdate.execute();
                        alertDialog.dismiss();

                    }
                }else if (checkBoxAulas.isChecked() &&  checkBoxNombre.isChecked()){

                    if (editTextNombre.getText().toString().trim().isEmpty()){
                        editTextNombre.setError("Comple este Campo");
                    }else if (editTextAulas.getText().toString().trim().isEmpty()){
                        editTextAulas.setError("Comple este Campo");

                    }else {

                        Aulas =Integer.parseInt(editTextAulas.getText().toString().trim());
                        Nombre =editTextNombre.getText().toString().trim();
                        caso=1;
                        AsyncUpdate asyncUpdate = new AsyncUpdate(Nombre,Aulas,0,ID,caso);
                        asyncUpdate.execute();
                        alertDialog.dismiss();

                    }

                }else  if (checkBoxAulas.isChecked() && checkBoxPlantas.isChecked()){

                     if (editTextAulas.getText().toString().trim().isEmpty()){
                        editTextAulas.setError("Comple este Campo");
                    }else if (editTextPisos.getText().toString().trim().isEmpty()){
                        editTextPisos.setError("Comple este Campo");
                    }else {

                        Pisos=Integer.parseInt(editTextPisos.getText().toString().trim());
                        Aulas =Integer.parseInt(editTextAulas.getText().toString().trim());
                        caso=2;
                         AsyncUpdate asyncUpdate = new AsyncUpdate(null,Aulas,Pisos,ID,caso);
                         asyncUpdate.execute();
                         alertDialog.dismiss();
                    }

                }else if (checkBoxPlantas.isChecked() && checkBoxNombre.isChecked()){
                    if (editTextNombre.getText().toString().trim().isEmpty()){
                        editTextNombre.setError("Comple este Campo");
                    }else if (editTextPisos.getText().toString().trim().isEmpty()){
                        editTextPisos.setError("Comple este Campo");
                    }else {
                        Pisos=Integer.parseInt(editTextPisos.getText().toString().trim());
                        Nombre =editTextNombre.getText().toString().trim();
                        caso=3;
                        AsyncUpdate asyncUpdate = new AsyncUpdate(Nombre,0,Pisos,ID,caso);
                        asyncUpdate.execute();
                        alertDialog.dismiss();

                    }

                }else if (checkBoxAulas.isChecked()){

                    if (editTextAulas.getText().toString().trim().isEmpty()) {
                        editTextAulas.setError("Comple este Campo");
                    }else {
                        Aulas =Integer.parseInt(editTextAulas.getText().toString().trim());
                        caso=4;
                        AsyncUpdate asyncUpdate = new AsyncUpdate(null,Aulas,0,ID,caso);
                        asyncUpdate.execute();
                        alertDialog.dismiss();

                    }

                }else if (checkBoxNombre.isChecked()){
                    if (editTextNombre.getText().toString().trim().isEmpty()){
                        editTextNombre.setError("Comple este Campo");
                    }else {
                        Nombre =editTextNombre.getText().toString().trim();
                        caso=5;
                        AsyncUpdate asyncUpdate = new AsyncUpdate(Nombre,0,0,ID,caso);
                        asyncUpdate.execute();
                        alertDialog.dismiss();
                    }

                }else if (checkBoxPlantas.isChecked()){

                     if (editTextPisos.getText().toString().trim().isEmpty()){
                        editTextPisos.setError("Comple este Campo");
                    }else {
                        Pisos=Integer.parseInt(editTextPisos.getText().toString().trim());
                        caso=6;
                         AsyncUpdate asyncUpdate = new AsyncUpdate(null,0,Pisos,ID,caso);
                         asyncUpdate.execute();
                         alertDialog.dismiss();

                    }
                }

            }

        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });


        return alertDialog ;
    }
    public class AsyncSet extends  AsyncTask<Void,Void,Boolean>{

        ProgressDialog pDialog;
         int result;
        String nombre;
        int num_p,num_au;
        public AsyncSet(String nombre , int num_p, int num_au){
            this.nombre=nombre;
            this.num_p = num_p;
            this.num_au = num_au;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Cargando Lista");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            result =edificios.setEdificio(nombre,num_p,num_au);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            try {
                super.onPostExecute(aBoolean);
                if (result != 0 && result != -1) {
                    AsyncGet asyncGet = new AsyncGet();
                    asyncGet.execute();
                    Toast.makeText(getContext(), "Se insertó un nuevo Edificio", Toast.LENGTH_LONG).show();
                    layoutManager.scrollToPosition(result);
                }

            }
            catch (Exception ex)
            {
                AlertConexion().show();
            }
            pDialog.dismiss();
        }
    }
    public class AsyncGet extends AsyncTask<Void,Void,Boolean>{
        ProgressDialog pDialog;
        @Override
        protected Boolean doInBackground(Void... voids) {
            edificios.getEdificioFromServer();
            try {
                Thread.sleep(2000);

            } catch(InterruptedException e) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            shimmerFrameLayout.startShimmer();

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                recyclerView = getView().findViewById(R.id.recyclerViewEdificio);
                layoutManager = new LinearLayoutManager(getContext());
                myAdapter = new EdificiosAdapter(edificios.getNOMBRE(), edificios.getNUM_PLANTAS()
                        , edificios.getNUM_AULAS(), edificios.getID(), R.layout.card_view_edificios, new EdificiosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int id, String nombre, int Plantas, int Aulas, int position) {
                        AlertModificar(id,nombre,Aulas,Plantas).show();
                    }
                });
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
            catch (Exception ex){
                try {
                    AlertConexion().show();
                }catch (Exception e){

                }
            }
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);

        }
    }

    public class AsyncUpdate extends AsyncTask<Void,Void,Boolean>{

        String Nombre;
        int Aulas,Pisos,ID,caso;
        ProgressDialog pDialog;

        public AsyncUpdate(String Nombre, int Aulas , int Pisos, int ID, int Caso){
            this.Aulas=Aulas;
            this.ID = ID;
            this.Pisos = Pisos;
            this.Nombre = Nombre;
            this.caso = Caso;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Actualizando");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            switch (caso){
                case 0:
                    edificios.UpdateAulas(Aulas,usuario_,ID);
                    edificios.UpdateNombre(Nombre,usuario_,ID);
                    edificios.UpdatePisos(Pisos,usuario_,ID);
                    break;
                case 1:
                    edificios.UpdateAulas(Aulas,usuario_,ID);
                    edificios.UpdateNombre(Nombre,usuario_,ID);
                    break;
                case 2:
                    edificios.UpdateAulas(Aulas,usuario_,ID);
                    edificios.UpdatePisos(Pisos,usuario_,ID);
                    break;
                case 3:
                    edificios.UpdateNombre(Nombre,usuario_,ID);
                    edificios.UpdatePisos(Pisos,usuario_,ID);
                    break;
                case 4:
                    edificios.UpdateAulas(Aulas,usuario_,ID);

                    break;
                case 5:
                    edificios.UpdateNombre(Nombre,usuario_,ID);

                    break;
                case 6:
                    edificios.UpdatePisos(Pisos,usuario_,ID);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            pDialog.dismiss();
            AsyncGet asyncGet = new AsyncGet();
            asyncGet.execute();
        }
    }

    private AlertDialog AlertConexion(){
        try {
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

        }catch (Exception ex){
            return null;
        }

    }
}
