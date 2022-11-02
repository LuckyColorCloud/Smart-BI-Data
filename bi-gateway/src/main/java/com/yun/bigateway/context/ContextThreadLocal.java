package com.yun.bigateway.context;

/**
 * @author Sober
 */
public class ContextThreadLocal {

    /**
     * 父子线程 传递
     */
    public static final ThreadLocal<RequestForWebFlux> REQUEST_FOR_WEB_FLUX_THREAD_LOCAL = new InheritableThreadLocal<>();

    public static RequestForWebFlux getRequest() {
        return REQUEST_FOR_WEB_FLUX_THREAD_LOCAL.get();
    }

    public static void setRequest(RequestForWebFlux requestForWebFlux) {
        REQUEST_FOR_WEB_FLUX_THREAD_LOCAL.set(requestForWebFlux);
    }

    public static void clearRequest() {
        REQUEST_FOR_WEB_FLUX_THREAD_LOCAL.remove();
    }
}
