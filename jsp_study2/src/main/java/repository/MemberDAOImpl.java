package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import orm.DatabaseBuilder;

public class MemberDAOImpl implements MemberDAO {
	
	// 로그 객체
	private static final Logger log = LoggerFactory.getLogger(MemberDAOImpl.class);
	
	// sqlSession 객체
	private SqlSession sql;
	
	public MemberDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int register(MemberVO mvo) {
		// TODO Auto-generated method stub
		log.info("register dao in!!");
		int isOk = sql.insert("MemberMapper.add",mvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public MemberVO getID(MemberVO mvo) {
		// TODO Auto-generated method stub
		log.info("login dao in!!");
		return sql.selectOne("MemberMapper.login",mvo);
	}

	@Override
	public int lastLogin(String id) {
		// TODO Auto-generated method stub
		log.info("lastLogin dao in!!");
		int isOk = sql.update("MemberMapper.last",id);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int update(MemberVO mvo) {
		// TODO Auto-generated method stub
		log.info("update dao in!!");
		int isOk = sql.update("MemberMapper.update",mvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<MemberVO> selectList() {
		// TODO Auto-generated method stub
		log.info("select dao in!!");
		return sql.selectList("MemberMapper.list");
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		log.info("delete dao in!!");
		int isOk = sql.update("MemberMapper.delete",id);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}
	
}
