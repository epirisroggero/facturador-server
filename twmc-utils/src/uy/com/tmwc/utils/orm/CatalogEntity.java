package uy.com.tmwc.utils.orm;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface CatalogEntity
{
	String prefix() default "";
	
	boolean abreviated() default false;
	
	boolean useNamedQuery() default false;
	
	boolean restrictEmpId() default true;
}
