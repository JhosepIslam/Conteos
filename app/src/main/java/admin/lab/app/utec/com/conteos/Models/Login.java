package admin.lab.app.utec.com.conteos.Models;


import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Login  {

    Conexion conexion = new Conexion();
    final String METHOD_NAME="IniciarSesion";
    final String SOAP_ACTION="http://apoyo.conteoutec.org/IniciarSesion";

    public View getView() {
        return view;
    }
    public void setView(View view) {
        this.view = view;
    }

    private View view;
    private String Usuario;
    public String getUsuario() {
        return this.Usuario;
    }
    public void setUsuario(String name) {
        this.Usuario = name;
    }

    private int Nivel;
    public int getNivel() {
        return this.Nivel;
    }
    public void setNivel(int nivel) {
        this.Nivel = Nivel;
    }

    private String Pass;
    public String getPass() {
        return this.Pass;
    }
    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public  int login()
    {
        int resul = 0;


        SoapObject  request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("usuario",Usuario);
        request.addProperty("Clave",Pass);

        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER12);


        envelope.dotNet = true;
        envelope.bodyOut = request;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(conexion.getURL());
        try {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive response =(SoapPrimitive) envelope.getResponse();
            resul= Integer.parseInt(response.toString());
            int verif =Asistente_Verificacion();
            if (verif ==1 && resul ==3){
                resul = 33;
            }
        }
        catch (Exception ex) {
            resul = -1;
        }


        return  resul;
    }
    public  int Asistente_Verificacion()
    {
        int resul = 0;

        String METHOD_NAME="Verificar_AsistenteJSON";
        String SOAP_ACTION="http://apoyo.conteoutec.org/Verificar_AsistenteJSON";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("usuario",Usuario);
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
            int result=0;
            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                result =v.getInt("RESULTADO");

            }
            return result;


        }catch (Exception ex){
            return 0;
        }
    }





}

    /*

    */
