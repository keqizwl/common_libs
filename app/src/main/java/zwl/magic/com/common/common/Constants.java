package zwl.magic.com.common.common;

import android.os.Environment;

import java.io.File;

/**
 * 常量的定义类1
 */
public class Constants {
    /* 应用基础的常量 */
    public static final String PACKAGE_NAME = "com.ishowedu.child.peiyin";
    public static final String INTENT_ACTION_HEAD = PACKAGE_NAME
            + ".intent.action";
    public static final String APPLICATION_NAME = "iShowdubbing_child";
    public static final String APP_CACHE_BASE_DIR = Environment
            .getExternalStorageDirectory() + "/" + APPLICATION_NAME;
    public static final String APP_IMAGE_CACHE_DIR = APP_CACHE_BASE_DIR
            + "/image";
    public static final String APP_COURSE_CACHE_DIR = APP_CACHE_BASE_DIR
            + "/courses";
    public static final String APP_COURSE_DOWNLOAD_DIR = APP_CACHE_BASE_DIR
            + "/download";
    public static final String APP_COURSE_DOWNLOAD_VIDEO_DIR = APP_CACHE_BASE_DIR
            + "/video";
    public static final String APP_DUB_ART_DIR = APP_CACHE_BASE_DIR + "/dubart";

	/* 第三方使用常量 */
    /**
     * 腾讯QQ的APPID
     */
    public static final String TENCENT_APP_KEY = "1104670989";
    /**
     * weixin的APPID
     */
    public static final String WECHAT_APP_KEY = "wxa12880c55537ecb0";
    /**
     * weixin的secret
     */
    public static final String WECHAT_APP_SECRET = "0f7f7e8d7ef6aebcddc07137bc73a695";
    /**
     * 新浪微博的APPID
     */
    public static final String SINA_APP_KEY = "3797555578";
    /**
     * 新浪微博的鉴权地址
     */
    public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    /**
     * 应用申请的高级权限
     */
    public static final String SINA_SCOPE = "all";

    /* 文件名常量 */
    public static final String FILE_VERSION = "file_version";
    public static final String FILE_JSON_CACHE = "file_json_cache";
    public static final String FILE_SETTING = "file_setting";
    public static final String FILE_TEMP = "file_temp";
    public static final String FILE_COVER_IMG_CACHE = Constants.APP_IMAGE_CACHE_DIR
            + File.separator + "tmpCourseCover.jpg";

    /* 关键字常量 */
    public static final String KEY_USER = "key_user";
    public static final String KEY_IS_FIRST = "key_is_first";
    public static final String KEY_AUTO_PLAY_IN_WIFI = "key_auto_play_in_wifi";
    public static final String KEY_COMMENT_NOTIFY = "key_comment_notify";
    public static final String KEY_PRIVATE_TALK_NOTIFY = "key_private_talk_notify";
    public static final String KEY_FIRST_ENTER_ME_SPACE = "key_first_enter_me_space";
    public static final String KEY_DUB_RESULT = "key_dub_result";
    public static final String KEY_IS_LOCAL = "key_is_local";
    public static final String KEY_GROUP_TASK = "key_group_task";
    public static final String KEY_NUM_EXTRA = "key_num_extra";
    public static final String KEY_NUM_VALUE = "key_num_value";
    public static final String KEY_ATTENTION_NUM_ADD = "key_attention_num_add";
    public static final String KEY_PHOTO_ADD = "key_photo_add";
    public static final String KEY_MY_DUB_ADD = "key_my_dub_add";
    public static final String KEY_CACHE_COURSE_ADD = "key_cache_course_add";
    public static final String KEY_COLLECT_COURSE_ADD = "key_collect_course_add";
    public static final String KEY_NEW_WORD_ADD = "key_new_word_add";
    public static final String KEY_IS_ENTER_DUB_ACTIVITY = "key_is_enter_dub_activity";
    public static final String KEY_LAST_USER_ID = "key_last_user_id";
    public static final String KEY_UNPROGRESS_MATTER = "key_unprogress_matter";
    public static final String KEY_CHAT_GROUP = "key_chat_group";
    public static final String KEY_UNPROGRESS_MATTERS_INFO = "key_unprogress_matters_info";
    public static final String KEY_IS_DUB_CHANGED = "key_is_dub_changed";
    public static final String KEY_ACCOUNT_BIND_INFO = "key_account_bind_info";
    public static final String KEY_MOBILE_NUM = "key_mobile_num";
    public static final String KEY_HOME_ENTITY = "key_home_entity";
    public static final String KEY_LATEST_DUBBING_COURSE = "key_latest_dubbing_course";
    public static final String KEY_UNFINISHED_COURSE = "key_unfinished_course";
    public static final String KEY_GROUPTYPES = "key_grouptypes";
    public static final String KEY_GROUP_ID = "key_group_id";
    public static final String KEY_WEB_TO_APP_URI = "key_web_to_app_uri";
    public static final String KEY_HOME_ENTITY_RAW = "home_entity_raw";

