package de.waishon.kstlogin;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

import de.waishon.kstlogin.listener.OnVmDetailsLongClickListener;
import de.waishon.kstlogin.ui.vmdetaillist.VmListDetailAdapter;
import de.waishon.kstlogin.ui.vmdetaillist.VmListDetailItem;
import de.waishon.kstlogin.ui.vmdetaillist.VmListDetailViewHolder;
import de.waishon.kstlogin.ui.vmlist.VmListAdapter;
import de.waishon.kstlogin.ui.vmlist.VmListItem;

public class VmDetailsActivity extends AppCompatActivity {

    // System
    ClipboardManager clipboardManager;

    // UI
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;

    // RecyclerView
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm_details);

        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_vm_details_coordinatorlayout);
        this.recyclerView = (RecyclerView) findViewById(R.id.activity_vm_details_recyclerview);

        VmListItem vmListItem = getIntent().getParcelableExtra("vmlist");

        // System Services

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        // UI initialisieren
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Toolbar setzen
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(vmListItem.getName());

        final ArrayList<VmListDetailItem> vmListDetailItems = new ArrayList<>();
        vmListDetailItems.add(new VmListDetailItem("Name", vmListItem.getName()));
        vmListDetailItems.add(new VmListDetailItem("IP", vmListItem.getIp()));
        vmListDetailItems.add(new VmListDetailItem("Node", String.valueOf(vmListItem.getNode())));
        vmListDetailItems.add(new VmListDetailItem("Port", String.valueOf(vmListItem.getPort())));
        vmListDetailItems.add(new VmListDetailItem("Password", String.valueOf(vmListItem.getPassword())));
        vmListDetailItems.add(new VmListDetailItem("Status", String.valueOf(vmListItem.getStatus())));
        vmListDetailItems.add(new VmListDetailItem("Type", String.valueOf(vmListItem.getType())));
        vmListDetailItems.add(new VmListDetailItem("Pool", String.valueOf(vmListItem.getPool())));

        //Recyclerview aufsetzen
        this.recyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        this.recyclerView.setAdapter(new VmListDetailAdapter(getApplicationContext(), vmListDetailItems, new OnVmDetailsLongClickListener() {
            @Override
            public void onVmDetailsLongClickListener(VmListDetailViewHolder viewHolder) {
                ClipData clipData = ClipData.newPlainText("spice", vmListDetailItems.get(viewHolder.getAdapterPosition()).getContent());
                clipboardManager.setPrimaryClip(clipData);

                Snackbar.make(VmDetailsActivity.this.coordinatorLayout, vmListDetailItems.get(viewHolder.getAdapterPosition()).getTitle() + " " + getString(R.string.activity_vmlist_copy_info), Snackbar.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
