package bitc.fullstack503.springpt.service;
import bitc.fullstack503.springpt.data.entity.BoardEntity;
import bitc.fullstack503.springpt.data.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BoardServiceImpl implements BoardService
{
  @Autowired
  private BoardRepository boardRepository;
  
  @Override
  public List<BoardEntity> selectBoardList ()
  {
    return boardRepository.findAllByOrderBySeqBoardDescNotNotice ();
  }
  
  @Override
  public List<String> boardCate ()
  {
    return boardRepository.findDistinctBoardCate ();
  }
  
  @Override
  public List<BoardEntity> indexBoard ()
  {
    return boardRepository.findFirst6ByOrderBySeqBoardDesc ();
  }
  
  @Override
  public List<BoardEntity> selectCateList (String boardCate)
  {
    return boardRepository.findByBoardCateOrderBySeqBoardDesc (boardCate);
  }
  
  @Override
  public BoardEntity boardDetail (long seqBoard)
  {
    return boardRepository.findById (seqBoard).orElseThrow (() -> new RuntimeException ("Board not found"));
  }
  
  @Override
  public void save (BoardEntity board)
  {
    boardRepository.save (board);
  }
  
  @Transactional
  @Override
  public void deleteBoard (long seqBoard)
  {
    boardRepository.deleteById (seqBoard);
  }
  
  @Transactional
  @Override
  public void boardLikeCount (long seqBoard)
  {
    boardRepository.updateLikeBoard (seqBoard);
  }
}
