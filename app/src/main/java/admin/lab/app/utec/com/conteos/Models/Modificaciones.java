package admin.lab.app.utec.com.conteos.Models;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Modificaciones {
    private  String METHOD_NAME ;
    private   String SOAP_ACTION;
    Conexion conexion = new Conexion();


    public boolean modificar_clave(int usuario_update, String Nueva_Clave, String Usuario){
            METHOD_NAME="Modificar_Clave";
            SOAP_ACTION="http://apoyo.conteoutec.org/Modificar_Clave";


            HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
            request.addProperty("usuario_update",usuario_update);
            request.addProperty("Nueva_Clave",Nueva_Clave);
            request.addProperty("Usuario",Usuario);

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

    public boolean modificar_facultad(int usuario_update, String Nueva_Facultad, String Usuario){
        METHOD_NAME="Modificar_Facultad";
        SOAP_ACTION="http://apoyo.conteoutec.org/Modificar_Facultad";


        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("usuario_update",usuario_update);
        request.addProperty("Nueva_Facultad",Nueva_Facultad);
        request.addProperty("Usuario",Usuario);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(request.toString());
        }
        catch (Exception ex){
            return false;
        }
    }


    public boolean modificar_laboratorio(int usuario_update, String Nuevo_Laboratorio, String Usuario){
        METHOD_NAME="Modificar_Laboratorio";
        SOAP_ACTION="http://apoyo.conteoutec.org/Modificar_Laboratorio";


        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("usuario_update",usuario_update);
        request.addProperty("Nuevo_Laboratorio",Nuevo_Laboratorio);
        request.addProperty("Usuario",Usuario);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(request.toString());
        }
        catch (Exception ex){
            return false;
        }
    }

    public boolean reset_clave(int usuario_update, String Usuario){
        METHOD_NAME="Reset_Clave";
        SOAP_ACTION="http://apoyo.conteoutec.org/Reset_Clave";


        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("usuario_update",usuario_update);

        request.addProperty("Usuario",Usuario);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try {
            transport.call(SOAP_ACTION, envelope);
            SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();


            return Boolean.parseBoolean(request.toString());

        }
        catch (Exception ex){
            return false;
        }
    }
}





