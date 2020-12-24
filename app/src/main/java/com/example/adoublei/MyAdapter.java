package com.example.adoublei;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolders> {

    private Context context;
    private ArrayList<ItemObject> mItem;
    private AdapterView.OnItemClickListener mListener = null;
    private Button btn_delete;


    public class RecyclerViewHolders extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {

        protected TextView title;
        protected ImageView imageView;


        public RecyclerViewHolders(@NonNull View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title_listitem);
            this.imageView = (ImageView) itemView.findViewById(R.id.photo_listitem);
//            btn_delete = itemView.findViewById(R.id.button_delete);

            itemView.setOnCreateContextMenuListener(this);
       /*     btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(getAdapterPosition());
                }
            });
        */

        }



      /* @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE,1001,1,"삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);
        }*/

    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case 1001:
                    mItem.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mItem.size());

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

        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        holder.title.setGravity(Gravity.CENTER);
        holder.title.setText(mItem.get(position).getTitle());
        holder.imageView.setImageURI(mItem.get(position).getPhoto());
//       Glide.with(this.context).load(mItem.get(position).getPhoto()).centerCrop().into(holder.imageView);



    }


    public void deleteItem(int position) {

            mItem.remove(position);

            notifyItemRemoved(position);



    }



    @Override
    public int getItemCount() {
        return (null != mItem ? mItem.size() : 0);
    }
}

   /*     private Context context;
        private ArrayList<ItemObject> itemList;

        //생성자
        public MyAdapter(ArrayList<ItemObject> itemList) {
            this.context = context;
            this.itemList = itemList;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {
            holder.title.setText(itemList.get(position).getTitle());
            holder.imageView2.setImageURI(itemList.get(position).getPhoto());
            Glide.with(context).load(itemList.get(position).getPhoto()).centerCrop().into(holder.imageView2);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        //뷰홀더 클래스 정의 - 별도 파일로 하거나 어답터 안에서 정의해 줄 수 있다.
        //여기에서 반복적으로 사용되는 각종 뷰들을 정의해 준다.
        public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView title;
            public ImageView imageView2;
            private Uri newUri = Uri.parse("http://클릭하면바뀔이미지.jpg");

            public RecyclerViewHolders(View itemView) {
                super(itemView);

                title = (TextView)itemView.findViewById(R.id.title);
                imageView2 = (ImageView)itemView.findViewById(R.id.imageView2);

                itemView.setOnClickListener(this);

            }

            //클릭 이벤트 정의
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked Card Position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
                //이미지를 교체 하는 기능
                Glide.with(context).load(newUri).centerCrop().into(imageView2);
            }
        }*/



