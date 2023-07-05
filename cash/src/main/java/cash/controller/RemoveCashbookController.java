package cash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.*;
import cash.vo.*;

@WebServlet("/removeCashbook")
public class RemoveCashbookController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 값 받기
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		
		//모델 값 구하기(dao 메서드 호출)
		CashbookDao cashbookDao = new CashbookDao();
		int row = cashbookDao.removeCashbook(cashbookNo);
		
		// 탈퇴성공
		if(row==0) { //탈퇴 실패시
			System.out.println("가계부 삭제 실패");
			response.sendRedirect(request.getContextPath()+"/calendarOne");
			return;
		} else if(row==1) {
			System.out.println("가계부 삭제 성공");
			session.invalidate();
			// jsp페이지로 포워드(디스패치)
			request.getRequestDispatcher("/WEB-INF/view/calendar.jsp").forward(request, response);
		} else {
			System.out.println("remove cashbook error!");
		}
	}

}
