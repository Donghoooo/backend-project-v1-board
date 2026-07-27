package bitc.fullstack503.springpt.service;
import bitc.fullstack503.springpt.data.entity.MemberEntity;
public interface MemberService
{
  boolean isUserInfo (String id, String pw);
  
  boolean isUserId (String id);
  
  boolean isUserName (String name);
  
  MemberEntity getUserInfo (String id);
  
  MemberEntity getNickInfo (String nickName);
}
