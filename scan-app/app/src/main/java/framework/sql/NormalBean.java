package framework.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * by y on 2017/2/15
 */
@Entity(nameInDb = "NormalDb")
public class NormalBean {

    @Id
    private Long id;
    @NotNull
    private String pkgName;
    @NotNull
    private String appLabel;
    @Generated(hash = 1897703403)
    public NormalBean(Long id, @NotNull String pkgName, @NotNull String appLabel) {
        this.id = id;
        this.pkgName = pkgName;
        this.appLabel = appLabel;
    }
    @Generated(hash = 885171193)
    public NormalBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPkgName() {
        return this.pkgName;
    }
    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
    public String getAppLabel() {
        return this.appLabel;
    }
    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }
}
