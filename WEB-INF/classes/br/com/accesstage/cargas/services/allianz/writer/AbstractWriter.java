/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.allianz.writer;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Paths;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractWriter
/*    */ {
/*    */   public String formatadorEsquerda(String valor, String padding, int total) {
/* 13 */     StringBuilder strQuery = new StringBuilder();
/* 14 */     if (StringUtils.isEmpty(valor)) {
/* 15 */       for (int i = 0; i < total; i++) {
/* 16 */         strQuery.append(padding);
/*    */       }
/*    */     } else {
/* 19 */       for (int i = valor.trim().length(); i < total; i++) {
/* 20 */         strQuery.append(padding);
/*    */       }
/* 22 */       strQuery.append(valor);
/*    */     } 
/* 24 */     return strQuery.toString();
/*    */   }
/*    */   
/*    */   public String formatadorDireita(String valor, String padding, int total) {
/* 28 */     StringBuilder strQuery = new StringBuilder();
/* 29 */     if (StringUtils.isEmpty(valor)) {
/* 30 */       for (int i = 0; i < total; i++) {
/* 31 */         strQuery.append(padding);
/*    */       }
/*    */     } else {
/* 34 */       strQuery.append(valor);
/* 35 */       for (int i = valor.trim().length(); i < total; i++) {
/* 36 */         strQuery.append(padding);
/*    */       }
/*    */     } 
/* 39 */     return strQuery.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String moveResubmit(File arquivo, String diretorioResubmit) throws IOException {
/* 44 */     Files.move(Paths.get(arquivo.getAbsolutePath(), new String[0]), Paths.get(diretorioResubmit + arquivo.getName(), new String[0]), new java.nio.file.CopyOption[0]);
/* 45 */     return "created";
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\allianz\writer\AbstractWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */