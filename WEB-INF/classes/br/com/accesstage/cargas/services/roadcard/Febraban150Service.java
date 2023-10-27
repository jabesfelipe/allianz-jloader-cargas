/*     */ package WEB-INF.classes.br.com.accesstage.cargas.services.roadcard;
/*     */ 
/*     */ import br.com.accesstage.cargas.services.AbstractService;
/*     */ import br.com.accesstage.loader.util.commom.ffpojo.Constantes150;
/*     */ import br.com.accesstage.loader.util.constantes.carga.DateUtils;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.DetalheF;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.DetalheFV4;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.DetalheG;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout150.Febraban150VO;
/*     */ import java.io.File;
/*     */ import java.sql.SQLException;
/*     */ import org.apache.commons.collections4.CollectionUtils;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class Febraban150Service
/*     */   extends AbstractService<Febraban150VO>
/*     */   implements Constantes150
/*     */ {
/*  23 */   public String SEQ_DCC150_HEADER_RET = "SEQ_COD_FB150_HDR";
/*  24 */   public String SEQ_DCC_RET_TPOB = "COD_DCC150_RET_TPOB_SEQ";
/*  25 */   public String SEQ_DCC150_RET_ARRCODBARR = "SEQ_COD_FB150_DTHL_G";
/*  26 */   public String SEQ_DCC150_RET_TPOF = "SEQ_COD_FB150_DTHL_F";
/*  27 */   public String SEQ_DCC150_RET_TRAILLER = "SEQ_COD_FB150_HDR_TRLLR";
/*     */ 
/*     */   
/*  30 */   public String SQL_INSERT_DCC150_HEADER_RET = "INSERT INTO FB150_HDR (COD_FB150_HDR,COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA,COD_REGISTRO,COD_REM,COD_CONV,NME_EMP,COD_BANCO,NME_BCO,DTA_GERACAO,NRO_NSA,NRO_VERSAO_LAYOUT,ID_SERV,TXT_RESERVADO_FUTURO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/*     */ 
/*     */   
/*  34 */   public String SQL_INSERT_DCC150_RET_TPOB = "INSERT INTO DCC150_RET_TPOB(COD_DCC_RET_TPOB,COD_REGISTRO,IDEN_CLIENTE_EMP,AGENCIA_DEBITO,IDEN_CLIENTE_BANCO,DTA_OPCAO,RES_FUTURO,COD_MOVIMENTO,COD_DCC_HEADER_RET) VALUES (?,?,?,?,?,?,?,?,?)";
/*     */ 
/*     */ 
/*     */   
/*  38 */   public String SQL_INSERT_DCC150_RET_TPOF = "INSERT INTO FB150_DTHL_F (COD_FB150_DTHL_F,COD_FB150_HDR,COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA,COD_REGISTRO,ID_CLIENTE_EMP,AGE_DEB,ID_CLIENTE_BCO,DTA_VENC_DEB,VLR_ORIG_DEB,COD_RET,USO_EMP,TPO_IDENTIFICACAO,NRO_IDENTIFICACAO,TXT_RESERVADO_FUTURO,COD_MOVIMENTO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/*     */ 
/*     */   
/*  42 */   public String SQL_INSERT_DCC150_RET_ARRCODBARR = "INSERT INTO FB150_DTHL_G  (COD_FB150_DTHL_G,COD_FB150_HDR,COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA,COD_REGISTRO,ID_AGE_CC,DTA_PGTO,DTA_CRED,COD_BARR,VLR_RECEBIDO,VLR_TARIFA,NRO_NSR,COD_AGE_ARR,NRO_FRM_ARR,NRO_AUT_CXA,FRM_PGTO,TXT_RESERVADO_FUTURO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/*     */ 
/*     */   
/*  46 */   public String SQL_INSERT_DCC150_RET_TRAILLER = "INSERT INTO FB150_HDR_TRLLR (COD_FB150_HDR_TRLLR,COD_FB150_HDR,COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA,COD_REGISTRO,QTD_REG_ARQ,VLR_TT_REG_ARQ,TXT_RESERVADO_FUTURO) VALUES (?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/*  52 */     this.dataFileName = fileName;
/*  53 */     this.dataFilePath = fileDir;
/*  54 */     this.empid = empid;
/*  55 */     this.codArquivo = codArquivo;
/*  56 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/*  57 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
/*     */   public void process(Febraban150VO febraban150VO, int codigoArquivo, int empId) throws SQLException {
/*  64 */     Long codHeader = this.baseDAO.getSequenceID(this.SEQ_DCC150_HEADER_RET);
/*  65 */     String codMesRefCarga = DateUtils.codMesRefCarga();
/*     */     
/*  67 */     this.baseDAO.merge(this.SQL_INSERT_DCC150_HEADER_RET, new Object[] { codHeader, Integer.valueOf(this.codArquivo), Integer.valueOf(febraban150VO.getHeader().getLinhaProcessada()), Integer.valueOf(empId), codMesRefCarga, "A", febraban150VO.getHeader().getCodRemessa(), febraban150VO.getHeader().getCodConvenio(), febraban150VO.getHeader().getNmeEmpresa(), febraban150VO.getHeader().getNroBanco(), febraban150VO.getHeader().getNmeBanco(), febraban150VO.getHeader().getDtaGeracao(), febraban150VO.getHeader().getNroNSA(), febraban150VO.getHeader().getNroVersaoLayout(), febraban150VO.getHeader().getIdentServico(), febraban150VO.getHeader().getUsoFuturo() });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     if (CollectionUtils.isNotEmpty(febraban150VO.getListDetalheF())) {
/*  73 */       for (DetalheF detalheF : febraban150VO.getListDetalheF()) {
/*  74 */         Long codCorpoF = this.baseDAO.getSequenceID(this.SEQ_DCC150_RET_TPOF);
/*  75 */         this.baseDAO.merge(this.SQL_INSERT_DCC150_RET_TPOF, new Object[] { codCorpoF, codHeader, Integer.valueOf(this.codArquivo), Integer.valueOf(detalheF.getLinhaProcessada()), Integer.valueOf(empId), codMesRefCarga, "F", detalheF.getIdentClienteEmpresa(), detalheF.getAgenciaDebito(), detalheF.getIdentClienteBanco(), detalheF.getDtaVencimentoOuDebito(), detalheF.getVlorOriginalDebitado(), detalheF.getCodRetorno(), detalheF.getUsoEmpresa(), detalheF.getTpoIdentificacao(), detalheF.getIden(), detalheF.getUsoFuturo(), detalheF.getCodMovimento() });
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     if (CollectionUtils.isNotEmpty(febraban150VO.getListDetalheFV4())) {
/*  83 */       Long codCorpoF = this.baseDAO.getSequenceID(this.SEQ_DCC150_RET_TPOF);
/*  84 */       for (DetalheFV4 detalheFV4 : febraban150VO.getListDetalheFV4()) {
/*  85 */         this.baseDAO.merge(this.SQL_INSERT_DCC150_RET_TPOF, new Object[] { codCorpoF, codHeader, Integer.valueOf(this.codArquivo), Integer.valueOf(detalheFV4.getLinhaProcessada()), Integer.valueOf(empId), codMesRefCarga, "F", detalheFV4.getIdentClienteEmpresa(), detalheFV4.getAgenciaDebito(), detalheFV4.getIdentClienteBanco(), detalheFV4.getDtaVencimentoOuDebito(), detalheFV4.getVlorOriginalDebitado(), detalheFV4.getCodRetorno(), detalheFV4.getUsoEmpresa(), null, null, detalheFV4.getUsoFuturo(), detalheFV4.getCodMovimento() });
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (CollectionUtils.isNotEmpty(febraban150VO.getListDetalheG())) {
/*  93 */       Long codCorpoG = this.baseDAO.getSequenceID(this.SEQ_DCC150_RET_ARRCODBARR);
/*  94 */       for (DetalheG detalheG : febraban150VO.getListDetalheG()) {
/*  95 */         this.baseDAO.merge(this.SQL_INSERT_DCC150_RET_ARRCODBARR, new Object[] { codCorpoG, codHeader, Integer.valueOf(this.codArquivo), Integer.valueOf(detalheG.getLinhaProcessada()), Integer.valueOf(empId), codMesRefCarga, "G", detalheG.getIdentClienteEmpresa(), detalheG.getDtaPagamento(), detalheG.getDtaCredito(), detalheG.getInfoCodigoBarra(), detalheG.getValorRecebido(), detalheG.getValorTarifa(), detalheG.getNroSeqRegistro(), detalheG.getCodAgArrecadadora(), detalheG.getFormaArrecadacao(), detalheG.getNroAutenticacao(), detalheG.getFormaPagamento(), detalheG.getUsoFuturo() });
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     Long codTrailler = this.baseDAO.getSequenceID(this.SEQ_DCC150_RET_TRAILLER);
/* 103 */     this.baseDAO.merge(this.SQL_INSERT_DCC150_RET_TRAILLER, new Object[] { codTrailler, codHeader, Integer.valueOf(this.codArquivo), Integer.valueOf(febraban150VO.getTrailler().getLinhaProcessada()), Integer.valueOf(empId), codMesRefCarga, "Z", febraban150VO.getTrailler().getTotalRegistroArq(), febraban150VO.getTrailler().getVlorTotalRegistroArq(), febraban150VO.getTrailler().getUsoFuturo() });
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\roadcard\Febraban150Service.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */