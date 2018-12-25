import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Pattern;

public class Util {
    private Util() {
    }

    /**
     * 获取String里面的所有数字,转为int
     */
    public static int getInt(String s) {
        return Integer.parseInt(Pattern.compile("[^0-9]").matcher(s).replaceAll("").trim());
    }

    /**
     * 获取图片名称
     */
    private static String getImgName(String s) {
        return Pattern.compile("[^0-9]").matcher(s).replaceAll("").trim();
    }


    /**
     * 简单弄个下载
     *
     * @param downloadUrl 下载地址
     * @throws IOException IO
     */
    public static void downloadNet(String downloadUrl, String filePath) throws IOException {
        int byteRead;
        URL url = new URL(downloadUrl);
        File downloadFile = new File("download");
        if (!downloadFile.exists()) {
            downloadFile.mkdir();
        }
        File file = new File(String.format("download/%s", filePath));
        if (!file.exists()) {
            file.mkdir();
        }
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        InputStream inStream = conn.getInputStream();
        FileOutputStream fs = new FileOutputStream(String.format("download/%s/%s.jpg", filePath, getImgName(downloadUrl) + System.currentTimeMillis()));
        byte[] buffer = new byte[1204];
        while ((byteRead = inStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteRead);
        }
    }


    /**
     * 判断是否包含 has
     */

    public static boolean hasDownloadUrl(String url, List<String> list) {
        for (String s : list) {
            if (s.equals(url)) {
                return true;
            }
        }
        return false;
    }

}
