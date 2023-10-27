/*     */ package WEB-INF.classes.br.com.accesstage.cargas.services.roadcard;
/*     */ 
/*     */ import br.com.accesstage.cargas.services.AbstractService;
/*     */ import br.com.accesstage.loader.util.commom.ffpojo.Constantes240;
/*     */ import br.com.accesstage.loader.util.constantes.carga.DateUtils;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.Febraban240VO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.HdrExtL033;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.HdrLteCobL045;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.Header;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.HeaderL043;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentY50;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoA;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoB;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoC;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoE;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoP;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoQ;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoR;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoS;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoSImpressao3;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoT;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoU;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.SegmentoY04;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.Trailler;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.TraillerCobL045;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.TraillerL043;
/*     */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout240.TrllExtL033;
/*     */ import com.github.ffpojo.container.HybridMetadataContainer;
/*     */ import com.github.ffpojo.metadata.RecordDescriptor;
/*     */ import com.github.ffpojo.metadata.positional.PositionalRecordDescriptor;
/*     */ import com.github.ffpojo.parser.PositionalRecordParser;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class Febraban240Service
/*     */   extends AbstractService<Febraban240VO>
/*     */   implements Constantes240
/*     */ {
/*  52 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.roadcard.Febraban240Service.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/*  57 */     this.dataFileName = fileName;
/*  58 */     this.dataFilePath = fileDir;
/*  59 */     this.empid = empid;
/*  60 */     this.codArquivo = codArquivo;
/*  61 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/*  62 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*     */   }
/*     */   
/*     */   @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
/*     */   public void process(File file, int codigoArquivo, int empId) throws SQLException {
/*  67 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/*  68 */     Febraban240VO febraban240vo = new Febraban240VO();
/*  69 */     PositionalRecordParser parser = null;
/*  70 */     int hdrL045 = 0;
/*  71 */     int hdrL043 = 0;
/*  72 */     int hdrL033 = 0;
/*  73 */     String tpoImpressao = null;
/*  74 */     Long codHdrTrllrArq = null;
/*  75 */     Long codHdrTrllrLteCobL045 = null;
/*  76 */     Long codHdrTrllrLteExtL033 = null;
/*  77 */     Long codDetailP = null;
/*  78 */     Long codDetailQ = null;
/*  79 */     Long codHeader = Long.valueOf(0L);
/*  80 */     Long codDetailA = Long.valueOf(0L);
/*  81 */     Long codDetailB = Long.valueOf(0L);
/*  82 */     Long codDetailC = Long.valueOf(0L);
/*     */     try {
/*  84 */       InputStream bis = new FileInputStream(file);
/*  85 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/*  86 */       String codMesRefCarga = DateUtils.codMesRefCarga();
/*  87 */       for (int index = 0; index < lines.size(); index++) {
/*  88 */         String linha = lines.get(index);
/*  89 */         String codRegistro = linha.substring(7, 8);
/*  90 */         String codOperacao = linha.substring(8, 9);
/*  91 */         String tpoServico = linha.substring(9, 11);
/*  92 */         String codSegmento = linha.substring(13, 14);
/*  93 */         String identRegOpcional = linha.substring(17, 19);
/*  94 */         tpoImpressao = linha.substring(17, 18);
/*  95 */         RecordDescriptor recordDescriptor = null;
/*  96 */         if ("0".equalsIgnoreCase(codRegistro)) {
/*  97 */           codHdrTrllrArq = this.baseDAO.getSequenceID("SEQ_COD_FB240_HDR_TRLLR_ARQ");
/*  98 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/*  99 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 100 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/* 101 */           header.setLinhaProcessada(index);
/* 102 */           febraban240vo.setHeader(header);
/* 103 */           this.baseDAO.merge("INSERT INTO FB240_HDR_TRLLR_ARQ (COD_FB240_HDR_TRLLR_ARQ, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO_HDR_ARQ, LTE_SERVICO_HDR_ARQ,TPO_REGISTRO_HDR_ARQ, DSC_USO_FEBRABAN1_HDR_ARQ, TPO_INSC_EMP, NRO_INSC_EMP, COD_CONVENIO, DSC_AGENCIA, DSC_AGENCIA_DV, DSC_CONTA, DSC_CONTA_DV, DSC_AGE_CTA_DV, NME_EMPRESA, NME_BANCO, DSC_USO_FEBRABAN2_HDR_ARQ, NRO_RMSS_RTRN,DTA_GERACAO_AR, HRA_GERACAO_ARQ, NRO_NSA, NR0_LAYOUT_ARQ, COD_DENSIDADE_GRAV_ARQ, DSC_USO_RESERVADO_BCO, DSC_USO_RESERVADO_EMP, DSC_USO_FEBRABAN3_HDR_ARQ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrArq, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, header.getCodBcoHdrArq(), header.getLteServicoHdrArq(), header.getTpoRegHdrArq(), header.getDscUsoFbrn1(), header.getTpoIncsEmp(), header.getNroInscEmp(), header.getCodConvenio(), header.getDscAgencia(), header.getDscAgenciaDV(), header.getDscConta(), header.getDscContaDV(), header.getDscAgCtaDV(), header.getNmeEmp(), header.getNmeBco(), header.getDscUsoFbrn2(), header.getNroRmssRtrn(), header.getDtaGeracaoArq(), header.getHraGeracaoArq(), header.getNroNsa(), header.getNroLytArq(), header.getCodDensidade(), header.getDscResercadoBco(), header.getDscResercadoEmp(), header.getDscUsoFbrn3() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 110 */         else if ("9".equalsIgnoreCase(codRegistro)) {
/* 111 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/* 112 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 113 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/* 114 */           trailler.setLinhaProcessada(index);
/* 115 */           febraban240vo.setTrailler(trailler);
/* 116 */           this.baseDAO.merge("UPDATE FB240_HDR_TRLLR_ARQ SET COD_BANCO_TRLLR_ARQ = ?, NRO_LTE_SERVICO_ARQ = ?, TPO_REGISTRO_TRLLR_ARQ = ?, DSC_USO_FEBRABAN1_TRLLR_ARQ = ?,QTD_LTE_ARQ = ?, QTD_REG_ARQ = ?, QTD_CONTAS_CONC = ?, DSC_USO_FEBRABAN2_TRLLR_ARQ = ?, NRO_LINHA_ARQ = ? WHERE COD_FB240_HDR_TRLLR_ARQ = ?", new Object[] { trailler.getCodBcoTrllrArq(), trailler.getNroLteServico(), trailler.getTpoRegTrllrArq(), trailler.getDscUsoFbrn1(), trailler.getQtdLteArq(), trailler.getQtdRegArq(), trailler.getQtdContaConcArq(), trailler.getDscUsoFbrn2(), Integer.valueOf(index), codHdrTrllrArq });
/*     */         
/*     */         }
/* 119 */         else if ("1".equalsIgnoreCase(codRegistro) && "01".equals(tpoServico)) {
/* 120 */           hdrL045++;
/* 121 */           codHdrTrllrLteCobL045 = this.baseDAO.getSequenceID("SEQ_COD_FB240_HDRTRLR_COB_L045");
/* 122 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HdrLteCobL045.class);
/* 123 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 124 */           HdrLteCobL045 cobL045 = (HdrLteCobL045)parser.parseFromText(HdrLteCobL045.class, linha);
/* 125 */           cobL045.setLinhaProcessada(index);
/* 126 */           febraban240vo.getListHdrLteCobL045().add(cobL045);
/* 127 */           this.baseDAO.merge("INSERT INTO FB240_HDR_TRLLR_COB_L045 (COD_FB240_HDR_TRLLR_COB_L045, COD_FB240_HDR_TRLLR_ARQ, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA,COD_BANCO_HDR_LTE, LTE_SERVICO_HDR_LTE, TPO_REGISTRO_HDR_LTE, COD_TPO_OP_LTE, COD_TPO_SERVICO, DSC_USO_FEBRABAN1_HDR_LTE, NR0_LAYOUT_ARQ, DSC_USO_FEBRABAN2_HDR_LTE, TPO_INSC_EMP, NRO_INSC_EMP, COD_CONVENIO, DSC_AGENCIA, DSC_AGENCIA_DV,DSC_CONTA, DSC_CONTA_DV, DSC_AGE_CTA_DV, NME_EMPRESA, DSC_MENSAGEM1_LTE, DSC_MENSAGEM2_LTE, NRO_RMSS_RTRN_LTE, DTA_GERACAO_RMSS_RTRN, DTA_CREDITO, DSC_USO_FEBRABAN3_HDR_LTE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteCobL045, codHdrTrllrArq, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, cobL045.getCodBcoHdrLte(), cobL045.getLteServicoHdrLte(), cobL045.getTpoRegHdrLte(), cobL045.getCodTpoOpLte(), cobL045.getCodTpoServico(), cobL045.getDscUsoFbrn1(), cobL045.getNroLyt(), cobL045.getDscUsoFbrn2(), cobL045.getTpoInsEmp(), cobL045.getNroInsEmp(), cobL045.getCodConvenio(), cobL045.getDscAgencia(), cobL045.getDscAgenciaDV(), cobL045.getDscConta(), cobL045.getDscContaDV(), cobL045.getDscAgeCta(), cobL045.getNmeEmp(), cobL045.getDscMsg1(), cobL045.getDscMsg2(), cobL045.getNrmRmssRtrn(), cobL045.getDtaGeracaoRmssRtrn(), cobL045.getDtaCredito(), cobL045.getDscUsoFbrn3() });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 133 */         else if ("1".equalsIgnoreCase(codRegistro) && "C".equals(codOperacao)) {
/* 134 */           hdrL043++;
/* 135 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HeaderL043.class);
/* 136 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 137 */           HeaderL043 headerL043 = (HeaderL043)parser.parseFromText(HeaderL043.class, linha);
/* 138 */           headerL043.setLinhaProcessada(index);
/* 139 */           febraban240vo.getListHeaderL043().add(headerL043);
/* 140 */           codHeader = this.baseDAO.getSequenceID("SEQ_COD_ROADCARD240_RET_HDR");
/* 141 */           this.baseDAO.merge("INSERT into FB240_HDR_TRLLR_PAG_L043(COD_FB240_HDR_TRLLR_PAG_L043,COD_ARQUIVO,NRO_LINHA,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_HDR,COD_BANCO_COMP_HDR,COD_LOTE_HDR,COD_TIPO_REG_HDR,DSC_TIPO_OP_HDR,COD_TIPO_SER_HDR,COD_FOR_LANC_HDR,NRO_LAYOUT_LOTE_HDR,DSC_RESERVADO_HDR,COD_TPO_INSC_EMP_HDR,NRO_INSC_EMP_HDR,COD_CONV_BANCO_HDR,COD_AG_MAN_CONTA_HDR,COD_DIG_VERIFICADOR_CONTA_HDR,NRO_CC_HDR,COD_DIG_CONTA_HDR,COD_DIG_AG_CC_HDR,DSC_NME_EMP_HDR,DSC_MENSAGEM_HDR,DSC_END_EMP_HDR,DSC_NUM_END_EMP_HDR,DSC_COMPLEMENTO_EMP_HDR,DSC_CIDADE_EMP_HDR,DSC_CEP_EMP_HDR,DSC_COMP_CEP_EMP_HDR,DSC_SGL_EST_EMP_HDR,DSC_RESERVADO1_HDR,DSC_OCORRENCIAS_HDR)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { codHeader, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, "1", headerL043.getCodBandoHdr(), headerL043.getCodLoteHdr(), headerL043.getTpoRegisHdr(), headerL043.getTpoOperacaoHdr(), headerL043.getCodTpoServicoHdr(), headerL043.getCodFormaLancHdr(), headerL043.getNroLayoutLteHdr(), headerL043.getDscReservado(), headerL043.getCodTpoInsEmpHdr(), headerL043.getNroInscEmpHdr(), headerL043.getCodConvbancoHdr(), headerL043.getCodAgMantContaHdr(), headerL043.getCodDigVeriContaHdr(), headerL043.getNroContaCorrenteHdr(), headerL043.getDigContaHdr(), headerL043.getDigAgCcHdr(), headerL043.getNmeEmpresaHdr(), headerL043.getDscMensagemHdr(), headerL043.getDscEndEmpresaHdr(), headerL043.getNumEndEmpHdr(), headerL043.getDscComplemnteEmpHdr(), headerL043.getDscCidadeEmpHdr(), headerL043.getDscCepEmpHdr(), headerL043.getDscCompCepEmpHdr(), headerL043.getDscSlgEstEmpHdr(), headerL043.getDscReservado1Hdr(), headerL043.getDscOcorrenciasHdr() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 148 */         else if ("5".equalsIgnoreCase(codRegistro) && hdrL043 > 0) {
/* 149 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TraillerL043.class);
/* 150 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 151 */           TraillerL043 traillerL043 = (TraillerL043)parser.parseFromText(TraillerL043.class, linha);
/* 152 */           traillerL043.setLinhaProcessada(index);
/* 153 */           febraban240vo.getListTraillerL043().add(traillerL043);
/* 154 */           this.baseDAO.merge("UPDATE FB240_HDR_TRLLR_PAG_L043 SET COD_REGISTRO_TRLLR = ?, COD_BANCO_COMP_TRLLR = ?, COD_LOTE_TRLLR = ?, COD_TIP_SER_TRLLR = ?, DSC_RESERVADO_TRLLR = ?,COD_REGISTRO_LOTE_TRLLR = ?, DSC_SOMA_VLR_TRLLR = ?, DSC_SOMA_QTD_MOEDA_TRLLR = ?, DSC_NUM_AVISO_DEB_TRLLR = ?, DSC_RERVADO1_TRLLR = ?, DSC_OCORRENCIAS_TRLLR=? WHERE COD_FB240_HDR_TRLLR_PAG_L043 = ?", new Object[] { "5", traillerL043.getCodBancoTrllr(), traillerL043.getCodLoteTrllr(), traillerL043.getCodTpoServTrllr(), traillerL043.getDscReservadoTrllr(), traillerL043.getCodResgistroLteTrllr(), traillerL043.getDscSomaVlrTrllr(), traillerL043.getDscSomaQtdMoedaTrllr(), traillerL043.getDscNumAvisoDebTrllr(), traillerL043.getDscReservado1Trllr(), traillerL043.getDscOcorrenciasTrllr(), codHeader });
/*     */         
/*     */         }
/* 157 */         else if ("5".equalsIgnoreCase(codRegistro) && hdrL045 > 0) {
/* 158 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TraillerCobL045.class);
/* 159 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 160 */           TraillerCobL045 traillerCobL045 = (TraillerCobL045)parser.parseFromText(TraillerCobL045.class, linha);
/* 161 */           traillerCobL045.setLinhaProcessada(index);
/* 162 */           febraban240vo.getListTraillerCobL045().add(traillerCobL045);
/* 163 */           this.baseDAO.merge("UPDATE FB240_HDR_TRLLR_COB_L045 SET COD_BANCO_TRLLR_LTE = ?, NRO_LTE_SERVICO_LTE = ?, TPO_REGISTRO_TRLLR_LTE = ?, DSC_USO_FEBRABAN1_TRLLR_LTE = ?,QTD_REG_LTE = ?, QTD_TIT_COBRANCA_SIMPLES = ?, VLR_TTL_TIT_CARTEIRAS_SIMPLES = ?, QTD_TIT_COBRANCA_VINC = ?,VLR_TTL_TIT_CARTEIRAS_VINC = ?,QTD_TIT_COBRANCA_CAUC = ?, VLR_TTL_TIT_CARTEIRAS_CAUC = ?, QTD_TIT_COBRANCA_DESC = ?, VLR_TTL_TIT_CARTEIRAS_DESC = ?, NRO_AVISO_LANCAMENTO = ?,DSC_USO_FEBRABAN2_TRLLR_LTE = ?, NRO_LINHA_ARQ = ? WHERE COD_FB240_HDR_TRLLR_COB_L045 = ?", new Object[] { traillerCobL045.getCodBcoTrllrLte(), traillerCobL045.getNroLteServico(), traillerCobL045.getTpoRegTrllrLte(), traillerCobL045.getDscUsoFbrn1(), traillerCobL045.getQtdLteLte(), traillerCobL045.getQtdTitCobSimples(), traillerCobL045.getVlrTtTitCobSimples(), traillerCobL045.getQtdTitCobVincs(), traillerCobL045.getVlrTtTitCobVincs(), traillerCobL045.getQtdTitCobCaucs(), traillerCobL045.getVlrTtTitCobCaucs(), traillerCobL045.getQtdTitCobDescs(), traillerCobL045.getVlrTtTitCobDescs(), traillerCobL045.getNroAvisoLanc(), traillerCobL045.getDscUsoFbrn2(), Integer.valueOf(index), codHdrTrllrLteCobL045 });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 168 */         else if ("1".equalsIgnoreCase(codRegistro) && "E".equals(codOperacao) && "04".equals(tpoServico)) {
/* 169 */           hdrL033++;
/* 170 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HdrExtL033.class);
/* 171 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 172 */           HdrExtL033 hdrExtL033 = (HdrExtL033)parser.parseFromText(HdrExtL033.class, linha);
/* 173 */           hdrExtL033.setLinhaProcessada(index);
/* 174 */           febraban240vo.getListHdrExtL033().add(hdrExtL033);
/* 175 */           this.baseDAO.merge("INSERT INTO FB240_HDR_TRLLR_EXT_L033 ( COD_FB240_HDR_TRLLR_EXT_L033, COD_FB240_HDR_TRLLR_ARQ, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO_COMPENSADO_HDR, LTE_SERVICO_HDR, TPO_REGISTRO_HDR,COD_TPO_OP, COD_TPO_SERVICO, DSC_FORMA_LANC, NR0_LAYOUT_ARQ, DSC_USO_FEBRABAN1_HDR_LTE, TPO_INSC_EMP_HDR_LTE, NRO_INSC_EMP_HDR_LTE, COD_CONVENIO_HDR_LTE, DSC_AGENCIA_HDR_LTE, DSC_AGENCIA_DV_HDR_LTE, DSC_CONTA_HDR_LTE, DSC_CONTA_DV_HDR_LTE, DSC_AGE_CTA_DV_HDR_LTE, NME_EMPRESA,DSC_USO_FEBRABAN2_HDR_LTE, DTA_SALDO_INICIAL, VLR_SALDO_INICIAL, DSC_SITUACAO_SALDO_INICIAL, DSC_STATUS_SALDO_INICIAL_HDR, COD_MOEDA, NRO_SEQ_HDR, DSC_USO_FEBRABAN3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteExtL033, codHdrTrllrArq, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, hdrExtL033.getCodBco(), hdrExtL033.getLteServico(), hdrExtL033.getTpoReg(), hdrExtL033.getCodTpoOp(), hdrExtL033.getCodTpoServ(), hdrExtL033.getDscFormaLan(), hdrExtL033.getNroLytArq(), hdrExtL033.getDscUsoFbrn1(), hdrExtL033.getTpoInscEmp(), hdrExtL033.getNroInscEmp(), hdrExtL033.getCodConvenio(), hdrExtL033.getDscAgencia(), hdrExtL033.getDscAgenciaDV(), hdrExtL033.getDscConta(), hdrExtL033.getDscContaDV(), hdrExtL033.getDscAgCtaDV(), hdrExtL033.getNmeEmp(), hdrExtL033.getDscUsoFbrn2(), hdrExtL033.getDtaSldInicial(), hdrExtL033.getVlrSldIncial(), hdrExtL033.getDscSituacaoSldInicial(), hdrExtL033.getDscStatusSldInicial(), hdrExtL033.getCodMoedaStr(), hdrExtL033.getNroSeqHdr(), hdrExtL033.getDscUsoFbrn3() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 182 */         else if ("5".equalsIgnoreCase(codRegistro) && hdrL033 > 0) {
/* 183 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TrllExtL033.class);
/* 184 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 185 */           TrllExtL033 trllExtL033 = (TrllExtL033)parser.parseFromText(TrllExtL033.class, linha);
/* 186 */           trllExtL033.setLinhaProcessada(index);
/* 187 */           febraban240vo.getListTrllExtL033().add(trllExtL033);
/* 188 */           this.baseDAO.merge("UPDATE FB240_HDR_TRLLR_EXT_L033 SET COD_BANCO_COMPENSADO_TRLLR_LTE = ?, LTE_SERVICO_TRLLR_LTE = ?, TPO_REGISTRO_TRLLR_LTE = ?, DSC_USO_FEBRABAN1_TRLLR_LTE = ?, TPO_INSC_EMP_TRLLR_LTE = ?, NRO_INSC_EMP_TRLLR_LTE = ?,COD_CONVENIO_TRLLR_LTE = ?, DSC_AGENCIA_TRLLR_LTE = ?, DSC_AGENCIA_DV_TRLLR_LTE = ?, DSC_CONTA_TRLLR_LTE = ?, DSC_CONTA_DV_TRLLR_LTE = ?, DSC_AGE_CTA_DV_TRLLR_LTE = ?, DSC_USO_FEBRABAN2_TRLLR_LTE = ?, VLR_SALDO_BLOQ_ACIMA_24H = ?, VLR_LIMITE_CONTA = ?, VLR_SALDO_BLOQ_ATE_24H = ?,DTA_SALDO_FINAL = ?, VLR_SALDO_FINAL = ?, DSC_SITUACAO_SALDO_FINAL_TRLLR = ?, DSC_STATUS_SALDO_INICIAL_TRLLR = ?, QTD_REG_TRLLR_LTE = ?, VLR_SOMATORIA_DEB = ?, VLR_SOMATORIA_CRED = ?, DSC_USO_FEBRABAN3_TRLLR_LTE = ? WHERE COD_FB240_HDR_TRLLR_EXT_L033 = ?", new Object[] { trllExtL033.getCodBco(), trllExtL033.getLteServico(), trllExtL033.getTpoReg(), trllExtL033.getDscUsoFbrn1(), trllExtL033.getTpoInscEmp(), trllExtL033.getNroInscEmp(), trllExtL033.getCodConvenio(), trllExtL033.getDscAgencia(), trllExtL033.getDscAgenciaDV(), trllExtL033.getDscConta(), trllExtL033.getDscContaDV(), trllExtL033.getDscAgCtaDV(), trllExtL033.getDscUsoFbrn2(), trllExtL033.getVlrSaldoBloqAcima24(), trllExtL033.getVlrLimite(), trllExtL033.getVlrSaldoBloqAte24(), trllExtL033.getDtaSldFinal(), trllExtL033.getVlrSaldoFinal(), trllExtL033.getDscSituacaoSldFinal(), trllExtL033.getDscStatusSldFinal(), trllExtL033.getQtdReg(), trllExtL033.getVlrSomaDeb(), trllExtL033.getVlrSomaCred(), trllExtL033.getDscUsoFbrn3(), codHdrTrllrLteExtL033 });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 194 */         else if ("3".equalsIgnoreCase(codRegistro) && "U".equals(codSegmento)) {
/* 195 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoU.class);
/* 196 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 197 */           SegmentoU segmentoU = (SegmentoU)parser.parseFromText(SegmentoU.class, linha);
/* 198 */           segmentoU.setLinhaProcessada(index);
/* 199 */           febraban240vo.getListSegmentoU().add(segmentoU);
/* 200 */           this.baseDAO.merge("INSERT INTO FB240_DTLH_U (COD_FB240_DTLH_U, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO,TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1, COD_MOV_RTRN, VLR_JUROS_MULTA, VLR_DSC_CONCEDIDO, VLR_ABATIMENTO_CONCEDIDO, VLR_IOF, VLR_PAGO_SACADO, VLR_LIQ_CREDITADO, VLR_OUTRAS_DESP, VLR_OUTROS_CRED,DTA_OCORRENCIA1, DTA_EFETIVA_CRED, COD_OCORRENCIA, DTA_OCORRENCIA2, VLR_OCORRENCIA, DSC_OCORRENCIA, COD_BANCO_COMPENSADO, NRO_BCO_NOSSO, DSC_USO_FEBRABAN2) VALUES (SEQ_COD_FB240_DTLH_U.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoU.getCodBco(), segmentoU.getLteServico(), segmentoU.getTpoReg(), segmentoU.getNroSeqLte(), segmentoU.getCodSegRegDtlh(), segmentoU.getDscUsoFbrn1(), segmentoU.getCodMovRtrn(), segmentoU.getVlrJurosMulta(), segmentoU.getVlrDscConc(), segmentoU.getVlrAbatConc(), segmentoU.getVlrIof(), segmentoU.getVlrPgtSacado(), segmentoU.getVlrLiqCred(), segmentoU.getVlrOutrasDesp(), segmentoU.getVlrOutrasCred(), segmentoU.getDtaOcorrencia1(), segmentoU.getDtaEfetCred(), segmentoU.getCodOcorrencia(), segmentoU.getDtaOcorrencia2(), segmentoU.getVlrOcorrencia(), segmentoU.getDscOCorrencia(), segmentoU.getCodBcoComp(), segmentoU.getNroBcoNosso(), segmentoU.getDscUsoFbrn2() });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 205 */         else if ("3".equalsIgnoreCase(codRegistro) && "T".equals(codSegmento)) {
/* 206 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoT.class);
/* 207 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 208 */           SegmentoT segmentoT = (SegmentoT)parser.parseFromText(SegmentoT.class, linha);
/* 209 */           segmentoT.setLinhaProcessada(index);
/* 210 */           febraban240vo.getListSegmentoT().add(segmentoT);
/* 211 */           this.baseDAO.merge("INSERT INTO FB240_DTLH_T (COD_FB240_DTLH_T, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO,TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1, COD_MOV_RTRN, DSC_AGENCIA, DSC_AGENCIA_DV, DSC_CONTA, DSC_CONTA_DV, DSC_AGE_CTA_DV, NRO_IDENT_TIT_BCO, COD_CARTEIRA, NRO_DOC_COBRANCA, DTA_VENC_TIT, VLR_NML_TIT,COD_BANCO_COMPENSADO, DSC_AGENCIA_COB_REC, DSC_AGENCIA_COB_REC_DV, DSC_TIT_EMP, COD_MOEDA, TPO_INSC_EMP, NRO_INSC_EMP, NME_EMPRESA, NRO_CONTRATO_OP_CRED, VLR_TRF_CUSTAS, DSC_REJEICAO_TRF, DSC_USO_FEBRABAN2) VALUES(SEQ_COD_FB240_DTLH_T.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoT.getCodBco(), segmentoT.getLteServico(), segmentoT.getTpoReg(), segmentoT.getNroSeqLte(), segmentoT.getCodSegRegDtlh(), segmentoT.getDscUsoFbrn1(), segmentoT.getCodMovRtrn(), segmentoT.getDscAgencia(), segmentoT.getDscAgenciaDV(), segmentoT.getDscConta(), segmentoT.getDscContaDV(), segmentoT.getDscAgeCta(), segmentoT.getNroIdentTitBco(), segmentoT.getCodCarteira(), segmentoT.getNroDocCob(), segmentoT.getDtaVencTit(), segmentoT.getVlrNmlTit(), segmentoT.getCodBcoComp(), segmentoT.getDscAgenciaCobRec(), segmentoT.getDscAgenciaCobRecDV(), segmentoT.getDscTitEmp(), segmentoT.getCodMoeda(), segmentoT.getTpoInscEmp(), segmentoT.getNroInscEmp(), segmentoT.getNmeEmp(), segmentoT.getNroContratoOpCred(), segmentoT.getVlrTrfCustas(), segmentoT.getDscRejeicaoTrf(), segmentoT.getDscUsoFbrn2() });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 217 */         else if ("3".equalsIgnoreCase(codRegistro) && "Y".equals(codSegmento) && "50".equals(identRegOpcional)) {
/* 218 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentY50.class);
/* 219 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 220 */           SegmentY50 segmentY50 = (SegmentY50)parser.parseFromText(SegmentY50.class, linha);
/* 221 */           segmentY50.setLinhaProcessada(index);
/* 222 */           febraban240vo.getListSegmentY50().add(segmentY50);
/* 223 */           this.baseDAO.merge("INSERT INTO FB240_DTLH_Y50 (COD_FB240_DTLH_Y50, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO,TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1,         COD_MOV_RMSS, COD_REG_OPE, DSC_AGENCIA, DSC_AGENCIA_DV, DSC_CONTA, DSC_CONTA_DV, DSC_AGE_CTA_DV, NRO_IDENT_TIT_BCO, COD_CALC_RATEIO_BENEF, COD_TPO_VLR_INFO,   VLR_PRCTL,COD_BANCO_CRED_BENEF, DSC_AGENCIA_CRED_BENEF, COD_DIG_VERIF_CRED_BENEF, DSC_CONTA_CRED_BENEF, DSC_CONTA_DV_CRED_BENEF, DSC_AGE_CTA_DV_BENEF, NME_BENEF, DSC_PARCELA_RATEIO, QTD_CRED_BENEF, DTA_CRED_BENEF, COD_REJEICOES, DSC_USO_FEBRABAN2) VALUES(SEQ_COD_FB240_DTLH_Y50.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentY50.getCodBco(), segmentY50.getLteServico(), segmentY50.getTpoReg(), segmentY50.getNroSeqLte(), segmentY50.getCodSegRegDtlh(), segmentY50.getDscUsoFbrn1(), segmentY50.getCodMovRmss(), segmentY50.getCodRegOpe(), segmentY50.getDscAgencia(), segmentY50.getDscAgenciaDV(), segmentY50.getDscConta(), segmentY50.getDscContaDV(), segmentY50.getDscAgeCta(), segmentY50.getNroIdentTitBco(), segmentY50.getCodCalcRateioBenef(), segmentY50.getCodTpoVlrInfo(), segmentY50.getVlrPrctl(), segmentY50.getCodBcoCreBenef(), segmentY50.getDscAgeCredBenef(), segmentY50.getCodDigVerifCredBenef(), segmentY50.getDscContaCredBenef(), segmentY50.getDscContaCredBenefDV(), segmentY50.getDscAgeCtaDVBenef(), segmentY50.getNmeBenef(), segmentY50.getDscParcelaRateio(), segmentY50.getQtdCredBenef(), segmentY50.getDtaCredBenef(), segmentY50.getCodRejeicoes(), segmentY50.getDscUsoFbrn2() });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 229 */         else if ("3".equalsIgnoreCase(codRegistro) && "Y".equals(codSegmento) && "03".equals(identRegOpcional)) {
/* 230 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoY04.class);
/* 231 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 232 */           SegmentoY04 segmentoY04 = (SegmentoY04)parser.parseFromText(SegmentoY04.class, linha);
/* 233 */           segmentoY04.setLinhaProcessada(index);
/* 234 */           febraban240vo.getListSegmentoY04().add(segmentoY04);
/* 235 */           this.baseDAO.merge("INSERT INTO FB240_DTLH_Y04 (COD_FB240_DTLH_Y04, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1,COD_MOVIMENTO, COD_REGISTRO_OPCIONAL, DSC_EMAIL, COD_DDD, NRO_CELULAR_SMS, DSC_USO_FEBRABAN2, COD_FB240_DTLH_Q) VALUES (SEQ_COD_FB240_DTLH_Y04.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoY04.getCodBco(), segmentoY04.getLteServico(), segmentoY04.getTpoReg(), segmentoY04.getNroSeq(), segmentoY04.getCodSegReg(), segmentoY04.getDscUsoFbrn1(), segmentoY04.getCodMov(), segmentoY04.getCodRegOpe(), segmentoY04.getDscEmail(), segmentoY04.getCodDdd(), segmentoY04.getNroCelular(), segmentoY04.getDscUsoFbrn2(), codDetailQ });
/*     */         
/*     */         }
/* 238 */         else if ("3".equalsIgnoreCase(codRegistro) && "P".equals(codSegmento)) {
/* 239 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoP.class);
/* 240 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 241 */           SegmentoP segmentoP = (SegmentoP)parser.parseFromText(SegmentoP.class, linha);
/* 242 */           segmentoP.setLinhaProcessada(index);
/* 243 */           febraban240vo.getListSegmentoP().add(segmentoP);
/* 244 */           codDetailP = this.baseDAO.getSequenceID("SEQ_COD_FB240_DTLH_P");
/* 245 */           this.baseDAO.merge("INSERT INTO FB240_DTLH_P (COD_FB240_DTLH_P, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_LTE,COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1, COD_MOV_RMSS, DSC_AGENCIA, DSC_AGENCIA_DV, DSC_CONTA, DSC_CONTA_DV, DSC_AGE_CTA_DV, NRO_IDENT_TIT_BCO, COD_CARTEIRA, DSC_FORMA_TIT_BCO, DSC_TPO_DOC, COD_EMISSAO_BLOQUETO, DSC_DISTRIBUICAO, NRO_DOC_COBRANCA,DTA_VENC_TIT, VLR_NML_TIT, DSC_AGE_COBRANCA, DSC_AGE_COBRANCA_DV, COD_ESPECIE_TIT, DSC_IDENTIF_TIT, DTA_EMISSAO_TIT, COD_JUROS_MORA, DTA_JUROS_MORA, VLR_JUROS_MORA, COD_DESCONTO1, DTA_DESCONTO1, VLR_PRCTL_CONCEDIDO, VLR_IOF_RECOLHIDO, VLR_ABATIMENTO,DSC_IDENTIF_TIT_EMP, COD_PROTESTO, NRO_DIAS_PROTESTO, COD_BAIXA_DEV, NRO_DIAS_BAIXA_DEV, COD_MOEDA, NRO_CONTRATO_OP_CRED, DSC_USO_LIVRE1) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codDetailP, codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoP.getCodBco(), segmentoP.getLteServico(), segmentoP.getTpoReg(), segmentoP.getNroSeqLte(), segmentoP.getCodSegRegDtlh(), segmentoP.getDscUsoFbrn1(), segmentoP.getCodMovRmss(), segmentoP.getDscAge(), segmentoP.getDscAgeDV(), segmentoP.getDscConta(), segmentoP.getDscContaDV(), segmentoP.getDscAgeCtaDV(), segmentoP.getNroIdentTitBco(), segmentoP.getCodCarteira(), segmentoP.getDscTitBco(), segmentoP.getDscTpoDoc(), segmentoP.getCodEmissaoBloq(), segmentoP.getDscDist(), segmentoP.getNroDocCob(), segmentoP.getDtaVencTit(), segmentoP.getVlrNmlTit(), segmentoP.getDscAgeCob(), segmentoP.getDscAgeCobDV(), segmentoP.getCodEspTit(), segmentoP.getDscIdentTit(), segmentoP.getDtaEmissaoTit(), segmentoP.getCodJuros(), segmentoP.getDtaJuros(), segmentoP.getVlrJuros(), segmentoP.getCodDesc1(), segmentoP.getDtaDesc1(), segmentoP.getVlrPrctConc(), segmentoP.getVlrIofRecolhido(), segmentoP.getVlrAbat(), segmentoP.getDscIdentTitEmp(), segmentoP.getCodProtesto(), segmentoP.getNroDiasProt(), segmentoP.getCodBaixa(), segmentoP.getNroDiasBaixa(), segmentoP.getCodMoeda(), segmentoP.getNroContratoOpCred(), segmentoP.getDscUsoLivre1() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 253 */         else if ("3".equalsIgnoreCase(codRegistro) && "Q".equals(codSegmento)) {
/* 254 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoQ.class);
/* 255 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 256 */           SegmentoQ segmentoQ = (SegmentoQ)parser.parseFromText(SegmentoQ.class, linha);
/* 257 */           segmentoQ.setLinhaProcessada(index);
/* 258 */           febraban240vo.getListSegmentoQ().add(segmentoQ);
/* 259 */           codDetailQ = this.baseDAO.getSequenceID("SEQ_COD_FB240_DTLH_Q");
/* 260 */           this.baseDAO.merge("INSERT INTO FB240_DTLH_Q (COD_FB240_DTLH_Q, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH,DSC_USO_FEBRABAN1, COD_MOV_RMSS, TPO_INSC1, NRO_INSC1, NME_EMPRESA, DSC_ENDERECO, DSC_BAIRRO, NRO_CEP, NRO_CEP_SUFIXO, NME_CIDADE, NME_FEDERACAO, TPO_INSC2, NRO_INSC2, NME_SACADOR, COD_BANCO_COMPENSADO, NRO_BCO_NOSSO, DSC_USO_FEBRABAN2, COD_FB240_DTLH_P)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codDetailQ, codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoQ.getCodBco(), segmentoQ.getLteServico(), segmentoQ.getTpoReg(), segmentoQ.getNroSeqLte(), segmentoQ.getCodSegRegDtlh(), segmentoQ.getDscUsoFbrn1(), segmentoQ.getCodMovRmss(), segmentoQ.getTpoInsc(), segmentoQ.getNroInsc(), segmentoQ.getNome(), segmentoQ.getEndereco(), segmentoQ.getBairro(), segmentoQ.getNroCep(), segmentoQ.getNroCepSufixo(), segmentoQ.getNmeCidade(), segmentoQ.getUf(), segmentoQ.getTpoInsc2(), segmentoQ.getNroInsc2(), segmentoQ.getNmeAvalista(), segmentoQ.getCodCompensacao(), segmentoQ.getNroNosso(), segmentoQ.getDscUsoFbrn2(), codDetailP });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 265 */         else if ("3".equalsIgnoreCase(codRegistro) && "R".equals(codSegmento)) {
/* 266 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoR.class);
/* 267 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 268 */           SegmentoR segmentoR = (SegmentoR)parser.parseFromText(SegmentoR.class, linha);
/* 269 */           segmentoR.setLinhaProcessada(index);
/* 270 */           febraban240vo.getListSegmentoR().add(segmentoR);
/* 271 */           this.baseDAO.merge("INSERT INTO FB240_DTLH_R (COD_FB240_DTLH_R, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1, COD_MOV_RMSS, COD_DESCONTO2, DTA_DESCONTO2, VLR_PRCTL_CONCEDIDO2, COD_DESCONTO3, DTA_DESCONTO3, VLR_PRCTL_CONCEDIDO3, COD_MULTA, DTA_MULTA, VLR_PRCTL_APLICADO, DSC_INFO_SACADO, DSC_MENSAGEM3, DSC_MENSAGEM4, DSC_USO_FEBRABAN2,COD_OCORRENCIA_SACADO, COD_BANCO_CONTA_DEBITO, DSC_AGENCIA_DEBITO, DSC_AGENCIA_DV, DSC_CONTA_DEBITO, DSC_CONTA_DV, DSC_CONTA_AGENCIA_DV, COD_DEBITO_AUTOMATICO, DSC_USO_FEBRABAN3, COD_FB240_DTLH_P) VALUES (SEQ_COD_FB240_DTLH_R.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoR.getCodBco(), segmentoR.getLteServico(), segmentoR.getTpoReg(), segmentoR.getNroSeq(), segmentoR.getCodSegReg(), segmentoR.getDscUsoFbrn1(), segmentoR.getCodMovRmss(), segmentoR.getCodDesconto2(), segmentoR.getDtaDesconto2(), segmentoR.getVlrPrctlConcedido2(), segmentoR.getCodDesconto3(), segmentoR.getDtaDesconto3(), segmentoR.getVlrPrctlConcedido3(), segmentoR.getCodMulta(), segmentoR.getDtaMulta(), segmentoR.getVlrPrctlAplicado(), segmentoR.getDscInfoSacado(), segmentoR.getDscMensagem3(), segmentoR.getDscMensagem4(), segmentoR.getDscUsoFebraban2(), segmentoR.getCodOcorrenciaSacado(), segmentoR.getCodBancoContaDebito(), segmentoR.getDscAgenciaDebito(), segmentoR.getDscAgenciaDv(), segmentoR.getDscContaDebito(), segmentoR.getDscContaDv(), segmentoR.getDscContaAgenciaDv(), segmentoR.getCodDebitoAutomatico(), segmentoR.getDscUsoFebraban3(), codDetailP });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 279 */         else if ("3".equalsIgnoreCase(codRegistro) && "S".equals(codSegmento)) {
/* 280 */           if (tpoImpressao.equals("1") || tpoImpressao.equals("2")) {
/* 281 */             recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoS.class);
/* 282 */             parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 283 */             SegmentoS segmentoS = (SegmentoS)parser.parseFromText(SegmentoS.class, linha);
/* 284 */             segmentoS.setLinhaProcessada(index);
/* 285 */             febraban240vo.getListSegmentoS().add(segmentoS);
/* 286 */             this.baseDAO.merge("INSERT INTO FB240_DTLH_S (COD_FB240_DTLH_S, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1, COD_MOV_RMSS, COD_TPO_IMPRESSAO, NRO_LINHA_IMPRESSA, DSC_MENSAGEM1, TPO_CARACTER_IMPRESSO, DSC_USO_FEBRABAN2, COD_FB240_DTLH_P) VALUES (SEQ_COD_FB240_DTLH_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoS.getCodBco(), segmentoS.getLteServico(), segmentoS.getTpoReg(), segmentoS.getNroSeq(), segmentoS.getCodSegReg(), segmentoS.getDscUsoFbrn1(), segmentoS.getCodMovRmss(), tpoImpressao, segmentoS.getNroLinhaImpressa(), segmentoS.getDscMensagem1Str(), segmentoS.getTpoCaracterImpresso(), segmentoS.getDscUsoFebraban2(), codDetailP });
/*     */           } 
/*     */ 
/*     */           
/* 290 */           if (tpoImpressao.equalsIgnoreCase("3")) {
/* 291 */             recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoSImpressao3.class);
/* 292 */             parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 293 */             SegmentoSImpressao3 segmentoSImpressao3 = (SegmentoSImpressao3)parser.parseFromText(SegmentoSImpressao3.class, linha);
/* 294 */             segmentoSImpressao3.setLinhaProcessada(index);
/* 295 */             febraban240vo.getListSegmentSImpressao3().add(segmentoSImpressao3);
/* 296 */             this.baseDAO.merge("INSERT INTO FB240_DTLH_S (COD_FB240_DTLH_S, COD_FB240_HDR_TRLLR_COB_L045, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1,COD_MOV_RMSS, COD_TPO_IMPRESSAO, DSC_INFORMACAO5, DSC_INFORMACAO6, DSC_INFORMACAO7, DSC_INFORMACAO8, DSC_INFORMACAO9, DSC_USO_FEBRABAN3, COD_FB240_DTLH_P) VALUES (SEQ_COD_FB240_DTLH_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteCobL045, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoSImpressao3.getCodBco(), segmentoSImpressao3.getLteServico(), segmentoSImpressao3.getTpoReg(), segmentoSImpressao3.getNroSeq(), segmentoSImpressao3.getCodSegReg(), segmentoSImpressao3.getDscUsoFbrn1(), segmentoSImpressao3.getCodMovRmss(), tpoImpressao, segmentoSImpressao3.getDscInformacao5(), segmentoSImpressao3.getDscInformacao6(), segmentoSImpressao3.getDscInformacao7(), segmentoSImpressao3.getDscInformacao8(), segmentoSImpressao3.getDscInformacao9(), segmentoSImpressao3.getDscUsoFebraban3(), codDetailP });
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 301 */         else if ("3".equalsIgnoreCase(codRegistro) && "E".equals(codSegmento)) {
/* 302 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoE.class);
/* 303 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 304 */           SegmentoE segmentoE = (SegmentoE)parser.parseFromText(SegmentoE.class, linha);
/* 305 */           segmentoE.setLinhaProcessada(index);
/* 306 */           febraban240vo.getListSegmentoE().add(segmentoE);
/* 307 */           this.baseDAO.merge("INSERT INTO FB240_DTLH_E (COD_FB240_DTLH_E, COD_FB240_HDR_TRLLR_EXT_L033, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_LTE, COD_SEG_REG_DTLH, DSC_USO_FEBRABAN1, TPO_INSC_EMP, NRO_INSC_EMP, COD_CONVENIO, DSC_AGENCIA,DSC_AGENCIA_DV, DSC_CONTA, DSC_CONTA_DV, DSC_AGE_CTA_DV, NME_EMPRESA, DSC_USO_FEBRABAN2, DSC_NATUREZA_LANCAMENTO, TPO_COMPLEMENTO_LANCAMENTO, DSC_COMPLEMENTO_LANCAMENTO, DSC_ISENCAO_CPMF, DTA_CONTABIL, DTA_LANCAMENTO, VLR_LANCAMENTO, TPO_LANCAMENTO, DSC_CATEGORIA_LANCAMENTO, COD_HIST_BANCO, DSC_HIST_BANCO, NRO_DOCUMENTO) VALUES (SEQ_COD_FB240_DTLH_E.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHdrTrllrLteExtL033, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, segmentoE.getCodBco(), segmentoE.getLteServico(), segmentoE.getTpoReg(), segmentoE.getNroSeq(), segmentoE.getCodSegReg(), segmentoE.getDscUsoFbrn1(), segmentoE.getTpoInscEmp(), segmentoE.getNroInscEmp(), segmentoE.getCodConvenio(), segmentoE.getDscAgencia(), segmentoE.getDscAgenciaDV(), segmentoE.getDscConta(), segmentoE.getDscContaDV(), segmentoE.getDscAgCtaDV(), segmentoE.getNmeEmp(), segmentoE.getDscUsoFbrn2(), segmentoE.getDscNatureza(), segmentoE.getTpoComplLan(), segmentoE.getDscComplLan(), segmentoE.getDscIsencaoCPMF(), segmentoE.getDtaContabil(), segmentoE.getDtaLancamento(), segmentoE.getVlrLancamento(), segmentoE.getTpoLancamento(), segmentoE.getDscCategoria(), segmentoE.getCodHistBco(), segmentoE.getDscHistBco(), segmentoE.getNroDocumento() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 314 */         else if ("3".equalsIgnoreCase(codRegistro) && "A".equals(codSegmento)) {
/* 315 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoA.class);
/* 316 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 317 */           SegmentoA segmentoA = (SegmentoA)parser.parseFromText(SegmentoA.class, linha);
/* 318 */           segmentoA.setLinhaProcessada(index);
/* 319 */           febraban240vo.getListSegmentoA().add(segmentoA);
/* 320 */           codDetailA = this.baseDAO.getSequenceID("SEQ_COD_ROADCARD240_DTLH_A");
/* 321 */           this.baseDAO.merge("INSERT into FB240_DTLH_A(COD_FB240_DTLH_A,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_DTLH,COD_BANCO,LTE_SERVICO,TPO_REGISTRO,NRO_SEQ_REG_LTE,COD_SEG_DTLH,TPO_MOVIMENTO,COD_INST_MOV,COD_CAMARA_CENT,COD_BCO_FAVORECIDO,AG_MANTENEDORA,DIG_AGENCIA,NRO_CC,DIG_CC,DIG_AG_CC,NME_FAVORECIDO,NRO_DOC_EMP,DTA_PGTO,TPO_MOEDA,QTD_MOEDA,VLR_PGTO,NRO_DOC_BANCO,DTA_EFETIVACAO_PGTO,VLR_EFETIVACAO_PGTO,DSC_INFO,COD_FINALIDADE_DOC,COD_FINALIDADE_TED,COD_FINALIDADE_PGTO,DSC_CNAB,DSC_AVISO,DSC_OCORRENCIA)values(? ,? ,? ,? ,? ,? ,? , ?, ?, ?, ?, ?, ? , ?, ?, ?, ?,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? , ?, ?, ?, ? ,?, ?)", new Object[] { codDetailA, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, "3", segmentoA.getCodBanco(), segmentoA.getLoteServico(), segmentoA.getTpoRegistro(), segmentoA.getNroSeqLote(), segmentoA.getCodSeguimentoDetalhe(), segmentoA.getTpoMovimento(), segmentoA.getCodInstMov(), segmentoA.getCodCamaraCent(), segmentoA.getCodBancoFavorecido(), segmentoA.getAgMantenedora(), segmentoA.getDigAg(), segmentoA.getNroCcStr(), segmentoA.getNroDigCc(), segmentoA.getNroDigAgCc(), segmentoA.getNmeFavorecido(), segmentoA.getNroDocEmpresa(), segmentoA.getDtaPgtoStr(), segmentoA.getTpoMoeda(), segmentoA.getQtdMoeda(), segmentoA.getVlrPgto(), segmentoA.getNroDocBanco(), segmentoA.getDtaEfetivacaoStr(), segmentoA.getVlrEfetivacao(), segmentoA.getDscInfo(), segmentoA.getCodFinalidadeDoc(), segmentoA.getCodFinalidadeTed(), segmentoA.getCodFinalidadePgto(), segmentoA.getDscCnab(), segmentoA.getDscAviso(), segmentoA.getDscOcorrencia() });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 327 */         else if ("3".equalsIgnoreCase(codRegistro) && "B".equals(codSegmento)) {
/* 328 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoB.class);
/* 329 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 330 */           SegmentoB segmentoB = (SegmentoB)parser.parseFromText(SegmentoB.class, linha);
/* 331 */           segmentoB.setLinhaProcessada(index);
/* 332 */           febraban240vo.getListSegmentoB().add(segmentoB);
/* 333 */           codDetailB = this.baseDAO.getSequenceID("SEQ_COD_ROADCARD240_DTLH_B");
/* 334 */           this.baseDAO.merge("INSERT into fb240_dtlh_b(COD_FB240_DTLH_B,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_DTLH,COD_BANCO,LTE_SERVICO,TPO_REGISTRO,NRO_SEQ_LTE,COD_SEG_DTLH,USO_CNAB,TPO_INSC_FAVORECIDO,NRO_INSC_FAVORECIDO,DSC_LOGRADOURO,NRO_LOCAL,DSC_COMPLEMENTO,DSC_BAIRRO,NME_CIDADE,NRO_CEP,NRO_CEP_COMP,SLG_ESTADO,DTA_VENCIMENTO,VLR_DOCUMENTO,VLR_ABATIMENTO,VLR_DESCONTO,VLR_MORA,VLR_MULTA,COD_DOC_FAVORECIDO,AVISO_FAVORECIDO,USO_SIAPE,USO_CNAB1) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { codDetailB, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, "3", segmentoB.getCodBanco(), segmentoB.getLoteServico(), segmentoB.getTpoRegistro(), segmentoB.getNroSeqLote(), segmentoB.getCodSeguimentoDetalhe(), segmentoB.getUsoCNAB(), segmentoB.getTpoInsFavorecido(), segmentoB.getNroInsFavorecido(), segmentoB.getDscLogradouro(), segmentoB.getNroLocal(), segmentoB.getDscComplmento(), segmentoB.getDscBairro(), segmentoB.getNmeCidade(), segmentoB.getNroCep(), segmentoB.getNroCepComplemento(), segmentoB.getSlgEstado(), segmentoB.getDtaVenc(), segmentoB.getVlrDoc(), segmentoB.getVlrAba(), segmentoB.getVlrDes(), segmentoB.getVlrMora(), segmentoB.getVlrMulta(), segmentoB.getCodDocFavorecido(), segmentoB.getDscAviso(), segmentoB.getUsoSiape(), segmentoB.getUsoCNAB1() });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 339 */         else if ("3".equalsIgnoreCase(codRegistro) && "C".equals(codSegmento)) {
/* 340 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoC.class);
/* 341 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 342 */           SegmentoC segmentoC = (SegmentoC)parser.parseFromText(SegmentoC.class, linha);
/* 343 */           segmentoC.setLinhaProcessada(index);
/* 344 */           codDetailC = this.baseDAO.getSequenceID("SEQ_COD_ROADCARD240_DTLH_C");
/* 345 */           this.baseDAO.merge("INSERT into FB240_DTLH_C(COD_FB240_DTLH_C,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_DTLH,COD_BANCO,LTE_SERVICO,TPO_REGISTRO,NRO_SEQ_LTE,COD_SEG_REG_DTLH,DSC_RESERVADO,VLR_IR,VLR_ISS,VLR_IOF,VLR_DEDUCOES,VLR_ACRECIMOSCOD_AG_FAVORECIDO,COD_DIG_AG,NRO_CC,NRO_DIG_CC,NRO_DIG_AG_CC,VLR_INSS,DSC_RESERVADO1) values(? ,?, ?, ?, ?, ? ,? , ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?)", new Object[] { codDetailC, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, "3", segmentoC.getCodBanco(), segmentoC.getLoteServico(), segmentoC.getTpoRegistro(), segmentoC.getNroSeqLote(), segmentoC.getCodSegRegistro(), segmentoC.getDscReservado(), segmentoC.getVlrIr(), segmentoC.getVlrIss(), segmentoC.getVlrIof(), segmentoC.getVlrDeducoes(), segmentoC.getVlrAcrescimos(), segmentoC.getCodAgFavorecido(), segmentoC.getDigAg(), segmentoC.getNroCc(), segmentoC.getNroDigCc(), segmentoC.getNroDigAgCc(), segmentoC.getVlrInss(), segmentoC.getDscReservado1() });
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 350 */           febraban240vo.getListSegmentoC().add(segmentoC);
/*     */         } 
/*     */       } 
/* 353 */     } catch (FileNotFoundException e) {
/* 354 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 355 */     } catch (IOException e) {
/* 356 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void process(Febraban240VO registro, int codigoArquivo, int empId) throws SQLException {}
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\roadcard\Febraban240Service.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */