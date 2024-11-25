package edu.kh.bcs.common.filter;

import java.io.IOException;

import edu.kh.bcs.myPage.dto.Member;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// HttpServletRequest/Response로 다운 캐스팅
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// Session 객체 얻어오기
		HttpSession session = req.getSession();
		
		
		if(session.getAttribute("loginMember")!=null) {
			Member loginMember = (Member) session.getAttribute("loginMember");
			if(loginMember.getMemberFl() == 1) {
				resp.sendRedirect("/");
			}
			else {
				chain.doFilter(request, response); // 다음 필터로 이동
			}
		}
		else {
			resp.sendRedirect("/");
		}

	}

}
