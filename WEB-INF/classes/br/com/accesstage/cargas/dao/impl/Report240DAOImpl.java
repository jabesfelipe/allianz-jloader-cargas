/*     */ package WEB-INF.classes.br.com.accesstage.cargas.dao.impl;
/*     */ 
/*     */ import br.com.accesstage.cargas.dao.Report240DAO;
/*     */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*     */ import br.com.accesstage.loader.util.dao.rowmapper.cargas.ComplementDetailRowMapper;
/*     */ import br.com.accesstage.loader.util.dao.rowmapper.cargas.ComplementHeaderRowMapper;
/*     */ import br.com.accesstage.loader.util.dao.rowmapper.cargas.ComplementTraillerRowMapper;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Detalhe;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Header;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Trailler;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.commons.collections4.CollectionUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.dao.EmptyResultDataAccessException;
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
/*     */ @Repository
/*     */ public class Report240DAOImpl
/*     */   extends BaseDAO
/*     */   implements Report240DAO
/*     */ {
/*  35 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.dao.impl.Report240DAOImpl.class);
/*     */   
/*     */   @Autowired
/*     */   public Report240DAOImpl(DataSource dataSource) {
/*  39 */     super(dataSource);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Detalhe> findCobRegister() {
/*  44 */     List<Detalhe> lista = new ArrayList<>();
/*  45 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/*  46 */     call.withCatalogName("PKG_ALZ_REPORTS");
/*  47 */     call.withProcedureName("getCOBReport");
/*  48 */     call.declareParameters(new SqlParameter[] { (SqlParameter)new SqlOutParameter("P_LISTA", -10) });
/*  49 */     call.returningResultSet("P_LISTA", (RowMapper)new ComplementDetailRowMapper());
/*  50 */     MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
/*  51 */     Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/*  52 */     lista = (List<Detalhe>)m.get("P_LISTA");
/*  53 */     return lista;
/*     */   }
/*     */ 
/*     */   
/*     */   public Header getComplHeader(int codigoArquivo, int codHeader) {
/*  58 */     Header header = new Header();
/*  59 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/*  60 */     call.withCatalogName("PKG_ALZ_REPORTS");
/*  61 */     call.withProcedureName("getHeaderCompl");
/*  62 */     call.declareParameters(new SqlParameter[] { new SqlParameter("codArquivo", 2), new SqlParameter("codHeader", 2), (SqlParameter)new SqlOutParameter("P_LISTA", -10) });
/*     */ 
/*     */     
/*  65 */     call.returningResultSet("P_LISTA", (RowMapper)new ComplementHeaderRowMapper());
/*  66 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("codArquivo", Integer.valueOf(codigoArquivo)).addValue("codHeader", Integer.valueOf(codHeader));
/*     */ 
/*     */     
/*  69 */     Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/*  70 */     List<Header> list = (List<Header>)m.get("P_LISTA");
/*  71 */     if (CollectionUtils.isNotEmpty(list)) {
/*  72 */       header = list.get(0);
/*     */     } else {
/*  74 */       header = null;
/*     */     } 
/*  76 */     return header;
/*     */   }
/*     */ 
/*     */   
/*     */   public Trailler getComplTrailler(int codigoArquivo, int codHeader) {
/*  81 */     Trailler trailler = new Trailler();
/*  82 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/*  83 */     call.withCatalogName("PKG_ALZ_REPORTS");
/*  84 */     call.withProcedureName("getTraillerCompl");
/*  85 */     call.declareParameters(new SqlParameter[] { new SqlParameter("codArquivo", 2), new SqlParameter("codHeader", 2), (SqlParameter)new SqlOutParameter("P_LISTA", -10) });
/*     */ 
/*     */     
/*  88 */     call.returningResultSet("P_LISTA", (RowMapper)new ComplementTraillerRowMapper());
/*  89 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("codArquivo", Integer.valueOf(codigoArquivo)).addValue("codHeader", Integer.valueOf(codHeader));
/*     */ 
/*     */     
/*  92 */     Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/*  93 */     List<Trailler> list = (List<Trailler>)m.get("P_LISTA");
/*  94 */     if (CollectionUtils.isNotEmpty(list)) {
/*  95 */       trailler = list.get(0);
/*     */     } else {
/*  97 */       trailler = null;
/*     */     } 
/*  99 */     return trailler;
/*     */   }
/*     */   
/*     */   public String geTipoCobranca(String codBanco, String codRetorno) {
/* 103 */     String tipoCobranca = "";
/* 104 */     StringBuffer str = new StringBuffer();
/* 105 */     str.append("select tpo_cobranca from alz_rejeicao rej where rej.cod_banco_debito =").append(codBanco).append(" and rej.cod_ocorrencia = ").append(codRetorno);
/*     */     try {
/* 107 */       tipoCobranca = (String)getJdbcTemplate().queryForObject(str.toString(), String.class);
/* 108 */     } catch (EmptyResultDataAccessException emptyResultDataAccessException) {}
/*     */ 
/*     */     
/* 111 */     return tipoCobranca;
/*     */   }
/*     */   
/*     */   public Long getNroSequencialArquivo() {
/* 115 */     Long nroArquivo = null;
/*     */     try {
/* 117 */       nroArquivo = getSequenceID("SEQ_ALZ_SEQARQUIVO");
/* 118 */     } catch (SQLException e) {
/* 119 */       logger.error("Error ao obter sequence");
/*     */     } 
/* 121 */     return nroArquivo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(long nroUnico) {
/* 126 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 127 */     call.withCatalogName("PKG_ALZ_REPORTS");
/* 128 */     call.withProcedureName("updateStatusCOB");
/* 129 */     call.declareParameters(new SqlParameter[] { new SqlParameter("lpNroUnico", 2) });
/* 130 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("lpNroUnico", Long.valueOf(nroUnico));
/*     */     
/* 132 */     call.execute((SqlParameterSource)mapSqlParameterSource);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkNossoNumero(String nossoNumero) {
/* 138 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 139 */     call.withCatalogName("PKG_ALZ_REPORTS");
/* 140 */     call.withProcedureName("checkNossoNumero");
/* 141 */     call.declareParameters(new SqlParameter[] { new SqlParameter("pNossoNumero", 12) });
/* 142 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("pNossoNumero", nossoNumero);
/*     */     
/* 144 */     call.execute((SqlParameterSource)mapSqlParameterSource);
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\impl\Report240DAOImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */