package uy.com.tmwc.facturator.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.dozer.util.ReflectionUtils;
import org.hibernate.Hibernate;
import org.hibernate.collection.PersistentCollection;

public class Mapper {
	IdentityHashMap<Object, Object> instantiationMemory = new IdentityHashMap();
	private ClassMapper classMapper;
	private static final Map<Object, Object> CONVERTER_MAP = new HashMap(10);

	static {
		CONVERTER_MAP.put(Integer.class, new IntegerConverter());
		CONVERTER_MAP.put(Double.class, new DoubleConverter());
		CONVERTER_MAP.put(Short.class, new ShortConverter());
		CONVERTER_MAP.put(Character.class, new CharacterConverter());
		CONVERTER_MAP.put(Long.class, new LongConverter());
		CONVERTER_MAP.put(Boolean.class, createBooleanConverter());
		CONVERTER_MAP.put(Byte.class, new ByteConverter());
		CONVERTER_MAP.put(Float.class, new FloatConverter());
		CONVERTER_MAP.put(BigDecimal.class, new BigDecimalConverter());
		CONVERTER_MAP.put(BigInteger.class, new BigIntegerConverter());
	}
	
	public static <T> List<T> mapList(Collection<?> source, Class<T> destinationClass) {
		return (List<T>) new Mapper().mapCollection(source, destinationClass);
	}

	public Mapper() {
	}

	public Mapper(ClassMapper classMapper) {
		this.classMapper = classMapper;
	}

	public <S, T> T map(S source, Class<T> destinationClass) {
		return (T) mapFullGraph(source, mapClass(source, destinationClass), null);
	}

	private Object mapFullGraph(Object source, Class<?> destinationClass, FieldMapping referenceFM) {

		// TODO manejar arrays.
		if (source.getClass().isArray()) {
			return null;
		}

		Object dest = this.instantiationMemory.get(source);
		if (dest != null) {
			return dest;
		}

		if ((source instanceof Collection)) {
			return mapCollection((Collection) source, referenceFM.getCollectionItemClass());
		}

		dest = createAndRetain(source, destinationClass);

		Collection<FieldMapping> fields = getFieldMappings(source.getClass(), dest.getClass());
		for (FieldMapping fieldMapping : fields) {
			Object value = fieldMapping.get(source);
			Object destValue;
			if (value != null) {
				destValue = handleVeryWellKnownClass(value);
				if (destValue == null)
					destValue = mapFullGraph(value, mapClass(value, fieldMapping.getDestinationType()), fieldMapping);
			} else {
				destValue = null;
			}
			fieldMapping.set(dest, destValue);
		}

		return dest;
	}

	private Class<?> mapClass(Object source, Class<?> clazz) {
		if (this.classMapper == null) {
			return clazz;
		}
		Class mappedClass = this.classMapper.map(source.getClass());
		return mappedClass != null ? mappedClass : clazz;
	}

	private Object handleVeryWellKnownClass(Object value) {
		if (((value instanceof Number)) || ((value instanceof String)) || ((value instanceof Boolean)) || ((value instanceof Character)))
			return value;
		if ((value instanceof Date)) {
			return new Date(((Date) value).getTime());
		}
		return null;
	}

	public <T> Collection<T> mapCollection(Collection<?> source, Class<T> destinationClass) {
		ArrayList destCollection = new ArrayList(source.size());
		this.instantiationMemory.put(source, destCollection);
		for (Iterator localIterator = source.iterator(); localIterator.hasNext();) {
			Object object = localIterator.next();
			Object destObject = map(object, destinationClass);
			destCollection.add(destObject);
		}
		return destCollection;
	}

	public <T> List<T> mapArrayQueryResult(List<Object[]> source, Class<T> wrapperClazz) {
		Constructor c = getFirstNonTrivialConstructor(wrapperClazz);

		ArrayList result = new ArrayList(source.size());
		int cArgsLen = c.getParameterTypes().length;
		for (Object[] arr : source) {
			Object[] cArgs = new Object[cArgsLen];
			for (int i = 0; i < cArgsLen; i++) {
				cArgs[i] = map(arr[i], c.getParameterTypes()[i]);
			}
			Object r = newInstance(c, cArgs);
			result.add(r);
		}

		return result;
	}

	public <T> T newInstance(Constructor<T> constructor, Object[] cArgs) {
		try {
			return constructor.newInstance(cArgs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <T> Constructor<T> getFirstNonTrivialConstructor(Class<T> wrapperClazz) {
		Constructor[] cs = wrapperClazz.getConstructors();
		Constructor ct = null;
		for (Constructor constructor : cs) {
			if (constructor.getParameterTypes().length > 0) {
				ct = constructor;
				break;
			}
		}
		if (ct == null) {
			throw new RuntimeException("No mapping defining constructor found");
		}
		Constructor c;
		try {
			c = wrapperClazz.getConstructor(ct.getParameterTypes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return c;
	}

	private Collection<FieldMapping> getFieldMappings(Class<? extends Object> srcClass, Class<? extends Object> destClass) {
		ArrayList fieldMappings = new ArrayList();

		Method[] methods = srcClass.getMethods();
		for (Method method : methods) {
			if ((method.getName().startsWith("get")) && (method.getParameterTypes().length == 0)) {
				Method targetMethod = findSetter(method, destClass);
				if (targetMethod == null) {
					continue;
				}

				fieldMappings.add(new TruchoFieldMapping(method, targetMethod));
			}

		}

		return fieldMappings;
	}

	private Method findSetter(Method getMethod, Class<? extends Object> destClass) {
		String propertyName = getMethod.getName().substring(3);
		Method[] methods = destClass.getMethods();
		for (Method method : methods) {
			String name = method.getName();
			if ((name.equals("set" + propertyName))
					&& (method.getParameterTypes().length == 1)
					&& ((!Collection.class.isAssignableFrom(getMethod.getReturnType())) || (Collection.class.isAssignableFrom(method
							.getParameterTypes()[0])))) {
				return method;
			}
		}
		return null;
	}

	private Object createAndRetain(Object source, Class<?> destinationClass) {
		Object res;
		try {
			res = destinationClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		this.instantiationMemory.put(source, res);
		return res;
	}

	private static BooleanConverter createBooleanConverter() {
		String[] trueStrings = { "yes", "y", "true", "on", "1", "SI", "S", "s" };
		String[] falseStrings = { "no", "n", "false", "off", "0", "NO", "N", "n", "" };

		return new BooleanConverter(trueStrings, falseStrings);

	}

	private static abstract interface FieldMapping {
		public abstract Object get(Object paramObject);

		public abstract void set(Object paramObject1, Object paramObject2);

		public abstract Class<?> getDestinationType();

		public abstract Class<?> getCollectionItemClass();
	}

	private static class TruchoFieldMapping implements Mapper.FieldMapping {
		private Method getMethod;
		private Method setMethod;

		public TruchoFieldMapping(Method getMethod, Method setMethod) {
			this.getMethod = getMethod;
			this.setMethod = setMethod;
		}

		public Object get(Object o) {
			Object value = null;
			try {
				value = this.getMethod.invoke(o, new Object[0]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			if ((value != null) && (PersistentCollection.class.isAssignableFrom(value.getClass())) && (!Hibernate.isInitialized(value))) {
				return null;
			}
			return value;
		}

		public Class<?> getDestinationType() {
			return this.setMethod.getParameterTypes()[0];
		}

		private Class<?> getNonPrimitiveDestinationType() {
			Class destinationType = getDestinationType();
			if (destinationType.isPrimitive()) {
				return MethodUtils.getPrimitiveWrapper(destinationType);
			}
			return destinationType;
		}

		public void set(Object o, Object value) {
			if (value != null) {
				Class destinationType = getNonPrimitiveDestinationType();
				if (!destinationType.isAssignableFrom(value.getClass()))
					value = convert(value, destinationType);
			}
			try {
				this.setMethod.invoke(o, new Object[] { value });
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private Object convert(Object value, Class<?> destinationType) {
			Converter converter = (Converter) Mapper.CONVERTER_MAP.get(destinationType);
			if (converter != null) {
				return converter.convert(destinationType, value);
			}

			return value;
		}

		public Class<?> getCollectionItemClass() {
			Class clazz = ReflectionUtils.determineGenericsType(this.setMethod, false);
			if (clazz == null) {
				throw new RuntimeException("Couldnt determine generics type for " + this.setMethod);
			}
			return clazz;
		}
	}
}