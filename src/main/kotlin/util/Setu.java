package util;//

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Created by frynoodles on 2021/3/30 23:34
//
public class Setu {

  private final static int CODE_ERROR = -1;
  private final static int CODE_SUCCESS = 0;
  private final static int CODE_API_ERROR = 401;
  private final static int CODE_REFUSE = 403;
  private final static int CODE_KEYWORD_PIC_NOT_FOUND = 404;
  private final static int CODE_LIMITED_CALL = 429;

  private final static OkHttpClient client = new OkHttpClient();
  private String url;
  private int p;
  private int uid;
  private int pid;
  private String title;
  private String author;
  private String location;
  private JSONObject object;

  public String get() {
    Request request;
    request = new Request.Builder()
        .url("https://api.lolicon.app/setu/")
        .get()
        .addHeader("apikey", BotConfig.INSTANCE.getApiKey())
        .addHeader("r18", BotConfig.INSTANCE.getR18())
        .build();
    try {
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        object = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());
        switch (object.getInteger("code")) {
          case CODE_SUCCESS:
            set();
            downloadPic();
            break;
          case CODE_ERROR:
            return "内部错误，请向 i@loli.best 反馈";
          case CODE_API_ERROR:
            return "APIKEY 不存在或被封禁";
          case CODE_REFUSE:
            return "由于不规范的操作而被拒绝调用";
          case CODE_KEYWORD_PIC_NOT_FOUND:
            return "找不到符合关键字的色图";
          case CODE_LIMITED_CALL:
            return "达到调用额度限制";
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void set() {
    JSONArray datas = object.getJSONArray("data");
    JSONObject data = (JSONObject) datas.get(0);
    p = data.getInteger("p");
    uid = data.getInteger("uid");
    pid = data.getInteger("pid");
    url = data.getString("url");
    title = data.getString("title");
    author = data.getString("author");
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private void downloadPic() throws IOException {
    File picDir = new File(Utils.FILE_PATH+"pic").getAbsoluteFile();
    if (!picDir.exists()) {
      picDir.mkdir();
    }
    File pic = new File(picDir, getShortName(url));
    if (!pic.exists()) {
      pic.createNewFile();
    }
    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      byte[] data = Objects.requireNonNull(response.body()).bytes();
      FileOutputStream fout = new FileOutputStream(pic);
      fout.write(data);
      fout.flush();
      fout.close();
      location = pic.getAbsolutePath();
    } else {
      location = null;
      throw new IOException(response.code() + ":" + response.message());
    }
  }

  public static String getShortName(String url) {
    String[] strings = url.split("[/\\\\]");
    return strings[strings.length - 1];
  }

  public String getPicLocation() {
    return location;
  }

  public String getFormat() {
    return "\n标题:" + title +
        "\nPid:" + pid +
        "\nPage:" + p +
        "\nUid:" + uid +
        "\n画师:" + author;
  }
}
