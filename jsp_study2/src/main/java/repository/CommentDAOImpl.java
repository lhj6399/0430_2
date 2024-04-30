package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import orm.DatabaseBuilder;

public class CommentDAOImpl implements CommentDAO {

	private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);
	
	private SqlSession sql;
	
	public CommentDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(CommentVO cvo) {
		// TODO Auto-generated method stub
		log.info("post DAO check 3");
		int isOk = sql.insert("CommentMapper.post",cvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<CommentVO> getList(int bno) {
		// TODO Auto-generated method stub
		log.info("list DAO check 3");
		return sql.selectList("CommentMapper.list",bno);
		
	}

	@Override
	public int delete(int cno) {
		log.info("remove DAO check 3");
		int isOk = sql.delete("CommentMapper.del",cno);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int update(CommentVO cvo) {
		log.info("update dao in!!");
		int isOk = sql.update("CommentMapper.update",cvo);
		// insert, update, delete는 DB가 변경되는 구문
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}
}
