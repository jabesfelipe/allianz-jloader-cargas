/*    */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.roadcard;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.AbstractReader;
/*    */ import br.com.accesstage.loader.util.commom.ffpojo.Constantes150;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.DetalheF;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.DetalheFV4;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.DetalheG;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.Febraban150VO;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.Trailler;
/*    */ import com.github.ffpojo.container.HybridMetadataContainer;
/*    */ import com.github.ffpojo.metadata.RecordDescriptor;
/*    */ import com.github.ffpojo.metadata.positional.PositionalRecordDescriptor;
/*    */ import com.github.ffpojo.parser.PositionalRecordParser;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class Febraban150Reader
/*    */   extends AbstractReader<Febraban150VO>
/*    */   implements Constantes150
/*    */ {
/* 32 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.layouts.roadcard.Febraban150Reader.class);
/*    */ 
/*    */   
/*    */   public Febraban150VO readFiles(File file) {
/* 36 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/* 37 */     PositionalRecordParser parser = null;
/* 38 */     Febraban150VO febraban150vo = new Febraban150VO();
/*    */     try {
/* 40 */       InputStream bis = new FileInputStream(file);
/* 41 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/* 42 */       boolean v4 = false;
/* 43 */       for (int index = 0; index < lines.size(); index++) {
/* 44 */         String linha = lines.get(index);
/* 45 */         String codRegistro = linha.substring(0, 1);
/* 46 */         RecordDescriptor recordDescriptor = null;
/* 47 */         if ("A".equalsIgnoreCase(codRegistro)) {
/* 48 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/* 49 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 50 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/* 51 */           header.setLinhaProcessada(index);
/* 52 */           v4 = header.isV4();
/* 53 */           febraban150vo.setHeader(header);
/*    */         } 
/* 55 */         if ("F".equalsIgnoreCase(codRegistro)) {
/* 56 */           if (v4) {
/* 57 */             recordDescriptor = hybridMetadataContainer.getRecordDescriptor(DetalheFV4.class);
/* 58 */             parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 59 */             DetalheFV4 detalheFV4 = (DetalheFV4)parser.parseFromText(DetalheFV4.class, linha);
/* 60 */             detalheFV4.setLinhaProcessada(index);
/* 61 */             febraban150vo.getListDetalheFV4().add(detalheFV4);
/*    */           } else {
/* 63 */             recordDescriptor = hybridMetadataContainer.getRecordDescriptor(DetalheF.class);
/* 64 */             parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 65 */             DetalheF detalheF = (DetalheF)parser.parseFromText(DetalheF.class, linha);
/* 66 */             detalheF.setLinhaProcessada(index);
/* 67 */             febraban150vo.getListDetalheF().add(detalheF);
/*    */           } 
/*    */         }
/* 70 */         if ("G".equalsIgnoreCase(codRegistro)) {
/* 71 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(DetalheG.class);
/* 72 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 73 */           DetalheG detalheG = (DetalheG)parser.parseFromText(DetalheG.class, linha);
/* 74 */           detalheG.setLinhaProcessada(index);
/* 75 */           febraban150vo.getListDetalheG().add(detalheG);
/*    */         } 
/* 77 */         if ("Z".equalsIgnoreCase(codRegistro)) {
/* 78 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/* 79 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 80 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/* 81 */           trailler.setLinhaProcessada(index);
/* 82 */           febraban150vo.setTrailler(trailler);
/*    */         } 
/*    */       } 
/* 85 */     } catch (FileNotFoundException e) {
/* 86 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 87 */     } catch (IOException e) {
/* 88 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*    */     } 
/* 90 */     return febraban150vo;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\roadcard\Febraban150Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */