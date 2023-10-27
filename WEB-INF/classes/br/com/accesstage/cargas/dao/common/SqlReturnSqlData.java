/*    */ package WEB-INF.classes.br.com.accesstage.cargas.dao.common;
/*    */ 
/*    */ import java.sql.CallableStatement;
/*    */ import java.sql.Connection;
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
/*    */ public class SqlReturnSqlData
/*    */   implements SqlReturnType
/*    */ {
/*    */   private Class<?> targetClass;
/*    */   
/*    */   public SqlReturnSqlData(Class<?> targetClass) {
/* 21 */     this.targetClass = targetClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getTypeValue(CallableStatement cs, int paramIndex, int sqlType, String typeName) throws SQLException {
/* 32 */     Connection con = cs.getConnection();
/* 33 */     Map<String, Class<?>> typeMap = con.getTypeMap();
/* 34 */     typeMap.put(typeName, this.targetClass);
/* 35 */     Object o = cs.getObject(paramIndex);
/* 36 */     return o;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\common\SqlReturnSqlData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */