/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.roadcard.Febraban150Reader;
/*    */ import br.com.accesstage.cargas.services.roadcard.Febraban150Service;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.Febraban150VO;
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
/*    */ public class Febraban150
/*    */   extends AbstractTimer
/*    */ {
/*    */   @Autowired
/*    */   private Febraban150Service febraban150Service;
/*    */   @Autowired
/*    */   private Febraban150Reader febraban150Reader;
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.febraban150}")
/*    */   public void execute() {
/* 34 */     load("FEBRABAN150");
/* 35 */     logger.warn("Febraban150 - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 36 */     File files = new File((String)getConfig().get("FEBRABAN150_CARGA_INPUT"));
/* 37 */     Febraban150VO febraban150vo = null;
/* 38 */     for (File arquivo : files.listFiles()) {
/* 39 */       if (arquivo.getName().contains("@")) {
/* 40 */         ArquivoCarga arquivoCarga = createArquivoCargaArquivo(arquivo, (String)getConfig().get("FEBRABAN150_CARGA_INPUT"), "FEBRABAN150");
/*    */         try {
/* 42 */           if (arquivoCarga != null && !arquivoCarga.isExists()) {
/* 43 */             this.febraban150Service.load(arquivo.getName(), (String)getConfig().get("FEBRABAN150_CARGA_INPUT"), arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 44 */             this.febraban150Service.iniciarProcessamento(arquivoCarga.getCodigoArquivo().intValue(), 9999, (String)getConfig().get("FEBRABAN150_CARGA_INPUT"), arquivo.getName(), (String)getConfig().get("FEBRABAN150_CARGA_ERROR"));
/*    */             
/* 46 */             febraban150vo = this.febraban150Reader.readFiles(arquivo);
/* 47 */             this.febraban150Service.process(febraban150vo, arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 48 */             String newPath = this.febraban150Service.moveArquivo(null, true, (String)getConfig().get("FEBRABAN150_CARGA_INPUT"), arquivo.getName());
/* 49 */             if (newPath != null && !newPath.isEmpty()) {
/*    */               
/* 51 */               this.febraban150Service.alteraStatus(arquivoCarga.getCodigoArquivo().intValue(), StatusCarga.STATUS_OK.getCodStatusCarga().intValue(), null, arquivo.getName(), newPath);
/*    */               
/* 53 */               logger.info("[JLoader] - Iniciou Altera Status para OK");
/*    */             } 
/* 55 */             Thread.sleep(2000L);
/*    */           } else {
/* 57 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("FEBRABAN150_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */           } 
/* 59 */         } catch (InterruptedException e) {
/* 60 */           logger.error("Febraban150 - Loading - Error loading file:[" + arquivo.getName() + "]");
/*    */           try {
/* 62 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("FEBRABAN150_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 63 */           } catch (IOException e1) {
/* 64 */             logger.error("Febraban150 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 66 */         } catch (IOException e) {
/* 67 */           logger.error("Febraban150 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 69 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("FEBRABAN150_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 70 */           } catch (IOException e1) {
/* 71 */             logger.error("Febraban150 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 73 */         } catch (SQLException e) {
/* 74 */           logger.error("Febraban150 - Inserting on Database - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 76 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("FEBRABAN150_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 77 */           } catch (IOException e1) {
/* 78 */             logger.error("Febraban150 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 83 */     logger.warn("Febraban150 - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\Febraban150.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */