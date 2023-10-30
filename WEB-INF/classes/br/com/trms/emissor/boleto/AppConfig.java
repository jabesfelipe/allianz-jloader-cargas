/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto;
/*    */ 
/*    */ import br.com.trms.emissor.boleto.timer.EmissorBoletoAllianz;
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ import java.util.concurrent.Executors;
/*    */ import org.apache.velocity.app.VelocityEngine;
/*    */ import org.apache.velocity.app.event.implement.IncludeRelativePath;
/*    */ import org.apache.velocity.exception.VelocityException;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.ComponentScan;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.scheduling.TaskScheduler;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
/*    */ import org.springframework.scheduling.annotation.SchedulingConfigurer;
/*    */ import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
/*    */ import org.springframework.scheduling.config.ScheduledTaskRegistrar;
/*    */ import org.springframework.ui.velocity.VelocityEngineFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @EnableScheduling
/*    */ @Configuration
/*    */ @ComponentScan(basePackages = {"br.com.trms"})
/*    */ public class AppConfig
/*    */   implements SchedulingConfigurer
/*    */ {
/*    */   @Bean
/*    */   public VelocityEngine getVelocityEngine() throws VelocityException, IOException {
/* 35 */     VelocityEngineFactory factory = new VelocityEngineFactory();
/* 36 */     Properties props = new Properties();
/* 37 */     props.put("resource.loader", "class");
/* 38 */     props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
/* 39 */     props.setProperty("eventhandler.include.class", IncludeRelativePath.class.getName());
/* 40 */     factory.setVelocityProperties(props);
/* 41 */     return factory.createVelocityEngine();
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public EmissorBoletoAllianz myBean() {
/* 46 */     return new EmissorBoletoAllianz();
/*    */   }
/*    */ 
/*    */   
/*    */   public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
/* 51 */     taskRegistrar.setScheduler(taskExecutor());
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public TaskScheduler taskExecutor() {
/* 56 */     return (TaskScheduler)new ConcurrentTaskScheduler(
/* 57 */         Executors.newScheduledThreadPool(1));
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\boleto\AppConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */