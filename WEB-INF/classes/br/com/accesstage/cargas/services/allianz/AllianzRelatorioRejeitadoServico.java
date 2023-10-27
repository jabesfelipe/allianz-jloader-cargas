/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.allianz;
/*    */ 
/*    */ import br.com.accesstage.cargas.dao.RelatoriosDAO;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.relatorio.RelatorioNaoCadastrado;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.relatorio.RelatorioRejeitado;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class AllianzRelatorioRejeitadoServico
/*    */ {
/*    */   @Autowired
/*    */   private RelatoriosDAO relatoriosDAO;
/*    */   
/*    */   public List<RelatorioRejeitado> listarRelatorioRejeitados() {
/* 19 */     return this.relatoriosDAO.findRelatorioRejeitado();
/*    */   }
/*    */   
/*    */   public List<RelatorioNaoCadastrado> listarRelatorioNaoCadastrado() {
/* 23 */     return this.relatoriosDAO.findRelatorioNaoCadastrado();
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateStatusRejeitado(List<RelatorioRejeitado> listaRelatorioRejeitados) {
/* 28 */     for (RelatorioRejeitado relatorioRejeitado : listaRelatorioRejeitados) {
/* 29 */       this.relatoriosDAO.updateRelatorioRejeitado(relatorioRejeitado.getNroUnico());
/*    */     }
/*    */   }
/*    */   
/*    */   public void updateStatusNaoCadastrado(List<RelatorioNaoCadastrado> listarRelatorioNaoCadastrado) {
/* 34 */     for (RelatorioNaoCadastrado relatorioNaoCadastrado : listarRelatorioNaoCadastrado)
/* 35 */       this.relatoriosDAO.updateRelatorioNaoCadastrado(relatorioNaoCadastrado.getNroUnico()); 
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\allianz\AllianzRelatorioRejeitadoServico.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */