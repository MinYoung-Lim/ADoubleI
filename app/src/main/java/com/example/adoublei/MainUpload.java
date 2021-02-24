package com.example.adoublei;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.camera2.TotalCaptureResult;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.text.TextUtils;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.DescriptorProtos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static android.graphics.BitmapFactory.decodeByteArray;
import static com.example.adoublei.InputEmail.mContext;
import static java.nio.charset.StandardCharsets.UTF_8;

public class MainUpload extends AppCompatActivity {

    RecyclerView mRecyclerView = null;
    MyAdapter myAdapter=null;
    private ArrayList<ItemObject> mItem =new ArrayList<ItemObject>();
    AESCoderAndriod aesCoderAndriod = new AESCoderAndriod();


    private Uri filePath;
    private Button btn_upload;
    private ImageView imageView;
    private Button btn_delete;
    private ImageView mypage;
    private DrawerLayout drawerLayout;
    private boolean isChecked = false;
    private TextView textView;
    private Bitmap ImgBitmap;
    private NavigationView navigation;

    public Switch sw;

    public Boolean switchChecked = false;

    private static final String charsetName = "UTF-8";
    private  String useruuid = "name";


    private byte[] seed = useruuid.getBytes();
    private byte[] Byte_image;
    private byte[] EncryptImg;

    public String path;
    public String[] delete_path = null;
    ArrayList<Object> delete_path2 = new ArrayList<Object>();
    int num = 0;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkSelfPermission();

        btn_upload = findViewById(R.id.button_main_insert);
        imageView = findViewById(R.id.photo_listitem);
        textView = findViewById(R.id.title_listitem);

        mypage = findViewById(R.id.mypage); //마이페이지 버튼
        drawerLayout = findViewById(R.id.firstlayout); //마이페이지 레이아웃
        navigation = findViewById(R.id.navigation);


        //final TextView userName = (TextView) findViewById(R.id.userName);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        /*SharedPreferences prefs =getSharedPreferences("user", MODE_PRIVATE);
        String result = prefs.getString("name", "0"); //키값, 디폴트값
        Log.e("name", result);
        userName.setText(result);*/

