package msomihub.com.journalapp;

import java.io.Serializable;

public class JournalModel implements Serializable {

    private int no;
    private String dat;
    private String desc;

    public JournalModel() {
    }

    public JournalModel(int no, String dat, String desc) {
        this.no = no;
        this.dat = dat;
        this.desc = desc;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
