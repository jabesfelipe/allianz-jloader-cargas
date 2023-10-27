/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.volkswagen;
/*    */ 
/*    */ import br.com.accesstage.cargas.services.AbstractService;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.Detalhe;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.Trailler;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.retorno.VolkswagenRetorno500VO;
/*    */ import java.io.File;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class VolkswagenRetorno500Service
/*    */   extends AbstractService<VolkswagenRetorno500VO>
/*    */ {
/* 20 */   private String SEQ_BVW_PGF500_RET_HDR_TRLLR = "SEQ_BVW_PGF500_RET_HDR_TRLLR";
/*    */   
/* 22 */   private String SQL_INSERT_BVW_PGF500_RET_HDR_TRLLR = "INSERT INTO BVW_PGF500_RET_HDR_TRLLR (COD_PGF500_RET_HDR_TRLLR,COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_COMUNICACAO_HDR, TPO_INSCRICAO, NRO_DOCUMENTO, NME_EMPRESA\t,TPO_SERVICO_HDR, COD_ORIGEM_ARQUIVO_HDR , NRO_REMESSA_HDR, DTA_GRAVACAO_ARQUIVO_HDR, NRO_LIST_ARQU, NRO_SEQUENCIAL_REGISTROS_HDR, NRO_RETORNO, TPO_PROCESSAMENTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?) ";
/*    */ 
/*    */ 
/*    */   
/* 26 */   private String SQL_INSERT_ROADCARD500_RET_DTLH = "INSERT INTO BVW_PGF500_RET_DTLH (COD_PGF500_RET_DTLH, COD_PGF500_RET_HDR_TRLLR, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, TIP_INSCRICAO, NRO_DOCUMENTO, NME_FORNECEDOR, ENDERECO_FORNECEDOR, COD_CEP_FORNECEDOR, COD_BANCO_FORNECEDOR, COD_AGENCIA_FORNECEDOR, DIG_AGENCIA_FORNECEDOR, NRO_CCORRENTE_FORNECEDOR,DIG_CCORRENTE_FORNECEDOR,NRO_PGTO, CARTEIRA, NSO_NUMERO, SEU_NUMERO, DTA_VENCIMENTO, DTA_EMI_DCTO, DTA_LMT_DSCT, FTR_VENCIMENTO, VLR_DCTO, VLR_PAGTO, VLR_DSCT, VLR_ACMO, TPO_DCTO, NRO_NOT_FISCAL, MOD_PGTO, DTA_EFT_PGTO, COD_MOEDA, SIT_AGTO, TPO_MVTO, COD_MVTO, HRA_CONS_SALDO, VLR_SALDO, VLR_TAX_FUND, NME_SAC_AVAL, INF_COMPL, COD_AREA_EMP, USO_EMPRESA, COD_LANCAMENTO, TPO_CONTA, CONTA_COMPL, NRO_SEQUENCIAL, SER_CODTO, INF_RET_1, INF_RET_2, INF_RET_3, INF_RET_4, INF_RET_5, NVL_INF_RET, NRO_UNICO) VALUES (SEQ_BVW_PGF500_RET_DTLH.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SEQ_NRO_UNICO.NEXTVAL)";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   private String SQL_UPDATE_BVW_PGF500_RET_HDR_TRLLR = "UPDATE BVW_PGF500_RET_HDR_TRLLR SET QTD_REGISTROS_TRLLR = ?, VLR_TOTAL_PAGAMENTOS_TRLLR = ?, NRO_SEQUENCIAL_REGISTRO_TRLLR = ? WHERE COD_PGF500_RET_HDR_TRLLR = ? ";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/* 41 */     this.dataFileName = fileName;
/* 42 */     this.dataFilePath = fileDir;
/* 43 */     this.empid = empid;
/* 44 */     this.codArquivo = codArquivo;
/* 45 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/* 46 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
/*    */   public void process(VolkswagenRetorno500VO registro, int codigoArquivo, int empId) throws SQLException {
/* 53 */     Long codHeader = this.baseDAO.getSequenceID(this.SEQ_BVW_PGF500_RET_HDR_TRLLR);
/* 54 */     Header header = registro.getHeader();
/*    */     
/* 56 */     this.baseDAO.merge(this.SQL_INSERT_BVW_PGF500_RET_HDR_TRLLR, new Object[] { codHeader, Integer.valueOf(codigoArquivo), Integer.valueOf(header.getLinhaProcessada()), Integer.valueOf(empId), header.getCodigoComunicacao(), header.getTipoInscricao(), header.getNroDocumento(), header.getNmeEmpresa(), header.getTipoServico(), header.getCodigoOrigemArquivo(), header.getNumeroRemessa(), header.getDataHoraArquivo(), header.getNumeroListaDebito(), header.getNumeroSequencial(), header.getNumeroRetorno(), header.getTipoProcessamento() });
/*    */ 
/*    */     
/* 59 */     List<Detalhe> listaDetalhe = registro.getListaDetalhe();
/* 60 */     for (Detalhe det : listaDetalhe) {
/*    */       
/* 62 */       this.baseDAO.merge(this.SQL_INSERT_ROADCARD500_RET_DTLH, new Object[] { codHeader, Integer.valueOf(codigoArquivo), Integer.valueOf(det.getLinhaProcessada()), Integer.valueOf(empId), det.getTipoInscricao(), det.getNroDocumento(), det.getNomeFornecedor(), det.getEnderecoFornecedor(), det.getCepFornecedor(), det.getCodigoBancoFornecedor(), det.getAgenciaFornecedor(), det.getDigitoFornecedor(), det.getContaCorrenteFornecedor(), det.getDigitoContaCorrenteFornecedor(), det.getNumeroPagamento(), det.getCarteira(), det.getNossoNumero(), det.getSeuNumero(), det.getDataVencimento(), det.getDataEmissaoDocumento(), det.getDataLimiteDesconto(), det.getFatorVencimento(), det.getValorDocumento(), det.getValorPagamento(), det.getValorDesconto(), det.getValorAcrescimo(), det.getTipoDocumento(), det.getNumeroNotaFiscal(), det.getModalidadePagamento(), det.getDataEfetivacao(), det.getMoeda(), det.getSituacaoAgendamento(), det.getTipoMovimento(), det.getCodigoMovimento(), det.getHorarioConsultaSaldo(), det.getSaldoDisponivelSaldo(), det.getValorTaxaPreFunding(), det.getSacadorAvalista(), det.getInformacoesComplementares(), det.getCodigoAreaEmpresa(), det.getCampoUsoEmpresa(), det.getCodigoLancamento(), det.getTipoContaFornecedor(), det.getContaComplementar(), det.getNumeroSequencial(), det.getSerieDocumento(), det.getInformacaoRetorno1(), det.getInformacaoRetorno2(), det.getInformacaoRetorno3(), det.getInformacaoRetorno4(), det.getInformacaoRetorno5(), det.getNivelInfoRetorno() });
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     Trailler trailler = registro.getTrailler();
/* 71 */     this.baseDAO.merge(this.SQL_UPDATE_BVW_PGF500_RET_HDR_TRLLR, new Object[] { trailler.getQtdeRegistros(), trailler.getTotalValoresPagamentos(), trailler.getNumeroSequencial(), codHeader });
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\volkswagen\VolkswagenRetorno500Service.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */