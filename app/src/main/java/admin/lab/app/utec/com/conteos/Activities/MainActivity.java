package admin.lab.app.utec.com.conteos.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import admin.lab.app.utec.com.conteos.Fragments.AgreagarUsuarioFragment;
import admin.lab.app.utec.com.conteos.Fragments.AgregarConteoFragment;
import admin.lab.app.utec.com.conteos.Fragments.AgregarEdificioFragment;
import admin.lab.app.utec.com.conteos.Fragments.CicloFragment;
import admin.lab.app.utec.com.conteos.Fragments.ClasesFragment;
import admin.lab.app.utec.com.conteos.Fragments.DiaHoraParcial_Fragment;
import admin.lab.app.utec.com.conteos.Fragments.DiasHorasFragment;
import admin.lab.app.utec.com.conteos.Fragments.HomeFragment;
import admin.lab.app.utec.com.conteos.Fragments.LaboratoriosFragment;
import admin.lab.app.utec.com.conteos.Fragments.ParcialesFragment;
import admin.lab.app.utec.com.conteos.Fragments.PerfilFragment;
import admin.lab.app.utec.com.conteos.Fragments.ReportesFragment;
import admin.lab.app.utec.com.conteos.Fragments.UsuariosFragment;
import admin.lab.app.utec.com.conteos.Fragments.VerClasesFragment;
import admin.lab.app.utec.com.conteos.Fragments.Ver_Info_ConteosFragment;
import admin.lab.app.utec.com.conteos.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String name="";

    public int getNivel() {
        return nivel;
    }

    int nivel=0;
    TextView nameU, levelU;
    DrawerLayout drawer;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        drawer=  findViewById(R.id.drawer_layout);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            name = bundle.getString("name");
            nivel = Integer.parseInt(bundle.getString("level"));
        }


        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        nameU=hView.findViewById(R.id.textViewNameUser);
        levelU=hView.findViewById(R.id.textViewLevelUser);
        nameU.setText(name);
        String lvl=null;
        switch (nivel){
            case 1:
                lvl="Decano";
                break;
            case 2:
                lvl="Administrador";
                break;
            case 3:
                lvl="Admin_lab";
                break;
            case 4:
                lvl="Docente";
                break;
            case 5:
                lvl="Instructor";
                break;
            case 33:
                lvl = "Asistente";
                nivel=3;
                break;

        }

        levelU.setText(lvl);
        navigationView.setNavigationItemSelectedListener(this);

        setFragmentByDefault();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        boolean transaction=false;
        String tag=null;
        switch (id)
        {
            case R.id.nav_Usuarios:
                fragment = new UsuariosFragment();
                tag="usuario";
                transaction=true;
                break;
            case R.id.nav_Home:
                 fragment= new HomeFragment();
                 tag="home";
                 transaction=true;
                break;
            case  R.id.nav_AgregarConteo:
                fragment= new AgregarConteoFragment();
                tag="Agregar_conteo";
                transaction=true;
                break;
            case  R.id.nav_AgregarUSuarios:
                fragment = new AgreagarUsuarioFragment();
                tag="Agregar_Usuario";
                transaction=true;
                break;
            case  R.id.nav_Clases:
                fragment= new Ver_Info_ConteosFragment();
                tag="Clases";
                transaction=true;
                break;
            case R.id.nav_session:
                Alert().show();
                item.setChecked(true);
                break;
            case R.id.nav_Perfil:
                fragment = new PerfilFragment();
                tag="Perfil";
                transaction=true;
                break;
            case  R.id.nav_AgregarEdificio:
                fragment = new AgregarEdificioFragment();
                tag="Agregar_Edificio";
                transaction=true;
                break;
            case R.id.nav_Ciclos:
                fragment = new CicloFragment();
                tag="Ciclos";
                transaction=true;
                break;
            case R.id.nav_Parciales:
                fragment = new ParcialesFragment();
                tag="Parciales";
                transaction=true;
                break;
            case R.id.nav_Clases_inf:
                fragment = new VerClasesFragment();
                tag="ClasesInfo";
                transaction=true;
                break;
            case R.id.nav_Reportes:
                fragment = new ReportesFragment();
                tag="Reporte";
                transaction=true;
                break;
            case R.id.nav_AgregarLabs:
                fragment = new LaboratoriosFragment();
                tag="Labs";
                transaction=true;
                break;
        }
        if (transaction){
            changeFragment(fragment,tag);

            getSupportActionBar().setTitle(item.getTitle());
            item.setChecked(true);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;

    }

    private void changeFragment(Fragment fragment, String tag){
        Bundle args = new Bundle();
        args.putString("usuario",name);
        args.putString("nivel", (""+nivel));
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.content_frame,fragment,tag).commit();

        getSupportActionBar();
    }




    public void setFragmentByDefault(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu= navigationView.getMenu();
        MenuItem item;
        switch (nivel){
            case 1:
                nav_Menu.findItem(R.id.nav_Parciales).setVisible(false);
                nav_Menu.findItem(R.id.nav_Clases).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarEdificio).setVisible(false);
                nav_Menu.findItem(R.id.nav_Usuarios).setVisible(false);
                nav_Menu.findItem(R.id.nav_Ciclos).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarUSuarios).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarConteo).setVisible(false);
                nav_Menu.findItem(R.id.nav_Clases_inf).setVisible(false);
                nav_Menu.findItem(R.id.nav_Reportes).setVisible(false);
                break;
            case 2:
                nav_Menu.findItem(R.id.nav_Reportes).setVisible(false);
                break;
            case 3:
                nav_Menu.findItem(R.id.nav_Reportes).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarLabs).setVisible(false);
                nav_Menu.findItem(R.id.nav_Reportes).setVisible(false);
                nav_Menu.findItem(R.id.nav_Parciales).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarEdificio).setVisible(false);
                nav_Menu.findItem(R.id.nav_Ciclos).setVisible(false);
                nav_Menu.findItem(R.id.nav_Usuarios).setVisible(false);
                nav_Menu.findItem(R.id.nav_Clases_inf).setVisible(false);
                break;
            case 4:
                nav_Menu.findItem(R.id.nav_Reportes).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarLabs).setVisible(false);
                nav_Menu.findItem(R.id.nav_Reportes).setVisible(false);
                nav_Menu.findItem(R.id.nav_Parciales).setVisible(false);
                nav_Menu.findItem(R.id.nav_Clases).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarEdificio).setVisible(false);
                nav_Menu.findItem(R.id.nav_Usuarios).setVisible(false);
                nav_Menu.findItem(R.id.nav_Ciclos).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarUSuarios).setVisible(false);
                nav_Menu.findItem(R.id.nav_Clases_inf).setVisible(false);
                break;
            case 5:
                nav_Menu.findItem(R.id.nav_Reportes).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarLabs).setVisible(false);
                nav_Menu.findItem(R.id.nav_Reportes).setVisible(false);
                nav_Menu.findItem(R.id.nav_Parciales).setVisible(false);
                nav_Menu.findItem(R.id.nav_Clases).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarEdificio).setVisible(false);
                nav_Menu.findItem(R.id.nav_Usuarios).setVisible(false);
                nav_Menu.findItem(R.id.nav_Ciclos).setVisible(false);
                nav_Menu.findItem(R.id.nav_AgregarUSuarios).setVisible(false);
                nav_Menu.findItem(R.id.nav_Clases_inf).setVisible(false);
                break;
        }
        changeFragment(new HomeFragment(),"Home");
        item =navigationView.getMenu().getItem(0);
        item.setChecked(true);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //buscar un fragmento con el tag que se le asignó
        HomeFragment fragment =(HomeFragment) getSupportFragmentManager().findFragmentByTag("Home");
        //verificar que el fragment este activo y que no este vacio
        if (fragment != null && fragment.isVisible()) {
            //dejar la accion por defecto de el boton back del hardware
            return super.onKeyDown(keyCode, event);
        }
        else {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

                AgreagarUsuarioFragment fragment3 =(AgreagarUsuarioFragment) getSupportFragmentManager().findFragmentByTag("agregar_user");
                DiaHoraParcial_Fragment fragment2 =(DiaHoraParcial_Fragment) getSupportFragmentManager().findFragmentByTag("Fechas_Parcial");
                DiasHorasFragment fragment4 =(DiasHorasFragment) getSupportFragmentManager().findFragmentByTag("Dia_Hora");
                //verificar que el fragment este activo y que no este vacio
                if (fragment2 != null && fragment2.isVisible()) {
                    changeFragment(new ParcialesFragment(),"Parciales");
                }
                else if (fragment4 != null && fragment4.isVisible()){
                    changeFragment(new ParcialesFragment(),"Parciales");
                }
                else if (fragment3 != null && fragment3.isVisible()){
                    changeFragment(new UsuariosFragment(),"Usuarios");
                }

                else{
                    changeFragment(new HomeFragment(),"Home");
                    NavigationView navigationView = findViewById(R.id.nav_view);
                    Menu nav_Menu= navigationView.getMenu();
                    MenuItem item;

                    item =navigationView.getMenu().getItem(0);
                    item.setChecked(true);
                    getSupportActionBar().setTitle(item.getTitle());
                }
                return true;
            }
        }
        return  false;
    }


    private AlertDialog Alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setIcon(R.drawable.cerrarsesion);
        builder.setMessage("¿Esta seguro  que quiere Cerrar Sesión?")
                .setTitle("Advertencia");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                prefs.edit().clear().apply();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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
}
