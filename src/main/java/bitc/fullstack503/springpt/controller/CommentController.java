package bitc.fullstack503.springpt.controller;
import bitc.fullstack503.springpt.data.entity.CommentEntity;
import bitc.fullstack503.springpt.data.entity.MemberEntity;
import bitc.fullstack503.springpt.service.CommentService;
import bitc.fullstack503.springpt.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Controller
public class CommentController
{
  @Autowired
  private CommentService commentService;
  @Autowired
  private MemberService memberService;
  
  @GetMapping ("/guestBook")
  public ModelAndView guestBook () throws Exception
  {
    ModelAndView mv = new ModelAndView ("guestBook");
    List<CommentEntity> commentList = commentService.commentList ();
    mv.addObject ("commentList", commentList);
    return mv;
  }
  
  @RequestMapping ("/guestBook")
  public String insertComment (@RequestParam ("input") String input, HttpServletRequest request) throws Exception
  {
    boolean i = false;
    if (input != null && !input.isBlank ()) i = true;
    if (i)
    {
      HttpSession session = request.getSession ();
      Object name1 = session.getAttribute ("userName");
      if (name1 != null)
      {
        String name = name1.toString ();
        MemberEntity member = memberService.getNickInfo (name);
        CommentEntity comment = new CommentEntity ();
        comment.setContent (input);
        comment.setMember_nick (member);
        commentService.commentSave (comment);
        return "redirect:/guestBook";
      }
    }
    return "redirect:/guestBook?ErrMsg=" + URLEncoder.encode ("필수항목이 비었습니다.", StandardCharsets.UTF_8);
  }
  
  @DeleteMapping ("/guestBook/{seqComment}")
  public ResponseEntity<String> deleteComment (@PathVariable ("seqComment") long seqComment, HttpServletRequest request) throws Exception
  {
    HttpSession session = request.getSession ();
    Object name1 = session.getAttribute ("userName");
    if (name1 != null)
    {
      String name = name1.toString ();
      String nick = commentService.getComment (seqComment).getMember_nick ().getNickName ();
      if (name.equals (nick))
      {
        commentService.deleteComment (seqComment);
        return ResponseEntity.ok ("redirect:/guestBook");
      }
    }
    String errMsg = URLEncoder.encode ("삭제 권한이 없습니다.", StandardCharsets.UTF_8);
    return ResponseEntity.ok ("redirect:/guestBook?ErrMsg=" + errMsg);
  }
  
  @PutMapping ("/guestBook/{seqComment}")
  public ResponseEntity<Integer> updateComment (@PathVariable ("seqComment") long seqComment, HttpServletRequest request) throws Exception
  {
    HttpSession session = request.getSession ();
    Object name1 = session.getAttribute ("userName");
    if (name1 != null)
    {
      commentService.commentLikeCount (seqComment);
      int likeCount = commentService.getComment (seqComment).getLikeComment ();
      return ResponseEntity.ok (likeCount);
    }
    return ResponseEntity.ok (commentService.getComment (seqComment).getLikeComment ());
  }
}
