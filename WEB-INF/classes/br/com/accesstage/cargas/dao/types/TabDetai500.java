/*    */ package WEB-INF.classes.br.com.accesstage.cargas.dao.types;
/*    */ 
/*    */ import java.sql.SQLData;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.SQLInput;
/*    */ import java.sql.SQLOutput;
/*    */ 
/*    */ public class TabDetai500
/*    */   implements SQLData
/*    */ {
/*    */   public String getSQLTypeName() throws SQLException {
/* 12 */     return "TAB_DETAIL_VOLKS_500";
/*    */   }
/*    */   
/*    */   public void readSQL(SQLInput stream, String typeName) throws SQLException {}
/*    */   
/*    */   public void writeSQL(SQLOutput stream) throws SQLException {}
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\types\TabDetai500.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */