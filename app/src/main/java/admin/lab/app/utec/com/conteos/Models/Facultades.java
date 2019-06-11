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

public class Facultades {

    private ArrayList Facultad;



    Conexion conexion = new Conexion();
    String METHOD_NAME="ListarFacultadesJSON";
    String SOAP_ACTION="http://apoyo.conteoutec.org/ListarFacultadesJSON";




    public ArrayList getFacultad() {
        return Facultad;
    }

    public void setFacultad(ArrayList NOMBRE) {
        this.Facultad = NOMBRE;
    }



    public int  get_facultad(String usuario){

        METHOD_NAME="ID_Facultad_UsuarioJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/ID_Facultad_UsuarioJSON";
        int id=0;

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("nombre_usuario",usuario);
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

            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                id=v.getInt("FACULTAD");

            }
            return id;

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
            return 0;
        }

    }

    public void getFacultadesFromServer(){

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
                String nombre =v.getString("NOMBRE");
                Log.d("res",nombre);
                if (!nombre.isEmpty() && nombre !=null){
                    //setNOMBRE(nombre);
                    list.add(nombre);
                }
            }
            setFacultad(list);
        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }

    }

}
