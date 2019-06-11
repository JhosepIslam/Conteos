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

public class Ciclo {

    private  String METHOD_NAME ;
    private   String SOAP_ACTION;


    public ArrayList getID_CICLO() {
        return ID_CICLO;
    }

    public void setID_CICLO(ArrayList ID_CICLO) {
        this.ID_CICLO = ID_CICLO;
    }

    public ArrayList getFECHA_INICIO() {
        return FECHA_INICIO;
    }

    public void setFECHA_INICIO(ArrayList FECHA_INICIO) {
        this.FECHA_INICIO = FECHA_INICIO;
    }

    public ArrayList getFECHA_FIN() {
        return FECHA_FIN;
    }

    public void setFECHA_FIN(ArrayList FECHA_FIN) {
        this.FECHA_FIN = FECHA_FIN;
    }

    public ArrayList getESTADO() {
        return ESTADO;
    }

    public void setESTADO(ArrayList ESTADO) {
        this.ESTADO = ESTADO;
    }

    public ArrayList getANIO() {
        return ANIO;
    }

    public void setANIO(ArrayList ANIO) {
        this.ANIO = ANIO;
    }

    Conexion conexion = new Conexion();

    ArrayList ID_CICLO,FECHA_INICIO,FECHA_FIN,ESTADO,ANIO;

    public  void Get_Ciclos_From_Server(){
        METHOD_NAME="Get_ALL_CICLOSJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_ALL_CICLOSJSON";

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
            ArrayList Ciclos = new ArrayList<String>() ;
            ArrayList Inicios = new ArrayList<String>() ;
            ArrayList Fin = new ArrayList<String>() ;
            ArrayList Estados = new ArrayList<String>() ;
            ArrayList Anios = new ArrayList<String>() ;
            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String ciclo =v.getString("ID_CICLO");
                String incio =v.getString("FECHA_INICIO");
                String fin =v.getString("FECHA_FIN");
                String estado =v.getString("ESTADO");
                String anio =v.getString("ANIO");


                if (!ciclo.isEmpty() && ciclo !=null){
                    Ciclos.add(ciclo);
                    Inicios.add(incio);
                    Fin.add(fin);
                    Estados.add(estado);
                    Anios.add(anio);

                }
            }
            setID_CICLO(Ciclos);
            setANIO(Anios);
            setESTADO(Estados);
            setFECHA_FIN(Fin);
            setFECHA_INICIO(Inicios);

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }

    public int Update_Ciclo(String usuario , String Fecha_I ,String Fecha_F, int Estado, int ID_Ciclo, String anio){

        METHOD_NAME="Modificar_Ciclo";
        SOAP_ACTION="http://apoyo.conteoutec.org/Modificar_Ciclo";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("usuario",usuario);
        request.addProperty("fecha_inicio",Fecha_I);
        request.addProperty("fecha_fin",Fecha_F);
        request.addProperty("estado",Estado);
        request.addProperty("id_ciclo",ID_Ciclo);
        request.addProperty("anio",anio);
        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            resultado=respSoap.toString();

            return Integer.parseInt(resultado);


        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
            return -5;//error de conexion
        }


    }

}
