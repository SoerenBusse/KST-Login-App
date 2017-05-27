package de.waishon.kstlogin.ui.vmdetaillist;

/**
 * Created by Sören on 10.03.2017.
 */

public class VmListDetailItem {
    private String title;
    private String content;

    public VmListDetailItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
