/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import java.beans.BeanInfo;
/*    */ import java.beans.Introspector;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectionUtils
/*    */ {
/* 23 */   private static final Logger LOGGER = Logger.getLogger(br.com.trms.emissor.boleto.utils.ReflectionUtils.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> boolean isEntity(T t) {
/* 38 */     return isAnnotationPresent(t, (Class)Entity.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> boolean isAnnotationPresent(T entity, Class<? extends Annotation> annotation) {
/* 49 */     return entity.getClass().isAnnotationPresent(annotation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> Long getId(T entity) {
/* 60 */     if (entity != null) {
/* 61 */       Class<?> entityClass = entity.getClass();
/* 62 */       BeanInfo info = null;
/*    */       
/*    */       try {
/* 65 */         info = Introspector.getBeanInfo(entityClass);
/* 66 */       } catch (Exception ex) {
/*    */         
/* 68 */         return null;
/*    */       } 
/*    */       
/* 71 */       PropertyDescriptor[] props = info.getPropertyDescriptors();
/*    */ 
/*    */       
/* 74 */       for (PropertyDescriptor pd : props) {
/*    */         try {
/* 76 */           Field field = entityClass.getDeclaredField(pd.getName());
/*    */ 
/*    */           
/* 79 */           if (field.isAnnotationPresent((Class)Id.class)) {
/* 80 */             Method getter = pd.getReadMethod();
/* 81 */             Object id = getter.invoke(entity, new Object[0]);
/*    */ 
/*    */             
/* 84 */             return (Long)id;
/*    */           } 
/* 86 */         } catch (Exception exception) {}
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 92 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\ReflectionUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */