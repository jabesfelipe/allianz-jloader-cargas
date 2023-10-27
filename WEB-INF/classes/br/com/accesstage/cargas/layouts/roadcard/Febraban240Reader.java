/*     */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.roadcard;
/*     */ 
/*     */ import br.com.accesstage.cargas.layouts.AbstractReader;
/*     */ import br.com.accesstage.loader.util.commom.ffpojo.Constantes240;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.Febraban240VO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.HdrExtL033;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.HdrLteCobL045;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.Header;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.HeaderL043;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentY50;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoA;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoB;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoC;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoE;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoP;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoQ;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoR;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoS;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoSImpressao3;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoT;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoU;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoY04;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.Trailler;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.TraillerCobL045;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.TraillerL043;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.TrllExtL033;
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
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class Febraban240Reader
/*     */   extends AbstractReader<Febraban240VO>
/*     */   implements Constantes240
/*     */ {
/*  48 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.layouts.roadcard.Febraban240Reader.class);
/*     */ 
/*     */   
/*     */   public Febraban240VO readFiles(File file) {
/*  52 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/*  53 */     Febraban240VO febraban240vo = new Febraban240VO();
/*  54 */     PositionalRecordParser parser = null;
/*  55 */     int hdrL045 = 0;
/*  56 */     int hdrL043 = 0;
/*  57 */     int hdrL033 = 0;
/*  58 */     String tpoImpressao = null;
/*     */     try {
/*  60 */       InputStream bis = new FileInputStream(file);
/*  61 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/*  62 */       for (int index = 0; index < lines.size(); index++) {
/*  63 */         String linha = lines.get(index);
/*  64 */         String codRegistro = linha.substring(7, 8);
/*  65 */         String codOperacao = linha.substring(8, 9);
/*  66 */         String tpoServico = linha.substring(9, 11);
/*  67 */         String codSegmento = linha.substring(13, 14);
/*  68 */         String identRegOpcional = linha.substring(17, 19);
/*  69 */         tpoImpressao = linha.substring(17, 18);
/*  70 */         RecordDescriptor recordDescriptor = null;
/*  71 */         if ("0".equalsIgnoreCase(codRegistro)) {
/*  72 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/*  73 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  74 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/*  75 */           header.setLinhaProcessada(index);
/*  76 */           febraban240vo.setHeader(header);
/*  77 */         } else if ("9".equalsIgnoreCase(codRegistro)) {
/*  78 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/*  79 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  80 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/*  81 */           trailler.setLinhaProcessada(index);
/*  82 */           febraban240vo.setTrailler(trailler);
/*  83 */         } else if ("1".equalsIgnoreCase(codRegistro) && "01".equals(tpoServico)) {
/*  84 */           hdrL045++;
/*  85 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HdrLteCobL045.class);
/*  86 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  87 */           HdrLteCobL045 cobL045 = (HdrLteCobL045)parser.parseFromText(HdrLteCobL045.class, linha);
/*  88 */           cobL045.setLinhaProcessada(index);
/*  89 */           febraban240vo.getListHdrLteCobL045().add(cobL045);
/*  90 */         } else if ("1".equalsIgnoreCase(codRegistro) && "C".equals(codOperacao)) {
/*  91 */           hdrL043++;
/*  92 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HeaderL043.class);
/*  93 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  94 */           HeaderL043 headerL043 = (HeaderL043)parser.parseFromText(HeaderL043.class, linha);
/*  95 */           headerL043.setLinhaProcessada(index);
/*  96 */           febraban240vo.getListHeaderL043().add(headerL043);
/*  97 */         } else if ("5".equalsIgnoreCase(codRegistro) && hdrL043 > 0) {
/*  98 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TraillerL043.class);
/*  99 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 100 */           TraillerL043 traillerL043 = (TraillerL043)parser.parseFromText(TraillerL043.class, linha);
/* 101 */           traillerL043.setLinhaProcessada(index);
/* 102 */           febraban240vo.getListTraillerL043().add(traillerL043);
/* 103 */         } else if ("5".equalsIgnoreCase(codRegistro) && hdrL045 > 0) {
/* 104 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TraillerCobL045.class);
/* 105 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 106 */           TraillerCobL045 traillerCobL045 = (TraillerCobL045)parser.parseFromText(TraillerCobL045.class, linha);
/* 107 */           traillerCobL045.setLinhaProcessada(index);
/* 108 */           febraban240vo.getListTraillerCobL045().add(traillerCobL045);
/* 109 */         } else if ("1".equalsIgnoreCase(codRegistro) && "E".equals(codOperacao) && "04".equals(tpoServico)) {
/* 110 */           hdrL033++;
/* 111 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HdrExtL033.class);
/* 112 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 113 */           HdrExtL033 hdrExtL033 = (HdrExtL033)parser.parseFromText(HdrExtL033.class, linha);
/* 114 */           hdrExtL033.setLinhaProcessada(index);
/* 115 */           febraban240vo.getListHdrExtL033().add(hdrExtL033);
/* 116 */         } else if ("5".equalsIgnoreCase(codRegistro) && hdrL033 > 0) {
/* 117 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TrllExtL033.class);
/* 118 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 119 */           TrllExtL033 trllExtL033 = (TrllExtL033)parser.parseFromText(TrllExtL033.class, linha);
/* 120 */           trllExtL033.setLinhaProcessada(index);
/* 121 */           febraban240vo.getListTrllExtL033().add(trllExtL033);
/* 122 */         } else if ("3".equalsIgnoreCase(codRegistro) && "U".equals(codSegmento)) {
/* 123 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoU.class);
/* 124 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 125 */           SegmentoU segmentoU = (SegmentoU)parser.parseFromText(SegmentoU.class, linha);
/* 126 */           segmentoU.setLinhaProcessada(index);
/* 127 */           febraban240vo.getListSegmentoU().add(segmentoU);
/* 128 */         } else if ("3".equalsIgnoreCase(codRegistro) && "T".equals(codSegmento)) {
/* 129 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoT.class);
/* 130 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 131 */           SegmentoT segmentoT = (SegmentoT)parser.parseFromText(SegmentoT.class, linha);
/* 132 */           segmentoT.setLinhaProcessada(index);
/* 133 */           febraban240vo.getListSegmentoT().add(segmentoT);
/* 134 */         } else if ("3".equalsIgnoreCase(codRegistro) && "Y".equals(codSegmento) && "50".equals(identRegOpcional)) {
/* 135 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentY50.class);
/* 136 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 137 */           SegmentY50 segmentY50 = (SegmentY50)parser.parseFromText(SegmentY50.class, linha);
/* 138 */           segmentY50.setLinhaProcessada(index);
/* 139 */           febraban240vo.getListSegmentY50().add(segmentY50);
/* 140 */         } else if ("3".equalsIgnoreCase(codRegistro) && "Y".equals(codSegmento) && "03".equals(identRegOpcional)) {
/* 141 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoY04.class);
/* 142 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 143 */           SegmentoY04 segmentoY04 = (SegmentoY04)parser.parseFromText(SegmentoY04.class, linha);
/* 144 */           segmentoY04.setLinhaProcessada(index);
/* 145 */           febraban240vo.getListSegmentoY04().add(segmentoY04);
/* 146 */         } else if ("3".equalsIgnoreCase(codRegistro) && "P".equals(codSegmento)) {
/* 147 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoP.class);
/* 148 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 149 */           SegmentoP segmentoP = (SegmentoP)parser.parseFromText(SegmentoP.class, linha);
/* 150 */           segmentoP.setLinhaProcessada(index);
/* 151 */           febraban240vo.getListSegmentoP().add(segmentoP);
/* 152 */         } else if ("3".equalsIgnoreCase(codRegistro) && "Q".equals(codSegmento)) {
/* 153 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoQ.class);
/* 154 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 155 */           SegmentoQ segmentoQ = (SegmentoQ)parser.parseFromText(SegmentoQ.class, linha);
/* 156 */           segmentoQ.setLinhaProcessada(index);
/* 157 */           febraban240vo.getListSegmentoQ().add(segmentoQ);
/* 158 */         } else if ("3".equalsIgnoreCase(codRegistro) && "R".equals(codSegmento)) {
/* 159 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoR.class);
/* 160 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 161 */           SegmentoR segmentoR = (SegmentoR)parser.parseFromText(SegmentoR.class, linha);
/* 162 */           segmentoR.setLinhaProcessada(index);
/* 163 */           febraban240vo.getListSegmentoR().add(segmentoR);
/* 164 */         } else if ("3".equalsIgnoreCase(codRegistro) && "S".equals(codSegmento)) {
/* 165 */           if (tpoImpressao.equals("1") || tpoImpressao.equals("2")) {
/* 166 */             recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoS.class);
/* 167 */             parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 168 */             SegmentoS segmentoS = (SegmentoS)parser.parseFromText(SegmentoS.class, linha);
/* 169 */             segmentoS.setLinhaProcessada(index);
/* 170 */             febraban240vo.getListSegmentoS().add(segmentoS);
/*     */           } 
/* 172 */           if (tpoImpressao.equalsIgnoreCase("3")) {
/* 173 */             recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoSImpressao3.class);
/* 174 */             parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 175 */             SegmentoSImpressao3 segmentoSImpressao3 = (SegmentoSImpressao3)parser.parseFromText(SegmentoSImpressao3.class, linha);
/* 176 */             segmentoSImpressao3.setLinhaProcessada(index);
/* 177 */             febraban240vo.getListSegmentSImpressao3().add(segmentoSImpressao3);
/*     */           } 
/* 179 */         } else if ("3".equalsIgnoreCase(codRegistro) && "E".equals(codSegmento)) {
/* 180 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoE.class);
/* 181 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 182 */           SegmentoE segmentoE = (SegmentoE)parser.parseFromText(SegmentoE.class, linha);
/* 183 */           segmentoE.setLinhaProcessada(index);
/* 184 */           febraban240vo.getListSegmentoE().add(segmentoE);
/* 185 */         } else if ("3".equalsIgnoreCase(codRegistro) && "A".equals(codSegmento)) {
/* 186 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoA.class);
/* 187 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 188 */           SegmentoA segmentoA = (SegmentoA)parser.parseFromText(SegmentoA.class, linha);
/* 189 */           segmentoA.setLinhaProcessada(index);
/* 190 */           febraban240vo.getListSegmentoA().add(segmentoA);
/* 191 */         } else if ("3".equalsIgnoreCase(codRegistro) && "B".equals(codSegmento)) {
/* 192 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoB.class);
/* 193 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 194 */           SegmentoB segmentoB = (SegmentoB)parser.parseFromText(SegmentoB.class, linha);
/* 195 */           segmentoB.setLinhaProcessada(index);
/* 196 */           febraban240vo.getListSegmentoB().add(segmentoB);
/* 197 */         } else if ("3".equalsIgnoreCase(codRegistro) && "C".equals(codSegmento)) {
/* 198 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoC.class);
/* 199 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 200 */           SegmentoC segmentoC = (SegmentoC)parser.parseFromText(SegmentoC.class, linha);
/* 201 */           segmentoC.setLinhaProcessada(index);
/* 202 */           febraban240vo.getListSegmentoC().add(segmentoC);
/*     */         } 
/*     */       } 
/* 205 */     } catch (FileNotFoundException e) {
/* 206 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 207 */     } catch (IOException e) {
/* 208 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*     */     } 
/* 210 */     return febraban240vo;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\roadcard\Febraban240Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */