package uy.com.tmwc.facturator.server.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.interceptor.AroundInvoke;
import javax.persistence.EntityManager;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.intercept.Interceptor;
import org.jboss.seam.intercept.InvocationContext;

@Interceptor
public class ReplacePersistentBags
{
  @AroundInvoke
  public Object replacePersistentBags(InvocationContext context)
    throws Exception
  {
    Object result = context.proceed();
    if ((result != null) && (context.getMethod().getName().equals("getResultList"))) {
      ((EntityManager)Component.getInstance("entityManager")).clear();
      List lista = (List)result;
      for (Iterator localIterator = lista.iterator(); localIterator.hasNext(); ) { Object item = localIterator.next();
        Class clazz = item.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
          handleField(field, item);
        }
      }
    }

    return result;
  }

  private void handleField(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
    if (Collection.class.isAssignableFrom(field.getType())) {
      Collection value = null;
      field.setAccessible(true);
      value = (Collection)field.get(object);
      if (value != null) {
        ArrayList plainCollection = new ArrayList(value);
        field.set(object, plainCollection);
      }
    }
  }
}