package com.example.adoublei;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DetailActivity extends AppCompatActivity {

    private byte[] Byte_image;

    private ImageView detailPhoto;
    private EditText detailName;
    private ImageButton btn_download;
    private ImageButton btn_back;
  //  private TextView showName;
    private String str;
    private String temp;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);  // layout xml 과 자바파일을 연결

        detailPhoto = findViewById(R.id.detail_image);
        detailName = findViewById(R.id.thename);
     //   showName = findViewById(R.id.showTheName);
        btn_download = findViewById(R.id.btn_img_download);
        btn_back = findViewById(R.id.btn_back);



        final Intent intent = getIntent();

        Uri photo = intent.getParcelableExtra("photo");
        final String title = intent.getStringExtra("title");

        Bitmap ImgBitmap = null;

        //ImgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
        ImgBitmap = resize(getApplicationContext(), photo, 1000);

        Byte_image = BitmapToByteArray(ImgBitmap);

        detailPhoto.setImageBitmap(ImgBitmap);
        detailName.setText(title);

        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,MainUpload.class);
                intent.putExtra("title", str);
                startActivity(intent);
            }
        });

        final String[] listArray = {"PNG","PDF"};
        final int[] selectedIndex = {0};
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                dialog.setTitle("DownLoad");
                dialog.setItems(listArray,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listArray[which]=="PNG"){
                            saveToGallery();
                            Toast.makeText(DetailActivity.this, "갤러리에 저장되었습니다.", Toast.LENGTH_LONG);
                        }else{
                            //pdf
                        }
                    }
                }).create().show();
            }
        });


        detailName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    //editText에서 받은 문자열 저장
                    str = detailName.getText().toString();
                    detailName.setText(str);
                    return true;
                }
                return false;

            }
        });

    } // end onCreate()

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //Bitmap을 String형으로 변환
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 15, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }


    //Bitmap을 byte배열로 변환
    public static byte[] BitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        return baos.toByteArray();
    }

    //byte를 Bitmap으로 변환
    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    private Bitmap resize(Context context, Uri uri, int resize) {
        Bitmap resizeBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); // 1번

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); //3번
            resizeBitmap = bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resizeBitmap;

    }

    private void saveToGallery(){

        BitmapDrawable bitmapDrawable = (BitmapDrawable) detailPhoto.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/MyPics");
        dir.mkdirs();

        String filename = String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try{
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
