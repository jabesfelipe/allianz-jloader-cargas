/*     */ package WEB-INF.classes.br.com.trms.emissor.boleto;
/*     */ 
/*     */ import br.com.trms.emissor.boleto.BoletoTemplateParser;
/*     */ import br.com.trms.emissor.boleto.dao.BoletoDAO;
/*     */ import br.com.trms.emissor.boleto.vo.Boleto;
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.PageSize;
/*     */ import com.itextpdf.text.pdf.PdfWriter;
/*     */ import com.itextpdf.tool.xml.XMLWorkerHelper;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class HtmlToPdf
/*     */ {
/*     */   @Value("${allianz.boleto.nome.cedente}")
/*     */   private String nomeCedente;
/*     */   @Value("${allianz.boleto.endereco.cedente}")
/*     */   private String enderecoCedente;
/*     */   @Value("${allianz.boleto.documento.cedente}")
/*     */   private String documentoCedente;
/*     */   @Value("${allianz.boleto.url}")
/*     */   private String urlBoleto;
/*     */   @Autowired
/*     */   private BoletoDAO boletoDAO;
/*  41 */   private SimpleDateFormat fmtNmeBoleto = new SimpleDateFormat("yyyyMMdd");
/*  42 */   private SimpleDateFormat timestamp = new SimpleDateFormat("yyyyMMdd");
/*     */   
/*  44 */   private Logger looger = Logger.getLogger(br.com.trms.emissor.boleto.HtmlToPdf.class);
/*     */   
/*     */   @Autowired
/*     */   private BoletoTemplateParser boletoTemplateParser;
/*     */ 
/*     */   
/*     */   public void generateBoleto(Boleto boleto, String pathOut, String pathOutTemp) {
/*  51 */     PdfWriter pdfWriter = null;
/*     */     
/*  53 */     String nomeArquivo = "";
/*  54 */     Document document = new Document();
/*     */     try {
/*  56 */       boleto.setNomeCedente(this.nomeCedente);
/*  57 */       boleto.setEnderecoCedente(this.enderecoCedente);
/*  58 */       boleto.setDocumentoCedente(this.documentoCedente);
/*  59 */       boleto.setUrl(this.urlBoleto);
/*  60 */       String htmlBoleto = this.boletoTemplateParser.generateHTML(boleto);
/*     */       
/*  62 */       document = new Document();
/*     */       
/*  64 */       document.addAuthor("");
/*  65 */       document.addCreationDate();
/*  66 */       document.addProducer();
/*  67 */       document.addCreator("");
/*  68 */       document.addTitle("");
/*  69 */       document.setPageSize(PageSize.LETTER);
/*     */       
/*  71 */       nomeArquivo = gerarNomeBoleto(boleto, pathOutTemp);
/*     */       
/*  73 */       OutputStream file = new FileOutputStream(new File(nomeArquivo));
/*  74 */       pdfWriter = PdfWriter.getInstance(document, file);
/*     */ 
/*     */       
/*  77 */       document.open();
/*  78 */       XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
/*  79 */       xmlWorkerHelper.getDefaultCssResolver(true);
/*  80 */       xmlWorkerHelper.parseXHtml(pdfWriter, document, new StringReader(htmlBoleto));
/*     */       
/*  82 */       document.close();
/*     */       
/*  84 */       pdfWriter.close();
/*  85 */       String tempArquivo = nomeArquivo;
/*  86 */       String nomeNovo = nomeArquivo.replace(pathOutTemp, pathOut);
/*  87 */       Path temp = Files.move(Paths.get(tempArquivo, new String[0]), Paths.get(nomeNovo, new String[0]), new java.nio.file.CopyOption[0]);
/*  88 */       if (temp != null) {
/*  89 */         this.boletoDAO.udpateBoleto(boleto.getNossoNumero());
/*     */       } else {
/*  91 */         this.looger.error("Erro ao mover arquivo pdf: " + tempArquivo);
/*     */       } 
/*  93 */     } catch (Exception e) {
/*  94 */       File file = new File(nomeArquivo);
/*  95 */       if (file.exists()) {
/*  96 */         file.delete();
/*     */       }
/*  98 */       this.looger.error("Erro ao converter o pdf: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String gerarNomeBoleto(Boleto boleto, String pathOut) {
/* 106 */     StringBuilder nomeBoleto = new StringBuilder();
/* 107 */     nomeBoleto.append(pathOut);
/* 108 */     nomeBoleto.append(this.timestamp.format(new Date()));
/* 109 */     nomeBoleto.append("@");
/* 110 */     nomeBoleto.append(this.fmtNmeBoleto.format(new Date()));
/* 111 */     nomeBoleto.append("_");
/* 112 */     nomeBoleto.append("01");
/* 113 */     nomeBoleto.append("_");
/* 114 */     nomeBoleto.append("01");
/* 115 */     nomeBoleto.append("_");
/* 116 */     nomeBoleto.append(formatadorEsquerda(String.valueOf(this.boletoDAO.getSequencialBoleto()), "0", 5));
/* 117 */     nomeBoleto.append("_");
/* 118 */     nomeBoleto.append(boleto.getPoliza());
/* 119 */     nomeBoleto.append("_");
/* 120 */     nomeBoleto.append("00000");
/* 121 */     nomeBoleto.append("_");
/* 122 */     nomeBoleto.append(boleto.getEndosso());
/* 123 */     nomeBoleto.append("_");
/* 124 */     nomeBoleto.append(boleto.getParcela());
/* 125 */     nomeBoleto.append(".pdf");
/* 126 */     return nomeBoleto.toString();
/*     */   }
/*     */   
/*     */   public String formatadorEsquerda(String valor, String padding, int total) {
/* 130 */     StringBuilder strQuery = new StringBuilder();
/* 131 */     if (StringUtils.isEmpty(valor)) {
/* 132 */       for (int i = 0; i < total; i++) {
/* 133 */         strQuery.append(padding);
/*     */       }
/*     */     } else {
/* 136 */       for (int i = valor.trim().length(); i < total; i++) {
/* 137 */         strQuery.append(padding);
/*     */       }
/* 139 */       strQuery.append(valor.trim());
/*     */     } 
/* 141 */     return strQuery.toString();
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\boleto\HtmlToPdf.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */