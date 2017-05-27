package de.waishon.kstlogin.ui.vmdetaillist;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.waishon.kstlogin.R;
import de.waishon.kstlogin.listener.OnVmDetailsLongClickListener;
import de.waishon.kstlogin.ui.vmlist.VmListViewHolder;

/**
 * Created by Sören on 10.03.2017.
 */

public class VmListDetailAdapter extends RecyclerView.Adapter<VmListDetailViewHolder> {

    private Context context;
    private ArrayList<VmListDetailItem> items;
    private OnVmDetailsLongClickListener onVmDetailsLongClickListener;

    public VmListDetailAdapter(Context context, ArrayList<VmListDetailItem> items, OnVmDetailsLongClickListener onVmDetailsLongClickListener) {
        this.items = items;
        this.context = context;
        this.onVmDetailsLongClickListener = onVmDetailsLongClickListener;
    }

    @Override
    public VmListDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vmlist_details, parent, false);
        return new VmListDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VmListDetailViewHolder holder, int position) {
        holder.getTitleTextView().setText(items.get(position).getTitle());
        holder.getContentTextView().setText(items.get(position).getContent());

        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                VmListDetailAdapter.this.onVmDetailsLongClickListener.onVmDetailsLongClickListener(holder);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
