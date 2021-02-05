package com.example.adoublei;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static android.graphics.BitmapFactory.decodeByteArray;
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

    private static final String charsetName = "UTF-8";
    private  String useruuid = "name";


    private byte[] seed = useruuid.getBytes();
    private byte[] Byte_image;
    private byte[] EncryptImg;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_upload = findViewById(R.id.button_main_insert);
        imageView = findViewById(R.id.photo_listitem);
        textView = findViewById(R.id.title_listitem);

        mypage = findViewById(R.id.mypage); //마이페이지 버튼
        drawerLayout = findViewById(R.id.firstlayout); //마이페이지 레이아웃

        mRecyclerView = findViewById(R.id.recyclerview_main_list);
        int numberOfColumns = 3;
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getApplication(),numberOfColumns);
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
                intent.putExtra("title",itemObject.getTitle());
                intent.putExtra("photo",itemObject.getPhoto());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //String currentTitle = mItem.get(position).getTitle();
               // String currentPhoto = mItem.get(position).getPhoto();
              //  showDeleteDataDialog(currentTitle,currentPhoto);
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
   /* private void showDeleteDataDialog(final String currentTitle, final String currentPhoto){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainUpload.this);
        builder.setTitle("Delete");
        builder.setMessage("정말로 삭제하시겠습니까?");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    //"yes" -> delete
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                        .child(currentUser.getUid()).child("Object");

                Query mQuery = databaseReference.orderByChild("title").equalTo(currentPhoto);
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(MainUpload.this,"삭제되었습니다.",Toast.LENGTH_SHORT);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainUpload.this,error.getMessage(),Toast.LENGTH_SHORT);
                    }
                });

            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    //"no" -> just dismiss dialog
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    */
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

            ImgBitmap = resize(getApplicationContext(), filePath, 300);

            Byte_image = BitmapToByteArray(ImgBitmap);


            try {
                // Uri파일로 bitmap resize
                //resize(getApplicationContext(), filePath, 1000);

                // 이미지 암호화
                EncryptImg = aesCoderAndriod.encrypt(seed, Byte_image);
                //EncryptImg = Base64.encodeToString(Byte_image,0);
                //String EncryptString = EncryptImg.toString();
                String EncrypString = Base64.encodeToString(EncryptImg,0);

               // Log.e("Encrypt", EncrypString);
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                            .child(currentUser.getUid()).child("Object");
                String key = databaseReference.push().getKey();
                    HashMap<Object,String> object = new HashMap<Object, String>();

                    object.put("title","image_title");
                    object.put("photo",EncrypString);

                    DatabaseReference keyRef = databaseReference.child(key);
                    keyRef.setValue(object);

            } catch (Exception e) {
                e.printStackTrace();
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ItemObject itemObject = snapshot.getValue(ItemObject.class);
                    String title = itemObject.getTitle();
                    String En1 = itemObject.getPhoto();
                    Log.e("En1", En1);
                    // byte[] En2 = En1.getBytes();
                    // Log.e("En2", En2.toString());
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
                    itemObject = new ItemObject(title, Dn4.toString());
                    mItem.add(itemObject);

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100,bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
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


    private void clearAll(){
        if(mItem !=null){
            mItem.clear();
            if(myAdapter != null){
                myAdapter.notifyDataSetChanged();

            }
        }
        mItem = new ArrayList<>();
    }

}

