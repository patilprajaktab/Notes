package com.example.prajakta.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddPhoto extends ActionBarActivity {



    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri = null;
    private static final String IMAGE_DIRECTORY_NAME = "Notes";
    private ImageView imageView;
    public static final int MEDIA_TYPE_IMAGE = 1;
    DBAdapter db = new DBAdapter(this);
    private ImageButton photo;
    private ImageButton done;
    private String captionString=null;
    private TextView textPreview;

    /** On create method  which has definitions of all image buttons & text buttons*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        photo = (ImageButton)findViewById(R.id.imageButton);
        done=(ImageButton)findViewById(R.id.imageButton2);
        imageView=(ImageView)findViewById(R.id.imageView);
        textPreview = (TextView)findViewById(R.id.textPreview);



        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraAction();

            }


        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database_operations();
            }
        });



    }

    private void database_operations() {

        EditText editText = (EditText)findViewById(R.id.editText);
        captionString = String.valueOf(editText.getText());
        db.open();
        if(captionString.isEmpty()|| fileUri==null )
        {
            Toast.makeText(this, "Set Caption and/or Click Image", Toast.LENGTH_SHORT).show();
        }
        else
        {

            long id  = db.insertNote(captionString, fileUri.toString());
//            Cursor c = db.getAllNotes();
//            {
//                if (c.moveToFirst())
//                    do{
//
//                       // DisplayNotes(c);
//
//                    } while( c.moveToNext());
//            }
            db.close();
            finish();
        }



    }

//    public void DisplayNotes(Cursor c)
//    {
//        Toast.makeText(this, "id" +c.getString(0) + " \n" + "Caption " +c.getString(1) + "\n" + "Path " +c.getString(2),Toast.LENGTH_SHORT).show();
//    }


    private void cameraAction() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
                // Toast.makeText(this, "Image saved to:\n" +
                //       data.getData(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void previewCapturedImage() {
        try{
            imageView.setVisibility(View.VISIBLE);


            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imageView.setImageBitmap(bitmap);
            textPreview.setVisibility(View.INVISIBLE);
        }

        catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {


        // External sdcard location //sdcard/Pictures/Hello Camera
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }


        String timeStamp = new SimpleDateFormat("yyyy_MMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        else {
            return null;
        }

        return mediaFile;
    }

}


