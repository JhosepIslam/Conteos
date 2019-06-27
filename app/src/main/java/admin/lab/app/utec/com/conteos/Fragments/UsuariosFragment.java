    package admin.lab.app.utec.com.conteos.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import admin.lab.app.utec.com.conteos.Adapters.UsuariosAdapter;
import admin.lab.app.utec.com.conteos.Models.Facultades;
import admin.lab.app.utec.com.conteos.Models.Laboratorios;
import admin.lab.app.utec.com.conteos.Models.Modificaciones;
import admin.lab.app.utec.com.conteos.Models.Niveles;
import admin.lab.app.utec.com.conteos.Models.Usuarios;
import admin.lab.app.utec.com.conteos.R;


    /**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {

        Modificaciones modificaciones = new Modificaciones();
        Usuarios usuarios = new Usuarios();
        Niveles niveles = new Niveles();
         Laboratorios laboratorios = new Laboratorios();
         Facultades facultades = new Facultades();
         private EditText txtBuscarUsuario;
        private RecyclerView recyclerView;
        String usuario_,nivel_;
        ShimmerFrameLayout shimmerFrameLayout;
        private EditText editTextNueva;
        private EditText editTextRepetir;
        private RecyclerView.Adapter myAdapter;
        private RecyclerView.LayoutManager layoutManager;
        AsyncGet asyncGet = new AsyncGet();
        FloatingActionButton floatingActionButtonAdd;
    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);
        usuario_= getArguments().getString("usuario");
        nivel_= getArguments().getString("nivel");
        return view;
    }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            shimmerFrameLayout = getView().findViewById(R.id.shimmerLayout);
            asyncGet.execute();
            floatingActionButtonAdd = getView().findViewById(R.id.fabAddUser);
            txtBuscarUsuario  = getView().findViewById(R.id.txtBuscarUsuario);
            floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fragment = new AgreagarUsuarioFragment();

                    changeFragment(fragment,"agregar_user");
                }
            });

            txtBuscarUsuario.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    String nombre = txtBuscarUsuario.getText().toString().trim();
                    if (nombre.isEmpty()){
                        AsyncGet asyncGet1 = new AsyncGet();
                        asyncGet1.execute();
                    }
                    else {
                        Async_search async_search = new Async_search(nombre);
                        async_search.execute();
                    }
                }
            });
        }

        private void changeFragment(Fragment fragment, String tag){
            Bundle args = new Bundle();
            args.putString("usuario",usuario_);
            args.putString("nivel", (""+nivel_));
            fragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,tag).commit();
        }
        private AlertDialog Alert(final int id_Usuario , String Nombre, String Usuario , String Facultad , String Nivel, String Lab, final String Estado){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.info_usuario_dialog, null);
            builder.setView(v);
            final AlertDialog alertDialog =builder.create();

            final LinearLayout linearLayoutPassEdit = v.findViewById(R.id.LLEditPassWord);
            final LinearLayout linearLayoutPass1Edit = v.findViewById(R.id.LLEditPassWord1);

            final LinearLayout linearLayoutFacultad = v.findViewById(R.id.LLEditFacultad);
            final LinearLayout linearLayoutLabEdit = v.findViewById(R.id.LLEditLab);

            final Spinner spinnerFacultades = v.findViewById(R.id.sp_editFacultad);
            Spinner spinnerNivels = v.findViewById(R.id.sp_editNivel);
            final Spinner spinnerLab = v.findViewById(R.id.sp_editLaboratorio);

            TextView textViewNombre = v.findViewById(R.id.textViewNombre_dialog);
            TextView textViewUsuario = v.findViewById(R.id.textViewUsuario_dialog);
            TextView textViewNivel = v.findViewById(R.id.textViewNivel_dialog);
            TextView textViewFacultad = v.findViewById(R.id.textViewFacutad_dialog);
            TextView textViewLab = v.findViewById(R.id.textViewLaboratorio_dialog);


            editTextNueva   =    v.findViewById(R.id.txtContraseñaNueva_Edit);
            editTextRepetir  = v.findViewById(R.id.txtContraseñaNuevaRepetir_Edit);

            final CheckBox checkBoxLabEdit = v.findViewById(R.id.checkboxEditLaboratorio_dialog);
            final CheckBox checkBoxPassEdit = v.findViewById(R.id.checkboxEditPass_dialog);
            final CheckBox checkBoxFacultadEdit = v.findViewById(R.id.checkboxEditFacult_dialog);
            final CheckBox checkBoxResetPass = v.findViewById(R.id.checkboxResetPassWord);
            final CheckBox checkBoxEstadoEdit = v.findViewById(R.id.checkboxEditEstado_dialog);
            if (Estado =="Activo"){
                checkBoxEstadoEdit.setText("Desactivar");
            }else {
                checkBoxEstadoEdit.setText("Activar");
            }



            final ArrayAdapter spinnerAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, niveles.getNiveles());
            spinnerNivels.setAdapter(spinnerAdapter1);

            final ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, facultades.getFacultad());
            spinnerFacultades.setAdapter(spinnerAdapter);



            checkBoxPassEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        linearLayoutPass1Edit.setVisibility(View.VISIBLE);
                        linearLayoutPassEdit.setVisibility(View.VISIBLE);
                    }
                    else {
                        linearLayoutPass1Edit.setVisibility(View.GONE);
                        linearLayoutPassEdit.setVisibility(View.GONE);
                        checkBoxResetPass.setChecked(false);
                    }

                }
            });
            checkBoxResetPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        linearLayoutPass1Edit.setVisibility(View.GONE);
                    }
                    else {
                        linearLayoutPass1Edit.setVisibility(View.VISIBLE);
                    }
                }
            });
            checkBoxFacultadEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        linearLayoutFacultad.setVisibility(View.VISIBLE);
                    }else {
                        linearLayoutFacultad.setVisibility(View.GONE);
                    }
                }
            });
            checkBoxLabEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        linearLayoutLabEdit.setVisibility(View.VISIBLE);
                    }else {
                        linearLayoutLabEdit.setVisibility(View.GONE);
                    }
                }
            });

            textViewFacultad.setText(Facultad);
            textViewNivel.setText(Nivel);
            textViewNombre.setText(Nombre);
            textViewUsuario.setText(Usuario);

            switch (Nivel){


                case "ADMIN_LAB":
                case "INSTRUCTOR":
                    textViewLab.setText(Lab);
                    final ArrayAdapter spinnerAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, laboratorios.getLaboratorio());
                    spinnerLab.setAdapter(spinnerAdapter2);
                    break;
                default:
                    textViewLab.setVisibility(View.GONE);
                    checkBoxLabEdit.setVisibility(View.GONE);
                    break;
            }


            Button btnEviar = v.findViewById(R.id.btnEnviar_dialog);
            final Button btnCancelar = v.findViewById(R.id.btnCacelar_dialog);

            btnEviar.setOnClickListener(new View.OnClickListener() {
                int caso;
                @Override
                public void onClick(View view) {
                    AsyncModificar asyncModificar;
                    if (checkBoxEstadoEdit.isChecked()){
                        //
                        int EstadO_N =1;
                        String es = checkBoxEstadoEdit.getText().toString();
                        if (es =="Desactivar"){
                            EstadO_N = 0;
                        }else {
                            EstadO_N = 1;
                        }
                        AsyncModificarEstado asyncModificarEstado = new AsyncModificarEstado(EstadO_N,id_Usuario);
                        asyncModificarEstado.execute();

                    }
                    if (checkBoxPassEdit.isChecked() && checkBoxLabEdit.isChecked() && checkBoxFacultadEdit.isChecked()){
                        if (checkBoxResetPass.isChecked()){
                            caso=1;
                            asyncModificar= new AsyncModificar(null,spinnerFacultades.getSelectedItem().toString(),spinnerLab.getSelectedItem().toString(),
                                    caso,id_Usuario,usuario_);
                            asyncModificar.execute();
                            alertDialog.dismiss();
                        }
                        else {
                            caso=2;
                            if (comprobarTextos()){
                                String clave = editTextNueva.getText().toString().trim();
                                asyncModificar= new AsyncModificar(clave,spinnerFacultades.getSelectedItem().toString(),spinnerLab.getSelectedItem().toString(),
                                        caso,id_Usuario,usuario_);
                                asyncModificar.execute();
                                alertDialog.dismiss();
                            }
                        }
                    }
                    else if (checkBoxPassEdit.isChecked() && checkBoxLabEdit.isChecked()){
                        if (checkBoxResetPass.isChecked()){
                            caso =3;
                            asyncModificar= new AsyncModificar(null,null,spinnerLab.getSelectedItem().toString(),
                                    caso,id_Usuario,usuario_);
                            asyncModificar.execute();
                            alertDialog.dismiss();
                        }
                        else {
                            caso=4;
                            if (comprobarTextos()){
                                String clave = editTextNueva.getText().toString().trim();
                                asyncModificar= new AsyncModificar(clave,spinnerFacultades.getSelectedItem().toString(),spinnerLab.getSelectedItem().toString(),
                                        caso,id_Usuario,usuario_);
                                asyncModificar.execute();
                                alertDialog.dismiss();
                            }
                        }
                    }
                    else if (checkBoxPassEdit.isChecked() && checkBoxFacultadEdit.isChecked()){
                        if (checkBoxResetPass.isChecked()){
                            caso = 5;
                            asyncModificar= new AsyncModificar(null,spinnerFacultades.getSelectedItem().toString(),null,
                                    caso,id_Usuario,usuario_);
                            asyncModificar.execute();
                            alertDialog.dismiss();
                        }else {
                            caso=6;
                            if (comprobarTextos()){
                                String clave = editTextNueva.getText().toString().trim();
                                asyncModificar= new AsyncModificar(clave,spinnerFacultades.getSelectedItem().toString(),null,
                                        caso,id_Usuario,usuario_);
                                asyncModificar.execute();
                                alertDialog.dismiss();
                            }
                        }
                    }
                    else if (checkBoxFacultadEdit.isChecked() && checkBoxLabEdit.isChecked()){
                        caso=7;
                        asyncModificar= new AsyncModificar(null,spinnerFacultades.getSelectedItem().toString(),spinnerLab.getSelectedItem().toString(),
                                caso,id_Usuario,usuario_);
                        asyncModificar.execute();
                        alertDialog.dismiss();
                    }
                    else if (checkBoxFacultadEdit.isChecked()){
                        caso=8;
                        asyncModificar= new AsyncModificar(null,spinnerFacultades.getSelectedItem().toString(),null,
                                caso,id_Usuario,usuario_);
                        asyncModificar.execute();
                        alertDialog.dismiss();
                    }
                    else if (checkBoxLabEdit.isChecked()){
                        caso=9;
                        asyncModificar= new AsyncModificar(null,null,spinnerLab.getSelectedItem().toString(),
                                caso,id_Usuario,usuario_);
                        asyncModificar.execute();
                        alertDialog.dismiss();
                    }
                    else if (checkBoxPassEdit.isChecked()){
                        if (checkBoxResetPass.isChecked()){
                            caso=10;
                            asyncModificar= new AsyncModificar(null,null,null,
                                    caso,id_Usuario,usuario_);
                            asyncModificar.execute();
                            alertDialog.dismiss();
                        }
                        else {
                            caso=11;
                            if (comprobarTextos()){
                                String clave = editTextNueva.getText().toString().trim();
                                asyncModificar= new AsyncModificar(clave,null,null,
                                        caso,id_Usuario,usuario_);
                                asyncModificar.execute();
                                alertDialog.dismiss();
                            }
                        }
                    }
                    else {
                        alertDialog.dismiss();
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
        private boolean comprobarTextos(){
            if (editTextNueva.getText().toString().trim().isEmpty()){
                editTextNueva.setError("Escriba un Contraseña");
                return false;
            }else if (editTextNueva.getText().toString().trim().length()<=4){
                editTextNueva.setError("La Contraseña es muy corta");
                return false;
            }else if (editTextRepetir.getText().toString().trim().isEmpty()){
                editTextRepetir.setError("Las Contraseñas no son Iguales");
                return false;
            }else if (!editTextRepetir.getText().toString().trim().equals(editTextNueva.getText().toString().trim())){
                editTextRepetir.setError("Las Contraseñas no son Iguales");
                editTextNueva.setError("Las Contraseñas no son Iguales");
                return false;
            }
            else {
                return true;
            }
        }
        public class AsyncModificarEstado extends AsyncTask<Void,Void,Boolean> {

            int Estado,id ;
            boolean mod;
            public AsyncModificarEstado(int Estado,int id){
                this.Estado= Estado;
                this.id = id;
            }
            @Override
            protected Boolean doInBackground(Void... voids) {

                mod =modificaciones.modificar_estado(id,Estado,usuario_);
                return null;

            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                AsyncGet asyncGet = new AsyncGet();
                asyncGet.execute();
            }
        }
        public class AsyncModificar extends AsyncTask<Void,Void,Boolean> {

            String nueva_clave, nueva_facultad,nuevo_lab,Usuario;
            int caso,id_usuario_update;

            boolean r1,r2,r3;


            public AsyncModificar(String nueva_clave, String nueva_facultad , String nuevo_lab,
                                  int caso,int id_usuario_update, String usuario){
                this.nueva_clave = nueva_clave;
                this.nueva_facultad = nueva_facultad;
                this.nuevo_lab = nuevo_lab;
                this.caso = caso;
                this.id_usuario_update = id_usuario_update;
                this.Usuario = usuario;
            }
            @Override
            protected Boolean doInBackground(Void... voids) {
                switch (caso){
                    case 1:
                        r1=modificaciones.reset_clave(id_usuario_update,Usuario);
                        r2=modificaciones.modificar_facultad(id_usuario_update,nueva_facultad,Usuario);
                        r3= modificaciones.modificar_laboratorio(id_usuario_update,nuevo_lab,Usuario);
                        break;
                    case 2:
                        r1=modificaciones.modificar_clave(id_usuario_update,nueva_clave,Usuario);
                        r2=modificaciones.modificar_facultad(id_usuario_update,nueva_facultad,Usuario);
                        r3= modificaciones.modificar_laboratorio(id_usuario_update,nuevo_lab,Usuario);
                        break;
                    case 3:
                        r1=modificaciones.reset_clave(id_usuario_update,Usuario);
                        r3= modificaciones.modificar_laboratorio(id_usuario_update,nuevo_lab,Usuario);
                        break;
                    case 4:
                        r1=modificaciones.modificar_clave(id_usuario_update,nueva_clave,Usuario);
                        r3= modificaciones.modificar_laboratorio(id_usuario_update,nuevo_lab,Usuario);
                        break;
                    case 5:
                        r1=modificaciones.reset_clave(id_usuario_update,Usuario);
                        r2=modificaciones.modificar_facultad(id_usuario_update,nueva_facultad,Usuario);
                        break;
                    case 6:
                        r1=modificaciones.modificar_clave(id_usuario_update,nueva_clave,Usuario);
                        r2=modificaciones.modificar_facultad(id_usuario_update,nueva_facultad,Usuario);
                        break;
                    case 7:
                        r2=modificaciones.modificar_facultad(id_usuario_update,nueva_facultad,Usuario);
                        r3= modificaciones.modificar_laboratorio(id_usuario_update,nuevo_lab,Usuario);
                        break;
                    case 8:
                        r2=modificaciones.modificar_facultad(id_usuario_update,nueva_facultad,Usuario);
                        break;
                    case 9:
                        r3= modificaciones.modificar_laboratorio(id_usuario_update,nuevo_lab,Usuario);
                        break;
                    case 10:
                        r1=modificaciones.reset_clave(id_usuario_update,Usuario);
                        break;
                    case 11:
                        r1=modificaciones.modificar_clave(id_usuario_update,nueva_clave,Usuario);
                        break;
                }

                return null;

            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                AsyncGet asyncGet = new AsyncGet();
                asyncGet.execute();
            }
        }

        public class AsyncGet extends AsyncTask<Void,Void,Boolean> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                shimmerFrameLayout.startShimmer();

            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                usuarios.getUsuariosFromServer();
                try {
                    Thread.sleep(2000);

                } catch(InterruptedException e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                try {
                    recyclerView = getView().findViewById(R.id.recyclerViewUsuarios);
                    layoutManager = new LinearLayoutManager(getContext());
                    myAdapter =  new UsuariosAdapter(usuarios.getNOMBRE(), usuarios.getNOMBRE_USUARIO(), usuarios.getFACULTAD()
                            , usuarios.getNIVEL(), usuarios.getID_USUARIO(),usuarios.getESTADO(), R.layout.card_view_usuario, new UsuariosAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int id_usuario,String nivel, int position) {
                            Async_A_Get async_a_get = new Async_A_Get(nivel,id_usuario);
                            async_a_get.execute();
                        }
                    });
                    recyclerView.setAdapter(myAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                    asyncGet.execute();
                }catch (Exception ex){

                }
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        }

        public class Async_search extends AsyncTask<Void,Void,Boolean> {

            String nombredeUsuario;

            public  Async_search(String nombredeUsuario){
                this.nombredeUsuario = nombredeUsuario;
            }
            @Override
            protected Boolean doInBackground(Void... voids) {
                usuarios.Get_user_like(nombredeUsuario);
                return null;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                try {
                    recyclerView = getView().findViewById(R.id.recyclerViewUsuarios);
                    layoutManager = new LinearLayoutManager(getContext());
                    myAdapter =  new UsuariosAdapter(usuarios.getNOMBRE(), usuarios.getNOMBRE_USUARIO(), usuarios.getFACULTAD()
                            , usuarios.getNIVEL(), usuarios.getID_USUARIO(),usuarios.getESTADO(), R.layout.card_view_usuario, new UsuariosAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int id_usuario,String nivel, int position) {
                            Async_A_Get async_a_get = new Async_A_Get(nivel,id_usuario);
                            async_a_get.execute();
                        }
                    });
                    recyclerView.setAdapter(myAdapter);
                    recyclerView.setLayoutManager(layoutManager);

                }catch (Exception ex){

                }
            }
        }

        public class Async_A_Get extends AsyncTask<Void,Void,Boolean> {

            private String nivel;
            private int id;

            public  Async_A_Get(String nivel, int id){
                this.nivel = nivel;
                this.id = id;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                shimmerFrameLayout.startShimmer();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                switch (nivel){
                    case "ADMIN_LAB":
                    case "INSTRUCTOR":
                        usuarios.Get_an_user_adminlab_instructor(id);
                        laboratorios.getLaboratorioFromServer();
                        break;
                    default:
                        usuarios.Get_an_user(id);
                        laboratorios.getLaboratorioFromServer();
                        break;
                }
                niveles.getNivelesFromServer();
                facultades.getFacultadesFromServer();
                return null;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                switch (nivel){
                    case "ADMIN_LAB":
                    case "INSTRUCTOR":

                            try {
                                if (usuarios.getA_nombre().isEmpty()){
                                    Async_A_Get async_a_get = new Async_A_Get("Secretaria",id);
                                    async_a_get.execute();
                                }else {
                                    Alert(id,usuarios.getA_nombre(),usuarios.getA_Nombre_Usuario()
                                            ,usuarios.getA_Facultad(),usuarios.getA_Nivel(),usuarios.getA_Lab(),usuarios.getA_Estado()).show();
                                }

                            }catch (Exception ex){
                                Async_A_Get async_a_get = new Async_A_Get("Secretaria",id);
                                async_a_get.execute();
                        }
                        break;
                    default:
                        Alert(id,usuarios.getA_nombre(),usuarios.getA_Nombre_Usuario()
                                ,usuarios.getA_Facultad(),usuarios.getA_Nivel(),null,usuarios.getA_Estado()).show();

                        String us = usuarios.getA_Nombre_Usuario();

                        break;
                }


            }
        }


}
