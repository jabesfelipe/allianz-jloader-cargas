/*     */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*     */ 
/*     */ import br.com.trms.emissor.boleto.utils.Crc32;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.sql.Date;
/*     */ import java.text.DateFormat;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.Random;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FormatUtil
/*     */ {
/*     */   public static final int LOG_INFO = 1;
/*     */   public static final int LOG_ERROR = 2;
/*     */   public static final String BARCODE_URL_MAGIC_WORD = "b4rc0d&";
/*  33 */   public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
/*     */   public static void toLog(String msg, String pathAndName, int ErrorCode) throws Exception {
/*  46 */     Calendar cal = GregorianCalendar.getInstance();
/*  47 */     BufferedWriter out = null;
/*     */ 
/*     */     
/*  50 */     File file = new File(pathAndName + (new SimpleDateFormat("yyyyMMdd")).format(cal.getTime()) + ".log");
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  55 */       out = new BufferedWriter(new FileWriter(file, true));
/*  56 */     } catch (FileNotFoundException e) {
/*  57 */       e.printStackTrace();
/*  58 */     } catch (IOException e) {
/*  59 */       e.printStackTrace();
/*     */     } 
/*  61 */     if (ErrorCode == 1) {
/*  62 */       out.write((new SimpleDateFormat("HH:mm:ss,SS")).format(cal
/*  63 */             .getTime()) + " INFO:  [" + msg + "]");
/*     */     } else {
/*  65 */       out.write((new SimpleDateFormat("HH:mm:ss,SS")).format(cal
/*  66 */             .getTime()) + " ERROR: [" + msg + "]");
/*     */     } 
/*  68 */     out.newLine();
/*  69 */     out.close();
/*     */   }
/*     */ 
/*     */   
/*  73 */   private static SimpleDateFormat DATE_FMT_DMA = new SimpleDateFormat("dd/MM/yyyy");
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
/*     */   public static String encodeMD5(String toencode) throws NoSuchAlgorithmException {
/*  86 */     MessageDigest md = MessageDigest.getInstance("MD5");
/*  87 */     md.update(toencode.getBytes());
/*  88 */     BigInteger hash = new BigInteger(1, md.digest());
/*  89 */     return hash.toString(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEmpty(Object obj) {
/* 100 */     boolean ret = false;
/* 101 */     if (obj == null)
/* 102 */       ret = true; 
/* 103 */     if (obj instanceof String)
/* 104 */       ret = obj.equals(""); 
/* 105 */     if (obj instanceof Integer)
/* 106 */       ret = obj.equals(new Integer(0)); 
/* 107 */     if (obj instanceof Long)
/* 108 */       ret = obj.equals(new Long(0L)); 
/* 109 */     if (obj instanceof Float)
/* 110 */       ret = obj.equals(new Float(0.0F)); 
/* 111 */     if (obj instanceof Double)
/* 112 */       ret = obj.equals(new Float(0.0F)); 
/* 113 */     return ret;
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
/*     */   public static String toTipoEInscricao(int tipo, String inscricao) throws Exception {
/* 128 */     StringBuffer resultado = new StringBuffer();
/* 129 */     switch (tipo) {
/*     */       case 1:
/* 131 */         resultado.append("CPF");
/*     */         break;
/*     */       case 2:
/* 134 */         resultado.append("CNPJ");
/*     */         break;
/*     */       case 3:
/* 137 */         resultado.append("PIS");
/*     */         break;
/*     */     } 
/* 140 */     resultado.append(" ");
/* 141 */     resultado.append(toInscricao(tipo, inscricao));
/* 142 */     return resultado.toString();
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
/*     */   public static String toInscricao(int tipo, String inscricao) throws Exception {
/* 158 */     StringBuffer retorno = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (inscricao.length() == 15) {
/* 167 */       String tmp; switch (tipo) {
/*     */         case 0:
/* 169 */           retorno.append("Isento / N&atilde;o Informado");
/*     */           break;
/*     */         case 1:
/* 172 */           tmp = inscricao.substring(4);
/* 173 */           retorno.append(tmp.substring(0, 3));
/* 174 */           retorno.append(".");
/* 175 */           retorno.append(tmp.substring(3, 6));
/* 176 */           retorno.append(".");
/* 177 */           retorno.append(tmp.substring(6, 9));
/* 178 */           retorno.append("-");
/* 179 */           retorno.append(tmp.substring(9, 11));
/*     */           break;
/*     */         
/*     */         case 2:
/* 183 */           tmp = inscricao.substring(1);
/* 184 */           retorno.append(tmp.substring(0, 2));
/* 185 */           retorno.append(".");
/* 186 */           retorno.append(tmp.substring(2, 5));
/* 187 */           retorno.append(".");
/* 188 */           retorno.append(tmp.substring(5, 8));
/* 189 */           retorno.append("/");
/* 190 */           retorno.append(tmp.substring(8, 12));
/* 191 */           retorno.append("-");
/* 192 */           retorno.append(tmp.substring(12, 14));
/*     */           break;
/*     */         case 3:
/* 195 */           tmp = inscricao.substring(1);
/* 196 */           retorno.append(tmp.substring(0, 2));
/* 197 */           retorno.append(".");
/* 198 */           retorno.append(tmp.substring(2, 5));
/* 199 */           retorno.append(".");
/* 200 */           retorno.append(tmp.substring(5, 8));
/* 201 */           retorno.append("/");
/* 202 */           retorno.append(tmp.substring(8, 12));
/* 203 */           retorno.append("-");
/* 204 */           retorno.append(tmp.substring(12, 14));
/*     */ 
/*     */         
/*     */         case 4:
/* 208 */           retorno.append(inscricao);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } else {
/* 214 */       retorno.append("Erro ao formatar inscrição Tipo:");
/* 215 */       retorno.append(tipo);
/* 216 */       retorno.append(" Inscricao: ");
/* 217 */       retorno.append(inscricao);
/* 218 */       throw new Exception(retorno.toString());
/*     */     } 
/* 220 */     return retorno.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date toDate(String dta) {
/* 230 */     if (dta == null || dta.trim().length() == 0) {
/* 231 */       return null;
/*     */     }
/*     */     try {
/* 234 */       return DATE_FMT_DMA.parse(dta);
/* 235 */     } catch (ParseException e) {
/* 236 */       return null;
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
/*     */   public static Date toDate(String dta, DateFormat format) {
/* 248 */     if (dta == null || dta.trim().length() == 0) {
/* 249 */       return null;
/*     */     }
/*     */     try {
/* 252 */       return format.parse(dta);
/* 253 */     } catch (ParseException e) {
/* 254 */       return null;
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
/*     */   public static Date toSQLDate(String dta) {
/* 266 */     if (dta == null || dta.trim().length() == 0) {
/* 267 */       return null;
/*     */     }
/*     */     try {
/* 270 */       return new Date(DATE_FMT_DMA.parse(dta).getTime());
/* 271 */     } catch (ParseException e) {
/* 272 */       return null;
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
/*     */   public static final Date getDate(String date) {
/* 285 */     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
/* 286 */     Date dt = null;
/*     */     try {
/* 288 */       dt = df.parse(date);
/* 289 */     } catch (Exception e) {
/* 290 */       dt = null;
/*     */     } 
/*     */     
/* 293 */     return (date == null) ? null : dt;
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
/*     */   public static String formatWithZero(String str, int len) {
/* 306 */     String result = "";
/* 307 */     if (str == null) {
/* 308 */       str = "";
/*     */     }
/* 310 */     else if (str.length() > len) {
/* 311 */       str = str.substring(str.length() - len);
/*     */     } 
/* 313 */     for (int i = 0; i < len - str.trim().length(); i++) {
/* 314 */       result = result.concat("0");
/*     */     }
/* 316 */     result = result.concat(str);
/* 317 */     return result.substring(0, len);
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
/*     */   public static String formatWithZero(int value, int len) {
/* 331 */     return formatWithZero(String.valueOf(value), len);
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
/*     */   public static String formatWithZero(long value, int len) {
/* 345 */     return formatWithZero(String.valueOf(value), len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toStringDate(Date dta) {
/* 355 */     if (dta == null) {
/* 356 */       return null;
/*     */     }
/* 358 */     return DATE_FMT_DMA.format(dta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toValorReal(String pValor) {
/* 368 */     if (pValor == null || pValor.trim().length() == 0) {
/* 369 */       return "0,00";
/*     */     }
/* 371 */     NumberFormat nFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
/* 372 */     String strValorRetorno = nFormat.format(Double.parseDouble(pValor));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 377 */     if (strValorRetorno.indexOf(",") == -1) {
/* 378 */       strValorRetorno = strValorRetorno + ",00";
/*     */     } else {
/* 380 */       int i = strValorRetorno.indexOf(",");
/* 381 */       if (i >= 0) {
/* 382 */         String str = strValorRetorno.substring(i + 1, strValorRetorno
/* 383 */             .length());
/* 384 */         if (str.length() == 1) {
/* 385 */           strValorRetorno = strValorRetorno + "0";
/*     */         }
/*     */       } 
/*     */     } 
/* 389 */     return strValorRetorno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toValorUS(String pValor) {
/* 398 */     if (pValor == null || pValor.trim().length() == 0) {
/* 399 */       return "0.00";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 404 */     String strValorRetorno = pValor;
/*     */ 
/*     */     
/* 407 */     if (pValor.indexOf(".") == -1) {
/* 408 */       if (strValorRetorno.indexOf(",") == -1) {
/*     */ 
/*     */         
/* 411 */         strValorRetorno = strValorRetorno + ".00";
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 416 */         strValorRetorno = strValorRetorno.replace(',', '.');
/*     */       } 
/*     */     } else {
/* 419 */       if (strValorRetorno.indexOf(",") != -1)
/*     */       {
/*     */         
/* 422 */         strValorRetorno = strValorRetorno.replaceAll("\\.", "");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 427 */       int i = strValorRetorno.indexOf(".");
/* 428 */       if (i >= 0) {
/* 429 */         String str = strValorRetorno.substring(i + 1, strValorRetorno
/* 430 */             .length());
/* 431 */         if (str.length() == 1) {
/* 432 */           strValorRetorno = strValorRetorno + "0";
/*     */         }
/*     */       } 
/*     */     } 
/* 436 */     return strValorRetorno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toPercent(String pValor) {
/* 445 */     if (pValor == null || pValor.trim().length() == 0) {
/* 446 */       return "0,00";
/*     */     }
/* 448 */     NumberFormat nFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
/* 449 */     String strValorRetorno = nFormat.format(Double.parseDouble(pValor));
/* 450 */     if (strValorRetorno.indexOf(",") == -1) {
/* 451 */       strValorRetorno = strValorRetorno + ",00";
/*     */     } else {
/* 453 */       int i = strValorRetorno.indexOf(",");
/* 454 */       if (i >= 0) {
/* 455 */         String str = strValorRetorno.substring(i + 1, strValorRetorno
/* 456 */             .length());
/* 457 */         if (str.length() == 1) {
/* 458 */           strValorRetorno = strValorRetorno + "0";
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 463 */     return strValorRetorno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validarEmail(String pEmail) {
/* 472 */     boolean validou = false;
/* 473 */     String email = pEmail;
/* 474 */     String strAntesArroba = "", strDepoisArroba = "";
/* 475 */     email.trim();
/* 476 */     if (email != null && !email.equals("") && 
/* 477 */       email.substring(email.indexOf("@") + 1, email.length())
/* 478 */       .indexOf("@") == -1 && email.indexOf("@") > 0) {
/* 479 */       strAntesArroba = email.substring(0, email.indexOf("@"));
/* 480 */       strDepoisArroba = email.substring(email.indexOf("@") + 1, email
/* 481 */           .length());
/* 482 */       if (verificaSomentePalavra(strAntesArroba) && 
/* 483 */         verificaSomentePalavra("@" + strDepoisArroba)) {
/* 484 */         String[] strArrayPalavras = strDepoisArroba.split("\\.");
/* 485 */         validou = true;
/* 486 */         if (strArrayPalavras.length > 0) {
/* 487 */           for (int i = 0; i < strArrayPalavras.length; i++) {
/* 488 */             if (i + 1 == strArrayPalavras.length) {
/* 489 */               if (strArrayPalavras[i].length() > 1) {
/* 490 */                 if (!verificaSomenteLetra(strArrayPalavras[i]))
/* 491 */                   validou = false; 
/*     */               } else {
/* 493 */                 validou = false;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } else {
/* 498 */           validou = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 503 */     return validou;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean verificaSomentePalavra(String strParam) {
/* 514 */     if (strParam.endsWith(".") || strParam.endsWith("-") || strParam
/* 515 */       .endsWith("_") || strParam.equals("@") || strParam
/* 516 */       .equals("") || strParam == null) {
/* 517 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 521 */     for (int i = 0; i < strParam.length(); i++) {
/*     */       
/* 523 */       char c = strParam.charAt(i);
/* 524 */       Character carac = new Character(c);
/* 525 */       int unicode = carac.hashCode();
/* 526 */       if (strParam.indexOf("@") == -1) {
/* 527 */         if (i == 0 && (unicode < 65 || unicode > 90) && (unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57))
/*     */         {
/*     */           
/* 530 */           return false;
/*     */         }
/* 532 */         if (i > 0 && c != '.' && c != '-' && c != '_' && (unicode < 65 || unicode > 90) && (unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 538 */           return false;
/*     */         }
/* 540 */         if ((c == '.' || c == '-' || c == '_') && (strParam
/* 541 */           .charAt(i + 1) == '.' || strParam
/* 542 */           .charAt(i + 1) == '-' || strParam
/* 543 */           .charAt(i + 1) == '_')) {
/* 544 */           return false;
/*     */         }
/*     */       } else {
/* 547 */         if (i == 1 && (unicode < 65 || unicode > 90) && (unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57))
/*     */         {
/*     */           
/* 550 */           return false;
/*     */         }
/* 552 */         if (i > 1 && c != '.' && c != '-' && (unicode < 65 || unicode > 90) && (unicode < 97 || unicode > 122) && (unicode < 48 || unicode > 57))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 557 */           return false;
/*     */         }
/* 559 */         if ((c == '.' || c == '-') && (strParam
/* 560 */           .charAt(i + 1) == '.' || strParam
/* 561 */           .charAt(i + 1) == '-'))
/* 562 */           return false; 
/*     */       } 
/*     */     } 
/* 565 */     return true;
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
/*     */   public static boolean verificaSomenteLetra(String strParam) {
/* 578 */     for (int i = 0; i < strParam.length(); i++) {
/*     */       
/* 580 */       char c = strParam.charAt(i);
/* 581 */       if (!Character.isLetter(c)) {
/* 582 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 586 */     return true;
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
/*     */   public static String generatePassword() {
/*     */     String senhaTmp, novaSenha;
/* 599 */     Random rand = new Random();
/*     */ 
/*     */     
/* 602 */     int semente = rand.nextInt(2147483647);
/* 603 */     String caractereEspecial = "-";
/*     */ 
/*     */     
/* 606 */     switch (semente - semente / 10 * 10) {
/*     */       case 1:
/* 608 */         caractereEspecial = "@";
/*     */         break;
/*     */       case 2:
/* 611 */         caractereEspecial = "!";
/*     */         break;
/*     */       case 3:
/* 614 */         caractereEspecial = "#";
/*     */         break;
/*     */       case 4:
/* 617 */         caractereEspecial = "$";
/*     */         break;
/*     */       case 5:
/* 620 */         caractereEspecial = "*";
/*     */         break;
/*     */       case 6:
/* 623 */         caractereEspecial = "+";
/*     */         break;
/*     */       case 7:
/* 626 */         caractereEspecial = ":";
/*     */         break;
/*     */       case 8:
/* 629 */         caractereEspecial = ".";
/*     */         break;
/*     */       case 9:
/* 632 */         caractereEspecial = "=";
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 639 */       senhaTmp = encodeMD5(String.valueOf(semente));
/* 640 */     } catch (NoSuchAlgorithmException nsae) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 645 */       senhaTmp = String.valueOf(semente) + Integer.toHexString(rand.nextInt(2147483647)) + Integer.toOctalString(rand.nextInt(2147483647));
/*     */     } 
/*     */     
/* 648 */     if (senhaTmp.length() >= 8) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 653 */       novaSenha = senhaTmp.substring(0, 4).toLowerCase() + caractereEspecial + senhaTmp.substring(5, 8).toUpperCase();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 658 */       novaSenha = caractereEspecial + senhaTmp;
/*     */     } 
/* 660 */     return novaSenha;
/*     */   }
/*     */   
/*     */   public static int elevado(int base, int exp) {
/* 664 */     int retorno = 1;
/* 665 */     for (int i = 1; i <= exp; i++) {
/* 666 */       retorno *= base;
/*     */     }
/* 668 */     return retorno;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encryptaLinkEmail(String urlBase, String servlet, String codigoBanco, String codigoTitulo, String codigoHistorico) {
/* 674 */     String data = codigoBanco + codigoTitulo + codigoHistorico;
/* 675 */     int crc = Crc32.getIntValue(data, 0, data.length());
/* 676 */     StringBuffer buf = new StringBuffer();
/* 677 */     buf.append(urlBase);
/* 678 */     buf.append(servlet);
/* 679 */     buf.append("?l=N&b=");
/* 680 */     buf.append(codigoBanco);
/* 681 */     buf.append("&t=");
/* 682 */     buf.append(codigoTitulo);
/* 683 */     buf.append("&h=");
/* 684 */     buf.append(codigoHistorico);
/* 685 */     buf.append("&c=");
/* 686 */     buf.append(String.valueOf(crc));
/*     */     
/* 688 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigDecimal toBigDecimal(String valor) {
/*     */     BigDecimal valorBig;
/*     */     try {
/* 696 */       valorBig = new BigDecimal(toValorUS(valor));
/* 697 */     } catch (Exception e) {
/* 698 */       valorBig = BigDecimal.valueOf(0L);
/*     */     } 
/* 700 */     return valorBig;
/*     */   }
/*     */   
/*     */   public static boolean isEmptyString(String obj) {
/* 704 */     if (obj == null) {
/* 705 */       return true;
/*     */     }
/* 707 */     return (obj.trim().length() <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validaParamsEmail(String crcParam, String codigoBanco, String codigoHistoricoEmailParam, String codTitulo) {
/* 716 */     int crc = 0;
/* 717 */     int codigoHistoricoEmail = 0;
/* 718 */     if (crcParam != null && crcParam.length() > 0) {
/* 719 */       crc = Integer.parseInt(crcParam);
/*     */     }
/* 721 */     if (codigoHistoricoEmailParam != null && codigoHistoricoEmailParam
/* 722 */       .length() > 0) {
/* 723 */       codigoHistoricoEmail = Integer.parseInt(codigoHistoricoEmailParam);
/*     */     }
/* 725 */     String data = codigoBanco + codTitulo + codigoHistoricoEmail;
/* 726 */     return Crc32.verify(crc, data, 0, data.length());
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
/*     */   
/*     */   public static boolean validaParamsTitulo(String crcParam, String codigoBanco, String codigoHistoricoEmailParam, String codTitulo, String timestampCript) {
/* 745 */     int crc = 0;
/* 746 */     int codigoHistoricoEmail = 0;
/* 747 */     if (crcParam != null && crcParam.length() > 0) {
/* 748 */       crc = Integer.parseInt(crcParam);
/*     */     }
/* 750 */     if (codigoHistoricoEmailParam != null && codigoHistoricoEmailParam
/* 751 */       .length() > 0) {
/* 752 */       codigoHistoricoEmail = Integer.parseInt(codigoHistoricoEmailParam);
/*     */     }
/* 754 */     String data = codigoBanco + codTitulo + codigoHistoricoEmail;
/*     */ 
/*     */     
/* 757 */     if (Crc32.verify(crc, data, 0, data.length())) {
/*     */       
/* 759 */       String timestamp = new String(Base64.decodeBase64(timestampCript.getBytes()));
/* 760 */       Long time = Long.valueOf(Long.parseLong(timestamp));
/*     */       
/* 762 */       Date expire = new Date(time.longValue());
/*     */       
/* 764 */       Date now = new Date();
/* 765 */       return now.before(expire);
/*     */     } 
/*     */ 
/*     */     
/* 769 */     return false;
/*     */   }
/*     */   
/*     */   public static Date getBeginOfTheDay(Date data) {
/* 773 */     Calendar calendar = Calendar.getInstance();
/* 774 */     calendar.setTime(data);
/* 775 */     calendar.set(11, calendar.getMinimum(11));
/* 776 */     calendar.set(12, calendar.getMinimum(12));
/* 777 */     calendar.set(13, calendar.getMinimum(13));
/* 778 */     calendar.set(14, calendar.getMinimum(14));
/* 779 */     return calendar.getTime();
/*     */   }
/*     */   
/*     */   public static Date getEndOfDay(Date data) {
/* 783 */     Calendar calendar = Calendar.getInstance();
/* 784 */     calendar.setTime(data);
/* 785 */     calendar.set(11, calendar.getMaximum(11));
/* 786 */     calendar.set(12, calendar.getMaximum(12));
/* 787 */     calendar.set(13, calendar.getMaximum(13));
/* 788 */     calendar.set(14, calendar.getMaximum(14));
/* 789 */     return calendar.getTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String formatBarCodeNum(String barCodeNum) {
/* 794 */     StringBuffer buf = new StringBuffer();
/* 795 */     buf.append(barCodeNum.substring(0, 10));
/* 796 */     buf.append(" ");
/* 797 */     buf.append(barCodeNum.substring(10, 21));
/* 798 */     buf.append(" ");
/* 799 */     buf.append(barCodeNum.substring(21, 32));
/* 800 */     buf.append(" ");
/* 801 */     buf.append(barCodeNum.substring(32, 33));
/* 802 */     buf.append(" ");
/* 803 */     buf.append(barCodeNum.substring(33));
/*     */ 
/*     */     
/* 806 */     return buf.toString();
/*     */   }
/*     */   
/*     */   public static String formatValue(BigDecimal value) {
/* 810 */     DecimalFormat formatter = new DecimalFormat("0000000000");
/*     */     
/* 812 */     BigDecimal tmp = value.multiply(new BigDecimal(100)).setScale(0, 4);
/*     */     
/* 814 */     return formatter.format(tmp.doubleValue());
/*     */   }
/*     */   
/*     */   public static String formatValue(String value, int zeros) {
/* 818 */     return formatWithZero(value, zeros);
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\FormatUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */