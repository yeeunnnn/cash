package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootListener implements ServletContextListener {
	
    public void contextInitialized(ServletContextEvent sce)  { //ServletContextEvent sce 이벤트 객체의 주인
		System.out.println("실행확인 : ServletContextInitialized");
		
		ServletContext application = sce.getServletContext();
		application.setAttribute("currentCounter", 0);
		
		 try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MariaDB 드라이버 로딩 실패...");
			e.printStackTrace();
		}
         System.out.println("MariaDB 드라이버 로딩 성공...");
    }
	
}
