package de.waishon.kstlogin.ui.navigationdrawer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.waishon.kstlogin.R;

/**
 * Created by Sören on 10.04.2016.
 */
public class NavigationDrawerViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView entryName;
    private View itemView;

    public NavigationDrawerViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.item_navigationdrawer_image_view);
        this.entryName = (TextView) itemView.findViewById(R.id.item_navigationdrawer_entry_name);
        this.itemView = itemView;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }

    public TextView getEntryNameTextView() {
        return entryName;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
