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

public class Clases {

    private  String METHOD_NAME ;
    private   String SOAP_ACTION;
    Conexion conexion = new Conexion();
    private int cantida,  id_conteo;
    String nivel;
    private  ArrayList HORAS_CLASES,FACULTAD;

    private  ArrayList ID_CLASES, MATERIAS, CODIGO, DOCENTE, AULAS, EDIFICIO,CICLO,
                        DIAS, SECCION, INCRITOS, HORA ,ID_MATERIA_CONTRO ,CANTIDAD_CONTEO,ID_MATERIA_FALTA ,DETALLE;

    public ArrayList getID_MATERIA_FALTA() {
        return ID_MATERIA_FALTA;
    }

    public void setID_MATERIA_FALTA(ArrayList ID_MATERIA_FALTA) {
        this.ID_MATERIA_FALTA = ID_MATERIA_FALTA;
    }

    public ArrayList getCICLO() {
        return CICLO;
    }

    public void setCICLO(ArrayList CICLO) {
        this.CICLO = CICLO;
    }

    public ArrayList getDETALLE() {
        return DETALLE;
    }

    public void setDETALLE(ArrayList DETALLE) {
        this.DETALLE = DETALLE;
    }



    public ArrayList getHORAS_CLASES() {
        return HORAS_CLASES;
    }

    public void setHORAS_CLASES(ArrayList HORAS_CLASES) {
        this.HORAS_CLASES = HORAS_CLASES;
    }

    public ArrayList getID_MATERIA_CONTRO() {
        return ID_MATERIA_CONTRO;
    }

    public void setID_MATERIA_CONTRO(ArrayList ID_MATERIA_CONTRO) {
        this.ID_MATERIA_CONTRO = ID_MATERIA_CONTRO;
    }

    public ArrayList getCANTIDAD_CONTEO() {
        return CANTIDAD_CONTEO;
    }

    public void setCANTIDAD_CONTEO(ArrayList CANTIDAD_CONTEO) {
        this.CANTIDAD_CONTEO = CANTIDAD_CONTEO;
    }

    public int getId_conteo() {
        return id_conteo;
    }

    public void setId_conteo(int id_conteo) {
        this.id_conteo = id_conteo;
    }

    public int getCantida() {
        return cantida;
    }

