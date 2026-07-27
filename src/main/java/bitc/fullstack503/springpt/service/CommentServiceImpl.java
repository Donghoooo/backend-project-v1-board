package bitc.fullstack503.springpt.service;
import bitc.fullstack503.springpt.data.entity.CommentEntity;
import bitc.fullstack503.springpt.data.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService
{
  @Autowired
  private CommentRepository commentRepository;
  
  @Override
  public CommentEntity getComment (long seqComment)
  {
    return commentRepository.findById (seqComment).orElseThrow (() -> new RuntimeException ("Comment not found"));
  }
  
  @Override
  public List<CommentEntity> commentList ()
  {
    return commentRepository.findAllByOrderBySeqCommentDesc ();
  }
  
  @Override
  public void commentSave (CommentEntity comment)
  {
    commentRepository.save (comment);
  }
  
  @Override
  public void deleteComment (long seqComment)
  {
    commentRepository.deleteById (seqComment);
  }
  
  @Transactional
  @Override
  public void commentLikeCount (long seqComment)
  {
    commentRepository.updateLikeComment (seqComment);
  }
}
