/*     */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.incomm.faturas;
/*     */ 
/*     */ import br.com.accesstage.loader.util.accmon.AlertAccMon;
/*     */ import br.com.accesstage.loader.util.commom.AbstractLayout;
/*     */ import br.com.accesstage.loader.util.constantes.carga.DateUtils;
/*     */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*     */ import br.com.accesstage.loader.util.exception.LayoutException;
/*     */ import br.com.accesstage.loader.util.vo.cargas.FaturaTotalizadorVO;
/*     */ import br.com.accesstage.loader.util.vo.cargas.FaturaVO;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
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
/*     */ @Component
/*     */ public class Faturas
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   private static Logger logger = Logger.getLogger("[ASCargasIncommFatura] - ");
/*     */   
/*     */   private AbstractLayout layout;
/*     */   
/*     */   @Autowired
/*     */   private BaseDAO baseDAO;
/*     */   
/*     */   private Long pkFaturas;
/*     */   
/*     */   private Long pkFaturasTotal;
/*     */   
/*     */   private AlertAccMon accMon;
/*     */   
/*     */   private boolean gerarPkFaturasTotal = false;
/*     */   
/*     */   public Faturas() {}
/*     */   
/*     */   public Faturas(AbstractLayout layout) {
/*  50 */     this.layout = layout;
/*  51 */     this.accMon = new AlertAccMon(this.layout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] subStr(String linha) {
/*  61 */     String[] campos = linha.split("\\;");
/*  62 */     return campos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String subStrVir(String valor) {
/*  72 */     String valorSemVirgula = valor.replaceAll(",", "");
/*  73 */     return valorSemVirgula;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processaLinha(String linha) throws LayoutException {
/*  83 */     if (linha.contains("\"Country\"")) {
/*  84 */       logger.warn("Linha Cabecalho: " + linha);
/*  85 */     } else if (linha.contains("\"TOTALS:\"") || linha.contains("\"SUB TOTALS:\"")) {
/*  86 */       logger.info("Linha do Total e/ou Sub-Total: " + linha);
/*  87 */       if (linha.contains("\"SUB TOTALS:\"")) {
/*  88 */         logger.info("Valor da Linha do Sub-Total: " + linha);
/*  89 */         this.gerarPkFaturasTotal = false;
/*  90 */         processaInsertFaturasSubTotal(linha);
/*     */       } else {
/*  92 */         logger.info("Valor da Linha do Total: " + linha);
/*  93 */         processaInsertFaturasTotal(linha);
/*     */       } 
/*     */     } else {
/*  96 */       logger.info("Valor da Linha a ser Carregada: " + linha);
/*  97 */       processaInsertFaturas(linha);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processaInsertFaturas(String linha) throws LayoutException {
/* 108 */     logger.info("Entrei no Insert de Faturas....");
/* 109 */     if (!this.gerarPkFaturasTotal) {
/* 110 */       insertFaturasTotalizadores();
/*     */     }
/* 112 */     this.gerarPkFaturasTotal = true;
/* 113 */     FaturaVO fatVO = getFaturasVO(linha);
/* 114 */     insertFaturas(fatVO);
/*     */     
/* 116 */     logger.info("Insert-Carga de Faturas: " + fatVO.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processaInsertFaturasSubTotal(String linha) throws LayoutException {
/* 126 */     logger.info("Entrei no Insert de Faturas Totalizadores-SubTotal...");
/* 127 */     FaturaTotalizadorVO fatTotSubTotalVO = getFaturasSubTotalVO(linha);
/* 128 */     updateFaturasSubTotal(fatTotSubTotalVO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processaInsertFaturasTotal(String linha) throws LayoutException {
/* 139 */     logger.info("Entrei no Insert de Faturas Totalizadores-Total...");
/* 140 */     FaturaTotalizadorVO fatTotTotalVO = getFaturasTotalVO(linha);
/* 141 */     updateFaturasTotal(fatTotTotalVO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FaturaVO getFaturasVO(String linha) throws LayoutException {
/* 153 */     FaturaVO fatVO = new FaturaVO();
/* 154 */     String[] campos = subStr(linha);
/*     */     try {
/* 156 */       fatVO.setDscCountry(campos[0].replaceAll("\"", ""));
/* 157 */       fatVO.setDscLegalName(campos[1].replaceAll("\"", ""));
/* 158 */       fatVO.setDscLocation(campos[2].replaceAll("\"", ""));
/* 159 */       fatVO.setDscState(campos[3].replaceAll("\"", ""));
/* 160 */       fatVO.setDscTerminal(campos[4].replaceAll("\"", ""));
/* 161 */       fatVO.setDscProduct(campos[5].replaceAll("\"", ""));
/* 162 */       fatVO.setDscUpc(campos[6].replaceAll("\"", ""));
/* 163 */       fatVO.setDscSku(campos[7].replaceAll("\"", ""));
/* 164 */       fatVO.setDscCurrency(campos[8].replaceAll("\"", ""));
/* 165 */       fatVO.setVlrUnitCost(Double.valueOf(Double.parseDouble(campos[9])));
/* 166 */       fatVO.setDscSerialNumber(campos[10].replaceAll("\"", ""));
/* 167 */       fatVO.setDscVan16(campos[11].replaceAll("\"", ""));
/*     */       
/* 169 */       String dtaFaturaStr = campos[12].replaceAll("\"", "");
/* 170 */       dtaFaturaStr = dtaFaturaStr.replaceAll("-", "");
/* 171 */       Date dtaFaturaDt = null;
/*     */       try {
/* 173 */         dtaFaturaDt = DateUtils.parseDateFile2(dtaFaturaStr);
/* 174 */         fatVO.setDtaFatura(dtaFaturaDt);
/* 175 */       } catch (ParseException pe) {
/* 176 */         this.accMon.alertaAccMon(pe.getMessage());
/* 177 */         throw new LayoutException("Nao foi possivel efetuar o parse na data de fatura: " + dtaFaturaStr, pe);
/*     */       } 
/*     */       
/* 180 */       fatVO.setHraFatura(campos[13].replaceAll("\"", ""));
/*     */ 
/*     */       
/* 183 */       String dtaLocalStr = campos[14].replaceAll("\"", "");
/* 184 */       dtaLocalStr = dtaLocalStr.replaceAll("-", "");
/* 185 */       Date dtaLocalDt = null;
/*     */       try {
/* 187 */         dtaLocalDt = DateUtils.parseDateFile2(dtaLocalStr);
/* 188 */         fatVO.setDtaLocal(dtaLocalDt);
/* 189 */       } catch (ParseException pe) {
/* 190 */         this.accMon.alertaAccMon(pe.getMessage());
/* 191 */         throw new LayoutException("Nao foi possivel efetuar o parse na data local: " + dtaLocalStr, pe);
/*     */       } 
/*     */       
/* 194 */       fatVO.setHraLocal(campos[15].replaceAll("\"", ""));
/*     */ 
/*     */       
/* 197 */       fatVO.setDscRefNumber(campos[16].replaceAll("\"", ""));
/* 198 */       fatVO.setDscStan(campos[17].replaceAll("\"", ""));
/* 199 */       fatVO.setDscTransType(campos[18].replaceAll("\"", ""));
/* 200 */       fatVO.setQtdTransCount(Long.valueOf(Long.parseLong(campos[19])));
/* 201 */       fatVO.setVlrGross(Double.valueOf(Double.parseDouble(campos[20])));
/* 202 */       fatVO.setVlrComission(Double.valueOf(Double.parseDouble(campos[21])));
/* 203 */       fatVO.setVlrTransFee(Double.valueOf(Double.parseDouble(campos[22])));
/* 204 */       fatVO.setVlrVat(Double.valueOf(Double.parseDouble(campos[23])));
/* 205 */       fatVO.setVlrTotal(Double.valueOf(Double.parseDouble(campos[24])));
/* 206 */       fatVO.setNroNossoNumero(campos[25].replaceAll("\"", ""));
/*     */ 
/*     */       
/* 209 */       String fileName = this.layout.getFileName();
/* 210 */       String[] getArroba = fileName.split("@");
/*     */       
/* 212 */       fatVO.setNroTrkidin(Long.valueOf(Long.parseLong(getArroba[3])));
/*     */       
/* 214 */       String nmeArqFatura = getArroba[4];
/*     */       
/* 216 */       fatVO.setNmeArqFatura(this.layout.getFileName());
/*     */       
/* 218 */       String[] nroDocumento = nmeArqFatura.split("_");
/* 219 */       fatVO.setNroDocumento(nroDocumento[0]);
/* 220 */     } catch (Exception e) {
/* 221 */       this.accMon.alertaAccMon(e.getMessage());
/* 222 */       throw new LayoutException("Ocorreu um erro ao obter os dados de fatura: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 225 */     return fatVO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FaturaTotalizadorVO getFaturasSubTotalVO(String linha) throws LayoutException {
/* 235 */     FaturaTotalizadorVO fatTotSubTotalVO = new FaturaTotalizadorVO();
/* 236 */     String[] campos = subStr(linha);
/*     */ 
/*     */     
/*     */     try {
/* 240 */       fatTotSubTotalVO.setQtdTransCountSubtotal(Long.valueOf(Long.parseLong(campos[19])));
/* 241 */       fatTotSubTotalVO.setVlrGrossSubtotal(Double.valueOf(Double.parseDouble(subStrVir(campos[20]))));
/* 242 */       fatTotSubTotalVO.setVlrComissionSubtotal(Double.valueOf(Double.parseDouble(subStrVir(campos[21]))));
/* 243 */       fatTotSubTotalVO.setVlrTransfeeSubtotal(Double.valueOf(Double.parseDouble(subStrVir(campos[22]))));
/* 244 */       fatTotSubTotalVO.setVlrVatSubtotal(Double.valueOf(Double.parseDouble(subStrVir(campos[23]))));
/* 245 */       fatTotSubTotalVO.setVlrSubtotalFatura(Double.valueOf(Double.parseDouble(subStrVir(campos[24]))));
/* 246 */     } catch (Exception e) {
/* 247 */       this.accMon.alertaAccMon(e.getMessage());
/* 248 */       throw new LayoutException("Ocorreu um erro ao obter os dados de fatura totalizador sub-total: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 251 */     return fatTotSubTotalVO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FaturaTotalizadorVO getFaturasTotalVO(String linha) throws LayoutException {
/* 261 */     FaturaTotalizadorVO fatTotTotalVO = new FaturaTotalizadorVO();
/* 262 */     String[] campos = subStr(linha);
/*     */     
/*     */     try {
/* 265 */       fatTotTotalVO.setQtdTransCountTotal(Long.valueOf(Long.parseLong(campos[19])));
/* 266 */       fatTotTotalVO.setVlrGrossTotal(Double.valueOf(Double.parseDouble(subStrVir(campos[20]))));
/* 267 */       fatTotTotalVO.setVlrComissionTotal(Double.valueOf(Double.parseDouble(subStrVir(campos[21]))));
/* 268 */       fatTotTotalVO.setVlrTransfeeTotal(Double.valueOf(Double.parseDouble(subStrVir(campos[22]))));
/* 269 */       fatTotTotalVO.setVlrVatTotal(Double.valueOf(Double.parseDouble(subStrVir(campos[23]))));
/* 270 */       fatTotTotalVO.setVlrTotalFatura(Double.valueOf(Double.parseDouble(subStrVir(campos[24]))));
/* 271 */     } catch (Exception e) {
/* 272 */       this.accMon.alertaAccMon(e.getMessage());
/* 273 */       throw new LayoutException("Ocorreu um erro ao obter os dados de fatura totalizador total: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 276 */     return fatTotTotalVO;
/*     */   }
/*     */   
/*     */   private void insertFaturas(FaturaVO fatVO) throws LayoutException {
/*     */     try {
/* 281 */       this.pkFaturas = this.baseDAO.getSequenceID("SEQ_COD_INCOMM_FATURA");
/* 282 */       logger.info("Valor Sequence Faturas: " + this.pkFaturas);
/* 283 */     } catch (SQLException sqle) {
/* 284 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 285 */       throw new LayoutException("Erro ao incrementar sequence faturas: SEQ_COD_INCOMM_FATURA", sqle);
/*     */     } 
/*     */     
/*     */     try {
/* 289 */       this.baseDAO.merge("INSERT INTO INCOMM_FATURA (COD_INCOMM_FATURA, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, DSC_COUNTRY, DSC_LEGAL_NAME, DSC_LOCATION, DSC_STATE, DSC_TERMINAL, DSC_PRODUCT, DSC_UPC,DSC_SKU, DSC_CURRENCY, VLR_UNIT_COST, DSC_SERIAL_NUMBER, DSC_VAN16, DTA_FATURA, HRA_FATURA, DSC_REF_NUMBER, DSC_STAN, DSC_TRANS_TYPE, QTD_TRANS_COUNT, VLR_GROSS, VLR_COMISSION, VLR_TRANS_FEE, VLR_VAT, VLR_TOTAL, NRO_NOSSO_NUMERO, NRO_TRKIDIN, NRO_DOCUMENTO, NME_ARQ_FATURA, DTA_LOCAL, HRA_LOCAL, COD_INCOMM_FATURA_TOTALIZADOR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { this.pkFaturas, Integer.valueOf(this.layout.getCodArquivo()), Integer.valueOf(this.layout.getLinhaProcessada()), Integer.valueOf(this.layout.getEmpid()), DateUtils.codMesRefCarga(), fatVO.getDscCountry(), fatVO.getDscLegalName(), fatVO.getDscLocation(), fatVO.getDscState(), fatVO.getDscTerminal(), fatVO.getDscProduct(), fatVO.getDscUpc(), fatVO.getDscSku(), fatVO.getDscCurrency(), fatVO.getVlrUnitCost(), fatVO.getDscSerialNumber(), fatVO.getDscVan16(), fatVO.getDtaFatura(), fatVO.getHraFatura(), fatVO.getDscRefNumber(), fatVO.getDscStan(), fatVO.getDscTransType(), fatVO.getQtdTransCount(), fatVO.getVlrGross(), fatVO.getVlrComission(), fatVO.getVlrTransFee(), fatVO.getVlrVat(), fatVO.getVlrTotal(), fatVO.getNroNossoNumero(), fatVO.getNroTrkidin(), fatVO.getNroDocumento(), fatVO.getNmeArqFatura(), fatVO.getDtaLocal(), fatVO.getHraLocal(), this.pkFaturasTotal });
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
/*     */     }
/* 325 */     catch (SQLException sqle) {
/* 326 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 327 */       throw new LayoutException("Erro ao inserir Faturas: " + fatVO.toString(), sqle);
/*     */     } 
/*     */   }
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
/*     */   private void updateFaturasSubTotal(FaturaTotalizadorVO fatTotVO) throws LayoutException {
/*     */     try {
/* 347 */       this.baseDAO.merge("UPDATE INCOMM_FATURA_TOTALIZADORES SET QTD_TRANS_COUNT_SUBTOTAL = ?, VLR_GROSS_SUBTOTAL = ?, VLR_COMISSION_SUBTOTAL = ?, VLR_TRANSFEE_SUBTOTAL = ?, VLR_VAT_SUBTOTAL = ?, VLR_SUBTOTAL_FATURA = ? WHERE COD_INCOMM_FATURA_TOTALIZADOR = ? AND COD_ARQUIVO = ?", new Object[] { fatTotVO.getQtdTransCountSubtotal(), fatTotVO.getVlrGrossSubtotal(), fatTotVO.getVlrComissionSubtotal(), fatTotVO.getVlrTransfeeSubtotal(), fatTotVO.getVlrVatSubtotal(), fatTotVO.getVlrSubtotalFatura(), this.pkFaturasTotal, Integer.valueOf(this.layout.getCodArquivo()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 356 */     catch (SQLException sqle) {
/* 357 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 358 */       throw new LayoutException("Erro ao inserir Faturas Totalizadores: " + fatTotVO.toString(), sqle);
/*     */     } 
/* 360 */     this.gerarPkFaturasTotal = false;
/*     */   }
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
/*     */   private void updateFaturasTotal(FaturaTotalizadorVO fatTotUpVO) throws LayoutException {
/*     */     try {
/* 379 */       this.baseDAO.merge("UPDATE INCOMM_FATURA_TOTALIZADORES SET QTD_TRANS_COUNT_TOTAL = ?, VLR_GROSS_TOTAL = ?, VLR_COMISSION_TOTAL = ?, VLR_TRANSFEE_TOTAL = ?, VLR_VAT_TOTAL = ?, VLR_TOTAL_FATURA = ? WHERE COD_INCOMM_FATURA_TOTALIZADOR = ? AND COD_ARQUIVO = ?", new Object[] { fatTotUpVO.getQtdTransCountTotal(), fatTotUpVO.getVlrGrossTotal(), fatTotUpVO.getVlrComissionTotal(), fatTotUpVO.getVlrTransfeeTotal(), fatTotUpVO.getVlrVatTotal(), fatTotUpVO.getVlrTotalFatura(), this.pkFaturasTotal, Integer.valueOf(this.layout.getCodArquivo()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 388 */     catch (SQLException sqle) {
/* 389 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 390 */       throw new LayoutException("Erro ao atualizar Faturas Totalizadores: " + fatTotUpVO.toString(), sqle);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void insertFaturasTotalizadores() throws LayoutException {
/* 400 */     logger.info("Iniciando insert na tabela INCOMM_FATURAS_TOTALIZADORES");
/*     */     try {
/* 402 */       this.pkFaturasTotal = this.baseDAO.getSequenceID("SEQ_COD_INCOMM_FATURA_TOTALIZA");
/* 403 */       logger.info("Valor Sequence Faturas: " + this.pkFaturas);
/* 404 */     } catch (SQLException sqle) {
/* 405 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 406 */       throw new LayoutException("Erro ao incrementar sequence faturas: SEQ_COD_INCOMM_FATURA", sqle);
/*     */     } 
/*     */     
/*     */     try {
/* 410 */       this.baseDAO.merge("INSERT INTO INCOMM_FATURA_TOTALIZADORES (COD_INCOMM_FATURA_TOTALIZADOR, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA) VALUES (?, ?, ?, ?, ?)", new Object[] { this.pkFaturasTotal, Integer.valueOf(this.layout.getCodArquivo()), Integer.valueOf(this.layout.getLinhaProcessada()), Integer.valueOf(this.layout.getEmpid()), DateUtils.codMesRefCarga() });
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 415 */     catch (SQLException sqle) {
/* 416 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 417 */       throw new LayoutException("Erro ao efetuar insert na tabela INCOMM_FATURA_TOTALIZADORES para o COD_INCOMM_FATURA_TOTALIZADOR: " + this.pkFaturasTotal, sqle);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\incomm\faturas\Faturas.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */