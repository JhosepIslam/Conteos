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

public class Reportes {

    private  String METHOD_NAME ;
    private   String SOAP_ACTION;
    Conexion conexion = new Conexion();

    ArrayList FACULTADES , CANTIDADES;

    public ArrayList getFACULTADES() {
        return FACULTADES;
    }

    public void setFACULTADES(ArrayList FACULTADES) {
        this.FACULTADES = FACULTADES;
    }

    public ArrayList getCANTIDADES() {
        return CANTIDADES;
    }

    public void setCANTIDADES(ArrayList CANTIDADES) {
        this.CANTIDADES = CANTIDADES;
    }


    public void getReporteFromServer(){
        METHOD_NAME="ListarEdificiosJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/ListarEdificiosJSON";

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
            ArrayList facultades = new ArrayList<String>() ;
            ArrayList cantidades = new ArrayList<String>() ;



            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);

               String nombre = v.getString("NOMBRE");
                if (!nombre.isEmpty() && nombre !=null){
                    facultades.add(v.getString("NOMBRE"));
                    cantidades.add(v.getString("NUMERO_PLANTAS"));
                }
            }

            setFACULTADES(facultades);
            setCANTIDADES(cantidades);

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }

    }
}
