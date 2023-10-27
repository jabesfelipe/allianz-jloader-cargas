/*    */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.allianz;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.AbstractReader;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.AllianzDebitoComplementarVO;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Detalhe;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Trailler;
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
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class AllianzDebitoComplementarReader
/*    */   extends AbstractReader<AllianzDebitoComplementarVO>
/*    */ {
/* 29 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.layouts.allianz.AllianzDebitoComplementarReader.class);
/*    */ 
/*    */   
/*    */   public AllianzDebitoComplementarVO readFiles(File file) {
/* 33 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/* 34 */     AllianzDebitoComplementarVO debitoComplementarVO = new AllianzDebitoComplementarVO();
/* 35 */     PositionalRecordParser parser = null;
/*    */     try {
/* 37 */       InputStream bis = new FileInputStream(file);
/* 38 */       List<String> lines = IOUtils.readLines(bis, "ISO-8859-1");
/* 39 */       for (int index = 0; index < lines.size(); index++) {
/* 40 */         String linha = lines.get(index);
/* 41 */         char codRegistro = linha.charAt(0);
/* 42 */         RecordDescriptor recordDescriptor = null;
/* 43 */         if (codRegistro == '1') {
/* 44 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/* 45 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 46 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/* 47 */           debitoComplementarVO.setHeader(header);
/* 48 */         } else if (codRegistro == '5') {
/* 49 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Detalhe.class);
/* 50 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 51 */           Detalhe detalhe = (Detalhe)parser.parseFromText(Detalhe.class, linha);
/* 52 */           debitoComplementarVO.getListDetalhe().add(detalhe);
/* 53 */         } else if (codRegistro == '9') {
/* 54 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/* 55 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 56 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/* 57 */           debitoComplementarVO.setTrailler(trailler);
/*    */         }
/*    */       
/*    */       } 
/* 61 */     } catch (FileNotFoundException e) {
/* 62 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 63 */     } catch (IOException e) {
/* 64 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*    */     } 
/*    */     
/* 67 */     return debitoComplementarVO;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\allianz\AllianzDebitoComplementarReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */