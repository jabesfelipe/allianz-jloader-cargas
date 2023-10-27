/*    */ package WEB-INF.classes.br.com.accesstage.cargas.services.allianz;
/*    */ 
/*    */ import br.com.accesstage.cargas.services.AbstractService;
/*    */ import br.com.accesstage.loader.util.constantes.carga.DateUtils;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.AllianzDebitoVO;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.DetalheE;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.Header;
/*    */ import br.com.accesstage.loader.util.vo.cargas.allianz.debito.Trailler;
/*    */ import java.io.File;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.collections4.CollectionUtils;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class AllianzDebitoRemessaServico
/*    */   extends AbstractService<AllianzDebitoVO>
/*    */ {
/* 22 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.services.allianz.AllianzDebitoRemessaServico.class);
/*    */   
/* 24 */   public String SEQ_DEB_HDR = "SEQ_DEB_HDR";
/*    */   
/* 26 */   public String SEQ_NUM_UNICO = "SEQ_NUM_UNICO";
/*    */   
/* 28 */   public String SEQ_DEB_TRAILLER = "SEQ_DEB_TRAILLER";
/*    */   
/* 30 */   public String INSERT_FB150_HEADER = "INSERT INTO ALZ_FB150_HEADER(COD_ARQUIVO, COD_FB150_HEADER, NRO_UNICO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, CREATED, COD_REGISTRO,COD_REMESSA, COD_CONVENIO, NME_EMPRESA, COD_BANCO, NME_BANCO, DTA_GERACAO, NRO_NSA, NRO_VERSAO_LAYOUT, IDT_SERVICO, TXT_RESERVADO_FUTURO) VALUES(?, ?, SEQ_NUM_UNICO.NEXTVAL, ?, ?, ?, SYSDATE, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*    */ 
/*    */ 
/*    */   
/* 34 */   public String INSERT_FB150_TRAILLER = "INSERT INTO ALZ_FB150_TRAILLER(COD_ARQUIVO, COD_FB150_HEADER, NRO_UNICO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, CREATED, COD_REGISTRO,QTD_TOTAL_REGISTRO, VLR_TOTAL_REGISTRO, TXT_RESERVADO) VALUES (?, ?, SEQ_NUM_UNICO.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?, ?, ?)";
/*    */ 
/*    */   
/* 37 */   public String INSERT_FB150_DETAIL_E = "INSERT INTO ALZ_FB150_E(COD_ARQUIVO, COD_FB150_HEADER, COD_FB150_E, NRO_UNICO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, CREATED, COD_REGISTRO, IDT_CLIENTE_EMPRESA, NRO_AGENCIA_DEBITO, IDT_CLIENTE_BANCO, DTA_VENCIMENTO, VLR_DEBITO, COD_MOEDA, USO_EMPRESA, TPO_IDENTIFICACAO, NRO_IDENTIFICACAO, TXT_RESERVADO_FUTURO, COD_MOVIMENTO) VALUES(?, ?, SEQ_NUM_UNICO.NEXTVAL, SEQ_NUM_UNICO.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(String fileName, String fileDir, int codArquivo, int empid) {
/* 43 */     this.dataFileName = fileName;
/* 44 */     this.dataFilePath = fileDir;
/* 45 */     this.empid = empid;
/* 46 */     this.codArquivo = codArquivo;
/* 47 */     this.workFile = new File(this.dataFilePath, this.dataFileName + ".work");
/* 48 */     this.fileToProcess = new File(this.dataFilePath, this.dataFileName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(AllianzDebitoVO registro, int codigoArquivo, int empId) throws SQLException {
/* 54 */     int totalRegistros = 1;
/*    */     
/* 56 */     Long codHeader = this.baseDAO.getSequenceID(this.SEQ_DEB_HDR);
/*    */     
/* 58 */     String codMesRefCarga = DateUtils.codMesRefCarga();
/*    */ 
/*    */     
/* 61 */     Header header = registro.getHeader();
/* 62 */     this.baseDAO.merge(this.INSERT_FB150_HEADER, new Object[] { Integer.valueOf(codigoArquivo), codHeader, Integer.valueOf(totalRegistros), Integer.valueOf(empId), codMesRefCarga, header.getCodRegistro(), header.getCodRemessa(), header.getCodConvenio(), header.getNmeEmpresa(), header.getNroBanco(), header.getNmeBanco(), header.getDtaGeracao(), header.getNroNSA(), header.getNroVersaoLayout(), header.getIdentServico(), header.getUsoFuturo() });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 68 */     int contador = 0;
/* 69 */     StringBuffer strQuery = new StringBuffer();
/* 70 */     strQuery.append("INSERT INTO ALZ_FB150_E(COD_ARQUIVO, COD_FB150_HEADER, COD_FB150_E, NRO_UNICO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, CREATED, COD_REGISTRO, ");
/* 71 */     strQuery.append("IDT_CLIENTE_EMPRESA, NRO_AGENCIA_DEBITO, IDT_CLIENTE_BANCO, DTA_VENCIMENTO, VLR_DEBITO, COD_MOEDA, USO_EMPRESA, TPO_IDENTIFICACAO, NRO_IDENTIFICACAO, ");
/* 72 */     strQuery.append("TXT_RESERVADO_FUTURO, COD_MOVIMENTO) VALUES(?, ?, SEQ_NUM_UNICO.NEXTVAL, SEQ_NUM_UNICO.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
/* 73 */     List<DetalheE> listaDetalhe = registro.getListDetalheE();
/* 74 */     List<Object[]> list = new ArrayList();
/* 75 */     for (int i = 0; i < listaDetalhe.size(); i++) {
/* 76 */       DetalheE detalheE = listaDetalhe.get(i);
/* 77 */       totalRegistros++;
/* 78 */       Object[] valuesDetalhe = { Integer.valueOf(codigoArquivo), codHeader, Integer.valueOf(totalRegistros), Integer.valueOf(empId), codMesRefCarga, detalheE.getCodRegistro(), detalheE.getIdentClienteEmpresa(), detalheE.getAgenciaDebito(), detalheE.getIdentClienteBanco(), detalheE.getDataVencimento(), detalheE.getValorDebito(), detalheE.getCodigoMoeda(), detalheE.getUsoEmpresa(), detalheE.getTipoIdentificacao(), detalheE.getIdentificacao(), detalheE.getUsoFuturo(), detalheE.getCodMovimento() };
/*    */ 
/*    */       
/* 81 */       list.add(valuesDetalhe);
/* 82 */       if (contador == 1000) {
/* 83 */         this.baseDAO.updateBatch(list, strQuery.toString());
/*    */         try {
/* 85 */           Thread.sleep(10L);
/* 86 */         } catch (InterruptedException e) {
/* 87 */           logger.error(e);
/*    */         } 
/* 89 */         list = new ArrayList();
/* 90 */         contador = 0;
/*    */       } 
/*    */     } 
/* 93 */     if (CollectionUtils.isNotEmpty(list)) {
/* 94 */       this.baseDAO.updateBatch(list, strQuery.toString());
/*    */     }
/*    */ 
/*    */     
/* 98 */     Trailler trailler = registro.getTrailler();
/* 99 */     this.baseDAO.merge(this.INSERT_FB150_TRAILLER, new Object[] { Integer.valueOf(codigoArquivo), codHeader, Integer.valueOf(totalRegistros++), Integer.valueOf(empId), codMesRefCarga, trailler.getCodRegistro(), trailler.getTotalRegistrosDebito(), trailler.getValorTotalRegistrosDebito(), trailler.getUsoFuturo() });
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\services\allianz\AllianzDebitoRemessaServico.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */