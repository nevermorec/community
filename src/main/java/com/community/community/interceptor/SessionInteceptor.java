package com.community.community.interceptor;

import com.community.community.mapper.UserMapper;
import com.community.community.model.User;
import com.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInteceptor implements HandlerInterceptor {
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie[] cookies = request.getCookies();
		boolean flag = false;
		if (cookies!=null && cookies.length!=0) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					String token = cookie.getValue();
					UserExample userExample = new UserExample();
					userExample.createCriteria().andTokenEqualTo(token);
					List<User> users = userMapper.selectByExample(userExample);
					if (users!=null && users.size()!=0) {
						request.getSession().setAttribute("user", users.get(0));
						flag = true;
					}
					break;
				}
			}
		}
		if (!flag) {
			request.setAttribute("msg","没有权限请先登陆");
//			request.getRequestDispatcher("/").forward(request,response);
			return true;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
