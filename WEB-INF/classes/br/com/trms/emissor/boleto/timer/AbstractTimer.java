/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.timer;
/*    */ 
/*    */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*    */ import br.com.accesstage.loader.util.dao.ConfigDAO;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class AbstractTimer
/*    */ {
/* 15 */   protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*    */   
/*    */   @Autowired
/*    */   protected BaseDAO baseDAO;
/*    */   
/*    */   @Autowired
/*    */   private ConfigDAO configDAO;
/*    */   private Map<String, String> config;
/*    */   
/*    */   public void load(String layout) {
/* 25 */     this.config = this.configDAO.mapConfigLayout(layout);
/*    */   }
/*    */   
/*    */   public Map<String, String> getConfig() {
/* 29 */     return this.config;
/*    */   }
/*    */   public void setConfig(Map<String, String> config) {
/* 32 */     this.config = config;
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\boleto\timer\AbstractTimer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */