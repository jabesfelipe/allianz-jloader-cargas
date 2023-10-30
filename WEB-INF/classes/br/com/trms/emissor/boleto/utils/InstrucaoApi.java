/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import br.com.trms.emissor.boleto.utils.ConexaoInstrucao;
/*    */ import br.com.trms.emissor.boleto.utils.EnvioInstrucao;
/*    */ import br.com.trms.emissor.boleto.utils.SequenciaInstrucao;
/*    */ 
/*    */ public class InstrucaoApi
/*    */ {
/*    */   public SequenciaInstrucao[] envioInstrucao(EnvioInstrucao envio) throws Exception {
/* 10 */     return (SequenciaInstrucao[])(new ConexaoInstrucao()).invoqueMetodo("geraRemessaApi", envio, SequenciaInstrucao[].class);
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\InstrucaoApi.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */