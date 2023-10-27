/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.services.volkswagen.VolkswagenRetorno240Service;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
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
/*    */ public class VolkswagenRetorno240
/*    */   extends AbstractTimer
/*    */ {
/* 26 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.VolkswagenRetorno240.class);
/*    */   
/*    */   @Autowired
/*    */   private VolkswagenRetorno240Service service;
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.volkswagen.retorno240}")
/*    */   public void execute() {
/* 33 */     load("VOLKSWAGENRETORNO240");
/* 34 */     logger.warn("VolkswagenRetorno240 - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 35 */     File files = new File((String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_INPUT"));
/* 36 */     for (File arquivo : files.listFiles()) {
/* 37 */       if (arquivo.getName().contains("@")) {
/* 38 */         ArquivoCarga arquivoCarga = createArquivoCargaArquivo(arquivo, (String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_INPUT"), "VOLKSWAGENRETORNO240");
/*    */         try {
/* 40 */           if (arquivoCarga != null && !arquivoCarga.isExists()) {
/* 41 */             this.service.load(arquivo.getName(), (String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_INPUT"), arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 42 */             this.service.iniciarProcessamento(arquivoCarga.getCodigoArquivo().intValue(), 9999, (String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_INPUT"), arquivo.getName(), (String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_ERROR"));
/*    */             
/* 44 */             this.service.process(arquivo, arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 45 */             String newPath = this.service.moveArquivo(null, true, (String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_INPUT"), arquivo.getName());
/* 46 */             if (newPath != null && !newPath.isEmpty()) {
/* 47 */               this.service.alteraStatus(arquivoCarga.getCodigoArquivo().intValue(), StatusCarga.STATUS_OK.getCodStatusCarga().intValue(), null, arquivo.getName(), newPath);
/*    */               
/* 49 */               logger.info("[JLoader]- VolkswagenRetorno240 - Iniciou Altera Status para OK");
/*    */             } 
/* 51 */             Thread.sleep(2000L);
/*    */           } else {
/* 53 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */           } 
/* 55 */         } catch (IOException e) {
/* 56 */           logger.error("[JLoader]-  VolkswagenRetorno240 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 58 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 59 */           } catch (IOException e1) {
/* 60 */             logger.error("[JLoader]- VolkswagenRetorno240 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 62 */         } catch (SQLException e) {
/* 63 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 65 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 66 */           } catch (IOException e1) {
/* 67 */             logger.error("[JLoader]- VolkswagenRetorno240 - - Erro ao mover o arquivo :" + arquivo.getName());
/*    */           } 
/* 69 */         } catch (Exception e) {
/* 70 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 72 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 73 */           } catch (IOException e1) {
/* 74 */             logger.error("[JLoader]- VolkswagenRetorno240 - - Erro ao mover o arquivo :" + (String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_ERROR") + arquivo.getName());
/*    */           } 
/* 76 */         } catch (Throwable e) {
/* 77 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 79 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 80 */           } catch (IOException e1) {
/* 81 */             logger.error("[JLoader]- VolkswagenRetorno240 -- Erro ao mover o arquivo :" + (String)getConfig().get("VOLKSWAGENRETORNO240_CARGA_ERROR") + arquivo.getName());
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\VolkswagenRetorno240.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */