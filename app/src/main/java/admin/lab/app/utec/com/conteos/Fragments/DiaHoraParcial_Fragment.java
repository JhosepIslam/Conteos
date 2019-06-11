package admin.lab.app.utec.com.conteos.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import admin.lab.app.utec.com.conteos.Adapters.DiaParcialAdapter;
import admin.lab.app.utec.com.conteos.Adapters.UsuariosAdapter;
import admin.lab.app.utec.com.conteos.Models.Parciales;
import admin.lab.app.utec.com.conteos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaHoraParcial_Fragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Parciales parciales = new Parciales();
    private String usuario_;
    private String nivel_;
    private String id_parcial_;
    private String ciclo_;

    public DiaHoraParcial_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_dia_hora_parcial_, container, false);
        usuario_= getArguments().getString("usuario");
        nivel_= getArguments().getString("nivel");
        ciclo_= getArguments().getString("Ciclo");
        id_parcial_= getArguments().getString("ID");
        return view;
    }
    private void changeFragment(Fragment fragment, String tag,int ID, String fecha){
        Bundle args = new Bundle();
        args.putString("usuario",usuario_);
        args.putString("nivel", (""+nivel_));
        args.putInt("ID", (ID));
        args.putString("Fecha", (""+fecha));
        args.putInt("Ciclo", (Integer.parseInt(ciclo_)));
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,tag).commit();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Async_Get_Fechas async_get_fechas = new Async_Get_Fechas();
        async_get_fechas.execute();
    }

    public class Async_Get_Fechas extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Void... voids) {
            parciales.Get_Fechas_From_Server(Integer.parseInt(id_parcial_));
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            try {
                recyclerView = getView().findViewById(R.id.recyclerViewDias_PArciales);
                layoutManager = new GridLayoutManager(getContext(),2);
                myAdapter =  new DiaParcialAdapter(parciales.getDIA(), parciales.getFECHA(), R.layout.card_view_dias_parciales, new DiaParcialAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String Fecha) {
                        changeFragment(new DiasHorasFragment(),"Dia_Hora",Integer.parseInt(id_parcial_),Fecha);
                    }
                });
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(layoutManager);

            }catch (Exception ex){

            }
        }
    }



}
