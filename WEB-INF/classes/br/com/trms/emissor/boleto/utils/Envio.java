/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Envio
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 699001016558560142L;
/*    */   public String[] destinatarios;
/*    */   public String mensagem;
/*    */   public Date data;
/*    */   public String assunto;
/*    */   
/*    */   public String toString() {
/* 16 */     return "Envio{destinatarios=" + this.destinatarios + ", mensagem=" + this.mensagem + ", data=" + this.data + ", assunto=" + this.assunto + '}';
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\Envio.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */