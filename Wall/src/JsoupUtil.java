import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class JsoupUtil {

    private JsoupUtil() {
    }

    /**
     * 获取jsoup对象
     *
     * @param url url
     * @return @{@link Jsoup}
     */
    private static Connection connect(String url) {
        return Jsoup.connect(url.trim()).header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
    }

    /**
     * @param url url
     * @return {@link Document}
     * @throws IOException jsoup error
     */
    public static Document getDocument(String url) throws IOException {
        System.out.print("url:" + url + "\n");
        return connect(url).get();
    }

    /**
     * 获取wall总页数
     *
     * @param document {@link Document}
     * @return wall page
     */
    public static int getWallPage(Document document) {
        return Util.getInt(document.select("div.visible-xs").select("a[title]").attr("title"));
    }


    /**
     * 获取wallBetter总页数
     *
     * @param document {@link Document}
     * @return wall better page
     */
    public static int getWallBetterPage(Document document) {
        Elements select = document.select("a[class=page_btn]");
        Integer[] args = new Integer[select.size()];
        for (int i = 0; i < select.size(); i++) {
            int temp = 0;
            try {
                temp = Integer.valueOf(select.get(i).text());
            } catch (Exception ignored) {
            }
            args[i] = temp;
        }
        return Collections.max(Arrays.asList(args));
    }

    /**
     * 获取wallBetter所有图片下载地址
     *
     * @param document {@link Document}
     * @return list
     */
    public static List<String> getDownloadWallBetterUrl(Document document) {
        List<String> url = new ArrayList<>();
        Elements select = document.select("link[itemprop=contentUrl]");
        for (Element element : select) {
            url.add(element.attr("href"));
        }
        return url;
    }

    /**
     * 获取wall网页下的所有图片下载地址
     *
     * @param document {@link Document}
     * @return list
     */
    public static List<String> getDownloadWallUrl(Document document) {
        List<String> url = new ArrayList<>();
        Elements select = document.select("div.overlay").select("span[data-href]");
        for (Element element : select) {
            String download_url = element.attr("data-href");
            url.add(download_url);
        }
        return url;
    }

    /**
     * 获取Hdq网页下总页数
     *
     * @param document {@link Document}
     * @return hdq walls page
     */
    public static int getHdqWallsPage(Document document) {
        Elements select = document.select("ul.pagination").select("a[class]");
        Element element = select.get(select.size() - 1);
        return Integer.valueOf(element.text());
    }

    /**
     * 获取DBQ所有图片下载地址
     *
     * @param document {@link Document}
     * @return LIST
     */
    public static List<String> getDownloadHdqWallsUrl(Document document) throws InterruptedException, IOException {
        List<String> listUrl = new ArrayList<>();
        List<String> url = new ArrayList<>();
        Elements listSelect = document.select("a.caption");
        for (Element element : listSelect) {
            listUrl.add(element.attr("abs:href"));
        }
        for (String s : listUrl) {
            Thread.sleep(2000);
            Elements select = JsoupUtil.getDocument(s).select("a[rel=nofollow]");
            String attr = select.get(select.size() - 1).attr("abs:href");
            url.add(attr);
        }
        return url;
    }
}
