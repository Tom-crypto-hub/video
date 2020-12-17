package com.martinwj.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: SysException
 * @Description: TODO 自定义拦截器
 * @author: martin-wj
 * @createDate: 2020-12-01
 */
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 预处理，controller方法执行前
     * return true 放行，执行下一个拦截器，如果没有，执行controller中的方法
     * return false不放行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor1执行了...前");
        // 获取请求的url
        String url = request.getRequestURI();

        // 放行链接
        if (url.indexOf("login") >= 0 || url.indexOf("portal") >= 0 || url.indexOf("api/") >= 0) {
            return true;
        }
        // 判断session
        HttpSession session = request.getSession();
        // 从session中取出用户身份信息

        // session存在并且是管理员时，放行

        return true;

        // 执行这里表示用户身份需要认证，跳转登陆页面
//        request.getRequestDispatcher("/WEB-INF/page/admin/login.jsp").forward(request, response);

//        return false;
    }

    /**
     * 后处理方法，controller方法执行后，success.jsp执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor1执行了...后");
        // request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
    }

    /**
     * success.jsp页面执行后，该方法会执行
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyInterceptor1执行了...最后");
    }

}












