package framework.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import framework.base.BaseModel;
import framework.data.Constant;

/**
 * by y on 2016/7/27.
 */
public class JsoupTool {

    private static final String JSOUP_HEADER = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

    private static class JsoupToolHolder {
        private static final JsoupTool JSOUP_TOOL = new JsoupTool();
    }

    private JsoupTool() {
    }

    public static JsoupTool getInstance() {
        return JsoupToolHolder.JSOUP_TOOL;
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url.trim()).header("User-Agent", JSOUP_HEADER).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BaseModel getVideo(String url) {
        Document document = getDocument(url);
        BaseModel videoModel = new BaseModel();
        if (document == null) {
            throw new NullPointerException("the document is null");
        }
        Elements script = document.select("script");
        int j = 0;
        for (Element element : script) {
            if (j == 3) {
                String videoUrl = element.toString().substring(element.toString().indexOf("\"http://gslb") + 1, element.toString().indexOf(".mp4"));
                videoModel.setUrl(videoUrl + ".mp4");
            }
            j++;
        }
        videoModel.setDetailUrl(script.html());
        return videoModel;
    }

    public List<BaseModel> getImageList(String url, String type) {
        Document document = getDocument(url);
        List<BaseModel> list = new LinkedList<>();
        BaseModel imageListModel;
        if (document == null) {
            throw new NullPointerException("the document is null");
        }
        switch (type) {
            case Constant.DOU_BAN_MEI_ZI:
                for (Element element : document.select("div.img_single").select("a")) {
                    imageListModel = new BaseModel();
                    imageListModel.setUrl(element.select("img[class]").attr("src"));
                    imageListModel.setDetailUrl(element.select("a[class]").attr("href"));
                    list.add(imageListModel);
                }
                break;
            case Constant.M_ZI_TU:
                for (Element element : document.select("#pins").select("a:has(img)")) {
                    imageListModel = new BaseModel();
                    imageListModel.setUrl(element.select("img").attr("data-original"));
                    imageListModel.setDetailUrl(element.select("a").attr("href"));
                    list.add(imageListModel);
                }
                break;
        }
        return list;
    }

    public List<BaseModel> getImageDetail(String url, String type) {
        Document document = getDocument(url);
        List<BaseModel> list = new LinkedList<>();
        BaseModel imageDetailModel;
        if (document == null) {
            throw new NullPointerException("the document is null");
        }
        switch (type) {
            case Constant.DOU_BAN_MEI_ZI_DETAIL:
                for (Element element : document.select("div.panel-body").select("img")) {
                    imageDetailModel = new BaseModel();
                    imageDetailModel.setUrl(element.select("img[src]").attr("src"));
                    list.add(imageDetailModel);
                }
                break;
            case Constant.M_ZI_TU_DETAIL:
                for (Element element : document.select("div.main-image").select("img")) {
                    imageDetailModel = new BaseModel();
                    imageDetailModel.setUrl(element.attr("src"));
                    list.add(imageDetailModel);
                }
                break;
        }
        return list;
    }
}
