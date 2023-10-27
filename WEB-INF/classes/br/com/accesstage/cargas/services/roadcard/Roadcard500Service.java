/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.roadcard;
/*    */ 
/*    */ import br.com.accesstage.cargas.services.AbstractService;
/*    */ import br.com.accesstage.loader.util.constantes.carga.DateUtils;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout500.Detalhe;
/*    */ import br.com.accesstage.loader.util.vo.cargas.roadcard.layout500.RoadcardVO;
/*    */ import java.io.File;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class Roadcard500Service
/*    */   extends AbstractService<RoadcardVO>
/*    */ {
/*    */   private static final short HEADER = 0;
/*    */   private static final short DETAIL = 1;
/*    */   private static final short TRAILLER = 9;
/* 22 */   private String SEQ_COD_ROADCARD500RET_HDR = "SEQ_COD_ROADCARD500RET_HDR";
/*    */   
/* 24 */   private String SQL_INSERT_ROADCARD500_RET_HDR_TRLLR = "Insert into ROADCARD500_RET_HDR_TRLLR (COD_ROADCARD_RET_HDR_TRLLR,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,COD_REGISTRO_HDR,COD_COMUNICACAO_HDR,TIP_INSC_EMP_PAGADORA_HDR,NRO_CNPJ_EMP_HDR,NME_EMPRESA_PAGADORA_HDR,TIP_SERVICO_HDR,COD_ORIGEM_ARQUIVO_HDR,NRO_REMESSA_HDR,NRO_RETORNO_HDR,DTA_GRAVACAO_ARQUIVO_HDR,HRA_GRAVACAO_ARQUIVO_HDR,DSC_DENSIDADE_GRAVACAO_ARQ_HRD,UNI_DENS_GRAVACAO_HDR,DSC_IDEN_MODULO_MICRO_HDR,TIP_PROCESSAMENTO_HDR,DSC_RESERVADO_EMPRESA_HDR,DSC_RESERVADO_BANCO_HDR,DSC_RESERVADO_BANCO1_HDR,NRO_LISTA_DEB,DSC_RESERVADO_BANCO2_HDR,NRO_SEQUENCIAL_REGISTROS_HDR) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   private String SQL_INSERT_ROADCARD500_RET_HDR_TRLLR_UPDATE = "Update ROADCARD500_RET_HDR_TRLLR SET COD_REGISTRO_TRLLR = ?, QTD_REGISTROS_TRLLR = ?, VLR_TOTAL_PAGAMENTOS_TRLLR = ?, DSC_RESERVADO_TRLLR= ? , NRO_SEQUENCIAL_REGISTRO_TRLLR = ? WHERE COD_ROADCARD_RET_HDR_TRLLR = ? ";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   private String SQL_INSERT_ROADCARD500_RET_DTLH = "Insert into ROADCARD500_RET_DTLH (COD_ROADCARD500_RET_DTLH,COD_ROADCARD_RET_HDR_TRLLR,COD_ARQUIVO,NRO_LINHA_ARQ,EMPID,COD_MES_REF_CARGA,TIP_INSC_FORNECEDOR,NRO_CNPJ_FORNECEDOR,NRO_CNPJ_FILIAL,NRO_CNPJ_CONTROLE,NME_FORNECEDOR,DSC_END_FORNECEDOR,DSC_CEP_FORNECEDOR,DSC_CEP_COMPLEMENTO,COD_BANCO_FORNECEDOR,COD_AGENCIA_FORNECEDOR,DIG_AGENCIA_FORNECEDOR,NRO_CCORRENTE_FORNECEDOR,DIG_CCORRENTE_FORNECEDOR,NRO_PAGAMENTO,NRO_CARTEIRA,DSC_NOSSO_NUMERO,DSC_SEU_NUMERO,DTA_VENCIMENTO,DTA_EMISSAO_DOCTO,DTA_LIM_DESCONTO,NRO_ZERO,DSC_FATOR_VENC,VLR_DOCTO,VLR_PAGAMENTO,VLR_DESCONTO,VLR_ACRESCIMO,TIP_DOCTO,NRO_NOTA_FISCAL,NRO_SERIE_DOCTO,NRO_MODALIDADE_PGTO,DTA_EFETIVACAO_PGTO,DSC_MOEDA,SIT_AGENDAMENTO,DSC_INF_RETORNO1,DSC_INF_RETORNO2,DSC_INF_RETORNO3,DSC_INF_RETORNO4,DSC_INF_RETORNO5,TIP_MOVIMENTO,COD_MOVIMENTO,HRA_CONS_SALDO,VLR_SALDO_DISP_CONS,VLR_TX_PRE_FUNGING,DSC_RESERVA_BANCO,DSC_SACADOR_AVALISTA,DSC_RESERVADO,NVL_INF_RETORNO,INF_COMPLEMENTARES,COD_AREA_EMP,DSC_USO_EMP,DSC_RESERVADO1,COD_LANCAMENTO,DSC_RESERVADO2,TIP_CONTA_FORNEC,DSC_CONTA_COMPLEMENTAR,DSC_RESERVADO3,NRO_SEQUENCIAL_REGISTRO, COD_REGISTO_DTLH) values (SEQ_COD_PGF500_RET_DTLH.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/* 44 */     this.dataFileName = fileName;
/* 45 */     this.dataFilePath = fileDir;
/* 46 */     this.empid = empid;
/* 47 */     this.codArquivo = codArquivo;
/* 48 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/* 49 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
/*    */   public void process(RoadcardVO roadcardVO, int codigoArquivo, int empId) throws SQLException {
/* 55 */     Long codHeader = this.baseDAO.getSequenceID(this.SEQ_COD_ROADCARD500RET_HDR);
/* 56 */     String codMesRefCarga = DateUtils.codMesRefCarga();
/* 57 */     this.baseDAO.merge(this.SQL_INSERT_ROADCARD500_RET_HDR_TRLLR, new Object[] { codHeader, Integer.valueOf(codigoArquivo), Integer.valueOf(roadcardVO.getHeader().getLinhaProcessada()), Integer.valueOf(empId), codMesRefCarga, Short.valueOf((short)0), Long.valueOf(roadcardVO.getHeader().getCodComunicacao()), Long.valueOf(roadcardVO.getHeader().getTpInscEmpPagadora()), Long.valueOf(roadcardVO.getHeader().getCnpjPagadora()), roadcardVO.getHeader().getNomeEmpPagadora(), Long.valueOf(roadcardVO.getHeader().getTpServicos()), Long.valueOf(roadcardVO.getHeader().getCodOrigemArquivo()), Long.valueOf(roadcardVO.getHeader().getNumeroRemessa()), roadcardVO.getHeader().getNumeroRetorno(), roadcardVO.getHeader().getDataHoraGrv(), Long.valueOf(roadcardVO.getHeader().getHoraGravacao()), roadcardVO.getHeader().getDensidade(), roadcardVO.getHeader().getUnidadeGravacao(), roadcardVO.getHeader().getIdenModuloMicro(), Long.valueOf(roadcardVO.getHeader().getTipProcessamento()), roadcardVO.getHeader().getReservadoEmp(), roadcardVO.getHeader().getReservadoBanco(), roadcardVO.getHeader().getReservadoBanco1(), roadcardVO.getHeader().getNroListaDeb(), roadcardVO.getHeader().getReservadoBanco2(), roadcardVO.getHeader().getNroSequencial() });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 67 */     for (Detalhe detalhe : roadcardVO.getListaDetalhe()) {
/* 68 */       this.baseDAO.merge(this.SQL_INSERT_ROADCARD500_RET_DTLH, new Object[] { codHeader, Integer.valueOf(codigoArquivo), Integer.valueOf(detalhe.getLinhaProcessada()), Integer.valueOf(empId), codMesRefCarga, detalhe.getTpInscFornedor(), detalhe.getCnpjCpfFornecedor(), detalhe.getFilialCnpj(), detalhe.getCpnjControle(), detalhe.getNomeFornecedor(), detalhe.getEnderecoFornecedor(), detalhe.getCepFornecedor(), detalhe.getCepComplemento(), detalhe.getCodBancoFornecedor(), detalhe.getCodAgenciaFornecedor(), detalhe.getDigitoAgenciaFornecedor(), detalhe.getContaCorrenteFornecedor(), detalhe.getDigitoContaCorrenteFornecedor(), detalhe.getNumeroPagamento(), detalhe.getCarteira(), detalhe.getNossoNumero(), detalhe.getSeuNumero(), detalhe.getDtaVenc(), detalhe.getDtaEmissaoDoc(), detalhe.getDtaLimDesc(), detalhe.getZeros(), detalhe.getFatorVenc(), detalhe.getValorDocumento(), detalhe.getValorPagamento(), detalhe.getValorDesconto(), detalhe.getValorAcrescimo(), detalhe.getTpDocumento(), detalhe.getNumNotaFiscalFaturaDuplicata(), detalhe.getSerieDocumento(), detalhe.getModalidadePagamento(), detalhe.getDtaEfePgto(), detalhe.getMoeda(), detalhe.getSituacaoAgendamento(), detalhe.getInfRetorno1(), detalhe.getInfRetorno2(), detalhe.getInfRetorno3(), detalhe.getInfRetorno4(), detalhe.getInfRetorno5(), detalhe.getTpMovimento(), detalhe.getCodMovimento(), detalhe.getHorarioConsultaSaldo(), detalhe.getSaldoDispHraConsulta(), detalhe.getValorTxPreFunding(), detalhe.getReservaBanco(), detalhe.getSacadorAvalista(), detalhe.getReserva(), detalhe.getNvlInfRetorno(), detalhe.getInfoComplementares(), detalhe.getCodAreaEmp(), detalhe.getCampoUsoEmp(), detalhe.getReserva1(), detalhe.getCodLancamento(), detalhe.getReserva2(), detalhe.getTpContaFornecedor(), detalhe.getContaComplementar(), detalhe.getReserva3(), detalhe.getNumSeqRegistro(), Short.valueOf((short)1) });
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     this.baseDAO.merge(this.SQL_INSERT_ROADCARD500_RET_HDR_TRLLR_UPDATE, new Object[] { Short.valueOf((short)9), roadcardVO.getTrailler().getQtdRegistros(), roadcardVO.getTrailler().getTotalValoresPagamento(), roadcardVO.getTrailler().getBrancos(), roadcardVO.getTrailler().getNumeroSequencialTrailer(), codHeader });
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\roadcard\Roadcard500Service.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */