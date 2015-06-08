package com.andres.unir.applicationsqlite.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andres.unir.applicationsqlite.Modelo.Persona;
import com.andres.unir.applicationsqlite.Persistencia.PersistManager;
import com.andres.unir.applicationsqlite.R;


public class RegisterActivity extends Activity implements View.OnClickListener{

    private Button btn_register;
    private TextView tv_name;
    private  TextView tv_dni;
    private TextView tv_Error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        btn_register = (Button)findViewById(R.id.register_button);
        tv_name = (TextView)findViewById(R.id.register_name);
        tv_dni = (TextView)findViewById(R.id.register_dni);
        tv_Error =(TextView)findViewById(R.id.register_error);

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == btn_register.getId()){
            String name = tv_name.getText().toString();
            String dni = tv_dni.getText().toString();
            if(name.equals("") || dni.equals("")){
                tv_Error.setText("Datos incompletos");
            }else{
                Persona p = new Persona(name, dni);
                PersistManager pm = new PersistManager(this);
                String response  =  pm.Save(p);
                if( response.equals("ok") ){
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                    finish();
                }else if(response.equals("id")){
                    tv_Error.setText("DNI ya Existe");
                }else{
                    tv_Error.setText(response);
                }
            }
        }
    }

}
