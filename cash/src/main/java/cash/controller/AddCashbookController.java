package cash.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.vo.*;
import cash.model.*;

@WebServlet("/addCashbook")
public class AddCashbookController extends HttpServlet {
	// 입력 폼으로 가는 것
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		if(loginMember == null) {
			   response.sendRedirect(request.getContextPath()+"/login");
			   return;
	    }
		// request 매개값
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		
		if (request.getParameter("targetYear") == null
		|| request.getParameter("targetMonth") == null
		|| request.getParameter("targetDate") == null) {
		    // 파라미터가 전달되지 않은 경우에 대한 처리
		    response.sendRedirect(request.getContextPath() + "/login");
		    return;
		}
		
		// 나머지는 입력 폼에서 입력 받도록
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("targetDate", targetDate);
		
		request.getRequestDispatcher("/WEB-INF/view/calendarOne.jsp").forward(request, response);
	}
	// 입력 액션으로 가는 것
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
		// session
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		// request 매개값
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		// cashbook 객체를 활용하기 위해 날짜를 결합
		String cashbookDate = String.valueOf(targetYear) + "-" + (targetMonth >= 10 ? targetMonth+1 : "0" + (targetMonth+1)) + "-" + (targetDate >= 10 ? targetDate : "0" + targetDate);
		System.out.println("cashbookDate: " + cashbookDate);

		// cashbook 객체에 데이터 담기
		Cashbook cashbook = new Cashbook();
		cashbook.setMemberId(loginMember.getMemberId());
		cashbook.setCategory(request.getParameter("category"));
		cashbook.setCashbookDate(String.valueOf(targetYear) + "-" + (targetMonth >= 10 ? targetMonth+1 : "0" + (targetMonth+1)) + "-" + (targetDate >= 10 ? targetDate : "0" + targetDate));
		cashbook.setPrice(Integer.parseInt(request.getParameter("price")));
		cashbook.setMemo(request.getParameter("memo"));
		
		// 디버깅
		System.out.println(cashbook.getMemberId());
		System.out.println(cashbook.getCategory());
		System.out.println(cashbook.getCashbookDate());
		System.out.println(cashbook.getPrice());
		System.out.println(cashbook.getMemo());
		
		CashbookDao cashbookDao = new CashbookDao();
		int cashbookNo = cashbookDao.insertCashbook(cashbook);// 키값 반환
		
		if(cashbookNo == 0) {
			System.out.println("입력실패");
			response.sendRedirect(request.getContextPath()+"/calendarOne?targetYear="+targetYear+"&targetMonth="+targetMonth+"&targetDate="+targetDate);
			return;
		} else {
		    System.out.println("입력성공. cashbookNo: " + cashbookNo);
		}
		// 입력 성공시 -> 해시태그가 있다면 -> 해시태그 추출하고 -> 해시태그 입력(반복문)
		// 해시태그 추출 알고리즘
		// # #구디 #구디 #자바
		HashtagDao hashtagDao = new HashtagDao();
		String memo = cashbook.getMemo();
		String memo2 = memo.replace("#", " #");//#구디 #아카데미 -> " #구디 #아카데미"
		
		Set<String> set = new HashSet<String>(); // 중복된 해시태그방지를 위해 set자료구조를 사용
		
		// 해시태그가 여러개이면 반복해서 입력
		for(String ht : memo2.split(" ")) {
			if (ht.startsWith("#")) {
				
				
				
				String ht2 = ht.replace("#", "");
				if(ht.length() > 0) {
					Hashtag hashtag = new Hashtag();
					hashtag.setCashbookNo(cashbookNo);
					hashtag.setWord(ht2);
					hashtagDao.insertHashtag(hashtag);
				}
			}
		}
		// redirect -> 날짜를 누르면 나오는 cashbookList? cashbookOne.
		response.sendRedirect(request.getContextPath()+"/calendarOne?targetYear="+targetYear+"&targetMonth="+targetMonth+"&targetDate="+targetDate);
	}

}
