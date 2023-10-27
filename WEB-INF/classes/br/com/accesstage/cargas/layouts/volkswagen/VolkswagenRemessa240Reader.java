/*     */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.volkswagen;
/*     */ 
/*     */ import br.com.accesstage.cargas.layouts.AbstractReader;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.Header;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.HeaderLote;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.Segmento3Z;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.SegmentoA;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.SegmentoB;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.SegmentoJ;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.SegmentoJ52;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.Trailler;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.TraillerLote;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.VolkswagenRemessa240VO;
/*     */ import com.github.ffpojo.container.HybridMetadataContainer;
/*     */ import com.github.ffpojo.metadata.RecordDescriptor;
/*     */ import com.github.ffpojo.metadata.positional.PositionalRecordDescriptor;
/*     */ import com.github.ffpojo.parser.PositionalRecordParser;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class VolkswagenRemessa240Reader
/*     */   extends AbstractReader<VolkswagenRemessa240VO>
/*     */ {
/*  35 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.layouts.volkswagen.VolkswagenRemessa240Reader.class);
/*     */   
/*  37 */   public static String HDR_ARQ = "0";
/*  38 */   public static String TRLLR_ARQ = "9";
/*  39 */   public static String HDR_LOTE = "1";
/*  40 */   public static String TRLLR_LOTE = "5";
/*  41 */   public static String DETALHE_LOTE = "3";
/*  42 */   public static String SEGMENTO_A = "A";
/*  43 */   public static String SEGMENTO_B = "B";
/*  44 */   public static String SEGMENTO_J = "J";
/*  45 */   public static String SEGMENTO_J52 = "52";
/*  46 */   public static String SEGMENTO_Z = "Z";
/*     */ 
/*     */ 
/*     */   
/*     */   public VolkswagenRemessa240VO readFiles(File file) {
/*  51 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/*  52 */     VolkswagenRemessa240VO remessa240vo = new VolkswagenRemessa240VO();
/*  53 */     PositionalRecordParser parser = null;
/*  54 */     int hdrLote = 0;
/*     */     
/*     */     try {
/*  57 */       InputStream bis = new FileInputStream(file);
/*  58 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/*  59 */       for (int index = 0; index < lines.size(); index++) {
/*  60 */         String linha = lines.get(index);
/*  61 */         String codRegistro = linha.substring(7, 8);
/*  62 */         String codSegmento = linha.substring(13, 14);
/*  63 */         String codSegmentoOpc = linha.substring(18, 19);
/*  64 */         RecordDescriptor recordDescriptor = null;
/*  65 */         if (HDR_ARQ.equalsIgnoreCase(codRegistro)) {
/*  66 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/*  67 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  68 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/*  69 */           header.setLinhaProcessada(index);
/*  70 */           remessa240vo.setHeader(header);
/*  71 */         } else if (TRLLR_ARQ.equalsIgnoreCase(codRegistro)) {
/*  72 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/*  73 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  74 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/*  75 */           trailler.setLinhaProcessada(index);
/*  76 */           remessa240vo.setTrailler(trailler);
/*  77 */         } else if (HDR_LOTE.equalsIgnoreCase(codRegistro)) {
/*  78 */           hdrLote++;
/*  79 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HeaderLote.class);
/*  80 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  81 */           HeaderLote headerLote = (HeaderLote)parser.parseFromText(HeaderLote.class, linha);
/*  82 */           headerLote.setLinhaProcessada(index);
/*  83 */           remessa240vo.getListHeaderLote().add(headerLote);
/*  84 */         } else if (TRLLR_LOTE.equalsIgnoreCase(codRegistro)) {
/*  85 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TraillerLote.class);
/*  86 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  87 */           TraillerLote traillerLote = (TraillerLote)parser.parseFromText(TraillerLote.class, linha);
/*  88 */           traillerLote.setLinhaProcessada(index);
/*  89 */           traillerLote.setHeaderLote(hdrLote);
/*  90 */           remessa240vo.getListTraillerLote().add(traillerLote);
/*  91 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_A.equalsIgnoreCase(codSegmento)) {
/*  92 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoA.class);
/*  93 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  94 */           SegmentoA segmentoA = (SegmentoA)parser.parseFromText(SegmentoA.class, linha);
/*  95 */           segmentoA.setLinhaProcessada(index);
/*  96 */           segmentoA.setHeaderLote(hdrLote);
/*  97 */           remessa240vo.getListSegmentoA().add(segmentoA);
/*  98 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_B.equalsIgnoreCase(codSegmento)) {
/*  99 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoB.class);
/* 100 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 101 */           SegmentoB segmentoB = (SegmentoB)parser.parseFromText(SegmentoB.class, linha);
/* 102 */           segmentoB.setLinhaProcessada(index);
/* 103 */           segmentoB.setHeaderLote(hdrLote);
/* 104 */           remessa240vo.getListSegmentoB().add(segmentoB);
/* 105 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_J.equalsIgnoreCase(codSegmento)) {
/* 106 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoJ.class);
/* 107 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 108 */           SegmentoJ segmentoJ = (SegmentoJ)parser.parseFromText(SegmentoJ.class, linha);
/* 109 */           segmentoJ.setLinhaProcessada(index);
/* 110 */           segmentoJ.setHeaderLote(hdrLote);
/* 111 */           remessa240vo.getListSegmentoJ().add(segmentoJ);
/* 112 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_J.equalsIgnoreCase(codSegmento) && SEGMENTO_J52.equalsIgnoreCase(codSegmentoOpc)) {
/* 113 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoJ52.class);
/* 114 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 115 */           SegmentoJ52 segmentoJ52 = (SegmentoJ52)parser.parseFromText(SegmentoJ52.class, linha);
/* 116 */           segmentoJ52.setLinhaProcessada(index);
/* 117 */           segmentoJ52.setHeaderLote(hdrLote);
/* 118 */           remessa240vo.getListSegmentoJ52().add(segmentoJ52);
/* 119 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_Z.equalsIgnoreCase(codSegmento)) {
/* 120 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Segmento3Z.class);
/* 121 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 122 */           Segmento3Z segmentoZ = (Segmento3Z)parser.parseFromText(Segmento3Z.class, linha);
/* 123 */           segmentoZ.setLinhaProcessada(index);
/* 124 */           segmentoZ.setHeaderLote(hdrLote);
/* 125 */           remessa240vo.getListSegmento3Z().add(segmentoZ);
/*     */         } 
/*     */       } 
/* 128 */     } catch (FileNotFoundException e) {
/* 129 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 130 */     } catch (IOException e) {
/* 131 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*     */     } 
/* 133 */     return remessa240vo;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\volkswagen\VolkswagenRemessa240Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */