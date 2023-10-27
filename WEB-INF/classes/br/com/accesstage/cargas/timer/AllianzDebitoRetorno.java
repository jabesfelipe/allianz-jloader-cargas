/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.allianz.AlliansDebitoReader;
/*    */ import br.com.accesstage.cargas.services.allianz.AllianzDebitoRetornoServico;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.AllianzDebitoVO;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Paths;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Date;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.scheduling.annotation.EnableAsync;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
/*    */ import org.springframework.scheduling.annotation.Scheduled;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ @EnableAsync
/*    */ @EnableScheduling
/*    */ public class AllianzDebitoRetorno
/*    */   extends AbstractTimer
/*    */ {
/* 28 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.AllianzDebitoRetorno.class);
/*    */   
/*    */   @Autowired
/*    */   private AllianzDebitoRetornoServico service;
/*    */   @Autowired
/*    */   private AlliansDebitoReader alliansDebitoReader;
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.allianz.debito.retorno}")
/*    */   public void execute() {
/* 37 */     load("ALLIANZDEBITO");
/* 38 */     logger.warn("AllianzDebitoRetorno - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 39 */     File files = new File((String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_INPUT"));
/*    */     
/* 41 */     for (File arquivo : files.listFiles()) {
/*    */       
/* 43 */       if (arquivo.getName().contains("@")) {
/* 44 */         ArquivoCarga arquivoCarga = createArquivoCargaArquivo(arquivo, (String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_INPUT"), "FEBRABAN150");
/*    */         try {
/* 46 */           if (arquivoCarga != null && !arquivoCarga.isExists()) {
/* 47 */             this.service.load(arquivo.getName(), (String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_INPUT"), arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 48 */             this.service.iniciarProcessamento(arquivoCarga.getCodigoArquivo().intValue(), 9999, (String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_INPUT"), arquivo.getName(), (String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_ERROR"));
/*    */             
/* 50 */             AllianzDebitoVO allianzDebitoVO = this.alliansDebitoReader.readFiles(arquivo);
/* 51 */             this.service.process(allianzDebitoVO, arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 52 */             String newPath = this.service.moveArquivo(null, true, (String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_INPUT"), arquivo.getName());
/* 53 */             if (newPath != null && !newPath.isEmpty()) {
/* 54 */               this.service.alteraStatus(arquivoCarga.getCodigoArquivo().intValue(), StatusCarga.STATUS_OK.getCodStatusCarga().intValue(), null, arquivo.getName(), newPath);
/*    */               
/* 56 */               logger.info("[JLoader] - Iniciou Altera Status para OK");
/*    */             } 
/* 58 */             Thread.sleep(2000L);
/*    */           } else {
/* 60 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */           } 
/* 62 */         } catch (InterruptedException e) {
/* 63 */           logger.error("AllianzDebitoRetorno - Loading - Error loading file:[" + arquivo.getName() + "]");
/*    */           try {
/* 65 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 66 */           } catch (IOException e1) {
/* 67 */             logger.error("AllianzDebitoRetorno - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 69 */         } catch (IOException e) {
/* 70 */           logger.error("AllianzDebitoRetorno - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 72 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 73 */           } catch (IOException e1) {
/* 74 */             logger.error("AllianzDebitoRetorno - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 76 */         } catch (SQLException e) {
/* 77 */           logger.error("AllianzDebitoRetorno - Inserting on Database - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 79 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ALLIANZDEBITORETORNO_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 80 */           } catch (IOException e1) {
/* 81 */             logger.error("AllianzDebitoRetorno - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 86 */     logger.warn("AllianzDebitoRemessa - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\AllianzDebitoRetorno.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */