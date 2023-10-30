/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Timer
/*    */ {
/*    */   private Date startTime;
/*    */   private Date endTime;
/* 13 */   private List<Long> intervalos = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void start() {
/* 17 */     this.startTime = new Date();
/*    */   }
/*    */   
/*    */   public void end() {
/* 21 */     if (this.startTime != null) {
/* 22 */       this.endTime = new Date();
/* 23 */       Long diff = Long.valueOf(this.endTime.getTime() - this.startTime.getTime());
/* 24 */       this.intervalos.add(diff);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Date getStartTime() {
/* 29 */     return this.startTime;
/*    */   }
/*    */   
/*    */   public Date getEndTime() {
/* 33 */     return this.endTime;
/*    */   }
/*    */   
/*    */   public Long getLastDiff() {
/* 37 */     return Long.valueOf((this.intervalos.size() > 0) ? ((Long)this.intervalos.get(this.intervalos.size() - 1)).longValue() : 0L);
/*    */   }
/*    */   
/*    */   public Long getMediaAritimeticaIntervalos() {
/* 41 */     int qtdZerados = 0;
/* 42 */     int size = this.intervalos.size();
/* 43 */     Long soma = Long.valueOf(0L);
/* 44 */     for (Long diff : this.intervalos) {
/* 45 */       if (Long.valueOf(0L) == diff) {
/* 46 */         qtdZerados++; continue;
/*    */       } 
/* 48 */       soma = Long.valueOf(soma.longValue() + diff.longValue());
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 53 */     return Long.valueOf((size != qtdZerados) ? (soma.longValue() / (size - qtdZerados)) : 0L);
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\Timer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */