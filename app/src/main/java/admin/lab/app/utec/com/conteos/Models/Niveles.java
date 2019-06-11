package admin.lab.app.utec.com.conteos.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class Niveles {

    private ArrayList Niveles;

    Conexion conexion = new Conexion();
    final String METHOD_NAME="ListarNivelesJSON";
    final String SOAP_ACTION="http://apoyo.conteoutec.org/ListarNivelesJSON";

    public ArrayList getNiveles() {
        return Niveles;
    }

    public void setNiveles(ArrayList niveles) {
        Niveles = niveles;
    }

    public void getNivelesFromServer(){

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            resultado=respSoap.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            JSONArray obtener = jsonObj.getJSONArray("Table");

            int a=obtener.length();
            ArrayList list = new ArrayList<String>() ;

            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String nombre =v.getString("TIPO");
                Log.d("res",nombre);
                if (!nombre.isEmpty() && nombre !=null){
                    //setNOMBRE(nombre);
                    list.add(nombre);
                }
            }
            setNiveles(list);
        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }

}
