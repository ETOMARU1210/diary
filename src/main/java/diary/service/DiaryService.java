package diary.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import diary.repository.IDiaryDao;
import diary.entity.Diary;
import diary.form.diary.GetForm;
import diary.form.diary.PostForm;
 
@Service
@Transactional
public class DiaryService {
 
    private final IDiaryDao dao;
 
    @Autowired
    public DiaryService(IDiaryDao dao) {
        this.dao = dao;
    }
 
    public List<Diary> findList(GetForm form) {
        return dao.findList(form);
    }
    
    public int insert(PostForm form) {
    	return dao.insert(form);
    }
}