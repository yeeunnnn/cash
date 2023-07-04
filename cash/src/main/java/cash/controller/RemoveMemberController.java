package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;

@WebServlet("/removeMember")
public class RemoveMemberController extends HttpServlet {
	// 비밀번호 입력 폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
		request.getRequestDispatcher("/WEB-INF/view/removeMember.jsp").forward(request, response);
	}
	
	// 탈퇴
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 값 받기
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		String memberPw = request.getParameter("memberPw");
		
		//모델 값 구하기(dao 메서드 호출)
		MemberDao memberDao = new MemberDao();
		int row = memberDao.removeMember(loginMember.getMemberId(), memberPw);
		
		// 탈퇴성공
		if(row==0) { //탈퇴 실패시
			System.out.println("탈퇴 실패");
			response.sendRedirect(request.getContextPath()+"/removeMember");
			return;
		} else if(row==1) {
			System.out.println("탈퇴 성공");
			session.invalidate();
			// jsp페이지로 포워드(디스패치)
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		} else {
			System.out.println("remove member error!");
		}
		
	}

}
