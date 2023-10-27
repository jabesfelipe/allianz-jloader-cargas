/*    */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.volkswagen;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.AbstractReader;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.Detalhe;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.Trailler;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.VolkswagenRetorno500VO;
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
/*    */ 
/*    */ @Component
/*    */ public class VolkswagenRetorno500Reader
/*    */   extends AbstractReader<VolkswagenRetorno500VO>
/*    */ {
/* 23 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.layouts.volkswagen.VolkswagenRetorno500Reader.class);
/*    */ 
/*    */   
/*    */   public VolkswagenRetorno500VO readFiles(File file) {
/* 27 */     FlatFileReaderDefinition ffDefinition = new FlatFileReaderDefinition(Detalhe.class);
/* 28 */     ffDefinition.setHeader(Header.class);
/* 29 */     ffDefinition.setTrailer(Trailler.class);
/*    */     
/* 31 */     VolkswagenRetorno500VO retorno500vo = new VolkswagenRetorno500VO();
/*    */     try {
/* 33 */       FileSystemFlatFileReader fileSystemFlatFileReader = new FileSystemFlatFileReader(file, ffDefinition);
/* 34 */       Header header = null;
/* 35 */       Trailler trailler = null;
/* 36 */       Detalhe detalhe = null;
/* 37 */       int contador = 0;
/* 38 */       for (Object record : fileSystemFlatFileReader) {
/* 39 */         RecordType recordType = fileSystemFlatFileReader.getRecordType();
/* 40 */         if (recordType == RecordType.HEADER) {
/* 41 */           header = (Header)record;
/* 42 */           header.setLinhaProcessada(++contador);
/* 43 */           retorno500vo.setHeader(header); continue;
/* 44 */         }  if (recordType == RecordType.BODY) {
/* 45 */           detalhe = (Detalhe)record;
/* 46 */           detalhe.setLinhaProcessada(++contador);
/* 47 */           retorno500vo.getListaDetalhe().add(detalhe); continue;
/* 48 */         }  if (recordType == RecordType.TRAILER) {
/* 49 */           trailler = (Trailler)record;
/* 50 */           trailler.setLinhaProcessada(++contador);
/* 51 */           retorno500vo.setTrailler(trailler);
/*    */         } 
/*    */       } 
/* 54 */     } catch (IOException e) {
/* 55 */       logger.error("Error reading file [" + file.getName() + "]" + e);
/*    */     } 
/* 57 */     return retorno500vo;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\volkswagen\VolkswagenRetorno500Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */