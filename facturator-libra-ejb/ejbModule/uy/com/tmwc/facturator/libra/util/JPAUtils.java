package uy.com.tmwc.facturator.libra.util;

import java.util.List;

import javax.persistence.Query;

public class JPAUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getAtMostOne(Query query) {
		List list = query.getResultList();
		return (T) (list.size() == 0 ? null : list.get(0));
	}
	
	
}
