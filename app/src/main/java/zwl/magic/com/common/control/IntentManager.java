package zwl.magic.com.common.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ishowedu.child.peiyin.activity.Room.Course.CourseActivity;
import com.ishowedu.child.peiyin.activity.Room.Dub.DubbingActivity;
import com.ishowedu.child.peiyin.activity.Room.Share.weiboshare.ShareActivity;
import com.ishowedu.child.peiyin.activity.coursealbum.AlbumActivity;
import com.ishowedu.child.peiyin.activity.courselist.CourseListActivity;
import com.ishowedu.child.peiyin.activity.dubbingart.HotRankInfoActivity;
import com.ishowedu.child.peiyin.activity.dubbingart.HowToBeBestShowActivity;
import com.ishowedu.child.peiyin.activity.dubbingart.IShowCourseRankActivity;
import com.ishowedu.child.peiyin.activity.dubbingart.LatestDubArtActivity;
import com.ishowedu.child.peiyin.activity.dubbingart.RecomDubArtActivity;
import com.ishowedu.child.peiyin.activity.iShow.IShowCourseListActivity;
import com.ishowedu.child.peiyin.activity.login.LoginActivity;
import com.ishowedu.child.peiyin.activity.login.ResetPwdActivity;
import com.ishowedu.child.peiyin.activity.login.SignActivity;
import com.ishowedu.child.peiyin.activity.main.MainActivity;
import com.ishowedu.child.peiyin.activity.maintabs.rankcity.ChangeRankCityActivity;
import com.ishowedu.child.peiyin.activity.search.SearchActivity;
import com.ishowedu.child.peiyin.activity.search.SearchCourseContentActivity;
import com.ishowedu.child.peiyin.activity.setting.AboutUsActivity;
import com.ishowedu.child.peiyin.activity.setting.BindDetailActivity;
import com.ishowedu.child.peiyin.activity.setting.ChangeNicknameActivity;
import com.ishowedu.child.peiyin.activity.setting.ChangeSchoolActivity;
import com.ishowedu.child.peiyin.activity.setting.ChangeSignatureActivity;
import com.ishowedu.child.peiyin.activity.setting.DisclaimActivity;
import com.ishowedu.child.peiyin.activity.setting.MessageNotifyActivity;
import com.ishowedu.child.peiyin.activity.setting.PersonInfoActivity;
import com.ishowedu.child.peiyin.activity.setting.SelectProvinceActivity;
import com.ishowedu.child.peiyin.activity.setting.SettingActivity;
import com.ishowedu.child.peiyin.activity.setting.SuggestVideoActivity;
import com.ishowedu.child.peiyin.activity.setting.UserInfoActivity;
import com.ishowedu.child.peiyin.activity.setting.feedback.FeedBackActivity;
import com.ishowedu.child.peiyin.activity.space.attention.AttentionActivity;
import com.ishowedu.child.peiyin.activity.space.coursecache.CourseCacheActivity;
import com.ishowedu.child.peiyin.activity.space.coursecollect.CourseCollectActivity;
import com.ishowedu.child.peiyin.activity.space.dublist.DubbingListActivity;
import com.ishowedu.child.peiyin.activity.space.fans.FansActivity;
import com.ishowedu.child.peiyin.activity.space.message.MessageManageActivity;
import com.ishowedu.child.peiyin.activity.space.message.comment.CommentMessageActivity;
import com.ishowedu.child.peiyin.activity.space.message.good.GoodMessageActivity;
import com.ishowedu.child.peiyin.activity.space.message.user.UserMessageActivity;
import com.ishowedu.child.peiyin.activity.space.message.xuj.XujMessageActivity;
import com.ishowedu.child.peiyin.activity.space.photo.PhotoActivity;
import com.ishowedu.child.peiyin.activity.space.recommend.RecommendRankActivity;
import com.ishowedu.child.peiyin.activity.space.webview.WebViewActivity;
import com.ishowedu.child.peiyin.activity.space.word.WordBookActivity;
import com.ishowedu.child.peiyin.model.database.course.Course;
import com.ishowedu.child.peiyin.model.database.dubbingArt.DubbingArt;
import com.ishowedu.child.peiyin.model.database.message.ChatMessage;
import com.ishowedu.child.peiyin.model.entity.City;
import com.ishowedu.child.peiyin.model.entity.HomeCategory;
import com.ishowedu.child.peiyin.model.entity.SpaceInfo;

/**
 * Created by weilong zhou on 2015/9/2.
 * Email:zhouwlong@gmail.com
 */
public class IntentManager {
    private static IntentManager instance;

    private IntentManager() {
    }

    public static IntentManager getInstance() {
        if (instance == null) {
            synchronized (IntentManager.class) {
                if (instance == null) {
                    instance = new IntentManager();
                }
            }
        }
        return instance;
    }

    private void startActivity(Context context, boolean needResult, Intent intent, int requestCode) {
        if (context == null || intent == null) {
            return;
        }

        if (needResult) {
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            }
        } else {
            context.startActivity(intent);
        }
    }

    public void gotoMainPage(Context context) {
        startActivity(context, false, new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);
    }

    public void gotoCourseAlbum(Context context, int albumId) {
        Intent intent = AlbumActivity.createIntent(context, albumId);
        startActivity(context, false, intent, 0);
    }

    public void gotoCourseList(Context context, HomeCategory homeCategory) {
        Intent intent = CourseListActivity.createIntent(context, homeCategory);
        startActivity(context, false, intent, 0);
    }

    public void gotoDubArtDetail(Context context, DubbingArt dubbingArt) {
        Intent intent = HotRankInfoActivity.createIntent(context, dubbingArt);
        startActivity(context, false, intent, 0);
    }

    public void gotoDubArtDetail(Context context, int dubbingArtId) {
        Intent intent = HotRankInfoActivity.createIntent(context, dubbingArtId);
        startActivity(context, false, intent, 0);
    }

    public void gotoHowToBeBestShow(Context context) {
        Intent intent = HowToBeBestShowActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoIShowCourseRank(Context context, long course_id) {
        Intent intent = IShowCourseRankActivity.createIntent(context, course_id);
        startActivity(context, false, intent, 0);
    }

    public void gotoLatestDubArtList(Context context, String title) {
        Intent intent = LatestDubArtActivity.createIntent(context, title);
        startActivity(context, false, intent, 0);
    }

    public void gotoRecomDubArtList(Context context, String title) {
        Intent intent = RecomDubArtActivity.createIntent(context, title);
        startActivity(context, false, intent, 0);
    }

    public void gotoIShowCourseList(Context context, int ishowId) {
        Intent intent = IShowCourseListActivity.createIntent(context, ishowId);
        startActivity(context, false, intent, 0);
    }

    public void gotoLogin(Context context, int flags) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(flags);
        startActivity(context, false, intent, 0);
    }

    public void gotoLogin(Context context, boolean ifFromMain, boolean ifFinishAfterLogin) {
        Intent intent = LoginActivity.createIntent(context, ifFromMain, ifFinishAfterLogin);
        startActivity(context, false, intent, 0);
    }

    public void gotoLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(context, false, intent, 0);
    }

    public void gotoResetPwd(Context context) {
        Intent intent = new Intent(context, ResetPwdActivity.class);
        startActivity(context, false, intent, 0);
    }

    public void gotoSign(Context context, int type, boolean isFinishAfterSuccess) {
        Intent intent = SignActivity.createIntent(context, type, isFinishAfterSuccess);
        startActivity(context, false, intent, 0);
    }

    public void gotoSignForResult(Context context, int type, boolean isFinishAfterSuccess, int RequestCode) {
        Intent intent = SignActivity.createIntent(context, type, isFinishAfterSuccess);
        startActivity(context, true, intent, RequestCode);
    }

    public void gotoChangeRankCityForResult(Context context, City city, int RequestCode) {
        Intent intent = ChangeRankCityActivity.createIntent(context, city);
        startActivity(context, true, intent, RequestCode);
    }

    public void gotoCourse(Context context, long courseId) {
        Intent intent = CourseActivity.createIntent(context, courseId);
        startActivity(context, false, intent, 0);
    }

    public void gotoCourseFromLocal(Context context, Course course) {
        Intent intent = CourseActivity.createIntentFromLocal(context, course);
        startActivity(context, false, intent, 0);
    }

    public void gotoCourse(Context context, long courseId,
                           long ablumId, int level) {
        Intent intent = CourseActivity.createIntent(context, courseId, ablumId, level);
        startActivity(context, false, intent, 0);
    }

    public void gotoCourse(Context context, long courseId,
                           String gid, String gotyeGroupId, String tid) {
        Intent intent = CourseActivity.createIntent(context, courseId, gid, gotyeGroupId, tid);
        startActivity(context, false, intent, 0);
    }

    public void gotoDubRoom(Context context, Course course,
                            boolean isLocal) {
        Intent intent = DubbingActivity.createIntent(context, course, isLocal);
        startActivity(context, false, intent, 0);
    }

    public void gotoDubRoom(Context context, Course course,
                            boolean isLocal, String groupId, String gytoGroupId, String taskId) {
        Intent intent = DubbingActivity.createIntent(context, course, isLocal, groupId, gytoGroupId, taskId);
        startActivity(context, false, intent, 0);
    }

    public void gotoShare(Context context, String name,
                          String videoShareUrl, String title, String localCoverPath,
                          boolean isFromShow, String picWebUrl, int dubbingArtId,
                          Long courseId, String videoUrl, Course course, String groupId,
                          String shareTalk) {
        Intent intent = ShareActivity.createIntent(context, name, videoShareUrl, title, localCoverPath, isFromShow, picWebUrl, dubbingArtId, courseId, videoUrl, course, groupId, shareTalk);
        startActivity(context, false, intent, 0);

    }

    public void gotoShare(Context context, String name,
                          String videoShareUrl, String title, String localCoverPath,
                          boolean isFromShow, String picWebUrl, int dubbingArtId,
                          Long courseId, String videoUrl, DubbingArt dubbingArt,
                          String shareTalk) {
        Intent intent = ShareActivity.createIntent(context, name, videoShareUrl, title, localCoverPath, isFromShow, picWebUrl, dubbingArtId, courseId, videoUrl, dubbingArt, shareTalk);
        startActivity(context, false, intent, 0);
    }

    public void gotoSearch(Context context) {
        Intent intent = SearchActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoSearchCourseContent(Context context) {
        Intent intent = SearchCourseContentActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoFeedback(Context context) {
        Intent intent = FeedBackActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoAboutUs(Context context) {
        Intent intent = AboutUsActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoBindDetailForResult(Context context, int i, int requestUnbind) {
        Intent intent = BindDetailActivity.createIntent(context, i);
        startActivity(context, true, intent, requestUnbind);
    }

    public void gotoChangeNickName(Context context) {
        Intent intent = ChangeNicknameActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoChangeSignature(Context context) {
        Intent intent = ChangeSignatureActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoDisclaim(Context context) {
        Intent intent = DisclaimActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoMessageNotify(Context context) {
        Intent intent = MessageNotifyActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoPersonInfo(Context context) {
        Intent intent = PersonInfoActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoSelectCity(Context context, int type,
                               String provinceCode, int requestCode) {
        Intent intent = SelectProvinceActivity.createIntent(context, type, provinceCode);
        startActivity(context, true, intent, requestCode);
    }

    public void gotoSetting(Context context, int requestCodeSetting) {
        Intent intent = SettingActivity.createIntent(context);
        startActivity(context, true, intent, requestCodeSetting);
    }

    public void gotoSuggestVideo(Context context, String content) {
        Intent intent = SuggestVideoActivity.createIntent(context, content);
        startActivity(context, false, intent, 0);
    }

    public void gotoUserInfo(Context context, SpaceInfo info, int uid) {
        Intent intent = UserInfoActivity.createIntent(context, info, uid);
        startActivity(context, false, intent, 0);
    }

    public void gotoAttention(Context context, int mUid) {
        Intent intent = AttentionActivity.createIntent(context, mUid);
        startActivity(context, false, intent, 0);
    }


    public void gotoCourseCache(Context context) {
        Intent intent = CourseCacheActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoCourseCollect(Context context) {
        Intent intent = CourseCollectActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoDubbingList(Context context, int uid, String fromtag, int requestCode) {
        Intent intent = DubbingListActivity.createIntent(context, uid, fromtag);
        startActivity(context, true, intent, requestCode);
    }

    public void gotoFans(Context context, int mUid) {
        Intent intent = FansActivity.createIntent(context, mUid);
        startActivity(context, false, intent, 0);
    }

    public void gotoCommentMessage(Context context, int commentResquestCode) {
        Intent intent = CommentMessageActivity.createIntent(context);
        startActivity(context, true, intent, commentResquestCode);
    }

    public void gotoGoodMessage(Context context, int commentResquestCode) {
        Intent intent = GoodMessageActivity.createIntent(context);
        startActivity(context, true, intent, commentResquestCode);
    }

    public void gotoUserMessage(Context context, ChatMessage message, boolean flag) {
        Intent intent = UserMessageActivity.createIntent(context, message, flag);
        startActivity(context, false, intent, 0);
    }

    public void gotoXujMessage(Context context, int requestCode) {
        Intent intent = XujMessageActivity.createIntent(context);
        startActivity(context, true, intent, requestCode);
    }

    public void gotoMessageManage(Context context, int requestCode) {
        Intent intent = MessageManageActivity.createIntent(context);
        startActivity(context, true, intent, requestCode);
    }

    public void gotoPhoto(Context context, int uid) {
        Intent intent = PhotoActivity.createIntent(context, uid);
        startActivity(context, false, intent, 0);
    }

    public void gotoRecommendRank(Context context) {
        Intent intent = RecommendRankActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoWebView(Context context, String url, String title) {
        Intent intent = WebViewActivity.createIntent(context, url, title);
        startActivity(context, false, intent, 0);
    }

    public void gotoWordBook(Context context) {
        Intent intent = WordBookActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }

    public void gotoChangeSchool(Context context) {
        Intent intent = ChangeSchoolActivity.createIntent(context);
        startActivity(context, false, intent, 0);
    }
}
