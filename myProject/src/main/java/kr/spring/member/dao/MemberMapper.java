package kr.spring.member.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.member.vo.MemberVO;

public interface MemberMapper {
	@Select("SELECT member_seq.nextval FROM dual")
	public int selectMem_num();
	@Insert("INSERT INTO spmember (mem_num,id) VALUES (#{mem_num},#{id})")
	public void insertMember(MemberVO member);
	@Insert("INSERT INTO spmember_detail (mem_num,name,passwd,phone,email,zipcode,address1,address2) VALUES (#{mem_num},#{name},#{passwd},#{phone},#{email},#{zipcode},#{address1},#{address2})")
	public void insertMember_detail(MemberVO member);//누락된 id까지 검색하기 위해 LEFT OUTER JOIN 사용
	@Select("SELECT m.mem_num,m.id,m.auth,d.passwd,d.photoname,d.email FROM spmember m LEFT OUTER JOIN spmember_detail d ON m.mem_num=d.mem_num WHERE m.id=#{id}")
	public MemberVO selectCheckMember(String id);//누락된id(탈퇴한 회원의 id)는 검색필요 X -> JOIN
	@Select("SELECT * FROM spmember m JOIN spmember_detail d ON m.mem_num=d.mem_num WHERE m.mem_num=#{mem_num}")
	public MemberVO selectMember(Integer mem_num);
	@Update("UPDATE spmember_detail SET name=#{name},phone=#{phone},email=#{email},zipcode=#{zipcode},address1=#{address1},address2=#{address2},modify_date=SYSDATE WHERE mem_num=#{mem_num}")
	public void updateMember(MemberVO member);
	@Update("UPDATE spmember_detail SET passwd=#{passwd} WHERE mem_num=#{mem_num}")
	public void updatePassword(MemberVO member);
	@Update("UPDATE spmember SET auth=0 WHERE mem_num=#{mem_num}")
	public void deleteMember(Integer mem_num);
	@Delete("DELETE FROM spmember_detail WHERE mem_num=#{mem_num}")
	public void deleteMember_detail(Integer mem_num);
	//프로필이미지업데이트
	@Update("UPDATE spmember_detail SET photo=#{photo},photoname=#{photoname} WHERE mem_num=#{mem_num}")
	public void updateProfile(MemberVO member);
}



