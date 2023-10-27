/*    */ package WEB-INF.classes.br.com.accesstage.cargas.dao.impl;
/*    */ 
/*    */ import br.com.accesstage.cargas.dao.RelatoriosDAO;
/*    */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*    */ import br.com.accesstage.loader.util.dao.rowmapper.cargas.RelatoriosNaoCadastradoRowMapper;
/*    */ import br.com.accesstage.loader.util.dao.rowmapper.cargas.RelatoriosRejeitadosRowMapper;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.relatorio.RelatorioNaoCadastrado;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.relatorio.RelatorioRejeitado;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.sql.DataSource;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ import org.springframework.jdbc.core.SqlOutParameter;
/*    */ import org.springframework.jdbc.core.SqlParameter;
/*    */ import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
/*    */ import org.springframework.jdbc.core.namedparam.SqlParameterSource;
/*    */ import org.springframework.jdbc.core.simple.SimpleJdbcCall;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Repository
/*    */ public class RelatoriosDAOImpl
/*    */   extends BaseDAO
/*    */   implements RelatoriosDAO
/*    */ {
/* 29 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.dao.impl.RelatoriosDAOImpl.class);
/*    */   
/*    */   @Autowired
/*    */   public RelatoriosDAOImpl(DataSource dataSource) {
/* 33 */     super(dataSource);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<RelatorioNaoCadastrado> findRelatorioNaoCadastrado() {
/* 40 */     logger.warn("findRelatorioNaoCadastrado: chamando procedure PKG_ALZ_REPORTS.findReportsWithoutComplement");
/*    */     
/* 42 */     List<RelatorioNaoCadastrado> lista = null;
/* 43 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 44 */     call.withCatalogName("PKG_ALZ_REPORTS");
/* 45 */     call.withProcedureName("findReportsWithoutComplement");
/* 46 */     call.declareParameters(new SqlParameter[] { (SqlParameter)new SqlOutParameter("P_LISTA", -10) });
/* 47 */     call.returningResultSet("P_LISTA", (RowMapper)new RelatoriosNaoCadastradoRowMapper());
/* 48 */     MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
/* 49 */     Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/* 50 */     lista = (List<RelatorioNaoCadastrado>)m.get("P_LISTA");
/* 51 */     return lista;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<RelatorioRejeitado> findRelatorioRejeitado() {
/* 58 */     logger.warn("findRelatorioRejeitado: chamando procedure PKG_ALZ_REPORTS.findReportsRejected");
/*    */     
/* 60 */     List<RelatorioRejeitado> lista = null;
/* 61 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 62 */     call.withCatalogName("PKG_ALZ_REPORTS");
/* 63 */     call.withProcedureName("findReportsRejected");
/* 64 */     call.declareParameters(new SqlParameter[] { (SqlParameter)new SqlOutParameter("P_LISTA", -10) });
/* 65 */     call.returningResultSet("P_LISTA", (RowMapper)new RelatoriosRejeitadosRowMapper());
/* 66 */     MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
/* 67 */     Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/* 68 */     lista = (List<RelatorioRejeitado>)m.get("P_LISTA");
/* 69 */     return lista;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateRelatorioNaoCadastrado(long nroUnico) {
/* 76 */     logger.warn("updateRelatorioNaoCadastrado: chamando procedure PKG_ALZ_REPORTS.updateStatusReportWithoutCompl");
/*    */     
/* 78 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 79 */     call.withCatalogName("PKG_ALZ_REPORTS");
/* 80 */     call.withProcedureName("updateStatusReportWithoutCompl");
/* 81 */     call.declareParameters(new SqlParameter[] { new SqlParameter("NRO_UNICO", 2) });
/* 82 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("lpNroUnico", Long.valueOf(nroUnico));
/*    */     
/* 84 */     call.execute((SqlParameterSource)mapSqlParameterSource);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateRelatorioRejeitado(long nroUnico) {
/* 90 */     logger.warn("updateRelatorioRejeitado: chamando procedure PKG_ALZ_REPORTS.updateStatusReportRejected");
/*    */     
/* 92 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 93 */     call.withCatalogName("PKG_ALZ_REPORTS");
/* 94 */     call.withProcedureName("updateStatusReportRejected");
/* 95 */     call.declareParameters(new SqlParameter[] { new SqlParameter("NRO_UNICO", 2) });
/* 96 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("lpNroUnico", Long.valueOf(nroUnico));
/*    */     
/* 98 */     call.execute((SqlParameterSource)mapSqlParameterSource);
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\impl\RelatoriosDAOImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */