/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.allianz.writer;
/*    */ 
/*    */ import br.com.accesstage.cargas.services.allianz.writer.AbstractWriter;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.AllianzDebitoComplementarVO;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Detalhe;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class ComplementoTransformer
/*    */   extends AbstractWriter
/*    */ {
/* 17 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.allianz.writer.ComplementoTransformer.class);
/*    */ 
/*    */   
/*    */   public void transform(AllianzDebitoComplementarVO value, String outPath, String outPathComplResubmit) throws IOException {
/* 21 */     logger.warn("ComplementoTransformer: inicio - " + outPath);
/*    */     
/* 23 */     int contador = 1;
/* 24 */     File file = new File(outPath);
/* 25 */     StringBuffer strHeader = new StringBuffer();
/* 26 */     if (value.getHeader() != null) {
/* 27 */       strHeader.append(value.getHeader().getCodRegistro());
/* 28 */       strHeader.append(formatadorDireita(value.getHeader().getEmpresaAllianz(), " ", 3));
/* 29 */       strHeader.append(formatadorEsquerda(value.getHeader().getDataEnvio(), "0", 8));
/* 30 */       strHeader.append(formatadorEsquerda(value.getHeader().getHoraEnvio(), "0", 6));
/* 31 */       strHeader.append(formatadorEsquerda(value.getHeader().getNsa(), "0", 5));
/* 32 */       strHeader.append(formatadorDireita(null, " ", 727));
/*    */       
/* 34 */       BufferedWriter textFileWriter = new BufferedWriter(new FileWriter(file));
/* 35 */       textFileWriter.write(strHeader.toString());
/* 36 */       textFileWriter.newLine();
/* 37 */       StringBuffer strDetalhe = new StringBuffer();
/* 38 */       for (Detalhe detalhe : value.getListDetalhe()) {
/* 39 */         strDetalhe.append("5");
/* 40 */         strDetalhe.append(formatadorDireita(detalhe.getDocumento(), " ", 15));
/* 41 */         strDetalhe.append(detalhe.getTipoPessoa().trim());
/* 42 */         strDetalhe.append(formatadorDireita(detalhe.getNomeCliente(), " ", 40));
/* 43 */         strDetalhe.append(formatadorDireita(detalhe.getEndereco(), " ", 40));
/* 44 */         strDetalhe.append(formatadorDireita(detalhe.getBairro(), " ", 15));
/* 45 */         strDetalhe.append(formatadorEsquerda(detalhe.getCep(), "0", 8));
/* 46 */         strDetalhe.append(formatadorDireita(detalhe.getCidade(), " ", 15));
/* 47 */         strDetalhe.append(formatadorDireita(detalhe.getEstado(), " ", 2));
/* 48 */         strDetalhe.append(formatadorEsquerda(detalhe.getPoliza(), "0", 10));
/* 49 */         strDetalhe.append(formatadorDireita(detalhe.getApolice(), " ", 26));
/* 50 */         strDetalhe.append(formatadorDireita(detalhe.getEndosso(), " ", 6));
/* 51 */         strDetalhe.append(formatadorEsquerda(detalhe.getParcela(), "0", 2));
/* 52 */         strDetalhe.append(formatadorEsquerda(detalhe.getPlano(), "0", 2));
/* 53 */         strDetalhe.append(formatadorEsquerda(detalhe.getRecibo(), "0", 10));
/* 54 */         strDetalhe.append(formatadorEsquerda(detalhe.getDataVencimento(), "0", 8));
/* 55 */         strDetalhe.append(formatadorDireita(detalhe.getDsProduto(), " ", 40));
/* 56 */         strDetalhe.append(formatadorEsquerda(detalhe.getBancoDebito().trim(), "0", 3));
/* 57 */         strDetalhe.append(formatadorEsquerda(detalhe.getAgencia(), "0", 6));
/* 58 */         strDetalhe.append(formatadorDireita(detalhe.getAgenciaDV(), " ", 2));
/* 59 */         strDetalhe.append(formatadorDireita(detalhe.getContaCorrente(), " ", 20));
/* 60 */         strDetalhe.append(formatadorDireita(detalhe.getContaCorrenteDV(), " ", 2));
/* 61 */         strDetalhe.append(formatadorEsquerda(detalhe.getDataPrevCancelamento(), "0", 8));
/* 62 */         strDetalhe.append(formatadorDireita(detalhe.getEmailCliente(), " ", 80));
/* 63 */         strDetalhe.append(formatadorEsquerda(detalhe.getCelularCliente(), "0", 20));
/* 64 */         strDetalhe.append(formatadorDireita(detalhe.getEmailCorretor(), " ", 80));
/* 65 */         strDetalhe.append(formatadorEsquerda(detalhe.getCelularCorretor(), "0", 20));
/* 66 */         strDetalhe.append(formatadorEsquerda(detalhe.getAliquotaIOF(), "0", 8));
/* 67 */         strDetalhe.append(formatadorDireita(detalhe.getCodRejeicaoDebito(), " ", 10));
/* 68 */         strDetalhe.append(formatadorEsquerda(detalhe.getBancoBoleto(), "0", 3));
/* 69 */         strDetalhe.append(formatadorEsquerda(detalhe.getNossoNumero(), "0", 20));
/* 70 */         strDetalhe.append(formatadorDireita(detalhe.getLinhaDigitavel(), " ", 55));
/* 71 */         strDetalhe.append(formatadorDireita(detalhe.getCodigoBarras(), " ", 55));
/* 72 */         strDetalhe.append(formatadorEsquerda(detalhe.getValorBoleto(), "0", 10));
/* 73 */         strDetalhe.append(formatadorEsquerda(detalhe.getVencimentoBoleto(), "0", 8));
/* 74 */         strDetalhe.append(formatadorEsquerda(detalhe.getTipoCobranca(), "0", 1));
/* 75 */         strDetalhe.append(formatadorDireita(null, " ", 98));
/* 76 */         strDetalhe.append(System.lineSeparator());
/* 77 */         contador++;
/*    */       } 
/* 79 */       textFileWriter.write(strDetalhe.toString());
/* 80 */       contador++;
/* 81 */       StringBuffer strTrailler = new StringBuffer();
/* 82 */       strTrailler.append(value.getTrailler().getCodRegistro());
/* 83 */       strTrailler.append(formatadorEsquerda(String.valueOf(contador), "0", 9));
/* 84 */       strTrailler.append(formatadorDireita(null, " ", 740));
/*    */       
/* 86 */       textFileWriter.write(strTrailler.toString());
/* 87 */       textFileWriter.close();
/*    */       
/* 89 */       logger.warn("ComplementoTransformer: movendo arquivo " + outPath + " " + outPathComplResubmit);
/* 90 */       moveResubmit(new File(outPath), outPathComplResubmit);
/*    */       
/* 92 */       logger.warn("ComplementoTransformer: fim - " + outPath);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\allianz\writer\ComplementoTransformer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */