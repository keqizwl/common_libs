package zwl.magic.com.common.model.net.request;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.util.List;

public class Server {

    // 现网
    public static final String URL_SERVER_BASE = "http://child.qupeiyin.cn";

    // 测试
//	public static final String URL_SERVER_BASE = "http://112.124.25.25:8082";


    public static String getUrl(String url, List<NameValuePair> params) {
        if (url.contains("?")) {
            return url + "&" + URLEncodedUtils.format(params, "utf-8");
        } else {
            return url + "?" + URLEncodedUtils.format(params, "utf-8");
        }
    }

    public static final String URL_CHANGE_AVATAR = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=change_avatar";

    public static final String URL_GET_VISITOR_NEW = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=guest_login";

    public static final String URL_REGISTE = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=register";

    public static final String URL_RESET_PWD = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=reset_password";

    public static final String URL_LOGIN = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=login";

    public static final String URL_QUPEIYIN_LOGIN = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=peiyin_login";

    public static final String URL_LOGOUT = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=logout";

    public static final String URL_MODIFY_USER_INFO = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=edit_member";

    public static final String URL_GET_FAV_COURSE = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=my_courses";

    public static final String URL_FEEDBACK = URL_SERVER_BASE
            + "/index.php?m=api&c=public&a=feedback";

    public static final String URL_SUGGEST_VIDEO = URL_SERVER_BASE
            + "/index.php?m=api&c=public&a=recomand";
    // 获取非即时聊天消息列表
    public static final String URL_GET_MESSAGE_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=messages&a=msglist";

    // 获取所有人聊天记录列表
    public static final String URL_GET_ALL_CHAT_MESSAGE_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=messages&a=history_msg";

    public static final String URL_GET_UNREAD_MESSAGE_COUNT = URL_SERVER_BASE
            + "/index.php?m=api&c=messages&a=index";

    // 收藏
    public static final String URL_COLLECT = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=collect";

    public static final String URL_GET_CODE = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=mobile_code";

    public static final String URL_CHANGE_PWD = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=change_password";

    public static final String URL_GET_VERSION = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=get_version";

    public static final String URL_ADD_PRAISE = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=support";

    public static final String URL_DELETE_DUBBING = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=delete";

    public static final String URL_CANCEL_COLLECT = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=cancel_collect";

    public static final String URL_DELETE_COLLECT = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=delete_collect";

    public static final String URL_CHECK_COLLECT = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=check_collect";

    public static final String URL_CHECK_PRAISE = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=check_support";

    public static final String URL_ADD_COMMENT = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=comment_add";

    public static final String URL_COURSE_PRIVILEGE = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=course_privilege";

    public static final String URL_UPLOAD_FILEINFO = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=add";

    public static final String URL_GET_ALL_DUBBINGS = URL_SERVER_BASE
            + "/index.php?m=api&c=StudyShow&a=show_list";

    public static final String URL_GET_ATTENTION_DUBBINGS = URL_SERVER_BASE
            + "/index.php?m=api&c=StudyShow&a=follow_show";

    public static final String URL_GET_NEAR_DUBBINGS = URL_SERVER_BASE
            + "/index.php?m=api&c=StudyShow&a=near_show";

    public static final String URL_GET_GROUP_DUBBINGS = URL_SERVER_BASE
            + "/index.php?m=api&c=StudyShow&a=group_show";

    public static final String URL_GET_BEST_DUBBINGS = URL_SERVER_BASE
            + "/index.php?m=api&c=StudyShow&a=course_show";

    public static final String URL_GET_RECOMDUBBINGS = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=best_show";

    public static final String URL_ADD_VIEW = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=view";

    public static final String URL_GET_BG_MUSIC = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=get_bg";

    public static final String URL_GET_RELATED_COURSES = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=get_related_course";

    public static final String URL_SEARCH_COURSES = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=search";

    public static final String URL_SEARCH_USERS = URL_SERVER_BASE
            + "/index.php?m=api&c=search&a=search_user";

