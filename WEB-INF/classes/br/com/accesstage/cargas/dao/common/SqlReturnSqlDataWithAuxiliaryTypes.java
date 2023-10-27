/*    */ package WEB-INF.classes.br.com.accesstage.cargas.dao.common;
/*    */ 
/*    */ import java.sql.CallableStatement;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ import org.springframework.jdbc.core.SqlReturnType;
/*    */ 
/*    */ 
/*    */ public class SqlReturnSqlDataWithAuxiliaryTypes
/*    */   implements SqlReturnType
/*    */ {
/*    */   private Class<?> targetClass;
/*    */   private Map<String, Class<?>> auxiliaryTypes;
/*    */   
/*    */   public SqlReturnSqlDataWithAuxiliaryTypes(Class<?> targetClass, Map<String, Class<?>> auxiliaryTypes) {
/* 17 */     this.targetClass = targetClass;
/* 18 */     this.auxiliaryTypes = auxiliaryTypes;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getTypeValue(CallableStatement cs, int paramIndex, int sqlType, String typeName) throws SQLException {
/* 23 */     Connection con = cs.getConnection();
/* 24 */     Map<String, Class<?>> typeMap = con.getTypeMap();
/* 25 */     typeMap.put(typeName, this.targetClass);
/* 26 */     typeMap.putAll(this.auxiliaryTypes);
/* 27 */     Object o = cs.getObject(paramIndex);
/* 28 */     return o;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\common\SqlReturnSqlDataWithAuxiliaryTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */