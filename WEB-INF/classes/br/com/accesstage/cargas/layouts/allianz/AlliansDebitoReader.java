/*    */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.allianz;
/*    */ 
/*    */ import br.com.accesstage.cargas.layouts.AbstractReader;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.AllianzDebitoVO;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.DetalheE;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.DetalheF;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.Trailler;
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
/*    */ public class AlliansDebitoReader
/*    */   extends AbstractReader<AllianzDebitoVO>
/*    */ {
/* 30 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.layouts.allianz.AlliansDebitoReader.class);
/*    */ 
/*    */   
/*    */   public AllianzDebitoVO readFiles(File file) {
/* 34 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/* 35 */     AllianzDebitoVO debito = new AllianzDebitoVO();
/* 36 */     PositionalRecordParser parser = null;
/*    */     try {
/* 38 */       InputStream bis = new FileInputStream(file);
/* 39 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/* 40 */       for (int index = 0; index < lines.size(); index++) {
/* 41 */         String linha = lines.get(index);
/* 42 */         char codRegistro = linha.charAt(0);
/* 43 */         RecordDescriptor recordDescriptor = null;
/* 44 */         if (codRegistro == 'A') {
/* 45 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/* 46 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 47 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/* 48 */           debito.setHeader(header);
/* 49 */         } else if (codRegistro == 'E') {
/* 50 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(DetalheE.class);
/* 51 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 52 */           DetalheE detalheE = (DetalheE)parser.parseFromText(DetalheE.class, linha);
/* 53 */           debito.getListDetalheE().add(detalheE);
/* 54 */         } else if (codRegistro == 'F') {
/* 55 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(DetalheF.class);
/* 56 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 57 */           DetalheF detalheF = (DetalheF)parser.parseFromText(DetalheF.class, linha);
/* 58 */           debito.getListDetalheF().add(detalheF);
/* 59 */         } else if (codRegistro == 'Z') {
/* 60 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/* 61 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 62 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/* 63 */           debito.setTrailler(trailler);
/*    */         }
/*    */       
/*    */       } 
/* 67 */     } catch (FileNotFoundException e) {
/* 68 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 69 */     } catch (IOException e) {
/* 70 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*    */     } 
/* 72 */     return debito;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 76 */     br.com.accesstage.cargas.layouts.allianz.AlliansDebitoReader reader = new br.com.accesstage.cargas.layouts.allianz.AlliansDebitoReader();
/* 77 */     File file = new File("/data/jloader/allianz/debito/retorno/in/Retorno.txt");
/* 78 */     reader.readFiles(file);
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\allianz\AlliansDebitoReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */