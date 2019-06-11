package admin.lab.app.utec.com.conteos.Models;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Requests  {

    private int type;





    public Requests(int type){
        this.type=type;

    }

}
