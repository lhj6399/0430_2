package service;

import java.util.List;

import domain.MemberVO;

public interface MemberService {

	int register(MemberVO mvo);

	MemberVO login(MemberVO mvo);

	int lastLogin(String id);

	int update(MemberVO mvo);

	List<MemberVO> getList();

	int delete(String id);

}
