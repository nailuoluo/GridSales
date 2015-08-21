package com.liuy314.griddingsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liuy314.griddingsale.R;
import com.liuy314.griddingsale.data.FenjuData;
import com.liuy314.griddingsale.ui.WangGeActivity;
import com.liuy314.griddingsale.utl.GriddingSaleApp;

import java.util.ArrayList;

/**
 * Created by liuy314 on 2015/8/11.
 */
public class FenjuAdapter extends RecyclerView.Adapter<FenjuAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<FenjuData> mFenjuDatas;

    public FenjuAdapter(Context context, ArrayList<FenjuData> datas) {
        mContext = context;
        mFenjuDatas = datas;
    }

    @Override
    public FenjuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_fenju, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ViewHolder#itemView} to reflect the item at
     * the given position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will have
     * the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        FenjuData fenjuData = mFenjuDatas.get(position);
        holder.mFenjuNameTextView.setText(fenjuData.getmFenjuName());
        holder.mFenjuPicImageView.setImageResource(fenjuData.getmFenjuPic());
        holder.mFenjuCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WangGeActivity.class);
                intent.putExtra("FenjuCode", mFenjuDatas.get(position).getmFenjuCode());
                intent.putExtra("FenjuName", mFenjuDatas.get(position).getmFenjuName());
//                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mFenjuDatas == null ? 0: mFenjuDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFenjuNameTextView;
        public ImageView mFenjuPicImageView;
        public CardView mFenjuCardView;
        public ViewHolder(View v) {
            super(v);
            mFenjuCardView = (CardView)v.findViewById(R.id.fenju_card_view);
            mFenjuNameTextView = (TextView)v.findViewById(R.id.fenju_name_textview);
            mFenjuPicImageView = (ImageView)v.findViewById(R.id.fenju_imageview);
        }
    }


}
