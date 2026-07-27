package bitc.fullstack503.springpt.data.repository;
import bitc.fullstack503.springpt.data.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>
{
  @Query ("SELECT b FROM BoardEntity b WHERE b.boardCate NOT IN ('공지사항') ORDER BY b.seqBoard DESC")
  List<BoardEntity> findAllByOrderBySeqBoardDescNotNotice ();
  
  @Query ("SELECT DISTINCT b.boardCate FROM BoardEntity b WHERE b.boardCate NOT IN ('공지사항')")
  List<String> findDistinctBoardCate ();
  
  List<BoardEntity> findFirst6ByOrderBySeqBoardDesc ();
  
  List<BoardEntity> findByBoardCateOrderBySeqBoardDesc (String boardCate);
  
  @Modifying
  @Query ("update BoardEntity b set b.likeBoard = b.likeBoard + 1 where b.seqBoard = :seqBoard")
  void updateLikeBoard (@Param ("seqBoard") Long seqBoard);
}
