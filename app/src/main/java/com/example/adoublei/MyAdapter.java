package com.example.adoublei;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolders> {

    private Context context;
    private ArrayList<ItemObject> mItem;
    private AdapterView.OnItemClickListener mListener = null;




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






        public MyAdapter(ArrayList<ItemObject> item){

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
       /* Glide.with(context)
                .load(mItem.get(position).getPhoto())
                .into(holder.imageView);


        */

        ItemObject itemObject = mItem.get(position);
        String photo = itemObject.getPhoto();
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        holder.title.setGravity(Gravity.CENTER);
        holder.title.setText(itemObject.getTitle());
        holder.imageView.setImageURI(Uri.parse(photo));
//       Glide.with(this.context).load(mItem.get(position).getPhoto()).centerCrop().into(holder.imageView);



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





