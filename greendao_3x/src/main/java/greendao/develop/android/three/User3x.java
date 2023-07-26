package greendao.develop.android.three;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "UserBean3x")
public class User3x {

    @Id
    private Long id;
    private String name3x;
    private String age3x;

    @Generated(hash = 1701431652)
    public User3x(Long id, String name3x, String age3x) {
        this.id = id;
        this.name3x = name3x;
        this.age3x = age3x;
    }

    @Generated(hash = 2094007524)
    public User3x() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName3x() {
        return this.name3x;
    }

    public void setName3x(String name3x) {
        this.name3x = name3x;
    }

    public String getAge3x() {
        return this.age3x;
    }

    public void setAge3x(String age3x) {
        this.age3x = age3x;
    }

}
