/*     */ package WEB-INF.classes.br.com.trms.emissor.boleto.dao.impl;
/*     */ 
/*     */ import br.com.accesstage.loader.util.dao.BaseDAO;
/*     */ import br.com.trms.emissor.boleto.dao.BoletoDAO;
/*     */ import br.com.trms.emissor.boleto.dao.rowmapper.BoletoRowMapper;
/*     */ import br.com.trms.emissor.boleto.vo.Boleto;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ import javax.swing.text.MaskFormatter;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.dao.EmptyResultDataAccessException;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.jdbc.core.SqlOutParameter;
/*     */ import org.springframework.jdbc.core.SqlParameter;
/*     */ import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
/*     */ import org.springframework.jdbc.core.namedparam.SqlParameterSource;
/*     */ import org.springframework.jdbc.core.simple.SimpleJdbcCall;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository
/*     */ public class BoletoDAOImpl
/*     */   extends BaseDAO
/*     */   implements BoletoDAO
/*     */ {
/*  40 */   private Logger looger = Logger.getLogger(br.com.trms.emissor.boleto.dao.impl.BoletoDAOImpl.class);
/*     */   
/*  42 */   protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*  43 */   protected DecimalFormat formato = new DecimalFormat("#.##");
/*     */   
/*     */   @Autowired
/*     */   public BoletoDAOImpl(DataSource dataSource) {
/*  47 */     super(dataSource);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Boleto> getBoletos_P() {
/*  52 */     List<Boleto> lista = new ArrayList<>();
/*  53 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/*  54 */     call.withCatalogName("PKG_ALZ_REPORTS");
/*  55 */     call.withProcedureName("getBoletos");
/*  56 */     call.declareParameters(new SqlParameter[] { (SqlParameter)new SqlOutParameter("P_LISTA", -10) });
/*  57 */     call.returningResultSet("P_LISTA", (RowMapper)new BoletoRowMapper());
/*  58 */     MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
/*  59 */     Map<String, Object> m = call.execute((SqlParameterSource)mapSqlParameterSource);
/*  60 */     lista = (List<Boleto>)m.get("P_LISTA");
/*  61 */     return lista;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Boleto> getBoletos() {
/*  66 */     StringBuffer str = new StringBuffer();
/*  67 */     str.append("select com.* ");
/*  68 */     str.append(", com.nro_unico nroUnicoCompl ");
/*  69 */     str.append(", com.parcela_original ");
/*  70 */     str.append(", lpad(ltrim(com.poliza, '0'),9, '0') poliza_fmt ");
/*  71 */     str.append(", lpad(substr(endosso, -5), 5, '0') endosso_fmt ");
/*  72 */     str.append(", ret.nro_unico ");
/*  73 */     str.append(", ret.idt_cliente_empresa ");
/*  74 */     str.append(", ret.cod_retorno ");
/*  75 */     str.append(", hed.cod_banco ");
/*     */     
/*  77 */     str.append(", to_char(sysdate, 'ddmmyyyy') dta_processamento ");
/*  78 */     str.append(", case when to_char(sysdate, 'yyyymmdd') < com.vencimentoboleto then to_char(sysdate, 'ddmmyyyy') ");
/*  79 */     str.append("when to_char(sysdate, 'yyyymmdd') > com.vencimentoboleto then to_char(to_date(com.vencimentoboleto, 'yyyymmdd'), 'ddmmyyyy') ");
/*  80 */     str.append("when to_char(sysdate, 'yyyymmdd') = com.vencimentoboleto then to_char(sysdate, 'ddmmyyyy') ");
/*  81 */     str.append("end dta_emissao ");
/*  82 */     str.append("from alz_fb150_f      ret ");
/*  83 */     str.append(", alz_compl_detail com ");
/*  84 */     str.append(", alz_fb150_header hed ");
/*  85 */     str.append("where ( com.env_boleto       = 'GEN' ) ");
/*  86 */     str.append("and ( ret.sta_arq_cobranca = 'DONE' ) ");
/*  87 */     str.append("and ( ret.sta_reg_rejeicao = 'SIM' ) ");
/*  88 */     str.append("and ( ret.sta_reg_remessa  = 'SIM' ) ");
/*  89 */     str.append("and ( com.nro_conciliado   = ret.nro_unico ) ");
/*  90 */     str.append("and ( ret.cod_fb150_header = hed.cod_fb150_header ) ");
/*     */     
/*  92 */     List<Boleto> lista = new ArrayList<>();
/*     */ 
/*     */     
/*  95 */     Locale ptBr = new Locale("pt", "BR");
/*     */     
/*     */     try {
/*  98 */       Connection conn = getJdbcTemplate().getDataSource().getConnection();
/*  99 */       CallableStatement cs = conn.prepareCall(str.toString());
/* 100 */       ResultSet rs = cs.executeQuery();
/* 101 */       while (rs.next()) {
/* 102 */         Boleto boleto = new Boleto();
/* 103 */         boleto.setBanco(String.valueOf(rs.getString("BANCOBOLETO")) + "- 7");
/*     */         
/* 105 */         boleto.setNomeSacado(rs.getString("NMCLIENTE"));
/* 106 */         boleto.setEndereco(rs.getString("ENDERECO"));
/* 107 */         boleto.setCidade(rs.getString("CIDADE"));
/* 108 */         boleto.setCep(formatString(rs.getString("CEP"), "#####-###"));
/* 109 */         boleto.setCodigoBarras(rs.getString("CODIGOBARRAS"));
/*     */         
/* 111 */         StringBuilder linhaDigitavel = new StringBuilder(rs.getString("LINHADIGITAVEL"));
/* 112 */         formata(linhaDigitavel);
/* 113 */         boleto.setLinhaDigitavelFormatada(linhaDigitavel.toString());
/* 114 */         boleto.setLinhaDigitavel(rs.getString("LINHADIGITAVEL"));
/* 115 */         boleto.setNossoNumero(rs.getString("NOSSONUMERO"));
/* 116 */         boleto.setUf(rs.getString("ESTADO"));
/*     */         
/* 118 */         boleto.setNumeroDocumento(rs.getString("RECIBO"));
/*     */         
/* 120 */         String dataBanco = rs.getString("VENCIMENTOBOLETO");
/* 121 */         boleto.setVencimento(String.valueOf(dataBanco.substring(6, 8)) + "/" + dataBanco.substring(4, 6) + "/" + dataBanco.substring(0, 4));
/* 122 */         Double valorJuros = new Double(rs.getString("VLR_MULTA"));
/* 123 */         double valorAjustado = valorJuros.doubleValue() / 100.0D;
/* 124 */         boleto.setMulta(Double.valueOf(valorAjustado));
/* 125 */         boleto.setStrMulta(NumberFormat.getCurrencyInstance(ptBr).format(valorAjustado).replace("R$", ""));
/*     */         
/* 127 */         double valorBoleto = (new Double(rs.getString("VALORBOLETO"))).doubleValue();
/* 128 */         double valorBoletoAjustado = valorBoleto / 100.0D;
/* 129 */         boleto.setValorDocumento(Double.valueOf(valorBoletoAjustado));
/* 130 */         boleto.setStrValorDocumento(NumberFormat.getCurrencyInstance(ptBr).format(valorBoletoAjustado).replace("R$", ""));
/*     */         
/* 132 */         boleto.setAgenciaCedente(rs.getString("AGENCIA"));
/* 133 */         boleto.setCodigoCedente(String.valueOf(rs.getString("CONTACORRENTE")) + "-" + rs.getString("DVCONTA"));
/* 134 */         boleto.setLocalPagamento("PAGAR PREFERENCIALMENTE NO BANCO SANTANDER");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 140 */         boleto.setDataDocumento(rs.getString("DTA_EMISSAO"));
/* 141 */         boleto.setDataProcessamento(rs.getString("DTA_PROCESSAMENTO"));
/* 142 */         boleto.setEspecie("REAL");
/* 143 */         boleto.setEspecieDocumento("REAL");
/*     */         
/* 145 */         boleto.setCedente("ALLIANZ SEGUROS S.A.");
/* 146 */         boleto.setCarteira("COBRANCA SIMPLES ECR");
/* 147 */         String idtCliente = rs.getString("IDT_CLIENTE_EMPRESA");
/* 148 */         idtCliente = String.valueOf(idtCliente.substring(0, 4)) + "/" + idtCliente.substring(5, idtCliente.length());
/* 149 */         boleto.setIdtClienteEmpresa(idtCliente);
/*     */         
/* 151 */         String tipoPessoa = rs.getString("TPPESSOA");
/* 152 */         String documento = rs.getString("CPFCNPJ");
/* 153 */         Long doc = Long.valueOf(documento);
/* 154 */         if (tipoPessoa != null && tipoPessoa.equalsIgnoreCase("1")) {
/* 155 */           boleto.setDocumentoSacado(formatString(formatadorEsquerda(String.valueOf(doc), "0", 11), "###.###.###-##"));
/*     */         } else {
/* 157 */           boleto.setDocumentoSacado(formatString(formatadorEsquerda(String.valueOf(doc), "0", 14), "##.###.###/####-##"));
/*     */         } 
/*     */         
/* 160 */         boleto.setPoliza(formatadorEsquerda(rs.getString("POLIZA_FMT").trim(), "0", 9));
/* 161 */         boleto.setEndosso(formatadorEsquerda(rs.getString("ENDOSSO_FMT"), "0", 5));
/* 162 */         boleto.setParcela(formatadorEsquerda(rs.getString("PARCELA_ORIGINAL"), "0", 2));
/* 163 */         boleto.setNroUnico(Long.valueOf(rs.getLong("nroUnicoCompl")));
/* 164 */         lista.add(boleto);
/*     */       }
/*     */     
/* 167 */     } catch (SQLException e) {
/* 168 */       this.looger.error(e.getMessage());
/*     */     } 
/* 170 */     return lista;
/*     */   }
/*     */   
/*     */   public String geTipoCobranca(String codBanco, String codRetorno) {
/* 174 */     String tipoCobranca = "";
/* 175 */     StringBuffer str = new StringBuffer();
/* 176 */     str.append("select tpo_cobranca from alz_rejeicao rej where rej.cod_banco_debito =").append(codBanco).append(" and rej.cod_ocorrencia = ").append(codRetorno);
/*     */     try {
/* 178 */       tipoCobranca = (String)getJdbcTemplate().queryForObject(str.toString(), String.class);
/* 179 */     } catch (EmptyResultDataAccessException emptyResultDataAccessException) {}
/*     */ 
/*     */     
/* 182 */     return tipoCobranca;
/*     */   }
/*     */ 
/*     */   
/*     */   public void udpateBoleto(String nossoNumero) {
/* 187 */     SimpleJdbcCall call = new SimpleJdbcCall(getDataSource());
/* 188 */     call.withCatalogName("PKG_ALZ_REPORTS");
/* 189 */     call.withProcedureName("updateStatusBoleto");
/* 190 */     call.declareParameters(new SqlParameter[] { new SqlParameter("pNossoNumero", 12) });
/* 191 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("pnossoNumero", nossoNumero);
/* 192 */     call.execute((SqlParameterSource)mapSqlParameterSource);
/*     */   }
/*     */   
/*     */   public Long getSequencialBoleto() {
/* 196 */     Long sequencial = null;
/*     */     try {
/* 198 */       sequencial = getSequenceID("SEQ_ALZ_SEQBOLETO");
/* 199 */     } catch (SQLException e) {
/*     */       
/* 201 */       e.printStackTrace();
/*     */     } 
/* 203 */     return sequencial;
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatString(String value, String pattern) {
/*     */     try {
/* 209 */       MaskFormatter mf = new MaskFormatter(pattern);
/* 210 */       mf.setValueContainsLiteralCharacters(false);
/* 211 */       return mf.valueToString(value);
/* 212 */     } catch (ParseException ex) {
/* 213 */       return value;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String formatadorEsquerda(String valor, String padding, int total) {
/* 218 */     StringBuilder strQuery = new StringBuilder();
/* 219 */     if (StringUtils.isEmpty(valor)) {
/* 220 */       for (int i = 0; i < total; i++) {
/* 221 */         strQuery.append(padding);
/*     */       }
/*     */     } else {
/* 224 */       for (int i = valor.trim().length(); i < total; i++) {
/* 225 */         strQuery.append(padding);
/*     */       }
/* 227 */       strQuery.append(valor.trim());
/*     */     } 
/* 229 */     return strQuery.toString();
/*     */   }
/*     */   
/*     */   private StringBuilder formata(StringBuilder linhaDigitavel) {
/* 233 */     linhaDigitavel.insert(5, '.');
/* 234 */     linhaDigitavel.insert(11, "  ");
/* 235 */     linhaDigitavel.insert(18, '.');
/* 236 */     linhaDigitavel.insert(25, "  ");
/* 237 */     linhaDigitavel.insert(32, '.');
/* 238 */     linhaDigitavel.insert(39, "  ");
/* 239 */     linhaDigitavel.insert(42, "  ");
/* 240 */     return linhaDigitavel;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\boleto\dao\impl\BoletoDAOImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */