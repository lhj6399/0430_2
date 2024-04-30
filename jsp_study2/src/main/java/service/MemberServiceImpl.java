package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import repository.MemberDAO;
import repository.MemberDAOImpl;

public class MemberServiceImpl implements MemberService{

	// 로그 객체
	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	// DAO 객체 연결
	private MemberDAO mdao; // repository => interface
	
	public MemberServiceImpl() {
		mdao = new MemberDAOImpl(); // repository => class implement MemberDAO
	}
	
	@Override
	public int register(MemberVO mvo) {
		// TODO Auto-generated method stub
		log.info("join service in!!");
		return mdao.register(mvo);
	}
	@Override
	public MemberVO login(MemberVO mvo) {
		// TODO Auto-generated method stub
		log.info("login service in!!");
		return mdao.getID(mvo);
	}
	@Override
	public int lastLogin(String id) {
		// TODO Auto-generated method stub
		log.info("lastLogin service in!!");
		return mdao.lastLogin(id);
	}
	@Override
	public int update(MemberVO mvo) {
		// TODO Auto-generated method stub
		log.info("update service in!!");
		return mdao.update(mvo);
	}
	@Override
	public List<MemberVO> getList() {
		// TODO Auto-generated method stub
		log.info("list service in!!");
		return mdao.selectList();
	}
	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		log.info("delete service in!!");
		return mdao.delete(id);
	}

}
