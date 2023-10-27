/*    */ package WEB-INF.classes.br.com.accesstage.cargas.dao.common;
/*    */ 
/*    */ import java.sql.Array;
/*    */ import java.sql.CallableStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ import org.springframework.jdbc.core.SqlReturnType;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SqlReturnArray
/*    */   implements SqlReturnType
/*    */ {
/*    */   private Map<String, Class<?>> auxiliaryTypes;
/*    */   
/*    */   public SqlReturnArray(Map<String, Class<?>> auxiliaryTypes) {
/* 32 */     this.auxiliaryTypes = auxiliaryTypes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getTypeValue(CallableStatement cs, int i, int sqlType, String typeName) throws SQLException {
/* 42 */     Array array = (Array)cs.getObject(i);
/* 43 */     if (array == null) {
/* 44 */       return null;
/*    */     }
/* 46 */     Object valueMap = array.getArray(this.auxiliaryTypes);
/* 47 */     return valueMap;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\common\SqlReturnArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */