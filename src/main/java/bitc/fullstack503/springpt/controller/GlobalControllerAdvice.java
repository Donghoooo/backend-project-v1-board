package bitc.fullstack503.springpt.controller;
import bitc.fullstack503.springpt.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
@ControllerAdvice
public class GlobalControllerAdvice
{
  @Autowired
  private BoardService boardService;
  
  @ModelAttribute ("sideCate")
  public List<String> populateSideCate ()
  {
    return boardService.boardCate ();
  }
}
