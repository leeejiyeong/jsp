package co.micol.prj.member.service;

//lombok이 세터세터 자동으로 만들어준다	//lombok안쓸거면 평소처럼 게터세터 만들어주면됨
import lombok.Getter;
import lombok.Setter;

// DTO
@Setter
@Getter
public class MemberVO {
	private String memberId;
	private String memberName;
	private String memberPassword;
	private int memberAge;
	private String memberAddress;
	private String memberTel;
	private String memberAuthor;
}
