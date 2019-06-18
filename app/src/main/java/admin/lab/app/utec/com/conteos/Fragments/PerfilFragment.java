package admin.lab.app.utec.com.conteos.Fragments;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import admin.lab.app.utec.com.conteos.Models.Modificaciones;
import admin.lab.app.utec.com.conteos.Models.Usuarios;
import admin.lab.app.utec.com.conteos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {


    private String usuario_;
    private TextView nombre, usuarioN, nivel ,facultad,lab;
    private Button buttonContrasenia;
    private int nivel_;
    private int ID_Usuario=0;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        try {
            usuario_= getArguments().getString("usuario");
            nivel_= Integer.parseInt(getArguments().getString("nivel"));
        }catch (Exception ex){
            usuario_="";
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nombre = getView().findViewById(R.id.textViewNombrePerfil);
        usuarioN = getView().findViewById(R.id.textViewUsuarioPerfil);
        nivel = getView().findViewById(R.id.textViewNivelPerfil);
        facultad = getView().findViewById(R.id.textViewFacultadPerfil);
        lab = getView().findViewById(R.id.textViewLaboratorioPerfil);
        buttonContrasenia = getView().findViewById(R.id.btn_contraseña);
        AsyncGet asyncGet = new AsyncGet();
        asyncGet.execute();

        buttonContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ID_Usuario !=0){
                    Alert().show();
                }
            }
        });


    }

    private AlertDialog Alert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.contrasenia_edit_layout,null);
        builder.setView(v);
        final AlertDialog alertDialog =builder.create();



        final EditText editTextPass = v.findViewById(R.id.txtNuevaContraseña);
        final EditText editTextPassR = v.findViewById(R.id.txtNuevaContraseñaRepetir);

        Button btnEnviar = v.findViewById(R.id.btnSaveCON);
        Button btnCancelar = v.findViewById(R.id.btnCancelCON);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextPass.getText().toString().trim().isEmpty()){
                    editTextPass.setError("Campo Requerido");
                    editTextPass.requestFocus();

                }else if (editTextPassR.getText().toString().trim().isEmpty()){
                    editTextPassR.setError("Campo Requerido");
                    editTextPassR.requestFocus();
                }else if (!editTextPassR.getText().toString().trim().equals(editTextPass.getText().toString().trim())){
                    editTextPass.setError("Las Contraseña no Son Iguales");
                    editTextPassR.setError("Las Contraseña no Son Iguales");
                }else {
                    String pass = editTextPass.getText().toString().trim();
                    AsyncSetPass asyncSetPass = new AsyncSetPass(pass);
                    asyncSetPass.execute();
                    alertDialog.dismiss();
                }

            }
        });

        return alertDialog ;
    }

    public class AsyncSetPass extends AsyncTask<Void,Void,Boolean> {
        String Clave ;
        Boolean resp;
        public  AsyncSetPass(String Clave){
            this.Clave = Clave;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            Modificaciones modificaciones = new Modificaciones();
            resp=modificaciones.modificar_clave(ID_Usuario,Clave,usuario_);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (resp){
                Toast.makeText(getContext(),"Actualizado Existosamente",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(),"Fallo al Actualizar",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class AsyncGet extends AsyncTask<Void,Void,Boolean> {
        Usuarios usuarios = new Usuarios();
        ArrayList Usuarios,id;
        int ID,index;
        @Override
        protected Boolean doInBackground(Void... voids) {
            usuarios.Get_user_like(usuario_);
            Usuarios= usuarios.getNOMBRE_USUARIO();
            id= usuarios.getID_USUARIO();
            try {
                if (Usuarios.size()>1){
                    index = Usuarios.indexOf(usuario_);
                    ID = Integer.parseInt(id.get(index).toString());
                }else {
                    index = Usuarios.indexOf(usuario_);
                    ID = Integer.parseInt(id.get(index).toString());
                }
                if (nivel_ == 3 || nivel_ == 5){
                    usuarios.Get_an_user_adminlab_instructor(ID);
                }else {
                    usuarios.Get_an_user(ID);
                }
                ID_Usuario = ID;

            }catch (Exception ex){
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (!aBoolean){
                nombre.setText("Sin Datos");
                usuarioN.setText(usuarios.getA_Nombre_Usuario());
                nivel.setText("Nivel: "+usuarios.getA_Nivel());
                facultad.setText("Sin Datos");
                lab.setText("");
            }
            if (nivel_ == 3 || nivel_ == 5){
                nombre.setText(usuarios.getA_nombre());
                usuarioN.setText(usuarios.getA_Nombre_Usuario());
                nivel.setText("Nivel: "+usuarios.getA_Nivel());
                facultad.setText("Facultad: "+usuarios.getA_Facultad());
                lab.setText("Laboratorio: "+usuarios.getA_Lab());
            }
            else {
                nombre.setText(usuarios.getA_nombre());
                usuarioN.setText(usuarios.getA_Nombre_Usuario());
                nivel.setText("Nivel: "+usuarios.getA_Nivel());
                facultad.setText("Facultad: "+usuarios.getA_Facultad());
                lab.setText("");
            }

        }
    }
}
