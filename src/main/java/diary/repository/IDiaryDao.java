package diary.repository;

import java.util.List;

import diary.entity.Diary;
import diary.form.diary.GetForm;
import diary.form.diary.PostForm;
import diary.form.diary.PutForm;

public interface IDiaryDao {
	List<Diary> findList(GetForm form);
	//日記を登録する
	int insert(PostForm form);
	
	//idを指定して日記を1件取得
	Diary findById(int id);
	
	//日記を更新する
	int update(PutForm form);
	
	//日記を削除する
	int delete(int id);
}
