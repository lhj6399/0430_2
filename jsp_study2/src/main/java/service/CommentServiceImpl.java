package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import repository.CommentDAO;
import repository.CommentDAOImpl;

public class CommentServiceImpl implements CommentService{

	// 로그 객체
	private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
		
	// DAO 객체 연결
	private CommentDAO cdao; // repository => interface
	
	public CommentServiceImpl() {
		cdao = new CommentDAOImpl(); 
		// repository => class implement MemberDAO
	}

	@Override
	public int post(CommentVO cvo) {
		// TODO Auto-generated method stub
		log.info("post service check 2");
		return cdao.insert(cvo);
	}

	@Override
	public List<CommentVO> getList(int bno) {
		// TODO Auto-generated method stub
		log.info("getList service check 2");
		return cdao.getList(bno);
	}

	@Override
	public int remove(int cno) {
		log.info("remove service check 2");
		return cdao.delete(cno);
	}

	@Override
	public int modify(CommentVO cvo) {
		log.info("modify service check 2");
		return cdao.update(cvo);
	}

//	public int removeAll(int bno) {
//		// TODO Auto-generated method stub
//		int isOk = sql.delete("CommentMapper.all",bno);
//		if(isOk>=0) {
//			sql.commit();
//		}
//		return isOk;
//	}
	
}
