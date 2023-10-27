/*     */ package WEB-INF.classes.br.com.accesstage.cargas.services.volkswagen;
/*     */ 
/*     */ import br.com.accesstage.cargas.services.AbstractService;
/*     */ import br.com.accesstage.loader.util.constantes.carga.DateUtils;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.Constantes240;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.Header;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.HeaderLote;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.Segmento3Z;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.SegmentoA;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.SegmentoB;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.SegmentoJ;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.SegmentoJ52;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.Trailler;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.TraillerLote;
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.VolkswagenRemessa240VO;
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
/*     */ public class VolkswagenRemessa240Service
/*     */   extends AbstractService<VolkswagenRemessa240VO>
/*     */   implements Constantes240
/*     */ {
/*  40 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.volkswagen.VolkswagenRemessa240Service.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/*  45 */     this.dataFileName = fileName;
/*  46 */     this.dataFilePath = fileDir;
/*  47 */     this.empid = empid;
/*  48 */     this.codArquivo = codArquivo;
/*  49 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/*  50 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
/*     */   public void process(File file, int codigoArquivo, int empId) throws SQLException {
/*  56 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/*  57 */     VolkswagenRemessa240VO remessa240vo = new VolkswagenRemessa240VO();
/*  58 */     PositionalRecordParser parser = null;
/*  59 */     Long hdrLote = null;
/*     */     
/*  61 */     String codMesRefCarga = DateUtils.codMesRefCarga();
/*     */     try {
/*  63 */       InputStream bis = new FileInputStream(file);
/*  64 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/*  65 */       for (int index = 0; index < lines.size(); index++) {
/*  66 */         String linha = lines.get(index);
/*  67 */         String codRegistro = linha.substring(7, 8);
/*  68 */         String codSegmento = linha.substring(13, 14);
/*  69 */         String codSegmentoOpc = linha.substring(17, 19);
/*  70 */         RecordDescriptor recordDescriptor = null;
/*  71 */         Long codHeader = null;
/*  72 */         Long codDetailA = null;
/*  73 */         Long codDetailB = null;
/*  74 */         Long codDetailJ = null;
/*  75 */         Long codDetailJ52 = null;
/*  76 */         Long codDetailZ = null;
/*  77 */         Long seqUnico = null;
/*  78 */         if ("0".equalsIgnoreCase(codRegistro)) {
/*  79 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/*  80 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  81 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/*  82 */           header.setLinhaProcessada(index);
/*  83 */           remessa240vo.setHeader(header);
/*  84 */           codHeader = this.baseDAO.getSequenceID("SEQ_BVW_FB240_HDR_TRLLR_ARQ");
/*  85 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/*  86 */           this.baseDAO.merge("INSERT INTO BVW_FB240_HDR_TRLLR_ARQ (COD_FB240_HDR_TRLLR_ARQ, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO_HDR_ARQ, LTE_SERVICO_HDR_ARQ,TPO_REGISTRO_HDR_ARQ, DSC_USO_FEBRABAN1_HDR_ARQ, TPO_INSC_EMP, NRO_INSC_EMP, COD_CONVENIO, DSC_AGENCIA, DSC_AGENCIA_DV, DSC_CONTA, DSC_CONTA_DV, DSC_AGE_CTA_DV, NME_EMPRESA, NME_BANCO, DSC_USO_FEBRABAN2_HDR_ARQ, NRO_RMSS_RTRN,DTA_GERACAO_AR, HRA_GERACAO_ARQ, NRO_NSA, NR0_LAYOUT_ARQ, COD_DENSIDADE_GRAV_ARQ, DSC_USO_RESERVADO_BCO, DSC_USO_RESERVADO_EMP, DSC_USO_FEBRABAN3_HDR_ARQ, NRO_UNICO, TPO_ARQUIVO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)", new Object[] { codHeader, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, header.getCodBcoHdrArq(), header.getLteServicoHdrArq(), header.getTpoRegHdrArq(), header.getDscUsoFbrn1(), header.getTpoIncsEmp(), header.getNroInscEmp(), header.getCodConvenio(), header.getDscAgencia(), header.getDscAgenciaDV(), header.getDscConta(), header.getDscContaDV(), header.getDscAgCtaDV(), header.getNmeEmp(), header.getNmeBco(), header.getDscUsoFbrn2(), header.getNroRmssRtrn(), header.getDtaGeracaoArq(), header.getHraGeracaoArq(), header.getNroNsa(), header.getNroLytArq(), header.getCodDensidade(), header.getDscResercadoBco(), header.getDscResercadoEmp(), header.getDscUsoFbrn3(), seqUnico, "REMESSA" });
/*     */ 
/*     */         
/*     */         }
/*  90 */         else if ("9".equalsIgnoreCase(codRegistro)) {
/*  91 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/*  92 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  93 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/*  94 */           trailler.setLinhaProcessada(index);
/*  95 */           remessa240vo.setTrailler(trailler);
/*  96 */           this.baseDAO.merge("UPDATE BVW_FB240_HDR_TRLLR_ARQ SET COD_BANCO_TRLLR_ARQ = ?, NRO_LTE_SERVICO_ARQ = ?, TPO_REGISTRO_TRLLR_ARQ = ?, DSC_USO_FEBRABAN1_TRLLR_ARQ = ?,QTD_LTE_ARQ = ?, QTD_REG_ARQ = ?, QTD_CONTAS_CONC = ?, DSC_USO_FEBRABAN2_TRLLR_ARQ = ?, NRO_LINHA_ARQ = ? WHERE COD_FB240_HDR_TRLLR_ARQ = ?", new Object[] { trailler.getCodBcoTrllrArq(), trailler.getNroLteServico(), trailler.getTpoRegTrllrArq(), trailler.getDscUsoFbrn1(), trailler.getQtdLteArq(), trailler.getQtdRegArq(), trailler.getQtdContaConcArq(), trailler.getDscUsoFbrn2(), Integer.valueOf(index), codHeader });
/*     */         }
/*  98 */         else if ("1".equalsIgnoreCase(codRegistro)) {
/*  99 */           hdrLote = this.baseDAO.getSequenceID("SEQ_BVW_FB240_HDR_LOTE");
/* 100 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HeaderLote.class);
/* 101 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 102 */           HeaderLote headerLote = (HeaderLote)parser.parseFromText(HeaderLote.class, linha);
/* 103 */           headerLote.setLinhaProcessada(index);
/* 104 */           remessa240vo.getListHeaderLote().add(headerLote);
/* 105 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 106 */           this.baseDAO.merge("INSERT INTO BVW_FB240_HDR_LOTE(COD_FB240_HDR_TRLLR_ARQ, COD_FB240_HDR_LOTE, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NRO_UNICO, IDT_SEGMENTO, TPO_TITULO, COD_BANCO, NRO_LOTE_SERVICO, TPO_REGISTRO, TPO_OPERACAO, TPO_SERVICO,FRM_LANCTO, NRO_VERSAO_LAYOUT, USO_FEBRABAN_01, TPO_INSCRICAO, NRO_INSCRICAO, COD_CONVENIO_BANCO, NRO_AGENCIA, NRO_AGENCIA_DV, NRO_CONTA, NRO_CONTA_DV, NRO_AG_CONTA_DV, NME_EMPRESA, MSG_EMPRESA, DSC_ENDERECO, NRO_ENDERECO, DSC_ENDERECO_COMPL, NME_CIDADE, NRO_CEP, NRO_CEP_COMPL, SGL_UF, STA_FORMA_PAGTO, USO_FEBRABAN_02, DSC_OCORRENCIAS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHeader, hdrLote, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, seqUnico, null, null, headerLote.getCodBanco(), headerLote.getLoteServico(), headerLote.getTipoRegistro(), headerLote.getTipoOperacao(), headerLote.getTipoServico(), headerLote.getFormaLancamento(), headerLote.getVersaoLayout(), headerLote.getFiller1(), headerLote.getTipoInscricao(), headerLote.getNroInscricao(), headerLote.getCodConvenio(), headerLote.getAgenciaConta(), headerLote.getDigitoAgencia(), headerLote.getContaCorrente(), headerLote.getDigitoConta(), headerLote.getDigitoAgenciaConta(), headerLote.getNomeEmpresa(), headerLote.getMensagem1(), headerLote.getEndereco(), headerLote.getNroEndereco(), headerLote.getComplementoEndereco(), headerLote.getCidade(), headerLote.getCep(), headerLote.getComplementoCep(), headerLote.getEstado(), headerLote.getIndFormaPagamento(), headerLote.getFiller2(), headerLote.getCodOcorrencia() });
/*     */ 
/*     */         
/*     */         }
/* 110 */         else if ("5".equalsIgnoreCase(codRegistro)) {
/* 111 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TraillerLote.class);
/* 112 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 113 */           TraillerLote traillerLote = (TraillerLote)parser.parseFromText(TraillerLote.class, linha);
/* 114 */           traillerLote.setLinhaProcessada(index);
/* 115 */           traillerLote.setHeaderLote(hdrLote.intValue());
/* 116 */           remessa240vo.getListTraillerLote().add(traillerLote);
/* 117 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 118 */           this.baseDAO.merge("INSERT INTO BVW_FB240_TRL_LOTE(COD_FB240_HDR_TRLLR_ARQ, COD_FB240_HDR_LOTE, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NRO_UNICO, IDT_SEGMENTO, COD_BANCO, NRO_LOTE_SERVICO, TPO_REGISTRO, USO_FEBRABAN_01, QTD_REG_LOTE, VLR_SOMA_VALOR, QTD_SOMA_MOEDA, NRO_AVISO_DEBITO, USO_FEBRABAN_02, DSC_OCORRENCIA) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHeader, hdrLote, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, seqUnico, null, traillerLote.getCodBancoTrllr(), traillerLote.getCodLoteTrllr(), traillerLote.getCodTpoServTrllr(), traillerLote.getDscReservadoTrllr(), traillerLote.getQtdeRegistrosLotes(), traillerLote.getDscSomaVlrTrllr(), traillerLote.getDscSomaQtdMoedaTrllr(), traillerLote.getDscNumAvisoDebTrllr(), traillerLote.getDscReservado1Trllr(), traillerLote.getDscOcorrenciasTrllr() });
/*     */         }
/* 120 */         else if ("3".equalsIgnoreCase(codRegistro) && "A".equalsIgnoreCase(codSegmento)) {
/* 121 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoA.class);
/* 122 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 123 */           SegmentoA segmentoA = (SegmentoA)parser.parseFromText(SegmentoA.class, linha);
/* 124 */           segmentoA.setLinhaProcessada(index);
/* 125 */           segmentoA.setHeaderLote(hdrLote.intValue());
/* 126 */           remessa240vo.getListSegmentoA().add(segmentoA);
/* 127 */           codDetailA = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_A");
/* 128 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 129 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_A(COD_FB240_DTLH_A,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_DTLH,COD_BANCO,LTE_SERVICO,TPO_REGISTRO,NRO_SEQ_REG_LTE,COD_SEG_DTLH,TPO_MOVIMENTO,COD_INST_MOV,COD_CAMARA_CENT,COD_BCO_FAVORECIDO,AG_MANTENEDORA,DIG_AGENCIA,NRO_CC,DIG_CC,DIG_AG_CC,NME_FAVORECIDO,NRO_DOC_EMP,DTA_PGTO,TPO_MOEDA,QTD_MOEDA,VLR_PGTO,NRO_DOC_BANCO,DTA_EFETIVACAO_PGTO,VLR_EFETIVACAO_PGTO,DSC_INFO,COD_FINALIDADE_DOC,COD_FINALIDADE_TED,COD_FINALIDADE_PGTO,DSC_CNAB,DSC_AVISO,DSC_OCORRENCIA, NRO_UNICO, COD_FB240_HDR_LOTE)values(? ,? ,? ,? ,? ,? ,? , ?, ?, ?, ?, ?, ? , ?, ?, ?, ?,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? , ?, ?, ?, ? ,?, ?, ?, ?)", new Object[] { codDetailA, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, "3", segmentoA.getCodBanco(), segmentoA.getLoteServico(), segmentoA.getTpoRegistro(), segmentoA.getNroSeqLote(), segmentoA.getCodSeguimentoDetalhe(), segmentoA.getTpoMovimento(), segmentoA.getCodInstMov(), segmentoA.getCodCamaraCent(), segmentoA.getCodBancoFavorecido(), segmentoA.getAgMantenedora(), segmentoA.getDigAg(), segmentoA.getNroCcStr(), segmentoA.getNroDigCc(), segmentoA.getNroDigAgCc(), segmentoA.getNmeFavorecido(), segmentoA.getNroDocEmpresa(), segmentoA.getDtaPgtoStr(), segmentoA.getTpoMoeda(), segmentoA.getQtdMoeda(), segmentoA.getVlrPgto(), segmentoA.getNroDocBanco(), segmentoA.getDtaEfetivacaoStr(), segmentoA.getVlrEfetivacao(), segmentoA.getDscInfo(), segmentoA.getCodFinalidadeDoc(), segmentoA.getCodFinalidadeTed(), segmentoA.getCodFinalidadePgto(), segmentoA.getDscCnab(), segmentoA.getDscAviso(), segmentoA.getDscOcorrencia(), seqUnico, hdrLote });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 135 */         else if ("3".equalsIgnoreCase(codRegistro) && "B".equalsIgnoreCase(codSegmento)) {
/* 136 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoB.class);
/* 137 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 138 */           SegmentoB segmentoB = (SegmentoB)parser.parseFromText(SegmentoB.class, linha);
/* 139 */           segmentoB.setLinhaProcessada(index);
/* 140 */           segmentoB.setHeaderLote(hdrLote.intValue());
/* 141 */           remessa240vo.getListSegmentoB().add(segmentoB);
/* 142 */           codDetailB = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_B");
/* 143 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 144 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_B(COD_FB240_DTLH_B,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_DTLH,COD_BANCO,LTE_SERVICO,TPO_REGISTRO,NRO_SEQ_LTE,COD_SEG_DTLH,USO_CNAB,TPO_INSC_FAVORECIDO,NRO_INSC_FAVORECIDO,DSC_LOGRADOURO,NRO_LOCAL,DSC_COMPLEMENTO,DSC_BAIRRO,NME_CIDADE,NRO_CEP,NRO_CEP_COMP,SLG_ESTADO,DTA_VENCIMENTO,VLR_DOCUMENTO,VLR_ABATIMENTO,VLR_DESCONTO,VLR_MORA,VLR_MULTA,COD_DOC_FAVORECIDO,AVISO_FAVORECIDO,USO_SIAPE,USO_CNAB1, NRO_UNICO, COD_FB240_HDR_LOTE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { codDetailB, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, "3", segmentoB.getCodBanco(), segmentoB.getLoteServico(), segmentoB.getTpoRegistro(), segmentoB.getNroSeqLote(), segmentoB.getCodSeguimentoDetalhe(), segmentoB.getUsoCNAB(), segmentoB.getTpoInsFavorecido(), segmentoB.getNroInsFavorecido(), segmentoB.getDscLogradouro(), segmentoB.getNroLocal(), segmentoB.getDscComplmento(), segmentoB.getDscBairro(), segmentoB.getNmeCidade(), segmentoB.getNroCep(), segmentoB.getNroCepComplemento(), segmentoB.getSlgEstado(), segmentoB.getDtaVenc(), segmentoB.getVlrDoc(), segmentoB.getVlrAba(), segmentoB.getVlrDes(), segmentoB.getVlrMora(), segmentoB.getVlrMulta(), segmentoB.getCodDocFavorecido(), segmentoB.getDscAviso(), segmentoB.getUsoSiape(), segmentoB.getUsoCNAB1(), seqUnico, hdrLote });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 149 */         else if ("3".equalsIgnoreCase(codRegistro) && "J".equalsIgnoreCase(codSegmento) && !"52".equalsIgnoreCase(codSegmentoOpc)) {
/* 150 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoJ.class);
/* 151 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 152 */           SegmentoJ segmentoJ = (SegmentoJ)parser.parseFromText(SegmentoJ.class, linha);
/* 153 */           segmentoJ.setLinhaProcessada(index);
/* 154 */           segmentoJ.setHeaderLote(hdrLote.intValue());
/* 155 */           remessa240vo.getListSegmentoJ().add(segmentoJ);
/* 156 */           codDetailJ = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_J");
/* 157 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 158 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_J(COD_FB240_HDR_TRLLR_ARQ, COD_FB240_HDR_LOTE, COD_FB240_DTLH_J, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NRO_UNICO, IDT_SEGMENTO, COD_BANCO, NRO_LOTE_SERVICO, TPO_REGISTRO, NRO_SEQ_REGISTRO, TPO_SEGMENTO, TPO_MOVTO, COD_INSTR_MOVTO, DSC_CODBAR, NME_BENEFICIARIO, DTA_VENCTO, VLR_TITULO, VLR_DESCONTO, VLR_MULTA, DTA_PAGTO, VLR_PAGTO, QTD_MOEDA, NRO_SEU_NUMERO,NRO_NOSSO_NUMERO, COD_MOEDA, USO_FEBRABAN_01, COD_OCORRENCIAS) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)", new Object[] { codHeader, hdrLote, codDetailJ, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, seqUnico, "J", segmentoJ.getCodBco(), segmentoJ.getLteServico(), segmentoJ.getTpoReg(), segmentoJ.getNroSeq(), "3", segmentoJ.getTipoMovimento(), segmentoJ.getCodInstMovimento(), segmentoJ.getCodidoBarras(), segmentoJ.getNomeCedente(), segmentoJ.getDataVencNominal(), segmentoJ.getValorTituloNominal(), segmentoJ.getValorDesconto(), segmentoJ.getValorMoraMulta(), segmentoJ.getDtaPagto(), segmentoJ.getValorPagamento(), segmentoJ.getQuantidadeMoeda(), segmentoJ.getSeuNumero(), segmentoJ.getNumDocBanco(), segmentoJ.getCodMoeda(), segmentoJ.getFiller1(), segmentoJ.getCodOcorrencia() });
/*     */ 
/*     */         
/*     */         }
/* 162 */         else if ("3".equalsIgnoreCase(codRegistro) && "J".equalsIgnoreCase(codSegmento) && "52".equalsIgnoreCase(codSegmentoOpc)) {
/* 163 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoJ52.class);
/* 164 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 165 */           SegmentoJ52 segmentoJ52 = (SegmentoJ52)parser.parseFromText(SegmentoJ52.class, linha);
/* 166 */           segmentoJ52.setLinhaProcessada(index);
/* 167 */           segmentoJ52.setHeaderLote(hdrLote.intValue());
/* 168 */           remessa240vo.getListSegmentoJ52().add(segmentoJ52);
/* 169 */           codDetailJ52 = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_J52");
/* 170 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 171 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_J52(COD_FB240_HDR_TRLLR_ARQ, COD_FB240_HDR_LOTE, COD_FB240_DTLH_J52, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NRO_UNICO, IDT_SEGMENTO, COD_BANCO, NRO_LOTE_SERVICO, TPO_REGISTRO, NRO_SEQ_REGISTRO, TPO_SEGMENTO, USO_FEBRABAN_01, COD_INSTR_MOVTO, STA_REG_OPCIONAL, TPO_INSCRICAO_PAGADOR_01, NRO_INSCRICAO_PAGADOR_01, NME_PAGADOR_01, TPO_INSCRICAO_BENEF, NRO_INSCRICAO_BENEF, NME_BENEF, TPO_INSCRICAO_PAGADOR_02, NRO_INSCRICAO_PAGADOR_02, NME_PAGADOR_02, USO_FEBRABAN_02) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHeader, hdrLote, codDetailJ52, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, seqUnico, "J52", segmentoJ52.getCodBco(), segmentoJ52.getLteServico(), segmentoJ52.getTpoReg(), segmentoJ52.getNroSeq(), "3", segmentoJ52.getUsoExclusivoFebraban(), segmentoJ52.getCodInstMovimento(), segmentoJ52.getCodRegOpcional(), segmentoJ52.getTipoInscricaoSacado(), segmentoJ52.getNumInscricaoSacado(), segmentoJ52.getNomeSacado(), segmentoJ52.getTipoInscricaoCedente(), segmentoJ52.getNumInscricaoCedente(), segmentoJ52.getNomeCedente(), segmentoJ52.getTipoInscricaoSacador(), segmentoJ52.getNumInscricaoSacador(), segmentoJ52.getNomeSacador(), segmentoJ52.getFiller1() });
/*     */         
/*     */         }
/* 174 */         else if ("3".equalsIgnoreCase(codRegistro) && "Z".equalsIgnoreCase(codSegmento)) {
/* 175 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Segmento3Z.class);
/* 176 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 177 */           Segmento3Z segmentoZ = (Segmento3Z)parser.parseFromText(Segmento3Z.class, linha);
/* 178 */           segmentoZ.setLinhaProcessada(index);
/* 179 */           segmentoZ.setHeaderLote(hdrLote.intValue());
/* 180 */           remessa240vo.getListSegmento3Z().add(segmentoZ);
/* 181 */           codDetailZ = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_Z");
/* 182 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 183 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_Z(COD_FB240_DTLH_Z, COD_FB240_HDR_LOTE, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_REG_LTE, COD_SEG_DTLH, AUT_LEGIS, AUT_MECANICA, COD_OCORR, NRO_UNICO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { hdrLote, codDetailZ, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), segmentoZ.getCodBanco(), segmentoZ.getLoteServico(), segmentoZ.getTpoRegistro(), segmentoZ.getNroSeqLote(), segmentoZ.getCodSeguimentoDetalhe(), segmentoZ.getAutenticacaoLegislacao(), segmentoZ.getAutenticacaoMecanica(), segmentoZ.getCodOcorrencia(), seqUnico });
/*     */         }
/*     */       
/*     */       } 
/* 187 */     } catch (FileNotFoundException e) {
/* 188 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 189 */     } catch (IOException e) {
/* 190 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void process(VolkswagenRemessa240VO registro, int codigoArquivo, int empId) throws SQLException {}
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\volkswagen\VolkswagenRemessa240Service.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */