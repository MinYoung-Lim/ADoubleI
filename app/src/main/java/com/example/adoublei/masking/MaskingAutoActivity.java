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

    static String[] result1_ex1 = {"id", "0.47440708", "0.3533485", "0.6719552", "0.38725245"};
    static String[] result_ex2 = {"name", "0.59669155", "0.29243743", "0.686663", "0.3303905"};
    static String[] result_ex3 = {"address", "0.4680234", "0.365", "70442", "0.70887196", "0.4280191"};
    static int NumOfClass_ex = 3;
    public static String[][] result = new String[8][5];


    private ImageView beforeMasking;
    private ArrayList<MaskingItem> mItem = new ArrayList<>();
    private Bitmap image;
    private Bitmap afterMasking;
    File tempSelectFile;
    String filePath = "";

    int image_width=0;
    int image_height=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masking_auto);

        byte[] arr = getIntent().getByteArrayExtra("image");
        image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        beforeMasking = (ImageView) findViewById(R.id.imageMasking);
        beforeMasking.setImageBitmap(image);
        afterMasking = Bitmap.createBitmap(image).copy(Bitmap.Config.ARGB_8888, true);

        image_width = (int) (image.getWidth()*0.7);
        image_height = (int) (image.getHeight()*0.7);
        Bitmap resized = Bitmap.createScaledBitmap(image, image_width, image_height, true);

        filePath = getRealPathFromURI(getImageUri(getApplicationContext(), resized));

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

                // 은지야! 이건 서버 연결했을 때야! 서버 연결 안했을 때는 switch문 전체 주석 처리!

                /*
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

                 */

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
    public void onButtonClicked(View view, String text) {
        Canvas canvas = new Canvas(afterMasking);

        //이건 서버 연결한거! - 수정 필요
//        int image_width = afterMasking.getWidth();
//        int image_height = afterMasking.getHeight();
//        for(int i=0;i<NumOfClass;i++){
//            if(text.equals(result[i][0])){
//                canvas.drawRect( Integer.parseInt(result[i][1])*image_width,
//                        Integer.parseInt(result[i][2])*image_height,
//                        Integer.parseInt(result[i][3])*image_width,
//                        Integer.parseInt(result[i][4])*image_height, new Paint());
//            }
//        }

        // 은지야! 이게 서버 연결 안한거!
        switch (text){
            case "id":
                canvas.drawRect(100, 100, 200, 200, new Paint());
                break;
            case "name":
                canvas.drawRect(130, 130, 200, 200, new Paint());
                break;
            case "address":
                canvas.drawRect(160, 160, 200, 200, new Paint());
                break;
            default:
                break;
        }


        beforeMasking.setImageBitmap(afterMasking);
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
