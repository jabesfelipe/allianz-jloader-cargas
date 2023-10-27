/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.allianz.AllianzDebitoComplementarReader;
/*    */ import br.com.accesstage.cargas.services.allianz.AllianzDebitoComplementarServico;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.AllianzDebitoComplementarVO;
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
/*    */ 
/*    */ @Component
/*    */ @EnableAsync
/*    */ @EnableScheduling
/*    */ public class AllianzDebitoComplementar
/*    */   extends AbstractTimer
/*    */ {
/*    */   @Autowired
/*    */   private AllianzDebitoComplementarServico service;
/*    */   @Autowired
/*    */   private AllianzDebitoComplementarReader reader;
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.allianz.debito.complemento}")
/*    */   public void execute() {
/* 35 */     load("ALLIANZDEBITO");
/* 36 */     logger.warn("AllianzDebitoComplementar - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 37 */     File files = new File((String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_INPUT"));
/*    */     
/* 39 */     for (File arquivo : files.listFiles()) {
/*    */       
/* 41 */       if (arquivo.getName().contains("@")) {
/* 42 */         ArquivoCarga arquivoCarga = createArquivoCargaArquivo(arquivo, (String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_INPUT"), "FEBRABAN150");
/*    */         try {
/* 44 */           if (arquivoCarga != null && !arquivoCarga.isExists()) {
/* 45 */             this.service.load(arquivo.getName(), (String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_INPUT"), arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 46 */             this.service.iniciarProcessamento(arquivoCarga.getCodigoArquivo().intValue(), 9999, (String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_INPUT"), arquivo.getName(), (String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_ERROR"));
/*    */             
/* 48 */             AllianzDebitoComplementarVO allianzDebitoVO = this.reader.readFiles(arquivo);
/* 49 */             this.service.process(allianzDebitoVO, arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 50 */             String newPath = this.service.moveArquivo(null, true, (String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_INPUT"), arquivo.getName());
/* 51 */             if (newPath != null && !newPath.isEmpty()) {
/* 52 */               this.service.alteraStatus(arquivoCarga.getCodigoArquivo().intValue(), StatusCarga.STATUS_OK.getCodStatusCarga().intValue(), null, arquivo.getName(), newPath);
/*    */               
/* 54 */               logger.info("[JLoader] - Iniciou Altera Status para OK");
/*    */             } 
/* 56 */             Thread.sleep(2000L);
/*    */           } else {
/* 58 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */           } 
/* 60 */         } catch (InterruptedException e) {
/* 61 */           logger.error("AllianzDebitoComplementar - Loading - Error loading file:[" + arquivo.getName() + "]");
/*    */           try {
/* 63 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 64 */           } catch (IOException e1) {
/* 65 */             logger.error("AllianzDebitoComplementar - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 67 */         } catch (IOException e) {
/* 68 */           logger.error("AllianzDebitoComplementar - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 70 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 71 */           } catch (IOException e1) {
/* 72 */             logger.error("AllianzDebitoComplementar - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 74 */         } catch (SQLException e) {
/* 75 */           logger.error("AllianzDebitoComplementar - Inserting on Database - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 77 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZDEBITOCOMPLEM_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 78 */           } catch (IOException e1) {
/* 79 */             logger.error("AllianzDebitoComplementar - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 84 */     logger.warn("AllianzDebitoComplementar - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\AllianzDebitoComplementar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */