package bitc.fullstack503.springpt.controller;
import bitc.fullstack503.springpt.data.entity.BoardEntity;
import bitc.fullstack503.springpt.data.entity.MemberEntity;
import bitc.fullstack503.springpt.service.BoardService;
import bitc.fullstack503.springpt.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Controller
public class BoardController
{
  @Autowired
  private BoardService boardService;
  @Autowired
  private MemberService memberService;
  
  @GetMapping ({"/", "index"})
  public ModelAndView mainPage () throws Exception
  {
    ModelAndView mv = new ModelAndView ("index");
    List<BoardEntity> indexBoard = boardService.indexBoard ();
    mv.addObject ("indexBoard", indexBoard);
    return mv;
  }
  
  @RequestMapping ("/editBanner")
  public ModelAndView editBanner () throws Exception
  {
    ModelAndView mv = new ModelAndView ("editBanner");
    return mv;
  }
  
  @GetMapping ("/boardList")
  public ModelAndView selectBoardList () throws Exception
  {
    ModelAndView mv = new ModelAndView ("boardList");
    List<BoardEntity> boardList = boardService.selectBoardList ();
    mv.addObject ("boardList", boardList);
    return mv;
  }
  
  @GetMapping ("/boardList/{sidecate}")
  public ModelAndView selectCateList (@PathVariable ("sidecate") String category) throws Exception
  {
    ModelAndView mv = new ModelAndView ("boardList");
    List<BoardEntity> board = boardService.selectCateList (category);
    mv.addObject ("boardList", board);
    return mv;
  }
  
  @RequestMapping ("/boardWrite")
  public ModelAndView boardWritePage () throws Exception
  {
    ModelAndView mv = new ModelAndView ("boardWrite");
    List<String> boardCate = boardService.boardCate ();
    mv.addObject ("boardCate", boardCate);
    return mv;
  }
  
  @GetMapping ("/boardUpdate/{seqBoard}")
  public ModelAndView boardUpdatePage (@PathVariable ("seqBoard") long seqBoard) throws Exception
  {
    ModelAndView mv = new ModelAndView ("boardUpdate");
    BoardEntity board = new BoardEntity ();
    board = boardService.boardDetail (seqBoard);
    List<String> boardCate = boardService.boardCate ();
    mv.addObject ("boardCate", boardCate);
    mv.addObject ("board", board);
    return mv;
  }
  
  @RequestMapping ("/insertBoard")
  public String insertBoard (@RequestParam ("title") String title, @RequestParam ("boardCate") String cate, @RequestParam ("content") String content, @RequestParam ("customCategory") String customCate, HttpServletRequest request) throws Exception
  {
    boolean t = false;
    boolean c = false;
    boolean a = false;
    if (title != null && !title.isBlank ()) t = true;
    if (cate.isBlank ())
    {
      cate = customCate;
    }
    if (cate != null && !cate.isBlank ()) a = true;
    if (content != null && !content.isBlank ()) c = true;
    if (t && c && a)
    {
      HttpSession session = request.getSession ();
      Object name1 = session.getAttribute ("userName");
      if (name1 != null)
      {
        String name = name1.toString ();
        MemberEntity member = memberService.getNickInfo (name);
        BoardEntity board = new BoardEntity ();
        board.setMember_nick (member);
        board.setBoardTitle (title);
        board.setBoardCate (cate);
        board.setContent (content);
        boardService.save (board);
        return "redirect:/boardList";
      }
    }
    return "redirect:/boardWrite?ErrMsg=" + URLEncoder.encode ("필수항목이 비었습니다.", StandardCharsets.UTF_8);
  }
  
  @GetMapping ("boardDetail/{cateName}/{seqBoard}")
  public ModelAndView selectBoardDetail (@PathVariable ("seqBoard") long seqBoard) throws Exception
  {
    ModelAndView mv = new ModelAndView ("boardDetail");
    BoardEntity board = boardService.boardDetail (seqBoard);
    mv.addObject ("board", board);
    return mv;
  }
  
  @PostMapping ("boardDetail/{cateName}/{seqBoard}/like")
  public ResponseEntity<Integer> boardLikeCount (@PathVariable Long seqBoard, HttpServletRequest request)
  {
    HttpSession session = request.getSession ();
    Object name1 = session.getAttribute ("userName");
    if (name1 != null)
    {
      // 세션에 추천 기록이 없으면 새로 만듦
      Set<Long> likedBoards = (Set<Long>) session.getAttribute ("likedBoards");
      if (likedBoards == null)
      {
        likedBoards = new HashSet<> ();
      }
      // 이미 추천했는지 확인
      if (likedBoards.contains (seqBoard))
      {
        return ResponseEntity.status (HttpStatus.CONFLICT) // 409 Conflict
          .body (boardService.boardDetail (seqBoard).getLikeBoard ());
      }
      boardService.boardLikeCount (seqBoard);
      likedBoards.add (seqBoard); // 추천 기록에 추가
      session.setAttribute ("likedBoards", likedBoards);
      int likeCount = boardService.boardDetail (seqBoard).getLikeBoard ();
      return ResponseEntity.ok (likeCount);
    }
    return ResponseEntity.ok (boardService.boardDetail (seqBoard).getLikeBoard ());
  }
  
  @PutMapping ("/boardDetail/{seqBoard}")
  public String updateBoard (@PathVariable ("seqBoard") long seqBoard, @RequestParam ("title") String title, @RequestParam ("content") String content, @RequestParam ("boardCate") String boardCate, @RequestParam ("customCategory") String customCate, HttpServletRequest request) throws Exception
  {
    boolean t = false;
    boolean c = false;
    boolean a = false;
    if (title != null && !title.isBlank ()) t = true;
    if (boardCate.isBlank ())
    {
      boardCate = customCate;
    }
    if (boardCate != null && !boardCate.isBlank ()) a = true;
    if (content != null && !content.isBlank ()) c = true;
    if (t && c && a)
    {
      HttpSession session = request.getSession ();
      Object name1 = session.getAttribute ("userName");
      if (name1 != null)
      {
        String name = name1.toString ();
        String nick = boardService.boardDetail (seqBoard).getMember_nick ().getNickName ();
        if (name.equals (nick))
        {
          BoardEntity board = boardService.boardDetail (seqBoard);
          board.setBoardTitle (title);
          board.setContent (content);
          board.setBoardCate (boardCate);
          boardService.save (board);
          return "redirect:/boardList";
        }
      }
    }
    return "redirect:/boardUpdate/" + seqBoard + "?ErrMsg=" + URLEncoder.encode ("필수항목이 비었습니다.", StandardCharsets.UTF_8);
  }
  
  @DeleteMapping ("/boardDetail/{seqBoard}")
  public String deleteBoard (@PathVariable ("seqBoard") long seqBoard, HttpServletRequest request) throws Exception
  {
    BoardEntity board = boardService.boardDetail (seqBoard);
    HttpSession session = request.getSession ();
    Object name1 = session.getAttribute ("userName");
    if (name1 != null)
    {
      String name = name1.toString ();
      String nick = board.getMember_nick ().getNickName ();
      if (name.equals (nick))
      {
        boardService.deleteBoard (seqBoard);
        return "redirect:/boardList";
      }
    }
    String cate = board.getBoardCate ();
    return "redirect:/boardDetail/" + URLEncoder.encode (cate, StandardCharsets.UTF_8) + "/" + seqBoard + "?ErrMsg=" + URLEncoder.encode ("삭제 권한이 없습니다.", StandardCharsets.UTF_8);
  }
}