    public void setCantida(int cantida) {
        this.cantida = cantida;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    private ArrayList FALTAS;

    public ArrayList getFALTAS() {
        return FALTAS;
    }

    public void setFALTAS(ArrayList FALTAS) {
        this.FALTAS = FALTAS;
    }

    public String getMETHOD_NAME() {
        return METHOD_NAME;
    }

    public void setMETHOD_NAME(String METHOD_NAME) {
        this.METHOD_NAME = METHOD_NAME;
    }

    public ArrayList getID_CLASES() {
        return ID_CLASES;
    }

    public void setID_CLASES(ArrayList ID_CLASES) {
        this.ID_CLASES = ID_CLASES;
    }

    public ArrayList getMATERIAS() {
        return MATERIAS;
    }

    public void setMATERIAS(ArrayList MATERIAS) {
        this.MATERIAS = MATERIAS;
    }

    public ArrayList getCODIGO() {
        return CODIGO;
    }

    public ArrayList getFACULTAD() {
        return FACULTAD;
    }

    public void setFACULTAD(ArrayList FACULTAD) {
        this.FACULTAD = FACULTAD;
    }

    public void setCODIGO(ArrayList CODIGO) {
        this.CODIGO = CODIGO;
    }

    public ArrayList getDOCENTE() {
        return DOCENTE;
    }

    public void setDOCENTE(ArrayList DOCENTE) {
        this.DOCENTE = DOCENTE;
    }

    public ArrayList getAULAS() {
        return AULAS;
    }

    public void setAULAS(ArrayList AULAS) {
        this.AULAS = AULAS;
    }

    public ArrayList getEDIFICIO() {
        return EDIFICIO;
    }

    public void setEDIFICIO(ArrayList EDIFICIO) {
        this.EDIFICIO = EDIFICIO;
    }

    public ArrayList getDIAS() {
        return DIAS;
    }

    public void setDIAS(ArrayList DIAS) {
        this.DIAS = DIAS;
    }

    public ArrayList getSECCION() {
        return SECCION;
    }

    public void setSECCION(ArrayList SECCION) {
        this.SECCION = SECCION;
    }

    public ArrayList getINCRITOS() {
        return INCRITOS;
    }

    public void setINCRITOS(ArrayList INCRITOS) {
        this.INCRITOS = INCRITOS;
    }

    public ArrayList getHORA() {
        return HORA;
    }

    public void setHORA(ArrayList HORA) {
        this.HORA = HORA;
    }





    public void  Get_Clases_FromServer(String Edificio, String Hora,String usuario, int nivel){
        Facultades facultades = new Facultades();
        int facultad_id = facultades.get_facultad(usuario);

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        if(nivel ==2){
            METHOD_NAME="Get_Clases_ActualesAdminJSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Clases_ActualesAdminJSON";
        }else {
            METHOD_NAME="Get_Clases_ActualesJSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Clases_ActualesJSON";
        }
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("edificio",Edificio);
        request.addProperty("hora",Hora);
        if (nivel != 2){
            request.addProperty("Facultad",facultad_id);
        }


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

            ArrayList IdClases = new ArrayList<String>();
            ArrayList Materias = new ArrayList<String>();
            ArrayList Codigo = new ArrayList<String>();
            ArrayList Docente = new ArrayList<String>();
            ArrayList Aulas = new ArrayList();
            ArrayList Edificios = new ArrayList<String>();
            ArrayList Dias = new ArrayList<String>();
            ArrayList Secciones = new ArrayList<String>();
            ArrayList Inscritos = new ArrayList<String>();
            ArrayList Horas = new ArrayList<String>();
            ArrayList Facultades = new ArrayList<String>();
            ArrayList Ciclos = new ArrayList<String>();

            try {
                if (length <=0){
                    ID_CLASES.clear();
                    MATERIAS.clear();
                    CODIGO.clear();
                    DOCENTE.clear();
                    AULAS.clear();
                    EDIFICIO.clear();
                    DIAS.clear();
                    SECCION.clear();
                    SECCION.clear();
                    INCRITOS.clear();
                    HORA.clear();
                    FACULTAD.clear();
                }
            }catch (Exception ex){}

            for (int i = 0; i < length; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String id_clase =v.getString("ID_CLASE");
                String materia =v.getString("MATERIA");
                String codigo =v.getString("CODIGO");
                String docente =v.getString("DOCENTE");
                String aula = v.getString("NUMERO_AULA");
                String edificio = v.getString("EDIFICIO");
                String dia = v.getString("DIA_DIAS");
                String seccion = v.getString("SECCION");
                String inscritos = v.getString("NUM_INSCRITOS");
                String hora_inicio = v.getString("HORA_INICIO");
                String hora_final = v.getString("HORA_FIN");
                String facultad = v.getString("FACULTAD");
                Ciclos.add(v.getString("CICLO"));



                if (!id_clase.isEmpty() && id_clase !=null){
                    IdClases.add(id_clase);
                    Materias.add(materia);
                    Codigo.add(codigo);
                    Docente.add(docente);
                    Aulas.add(aula);
                    Edificios.add(edificio);
                    Dias.add(dia);
                    Secciones.add(seccion);
                    Inscritos.add(inscritos);
                    Horas.add(hora_inicio.substring(0,5)+"-"+hora_final.substring(0,5));
                    Facultades.add(facultad);

                }
                setCICLO(Ciclos);
                setID_CLASES(IdClases);
                setMATERIAS(Materias);
                setCODIGO(Codigo);
                setDOCENTE(Docente);
                setFACULTAD(Facultades);
                setAULAS(Aulas);
                setEDIFICIO(Edificios);
                setDIAS(Dias);
                setSECCION(Secciones);
                setINCRITOS(Inscritos);
                setHORA(Horas);

            }
        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());
        }
    }

    public void  Get_Clases_Docente_FromServer(String Nombre_usuario, String Hora){
        METHOD_NAME="Get_Clases_Actuales_DocenteJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_Clases_Actuales_DocenteJSON";
        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("Nombre_usuario",Nombre_usuario);
        request.addProperty("hora",Hora);

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

            ArrayList IdClases = new ArrayList<String>();
            ArrayList Materias = new ArrayList<String>();
            ArrayList Codigo = new ArrayList<String>();
            ArrayList Docente = new ArrayList<String>();
            ArrayList Aulas = new ArrayList();
            ArrayList Edificios = new ArrayList<String>();
            ArrayList Dias = new ArrayList<String>();
            ArrayList Secciones = new ArrayList<String>();
            ArrayList Inscritos = new ArrayList<String>();
            ArrayList Horas = new ArrayList<String>();
            ArrayList Escuelas = new ArrayList();
            ArrayList Facultades = new ArrayList<String>();
            ArrayList Ciclos = new ArrayList<String>();
            if (length <=0){
                ID_CLASES.clear();
                MATERIAS.clear();
                CODIGO.clear();
                DOCENTE.clear();
                AULAS.clear();
                EDIFICIO.clear();
                DIAS.clear();
                SECCION.clear();
                SECCION.clear();
                INCRITOS.clear();
                HORA.clear();

            }
            for (int i = 0; i < length; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String id_clase =v.getString("ID_CLASE");
                String materia =v.getString("MATERIA");
                String codigo =v.getString("CODIGO");
                String docente =v.getString("DOCENTE");
                String aula = v.getString("NUMERO_AULA");
                String edificio = v.getString("EDIFICIO");
                String dia = v.getString("DIA_DIAS");
                String seccion = v.getString("SECCION");
                String inscritos = v.getString("NUM_INSCRITOS");
                String hora_inicio = v.getString("HORA_INICIO");
                String hora_final = v.getString("HORA_FIN");
                String escuela = v.getString("ESCUELA");
                String facultad = v.getString("FACULTAD");
                Ciclos.add( v.getString("CICLO"));
                if (!id_clase.isEmpty() && id_clase !=null){
                    IdClases.add(id_clase);
                    Materias.add(materia);
                    Codigo.add(codigo);
                    Docente.add(docente);
                    Aulas.add(aula);
                    Edificios.add(edificio);
                    Dias.add(dia);
                    Secciones.add(seccion);
                    Inscritos.add(inscritos);
                    Horas.add(hora_inicio.substring(0,5)+"-"+hora_final.substring(0,5));
                    Escuelas.add(escuela);
                    Facultades.add(facultad);
                }
                setCICLO(Ciclos);
                setID_CLASES(IdClases);
                setMATERIAS(Materias);
                setCODIGO(Codigo);
                setDOCENTE(Docente);
                setAULAS(Aulas);
                setEDIFICIO(Edificios);
                setDIAS(Dias);
                setSECCION(Secciones);
                setINCRITOS(Inscritos);
                setHORA(Horas);
                setFACULTAD(Facultades);

            }
        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());
        }
    }
    public void  Get_All_Conteo(String  Hora){

        METHOD_NAME="Get_ALL_CONTEOSJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_ALL_CONTEOSJSON";


        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("hora",Hora);
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
            ArrayList ids = new ArrayList<String>() ;
            ArrayList cantidades = new ArrayList<String>() ;
            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String id =v.getString("ID_CLASE");
                int ult_cont =v.getInt("CANTIDAD");

                if (!id.isEmpty() && id !=null){
                    ids.add(id);
                    cantidades.add(ult_cont);
                }
            }
            setID_MATERIA_CONTRO(ids);
            setCANTIDAD_CONTEO(cantidades);

        }catch (Exception ex){
            System.out.println("a");
        }

    }

    public void  Get_All_Faltas(String  Hora){

        METHOD_NAME="Get_ALL_FALTASJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_ALL_FALTASJSON";


        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("hora",Hora);
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
            ArrayList ids = new ArrayList<String>() ;
            ArrayList detalles = new ArrayList<String>() ;
            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String id =v.getString("ID_CLASE");
                String detalle =v.getString("DETALLE");
                String comentario =v.getString("COMENTARIO");

                if (!id.isEmpty() && id !=null){
                    ids.add(id);
                    if (detalle.equals("Otro")){
                        detalles.add(comentario);
                    }else {
                        detalles.add(detalle);
                    }
                }
            }
            setID_MATERIA_FALTA(ids);
            setDETALLE(detalles);

        }catch (Exception ex){
            System.out.println("a");
        }

    }
    public void  Get_Faltas_fromServer(){

        METHOD_NAME="Get_detalle_faltaJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_detalle_faltaJSON";


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
            ArrayList faltas = new ArrayList<String>() ;
            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String falta =v.getString("DETALLE");

                if (!falta.isEmpty() && falta !=null){
                    faltas.add(falta);
                }
            }
            setFALTAS(faltas);



        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }

    }
    public boolean Set_conteo(int id, String usuario,int cantida){

        METHOD_NAME="Insertar_Conteo";
        SOAP_ACTION="http://apoyo.conteoutec.org/Insertar_Conteo";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("id_clase",id);
        request.addProperty("usuario",usuario);
        request.addProperty("cantidad",cantida);
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


    public int Comprobar_Parcial(String hora){

        METHOD_NAME="Verificar_fecha_ParcialJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Verificar_fecha_ParcialJSON";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("hora",hora);
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
    public boolean Set_Falta(int id, String usuario,String detalle ,String comentario, String hora){

        METHOD_NAME="Insertar_Falta";
        SOAP_ACTION="http://apoyo.conteoutec.org/Insertar_Falta";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("id_clase",id);
        request.addProperty("usuario",usuario);
        request.addProperty("detalle",detalle);
        request.addProperty("comentario",comentario);
        request.addProperty("hora",hora);
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
    public void  Get_conteo_fromServer(int id,String hora){

        METHOD_NAME="Get_Conteo_ifExistJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_Conteo_ifExistJSON";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("id",id);
        request.addProperty("hora",hora);

        envelope.dotNet=true;
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        try{
            transport.call(SOAP_ACTION,envelope);
            SoapPrimitive respSoap=(SoapPrimitive) envelope.getResponse();

            resultado=respSoap.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            JSONArray obtener = jsonObj.getJSONArray("Table");

            setCantida(0);
            setNivel(null);
            int a=obtener.length();

            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                setCantida(Integer.parseInt(v.getString("CANTIDAD")));
                setNivel(v.getString("TIPO"));
                setId_conteo(v.getInt("ID_ASISTENCIA"));
            }

        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }

    public void  Get_Horas_Clases_fromServer(int nivel, String usuario,String edificio){

        Facultades facultades = new Facultades();
        int facultad_id = facultades.get_facultad(usuario);

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        if(nivel ==2){
            METHOD_NAME="Get_Horas_Clases_Admin_JSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Horas_Clases_Admin_JSON";
        }else if (nivel == 4){

            METHOD_NAME="Get_Horas_Clases_Docente_JSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Horas_Clases_Docente_JSON";
        }
        else{
            METHOD_NAME="Get_Horas_Clases_JSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Horas_Clases_JSON";
        }

        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        if (nivel != 4){
            request.addProperty("edificio",edificio);
        }
        if (nivel == 4){
            request.addProperty("usuario",usuario);
        }
        else if (nivel != 2){
            request.addProperty("facultad",facultad_id);


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

            ArrayList Horas = new ArrayList();
            int a=obtener.length();

            for (int i = 0; i < a; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                Horas.add(v.getString("HORA"));
            }
            setHORAS_CLASES(Horas);


        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }
    public boolean Update_conteo(int id_asitencia, String Nuevo_usuario,int Nueva_cantida){

        METHOD_NAME="Actualizar_Conteo";
        SOAP_ACTION="http://apoyo.conteoutec.org/Actualizar_Conteo";

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        String resultado;
        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("id_Asistencia",id_asitencia);
        request.addProperty("Nuevo_usuario",Nuevo_usuario);
        request.addProperty("Nueva_cantidad",Nueva_cantida);
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
    public void  Get_Clases_Contadas_FromServer(String Fecha, String Hora,String Facultad,int nivel, String usuario){
        int facultad_id=0;
        if (nivel == 3){
            Facultades facultades = new Facultades();
            facultad_id=facultades.get_facultad(usuario);
        }else{
            facultad_id =Integer.parseInt(Facultad.substring(0,1));
        }

        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            METHOD_NAME="Get_Clases_ContadasJSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Clases_ContadasJSON";

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("fecha",Fecha);
        request.addProperty("hora",Hora);
        request.addProperty("id_facultad",facultad_id);



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

            ArrayList IdClases = new ArrayList<String>();
            ArrayList Materias = new ArrayList<String>();
            ArrayList Codigo = new ArrayList<String>();
            ArrayList Docente = new ArrayList<String>();
            ArrayList Aulas = new ArrayList();
            ArrayList Edificios = new ArrayList<String>();
            ArrayList Dias = new ArrayList<String>();
            ArrayList Secciones = new ArrayList<String>();
            ArrayList Inscritos = new ArrayList<String>();
            ArrayList Horas = new ArrayList<String>();
            ArrayList Cantidad = new ArrayList<String>();

            try {
                if (length <=0){
                    ID_CLASES.clear();
                    MATERIAS.clear();
                    DOCENTE.clear();
                    AULAS.clear();
                    EDIFICIO.clear();
                    DIAS.clear();
                    SECCION.clear();
                    INCRITOS.clear();
                    HORA.clear();



                }
            }catch (Exception ex){}

            for (int i = 0; i < length; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String id_clase =v.getString("ID_CLASE");
                String materia =v.getString("MATERIA");

                String docente =v.getString("DOCENTE");
                String aula = v.getString("NUMERO_AULA");
                String edificio = v.getString("EDIFICIO");
                String dia = v.getString("DIA_DIAS");
                String seccion = v.getString("SECCION");
                String inscritos = v.getString("NUM_INSCRITOS");
                String hora_inicio = v.getString("HORA_INICIO");
                String hora_final = v.getString("HORA_FIN");
                String conteo = v.getString("CANTIDAD");

                if (!id_clase.isEmpty() && id_clase !=null){
                    IdClases.add(id_clase);
                    Materias.add(materia);
                    Docente.add(docente);
                    Aulas.add(aula);
                    Edificios.add(edificio);
                    Dias.add(dia);
                    Secciones.add(seccion);
                    Inscritos.add(inscritos);
                    Cantidad.add(conteo);
                    Horas.add(hora_inicio.substring(0,5)+"-"+hora_final.substring(0,5));

                }
                setID_CLASES(IdClases);
                setMATERIAS(Materias);
                setCODIGO(Codigo);
                setDOCENTE(Docente);
                setAULAS(Aulas);
                setEDIFICIO(Edificios);
                setDIAS(Dias);
                setSECCION(Secciones);
                setINCRITOS(Inscritos);
                setHORA(Horas);
                setCANTIDAD_CONTEO(Cantidad);

            }
        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());
        }
    }
    public void  Get_Clases_Sin_Contar_FromServer(String Fecha, String Hora,String Facultad,int nivel, String usuario){
        int facultad_id=0;
        if (nivel == 3){
            Facultades facultades = new Facultades();
            facultad_id=facultades.get_facultad(usuario);
        }else{
            facultad_id =Integer.parseInt(Facultad.substring(0,1));
        }
        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        METHOD_NAME="Get_Clases_Sin_ConteoJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_Clases_Sin_ConteoJSON";

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("fecha",Fecha);
        request.addProperty("hora",Hora);
        request.addProperty("id_facultad",facultad_id);



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

            ArrayList IdClases = new ArrayList<String>();
            ArrayList Materias = new ArrayList<String>();
            ArrayList Codigo = new ArrayList<String>();
            ArrayList Docente = new ArrayList<String>();
            ArrayList Aulas = new ArrayList();
            ArrayList Edificios = new ArrayList<String>();
            ArrayList Dias = new ArrayList<String>();
            ArrayList Secciones = new ArrayList<String>();
            ArrayList Inscritos = new ArrayList<String>();
            ArrayList Horas = new ArrayList<String>();
            ArrayList Cantidad = new ArrayList<String>();

            try {
                if (length <=0){
                    ID_CLASES.clear();
                    MATERIAS.clear();
                    DOCENTE.clear();
                    AULAS.clear();
                    EDIFICIO.clear();
                    DIAS.clear();
                    SECCION.clear();
                    INCRITOS.clear();
                    HORA.clear();



                }
            }catch (Exception ex){}

            for (int i = 0; i < length; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String id_clase =v.getString("ID_CLASE");
                String materia =v.getString("MATERIA");

                String docente =v.getString("DOCENTE");
                String aula = v.getString("NUMERO_AULA");
                String edificio = v.getString("EDIFICIO");
                String dia = v.getString("DIA_DIAS");
                String seccion = v.getString("SECCION");
                String inscritos = v.getString("NUM_INSCRITOS");
                String hora_inicio = v.getString("HORA_INICIO");
                String hora_final = v.getString("HORA_FIN");
                String conteo ="-";

                if (!id_clase.isEmpty() && id_clase !=null){
                    IdClases.add(id_clase);
                    Materias.add(materia);
                    Docente.add(docente);
                    Aulas.add(aula);
                    Edificios.add(edificio);
                    Dias.add(dia);
                    Secciones.add(seccion);
                    Inscritos.add(inscritos);
                    Cantidad.add(conteo);
                    Horas.add(hora_inicio.substring(0,5)+"-"+hora_final.substring(0,5));

                }
                setID_CLASES(IdClases);
                setMATERIAS(Materias);
                setCODIGO(Codigo);
                setDOCENTE(Docente);
                setAULAS(Aulas);
                setEDIFICIO(Edificios);
                setDIAS(Dias);
                setSECCION(Secciones);
                setINCRITOS(Inscritos);
                setHORA(Horas);
                setCANTIDAD_CONTEO(Cantidad);

            }
        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());
        }
    }
    public void  Get_Clases_Con_Falta_FromServer(String Fecha, String Hora,String Facultad, int nivel, String usuario){
        int facultad_id=0;
        if (nivel == 3){
            Facultades facultades = new Facultades();
            facultad_id=facultades.get_facultad(usuario);
        }else{
            facultad_id =Integer.parseInt(Facultad.substring(0,1));
        }
        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        METHOD_NAME="Get_Clases_Con_FaltaJSON";
        SOAP_ACTION="http://apoyo.conteoutec.org/Get_Clases_Con_FaltaJSON";

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("fecha",Fecha);
        request.addProperty("hora",Hora);
        request.addProperty("id_facultad",facultad_id);



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

            ArrayList IdClases = new ArrayList<String>();
            ArrayList Materias = new ArrayList<String>();
            ArrayList Codigo = new ArrayList<String>();
            ArrayList Docente = new ArrayList<String>();
            ArrayList Aulas = new ArrayList();
            ArrayList Edificios = new ArrayList<String>();
            ArrayList Dias = new ArrayList<String>();
            ArrayList Secciones = new ArrayList<String>();
            ArrayList Inscritos = new ArrayList<String>();
            ArrayList Horas = new ArrayList<String>();
            ArrayList Cantidad = new ArrayList<String>();

            try {
                if (length <=0){
                    ID_CLASES.clear();
                    MATERIAS.clear();
                    DOCENTE.clear();
                    AULAS.clear();
                    EDIFICIO.clear();
                    DIAS.clear();
                    SECCION.clear();
                    INCRITOS.clear();
                    HORA.clear();



                }
            }catch (Exception ex){}

            for (int i = 0; i < length; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String id_clase =v.getString("ID_CLASE");
                String materia =v.getString("MATERIA");

                String docente =v.getString("DOCENTE");
                String aula = v.getString("NUMERO_AULA");
                String edificio = v.getString("EDIFICIO");
                String dia = v.getString("DIA_DIAS");
                String seccion = v.getString("SECCION");
                String inscritos = v.getString("NUM_INSCRITOS");
                String hora_inicio = v.getString("HORA_INICIO");
                String hora_final = v.getString("HORA_FIN");
                String conteo =v.getString("DETALLE");

                if (!id_clase.isEmpty() && id_clase !=null){
                    IdClases.add(id_clase);
                    Materias.add(materia);
                    Docente.add(docente);
                    Aulas.add(aula);
                    Edificios.add(edificio);
                    Dias.add(dia);
                    Secciones.add(seccion);
                    Inscritos.add(inscritos);
                    Cantidad.add(conteo);
                    Horas.add(hora_inicio.substring(0,5)+"-"+hora_final.substring(0,5));

                }
                setID_CLASES(IdClases);
                setMATERIAS(Materias);
                setCODIGO(Codigo);
                setDOCENTE(Docente);
                setAULAS(Aulas);
                setEDIFICIO(Edificios);
                setDIAS(Dias);
                setSECCION(Secciones);
                setINCRITOS(Inscritos);
                setHORA(Horas);
                setCANTIDAD_CONTEO(Cantidad);

            }
        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());
        }
    }

    public void  Get_Clases__Anteriores_FromServer(String Facultad, String Anio, int Ciclo){


        HttpTransportSE transport = new HttpTransportSE(conexion.getURL());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);


            METHOD_NAME="Get_Clases_Otro_CicloJSON";
            SOAP_ACTION="http://apoyo.conteoutec.org/Get_Clases_Otro_CicloJSON";

        SoapObject request = new SoapObject(conexion.getNAMESPACE(),METHOD_NAME);
        request.addProperty("ciclo",Ciclo);
        request.addProperty("año",Anio);
        request.addProperty("facultad",Facultad);



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

            ArrayList IdClases = new ArrayList<String>();
            ArrayList Materias = new ArrayList<String>();
            ArrayList Codigo = new ArrayList<String>();
            ArrayList Docente = new ArrayList<String>();
            ArrayList Aulas = new ArrayList();
            ArrayList Edificios = new ArrayList<String>();
            ArrayList Dias = new ArrayList<String>();
            ArrayList Secciones = new ArrayList<String>();
            ArrayList Inscritos = new ArrayList<String>();
            ArrayList Horas = new ArrayList<String>();
            ArrayList Ciclos = new ArrayList<String>();

            ArrayList Facultades = new ArrayList<String>();
            try {
                if (length <=0){
                    ID_CLASES.clear();
                    MATERIAS.clear();
                    CODIGO.clear();
                    DOCENTE.clear();
                    AULAS.clear();
                    EDIFICIO.clear();
                    DIAS.clear();
                    SECCION.clear();
                    SECCION.clear();
                    INCRITOS.clear();
                    HORA.clear();

                }
            }catch (Exception ex){}

            for (int i = 0; i < length; i++)
            {
                JSONObject v = obtener.getJSONObject(i);
                String id_clase =v.getString("ID_CLASE");
                String materia =v.getString("MATERIA");
                String codigo =v.getString("CODIGO");
                String docente =v.getString("DOCENTE");
                String aula = v.getString("NUMERO_AULA");
                String edificio = v.getString("EDIFICIO");
                String dia = v.getString("DIA_DIAS");
                String seccion = v.getString("SECCION");
                String inscritos = v.getString("NUM_INSCRITOS");
                String hora_inicio = v.getString("HORA_INICIO");
                String hora_final = v.getString("HORA_FIN");

                String facultad = v.getString("FACULTAD");
                Ciclos.add( v.getString("CICLO"));

                if (!id_clase.isEmpty() && id_clase !=null){
                    IdClases.add(id_clase);
                    Materias.add(materia);
                    Codigo.add(codigo);
                    Docente.add(docente);
                    Aulas.add(aula);
                    Edificios.add(edificio);
                    Dias.add(dia);
                    Secciones.add(seccion);
                    Facultades.add(facultad);
                    Inscritos.add(inscritos);
                    Horas.add(hora_inicio.substring(0,5)+"-"+hora_final.substring(0,5));

                }
                setID_CLASES(IdClases);
                setMATERIAS(Materias);
                setCODIGO(Codigo);
                setDOCENTE(Docente);
                setFACULTAD(Facultades);
                setAULAS(Aulas);
                setEDIFICIO(Edificios);
                setDIAS(Dias);
                setSECCION(Secciones);
                setINCRITOS(Inscritos);
                setHORA(Horas);
                setCICLO(Ciclos);

            }
        }
        catch (Exception ex){
            Log.d("error",ex.getMessage());
        }
    }
}
