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
import java.util.concurrent.atomic.AtomicReference;

public class Parciales {

    private  String METHOD_NAME ;
    private   String SOAP_ACTION;
    Conexion conexion = new Conexion();

    private ArrayList PARCIAL,ID_FECHA,INICIO,FIN,CICLO, FECHA, DIA,HORA_INICIO,HORA_FIN,ID_HORA;

    public ArrayList getID_HORA() {
        return ID_HORA;
    }

    public void setID_HORA(ArrayList ID_HORA) {
        this.ID_HORA = ID_HORA;
    }

    public ArrayList getHORA_INICIO() {
        return HORA_INICIO;
    }

    public void setHORA_INICIO(ArrayList HORA_INICIO) {
        this.HORA_INICIO = HORA_INICIO;
    }

    public ArrayList getHORA_FIN() {
        return HORA_FIN;
    }

    public void setHORA_FIN(ArrayList HORA_FIN) {
        this.HORA_FIN = HORA_FIN;
    }

    public ArrayList getFECHA() {
        return FECHA;
    }

    public void setFECHA(ArrayList FECHA) {
        this.FECHA = FECHA;
    }

    public ArrayList getDIA() {
        return DIA;
    }

    public void setDIA(ArrayList DIA) {
        this.DIA = DIA;
    }

    public ArrayList getPARCIAL() {
        return PARCIAL;
    }

    public void setPARCIAL(ArrayList PARCIAL) {
        this.PARCIAL = PARCIAL;
    }

    public ArrayList getID_FECHA() {
        return ID_FECHA;
    }

    public void setID_FECHA(ArrayList ID_FECHA) {
        this.ID_FECHA = ID_FECHA;
    }

    public ArrayList getINICIO() {
        return INICIO;
    }

    public void setINICIO(ArrayList INICIO) {
        this.INICIO = INICIO;
    }

    public ArrayList getFIN() {
        return FIN;
    }

    public void setFIN(ArrayList FIN) {
        this.FIN = FIN;
    }

    public ArrayList getCICLO() {
        return CICLO;
    }

    public void setCICLO(ArrayList CICLO) {
        this.CICLO = CICLO;
    }


