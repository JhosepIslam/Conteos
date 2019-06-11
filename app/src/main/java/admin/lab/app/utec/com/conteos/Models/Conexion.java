package admin.lab.app.utec.com.conteos.Models;

public class Conexion {
    private final String NAMESPACE="http://apoyo.conteoutec.org/";
    private final String URL="http://192.168.1.84/WS/ConteoUtec.asmx";

    public String getNAMESPACE() {
        return NAMESPACE;
    }

    public String getURL() {
        return URL;
    }
}
