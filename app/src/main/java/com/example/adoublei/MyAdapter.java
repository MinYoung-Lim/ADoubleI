package com.example.adoublei;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.io.IOException;

import java.util.ArrayList;


import static android.content.ContentValues.TAG;




public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolders> {

    private Context context;
    private ArrayList<ItemObject> mItem;
    private AdapterView.OnItemClickListener mListener = null;
    RequestManager mRequestManager;
    ContentResolver contentResolver;



    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

         TextView title;
         ImageView imageView;
     //   private CardView cardView;


        public RecyclerViewHolders(@NonNull View itemView) {
            super(itemView);
            this.title =  itemView.findViewById(R.id.title_listitem);
            this.imageView = itemView.findViewById(R.id.photo_listitem);

//
           itemView.setOnCreateContextMenuListener(this);


        }


     private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case 1001:

                   // mItem.remove(getAdapterPosition());
                    final String currentKey = mItem.get(getAdapterPosition()).getKey();

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                            .child(currentUser.getUid()).child("Object");

                    databaseReference.child(currentKey).removeValue();

                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mItem.size());
                    notifyDataSetChanged();



                    break;
            }
            return true;

        }
    };



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE,1001,1,"삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);
        }




    }
        public MyAdapter(ArrayList<ItemObject> item,Context context){
             this.context = context;
             this.mItem = item;
     }
    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem,viewGroup,false);
        RecyclerViewHolders viewHolders=new RecyclerViewHolders(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {


        ItemObject itemObject = mItem.get(position);
        String photo = itemObject.getPhoto();


        Uri mPhotoUri = Uri.parse(getRealPathFromURI(Uri.parse(photo)));

        Bitmap bitmap =  BitmapFactory.decodeFile(String.valueOf(mPhotoUri));
        // 이미지 회전
        if (bitmap.getHeight() <= bitmap.getWidth()){
            bitmap = imgRotate(bitmap);
        }

    //    Bitmap realBitmap = GetRotatedBitmap(bitmap,GetExifOrientation(String.valueOf(mPhotoUri)));
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        holder.title.setGravity(Gravity.CENTER);
        holder.title.setText(itemObject.getTitle());
        holder.imageView.setImageBitmap(bitmap);
   /*     int degree = getOrientationOfImage(String.valueOf(mPhotoUri));
        try {
            Bitmap bitmap =  BitmapFactory.decodeFile(String.valueOf(mPhotoUri));
            holder.imageView.setImageBitmap(getRotatedBitmap(bitmap,degree));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



        Picasso.get()
                .load(getRightAngleImage(mPhotoUri.toString()))
                .into(holder.imageView);


    */



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


    public int GetExifOrientation(String filepath)  {

            int degree = 0;
           ExifInterface exif = null;

           try
            {
                   exif = new ExifInterface(filepath);
               }
           catch (IOException e)
           {
                    Log.e(TAG, "cannot read exif");
                   e.printStackTrace();
                }

           if (exif != null)
                {
                  int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

                   if (orientation != -1)
                      {
                            // We only recognize a subset of orientation tag values.
                           switch(orientation)
                                                {
                                   case ExifInterface.ORIENTATION_ROTATE_90:
                                            degree = 90;
                                            break;
                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                          degree = 180;
                                            break;

                                   case ExifInterface.ORIENTATION_ROTATE_270:
                                           degree = 270;
                                           break;
                               }

                        }
                }

         return degree;
        }




    /**

     * 이미지를 특정 각도로 회전

     * @param bitmap

     * @param degrees

     * @return

     */

    public synchronized static Bitmap GetRotatedBitmap(Bitmap bitmap, int degrees)
{
            if ( degrees != 0 && bitmap != null )
                {
                    Matrix m = new Matrix();
                   m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2 );
                    try
                   {
                            Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                            if (bitmap != b2)
                                {
                                   bitmap.recycle();
                                   bitmap = b2;
                              }
                       }
                    catch (OutOfMemoryError ex)
                    {
                           // We have no memory to rotate. Return the original bitmap.
                       }
                }

            return bitmap;
        }

    //사진의 절대경로 찾아주는 함수
    private String getRealPathFromURI(Uri contentURI) {
        String[]proj={MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(contentURI,proj,null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void setmItem(ArrayList<ItemObject> mItem) {
        this.mItem = mItem;
    }


    public void deleteItem(int position) {

            mItem.remove(position);

            notifyItemRemoved(position);
            notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return mItem.size();
    }
}





