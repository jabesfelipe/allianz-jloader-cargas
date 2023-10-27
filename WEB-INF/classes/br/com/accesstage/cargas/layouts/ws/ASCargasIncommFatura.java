/*    */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.ws;
/*    */ 
/*    */ import br.com.accesstage.loader.util.commom.ASCargasLayout;
/*    */ import java.io.Serializable;
/*    */ import javax.jws.Oneway;
/*    */ import javax.jws.WebMethod;
/*    */ import javax.jws.WebService;
/*    */ import org.springframework.web.context.support.SpringBeanAutowiringSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebService(targetNamespace = "http://layouts.cargas.accesstage.com.br/")
/*    */ public class ASCargasIncommFatura
/*    */   extends SpringBeanAutowiringSupport
/*    */   implements Serializable, ASCargasLayout
/*    */ {
/*    */   private static final long serialVersionUID = -4460644902234973663L;
/*    */   
/*    */   @WebMethod
/*    */   @Oneway
/*    */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/* 30 */     (new Object(this, fileName, fileDir, codArquivo, empid)).process();
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\ws\ASCargasIncommFatura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */