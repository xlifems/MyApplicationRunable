package com.example.adrian.myapplicationrunable;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progresss);
        imageView = (ImageView) findViewById(R.id.imageView1);


        pro();

    }

    public void pro(){
        progressBar.setVisibility(View.VISIBLE);
        new Thread(){
            public void run() {
                try
                {
                    //espera de 3 segundos para que de tiempo de ver el progress si la descarga es muy rapida
                    //Thread.sleep(3000);
                    URL url = new URL("https://images5.alphacoders.com/481/thumb-1920-481903.png");
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Message message = new Message();
                    message.obj = bmp;
                    message.what = 1;
                    getHandler.sendMessage(message);

                }
                catch (Exception ex)
                {
                    Log.e(MainActivity.class.toString(), ex.getMessage(), ex);
                    Message message = new Message();
                    message.what = 2;
                    getHandler.sendMessage(message);
                }
            }
        }.start();
    }

    private Handler getHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //la tarea en segundo plano ya ha terminado. Ocultamos el progreso.
            progressBar.setVisibility(View.GONE);
            //si tenemos la imagen la mostramos
            if (msg.what == 1 && msg.obj != null)
            {
                imageView.setImageBitmap((Bitmap) msg.obj);
                imageView.setVisibility(View.VISIBLE);
            }
            //si no, informamos del error
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("titulo");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage("error");
                builder.setNeutralButton("ok", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCancelable(false);
            }
        }
    };

    public void ok(View view){
       pro();
    }
}
