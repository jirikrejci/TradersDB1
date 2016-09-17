package com.JKSoft.TdbClient.TradesRecyclerView.adapter;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.AlphabeticIndex;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.JKSoft.DataStructures.TradeRecord;
import com.example.jirka.TdbClient.R;

import java.util.List;

/**
 * Created by Jirka on 15.9.2016.
 */
public class TradesListAdapter extends RecyclerView.Adapter<TradesListAdapter.TradeHolder> {

    private List<TradeRecord> listData;
    private LayoutInflater inflater;


    // doplněný constructor
    public TradesListAdapter(List<TradeRecord> listData, Context c) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(c);  // layout inflater v kontexctu volající třídy


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
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public TradeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // vytvorení view jedné položky z xml
        View view = inflater.inflate(R.layout.trades_overview_listitem, parent, false);
        //DerpHolder obsahuje ukazatele na views v rámci tednoho ItemView
        return new TradeHolder(view);

    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(TradeHolder holder, int position) {
        TradeRecord tradeRecord = listData.get(position);
        holder.tvSymbol.setText(tradeRecord.getSymbol());
        holder.tvLevelPrice.setText(tradeRecord.getLevelPrice().toString()); //TODO předělat na String.format
        holder.tvDirection.setText(tradeRecord.getDirection());
        if (tradeRecord.getDirection().equals("buy")) {
            holder.tvDirection.setTextColor(Color.BLUE);     //TODO vyzkouset ColorStateList  http://stackoverflow.com/questions/6678694/how-to-set-textcolor-using-settextcolorcolorsstatelist-colors
        } else {
            holder.tvDirection.setTextColor(Color.RED);
        }



        holder.tvOrderStatus.setText(tradeRecord.getOrderStatus());    //TODO doimplementovat OrderStatus
        holder.tvEstimatedTradeStatus.setText(tradeRecord.getEstimatedTradeStatus()); //TODO doimplementovat EstimatedTradeStatus



    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return listData.size();
    }

    class TradeHolder extends RecyclerView.ViewHolder {
        private TextView tvSymbol;
        private TextView tvLevelPrice;
        private TextView tvDirection;
        private TextView tvOrderStatus;
        private TextView tvEstimatedTradeStatus;
        private View viewItemContainer;

        public TradeHolder(View itemView) {
            super(itemView);

            tvSymbol = (TextView) itemView.findViewById(R.id.tvSymbol);
            tvLevelPrice = (TextView) itemView.findViewById(R.id.tvLevelPrice);
            tvDirection = (TextView) itemView.findViewById(R.id.tvDirection);
            tvOrderStatus = (TextView) itemView.findViewById(R.id.tvOrderStatus);
            tvEstimatedTradeStatus = (TextView) itemView.findViewById(R.id.tvEstimatedTradeStatus);

            viewItemContainer = itemView.findViewById(R.id.viewItemRoot);

        }
    }


}
