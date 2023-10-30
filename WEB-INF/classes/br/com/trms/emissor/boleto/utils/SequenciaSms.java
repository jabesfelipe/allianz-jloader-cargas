/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SequenciaSms
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1740505820984626303L;
/*    */   public long celular;
/*    */   public int sequencia;
/*    */   
/*    */   public String toString() {
/* 17 */     return "SequenciaSms{celular=" + this.celular + ", sequencia=" + this.sequencia + '}';
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\SequenciaSms.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */