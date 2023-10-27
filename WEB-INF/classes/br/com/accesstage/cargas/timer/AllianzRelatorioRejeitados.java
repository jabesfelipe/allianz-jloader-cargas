/*     */ package WEB-INF.classes.br.com.accesstage.cargas.timer;
/*     */ 
/*     */ import br.com.accesstage.cargas.services.allianz.AllianzRelatorioRejeitadoServico;
/*     */ import br.com.accesstage.cargas.timer.AbstractTimer;
/*     */ import br.com.accesstage.loader.util.vo.cargas.allianz.relatorio.RelatorioRejeitado;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.poi.ss.usermodel.BorderStyle;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.CellStyle;
/*     */ import org.apache.poi.ss.usermodel.CreationHelper;
/*     */ import org.apache.poi.ss.usermodel.Font;
/*     */ import org.apache.poi.ss.usermodel.IndexedColors;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.scheduling.annotation.EnableAsync;
/*     */ import org.springframework.scheduling.annotation.EnableScheduling;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ @Component
/*     */ @EnableAsync
/*     */ @EnableScheduling
/*     */ public class AllianzRelatorioRejeitados
/*     */   extends AbstractTimer
/*     */ {
/*  35 */   private static Logger logger = Logger.getLogger(br.com.accesstage.cargas.timer.AllianzRelatorioRejeitados.class);
/*     */   
/*     */   @Autowired
/*     */   private AllianzRelatorioRejeitadoServico servico;
/*     */   
/*     */   @Scheduled(cron = "${timer.schedule.allianz.relatorio.rejeitado}")
/*     */   public void execute() {
/*  42 */     load("ALLIANZREJEITADOS");
/*  43 */     logger.warn("AllianzRelatorioRejeitados - Inicio de processamento:[" + this.sdf.format(new Date()) + "]");
/*     */     
/*  45 */     StringBuffer strFile = new StringBuffer();
/*     */     
/*     */     try {
/*  48 */       strFile.append((String)getConfig().get("ALLIANZRELATREJEITAD_OUTPUT"));
/*  49 */       strFile.append((String)getConfig().get("ALLIANZRELATREJEITAD_INTERCAMBIO"));
/*  50 */       strFile.append("_").append((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).append(".xlsx");
/*  51 */       List<RelatorioRejeitado> lista = this.servico.listarRelatorioRejeitados();
/*     */       
/*  53 */       if (lista.size() > 0) {
/*  54 */         String[] columns = { "Banco", "Identificação do Cliente na Empresa", "Agência para Débito", "Identificação do Cliente no Banco", "Data do Vencimento ou Débito", "Valor Original ou Debitado", "Código de Retorno", "Uso da Empresa" };
/*     */ 
/*     */         
/*  57 */         XSSFWorkbook xSSFWorkbook = new XSSFWorkbook();
/*  58 */         CreationHelper createHelper = xSSFWorkbook.getCreationHelper();
/*  59 */         Sheet sheet = xSSFWorkbook.createSheet("Ocorrencia nao cadastrada");
/*     */         
/*  61 */         Font headerFont = xSSFWorkbook.createFont();
/*  62 */         headerFont.setBold(true);
/*  63 */         headerFont.setFontHeightInPoints((short)14);
/*  64 */         headerFont.setColor(IndexedColors.BLACK1.getIndex());
/*     */         
/*  66 */         CellStyle headerCellStyle = xSSFWorkbook.createCellStyle();
/*  67 */         headerCellStyle.setFont(headerFont);
/*  68 */         headerCellStyle.setBorderBottom(BorderStyle.THIN);
/*  69 */         headerCellStyle.setBorderTop(BorderStyle.THIN);
/*  70 */         headerCellStyle.setBorderRight(BorderStyle.THIN);
/*  71 */         headerCellStyle.setBorderLeft(BorderStyle.THIN);
/*     */         
/*  73 */         Row headerRow = sheet.createRow(0);
/*     */ 
/*     */         
/*  76 */         for (int i = 0; i < columns.length; i++) {
/*  77 */           Cell cell = headerRow.createCell(i);
/*  78 */           cell.setCellValue(columns[i]);
/*  79 */           cell.setCellStyle(headerCellStyle);
/*     */         } 
/*     */         
/*  82 */         CellStyle dateCellStyle = xSSFWorkbook.createCellStyle();
/*  83 */         dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
/*     */ 
/*     */         
/*  86 */         CellStyle cellStyle = xSSFWorkbook.createCellStyle();
/*  87 */         cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("R$ #0.00"));
/*     */ 
/*     */ 
/*     */         
/*  91 */         int rowNum = 1;
/*  92 */         for (RelatorioRejeitado rejeitado : lista) {
/*  93 */           Row row = sheet.createRow(rowNum++);
/*     */           
/*  95 */           row.createCell(0).setCellValue(rejeitado.getBanco());
/*  96 */           row.createCell(1).setCellValue(rejeitado.getIdentificacaoClienteEmpresa());
/*  97 */           row.createCell(2).setCellValue(rejeitado.getAgenciaDebito());
/*  98 */           row.createCell(3).setCellValue(rejeitado.getIdentificacaoClienteBanco());
/*     */ 
/*     */           
/* 101 */           Cell dateOfBirthCell = row.createCell(4);
/* 102 */           dateOfBirthCell.setCellValue(rejeitado.getDataVencimentoDebito());
/* 103 */           dateOfBirthCell.setCellStyle(dateCellStyle);
/*     */           
/* 105 */           Cell valor = row.createCell(5);
/* 106 */           valor.setCellStyle(cellStyle);
/* 107 */           valor.setCellValue(rejeitado.getValorOriginalDebitado().doubleValue());
/*     */           
/* 109 */           row.createCell(6).setCellValue(rejeitado.getCodigoRetorno());
/* 110 */           row.createCell(7).setCellValue(rejeitado.getUsoEmpresa());
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 116 */         for (int j = 0; j < columns.length; j++) {
/* 117 */           sheet.autoSizeColumn(j);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 123 */         FileOutputStream fileOut = new FileOutputStream(strFile.toString());
/* 124 */         xSSFWorkbook.write(fileOut);
/* 125 */         fileOut.close();
/* 126 */         xSSFWorkbook.close();
/*     */         
/* 128 */         this.servico.updateStatusRejeitado(lista);
/*     */       } else {
/* 130 */         logger.warn("AllianzRelatorioRejeitados: sem registros para geracao");
/*     */       }
/*     */     
/* 133 */     } catch (FileNotFoundException fe) {
/* 134 */       logger.error("AllianzRelatorioRejeitados - Error writing file:[" + strFile.toString() + "]");
/* 135 */     } catch (IOException e) {
/* 136 */       logger.error("AllianzRelatorioRejeitados - Error writing file:[" + strFile.toString() + "]");
/* 137 */     } catch (Exception ex) {
/* 138 */       logger.error("AllianzRelatorioRejeitados - General error:[" + ex.getMessage() + "]");
/*     */     } 
/*     */     
/* 141 */     logger.warn("AllianzRelatorioRejeitados - Final de processamento:[" + this.sdf.format(new Date()) + "]");
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\AllianzRelatorioRejeitados.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */