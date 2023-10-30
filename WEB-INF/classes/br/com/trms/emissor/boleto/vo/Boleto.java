/*     */ package WEB-INF.classes.br.com.trms.emissor.boleto.vo;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Boleto
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2307306277638925509L;
/*     */   private String banco;
/*     */   private String nossoNumero;
/*     */   private String especie;
/*     */   private String quantidade;
/*     */   private Double valorDocumento;
/*     */   private String strValorDocumento;
/*     */   private String idtClienteEmpresa;
/*     */   private String especieDocumento;
/*     */   private String agenciaCedente;
/*     */   private String codigoCedente;
/*     */   private String nomeCedente;
/*     */   private String documentoCedente;
/*     */   private String enderecoCedente;
/*     */   private String codigoBoleto;
/*     */   private String linhaDigitavel;
/*     */   private String linhaDigitavelFormatada;
/*     */   private String codigoBarras;
/*     */   private String localPagamento;
/*     */   private String cedente;
/*     */   private String dataDocumento;
/*     */   private String numeroDocumento;
/*     */   private String aceite;
/*     */   private String dataProcessamento;
/*     */   private String usoBanco;
/*     */   private String carteira;
/*     */   private String vencimento;
/*     */   private Double desconto;
/*     */   private Double outrasDeducoes;
/*     */   private Double multa;
/*     */   private String strMulta;
/*     */   private Double outrasAcrescimos;
/*     */   private Double valorCobrado;
/*     */   private String instrucoes1;
/*     */   private String instrucoes2;
/*     */   private String instrucoes3;
/*     */   private String instrucoes4;
/*     */   private String instrucoes5;
/*     */   private String instrucoes6;
/*     */   private String nomeSacado;
/*     */   private String documentoSacado;
/*     */   private String endereco;
/*     */   private String cidade;
/*     */   private String uf;
/*     */   private String cep;
/*     */   private String poliza;
/*     */   private String endosso;
/*     */   private String parcela;
/*     */   private String url;
/*     */   private Long nroUnico;
/*     */   
/*     */   public String getBanco() {
/*  65 */     return this.banco;
/*     */   }
/*     */   public void setBanco(String banco) {
/*  68 */     this.banco = banco;
/*     */   }
/*     */   public String getNossoNumero() {
/*  71 */     return this.nossoNumero;
/*     */   }
/*     */   public void setNossoNumero(String nossoNumero) {
/*  74 */     this.nossoNumero = nossoNumero;
/*     */   }
/*     */   public String getEspecie() {
/*  77 */     return this.especie;
/*     */   }
/*     */   public void setEspecie(String especie) {
/*  80 */     this.especie = especie;
/*     */   }
/*     */   public String getQuantidade() {
/*  83 */     return this.quantidade;
/*     */   }
/*     */   public void setQuantidade(String quantidade) {
/*  86 */     this.quantidade = quantidade;
/*     */   }
/*     */   public Double getValorDocumento() {
/*  89 */     return this.valorDocumento;
/*     */   }
/*     */   public void setValorDocumento(Double valorDocumento) {
/*  92 */     this.valorDocumento = valorDocumento;
/*     */   }
/*     */   public String getStrValorDocumento() {
/*  95 */     return this.strValorDocumento;
/*     */   }
/*     */   public void setStrValorDocumento(String strValorDocumento) {
/*  98 */     this.strValorDocumento = strValorDocumento;
/*     */   }
/*     */   public String getIdtClienteEmpresa() {
/* 101 */     return this.idtClienteEmpresa;
/*     */   }
/*     */   public void setIdtClienteEmpresa(String idtClienteEmpresa) {
/* 104 */     this.idtClienteEmpresa = idtClienteEmpresa;
/*     */   }
/*     */   public String getEspecieDocumento() {
/* 107 */     return this.especieDocumento;
/*     */   }
/*     */   public void setEspecieDocumento(String especieDocumento) {
/* 110 */     this.especieDocumento = especieDocumento;
/*     */   }
/*     */   public String getAgenciaCedente() {
/* 113 */     return this.agenciaCedente;
/*     */   }
/*     */   public void setAgenciaCedente(String agenciaCedente) {
/* 116 */     this.agenciaCedente = agenciaCedente;
/*     */   }
/*     */   public String getCodigoCedente() {
/* 119 */     return this.codigoCedente;
/*     */   }
/*     */   public void setCodigoCedente(String codigoCedente) {
/* 122 */     this.codigoCedente = codigoCedente;
/*     */   }
/*     */   public String getNomeCedente() {
/* 125 */     return this.nomeCedente;
/*     */   }
/*     */   public void setNomeCedente(String nomeCedente) {
/* 128 */     this.nomeCedente = nomeCedente;
/*     */   }
/*     */   public String getDocumentoCedente() {
/* 131 */     return this.documentoCedente;
/*     */   }
/*     */   public void setDocumentoCedente(String documentoCedente) {
/* 134 */     this.documentoCedente = documentoCedente;
/*     */   }
/*     */   public String getEnderecoCedente() {
/* 137 */     return this.enderecoCedente;
/*     */   }
/*     */   public void setEnderecoCedente(String enderecoCedente) {
/* 140 */     this.enderecoCedente = enderecoCedente;
/*     */   }
/*     */   public String getCodigoBoleto() {
/* 143 */     return this.codigoBoleto;
/*     */   }
/*     */   public void setCodigoBoleto(String codigoBoleto) {
/* 146 */     this.codigoBoleto = codigoBoleto;
/*     */   }
/*     */   public String getLinhaDigitavel() {
/* 149 */     return this.linhaDigitavel;
/*     */   }
/*     */   public void setLinhaDigitavel(String linhaDigitavel) {
/* 152 */     this.linhaDigitavel = linhaDigitavel;
/*     */   }
/*     */   public String getLinhaDigitavelFormatada() {
/* 155 */     return this.linhaDigitavelFormatada;
/*     */   }
/*     */   public void setLinhaDigitavelFormatada(String linhaDigitavelFormatada) {
/* 158 */     this.linhaDigitavelFormatada = linhaDigitavelFormatada;
/*     */   }
/*     */   public String getCodigoBarras() {
/* 161 */     return this.codigoBarras;
/*     */   }
/*     */   public void setCodigoBarras(String codigoBarras) {
/* 164 */     this.codigoBarras = codigoBarras;
/*     */   }
/*     */   public String getLocalPagamento() {
/* 167 */     return this.localPagamento;
/*     */   }
/*     */   public void setLocalPagamento(String localPagamento) {
/* 170 */     this.localPagamento = localPagamento;
/*     */   }
/*     */   public String getCedente() {
/* 173 */     return this.cedente;
/*     */   }
/*     */   public void setCedente(String cedente) {
/* 176 */     this.cedente = cedente;
/*     */   }
/*     */   public String getDataDocumento() {
/* 179 */     return this.dataDocumento;
/*     */   }
/*     */   public void setDataDocumento(String dataDocumento) {
/* 182 */     this.dataDocumento = dataDocumento;
/*     */   }
/*     */   public String getNumeroDocumento() {
/* 185 */     return this.numeroDocumento;
/*     */   }
/*     */   public void setNumeroDocumento(String numeroDocumento) {
/* 188 */     this.numeroDocumento = numeroDocumento;
/*     */   }
/*     */   public String getAceite() {
/* 191 */     return this.aceite;
/*     */   }
/*     */   public void setAceite(String aceite) {
/* 194 */     this.aceite = aceite;
/*     */   }
/*     */   public String getDataProcessamento() {
/* 197 */     return this.dataProcessamento;
/*     */   }
/*     */   public void setDataProcessamento(String dataProcessamento) {
/* 200 */     this.dataProcessamento = dataProcessamento;
/*     */   }
/*     */   public String getUsoBanco() {
/* 203 */     return this.usoBanco;
/*     */   }
/*     */   public void setUsoBanco(String usoBanco) {
/* 206 */     this.usoBanco = usoBanco;
/*     */   }
/*     */   public String getCarteira() {
/* 209 */     return this.carteira;
/*     */   }
/*     */   public void setCarteira(String carteira) {
/* 212 */     this.carteira = carteira;
/*     */   }
/*     */   public String getVencimento() {
/* 215 */     return this.vencimento;
/*     */   }
/*     */   public void setVencimento(String vencimento) {
/* 218 */     this.vencimento = vencimento;
/*     */   }
/*     */   public Double getDesconto() {
/* 221 */     return this.desconto;
/*     */   }
/*     */   public void setDesconto(Double desconto) {
/* 224 */     this.desconto = desconto;
/*     */   }
/*     */   public Double getOutrasDeducoes() {
/* 227 */     return this.outrasDeducoes;
/*     */   }
/*     */   public void setOutrasDeducoes(Double outrasDeducoes) {
/* 230 */     this.outrasDeducoes = outrasDeducoes;
/*     */   }
/*     */   public Double getMulta() {
/* 233 */     return this.multa;
/*     */   }
/*     */   public void setMulta(Double multa) {
/* 236 */     this.multa = multa;
/*     */   }
/*     */   public String getStrMulta() {
/* 239 */     return this.strMulta;
/*     */   }
/*     */   public void setStrMulta(String strMulta) {
/* 242 */     this.strMulta = strMulta;
/*     */   }
/*     */   public Double getOutrasAcrescimos() {
/* 245 */     return this.outrasAcrescimos;
/*     */   }
/*     */   public void setOutrasAcrescimos(Double outrasAcrescimos) {
/* 248 */     this.outrasAcrescimos = outrasAcrescimos;
/*     */   }
/*     */   public Double getValorCobrado() {
/* 251 */     return this.valorCobrado;
/*     */   }
/*     */   public void setValorCobrado(Double valorCobrado) {
/* 254 */     this.valorCobrado = valorCobrado;
/*     */   }
/*     */   public String getInstrucoes1() {
/* 257 */     return this.instrucoes1;
/*     */   }
/*     */   public void setInstrucoes1(String instrucoes1) {
/* 260 */     this.instrucoes1 = instrucoes1;
/*     */   }
/*     */   public String getInstrucoes2() {
/* 263 */     return this.instrucoes2;
/*     */   }
/*     */   public void setInstrucoes2(String instrucoes2) {
/* 266 */     this.instrucoes2 = instrucoes2;
/*     */   }
/*     */   public String getInstrucoes3() {
/* 269 */     return this.instrucoes3;
/*     */   }
/*     */   public void setInstrucoes3(String instrucoes3) {
/* 272 */     this.instrucoes3 = instrucoes3;
/*     */   }
/*     */   public String getInstrucoes4() {
/* 275 */     return this.instrucoes4;
/*     */   }
/*     */   public void setInstrucoes4(String instrucoes4) {
/* 278 */     this.instrucoes4 = instrucoes4;
/*     */   }
/*     */   public String getInstrucoes5() {
/* 281 */     return this.instrucoes5;
/*     */   }
/*     */   public void setInstrucoes5(String instrucoes5) {
/* 284 */     this.instrucoes5 = instrucoes5;
/*     */   }
/*     */   public String getInstrucoes6() {
/* 287 */     return this.instrucoes6;
/*     */   }
/*     */   public void setInstrucoes6(String instrucoes6) {
/* 290 */     this.instrucoes6 = instrucoes6;
/*     */   }
/*     */   public String getNomeSacado() {
/* 293 */     return this.nomeSacado;
/*     */   }
/*     */   public void setNomeSacado(String nomeSacado) {
/* 296 */     this.nomeSacado = nomeSacado;
/*     */   }
/*     */   public String getDocumentoSacado() {
/* 299 */     return this.documentoSacado;
/*     */   }
/*     */   public void setDocumentoSacado(String documentoSacado) {
/* 302 */     this.documentoSacado = documentoSacado;
/*     */   }
/*     */   public String getEndereco() {
/* 305 */     return this.endereco;
/*     */   }
/*     */   public void setEndereco(String endereco) {
/* 308 */     this.endereco = endereco;
/*     */   }
/*     */   public String getCidade() {
/* 311 */     return this.cidade;
/*     */   }
/*     */   public void setCidade(String cidade) {
/* 314 */     this.cidade = cidade;
/*     */   }
/*     */   public String getUf() {
/* 317 */     return this.uf;
/*     */   }
/*     */   public void setUf(String uf) {
/* 320 */     this.uf = uf;
/*     */   }
/*     */   public String getCep() {
/* 323 */     return this.cep;
/*     */   }
/*     */   public void setCep(String cep) {
/* 326 */     this.cep = cep;
/*     */   }
/*     */   public String getPoliza() {
/* 329 */     return this.poliza;
/*     */   }
/*     */   public void setPoliza(String poliza) {
/* 332 */     this.poliza = poliza;
/*     */   }
/*     */   public String getEndosso() {
/* 335 */     return this.endosso;
/*     */   }
/*     */   public void setEndosso(String endosso) {
/* 338 */     this.endosso = endosso;
/*     */   }
/*     */   public String getParcela() {
/* 341 */     return this.parcela;
/*     */   }
/*     */   public void setParcela(String parcela) {
/* 344 */     this.parcela = parcela;
/*     */   }
/*     */   public String getUrl() {
/* 347 */     return this.url;
/*     */   }
/*     */   public void setUrl(String url) {
/* 350 */     this.url = url;
/*     */   }
/*     */   public Long getNroUnico() {
/* 353 */     return this.nroUnico;
/*     */   }
/*     */   public void setNroUnico(Long nroUnico) {
/* 356 */     this.nroUnico = nroUnico;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\boleto\vo\Boleto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */