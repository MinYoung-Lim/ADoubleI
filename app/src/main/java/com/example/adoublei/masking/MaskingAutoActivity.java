package com.example.adoublei.masking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adoublei.DetailActivity;
import com.example.adoublei.FileUploadUtils;
import com.example.adoublei.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.adoublei.FileUploadUtils.result1;
import static com.example.adoublei.FileUploadUtils.result2;
import static com.example.adoublei.FileUploadUtils.result3;
import static com.example.adoublei.FileUploadUtils.result4;
import static com.example.adoublei.FileUploadUtils.result5;
import static com.example.adoublei.FileUploadUtils.result6;
import static com.example.adoublei.FileUploadUtils.result7;
import static com.example.adoublei.FileUploadUtils.result8;
import static com.example.adoublei.FileUploadUtils.NumOfClass;
import static com.example.adoublei.masking.BottomSheetDialog.flag;

public class MaskingAutoActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {

    //static String[] result1_ex1 = {"id", "0.47440708", "0.3533485", "0.6719552", "0.38725245"};
    //static String[] result_ex2 = {"name", "0.59669155", "0.29243743", "0.686663", "0.3303905"};
    //static String[] result_ex3 = {"address", "0.4680234", "0.365", "70442", "0.70887196", "0.4280191"};
    //static int NumOfClass_ex = 3;
    public static String[][] result = new String[8][5];
    public static int NumOfClass_Masking;

    private ImageView beforeMasking;
    private ArrayList<MaskingItem> mItem = new ArrayList<>();
    private Bitmap image;
    private Bitmap afterMasking;
    File tempSelectFile;
    String filePath = "";

    private ImageButton btn_back;
    private ImageButton btn_download;
    private TextView image_name;

    int image_width=0;
    int image_height=0;
    boolean boolean_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masking_auto);

        //byte[] arr = getIntent().getByteArrayExtra("image");
        String image_intent = getIntent().getStringExtra("image_string");
        String image_title = getIntent().getStringExtra("image_title");

        image = resize(getApplicationContext(), Uri.parse(image_intent), 1000);

        //image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        beforeMasking = (ImageView) findViewById(R.id.imageMasking);
        btn_back = findViewById(R.id.btn_back);
        btn_download = findViewById(R.id.btn_img_download);
        image_name = findViewById(R.id.thename);
        image_name.setText(image_title);

        // 이미지 회전
        if (image.getHeight() <= image.getWidth()){
            image = imgRotate(image);
        }

        beforeMasking.setImageBitmap(image);

        image_width = image.getWidth();
        image_height = image.getHeight();
        Bitmap resized = Bitmap.createScaledBitmap(image, image_width, image_height, true);

        filePath = getRealPathFromURI(getImageUri(getApplicationContext(), image));

        tempSelectFile = new File(filePath);

        // 뒤로가기 버튼
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 다운로드 버튼
        final String[] listArray = {"PNG","PDF"};
        final int[] selectedIndex = {0};
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MaskingAutoActivity.this);
                dialog.setTitle("Download");
                dialog.setItems(listArray,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listArray[which]=="PNG"){
                            saveToGallery();
                            Toast.makeText(getApplicationContext(), "갤러리에 저장되었습니다.", Toast.LENGTH_LONG);
                        }if(listArray[which]=="PDF"){
                            //pdf
                            createPdf();
                            Toast.makeText(getApplicationContext(), "pdf파일이 저장되었습니다.", Toast.LENGTH_LONG);
                        }else{
                            Toast.makeText(getApplicationContext(),"잘못된 요청입니다.",Toast.LENGTH_LONG);
                        }

                    }

                }).create().show();
            }
        });

        Button buttonOpenBottomSheet = findViewById(R.id.buttonOpenBottomSheet);
        buttonOpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "detailBottomSheet");
            }
        });

        // 인공지능 Part  21-02-03 임민영
        ResetStaticResult();  // static result, NumOfClass 초기화
        FileUploadUtils.send2Server(tempSelectFile);  // Flask서버에 이미지 전송

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                // 시간 지난 후 실행할 코딩
                // 은지야 이부분에 result1~8, NumOfClass 사용하는 코드 작성하면돼!
                // 다른데서 사용하면 값이 안들어와져있을거야..!

                // 은지야! 이건 서버 연결했을 때야! 서버 연결 안했을 때는 switch문 전체 주석 처리!
                NumOfClass_Masking = NumOfClass;
                switch (NumOfClass){
                    case 0:
                        break;
                    case 1:
                        result[0] = result1;
                        break;
                    case 2:
                        result[0] = result1;
                        result[1] = result2;
                        break;
                    case 3:
                        result[0] = result1;
                        result[1] = result2;
                        result[2] = result3;
                        break;
                    case 4:
                        result[0] = result1;
                        result[1] = result2;
                        result[2] = result3;
                        result[3] = result4;
                        break;
                    case 5:
                        result[0] = result1;
                        result[1] = result2;
                        result[2] = result3;
                        result[3] = result4;
                        result[4] = result5;
                        break;
                    case 6:
                        result[0] = result1;
                        result[1] = result2;
                        result[2] = result3;
                        result[3] = result4;
                        result[4] = result5;
                        result[5] = result6;
                        break;
                    case 7:
                        result[0] = result1;
                        result[1] = result2;
                        result[2] = result3;
                        result[3] = result4;
                        result[4] = result5;
                        result[5] = result6;
                        result[6] = result7;
                        break;
                    case 8:
                        result[0] = result1;
                        result[1] = result2;
                        result[2] = result3;
                        result[3] = result4;
                        result[4] = result5;
                        result[5] = result6;
                        result[6] = result7;
                        result[7] = result8;
                        break;
                }


                //아래는 값이 잘 들어왔는지 확인하고싶을때 사용하면 됨
