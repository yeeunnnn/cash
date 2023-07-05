package cash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.vo.*;
import cash.model.CashbookDao;

@WebServlet("/cashbookList")
public class CashbookListController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 구현
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		if(loginMember == null) {
			   response.sendRedirect(request.getContextPath()+"/login");
			   return;
	    }
		String memberId = loginMember.getMemberId();
		String word = request.getParameter("word");
		
		// 페이징 알고리즘
		int currentPage = 1;
		int rowPerPage = 5;
		int beginRow = (currentPage - 1) * rowPerPage; // 위 두 변수 이용해서 구하기
		CashbookDao cashbookDao = new CashbookDao();
		List<Cashbook> list = cashbookDao.selectCashbookListByTag(memberId, word, beginRow, rowPerPage);
		
		int totalRow = cashbookDao.cashbookTotalRow(memberId, word);
		int pagePerPage = 5;
		int lastPage = totalRow / rowPerPage; // pagePerPage X
		if(totalRow % rowPerPage != 0){
			lastPage = lastPage + 1;
		}
		int minPage = (((currentPage - 1) / pagePerPage) * pagePerPage) + 1; //하단 페이징에서 제일 작은 값
		int maxPage = minPage + (pagePerPage - 1); // 하단 페이징에서 제일 큰 값
		//마지막 번호가, 실제 페이지 번호보다 많지 않도록 처리
		if(maxPage > lastPage){
			maxPage = lastPage;
		}
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("beginRow", beginRow);
		request.setAttribute("rowPerPage", rowPerPage);
		request.setAttribute("pagePerPage", pagePerPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("minPage", minPage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("word", word);
		request.setAttribute("list", list);
		
		// 포워딩(redirect와 다른 성격의 같은 역할?
		request.getRequestDispatcher("/WEB-INF/view/cashbookListByTag.jsp").forward(request, response);
	}

}