        /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + uid + "/user/userName");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.getValue(String.class);
                userName.setText(name);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.pwChange:
                        changePwd();
                        break;
                    case R.id.exit:
                        userDelete();
                        break;
                    case R.id.fingerprintTF:
                        //settingSwitch();

                        break;


                }

                DrawerLayout drawer = findViewById(R.id.firstlayout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        /*Switch sw = findViewById(R.id.switch_finger);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isChecked)
                    Toast.makeText(MainUpload.this, "스위치체크", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "스위치체크X", Toast.LENGTH_LONG).show();
            }
        });
*/
        mRecyclerView = findViewById(R.id.recyclerview_main_list);
        int numberOfColumns = 3;
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getApplication(), numberOfColumns);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mItem = new ArrayList<>();
        loadPhoto();

        myAdapter = new MyAdapter(mItem);
        mRecyclerView.setAdapter(myAdapter);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mRecyclerView.getContext(),mGridLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ItemObject itemObject = mItem.get(position);

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("key", itemObject.getKey());
                intent.putExtra("title",itemObject.getTitle());
                intent.putExtra("photo",itemObject.getPhoto());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }


        }));


        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });



        btn_upload.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              //이미지를 선택
                                              Intent intent = new Intent();
                                              intent.setType("image/*");
                                              intent.setAction(Intent.ACTION_GET_CONTENT);
                                              startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);

                                          }
                                      }
        );


        /*View navHeader = navigation.getHeaderView(0);
        TextView navName = navHeader.findViewById(R.id.userName);
        SharedPreferences prefs =getSharedPreferences("user", MODE_PRIVATE);
        String result = prefs.getString("name", "0"); //키값, 디폴트값
        Log.e("name", result);
        navName.setText(result);*/

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navi_menu, menu);
        MenuItem item = (MenuItem) menu.findItem(R.id.fingerprintTF);
        item.setActionView(R.layout.switch_item);
        Switch switchAB = item
                .getActionView().findViewById(R.id.switch_finger);
        switchAB.setChecked(false);

        switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }*/

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.navi_menu, menu);

       MenuItem item = menu.findItem(R.id.userName);
       SharedPreferences prefs =getSharedPreferences("user", MODE_PRIVATE);
       String result = prefs.getString("name", "0"); //키값, 디폴트값
       Log.e("name", result);
       item.setTitle(result);


       return super.onCreateOptionsMenu(menu);
   }

    private void settingSwitch() {

    }

    private void changePwd() {

        Intent intent = new Intent(getApplicationContext(), ChangePwdActivity.class);
        startActivity(intent);

    }

    private void userDelete() {

        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainUpload.this)
                .setTitle("회원 탈퇴")
                .setMessage("정말로 회원을 탈퇴하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "회원 탈퇴에 성공하였습니다", Toast.LENGTH_LONG).show();
                                            Log.e("회원탈퇴", "성공");
                                            finishAffinity();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            System.exit(0);

                                        }
                                        else{
                                            Log.e("회원탈퇴", "실패");
                                            Toast.makeText(getApplicationContext(), "회원 탈퇴에 실패하였습니다", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                    } })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();


    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }



    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainUpload.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainUpload.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });

        }




        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

    }



    //결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {

            filePath = data.getData();

            //ImgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

            ImgBitmap = resize(getApplicationContext(), filePath, 1000);


            Byte_image = BitmapToByteArray(ImgBitmap);


            try {
                // Uri파일로 bitmap resize
                //resize(getApplicationContext(), filePath, 1000);

                // 이미지 암호화
                EncryptImg = aesCoderAndriod.encrypt(seed, Byte_image);

                String EncrypString = Base64.encodeToString(EncryptImg,0);

                // Log.e("Encrypt", EncrypString);
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                        .child(currentUser.getUid()).child("Object");
                String key = databaseReference.push().getKey();
                HashMap<Object,String> object = new HashMap<Object, String>();

                object.put("key", key);
                object.put("title","IMG_" + System.currentTimeMillis()/10000000);
                object.put("photo",EncrypString);

                Log.e("올릴때의 key", key);

                DatabaseReference keyRef = databaseReference.child(key);
                keyRef.setValue(object);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public Bitmap rotate(Bitmap bitmap, int degrees)
    {
        if(degrees != 0 && bitmap != null)
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try
            {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap != converted)
                {
                    bitmap.recycle();
                    bitmap = converted;
                }
            }
            catch(OutOfMemoryError ex)
            {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;
    }

    public int exifOrientationToDegrees(int exifOrientation)
    {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
        {
            return 90;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
        {
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
        {
            return 270;
        }
        return 0;
    }

    public void fileDelete(){
        for(int i=0;i<delete_path2.size();i++){
            //fileDelete((String) delete_path2.get(i));
            Log.e("hello", (String) delete_path2.get(i));
            File file = new File((String) delete_path2.get(i));
            if(file.exists()){
                if(file.delete()){
                    Log.e("file delete", "성공");
                }
                Log.e("file delete", "실패");
            }
        }
    }
    public void loadPhoto() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(currentUser.getUid()).child("Object");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mItem.clear();
                num = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ItemObject itemObject = snapshot.getValue(ItemObject.class);
                    String key = itemObject.getKey();
                    String title = itemObject.getTitle();
                    String En1 = itemObject.getPhoto();

                    Log.e("받았을때의 key", key);


                    byte[] Dn1 = (Base64.decode(En1, 0));
                    byte[] Dn2 = new byte[0];
                    try {
                        Dn2 = aesCoderAndriod.decrypt(seed, Dn1);
                        //Log.e("Dn2", String.valueOf(Dn2));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Bitmap Dn3 = byteArrayToBitmap(Dn2);
                    Uri Dn4 = getImageUri(getApplicationContext(), Dn3);
                    //  Log.e("Dn3", String.valueOf(Dn3));
                    ItemObject itemObject1 = new ItemObject(key, title, Dn4.toString());
                    mItem.add(itemObject1);

                    //File delete_file = new File(please);
                    //boolean delete = delete_file.delete();
                    //fileDelete("");
                    //fileDelete(delete_path);

//                    String delete_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/";
//
//                    File delete_file;
//                    if(num==0){
//                        delete_path += "Title.jpg";
//                        delete_file = new File(delete_path);
//                        //delete_path = "/storage/emulated/Pictures/Title.jpg";
//                    }
//                    else{
//                        delete_path += "Title ("+num+").jpg";
//                        delete_file = new File(delete_path);
//
//                    }
//                    Log.e("delete_path",delete_path);
//
//                    delete_file.delete();
                    num++;


                }
                myAdapter.notifyDataSetChanged();
                num = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//        File file = new File(getPath(Uri.parse(path)));
//        if(file.exists()){
//            if(file.delete()){
//                Log.e("file delete", "성공");
//            }
//            Log.e("file delete", "실패");
//        }


   /*     databaseReference.removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
                ItemObject itemObject = snapshot.getValue(ItemObject.class);
                String title = itemObject.getTitle();
                String photo = itemObject.getPhoto();
                itemObject = new ItemObject(title, photo);
                mItem.remove(itemObject);
                myAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    */

    }

    public static boolean fileDelete(String filePath){
        try{
            File file = new File(filePath);

            if(file.exists()){

                file.delete();
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    private Uri getImageUri(Context context, Bitmap inImage) { //bitmap -> Uri
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
        path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);

        delete_path2.add(getPath(Uri.parse(path)));

        return Uri.parse(path);
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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
    public static Bitmap byteArrayToBitmap1(byte[] byteArray ) {
        //바이트 배열
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        int width = options.outWidth;  //이미지의 너비
        int height = options.outHeight; //이미지의 높이
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); //비트맵을 생성
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        buffer.rewind();
        bitmap.copyPixelsFromBuffer(buffer);

        return bitmap;
    }
    public static Bitmap byteArrayToBitmap(byte[] byteArray){

        Bitmap bitmap = decodeByteArray(byteArray,0,byteArray.length);
        return bitmap;
    }

    public static Bitmap resize(Context context, Uri uri, int resize) {
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

    /*public boolean onPrepareOptionMenu(Menu menu){
        MenuItem checkable = menu.findItem(R.id.fingerprintTF);
        checkable.setChecked(isChecked);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.fingerprintTF:
                isChecked = !item.isChecked();
                item.setChecked(isChecked);
                Toast.makeText(getApplicationContext(), "스위치클릭", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }*/


    private void clearAll(){
        if(mItem !=null){
            mItem.clear();
            if(myAdapter != null){
                myAdapter.notifyDataSetChanged();

            }
        }
        mItem = new ArrayList<>();
    }

    public void checkSelfPermission() {
        String temp = "";
        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        }
        else {
            // 모두 허용 상태
            //Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==1){
            int length = permissions.length;
            for (int i=0;i<length;i++){
                if (grantResults[i]==PackageManager.PERMISSION_GRANTED)
                    Log.e("MainUpload", "권한허용"+permissions[i]);
            }
        }

    }
}


