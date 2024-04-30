package repository;

import java.util.List;

import domain.MemberVO;

public interface MemberDAO {

	int register(MemberVO mvo);

	MemberVO getID(MemberVO mvo);

	int lastLogin(String id);

	int update(MemberVO mvo);

	List<MemberVO> selectList();

	int delete(String id);

}
