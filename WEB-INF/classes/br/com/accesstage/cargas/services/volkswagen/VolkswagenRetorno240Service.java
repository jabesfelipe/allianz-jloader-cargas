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
/*     */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout240.VolkswagenRetorno240VO;
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
/*     */ import org.springframework.stereotype.Component;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class VolkswagenRetorno240Service
/*     */   extends AbstractService<VolkswagenRetorno240VO>
/*     */   implements Constantes240
/*     */ {
/*  40 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.volkswagen.VolkswagenRetorno240Service.class);
/*     */ 
/*     */   
/*     */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/*  44 */     this.dataFileName = fileName;
/*  45 */     this.dataFilePath = fileDir;
/*  46 */     this.empid = empid;
/*  47 */     this.codArquivo = codArquivo;
/*  48 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/*  49 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
/*     */   public void process(File file, int codigoArquivo, int empId) throws SQLException {
/*  55 */     HybridMetadataContainer hybridMetadataContainer = new HybridMetadataContainer();
/*  56 */     VolkswagenRetorno240VO retorno240vo = new VolkswagenRetorno240VO();
/*  57 */     PositionalRecordParser parser = null;
/*  58 */     Long hdrLote = null;
/*     */     
/*  60 */     String codMesRefCarga = DateUtils.codMesRefCarga();
/*     */     try {
/*  62 */       InputStream bis = new FileInputStream(file);
/*  63 */       List<String> lines = IOUtils.readLines(bis, "UTF-8");
/*  64 */       for (int index = 0; index < lines.size(); index++) {
/*  65 */         String linha = lines.get(index);
/*  66 */         String codRegistro = linha.substring(7, 8);
/*  67 */         String codSegmento = linha.substring(13, 14);
/*  68 */         String codSegmentoOpc = linha.substring(17, 19);
/*  69 */         RecordDescriptor recordDescriptor = null;
/*  70 */         Long codHeader = null;
/*  71 */         Long codDetailA = null;
/*  72 */         Long codDetailB = null;
/*  73 */         Long codDetailJ = null;
/*  74 */         Long codDetailJ52 = null;
/*  75 */         Long codDetailZ = null;
/*  76 */         Long seqUnico = null;
/*  77 */         if ("0".equalsIgnoreCase(codRegistro)) {
/*  78 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Header.class);
/*  79 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  80 */           Header header = (Header)parser.parseFromText(Header.class, linha);
/*  81 */           header.setLinhaProcessada(index);
/*  82 */           retorno240vo.setHeader(header);
/*  83 */           codHeader = this.baseDAO.getSequenceID("SEQ_BVW_FB240_HDR_TRLLR_ARQ");
/*  84 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/*  85 */           this.baseDAO.merge("INSERT INTO BVW_FB240_HDR_TRLLR_ARQ (COD_FB240_HDR_TRLLR_ARQ, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, COD_BANCO_HDR_ARQ, LTE_SERVICO_HDR_ARQ,TPO_REGISTRO_HDR_ARQ, DSC_USO_FEBRABAN1_HDR_ARQ, TPO_INSC_EMP, NRO_INSC_EMP, COD_CONVENIO, DSC_AGENCIA, DSC_AGENCIA_DV, DSC_CONTA, DSC_CONTA_DV, DSC_AGE_CTA_DV, NME_EMPRESA, NME_BANCO, DSC_USO_FEBRABAN2_HDR_ARQ, NRO_RMSS_RTRN,DTA_GERACAO_AR, HRA_GERACAO_ARQ, NRO_NSA, NR0_LAYOUT_ARQ, COD_DENSIDADE_GRAV_ARQ, DSC_USO_RESERVADO_BCO, DSC_USO_RESERVADO_EMP, DSC_USO_FEBRABAN3_HDR_ARQ, NRO_UNICO, TPO_ARQUIVO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)", new Object[] { codHeader, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, header.getCodBcoHdrArq(), header.getLteServicoHdrArq(), header.getTpoRegHdrArq(), header.getDscUsoFbrn1(), header.getTpoIncsEmp(), header.getNroInscEmp(), header.getCodConvenio(), header.getDscAgencia(), header.getDscAgenciaDV(), header.getDscConta(), header.getDscContaDV(), header.getDscAgCtaDV(), header.getNmeEmp(), header.getNmeBco(), header.getDscUsoFbrn2(), header.getNroRmssRtrn(), header.getDtaGeracaoArq(), header.getHraGeracaoArq(), header.getNroNsa(), header.getNroLytArq(), header.getCodDensidade(), header.getDscResercadoBco(), header.getDscResercadoEmp(), header.getDscUsoFbrn3(), seqUnico, "RETORNO" });
/*     */ 
/*     */         
/*     */         }
/*  89 */         else if ("9".equalsIgnoreCase(codRegistro)) {
/*  90 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Trailler.class);
/*  91 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/*  92 */           Trailler trailler = (Trailler)parser.parseFromText(Trailler.class, linha);
/*  93 */           trailler.setLinhaProcessada(index);
/*  94 */           retorno240vo.setTrailler(trailler);
/*  95 */           this.baseDAO.merge("UPDATE BVW_FB240_HDR_TRLLR_ARQ SET COD_BANCO_TRLLR_ARQ = ?, NRO_LTE_SERVICO_ARQ = ?, TPO_REGISTRO_TRLLR_ARQ = ?, DSC_USO_FEBRABAN1_TRLLR_ARQ = ?,QTD_LTE_ARQ = ?, QTD_REG_ARQ = ?, QTD_CONTAS_CONC = ?, DSC_USO_FEBRABAN2_TRLLR_ARQ = ?, NRO_LINHA_ARQ = ? WHERE COD_FB240_HDR_TRLLR_ARQ = ?", new Object[] { trailler.getCodBcoTrllrArq(), trailler.getNroLteServico(), trailler.getTpoRegTrllrArq(), trailler.getDscUsoFbrn1(), trailler.getQtdLteArq(), trailler.getQtdRegArq(), trailler.getQtdContaConcArq(), trailler.getDscUsoFbrn2(), Integer.valueOf(index), codHeader });
/*     */         }
/*  97 */         else if ("1".equalsIgnoreCase(codRegistro)) {
/*  98 */           hdrLote = this.baseDAO.getSequenceID("SEQ_BVW_FB240_HDR_LOTE");
/*  99 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(HeaderLote.class);
/* 100 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 101 */           HeaderLote headerLote = (HeaderLote)parser.parseFromText(HeaderLote.class, linha);
/* 102 */           headerLote.setLinhaProcessada(index);
/* 103 */           retorno240vo.getListHeaderLote().add(headerLote);
/* 104 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 105 */           this.baseDAO.merge("INSERT INTO BVW_FB240_HDR_LOTE(COD_FB240_HDR_TRLLR_ARQ, COD_FB240_HDR_LOTE, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NRO_UNICO, IDT_SEGMENTO, TPO_TITULO, COD_BANCO, NRO_LOTE_SERVICO, TPO_REGISTRO, TPO_OPERACAO, TPO_SERVICO,FRM_LANCTO, NRO_VERSAO_LAYOUT, USO_FEBRABAN_01, TPO_INSCRICAO, NRO_INSCRICAO, COD_CONVENIO_BANCO, NRO_AGENCIA, NRO_AGENCIA_DV, NRO_CONTA, NRO_CONTA_DV, NRO_AG_CONTA_DV, NME_EMPRESA, MSG_EMPRESA, DSC_ENDERECO, NRO_ENDERECO, DSC_ENDERECO_COMPL, NME_CIDADE, NRO_CEP, NRO_CEP_COMPL, SGL_UF, STA_FORMA_PAGTO, USO_FEBRABAN_02, DSC_OCORRENCIAS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHeader, hdrLote, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, seqUnico, null, null, headerLote.getCodBanco(), headerLote.getLoteServico(), headerLote.getTipoRegistro(), headerLote.getTipoOperacao(), headerLote.getTipoServico(), headerLote.getFormaLancamento(), headerLote.getVersaoLayout(), headerLote.getFiller1(), headerLote.getTipoInscricao(), headerLote.getNroInscricao(), headerLote.getCodConvenio(), headerLote.getAgenciaConta(), headerLote.getDigitoAgencia(), headerLote.getContaCorrente(), headerLote.getDigitoConta(), headerLote.getDigitoAgenciaConta(), headerLote.getNomeEmpresa(), headerLote.getMensagem1(), headerLote.getEndereco(), headerLote.getNroEndereco(), headerLote.getComplementoEndereco(), headerLote.getCidade(), headerLote.getCep(), headerLote.getComplementoCep(), headerLote.getEstado(), headerLote.getIndFormaPagamento(), headerLote.getFiller2(), headerLote.getCodOcorrencia() });
/*     */ 
/*     */         
/*     */         }
/* 109 */         else if ("5".equalsIgnoreCase(codRegistro)) {
/* 110 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(TraillerLote.class);
/* 111 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 112 */           TraillerLote traillerLote = (TraillerLote)parser.parseFromText(TraillerLote.class, linha);
/* 113 */           traillerLote.setLinhaProcessada(index);
/* 114 */           traillerLote.setHeaderLote(hdrLote.intValue());
/* 115 */           retorno240vo.getListTraillerLote().add(traillerLote);
/* 116 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 117 */           this.baseDAO.merge("INSERT INTO BVW_FB240_TRL_LOTE(COD_FB240_HDR_TRLLR_ARQ, COD_FB240_HDR_LOTE, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NRO_UNICO, IDT_SEGMENTO, COD_BANCO, NRO_LOTE_SERVICO, TPO_REGISTRO, USO_FEBRABAN_01, QTD_REG_LOTE, VLR_SOMA_VALOR, QTD_SOMA_MOEDA, NRO_AVISO_DEBITO, USO_FEBRABAN_02, DSC_OCORRENCIA) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHeader, hdrLote, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, seqUnico, null, traillerLote.getCodBancoTrllr(), traillerLote.getCodLoteTrllr(), traillerLote.getCodTpoServTrllr(), traillerLote.getDscReservadoTrllr(), traillerLote.getQtdeRegistrosLotes(), traillerLote.getDscSomaVlrTrllr(), traillerLote.getDscSomaQtdMoedaTrllr(), traillerLote.getDscNumAvisoDebTrllr(), traillerLote.getDscReservado1Trllr(), traillerLote.getDscOcorrenciasTrllr() });
/*     */         }
/* 119 */         else if ("3".equalsIgnoreCase(codRegistro) && "A".equalsIgnoreCase(codSegmento)) {
/* 120 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoA.class);
/* 121 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 122 */           SegmentoA segmentoA = (SegmentoA)parser.parseFromText(SegmentoA.class, linha);
/* 123 */           segmentoA.setLinhaProcessada(index);
/* 124 */           segmentoA.setHeaderLote(hdrLote.intValue());
/* 125 */           retorno240vo.getListSegmentoA().add(segmentoA);
/* 126 */           codDetailA = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_A");
/* 127 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 128 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_A(COD_FB240_DTLH_A,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_DTLH,COD_BANCO,LTE_SERVICO,TPO_REGISTRO,NRO_SEQ_REG_LTE,COD_SEG_DTLH,TPO_MOVIMENTO,COD_INST_MOV,COD_CAMARA_CENT,COD_BCO_FAVORECIDO,AG_MANTENEDORA,DIG_AGENCIA,NRO_CC,DIG_CC,DIG_AG_CC,NME_FAVORECIDO,NRO_DOC_EMP,DTA_PGTO,TPO_MOEDA,QTD_MOEDA,VLR_PGTO,NRO_DOC_BANCO,DTA_EFETIVACAO_PGTO,VLR_EFETIVACAO_PGTO,DSC_INFO,COD_FINALIDADE_DOC,COD_FINALIDADE_TED,COD_FINALIDADE_PGTO,DSC_CNAB,DSC_AVISO,DSC_OCORRENCIA, NRO_UNICO, COD_FB240_HDR_LOTE)values(? ,? ,? ,? ,? ,? ,? , ?, ?, ?, ?, ?, ? , ?, ?, ?, ?,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? , ?, ?, ?, ? ,?, ?, ?, ?)", new Object[] { codDetailA, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, "3", segmentoA.getCodBanco(), segmentoA.getLoteServico(), segmentoA.getTpoRegistro(), segmentoA.getNroSeqLote(), segmentoA.getCodSeguimentoDetalhe(), segmentoA.getTpoMovimento(), segmentoA.getCodInstMov(), segmentoA.getCodCamaraCent(), segmentoA.getCodBancoFavorecido(), segmentoA.getAgMantenedora(), segmentoA.getDigAg(), segmentoA.getNroCcStr(), segmentoA.getNroDigCc(), segmentoA.getNroDigAgCc(), segmentoA.getNmeFavorecido(), segmentoA.getNroDocEmpresa(), segmentoA.getDtaPgtoStr(), segmentoA.getTpoMoeda(), segmentoA.getQtdMoeda(), segmentoA.getVlrPgto(), segmentoA.getNroDocBanco(), segmentoA.getDtaEfetivacaoStr(), segmentoA.getVlrEfetivacao(), segmentoA.getDscInfo(), segmentoA.getCodFinalidadeDoc(), segmentoA.getCodFinalidadeTed(), segmentoA.getCodFinalidadePgto(), segmentoA.getDscCnab(), segmentoA.getDscAviso(), segmentoA.getDscOcorrencia(), seqUnico, hdrLote });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 134 */         else if ("3".equalsIgnoreCase(codRegistro) && "B".equalsIgnoreCase(codSegmento)) {
/* 135 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoB.class);
/* 136 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 137 */           SegmentoB segmentoB = (SegmentoB)parser.parseFromText(SegmentoB.class, linha);
/* 138 */           segmentoB.setLinhaProcessada(index);
/* 139 */           segmentoB.setHeaderLote(hdrLote.intValue());
/* 140 */           retorno240vo.getListSegmentoB().add(segmentoB);
/* 141 */           codDetailB = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_B");
/* 142 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 143 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_B(COD_FB240_DTLH_B,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_DTLH,COD_BANCO,LTE_SERVICO,TPO_REGISTRO,NRO_SEQ_LTE,COD_SEG_DTLH,USO_CNAB,TPO_INSC_FAVORECIDO,NRO_INSC_FAVORECIDO,DSC_LOGRADOURO,NRO_LOCAL,DSC_COMPLEMENTO,DSC_BAIRRO,NME_CIDADE,NRO_CEP,NRO_CEP_COMP,SLG_ESTADO,DTA_VENCIMENTO,VLR_DOCUMENTO,VLR_ABATIMENTO,VLR_DESCONTO,VLR_MORA,VLR_MULTA,COD_DOC_FAVORECIDO,AVISO_FAVORECIDO,USO_SIAPE,USO_CNAB1, NRO_UNICO, COD_FB240_HDR_LOTE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { codDetailB, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, "3", segmentoB.getCodBanco(), segmentoB.getLoteServico(), segmentoB.getTpoRegistro(), segmentoB.getNroSeqLote(), segmentoB.getCodSeguimentoDetalhe(), segmentoB.getUsoCNAB(), segmentoB.getTpoInsFavorecido(), segmentoB.getNroInsFavorecido(), segmentoB.getDscLogradouro(), segmentoB.getNroLocal(), segmentoB.getDscComplmento(), segmentoB.getDscBairro(), segmentoB.getNmeCidade(), segmentoB.getNroCep(), segmentoB.getNroCepComplemento(), segmentoB.getSlgEstado(), segmentoB.getDtaVenc(), segmentoB.getVlrDoc(), segmentoB.getVlrAba(), segmentoB.getVlrDes(), segmentoB.getVlrMora(), segmentoB.getVlrMulta(), segmentoB.getCodDocFavorecido(), segmentoB.getDscAviso(), segmentoB.getUsoSiape(), segmentoB.getUsoCNAB1(), seqUnico, hdrLote });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 148 */         else if ("3".equalsIgnoreCase(codRegistro) && "J".equalsIgnoreCase(codSegmento) && !"52".equalsIgnoreCase(codSegmentoOpc)) {
/* 149 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoJ.class);
/* 150 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 151 */           SegmentoJ segmentoJ = (SegmentoJ)parser.parseFromText(SegmentoJ.class, linha);
/* 152 */           segmentoJ.setLinhaProcessada(index);
/* 153 */           segmentoJ.setHeaderLote(hdrLote.intValue());
/* 154 */           retorno240vo.getListSegmentoJ().add(segmentoJ);
/* 155 */           codDetailJ = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_J");
/* 156 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 157 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_J(COD_FB240_HDR_TRLLR_ARQ, COD_FB240_HDR_LOTE, COD_FB240_DTLH_J, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NRO_UNICO, IDT_SEGMENTO, COD_BANCO, NRO_LOTE_SERVICO, TPO_REGISTRO, NRO_SEQ_REGISTRO, TPO_SEGMENTO, TPO_MOVTO, COD_INSTR_MOVTO, DSC_CODBAR, NME_BENEFICIARIO, DTA_VENCTO, VLR_TITULO, VLR_DESCONTO, VLR_MULTA, DTA_PAGTO, VLR_PAGTO, QTD_MOEDA, NRO_SEU_NUMERO,NRO_NOSSO_NUMERO, COD_MOEDA, USO_FEBRABAN_01, COD_OCORRENCIAS) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)", new Object[] { codHeader, hdrLote, codDetailJ, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, seqUnico, "J", segmentoJ.getCodBco(), segmentoJ.getLteServico(), segmentoJ.getTpoReg(), segmentoJ.getNroSeq(), "3", segmentoJ.getTipoMovimento(), segmentoJ.getCodInstMovimento(), segmentoJ.getCodidoBarras(), segmentoJ.getNomeCedente(), segmentoJ.getDataVencNominal(), segmentoJ.getValorTituloNominal(), segmentoJ.getValorDesconto(), segmentoJ.getValorMoraMulta(), segmentoJ.getDtaPagto(), segmentoJ.getValorPagamento(), segmentoJ.getQuantidadeMoeda(), segmentoJ.getSeuNumero(), segmentoJ.getNumDocBanco(), segmentoJ.getCodMoeda(), segmentoJ.getFiller1(), segmentoJ.getCodOcorrencia() });
/*     */ 
/*     */         
/*     */         }
/* 161 */         else if ("3".equalsIgnoreCase(codRegistro) && "J".equalsIgnoreCase(codSegmento) && "52".equalsIgnoreCase(codSegmentoOpc)) {
/* 162 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(SegmentoJ52.class);
/* 163 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 164 */           SegmentoJ52 segmentoJ52 = (SegmentoJ52)parser.parseFromText(SegmentoJ52.class, linha);
/* 165 */           segmentoJ52.setLinhaProcessada(index);
/* 166 */           segmentoJ52.setHeaderLote(hdrLote.intValue());
/* 167 */           retorno240vo.getListSegmentoJ52().add(segmentoJ52);
/* 168 */           codDetailJ52 = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_J52");
/* 169 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 170 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_J52(COD_FB240_HDR_TRLLR_ARQ, COD_FB240_HDR_LOTE, COD_FB240_DTLH_J52, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NRO_UNICO, IDT_SEGMENTO, COD_BANCO, NRO_LOTE_SERVICO, TPO_REGISTRO, NRO_SEQ_REGISTRO, TPO_SEGMENTO, USO_FEBRABAN_01, COD_INSTR_MOVTO, STA_REG_OPCIONAL, TPO_INSCRICAO_PAGADOR_01, NRO_INSCRICAO_PAGADOR_01, NME_PAGADOR_01, TPO_INSCRICAO_BENEF, NRO_INSCRICAO_BENEF, NME_BENEF, TPO_INSCRICAO_PAGADOR_02, NRO_INSCRICAO_PAGADOR_02, NME_PAGADOR_02, USO_FEBRABAN_02) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { codHeader, hdrLote, codDetailJ52, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), codMesRefCarga, seqUnico, "J52", segmentoJ52.getCodBco(), segmentoJ52.getLteServico(), segmentoJ52.getTpoReg(), segmentoJ52.getNroSeq(), "3", segmentoJ52.getUsoExclusivoFebraban(), segmentoJ52.getCodInstMovimento(), segmentoJ52.getCodRegOpcional(), segmentoJ52.getTipoInscricaoSacado(), segmentoJ52.getNumInscricaoSacado(), segmentoJ52.getNomeSacado(), segmentoJ52.getTipoInscricaoCedente(), segmentoJ52.getNumInscricaoCedente(), segmentoJ52.getNomeCedente(), segmentoJ52.getTipoInscricaoSacador(), segmentoJ52.getNumInscricaoSacador(), segmentoJ52.getNomeSacador(), segmentoJ52.getFiller1() });
/*     */         
/*     */         }
/* 173 */         else if ("3".equalsIgnoreCase(codRegistro) && "Z".equalsIgnoreCase(codSegmento)) {
/* 174 */           recordDescriptor = hybridMetadataContainer.getRecordDescriptor(Segmento3Z.class);
/* 175 */           parser = new PositionalRecordParser((PositionalRecordDescriptor)recordDescriptor);
/* 176 */           Segmento3Z segmentoZ = (Segmento3Z)parser.parseFromText(Segmento3Z.class, linha);
/* 177 */           segmentoZ.setLinhaProcessada(index);
/* 178 */           segmentoZ.setHeaderLote(hdrLote.intValue());
/* 179 */           retorno240vo.getListSegmento3Z().add(segmentoZ);
/* 180 */           codDetailZ = this.baseDAO.getSequenceID("SEQ_BVW_FB240_DTLH_Z");
/* 181 */           seqUnico = this.baseDAO.getSequenceID("SEQ_NRO_UNICO");
/* 182 */           this.baseDAO.merge("INSERT INTO BVW_FB240_DTLH_Z(COD_FB240_DTLH_Z, COD_FB240_HDR_LOTE, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_BANCO, LTE_SERVICO, TPO_REGISTRO, NRO_SEQ_REG_LTE, COD_SEG_DTLH, AUT_LEGIS, AUT_MECANICA, COD_OCORR, NRO_UNICO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { hdrLote, codDetailZ, Integer.valueOf(this.codArquivo), Integer.valueOf(index), Integer.valueOf(empId), segmentoZ.getCodBanco(), segmentoZ.getLoteServico(), segmentoZ.getTpoRegistro(), segmentoZ.getNroSeqLote(), segmentoZ.getCodSeguimentoDetalhe(), segmentoZ.getAutenticacaoLegislacao(), segmentoZ.getAutenticacaoMecanica(), segmentoZ.getCodOcorrencia(), seqUnico });
/*     */         }
/*     */       
/*     */       } 
/* 186 */     } catch (FileNotFoundException e) {
/* 187 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/* 188 */     } catch (IOException e) {
/* 189 */       logger.error("Erro ao ler o arquivo - [" + file.getName() + "] " + e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void process(VolkswagenRetorno240VO registro, int codigoArquivo, int empId) throws SQLException {}
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\volkswagen\VolkswagenRetorno240Service.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */