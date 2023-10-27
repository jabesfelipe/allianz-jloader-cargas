/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.volkswagen;
/*    */ 
/*    */ import br.com.accesstage.cargas.services.AbstractService;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.remessa.Detalhe;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.remessa.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.remessa.Trailler;
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.remessa.VolkswagenRemessa500VO;
/*    */ import java.io.File;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class VolkswagenRemessa500Service
/*    */   extends AbstractService<VolkswagenRemessa500VO>
/*    */ {
/* 20 */   private String SEQ_BVW_PGF500_REM_HDR_TRLLR = "SEQ_BVW_PGF500_REM_HDR_TRLLR";
/*    */   
/* 22 */   private String SQL_INSERT_BVW_PGF500_REM_HDR_TRLLR = "INSERT INTO BVW_PGF500_REM_HDR_TRLLR (COD_PGF500_REM_HDR_TRLLR,COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_COMUNICACAO_HDR, TPO_INSCRICAO, NRO_DOCUMENTO, NME_EMPRESA\t,TPO_SERVICO_HDR, COD_ORIGEM_ARQUIVO_HDR , NRO_REMESSA_HDR, DTA_GRAVACAO_ARQUIVO_HDR, NRO_LIST_ARQU, NRO_SEQUENCIAL_REGISTROS_HDR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?) ";
/*    */ 
/*    */   
/* 25 */   private String SQL_INSERT_ROADCARD500_REM_DTLH = "INSERT INTO BVW_PGF500_REM_DTLH (COD_PGF500_REM_DTLH, COD_PGF500_REM_HDR_TRLLR, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, TIP_INSCRICAO, NRO_DOCUMENTO, NME_FORNECEDOR, ENDERECO_FORNECEDOR, COD_CEP_FORNECEDOR, COD_BANCO_FORNECEDOR, COD_AGENCIA_FORNECEDOR, DIG_AGENCIA_FORNECEDOR, NRO_CCORRENTE_FORNECEDOR,DIG_CCORRENTE_FORNECEDOR,NRO_PGTO, CARTEIRA, NSO_NUMERO, SEU_NUMERO, DTA_VENCIMENTO, DTA_EMI_DCTO, DTA_LMT_DSCT, FTR_VENCIMENTO, VLR_DCTO, VLR_PAGTO, VLR_DSCT, VLR_ACMO, TPO_DCTO, NRO_NOT_FISCAL, MOD_PGTO, DTA_EFT_PGTO, COD_MOEDA, SIT_AGTO, TPO_MVTO, COD_MVTO, HRA_CONS_SALDO, VLR_SALDO, VLR_TAX_FUND, NME_SAC_AVAL, INF_COMPL, COD_AREA_EMP, USO_EMPRESA, COD_LANCAMENTO, TPO_CONTA, CONTA_COMPL, NRO_SEQUENCIAL, SER_CODTO, NRO_UNICO) VALUES (SEQ_BVW_PGF500_REM_DTLH.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SEQ_NRO_UNICO.NEXTVAL)";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   private String SQL_UPDATE_BVW_PGF500_REM_HDR_TRLLR = "UPDATE BVW_PGF500_REM_HDR_TRLLR SET QTD_REGISTROS_TRLLR = ?, VLR_TOTAL_PAGAMENTOS_TRLLR = ?, NRO_SEQUENCIAL_REGISTRO_TRLLR = ? WHERE COD_PGF500_REM_HDR_TRLLR = ? ";
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/* 38 */     this.dataFileName = fileName;
/* 39 */     this.dataFilePath = fileDir;
/* 40 */     this.empid = empid;
/* 41 */     this.codArquivo = codArquivo;
/* 42 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/* 43 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
/*    */   public void process(VolkswagenRemessa500VO registro, int codigoArquivo, int empId) throws SQLException {
/* 50 */     Long codHeader = this.baseDAO.getSequenceID(this.SEQ_BVW_PGF500_REM_HDR_TRLLR);
/* 51 */     Header header = registro.getHeader();
/* 52 */     this.baseDAO.merge(this.SQL_INSERT_BVW_PGF500_REM_HDR_TRLLR, new Object[] { codHeader, Integer.valueOf(codigoArquivo), Integer.valueOf(header.getLinhaProcessada()), Integer.valueOf(empId), header.getCodigoComunicacao(), header.getTipoInscricao(), header.getNroDocumento(), header.getNmeEmpresa(), header.getTipoServico(), header.getCodigoOrigemArquivo(), header.getNumeroRemessa(), header.getDataHoraArquivo(), header.getNumeroListaDebito(), header.getNumeroSequencial() });
/*    */ 
/*    */     
/* 55 */     List<Detalhe> listaDetalhe = registro.getListaDetalhe();
/* 56 */     for (Detalhe det : listaDetalhe) {
/*    */       
/* 58 */       this.baseDAO.merge(this.SQL_INSERT_ROADCARD500_REM_DTLH, new Object[] { codHeader, Integer.valueOf(codigoArquivo), Integer.valueOf(det.getLinhaProcessada()), Integer.valueOf(empId), det.getTipoInscricao(), det.getNroDocumento(), det.getNomeFornecedor(), det.getEnderecoFornecedor(), det.getCepFornecedor(), det.getCodigoBancoFornecedor(), det.getAgenciaFornecedor(), det.getDigitoFornecedor(), det.getContaCorrenteFornecedor(), det.getDigitoContaCorrenteFornecedor(), det.getNumeroPagamento(), det.getCarteira(), det.getNossoNumero(), det.getSeuNumero(), det.getDataVencimento(), det.getDataEmissaoDocumento(), det.getDataLimiteDesconto(), det.getFatorVencimento(), det.getValorDocumento(), det.getValorPagamento(), det.getValorDesconto(), det.getValorAcrescimo(), det.getTipoDocumento(), det.getNumeroNotaFiscal(), det.getModalidadePagamento(), det.getDataEfetivacao(), det.getMoeda(), det.getSituacaoAgendamento(), det.getTipoMovimento(), det.getCodigoMovimento(), det.getHorarioConsultaSaldo(), det.getSaldoDisponivelSaldo(), det.getValorTaxaPreFunding(), det.getSacadorAvalista(), det.getInformacoesComplementares(), det.getCodigoAreaEmpresa(), det.getCampoUsoEmpresa(), det.getCodigoLancamento(), det.getTipoContaFornecedor(), det.getContaComplementar(), det.getNumeroSequencial(), det.getSerieDocumento() });
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 65 */     Trailler trailler = registro.getTrailler();
/* 66 */     this.baseDAO.merge(this.SQL_UPDATE_BVW_PGF500_REM_HDR_TRLLR, new Object[] { trailler.getQtdeRegistros(), trailler.getTotalValoresPagamentos(), trailler.getNumeroSequencial(), codHeader });
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\volkswagen\VolkswagenRemessa500Service.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */