package com.example.prajakta.notes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private List<String> allNotes;
    private DBAdapter db;
    private ListView lister;
    private int itemClicked;
    private File mediaStorageDir;
    private static final String IMAGE_DIRECTORY_NAME = "Notes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPublicDirectory();
        openDatabase();
        populateListView();

    }

    private void setupPublicDirectory() {
         mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
               return ;
            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            Intent i = new Intent(MainActivity.this,AddPhoto.class);
             startActivity(i);
        }

        else if(id== R.id.action_compose)
        {

            Uri packageURI = Uri.parse("package:com.example.prajakta.notes");
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
//            Toast ifx = Toast.makeText(getApplicationContext(), ""+mediaStorageDir, Toast.LENGTH_SHORT);
//             ifx.show();
            if (mediaStorageDir.exists()) {
              DeleteRecursive(mediaStorageDir);
            }
            startActivity(uninstallIntent);

        }
        return super.onOptionsItemSelected(item);

    }
    void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        Toast ifx = Toast.makeText(getApplicationContext(), "Restarted", Toast.LENGTH_SHORT);
//        ifx.show();
        openDatabase();
        populateListView();


    }

    private void openDatabase() {

        db = new DBAdapter(this);
        db.open();
    }

    private void populateListView() {
        lister = (ListView)findViewById(R.id.listView);



        Cursor cursor = db.getAllNotes();
        List<String> array = new ArrayList<String>();
        while(cursor.moveToNext()){
            String uname = cursor.getString(cursor.getColumnIndex("caption"));
            array.add(uname);
        }

        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, array);
       lister.setAdapter(adapter);
        //** onclick listener for list view*/
       lister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               itemClicked = position;
               Intent intent = new Intent(MainActivity.this,ViewPhoto.class);
               intent.putExtra("Itemclicked",itemClicked);
               startActivity(intent);
           }
       });
    }




    @Override
    protected void onResume() {
        super.onResume();

    }



}
