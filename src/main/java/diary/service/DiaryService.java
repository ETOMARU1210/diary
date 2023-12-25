package diary.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diary.entity.Diary;
import diary.form.diary.GetForm;
import diary.form.diary.PostForm;
import diary.form.diary.PutForm;
import diary.repository.IDiaryDao;
 
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
    
    public Diary findById(int id) {
    	try {
    		return dao.findById(id);
    	} catch(IncorrectResultSizeDataAccessException e) {
    		return null;
    	}
    }
    
    public int update(PutForm form) {
    	return dao.update(form);
    }
    
    public int delete(int id) {
    	return dao.delete(id);
    }
}