//                String result="";
//                result += "인식된 라벨 개수 : " + NumOfClass + "\n";
//                result+="result1 : ";
//                for(int i=0;i<5;i++){
//                    result += result1[i] + " ";
//                }
//                result+="\nresult2 : ";
//                for(int i=0;i<5;i++){
//                    result += result2[i] + " ";
//                }
//                result+="\nresult3 : ";
//                for(int i=0;i<5;i++){
//                    result += result3[i] + " ";
//                }
//                result+="\nresult4 : ";
//                for(int i=0;i<5;i++){
//                    result += result4[i] + " ";
//                }
//                result+="\nresult5 : ";
//                for(int i=0;i<5;i++){
//                    result += result5[i] + " ";
//                }
//                result+="\nresult6 : ";
//                for(int i=0;i<5;i++){
//                    result += result6[i] + " ";
//                }
//                result+="\nresult7 : ";
//                for(int i=0;i<5;i++){
//                    result += result7[i] + " ";
//                }
//                result+="\nresult8 : ";
//                for(int i=0;i<5;i++){
//                    result += result8[i] + " ";
//                }
//                Log.e("result", result);
            }
        }, 1000);
    }

    private Bitmap imgRotate(Bitmap bmp){
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        bmp.recycle();

        return resizedBitmap;
    }

    private void ResetStaticResult() {
        // static값 초기화
        result1 = new String[]{"", "", "", "", ""};
        result2 = new String[]{"", "", "", "", ""};
        result3 = new String[]{"", "", "", "", ""};
        result4 = new String[]{"", "", "", "", ""};
        result5 = new String[]{"", "", "", "", ""};
        result6 = new String[]{"", "", "", "", ""};
        result7 = new String[]{"", "", "", "", ""};
        result8 = new String[]{"", "", "", "", ""};
        NumOfClass=0;
        result = new String[][]{{"","","","",""}, {"","","","",""}, {"","","","",""}, {"","","","",""},
                {"","","","",""}, {"","","","",""}, {"","","","",""}, {"","","","",""}};
        flag = new String[]{"","","","","","","",""};

    }

    @Override
    public void onButtonClicked(View view, String text, Boolean bool) {

        afterMasking = Bitmap.createBitmap(image).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(afterMasking);

        int image_width = afterMasking.getWidth();
        int image_height = afterMasking.getHeight();

        for(int k = 0; k< NumOfClass_Masking; k++){
            if(flag[k].equals("O")){
                int[] location = {Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(result[k][1]) * image_width))),
                        Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(result[k][2]) * image_height))),
                        Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(result[k][3]) * image_width))),
                        Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(result[k][4]) * image_height)))};

                for (int j = 0; j < 4; j++) {
                    Log.e("location", String.valueOf(location[j]));
                }

                canvas.drawRect(location[0], location[1], location[2], location[3], new Paint());

            }
        }

            // 은지야! 이게 서버 연결 안한거!
//        switch (text){
//            case "id":
//                canvas.drawRect(100, 100, 200, 200, new Paint());
//                break;
//            case "name":
//                canvas.drawRect(130, 130, 200, 200, new Paint());
//                break;
//            case "address":
//                canvas.drawRect(160, 160, 200, 200, new Paint());
//                break;
//            default:
//                break;
//        }

        beforeMasking.setImageBitmap(afterMasking);

    }

    // 사진 갤러리 저장
    private void saveToGallery(){

        BitmapDrawable bitmapDrawable = (BitmapDrawable)beforeMasking.getDrawable();
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

        BitmapDrawable bitmapDrawable = (BitmapDrawable)beforeMasking.getDrawable();
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

    private String getRealPathFromURI(Uri contentURI) {

        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        //String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        String path = MediaStore.Images.Media.insertImage(
                inContext.getContentResolver(), inImage, "IMG_" + System.currentTimeMillis(), null
        );
        return Uri.parse(path);
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

}

//    @Override
//    public void onSwitchChecked(boolean checked) {
//        if (checked == true) {
//            Canvas canvas = new Canvas(afterMasking);
//
//            canvas.drawRect(100, 100, 200, 200, new Paint());
//
//            beforeMasking.setImageBitmap(afterMasking);
//        } else{
//            beforeMasking.setImageBitmap(image);
//        }
//    }