    /**
     * Broadcastreceiver Action
     */
    public static final String BROADCAST_NAME_MODIFY_USERDATA = INTENT_ACTION_HEAD
            + ".MODIFY_USERDATA";
    public static final String BROADCAST_NAME_UPLOAD_ART_SUCCESS = INTENT_ACTION_HEAD
            + ".UPLOAD_ART_SUCCESS";
    public static final String BROADCAST_CHANGE_USER_DATA = INTENT_ACTION_HEAD
            + ".USER_DATA";
    public static final String BROADCAST_TIE_UP_SUCCESS = INTENT_ACTION_HEAD
            + ".TIE_UP_SUCCESS";
    public static final String BROADCAST_NUM_CHANGE = INTENT_ACTION_HEAD
            + ".NUM_CHANGE";
    public static final String BROADCAST_ADD_GROUP_TASK_SUCCESS = INTENT_ACTION_HEAD
            + ".ADD_GROUP_TASK_SUCCESS";
    public static final String BROADCAST_NEW_GROUP_CREATE = INTENT_ACTION_HEAD
            + ".NEW_GROUP_CREATE";
    public static final String BROADCAST_QUIT_GROUP = INTENT_ACTION_HEAD
            + ".QUIT_GROUP";
    public static final String BROADCAST_GROUP_CREATE_SUCCESS = INTENT_ACTION_HEAD
            + ".CREATE_GROUP_SUCCESS";
    public static final String BROADCAST_GROUP_CHANGE_SUCCESS = INTENT_ACTION_HEAD
            + ".CHANGE_GROUP_SUCCESS";
    public static final String BROADCAST_GROUP_NICKNAME_CHANGE_SUCCESS = INTENT_ACTION_HEAD
            + ".CHANGE_NICKNAME_GROUP_SUCCESS";
    public static final String BROADCAST_GROUP_MEMBER_CHANGE_SUCCESS = INTENT_ACTION_HEAD
            + ".CHANGE_MEMBER_GROUP_SUCCESS";
    public static final String BROADCAST_GROUP_MANAGER_CHANGE_SUCCESS = INTENT_ACTION_HEAD
            + ".CHANGE_MANAGER_GROUP_SUCCESS";
    public static final String BROADCAST_CLEAR_CHAT_MESSAGE = INTENT_ACTION_HEAD
            + ".CLEAR_CHAT_MESESAGE";
    public static final String BROADCAST_OTHER_LOGINED = INTENT_ACTION_HEAD
            + ".OTHER_LOGINED";
    public static final String BROADCAST_CLEAR_CACHE = INTENT_ACTION_HEAD
            + ".CLEAR_CACHE";
    public static final String BROADCAST_GET_WECHAT_USER_INFO = INTENT_ACTION_HEAD
            + ".GET_WECHAT_USER_INFO";
    public static final String BROADCAST_GROUP_MANAGER_OTHER_CHANGE_SUCCESS = "broadcast_change_other_manager_group_success";
    public static final String BROADCAST_GROUP_MANAGER_ADD_SUCCESS = "broadcast_add_manager_group_success";
    public static final String IS_TIP_BING_USER = "is_app_update";
    public static final String VERSION_CODE = "versionCode";
    public static final String BROADCAST_CANCEL_WECHAT_AUTHOR = INTENT_ACTION_HEAD
            + ".CANCEL_WECHAT_AUTHOR";
    public static final java.lang.String BROADCAST_CHANGE_USER_ACCOUNT_CHANGE = INTENT_ACTION_HEAD
            + ".USER_ACCOUNT_CHANGE";
}