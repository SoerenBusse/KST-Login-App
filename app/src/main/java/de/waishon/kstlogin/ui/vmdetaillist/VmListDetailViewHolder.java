package de.waishon.kstlogin.ui.vmdetaillist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.waishon.kstlogin.R;

/**
 * Created by Sören on 10.03.2017.
 */

public class VmListDetailViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTextView;
    private TextView contentTextView;
    private View itemView;

    public VmListDetailViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        this.titleTextView = (TextView) itemView.findViewById(R.id.item_vmlist_details_title);
        this.contentTextView = (TextView) itemView.findViewById(R.id.item_vmlist_details_content);
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.itemView.setOnLongClickListener(onLongClickListener);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getContentTextView() {
        return contentTextView;
    }
}
