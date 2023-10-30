/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import br.com.trms.emissor.boleto.utils.Utils;
/*    */ import java.io.PrintStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import java.util.Properties;
/*    */ import java.util.Scanner;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvocarMetodo<T, U>
/*    */ {
/* 16 */   private static final Logger LOGGER = Logger.getLogger(br.com.trms.emissor.boleto.utils.InvocarMetodo.class.getName());
/*    */   
/*    */   T invoqueMetodo(String metodo, U dados, Class<T> classeRetorno) throws Exception {
/* 19 */     T retorno = null;
/*    */     
/* 21 */     Properties properties = Utils.loadProperties();
/* 22 */     String pathSms = properties.getProperty("path_sms_accesstage");
/* 23 */     LOGGER.info("Carrega o Properties com o Path de Envio SMS: " + pathSms);
/*    */     
/* 25 */     String input = "";
/*    */ 
/*    */ 
/*    */     
/* 29 */     URL url = new URL(pathSms + "envio");
/* 30 */     LOGGER.info("Monta URl de Envio path-hiker/envio: " + url);
/*    */     
/* 32 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/*    */     
/* 34 */     connection.setRequestMethod("POST");
/* 35 */     connection.setRequestProperty("Content-type", "application/json");
/*    */     
/* 37 */     connection.setDoOutput(true);
/* 38 */     PrintStream printStream = new PrintStream(connection.getOutputStream());
/* 39 */     printStream.println(input);
/* 40 */     connection.connect();
/*    */     
/*    */     try {
/* 43 */       LOGGER.info("----------------------------------------");
/* 44 */       LOGGER.info(connection.getResponseCode() + " " + connection.getResponseMessage());
/* 45 */       LOGGER.info("----------------------------------------");
/* 46 */     } catch (Exception e) {
/* 47 */       LOGGER.error(e.getMessage());
/*    */     } 
/*    */     
/* 50 */     String jsonDeResposta = (new Scanner(connection.getInputStream())).next();
/* 51 */     LOGGER.info("Resposta: " + jsonDeResposta);
/*    */     
/* 53 */     connection.disconnect();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     LOGGER.info("Serealização do OBjeto Class Retorno");
/* 59 */     LOGGER.info(retorno);
/*    */     
/* 61 */     return retorno;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\InvocarMetodo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */