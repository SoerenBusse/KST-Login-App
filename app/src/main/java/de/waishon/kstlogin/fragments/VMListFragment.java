package de.waishon.kstlogin.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.waishon.kstlogin.R;
import de.waishon.kstlogin.VmDetailsActivity;
import de.waishon.kstlogin.api.ApiError;
import de.waishon.kstlogin.api.ApiMethods;
import de.waishon.kstlogin.api.ApiResponse;
import de.waishon.kstlogin.api.AuthCredentials;
import de.waishon.kstlogin.aspice.ASpiceHandler;
import de.waishon.kstlogin.listener.OnASpiceStartListener;
import de.waishon.kstlogin.listener.OnVmListClickListener;
import de.waishon.kstlogin.listener.OnVmListLongClickListener;
import de.waishon.kstlogin.ui.vmlist.VmListAdapter;
import de.waishon.kstlogin.ui.vmlist.VmListItem;
import de.waishon.kstlogin.ui.vmlist.VmListViewHolder;

/**
 * Created by Sören on 18.02.2017.
 */

public class VMListFragment extends Fragment {

    // UI
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private ProgressBar loadingSpinner;
    private MaterialDialog materialDialog;

    // Recyclerview
    private RecyclerView.LayoutManager layoutManager;

    // API
    private ApiMethods apiMethods;

    // Liste mit allen VMs
    private ArrayList<VmListItem> vmListItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_vmlist, container, false);

        // UI initialisieren
        this.coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.fragment_vmlist_coordinatorlayout);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.fragment_vmlist_recyclerview);
        this.loadingSpinner = (ProgressBar) view.findViewById(R.id.fragment_vmlist_progressbar);

        // API initialisieren
        this.apiMethods = new ApiMethods(getContext());

        // Recyclerview aufsetzen
        this.recyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(layoutManager);

        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_vmlist));
        }

        loadVmList();

        return view;
    }

    public void loadVmList() {
        // VM-Liste leeren
        this.vmListItems.clear();

        // Ladekringel zeigen
        this.loadingSpinner.setVisibility(View.VISIBLE);
        this.recyclerView.setVisibility(View.GONE);

        Log.i("KST-Login", "SIze:" + vmListItems.size());
        // VM-Liste laden
        this.apiMethods.listVms(AuthCredentials.load(getContext()), new ApiResponse() {
            @Override
            public void onError(ApiError apiError) {
                Snackbar.make(coordinatorLayout, apiError.getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFinish(JSONObject response) {
                // Liste der anzuzeigenden VMs
                VMListFragment.this.vmListItems.clear();

                Log.i("KST-Client", response.toString());

                // JSON Parsen
                try {
                    // VM Array ermitteln
                    JSONArray vms = response.getJSONArray("vms");

                    // TODO: Leeres Array -> Keine VMs
                    // Durch alle VMs iterieren
                    for (int i = 0; i < vms.length(); i++) {
                        JSONObject vmDetails = vms.getJSONObject(i);

                        // Workaround, kann kein null -> int konvertieren
                        int port = (vmDetails.getString("port").equals("null")) ? 0 : vmDetails.getInt("port");

                        vmListItems.add(new VmListItem(vmDetails.getString("name"),
                                vmDetails.getString("ip"),
                                vmDetails.getString("node"),
                                port,
                                vmDetails.getString("password"),
                                vmDetails.getString("status"),
                                vmDetails.getString("type"),
                                vmDetails.getString("pool")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Snackbar.make(coordinatorLayout, getResources().getString(R.string.error_json_parsing), Snackbar.LENGTH_LONG).show();
                }

                // Recyclerview Adapter setzen
                VMListFragment.this.recyclerView.setAdapter(new VmListAdapter(getActivity(), vmListItems, new OnVmListClickListener() {
                    @Override
                    public void onClick(VmListViewHolder viewHolder) {
                        // OnClick Methode aufrufen, die das Klicken eines Eintrags händelt
                        onVmListClick(vmListItems.get(viewHolder.getAdapterPosition()));
                    }
                }, new OnVmListLongClickListener() {
                    @Override
                    public void onLongClick(VmListViewHolder viewHolder) {
                        Intent intent = new Intent(VMListFragment.this.getActivity(), VmDetailsActivity.class);
                        intent.putExtra("vmlist", vmListItems.get(viewHolder.getAdapterPosition()));

                        startActivity(intent);
                    }
                }));

                // Loadingspinner nicht mehr anzeigen
                VMListFragment.this.loadingSpinner.setVisibility(View.GONE);
                VMListFragment.this.recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStart() {
                VMListFragment.this.loadingSpinner.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onVmListClick(VmListItem vmListItem) {
        // Ist die VM offline? -> Fehler
        if (vmListItem.getStatus().equals("offline")) {
            Snackbar.make(VMListFragment.this.coordinatorLayout, getContext().getString(R.string.fragment_vmlist_vm_not_available), Snackbar.LENGTH_LONG).show();
            return;
        }

        // Muss die VM erst zugewiesen werden
        if (vmListItem.getStatus().equals("assignable")) {
            /*if (ASpiceHandler.getSpicePackage(getContext()) == null) {
                Snackbar.make(VMListFragment.this.coordinatorLayout, getString(R.string.error_spice_not_installed), Snackbar.LENGTH_LONG).show();
                return;
            }*/

            this.apiMethods.assignVm(vmListItem.getPool(), new ApiResponse() {
                @Override
                public void onError(ApiError apiError) {
                    Snackbar.make(VMListFragment.this.coordinatorLayout, apiError.getMessage(), Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onFinish(JSONObject response) {
                    materialDialog.dismiss();
                    Log.i("KST-Client", response.toString());

                    // JSON parsen
                    try {
                        JSONObject vm = response.getJSONObject("vm");
                        VmListItem dynamicVM = new VmListItem(null, vm.getString("ip"), vm.getString("node"), vm.getInt("port"), vm.getString("password"), null, null, null);

                        startASpice(getContext(), dynamicVM);

                        // VM-Liste neuladen
                        loadVmList();
                    } catch (JSONException e) {
                        Snackbar.make(VMListFragment.this.coordinatorLayout, getContext().getString(R.string.error_json_parsing), Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onStart() {
                    materialDialog = new MaterialDialog.Builder(getActivity())
                            .content(R.string.fragment_vmlist_assigning_vm)
                            .progress(true, 0)
                            .cancelable(false)
                            .show();
                }
            });

            return;
        }

        // ASpice starten
        startASpice(getContext(), vmListItem);
    }

    public void startASpice(Context context, VmListItem vmListItem) {
        ASpiceHandler.startASpice(getContext(), vmListItem, new OnASpiceStartListener() {
            @Override
            public void onError(String message) {
                Snackbar.make(VMListFragment.this.coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess() {
                loadVmList();
            }
        });
    }
}
