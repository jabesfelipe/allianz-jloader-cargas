/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import br.com.trms.emissor.boleto.utils.Envio;
/*    */ import br.com.trms.emissor.boleto.utils.InvocarMetodo;
/*    */ import br.com.trms.emissor.boleto.utils.SequenciaSms;
/*    */ 
/*    */ public class SMSApi
/*    */ {
/*    */   public SequenciaSms[] envioSms(Envio envio) throws Exception {
/* 10 */     return (SequenciaSms[])(new InvocarMetodo()).invoqueMetodo("envioSMS", envio, SequenciaSms[].class);
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\SMSApi.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */