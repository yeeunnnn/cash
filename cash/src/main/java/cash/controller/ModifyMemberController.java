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

/**
 * Servlet implementation class ModifyMember
 */
@WebServlet("/modifyMember")
public class ModifyMemberController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/view/modifyMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 값 받기
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		String memberPw = request.getParameter("memberPw");
		
		//모델 값 구하기(dao 메서드 호출)
		MemberDao memberDao = new MemberDao();
		int row = memberDao.modifyMember(loginMember.getMemberId(), memberPw);
		
		// 수정 성공
		if(row==0) { // 수정 실패시
			System.out.println("수정 실패");
			response.sendRedirect(request.getContextPath()+"/modifyMember");
			return;
		} else if(row==1) {
			System.out.println("수정 성공");
			response.sendRedirect(request.getContextPath()+"/memberOne");
			return;
		} else {
			System.out.println("modify member error!");
		}	
	}

}
