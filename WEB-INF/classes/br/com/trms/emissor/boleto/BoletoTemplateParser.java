/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto;
/*    */ 
/*    */ import br.com.trms.emissor.boleto.vo.Boleto;
/*    */ import java.io.StringWriter;
/*    */ import java.util.Properties;
/*    */ import org.apache.velocity.Template;
/*    */ import org.apache.velocity.VelocityContext;
/*    */ import org.apache.velocity.app.VelocityEngine;
/*    */ import org.apache.velocity.app.event.implement.IncludeRelativePath;
/*    */ import org.apache.velocity.context.Context;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class BoletoTemplateParser
/*    */ {
/*    */   public String generateHTML(Boleto boleto) throws Exception {
/* 22 */     VelocityEngine ve = new VelocityEngine();
/* 23 */     Properties props = new Properties();
/* 24 */     props.put("resource.loader", "class");
/* 25 */     props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
/* 26 */     props.put("file.resource.loader.path", "/data/jloader/allianz/templates/");
/* 27 */     props.put("eventhandler.include.class", IncludeRelativePath.class.getName());
/* 28 */     ve.init(props);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     VelocityContext context = new VelocityContext();
/* 34 */     context.put("boleto", boleto);
/*    */ 
/*    */     
/* 37 */     Template t = ve.getTemplate("boleto_santander.vm");
/*    */ 
/*    */     
/* 40 */     StringWriter writer = new StringWriter();
/* 41 */     t.merge((Context)context, writer);
/* 42 */     return writer.toString();
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\boleto\BoletoTemplateParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */