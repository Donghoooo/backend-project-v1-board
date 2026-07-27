package bitc.fullstack503.springpt.data.repository;
import bitc.fullstack503.springpt.data.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface CommentRepository extends JpaRepository<CommentEntity, Long>
{
  List<CommentEntity> findAllByOrderBySeqCommentDesc ();
  
  @Modifying
  @Query ("update CommentEntity c set c.likeComment = c.likeComment + 1 where c.seqComment = :seqComment")
  void updateLikeComment (@Param ("seqComment") Long seqComment);
}
