package zwl.magic.com.common.model.net.request;

import android.content.Context;

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
import com.ishowedu.child.peiyin.model.entity.Result;
import com.ishowedu.child.peiyin.model.entity.ResultWithId;
import com.ishowedu.child.peiyin.model.entity.SchoolAndArea;
import com.ishowedu.child.peiyin.model.entity.SearchUserInfo;
import com.ishowedu.child.peiyin.model.entity.SpaceInfo;
import com.ishowedu.child.peiyin.model.entity.Support;
import com.ishowedu.child.peiyin.model.entity.Upload;
import com.ishowedu.child.peiyin.model.entity.User;
import com.ishowedu.child.peiyin.model.entity.UserType;
import com.ishowedu.child.peiyin.model.entity.VerifyCode;
import com.ishowedu.child.peiyin.model.entity.Version;
import com.ishowedu.child.peiyin.model.entity.VisitorEntity;
import com.ishowedu.child.peiyin.model.entity.WeChatUserInfo;
import com.ishowedu.child.peiyin.model.entity.WechatAuthInfo;
import com.ishowedu.child.peiyin.model.entity.WeiboUserInfo;
import com.ishowedu.child.peiyin.model.entity.WonderAct;
import com.ishowedu.child.peiyin.model.net.base.HttpException;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

public interface INetInterface {
    // 获取游客账户
    public User getVisitor(Context context)
            throws HttpException;

    public User getUserData(Context context)
            throws HttpException;

    // 获取验证码
    public VerifyCode getCode(String mobile, int isReset)
            throws HttpException;

    // 注册
    public User register(Context context, String mobile, String password,
                         String nickname, String code) throws HttpException;

    // 游客绑定
    public User tieupMobile(Context context, String mobile,
                            String password, String nickname, String code)
            throws HttpException;

    // 第三方
    public User thirdLogin(Context context, String token, String type,
                           String nickname, String avatar, String sex, String signature)
            throws HttpException;


    public WeiboUserInfo getWeiboUserInfo(String uid, String authToken)
            throws HttpException;

    // 找回密码
    public ChanagePwd resetPwd(Context context, String mobile, String password,
                               String code) throws HttpException;

    // 修改密码
    public ChanagePwd changePwd(Context context, String password, String newpassword)
            throws HttpException;

    // 登陆
    public User login(Context context, String mobile, String password)
            throws HttpException;

    // qupeiyin登陆
    public User quPeiyinLogin(Context context, String mobile, String password)
            throws HttpException;

    // 注销
    public Result logout(Context context) throws HttpException;

    // 修改头像
    public User changeAvatar(Context context, File file)
            throws HttpException;

    // 修改用户信息
    public User modifyUserInfo(Context context, User user, String class_id)
            throws HttpException;

    // 获取收藏课程列表(changed include album and course)
    public List<CourseCollectBean> getFavCourse(int start, int rows, String keyword)
            throws HttpException;

    // 提交推荐视频
    public Result suggestVideo(String content) throws HttpException;

    // 获取所有人聊天记录列表
    public List<ChatMessageRet> getAllChatMessageList()
            throws HttpException;

    public List<MessageV2> getMessageListV32(String type, int start, int rows)
            throws HttpException;

    // 获取未读消息数
    public UnreadMessageCount getUnreadMessageCount()
            throws HttpException;

    public Result collectFav(Context context, String courseId, String albumId)
            throws HttpException;

    public Version getVersion() throws HttpException;

    public Result addPraise(Context context, String show_id, String show_uid, int type)
            throws HttpException;

    public Result cancelCollect(Context context, String courseId, String albumId)
            throws HttpException;

    public Result delectCollect(Context context, String collect_id)
            throws HttpException;

    public Result delectMyDubbing(Context context, String show_id)
            throws HttpException;

    public Collect getIfCollected(Context context, String course_id,
                                  String album_id) throws HttpException;

    public Support getIfPraise(Context context, String show_id)
            throws HttpException;

    public ResultWithId addComment(Context context, String show_uid,
                                   String show_id, String commentStr) throws HttpException;

    public ResultWithId addAudioComment(Context context, String show_uid,
                                        String show_id, String commentStr, String audio, int audioTimeLen)
            throws HttpException;

    public Result getCoursePrivilege(Context context, String course_id,
                                     String album_id) throws HttpException;

    public Upload uploadFileInfo(Context context, String course_id,
                                 String file_info, String album_id, String group_id, String task_id, String area_id, String lon, String lat)
            throws HttpException;

    //最新配音-全国
    public List<DubbingArt> getLatestDubbingListAll(Context context, int start, int rows)
            throws HttpException;

    //最新配音-关注
    public List<DubbingArt> getLatestDubbingListAttention(Context context, int start, int rows)
            throws HttpException;

    //最新配音-附近
    public List<DubbingArt> getLatestDubbingListNear(Context context, int start, int rows, String lon, String lat)
            throws HttpException;

    //小组内的配音
    public List<DubbingArt> getDubbingListGroup(Context context, int start, int rows, String group_id)
            throws HttpException;

    //单个课程里的排行榜（本片排行榜）
    public List<DubbingArt> getBestDubbingListCourse(Context context, int start, int rows, String course_id)
            throws HttpException;


    public List<DubbingArt> getRecomDubbingList(Context context, int start,
                                                int rows) throws HttpException;

    public List<SearchUserInfo> searchUsers(Context context, String keyword,
                                            int start, int rows) throws HttpException;

    public List<SearchUserInfo> searchHotUsers(Context context, int start,
                                               int rows) throws HttpException;

    public List<DubbingArt> getRankList(Context context, int ishow,
                                        int time_type, int ishow_type, long course_id, int start, int rows)
            throws HttpException;

    // 获取专辑列表，按分类
    public List<CourseAlbum> getCourseAlbumList(Context context, int cate_id, int start,
                                                int rows, LinkedHashMap<String, String> args)
            throws HttpException;

    public CourseAlbum getCourseAlbumDetail(Context context, long album_id)
            throws HttpException;

    public List<HomePageContent> getIndexPage(Context context)
            throws HttpException;

    // 获取课程列表，按分类
    public List<Course> getCourseList(Context context, int category_id,
                                      int start, int rows, LinkedHashMap<String, String> args, int ishow)
            throws HttpException;

    public Course getCourseDetail(Context context, long course_id)
            throws HttpException;

    public IshowHomePageData getIshowHomePage(Context context)
            throws HttpException;

    public Result deleteComment(Context context, long commentId)
            throws HttpException;

    public Result addDubView(Context context, long show_id)
            throws HttpException;

    public SpaceInfo getSpaceInfo(Context context, int member_id)
            throws HttpException;

    public List<Comment> getCommentList(Context context, int show_id,
                                        int start, int rows) throws HttpException;

    public ResultWithId replyAudioComment(Context context, long comment_id,
                                          String comment_text, String audio, int auidoTimeLen)
            throws HttpException;

    public ResultWithId replyComment(Context context, long comment_id,
                                     String comment_text) throws HttpException;

    public Result addAttention(Context context, int fans_uid)
            throws HttpException;

    public Result delAttention(Context context, int fans_uid)
            throws HttpException;

    public AttentionUserEntity getAttention(Context context, int member_id,
                                            int start, int rows, String keyword) throws HttpException;

    public AttentionUserEntity getFans(Context context, int member_id,
                                       int start, int rows, String keyword) throws HttpException;

    public Result addWords(Context context, int member_id, int msgtype,
                           String content) throws HttpException;

    public Result deletePhoto(Context context, String photo_ids)
            throws HttpException;

    public Result setPhoto(Context context, long photo_id, String type)
            throws HttpException;

    public PhotoEntity getPhotoList(Context context, int member_id, int start,
                                    int rows) throws HttpException;

    public VisitorEntity getVisitorList(Context context, int member_id,
                                        int start, int rows) throws HttpException;

    public List<DubbingArt> getOtherDubList(Context context, int member_id,
                                            int start, int rows, String keyword) throws HttpException;

    // 上传照片
    public Result uploadPhoto(Context context, File file)
            throws HttpException;

    public List<CommentWrapper> getCommentsLayer(Context context, int show_id,
                                                 int start, int rows) throws HttpException;

    // 获取二维码等信息
    public RecommendInfo getQRCode(String uid) throws HttpException;

    // 获取推客榜单
    public List<RecommendRankInfo> getRecommendRank(int time_type, int start,
                                                    int rows) throws HttpException;

    public DubbingArt getDubbingArt(int id) throws HttpException;

    public List<Course> getRelativeCourse(long course_id, int start, int rows,
                                          String album_id) throws HttpException;

    public List<AlbumOrCourse> searchAlbumOrCourse(String keyword, int start,
                                                   int rows) throws HttpException;

    public User tieupThirdLogin(Context context, String auth_token, int type, String nickname)
            throws HttpException;

    public UserType getUserType() throws HttpException;


    public List<DubbingArt> getDubbingRank(int time_type, int ishow_type,
                                           int start, int rows, String cityId) throws HttpException;

    // 获取所有学校
    public List<SchoolAndArea> getAllSchool(String area_id) throws HttpException;

    public User tieupGuest(Context context, int uid, String deviceId)
            throws HttpException;

    public List<Course> getAlbumCourseList(int albumId, int start, int rows)
            throws HttpException;

    public Result suggestArt(int show_id) throws HttpException;

    // 获取生词本
    public List<Word> getWordBookList(int start, int rows) throws HttpException;

    // 删除单词
    public Result delWords(Context context, String words, int isclear)
            throws HttpException;

    // 同步单词到服务端
    public Result addWords(Context context, List<Word> words,
                           boolean isAddNewWord) throws HttpException;

    public List<SystemMessage> getSystemMessageList(int start, int rows)
            throws HttpException;

    public Result replySystem(int msg_type, String content, String dataLen,
                              int cate) throws HttpException;

    public Result deleteMessage(Context context, String type, int isclear,
                                int msg_id) throws HttpException;

    public Result deleteUserMessageByUserId(Context context, String member_id)
            throws HttpException;

    public Result deleteUserMessageByMsgId(Context context, String msg_id)
            throws HttpException;

    // 修改封面
    public String changeCover(Context context, File file)
            throws HttpException;

    public User unbindMobile(Context context, String mobile, String code)
            throws HttpException;

    public User unbindOthers(Context context, int type)
            throws HttpException;

    public List<Catagory> getCatagoryList(Context context, String type,
                                          int category_id) throws HttpException;

    // 热门视频推荐
    public List<Course> getHotCourseList(Context context, int start, int rows)
            throws HttpException;

    public AccountBindInfo getUserTypeByDeviceid(Context context,
                                                 String devicetoken) throws HttpException;

    // 获取组分类和标签
    public List<GroupType> getGroupTypeList() throws HttpException;

    // 根据类型id获取组标签
    public List<GroupTag> getGroupTagList(String category_id)
            throws HttpException;

    // 创建小组
    public ChatGroup createGroup(GroupTempBean groupTempBean)
            throws HttpException;

    // 获取小组成员列表
    public List<GroupMember> getGroupMembers(Context context, String group_id,
                                             String type) throws HttpException;

    // 申请加入或者邀请加入群
    public Result invitationMember(Context context, int type, String group_id,
                                   String invited_uid, String remark) throws HttpException;

    // 某个用户同意加入某个小组
    public ChatGroup selfJoin(Context context, int group_id)
            throws HttpException;

    // 某个用户拒绝加入某个小组
    public Result selfRefuse(Context context, int group_id)
            throws HttpException;

    // 群管理人员同意加入
    public ChatGroup agreeJoin(Context context, int group_id, int apply_uid)
            throws HttpException;

    // 群管理人员不同意加入
    public Result refuseJoin(Context context, int group_id, int apply_uid)
            throws HttpException;

    // 获取相关用户列表,默认按照用户编号排序（粉丝，关注，同城，同校，搜索）
    public List<SimpleUserInfo> getAboutMembers(Context context,
                                                String group_id, int type, int page, int page_size, String name)
            throws HttpException;

    // 删除组员
    public Result delGroupMember(Context context, String group_id,
                                 String delete_uid, int type) throws HttpException;

    // 编辑小组头像
    public GroupImageUrl changeGroupAvatar(Context context, File avatar,
                                           String gid) throws HttpException;

    // 编辑小组基本信息
    public ChatGroup editGroupInfo(Context context, String id, String name,
                                   String info, String category_id, String tag_id)
            throws HttpException;

    // 修改自己在小组的昵称
    public ChatGroup editGroupMyNickname(Context context, String group_id,
                                         String nickname) throws HttpException;

    // 小组列表
    public List<ChatGroup> getGroupList(Context context, int category_id,
                                        int tag_id, int start, int rows) throws HttpException;

    // 热门推荐小组
    public List<ChatGroup> getHotGroup(Context context, int start, int rows)
            throws HttpException;

    // 搜索小组
    public List<ChatGroup> searchGroup(Context context, String keywords,
                                       int start, int rows) throws HttpException;

    // 小组详情
    public ChatGroup getGroupDetail(Context context, String group_id)
            throws HttpException;

    // 小组热搜关键词列表
    public List<String> getGroupSeachkey(Context context)
            throws HttpException;

    // 我的小组列表
    public ChatGroupWrapper getMyGroupList(Context context, int start, int rows) throws HttpException;

    // 我的小组列表
    public List<UnprogressedMatter> getUnprogressMatterList(Context context,
                                                            int start, int rows) throws HttpException;

    // 修改我在组中的头衔
    public Result modifyMyGroupRank(Context context, String group_id,
                                    String rank, String group_uid) throws HttpException;

    // 删除组中的管理员
    public Result deleteGroupManager(Context context, String group_id,
                                     String rank) throws HttpException;

    // 添加组中的管理员
    public Result addGroupManager(Context context, String group_id, String rank)
            throws HttpException;

    // 检查是否有权限创建组
    public Result checkGroupCreate(Context context)
            throws HttpException;

    // 检查是否有权限加入组
    public Result checkGroupIn(Context context)
            throws HttpException;

    // 获取任务列表
    public List<GroupWork> getGroupWorkList(String groupId, int start, int rows)
            throws HttpException;

    // 添加群任务
    public int groupAddTask(String group_id, String course_list, String remark)
            throws HttpException;

    // 把待处理事项置为已读
    public Result readMatter(String wait_id) throws HttpException;

    // 获取排行城市信息
    public City getRankCity() throws HttpException;

    // 获取所有城市
    public List<City> getCities() throws HttpException;

    // 设置排行城市
    public Result setCity(int cityId) throws HttpException;

    // 设置位置信息
    public Result setLonLatLocation(String lon, String lat) throws HttpException;

    // 设置排行城市
    public WechatAuthInfo getWechatAuthInfo(String code) throws HttpException;

    // 设置排行城市
    public WeChatUserInfo getWeChatUserInfo(String access_token, String openId) throws HttpException;

    //获取课程所有的名字
    public SearchCourseOrAlbum getAllCourseName(String course_time, String album_time)
            throws HttpException;

    //置顶视频收藏
    public Result topCollect(Context context, String collectId)
            throws HttpException;

    //置顶我的配音列表里的配音作品
    public Result topMyDubbingArt(Context context, String showId)
            throws HttpException;

    //取消置顶我的配音列表里的配音作品
    public Result cancelTopMyDubbingArt(Context context, String showId)
            throws HttpException;

    public Result topMyMember(Context context, String member_id, String type)
            throws HttpException;

    public Result cancelTopMyMember(Context context, String member_id, String type)
            throws HttpException;

    public Result deleteUnprogressMatter(Context context, String matterid)
            throws HttpException;

    public List<DubbingArt> getIshowRankList(Context context, int ishow,
                                             int time_type, int ishow_type, long course_id, int start, int rows)
            throws HttpException;

    public PrivateVideoInfo getPrivateVideoUrl(Context context, String key) throws HttpException;

    public WonderAct getActUrl() throws HttpException;
}
