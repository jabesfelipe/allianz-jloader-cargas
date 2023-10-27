/*    */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.volkswagen.VolkswagenRemessa500Reader;
/*    */ import br.com.accesstage.cargas.services.volkswagen.VolkswagenRemessa500Service;
/*    */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*    */ import br.com.accesstage.loader.util.constantes.processo.StatusCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.ArquivoCarga;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.remessa.VolkswagenRemessa500VO;
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
/*    */ public class VolkswagenRemessa500
/*    */   extends AbstractTimer
/*    */ {
/* 28 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.VolkswagenRemessa500.class);
/*    */   
/*    */   @Autowired
/*    */   private VolkswagenRemessa500Service service;
/*    */   
/*    */   @Autowired
/*    */   private VolkswagenRemessa500Reader reader;
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.volkswagen.remessa500}")
/*    */   public void execute() {
/* 38 */     load("VOLKSWAGENREMESSA500");
/* 39 */     logger.warn("VolkswagenRemessa500 - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 40 */     File files = new File((String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_INPUT"));
/* 41 */     for (File arquivo : files.listFiles()) {
/* 42 */       if (arquivo.getName().contains("@")) {
/* 43 */         ArquivoCarga arquivoCarga = createArquivoCargaArquivo(arquivo, (String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_INPUT"), "VOLKSWAGENREMESSA500");
/*    */         try {
/* 45 */           Thread.sleep(1000L);
/* 46 */           if (arquivoCarga != null && !arquivoCarga.isExists()) {
/* 47 */             this.service.load(arquivo.getName(), (String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_INPUT"), arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 48 */             this.service.iniciarProcessamento(arquivoCarga.getCodigoArquivo().intValue(), 9999, (String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_INPUT"), arquivo.getName(), (String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_ERROR"));
/*    */             
/* 50 */             VolkswagenRemessa500VO remessa500 = this.reader.readFiles(arquivo);
/* 51 */             this.service.process(remessa500, arquivoCarga.getCodigoArquivo().intValue(), 9999);
/* 52 */             String newPath = this.service.moveArquivo(null, true, (String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_INPUT"), arquivo.getName());
/* 53 */             if (newPath != null && !newPath.isEmpty()) {
/* 54 */               this.service.alteraStatus(arquivoCarga.getCodigoArquivo().intValue(), StatusCarga.STATUS_OK.getCodStatusCarga().intValue(), null, arquivo.getName(), newPath);
/*    */               
/* 56 */               logger.info("[JLoader]- VolkswagenRemessa500 - Iniciou Altera Status para OK");
/*    */             } 
/* 58 */             Thread.sleep(2000L);
/*    */           } else {
/* 60 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/*    */           } 
/* 62 */         } catch (IOException e) {
/* 63 */           logger.error("[JLoader]-  VolkswagenRemessa500 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           try {
/* 65 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 66 */           } catch (IOException e1) {
/* 67 */             logger.error("[JLoader]- VolkswagenRemessa500 - Moving - Error moving file:[" + arquivo.getName() + "]");
/*    */           } 
/* 69 */         } catch (SQLException e) {
/* 70 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 72 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 73 */           } catch (IOException e1) {
/* 74 */             logger.error("[JLoader]- VolkswagenRemessa500 - - Erro ao mover o arquivo :" + arquivo.getName());
/*    */           } 
/* 76 */         } catch (Exception e) {
/* 77 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 79 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 80 */           } catch (IOException e1) {
/* 81 */             logger.error("[JLoader]- VolkswagenRemessa500 - - Erro ao mover o arquivo :" + (String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_ERROR") + arquivo.getName());
/*    */           } 
/* 83 */         } catch (Throwable e) {
/* 84 */           this.service.trataErro(StatusCarga.STATUS_ERRO_SISTEMA.getCodStatusCarga().intValue(), e, "erroSistema");
/*    */           try {
/* 86 */             Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get((String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_ERROR") + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 87 */           } catch (IOException e1) {
/* 88 */             logger.error("[JLoader]- VolkswagenRemessa500 -- Erro ao mover o arquivo :" + (String)getConfig().get("VOLKSWAGENREMESSA500_CARGA_ERROR") + arquivo.getName());
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 95 */     logger.warn("VolkswagenRemessa500 - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\VolkswagenRemessa500.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */