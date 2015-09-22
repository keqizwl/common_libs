package zwl.magic.com.common.model.database.word;

import com.lidroid.xutils.DbUtils;

import java.util.List;

public interface IWordHandler {
	 /**
     * 这里对方法做描述
     * 
     * @param dbUtils
     * @since V1.0
     */
    public void setDbUtils(DbUtils dbUtils);
    
    
    /**
     * 保存或更新消息列表
     * 
     * @param msgList
     * @return
     * @since V1.0
     */
    public boolean saveOrUpdateWordList(List<Word> wordList);
    
    public boolean saveOrUpdateWord(Word word);
    
    public List<Word> findAllWordListById(String uid);
    
    public List<Word> findAllWordListByWordName(String uid, String wordname);
    
    public List<Word> findAllWordListByUploadStatu(String uid, String uploadstatu);
    
    public boolean deleteByWordName(String uid, String wordname);
    
    public boolean deleteWordList(List<Word> wordList);
}
