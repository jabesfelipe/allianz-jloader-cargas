/*     */ package WEB-INF.classes.br.com.accesstage.cargas.services.allianz.writer;
/*     */ 
/*     */ import br.com.accesstage.cargas.dao.Report240DAO;
/*     */ import br.com.accesstage.cargas.services.allianz.writer.AbstractWriter;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.AllianzDebitoComplementarVO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Detalhe;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.santander.Header;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.santander.HeaderLote;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.santander.SantanderAllianzVO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.santander.SegmentoP;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.santander.SegmentoQ;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.santander.Trailler;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.santander.TraillerLote;
/*     */ import com.github.ffpojo.FFPojoHelper;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class SantanderTransformer
/*     */   extends AbstractWriter
/*     */ {
/*  33 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.allianz.writer.SantanderTransformer.class);
/*     */   
/*     */   @Autowired
/*     */   private Report240DAO report240dao;
/*     */ 
/*     */   
/*     */   public void transform(AllianzDebitoComplementarVO complementar, String outPath, String outPathResubmit) throws IOException, ParseException {
/*  40 */     logger.warn("SantanderTransformer: inicio - " + outPath);
/*     */     
/*  42 */     FFPojoHelper ffpojo = FFPojoHelper.getInstance();
/*  43 */     File file = new File(outPath);
/*  44 */     SantanderAllianzVO allianzVO = new SantanderAllianzVO();
/*  45 */     Header header = new Header();
/*  46 */     HeaderLote headerLote = new HeaderLote();
/*  47 */     Long sequencialArquivo = this.report240dao.getNroSequencialArquivo();
/*  48 */     int contador = 0;
/*  49 */     SimpleDateFormat fmt = new SimpleDateFormat("ddMMyyyy");
/*  50 */     String dataAtual = fmt.format(new Date());
/*     */ 
/*     */     
/*  53 */     header.setNomeEmpresa(formatadorDireita("ALLIANZ SEGUROS S.A.", " ", 30));
/*  54 */     header.setNomeBanco(formatadorDireita("BANCO SANTANDER S.A.", " ", 30));
/*  55 */     header.setNroSequencial(sequencialArquivo.toString());
/*  56 */     header.setDataArquivo(dataAtual);
/*     */ 
/*     */     
/*  59 */     headerLote.setNroRemessaRetorno(dataAtual);
/*  60 */     headerLote.setDataRemessaRetorno(dataAtual);
/*  61 */     headerLote.setNomeCedente(formatadorDireita("ALLIANZ SEGUROS S.A.", " ", 30));
/*  62 */     BufferedWriter textFileWriter = new BufferedWriter(new FileWriter(file));
/*  63 */     String line = ffpojo.parseToText(header);
/*  64 */     textFileWriter.write(line);
/*  65 */     textFileWriter.newLine();
/*  66 */     line = ffpojo.parseToText(headerLote);
/*  67 */     textFileWriter.write(line);
/*  68 */     textFileWriter.newLine();
/*  69 */     for (Detalhe detalhe : complementar.getListDetalhe()) {
/*  70 */       contador++;
/*  71 */       SegmentoP segmentoP = new SegmentoP();
/*  72 */       SegmentoQ segmentoQ = new SegmentoQ();
/*     */       
/*  74 */       segmentoP.setNroSeqLte(formatadorEsquerda(String.valueOf(contador), "0", 5));
/*  75 */       segmentoP.setDadosAgencia("2271701300097370130009737");
/*  76 */       segmentoP.setNossoNumero(detalhe.getNossoNumero());
/*  77 */       segmentoP.setNroDocumento(formatadorDireita(detalhe.getRecibo(), " ", 15));
/*  78 */       segmentoP.setVlrJuros(formatadorEsquerda(detalhe.getVlrJuros(), "0", 15));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  83 */       String data = "";
/*  84 */       if (StringUtils.isNotEmpty(detalhe.getVencimentoBoleto())) {
/*  85 */         data = detalhe.getVencimentoBoleto().substring(6, 8) + "" + detalhe.getVencimentoBoleto().substring(4, 6) + "" + detalhe.getVencimentoBoleto().substring(0, 4);
/*  86 */         segmentoP.setDtaVencTit(data);
/*     */       } 
/*  88 */       segmentoP.setDtaJuros(data);
/*  89 */       segmentoP.setVlrNmlTit(formatadorEsquerda(detalhe.getValorBoleto(), "0", 15));
/*     */ 
/*     */       
/*  92 */       segmentoP.setDtaEmissaoTit(detalhe.getDtaEmissao());
/*  93 */       segmentoP.setVlrIofRecolhido(formatadorEsquerda(detalhe.getAliquotaIOF(), "0", 15));
/*     */       
/*  95 */       if (StringUtils.isNotEmpty(detalhe.getApolice()))
/*  96 */         segmentoP.setDscIdentTitEmp(formatadorDireita(detalhe.getApolice().trim(), " ", 25)); 
/*  97 */       line = ffpojo.parseToText(segmentoP);
/*     */ 
/*     */ 
/*     */       
/* 101 */       textFileWriter.write(line);
/* 102 */       textFileWriter.newLine();
/*     */       
/* 104 */       contador++;
/* 105 */       segmentoQ.setNroSeqLte(formatadorEsquerda(String.valueOf(contador), "0", 5));
/* 106 */       segmentoQ.setTpoInsc(detalhe.getTipoPessoa());
/* 107 */       if (StringUtils.isNotEmpty(detalhe.getDocumento()))
/* 108 */         segmentoQ.setNroInsc(formatadorEsquerda(detalhe.getDocumento(), "0", 15)); 
/* 109 */       segmentoQ.setNome(formatadorDireita(detalhe.getNomeCliente(), " ", 40));
/* 110 */       segmentoQ.setEndereco(formatadorDireita(detalhe.getEndereco(), " ", 40));
/* 111 */       segmentoQ.setBairro(formatadorDireita(detalhe.getBairro(), " ", 15));
/* 112 */       segmentoQ.setNroCep(detalhe.getCep().substring(0, 4));
/* 113 */       segmentoQ.setNroCepSufixo(detalhe.getCep().substring(5, 7));
/* 114 */       segmentoQ.setNmeCidade(formatadorDireita(detalhe.getCidade(), " ", 15));
/* 115 */       segmentoQ.setUf(detalhe.getEstado());
/* 116 */       segmentoQ.setQtdeParcelas(formatadorEsquerda(detalhe.getPlano(), "0", 3));
/* 117 */       segmentoQ.setNumeroPlano(formatadorEsquerda(detalhe.getParcela(), "0", 3));
/*     */       
/* 119 */       allianzVO.getMapSeqmentos().put(segmentoP, segmentoQ);
/* 120 */       line = ffpojo.parseToText(segmentoQ);
/*     */ 
/*     */ 
/*     */       
/* 124 */       textFileWriter.write(line);
/* 125 */       textFileWriter.newLine();
/*     */     } 
/*     */ 
/*     */     
/* 129 */     TraillerLote traillerLote = new TraillerLote();
/* 130 */     int contadorTraillerLote = contador + 2;
/* 131 */     traillerLote.setQtdeRegistros(formatadorEsquerda(String.valueOf(contadorTraillerLote), "0", 6));
/* 132 */     line = ffpojo.parseToText(traillerLote);
/* 133 */     textFileWriter.write(line);
/* 134 */     textFileWriter.newLine();
/*     */     
/* 136 */     Trailler trailler = new Trailler();
/* 137 */     int contadorTrailler = contador + 4;
/* 138 */     trailler.setQtdeRegistrosArquivo(formatadorEsquerda(String.valueOf(contadorTrailler), "0", 6));
/*     */     
/* 140 */     allianzVO.setHeader(header);
/* 141 */     allianzVO.setHeaderLote(headerLote);
/* 142 */     allianzVO.setTrailler(trailler);
/* 143 */     line = ffpojo.parseToText(trailler);
/* 144 */     textFileWriter.write(line);
/* 145 */     textFileWriter.close();
/*     */     
/* 147 */     logger.warn("SantanderTransformer: movendo arquivo " + outPath + " " + outPathResubmit);
/* 148 */     moveResubmit(new File(outPath), outPathResubmit);
/*     */     
/* 150 */     logger.warn("SantanderTransformer: final - " + outPath);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 156 */     Double valorJuros = new Double("12000");
/* 157 */     double valorAjustado = valorJuros.doubleValue() / 100.0D;
/* 158 */     valorAjustado *= 0.0027D;
/* 159 */     String valor = String.valueOf(valorAjustado);
/* 160 */     System.out.println(valor.substring(2, 4));
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\allianz\writer\SantanderTransformer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */