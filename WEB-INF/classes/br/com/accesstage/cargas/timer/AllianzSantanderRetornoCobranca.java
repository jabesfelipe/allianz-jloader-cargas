/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.services.allianz.AllianzSantanderServico;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Paths;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.scheduling.annotation.EnableAsync;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
/*    */ import org.springframework.scheduling.annotation.Scheduled;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ @Component
/*    */ @EnableAsync
/*    */ @EnableScheduling
/*    */ public class AllianzSantanderRetornoCobranca
/*    */   extends AbstractTimer
/*    */ {
/*    */   @Autowired
/*    */   private AllianzSantanderServico servico;
/* 33 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.AllianzSantanderRetornoCobranca.class);
/*    */ 
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.allianz.santander.retorno}")
/*    */   public void execute() {
/* 38 */     load("ALLIANZSANTANDER240");
/* 39 */     logger.warn("AllianzSantanderRetornoCobranca - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 40 */     String pathRet = (String)getConfig().get("ALLIANZCOBRANCASANTARET_CARGA_INPUT");
/* 41 */     if (StringUtils.isNotEmpty(pathRet)) {
/* 42 */       File file = new File(pathRet);
/* 43 */       if (file != null) {
/* 44 */         File[] files = file.listFiles();
/* 45 */         if (files != null) {
/* 46 */           List<String> listaNossoNumero = new ArrayList<>();
/*    */           try {
/* 48 */             for (File arquivo : file.listFiles()) {
/*    */               
/* 50 */               InputStream bis = new FileInputStream(arquivo);
/* 51 */               List<String> lines = IOUtils.readLines(bis, "UTF-8");
/* 52 */               for (int index = 0; index < lines.size(); index++) {
/* 53 */                 String linha = lines.get(index);
/* 54 */                 String tipoRegistro = linha.substring(7, 8);
/* 55 */                 String segmento = linha.substring(13, 14);
/* 56 */                 String codMovimento = linha.substring(15, 17);
/* 57 */                 if (tipoRegistro.equalsIgnoreCase("3") && segmento.equalsIgnoreCase("T") && codMovimento.equalsIgnoreCase("02")) {
/*    */                   
/* 59 */                   String nossoNumero = linha.substring(40, 53);
/* 60 */                   listaNossoNumero.add(nossoNumero);
/*    */                 } 
/*    */               } 
/* 63 */               this.servico.execute(listaNossoNumero);
/* 64 */               Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZCOBRANCASANTARET_CARGA_PROCESSED") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */             } 
/* 66 */           } catch (FileNotFoundException e) {
/* 67 */             logger.error("Erro ao encontrar o arquivo :" + e.getMessage());
/* 68 */           } catch (IOException e) {
/* 69 */             logger.error("Erro ao abrir o arquivo :" + e.getMessage());
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 74 */     logger.warn("AllianzSantanderRetornoCobranca - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\AllianzSantanderRetornoCobranca.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */