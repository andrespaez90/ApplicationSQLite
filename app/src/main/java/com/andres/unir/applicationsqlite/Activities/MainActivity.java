package com.andres.unir.applicationsqlite.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andres.unir.applicationsqlite.Modelo.Persona;
import com.andres.unir.applicationsqlite.Persistencia.PersistManager;
import com.andres.unir.applicationsqlite.R;

import java.util.Vector;


public class MainActivity extends ActionBarActivity {

    private MainController Controller;
    private Vector<Persona> dataList;
    private int Option;


    private Button btn_add;
    private Button btn_delete;
    private Button btn_search;
    private Button btn_all;

    private ListView Lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        PersistManager pm = new PersistManager(this);

    }

    private void init(){
        Controller = new MainController();

        btn_add = (Button)findViewById(R.id.main_plus);
        btn_search = (Button)findViewById(R.id.main_search);
        btn_delete = (Button)findViewById(R.id.main_minus);
        btn_all = (Button)findViewById(R.id.main_all);

        btn_add.setOnClickListener(Controller);
        btn_search.setOnClickListener(Controller);
        btn_delete.setOnClickListener(Controller);
        btn_all.setOnClickListener(Controller);

        createdList(getSaved());
    }

    private void createdList(final Vector<String> names) {
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names){};

        Lista = (ListView) findViewById(R.id.main_list);
        Lista.setAdapter(itemsAdapter);
        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                TextView v = (TextView)view.findViewById(android.R.id.text1);
                String[] info = v.getText().toString().split(" ");
                Option = 3;
                createDialog("Editar","Ingrese el nombre a editar:",info[1],info[3]);
            }
        });
    }


    private Vector<String> getSaved(){
        PersistManager pm = new PersistManager(this);
        dataList = pm.getsaved();;
        Vector<String> data= new Vector<String>();
        for(Persona p :dataList){
            String aux = "Nombre: "+p.getNombre()+" DNI: "+p.getDNI();
            data.add(aux);
        }
        return data;
    }

    private void doSomething(String value,String constant) {
        PersistManager pm = new PersistManager(this);
        if( Option == 1){
            pm.Delete(value);
        }else if( Option == 2){
            createdList( find(value) );
            return;
        }
        else{
            pm.Edit(constant,value);
        }
        createdList( getSaved() );
    }

    private Vector<String> find(String value) {
        Vector<String> data = new Vector<String>();
        for(Persona p :dataList){
            if(p.getDNI().equals(value)) {
                String aux = "Nombre: " + p.getNombre() + " DNI: " + p.getDNI();
                data.add(aux);
            }
        }
        return data;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == 1 ) {
            if(resultCode == RESULT_OK){
                createdList( getSaved() );
            }
        }
    }

    private void createDialog(final String title, String mensaje, final String valInput, final String value) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);


        alert.setTitle(title);
        alert.setMessage(mensaje);

        final EditText input = new EditText(this);
        if(valInput != null)input.setText(valInput);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String val = input.getText().toString();
                doSomething(val,value);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }



    private class MainController implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId() == btn_add.getId()){
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(i, 1);
            }else if(v.getId() == btn_delete.getId()){
                Option = 1;
                createDialog("Borrar","Ingrese el DNI:",null,null);
            }else if(v.getId() == btn_search.getId()){
                Option = 2;
                createDialog("Buscar","Ingrese el DNI a Buscar:",null,null);
            }else if(v.getId() == btn_all.getId()){
                createdList( getSaved() );
            }
        }




    }
}

