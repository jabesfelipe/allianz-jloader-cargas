/*    */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.ws;
/*    */ 
/*    */ import br.com.accesstage.loader.util.commom.ASCargasLayout;
/*    */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*    */ import javax.jws.Oneway;
/*    */ import javax.jws.WebMethod;
/*    */ import javax.jws.WebService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Component;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebService
/*    */ @Component
/*    */ public class CinemarkRetornoPedidoLayoutService
/*    */   extends SpringBeanAutowiringSupport
/*    */   implements ASCargasLayout
/*    */ {
/*    */   @Autowired
/*    */   private BaseDAO baseDAO;
/*    */   
/*    */   @WebMethod
/*    */   @Oneway
/*    */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/* 41 */     (new Object(this, fileName, fileDir, codArquivo, empid)).process();
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\ws\CinemarkRetornoPedidoLayoutService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */