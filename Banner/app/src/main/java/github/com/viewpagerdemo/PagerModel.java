package github.com.viewpagerdemo;

/**
 * by y on 2016/9/28
 */

public class PagerModel {
    public PagerModel(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    int imageId;
}
