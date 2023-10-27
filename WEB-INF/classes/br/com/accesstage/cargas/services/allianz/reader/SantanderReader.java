/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.allianz.reader;
/*    */ 
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.santander.SantanderAllianzVO;
/*    */ import com.github.ffpojo.container.HybridMetadataContainer;
/*    */ import com.github.ffpojo.parser.PositionalRecordParser;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SantanderReader
/*    */ {
/* 22 */   public String HDR_ARQ = "0";
/* 23 */   public String HDR_LOTE_ARQ = "1";
/* 24 */   public String TRLLR_LOTE_ARQ = "5";
/* 25 */   public String TRLLR_ARQ = "9";
/* 26 */   public String DETALHE = "3";
/* 27 */   public String DETALHEP = "P";
/* 28 */   public String DETALHEG = "G";
/*    */ 
/*    */   
/* 31 */   String path = "/Users/krebys/Desktop/Access/1210193@2906971@SANTBOWREM@201907225655968-201907225656029@201907221730.txt";
/*    */   
/* 33 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.allianz.reader.SantanderReader.class);
/*    */   
/*    */   public void reader(SantanderAllianzVO santanderAllianzVO, String outPath) {
/* 36 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/* 37 */     PositionalRecordParser parser = null;
/* 38 */     String tpoImpressao = null;
/* 39 */     File file = new File(outPath);
/*    */     try {
/* 41 */       InputStream bis = new FileInputStream(file);
/* 42 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/* 43 */       for (int index = 0; index < lines.size(); index++) {
/* 44 */         String linha = lines.get(index);
/* 45 */         String codRegistro = linha.substring(7, 8);
/* 46 */         Object object = null;
/*    */       } 
/* 48 */     } catch (FileNotFoundException e) {
/* 49 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 50 */     } catch (IOException e) {
/* 51 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\allianz\reader\SantanderReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */