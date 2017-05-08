package framework.network;

import com.fiction.y.contents.m.ContentsModel;
import com.fiction.y.detail.m.DetailModel;
import com.fiction.y.search.m.SearchModel;
import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2016/7/27.
 */
public class JsoupTool {

    private static final String JSOUP_HEADER = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
    private static final String TAG = "jsoup";


    private static class JsoupToolHolder {
        private static final JsoupTool JSOUP_TOOL = new JsoupTool();
    }

    private JsoupTool() {
    }

    public static JsoupTool getInstance() {
        return JsoupToolHolder.JSOUP_TOOL;
    }

    private Document getDocument(String url) {
        KLog.i(TAG, url);
        try {
            return Jsoup.connect(url.trim()).header("User-Agent", JSOUP_HEADER).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
            KLog.i(e.toString());
        }
        return null;
    }

    public List<SearchModel> getList(String url) {
        Document document = getDocument(url);
        List<SearchModel> list = new ArrayList<>();
        SearchModel fictionNameModel;
        if (document == null) {
            throw new NullPointerException("the document is null");
        }
        for (Element element : document.select("div.result-game-item")) {
            fictionNameModel = new SearchModel();
            fictionNameModel.setImage(element.select("img.result-game-item-pic-link-img").attr("src"));
            fictionNameModel.setTitle(element.select("a.result-game-item-title-link").attr("title"));
            fictionNameModel.setDetailUrl(element.select("a.result-game-item-title-link").attr("href"));
            fictionNameModel.setContent(element.select("p.result-game-item-desc").text());
            list.add(fictionNameModel);
        }
        return list;
    }

    public List<ContentsModel> getContents(String url) {
        Document document = getDocument(url);
        List<ContentsModel> list = new ArrayList<>();
        ContentsModel contentsModel;
        if (document == null) {
            throw new NullPointerException("the document is null");
        }
        for (Element element : document.select("#list").select("a")) {
            contentsModel = new ContentsModel();
            contentsModel.setTitle(element.text());
            contentsModel.setDetailUrl(element.attr("abs:href"));
            list.add(contentsModel);
        }
        return list;
    }

    public DetailModel getDetail(String url) {
        Document document = getDocument(url);
        DetailModel detailModel = new DetailModel();
        if (document == null) {
            throw new NullPointerException("the document is null");
        }
        Elements select = document.select("div.bottem2").select("a[href$=.html]");
        for (int i = 0; i < select.size(); i++) {
            switch (i) {
                case 0:
                    detailModel.setOnPage(select.get(i).attr("abs:href"));
                    break;
                case 1:
                    detailModel.setNextPage(select.get(i).attr("abs:href"));
                    break;
            }
        }
        detailModel.setTitle(document.select("div.bookname").select("h1").text());
        detailModel.setContent(document.select("#content").html());
        KLog.i(detailModel.getOnPage() + "    " + detailModel.getNextPage());
        return detailModel;
    }

}
