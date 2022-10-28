package com.yun.bigateway.context;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Optional;

/**
 *  Servlet 请求
 * @author Yun
 *
 */
public class RequestForWebFlux {

	/**
	 * 底层Request对象
	 */
	protected ServerHttpRequest request;
	
	/**
	 * 实例化
	 * @param request request对象 
	 */
	public RequestForWebFlux(ServerHttpRequest request) {
		this.request = request;
	}
	
	/**
	 * 获取底层源对象 
	 */
	public Object getSource() {
		return request;
	}

	/**
	 * 在 [请求体] 里获取一个值 
	 */
	public String getParam(String name) {
		return request.getQueryParams().getFirst(name);
	}

	/**
	 * 在 [请求头] 里获取一个值 
	 */
	public String getHeader(String name) {
		return request.getHeaders().getFirst(name);
	}

	/**
	 * 在 [Cookie作用域] 里获取一个值 
	 */
	public String getCookieValue(String name) {
		Optional<HttpCookie> cookieOptional = Optional.ofNullable(request.getCookies().getFirst(name));
		if (cookieOptional.isPresent()){
			return cookieOptional.get().getValue();
		}
		return null;
	}

	/**
	 * 返回当前请求path (不包括上下文名称) 
	 */
	public String getRequestPath() {
		return request.getURI().getPath();
	}


	/**
	 * 返回当前请求的类型 
	 */
	public String getMethod() {
		return request.getMethodValue();
	}


	
}
