package admin.lab.app.utec.com.conteos.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public boolean ValidarCorreo(String Correo){

       // Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        //        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@mail.utec.edu.sv");
        Matcher matcher = pattern.matcher(Correo);
        if (matcher.find()){
            return true;
        }
        else
        return false;
    }


}
