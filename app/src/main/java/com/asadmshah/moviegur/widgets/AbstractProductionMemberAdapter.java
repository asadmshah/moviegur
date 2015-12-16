package com.asadmshah.moviegur.widgets;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.models.ProfilePath;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

abstract class AbstractProductionMemberAdapter extends RecyclerView.Adapter<AbstractProductionMemberAdapter.ItemViewHolder> {

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(inflater.inflate(R.layout.viewholder_production_member, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.viewTopText.setText(getTopText(position));
        holder.viewBottomText.setText(getBottomText(position));

        Glide.with(holder.itemView.getContext())
                .load(getProfilePath(position))
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.ic_person_black_24px)
                .into(new BitmapImageViewTarget(holder.viewProfile) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(holder.itemView.getResources(), resource);
                        drawable.setCircular(true);
                        getView().setImageDrawable(drawable);
                    }
                });
    }

    abstract String getTopText(int position);
    abstract ProfilePath getProfilePath(int position);
    abstract String getBottomText(int position);

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        final TextView viewTopText;
        final ImageView viewProfile;
        final TextView viewBottomText;

        public ItemViewHolder(View itemView) {
            super(itemView);

            viewTopText = (TextView) itemView.findViewById(R.id.top_text);
            viewProfile = (ImageView) itemView.findViewById(R.id.profile);
            viewBottomText = (TextView) itemView.findViewById(R.id.bottom_text);
        }
    }

}