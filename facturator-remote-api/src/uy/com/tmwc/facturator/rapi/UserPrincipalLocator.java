package uy.com.tmwc.facturator.rapi;

import java.security.Principal;

public final class UserPrincipalLocator {
	
	private UserPrincipalLocator() {
	}
	
	public static ThreadLocal<Principal> userPrincipalTL = new ThreadLocal<Principal>();

}
