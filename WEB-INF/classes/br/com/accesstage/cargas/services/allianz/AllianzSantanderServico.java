/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.allianz;
/*    */ 
/*    */ import br.com.accesstage.cargas.dao.Report240DAO;
/*    */ import br.com.accesstage.cargas.services.allianz.writer.ComplementoTransformer;
/*    */ import br.com.accesstage.cargas.services.allianz.writer.SantanderTransformer;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.AllianzDebitoComplementarVO;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Detalhe;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Trailler;
/*    */ import java.io.IOException;
/*    */ import java.text.ParseException;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections4.CollectionUtils;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class AllianzSantanderServico
/*    */ {
/* 25 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.allianz.AllianzSantanderServico.class);
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private Report240DAO report240dao;
/*    */   
/*    */   @Autowired
/*    */   private SantanderTransformer santanderTransformer;
/*    */   
/*    */   @Autowired
/*    */   private ComplementoTransformer complementoTransformer;
/*    */ 
/*    */   
/*    */   public void execute(String outPath, String outPathComplemento, String outPathResubmit, String outPathComplResubmit) {
/* 39 */     AllianzDebitoComplementarVO allianzDebitoComplementarVO = null;
/* 40 */     HashMap<Integer, AllianzDebitoComplementarVO> mapCompl = new HashMap<>();
/*    */     
/* 42 */     List<Detalhe> listDetalhe = this.report240dao.findCobRegister();
/* 43 */     if (!CollectionUtils.isEmpty(listDetalhe)) {
/* 44 */       for (Detalhe detalhe : listDetalhe) {
/* 45 */         if (mapCompl.containsKey(Integer.valueOf(detalhe.getHeaderLote()))) {
/* 46 */           ((AllianzDebitoComplementarVO)mapCompl.get(Integer.valueOf(detalhe.getHeaderLote()))).getListDetalhe().add(detalhe); continue;
/*    */         } 
/* 48 */         allianzDebitoComplementarVO = new AllianzDebitoComplementarVO();
/* 49 */         Header header = this.report240dao.getComplHeader(detalhe.getCodArquivo(), detalhe.getHeaderLote());
/* 50 */         Trailler trailler = this.report240dao.getComplTrailler(detalhe.getCodArquivo(), detalhe.getHeaderLote());
/* 51 */         allianzDebitoComplementarVO.setHeader(header);
/* 52 */         allianzDebitoComplementarVO.setTrailler(trailler);
/* 53 */         allianzDebitoComplementarVO.getListDetalhe().add(detalhe);
/* 54 */         mapCompl.put(Integer.valueOf(detalhe.getHeaderLote()), allianzDebitoComplementarVO);
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 60 */       if (!mapCompl.isEmpty()) {
/* 61 */         int i = 0;
/* 62 */         for (Map.Entry<Integer, AllianzDebitoComplementarVO> entry : mapCompl.entrySet()) {
/* 63 */           i++;
/* 64 */           AllianzDebitoComplementarVO value = entry.getValue();
/* 65 */           this.santanderTransformer.transform(value, outPath + i, outPathResubmit);
/* 66 */           this.complementoTransformer.transform(value, outPathComplemento + i, outPathComplResubmit);
/* 67 */           for (Detalhe detalhe : value.getListDetalhe()) {
/* 68 */             this.report240dao.update(detalhe.getNroUnico());
/*    */           }
/*    */         } 
/*    */       } 
/* 72 */     } catch (IOException e) {
/* 73 */       logger.error("Error converting file:[" + outPath + "]" + e.getMessage());
/* 74 */     } catch (ParseException e) {
/* 75 */       logger.error("Error converting file:[" + outPath + "]" + e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(List<String> listaNossoNumero) {
/* 81 */     if (CollectionUtils.isNotEmpty(listaNossoNumero))
/* 82 */       for (String nossoNumero : listaNossoNumero)
/* 83 */         this.report240dao.checkNossoNumero(nossoNumero);  
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\allianz\AllianzSantanderServico.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */