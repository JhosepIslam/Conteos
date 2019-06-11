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
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.Activities.LoginActivity;
import admin.lab.app.utec.com.conteos.Adapters.ParcialesAdapter;
import admin.lab.app.utec.com.conteos.Models.Parciales;
import admin.lab.app.utec.com.conteos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParcialesFragment extends Fragment {

    Parciales parciales = new Parciales();
    AlertDialog alertDialog;
    Spinner spinnerCiclo;
    EditText editTextInicio;
    EditText editTextFin;
    EditText editTextParcial;
    Spinner  spinnerCicloAgregar;
    ProgressDialog pDialog;
    EditText editTextCiclo;


    private String usuario_;
    private TextView textViewInicio,textViewFin;
    private String nivel_;

    public ParcialesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parciales, container, false);
        usuario_= getArguments().getString("usuario");
        nivel_= getArguments().getString("nivel");
        return view;
    }

    private void changeFragment(Fragment fragment, String tag,int ID, int Ciclo){
        Bundle args = new Bundle();
        args.putString("usuario",usuario_);
        args.putString("nivel", (""+nivel_));
        args.putString("ID", (""+ID));
        args.putString("Ciclo", (""+Ciclo));
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,tag).commit();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinnerCiclo= getView().findViewById(R.id.spinnerCiclo);
        FloatingActionButton floatingActionButton = getView().findViewById(R.id.floatingActionButton_Add_Parcial);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertInsertar().show();
            }
        });

        spinnerCiclo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AsyncGetParciales asyncGet;
                switch (i){
                    case 0:
                        asyncGet = new AsyncGetParciales(1);
                        asyncGet.execute();
                        break;
                    case 1:
                         asyncGet = new AsyncGetParciales(3);
                         asyncGet.execute();
                        break;
                    case  2:
                        asyncGet = new AsyncGetParciales(2);
                        asyncGet.execute();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private AlertDialog AlertInsertar(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.agregar_parcial_dialog,null);
        builder.setView(v);
          alertDialog =builder.create();


         editTextInicio = v.findViewById(R.id.txtSetFecha_Inicio_Parcial);
         editTextFin  = v.findViewById(R.id.txtSetFecha_Fin_Parcial);
         editTextParcial = v.findViewById(R.id.txtSetNumerodeParcial);
         spinnerCicloAgregar = v.findViewById(R.id.spinnerCiclo_Parcial);

        Button btnEnviar = v.findViewById(R.id.btnSaveParcial);
        Button btnCancelar = v.findViewById(R.id.btnCancelParcial);

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
                }else if (editTextParcial.getText().toString().trim().isEmpty()){
                    editTextParcial.setError("Campo Obligatorio");
                }
                else {
                    String Inicio = editTextInicio.getText().toString().trim();
                    String Fin = editTextFin.getText().toString().trim();
                    int Parcial = Integer.parseInt(editTextParcial.getText().toString().trim());
                    if (!validarFecha(Inicio)){
                        editTextInicio.setError("Formato no valido: ejemplo: 2019-05-31");
                    }else if (!validarFecha(Fin)){
                        editTextFin.setError("Formato no valido: ejemplo: 2019-05-31");
                    }else if (Parcial <= 0 || Parcial >5){
                        editTextParcial.setError("No se encuentra ese numero de Parcial");
                    }else {
                         int Ciclo = Integer.parseInt(spinnerCicloAgregar.getSelectedItem().toString().trim());
                         Inicio = editTextInicio.getText().toString().trim();
                         Fin = editTextFin.getText().toString().trim();
                         Parcial = Integer.parseInt(editTextParcial.getText().toString().trim());
                        AsyncSetParcial asyncSetParcial = new AsyncSetParcial(Inicio,Fin,Ciclo,Parcial);
                        asyncSetParcial.execute();
                    }
                }
            }
        });

        return alertDialog ;
    }
    public static boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    private AlertDialog AlertModificar(final int ID , final String Inicio, final String Fin , final String Ciclo, String Parcial){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.editar_parcial_dialog, null);
        builder.setView(v);
        alertDialog=builder.create();

        TextView textViewParcial = v.findViewById(R.id.textViewParcial_dialog);
        textViewInicio= v.findViewById(R.id.textViewInicioParcial_dialog);
        textViewFin= v.findViewById(R.id.textViewFinParcial_dialog);
        TextView textViewCiclo = v.findViewById(R.id.textViewPArcial_Ciclo_dialog);
        textViewParcial.setText("Parcial: "+Parcial);
        textViewInicio.setText("Inicio: "+Inicio);
        textViewFin.setText("Finalización: "+Fin);
        int ciclo_=0;
        switch (Integer.parseInt(Ciclo.substring(0,2))){
            case 1:
                ciclo_ =(1);
                break;
            case 2:
                ciclo_ =(3);

                break;
            case  3:
                ciclo_ =(2);
                break;
        }
        textViewCiclo.setText("Ciclo: 0"+ciclo_+"-"+Inicio.substring(0,4));

        final LinearLayout linearLayoutInicio = v.findViewById(R.id.LLEditFechaInicioParcial);
        final LinearLayout linearLayoutFin = v.findViewById(R.id.LLEditFechaFinalParcial);
        final LinearLayout linearLayoutCiclo = v.findViewById(R.id.LLEditCicloParcial);

        final CheckBox checkBoxInicio= v.findViewById(R.id.checkboxEditFechaInicioParcial_dialog);
        final CheckBox checkBoxFin = v.findViewById(R.id.checkboxEditFechaFinParcial_dialog);
        final CheckBox checkBoxCiclo= v.findViewById(R.id.checkboxEditCiclo_Parcial_dialog);



        editTextInicio = v.findViewById(R.id.txtNuevasFechaInicioParcial_Edit);
        editTextFin= v.findViewById(R.id.txtNuevasFechaFinParcial_Edit);
        final Spinner spinnerCicloNuevo = v.findViewById(R.id.SpinnerNuevoCicloParcial_Edit);

        Button btnEnviar = v.findViewById(R.id.btnEnviarParcial_dialog);
        Button btnCancelar = v.findViewById(R.id.btnCacelarParcial_dialog);


        checkBoxCiclo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    linearLayoutCiclo.setVisibility(View.VISIBLE);
                    checkBoxFin.setChecked(true);
                    checkBoxInicio.setChecked(true);
                }
                else {
                    linearLayoutCiclo.setVisibility(View.GONE);
                    checkBoxFin.setChecked(false);
                    checkBoxInicio.setChecked(false);
                }

            }
        });

        checkBoxFin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b){
                   linearLayoutFin.setVisibility(View.VISIBLE);
               }else
               {
                   linearLayoutFin.setVisibility(View.GONE);
               }
            }
        });

        checkBoxInicio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    linearLayoutInicio.setVisibility(View.VISIBLE);
                }else {
                    linearLayoutInicio.setVisibility(View.GONE);
                }
            }
        });


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fecha_Inicio=null, Fecha_Fin=null;
                int ciclo=0;
                boolean mod=false;


                if (checkBoxCiclo.isChecked()){
                    if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Campo Requerido");
                    }else if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Campo Requerido");
                    }else  if (!validarFecha(editTextFin.getText().toString().trim())){
                        editTextFin.setError("Fecha Invalida");
                        editTextFin.requestFocus();
                    }else if (!validarFecha(editTextInicio.getText().toString().trim())){
                        editTextInicio.setError("Fecha Invalida");
                        editTextInicio.requestFocus();
                    }else {
                        Fecha_Fin = editTextFin.getText().toString().trim();
                        Fecha_Inicio = editTextInicio.getText().toString().trim();
                        switch (spinnerCicloNuevo.getSelectedItemPosition()){
                            case 0:
                                ciclo =(1);
                                break;
                            case 1:
                                ciclo =(3);

                                break;
                            case  2:
                                ciclo =(2);
                                break;
                        }
                        mod=true;
                    }
                }
                else if (checkBoxInicio.isChecked() && checkBoxFin.isChecked()){

                    if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Campo Requerido");
                    }else if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Campo Requerido");
                    }else  if (!validarFecha(editTextFin.getText().toString().trim())){
                        editTextFin.setError("Fecha Invalida");
                        editTextFin.requestFocus();
                    }else if (!validarFecha(editTextInicio.getText().toString().trim())){
                        editTextInicio.setError("Fecha Invalida");
                        editTextInicio.requestFocus();
                    }else {
                        Fecha_Fin = editTextFin.getText().toString().trim();
                        Fecha_Inicio = editTextInicio.getText().toString().trim();
                        ciclo =Integer.parseInt(Ciclo.substring(0,2));
                        mod=true;
                    }
                }
                else if (checkBoxInicio.isChecked()){
                    if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Campo Requerido");
                    }else if (!validarFecha(editTextInicio.getText().toString().trim())){
                        editTextInicio.setError("Fecha Invalida");
                        editTextInicio.requestFocus();
                    }else {
                        Fecha_Fin = Fin;
                        Fecha_Inicio = editTextInicio.getText().toString().trim();
                        ciclo =Integer.parseInt(Ciclo.substring(0,2));
                        mod=true;
                    }

                }
                else  if (checkBoxFin.isChecked()){
                    if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Campo Requerido");
                    }else  if (!validarFecha(editTextFin.getText().toString().trim())){
                        editTextFin.setError("Fecha Invalida");
                        editTextFin.requestFocus();
                    }else {
                        Fecha_Fin = editTextFin.getText().toString().trim();
                        Fecha_Inicio = Inicio;
                        ciclo =Integer.parseInt(Ciclo.substring(0,2));
                        mod=true;
                    }
                }

                if (mod){

                    AsyncUpdateParcial asyncUpdateParcial= new AsyncUpdateParcial(Fecha_Inicio,Fecha_Fin,ciclo,ID);
                    asyncUpdateParcial.execute();

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

    public class AsyncGetParciales extends AsyncTask<Void,Void,Boolean>{
        int ID_Ciclo;
        public AsyncGetParciales (int ID){
            this.ID_Ciclo=ID;
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
            parciales.Get_Parciales_From_Server(ID_Ciclo);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                 RecyclerView recyclerView;
                 RecyclerView.Adapter myAdapter;
                 RecyclerView.LayoutManager layoutManager;
                recyclerView = getView().findViewById(R.id.recyclerViewParciales);
                layoutManager = new LinearLayoutManager(getContext());

                myAdapter = new ParcialesAdapter(parciales.getPARCIAL(), parciales.getID_FECHA()
                        , parciales.getINICIO(), parciales.getFIN(), parciales.getCICLO(), R.layout.cardview_parciales, new ParcialesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int ID_Fecha_Parcial, int Ciclo, int position) {

                        changeFragment(new DiaHoraParcial_Fragment(),"Fechas_Parcial",ID_Fecha_Parcial,Ciclo);
                    }
                }, new ParcialesAdapter.OnLongItemClickListener() {
                    @Override
                    public void onLongItemClick(int ID_Fecha_Parcial, String Parcial, String Inicio, String Fin, String Ciclo, int position) {
                        AlertModificar(ID_Fecha_Parcial, Inicio, Fin, Ciclo, Parcial).show();
                    }

                });
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(layoutManager);
                pDialog.dismiss();
            }
            catch (Exception ex){
                Log.d("Error",ex.getMessage());
            }
            finally {
                pDialog.dismiss();
            }

        }
    }

    private AlertDialog Alert(String mensaje, String tipo, int icono, final int resp){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Add the buttons
        builder.setIcon(icono);
        builder.setMessage(mensaje)
                .setTitle(tipo);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                switch (resp){
                    case 0: //rango de fechas no valido
                        editTextInicio.setError("Fecha no Valida");
                        editTextFin.setError("Fecha no Valida");
                        editTextFin.requestFocus();
                        editTextInicio.requestFocus();
                        break;
                    case -1: //no se puede agregar mas parciales al ciclo
                        dialog.dismiss();
                        break;
                    case -2://-2 este parcial ya fue agregado
                        editTextParcial.setError("Escriba Otro Parcial");
                        editTextParcial.requestFocus();
                        dialog.dismiss();
                        break;
                    case -3:// -3 la fecha interfiere con otros parciales
                        editTextInicio.setError("Verifique que las fecha no coinsidan con otras");
                        editTextFin.setError("Verifique que las fecha no coinsidan con otras");
                        editTextFin.requestFocus();
                        editTextInicio.requestFocus();
                        dialog.dismiss();
                        break;
                    case -4://-4 falla en consulta
                        dialog.dismiss();
                        break;
                    case -5://-5 fechas estan fuera de rango del ciclo
                        editTextInicio.setError("Fecha no Valida");
                        editTextFin.setError("Fecha no Valida");
                        editTextFin.requestFocus();
                        editTextInicio.requestFocus();
                        dialog.dismiss();
                        break;
                    case 1://1 insetado
                        dialog.dismiss();
                        alertDialog.dismiss();
                        break;
                }

            }
        });
        if (resp !=1){
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    dialog.dismiss();
                    alertDialog.dismiss();
                }
            });
        }
