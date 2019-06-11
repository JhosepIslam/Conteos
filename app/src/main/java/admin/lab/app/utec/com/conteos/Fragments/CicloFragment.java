package admin.lab.app.utec.com.conteos.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.Models.Ciclo;
import admin.lab.app.utec.com.conteos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CicloFragment extends Fragment {


    ProgressDialog pDialog;
     AlertDialog alertDialog;
    Ciclo ciclo = new Ciclo();
    TextView textViewCiclo01,textViewInicio01,textViewFin01,
    textViewEstado01;

    TextView textViewCiclo02,textViewInicio02,textViewFin02,
            textViewEstado02;

    TextView textViewCiclo03,textViewInicio03,textViewFin03,
            textViewEstado03;

    EditText editTextInicio;
    EditText editTextFin;
    EditText editTextAnio;
    TextView textViewInicio;
    TextView textViewFin;
    Button button01,button02,button03;
    private String nivel_;
    private String usuario_;

    public CicloFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ciclo, container, false);
        usuario_= getArguments().getString("usuario");
        nivel_= getArguments().getString("nivel");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textViewCiclo01 = getView().findViewById(R.id.textViewCiclo01);
        textViewCiclo02 = getView().findViewById(R.id.textViewCiclo02);
        textViewCiclo03 = getView().findViewById(R.id.textViewCiclo03);

        textViewInicio01 = getView().findViewById(R.id.textViewInicioCiclo01);
        textViewInicio02 = getView().findViewById(R.id.textViewInicioCiclo02);
        textViewInicio03 = getView().findViewById(R.id.textViewInicioCiclo03);

        textViewFin01 = getView().findViewById(R.id.textViewFinCiclo01);
        textViewFin02 = getView().findViewById(R.id.textViewFinCiclo02);
        textViewFin03 = getView().findViewById(R.id.textViewFinCiclo03);

        textViewEstado01 = getView().findViewById(R.id.textViewActivo_Inactivo01);
        textViewEstado02 = getView().findViewById(R.id.textViewActivo_Inactivo02);
        textViewEstado03 = getView().findViewById(R.id.textViewActivo_Inactivo03);

        button01 = getView().findViewById(R.id.btn_modificar01);
        button02 = getView().findViewById(R.id.btn_modificar02);
        button03 = getView().findViewById(R.id.btn_modificar03);

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    String inicio, fin, anio,ai;
                    inicio = textViewInicio01.getText().toString().substring(7,18);
                    fin =textViewFin01.getText().toString().substring(5,15);
                    ai =textViewEstado01.getText().toString().trim();
                    anio = textViewCiclo01.getText().toString().substring(10,14);
                    AlertModificar(1,inicio,fin,ai,textViewCiclo01.getText().toString(),anio).show();
                }catch (Exception ex){

                }
            }
        });

        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {



                    String inicio, fin, anio,ai;
                    inicio = textViewInicio02.getText().toString().substring(7,18);
                    fin =textViewFin02.getText().toString().substring(5,15);
                    ai =textViewEstado02.getText().toString().trim();
                    anio = textViewCiclo02.getText().toString().substring(10,14);
                    AlertModificar(2,inicio,fin,ai,textViewCiclo02.getText().toString(),anio).show();
                }catch (Exception ex){

                }
            }
        });
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    String inicio, fin, anio,ai;
                    inicio = textViewInicio03.getText().toString().substring(7,18);
                    fin =textViewFin03.getText().toString().substring(5,15);
                    ai =textViewEstado03.getText().toString().trim();
                    anio = textViewCiclo03.getText().toString().substring(10,14);
                    AlertModificar(1,inicio,fin,ai,textViewCiclo03.getText().toString(),anio).show();
                }catch (Exception ex){

                }
            }
        });

        AsyncGet asyncGet = new AsyncGet();
        asyncGet.execute();
    }


    private AlertDialog AlertModificar(final int ID , final String Inicio, final String Fin , final String Activo_Inactivo, String Ciclo, final String Anio){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.edit_ciclo_dialog, null);
        builder.setView(v);
        alertDialog=builder.create();
        TextView textViewCiclo = v.findViewById(R.id.textViewCiclo_dialog);
        textViewInicio= v.findViewById(R.id.textViewInicio_dialog);
        textViewFin= v.findViewById(R.id.textViewFin_dialog);
        TextView textViewActivo_Inactivo = v.findViewById(R.id.textViewActivo_Inactivo_dialog);
        textViewCiclo.setText(Ciclo);
        textViewInicio.setText("Inicio: "+Inicio);
        textViewFin.setText("Finalización: "+Fin);
        textViewActivo_Inactivo.setText(Activo_Inactivo);

        final LinearLayout linearLayoutInicio = v.findViewById(R.id.LLEditFechaInicio);
        final LinearLayout linearLayoutFin = v.findViewById(R.id.LLEditFechaFinal);
        final LinearLayout linearLayoutAnio = v.findViewById(R.id.LLEditAnio);

        final CheckBox checkBoxInicio= v.findViewById(R.id.checkboxEditFechaInicio_dialog);
        final CheckBox checkBoxFin = v.findViewById(R.id.checkboxEditFechaFin_dialog);
        final CheckBox checkBoxActivo_Inactivo = v.findViewById(R.id.checkboxEditActivar_Desactivar_dialog);
        final CheckBox checkBoxAnio = v.findViewById(R.id.checkboxEditAnio_dialog);

        switch (Activo_Inactivo){
            case "Activo":
                checkBoxActivo_Inactivo.setText("Desactivar");
                break;
            case "Inactivo":
                checkBoxActivo_Inactivo.setText("Activar");
        }

         editTextInicio = v.findViewById(R.id.txtNuevasFechaInicio_Edit);
         editTextFin= v.findViewById(R.id.txtNuevasFechaFin_Edit);
         editTextAnio= v.findViewById(R.id.txtNuevoAnioCiclo_Edit);

        checkBoxInicio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    linearLayoutInicio.setVisibility(View.VISIBLE);
                    editTextInicio.requestFocus();
                }else {
                    linearLayoutInicio.setVisibility(View.GONE);
                }
            }
        });
        checkBoxFin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    linearLayoutFin.setVisibility(View.VISIBLE);
                    editTextFin.requestFocus();
                }else {
                    linearLayoutFin.setVisibility(View.GONE);
                }
            }
        });
        checkBoxAnio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    linearLayoutAnio.setVisibility(View.VISIBLE);
                    editTextAnio.requestFocus();
                }else {
                    linearLayoutAnio.setVisibility(View.GONE);
                }
            }
        });
        Button btnEnviar = v.findViewById(R.id.btnEnviarCiclo_dialog);
        Button btnCancelar = v.findViewById(R.id.btnCacelarCiclo_dialog);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fecha_Inicio=null, Fecha_Fin=null, Anio_=null;
                int Estado=0;
                boolean flag_Anio = false, flagInicio = false, flag_Fin = false, flag_Act_Inac = false, mod=false;

                flag_Anio = checkBoxAnio.isChecked() ? true : false;
                flag_Fin = checkBoxFin.isChecked() ? true : false;
                flagInicio = checkBoxInicio.isChecked() ? true : false;
                flag_Act_Inac = checkBoxActivo_Inactivo.isChecked() ? true : false;

                if (flag_Anio && flagInicio && flag_Fin && flag_Act_Inac){
                    if (editTextAnio.getText().toString().trim().isEmpty()){
                        editTextAnio.setError("Requerido");
                    }else if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Requerido");
                    }else if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Requerido");
                    }else {
                        Fecha_Inicio =  editTextInicio.getText().toString().trim();
                        Anio_ =editTextAnio.getText().toString().trim();
                        Fecha_Fin =editTextFin.getText().toString().trim();
                        switch (checkBoxActivo_Inactivo.getText().toString().trim()){
                            case "Desactivar":
                                Estado=0;
                                break;
                            case "Activar":
                                Estado=1;
                                break;
                        }
                        mod =true;
                    }
                }
                else if (flag_Anio && flagInicio && flag_Fin){
                    if (editTextAnio.getText().toString().trim().isEmpty()){
                        editTextAnio.setError("Requerido");
                    }else if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Requerido");
                    }else if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Requerido");
                    }else {
                        Fecha_Inicio =  editTextInicio.getText().toString().trim();
                        Anio_ =editTextAnio.getText().toString().trim();
                        Fecha_Fin =editTextFin.getText().toString().trim();
                        switch (Activo_Inactivo){
                            case "Activo":
                                Estado=1;
                                break;
                            case "Inactivo":
                                Estado=0;
                                break;
                        }
                        mod =true;
                    }

                }else if (flag_Anio && flagInicio  && flag_Act_Inac){
                    if (editTextAnio.getText().toString().trim().isEmpty()){
                        editTextAnio.setError("Requerido");
                    }else if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Requerido");
                    }else {
                        Fecha_Inicio =  editTextInicio.getText().toString().trim();
                        Anio_ =editTextAnio.getText().toString().trim();
                        switch (checkBoxActivo_Inactivo.getText().toString().trim()){
                            case "Desactivar":
                                Estado=0;
                                break;
                            case "Activar":
                                Estado=1;
                                break;
                        }
                        Fecha_Fin = Fin;
                        mod =true;
                    }

                }else if (flagInicio && flag_Fin && flag_Act_Inac){
                     if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Requerido");
                    }else if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Requerido");
                    }else {
                         Fecha_Inicio =  editTextInicio.getText().toString().trim();
                         Fecha_Fin =editTextFin.getText().toString().trim();
                        switch (checkBoxActivo_Inactivo.getText().toString().trim()){
                            case "Desactivar":
                                Estado=0;
                                break;
                            case "Activar":
                                Estado=1;
                                break;
                        }
                        mod =true;
                    }

                }
                else if (flag_Anio && flagInicio ){
                    if (editTextAnio.getText().toString().trim().isEmpty()){
                        editTextAnio.setError("Requerido");
                    }else if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Requerido");
                    }else {
                        Fecha_Inicio =  editTextInicio.getText().toString().trim();
                        Anio_ =editTextAnio.getText().toString().trim();
                        switch (Activo_Inactivo){
                            case "Activo":
                                Estado=1;
                                break;
                            case "Inactivo":
                                Estado=0;
                                break;
                        }
                        Fecha_Fin = Fin;
                        mod =true;
                    }

                }else if (flag_Anio && flag_Act_Inac){
                    if (editTextAnio.getText().toString().trim().isEmpty()){
                        editTextAnio.setError("Requerido");
                    }else {
                        Anio_ =editTextAnio.getText().toString().trim();
                        switch (checkBoxActivo_Inactivo.getText().toString().trim()){
                            case "Desactivar":
                                Estado=0;
                                break;
                            case "Activar":
                                Estado=1;
                                break;
                        }
                        Fecha_Inicio = Inicio;
                        Fecha_Fin =Fin;
                        mod =true;
                    }

                }
                else if (flag_Anio && flag_Fin){
                    if (editTextAnio.getText().toString().trim().isEmpty()){
                        editTextAnio.setError("Requerido");
                    }else if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Requerido");
                    }else {
                        Fecha_Fin =  editTextInicio.getText().toString().trim();
                        Anio_ =editTextAnio.getText().toString().trim();
                        switch (Activo_Inactivo){
                            case "Activo":
                                Estado=1;
                                break;
                            case "Inactivo":
                                Estado=0;
                                break;
                        }
                        Fecha_Inicio=Inicio;
                        mod =true;
                    }

                }else if (flagInicio && flag_Fin ){

                     if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Requerido");
                    }else if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Requerido");
                    }else {
                         Fecha_Fin =  editTextInicio.getText().toString().trim();
                         Fecha_Inicio =editTextInicio.getText().toString().trim();
                         switch (Activo_Inactivo){
                             case "Activo":
                                 Estado=1;
                                 break;
                             case "Inactivo":
                                 Estado=0;
                                 break;
                         }
                         Anio_ = Anio;
                        mod =true;
                    }


                }
                else if ( flagInicio && flag_Act_Inac){
                     if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Requerido");
                    }else {
                         Fecha_Inicio =editTextInicio.getText().toString().trim();
                        switch (checkBoxActivo_Inactivo.getText().toString().trim()){
                            case "Desactivar":
                                Estado=0;
                                break;
                            case "Activar":
                                Estado=1;
                                break;
                        }
                        Anio_ = Anio;
                        Fecha_Fin=Fin;
                        mod =true;
                    }

                }
                else if ( flag_Fin && flag_Act_Inac){

                     if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Requerido");
                        editTextFin.requestFocus();
                    }else {
                         Fecha_Fin = editTextFin.getText().toString().trim();
                        switch (checkBoxActivo_Inactivo.getText().toString().trim()){
                            case "Desactivar":
                                Estado=0;
                                break;
                            case "Activar":
                                Estado=1;
                                break;
                        }
                        Fecha_Inicio =Inicio;
                        Anio_ = Anio;
                        mod =true;
                    }
                }
                else if (flag_Anio){
                    if (editTextAnio.getText().toString().trim().isEmpty()){
                        editTextAnio.setError("Requerido");
                        editTextAnio.requestFocus();
                    }else {
                        Anio_ = editTextAnio.getText().toString().trim();
                        switch (Activo_Inactivo){
                            case "Activo":
                                Estado=1;
                                break;
                            case "Inactivo":
                                Estado=0;
                                break;
                        }
                        Fecha_Fin=Fin;
                        Fecha_Inicio =Inicio;
                        mod =true;
                    }

                }
                else if ( flagInicio ){
                    if (editTextInicio.getText().toString().trim().isEmpty()){
                        editTextInicio.setError("Requerido");
                        editTextInicio.requestFocus();
                    }else {
                        Fecha_Inicio =editTextInicio.getText().toString().trim();
                        switch (Activo_Inactivo){
                            case "Activo":
                                Estado=1;
                                break;
                            case "Inactivo":
                                Estado=0;
                                break;
                        }
                        Fecha_Fin=Fin;
                        Anio_ =Anio;
                        mod =true;
                    }

                }else if (flag_Fin ){
                     if (editTextFin.getText().toString().trim().isEmpty()){
                        editTextFin.setError("Requerido");
                        editTextFin.requestFocus();
                    }else {
                         Fecha_Fin = editTextFin.getText().toString().trim();
                         switch (Activo_Inactivo){
                             case "Activo":
                                 Estado=1;
                                 break;
                             case "Inactivo":
                                 Estado=0;
                                 break;
                         }
                         Fecha_Inicio=Inicio;
                         Anio_ =Anio;
                         mod =true;
                    }

                }else if ( flag_Act_Inac){

                        switch (checkBoxActivo_Inactivo.getText().toString().trim()){
                            case "Desactivar":
                                Estado=0;
                                break;
                            case "Activar":
                                Estado=1;
                                break;
                        }
                    Fecha_Inicio=Inicio;
                    Fecha_Fin=Fin;
                    Anio_ =Anio;
                    mod =true;

                }

                if (mod){
                    if (!validarFecha(Fecha_Inicio)){
                        editTextInicio.setError("Fecha no Valida");
                        editTextInicio.requestFocus();
                    }else if (!validarFecha(Fecha_Fin)){
                        editTextFin.setError("Fecha no Valida");
                        editTextFin.requestFocus();
                    }else {
                        AsyncUpdate asyncUpdate = new AsyncUpdate(ID, Fecha_Inicio, Fecha_Fin, Anio_, Estado);
                        asyncUpdate.execute();
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
                    case 0:// fallo la consulta
                        dialog.dismiss();
                        break;
                    case 1://actualizado
                        alertDialog.dismiss();
                        dialog.dismiss();

                        break;
                    case -1:// otro ciclo activo
                        dialog.dismiss();
                        break;
                    case -2://rengo de fechas coninsiden con otro
                        editTextInicio.setError("Fecha No Valida");
                        editTextFin.setError("Fecha No Valida");
                        editTextInicio.requestFocus();
                        dialog.dismiss();
                        break;
                    case -3://rengo de facha incorrecto
                        editTextInicio.setError("Fecha No Valida");
                        editTextFin.setError("Fecha No Valida");
                        editTextInicio.requestFocus();
                        dialog.dismiss();
                        break;
                    default://conexion
                        dialog.dismiss();
                        break;
                }

            }
        });
        if (resp !=1 || resp !=-5){
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

    public class AsyncGet extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            ciclo.Get_Ciclos_From_Server();
            return null;
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
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            ArrayList ciclos, inicios, fins, estados , anios;

            try {

                ciclos = ciclo.getID_CICLO();
                inicios = ciclo.getFECHA_INICIO();
                fins = ciclo.getFECHA_FIN();
                estados = ciclo.getESTADO();
                anios = ciclo.getANIO();

                String Estado;
                String Ciclo = ciclos.get(0)+"-"+anios.get(0);
                String Inicio = inicios.get(0).toString();
                String Fin = fins.get(0).toString();

                    if (Integer.parseInt(estados.get(0).toString()) == 1){
                        Estado = "Activo";
                    }else {
                        Estado ="Inactivo";
                    }
                textViewCiclo01.setText("Ciclo: 0"+Ciclo);
                textViewInicio01.setText("Inicio: "+Inicio.substring(0,10));
                textViewFin01.setText("Fin: "+Fin.substring(0,10));
                textViewEstado01.setText(Estado);


                Ciclo = ciclos.get(1)+"-"+anios.get(1);
                Inicio = inicios.get(1).toString();
                Fin = fins.get(1).toString();

                if (Integer.parseInt(estados.get(1).toString()) == 1){
                    Estado = "Activo";
                }else {
                    Estado ="Inactivo";
                }

                textViewCiclo03.setText("Ciclo: 0"+Ciclo);
                textViewInicio03.setText("Inicio: "+Inicio.substring(0,10));
                textViewFin03.setText("Fin: "+Fin.substring(0,10));
                textViewEstado03.setText(Estado);


                //
                Ciclo = ciclos.get(2)+"-"+anios.get(2);
                Inicio = inicios.get(2).toString();
                Fin = fins.get(2).toString();

                if (Integer.parseInt(estados.get(2).toString()) == 1){
                    Estado = "Activo";
                }else {
                    Estado ="Inactivo";
                }

                textViewCiclo02.setText("Ciclo: 0"+Ciclo);
                textViewInicio02.setText("Inicio: "+Inicio.substring(0,10));
                textViewFin02.setText("Fin: "+Fin.substring(0,10));
                textViewEstado02.setText(Estado);

            }catch (Exception e){

            }finally {
                pDialog.dismiss();
            }

        }
    }
    public class AsyncUpdate extends AsyncTask<Void,Void,Boolean>{
        int ID_Ciclo ,Estado,resultado;
        String F_Inicio,F_Fin,Anio;
        public  AsyncUpdate(int ID_Ciclo, String F_Inicio, String F_Fin, String Anio, int Estado ){
            this.Anio=Anio;
            this.Estado=Estado;
            this.F_Fin=F_Fin;
            this.F_Inicio =F_Inicio;
            this.ID_Ciclo=ID_Ciclo;

        }


        @Override
        protected Boolean doInBackground(Void... voids) {

            resultado = ciclo.Update_Ciclo(usuario_,F_Inicio,F_Fin,Estado,ID_Ciclo,Anio);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            switch (resultado){
                case 0:// fallo la consulta
                    Alert("El Servidor Esta Ocupado, Intenta mas tarde","Error",R.drawable.ic_error,resultado).show();
                    break;
                case 1://actualizado
                    Alert("Actualizado","Info",R.drawable.ic_info,resultado).show();
                    break;
                case -1:// otro ciclo activo
                    Alert("Otro Ciclo esta Activo","Error",R.drawable.ic_error,resultado).show();
                    break;
                case -2://rengo de fechas coninsiden con otro
                    Alert("Rango de Fechas Coinsiden con Otro Ciclo","Error",R.drawable.ic_error_fecha,resultado).show();
                    break;
                case -3://rengo de facha incorrecto
                    Alert("Rango de Fecha no Valido","Error",R.drawable.ic_error_fecha,resultado).show();
                    break;
                default://conexion
                    Alert("No hay Conexion a Internet, Verifica tu Conexion","Error",R.drawable.ic_wifi_check,resultado).show();
                        break;
            }

            AsyncGet asyncGet = new AsyncGet();
            asyncGet.execute();
        }
    }
}
