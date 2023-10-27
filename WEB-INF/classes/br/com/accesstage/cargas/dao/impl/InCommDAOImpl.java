/*    */ package WEB-INF.classes.br.com.accesstage.cargas.dao.impl;
/*    */ 
/*    */ import br.com.accesstage.cargas.dao.InCommDAO;
/*    */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*    */ import javax.sql.DataSource;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class InCommDAOImpl
/*    */   extends BaseDAO
/*    */   implements InCommDAO
/*    */ {
/*    */   @Autowired
/*    */   public InCommDAOImpl(DataSource dataSource) {
/* 16 */     super(dataSource);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean verificaVarejista(String... params) {
/* 21 */     String strQuery = "SELECT COUNT(*) FROM INCOMM_VAREJISTA WHERE NME_ARQUIVO_FAT = ? AND DSC_CPF_CNPJ_VAREJISTA =  ?";
/* 22 */     Integer total = (Integer)getJdbcTemplate().queryForObject(strQuery, (Object[])params, Integer.class);
/* 23 */     if (total != null && total.intValue() > 0) {
/* 24 */       return true;
/*    */     }
/* 26 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\impl\InCommDAOImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */