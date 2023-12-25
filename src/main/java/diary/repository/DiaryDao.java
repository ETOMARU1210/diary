package diary.repository;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
 
import diary.entity.Diary;
import diary.form.diary.GetForm;
import diary.form.diary.PostForm;
 
@Repository
public class DiaryDao implements IDiaryDao {
     
    private final NamedParameterJdbcTemplate jdbcTemplate;
 
    @Autowired
    public DiaryDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
 
    @Override
    public List<Diary> findList(GetForm form) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT d.id, d.category, d.title, d.content, TO_CHAR(d.date, 'YYYY/MM/DD') AS date, d.update_datetime, c.name "
                + "FROM diary AS d INNER JOIN code AS c ON d.category = c.cd "
                + "WHERE c.group_cd = '01'");
     
        // パラメータ設定用Map
        Map<String, String> param = new HashMap<>();
        // パラメータが存在した場合、where句にセット
        if(form.getCategory() != null && form.getCategory() != "") {
            sqlBuilder.append(" AND c.cd = :cd");
            param.put("cd", form.getCategory());
        }
        if(form.getDate() != null && form.getDate() != "") {
            sqlBuilder.append(" AND TO_CHAR(d.date, 'YYYY/MM') = :date");
            param.put("date", form.getDate());
        }
     
        String sql = sqlBuilder.toString();
     
        //タスク一覧をMapのListで取得
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, param);
        //return用の空のListを用意
        List<Diary> list = new ArrayList<Diary>();
     
        //データをDiaryにまとめる
        for(Map<String, Object> result : resultList) {
            Diary diary = new Diary();
            diary.setId((int)result.get("id"));
            diary.setCategory((String)result.get("category"));
            diary.setTitle((String)result.get("title"));
            diary.setContent((String)result.get("content"));
            diary.setDate((String)result.get("date"));
            diary.setUpdate_datetime((Timestamp)result.get("update_datetime"));
            diary.setName((String)result.get("name"));
            list.add(diary);
        }
        return list;
    }
    
    @Override
    public int insert(PostForm form) {
    	int count = 0;
    	String sql = "INSERT INTO diary(category, title, content, date , update_datetime) "
    	            + "VALUES(:category, :title, :content, :date , :update_datetime)";
    	Map<String, Object> param = new HashMap<>();
    	param.put("category", form.getCategoryForm());
    	param.put("title", form.getTitleForm());
    	param.put("content", form.getContentForm());
    	param.put("date", form.getDateForm());
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	param.put("update_datetime", timestamp);
    	count = jdbcTemplate.update(sql, param);
    	return count;
    }
}