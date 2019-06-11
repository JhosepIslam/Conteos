
package admin.lab.app.utec.com.conteos.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import admin.lab.app.utec.com.conteos.Models.Login;
import admin.lab.app.utec.com.conteos.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    SharedPreferences pref;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    Login login = new Login();
    private boolean flag;
    public int getNivel() {
        return Nivel;
    }

    public void setNivel(int nivel) {
        Nivel = nivel;
    }

    private int Nivel;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Switch recordar;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);


        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        recordar=findViewById(R.id.switchRecordar);


        mEmailView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                mPasswordView.requestFocus();
                return true;
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        setCredentialIfExist();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    View view = findViewById(R.id.email_sign_in_button);
                    login.setView(view);
                    log();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                login.setView(view);

                log();

            }
        });

        mLoginFormView = findViewById(R.id.login_form);

    }
    private void log(){

        if (mEmailView.getText().toString().trim().isEmpty() ){
            mEmailView.setError("Se Requiere un Nombre de Usuario");
        }else if (mPasswordView.getText().toString().trim().isEmpty()){
            mPasswordView.setError("Se requiere una Contraseña");
        }
        else if (!isPasswordValid(mPasswordView.getText().toString().trim()))
        {
            mPasswordView.setError("Contraseña no valida ");
        }else {
            login.setUsuario(mEmailView.getText().toString());
            login.setPass(mPasswordView.getText().toString());
            AsyncWS asyncWS = new AsyncWS();
            asyncWS.execute();
        }



    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private  void setCredentialIfExist(){
        String mail = getMailPrefs();
        String pass = getPassPrefs();
        if (!mail.isEmpty() && !pass.isEmpty()){
             mEmailView.setText(mail);
             mPasswordView.setText(pass);
            View view = findViewById(R.id.email_sign_in_button);
            login.setView(view);
            log();

        }
    }
    private  String getMailPrefs(){
        return  pref.getString("email","");
    }
    private  String getPassPrefs(){
        return  pref.getString("pass","");
    }
    private  void  saveOnPreferences(String email, String password){
        if (recordar.isChecked()){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("email",email);
            editor.putString("pass",password);
            editor.commit();
            editor.apply();
        }
    }





    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */


    public  class  AsyncWS extends  AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {

            int resul= login.login();
            if (resul!=-1){
                Nivel=resul;
                flag=true;
                return true;

            }

            else {
                flag=false;
                return false;
            }
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

                if (flag) {
                    String thiPass = login.getPass();
                    String thisUser = login.getUsuario();
                    if (recordar.isChecked()){
                        saveOnPreferences(thisUser, thiPass);
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name", thisUser);
                    intent.putExtra("level",  ""+Nivel);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {


                    Snackbar.make(login.getView(), "Usuario o contraseña invalido", Snackbar.LENGTH_LONG)
                            .show();
                    mPasswordView.setText("");

                }
            }


        }
    }


