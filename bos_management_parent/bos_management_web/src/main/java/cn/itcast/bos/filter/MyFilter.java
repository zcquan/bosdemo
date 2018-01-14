package cn.itcast.bos.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class MyFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		
		    Subject subject = getSubject(request, response);
	        String[] perms = (String[]) mappedValue;
	        
	        if (perms != null && perms.length > 0) {
	           for (String string : perms) {
	        	  if( subject.isPermitted(string)){
	        		  return true;
	        	  }
			   }
	        }
	        return false;
	}

}
