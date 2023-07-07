package listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import service.CounterService;

@WebListener
public class CountListener implements HttpSessionListener {
	private CounterService counterService;
    public void sessionCreated(HttpSessionEvent se)  {
    	
    	//현재 접속사 수 +1 -> application.attribute
    	System.out.println(se.getSession().getId() + "의 새로운 세션이 생성되었습니다.");
    	ServletContext application = se.getSession().getServletContext(); //session을 통해서 servletContext를 불러옴. 여기서는 바로 못부르기 때문.
    	int currentCounter = (Integer)(application.getAttribute("currentCounter"));
    	application.setAttribute("currentCounter", currentCounter+1); //새로운 세션이 생길때마다 덮어씌우겠다.
    	
    	// 오늘 접속자 수 +1 -> DB
    	this.counterService = new CounterService();
    	int counter = counterService.getCounter(); //오늘 카운트 수
    	if(counter == 0) {
    		counterService.addCounter();
    	} else {
    		counterService.modifyCounter();
    	}
    }

    public void sessionDestroyed(HttpSessionEvent se)  {
    	System.out.println(se.getSession().getId() + "세션이 소멸되었습니다..");
    	// 현재 접속자 수 -1 -> application.attribute
    	ServletContext application = se.getSession().getServletContext(); //session을 통해서 servletContext를 불러옴. 여기서는 바로 못부르기 때문.
    	int currentCounter = (Integer)(application.getAttribute("currentCounter"));
    	application.setAttribute("currentCounter", currentCounter-1);
    }
	
}
