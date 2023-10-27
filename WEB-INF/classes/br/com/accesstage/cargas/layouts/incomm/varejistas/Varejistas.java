/*     */ package WEB-INF.classes.br.com.accesstage.cargas.layouts.incomm.varejistas;
/*     */ 
/*     */ import br.com.accesstage.loader.util.accmon.AlertAccMon;
/*     */ import br.com.accesstage.loader.util.commom.AbstractLayout;
/*     */ import br.com.accesstage.loader.util.constantes.carga.DateUtils;
/*     */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*     */ import br.com.accesstage.loader.util.exception.LayoutException;
/*     */ import br.com.accesstage.loader.util.vo.cargas.VarejistaVO;
/*     */ import java.io.Serializable;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ @Component
/*     */ public class Varejistas
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1639112642933971309L;
/*  29 */   private static Logger logger = Logger.getLogger("[ASCargasIncommVarejista] - ");
/*     */   
/*     */   private AbstractLayout layout;
/*     */   
/*     */   private String nmeArqFat;
/*     */   
/*     */   private String cpnjVarej;
/*     */   
/*     */   private AlertAccMon accMon;
/*     */   @Autowired
/*     */   private BaseDAO baseDAO;
/*     */   
/*     */   public Varejistas() {}
/*     */   
/*     */   public Varejistas(AbstractLayout layout) {
/*  44 */     this.layout = layout;
/*  45 */     this.accMon = new AlertAccMon(this.layout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] subStr(String linha) {
/*  55 */     String[] campos = linha.split("\\;");
/*  56 */     return campos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processaLinha(String linha) throws LayoutException {
/*  67 */     if (!linha.contains("\"Name_Arq_Fat\"")) {
/*  68 */       logger.info("Valor da Linha Processada   : " + linha);
/*  69 */       String[] campos = subStr(linha);
/*  70 */       this.nmeArqFat = campos[0].replaceAll("\"", "");
/*  71 */       logger.info("Name File Bill: " + this.nmeArqFat);
/*  72 */       this.cpnjVarej = campos[8].replaceAll("\"", "");
/*  73 */       logger.info("CNPJ Varejista: " + this.cpnjVarej);
/*     */       
/*  75 */       ResultSet rs = null;
/*  76 */       PreparedStatement pstmt = null;
/*     */       try {
/*  78 */         pstmt = this.baseDAO.preparedStatement("SELECT * FROM INCOMM_VAREJISTA WHERE NME_ARQUIVO_FAT = ? AND DSC_CPF_CNPJ_VAREJISTA =  ?");
/*  79 */         pstmt.setString(1, this.nmeArqFat);
/*  80 */         pstmt.setString(2, this.cpnjVarej);
/*  81 */         pstmt.execute();
/*  82 */         rs = pstmt.getResultSet();
/*     */         
/*  84 */         if (rs.next()) {
/*  85 */           processUpdateVarejistas(linha);
/*     */         } else {
/*  87 */           processaInsertVarejistas(linha);
/*     */         } 
/*  89 */       } catch (SQLException sqle) {
/*  90 */         this.accMon.alertaAccMon(sqle.getMessage());
/*  91 */         throw new LayoutException("[ASCargasIncommVarejista] - Nao foi possivel efetuar a busca na tabela INCOMM_VAREJISTAS: " + sqle);
/*     */       } finally {
/*     */         try {
/*  94 */           rs.close();
/*  95 */           pstmt.close();
/*  96 */           this.baseDAO.close();
/*  97 */         } catch (SQLException sqle2) {
/*  98 */           this.accMon.alertaAccMon(sqle2.getMessage());
/*  99 */           throw new LayoutException("[ASCargasIncommVarejista] - Nao foi possivel fechar conexao na busca da tabela INCOMM_VAREJISTAS: " + sqle2);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 103 */       logger.warn("LINHA CABECALHO: " + linha);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processUpdateVarejistas(String linha) throws LayoutException {
/* 114 */     logger.info("Entrei no Update Varejistas...........");
/* 115 */     VarejistaVO varejVo = getVarejistaVO(linha);
/* 116 */     updateVarejistas(varejVo);
/* 117 */     logger.info("Update-Carga de Varejistas OK: " + varejVo.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processaInsertVarejistas(String linha) throws LayoutException {
/* 127 */     logger.info("Entrei no Insert de Varejistas..........");
/* 128 */     VarejistaVO varejVo = getVarejistaVO(linha);
/* 129 */     insertVarejistas(varejVo);
/*     */     
/* 131 */     logger.info("Insert-Carga de Varejistas: " + varejVo.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private VarejistaVO getVarejistaVO(String linha) throws LayoutException {
/* 141 */     VarejistaVO varej = new VarejistaVO();
/* 142 */     String[] campos = subStr(linha);
/*     */     
/*     */     try {
/* 145 */       varej.setDscNomeArquivoFat(this.nmeArqFat);
/* 146 */       varej.setDscNomeLegal(campos[1].replaceAll("\"", ""));
/* 147 */       varej.setDscTipoFatura(campos[2].replaceAll("\"", ""));
/* 148 */       varej.setDscLocalizacao(campos[3].replaceAll("\"", ""));
/* 149 */       varej.setDscTpoCobranca(campos[4].replaceAll("\"", ""));
/* 150 */       varej.setDscPrazoPagamento(Long.valueOf(Long.parseLong(campos[5])));
/* 151 */       varej.setDscEmail(campos[6].replaceAll("\"", ""));
/* 152 */       varej.setTpoIncricaoVarejista(campos[7]);
/* 153 */       varej.setNroInscricaoVarejista(this.cpnjVarej);
/* 154 */       varej.setDscRazaoSocialVarejista(campos[9].replaceAll("\"", ""));
/* 155 */       varej.setDscEndereco(campos[10].replaceAll("\"", ""));
/* 156 */       varej.setDscBairro(campos[11].replaceAll("\"", ""));
/* 157 */       varej.setDscCep(campos[12].replaceAll("\"", ""));
/* 158 */       varej.setDscCidade(campos[13].replaceAll("\"", ""));
/* 159 */       varej.setDscUnidadeFederativa(campos[14].replaceAll("\"", ""));
/* 160 */     } catch (Exception e) {
/* 161 */       this.accMon.alertaAccMon(e.getMessage());
/* 162 */       throw new LayoutException("Ocorreu um erro ao obter os dados do Varejistas: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 165 */     return varej;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void insertVarejistas(VarejistaVO varejVo) throws LayoutException {
/* 175 */     Long pkVarej = null;
/*     */     try {
/* 177 */       pkVarej = this.baseDAO.getSequenceID("SEQ_COD_INCOMM_VAREJISTA");
/* 178 */       logger.info("Valor Sequence Varejista: " + pkVarej);
/* 179 */     } catch (SQLException sqle) {
/* 180 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 181 */       throw new LayoutException("Erro ao incrementar sequence varejista: SELECT * FROM INCOMM_VAREJISTA WHERE NME_ARQUIVO_FAT = ? AND DSC_CPF_CNPJ_VAREJISTA =  ?", sqle);
/*     */     } 
/*     */     
/*     */     try {
/* 185 */       this.baseDAO.merge("INSERT INTO INCOMM_VAREJISTA (COD_INCOMM_VAREJISTA, COD_ARQUIVO, NRO_LINHA_ARQ, EMPID, COD_MES_REF_CARGA, NME_ARQUIVO_FAT, DSC_LEGAL_NAME,DSC_TIPO_FATURA, DSC_LOCATION, DSC_TPO_COBRANCA, NRO_PRAZO_PAGAMENTO, DSC_CPF_CNPJ_VAREJISTA, DSC_RAZAO_SOCIAL_VAREJISTA, TPO_INSCRICAO_VAREJISTA, DSC_ENDERECO, DSC_BAIRRO, DSC_CEP, DSC_CIDADE,SGL_UF, DSC_EMAIL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { pkVarej, Integer.valueOf(this.layout.getCodArquivo()), Integer.valueOf(this.layout.getLinhaProcessada()), Integer.valueOf(this.layout.getEmpid()), DateUtils.codMesRefCarga(), varejVo.getDscNomeArquivoFat(), varejVo.getDscNomeLegal(), varejVo.getDscTipoFatura(), varejVo.getDscLocalizacao(), varejVo.getDscTpoCobranca(), varejVo.getDscPrazoPagamento(), varejVo.getNroInscricaoVarejista(), varejVo.getDscRazaoSocialVarejista(), varejVo.getTpoIncricaoVarejista(), varejVo.getDscEndereco(), varejVo.getDscBairro(), varejVo.getDscCep(), varejVo.getDscCidade(), varejVo.getDscUnidadeFederativa(), varejVo.getDscEmail() });
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
/* 206 */     catch (SQLException sqle) {
/* 207 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 208 */       throw new LayoutException("Erro ao inserir Varejistas: " + varejVo.toString(), sqle);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateVarejistas(VarejistaVO varejVo) throws LayoutException {
/*     */     try {
/* 220 */       this.baseDAO.merge("UPDATE INCOMM_VAREJISTA SET COD_ARQUIVO = ?, NRO_LINHA_ARQ = ?, EMPID = ?, COD_MES_REF_CARGA = ?, NME_ARQUIVO_FAT = ?, DSC_LEGAL_NAME = ?, DSC_TIPO_FATURA = ?, DSC_LOCATION = ?, DSC_TPO_COBRANCA = ?, NRO_PRAZO_PAGAMENTO = ?, DSC_CPF_CNPJ_VAREJISTA = ?, DSC_RAZAO_SOCIAL_VAREJISTA = ?, TPO_INSCRICAO_VAREJISTA = ?, DSC_ENDERECO = ?, DSC_BAIRRO = ?, DSC_CEP = ?, DSC_CIDADE = ?, SGL_UF = ?, DSC_EMAIL = ?, DTA_CADASTRO = SYSDATE WHERE NME_ARQUIVO_FAT = ? AND DSC_CPF_CNPJ_VAREJISTA =  ?", new Object[] { Integer.valueOf(this.layout.getCodArquivo()), Integer.valueOf(this.layout.getLinhaProcessada()), Integer.valueOf(this.layout.getEmpid()), DateUtils.codMesRefCarga(), varejVo.getDscNomeArquivoFat(), varejVo.getDscNomeLegal(), varejVo.getDscTipoFatura(), varejVo.getDscLocalizacao(), varejVo.getDscTpoCobranca(), varejVo.getDscPrazoPagamento(), varejVo.getNroInscricaoVarejista(), varejVo.getDscRazaoSocialVarejista(), varejVo.getTpoIncricaoVarejista(), varejVo.getDscEndereco(), varejVo.getDscBairro(), varejVo.getDscCep(), varejVo.getDscCidade(), varejVo.getDscUnidadeFederativa(), varejVo.getDscEmail(), this.nmeArqFat, this.cpnjVarej });
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
/* 242 */     catch (SQLException sqle) {
/* 243 */       this.accMon.alertaAccMon(sqle.getMessage());
/* 244 */       throw new LayoutException("Erro ao atualizar Varejistas: " + varejVo.toString(), sqle);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\layouts\incomm\varejistas\Varejistas.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */