package com.example.adoublei;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;

    public ViewHolder(View view) {
        super(view);
        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.imagetitle);
    }


private void setAnimation(View viewToAnimate, int position) {
    // 새로 보여지는 뷰라면 애니메이션을 해줍니다
    if (position > lastPosition) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation); lastPosition = position;
    }
}
}