// Set other dialog properties
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;

    }

    private AlertDialog AlertUpdate(String mensaje, String tipo, int icono, final int resp){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Add the buttons
        builder.setIcon(icono);
        builder.setMessage(mensaje)
                .setTitle(tipo);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                switch (resp){
                    case 0: //rango de fechas no valido
                        editTextInicio.setError("Fecha no Valida");
                        editTextFin.setError("Fecha no Valida");
                        editTextFin.requestFocus();

                        dialog.dismiss();
                        break;
                    case -2://fecha coinside con otros parciales
                        editTextInicio.setError("Fecha no Valida");
                        editTextFin.setError("Fecha no Valida");
                        editTextFin.requestFocus();

                        dialog.dismiss();
                        break;
                    case -4://-4 falla en consulta
                        dialog.dismiss();
                        break;
                    case -5://-5 fechas estan fuera de rango del ciclo
                        editTextInicio.setError("Fecha no Valida");
                        editTextFin.setError("Fecha no Valida");
                        editTextInicio.requestFocus();
                        dialog.dismiss();
                        break;
                    case 1://1 insertado
                        dialog.dismiss();
                        alertDialog.dismiss();
                        break;
                }

            }
        });
        if (resp !=1){
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    dialog.dismiss();
                    alertDialog.dismiss();
                }
            });
        }
