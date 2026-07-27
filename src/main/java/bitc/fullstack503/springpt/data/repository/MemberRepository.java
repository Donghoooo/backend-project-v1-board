package bitc.fullstack503.springpt.data.repository;
import bitc.fullstack503.springpt.data.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface MemberRepository extends JpaRepository<MemberEntity, Long>
{
  boolean existsByIdAndPw (@Param ("userId") String id, @Param ("userPw") String pw);
  
  boolean existsById (@Param ("userId") String id);
  
  boolean existsByNickName (@Param ("userName") String nickName);
  
  MemberEntity getMemberEntityById (@Param ("userId") String id);
  
  MemberEntity findByNickName (String nickName);
}
