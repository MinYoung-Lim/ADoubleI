package com.example.adoublei.masking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.adoublei.FileUploadUtils;
import com.example.adoublei.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import static com.example.adoublei.FileUploadUtils.result1;
import static com.example.adoublei.FileUploadUtils.result2;
import static com.example.adoublei.FileUploadUtils.result3;
import static com.example.adoublei.FileUploadUtils.result4;
import static com.example.adoublei.FileUploadUtils.result5;
import static com.example.adoublei.FileUploadUtils.result6;
import static com.example.adoublei.FileUploadUtils.result7;
import static com.example.adoublei.FileUploadUtils.result8;
import static com.example.adoublei.FileUploadUtils.NumOfClass;


public class MaskingAutoActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {

    private ImageView beforeMasking;
    private ArrayList<MaskingItem> mItem = new ArrayList<>();
    private Bitmap image;
    private Bitmap afterMasking;
    File tempSelectFile;
    String filePath = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masking_auto);

        byte[] arr = getIntent().getByteArrayExtra("image");
        image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        beforeMasking = (ImageView)findViewById(R.id.imageMasking);
        beforeMasking.setImageBitmap(image);
        afterMasking = Bitmap.createBitmap(image).copy(Bitmap.Config.ARGB_8888,true);

        Bitmap resized = Bitmap.createScaledBitmap(image,(int)(image.getWidth()*0.7), (int)(image.getHeight()*0.7), true);

        filePath = getRealPathFromURI(getImageUri(getApplicationContext(), resized));
        //filePath = getRealPathFromURI(uri);

        tempSelectFile = new File(filePath);

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
                



                //아래는 값이 잘 들어왔는지 확인하고싶을때 사용하면 됨
                /*String result="";

                result += "인식된 라벨 개수 : " + NumOfClass + "\n";

                result+="result1 : ";

                for(int i=0;i<5;i++){
                    result += result1[i] + " ";
                }

                result+="\nresult2 : ";

                for(int i=0;i<5;i++){
                    result += result2[i] + " ";
                }

                result+="\nresult3 : ";

                for(int i=0;i<5;i++){
                    result += result3[i] + " ";
                }

                result+="\nresult4 : ";

                for(int i=0;i<5;i++){
                    result += result4[i] + " ";
                }

                result+="\nresult5 : ";

                for(int i=0;i<5;i++){
                    result += result5[i] + " ";
                }

                result+="\nresult6 : ";

                for(int i=0;i<5;i++){
                    result += result6[i] + " ";
                }

                result+="\nresult7 : ";

                for(int i=0;i<5;i++){
                    result += result7[i] + " ";
                }

                result+="\nresult8 : ";

                for(int i=0;i<5;i++){
                    result += result8[i] + " ";
                }


                Log.e("result", result);*/
            }
        }, 1000);



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
    }

    @Override
    public void onSwitchChecked(boolean checked) {
        if (checked == true) {
            Canvas canvas = new Canvas(afterMasking);

            canvas.drawRect(100, 100, 200, 200, new Paint());

            beforeMasking.setImageBitmap(afterMasking);
        } else{
            beforeMasking.setImageBitmap(image);
        }

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

}