    public static final String URL_SEARCH_HOTUSERS = URL_SERVER_BASE
            + "/index.php?m=api&c=search&a=hot_user";

    public static final String URL_GET_RANK_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=top&a=shownews_top";

    public static final String URL_GET_RANK_LIST_ISHOW = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=show_top";

    public static final String URL_GET_ALBUM_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=album&a=get_album_list";

    public static final String URL_GET_ALBUM_DETAIL = URL_SERVER_BASE
            + "/index.php?m=api&c=album&a=detail";

    public static final String URL_GET_INDEX = URL_SERVER_BASE
            + "/index.php?m=api&c=public&a=index";

    public static final String URL_GET_COURSE_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=get_course_list";

    public static final String URL_GET_HOTCOURSE_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=hot_course";

    public static final String URL_GET_COURSE_DETAIL = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=detail_new";

    public static final String URL_GET_ISHOW_HOME_PAGE = URL_SERVER_BASE
            + "/index.php?m=api&c=public&a=ishow_index";

    public static final String URL_DELETE_COMMENT = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=comment_delete";

    public static final String URL_ADD_DUB_VIEW = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=view";

    public static final String URL_GET_SPACE_INFO = URL_SERVER_BASE
            + "/index.php?m=api&c=member";

    public static final String URL_GET_COMMENT_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=comment_list";

    public static final String URL_REPLY_COMMENT = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=comment_reply";

    public static final String URL_ADD_ATTENTION = URL_SERVER_BASE
            + "/index.php?m=api&c=fans&a=add";

    public static final String URL_DEL_ATTENTION = URL_SERVER_BASE
            + "/index.php?m=api&c=fans&a=delete";

    public static final String URL_GET_ATTENTION = URL_SERVER_BASE
            + "/index.php?m=api&c=member&a=follows";

    public static final String URL_GET_FANS = URL_SERVER_BASE
            + "/index.php?m=api&c=member&a=fans";

    public static final String URL_ADD_WORDS = URL_SERVER_BASE
            + "/index.php?m=api&c=guestbook&a=send";

    public static final String URL_REPLY_WORDS = URL_SERVER_BASE
            + "/index.php?m=api&c=guestbook&a=reply";

    public static final String URL_GET_WORDS = URL_SERVER_BASE
            + "/index.php?m=api&c=member&a=guestbook";

    public static final String URL_UPLOAD_PHOTO = URL_SERVER_BASE
            + "/index.php?m=api&c=photo&a=add";

    public static final String URL_DEL_PHOTO = URL_SERVER_BASE
            + "/index.php?m=api&c=photo&a=delete";

    public static final String URL_SET_AVATAR_OR_COVER = URL_SERVER_BASE
            + "/index.php?m=api&c=photo&a=setphoto";

    public static final String URL_GET_PHOTO_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=member&a=photo";

    public static final String URL_GET_VISITOR_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=member&a=visitor";

    public static final String URL_GET_OHTER_DUB_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=member&a=show_list";

    public static final String URL_GET_HOT_SEARCH = URL_SERVER_BASE
            + "/index.php?m=api&c=public&a=hotSearch";

    public static final String URL_GET_COURSE_ALBUM = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=course_album";

    public static final String URL_GET_COMMENTS_LAYER_V313 = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=comment_list_v313";

    public static final String URL_GET_COMMENTS_SPREAD = URL_SERVER_BASE
            + "/index.php?m=api&c=member&a=spread";

    public static final String URL_GET_SPREAD_TOP = URL_SERVER_BASE
            + "/index.php?m=api&c=top&a=spread_top";

    public static final String URL_GET_NEWSHOW_TOP = URL_SERVER_BASE
            + "/index.php?m=api&c=top&a=newshow_top";

    public static final String URL_GET_NEWFANS_TOP = URL_SERVER_BASE
            + "/index.php?m=api&c=top&a=newfans_top";

    public static final String URL_GET_ART_DETAIL = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=detail";

    public static final String URL_GET_RELATIVE_COURSE = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=related_course";

    public static final String URL_SEARCH_COURSE_OR_ALBUM = URL_SERVER_BASE
            + "/index.php?m=api&c=search&a=course_album";

    public static final String URL_SEND_DEVICE_ID = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=user_pushinfo";

    public static final String URL_THIRD_LOGIN = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=third_login";

    public static final String URL_TIEUP_MOBILE = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=tieup_mobile";

    public static final String URL_TIEUP_THIRDLOGIN = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=tieup_thirdLogin";

    public static final String URL_GET_USER_TYPE = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=usertype";

    public static final String URL_SAVE_LOG = URL_SERVER_BASE
            + "/index.php?m=api&c=Errorlog";

    public static final String URL_GET_DUBBING_RANK = URL_SERVER_BASE
            + "/index.php?m=api&c=top&a=shownews_top";

    public static final String URL_GET_ALBUM_COURSE_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=album&a=course_list";

    public static String getShowShareUrl(String id) {
        return URL_SERVER_BASE + "/index.php?m=home&c=show&a=share" + "&id="
                + id;
    }

    public static String getShowUrl(String fileName) {
        return "http://cdn.qupeiyin.cn/" + fileName;

    }

    public static final String URL_TIEUP_GUEST = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=tieup_guest";

    public static final String URL_GET_ALL_SCHOOL = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=get_school";

    public static final String URL_SHOW_RECOMMEND = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=show_recommend";

    public static final String URL_GET_WORD_BOOK_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=words&a=index";

    public static final String URL_ADD_WORD_BOOK = URL_SERVER_BASE
            + "/index.php?m=api&c=words&a=add";

    public static final String URL_DEL_WORD_BOOK = URL_SERVER_BASE
            + "/index.php?m=api&c=words&a=delete";

    public static final String URL_GET_SYSTEM_MESSAGE_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=messages&a=msg_system";

    public static final String URL_REPLY_SYSTEM = URL_SERVER_BASE
            + "/index.php?m=api&c=messages&a=send_system";

    public static final String URL_GET_USER_DATA = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=member_data";

    public static final String URL_DELETE_MESSAGE = URL_SERVER_BASE
            + "/index.php?m=api&c=messages&a=delete";

    public static final String URL_RESERVER_CLASS = URL_SERVER_BASE
            + "/index.php?m=api&c=lessons&a=reserve";

    public static final String URL_CANCEL_RESERVER_CLASS = URL_SERVER_BASE
            + "/index.php?m=api&c=lessons&a=cancel_reserve";

    public static final String URL_DELETE_USER_MESSAGE = URL_SERVER_BASE
            + "/index.php?m=api&c=messages&a=delete_gotye";

    public static final String URL_CHANGE_COVER = URL_SERVER_BASE
            + "/index.php?m=api&c=photo&a=change_cover";

    public static final String URL_GET_CATAGORY = URL_SERVER_BASE
            + "/index.php?m=api&c=basic&a=cates";

    public static final String URL_UNBIND_MOBILE = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=untieup_mobile";

    public static final String URL_UNBIND_OTHERS = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=untieup_thirdLogin";

    public static final String URL_GET_USER_TYPE_BY_DEVICEID = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=user_devicetoken";

    public static final String URL_GET_GROUP_TAG = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=tag";

    public static final String URL_CREATE_GROUP = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=add";

    public static final String URL_GET_GROUP_MEMBERS = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=group_member_list";

    public static final String URL_INVITATION_MEMBER = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=invitation_member";

    public static final String URL_SELF_JOIN = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=self_join";

    public static final String URL_SELF_REFUSE = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=self_refuse";

    public static final String URL_AGREE_JOIN = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=agree_join";

    public static final String URL_REFUSE_JOIN = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=refuse_join";

    public static final String URL_GET_ABOUT_MEMBER = URL_SERVER_BASE
            + "/index.php?m=api&c=member&a=get_about_member";

    public static final String URL_DEL_GROUP_MEMBER = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=del_group_member";

    public static final String URL_CHANGE_GROUP_AVATAR = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=change_avatar";

    public static final String URL_EDIT_GROUP_INFO = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=edit_info";

    public static final String URL_EDIT_GROUP_MYNICKNAME = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=edit_nickname";

    public static final String URL_GET_GROUP_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=get_group_list";

    public static final String URL_GET_HOT_GROUP = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=hot_group";

    public static final String URL_SEARCH_GROUP = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=search";

    public static final String URL_GET_GROUP_DETAIL = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=detail";

    public static final String URL_GET_GROUP_KEYWORDS_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=keywords_list";

    public static final String URL_GET_MY_GROUP_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=my_group_list";

    public static final String URL_GET_UNPROGRESS_MATTER_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=wait_process";

    public static final String URL_GROUP_MODIFY_RANK = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=modify_rank";

    public static final String URL_GROUP_DELETE_MANAGER = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=del_group_admin";

    public static final String URL_GROUP_ADD_MANAGER = URL_SERVER_BASE
            + "/index.php?m=api&c=groupMember&a=add_group_admin";

    public static final String URL_GROUP_CREATE_CHECK = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=check_create";
    //游客加入教室权限接口
    public static final String URL_GROUP_JOIN_CHECK = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=check_visitor";

    public static final String URL_GET_GROUP_TASK_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=groupStudy&a=get_task_list";

    public static final String URL_GROUP_ADD_TASK = URL_SERVER_BASE
            + "/index.php?m=api&c=groupStudy&a=add_task";

    public static final String URL_GROUP_TASK_DETAIL = URL_SERVER_BASE
            + "/index.php?m=api&c=groupStudy&a=get_task_detail";

    public static final String URL_GROUP_READ_MATTER = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=see_wait_process";

    public static final String URL_COURSE_ERROR_CORRECTION = URL_SERVER_BASE
            + "/index.php?m=api&c=basic&a=course_feedback";

    public static final String URL_GET_ALL_COURSE_NAME = URL_SERVER_BASE
            + "/index.php?m=api&c=search&a=search_course";

    //城市排行榜
    public static final String URL_GET_CUR_RANK_CITY = URL_SERVER_BASE
            + "/index.php?m=api&c=top&a=top_city";
    public static final String URL_GET_CITY_LIST = URL_SERVER_BASE
            + "/index.php?m=api&c=basic&a=area_list";
    public static final String URL_SET_RANK_CITY = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=upload_userinfo";

    //微信第三方登录
    public static final String URL_WECHAT_GET_AUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?qupeiyin=1";
    public static final String URL_WECHAT_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?qupeiyin=1";

    public static final String URL_TOP_COLLECT = URL_SERVER_BASE
            + "/index.php?m=api&c=course&a=course_top";

    public static final String URL_TOP_MY_DUBART = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=myshow_top";

    public static final String URL_CANCEL_TOP_MY_DUBART = URL_SERVER_BASE
            + "/index.php?m=api&c=show&a=cancel_top";

    public static final String URL_TOP_MY_MEMBER = URL_SERVER_BASE
            + "/index.php?m=api&c=fans&a=set_top";

    public static final String URL_CANCEL_TOP_MY_MEMBER = URL_SERVER_BASE
            + "/index.php?m=api&c=fans&a=cancel_top";

    //如何进入高手秀场
    public static final String URL_HOW_TO_BESTSHOW = URL_SERVER_BASE
            + "/index.php?m=api&c=article&a=details&id=56";

    //删除带出事项
    public static final String URL_DELETE_UNPROGRESS_MATTER = URL_SERVER_BASE
            + "/index.php?m=api&c=group&a=del_process";

    //删除带出事项
    public static final String URL_REFRESH_TOKEN = URL_SERVER_BASE
            + "/index.php?m=api&c=user&a=auth_login";

    /**
     * 获取私密空间地址
     */
    public static final String URL_GET_PRIVATE_VIDE_INFO = URL_SERVER_BASE
            + "/index.php?m=api&c=video&a=video_url";

    public static final String URL_GET_ACT_INFO = URL_SERVER_BASE
            + "/index.php?m=api&c=basic&a=activity";
}
