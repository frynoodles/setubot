package com.kyuubiran.setubot.listener;

/*
根据标签返回p站图片及简要信息
调用方式!tag 标签
*/




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.ListenBreak;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.simplerobot.modules.utils.KQCodeUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.text.StringEscapeUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static method.Setu.getShortName;

public class Setu3 {
    private  String url="https://api.imjad.cn/pixiv/v1/?type=search&word=";
    private static Setumodle setumodle;
    private static String location;
    private static final OkHttpClient ok=new OkHttpClient.Builder()
            .callTimeout(130, TimeUnit.SECONDS)
            .connectTimeout(130,TimeUnit.SECONDS)
            .build();
    @Listen(MsgGetTypes.groupMsg)
    @Filter("!tag .*")
    @ListenBreak
    public void aliveTest(GroupMsg msg, MsgSender sender) throws IOException, InterruptedException {
        sender.SENDER.sendGroupMsg(msg,"收到指令，开始寻找图片，可能花费较长时间（当然也可能失败）");
        String tag=msg.getMsg().replace("!tag ","");
        Setu3 setu3 =new Setu3();
        String data= setu3.Get(tag);
        if (data.equals("请求失败")){
            sender.SENDER.sendGroupMsg(msg,"请求失败,请勿频繁使用");
        }else if (data.equals("没有找到相关tag")){
            sender.SENDER.sendGroupMsg(msg,"没有找到相关tag");
        }
        else {
            setu3.Download(data);
            sender.SENDER.sendGroupMsg(msg, KQCodeUtils.INSTANCE.toCq("image", "file="+location) +"\npid:"+setumodle.id+"\n标题:"+setumodle.title+"\nuid:"+setumodle.userid);
        }

    }
    public String Get(String tag) throws IOException {
        url=url+tag+"&mode=tag&page="+(new Random().nextInt(3)+1)+"&per_page=20";
        Request request=new Request.Builder()
                .url(url)
                .build();
        System.out.println(url);
        Response response=ok.newCall(request).execute();
        String str=response.body().string();
        String data=str;
        System.out.println(data);
        response.close();
        data=data.replace("\\s+","");
        data=data.replace("\n","");
        JSONObject jsonObject= JSON.parseObject(data);
        String staus=jsonObject.getString("status");
        if(staus.equals("failure")){
            return "请求失败";
        }else {
            int count=jsonObject.getInteger("count");
            if (count==0){
                return "没有找到相关text";
            }
            else {
                return data;
            }}
    }
    public void Download(String data) throws IOException {
        JSONObject jsonObject=JSON.parseObject(data);
        JSONArray responses=jsonObject.getJSONArray("response");
        JSONObject pic=responses.getJSONObject(new Random().nextInt(responses.size()));
        String id=pic.getString("id");
        String title=pic.getString("title");
        JSONObject image_urls=pic.getJSONObject("image_urls");
        String imgurl=image_urls.getString("px_480mw");
        JSONObject user=pic.getJSONObject("user");
        String userod=user.getString("id");
        title=StringEscapeUtils.unescapeJava(title);
        imgurl=StringEscapeUtils.unescapeJava(imgurl);
        setumodle=new Setumodle(id,imgurl,userod,title);
        File picDir = new File("tag").getAbsoluteFile();
        if (!picDir.exists()) {
            picDir.mkdir();
        }
        File pick = new File(picDir, getShortName(setumodle.imgurl));
        if (!pick.exists()) pick.createNewFile();
        Request request=new Request.Builder()
                .url(setumodle.getImgurl())
                .addHeader("Referer", "https://www.pixiv.net/member_illust.php?mode=medium&illust_id=60541651")
                .build();
        Response response1=ok.newCall(request).execute();
        if (response1.isSuccessful()) {
            byte[] picdata = Objects.requireNonNull(response1.body()).bytes();
            FileOutputStream fout = new FileOutputStream(pick);
            fout.write(picdata);
            fout.flush();
            fout.close();
            location = pick.getAbsolutePath();
        }
    }
    class Setumodle{
        private String id=null;
        private  String imgurl=null;
        private String userid=null;
        private String title=null;

        public Setumodle(String id, String imgurl, String userid,  String title) {
            this.id = id;
            this.imgurl = imgurl;
            this.userid = userid;
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }}
