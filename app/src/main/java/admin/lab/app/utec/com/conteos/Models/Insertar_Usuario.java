package admin.lab.app.utec.com.conteos.Models;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Insertar_Usuario {
    String METHOD_NAME;
    String SOAP_ACTION;

    Conexion conexion = new Conexion();

    private int validarUsuario(String usuario){
        METHOD_NAME="ValidarUsuario";
        SOAP_ACTION="http://apoyo.conteoutec.org/ValidarUsuario";
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

            boolean res =Boolean.parseBoolean(respSoap.toString());
            if (res){
                return 1; //existe
            }else return 0; // no existe

        }
        catch (Exception ex){
            return -1;//error de conexion
        }
    }


    public int Insertar_Usuario(String nombre, String apellido, String usuario, String contrasenia
    , String nivel , String facultad){
        int validarUser=validarUsuario(usuario);
        if (validarUser == 0)
        {

            METHOD_NAME="AgregarUsuario";
            SOAP_ACTION="http://apoyo.conteoutec.org/AgregarUsuario";
            HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
            request.addProperty("usuario",usuario);
            request.addProperty("clave",contrasenia);
            request.addProperty("nivel",nivel);
            request.addProperty("nombre",nombre);
            request.addProperty("apellido",apellido);
            request.addProperty("facultad",facultad);

            envelope.dotNet=true;
            envelope.bodyOut=request;
            envelope.setOutputSoapObject(request);

            try {
                transport.call(SOAP_ACTION, envelope);
                SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

                boolean res =Boolean.parseBoolean(respSoap.toString());
                if (res){
                    return 0; // insertado
                }else return 1;//no insetado
            }
            catch (Exception ex){
                return -1;//error de conexion
            }
        }
        else if (validarUser == 1)return 1; // usuario existente
        else return -1;
    }

    public int Insertar_Usuario_Docente(String nombre, String apellido, String usuario, String contrasenia
            , String nivel , String facultad, String codigo_empleado){
        int validarUser=validarUsuario(usuario);
        if (validarUser == 0)
        {

            METHOD_NAME="AgregarUsuarioDocente";
            SOAP_ACTION="http://apoyo.conteoutec.org/AgregarUsuarioDocente";
            HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
            request.addProperty("usuario",usuario);
            request.addProperty("clave",contrasenia);
            request.addProperty("nivel",nivel);
            request.addProperty("nombre",nombre);
            request.addProperty("apellido",apellido);
            request.addProperty("facultad",facultad);
            request.addProperty("codigo_empleado",codigo_empleado);


            envelope.dotNet=true;
            envelope.bodyOut=request;
            envelope.setOutputSoapObject(request);

            try {
                transport.call(SOAP_ACTION, envelope);
                SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

                boolean res =Boolean.parseBoolean(respSoap.toString());
                if (res){
                    return 0;//insertado
                }else return 1;//no insertado
            }
            catch (Exception ex){
                return -1;//error de conexion
            }
        }
        else if (validarUser == 1)return 1; //usuario existente
        else return -1;


    }

    public int Insertar_Usuario_Admin_lab_instructor(String nombre, String apellido, String usuario, String contrasenia
            , String nivel , String facultad, String laboratorio ,String Usuario_insert){
        int validarUser=validarUsuario(usuario);
        if (validarUser == 0)
        {

            METHOD_NAME="AgregarUsuario_AdminLab_Instructor";
            SOAP_ACTION="http://apoyo.conteoutec.org/AgregarUsuario_AdminLab_Instructor";
            HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
            request.addProperty("usuario",usuario);
            request.addProperty("clave",contrasenia);
            request.addProperty("nivel",nivel);
            request.addProperty("nombre",nombre);
            request.addProperty("apellido",apellido);
            request.addProperty("facultad",facultad);
            request.addProperty("laboratorio",laboratorio);
            request.addProperty("usuario_insert",Usuario_insert);
            envelope.dotNet=true;
            envelope.bodyOut=request;
            envelope.setOutputSoapObject(request);

            try {
                transport.call(SOAP_ACTION, envelope);
                SoapPrimitive respSoap = (SoapPrimitive) envelope.getResponse();

                boolean res =Boolean.parseBoolean(respSoap.toString());
                if (res){
                    return 0; //insertado
                }else return 1; // no insertado
            }
            catch (Exception ex){
                return -1;//error de conexion
            }
        }
        else if (validarUser == 1)return 1; // usuario existente
        else return -1;


    }



}
