/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.services.roadcard.Febraban240Service;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Paths;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Date;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.scheduling.annotation.EnableAsync;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
/*    */ import org.springframework.scheduling.annotation.Scheduled;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ @EnableAsync
/*    */ @EnableScheduling
/*    */ public class Febraban240
/*    */   extends AbstractTimer
/*    */ {
/*    */   @Autowired
/*    */   private Febraban240Service febraban240Service;
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.febraban240}")
/*    */   public void execute() {
/* 30 */     load("FEBRABAN240");
/* 31 */     logger.warn("Febraban240 - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 32 */     File files = new File((String)getConfig().get("FEBRABAN240_CARGA_INPUT"));
/* 33 */     for (File arquivo : files.listFiles()) {
/* 34 */       if (arquivo.getName().contains("@")) {
/* 35 */         ArquivoCarga arquivoCarga = createArquivoCargaArquivo(arquivo, (String)getConfig().get("FEBRABAN240_CARGA_INPUT"), "FEBRABAN240");
/*    */         try {
/* 37 */           if (arquivoCarga != null && !arquivoCarga.isExists()) {
/* 38 */             this.febraban240Service.load(arquivo.getName(), (String)getConfig().get("FEBRABAN240_CARGA_INPUT"), arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 39 */             this.febraban240Service.iniciarProcessamento(arquivoCarga.getCodigoArquivo().intValue(), 9999, (String)getConfig().get("FEBRABAN240_CARGA_INPUT"), arquivo.getName(), (String)getConfig().get("FEBRABAN240_CARGA_ERROR"));
/*    */             
/* 41 */             this.febraban240Service.process(arquivo, arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 42 */             String newPath = this.febraban240Service.moveArquivo(null, true, (String)getConfig().get("FEBRABAN240_CARGA_INPUT"), arquivo.getName());
/* 43 */             if (newPath != null && !newPath.isEmpty()) {
/* 44 */               this.febraban240Service.alteraStatus(arquivoCarga.getCodigoArquivo().intValue(), StatusCarga.STATUS_OK.getCodStatusCarga().intValue(), null, arquivo.getName(), newPath);
/*    */               
/* 46 */               logger.info("[JLoader] - Iniciou Altera Status para OK");
/*    */             } 
/* 48 */             Thread.sleep(2000L);
/*    */           } else {
/* 50 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("FEBRABAN240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */           } 
/* 52 */         } catch (InterruptedException e) {
/* 53 */           logger.error("Febraban240 - Loading - Error loading file:[" + arquivo.getName() + "]");
/*    */           try {
/* 55 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("FEBRABAN240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 56 */           } catch (IOException e1) {
/* 57 */             logger.error("Febraban240 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 59 */         } catch (IOException e) {
/* 60 */           logger.error("Febraban240 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 62 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("FEBRABAN240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 63 */           } catch (IOException e1) {
/* 64 */             logger.error("Febraban240 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 66 */         } catch (SQLException e) {
/* 67 */           logger.error("Febraban240 - Inserting on Database - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 69 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("FEBRABAN240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 70 */           } catch (IOException e1) {
/* 71 */             logger.error("Febraban240 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 76 */     logger.warn("Febraban240 - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\Febraban240.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */