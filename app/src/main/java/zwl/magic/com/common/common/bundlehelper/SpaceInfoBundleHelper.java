package zwl.magic.com.common.common.bundlehelper;

import android.os.Bundle;

import com.ishowedu.child.peiyin.model.entity.SpaceInfo;

/**
 * Created by weilong zhou on 2015/7/27.
 * Email:zhouwlong@gmail.com
 */
public class SpaceInfoBundleHelper extends BaseBundleHelper<SpaceInfo> {
    private static final String KEY_SPACE_INFO = "space_info";
    private static final String KEY_TEACHER_TALK_INFO = "teacher_talk_info";
    private static final String KEY_UID = "uid";
    private Bundle mBundle;

    private static SpaceInfoBundleHelper mSpaceInfoBundleHelper;

    public static SpaceInfoBundleHelper getInstant() {
        if (mSpaceInfoBundleHelper == null) {
            mSpaceInfoBundleHelper = new SpaceInfoBundleHelper();
        }
        return mSpaceInfoBundleHelper;
    }

    private SpaceInfoBundleHelper() {
        mBundle = new Bundle();
    }

    @Override
    public SpaceInfo get() {
        return (SpaceInfo) mBundle.getSerializable(KEY_SPACE_INFO);
    }

    @Override
    public void put(SpaceInfo spaceInfo) {
        mBundle.putSerializable(KEY_SPACE_INFO, spaceInfo);
    }

    public void putUid(int uid) {
        mBundle.putInt(KEY_UID, uid);
    }

    public int getUid() {
        return mBundle.getInt(KEY_UID, 0);
    }
}
