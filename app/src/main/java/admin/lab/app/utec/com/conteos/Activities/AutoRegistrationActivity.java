package admin.lab.app.utec.com.conteos.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import admin.lab.app.utec.com.conteos.Models.Facultades;
import admin.lab.app.utec.com.conteos.Models.Insertar_Usuario;
import admin.lab.app.utec.com.conteos.R;
import admin.lab.app.utec.com.conteos.Utils.Util;

public class AutoRegistrationActivity extends AppCompatActivity {

    Util util = new Util();
    private Button btnGuardar, btnCancelar;
    private String codigo;
    private String nivel;
    SharedPreferences pref;
    private Spinner spFacultad;
    private EditText txtNombre,txtApellido,txtUsuario,txtClaveUser,txtClaveComprobar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_registration);
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            codigo = bundle.getString("codigo");
            nivel = (bundle.getString("level"));
        }
        txtUsuario = findViewById(R.id.txtUsuarioUsuarioDocente);
        txtNombre = findViewById(R.id.txtNombreUsuarioDocente);
        txtApellido= findViewById(R.id.txtApellidoUsuarioDocente);
        txtClaveUser = findViewById(R.id.txtClaveUserNewDocente);
        txtClaveComprobar = findViewById(R.id.txtClaveComprobarUsuarioDocente);
        btnGuardar = findViewById(R.id.btnRegistrarUserDocente);
        spFacultad = findViewById(R.id.spinnerFacultadUserDocente);
        btnCancelar = findViewById(R.id.btnCancelUserRegisterDocente);
        final Activity activity_ = this;
        Async_Get async_get = new Async_Get(this);
        async_get.execute();
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String Nombre = txtNombre.getText().toString().trim().toUpperCase();
               String Apellido = txtApellido.getText().toString().trim().toUpperCase();
               String Usuario = txtUsuario.getText().toString().trim();
               String Contraseña = txtClaveUser.getText().toString().trim();

               String Facultad = spFacultad.getSelectedItem().toString();
                if (ComprobarDatosNulos()) {
                    if (util.ValidarCorreo(Usuario)){
                        //guardar
                        Async_Set async_set = new Async_Set(Nombre,Apellido,Usuario,Contraseña,"DOCENTE",Facultad,codigo,activity_);
                        async_set.execute();
                    }
                    else {
                        txtUsuario.setError("Nombre de Usuario  no Valido");
                    }
                }
            }
        });
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

    public  class  Async_Get extends AsyncTask<Void,Void,Boolean>{
        Facultades facultades = new Facultades();
        Activity activity;
        public  Async_Get(Activity activity){
            this.activity = activity;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            facultades.getFacultadesFromServer();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            final ArrayAdapter spinnerAdapterFacultades = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item, facultades.getFacultad());

            spFacultad.setAdapter(spinnerAdapterFacultades);
        }
    }

    public  class  Async_Set extends AsyncTask<Void,Void,Boolean> {
        int resp=0;
        Activity activity;
        String nombre, Apellido,Usuario,Contrasenia,Nivel,facultad,Cod_empleado;
        public  Async_Set(String nombre, String Apellido, String Usuario, String Contrasenia, String Nivel ,String facultad
                            ,String Cod_Empelado,Activity activity){
            this.activity = activity;
            this.nombre = nombre;
            this.Apellido = Apellido;
            this.Usuario = Usuario;
            this.Contrasenia = Contrasenia;
            this.Nivel = Nivel;
            this.facultad = facultad;
            this.Cod_empleado = Cod_Empelado;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {

            Insertar_Usuario insertar_usuario = new Insertar_Usuario();
            resp = insertar_usuario.Insertar_Usuario_Docente(nombre,Apellido,Usuario,Contrasenia,Nivel,facultad,Cod_empleado);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            switch (resp){
                case 1:
                    txtUsuario.setError("Nombre de Usuario No Valido");
                    break;
                case -1:
                    txtUsuario.setError("Error");
                    break;
                case 0:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name", Usuario);
                    intent.putExtra("level",  ""+4);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
            }
        }


    }
    private  void  saveOnPreferences(String email, String password){

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email",email);
        editor.putString("pass",password);
        editor.commit();
        editor.apply();
    }




}
