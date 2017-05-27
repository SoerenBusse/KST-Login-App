package de.waishon.kstlogin.ui.vmlist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sören on 20.02.2017.
 */

public class VmListItem implements Parcelable {
    private String name;
    private String ip;
    private String node;
    private int port;
    private String password;
    private String status;
    private String type;
    private String pool;

    public VmListItem(String name, String ip, String node, int port, String password, String status, String type, String pool) {
        this.name = name;
        this.ip = ip;
        this.node = node;
        this.port = port;
        this.password = password;
        this.status = status;
        this.type = type;
        this.pool = pool;
    }

    public VmListItem(Parcel parcel) {
        String parcelData[] = new String[8];

        parcel.readStringArray(parcelData);

        this.name = parcelData[0];
        this.ip = parcelData[1];
        this.node = parcelData[2];
        this.port = Integer.parseInt(parcelData[3]);
        this.password = parcelData[4];
        this.status = parcelData[5];
        this.type = parcelData[6];
        this.pool = parcelData[7];
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getNode() {
        return node;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getPool() {
        return pool;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                this.getName(),
                this.getIp(),
                String.valueOf(this.getNode()),
                String.valueOf(this.getPort()),
                this.getPassword(),
                this.getStatus(),
                this.getType(),
                this.getPool()
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public VmListItem createFromParcel(Parcel in) {
            return new VmListItem(in);
        }

        public VmListItem[] newArray(int size) {
            return new VmListItem[size];
        }
    };
}

