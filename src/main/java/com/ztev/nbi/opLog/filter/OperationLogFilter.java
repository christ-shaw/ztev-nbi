package com.ztev.nbi.opLog.filter;

import com.ztev.nbi.opLog.model.OperationLog;
import com.ztev.nbi.opLog.repository.OperationLogRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/28.
 *
 * @copyright by ztev
 */
@WebFilter(filterName = "opfilter",urlPatterns = {"/api/*","/sign-up"})
public class OperationLogFilter implements Filter
{
    private static Logger logger = LoggerFactory.getLogger(OperationLogFilter.class.getName());
    @Autowired
    OperationLogRepo repo;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        OperationLog operationLog = OperationLog.builder().remoteAddress(req.getRemoteAddr()).method(req.getRequestURI()).timeStamp(new Date()).build();
        logger.info("attempt to insert operation " + operationLog.toString());
        repo.save(operationLog);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
