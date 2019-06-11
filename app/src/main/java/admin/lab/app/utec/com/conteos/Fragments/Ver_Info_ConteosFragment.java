package admin.lab.app.utec.com.conteos.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import admin.lab.app.utec.com.conteos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ver_Info_ConteosFragment extends Fragment {


    public Ver_Info_ConteosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver__info__conteos, container, false);
    }


    public class Async_get_Clases extends AsyncTask<Void,Void,Boolean> {

        int Tipo;
        public Async_get_Clases (int Tipo ){
            this.Tipo = Tipo;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {

            switch (Tipo){

            }
            return null;
        }
    }

}
