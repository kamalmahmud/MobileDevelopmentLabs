package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.telephony.euicc.DownloadableSubscription;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    EditText txturl;
    Button btn;
    ImageView img;
    private static final int REQUEST_EXTERNAL_STORAGE=1;
    private  static String[] PERMISSION_STORAGE={
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        txturl=findViewById(R.id.txtURL);
        btn=findViewById(R.id.btnDownload);
        img=findViewById(R.id.imgView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission= ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
                int gg=PackageManager.PERMISSION_GRANTED;
                if(permission!=PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,PERMISSION_STORAGE,REQUEST_EXTERNAL_STORAGE);
                }
//                String fileName="temp.jpg";
//                String imagePath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName;
//                downloadFile(txturl.getText().toString(),imagePath);
//                preview(imagePath);
                else{
//                    DownloadTask backgroundTask=new DownloadTask();
//                    String[] urls = new String[1];
//                    urls[0] =txturl.getText().toString();
//                    backgroundTask.execute(urls);
                    Thread background= new Thread(new DownLoadRunnable(txturl.getText().toString()));
                    background.start();
                }
            }
        });
    }

    private void downloadFile(String url, String imagePath) {
        try {
            URL strUrl= new URL(url);
            URLConnection connection=strUrl.openConnection();
            connection.connect();
            InputStream inputstream=new BufferedInputStream(strUrl.openStream(),8192);
            OutputStream outputstream = new FileOutputStream(imagePath);
            byte data []= new byte[1024];
            int count;
            while ((count=inputstream.read(data))!=-1){
                outputstream.write(data,0,count);
            }
            outputstream.flush();
            inputstream.close();
            outputstream.close();
        }
        catch (Exception e ){
            e.printStackTrace();
        }
    }
    private Bitmap rescalBitmap(String imagePath){
        Bitmap image= BitmapFactory.decodeFile(imagePath);
        float imageWidth=image.getWidth();
        float imageheight=image.getHeight();
        int rescalewidth=400;
        int rescaledheight = (int )((imageheight*rescalewidth)/imageWidth);
        Bitmap bitmap= Bitmap.createScaledBitmap(image,rescalewidth,rescaledheight,false);
        return bitmap;
    }
    class DownloadTask extends AsyncTask<String ,Integer,Bitmap>{
        ProgressDialog progressDialog;

        @Override
        protected Bitmap doInBackground(String... urls) {
            String fileName= "temp.jpg";
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            downloadFile(urls[0],imagePath+"/"+fileName);
            return rescalBitmap(imagePath+"/"+fileName);
        }
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog= new ProgressDialog(MainActivity.this);
            progressDialog.setMax(100);
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }
        protected void onPostExecute(Bitmap bitmap){
            img.setImageBitmap(bitmap);
            progressDialog.dismiss();

        }
        protected void onProgressUpdate(Integer... progress) {
            progressDialog.setProgress(progress[0]);
        }


    }
    class DownLoadRunnable implements Runnable{
        String url;
        public DownLoadRunnable(String url){
            this.url=url;
        }
        public void run(){
            String fileName= "temp.jpg";
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            downloadFile(url,imagePath+"/"+fileName);
            Bitmap bitmap=rescalBitmap(imagePath+"/"+fileName);
            runOnUiThread(new UpdateBitmap(bitmap));

        }
        class UpdateBitmap implements Runnable {
            Bitmap bitmap;
            public UpdateBitmap(Bitmap bitmap){
                this.bitmap=bitmap;
            }
            public void run(){
                img.setImageBitmap((bitmap));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
//            String fileName="temp.jpg";
//            String imagePath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName;
//            downloadFile(txturl.getText().toString(),imagePath);
//            preview(imagePath);
//            DownloadTask backgroundTask=new DownloadTask();
//            String[] urls = new String[1];
//            urls[0] =txturl.getText().toString();
//            backgroundTask.execute(urls);
            Thread background= new Thread(new DownLoadRunnable(txturl.getText().toString()));
            background.start();
        }else {
            Toast.makeText(this,"External Storge Permission isn't granated",Toast.LENGTH_SHORT).show();
        }


    }
    public void preview(String imgPath){
        Bitmap image= BitmapFactory.decodeFile(imgPath);
        float imageWidth=image.getWidth();
        float imageheight=image.getHeight();
        int rescalewidth=400;
        int rescaledheight = (int )((imageheight*rescalewidth)/imageWidth);
        Bitmap bitmap= Bitmap.createScaledBitmap(image,rescalewidth,rescaledheight,false);
        img.setImageBitmap(bitmap);
    }


}