    public  void Get_Parciales_From_Server(int ID_Ciclo){
        METHOD_NAME="Get_Parciales_CicloJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_Parciales_CicloJSON";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("ID_Ciclo",ID_Ciclo);
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
            ArrayList Parciales = new ArrayList<String>() ;
            ArrayList Id_Fecha = new ArrayList<String>() ;
            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String ciclo =v.getString("NUMERO_CICLO");
                String incio =v.getString("FECHA_INICIO");
                incio = incio.substring(0,10);
                String fin =v.getString("FECHA_FIN");
                fin = fin.substring(0,10);
                String parcial =v.getString("NUMERO_PARCIAL");
                String id =v.getString("ID_FECHA_PARCIAL");


                if (!ciclo.isEmpty() && ciclo !=null){
                    Ciclos.add(ciclo);
                    Inicios.add(incio);
                    Fin.add(fin);
                    Parciales.add(parcial);
                    Id_Fecha.add(id);

                }
            }
            setCICLO(Ciclos);
            setFIN(Fin);
            setID_FECHA(Id_Fecha);
            setPARCIAL(Parciales);
            setINICIO(Inicios);

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }
    public int Update_Parcial(String usuario, String fecha_inicio , String fecha_fin
            ,int ciclo, int ID_Fecha_Parcial){

        METHOD_NAME="Actualizar_Parcial";
        SOAP_ACTION="http://apoyo.conteoutec.org/Actualizar_Parcial";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("Usuario",usuario);
        request.addProperty("Fecha_Inicio",fecha_inicio);
        request.addProperty("Fecha_Fin",fecha_fin);
        request.addProperty("ID_Ciclo",ciclo);
        request.addProperty("ID_Fecha_Parcial",ID_Fecha_Parcial);
        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);
        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();
            resultado=respSoap.toString();

            return Integer.parseInt(resultado);

        }catch (Exception ex){
            return -7;
        }
    }

    public int Insert_Parcial(String usuario, String fecha_inicio , String fecha_fin
                                ,int ciclo, int numero_parcial){

        METHOD_NAME="Insertar_Parcial";
        SOAP_ACTION="http://apoyo.conteoutec.org/Insertar_Parcial";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("Usuario",usuario);
        request.addProperty("Fecha_Inicio",fecha_inicio);
        request.addProperty("Fecha_Fin",fecha_fin);
        request.addProperty("ID_Ciclo",ciclo);
        request.addProperty("Numero_Parcial",numero_parcial);
        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);
        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();
            resultado=respSoap.toString();

            return Integer.parseInt(resultado);

        }catch (Exception ex){
            return -7;
        }
    }


    public  void Get_Fechas_From_Server(int ID_Parcial){
        METHOD_NAME="Get_Fecha_ParcialesJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_Fecha_ParcialesJSON";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("ID_Parcial",ID_Parcial);
        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            resultado=respSoap.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            JSONArray obtener = jsonObj.getJSONArray("Table1");

            int a=obtener.length();
            ArrayList Fechas = new ArrayList<String>() ;
            ArrayList Dias = new ArrayList<String>() ;

            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String fecha =v.getString("Fechas");
                String dia =v.getString("Dias");

                if (!fecha.isEmpty() && fecha !=null){
                    Fechas.add(fecha);
                    Dias.add(dia);

                }
            }
            setFECHA(Fechas);
            setDIA(Dias);

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }



    public  void Get_Horas_From_Server(int ID_Parcial,String Fecha){
        METHOD_NAME="Get_DIA_HORA_ParcialJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_DIA_HORA_ParcialJSON";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("ID_Parcial",ID_Parcial);
        request.addProperty("fecha",Fecha);
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
            ArrayList Inicios = new ArrayList<String>() ;
            ArrayList Fin = new ArrayList<String>() ;
            ArrayList ID = new ArrayList<String>() ;

            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String inicio =v.getString("HORA_INICIO");
                String fin =v.getString("HORA_FINAL");
                String id =v.getString("ID_DIA_HORA_PARCIAL");

                if (!inicio.isEmpty() && inicio !=null){
                    Inicios.add(inicio);
                    Fin.add(fin);
                    ID.add(id);

                }
            }

            setHORA_INICIO(Inicios);
            setHORA_FIN(Fin);
            setID_HORA(ID);

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }


    public  int Set_Horas_From_Server(int ID_Parcial,String Fecha, String hora_inicio, String hora_fin,String usuario,int ID_Ciclo){
        METHOD_NAME="Set_Dias_Parciales";
        SOAP_ACTION="http://apoyo.conteoutec.org/Set_Dias_Parciales";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        int resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("ID_Parcial",ID_Parcial);
        request.addProperty("fecha",Fecha);
        request.addProperty("hora_inicio",hora_inicio);
        request.addProperty("hora_fin",hora_fin);
        request.addProperty("usuario",usuario);
        request.addProperty("ID_Ciclo",ID_Ciclo);
        request.addProperty("TIPO",1);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            return resultado=Integer.parseInt(respSoap.toString());

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
            return -1;
        }
    }



    public  int Update_Horas_From_Server(int ID_Hora,String Fecha, String hora_inicio, String hora_fin,String usuario){
        METHOD_NAME="Actualizar_Dias_Parciales";
        SOAP_ACTION="http://apoyo.conteoutec.org/Actualizar_Dias_Parciales";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        int resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("ID_Parcial",ID_Hora);
        request.addProperty("fecha",Fecha);
        request.addProperty("hora_inicio",hora_inicio);
        request.addProperty("hora_fin",hora_fin);
        request.addProperty("usuario",usuario);



        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            return resultado=Integer.parseInt(respSoap.toString());

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
            return -1;
        }
    }


    public  int Delete_Horas_From_Server(int ID_Hora,String usuario){
        METHOD_NAME="Eliminar_Hora_Parcial";
        SOAP_ACTION="http://apoyo.conteoutec.org/Eliminar_Hora_Parcial";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        int resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("id_DIA_HORA",ID_Hora);
        request.addProperty("usuario",usuario);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            return resultado=Integer.parseInt(respSoap.toString());

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
            return -1;
        }
    }
}