// Set other dialog properties
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;

    }
        public class AsyncSetParcial extends AsyncTask<Void,Void,Boolean>{

        int resp=10;
        String inicio,fin;
        int id_ciclo,Parcial;
        public AsyncSetParcial(String inicio , String fin,int id_ciclo,int Parcial){
            this.fin= fin;
            this.id_ciclo= id_ciclo;
            this.inicio = inicio;
            this.Parcial=Parcial;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Pocesando...");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            resp =parciales.Insert_Parcial(usuario_,inicio,fin,id_ciclo,Parcial);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            switch (resp){
                case 0: //rango de fechas no valido
                    Alert("El Rango de Fecha no es Valido","Error",R.drawable.ic_warning,resp).show();
                    break;
                case -1: //no se puede agregar mas parciales al ciclo
                    Alert("No se Puede Agregar Mas Parciales a Este Ciclo","Error",R.drawable.ic_warning,resp).show();
                    break;
                case -2://-2 este parcial ya fue agregado
                    Alert("Este Parcial ya fue Agregado","Error",R.drawable.ic_warning,resp).show();
                    break;
                case -3:// -3 la fecha interfiere con otros parciales
                    Alert("La fecha Interfiere con otras Fechas","Error",R.drawable.ic_warning,resp).show();
                    break;
                case -4://-4 falla en consulta
                    Alert("Verifique su Conexion","Error",R.drawable.ic_wifi_check,resp).show();
                    break;
                case -5://-5 fechas estan fuera de rango del ciclo
                    Alert("El Rango de Fecha no es Valido","Error",R.drawable.ic_warning,resp).show();
                    break;
                case 1://1 insertado
                    Alert("Se Inserto Existosamente","Información",R.drawable.ic_info,resp).show();
                    break;

            }
            pDialog.dismiss();
        }
    }
    public class AsyncUpdateParcial extends AsyncTask<Void,Void,Boolean>{

        int resp=10;
        String inicio,fin;
        int id_ciclo,id_Fecha_Parcial;
        public AsyncUpdateParcial(String inicio , String fin,int id_ciclo,int id_Fecha_Parcial){
            this.fin= fin;
            this.id_ciclo= id_ciclo;
            this.inicio = inicio;
            this.id_Fecha_Parcial=id_Fecha_Parcial;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            resp=parciales.Update_Parcial(usuario_,inicio,fin,id_ciclo,id_Fecha_Parcial);

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            AsyncGetParciales asyncGetParciales = null;
            switch (spinnerCiclo.getSelectedItemPosition()){
                case 0:
                    asyncGetParciales= new AsyncGetParciales(1);
                    break;
                case 1:
                    asyncGetParciales = new AsyncGetParciales(3);

                    break;
                case  2:
                    asyncGetParciales = new AsyncGetParciales(2);
                    break;

                default:
                    asyncGetParciales= new AsyncGetParciales(1);

            }
            asyncGetParciales.execute();

            switch (resp){
                case 0://fechas no validas
                    AlertUpdate("El Rango de Fecha no es Valido","Error",R.drawable.ic_warning,resp).show();
                    break;
                case  1://actualizado
                    AlertUpdate("Actualizado","Info",R.drawable.ic_info,resp).show();
                    break;
                case -2://fecha coinside con otros parciales
                    AlertUpdate("La fecha Coinside con Otro Parcial","Error",R.drawable.ic_warning,resp).show();
                    break;
                case -4://falla en consulta
                    AlertUpdate("Verifique su Conexion","Error",R.drawable.ic_wifi_check,resp).show();
                    break;
                case -5://fecha fuera de rango del ciclo
                    AlertUpdate("El Rango de Fecha no es Valido","Error",R.drawable.ic_warning,resp).show();
                    break;


            }



        }
    }

}
