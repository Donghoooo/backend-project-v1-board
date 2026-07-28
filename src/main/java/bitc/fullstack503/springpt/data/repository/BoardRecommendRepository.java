package bitc.fullstack503.springpt.data.repository;

import bitc.fullstack503.springpt.data.entity.BoardRecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRecommendRepository extends JpaRepository<BoardRecommendEntity, Long> {
    // [가장 중요 ⭐]
    // 스프링 데이터 JPA의 마법 같은 기능입니다.
    // 메서드 이름(existsBy...)만 규칙에 맞게 이렇게 지어두면,
    // 스프링이 알아서 "SELECT EXISTS(SELECT 1 FROM board_recommend WHERE board_id = ? AND member_id = ?)"
    // 이라는 SQL 쿼리문을 내부적으로 뚝딱 만들어 실행해 줍니다.
    boolean existsByBoardIdAndMemberId(Long boardId, String memberId);
}