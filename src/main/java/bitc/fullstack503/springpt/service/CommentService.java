package bitc.fullstack503.springpt.service;
import bitc.fullstack503.springpt.data.entity.CommentEntity;

import java.util.List;
public interface CommentService
{
  CommentEntity getComment (long seqComment);
  
  List<CommentEntity> commentList ();
  
  void commentSave (CommentEntity comment);
  
  void deleteComment (long seqComment);
  
  void commentLikeCount (long seqComment);
}
