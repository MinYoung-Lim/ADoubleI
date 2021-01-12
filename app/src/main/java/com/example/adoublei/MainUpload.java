package com.example.adoublei;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainUpload extends AppCompatActivity {

    RecyclerView mRecyclerView = null;
    MyAdapter myAdapter=null;
    ArrayList<ItemObject> mItem = new ArrayList<ItemObject>();

    private String text;
    public String encryptText2;
    private Uri filePath;
    private Button btn_upload;
    private ImageView imageView;
    private Button btn_delete;
    private ImageView mypage;
    private DrawerLayout drawerLayout;
    private boolean isChecked = false;


    ValueEventListener mValueEventListener;

    private GridLayoutManager mGridLayoutManager;
  /*  private String EncryptImg="";
    private String DecryptImg="";
    private String bitmapToString="";
    private Bitmap stringToBitmap;

    */
    private static final String charsetName = "UTF-8";
  //  private String useruuid = null;
    private  String useruuid = "name";
    static public String userID;

    private byte[] seed = useruuid.getBytes();
    private byte[] Byte_image;
    private byte[] EncryptImg;
    private byte[] DecryptImg;
    private Bitmap Bitmap_image;

    private View view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_upload = findViewById(R.id.button_main_insert);
        imageView = findViewById(R.id.photo_listitem);
 //     btn_delete = findViewById(R.id.button_delete);
        mypage = findViewById(R.id.mypage); //마이페이지 버튼
        drawerLayout = findViewById(R.id.firstlayout); //마이페이지 레이아웃


        mRecyclerView = findViewById(R.id.recyclerview_main_list);
        int numberOfColumns = 3;
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,numberOfColumns);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mItem = new ArrayList<>();

        myAdapter = new MyAdapter(mItem);
        mRecyclerView.setAdapter(myAdapter);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mRecyclerView.getContext(),mGridLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ItemObject itemObject = mItem.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
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
        });



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

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");



        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {

            filePath = data.getData();

            ItemObject item = new ItemObject("주민등록증",filePath);

            mItem.add(item);
            myAdapter.notifyDataSetChanged();


            Bitmap ImgBitmap = null;

            //ImgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            ImgBitmap = resize(getApplicationContext(), filePath, 300);

            Byte_image = BitmapToByteArray(ImgBitmap);
            AESCoderAndriod aesCoderAndriod = new AESCoderAndriod();

            try {
                // Uri파일로 bitmap resize
                //resize(getApplicationContext(), filePath, 1000);

                // 이미지 암호화
                EncryptImg = aesCoderAndriod.encrypt(seed, Byte_image);
                String EncryptString = EncryptImg.toString();
                Log.e("Encrypt", EncryptString);

                // 암호화된 이미지 업로드
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    // User is signed in
                    userID = user.getUid();
                } else {
                    // No user is signed in
                }


                // 복호화
                DecryptImg = aesCoderAndriod.decrypt(seed, EncryptImg);
                //stringToBitmap = StringToBitmap(DecryptImg);
                Bitmap_image = byteArrayToBitmap(DecryptImg);
                imageView.setImageBitmap(Bitmap_image);

                String DecryptString = BitmapToString(Bitmap_image);
                Log.e("Decrypt", DecryptString);

                // 암호화된 이미지 업로드
                DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
                mRootRef.child("users").child(userID).child("Encrypt").push().setValue(EncryptString);
                // 복호화된 이미지 업로드
                DatabaseReference mRootRef2= FirebaseDatabase.getInstance().getReference();
                mRootRef2.child("users").child(userID).child("Decrypt").push().setValue(DecryptString);

            } catch (Exception e) {
                e.printStackTrace();
            }
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
    public Bitmap byteArrayToBitmap( byte[] byteArray ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length ) ;
        return bitmap ;
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
                width /= 4;
                height /= 4;
                samplesize *= 4;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); //3번
            resizeBitmap = bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resizeBitmap;

    }

    public boolean onPrepareOptionMenu(Menu menu){
        MenuItem checkable = menu.findItem(R.id.fingerprintTF);
        checkable.setChecked(isChecked);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.fingerprintTF:
                isChecked = !item.isChecked();
                item.setChecked(isChecked);
                return true;
            default:
                return false;
        }
    }


}

