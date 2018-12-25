import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static ArrayList<String> url = new ArrayList<>();
    /**
     * wall
     **/
    private static final String WALL_BASE_URL = "https://wall.alphacoders.com/";
    private static final String RESOLUTION_URL = "by_resolution.php?w=%s&h=%s&lang=Chinese&page=%s";
    private static final String SEARCH_TAG_URL = "search.php?search=%s&lang=Chinese&page=%s";
    private static final String MAC_WALL_WIDTH = "5120";
    private static final String MAC_WALL_HEIGHT = "2880";

    /**
     * douban
     */
    private static final String DOUBAN_BASE_URL = "http://www.dbmeinv.com/dbgroup/show.htm?cid=";
    private static final String DOUBAN_DOWNLOAD_URL = "%s&pager_offset=%s";

    /**
     * wall better
     */
    private static final String WALL_BETTER_BASE_URL = "http://www.wallpaperbetter.com/";
    private static final String WALL_BETTER_DOWNLOAD_URL = "%sk-wallpaper/%s";

    /**
     * hdq walls
     */
    private static final String HDQ_WALLS_BASE_URL = "https://hdqwalls.com/";
    private static final String HDQ_WALLS_DOWNLOAD_URL = "%sk-wallpapers/page/%s";


    public static void main(String[] args) throws IOException, InterruptedException {
//        downloadHdqWallsImg("10");
//        downloadHdqWallsImg("8");
        downloadHdqWallsImg("5");
//        downloadWallBetterImg("5");
//        downloadWallBetterImg("4");
//        downloadWallBetterImg("2");
//        downloadDouBanImg("2");
//        downloadDouBanImg("3");
//        downloadDouBanImg("4");
//        downloadDouBanImg("5");
//        downloadDouBanImg("6");
//        downloadDouBanImg("7");
//        downloadWallSearchImg("大熊猫");
//        downloadWallResolutionImg(MAC_WALL_WIDTH, MAC_WALL_HEIGHT, "wall");
    }


    private static void downloadHdqWallsImg(String resolution) throws IOException, InterruptedException {
        int page = JsoupUtil.getHdqWallsPage(JsoupUtil.getDocument(HDQ_WALLS_BASE_URL + String.format(HDQ_WALLS_DOWNLOAD_URL, resolution, "1")));
        System.out.print(page + "\n");
        for (int i = 25; i <= page; i++) {
            Thread.sleep(2000);
            Document document = JsoupUtil.getDocument(HDQ_WALLS_BASE_URL + String.format(HDQ_WALLS_DOWNLOAD_URL, resolution, i));
            url.addAll(JsoupUtil.getDownloadHdqWallsUrl(document));
            download(String.format("hdq-wall-%sk", resolution));
            url.clear();
        }
    }


    /**
     * 下载wallBetter图片
     */
    private static void downloadWallBetterImg(String resolution) throws IOException, InterruptedException {
        int page = JsoupUtil.getWallBetterPage(JsoupUtil.getDocument(WALL_BETTER_BASE_URL + String.format(WALL_BETTER_DOWNLOAD_URL, resolution, "1")));
        System.out.print(page + "\n");
        for (int i = 1; i <= page; i++) {
            Thread.sleep(2000);
            Document document = JsoupUtil.getDocument(WALL_BETTER_BASE_URL + String.format(WALL_BETTER_DOWNLOAD_URL, resolution, i));
            url.addAll(JsoupUtil.getDownloadWallBetterUrl(document));
        }
        download(String.format("wall-better-%sk", resolution));
    }


    /**
     * 下载豆瓣图片,默认下载50页
     */
    private static void downloadDouBanImg(String cid) throws IOException, InterruptedException {
        int page = 50;
        for (int i = 0; i < page; i++) {
            Document document = JsoupUtil.getDocument(DOUBAN_BASE_URL + String.format(DOUBAN_DOWNLOAD_URL, cid, i));
            Elements a = document.select("div.img_single").select("a");
            for (Element element : a) {
                String href = element.select("a[class]").attr("href");
                if (!Util.hasDownloadUrl(href, url)) {
                    Elements img = JsoupUtil.getDocument(href).select("div.panel-body").select("img");
                    Thread.sleep(1500);
                    for (Element detailElement : img) {
                        String src = detailElement.select("img[src]").attr("src");
                        Util.downloadNet(src, "douban-" + cid);
                    }
                    url.add(href);
                }
            }
        }
    }


    /**
     * 下载wall该分辨率的图片
     */
    private static void downloadWallResolutionImg(String width, String height, String fileName) throws IOException, InterruptedException {
        int page = JsoupUtil.getWallPage(JsoupUtil.getDocument(WALL_BASE_URL + String.format(RESOLUTION_URL, width, height, "1")));
        for (int i = 1; i <= page; i++) {
            Thread.sleep(2000);
            Document document = JsoupUtil.getDocument(WALL_BASE_URL + String.format(RESOLUTION_URL, width, height, i));
            url.addAll(JsoupUtil.getDownloadWallUrl(document));
        }
        download(fileName);
    }

    /**
     * 下载wall搜索到的图片
     */
    private static void downloadWallSearchImg(String search) throws IOException, InterruptedException {
        int page = JsoupUtil.getWallPage(JsoupUtil.getDocument(WALL_BASE_URL + String.format(SEARCH_TAG_URL, search, "1")));
        System.out.print(page + "\n");
        for (int i = 1; i <= page; i++) {
            Thread.sleep(2000);
            Document document = JsoupUtil.getDocument(WALL_BASE_URL + String.format(SEARCH_TAG_URL, search, i));
            url.addAll(JsoupUtil.getDownloadWallUrl(document));
        }
        download(search);
    }


    private static void download(String search) throws IOException, InterruptedException {
        System.out.print("共" + url.size() + "张\n");
        for (int i = 0; i < url.size(); i++) {
            System.out.print("开始下载第" + (i + 1) + "张\n");
            Thread.sleep(500);
            Util.downloadNet(url.get(i), search);
        }
    }
}
