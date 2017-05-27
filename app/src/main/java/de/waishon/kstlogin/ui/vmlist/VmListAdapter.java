package de.waishon.kstlogin.ui.vmlist;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.waishon.kstlogin.R;
import de.waishon.kstlogin.aspice.ASpiceHandler;
import de.waishon.kstlogin.listener.OnVmListClickListener;
import de.waishon.kstlogin.listener.OnVmListLongClickListener;

/**
 * Created by Sören on 20.02.2017.
 */

public class VmListAdapter extends RecyclerView.Adapter<VmListViewHolder> {

    // Context
    private Activity activity;

    // Enthält die Itemelemente der Liste
    private ArrayList<VmListItem> items;

    // Klick Listener, wenn auf das Item geklickt wird
    private OnVmListClickListener onVmListClickListener;
    private OnVmListLongClickListener onVmListLongClickListener;

    public VmListAdapter(Activity activity, ArrayList<VmListItem> items, OnVmListClickListener onVmListClickListener, OnVmListLongClickListener onVmListLongClickListener) {
        this.items = items;
        this.activity = activity;
        this.onVmListClickListener = onVmListClickListener;
        this.onVmListLongClickListener = onVmListLongClickListener;
    }

    @Override
    public VmListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vmlist, parent, false);

        return new VmListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VmListViewHolder holder, final int position) {
        holder.getTitle().setText(items.get(position).getName());
        new StatusDesigner(activity).design(holder.getStatus(), StatusDesigner.Status.valueOf(items.get(position).getStatus()));

        if (items.get(position).getType().equals("dynamic")) {
            holder.getTypeImage().setImageResource(R.drawable.ic_dynamic);
        } else {
            holder.getTypeImage().setImageResource(R.drawable.ic_persistent);
        }

        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVmListClickListener.onClick(holder);
            }
        });

        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onVmListLongClickListener.onLongClick(holder);

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
