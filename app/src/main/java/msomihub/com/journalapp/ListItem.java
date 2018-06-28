package msomihub.com.journalapp;

import java.io.Serializable;

public class ListItem implements Serializable {

    private String head;
    private String desc;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
