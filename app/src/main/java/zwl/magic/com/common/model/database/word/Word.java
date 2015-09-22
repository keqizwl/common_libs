package zwl.magic.com.common.model.database.word;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;
import com.lidroid.xutils.db.annotation.Unique;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Table(name = "word_book")
public class Word implements Serializable {

    /***/
    @Transient
    private static final long serialVersionUID = 1L;

    @Unique
    @Id(column = "id")
    public int _id;

    @Column(column = "uid")
    public int uid;

    @Unique
    @Column(column = "word")
    public String word;

    @Column(column = "meaning")
    public String meaning;

    @Column(column = "us_phonetic")
    public String usphonetic;

    @Column(column = "uploaded")
    public int uploaded = 0;//0未上传，1已上传

    /**
     * 非数据库字段
     */
    @Transient
    public boolean isSelected;

    public String getWordJson() throws UnsupportedEncodingException {
        return "{\"word\":\"" + getWord() + "\",\"meaning\":\"" + URLEncoder.encode(getMeaning(), "UTF-8") + "\",\"usphonetic\":\"" + getUsphonetic() + "\"}";
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getUsphonetic() {
        return usphonetic;
    }

    public void setUsphonetic(String usPhonetic) {
        this.usphonetic = usPhonetic;
    }
}
