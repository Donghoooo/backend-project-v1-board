package bitc.fullstack503.springpt.service;
import bitc.fullstack503.springpt.data.entity.BoardEntity;

import java.util.List;
public interface BoardService
{
  List<BoardEntity> selectBoardList ();
  
  List<String> boardCate ();
  
  List<BoardEntity> indexBoard ();
  
  List<BoardEntity> selectCateList (String boardCate);
  
  BoardEntity boardDetail (long seqBoard);
  
  void save (BoardEntity board);
  
  void deleteBoard (long seqBoard);
  
  void boardLikeCount (long seqBoard);
}
