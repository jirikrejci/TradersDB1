package com.JKSoft.TdbClient.TradesRecyclerView.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.JKSoft.DataStructures.TradeRecord;
import com.example.jirka.TdbClient.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jirka on 15.9.2016.
 */
public class TradesListAdapter extends RecyclerView.Adapter<TradesListAdapter.ItemHolder> {

    //TODO zkusit použít animátor   http://stackoverflow.com/questions/32463136/recyclerview-adapter-notifyitemchanged-never-passes-payload-to-onbindviewholde
    //TODO zkusit poižít decorator nebo decorer https://www.bignerdranch.com/blog/a-view-divided-adding-dividers-to-your-recyclerview-with-itemdecoration/

    private List<TradeRecord> listData;
    private LayoutInflater inflater;
    private Context context;
    private ItemClickCallback itemClickCallback;

    private int selectedPosition = -1;   // pro zvýraznění vybrané položky


    public interface ItemClickCallback {
        void onItemClick(int p);  //vystřelí (will fire) vždy když uživatel klikne kamkoliv
        //void onSecondaryIconCllick (int p);
    }

    // setter pro ItemClickCallback
    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    /**
     * Konsturktor Adaptéru
     * ukládá si odkaz na list data (zdroj dat) a kontext nadřazeného view, z něhož si vytahuje i inflater
      * @param listData - odkaz na zdrojová data
     * @param c context view, které bude Recycler view obsahovat, používá se na
     *          1) získání odkazu na LayoutInflater
     *          2)
     *
     */
    public TradesListAdapter(List<TradeRecord> listData, Context c) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(c);  // layout inflater v kontexctu volající třídy
        this.context = c;

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
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // vytvorení view jedné položky z xml
        View view = inflater.inflate(R.layout.lay_trades_overview_listitem, parent, false);
        //ItemHolder obsahuje ukazatele na views v rámci tednoho ItemView
        return new ItemHolder(view);

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
    public void onBindViewHolder(ItemHolder holder, int position) {
        TradeRecord tradeRecord = listData.get(position);
        holder.tvSymbol.setText(tradeRecord.getSymbol());
        holder.tvLevelPrice.setText(tradeRecord.getLevelPrice().toString()); //TODO předělat na String.format

        // zvýraznění vybrané položky

        if (selectedPosition == position) {
            holder.viewItemContainer.setBackgroundColor(Color.rgb(200,200,210));  // TODO dostat sem bartvu z resources
        }   else {
            holder.viewItemContainer.setBackgroundColor(Color.argb(0,0,0,0));  // TODO dostat sem bartvu z resources
        }

        //Selected(selectedPosition == position);
        holder.viewItemContainer.setSelected(true);

        // Trade Direction
        holder.tvDirection.setText(tradeRecord.getDirection());
        if (tradeRecord.getDirection().equals("buy")) {
            holder.tvDirection.setTextColor(Color.BLUE);     //TODO vyzkouset ColorStateList  http://stackoverflow.com/questions/6678694/how-to-set-textcolor-using-settextcolorcolorsstatelist-colors
            holder.imDirectionIcon.setImageResource(R.drawable.ic_action_arrow_top);
            holder.imDirectionIcon.setColorFilter(Color.BLUE);
        } else {
            holder.tvDirection.setTextColor(Color.RED);
            holder.imDirectionIcon.setImageResource(R.drawable.ic_action_arrow_bottom);
            holder.imDirectionIcon.setColorFilter(Color.RED);
        }

        // Order status
        holder.tvOrderStatus.setText(tradeRecord.getOrderStatus());

        if (tradeRecord.getOrderStatus() != null) {
            int color = Color.rgb(0, 0, 0);
            if (tradeRecord.getOrderStatus().equals("pending"))
                color = ContextCompat.getColor(context, R.color.colorTradePending);
            else if (tradeRecord.getOrderStatus().equals("in"))
                color = ContextCompat.getColor(context, R.color.colorTradeIn);
            else if (tradeRecord.getOrderStatus().equals("suspended"))
                color = ContextCompat.getColor(context, R.color.colorTradeSuspended);
            holder.tvOrderStatus.setTextColor(color);
        }

        // Trade Status
        holder.tvEstimatedTradeStatus.setText(tradeStatus2Text(tradeRecord.getEstimatedTradeStatus())); //TODO doimplementovat EstimatedTradeStatus

        if (tradeRecord.getEstimatedTradeStatus() != null) {
            int color = Color.rgb(0, 0, 0);
            switch (tradeRecord.getEstimatedTradeStatus()) {
                case "TS_PENDING":
                    color = ContextCompat.getColor(context, R.color.colorTradePending);
                    break;
                case "TS_EARLY_TURN":
                    color = ContextCompat.getColor(context, R.color.colorTradeEarlyTurn);
                    break;
                case "TS_IN":
                    color = ContextCompat.getColor(context, R.color.colorTradeIn);
                    break;
                case "TS_WAITING_FOR_SCRATCH":
                    color = ContextCompat.getColor(context, R.color.colorTradeWaitingForScratch);
                    break;
                case "TS_TP_REACHED":
                    color = ContextCompat.getColor(context, R.color.colorTradeTpReached);
                    break;
                case "TS_STOP_LOSS_REACHED":
                    color = ContextCompat.getColor(context, R.color.colorTradeSlReached);
                    break;
            }

            holder.tvEstimatedTradeStatus.setTextColor(color);

        }
        // Level Distance
        if (tradeRecord.getLevelDistance() != null) {
            holder.tvLevelDistance.setText(String.format("%.0f", tradeRecord.getLevelDistance()));
            if (Math.abs(tradeRecord.getLevelDistance())<= 10) holder.tvLevelDistance.setTextColor(Color.RED);
            else if (Math.abs(tradeRecord.getLevelDistance())<= 140) holder.tvLevelDistance.setTextColor(ContextCompat.getColor(context, R.color.colorTradePending));
            else holder.tvLevelDistance.setTextColor(Color.rgb(120,120,120));  // TODO ustandrardizovat styly

        }


    }

