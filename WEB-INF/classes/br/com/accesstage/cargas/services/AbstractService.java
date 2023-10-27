/*     */ package WEB-INF.classes.br.com.accesstage.cargas.services;
/*     */ 
/*     */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*     */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Paths;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public abstract class AbstractService<T>
/*     */ {
/*  21 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.AbstractService.class);
/*     */   
/*     */   protected static final String UPDATE_ARQUIVO_CARGA = "UPDATE ARQUIVO_CARGA SET COD_STATUS_CARGA = ?, DSC_NOME_ARQUIVO_DESTINO = ?, NME_PATH_OUT = ?, DTH_PROCESS_FIM = CURRENT_TIMESTAMP WHERE COD_ARQUIVO = ?";
/*     */   
/*     */   protected static final String INSERT_CONTROLE_CARGA = "INSERT INTO CONTROLE_CARGA (COD_CONTROLE,COD_ARQUIVO,DTH_EVENTO,DSC_OBSERVACAO, COD_STATUS_CARGA) VALUES (SEQ_CONTROLE_CARGA.NEXTVAL, ?, CURRENT_TIMESTAMP, ?, ?)";
/*     */   
/*     */   @Autowired
/*     */   protected BaseDAO baseDAO;
/*     */   
/*     */   protected String dataFileName;
/*     */   
/*     */   protected String dataFilePath;
/*     */   
/*     */   protected int codArquivo;
/*     */   
/*     */   protected int colunaProcessada;
/*     */   
/*     */   protected int linhaProcessada;
/*     */   protected File fileToProcess;
/*     */   protected File workFile;
/*     */   protected int empid;
/*     */   protected String errorPath;
/*     */   
/*     */   public abstract void load(String paramString1, String paramString2, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void process(T paramT, int paramInt1, int paramInt2) throws SQLException;
/*     */   
/*     */   public void iniciarProcessamento(int codArquivo, int empid, String dataFilePath, String dataFileName, String pathError) throws SQLException, IOException {
/*  49 */     logger.info("[JLoader] - iniciando processamento: " + this.fileToProcess.getAbsolutePath());
/*  50 */     this.errorPath = pathError;
/*  51 */     alteraStatus(codArquivo, StatusCarga.STATUS_PROCESSANDO.getCodStatusCarga().intValue(), null, null, null);
/*  52 */     logger.info("[JLoader] - Iniciou Altera Status para PROCESSANDO: ");
/*  53 */     this.baseDAO.commit();
/*     */   }
/*     */   
/*     */   public void alteraStatus(int codArquivo, int status, String obs, String nmeArquivoDest, String nmePathOut) throws SQLException {
/*  57 */     this.baseDAO.merge("UPDATE ARQUIVO_CARGA SET COD_STATUS_CARGA = ?, DSC_NOME_ARQUIVO_DESTINO = ?, NME_PATH_OUT = ?, DTH_PROCESS_FIM = CURRENT_TIMESTAMP WHERE COD_ARQUIVO = ?", new Object[] { Integer.valueOf(status), nmeArquivoDest, nmePathOut, Integer.valueOf(codArquivo) });
/*  58 */     logger.info("[JLoader] - Atualizando a ARQUIVO_CARGA, alterando status final.");
/*  59 */     this.baseDAO.merge("INSERT INTO CONTROLE_CARGA (COD_CONTROLE,COD_ARQUIVO,DTH_EVENTO,DSC_OBSERVACAO, COD_STATUS_CARGA) VALUES (SEQ_CONTROLE_CARGA.NEXTVAL, ?, CURRENT_TIMESTAMP, ?, ?)", new Object[] { Integer.valueOf(codArquivo), obs, Integer.valueOf(status) });
/*  60 */     logger.info("[JLoader] - Atualizando a CONTROLE_CARGA, alterando status final.");
/*  61 */     this.baseDAO.commit();
/*     */   }
/*     */ 
/*     */   
/*     */   public String moveArquivo(String dsc, boolean out, String dataFilePath, String dataFileName) throws IOException {
/*  66 */     String dateStr = (new SimpleDateFormat("yyyy'-'MM'/'dd")).format(new Date());
/*  67 */     String fullFile = dataFilePath + "/" + dataFileName;
/*  68 */     String diretorioProcessed = dataFilePath.replaceAll("/in", "/processed/" + dateStr + "/");
/*  69 */     File processedDir = new File(diretorioProcessed);
/*  70 */     if (!processedDir.exists()) {
/*  71 */       processedDir.mkdirs();
/*     */     }
/*  73 */     Files.move(Paths.get(fullFile, new String[0]), Paths.get(diretorioProcessed + "-" + dataFileName, new String[0]), new java.nio.file.CopyOption[0]);
/*  74 */     return "created";
/*     */   }
/*     */ 
/*     */   
/*     */   public String moveArquivo(String dsc, boolean out) throws IOException {
/*  79 */     String dateStr = (new SimpleDateFormat("yyyy'-'MM'/'dd")).format(new Date());
/*  80 */     String fullFile = this.dataFilePath + "/" + this.dataFileName;
/*  81 */     String diretorioProcessed = this.dataFilePath.replaceAll("/in", "/processed/" + dateStr + "/");
/*  82 */     File processedDir = new File(diretorioProcessed);
/*  83 */     if (!processedDir.exists()) {
/*  84 */       processedDir.mkdirs();
/*     */     }
/*  86 */     Files.move(Paths.get(fullFile, new String[0]), Paths.get(diretorioProcessed + "-" + this.dataFileName, new String[0]), new java.nio.file.CopyOption[0]);
/*  87 */     return "created";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void trataErro(int status, Exception e, String desc) {
/*     */     try {
/*  94 */       logger.error("Ocorreu um erro ao processar arquivo: " + this.fileToProcess.getAbsolutePath() + " (" + this.linhaProcessada + "," + this.colunaProcessada + ")", e);
/*  95 */       String newPath = moveArquivo(desc, false);
/*  96 */       alteraStatus(this.codArquivo, status, "(" + this.linhaProcessada + "," + this.colunaProcessada + ")" + e.getMessage(), this.dataFileName, newPath);
/*  97 */     } catch (Exception ex) {
/*  98 */       logger.error("Erro ao alterar status!", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void trataErro(int status, Throwable e, String desc) {
/*     */     try {
/* 104 */       logger.error("Ocorreu um erro ao processar arquivo: " + this.fileToProcess.getAbsolutePath() + " (" + this.linhaProcessada + "," + this.colunaProcessada + ")", e);
/* 105 */       String newPath = moveArquivo(desc, false);
/* 106 */       alteraStatus(this.codArquivo, status, "(" + this.linhaProcessada + "," + this.colunaProcessada + ")" + e.getMessage(), this.dataFileName, newPath);
/* 107 */     } catch (Exception ex) {
/* 108 */       logger.error("Erro ao alterar status!", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String removerAcentuacao(String conteudo) {
/* 113 */     conteudo = conteudo.replace("ç", "c");
/* 114 */     conteudo = conteudo.replace("Ç", "C");
/* 115 */     conteudo = conteudo.replace("á", "a");
/* 116 */     conteudo = conteudo.replace("Á", "A");
/* 117 */     conteudo = conteudo.replace("à", "a");
/* 118 */     conteudo = conteudo.replace("À", "A");
/* 119 */     conteudo = conteudo.replace("é", "e");
/* 120 */     conteudo = conteudo.replace("É", "E");
/* 121 */     conteudo = conteudo.replace("è", "e");
/* 122 */     conteudo = conteudo.replace("È", "E");
/* 123 */     conteudo = conteudo.replace("í", "i");
/* 124 */     conteudo = conteudo.replace("Í", "I");
/* 125 */     conteudo = conteudo.replace("ì", "i");
/* 126 */     conteudo = conteudo.replace("Ì", "I");
/* 127 */     conteudo = conteudo.replace("ó", "o");
/* 128 */     conteudo = conteudo.replace("Ó", "O");
/* 129 */     conteudo = conteudo.replace("ò", "o");
/* 130 */     conteudo = conteudo.replace("Ò", "O");
/* 131 */     conteudo = conteudo.replace("ú", "u");
/* 132 */     conteudo = conteudo.replace("Ú", "U");
/* 133 */     conteudo = conteudo.replace("ù", "u");
/* 134 */     conteudo = conteudo.replace("Ù", "U");
/* 135 */     conteudo = conteudo.replace("ã", "a");
/* 136 */     conteudo = conteudo.replace("Ã", "A");
/* 137 */     conteudo = conteudo.replace("â", "a");
/* 138 */     conteudo = conteudo.replace("Â", "A");
/* 139 */     conteudo = conteudo.replace("ê", "e");
/* 140 */     conteudo = conteudo.replace("Ê", "E");
/* 141 */     conteudo = conteudo.replace("õ", "o");
/* 142 */     conteudo = conteudo.replace("Õ", "O");
/* 143 */     conteudo = conteudo.replace("ô", "o");
/* 144 */     conteudo = conteudo.replace("Ô", "O");
/* 145 */     conteudo = conteudo.replace("ä", "a");
/* 146 */     conteudo = conteudo.replace("Ä", "A");
/* 147 */     conteudo = conteudo.replace("ë", "e");
/* 148 */     conteudo = conteudo.replace("Ë", "E");
/* 149 */     conteudo = conteudo.replace("ü", "u");
/* 150 */     conteudo = conteudo.replace("Ü", "U");
/* 151 */     conteudo = conteudo.replace("º", " ");
/* 152 */     conteudo = conteudo.replace("ª", " ");
/* 153 */     conteudo = conteudo.replace("§", " ");
/* 154 */     conteudo = conteudo.replace("£", " ");
/* 155 */     conteudo = conteudo.replace("¢", " ");
/* 156 */     conteudo = conteudo.replace("'", " ");
/* 157 */     conteudo = conteudo.replace("ó", "o");
/* 158 */     conteudo = conteudo.replace("&", " ");
/* 159 */     conteudo = conteudo.replace("°", " ");
/* 160 */     return conteudo.trim();
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\AbstractService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */