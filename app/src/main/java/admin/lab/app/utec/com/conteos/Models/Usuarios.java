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

public class Usuarios {
    private  String METHOD_NAME ;
    private   String SOAP_ACTION;
    private ArrayList NOMBRE,NOMBRE_USUARIO,ID_USUARIO,NIVEL,FACULTAD;
    private String A_nombre, A_Nombre_Usuario,A_Nivel, A_Facultad ,A_Clave, A_Lab;
    Conexion conexion = new Conexion();


    public String getA_nombre() {
        return A_nombre;
    }

    public String getA_Nombre_Usuario() {
        return A_Nombre_Usuario;
    }

    public String getA_Nivel() {
        return A_Nivel;
    }

    public String getA_Facultad() {
        return A_Facultad;
    }

    public String getA_Clave() {
        return A_Clave;
    }

    public String getA_Lab() {
        return A_Lab;
    }

    public ArrayList getNOMBRE() {
        return NOMBRE;
    }
    public void setNOMBRE(ArrayList NOMBRE) {
        this.NOMBRE = NOMBRE;
    }
    public ArrayList getNOMBRE_USUARIO() {
        return NOMBRE_USUARIO;
    }
    public void setNOMBRE_USUARIO(ArrayList NOMBRE_USUARIO) {
        this.NOMBRE_USUARIO = NOMBRE_USUARIO;
    }
    public ArrayList getID_USUARIO() {
        return ID_USUARIO;
    }
    public void setID_USUARIO(ArrayList ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }
    public ArrayList getNIVEL() {
        return NIVEL;
    }
    public void setNIVEL(ArrayList NIVEL) {
        this.NIVEL = NIVEL;
    }
    public ArrayList getFACULTAD() {
        return FACULTAD;
    }
    public void setFACULTAD(ArrayList FACULTAD) {
        this.FACULTAD = FACULTAD;
    }


    public void getUsuariosFromServer(){
        METHOD_NAME = "ListarUsuariosJSON";
        SOAP_ACTION = "http://apoyo.conteoutec.org/ListarUsuariosJSON";
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
            ArrayList usuarios = new ArrayList<String>() ;
            ArrayList niveles = new ArrayList<String>() ;
            ArrayList ids = new ArrayList<String>() ;
            ArrayList facultades = new ArrayList();


            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String nombre =v.getString("NOMBRE");
                String usuario =v.getString("NOMBRE_USUARIO");
                String nivel =v.getString("TIPO");
                String id =v.getString("ID_USUARIO");
                String facultad = v.getString("FACULTAD");
                if (!nombre.isEmpty() && nombre !=null){
                    nombres.add(nombre);
                    usuarios.add(usuario);
                    niveles.add(nivel);
                    ids.add(id);
                    facultades.add(facultad);
                }
            }
            setFACULTAD(facultades);
            setID_USUARIO(ids);
            setNIVEL(niveles);
            setNOMBRE(nombres);
            setNOMBRE_USUARIO(usuarios);
        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }

    public void  Get_an_user(int ID){
        METHOD_NAME="Get_an_UserJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_an_UserJSON";
        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("ID",ID);


        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);
        A_nombre ="";
        A_Nombre_Usuario="";
        A_Clave="";
        A_Facultad="";
        A_Nivel="";

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();
            String resultado = respSoap.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            JSONArray obtener = jsonObj.getJSONArray("Table");

            int length =  obtener.length();

            for (int i=0 ; i<length ; i++){
                JSONObject v = obtener.getJSONObject(i);
                A_nombre =v.getString("NOMBRE");
                A_Nombre_Usuario = v.getString("NOMBRE_USUARIO");
                A_Clave = v.getString("CLAVE");
                A_Facultad = v.getString("FACULTAD");
                A_Nivel = v.getString("TIPO");
            }
        }
        catch (Exception ex){

            Log.d("error",ex.getMessage());
        }
    }

    public void  Get_user_like(String usuario){
        METHOD_NAME="Get_User_LikeJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_User_LikeJSON";
        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("usuario",usuario);


        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();
            String resultado = respSoap.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            JSONArray obtener = jsonObj.getJSONArray("Table");

            int length =  obtener.length();

            ArrayList nombres = new ArrayList<String>() ;
            ArrayList usuarios = new ArrayList<String>() ;
            ArrayList niveles = new ArrayList<String>() ;
            ArrayList ids = new ArrayList<String>() ;
            ArrayList facultades = new ArrayList();
            for (int i = 0; i < length; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String nombre =v.getString("NOMBRE");
                String usuario_ =v.getString("NOMBRE_USUARIO");
                String nivel =v.getString("TIPO");
                String id =v.getString("ID_USUARIO");
                String facultad = v.getString("FACULTAD");
                if (!nombre.isEmpty() && nombre !=null){
                    nombres.add(nombre);
                    usuarios.add(usuario_);
                    niveles.add(nivel);
                    ids.add(id);
                    facultades.add(facultad);
                }
            }
            setFACULTAD(facultades);
            setID_USUARIO(ids);
            setNIVEL(niveles);
            setNOMBRE(nombres);
            setNOMBRE_USUARIO(usuarios);
        }
        catch (Exception ex){

            Log.d("error",ex.getMessage());
        }
    }

    public void  Get_an_user_adminlab_instructor(int ID){
        METHOD_NAME="Get_an_User_Admin_IntructorJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_an_User_Admin_IntructorJSON";
        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("ID",ID);


        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);
        A_nombre ="";
        A_Nombre_Usuario="";
        A_Clave="";
        A_Facultad="";
        A_Nivel="";
        A_Lab="";

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();
            String resultado = respSoap.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            JSONArray obtener = jsonObj.getJSONArray("Table");

            int length =  obtener.length();

            for (int i=0 ; i<length ; i++){
                JSONObject v = obtener.getJSONObject(i);
                A_nombre =v.getString("NOMBRE");
                A_Nombre_Usuario = v.getString("NOMBRE_USUARIO");
                A_Clave = v.getString("CLAVE");
                A_Facultad = v.getString("FACULTAD");
                A_Nivel = v.getString("TIPO");
                A_Lab= v.getString("LABORATORIO");
            }
        }
        catch (Exception ex){

            Log.d("error",ex.getMessage());
        }
    }
}
