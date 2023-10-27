/*     */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*     */ 
/*     */ import br.com.accesstage.cargas.dao.ArquivoCargaDAO;
/*     */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*     */ import br.com.accesstage.loader.util.core.TrackingHandler;
/*     */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*     */ import br.com.accesstage.loader.util.dao.ConfigDAO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class AbstractTimer
/*     */ {
/*  29 */   protected static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.AbstractTimer.class);
/*     */   
/*  31 */   protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*     */   
/*     */   @Autowired
/*     */   protected BaseDAO baseDAO;
/*     */   
/*     */   @Autowired
/*     */   private ArquivoCargaDAO arquivoCargaDAO;
/*     */   @Autowired
/*     */   private ConfigDAO configDAO;
/*     */   private Map<String, String> config;
/*     */   
/*     */   public void load(String layout) {
/*  43 */     this.config = this.configDAO.mapConfigLayout(layout);
/*     */   }
/*     */   
/*     */   public Long createArquivoCarga(File arquivo, String input, String layout) {
/*  47 */     Long id = null;
/*  48 */     StringBuffer strQuery = new StringBuffer();
/*  49 */     strQuery.append("INSERT INTO ARQUIVO_CARGA(COD_ARQUIVO, DSC_NOME_ARQUIVO_ORIGEM, DSC_NOME_ARQUIVO_DESTINO, DSC_MD5SUM, ");
/*  50 */     strQuery.append("NME_PATH_IN, NME_PATH_OUT, NRO_REGISTROS, NRO_TAMANHO_ARQUIVO, COD_INSTANCIA_BPEL, DTH_PROCESS_INI,  ");
/*  51 */     strQuery.append("DTH_PROCESS_FIM, EMPID, COD_TPO_LAYOUT, DSC_ORIGEM, COD_STATUS_CARGA, CREATED) ");
/*  52 */     strQuery.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
/*     */     try {
/*  54 */       InputStream bis = new FileInputStream(arquivo);
/*  55 */       String digest = DigestUtils.md5Hex(bis);
/*  56 */       id = this.baseDAO.getSequenceID("SEQ_ARQUIVO_CARGA");
/*  57 */       this.baseDAO.merge(strQuery.toString(), new Object[] { id, arquivo.getName(), null, digest, input, null, null, Long.valueOf(arquivo.length()), null, new Date(), null, Integer.valueOf(9999), layout, null, StatusCarga.STATUS_PROCESSANDO.getCodStatusCarga(), new Date() });
/*     */     }
/*  59 */     catch (FileNotFoundException e) {
/*  60 */       logger.error("Error loading file:[" + arquivo.getName() + "]");
/*  61 */     } catch (IOException e) {
/*  62 */       logger.error("Error generating MD5 file:[" + arquivo.getName() + "]");
/*  63 */     } catch (SQLException e) {
/*  64 */       logger.error("Error obtaining id to ARQUIVO_CARGA file:[" + arquivo.getName() + "]");
/*     */     } 
/*  66 */     return id;
/*     */   }
/*     */   
/*     */   public ArquivoCarga createArquivoCargaArquivo(File arquivo, String input, String layout) {
/*  70 */     ArquivoCarga arquivoCarga = new ArquivoCarga();
/*  71 */     arquivoCarga.setDscNomeArquivoOrigem(arquivo.getName());
/*     */     try {
/*  73 */       InputStream bis = new FileInputStream(arquivo);
/*  74 */       arquivoCarga.setDscMd5sum(DigestUtils.md5Hex(bis));
/*  75 */       arquivoCarga.setDscNomeArquivoOrigem(arquivo.getName());
/*  76 */       arquivoCarga.setNmePathIn(input);
/*  77 */       arquivoCarga.setNroTamanhoArquivo(Long.valueOf(arquivo.length()));
/*  78 */       arquivoCarga.setEmpId(new Long(9999L));
/*  79 */       arquivoCarga.setCodTpoLayout(layout);
/*  80 */       arquivoCarga.setCodStatus(StatusCarga.STATUS_PROCESSANDO.getCodStatusCarga().toString());
/*     */       
/*  82 */       arquivoCarga = this.arquivoCargaDAO.existArquivoCarga(arquivoCarga);
/*     */     }
/*  84 */     catch (FileNotFoundException e) {
/*  85 */       logger.error("Error loading file:[" + arquivo.getName() + "]");
/*  86 */     } catch (IOException e) {
/*  87 */       logger.error("Error generating MD5 file:[" + arquivo.getName() + "]");
/*     */     } 
/*  89 */     return arquivoCarga;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getConfig() {
/*  96 */     return this.config;
/*     */   }
/*     */   public void setConfig(Map<String, String> config) {
/*  99 */     this.config = config;
/*     */   }
/*     */   
/*     */   protected void regEvento(String nmeProcesso, Long trackingID, String staSeveridade, String dscEvento) throws Exception {
/*     */     try {
/* 104 */       TrackingHandler trackingHandler = new TrackingHandler();
/* 105 */       trackingHandler.regEvento(trackingID.toString(), staSeveridade, dscEvento, nmeProcesso);
/*     */     }
/* 107 */     catch (Exception e) {
/* 108 */       throw new Exception(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getTrackingID(String filename) {
/* 113 */     StringTokenizer st = new StringTokenizer(filename, "@");
/*     */     
/* 115 */     String nextToken = st.nextToken();
/* 116 */     nextToken = st.nextToken();
/* 117 */     nextToken = st.nextToken();
/* 118 */     nextToken = st.nextToken();
/* 119 */     st = new StringTokenizer(nextToken, "-");
/* 120 */     nextToken = st.nextToken();
/*     */     
/* 122 */     return nextToken;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\AbstractTimer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */