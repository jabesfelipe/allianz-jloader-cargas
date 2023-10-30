/*     */ package WEB-INF.classes.br.com.trms.emissor.boleto;
/*     */ 
/*     */ import com.idautomation.linear.BarCode;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ public class BarcodeImgServlet
/*     */   extends HttpServlet
/*     */   implements Serializable
/*     */ {
/*     */   private static final String BARCODE_DEFAULT = "000000000000";
/*     */   public static final String BARCODE_URL_MAGIC_WORD = "b4rc0d&";
/*     */   private static final long serialVersionUID = 2888632694287044945L;
/*     */   
/*     */   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
/*  39 */     res.setContentType("image/jpeg");
/*  40 */     res.setHeader("Pragma", "no-cache");
/*  41 */     res.setHeader("Cache-Control", "no-cache");
/*  42 */     res.setDateHeader("Expires", 0L);
/*     */     
/*  44 */     ServletOutputStream out = res.getOutputStream();
/*     */     
/*  46 */     BarCode cb = new BarCode();
/*     */     
/*  48 */     cb.barType = 2;
/*  49 */     cb.barHeightCM = 2.0D;
/*  50 */     cb.X = 0.25D;
/*  51 */     cb.resolution = 38;
/*  52 */     cb.setXDimensionCM(0.04D);
/*  53 */     cb.leftMarginCM = 0.0D;
/*  54 */     cb.topMarginCM = 0.0D;
/*  55 */     cb.N = 3.0D;
/*  56 */     cb.H = 2.0D;
/*  57 */     cb.checkCharacter = false;
/*  58 */     cb.checkCharacterInText = false;
/*  59 */     cb.showText = false;
/*     */ 
/*     */     
/*  62 */     String c = req.getParameter("c");
/*  63 */     String barcode = req.getParameter("BARCODE");
/*     */ 
/*     */ 
/*     */     
/*  67 */     cb.code = "000000000000";
/*     */     try {
/*  69 */       c = encodeMD5(String.valueOf(barcode) + "b4rc0d&");
/*  70 */       if (barcode != null && c != null) {
/*  71 */         String barcodeCrypted = encodeMD5(String.valueOf(barcode) + "b4rc0d&");
/*     */         
/*  73 */         if (barcodeCrypted.equals(c)) {
/*  74 */           cb.code = barcode;
/*     */         } else {
/*     */           
/*  77 */           System.out.println("Chave nao confere para BARCODE" + barcode + " c: " + c);
/*     */         } 
/*     */       } else {
/*     */         
/*  81 */         System.out.println("Nao enviou BARCODE ou chave. BARCODE: " + barcode + " c: " + c);
/*     */       }
/*     */     
/*  84 */     } catch (NoSuchAlgorithmException nse) {
/*  85 */       System.out.println("Erro ao tentar decriptar BARCODE BARCODE: " + barcode + " c: " + c + ". " + nse.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  90 */     BufferedImage BarImage = new BufferedImage(
/*  91 */         405, 50, 1);
/*  92 */     Graphics2D BarGraphics = BarImage.createGraphics();
/*  93 */     cb.setSize(405, 50);
/*  94 */     cb.paint(BarGraphics);
/*     */     
/*  96 */     ImageIO.write(BarImage, "jpg", (OutputStream)out);
/*  97 */     out.close();
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
/*     */   public String encodeMD5(String toencode) throws NoSuchAlgorithmException {
/* 111 */     MessageDigest md = MessageDigest.getInstance("MD5");
/* 112 */     md.update(toencode.getBytes());
/* 113 */     BigInteger hash = new BigInteger(1, md.digest());
/* 114 */     return hash.toString(16);
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\boleto\BarcodeImgServlet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */