/*    */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.roadcard;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.AbstractReader;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout500.Detalhe;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout500.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout500.RoadcardVO;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout500.Trailler;
/*    */ import com.github.ffpojo.file.reader.FileSystemFlatFileReader;
/*    */ import com.github.ffpojo.file.reader.FlatFileReaderDefinition;
/*    */ import com.github.ffpojo.file.reader.RecordType;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class Roadcard500Reader
/*    */   extends AbstractReader<RoadcardVO>
/*    */ {
/* 22 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.layouts.roadcard.Roadcard500Reader.class);
/*    */ 
/*    */   
/*    */   public RoadcardVO readFiles(File file) {
/* 26 */     FlatFileReaderDefinition ffDefinition = new FlatFileReaderDefinition(Detalhe.class);
/* 27 */     ffDefinition.setHeader(Header.class);
/* 28 */     ffDefinition.setTrailer(Trailler.class);
/*    */     
/* 30 */     RoadcardVO roadcardVO = new RoadcardVO();
/*    */     try {
/* 32 */       FileSystemFlatFileReader fileSystemFlatFileReader = new FileSystemFlatFileReader(file, ffDefinition);
/* 33 */       Header header = null;
/* 34 */       Trailler trailler = null;
/* 35 */       Detalhe detalhe = null;
/* 36 */       int contador = 0;
/* 37 */       for (Object record : fileSystemFlatFileReader) {
/* 38 */         RecordType recordType = fileSystemFlatFileReader.getRecordType();
/* 39 */         if (recordType == RecordType.HEADER) {
/* 40 */           header = (Header)record;
/* 41 */           header.setLinhaProcessada(++contador);
/* 42 */           roadcardVO.setHeader(header); continue;
/* 43 */         }  if (recordType == RecordType.BODY) {
/* 44 */           detalhe = (Detalhe)record;
/* 45 */           detalhe.setLinhaProcessada(++contador);
/* 46 */           roadcardVO.getListaDetalhe().add(detalhe); continue;
/* 47 */         }  if (recordType == RecordType.TRAILER) {
/* 48 */           trailler = (Trailler)record;
/* 49 */           trailler.setLinhaProcessada(++contador);
/* 50 */           roadcardVO.setTrailler(trailler);
/*    */         } 
/*    */       } 
/* 53 */     } catch (IOException e) {
/* 54 */       logger.error("Error reading file [" + file.getName() + "]" + e);
/*    */     } 
/* 56 */     return roadcardVO;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\roadcard\Roadcard500Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */