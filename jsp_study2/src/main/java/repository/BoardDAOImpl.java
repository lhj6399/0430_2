package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import orm.DatabaseBuilder;

public class BoardDAOImpl implements BoardDAO {

	private static final Logger log = LoggerFactory.getLogger(BoardDAOImpl.class);
	
	private SqlSession sql;
	
	public BoardDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	// 메서드 구현
	@Override
	public int insert(BoardVO bvo) {
		// TODO Auto-generated method stub
		log.info("insert dao in!!");
		
		// 실제 DB저장
		// sql.insert(mapperNamespace.id, bvo);
		int isOk = sql.insert("BoardMapper.add", bvo); 
		// insert, update, delete는 DB가 변경되는 구문
		if(isOk > 0) {
			sql.commit();
		}
		
		return isOk;
	}

	@Override
	public List<BoardVO> selectList(PagingVO pgvo) {
		log.info("select dao in!!");
		return sql.selectList("BoardMapper.list", pgvo);
	}

	@Override
	public BoardVO selectOne(int bno) {
		log.info("selectOne dao in!!");
		return sql.selectOne("BoardMapper.detail",bno);
	}

	@Override
	public int update(BoardVO bvo) {
		log.info("update dao in!!");
		int isOk = sql.update("BoardMapper.update",bvo);
		// insert, update, delete는 DB가 변경되는 구문
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int delete(int bno) {
		log.info("delete dao in!!");
		int isOk = sql.delete("BoardMapper.delete",bno);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return sql.selectOne("BoardMapper.cnt", pgvo);
	}

	@Override
	public String getFileName(int bno) {
		// TODO Auto-generated method stub
		return sql.selectOne("BoardMapper.getFileName",bno);
	}
}
