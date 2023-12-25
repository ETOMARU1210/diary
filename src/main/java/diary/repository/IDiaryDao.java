package diary.repository;

import java.util.List;
import diary.entity.Diary;
import diary.form.diary.GetForm;
import diary.form.diary.PostForm;

public interface IDiaryDao {
	List<Diary> findList(GetForm form);
	//日記を登録する
	int insert(PostForm form);
}
