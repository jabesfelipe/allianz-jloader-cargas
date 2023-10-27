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
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.VolkswagenRetorno240VO;
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
/*     */ public class VolkswagenRetorno240Reader
/*     */   extends AbstractReader<VolkswagenRetorno240VO>
/*     */ {
/*  35 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.layouts.volkswagen.VolkswagenRetorno240Reader.class);
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
/*     */   public VolkswagenRetorno240VO readFiles(File file) {
/*  50 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/*  51 */     VolkswagenRetorno240VO retorno240vo = new VolkswagenRetorno240VO();
/*  52 */     int hdrLote = 0;
/*  53 */     PositionalRecordParser parser = null;
/*     */     try {
/*  55 */       InputStream bis = new FileInputStream(file);
/*  56 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/*  57 */       for (int index = 0; index < lines.size(); index++) {
/*  58 */         String linha = lines.get(index);
/*  59 */         String codRegistro = linha.substring(7, 8);
/*  60 */         String codSegmento = linha.substring(13, 14);
/*  61 */         String codSegmentoOpc = linha.substring(18, 19);
/*  62 */         RecordDescriptor recordDescriptor = null;
/*  63 */         if (HDR_ARQ.equalsIgnoreCase(codRegistro)) {
/*  64 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/*  65 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  66 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/*  67 */           header.setLinhaProcessada(index);
/*  68 */           retorno240vo.setHeader(header);
/*  69 */         } else if (TRLLR_ARQ.equalsIgnoreCase(codRegistro)) {
/*  70 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/*  71 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  72 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/*  73 */           trailler.setLinhaProcessada(index);
/*  74 */           retorno240vo.setTrailler(trailler);
/*  75 */         } else if (HDR_LOTE.equalsIgnoreCase(codRegistro)) {
/*  76 */           hdrLote++;
/*  77 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HeaderLote.class);
/*  78 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  79 */           HeaderLote headerLote = (HeaderLote)parser.parseFromText(HeaderLote.class, linha);
/*  80 */           headerLote.setLinhaProcessada(index);
/*  81 */           retorno240vo.getListHeaderLote().add(headerLote);
/*  82 */         } else if (TRLLR_LOTE.equalsIgnoreCase(codRegistro)) {
/*  83 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TraillerLote.class);
/*  84 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  85 */           TraillerLote traillerLote = (TraillerLote)parser.parseFromText(TraillerLote.class, linha);
/*  86 */           traillerLote.setLinhaProcessada(index);
/*  87 */           traillerLote.setHeaderLote(hdrLote);
/*  88 */           retorno240vo.getListTraillerLote().add(traillerLote);
/*  89 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_A.equalsIgnoreCase(codSegmento)) {
/*  90 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoA.class);
/*  91 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  92 */           SegmentoA segmentoA = (SegmentoA)parser.parseFromText(SegmentoA.class, linha);
/*  93 */           segmentoA.setLinhaProcessada(index);
/*  94 */           segmentoA.setHeaderLote(hdrLote);
/*  95 */           retorno240vo.getListSegmentoA().add(segmentoA);
/*  96 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_B.equalsIgnoreCase(codSegmento)) {
/*  97 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoB.class);
/*  98 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  99 */           SegmentoB segmentoB = (SegmentoB)parser.parseFromText(SegmentoB.class, linha);
/* 100 */           segmentoB.setLinhaProcessada(index);
/* 101 */           segmentoB.setHeaderLote(hdrLote);
/* 102 */           retorno240vo.getListSegmentoB().add(segmentoB);
/* 103 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_J.equalsIgnoreCase(codSegmento)) {
/* 104 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoJ.class);
/* 105 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 106 */           SegmentoJ segmentoJ = (SegmentoJ)parser.parseFromText(SegmentoJ.class, linha);
/* 107 */           segmentoJ.setLinhaProcessada(index);
/* 108 */           segmentoJ.setHeaderLote(hdrLote);
/* 109 */           retorno240vo.getListSegmentoJ().add(segmentoJ);
/* 110 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_J.equalsIgnoreCase(codSegmento) && SEGMENTO_J52.equalsIgnoreCase(codSegmentoOpc)) {
/* 111 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoJ52.class);
/* 112 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 113 */           SegmentoJ52 segmentoJ52 = (SegmentoJ52)parser.parseFromText(SegmentoJ52.class, linha);
/* 114 */           segmentoJ52.setLinhaProcessada(index);
/* 115 */           segmentoJ52.setHeaderLote(hdrLote);
/* 116 */           retorno240vo.getListSegmentoJ52().add(segmentoJ52);
/* 117 */         } else if (DETALHE_LOTE.equalsIgnoreCase(codRegistro) && SEGMENTO_Z.equalsIgnoreCase(codSegmento)) {
/* 118 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Segmento3Z.class);
/* 119 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 120 */           Segmento3Z segmentoZ = (Segmento3Z)parser.parseFromText(Segmento3Z.class, linha);
/* 121 */           segmentoZ.setLinhaProcessada(index);
/* 122 */           segmentoZ.setHeaderLote(hdrLote);
/* 123 */           retorno240vo.getListSegmento3Z().add(segmentoZ);
/*     */         } 
/*     */       } 
/* 126 */     } catch (FileNotFoundException e) {
/* 127 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 128 */     } catch (IOException e) {
/* 129 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*     */     } 
/* 131 */     return retorno240vo;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\volkswagen\VolkswagenRetorno240Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */