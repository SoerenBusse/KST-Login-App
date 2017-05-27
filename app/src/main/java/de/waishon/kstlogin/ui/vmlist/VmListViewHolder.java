package de.waishon.kstlogin.ui.vmlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.waishon.kstlogin.R;

/**
 * Created by Sören on 20.02.2017.
 */

public class VmListViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView status;
    private ImageView typeImage;

    public VmListViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.item_vmlist_title);
        this.status = (TextView) itemView.findViewById(R.id.item_vmlist_status);
        this.typeImage = (ImageView) itemView.findViewById(R.id.item_vmlist_type_imageview);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        itemView.setOnLongClickListener(onLongClickListener);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getStatus() {
        return status;
    }

    public ImageView getTypeImage() {
        return typeImage;
    }
}
