package taurus.mvc.http;

class HttpIncludeResponse {
	
	Object getServletResponse() {
		if(javaxResponse!=null)
		{
			return javaxResponse;
		}
		return jakartaResponse;
	}
	
	WrapperResponseForJakarta jakartaResponse;
	WrapperResponseForJavax javaxResponse;
	public HttpIncludeResponse(jakarta.servlet.http.HttpServletResponse response) {
		this.jakartaResponse=new WrapperResponseForJakarta(response);
	}
	public HttpIncludeResponse(javax.servlet.http.HttpServletResponse response) {
		this.javaxResponse=new WrapperResponseForJavax(response);;
	}
	public String getContent() throws Exception {
		if(javaxResponse!=null)
		{
			return javaxResponse.getContent();
		}
		return jakartaResponse.getContent();
	}
}
