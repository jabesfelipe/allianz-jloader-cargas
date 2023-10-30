/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ public class SQLAppender
/*    */ {
/*    */   private StringBuilder appendSQL;
/*    */   private StringBuilder appendSQLSemQuebra;
/*    */   
/*    */   public SQLAppender(int capacidade) {
/*  9 */     this.appendSQL = new StringBuilder(capacidade);
/* 10 */     this.appendSQLSemQuebra = new StringBuilder(capacidade);
/*    */   }
/*    */   
/*    */   public StringBuilder getAppendSQL() {
/* 14 */     return this.appendSQL;
/*    */   }
/*    */   
/*    */   public void setAppendSQL(StringBuilder appendSQL) {
/* 18 */     this.appendSQL = appendSQL;
/*    */   }
/*    */   
/*    */   public void appendSQL(String texto) {
/* 22 */     String quebraLinha = System.getProperty("line.separator");
/* 23 */     this.appendSQL.append(quebraLinha).append(texto);
/* 24 */     this.appendSQLSemQuebra.append(texto);
/*    */   }
/*    */   
/*    */   public StringBuilder getAppendSQLSemQuebra() {
/* 28 */     return this.appendSQLSemQuebra;
/*    */   }
/*    */   
/*    */   public void setAppendSQLSemQuebra(StringBuilder appendSQLSemQuebra) {
/* 32 */     this.appendSQLSemQuebra = appendSQLSemQuebra;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\SQLAppender.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */