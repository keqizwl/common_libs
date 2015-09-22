package zwl.magic.com.common.model.net.request;

import android.content.Context;
import android.text.TextUtils;

import com.feizhu.publicutils.CacheUtils;
import com.feizhu.publicutils.StringUtil;
import com.feizhu.publicutils.StringUtils;
import com.feizhu.publicutils.SystemUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.ishowedu.child.peiyin.R;
import com.ishowedu.child.peiyin.activity.group.GroupMember;
import com.ishowedu.child.peiyin.activity.group.SimpleUserInfo;
import com.ishowedu.child.peiyin.activity.group.groupCreating.GroupTag;
import com.ishowedu.child.peiyin.activity.group.groupCreating.GroupTempBean;
import com.ishowedu.child.peiyin.activity.group.groupCreating.GroupType;
import com.ishowedu.child.peiyin.activity.group.groupEdit.GroupImageUrl;
import com.ishowedu.child.peiyin.activity.group.wrapper.GroupWork;
import com.ishowedu.child.peiyin.activity.search.Catagory;
import com.ishowedu.child.peiyin.activity.search.SearchCourseOrAlbum;
import com.ishowedu.child.peiyin.activity.space.coursecollect.CourseCollectBean;
import com.ishowedu.child.peiyin.activity.space.message.data.ChatMessageRet;
import com.ishowedu.child.peiyin.activity.space.message.data.MessageV2;
import com.ishowedu.child.peiyin.activity.space.message.data.SystemMessage;
import com.ishowedu.child.peiyin.activity.space.message.data.UnreadMessageCount;
import com.ishowedu.child.peiyin.common.Constants;
import com.ishowedu.child.peiyin.control.authority.UserAuthority;
import com.ishowedu.child.peiyin.model.database.course.Course;
import com.ishowedu.child.peiyin.model.database.dubbingArt.DubbingArt;
import com.ishowedu.child.peiyin.model.database.group.ChatGroup;
import com.ishowedu.child.peiyin.model.database.group.unprogressmatter.UnprogressedMatter;
import com.ishowedu.child.peiyin.model.database.word.Word;
import com.ishowedu.child.peiyin.model.entity.AccountBindInfo;
import com.ishowedu.child.peiyin.model.entity.AlbumOrCourse;
import com.ishowedu.child.peiyin.model.entity.AttentionUserEntity;
import com.ishowedu.child.peiyin.model.entity.ChanagePwd;
import com.ishowedu.child.peiyin.model.entity.ChatGroupWrapper;
import com.ishowedu.child.peiyin.model.entity.City;
import com.ishowedu.child.peiyin.model.entity.Collect;
import com.ishowedu.child.peiyin.model.entity.Comment;
import com.ishowedu.child.peiyin.model.entity.CommentWrapper;
import com.ishowedu.child.peiyin.model.entity.CourseAlbum;
import com.ishowedu.child.peiyin.model.entity.HomePageContent;
import com.ishowedu.child.peiyin.model.entity.IshowHomePageData;
import com.ishowedu.child.peiyin.model.entity.PhotoEntity;
import com.ishowedu.child.peiyin.model.entity.PrivateVideoInfo;
import com.ishowedu.child.peiyin.model.entity.RecommendInfo;
import com.ishowedu.child.peiyin.model.entity.RecommendRankInfo;
import com.ishowedu.child.peiyin.model.entity.RefreshToken;
import com.ishowedu.child.peiyin.model.entity.Result;
import com.ishowedu.child.peiyin.model.entity.ResultWithId;
import com.ishowedu.child.peiyin.model.entity.SchoolAndArea;
import com.ishowedu.child.peiyin.model.entity.SearchUserInfo;
import com.ishowedu.child.peiyin.model.entity.SpaceInfo;
import com.ishowedu.child.peiyin.model.entity.Support;
import com.ishowedu.child.peiyin.model.entity.Upload;
import com.ishowedu.child.peiyin.model.entity.User;
import com.ishowedu.child.peiyin.model.entity.UserData;
import com.ishowedu.child.peiyin.model.entity.UserType;
import com.ishowedu.child.peiyin.model.entity.VerifyCode;
import com.ishowedu.child.peiyin.model.entity.Version;
import com.ishowedu.child.peiyin.model.entity.VisitorEntity;
import com.ishowedu.child.peiyin.model.entity.WeChatUserInfo;
import com.ishowedu.child.peiyin.model.entity.WechatAuthInfo;
import com.ishowedu.child.peiyin.model.entity.WeiboUserInfo;
import com.ishowedu.child.peiyin.model.entity.WonderAct;
import com.ishowedu.child.peiyin.model.net.base.HttpException;
import com.ishowedu.child.peiyin.model.net.base.HttpHelper;
import com.ishowedu.child.peiyin.model.proxy.UserProxy;
import com.ishowedu.child.peiyin.util.CLog;
import com.ishowedu.child.peiyin.util.JsonUtil;
import com.ishowedu.child.peiyin.util.OtherUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 实现与后端的协议接口
 */
public class NetInterface implements INetInterface {
    private static final String TAG = "NetInterface";
    private static final String DEFALUT_REFRESH_TOKEN = "MTQzNDUzNzcyNLCHyGKAnqKh";
    /**
     * NetInterface的单例对象
     */
    private static NetInterface instance;
    /**
     * 用户的uid和token
     */
    private UserData userData;
    /**
     * App上下文
     */
    private Context context;
    /**
     * http请求封装对象
     */
    private HttpHelper httpHelper;
    /**
     * 刷新token
     */
    private String refreshToken;

    private boolean isAuthTokenAvailable = true;
    private RefreshTokenThread refreshTokenThread;
    private List<Object> threadLocks = new ArrayList<Object>();
    private int threadId;

    /**
     * 获取json解析对象
     *
     * @return
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * 创建json解析对象
     */
    private final static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 单利构造函数
     */
    private NetInterface() {
        context = IShowDubbingApplication.getInstance().getApplicationContext();
        httpHelper = HttpHelper.getInstance();
        initUserData();
    }

    /**
     * 获取单利对象
     *
     * @return
     */
    public static NetInterface getInstance() {
        if (instance == null) {
            synchronized (NetInterface.class) {
                if (instance == null) {
                    instance = new NetInterface();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化用户数据,获取用户数据
     */
    private void initUserData() {
        userData = new UserData();
        refreshToken = DEFALUT_REFRESH_TOKEN;

        User user = UserProxy.getInstance().getUser();
        if (user == null) {
            return;
        }
        refreshToken = TextUtils.isEmpty(user.refresh_token) ? DEFALUT_REFRESH_TOKEN : user.refresh_token;
        userData.id = String.valueOf(user.uid);
        userData.auth_token = user.auth_token;
    }

    private String getRefreeshToken() {
        User user = UserProxy.getInstance().getUser();
        if (user == null) {
            CLog.d(TAG, "getRefreeshToken user == null");
            return null;
        }

        if (user.refresh_token == null || user.refresh_token.isEmpty()) {
            return DEFALUT_REFRESH_TOKEN;
        }

        return user.refresh_token;
    }

    private String getJson(String url, List<NameValuePair> params, String key) throws HttpException {
        String json = null;
        HttpException httpException = null;
        try {
            json = httpHelper.httpGetRequestJson(Server.getUrl(
                    url, params));
            json = checkRespone(json);
            cacheJson(key, json);
        } catch (HttpException e) {
            httpException = e;
        }
        if (TextUtils.isEmpty(json)) {
            json = getCacheJson(key);
            if (json == null && httpException != null) {
                throw (httpException);
            }
        }

        return json;
    }

    private void cacheJson(String key, String json) {
        if (json == null) {
            return;
        }
        CacheUtils.saveStringToSharePrefs(context, Constants.FILE_JSON_CACHE, key, json);
    }

    private String getCacheJson(String key) {
        return CacheUtils.getStringFromSharePrefs(context, Constants.FILE_JSON_CACHE, key, null);
    }

    /**
     * 获取游客账户
     *
     * @param context
     * @return
     * @throws HttpException
     */
    @Override
    public User getVisitor(Context context) throws HttpException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("devicetoken",
                    SystemUtils.getDeviceId(context)));
            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_VISITOR_NEW, params));

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            saveUser(context, user);

            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 刷新user信息,当升级之后，User有新增字段时调用
     *
     * @param context
     * @return
     * @throws HttpException
     */
    @Override
    public User getUserData(Context context)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getUserData 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_USER_DATA, params));

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            // 保存用户信息
            saveUser(context, user);
            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取验证码
     *
     * @param mobile
     * @param isReset
     * @return
     * @throws HttpException
     */
    public VerifyCode getCode(String mobile, int isReset)
            throws HttpException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mobile", String.valueOf(mobile)));
            params.add(new BasicNameValuePair("isreset", String.valueOf(isReset)));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_CODE, params));

            json = checkRespone(json);

            return new VerifyCode();
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 注册
     *
     * @param context
     * @param mobile
     * @param password
     * @param nickname
     * @param code
     * @return
     * @throws HttpException
     */
    @Override
    public User register(Context context, String mobile, String password,
                         String nickname, String code) throws HttpException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("devicetoken",
                    SystemUtils.getDeviceId(context)));
            params.add(new BasicNameValuePair("nickname", nickname));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("code", code));
            params.add(new BasicNameValuePair("auto_login", "1"));

            String json = httpHelper.httpPostRequestJson(Server.URL_REGISTE, params);

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            // 保存用户信息
            saveUser(context, user);
            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 游客绑定
     *
     * @param context
     * @param mobile
     * @param password
     * @param nickname
     * @param code
     * @return
     * @throws HttpException
     */
    @Override
    public User tieupMobile(Context context, String mobile,
                            String password, String nickname, String code)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "tieupMobile 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("devicetoken",
                    SystemUtils.getDeviceId(context)));
            params.add(new BasicNameValuePair("nickname", nickname));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("code", code));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_TIEUP_MOBILE,
                    params);

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            saveBindInfo(context, 0, 1);
            // 保存用户信息
            saveUser(context, user);
            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 第三方
     *
     * @param context
     * @param token
     * @param type
     * @param nickname
     * @param avatar
     * @param sex
     * @param signature
     * @return
     * @throws HttpException
     */
    @Override
    public User thirdLogin(Context context, String token, String type,
                           String nickname, String avatar, String sex, String signature)
            throws HttpException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("devicetoken",
                    SystemUtils.getDeviceId(context)));
            params.add(new BasicNameValuePair("token", token));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("nickname", nickname));
            params.add(new BasicNameValuePair("avatar", avatar));
            params.add(new BasicNameValuePair("sex", "" + sex));
            params.add(new BasicNameValuePair("signature", signature));
            params.add(new BasicNameValuePair("auto_login", "1"));

            String json = httpHelper.httpPostRequestJson(Server.URL_THIRD_LOGIN,
                    params);

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            // 保存用户信息
            saveUser(context, user);
            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }


    /**
     * 获取微博信息
     *
     * @param uid
     * @param authToken
     * @return
     * @throws HttpException
     */
    @Override
    public WeiboUserInfo getWeiboUserInfo(String uid, String authToken)
            throws HttpException {
        try {

            String json = httpHelper
                    .httpGetRequestJson("https://api.weibo.com/2/users/show.json?uid="
                            + uid + "&access_token=" + authToken);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<WeiboUserInfo>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }


    /**
     * 找回密码
     *
     * @param context
     * @param mobile
     * @param password
     * @param code
     * @return
     * @throws HttpException
     */
    @Override
    public ChanagePwd resetPwd(Context context, String mobile, String password,
                               String code) throws HttpException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("newpassword", password));
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("code", code));

            String json = httpHelper
                    .httpPostRequestJson(Server.URL_RESET_PWD, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<ChanagePwd>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 修改密码
     *
     * @param context
     * @param password
     * @param newpassword
     * @return
     * @throws HttpException
     */
    @Override
    public ChanagePwd changePwd(Context context, String password, String newpassword)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "changePwd 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("mobile",
                    String.valueOf(UserProxy.getInstance().getUser().mobile)));
            params.add(new BasicNameValuePair("newpassword", newpassword));

            String json = httpHelper.httpPostRequestJson(Server.URL_CHANGE_PWD,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<ChanagePwd>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 手机号登陆
     *
     * @param context
     * @param mobile
     * @param password
     * @return
     * @throws HttpException
     */
    @Override
    public User login(Context context, String mobile, String password)
            throws HttpException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("devicetoken",
                    SystemUtils.getDeviceId(context)));

            String json = httpHelper.httpPostRequestJson(Server.URL_LOGIN, params);

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            // 保存用户信息
            saveUser(context, user);
            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    @Override
    public User quPeiyinLogin(Context context, String mobile, String password)
            throws HttpException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("devicetoken",
                    SystemUtils.getDeviceId(context)));

            String json = httpHelper.httpPostRequestJson(Server.URL_QUPEIYIN_LOGIN, params);

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            // 保存用户信息
            saveUser(context, user);
            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 注销
     *
     * @param context
     * @return
     * @throws HttpException
     */
    @Override
    public Result logout(Context context) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "logout 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            clearUserData(context);

            if (UserAuthority.getInstance().getAuthority(UserProxy.getInstance().getUser()) == UserAuthority.AUTHORITY_VISITOR_USER) {
                return null;
            } else {
                // 通知服务器退出
                String json = httpHelper.httpPostRequestJson(Server.URL_LOGOUT, params);
                json = checkRespone(json);
                return gson.fromJson(json, new TypeToken<Result>() {
                }.getType());
            }
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 修改头像
     *
     * @param context
     * @param file
     * @return
     * @throws HttpException
     */
    @Override
    public User changeAvatar(Context context, File file)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "changeAvatar 用户信息无效,用户已登出");
                return null;
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("uid", userData.id);
            params.put("auth_token", userData.auth_token);

            String json = UploadUtil.getInstance().uploadFile(context, file,
                    "avatar", Server.URL_CHANGE_AVATAR, params);

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            saveUser(context, user);
            OtherUtils.sendBroadcastNumChange(context,
                    Constants.KEY_PHOTO_ADD, 1);
            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 修改用户信息
     *
     * @param context
     * @param user
     * @param class_id
     * @return
     * @throws HttpException
     */
    @Override
    public User modifyUserInfo(Context context, User user, String class_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "modifyUserInfo 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if (user.nickname != null) {
                params.add(new BasicNameValuePair("nickname", user.nickname));
            }
            if (user.email != null) {
                params.add(new BasicNameValuePair("email", user.email));
            }
            if (user.school != null) {
                params.add(new BasicNameValuePair("school", user.school));
            }
            if (user.school_str != null) {
                params.add(new BasicNameValuePair("school_str", user.school_str));
            }
            if (user.area != null) {
                params.add(new BasicNameValuePair("area", user.area));
            }
            if (user.signature != null) {
                params.add(new BasicNameValuePair("signature", user.signature));
            }
            if (user.sex != 0) {
                params.add(new BasicNameValuePair("sex", String.valueOf(user.sex)));
            }

            if (class_id != null) {
                params.add(new BasicNameValuePair("class_id", String
                        .valueOf(class_id)));
            }
            if (user.birthday != null) {
                params.add(new BasicNameValuePair("birthday", String
                        .valueOf(user.birthday)));
            }

            if (params.size() == 0) {
                return null;
            }

            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            String json = httpHelper.httpPostRequestJson(Server.URL_MODIFY_USER_INFO,
                    params);

            json = checkRespone(json);

            User ret = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            // 保存用户信息
            saveUser(context, ret);
            return ret;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取收藏课程列表(changed include album and course)
     *
     * @param start
     * @param rows
     * @param keyword
     * @return
     * @throws HttpException
     */
    @Override
    public List<CourseCollectBean> getFavCourse(int start, int rows,
                                                String keyword) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getFavCourse 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));
            params.add(new BasicNameValuePair("keyword", keyword));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            CLog.v("url", Server.getUrl(Server.URL_GET_FAV_COURSE, params));
            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_FAV_COURSE, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<CourseCollectBean>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 提交推荐视频
     *
     * @param content
     * @return
     * @throws HttpException
     */
    @Override
    public Result suggestVideo(String content) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "suggestVideo 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("content", content));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_SUGGEST_VIDEO,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取所有人聊天记录列表
     *
     * @return
     * @throws HttpException
     */
    @Override
    public List<ChatMessageRet> getAllChatMessageList()
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getAllChatMessageList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ALL_CHAT_MESSAGE_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<ChatMessageRet>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取非即时聊天消息列表
     *
     * @param type: system 系统消息 comments 评论 showfollows 点赞关注
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<MessageV2> getMessageListV32(String type, int start, int rows)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getMessageListV32 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_MESSAGE_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<MessageV2>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取未读消息数
     *
     * @return
     * @throws HttpException
     */
    @Override
    public UnreadMessageCount getUnreadMessageCount()
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getUnreadMessageCount 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_UNREAD_MESSAGE_COUNT, params));

            json = checkRespone(json);

            UnreadMessageCount unreadMessageCount = gson.fromJson(json,
                    new TypeToken<UnreadMessageCount>() {
                    }.getType());
            return unreadMessageCount;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 收藏课程
     *
     * @param context
     * @param courseId
     * @param albumId
     * @return
     * @throws HttpException
     */
    @Override
    public Result collectFav(Context context, String courseId, String albumId)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "collectFav 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", courseId));
            params.add(new BasicNameValuePair("album_id", albumId));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_COLLECT, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取版本信息,用户更新版本
     *
     * @return
     * @throws HttpException
     */
    @Override
    public Version getVersion() throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getVersion 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("app", "android"));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_VERSION, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Version>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 点赞
     *
     * @param context
     * @param show_id
     * @param show_uid
     * @return
     * @throws HttpException
     */
    @Override
    public Result addPraise(Context context, String show_id, String show_uid, int type)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "addPraise 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", show_id));
            params.add(new BasicNameValuePair("show_uid", show_uid));
            params.add(new BasicNameValuePair("type", String.valueOf(type)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_ADD_PRAISE,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 取消收藏（课程）
     *
     * @param context
     * @param courseId
     * @param albumId
     * @return
     * @throws HttpException
     */
    @Override
    public Result cancelCollect(Context context, String courseId, String albumId)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "cancelCollect 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", courseId));
            params.add(new BasicNameValuePair("album_id", albumId));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_CANCEL_COLLECT,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 删除收藏的课程
     *
     * @param context
     * @param collect_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result delectCollect(Context context, String collect_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "delectCollect 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("collect_id", collect_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_DELETE_COLLECT,
                    params);

            json = checkRespone(json);

            OtherUtils.sendBroadcastNumChange(context,
                    Constants.KEY_CACHE_COURSE_ADD, -1);
            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 删除配音作品
     *
     * @param context
     * @param show_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result delectMyDubbing(Context context, String show_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "delectMyDubbing 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", show_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_DELETE_DUBBING,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取收藏的作品
     *
     * @param context
     * @param course_id
     * @param album_id
     * @return
     * @throws HttpException
     */
    @Override
    public Collect getIfCollected(Context context, String course_id,
                                  String album_id) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getIfCollected 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", course_id));
            params.add(new BasicNameValuePair("album_id", album_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_CHECK_COLLECT, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Collect>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取赞
     *
     * @param context
     * @param show_id
     * @return
     * @throws HttpException
     */
    @Override
    public Support getIfPraise(Context context, String show_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getIfPraise 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", show_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_CHECK_PRAISE, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Support>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 添加评论
     *
     * @param context
     * @param show_uid
     * @param show_id
     * @param commentStr
     * @return
     * @throws HttpException
     */
    @Override
    public ResultWithId addComment(Context context, String show_uid,
                                   String show_id, String commentStr) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "addComment 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_uid", show_uid));
            params.add(new BasicNameValuePair("show_id", show_id));
            params.add(new BasicNameValuePair("comment_text", commentStr));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_ADD_COMMENT,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<ResultWithId>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 添加语音评论
     *
     * @param context
     * @param show_uid
     * @param show_id
     * @param commentStr
     * @param audio
     * @param audioTimeLen
     * @return
     * @throws HttpException
     */
    @Override
    public ResultWithId addAudioComment(Context context, String show_uid,
                                        String show_id, String commentStr, String audio, int audioTimeLen)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "addAudioComment 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_uid", show_uid));
            params.add(new BasicNameValuePair("show_id", show_id));
            params.add(new BasicNameValuePair("comment_text", commentStr));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            params.add(new BasicNameValuePair("comment_audio", audio));
            params.add(new BasicNameValuePair("comment_audio_timelen", ""
                    + audioTimeLen));

            String json = httpHelper.httpPostRequestJson(Server.URL_ADD_COMMENT,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<ResultWithId>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param course_id
     * @param album_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result getCoursePrivilege(Context context, String course_id,
                                     String album_id) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCoursePrivilege 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", course_id));
            params.add(new BasicNameValuePair("album_id", album_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_COURSE_PRIVILEGE, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param course_id
     * @param file_info
     * @param album_id
     * @param group_id
     * @param task_id
     * @param area_id
     * @param lon
     * @param lat
     * @return
     * @throws HttpException
     */
    @Override
    public Upload uploadFileInfo(Context context, String course_id,
                                 String file_info, String album_id, String group_id, String task_id,
                                 String area_id, String lon, String lat)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "uploadFileInfo 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("album_id", album_id));
            params.add(new BasicNameValuePair("course_id", course_id));
            params.add(new BasicNameValuePair("group_id", group_id));
            params.add(new BasicNameValuePair("task_id", task_id));
            params.add(new BasicNameValuePair("area_id", area_id));
            params.add(new BasicNameValuePair("lon", lon));
            params.add(new BasicNameValuePair("lat", lat));
            params.add(new BasicNameValuePair("file_info", file_info));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_UPLOAD_FILEINFO,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Upload>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getRecomDubbingList(Context context, int start,
                                                int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getRecomDubbingList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_RECOMDUBBINGS, params));

            json = checkRespone(json);

            List<DubbingArt> dubbingList = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            if (dubbingList != null) {
                for (DubbingArt dubbingArt : dubbingList) {
                    dubbingArt.school = dubbingArt.school + "@"
                            + dubbingArt.school_str;
                }
            }

            return dubbingList;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param keyword
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<SearchUserInfo> searchUsers(Context context, String keyword,
                                            int start, int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "searchUsers 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("keyword", "" + keyword));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_SEARCH_USERS,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<SearchUserInfo>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<SearchUserInfo> searchHotUsers(Context context, int start,
                                               int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "searchHotUsers 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_SEARCH_HOTUSERS,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<SearchUserInfo>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param ishow
     * @param time_type
     * @param ishow_type
     * @param course_id
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getRankList(Context context, int ishow,
                                        int time_type, int ishow_type, long course_id, int start, int rows)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getRankList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ishow", "" + ishow));
            params.add(new BasicNameValuePair("time_type", "" + time_type));
            params.add(new BasicNameValuePair("ishow_type", "" + ishow_type));
            if (course_id > 0) {
                params.add(new BasicNameValuePair("course_id", "" + course_id));
            }
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_RANK_LIST, params));

            json = checkRespone(json);

            List<DubbingArt> dubbingList = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            if (dubbingList != null) {
                for (DubbingArt dubbingArt : dubbingList) {
                    dubbingArt.school = dubbingArt.school + "@"
                            + dubbingArt.school_str;
                }
            }

            return dubbingList;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取专辑列表，按分类
     *
     * @param context
     * @param start
     * @param rows
     * @param args
     * @return
     * @throws HttpException
     */
    @Override
    public List<CourseAlbum> getCourseAlbumList(Context context, int cate_id, int start,
                                                int rows, LinkedHashMap<String, String> args)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCourseAlbumList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("category_id", "" + cate_id));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            for (Map.Entry<String, String> map : args.entrySet()) {
                params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
            }
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ALBUM_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<CourseAlbum>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param album_id
     * @return
     * @throws HttpException
     */
    @Override
    public CourseAlbum getCourseAlbumDetail(Context context, long album_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCourseAlbumDetail 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("album_id", "" + album_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ALBUM_DETAIL, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<CourseAlbum>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @return
     * @throws HttpException
     */
    @Override
    public List<HomePageContent> getIndexPage(Context context)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getIndexPage 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = getJson(Server.URL_GET_INDEX, params, Constants.KEY_HOME_ENTITY_RAW);

            List<HomePageContent> homePageContent = gson.fromJson(json,
                    new TypeToken<List<HomePageContent>>() {
                    }.getType());

            return homePageContent;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取课程列表，按分类
     *
     * @param context
     * @param category_id
     * @param start
     * @param rows
     * @param args
     * @param ishow
     * @return
     * @throws HttpException
     */
    @Override
    public List<Course> getCourseList(Context context, int category_id,
                                      int start, int rows, LinkedHashMap<String, String> args, int ishow)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCourseList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("category_id", "" + category_id));
            for (Map.Entry<String, String> map : args.entrySet()) {
                params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
            }
            params.add(new BasicNameValuePair("ishow", "" + ishow));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_COURSE_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<Course>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param course_id
     * @return
     * @throws HttpException
     */
    @Override
    public Course getCourseDetail(Context context, long course_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCourseDetail 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", "" + course_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_COURSE_DETAIL, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Course>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @return
     * @throws HttpException
     */
    @Override
    public IshowHomePageData getIshowHomePage(Context context)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getIshowHomePage 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ISHOW_HOME_PAGE, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<IshowHomePageData>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param commentId
     * @return
     * @throws HttpException
     */
    @Override
    public Result deleteComment(Context context, long commentId)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "deleteComment 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("comment_id", "" + commentId));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_DELETE_COMMENT,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param show_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result addDubView(Context context, long show_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "addDubView 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", "" + show_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_ADD_DUB_VIEW,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id
     * @return
     * @throws HttpException
     */
    @Override
    public SpaceInfo getSpaceInfo(Context context, int member_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getSpaceInfo 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("member_id", "" + member_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_SPACE_INFO, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<SpaceInfo>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param show_id
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<Comment> getCommentList(Context context, int show_id,
                                        int start, int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCommentList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", "" + show_id));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_COMMENT_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<Comment>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param comment_id
     * @param comment_text
     * @param audio
     * @param auidoTimeLen
     * @return
     * @throws HttpException
     */
    @Override
    public ResultWithId replyAudioComment(Context context, long comment_id,
                                          String comment_text, String audio, int auidoTimeLen)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "replyAudioComment 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("comment_id", "" + comment_id));
            params.add(new BasicNameValuePair("comment_text", "" + comment_text));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            params.add(new BasicNameValuePair("comment_audio", audio));
            params.add(new BasicNameValuePair("comment_audio_timelen", ""
                    + auidoTimeLen));

            String json = httpHelper.httpPostRequestJson(Server.URL_REPLY_COMMENT,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<ResultWithId>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param comment_id
     * @param comment_text
     * @return
     * @throws HttpException
     */
    @Override
    public ResultWithId replyComment(Context context, long comment_id,
                                     String comment_text) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "replyComment 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("comment_id", "" + comment_id));
            params.add(new BasicNameValuePair("comment_text", "" + comment_text));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_REPLY_COMMENT,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<ResultWithId>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param fans_uid
     * @return
     * @throws HttpException
     */
    @Override
    public Result addAttention(Context context, int fans_uid)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "addAttention 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fans_uid", "" + fans_uid));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_ADD_ATTENTION, params));

            json = checkRespone(json);

            OtherUtils.sendBroadcastNumChange(context,
                    Constants.KEY_ATTENTION_NUM_ADD, 1);
            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param fans_uid
     * @return
     * @throws HttpException
     */
    @Override
    public Result delAttention(Context context, int fans_uid)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "delAttention 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fans_uid", "" + fans_uid));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_DEL_ATTENTION,
                    params);

            json = checkRespone(json);

            OtherUtils.sendBroadcastNumChange(context,
                    Constants.KEY_ATTENTION_NUM_ADD, -1);
            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id
     * @param start
     * @param rows
     * @param keyword
     * @return
     * @throws HttpException
     */
    @Override
    public AttentionUserEntity getAttention(Context context, int member_id,
                                            int start, int rows, String keyword)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getAttention 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("member_id", "" + member_id));
            params.add(new BasicNameValuePair("keyword", keyword));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ATTENTION, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<AttentionUserEntity>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id
     * @param start
     * @param rows
     * @param keyword
     * @return
     * @throws HttpException
     */
    @Override
    public AttentionUserEntity getFans(Context context, int member_id,
                                       int start, int rows, String keyword)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getFans 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("member_id", "" + member_id));
            params.add(new BasicNameValuePair("keyword", keyword));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_FANS, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<AttentionUserEntity>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id
     * @param msgtype
     * @param content
     * @return
     * @throws HttpException
     */
    @Override
    public Result addWords(Context context, int member_id, int msgtype,
                           String content) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "addWords 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("member_id", "" + member_id));
            params.add(new BasicNameValuePair("msgtype", "" + msgtype));
            params.add(new BasicNameValuePair("content", "" + content));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper
                    .httpPostRequestJson(Server.URL_ADD_WORDS, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param photo_ids
     * @return
     * @throws HttpException
     */
    @Override
    public Result deletePhoto(Context context, String photo_ids)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "deletePhoto 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("photo_id", "" + photo_ids));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper
                    .httpPostRequestJson(Server.URL_DEL_PHOTO, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param photo_id
     * @param type
     * @return
     * @throws HttpException
     */
    @Override
    public Result setPhoto(Context context, long photo_id, String type)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "setPhoto 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("photo_id", "" + photo_id));
            params.add(new BasicNameValuePair("type", "" + type));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_SET_AVATAR_OR_COVER, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public PhotoEntity getPhotoList(Context context, int member_id, int start,
                                    int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getPhotoList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("member_id", "" + member_id));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_PHOTO_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<PhotoEntity>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public VisitorEntity getVisitorList(Context context, int member_id,
                                        int start, int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getVisitorList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("member_id", "" + member_id));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_VISITOR_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<VisitorEntity>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id
     * @param start
     * @param rows
     * @param keyword
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getOtherDubList(Context context, int member_id,
                                            int start, int rows, String keyword)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getOtherDubList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("member_id", "" + member_id));
            params.add(new BasicNameValuePair("keyword", keyword));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_OHTER_DUB_LIST, params));

            json = checkRespone(json);

            List<DubbingArt> dubbingArts = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            if (dubbingArts != null) {
                for (DubbingArt dubbingArt : dubbingArts) {
                    dubbingArt.school = dubbingArt.school + "@"
                            + dubbingArt.school_str;
                }
            }

            return dubbingArts;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 上传照片
     *
     * @param context
     * @param file
     * @return
     * @throws HttpException
     */
    public Result uploadPhoto(Context context, File file)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "uploadPhoto 用户信息无效,用户已登出");
                return null;
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("uid", userData.id);
            params.put("auth_token", userData.auth_token);
            String json = UploadUtil.getInstance().uploadFile(context, file,
                    "photo", Server.URL_UPLOAD_PHOTO, params);

            json = checkRespone(json);

            OtherUtils.sendBroadcastNumChange(context,
                    Constants.KEY_PHOTO_ADD, 1);
            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param show_id
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<CommentWrapper> getCommentsLayer(Context context, int show_id,
                                                 int start, int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCommentsLayer 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", "" + show_id));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_COMMENTS_LAYER_V313, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<CommentWrapper>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取二维码等信息
     *
     * @param uid
     * @return
     * @throws HttpException
     */
    @Override
    public RecommendInfo getQRCode(String uid) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getQRCode 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", uid));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_COMMENTS_SPREAD, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<RecommendInfo>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取推客榜单
     *
     * @param time_type
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<RecommendRankInfo> getRecommendRank(int time_type, int start,
                                                    int rows) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getRecommendRank 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("time_type", time_type + ""));
            params.add(new BasicNameValuePair("start", start + ""));
            params.add(new BasicNameValuePair("rows", rows + ""));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_SPREAD_TOP, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<RecommendRankInfo>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param id
     * @return
     * @throws HttpException
     */
    @Override
    public DubbingArt getDubbingArt(int id) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getDubbingArt 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", id + ""));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ART_DETAIL, params));

            json = checkRespone(json);

            DubbingArt dubbingArt = gson.fromJson(json,
                    new TypeToken<DubbingArt>() {
                    }.getType());

            if (dubbingArt != null) {
                dubbingArt.school = dubbingArt.school + "@" + dubbingArt.school_str;
            }

            return dubbingArt;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param course_id
     * @param start
     * @param rows
     * @param album_id
     * @return
     * @throws HttpException
     */
    @Override
    public List<Course> getRelativeCourse(long course_id, int start, int rows,
                                          String album_id) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getRelativeCourse 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", course_id + ""));
            params.add(new BasicNameValuePair("album_id", album_id));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_RELATIVE_COURSE, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<Course>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param keyword
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<AlbumOrCourse> searchAlbumOrCourse(String keyword, int start,
                                                   int rows) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "searchAlbumOrCourse 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("keyword", keyword));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_SEARCH_COURSE_OR_ALBUM, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<AlbumOrCourse>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param auth_token
     * @param type
     * @param nickname
     * @return
     * @throws HttpException
     */
    @Override
    public User tieupThirdLogin(Context context, String auth_token, int type,
                                String nickname) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "tieupThirdLogin 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("token", auth_token));
            params.add(new BasicNameValuePair("type", "" + type));
            params.add(new BasicNameValuePair("nickname", nickname));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_TIEUP_THIRDLOGIN,
                    params);

            json = checkRespone(json);

            saveBindInfo(context, type, 1);

            return gson.fromJson(json, new TypeToken<User>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @return
     * @throws HttpException
     */
    @Override
    public UserType getUserType() throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getUserType 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_USER_TYPE, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<UserType>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param time_type
     * @param ishow_type
     * @param start
     * @param rows
     * @param cityId
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getDubbingRank(int time_type, int ishow_type,
                                           int start, int rows, String cityId) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getDubbingRank 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("time_type", "" + time_type));
            params.add(new BasicNameValuePair("ishow_type", "" + ishow_type));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            if (!StringUtils.isAllWhiteSpace(cityId)) {
                params.add(new BasicNameValuePair("area_id", cityId));
            }

            String json = httpHelper.httpPostRequestJson(Server.URL_GET_DUBBING_RANK,
                    params);

            json = checkRespone(json);

            List<DubbingArt> dubbingArts = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            if (dubbingArts != null) {
                for (DubbingArt dubbingArt : dubbingArts) {
                    dubbingArt.school = dubbingArt.school + "@"
                            + dubbingArt.school_str;
                }
            }

            return dubbingArts;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取所有学校
     *
     * @param area_id
     * @return
     * @throws HttpException
     */
    @Override
    public List<SchoolAndArea> getAllSchool(String area_id) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getAllSchool 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("area_id", area_id));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ALL_SCHOOL, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<SchoolAndArea>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param uid
     * @param deviceId
     * @return
     * @throws HttpException
     */
    @Override
    public User tieupGuest(Context context, int uid, String deviceId)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "tieupGuest 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", uid + ""));
            params.add(new BasicNameValuePair("devicetoken", deviceId));

            String json = httpHelper.httpPostRequestJson(Server.URL_TIEUP_GUEST,
                    params);

            json = checkRespone(json);

            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());

            saveBindInfo(context, -1, 1);
            CacheUtils.saveStringToSharePrefs(context, Constants.FILE_SETTING,
                    Constants.KEY_MOBILE_NUM, user.mobile);

            // 保存用户信息
            saveUser(context, user);
            return user;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param albumId
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<Course> getAlbumCourseList(int albumId, int start, int rows)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getAlbumCourseList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("album_id", "" + albumId));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ALBUM_COURSE_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<Course>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param show_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result suggestArt(int show_id) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "suggestArt 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", show_id + ""));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_SHOW_RECOMMEND,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 获取生词本
     *
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<Word> getWordBookList(int start, int rows) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getWordBookList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_WORD_BOOK_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<Word>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 删除单词
     *
     * @param context
     * @param words
     * @param isclear
     * @return
     * @throws HttpException
     */
    @Override
    public Result delWords(Context context, String words, int isclear)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "delWords 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("word", words));
            params.add(new BasicNameValuePair("isclear", isclear + ""));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_DEL_WORD_BOOK,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 同步单词到服务端
     *
     * @param context
     * @param words
     * @param isAddNewWord
     * @return
     * @throws HttpException
     */
    @Override
    public Result addWords(Context context, List<Word> words,
                           boolean isAddNewWord) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "addWords 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            StringBuffer sbufer = new StringBuffer("[");
            for (int i = 0; i < words.size(); i++) {
                sbufer = sbufer.append(words.get(i).getWordJson());
                if (i == words.size() - 1) {
                    sbufer.append("]");
                } else {
                    sbufer.append(",");
                }
            }
            params.add(new BasicNameValuePair("word", sbufer.toString()));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_ADD_WORD_BOOK,
                    params);

            json = checkRespone(json);

            if (isAddNewWord) {
                OtherUtils.sendBroadcastNumChange(context,
                        Constants.KEY_NEW_WORD_ADD, 1);
            }

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<SystemMessage> getSystemMessageList(int start, int rows)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getSystemMessageList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_SYSTEM_MESSAGE_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<SystemMessage>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param msg_type
     * @param content
     * @param dataLen
     * @param cate
     * @return
     * @throws HttpException
     */
    @Override
    public Result replySystem(int msg_type, String content, String dataLen,
                              int cate) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "replySystem 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("msg_type", String.valueOf(msg_type)));
            params.add(new BasicNameValuePair("content", content));
            params.add(new BasicNameValuePair("timelen", dataLen));
            params.add(new BasicNameValuePair("cate", String.valueOf(cate)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_REPLY_SYSTEM,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param type
     * @param isclear
     * @param msg_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result deleteMessage(Context context, String type, int isclear,
                                int msg_id) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "deleteMessage 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("isclear", String.valueOf(isclear)));
            params.add(new BasicNameValuePair("msg_id", String.valueOf(msg_id)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_DELETE_MESSAGE,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 删除用户聊天消息
     *
     * @param context
     * @param member_id
     * @param
     * @return
     * @throws HttpException
     */
    @Override
    public Result deleteUserMessageByUserId(Context context, String member_id)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "deleteUserMessageByUserId 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("member_id", member_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_DELETE_USER_MESSAGE, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * 删除用户聊天消息
     *
     * @param context
     * @param
     * @param msg_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result deleteUserMessageByMsgId(Context context, String msg_id)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "deleteUserMessageByMsgId 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("msg_id", String.valueOf(msg_id)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_DELETE_USER_MESSAGE, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param file
     * @return
     * @throws HttpException
     */
    @Override
    public String changeCover(Context context, File file)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "changeCover 用户信息无效,用户已登出");
                return null;
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("uid", userData.id);
            params.put("auth_token", userData.auth_token);
            String json = UploadUtil.getInstance().uploadFile(context, file,
                    "cover", Server.URL_CHANGE_COVER, params);

            json = checkRespone(json);

            JSONObject jsonObject = new JSONObject(json);
            OtherUtils.sendBroadcastNumChange(context,
                    Constants.KEY_PHOTO_ADD, 1);
            return jsonObject.getString("cover");
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param mobile
     * @param code
     * @return
     * @throws HttpException
     */
    @Override
    public User unbindMobile(Context context, String mobile, String code)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "unbindMobile 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mobile", String.valueOf(mobile)));
            params.add(new BasicNameValuePair("code", String.valueOf(code)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_UNBIND_MOBILE,
                    params);

            json = checkRespone(json);

            saveBindInfo(context, 0, 0);

            return gson.fromJson(json, new TypeToken<User>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param type
     * @return
     * @throws HttpException
     */
    @Override
    public User unbindOthers(Context context, int type)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "unbindOthers 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", String.valueOf(type)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_UNBIND_OTHERS,
                    params);

            json = checkRespone(json);

            saveBindInfo(context, type, 0);

            return gson.fromJson(json, new TypeToken<User>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param type        (course课程列表,album专辑列表,search搜索列表)
     * @param category_id (热门视频,动漫配音,名人演说,听歌学习等主页课程分类的id)
     * @return 得到搜索页分类信息
     * @throws HttpException
     */
    @Override
    public List<Catagory> getCatagoryList(Context context, String type,
                                          int category_id) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCatagoryList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", type));
            if (type.equals("album")) {
                params.add(new BasicNameValuePair("album_class_id", "" + category_id));
            } else {
                params.add(new BasicNameValuePair("category_id", "" + category_id));
            }
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_CATAGORY, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<Catagory>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<Course> getHotCourseList(Context context, int start, int rows)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getHotCourseList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_HOTCOURSE_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<Course>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param devicetoken
     * @return
     * @throws HttpException
     */
    @Override
    public AccountBindInfo getUserTypeByDeviceid(Context context,
                                                 String devicetoken) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getUserTypeByDeviceid 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("devicetoken", "" + devicetoken));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_USER_TYPE_BY_DEVICEID, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<AccountBindInfo>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @return
     * @throws HttpException
     */
    @Override
    public List<GroupType> getGroupTypeList() throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getGroupTypeList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_GROUP_TAG, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<GroupType>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param category_id
     * @return
     * @throws HttpException
     */
    @Override
    public List<GroupTag> getGroupTagList(String category_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getGroupTagList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            params.add(new BasicNameValuePair("category_id", category_id));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_GROUP_TAG, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<GroupTag>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param groupTempBean
     * @return
     * @throws HttpException
     */
    @Override
    public ChatGroup createGroup(GroupTempBean groupTempBean)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "createGroup 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));
            params.add(new BasicNameValuePair("name", groupTempBean.groupName));
            params.add(new BasicNameValuePair("category_id",
                    groupTempBean.groupType.id + ""));
            params.add(new BasicNameValuePair("tag_id", groupTempBean
                    .getGroupTagString()));
            //TODO:目前小组经纬度没有用到，以后用到需要改这里
            params.add(new BasicNameValuePair("latitude", "0"));
            params.add(new BasicNameValuePair("longitude", "0"));

            String json = httpHelper.httpPostRequestJson(Server.URL_CREATE_GROUP,
                    params);

            json = checkRespone(json);

            ChatGroup chatGroup = gson.fromJson(json, new TypeToken<ChatGroup>() {
            }.getType());
            if (chatGroup != null) {
                chatGroup.setAccountName("" + userData.id);
                chatGroup.setCreateTime(chatGroup.getCreateTime() * 1000);
            }

            return chatGroup;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param type
     * @return
     * @throws HttpException
     */
    public List<GroupMember> getGroupMembers(Context context, String group_id,
                                             String type) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getGroupMembers 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", group_id));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_GET_GROUP_MEMBERS, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<GroupMember>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param type
     * @param group_id
     * @param invited_uid
     * @param remark
     * @return
     * @throws HttpException
     */
    @Override
    public Result invitationMember(Context context, int type, String group_id,
                                   String invited_uid, String remark) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "invitationMember 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", "" + type));
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("invited_uid", "" + invited_uid));
            params.add(new BasicNameValuePair("remark", "" + remark));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_INVITATION_MEMBER, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @return
     * @throws HttpException
     */
    @Override
    public ChatGroup selfJoin(Context context, int group_id)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "selfJoin 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper
                    .httpPostRequestJson(Server.URL_SELF_JOIN, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<ChatGroup>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result selfRefuse(Context context, int group_id)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "selfRefuse 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_SELF_REFUSE,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param apply_uid
     * @return
     * @throws HttpException
     */
    @Override
    public ChatGroup agreeJoin(Context context, int group_id, int apply_uid)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "agreeJoin 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("apply_uid", "" + apply_uid));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_AGREE_JOIN,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<ChatGroup>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param apply_uid
     * @return
     * @throws HttpException
     */
    @Override
    public Result refuseJoin(Context context, int group_id, int apply_uid)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "refuseJoin 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("apply_uid", "" + apply_uid));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_REFUSE_JOIN,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param type
     * @param start
     * @param page_size
     * @param searchName
     * @return
     * @throws HttpException
     */
    @Override
    public List<SimpleUserInfo> getAboutMembers(Context context,
                                                String group_id, int type, int start, int page_size,
                                                String searchName) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getAboutMembers 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("type", "" + type));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + page_size));
            params.add(new BasicNameValuePair("name", "" + searchName));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_GET_ABOUT_MEMBER,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<SimpleUserInfo>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param delete_uid
     * @param type
     * @return
     * @throws HttpException
     */
    @Override
    public Result delGroupMember(Context context, String group_id,
                                 String delete_uid, int type) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "delGroupMember 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("del_uid", "" + delete_uid));
            params.add(new BasicNameValuePair("type", "" + type));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_DEL_GROUP_MEMBER,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param avatar
     * @param gid
     * @return
     * @throws HttpException
     */
    @Override
    public GroupImageUrl changeGroupAvatar(Context context, File avatar,
                                           String gid) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "changeGroupAvatar 用户信息无效,用户已登出");
                return null;
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("uid", userData.id);
            params.put("id", gid);
            params.put("auth_token", userData.auth_token);
            String json = UploadUtil.getInstance().uploadFile(context, avatar,
                    "avatar", Server.URL_CHANGE_GROUP_AVATAR, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<GroupImageUrl>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param id
     * @param name
     * @param info
     * @param category_id
     * @param tag_id
     * @return
     * @throws HttpException
     */
    @Override
    public ChatGroup editGroupInfo(Context context, String id, String name,
                                   String info, String category_id, String tag_id)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "editGroupInfo 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", "" + id));
            if (name != null) {
                params.add(new BasicNameValuePair("name", name));
            }
            if (info != null) {
                params.add(new BasicNameValuePair("info", info));
            }
            params.add(new BasicNameValuePair("category_id", "" + category_id));
            params.add(new BasicNameValuePair("tag_id", "" + tag_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_EDIT_GROUP_INFO,
                    params);

            json = checkRespone(json);

            ChatGroup chatGroup = gson.fromJson(json, new TypeToken<ChatGroup>() {
            }.getType());
            if (chatGroup != null) {
                chatGroup.setAccountName("" + userData.id);
                chatGroup.setCreateTime(chatGroup.getCreateTime() * 1000);
            }

            return chatGroup;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param nickname
     * @return
     * @throws HttpException
     */
    @Override
    public ChatGroup editGroupMyNickname(Context context, String group_id,
                                         String nickname) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "editGroupMyNickname 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("nickname", "" + nickname));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_EDIT_GROUP_MYNICKNAME, params);

            json = checkRespone(json);

            ChatGroup chatGroup = gson.fromJson(json, new TypeToken<ChatGroup>() {
            }.getType());

            return chatGroup;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param category_id
     * @param tag_id
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<ChatGroup> getGroupList(Context context, int category_id,
                                        int tag_id, int start, int rows) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getGroupList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("category_id", "" + category_id));
            params.add(new BasicNameValuePair("tag_id", "" + tag_id));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_GROUP_LIST, params));

            json = checkRespone(json);

            List<ChatGroup> chatGroups = gson.fromJson(json,
                    new TypeToken<List<ChatGroup>>() {
                    }.getType());
            if (chatGroups != null) {
                for (ChatGroup chatGroup : chatGroups) {
                    chatGroup.setCreateTime(chatGroup.getCreateTime() * 1000);
                }
            }
            return chatGroups;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<ChatGroup> getHotGroup(Context context, int start, int rows)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getHotGroup 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_HOT_GROUP, params));

            json = checkRespone(json);

            List<ChatGroup> chatGroups = gson.fromJson(json,
                    new TypeToken<List<ChatGroup>>() {
                    }.getType());
            if (chatGroups != null) {
                for (ChatGroup chatGroup : chatGroups) {
                    chatGroup.setCreateTime(chatGroup.getCreateTime() * 1000);
                }
            }
            return chatGroups;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param keywords
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<ChatGroup> searchGroup(Context context, String keywords,
                                       int start, int rows) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "searchGroup 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("keywords", "" + keywords));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_SEARCH_GROUP,
                    params);

            json = checkRespone(json);

            List<ChatGroup> chatGroups = gson.fromJson(json,
                    new TypeToken<List<ChatGroup>>() {
                    }.getType());
            if (chatGroups != null) {
                for (ChatGroup chatGroup : chatGroups) {
                    chatGroup.setCreateTime(chatGroup.getCreateTime() * 1000);
                }
            }
            return chatGroups;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @return
     * @throws HttpException
     */
    @Override
    public ChatGroup getGroupDetail(Context context, String group_id)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getGroupDetail 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", "" + group_id));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_GROUP_DETAIL, params));

            json = checkRespone(json);

            ChatGroup chatGroup = gson.fromJson(json, new TypeToken<ChatGroup>() {
            }.getType());

            if (chatGroup != null) {
                chatGroup.setCreateTime(chatGroup.getCreateTime() * 1000);
            }
            return chatGroup;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @return
     * @throws HttpException
     */
    @Override
    public List<String> getGroupSeachkey(Context context)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getGroupSeachkey 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_GROUP_KEYWORDS_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public ChatGroupWrapper getMyGroupList(Context context, int start, int rows) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getMyGroupList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_MY_GROUP_LIST, params));

            json = checkRespone(json);

            ChatGroupWrapper chatGroupWrapper = gson.fromJson(json,
                    new TypeToken<ChatGroupWrapper>() {
                    }.getType());

            if (chatGroupWrapper == null) {
                CLog.d(TAG, "getMyGroupList chatGroupWrapper == null");
                return null;
            }

            chatGroupWrapper.new_time = chatGroupWrapper.new_time * 1000;

            if (chatGroupWrapper.lists == null) {
                CLog.d(TAG, "getMyGroupList chatGroupWrapper.lists == null");
                return null;
            }

            for (ChatGroup chatGroup : chatGroupWrapper.lists) {
                chatGroup.setAccountName("" + userData.id);
                chatGroup.setCreateTime(chatGroup.getCreateTime() * 1000);
            }

            return chatGroupWrapper;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<UnprogressedMatter> getUnprogressMatterList(Context context,
                                                            int start, int rows) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getUnprogressMatterList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_UNPROGRESS_MATTER_LIST, params));

            json = checkRespone(json);

            List<UnprogressedMatter> matters = gson.fromJson(json,
                    new TypeToken<List<UnprogressedMatter>>() {
                    }.getType());
            if (matters != null) {
                for (UnprogressedMatter matter : matters) {
                    matter.create_time = matter.create_time * 1000;
                }
            }
            return matters;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param rank
     * @param group_uid
     * @return
     * @throws HttpException
     */
    @Override
    public Result modifyMyGroupRank(Context context, String group_id,
                                    String rank, String group_uid) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "modifyMyGroupRank 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("group_id", group_id));
            params.add(new BasicNameValuePair("group_uid", group_uid));
            params.add(new BasicNameValuePair("rank", rank));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_GROUP_MODIFY_RANK, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param admin_uid
     * @return
     * @throws HttpException
     */
    @Override
    public Result deleteGroupManager(Context context, String group_id,
                                     String admin_uid) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "deleteGroupManager 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("group_id", group_id));
            params.add(new BasicNameValuePair("admin_uid", admin_uid));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_GROUP_DELETE_MANAGER, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param group_id
     * @param admin_uid
     * @return
     * @throws HttpException
     */
    @Override
    public Result addGroupManager(Context context, String group_id,
                                  String admin_uid) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "addGroupManager 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("group_id", group_id));
            params.add(new BasicNameValuePair("admin_uid", admin_uid));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_GROUP_ADD_MANAGER, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @return
     * @throws HttpException
     */
    @Override
    public Result checkGroupCreate(Context context)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "checkGroupCreate 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GROUP_CREATE_CHECK, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param groupId
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<GroupWork> getGroupWorkList(String groupId, int start, int rows)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getGroupWorkList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("group_id", groupId));
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_GROUP_TASK_LIST, params));

            json = checkRespone(json);

            List<GroupWork> groupWorks = gson.fromJson(json,
                    new TypeToken<List<GroupWork>>() {
                    }.getType());

            for (GroupWork work : groupWorks) {
                work.create_time = work.create_time * 1000;
            }

            return groupWorks;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param group_id
     * @param course_list
     * @param remark
     * @return
     * @throws HttpException
     */
    @Override
    public int groupAddTask(String group_id, String course_list, String remark)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "groupAddTask 用户信息无效,用户已登出");
                return 0;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("group_id", group_id));
            params.add(new BasicNameValuePair("course_list", course_list));
            params.add(new BasicNameValuePair("remark", remark));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_GROUP_ADD_TASK,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Integer>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param wait_id
     * @return
     * @throws HttpException
     */
    @Override
    public Result readMatter(String wait_id) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "readMatter 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("wait_id", wait_id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_GROUP_READ_MATTER, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @return
     * @throws HttpException
     */
    //
    @Override
    public Result checkGroupIn(Context context)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "checkGroupIn 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GROUP_JOIN_CHECK, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param course_id
     * @return
     */
    public String getErrorCorrectionUrl(long course_id) {
        if (!isValidUser(userData)) {
            CLog.d(TAG, "getErrorCorrectionUrl 用户信息无效,用户已登出");
            return null;
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("course_id", String
                .valueOf(course_id)));
        params.add(new BasicNameValuePair("uid", userData.id));
        params.add(new BasicNameValuePair("auth_token", userData.auth_token));
        return Server.getUrl(Server.URL_COURSE_ERROR_CORRECTION, params);
    }

    /**
     * @param course_time
     * @param album_time
     * @return
     * @throws HttpException
     */
    @Override
    public SearchCourseOrAlbum getAllCourseName(String course_time,
                                                String album_time) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getAllCourseName 用户信息无效,用户已登出");
                return null;
            }

            if (course_time == null) {
                CLog.d(TAG, "SearchCourseOrAlbum course_time == null");
                return null;
            }

            if (album_time == null) {
                CLog.d(TAG, "SearchCourseOrAlbum album_time == null");
                return null;
            }

            if (!isValidUser(userData)) {
                CLog.d(TAG, "SearchCourseOrAlbum 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_time", course_time));
            params.add(new BasicNameValuePair("album_time", album_time));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ALL_COURSE_NAME, params));

            json = checkRespone(json);

            SearchCourseOrAlbum searchCourseOrAlbum = gson.fromJson(json,
                    new TypeToken<SearchCourseOrAlbum>() {
                    }.getType());

            return searchCourseOrAlbum;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @return
     * @throws HttpException
     */
    @Override
    public City getRankCity() throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getRankCity 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_CUR_RANK_CITY, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<City>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @return
     * @throws HttpException
     */
    @Override
    public List<City> getCities() throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "getCities 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_CITY_LIST, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<List<City>>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param cityId
     * @return
     * @throws HttpException
     */
    @Override
    public Result setCity(int cityId) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "setCity 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("area_id", String.valueOf(cityId)));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_SET_RANK_CITY,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param lon
     * @param lat
     * @return
     * @throws HttpException
     */
    @Override
    public Result setLonLatLocation(String lon, String lat)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "setLonLatLocation 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("lon", lon));
            params.add(new BasicNameValuePair("lat", lat));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_SET_RANK_CITY,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param code
     * @return
     * @throws HttpException
     */
    @Override
    public WechatAuthInfo getWechatAuthInfo(String code)
            throws HttpException {
        try {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("appid", Constants.WECHAT_APP_KEY));
            params.add(new BasicNameValuePair("secret", Constants.WECHAT_APP_SECRET));
            params.add(new BasicNameValuePair("code", code));
            params.add(new BasicNameValuePair("grant_type", "authorization_code"));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_WECHAT_GET_AUTH_TOKEN, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<WechatAuthInfo>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param access_token
     * @param openId
     * @return
     * @throws HttpException
     */
    @Override
    public WeChatUserInfo getWeChatUserInfo(String access_token, String openId)
            throws HttpException {
        try {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("access_token", access_token));
            params.add(new BasicNameValuePair("openid", openId));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_WECHAT_GET_USER_INFO, params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<WeChatUserInfo>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param collectId
     * @return
     * @throws HttpException
     */
    @Override
    public Result topCollect(Context context, String collectId)
            throws HttpException {
        //TODO:现在视频收藏还没用到置顶功能
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "topCollect 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("collect_id", collectId));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_TOP_COLLECT,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param showId
     * @return
     * @throws HttpException
     */
    @Override
    public Result topMyDubbingArt(Context context, String showId)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "topMyDubbingArt 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", showId));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_TOP_MY_DUBART,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }

    }

    /**
     * @param context
     * @param showId
     * @return
     * @throws HttpException
     */
    @Override
    public Result cancelTopMyDubbingArt(Context context, String showId)
            throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "cancelTopMyDubbingArt 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("show_id", showId));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_CANCEL_TOP_MY_DUBART, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id
     * @param type
     * @return
     * @throws HttpException
     */
    @Override
    public Result cancelTopMyMember(Context context, String member_id,
                                    String type) throws HttpException {
        try {

            if (!isValidUser(userData)) {
                CLog.d(TAG, "cancelTopMyMember 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("member_id", member_id));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(
                    Server.URL_CANCEL_TOP_MY_MEMBER, params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param member_id 目标用户id
     * @param type      粉丝 fans ,关注 follow
     * @return
     * @throws HttpException
     */
    @Override
    public Result topMyMember(Context context, String member_id, String type)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "topMyMember 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("member_id", member_id));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_TOP_MY_MEMBER,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getLatestDubbingListAll(Context context,
                                                    int start, int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getLatestDubbingListAll 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ALL_DUBBINGS, params));

            json = checkRespone(json);

            List<DubbingArt> dubbingList = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            return dubbingList;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getLatestDubbingListAttention(Context context,
                                                          int start, int rows) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getLatestDubbingListAttention 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_ATTENTION_DUBBINGS, params));

            if (UserAuthority.getInstance().getAuthority(UserProxy.getInstance().getUser()) == UserAuthority.AUTHORITY_VISITOR_USER) {
                throw (new HttpException(context.getString(R.string.visitor_toast), -1));
            } else {
                json = checkRespone(json);
                List<DubbingArt> dubbingList = gson.fromJson(json,
                        new TypeToken<List<DubbingArt>>() {
                        }.getType());
                return dubbingList;
            }
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @param lon
     * @param lat
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getLatestDubbingListNear(Context context,
                                                     int start, int rows, String lon, String lat)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getLatestDubbingListNear 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("lon", lon));
            params.add(new BasicNameValuePair("lat", lat));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_NEAR_DUBBINGS, params));

            json = checkRespone(json);

            List<DubbingArt> dubbingList = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            return dubbingList;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @param group_id
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getDubbingListGroup(Context context, int start,
                                                int rows, String group_id) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getDubbingListGroup 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("group_id", group_id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_GROUP_DUBBINGS, params));

            json = checkRespone(json);

            List<DubbingArt> dubbingList = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            return dubbingList;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param start
     * @param rows
     * @param course_id
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getBestDubbingListCourse(Context context,
                                                     int start, int rows, String course_id)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getBestDubbingListCourse 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start", String.valueOf(start)));
            params.add(new BasicNameValuePair("rows", String.valueOf(rows)));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("course_id", course_id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_BEST_DUBBINGS, params));

            json = checkRespone(json);

            List<DubbingArt> dubbingList = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            return dubbingList;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param matterid
     * @return
     * @throws HttpException
     */
    @Override
    public Result deleteUnprogressMatter(Context context, String matterid)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "deleteUnprogressMatter 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", matterid));

            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_DELETE_UNPROGRESS_MATTER,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param context
     * @param key
     * @return
     */
    @Override
    public PrivateVideoInfo getPrivateVideoUrl(Context context, String key) throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getPrivateVideoUrl 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("key", key));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpPostRequestJson(Server.URL_GET_PRIVATE_VIDE_INFO,
                    params);

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<PrivateVideoInfo>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    /**
     * @param
     * @param
     * @return
     * @throws HttpException
     */
    //@Override
    public RefreshToken refreshToken(Context context)
            throws HttpException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("refresh_token", refreshToken));

            String json = httpHelper.httpPostRequestJson(Server.URL_REFRESH_TOKEN, params);

            json = checkRespone(json);

            RefreshToken refreshToken = gson.fromJson(json, new TypeToken<RefreshToken>() {
            }.getType());

            updataToken(context, refreshToken);

            return refreshToken;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    private void updataToken(Context context, RefreshToken refreshToken) {
        if (refreshToken == null) {
            return;
        }

        User user = UserProxy.getInstance().getUser();
        if (user == null) {
            return;
        }

        user.refresh_token = refreshToken.refresh_token;
        user.auth_token = refreshToken.auth_token;

        saveUser(context, user);
    }

    /**
     * 保存用户数数据到全局内存和preshare
     *
     * @param context
     * @param -
     */
    private void saveUser(Context context, User user) {
        if (user == null) {
            CLog.d(TAG, "saveUser user == null");
            return;
        }

        if (context == null) {
            CLog.d(TAG, "saveUser context == null");
            return;
        }

        refreshNetParams(user);

        UserProxy.getInstance().setUser(user);
    }

    public void refreshNetParams(User user) {
        userData.id = String.valueOf(user.uid);
        userData.auth_token = user.auth_token;
        refreshToken = user.refresh_token;
    }

    /**
     * 清除application中user信息和preshare中保存的用户信息
     */
    public void clearUserData(Context context) {
        if (context == null) {
            CLog.d(TAG, "clearUserData context == null");
            return;
        }

        UserProxy.getInstance().setUser(User.getVisitor(context));
        initUserData();
    }


    /**
     * 获取用户Id
     *
     * @return
     */
    public int getUid() {
        return StringUtil.putInt(userData.id);
    }

    /**
     * @param json
     * @return
     * @throws HttpException
     */
    private String checkRespone(String json) throws HttpException {
        if (null == json || json.equals("")) {
            CLog.d(TAG, "checkRespone null == json || json.equals(\"\")");
            throw new HttpException("repose null", HttpException.HTTP_NET_RESPONE_NULL);
        }

        if (!json.contains("status")) {
            CLog.d(TAG, "handleStatusCode json not status");
            //throw new HttpException("checkRespone not status", HttpException.HTTP_NET_FORMAT_WRONG);
            return json;
        }

        handleStatusCode(json);

        if (!json.contains("data")) {
            CLog.d(TAG, "handleStatusCode json not contains(\"data\")");
            return json;
        }

        String data;
        try {
            data = new JSONObject(json).getString("data");
        } catch (JSONException e) {
            CLog.d(TAG, "checkRespone repose format wrong");
            throw new HttpException("repose format wrong", HttpException.HTTP_NET_FORMAT_WRONG);
        }

        return data;
    }

    /**
     * 检测服务器返回的status
     *
     * @param result
     * @throws HttpException
     */
    private void handleStatusCode(String result) throws HttpException {
        if (TextUtils.isEmpty(result)) {
            throw new HttpException("repose null", HttpException.HTTP_NET_RESPONE_NULL);
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            if (jsonObject == null) {
                throw new HttpException("repose format wrong", HttpException.HTTP_NET_FORMAT_WRONG);
            }
        } catch (JSONException e) {
            throw new HttpException("repose format wrong", HttpException.HTTP_NET_FORMAT_WRONG);
        }

        String status = JsonUtil.getStringFromJson(jsonObject, "status");
        if (status == null || status.isEmpty()) {
            return;
        }

        if (status.trim().equals("1")) {
            return;
        } else {
            String errorMsg = JsonUtil.getStringFromJson(jsonObject, "msg");
            if (status.trim().equals("0")) {
                throw new HttpException(errorMsg, HttpException.HTTP_NET_SERVER_ERROR);
            } else if (status.contentEquals("403")) {
                if (User.isGuiderUser(UserProxy.getInstance().getUser())) {
                    return;
                }
                getRefreshToken();
                if (isAuthTokenAvailable) {
                    CLog.v(TAG, "error_try_again" + System.currentTimeMillis());
                    throw new HttpException(IShowDubbingApplication.getInstance().getString(R.string.error_try_again), HttpException.HTTP_TRY_AGAIN);
                } else {
                    throw new HttpException(null, HttpException.HTTP_NET_REPEAT_REQUEST);
                }
            }
        }
    }

    private void getRefreshToken() {
        isAuthTokenAvailable = false;
        if (refreshTokenThread == null) {
            threadId = 0;
            refreshTokenThread = new RefreshTokenThread() {
                @Override
                public void OnRefreshSuccess(RefreshToken refreshT) {
                    refreshToken = refreshT.refresh_token;
                    userData.auth_token = refreshT.auth_token;
                    isAuthTokenAvailable = true;

                    User user = UserProxy.getInstance().getUser();
                    user.auth_token = userData.auth_token;
                    user.refresh_token = refreshToken;
                    saveUser(IShowDubbingApplication.getInstance(), user);
                    CLog.v(TAG, "notifyWaitThread" + System.currentTimeMillis());
                    notifyWaitThread();
                    refreshTokenThread = null;
                }

                @Override
                public void OnRefreshError(int errorCode, String msg) {
                    IShowDubbingApplication.getInstance().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            IShowDubbingApplication.getInstance().showLoginDialog(R.string.auth_empire);
                            isAuthTokenAvailable = false;
                            threadId = 0;
                            refreshTokenThread = null;
                            notifyWaitThread();
                        }
                    });
                }
            };
            refreshTokenThread.start();
        }
        addLock();
    }

    private void addLock() {
        Object flag = new Object();
        threadLocks.add(flag);
        try {
            synchronized (flag) {
                flag.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void notifyWaitThread() {
        for (Object flag : threadLocks) {
            synchronized (flag) {
                flag.notify();
            }
        }
        threadLocks.clear();
    }

    /**
     * @param context
     * @param type
     * @param value
     */
    public void saveBindInfo(Context context, int type, int value) {
        AccountBindInfo accountBindInfo = (AccountBindInfo) OtherUtils
                .getObjectFromJsonSharePrefs(context,
                        Constants.FILE_JSON_CACHE,
                        Constants.KEY_ACCOUNT_BIND_INFO,
                        new TypeToken<AccountBindInfo>() {
                        }.getType());
        if (accountBindInfo == null) {
            return;
        }

        switch (type) {
            case 0:
                accountBindInfo.is_mobile = value;
                break;
            case 1:
                accountBindInfo.is_qq = value;
                break;
            case 2:
                accountBindInfo.is_weibo = value;
                break;
            case 3:
                accountBindInfo.is_wechat = value;
                break;
            default:
                accountBindInfo.is_guest = value;
                break;
        }
        accountBindInfo.mtype = 1;
        OtherUtils.switchObjectToJsonAndSaveToSharePrefs(context,
                Constants.FILE_JSON_CACHE, Constants.KEY_ACCOUNT_BIND_INFO,
                accountBindInfo);
    }

    /**
     * 验证用户数据结构
     *
     * @param userData
     * @return
     */
    private boolean isValidUser(UserData userData) {
        if (userData == null) {
            CLog.d(TAG, "isValidUser userData == null");
            return false;
        }

        if (userData.id == null || userData.id.isEmpty()) {
            CLog.d(TAG, "isValidUser userData.id == null || userData.id.isEmpty()");
            return false;
        }

        if (userData.auth_token == null || userData.auth_token.isEmpty()) {
            CLog.d(TAG, "isValidUser userData.auth_token == null ||  userData.auth_token.isEmpty()");
            return false;
        }

        return true;
    }

    /**
     * @param context
     * @param ishow
     * @param time_type
     * @param ishow_type
     * @param course_id
     * @param start
     * @param rows
     * @return
     * @throws HttpException
     */
    @Override
    public List<DubbingArt> getIshowRankList(Context context, int ishow,
                                             int time_type, int ishow_type, long course_id, int start, int rows)
            throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getRankList 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ishow", "" + ishow));
            params.add(new BasicNameValuePair("time_type", "" + time_type));
            params.add(new BasicNameValuePair("ishow_type", "" + ishow_type));
            if (course_id > 0) {
                params.add(new BasicNameValuePair("course_id", "" + course_id));
            }
            params.add(new BasicNameValuePair("start", "" + start));
            params.add(new BasicNameValuePair("rows", "" + rows));
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(
                    Server.URL_GET_RANK_LIST_ISHOW, params));

            json = checkRespone(json);

            List<DubbingArt> dubbingList = gson.fromJson(json,
                    new TypeToken<List<DubbingArt>>() {
                    }.getType());

            if (dubbingList != null) {
                for (DubbingArt dubbingArt : dubbingList) {
                    dubbingArt.school = dubbingArt.school + "@"
                            + dubbingArt.school_str;
                }
            }

            return dubbingList;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    @Override
    public WonderAct getActUrl() throws HttpException {
        try {
            if (!isValidUser(userData)) {
                CLog.d(TAG, "getPrivateVideoUrl 用户信息无效,用户已登出");
                return null;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userData.id));
            params.add(new BasicNameValuePair("auth_token", userData.auth_token));

            String json = httpHelper.httpGetRequestJson(Server.getUrl(Server.URL_GET_ACT_INFO,
                    params));

            json = checkRespone(json);

            return gson.fromJson(json, new TypeToken<WonderAct>() {
            }.getType());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }
}
