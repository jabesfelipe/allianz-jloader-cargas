/*     */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*     */ 
/*     */ import br.com.accesstage.cargas.dao.ExportDAO;
/*     */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*     */ import br.com.accesstage.loader.util.vo.FileOutVO;
/*     */ import br.com.accesstage.loader.util.vo.TokenVO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.ConfigVO;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Paths;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.scheduling.annotation.EnableAsync;
/*     */ import org.springframework.scheduling.annotation.EnableScheduling;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ @Component
/*     */ @EnableAsync
/*     */ @EnableScheduling
/*     */ public class AllianzSantanderCobranca
/*     */   extends AbstractTimer
/*     */ {
/*  31 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.AllianzSantanderCobranca.class);
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private ExportDAO exportDAO;
/*     */ 
/*     */   
/*  39 */   private final String NME_PROCESSO = "Allianz-Santander-Cobranca";
/*  40 */   protected SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsssss");
/*     */   
/*     */   private String PATH_OUTPUT;
/*     */   
/*     */   private String PATH_PROCESSED;
/*     */   
/*     */   private String PATH_RESUBMIT;
/*     */ 
/*     */   
/*     */   @Scheduled(cron = "${timer.schedule.allianz.santander}")
/*     */   public void execute() throws Exception {
/*  51 */     load("ALLIANZSANTANDER240");
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
/*     */ 
/*     */ 
/*     */     
/*  75 */     logger.warn("[AllianzSantanderCobranca] Inicio da exportação - " + this.sdf.format(new Date()));
/*     */     
/*  77 */     String nomeArquivo = "";
/*  78 */     Long trackingID = Long.valueOf(0L);
/*  79 */     this.PATH_RESUBMIT = (String)getConfig().get("ALLIANZ_OUTPUT_RESUBMIT");
/*     */     
/*  81 */     logger.info("[AllianzSantanderCobranca] executeWriter - getConfig()");
/*  82 */     List<ConfigVO> list = this.exportDAO.getConfig("SAIDA");
/*     */     
/*  84 */     for (int k = 0; k < list.size(); k++) {
/*     */       
/*  86 */       String tipoArquivo = ((ConfigVO)list.get(k)).getTpo_arquivo();
/*  87 */       String sentido = ((ConfigVO)list.get(k)).getDsc_sentido();
/*  88 */       this.PATH_OUTPUT = ((ConfigVO)list.get(k)).getDsc_path_temp();
/*  89 */       this.PATH_PROCESSED = ((ConfigVO)list.get(k)).getDsc_path_processed();
/*     */       
/*  91 */       logger.warn("[AllianzSantanderCobranca] executeWriter - " + tipoArquivo + " | " + sentido);
/*     */       
/*     */       try {
/*  94 */         logger.debug("[AllianzSantanderCobranca] executeWriter - getTokenPending()");
/*  95 */         List<TokenVO> tokenVO = this.exportDAO.getTokenPending(tipoArquivo);
/*     */         
/*  97 */         for (int i = 0; i < tokenVO.size(); i++)
/*     */         {
/*  99 */           logger.warn("[AllianzSantanderCobranca] executeWriter - token: " + tokenVO.get(i));
/* 100 */           List<FileOutVO> lines = this.exportDAO.getFileOut(tokenVO.get(i));
/*     */           
/* 102 */           if (lines != null && lines.size() > 1)
/*     */           {
/* 104 */             nomeArquivo = geraNomeArquivo(this.PATH_OUTPUT, ((FileOutVO)lines.get(0)).getNme_arquivo());
/* 105 */             logger.warn("[AllianzSantanderCobranca] executeWriter - nome do arquivo: " + nomeArquivo);
/* 106 */             trackingID = new Long(getTrackingID(nomeArquivo));
/*     */             
/* 108 */             FileWriter fw = new FileWriter(nomeArquivo);
/* 109 */             BufferedWriter bw = new BufferedWriter(fw);
/* 110 */             for (int j = 0; j < lines.size(); j++) {
/* 111 */               bw.write(((FileOutVO)lines.get(j)).getDsc_registro());
/*     */               
/* 113 */               bw.write(System.lineSeparator());
/*     */             } 
/* 115 */             bw.close();
/* 116 */             fw.close();
/* 117 */             Thread.sleep(2000L);
/*     */             
/* 119 */             Thread.sleep(2000L);
/* 120 */             logger.debug("[AllianzSantanderCobranca] executeWriter - movendo arquivo para diretório: " + this.PATH_PROCESSED);
/* 121 */             moveProcessed(new File(nomeArquivo), this.PATH_PROCESSED);
/*     */             
/* 123 */             Thread.sleep(2000L);
/* 124 */             regEvento("Allianz-Santander-Cobranca", trackingID, "I", "Arquivo gerado e enviado: " + nomeArquivo);
/* 125 */             logger.debug("[AllianzSantanderCobranca] executeWriter - movendo arquivo para diretório: " + this.PATH_RESUBMIT);
/* 126 */             moveResubmit(new File(nomeArquivo), this.PATH_RESUBMIT);
/*     */             
/* 128 */             logger.debug("[AllianzSantanderCobranca] executeWriter - updateStatusExportacao: " + tokenVO.get(i));
/* 129 */             this.exportDAO.updateStatusExportacao(tokenVO.get(i));
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 135 */       catch (IOException ex) {
/* 136 */         regEvento("Allianz-Santander-Cobranca", trackingID, "E", "Erro ao gerar o arquivo: " + ex.getMessage());
/* 137 */         logger.error("Erro ao escrever o arquivo:" + nomeArquivo + ex);
/* 138 */       } catch (InterruptedException e) {
/* 139 */         regEvento("Allianz-Santander-Cobranca", trackingID, "E", "Erro ao gerar o arquivo: " + e.getMessage());
/* 140 */         logger.error("Erro ao escrever o arquivo:" + nomeArquivo + e);
/* 141 */       } catch (Exception e) {
/* 142 */         regEvento("Allianz-Santander-Cobranca", trackingID, "E", "Erro ao gerar o arquivo: " + e.getMessage());
/* 143 */         logger.error("Erro ao escrever o arquivo:" + nomeArquivo + e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 148 */     logger.warn("Final de Writer -" + this.sdf.format(new Date()));
/*     */   }
/*     */   
/*     */   public String geraNomeArquivo(String diretorio, String intercambio) {
/* 152 */     StringBuffer strArquivoSaida = new StringBuffer();
/* 153 */     strArquivoSaida.append(diretorio);
/* 154 */     strArquivoSaida.append(intercambio).append("@");
/* 155 */     strArquivoSaida.append((new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date()));
/* 156 */     return strArquivoSaida.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String moveProcessed(File arquivo, String pathOut) throws IOException {
/* 161 */     String dateStr = (new SimpleDateFormat("yyyy'-'MM'/'dd")).format(new Date());
/* 162 */     String diretorioProcessed = pathOut + dateStr + "/";
/* 163 */     File processedDir = new File(diretorioProcessed);
/* 164 */     if (!processedDir.exists()) {
/* 165 */       processedDir.mkdirs();
/*     */     }
/* 167 */     Files.copy(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get(diretorioProcessed + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 168 */     return "created";
/*     */   }
/*     */ 
/*     */   
/*     */   public String moveResubmit(File arquivo, String diretorioResubmit) throws IOException {
/* 173 */     Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get(diretorioResubmit + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 174 */     return "created";
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\AllianzSantanderCobranca.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */