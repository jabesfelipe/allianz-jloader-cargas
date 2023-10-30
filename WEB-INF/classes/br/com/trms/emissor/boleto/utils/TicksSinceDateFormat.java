/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.FieldPosition;
/*    */ import java.text.ParsePosition;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TicksSinceDateFormat
/*    */   extends DateFormat
/*    */ {
/*    */   public StringBuffer format(Date date, StringBuffer buffer, FieldPosition field) {
/* 60 */     long millis = date.getTime();
/* 61 */     return new StringBuffer("/Date(" + millis + ")/");
/*    */   }
/*    */   
/*    */   public Date parse(String string, ParsePosition position) {
/* 65 */     int start = string.indexOf("(") + 1;
/* 66 */     int end = string.indexOf(")");
/* 67 */     String ms = string.substring(start, end);
/* 68 */     Date date = new Date(Long.parseLong(ms));
/* 69 */     position.setIndex(string.length() - 1);
/* 70 */     return date;
/*    */   }
/*    */   
/*    */   public Object clone() {
/* 74 */     return new br.com.trms.emissor.boleto.utils.TicksSinceDateFormat();
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\TicksSinceDateFormat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */