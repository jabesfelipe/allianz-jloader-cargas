/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.volkswagen.VolkswagenRetorno500Reader;
/*    */ import br.com.accesstage.cargas.services.volkswagen.VolkswagenRetorno500Service;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.VolkswagenRetorno500VO;
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
/*    */ public class VolkswagenRetorno500
/*    */   extends AbstractTimer
/*    */ {
/* 28 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.VolkswagenRetorno500.class);
/*    */   
/*    */   @Autowired
/*    */   private VolkswagenRetorno500Service service;
/*    */   
/*    */   @Autowired
/*    */   private VolkswagenRetorno500Reader reader;
/*    */ 
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.volkswagen.retorno500}")
/*    */   public void execute() {
/* 39 */     load("VOLKSWAGENRETORNO500");
/* 40 */     logger.warn("VolkswagenRetorno500 - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 41 */     File files = new File((String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_INPUT"));
/* 42 */     for (File arquivo : files.listFiles()) {
/* 43 */       if (arquivo.getName().contains("@")) {
/* 44 */         ArquivoCarga arquivoCarga = createArquivoCargaArquivo(arquivo, (String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_INPUT"), "VOLKSWAGENRETORNO500");
/*    */         try {
/* 46 */           Thread.sleep(1000L);
/* 47 */           if (arquivoCarga != null && !arquivoCarga.isExists()) {
/* 48 */             this.service.load(arquivo.getName(), (String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_INPUT"), arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 49 */             this.service.iniciarProcessamento(arquivoCarga.getCodigoArquivo().intValue(), 9999, (String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_INPUT"), arquivo.getName(), (String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_ERROR"));
/*    */             
/* 51 */             VolkswagenRetorno500VO retorno500 = this.reader.readFiles(arquivo);
/* 52 */             this.service.process(retorno500, arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 53 */             String newPath = this.service.moveArquivo(null, true, (String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_INPUT"), arquivo.getName());
/* 54 */             if (newPath != null && !newPath.isEmpty()) {
/* 55 */               this.service.alteraStatus(arquivoCarga.getCodigoArquivo().intValue(), StatusCarga.STATUS_OK.getCodStatusCarga().intValue(), null, arquivo.getName(), newPath);
/*    */               
/* 57 */               logger.info("[JLoader]- VolkswagenRetorno500 - Iniciou Altera Status para OK");
/*    */             } 
/* 59 */             Thread.sleep(2000L);
/*    */           } else {
/* 61 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */           } 
/* 63 */         } catch (IOException e) {
/* 64 */           logger.error("[JLoader]-  VolkswagenRetorno500 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 66 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 67 */           } catch (IOException e1) {
/* 68 */             logger.error("[JLoader]- VolkswagenRetorno500 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 70 */         } catch (SQLException e) {
/* 71 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 73 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 74 */           } catch (IOException e1) {
/* 75 */             logger.error("[JLoader]- VolkswagenRetorno500 - - Erro ao mover o arquivo :" + arquivo.getName());
/*    */           } 
/* 77 */         } catch (Exception e) {
/* 78 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 80 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 81 */           } catch (IOException e1) {
/* 82 */             logger.error("[JLoader]- VolkswagenRetorno500 - - Erro ao mover o arquivo :" + (String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_ERROR") + arquivo.getName());
/*    */           } 
/* 84 */         } catch (Throwable e) {
/* 85 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 87 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 88 */           } catch (IOException e1) {
/* 89 */             logger.error("[JLoader]- VolkswagenRetorno500 -- Erro ao mover o arquivo :" + (String)getConfig().get("VOLKSWAGENRETORNO500_CARGA_ERROR") + arquivo.getName());
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 94 */     logger.warn("VolkswagenRetorno500 - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\VolkswagenRetorno500.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */