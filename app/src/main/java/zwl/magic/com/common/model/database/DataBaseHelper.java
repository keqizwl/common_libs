package zwl.magic.com.common.model.database;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ishowedu.child.peiyin.IShowDubbingApplication;
import com.ishowedu.child.peiyin.common.Constants;
import com.ishowedu.child.peiyin.model.database.contactor.Contactor;
import com.ishowedu.child.peiyin.model.database.contactor.ContactorHandler;
import com.ishowedu.child.peiyin.model.database.contactor.IContactorHandler;
import com.ishowedu.child.peiyin.model.database.course.Course;
import com.ishowedu.child.peiyin.model.database.course.CourseHandler;
import com.ishowedu.child.peiyin.model.database.course.ICourseHandler;
import com.ishowedu.child.peiyin.model.database.dubbingArt.DubbingArt;
import com.ishowedu.child.peiyin.model.database.dubbingArt.DubbingArtHandler;
import com.ishowedu.child.peiyin.model.database.dubbingArt.IDubbingArtHandler;
import com.ishowedu.child.peiyin.model.database.group.ChatGroup;
import com.ishowedu.child.peiyin.model.database.group.ChatGroupHandler;
import com.ishowedu.child.peiyin.model.database.group.IChatGroupHandler;
import com.ishowedu.child.peiyin.model.database.group.message.GroupChatMessage;
import com.ishowedu.child.peiyin.model.database.group.message.GroupChatMessageHandler;
import com.ishowedu.child.peiyin.model.database.group.message.IGroupChatMessageHandler;
import com.ishowedu.child.peiyin.model.database.message.ChatMessage;
import com.ishowedu.child.peiyin.model.database.message.ChatMessageHandler;
import com.ishowedu.child.peiyin.model.database.message.IChatMessageHandler;
import com.ishowedu.child.peiyin.model.database.searchCourseAuto.ISearchCourseAutoHandler;
import com.ishowedu.child.peiyin.model.database.searchCourseAuto.SearchCourseAuto;
import com.ishowedu.child.peiyin.model.database.searchCourseAuto.SearchCourseAutoHandler;
import com.ishowedu.child.peiyin.model.database.searchCourseHistory.ISearchCourseHistoryHandler;
import com.ishowedu.child.peiyin.model.database.searchCourseHistory.SearchCourseHistory;
import com.ishowedu.child.peiyin.model.database.searchCourseHistory.SearchCourseHistoryHandler;
import com.ishowedu.child.peiyin.model.database.searchUserHistory.ISearchUserHistoryHandler;
import com.ishowedu.child.peiyin.model.database.searchUserHistory.SearchUserHistory;
import com.ishowedu.child.peiyin.model.database.searchUserHistory.SearchUserHistoryHandler;
import com.ishowedu.child.peiyin.model.database.word.IWordHandler;
import com.ishowedu.child.peiyin.model.database.word.Word;
import com.ishowedu.child.peiyin.model.database.word.WordHandler;
import com.ishowedu.child.peiyin.util.OtherUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * 完成数据库打开关闭操作
 *
 * @author weilinfeng
 * @Data 2014-8-7
 */
public class DataBaseHelper implements DbUpgradeListener, IDubbingArtHandler,
        ICourseHandler, IChatMessageHandler, IWordHandler, IContactorHandler,
        IGroupChatMessageHandler, IChatGroupHandler, ISearchCourseHistoryHandler,
        ISearchCourseAutoHandler, ISearchUserHistoryHandler {
    /**
     * 数据库版本号
     **/
    private static final int DATABASE_VERSION = 2;
    /**
     * 数据库版本号
     **/
    private static final String DATABASE_NAME = Constants.APPLICATION_NAME
            + ".db";
    private static final String TAG = "DataBaseHelper";
    /**
     * 私有实例对讲
     */
    private static DataBaseHelper mDataBaseHelper = null;
    /**
     * 变量/常量说明
     */
    private DbUtils mDbUtils = null;
    /**
     * 变量/常量说明
     */
    private IDubbingArtHandler mDubbingArtHandler;
    /**
     * 变量/常量说明
     */
    private ICourseHandler mCourseHandler;
    /**
     * 变量/常量说明
     */
    private IWordHandler mWordHandler;
    /**
     * 聊天消息数据处理器
     */
    private IChatMessageHandler mChatMessageHandler;
    /**
     * 聊天消息数据处理器
     */
    private IContactorHandler mContactorHandler;
    /**
     * 组聊天记录
     */
    private IGroupChatMessageHandler mGroupChatMessageHandler;
    /**
     * 组聊天记录
     */
    private IChatGroupHandler mChatGroupHandler;
    /**
     * 搜索课程历史记录
     */
    private ISearchCourseHistoryHandler mSearchCourseHistoryHandler;

    /**
     * 搜索用户历史记录
     */
    private ISearchUserHistoryHandler mSearchUserHistoryHandler;

    /**
     * 所有课程保存在数据库
     */
    private ISearchCourseAutoHandler mSearchCourseAutoHandler;

    public int getCurVersion(Context context) {
        int curVersionCode = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            curVersionCode = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return curVersionCode;
    }

    // 更新数据库需要拷贝数据时，应该忽略掉id，否则拷贝可能有问题
    private DataBaseHelper() {
        Context context = IShowDubbingApplication.getInstance()
                .getApplicationContext();
        if (context != null) {
            String dir = OtherUtils.getRootDirPath();
            if (dir == null || dir.equals("")) {//友盟统计的下面一行代码的空指针bug,很难定位是否是这里空了,暂且一试
                return;
            }
            mDbUtils = DbUtils.create(context, dir,
                    DATABASE_NAME, DATABASE_VERSION, this);
            mDbUtils.configAllowTransaction(true);
            mDbUtils.configDebug(true);
            mCourseHandler = new CourseHandler(mDbUtils, context);
            mDubbingArtHandler = new DubbingArtHandler(mDbUtils);
            mChatMessageHandler = new ChatMessageHandler(mDbUtils);
            mWordHandler = new WordHandler(mDbUtils);
            mContactorHandler = new ContactorHandler(mDbUtils);
            mGroupChatMessageHandler = new GroupChatMessageHandler(mDbUtils);
            mChatGroupHandler = new ChatGroupHandler(mDbUtils);
            mSearchCourseHistoryHandler = new SearchCourseHistoryHandler(mDbUtils);
            mSearchCourseAutoHandler = new SearchCourseAutoHandler(mDbUtils);
            mSearchUserHistoryHandler = new SearchUserHistoryHandler(mDbUtils);
            // 触发由老数据库到数据的更新
            DbUtils.create(context, "xUtils.db", 2, onOldUpgrade);
        }
    }

    /**
     * 获取实例的方法
     *
     * @return
     * @since V1.0
     */
    public static synchronized DataBaseHelper getInstance() {
        if (mDataBaseHelper == null) {
            mDataBaseHelper = new DataBaseHelper();
        }
        return mDataBaseHelper;
    }

    private DbUpgradeListener onOldUpgrade = new DbUpgradeListener() {

        @Override
        // 后续完善，添加表示表明，数据库已更新时，不要再打开老数据库
        public void onUpgrade(DbUtils dbUtils, int oldVersion, int newVersion) {
        }
    };

    @Override
    public void onUpgrade(DbUtils dbUtils, int oldVersion, int newVersion) {

    }

    /**
     * 这里对方法做描述
     *
     * @return
     * @since V1.0
     */
    public DbUtils getDbUtils() {
        return mDbUtils;
    }

    @Override
    public void setDbUtils(DbUtils dbUtils) {
        mDbUtils = dbUtils;
    }

    @Override
    public boolean saveOrUpdateMessageList(List<ChatMessage> msgList) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.saveOrUpdateMessageList(msgList);
    }

    @Override
    public boolean saveOrUpdateMessage(ChatMessage msg) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.saveOrUpdateMessage(msg);
    }

    @Override
    public List<ChatMessage> findMessageListByUserId(String accountName,
                                                     String userId, int count) {
        if (mChatMessageHandler == null) {
            return null;
        }
        return mChatMessageHandler.findMessageListByUserId(accountName, userId,
                count);
    }

    @Override
    public List<ChatMessage> findMessageListByUserId(String accountName,
                                                     String userId, int count, long beforeTime) {
        if (mChatMessageHandler == null) {
            return null;
        }
        return mChatMessageHandler.findMessageListByUserId(accountName, userId,
                count, beforeTime);
    }

    @Override
    public List<ChatMessage> findLastedMessageListByUserId(String accountName) {
        if (mChatMessageHandler == null) {
            return null;
        }
        return mChatMessageHandler.findLastedMessageListByUserId(accountName);
    }

    @Override
    public int findUnreadMsgCount(String accountName) {
        if (mChatMessageHandler == null) {
            return 0;
        }
        return mChatMessageHandler.findUnreadMsgCount(accountName);
    }

    @Override
    public boolean updateAllListUpdateState(String accountName, boolean isUpdate) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.updateAllListUpdateState(accountName,
                isUpdate);
    }

    @Override
    public boolean updateAllListUpdateStateByUserId(String accountName,
                                                    String userId, boolean isUpdate) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.updateAllListUpdateStateByUserId(
                accountName, userId, isUpdate);
    }

    @Override
    public boolean updateMessageListReadStateByUserId(String accountName,
                                                      String userId, boolean isReaded) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.updateMessageListReadStateByUserId(
                accountName, userId, isReaded);
    }

    @Override
    public boolean updateMessageListSendState(String accountName,
                                              int orgSendState, int targetSendState) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.updateMessageListSendState(accountName,
                orgSendState, targetSendState);
    }

    @Override
    public boolean updateMessageReadStateById(String Id, boolean isReaded) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.updateMessageReadStateById(Id, isReaded);
    }

    @Override
    public boolean deleteMessageList(String accountName) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.deleteMessageList(accountName);
    }

    @Override
    public boolean deleteMessageListByType(String accountName, String userId) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mChatMessageHandler.deleteMessageListByType(accountName, userId);
    }

    @Override
    public boolean deleteGroupMessageById(String id) {
        if (mChatMessageHandler == null) {
            return false;
        }
        return mGroupChatMessageHandler.deleteGroupMessageById(id);
    }

    public void updateUnreadMsgCount(String accountName, String userId,
                                     int unreadCount) {
        if (mChatMessageHandler == null) {
            return;
        }

        mChatMessageHandler.updateUnreadMsgCount(accountName, userId,
                unreadCount);
    }

    @Override
    public int findUnreadMsgCount(String accountName, String userid) {
        if (mChatMessageHandler == null) {
            return 0;
        }
        return mChatMessageHandler.findUnreadMsgCount(accountName, userid);
    }

    @Override
    public boolean saveOrUpdateWordList(List<Word> wordList) {
        if (mWordHandler == null) {
            return false;
        }
        return mWordHandler.saveOrUpdateWordList(wordList);
    }

    @Override
    public boolean saveOrUpdateWord(Word word) {
        if (mWordHandler == null) {
            return false;
        }
        return mWordHandler.saveOrUpdateWord(word);
    }

    @Override
    public List<Word> findAllWordListByWordName(String uid, String wordname) {
        if (mWordHandler == null) {
            return null;
        }
        return mWordHandler.findAllWordListByWordName(uid, wordname);
    }

    @Override
    public boolean deleteByWordName(String uid, String wordname) {
        if (mWordHandler == null) {
            return false;
        }
        return mWordHandler.deleteByWordName(uid, wordname);
    }

    @Override
    public List<Word> findAllWordListByUploadStatu(String uid,
                                                   String uploadstatu) {
        if (mWordHandler == null) {
            return null;
        }
        return mWordHandler.findAllWordListByUploadStatu(uid, uploadstatu);
    }

    @Override
    public List<Word> findAllWordListById(String uid) {
        if (mWordHandler == null) {
            return null;
        }
        return mWordHandler.findAllWordListById(uid);
    }

    @Override
    public boolean saveOrUpdateDubbingArtList(List<DubbingArt> dubList) {
        if (mDubbingArtHandler == null) {
            return false;
        }
        return mDubbingArtHandler.saveOrUpdateDubbingArtList(dubList);
    }

    @Override
    public List<DubbingArt> findAllDubbingArtList(int uid) {
        if (mDubbingArtHandler == null) {
            return null;
        }
        return mDubbingArtHandler.findAllDubbingArtList(uid);
    }

    @Override
    public boolean deleteDubbingArtById(long id) {
        if (mDubbingArtHandler == null) {
            return false;
        }
        return mDubbingArtHandler.deleteDubbingArtById(id);
    }

    @Override
    public boolean saveOrUpdateDubbingArt(DubbingArt dubbingArt) {
        if (mDubbingArtHandler == null) {
            return false;
        }
        return mDubbingArtHandler.saveOrUpdateDubbingArt(dubbingArt);
    }

    @Override
    public long getDubbingArtCount(int uid) {
        if (mDubbingArtHandler == null) {
            return 0;
        }
        return mDubbingArtHandler.getDubbingArtCount(uid);
    }

    @Override
    public List<Course> findAllCourseList(String uid) {
        if (mCourseHandler == null) {
            return null;
        }
        return mCourseHandler.findAllCourseList(uid);
    }

    @Override
    public List<Course> findAllCourseListByPartName(String uid, String name) {
        if (mCourseHandler == null) {
            return null;
        }
        return mCourseHandler.findAllCourseListByPartName(uid, name);
    }

    @Override
    public List<Course> findAllCourseByCourseId(String uid, String courseId) {
        if (mCourseHandler == null) {
            return null;
        }
        return mCourseHandler.findAllCourseByCourseId(uid, courseId);
    }

    @Override
    public boolean deleteCourseByCourseId(String uid, String courseId) {
        if (mCourseHandler == null) {
            return false;
        }
        return mCourseHandler.deleteCourseByCourseId(uid, courseId);
    }

    @Override
    public boolean saveOrUpdateCourse(Course course) throws DbException {
        if (mCourseHandler == null) {
            return false;
        }
        return mCourseHandler.saveOrUpdateCourse(course);
    }

    @Override
    public boolean saveOrUpdateCourseList(List<Course> courseList) {
        if (mCourseHandler == null) {
            return false;
        }
        return mCourseHandler.saveOrUpdateCourseList(courseList);
    }

    @Override
    public void dropTableCourse() {
        if (mCourseHandler == null) {
            return;
        }
        mCourseHandler.dropTableCourse();
    }

    @Override
    public long getCountCourse() {
        if (mCourseHandler == null) {
            return 0;
        }
        return mCourseHandler.getCountCourse();
    }

    public long findMessageCount(String account) {
        if (mChatMessageHandler == null) {
            return 0;
        }
        return mChatMessageHandler.findMessageCount(account);
    }

    @Override
    public void clearAllMessage() {
        if (mChatMessageHandler == null) {
            return;
        }
        mChatMessageHandler.clearAllMessage();
    }

    @Override
    public List<Contactor> findContactorList(String accountName) {
        if (mContactorHandler == null) {
            return null;
        }
        return mContactorHandler.findContactorList(accountName);
    }

    @Override
    public Contactor findContactor(String contactorId) {
        if (mContactorHandler == null) {
            return null;
        }
        return mContactorHandler.findContactor(contactorId);
    }

    @Override
    public void saveOrUpdateContactor(Contactor contactor) {
        if (mContactorHandler == null) {
            return;
        }
        mContactorHandler.saveOrUpdateContactor(contactor);
    }

    @Override
    public void saveOrUpdateContactorList(List<Contactor> contactors) {
        if (mContactorHandler == null) {
            return;
        }
        mContactorHandler.saveOrUpdateContactorList(contactors);
    }

    @Override
    public void updateContactorState(String contactorId, int state) {
        if (mContactorHandler == null) {
            return;
        }
        mContactorHandler.updateContactorState(contactorId, state);
    }

    @Override
    public void updateContactorUnreadCount(String contactorId, int unreadCount) {
        if (mContactorHandler == null) {
            return;
        }
        mContactorHandler.updateContactorUnreadCount(contactorId, unreadCount);
    }

    @Override
    public void clearContactor(String contactorId) {
        if (mContactorHandler == null) {
            return;
        }
        mContactorHandler.clearContactor(contactorId);
    }

    @Override
    public void deleteContactor(String contactorId) {
        if (mContactorHandler == null) {
            return;
        }
        mContactorHandler.deleteContactor(contactorId);
    }

    @Override
    public boolean deleteWordList(List<Word> wordList) {
        if (mWordHandler == null) {
            return false;
        }
        return mWordHandler.deleteWordList(wordList);
    }

    @Override
    public boolean saveOrUpdateGroupMessageList(List<GroupChatMessage> msgList) {
        if (mGroupChatMessageHandler == null) {
            return false;
        }
        return mGroupChatMessageHandler.saveOrUpdateGroupMessageList(msgList);
    }

    @Override
    public boolean saveOrUpdateGroupMessage(GroupChatMessage msg) {
        if (mGroupChatMessageHandler == null) {
            return false;
        }
        return mGroupChatMessageHandler.saveOrUpdateGroupMessage(msg);
    }

    @Override
    public List<GroupChatMessage> findGroupMessageListByGruopId(
            String accountName, String groupId, int count) {
        if (mGroupChatMessageHandler == null) {
            return null;
        }
        return mGroupChatMessageHandler.findGroupMessageListByGruopId(
                accountName, groupId, count);
    }

    @Override
    public List<GroupChatMessage> findMoreGroupMessageListByGruopId(
            String accountName, String gruopId, int count, long beforeTime) {
        if (mGroupChatMessageHandler == null) {
            return null;
        }
        return mGroupChatMessageHandler
                .findMoreGroupMessageListByGruopId(accountName, gruopId, count, beforeTime);
    }

    @Override
    public List<GroupChatMessage> findLastedGroupMessageListByGroupId(
            String accountName) {
        if (mGroupChatMessageHandler == null) {
            return null;
        }
        return mGroupChatMessageHandler
                .findLastedGroupMessageListByGroupId(accountName);
    }

    @Override
    public boolean updateMessageListSendState(String accountName, int sendState) {
        if (mGroupChatMessageHandler == null) {
            return false;
        }
        return mGroupChatMessageHandler.updateMessageListSendState(accountName,
                sendState);
    }

    @Override
    public boolean deleteGroupMessageByGroupId(String accountName,
                                               String gotyeGroupId) {
        if (mGroupChatMessageHandler == null) {
            return false;
        }
        return mGroupChatMessageHandler.deleteGroupMessageByGroupId(
                accountName, gotyeGroupId);
    }

    @Override
    public List<ChatGroup> findChatGroupList(String accountName) {
        if (mChatGroupHandler == null) {
            return null;
        }
        return mChatGroupHandler.findChatGroupList(accountName);
    }

    @Override
    public ChatGroup findChatGroup(String id) {
        if (mChatGroupHandler == null) {
            return null;
        }
        return mChatGroupHandler.findChatGroup(id);
    }

    @Override
    public void saveOrUpdateChatGroup(ChatGroup chatGroup) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.saveOrUpdateChatGroup(chatGroup);
    }

    @Override
    public void saveOrUpdateChatGroupList(List<ChatGroup> chatGroups) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.saveOrUpdateChatGroupList(chatGroups);
    }

    @Override
    public void updateChatGroupUnreadCount(String id, int unreadCount) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.updateChatGroupUnreadCount(id, unreadCount);
    }

    @Override
    public void clearChatGroup(String id) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.clearChatGroup(id);
    }

    @Override
    public void deleteChatGroup(String id) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.deleteChatGroup(id);
    }

    @Override
    public int findChatGroupUnreadCount(String id) {
        if (mChatGroupHandler == null) {
            return 0;
        }
        return mChatGroupHandler.findChatGroupUnreadCount(id);
    }

    @Override
    public boolean updateChatGroup(String uid, String gytoGroidId,
                                   int lastestMsgType, String lastestMsgContent,
                                   long lastestMsgCreateTime, int msgUnreadCount, String userId, String msgNickName) {
        if (mChatGroupHandler == null) {
            return false;
        }
        return mChatGroupHandler.updateChatGroup(uid, gytoGroidId,
                lastestMsgType, lastestMsgContent, lastestMsgCreateTime,
                msgUnreadCount, userId, msgNickName);
    }

    @Override
    public boolean updateChatGroup(String id, int lastestMsgType,
                                   String lastestMsgContent, long lastestMsgCreateTime,
                                   int msgUnreadCount, String userId, String msgNickName) {
        if (mChatGroupHandler == null) {
            return false;
        }
        return mChatGroupHandler.updateChatGroup(id, lastestMsgType,
                lastestMsgContent, lastestMsgCreateTime, msgUnreadCount, userId, msgNickName);
    }

    @Override
    public int findChatGroupUnreadCount(String uid, String gytoGroidId) {
        if (mChatGroupHandler == null) {
            return 0;
        }
        return mChatGroupHandler.findChatGroupUnreadCount(uid, gytoGroidId);
    }

    @Override
    public boolean saveOrUpdateGroupMessage(String id, long msgId,
                                            long createTime, int state) {
        if (mGroupChatMessageHandler == null) {
            return false;
        }
        return mGroupChatMessageHandler.saveOrUpdateGroupMessage(id, msgId, createTime, state);
    }

    public ChatGroup findChatGroupByGotyGroupId(String gotyeGroupId, String uid) {
        if (mChatGroupHandler == null) {
            return null;
        }
        return mChatGroupHandler.findChatGroupByGotyGroupId(gotyeGroupId, uid);
    }

    @Override
    public ChatGroup findChatGroupByGroupId(String groupId, String uid) {
        if (mChatGroupHandler == null) {
            return null;
        }
        return mChatGroupHandler.findChatGroupByGroupId(groupId, uid);
    }

    @Override
    public void updateChatGroupNickName(String groupId, String accountName,
                                        String nickName) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.updateChatGroupNickName(groupId, accountName, nickName);
    }

    @Override
    public void updateChatGroupLevelName(String groupId, String accountName,
                                         String levelName) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.updateChatGroupLevelName(groupId, accountName, levelName);
    }

    @Override
    public boolean updataGroupMessageReaded(String id) {
        if (mGroupChatMessageHandler == null) {
            return false;
        }
        return mGroupChatMessageHandler.updataGroupMessageReaded(id);
    }

    @Override
    public void updataUserMessageReaded(String id) {
        if (mChatMessageHandler == null) {
            return;
        }
        mChatMessageHandler.updataUserMessageReaded(id);
    }

    @Override
    public void deleteChatGroup(String groupId, String accountName) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.deleteChatGroup(groupId, accountName);
    }

    public void updateChatGroupInputText(String accountName, String groupId,
                                         String inputText) {
        if (mChatGroupHandler == null) {
            return;
        }
        mChatGroupHandler.updateChatGroupInputText(accountName, groupId, inputText);
    }

    @Override
    public boolean saveOrUpdateSearchCourseHistoryList(
            List<SearchCourseHistory> searchList) {
        if (mSearchCourseHistoryHandler == null) {
            return false;
        }
        return mSearchCourseHistoryHandler.saveOrUpdateSearchCourseHistoryList(searchList);
    }

    @Override
    public boolean saveOrUpdateSearchCourseHistory(
            SearchCourseHistory searchCourseHistory) {
        if (mSearchCourseHistoryHandler == null) {
            return false;
        }
        return mSearchCourseHistoryHandler.saveOrUpdateSearchCourseHistory(searchCourseHistory);
    }

    @Override
    public List<SearchCourseHistory> findAllSearchCourseHistoryListByIdDescTime(
            String uid) {
        if (mSearchCourseHistoryHandler == null) {
            return null;
        }
        return mSearchCourseHistoryHandler.findAllSearchCourseHistoryListByIdDescTime(uid);
    }

    @Override
    public boolean deleteBySearchCourseHistoryName(String uid,
                                                   String historyname) {
        if (mSearchCourseHistoryHandler == null) {
            return false;
        }
        return mSearchCourseHistoryHandler.deleteBySearchCourseHistoryName(uid, historyname);
    }

    @Override
    public boolean deleteSearchCourseHistoryList(
            List<SearchCourseHistory> historyList) {
        if (mSearchCourseHistoryHandler == null) {
            return false;
        }
        return mSearchCourseHistoryHandler.deleteSearchCourseHistoryList(historyList);
    }

    @Override
    public boolean cleanSearchCourseHistoryTable(String uid) {
        if (mSearchCourseHistoryHandler == null) {
            return false;
        }
        return mSearchCourseHistoryHandler.cleanSearchCourseHistoryTable(uid);
    }

    @Override
    public boolean saveOrUpdateSearchCourseAutoList(
            List<SearchCourseAuto> searchList) {
        if (mSearchCourseHistoryHandler == null) {
            return false;
        }
        return mSearchCourseAutoHandler.saveOrUpdateSearchCourseAutoList(searchList);
    }

    @Override
    public boolean saveOrUpdateSearchCourseAuto(
            SearchCourseAuto searchCourseHistory) {
        if (mSearchCourseAutoHandler == null) {
            return false;
        }
        return mSearchCourseAutoHandler.saveOrUpdateSearchCourseAuto(searchCourseHistory);
    }

    @Override
    public List<SearchCourseAuto> findAllSearchCourseAutoList() {
        if (mSearchCourseAutoHandler == null) {
            return null;
        }
        return mSearchCourseAutoHandler.findAllSearchCourseAutoList();
    }

    @Override
    public List<SearchCourseAuto> findAllSearchCourseAutoListByPartOfTitle(
            String title) {
        if (mSearchCourseAutoHandler == null) {
            return null;
        }
        return mSearchCourseAutoHandler.findAllSearchCourseAutoListByPartOfTitle(title);
    }

    @Override
    public boolean cleanSearchCourseAutoTable() {
        if (mSearchCourseAutoHandler == null) {
            return false;
        }
        return mSearchCourseAutoHandler.cleanSearchCourseAutoTable();
    }

    @Override
    public SearchCourseAuto findNewestCourseSearchCourseAuto() {
        if (mSearchCourseAutoHandler == null) {
            return null;
        }
        return mSearchCourseAutoHandler.findNewestCourseSearchCourseAuto();
    }

    @Override
    public SearchCourseAuto findNewestAlbumSearchCourseAuto() {
        if (mSearchCourseAutoHandler == null) {
            return null;
        }
        return mSearchCourseAutoHandler.findNewestAlbumSearchCourseAuto();
    }

    @Override
    public boolean saveOrUpdateSearchUserHistoryList(
            List<SearchUserHistory> searchList) {
        if (mSearchUserHistoryHandler == null) {
            return false;
        }
        return mSearchUserHistoryHandler.saveOrUpdateSearchUserHistoryList(searchList);
    }

    @Override
    public boolean saveOrUpdateSearchUserHistory(
            SearchUserHistory searchUserHistory) {
        if (mSearchUserHistoryHandler == null) {
            return false;
        }
        return mSearchUserHistoryHandler.saveOrUpdateSearchUserHistory(searchUserHistory);
    }

    @Override
    public List<SearchUserHistory> findAllSearchUserHistoryListByIdDescTime(
            String uid) {
        if (mSearchUserHistoryHandler == null) {
            return null;
        }
        return mSearchUserHistoryHandler.findAllSearchUserHistoryListByIdDescTime(uid);
    }

    @Override
    public boolean deleteBySearchUserHistoryName(String uid, String historyname) {
        if (mSearchUserHistoryHandler == null) {
            return false;
        }
        return mSearchUserHistoryHandler.deleteBySearchUserHistoryName(uid, historyname);
    }

    @Override
    public boolean deleteSearchUserHistoryList(
            List<SearchUserHistory> historyList) {
        if (mSearchUserHistoryHandler == null) {
            return false;
        }
        return mSearchUserHistoryHandler.deleteSearchUserHistoryList(historyList);
    }

    @Override
    public boolean cleanSearchUserHistoryTable(String uid) {
        if (mSearchUserHistoryHandler == null) {
            return false;
        }
        return mSearchUserHistoryHandler.cleanSearchUserHistoryTable(uid);
    }

    @Override
    public long getDubbingArtLocalCount(int uid) {
        if (mDubbingArtHandler == null) {
            return 0;
        }
        return mDubbingArtHandler.getDubbingArtLocalCount(uid);
    }

    @Override
    public List<DubbingArt> findAllDubbingArtLocalList(int uid) {
        if (mDubbingArtHandler == null) {
            return null;
        }
        return mDubbingArtHandler.findAllDubbingArtLocalList(uid);
    }

    @Override
    public boolean deleteDubbingArtNotLocal(int uid) {
        if (mDubbingArtHandler == null) {
            return false;
        }
        return mDubbingArtHandler.deleteDubbingArtNotLocal(uid);
    }
}
