/*     */ package WEB-INF.classes.br.com.trms.emissor.boleto.dao.rowmapper;
/*     */ 
/*     */ import br.com.trms.emissor.boleto.vo.Boleto;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.swing.text.MaskFormatter;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoletoRowMapper
/*     */   implements RowMapper<Boleto>
/*     */ {
/*  19 */   protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*  20 */   DecimalFormat formato = new DecimalFormat("#.##");
/*     */ 
/*     */   
/*     */   public Boleto mapRow(ResultSet rs, int rowNum) throws SQLException {
/*  24 */     Boleto boleto = new Boleto();
/*     */ 
/*     */     
/*  27 */     boleto.setBanco(rs.getString("BANCOBOLETO") + "- 7");
/*     */     
/*  29 */     boleto.setNomeSacado(rs.getString("NMCLIENTE"));
/*  30 */     boleto.setEndereco(rs.getString("ENDERECO"));
/*  31 */     boleto.setCidade(rs.getString("CIDADE"));
/*  32 */     boleto.setCep(formatString(rs.getString("CEP"), "#####-###"));
/*  33 */     boleto.setCodigoBarras(rs.getString("CODIGOBARRAS"));
/*     */     
/*  35 */     StringBuilder linhaDigitavel = new StringBuilder(rs.getString("LINHADIGITAVEL"));
/*  36 */     formata(linhaDigitavel);
/*  37 */     boleto.setLinhaDigitavelFormatada(linhaDigitavel.toString());
/*  38 */     boleto.setLinhaDigitavel(rs.getString("LINHADIGITAVEL"));
/*  39 */     boleto.setNossoNumero(rs.getString("NOSSONUMERO"));
/*  40 */     boleto.setUf(rs.getString("ESTADO"));
/*     */     
/*  42 */     boleto.setNumeroDocumento(rs.getString("RECIBO"));
/*     */ 
/*     */ 
/*     */     
/*  46 */     Double valorJuros = new Double(rs.getString("VALORBOLETO"));
/*  47 */     double valorAjustado = valorJuros.doubleValue() / 100.0D;
/*  48 */     valorAjustado *= 0.0027D;
/*  49 */     boleto.setMulta(Double.valueOf(valorAjustado));
/*  50 */     boleto.setStrMulta(String.format("%.2f", new Object[] { Double.valueOf(valorAjustado) }));
/*     */ 
/*     */     
/*  53 */     double valorBoleto = (new Double(rs.getString("VALORBOLETO"))).doubleValue();
/*  54 */     double valorBoletoAjustado = valorBoleto / 100.0D;
/*  55 */     boleto.setValorDocumento(Double.valueOf(valorBoletoAjustado));
/*  56 */     boleto.setStrValorDocumento(String.format("%.2f", new Object[] { Double.valueOf(valorBoletoAjustado) }));
/*     */     
/*  58 */     String dataBanco = rs.getString("DTVENCIMENTO");
/*  59 */     boleto.setVencimento(dataBanco.substring(6, 8) + "/" + dataBanco.substring(4, 6) + "/" + dataBanco.substring(0, 4));
/*     */ 
/*     */     
/*  62 */     boleto.setAgenciaCedente(rs.getString("AGENCIA"));
/*  63 */     boleto.setCodigoCedente(rs.getString("CONTACORRENTE") + "-" + rs.getString("DVCONTA"));
/*  64 */     boleto.setLocalPagamento("PAGAR PREFERENCIALMENTE NO BANCO SANTANDER");
/*  65 */     boleto.setDataDocumento(this.sdf.format(new Date()));
/*  66 */     boleto.setDataProcessamento(this.sdf.format(new Date()));
/*  67 */     boleto.setEspecie("REAL");
/*  68 */     boleto.setEspecieDocumento("REAL");
/*     */     
/*  70 */     boleto.setCedente("ALLIANZ SEGUROS S.A.");
/*  71 */     boleto.setCarteira("COBRANCA SIMPLES ECR");
/*  72 */     String idtCliente = rs.getString("IDT_CLIENTE_EMPRESA");
/*  73 */     idtCliente = idtCliente.substring(0, 4) + "/" + idtCliente.substring(5, idtCliente.length());
/*  74 */     boleto.setIdtClienteEmpresa(idtCliente);
/*     */     
/*  76 */     String tipoPessoa = rs.getString("TPPESSOA");
/*  77 */     String documento = rs.getString("CPFCNPJ");
/*  78 */     Long doc = Long.valueOf(documento);
/*  79 */     if (tipoPessoa != null && tipoPessoa.equalsIgnoreCase("1")) {
/*  80 */       boleto.setDocumentoSacado(formatString(String.valueOf(doc), "###.###.###-##"));
/*     */     } else {
/*  82 */       boleto.setDocumentoSacado(formatString(String.valueOf(doc), "##.###.###/####-##"));
/*     */     } 
/*     */     
/*  85 */     boleto.setPoliza(formatadorEsquerda(rs.getString("POLIZA").trim(), "0", 9));
/*  86 */     boleto.setEndosso(formatadorEsquerda(rs.getString("ENDOSSO"), "0", 5));
/*  87 */     boleto.setParcela(formatadorEsquerda(rs.getString("PARCELA"), "0", 2));
/*     */     
/*  89 */     return boleto;
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatString(String value, String pattern) {
/*     */     try {
/*  95 */       MaskFormatter mf = new MaskFormatter(pattern);
/*  96 */       mf.setValueContainsLiteralCharacters(false);
/*  97 */       return mf.valueToString(value);
/*  98 */     } catch (ParseException ex) {
/*  99 */       return value;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String formatadorEsquerda(String valor, String padding, int total) {
/* 104 */     StringBuilder strQuery = new StringBuilder();
/* 105 */     if (StringUtils.isEmpty(valor)) {
/* 106 */       for (int i = 0; i < total; i++) {
/* 107 */         strQuery.append(padding);
/*     */       }
/*     */     } else {
/* 110 */       for (int i = valor.trim().length(); i < total; i++) {
/* 111 */         strQuery.append(padding);
/*     */       }
/* 113 */       strQuery.append(valor.trim());
/*     */     } 
/* 115 */     return strQuery.toString();
/*     */   }
/*     */   
/*     */   private StringBuilder formata(StringBuilder linhaDigitavel) {
/* 119 */     linhaDigitavel.insert(5, '.');
/* 120 */     linhaDigitavel.insert(11, "  ");
/* 121 */     linhaDigitavel.insert(18, '.');
/* 122 */     linhaDigitavel.insert(25, "  ");
/* 123 */     linhaDigitavel.insert(32, '.');
/* 124 */     linhaDigitavel.insert(39, "  ");
/* 125 */     linhaDigitavel.insert(42, "  ");
/* 126 */     return linhaDigitavel;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\boleto\dao\rowmapper\BoletoRowMapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */