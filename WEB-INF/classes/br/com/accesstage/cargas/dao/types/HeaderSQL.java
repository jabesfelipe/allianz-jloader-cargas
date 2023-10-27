/*    */ package WEB-INF.classes.br.com.accesstage.cargas.dao.types;
/*    */ 
/*    */ import br.com.accesstage.loader.util.vo.cargas.volkswagen.layout500.remessa.Header;
/*    */ import java.sql.SQLData;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.SQLInput;
/*    */ import java.sql.SQLOutput;
/*    */ 
/*    */ public class HeaderSQL
/*    */   extends Header
/*    */   implements SQLData {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public HeaderSQL() {}
/*    */   
/*    */   public HeaderSQL(Header header) {
/* 17 */     this.codArquivo = header.getCodArquivo();
/* 18 */     this.empid = header.getEmpid();
/* 19 */     this.linhaProcessada = header.getLinhaProcessada();
/* 20 */     setIdentRegistro(header.getIdentRegistro());
/* 21 */     setCodigoComunicacao(header.getCodigoComunicacao());
/* 22 */     setTipoInscricao(header.getTipoInscricao());
/* 23 */     setNroDocumento(header.getNroDocumento());
/* 24 */     setNmeEmpresa(header.getNmeEmpresa());
/* 25 */     setTipoServico(header.getTipoServico());
/* 26 */     setCodigoOrigemArquivo(header.getCodigoOrigemArquivo());
/* 27 */     setNumeroRemessa(header.getNumeroRemessa());
/* 28 */     setNumeroRetorno(header.getNumeroRetorno());
/* 29 */     setDataHoraArquivo(header.getDataHoraArquivo());
/* 30 */     setDensidadeGravacao(header.getDensidadeGravacao());
/* 31 */     setUnidadeDensidadeGravacao(header.getUnidadeDensidadeGravacao());
/* 32 */     setIdentModuloMicro(header.getIdentModuloMicro());
/* 33 */     setTipoProcessamento(header.getTipoProcessamento());
/* 34 */     setReservadoEmpresa(header.getReservadoEmpresa());
/* 35 */     setReservadoBanco(header.getReservadoBanco());
/* 36 */     setReservadoBanco2(header.getReservadoBanco2());
/* 37 */     setNumeroListaDebito(header.getNumeroListaDebito());
/* 38 */     setReservadoBanco3(header.getReservadoBanco3());
/* 39 */     setNumeroSequencial(header.getNumeroSequencial());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSQLTypeName() throws SQLException {
/* 50 */     return "HEADER_VOLKS_500";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readSQL(SQLInput stream, String typeName) throws SQLException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeSQL(SQLOutput stream) throws SQLException {
/* 61 */     stream.writeInt(0);
/* 62 */     stream.writeInt(getCodArquivo());
/* 63 */     stream.writeInt(getLinhaProcessada());
/* 64 */     stream.writeInt(getEmpid());
/* 65 */     stream.writeInt(getCodigoComunicacao().intValue());
/* 66 */     stream.writeInt(getTipoInscricao().intValue());
/* 67 */     stream.writeString(getNroDocumento());
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\types\HeaderSQL.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */