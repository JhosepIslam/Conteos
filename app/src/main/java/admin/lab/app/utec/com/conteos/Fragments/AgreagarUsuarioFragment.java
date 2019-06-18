package admin.lab.app.utec.com.conteos.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.Models.Facultades;
import admin.lab.app.utec.com.conteos.Models.Insertar_Usuario;
import admin.lab.app.utec.com.conteos.Models.Laboratorios;
import admin.lab.app.utec.com.conteos.Models.Niveles;
import admin.lab.app.utec.com.conteos.R;
import admin.lab.app.utec.com.conteos.Utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgreagarUsuarioFragment extends Fragment {

    Util util = new Util();
    Insertar_Usuario insertar_usuario = new Insertar_Usuario();
    Niveles niveles = new Niveles();
    Laboratorios laboratorios = new Laboratorios();
    Facultades facultades = new Facultades();
    private EditText txtNombre,txtApellido,txtUsuario,txtClaveUser,txtClaveComprobar,txtCarnet,txtCodigoEmpleado;
    private TextView textViewLab;

    private Spinner spNivel,spFacultad,spLab;
    private LinearLayout linearLayout,linearLayoutFacultad;
    private int nivel;
    public String usuario_;
    String Nombre;
    String Apellido;
    String Usuario;
    String Contraseña;
    String Facultad;
    String Nivel;
    String Lab;
    private Button btnRegistrar,btnCancelar;
    public AgreagarUsuarioFragment() {
        // Required empty public constructor
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agreagar_usuario, container, false);
        usuario_= getArguments().getString("usuario");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Async async = new Async();
        async.execute();

        nivel = Integer.parseInt(getArguments().getString("nivel"));



         btnCancelar=getView().findViewById(R.id.btnCancelUserRegister);
         btnRegistrar = getView().findViewById(R.id.btnRegistrarUser);
         spNivel= getView().findViewById(R.id.spinnerTipoUserUsuario);
         spFacultad = getView().findViewById(R.id.spinnerFacultadUser);
         txtNombre= getView().findViewById(R.id.txtNombreUsuario);
         txtCarnet= getView().findViewById(R.id.txtCarnetUsuario);

         linearLayout=getView().findViewById(R.id.LLFacultad_Usuario);
         txtApellido= getView().findViewById(R.id.txtApellidoUsuario);
         linearLayoutFacultad= getView().findViewById(R.id.LLFacultad_Usuario);
         txtUsuario = getView().findViewById(R.id.txtUsuarioUsuario);
         txtClaveUser = getView().findViewById(R.id.txtClaveUserNew);
         txtClaveComprobar = getView().findViewById(R.id.txtClaveComprobarUsuario);
         txtCodigoEmpleado = getView().findViewById(R.id.txtCodigoEmpleado_Usuario);
         spLab = getView().findViewById(R.id.spinnerLabUser);
         textViewLab = getView().findViewById(R.id.labLabUser);
         linearLayout = getView().findViewById(R.id.LLLaboratorioUser);
        if (nivel==3){
            LinearLayout nivelLinear = getView().findViewById(R.id.LLNivelUsuario);
            nivelLinear.setVisibility(View.GONE);
            txtCarnet.setVisibility(View.VISIBLE);
            txtUsuario.setText("");
            linearLayoutFacultad.setVisibility(View.GONE);
            textViewLab.setVisibility(View.GONE);
            spLab.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            txtUsuario.setFocusable(false);
            txtCodigoEmpleado.setVisibility(View.GONE);
            txtUsuario.setFocusableInTouchMode(false);
            txtUsuario.setClickable(false);
        }



         txtCarnet.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                 txtUsuario.setText(txtCarnet.getText().toString().trim());
             }
         });

         spNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                 if(nivel!=3){
                     String tipo = spNivel.getItemAtPosition(i).toString();
                     switch (tipo){
                         case "INSTRUCTOR":
                             txtCarnet.setVisibility(View.VISIBLE);
                             txtUsuario.setText("");
                             textViewLab.setVisibility(View.VISIBLE);
                             spLab.setVisibility(View.VISIBLE);
                             linearLayout.setVisibility(View.VISIBLE);
                             txtUsuario.setFocusable(false);
                             txtCodigoEmpleado.setVisibility(View.GONE);
                             txtUsuario.setFocusableInTouchMode(false);

                             txtUsuario.setClickable(false);

                             break;
                         case "ADMIN_LAB" :
                             txtUsuario.setText("");
                             txtCarnet.setVisibility(View.GONE);
                             textViewLab.setVisibility(View.VISIBLE);
                             spLab.setVisibility(View.VISIBLE);
                             linearLayout.setVisibility(View.VISIBLE);

                             txtUsuario.setFocusable(true);
                             txtCodigoEmpleado.setVisibility(View.GONE);
                             txtUsuario.setFocusableInTouchMode(true);
                             txtUsuario.setClickable(true);
                             linearLayout.setVisibility(View.VISIBLE);


                             break;
                         case "SECRETARIA" :
                             txtUsuario.setText("");
                             txtCarnet.setVisibility(View.GONE);
                             textViewLab.setVisibility(View.VISIBLE);
                             spLab.setVisibility(View.VISIBLE);
                             linearLayout.setVisibility(View.VISIBLE);

                             txtUsuario.setFocusable(true);
                             linearLayout.setVisibility(View.GONE);
                             txtCodigoEmpleado.setVisibility(View.GONE);
                             txtUsuario.setFocusableInTouchMode(true);
                             txtUsuario.setClickable(true);

                             break;
                         case "DOCENTE":
                             txtCodigoEmpleado.setVisibility(View.VISIBLE);
                             txtUsuario.setFocusable(true);
                             txtUsuario.setFocusableInTouchMode(true);
                             txtUsuario.setClickable(true);
                             txtUsuario.setText("");

                             txtCarnet.setVisibility(View.GONE);
                             textViewLab.setVisibility(View.GONE);
                             spLab.setVisibility(View.GONE);
                             linearLayout.setVisibility(View.GONE);
                             break;
                         default:
                             txtUsuario.setFocusable(true);
                             txtUsuario.setFocusableInTouchMode(true);
                             txtUsuario.setClickable(true);
                             txtUsuario.setText("");

                             txtCodigoEmpleado.setVisibility(View.GONE);
                             txtCarnet.setVisibility(View.GONE);
                             textViewLab.setVisibility(View.GONE);
                             spLab.setVisibility(View.GONE);
                             linearLayout.setVisibility(View.GONE);
                             break;
                        }
                 }
             }
             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });
         btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nombre = txtNombre.getText().toString().trim().toUpperCase();
                Apellido = txtApellido.getText().toString().trim().toUpperCase();
                Usuario = txtUsuario.getText().toString().trim();
                Contraseña = txtClaveUser.getText().toString().trim();

                Facultad = spFacultad.getSelectedItem().toString();
                if (nivel ==3){
                    Nivel ="INSTRUCTOR";
                }else
                Nivel = spNivel.getSelectedItem().toString();

                try {
                    switch (Nivel) {
                        case "DOCENTE":
                            if (txtCodigoEmpleado.getText().toString().trim().isEmpty()){
                                txtCodigoEmpleado.setError("Se requiere un codigo de Empledo");
                            }
                            else if (ComprobarDatosNulos()) {
                                if (util.ValidarCorreo(Usuario)){
                                        String codigo = txtCodigoEmpleado.getText().toString().trim();
                                        SetAsync setAsync = new SetAsync(Nombre,Apellido,Usuario,
                                            Contraseña,Nivel,Facultad,codigo);
                                        setAsync.execute();
                                }
                                else {
                                    txtUsuario.setError("Nombre de Usuario  no Valido");
                                }
                            }
                            break;
                        case "INSTRUCTOR":
                        case "SECRETARIA":
                        case "ADMIN_LAB":
                            Lab = spLab.getSelectedItem().toString();
                            if (ComprobarDatosNulos()){
                                if (Nivel.equals("INSTRUCTOR")){
                                    SetAsync setAsync = new SetAsync(Nombre,Apellido,Usuario,
                                            Contraseña,Nivel,Facultad,Lab,true);
                                    setAsync.execute();
                                }else {
                                    if (!util.ValidarCorreo(Usuario)){
                                        txtUsuario.setError("Nombre de Usuario  no Valido");
                                    }else {
                                        if (Nivel.equals("SECRETARIA")){
                                            Lab="";
                                            Nivel = "ADMIN_LAB";
                                        }
                                        SetAsync setAsync = new SetAsync(Nombre,Apellido,Usuario,
                                                Contraseña,Nivel,Facultad,Lab,true);
                                        setAsync.execute();
                                    }
                                }
                            }
                            break;
                        case "DECANO":
                            if (ComprobarDatosNulos()){
                                SetAsync setAsync = new SetAsync(Nombre,Apellido,Usuario,
                                        Contraseña,Nivel,Facultad);
                                setAsync.execute();
                            }
                            break;
                        default:
                            if (ComprobarDatosNulos()){
                                if (!util.ValidarCorreo(Usuario)){
                                    txtUsuario.setError("Nombre de Usuario  no Valido");
                                }
                                else {
                                    SetAsync setAsync = new SetAsync(Nombre,Apellido,Usuario,
                                            Contraseña,Nivel,Facultad);
                                    setAsync.execute();
                                }
                            }
                            break;

                    }
                    //AlertSave().show();
                }
                catch (Exception ex){}
            }

        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert().show();
            }
        });
    }
    private void changeFragment(Fragment fragment, String tag){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,tag).commit();
    }

    private AlertDialog  Alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setMessage("¿Esta seguro de que quiere salir?")
                .setTitle("Advertencia");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                HomeFragment fragment = new HomeFragment();
               changeFragment(fragment,"HomeAdmin");


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
// Set other dialog properties
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;

    }

    private boolean CompobarDatosNulosInstructor(){
        if (txtNombre.getText().toString().trim().isEmpty()){
            txtNombre.setError("Se requiere un nombre");
            return false;
        }
        else if (txtApellido.getText().toString().trim().isEmpty()){
            txtApellido.setError("Se requiere un Apellido");
            return false;
        }
        else if (txtCarnet.getText().toString().trim().isEmpty()){
            txtCarnet.setError("Se requiere un Numero de Carnet");
            txtUsuario.setError("Se requiere un Nombre de Usuario");
            return false;
        }
        else if (txtClaveUser.getText().toString().trim().isEmpty()){
            txtClaveUser.setError("Se requiere una Contraseña");
            return false;
        }
        else if (txtClaveUser.getText().toString().trim().length() <4){
            txtClaveUser.setError("La Contraseña es muy Corta");
            return false;
        }
        else if (txtClaveComprobar.getText().toString().trim().isEmpty()){
            txtClaveComprobar.setError("Las contraseñas no coinsiden");
            return false;
        }
        else return true;
    }
    private boolean ComprobarDatosNulos(){
        if (txtNombre.getText().toString().trim().isEmpty()){
            txtNombre.setError("Se requiere un nombre");
            return false;
        }
        else if (txtApellido.getText().toString().trim().isEmpty()){
            txtApellido.setError("Se requiere un Apellido");
            return false;
        }
        else if (txtUsuario.getText().toString().trim().isEmpty()){
            txtUsuario.setError("Se requiere un Nombre de Usuario");
            return false;
        }
        else if (txtClaveUser.getText().toString().trim().isEmpty()){
            txtClaveUser.setError("Se requiere una Contraseña");
            return false;
        }
        else if (txtClaveUser.getText().toString().trim().length() <5){
            txtClaveUser.setError("La Contraseña es muy Corta");
            return false;
        }
        else if (!txtClaveComprobar.getText().toString().trim().equals(txtClaveUser.getText().toString())){
            txtClaveComprobar.setError("Las contraseñas no coinsiden");
            return false;
        }else return true;
    }


    private AlertDialog  AlertSave(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setMessage("Usuario Registrado Existosamente!\n¿Desea Agregar Otro Usuario?")
                .setTitle("Informacion");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {    // User clicked OK button
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                HomeFragment fragment = new HomeFragment();
                changeFragment(fragment,"HomeAdmin");


            }
        });
// Set other dialog properties
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;

    }
    public class Async extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {

            niveles.getNivelesFromServer();
            facultades.getFacultadesFromServer();
            laboratorios.getLaboratorioFromServer();

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            try{
                ArrayList nivel;
                nivel =  niveles.getNiveles();
                nivel.add("SECRETARIA");
                final ArrayAdapter spinnerAdapterNiveles = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,nivel);
                final ArrayAdapter spinnerAdapterFacultades = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, facultades.getFacultad());
                final ArrayAdapter spinnerAdapterLaboratorios = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, laboratorios.getLaboratorio());
                spNivel.setAdapter(spinnerAdapterNiveles);
                spFacultad.setAdapter(spinnerAdapterFacultades);
                spLab.setAdapter(spinnerAdapterLaboratorios);
            }
            catch (Exception ex){

                AlertCon().show();
            }
        }
    }
    public class  SetAsync extends AsyncTask<Void,Void,Boolean>{

        int respuesta=-1;
        String nivel,nombre,apellido,usuario,clave,facultad,cod_empleado,laboratorio;

        public SetAsync(String nombre,String apellido,String usuario, String clave
        ,String nivel, String facultad){
            this.nombre = nombre;
            this.apellido = apellido;
            this.usuario = usuario;
            this.clave = clave;
            this.nivel = nivel;
            this.facultad = facultad;
        }
        public SetAsync(String nombre,String apellido,String usuario, String clave
                ,String nivel, String facultad, String cod_empleado){
            this.nombre = nombre;
            this.apellido = apellido;
            this.usuario = usuario;
            this.clave = clave;
            this.nivel = nivel;
            this.facultad = facultad;
            this.cod_empleado = cod_empleado;
        }
        public SetAsync(String nombre,String apellido,String usuario, String clave
                ,String nivel, String facultad, String laboratorio, boolean a){
            this.nombre = nombre;
            this.apellido = apellido;
            this.usuario = usuario;
            this.clave = clave;
            this.nivel = nivel;
            this.facultad = facultad;
            this.laboratorio = laboratorio;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {

            switch (nivel){
                case "DOCENTE":
                    respuesta =insertar_usuario.Insertar_Usuario_Docente(nombre.toUpperCase(),apellido.toUpperCase()
                            ,usuario,clave,nivel,facultad,cod_empleado);
                    break;
                case "ADMIN_LAB":
                case "INSTRUCTOR":
                    respuesta = insertar_usuario.Insertar_Usuario_Admin_lab_instructor(nombre,apellido,usuario,clave
                            ,nivel,facultad,laboratorio,usuario_);
                    break;
                default:
                    respuesta = insertar_usuario.Insertar_Usuario(nombre.toUpperCase(),apellido.toUpperCase()
                            ,usuario,clave,nivel,facultad);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            switch (respuesta){
                case 0://insertado
                    AlertSave().show();
                    txtNombre.setText("");
                    txtApellido.setText("");
                    txtCarnet.setText("");
                    txtClaveComprobar.setText("");
                    txtClaveUser.setText("");
                    txtUsuario.setText("");
                    txtCodigoEmpleado.setText("");
                    break;
                case 1://no insertado
                    AlertUserExist().show();
                    break;
                case  -1:
                    AlertCon().show();
                    break;
            }
        }
    }
    private AlertDialog AlertUserExist(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setMessage("Este Nombre de usuario ya esta registrado")
                .setTitle("Error").setIcon(R.drawable.ic_user_check);

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                txtUsuario.requestFocus();
                }
        });
// Set other dialog properties
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;

    }
    private AlertDialog AlertCon(){
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

}
