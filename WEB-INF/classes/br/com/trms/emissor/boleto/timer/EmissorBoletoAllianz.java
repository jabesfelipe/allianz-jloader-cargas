/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.timer;
/*    */ 
/*    */ import br.com.trms.emissor.boleto.HtmlToPdf;
/*    */ import br.com.trms.emissor.boleto.dao.BoletoDAO;
/*    */ import br.com.trms.emissor.boleto.timer.AbstractTimer;
/*    */ import br.com.trms.emissor.boleto.vo.Boleto;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.scheduling.annotation.Scheduled;
/*    */ 
/*    */ public class EmissorBoletoAllianz
/*    */   extends AbstractTimer
/*    */ {
/* 16 */   private static Logger logger = Logger.getLogger(br.com.trms.emissor.boleto.timer.EmissorBoletoAllianz.class);
/*    */   
/*    */   @Autowired
/*    */   private BoletoDAO boletoDAO;
/*    */   
/*    */   @Autowired
/*    */   private HtmlToPdf htmlToPdf;
/*    */   
/*    */   @Scheduled(cron = "${timer.schedule.allianz.gerar.boleto}")
/*    */   public void execute() {
/* 26 */     logger.warn("EmissorBoletoAllianz - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/* 27 */     load("ALLIANZBOLETO");
/* 28 */     String pathOutput = (String)getConfig().get("ALLIANZCOBRANCASANTARET_BOLETO_GEN");
/* 29 */     String pathTemp = (String)getConfig().get("ALLIANZCOBRANCASANTARET_BOLETO_TMP");
/* 30 */     List<Boleto> listaBoleto = this.boletoDAO.getBoletos();
/*    */     
/* 32 */     for (Boleto boleto : listaBoleto) {
/*    */       try {
/* 34 */         Thread.sleep(500L);
/* 35 */       } catch (InterruptedException e) {
/* 36 */         logger.error("TimerError");
/*    */       } 
/* 38 */       this.htmlToPdf.generateBoleto(boleto, pathOutput, pathTemp);
/*    */     } 
/*    */     
/* 41 */     logger.warn("EmissorBoletoAllianz - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\boleto\timer\EmissorBoletoAllianz.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */