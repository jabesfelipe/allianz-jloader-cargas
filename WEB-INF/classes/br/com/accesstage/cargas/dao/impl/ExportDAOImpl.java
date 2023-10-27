/*     */ package WEB-INF.classes.br.com.accesstage.cargas.dao.impl;
/*     */ 
/*     */ import br.com.accesstage.cargas.dao.ExportDAO;
/*     */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*     */ import br.com.accesstage.loader.util.dao.rowmapper.FileOutMapper;
/*     */ import br.com.accesstage.loader.util.dao.rowmapper.TokenMapper;
/*     */ import br.com.accesstage.loader.util.dao.rowmapper.cargas.ConfigMapper;
/*     */ import br.com.accesstage.loader.util.vo.FileOutVO;
/*     */ import br.com.accesstage.loader.util.vo.TokenVO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.ConfigVO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.jdbc.core.SqlOutParameter;
/*     */ import org.springframework.jdbc.core.SqlParameter;
/*     */ import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
/*     */ import org.springframework.jdbc.core.namedparam.SqlParameterSource;
/*     */ import org.springframework.jdbc.core.simple.SimpleJdbcCall;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository
/*     */ public class ExportDAOImpl
/*     */   extends BaseDAO
/*     */   implements ExportDAO
/*     */ {
/*  33 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.dao.impl.ExportDAOImpl.class);
/*     */   
/*     */   @Autowired
/*     */   public ExportDAOImpl(DataSource dataSource) {
/*  37 */     super(dataSource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateStatusExportacao(TokenVO tokenVO) throws Exception {
/*  49 */     logger.debug("[ExportDAO] ExportDAO.updateStatusExportacao() - atualiza status ");
/*     */     
/*     */     try {
/*  52 */       SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/*  53 */       call.withCatalogName("pkg_alz_fileout");
/*  54 */       call.withProcedureName("set_notify");
/*  55 */       MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("szToken", tokenVO.getToken());
/*  56 */       call.execute((SqlParameterSource)mapSqlParameterSource);
/*     */     }
/*  58 */     catch (Exception e) {
/*  59 */       logger.error("[ExportDAO] ExportDAO.updateAgendaExportacao() - Erro: " + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TokenVO> getTokenPending() throws Exception {
/*  73 */     logger.debug("[ExportDAO] ExportDAO.getTokenPending() - Recupera token pendente ");
/*  74 */     List<TokenVO> list = new ArrayList<>();
/*     */     
/*     */     try {
/*  77 */       SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/*  78 */       call.withCatalogName("pkg_alz_fileout");
/*  79 */       call.withProcedureName("get_token_pending");
/*  80 */       call.declareParameters(new SqlParameter[] { (SqlParameter)new SqlOutParameter("cOut", -10) });
/*  81 */       call.returningResultSet("cOut", (RowMapper)new TokenMapper());
/*  82 */       Map<String, Object> m = call.execute(new Object[0]);
/*  83 */       list = (List<TokenVO>)m.get("cOut");
/*     */     }
/*  85 */     catch (Exception e) {
/*  86 */       logger.error("[ExportDAO] ExportDAO.getTokenPending() - Erro: " + e);
/*  87 */       throw new Exception(e);
/*     */     } 
/*     */     
/*  90 */     logger.debug("[ExportDAO] ExportDAO.getTokenPending() - list.size() : " + list.size());
/*  91 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TokenVO> getTokenPending(String tipoArquivo) throws Exception {
/* 103 */     logger.debug("[ExportDAO] ExportDAO.getTokenPending() - Recupera token pendente ");
/* 104 */     List<TokenVO> list = new ArrayList<>();
/*     */     
/*     */     try {
/* 107 */       SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 108 */       call.withCatalogName("pkg_alz_fileout");
/* 109 */       call.withProcedureName("get_token_pending");
/* 110 */       call.declareParameters(new SqlParameter[] { (SqlParameter)new SqlOutParameter("cOut", -10) });
/* 111 */       MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("szTpoArquivo", tipoArquivo);
/* 112 */       call.returningResultSet("cOut", (RowMapper)new TokenMapper());
/* 113 */       Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/* 114 */       list = (List<TokenVO>)m.get("cOut");
/*     */     }
/* 116 */     catch (Exception e) {
/* 117 */       logger.error("[ExportDAO] ExportDAO.getTokenPending() - Erro: " + e);
/* 118 */       throw new Exception(e);
/*     */     } 
/*     */     
/* 121 */     logger.debug("[ExportDAO] ExportDAO.getTokenPending() - list.size() : " + list.size());
/* 122 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FileOutVO> getFileOut(TokenVO tokenVO) throws Exception {
/* 135 */     logger.debug("[ExportDAO] ExportDAO.getFileOut() - Recupera registros ");
/* 136 */     List<FileOutVO> list = new ArrayList<>();
/*     */     
/*     */     try {
/* 139 */       SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 140 */       call.withCatalogName("pkg_alz_fileout");
/* 141 */       call.withProcedureName("get_fileout");
/* 142 */       call.declareParameters(new SqlParameter[] { (SqlParameter)new SqlOutParameter("cOut", -10), new SqlParameter("szToken", 12) });
/* 143 */       MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("szToken", tokenVO.getToken()).addValue("szTpoArquivo", tokenVO.getTpoArquivo());
/*     */       
/* 145 */       call.returningResultSet("cOut", (RowMapper)new FileOutMapper());
/* 146 */       Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/* 147 */       list = (List<FileOutVO>)m.get("cOut");
/*     */     }
/* 149 */     catch (Exception e) {
/* 150 */       logger.error("[ExportDAO] ExportDAO.getFileOut() - Erro: " + e);
/* 151 */       throw new Exception(e);
/*     */     } 
/*     */     
/* 154 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ConfigVO> getConfig(String fluxo) throws Exception {
/* 160 */     logger.debug("[ExportDAO] ExportDAO.getConfig() - Recupera configs ");
/* 161 */     List<ConfigVO> list = new ArrayList<>();
/*     */     
/*     */     try {
/* 164 */       SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 165 */       call.withCatalogName("pkg_alz_fileout");
/* 166 */       call.withProcedureName("get_config");
/* 167 */       call.declareParameters(new SqlParameter[] { (SqlParameter)new SqlOutParameter("crResult", -10) });
/* 168 */       call.returningResultSet("crResult", (RowMapper)new ConfigMapper());
/* 169 */       MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("szDsc_fluxo", fluxo);
/* 170 */       Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/* 171 */       list = (List<ConfigVO>)m.get("crResult");
/*     */     }
/* 173 */     catch (Exception e) {
/* 174 */       logger.warn("[ExportDAO] ExportDAO.getConfig() - Erro: " + e);
/* 175 */       throw new Exception(e);
/*     */     } 
/*     */     
/* 178 */     if (list != null && list.size() > 1) {
/* 179 */       logger.warn("[ExportDAO] ExportDAO.getConfig() - list.size() : " + list.size());
/*     */     } else {
/* 181 */       logger.warn("[ExportDAO] ExportDAO.getConfig() - nao existem configuracoes cadastradas");
/*     */     } 
/* 183 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\impl\ExportDAOImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */