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

public class Laboratorios {

    Conexion conexion = new Conexion();
    private String METHOD_NAME;
    private String SOAP_ACTION;

    private ArrayList Laboratorios,Capaciad,ID,Edificio,Abreviatura;


    public ArrayList getCapaciad() {
        return Capaciad;
    }

    public void setCapaciad(ArrayList capaciad) {
        Capaciad = capaciad;
    }

    public ArrayList getID() {
        return ID;
    }

    public void setID(ArrayList ID) {
        this.ID = ID;
    }

    public ArrayList getEdificio() {
        return Edificio;
    }

    public void setEdificio(ArrayList edificio) {
        Edificio = edificio;
    }

    public ArrayList getAbreviatura() {
        return Abreviatura;
    }

    public void setAbreviatura(ArrayList abreviatura) {
        Abreviatura = abreviatura;
    }

    public ArrayList getLaboratorio() {
        return Laboratorios;
    }

    public void setLaboratorio(ArrayList laboratorio) {
        Laboratorios = laboratorio;
    }

    public void getLaboratorioFromServer(){
        METHOD_NAME="ListarLaboratoriosJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/ListarLaboratoriosJSON";
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
                String nombre =v.getString("LABORATORIO");
                if (!nombre.isEmpty() && nombre !=null){
                    list.add(nombre);
                }
            }
            setLaboratorio(list);
        }catch (Exception ex){
        }
    }


    public void getLaboratorioFromServerCompleto(){
        METHOD_NAME="ListarLaboratoriosJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/ListarLaboratoriosJSON";
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
            ArrayList ID = new ArrayList<String>() ;
            ArrayList Capacidad = new ArrayList<String>() ;
            ArrayList Edificio = new ArrayList<String>() ;
            ArrayList Abrev = new ArrayList<String>() ;


            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String nombre =v.getString("LABORATORIO");
                String abrev =v.getString("ABREVIATURA");
                if (abrev.equals(null) || abrev.isEmpty()){
                    abrev="-";
                }
                if (!nombre.isEmpty() && nombre !=null){
                    list.add(nombre);
                    ID.add(v.getString("ID_LAB"));
                    Capacidad.add(v.getString("CAPACIDAD"));
                    Edificio.add(v.getString("EDIFICIO"));
                    Abrev.add(abrev);
                }
            }
            setLaboratorio(list);
            setAbreviatura(Abrev);
            setCapaciad(Capacidad);
            setEdificio(Edificio);
            setID(ID);
        }catch (Exception ex){
            Log.d("a",ex.getMessage());
        }

    }

    public boolean Update(int id, String usuario,String Nombre,int capacidad, String Abreviatura, String Edificio){

        METHOD_NAME="Actualizar_Laboratorios";
        SOAP_ACTION="http://apoyo.conteoutec.org/Actualizar_Laboratorios";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("USuario",usuario);
        request.addProperty("Id_Lab",id);
        request.addProperty("nombre",Nombre);
        request.addProperty("edificio",Edificio);
        request.addProperty("capacidad",capacidad);
        request.addProperty("abreviatura",Abreviatura);
        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            resultado=respSoap.toString();

            return Boolean.parseBoolean(resultado);


        }catch (Exception ex){
            return false;
        }
    }

    public boolean Add( String usuario,String Nombre,int capacidad, String Abreviatura, String Edificio){

        METHOD_NAME="Insertar_Laboratorio";
        SOAP_ACTION="http://apoyo.conteoutec.org/Insertar_Laboratorio";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);

        request.addProperty("nombre",Nombre);
        request.addProperty("edificio",Edificio);
        request.addProperty("capacidad",capacidad);
        request.addProperty("abreviatura",Abreviatura);
        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            resultado=respSoap.toString();

            return Boolean.parseBoolean(resultado);


        }catch (Exception ex){
            return false;
        }
    }


}
