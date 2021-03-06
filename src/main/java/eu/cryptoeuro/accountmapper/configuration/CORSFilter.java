package eu.cryptoeuro.accountmapper.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
	HttpServletRequest httprequest = (HttpServletRequest) req;

	String[] allowDomain = {"http://localhost:8100","http://wallet.euro2.ee"};
	Set<String> allowedOrigins = new HashSet<String>(Arrays.asList (allowDomain));
	String originHeader = httprequest.getHeader("Origin");
	String allowedResponse = (allowedOrigins.contains(originHeader)) ? originHeader : "*";

	response.setHeader("Access-Control-Allow-Origin", allowedResponse);

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        List<String> allowedHeaders = Arrays.asList(
                "x-requested-with",
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.USER_AGENT,
                HttpHeaders.ORIGIN,
                HttpHeaders.ACCEPT
                );
        response.setHeader("Access-Control-Allow-Headers",  String.join(", ", allowedHeaders));

        HttpServletRequest request = ((HttpServletRequest) req);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }
}
