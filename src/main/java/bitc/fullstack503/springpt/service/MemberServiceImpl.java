package bitc.fullstack503.springpt.service;
import bitc.fullstack503.springpt.data.entity.MemberEntity;
import bitc.fullstack503.springpt.data.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MemberServiceImpl implements MemberService
{
  @Autowired
  private MemberRepository memberRepository;
  
  @Override
  public boolean isUserInfo (String id, String pw)
  {
    return memberRepository.existsByIdAndPw (id, pw);
  }
  
  @Override
  public boolean isUserId (String id)
  {
    return memberRepository.existsById (id);
  }
  
  @Override
  public boolean isUserName (String name)
  {
    return memberRepository.existsByNickName (name);
  }
  
  @Override
  public MemberEntity getUserInfo (String id)
  {
    return memberRepository.getMemberEntityById (id);
  }
  
  @Override
  public MemberEntity getNickInfo (String nickName)
  {
    return memberRepository.findByNickName (nickName);
  }
}
