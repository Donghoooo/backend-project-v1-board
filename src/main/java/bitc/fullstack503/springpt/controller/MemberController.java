package bitc.fullstack503.springpt.controller;
import bitc.fullstack503.springpt.data.entity.MemberEntity;
import bitc.fullstack503.springpt.data.repository.MemberRepository;
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
@Controller
public class MemberController
{
  @Autowired
  private MemberService memberService;
  @Autowired
  private MemberRepository memberRepository;
  
  @RequestMapping ("/login")
  public ModelAndView login () throws Exception
  {
    return new ModelAndView ("login/login");
  }
  
  @RequestMapping ("/loginProcess")
  public String loginProcess (@RequestParam ("userId") String id, @RequestParam ("userPw") String pw, HttpServletRequest request) throws Exception
  {
    boolean result = memberService.isUserInfo (id, pw);
    if (result)
    {
      MemberEntity member = memberService.getUserInfo (id);
      HttpSession session = request.getSession ();
      session.setAttribute ("userId", member.getId ());
      session.setAttribute ("userPw", member.getPw ());
      session.setAttribute ("userName", member.getNickName ());
      session.setMaxInactiveInterval (60 * 60 * 2);
      return "redirect:/index";
    }
    else
    {
      return "redirect:/login?errMsg=" + URLEncoder.encode ("로그인 정보가 다릅니다.", StandardCharsets.UTF_8);
    }
  }
  
  @GetMapping ("/logout")
  public ResponseEntity<String> logout (HttpServletRequest request)
  {
    HttpSession session = request.getSession ();
    session.removeAttribute ("userId");
    session.removeAttribute ("userPw");
    session.removeAttribute ("userName");
    session.invalidate ();
    // 클라이언트에게 "reload" 응답을 보내어 페이지 리로드 요청
    return ResponseEntity.ok ("reload");
  }
  
  @RequestMapping ("/signup")
  public ModelAndView singup () throws Exception
  {
    return new ModelAndView ("login/signup");
  }
  
  @RequestMapping ("/signupProcess")
  public String signupProcess (@RequestParam ("userId") String id, @RequestParam ("userPw") String pw, @RequestParam ("userName") String name) throws Exception
  {
    boolean isId = memberService.isUserId (id);
    boolean isName = memberService.isUserName (name);
    if (isId)
    {
      return "redirect:/signup?errMsg=" + URLEncoder.encode ("이미 있는 아이디거나 있는 이름 입니다.", StandardCharsets.UTF_8);
    }
    else if (isName)
    {
      return "redirect:/signup?errMsg=" + URLEncoder.encode ("이미 있는 아이디거나 있는 이름 입니다.", StandardCharsets.UTF_8);
    }
    else
    {
      MemberEntity member = new MemberEntity ();
      member.setId (id);
      member.setPw (pw);
      member.setNickName (name);
      MemberEntity save = memberRepository.save (member);
      return "redirect:/login";
    }
  }
}
