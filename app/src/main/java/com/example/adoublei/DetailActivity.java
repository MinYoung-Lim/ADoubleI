package com.example.adoublei;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private byte[] Byte_image;

    private ImageView detailPhoto;
    private EditText detailName;
    private ImageButton btn_download;
    private ImageButton btn_back;
  //  private TextView showName;
    private String str;
    boolean boolean_permission;
    boolean boolean_save;
    Bitmap bitmap_object;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);  // layout xml 과 자바파일을 연결

        detailPhoto = findViewById(R.id.detail_image);
        detailName = findViewById(R.id.thename);
     //   showName = findViewById(R.id.showTheName);
        btn_download = findViewById(R.id.btn_img_download);
        btn_back = findViewById(R.id.btn_back);

        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

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

        final String[] listArray = {"PNG","PDF","마스킹"};
        final int[] selectedIndex = {0};
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                dialog.setTitle("Download");
                dialog.setItems(listArray,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listArray[which]=="PNG"){
                            saveToGallery();
                            Toast.makeText(DetailActivity.this, "갤러리에 저장되었습니다.", Toast.LENGTH_LONG);
                        }if(listArray[which]=="PDF"){
                            //pdf
                            createPdf();
                            Toast.makeText(DetailActivity.this, "pdf파일이 저장되었습니다.", Toast.LENGTH_LONG);
                        }if(listArray[which]=="마스킹"){
                            //마스킹
                            masking();
                        } else{
                            Toast.makeText(DetailActivity.this,"잘못된 요청입니다.",Toast.LENGTH_LONG);
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

    private  void masking(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("개인정보 마스킹");
        builder.setMessage("개인정보가 담긴 부분에 마스킹을 처리하겠습니까?");
        builder.setPositiveButton("자동 선택",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DetailActivity.this, MaskingAutoActivity.class);
                        intent.putExtra("image", Byte_image);
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton("수동 선택",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DetailActivity.this, MaskingActivity.class);
                        intent.putExtra("image", Byte_image);
                        startActivity(intent);
                    }
                });
        builder.show();
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

    //pdf변환
    private void createPdf(){

        BitmapDrawable bitmapDrawable = (BitmapDrawable) detailPhoto.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();


//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(),bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Paint paint = new Paint();



    //    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        page.getCanvas().drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);
        String date = dateName(System.currentTimeMillis());

        // write the document content
        //pdf저장시 파일명은 저장하는 시간으로 설정
        String filePath = Environment.getExternalStorageDirectory().getPath()+"/sdcard/"+date+".pdf";
        File file = new File(filePath);

     /*   String targetPdf = "/sdcard/image.pdf";
        File filePath = new File(targetPdf);

      */
        try {
            document.writeTo(new FileOutputStream(file));
          //  btn_convert.setText("Check PDF");
            boolean_save=true;
         //   Toast.makeText(this, "다운로드 완료 ",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }
    private String dateName(long dateTaken){
        Date date = new Date(dateTaken);
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        return dateFormat.format(date);
    }
}
