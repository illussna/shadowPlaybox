package shadow.play.box;

import java.io.Serializable;

/**
 * Created by parkkh on 2016-06-25.
 */
public class Content implements Serializable{
    private String name;
    private String fileName;
    private String bgName;
    private String imageurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBgName() {
        return bgName;
    }
    public void setBgName(String bgName) {
        this.bgName = bgName;
    }

    public String getImageurl(){ return imageurl; }
    public void setImageurl(String imageurl){this.imageurl = imageurl;}
}
