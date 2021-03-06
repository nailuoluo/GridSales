package com.liuy314.griddingsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.liuy314.griddingsale.R;
import com.liuy314.griddingsale.data.ShopData;
import com.liuy314.griddingsale.ui.ShopMapActivity;

import java.util.ArrayList;

/**
 * Created by liuy314 on 2015/8/13.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ShopData> mShopDatas;

    public ShopAdapter(Context context, ArrayList<ShopData> datas) {
        mContext = context;
        mShopDatas = datas;
    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display different
     * items in the data set, it is a good idea to cache references to sub views of the View to
     * avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_shop, parent, false);
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mShopNameTextView.setText(mShopDatas.get(position).getmShopName());
        holder.mShopCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShopMapActivity.class);
                intent.putExtra("shopName", mShopDatas.get(position).getmShopName());
                intent.putExtra("shopCode", mShopDatas.get(position).getmShopCode());
                intent.putExtra("shopLatitude", mShopDatas.get(position).getmShopLatitude());
                intent.putExtra("shopLongitude", mShopDatas.get(position).getmShopLongitude());
                mContext.startActivity(intent);
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
        return mShopDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mShopNameTextView;
        public CardView mShopCardView;

        public ViewHolder(View v) {
            super(v);
            mShopNameTextView = (TextView)v.findViewById(R.id.shop_name_textview);
            mShopCardView = (CardView)v.findViewById(R.id.shop_card_view);
        }
    }
}