    private String tradeStatus2Text(String estimatedTradeStatus) {
        switch (estimatedTradeStatus) {
            case "TS_PENDING": return "PENDING";
            case "TS_EARLY_TURN": return "EARLY TURN";
            case "TS_IN": return "IN";
            case "TS_WAITING_FOR_SCRATCH": return "SCRATCH MODE";
            case "TS_IN_TEST": return "IN TEST";
            case "TS_TP_REACHED": return ("TARGET");
            case "TS_STOP_LOSS_REACHED": return ("STOP LOSS");
            default: return estimatedTradeStatus;
        }
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

    /**
     * Vlastní Item holder - objekt, který drží odkazy na jednotlivá subview v rámci jedné položky
     * a definuje onClickListener na úrovni Item view. OnClick listener dostává jako parametr konkrétní
     * subview, na které bylo kliknuto
     *
     */

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {  //TODO zkusit předělat na ButterKnife
        @BindView(R.id.tvSymbol) TextView tvSymbol;
        @BindView(R.id.tvLevelPrice) TextView tvLevelPrice;
        @BindView(R.id.imTradeDirectionIcon) ImageView imDirectionIcon;
        @BindView(R.id.tvDirection) TextView tvDirection;
        private TextView tvOrderStatus;
        private TextView tvEstimatedTradeStatus;
        private TextView tvLevelDistance;
        private View viewItemContainer;

        /**
         * constructor - naplňuje handlery odkazy a nastavuje listener
         * @param itemView item group view, které pod sebou drží jednotlivá zobrazovaná view v rámci řádku
         */
        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // následující položky převedeny pod Butterknife

 //           tvLevelPrice = (TextView) itemView.findViewById(R.id.tvLevelPrice);
//            imDirectionIcon = (ImageView) itemView.findViewById(R.id.imTradeDirectionIcon);
//            tvDirection = (TextView) itemView.findViewById(R.id.tvDirection);

            // násldující zůstává
            tvOrderStatus = (TextView) itemView.findViewById(R.id.tvOrderStatus);
            tvEstimatedTradeStatus = (TextView) itemView.findViewById(R.id.tvEstimatedTradeStatus);
            tvLevelDistance = (TextView) itemView.findViewById(R.id.tvLevelDistance);


            // uložení odkazu na item group view pro nastavení listeneru
            viewItemContainer = itemView.findViewById(R.id.viewItemRoot);
            // nastavení listeneru na click - odkazuje na unstanci Item Holderu => každý holder má svůj listener, a listener je definován v rámci holderu
            viewItemContainer.setOnClickListener(this);
        }



        /**
         * Vlastní onCllick listener
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            int oldSelectedPosition = selectedPosition;

            //TODO - kvuli notify musel byt nastaven RecyclerView.setItemAnimator(null), jinak to padalo. Spíš by se měl použít drawer. Ale animator by se měl nastavit taky
            //TODO správně by asi měl být piužit dekorátor, ale i tak by bylo dobré anučit se používat animátor
            // A taky by asi bylo dobré kolem tradu jen udělat obdélník - dají se nějak nastavit obrysy, nebo shape, nebo zviditelnit shape? (pak by to asi muselo být RelativeLayout


            selectedPosition = adapterPosition;
            notifyItemChanged(oldSelectedPosition);
            notifyItemChanged(selectedPosition);


            //notifyItemChanged(adapterPosition);
//            notifyItemChanged(selectedPosition);

            //v.setSelected(true);
            //notifyItemChanged(getAdapterPosition());
            itemClickCallback.onItemClick(adapterPosition);




        }
    }


}
