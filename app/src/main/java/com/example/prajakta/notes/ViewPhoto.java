package com.example.prajakta.notes;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewPhoto extends ActionBarActivity {
    private DBAdapter db;
    private int rowid;
    private ImageView imageDisplay;
    private TextView captionDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            rowid = extras.getInt("Itemclicked");
            dbOperations();
        }
    }

    private void dbOperations() {

        captionDisplay = (TextView)findViewById(R.id.captionDisplay);
        imageDisplay = (ImageView)findViewById(R.id.imageDisplay);
        db =new DBAdapter(this);

       db.open();
        Cursor c  =db.getNote(rowid+1);

            //Toast.makeText(this, "id" + rowid, Toast.LENGTH_SHORT).show();
            String Uname = c.getString(1);
            captionDisplay.setText(Uname);


            imageDisplay.setImageURI(Uri.parse(c.getString(2)));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
