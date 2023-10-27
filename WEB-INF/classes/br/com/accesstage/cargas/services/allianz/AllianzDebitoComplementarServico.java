/*     */ package WEB-INF.classes.br.com.accesstage.cargas.services.allianz;
/*     */ 
/*     */ import br.com.accesstage.cargas.services.AbstractService;
/*     */ import br.com.accesstage.loader.util.constantes.carga.DateUtils;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.AllianzDebitoComplementarVO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Detalhe;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Header;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Trailler;
/*     */ import java.io.File;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.collections4.CollectionUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AllianzDebitoComplementarServico
/*     */   extends AbstractService<AllianzDebitoComplementarVO>
/*     */ {
/*  22 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.allianz.AllianzDebitoComplementarServico.class);
/*     */   
/*  24 */   public String SEQ_DEB_COMP_HDR = "SEQ_DEB_COMP_HDR";
/*  25 */   public String SEQ_DEB_COM_TRAILLER = "SEQ_DEB_COM_TRAILLER";
/*     */   
/*  27 */   public String INSERT_FB150_HEADER_COMPL = "INSERT INTO ALZ_COMPL_HEADER(COD_ARQUIVO, COD_COMPL_HEADER, NRO_UNICO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, TPREGISTRO, EMPRESAALLIANZ, DTENVIO, HRENVIO, NSA, FILLER)  VALUES(?, ?, SEQ_NRO_UNICO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
/*     */ 
/*     */ 
/*     */   
/*  31 */   public String INSERT_FB150_HEADER_DETAIL = "INSERT INTO ALZ_COMPL_DETAIL(COD_ARQUIVO, COD_COMPL_HEADER, NRO_UNICO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, TPREGISTRO, CPFCNPJ, TPPESSOA, NMCLIENTE, ENDERECO, BAIRRO, CEP, CIDADE, ESTADO, POLIZA, APOLICE, ENDOSSO, PARCELA, PLANO, RECIBO, DTVENCIMENTO, DSPRODUTO, BANCODEBITO, AGENCIA, DVAGENCIA, CONTACORRENTE, DVCONTA, DTPREVCANCELAMENTO, EMAILCLIENTE, CELULARCLIENTE, EMAILCORRETOR, CELULARCORRETOR, CODREJEICAODEBITO, BANCOBOLETO, NOSSONUMERO, LINHADIGITAVEL, CODIGOBARRAS, FILLER,ALIQUOTAIOF, VALORBOLETO, VENCIMENTOBOLETO, TIPOCOBRANCA) VALUES(?, ?, SEQ_NRO_UNICO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public String INSERT_FB150_HEADER_TRAILLER = "INSERT INTO ALZ_COMPL_TRAILLER(COD_ARQUIVO, COD_COMPL_HEADER, NRO_UNICO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, TPREGISTRO, QTDTOTLINHAS, FILLER) VALUES(?, ?, SEQ_NRO_UNICO.NEXTVAL, ?, ?, ?, ?, ?, ?) ";
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/*  42 */     this.dataFileName = fileName;
/*  43 */     this.dataFilePath = fileDir;
/*  44 */     this.empid = empid;
/*  45 */     this.codArquivo = codArquivo;
/*  46 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/*  47 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(AllianzDebitoComplementarVO registro, int codigoArquivo, int empId) throws SQLException {
/*  53 */     int totalRegistros = 1;
/*     */     
/*  55 */     Long codHeader = this.baseDAO.getSequenceID(this.SEQ_DEB_COMP_HDR);
/*     */     
/*  57 */     String codMesRefCarga = DateUtils.codMesRefCarga();
/*     */     
/*  59 */     Header header = registro.getHeader();
/*  60 */     this.baseDAO.merge(this.INSERT_FB150_HEADER_COMPL, new Object[] { Integer.valueOf(codigoArquivo), codHeader, Integer.valueOf(totalRegistros), Integer.valueOf(empId), codMesRefCarga, header.getCodRegistro(), header.getEmpresaAllianz(), header.getDataEnvio(), header.getHoraEnvio(), header.getNsa(), header.getFiller() });
/*     */ 
/*     */     
/*  63 */     List<Detalhe> listaDetalhe = registro.getListDetalhe();
/*  64 */     List<Object[]> list = new ArrayList();
/*     */     
/*  66 */     int contador = 0;
/*  67 */     StringBuffer strQuery = new StringBuffer();
/*  68 */     strQuery.append("INSERT INTO ALZ_COMPL_DETAIL(COD_ARQUIVO, COD_COMPL_HEADER, NRO_UNICO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, TPREGISTRO, CPFCNPJ, TPPESSOA, NMCLIENTE, ENDERECO, BAIRRO, ");
/*  69 */     strQuery.append("CEP, CIDADE, ESTADO, POLIZA, APOLICE, ENDOSSO, PARCELA, PLANO, RECIBO, DTVENCIMENTO, DSPRODUTO, BANCODEBITO, AGENCIA, DVAGENCIA, CONTACORRENTE, DVCONTA, DTPREVCANCELAMENTO, EMAILCLIENTE, ");
/*  70 */     strQuery.append("CELULARCLIENTE, EMAILCORRETOR, CELULARCORRETOR, CODREJEICAODEBITO, BANCOBOLETO, NOSSONUMERO, LINHADIGITAVEL, CODIGOBARRAS, FILLER,ALIQUOTAIOF, VALORBOLETO, VENCIMENTOBOLETO, TIPOCOBRANCA) ");
/*  71 */     strQuery.append("VALUES(?, ?, SEQ_NRO_UNICO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
/*  72 */     for (int i = 0; i < listaDetalhe.size(); i++) {
/*  73 */       Detalhe detalhe = listaDetalhe.get(i);
/*  74 */       detalhe.setDsProduto(removerAcentuacao(detalhe.getDsProduto()));
/*  75 */       detalhe.setBairro(removerAcentuacao(detalhe.getBairro()));
/*  76 */       detalhe.setNomeCliente(removerAcentuacao(detalhe.getNomeCliente()));
/*  77 */       detalhe.setEndereco(removerAcentuacao(detalhe.getEndereco()));
/*  78 */       Object[] valuesDetalhe = { Integer.valueOf(codigoArquivo), codHeader, Integer.valueOf(totalRegistros), Integer.valueOf(empId), codMesRefCarga, detalhe.getCodRegistro(), detalhe.getDocumento(), detalhe.getTipoPessoa(), detalhe.getNomeCliente(), detalhe.getEndereco(), detalhe.getBairro(), detalhe.getCep(), detalhe.getCidade(), detalhe.getEstado(), detalhe.getPoliza(), detalhe.getApolice(), detalhe.getEndosso(), detalhe.getParcela(), detalhe.getPlano(), detalhe.getRecibo(), detalhe.getDataVencimento(), detalhe.getDsProduto(), detalhe.getBancoDebito(), detalhe.getAgencia(), detalhe.getAgenciaDV(), detalhe.getContaCorrente(), detalhe.getContaCorrenteDV(), detalhe.getDataPrevCancelamento(), detalhe.getEmailCliente(), detalhe.getCelularCliente(), detalhe.getEmailCorretor(), detalhe.getCelularCorretor(), detalhe.getCodRejeicaoDebito(), detalhe.getBancoBoleto(), detalhe.getNossoNumero(), detalhe.getLinhaDigitavel(), detalhe.getCodigoBarras(), detalhe.getFiller(), detalhe.getAliquotaIOF(), detalhe.getValorBoleto(), detalhe.getVencimentoBoleto(), detalhe.getTipoCobranca() };
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  83 */       list.add(valuesDetalhe);
/*  84 */       if (contador == 1000) {
/*  85 */         this.baseDAO.updateBatch(list, strQuery.toString());
/*     */         try {
/*  87 */           Thread.sleep(10L);
/*  88 */         } catch (InterruptedException e) {
/*  89 */           logger.error(e);
/*     */         } 
/*  91 */         list = new ArrayList();
/*  92 */         contador = 0;
/*     */       } 
/*     */     } 
/*  95 */     if (CollectionUtils.isNotEmpty(list)) {
/*  96 */       this.baseDAO.updateBatch(list, strQuery.toString());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     Trailler trailler = registro.getTrailler();
/* 121 */     this.baseDAO.merge(this.INSERT_FB150_HEADER_TRAILLER, new Object[] { Integer.valueOf(codigoArquivo), codHeader, Integer.valueOf(totalRegistros++), Integer.valueOf(this.empid), codMesRefCarga, trailler.getCodRegistro(), trailler.getQuantLinhas(), trailler.getFiller() });
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\allianz\AllianzDebitoComplementarServico.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */