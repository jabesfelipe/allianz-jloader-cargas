/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.roadcard.Roadcard500Reader;
/*    */ import br.com.accesstage.cargas.services.roadcard.Roadcard500Service;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout500.RoadcardVO;
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
/*    */ public class RoadCard500
/*    */   extends AbstractTimer
/*    */ {
/* 28 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.RoadCard500.class);
/*    */   
/*    */   @Autowired
/*    */   private Roadcard500Service roadcardService;
/*    */   @Autowired
/*    */   private Roadcard500Reader roadcard500Reader;
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.roadcard500}")
/*    */   public void execute() {
/* 37 */     load("ROADCARD500");
/* 38 */     logger.warn("RoadCard500 - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 39 */     File files = new File((String)getConfig().get("ROADCARD500_CARGA_INPUT"));
/* 40 */     for (File arquivo : files.listFiles()) {
/* 41 */       if (arquivo.getName().contains("@")) {
/* 42 */         ArquivoCarga arquivoCarga = createArquivoCargaArquivo(arquivo, (String)getConfig().get("ROADCARD500_CARGA_INPUT"), "ROADCARD500");
/*    */         try {
/* 44 */           if (arquivoCarga != null && !arquivoCarga.isExists()) {
/* 45 */             this.roadcardService.load(arquivo.getName(), (String)getConfig().get("ROADCARD500_CARGA_INPUT"), arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 46 */             RoadcardVO roadcardVO = this.roadcard500Reader.readFiles(arquivo);
/* 47 */             this.roadcardService.iniciarProcessamento(arquivoCarga.getCodigoArquivo().intValue(), 9999, (String)getConfig().get("ROADCARD500_CARGA_INPUT"), arquivo.getName(), (String)getConfig().get("ROADCARD500_CARGA_ERROR"));
/*    */             
/* 49 */             this.roadcardService.process(roadcardVO, arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 50 */             String newPath = this.roadcardService.moveArquivo(null, true, (String)getConfig().get("ROADCARD500_CARGA_INPUT"), arquivo.getName());
/* 51 */             if (newPath != null && !newPath.isEmpty()) {
/* 52 */               this.roadcardService.alteraStatus(arquivoCarga.getCodigoArquivo().intValue(), StatusCarga.STATUS_OK.getCodStatusCarga().intValue(), null, arquivo.getName(), newPath);
/*    */               
/* 54 */               logger.info("[JLoader] - Iniciou Altera Status para OK");
/*    */             } 
/* 56 */             Thread.sleep(2000L);
/*    */           } else {
/* 58 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ROADCARD500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */           } 
/* 60 */         } catch (IOException e) {
/* 61 */           logger.error("RoadCard500 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 63 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ROADCARD500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 64 */           } catch (IOException e1) {
/* 65 */             logger.error("RoadCard500 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 67 */         } catch (SQLException e) {
/* 68 */           this.roadcardService.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 70 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ROADCARD500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 71 */           } catch (IOException e1) {
/* 72 */             logger.error("[JLoader] - Erro ao mover o arquivo :" + arquivo.getName());
/*    */           } 
/* 74 */         } catch (Exception e) {
/* 75 */           this.roadcardService.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 77 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ROADCARD500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 78 */           } catch (IOException e1) {
/* 79 */             logger.error("[JLoader] - Erro ao mover o arquivo :" + (String)getConfig().get("ROADCARD500_CARGA_ERROR") + arquivo.getName());
/*    */           } 
/* 81 */         } catch (Throwable e) {
/* 82 */           this.roadcardService.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 84 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("ROADCARD500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 85 */           } catch (IOException e1) {
/* 86 */             logger.error("[JLoader] - Erro ao mover o arquivo :" + (String)getConfig().get("ROADCARD500_CARGA_ERROR") + arquivo.getName());
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 91 */     logger.warn("RoadCard500 - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\RoadCard500.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */