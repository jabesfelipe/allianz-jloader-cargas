/*    */ package WEB-INF.classes.br.com.accesstage.cargas.dao.impl;
/*    */ 
/*    */ import br.com.accesstage.cargas.dao.ArquivoCargaDAO;
/*    */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*    */ import br.com.accesstage.loader.util.dao.rowmapper.cargas.ArquivoCargaRowMapper;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.sql.DataSource;
/*    */ import org.apache.commons.collections4.CollectionUtils;
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
/*    */ 
/*    */ @Repository
/*    */ public class ArquivoCargaDAOImpl
/*    */   extends BaseDAO
/*    */   implements ArquivoCargaDAO
/*    */ {
/*    */   @Autowired
/*    */   public ArquivoCargaDAOImpl(DataSource dataSource) {
/* 30 */     super(dataSource);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ArquivoCarga existArquivoCarga(ArquivoCarga arquivoCarga) {
/* 36 */     ArquivoCarga retorno = null;
/* 37 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 38 */     call.withCatalogName("PKG_ROADCARD");
/* 39 */     call.withProcedureName("FIND_ARQUIVO_CARGA");
/* 40 */     call.declareParameters(new SqlParameter[] { new SqlParameter("P_NME_ARQUIVO", 12), new SqlParameter("P_MD5SUM", 12), new SqlParameter("P_NME_PATH", 12), new SqlParameter("P_NRO_TAM_ARQ", 2), new SqlParameter("P_EMPID", 2), new SqlParameter("P_COD_TPO_LAYOUT", 12), new SqlParameter("P_COD_STATUS", 12), (SqlParameter)new SqlOutParameter("P_ARQUIVO_CARGA", -10) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     call.returningResultSet("P_ARQUIVO_CARGA", (RowMapper)new ArquivoCargaRowMapper());
/* 50 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("P_NME_ARQUIVO", arquivoCarga.getDscNomeArquivoOrigem()).addValue("P_MD5SUM", arquivoCarga.getDscMd5sum()).addValue("P_NME_PATH", arquivoCarga.getNmePathIn()).addValue("P_NRO_TAM_ARQ", arquivoCarga.getNroTamanhoArquivo()).addValue("P_EMPID", arquivoCarga.getEmpId()).addValue("P_COD_TPO_LAYOUT", arquivoCarga.getCodTpoLayout()).addValue("P_COD_STATUS", arquivoCarga.getCodStatus());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/* 59 */     List<ArquivoCarga> list = (List<ArquivoCarga>)m.get("P_ARQUIVO_CARGA");
/* 60 */     if (CollectionUtils.isNotEmpty(list)) {
/* 61 */       retorno = list.get(0);
/*    */     } else {
/* 63 */       retorno = arquivoCarga;
/*    */     } 
/* 65 */     return retorno;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\impl\ArquivoCargaDAOImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */