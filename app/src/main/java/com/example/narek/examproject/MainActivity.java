package com.example.narek.examproject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DrawView drawView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = (DrawView) findViewById(R.id.draw_view);
        getSharedImage();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    private void getSharedImage()  {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                drawView.setBackgroundBitmap(bitmap);


            }  catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.about:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);


                return true;
            case R.id.export:

                Bitmap bitmap = drawView.asBitmap();


                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null));



                final Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/*");

                startActivity(Intent.createChooser(shareIntent, "Select application to share"));
                Share share = new Share();
                share.setmNotifyManager((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
                share.setmBuilder(new android.support.v7.app.NotificationCompat.Builder(MainActivity.this));
                share.getmBuilder().setContentTitle("Sharing")
                        .setContentText("Sharing in progress")
                        .setSmallIcon(R.drawable.icon);


                share.execute();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
