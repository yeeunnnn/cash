package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;

@WebServlet("/login")
public class LoginController extends HttpServlet {

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //session 인증 검사 코드
	   HttpSession session = request.getSession();
	   if(session.getAttribute("loginMember")!=null) {
		   response.sendRedirect(request.getContextPath()+"/cashbook");
	   }
	   // 쿠키에 저장된 아이디가 있다면 request속성에 저장
			Cookie[] cookies = request.getCookies(); // 키:값 키:값 키:값... 들어있는데 배열로 다 받음
			if(cookies != null) {
				for(Cookie c : cookies) {
					if(c.getName().equals("loginId") == true) {
						request.setAttribute("loginId", c.getValue());
					}
				}
			}
	   request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String memberId = request.getParameter("memberId");
	   String memberPw = request.getParameter("memberPw");
	   
	   Member member = new Member(memberId, memberPw, null, null);
	   
	   MemberDao memberDao = new MemberDao();
	   Member loginMember = memberDao.selectMemberById(member);
	   
	   if(loginMember == null) { // null이면 로그인 실패
		   System.out.println("로그인 실패");
		   response.sendRedirect(request.getContextPath()+"/login");
		   return;
	   }
	   
	   // 로그인 성공 시 : session 사용
	   HttpSession session = request.getSession();
	   System.out.println("로그인 성공");
	   session.setAttribute("loginMember", loginMember);
	   // idSave체크값이 넘어왔다면
			if(request.getParameter("idSave")!=null) {
				Cookie loginIdCookie = new Cookie("loginId", loginMember.getMemberId()); // defalt생성자 '()'는 사용못함. cookie하나가 곧 맵이므로 String:String 형태로 저장해야함.
																						 // (poperties)의 자식. 키:값 키:값 cookie.put 이런식으로도 가능.
				// loginIdCookie.setMaxAge(60*60*24); // 초단위. 추후 설정.
				response.addCookie(loginIdCookie); // 클라이언트 브라우저에 쿠키를 전송한 후 저장.
			}
	   response.sendRedirect(request.getContextPath()+"/cashbook");
	   
   }

}