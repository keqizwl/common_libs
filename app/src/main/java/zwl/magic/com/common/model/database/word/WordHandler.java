package zwl.magic.com.common.model.database.word;

import android.database.sqlite.SQLiteFullException;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

public class WordHandler implements IWordHandler {

	/** 变量/常量说明 */
	private DbUtils mDbUtils;

	/**
	 * @param dbUtils
	 */
	public WordHandler(DbUtils dbUtils) {
		
		mDbUtils = dbUtils;

		try {
			mDbUtils.createTableIfNotExist(Word.class);
		} catch (DbException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void setDbUtils(DbUtils dbUtils) {
		
		mDbUtils = dbUtils;
	}

	@Override
	public boolean saveOrUpdateWordList(List<Word> wordList) {
		
		if (mDbUtils == null) {
			return false;
		}

		if (wordList == null || wordList.isEmpty()) {
			return false;
		}

		try {
			mDbUtils.saveOrUpdateAll(wordList);
		} catch (DbException e) {
			
			e.printStackTrace();
			return false;
		} catch (SQLiteFullException e) {
			
			// ToastUtils.show(context, "sdcard空间不足，请检查后重试");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean saveOrUpdateWord(Word word) {
		if (mDbUtils == null) {
			return false;
		}

		if (word == null) {
			return false;
		}

		try {
			mDbUtils.saveOrUpdate(word);
		} catch (DbException e) {
			
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public List<Word> findAllWordListByWordName(String uid, String wordname) {
		if (mDbUtils == null) {
			return null;
		}

		if (isEmpty(uid)) {
			return null;
		}

		if (isEmpty(wordname)) {
			return null;
		}

		List<Word> list = null;
		try {
			list = mDbUtils.findAll(Selector.from(Word.class)
					.where("uid", "=", uid)
					.and(WhereBuilder.b("word", "=", wordname)));
		} catch (DbException e) {
			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Word> findAllWordListById(String uid) {
		if (mDbUtils == null) {
			return null;
		}

		if (isEmpty(uid)) {
			return null;
		}

		List<Word> list = null;
		try {
			list = mDbUtils.findAll(Selector.from(Word.class).where("uid", "=",
					uid));
		} catch (DbException e) {
			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Word> findAllWordListByUploadStatu(String uid,
			String uploadstatu) {
		if (mDbUtils == null) {
			return null;
		}

		if (isEmpty(uid)) {
			return null;
		}

		if (isEmpty(uploadstatu)) {
			return null;
		}

		List<Word> list = null;
		try {
			list = mDbUtils.findAll(Selector.from(Word.class)
					.where("uid", "=", uid)
					.and(WhereBuilder.b("uploaded", "=", uploadstatu)));
		} catch (DbException e) {
			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean deleteByWordName(String uid, String wordname) {
		if (mDbUtils == null) {
			return false;
		}

		if (isEmpty(uid)) {
			return false;
		}

		if (isEmpty(wordname)) {
			return false;
		}
		try {
			mDbUtils.delete(Word.class,
					WhereBuilder.b("uid", "=", uid).and("word", "=", wordname));
		} catch (DbException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteWordList(List<Word> wordList) {
		if (mDbUtils == null) {
			return false;
		}

		if (wordList != null) {
			return false;
		}

		try {
			mDbUtils.deleteAll(wordList);
		} catch (DbException e) {
			
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}

		if (str.isEmpty()) {
			return true;
		}

		return false;
	}
}
