/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.parser;
/*    */ 
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Detalhe;
/*    */ import br.com.trms.emissor.boleto.vo.Boleto;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class ParserBoleto
/*    */ {
/* 14 */   protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*    */ 
/*    */   
/*    */   public Boleto convert(Detalhe detalhe) {
/* 18 */     Boleto boleto = new Boleto();
/* 19 */     boleto.setBanco(String.valueOf(detalhe.getBancoBoleto()) + "- 7");
/* 20 */     boleto.setNossoNumero(detalhe.getNossoNumero());
/* 21 */     boleto.setDocumentoSacado(detalhe.getDocumento());
/* 22 */     boleto.setNomeSacado(detalhe.getNomeCliente());
/* 23 */     boleto.setEndereco(detalhe.getEndereco());
/* 24 */     boleto.setCidade(detalhe.getCidade());
/* 25 */     boleto.setCep(detalhe.getCep());
/* 26 */     boleto.setCodigoBarras(detalhe.getCodigoBarras());
/* 27 */     boleto.setLinhaDigitavel(detalhe.getLinhaDigitavel());
/* 28 */     boleto.setAgenciaCedente(detalhe.getAgencia());
/* 29 */     boleto.setCodigoCedente(detalhe.getContaCorrente());
/* 30 */     boleto.setLocalPagamento("PAGAR PREFERENCIALMENTE NO BANCO SANTANDER");
/* 31 */     boleto.setDataDocumento(this.sdf.format(new Date()));
/* 32 */     boleto.setEspecie("R$");
/* 33 */     boleto.setEspecieDocumento("R$");
/* 34 */     boleto.setDataDocumento(detalhe.getDataVencimento());
/*    */     
/* 36 */     double valorBoleto = (new Double(detalhe.getValorBoleto())).doubleValue();
/* 37 */     double valorBoletoAjustado = valorBoleto / 100.0D;
/* 38 */     boleto.setValorCobrado(Double.valueOf(valorBoletoAjustado));
/*    */     
/* 40 */     Double valorJuros = new Double(detalhe.getValorBoleto());
/* 41 */     double valorAjustado = valorJuros.doubleValue() / 100.0D;
/* 42 */     valorAjustado *= 0.0027D;
/* 43 */     boleto.setMulta(Double.valueOf(valorAjustado));
/*    */     
/* 45 */     return boleto;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\boleto\parser\ParserBoleto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */