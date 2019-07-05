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
import java.util.List;


public class Edificios {

    private ArrayList NOMBRE,ABREVIATURA;
    private ArrayList NUM_AULAS;
    private ArrayList NUM_PLANTAS;
    private ArrayList ID;


    Conexion conexion = new Conexion();



    String METHOD_NAME;
    String SOAP_ACTION;


    public ArrayList getNOMBRE() {
        return NOMBRE;
    }

    public ArrayList getABREVIATURA() {
        return ABREVIATURA;
    }

    public void setABREVIATURA(ArrayList ABREVIATURA) {
        this.ABREVIATURA = ABREVIATURA;
    }

    public void setNOMBRE(ArrayList NOMBRE) {
        this.NOMBRE = NOMBRE;
    }
    public void set_NOMBRE(String NOMBRE) {
        this.NOMBRE.add(NOMBRE);
    }

    public ArrayList getNUM_AULAS() {
        return NUM_AULAS;
    }

    public void setNUM_AULAS(ArrayList NUM_AULAS) {
        this.NUM_AULAS = NUM_AULAS;
    }
    public void set_NUM_AULAS(String NUM_AULAS) {
        this.NUM_AULAS.add(NUM_AULAS);
    }

    public ArrayList getNUM_PLANTAS() {
        return NUM_PLANTAS;
    }

    public void setNUM_PLANTAS(ArrayList NUM_PLANTAS) {
        this.NUM_PLANTAS = NUM_PLANTAS;
    }

    public void set_NUM_PLANTAS(String NUM_PLANTAS) {
        this.NUM_PLANTAS.add(NUM_PLANTAS);
    }
    public ArrayList getID() {
        return ID;
    }

    public void setID(ArrayList ID) {

        this.ID = ID;
    }
    public void set_ID(String ID) {

        this.ID.add(ID);
    }


    public void Get_Edificios_Dias_fromServer(String usuario, int nivel){

        Facultades facultades = new Facultades();
        int facultad_id = facultades.get_facultad(usuario);

        if (nivel ==2 ){
            METHOD_NAME="Get_Edificio_Dia_AdminJSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Edificio_Dia_AdminJSON";
        }
        else {
            METHOD_NAME="Get_Edificio_DiaJSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Edificio_DiaJSON";
        }

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        if (nivel!=2) {
            request.addProperty("facultad", facultad_id);
        }
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
            ArrayList nombres = new ArrayList<String>() ;

            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String nombre =v.getString("NOMBRE");
                if (!nombre.isEmpty() && nombre !=null){
                    nombres.add(nombre);
                }
            }
            setNOMBRE(nombres);
        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }

    public void getEdificioFromServer(){
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
            ArrayList nombres = new ArrayList<String>() ;
            ArrayList plantas = new ArrayList<String>() ;
            ArrayList aulas = new ArrayList<String>() ;
            ArrayList ids = new ArrayList<String>() ;
            ArrayList Abrev = new ArrayList<String>() ;


            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String nombre =v.getString("NOMBRE");
                String planta =v.getString("NUMERO_PLANTAS");
                String aula =v.getString("NUM_AULAS");
                String id =v.getString("ID_EDIFICIO");
                if (!nombre.isEmpty() && nombre !=null){
                    nombres.add(nombre);
                    plantas.add(planta);
                    aulas.add(aula);
                    ids.add(id);
                    Abrev.add(v.getString("ABREVIATURA"));
                }
            }
            setID(ids);
            setNUM_AULAS(aulas);
            setNUM_PLANTAS(plantas);
            setNOMBRE(nombres);
            setABREVIATURA(Abrev);
        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }

    }

    public int setEdificio(String nombre, int pisos, int aulas, String usuario, String Abreviatura){
        METHOD_NAME="InsertarEdificio";
        SOAP_ACTION="http://apoyo.conteoutec.org/InsertarEdificio";


        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("Nombre",nombre);
        request.addProperty("Num_plantas",pisos);
        request.addProperty("num_aulas",aulas);
        request.addProperty("usuario",usuario);
        request.addProperty("Abreviatura",Abreviatura);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

            return Integer.parseInt(respSoap.toString());
        }
        catch (Exception ex){
            return -1;
        }
    }


    public boolean UpdateNombre(String nombre, String usuario,int ID_Edificio){
        METHOD_NAME="Actualizar_Edificio_Nombre";
        SOAP_ACTION="http://apoyo.conteoutec.org/Actualizar_Edificio_Nombre";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("Nuevo_Nombre",nombre);
        request.addProperty("USuario",usuario);
        request.addProperty("ID_Edificio",ID_Edificio);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(respSoap.toString());
        }
        catch (Exception ex){
            return false;
        }
    }

    public boolean UpdateAulas(int Aulas, String usuario,int ID_Edificio){
        METHOD_NAME="Actualizar_Edificio_Aulas";
        SOAP_ACTION="http://apoyo.conteoutec.org/Actualizar_Edificio_Aulas";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("Nueva_cantidad",Aulas);
        request.addProperty("USuario",usuario);
        request.addProperty("ID_Edificio",ID_Edificio);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(respSoap.toString());
        }
        catch (Exception ex){
            return false;
        }
    }
    public boolean UpdatePisos(int Pisos, String usuario,int ID_Edificio){
        METHOD_NAME="Actualizar_Edificio_Plantas";
        SOAP_ACTION="http://apoyo.conteoutec.org/Actualizar_Edificio_Plantas";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("Nueva_cantidad",Pisos);
        request.addProperty("USuario",usuario);
        request.addProperty("ID_Edificio",ID_Edificio);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(respSoap.toString());
        }
        catch (Exception ex){
            return false;
        }
    }

    public boolean UpdateAbreviatura(String Abreviatura, String usuario,int ID_Edificio){
        METHOD_NAME="Actualizar_Edificios_Abreviatura";
        SOAP_ACTION="http://apoyo.conteoutec.org/Actualizar_Edificios_Abreviatura";
        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("Abreviatura",Abreviatura);
        request.addProperty("USuario",usuario);
        request.addProperty("ID_Edificio",ID_Edificio);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(respSoap.toString());
        }
        catch (Exception ex){
            return false;
        }
    }





}
