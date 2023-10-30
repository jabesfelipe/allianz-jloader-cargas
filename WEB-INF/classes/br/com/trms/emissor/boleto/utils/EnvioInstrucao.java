/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class EnvioInstrucao
/*    */ {
/*    */   private List<Long> listIdsTitulo;
/*    */   private Integer tipoInstrucaoGeraRemessa;
/*    */   
/*    */   public List<Long> getListIdsTitulo() {
/* 12 */     return this.listIdsTitulo;
/*    */   }
/*    */   
/*    */   public void setListIdsTitulo(List<Long> listIdsTitulo) {
/* 16 */     this.listIdsTitulo = listIdsTitulo;
/*    */   }
/*    */   
/*    */   public Integer getTipoInstrucaoGeraRemessa() {
/* 20 */     return this.tipoInstrucaoGeraRemessa;
/*    */   }
/*    */   
/*    */   public void setTipoInstrucaoGeraRemessa(Integer tipoInstrucaoGeraRemessa) {
/* 24 */     this.tipoInstrucaoGeraRemessa = tipoInstrucaoGeraRemessa;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 29 */     int prime = 31;
/* 30 */     int result = 1;
/* 31 */     result = 31 * result + ((this.listIdsTitulo == null) ? 0 : this.listIdsTitulo.hashCode());
/* 32 */     result = 31 * result + ((this.tipoInstrucaoGeraRemessa == null) ? 0 : this.tipoInstrucaoGeraRemessa.hashCode());
/* 33 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 38 */     if (this == obj)
/* 39 */       return true; 
/* 40 */     if (obj == null)
/* 41 */       return false; 
/* 42 */     if (getClass() != obj.getClass())
/* 43 */       return false; 
/* 44 */     br.com.trms.emissor.boleto.utils.EnvioInstrucao other = (br.com.trms.emissor.boleto.utils.EnvioInstrucao)obj;
/* 45 */     if (this.listIdsTitulo == null) {
/* 46 */       if (other.listIdsTitulo != null)
/* 47 */         return false; 
/* 48 */     } else if (!this.listIdsTitulo.equals(other.listIdsTitulo)) {
/* 49 */       return false;
/* 50 */     }  if (this.tipoInstrucaoGeraRemessa == null) {
/* 51 */       if (other.tipoInstrucaoGeraRemessa != null)
/* 52 */         return false; 
/* 53 */     } else if (!this.tipoInstrucaoGeraRemessa.equals(other.tipoInstrucaoGeraRemessa)) {
/* 54 */       return false;
/* 55 */     }  return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return "EnvioInstrucao [listIdsTitulo=" + this.listIdsTitulo + ", tipoInstrucaoGeraRemessa=" + 
/* 61 */       this.tipoInstrucaoGeraRemessa + "]";
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\EnvioInstrucao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */