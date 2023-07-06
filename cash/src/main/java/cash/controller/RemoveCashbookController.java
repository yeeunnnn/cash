package cash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cash.model.CashbookDao;
import cash.vo.*;

@WebServlet("/removeCashbook")
public class RemoveCashbookController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 값 받기
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		System.out.println(cashbookNo+" <-- cashbookNo");
		
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		
		//모델 값 구하기(dao 메서드 호출)
		CashbookDao cashbookDao = new CashbookDao();
		int hashtagCashbookNo = cashbookDao.selectCashbookNo(cashbookNo);
		int row = 0;
		if(hashtagCashbookNo > 0) {
			row = cashbookDao.removeCashbook(cashbookNo); //두개의 테이블에서 삭제
		} else {
			row = cashbookDao.removeOnlyCashbook(cashbookNo); //cashbook에서만 삭제
		}
		
		// 탈퇴성공
		if(row==0) { //탈퇴 실패시
			System.out.println("가계부 삭제 실패");
			response.sendRedirect(request.getContextPath() + "/calendarOne?targetYear=" + targetYear + "&targetMonth=" + targetMonth + "&targetDate=" + targetDate);
			return;
		} else if(row==1) {
			System.out.println("가계부 삭제 성공");
			// jsp페이지로 포워드(디스패치)
			response.sendRedirect(request.getContextPath() + "/calendarOne?targetYear=" + targetYear + "&targetMonth=" + targetMonth + "&targetDate=" + targetDate);
		} else {
			System.out.println("remove cashbook error!");
		}
	}

}
