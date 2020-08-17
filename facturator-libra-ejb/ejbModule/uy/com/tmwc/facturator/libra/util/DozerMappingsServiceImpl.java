package uy.com.tmwc.facturator.libra.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.dozer.DozerBeanMapper;
import org.dozer.DozerEventListener;

@Stateless
public class DozerMappingsServiceImpl implements DozerMappingsService {

	private static final DozerBeanMapper mapper = new DozerBeanMapper();

	static {
		ArrayList<String> files = new ArrayList<String>();
		files.add("dozerMappings.xml");
		mapper.setMappingFiles(files);

		List<DozerEventListener> list = new ArrayList<DozerEventListener>();
		list.add(new EmpIdDozerListener());
		mapper.setEventListeners(list);

	}

	public DozerBeanMapper getDozerBeanMapper() {
		return mapper;
	}

}