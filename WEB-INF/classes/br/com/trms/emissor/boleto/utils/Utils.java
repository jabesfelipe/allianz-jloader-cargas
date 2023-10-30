/*     */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Array;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.swing.text.MaskFormatter;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Utils
/*     */ {
/*  36 */   private static Logger LOGGER = Logger.getLogger(br.com.trms.emissor.boleto.utils.Utils.class.getName());
/*     */ 
/*     */   
/*  39 */   private static final int[] pesoCPF = new int[] { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
/*  40 */   private static final int[] pesoCNPJ = new int[] { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
/*     */   
/*     */   private static final String PROPERTIES_FILE = "/data/ASBoleto/boleto.properties";
/*     */   
/*  44 */   private static SimpleDateFormat DATE_FMT_DMA = new SimpleDateFormat("dd/MM/yyyy");
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
/*     */   public static Properties loadProperties() {
/*     */     try {
/*  60 */       File file = new File("/data/ASBoleto/boleto.properties");
/*  61 */       System.out.println(file);
/*     */       
/*  63 */       FileInputStream fileInput = new FileInputStream(file);
/*  64 */       Properties properties = new Properties();
/*  65 */       properties.load(fileInput);
/*  66 */       System.out.println(properties);
/*     */       
/*  68 */       fileInput.close();
/*  69 */       return properties;
/*  70 */     } catch (Exception e) {
/*  71 */       e.printStackTrace();
/*     */       
/*  73 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Properties loadMessageProperties(String arquivo) throws IOException {
/*  78 */     InputStream caminho = br.com.trms.emissor.boleto.utils.Utils.class.getResourceAsStream(arquivo);
/*  79 */     Properties properties = new Properties();
/*  80 */     properties.load(caminho);
/*  81 */     caminho.close();
/*     */ 
/*     */ 
/*     */     
/*  85 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMensagemLog(Class<?> clazz, String mensagem) {
/*  95 */     StringBuilder mensagemLog = new StringBuilder(mensagem.length() + 20);
/*     */     
/*  97 */     mensagemLog.append(clazz.getName());
/*  98 */     mensagemLog.append(" - ");
/*  99 */     mensagemLog.append(mensagem);
/*     */     
/* 101 */     return mensagemLog.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getNovaSenha() {
/* 110 */     return getRandomNumber(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchSenhas(String senha, String compareSenha) {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidSenha(String senha) {
/* 130 */     if (senha.length() >= 6) {
/* 131 */       return Boolean.TRUE.booleanValue();
/*     */     }
/* 133 */     return Boolean.FALSE.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEqualSenhas(String senha, String compareSenha) {
/* 143 */     if (senha.equals(compareSenha)) {
/* 144 */       return Boolean.TRUE.booleanValue();
/*     */     }
/* 146 */     return Boolean.FALSE.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePontuacao(String s) {
/* 155 */     if (s != null) {
/* 156 */       return s.replaceAll("\\p{Punct}", "");
/*     */     }
/* 158 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calcularDigito(String str, int[] peso) {
/* 168 */     int soma = 0;
/* 169 */     for (int indice = str.length() - 1; indice >= 0; indice--) {
/* 170 */       int digito = Integer.parseInt(str.substring(indice, indice + 1));
/* 171 */       soma += digito * peso[peso.length - str.length() + indice];
/*     */     } 
/* 173 */     soma = 11 - soma % 11;
/* 174 */     return (soma > 9) ? 0 : soma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidCPF(String cpf) {
/* 184 */     if (StringUtils.isBlank(cpf) || cpf.matches("^([0-9])(\\1{10})$") || cpf
/* 185 */       .length() != 11) {
/* 186 */       return false;
/*     */     }
/* 188 */     Integer digito1 = Integer.valueOf(calcularDigito(cpf.substring(0, 9), pesoCPF));
/* 189 */     Integer digito2 = Integer.valueOf(calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF));
/* 190 */     return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2
/* 191 */         .toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidCNPJ(String cnpj) {
/* 201 */     if (StringUtils.isBlank(cnpj) || cnpj.matches("^([0-9])(\\1{10})$") || cnpj
/* 202 */       .length() != 14 || cnpj.matches("^(.)\\1*$")) {
/* 203 */       return false;
/*     */     }
/*     */     
/* 206 */     Integer digito1 = Integer.valueOf(calcularDigito(cnpj.substring(0, 12), pesoCNPJ));
/* 207 */     Integer digito2 = Integer.valueOf(calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ));
/*     */     
/* 209 */     return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2
/* 210 */         .toString());
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
/*     */   public static boolean validaEmail(String email) {
/* 222 */     String regex = "[a-zA-Z0-9]+[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]*\\.+[a-z]{2,4}";
/* 223 */     boolean resultado = true;
/*     */     
/* 225 */     if (StringUtils.isBlank(email)) {
/* 226 */       resultado = false;
/*     */     } else {
/* 228 */       Pattern pattern = Pattern.compile(regex);
/* 229 */       Matcher matcher = pattern.matcher(email);
/*     */       
/* 231 */       resultado = matcher.find();
/*     */     } 
/* 233 */     return resultado;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validaTelefone(String strTelefone) {
/*     */     try {
/* 244 */       return (Long.parseLong(strTelefone) != 0L);
/* 245 */     } catch (NumberFormatException ex) {
/* 246 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String converteEmNumeroSemPontuacao(Object o) {
/* 257 */     if (o != null) {
/* 258 */       String s = String.valueOf(o);
/* 259 */       s = removePontuacao(s);
/* 260 */       return s.replace(" ", "");
/*     */     } 
/* 262 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatList(Collection<?> coll) {
/* 272 */     if (coll != null) {
/* 273 */       String s = String.valueOf(coll);
/* 274 */       if (s.startsWith("[") && s.endsWith("]")) {
/* 275 */         s = s.substring(1, s.length() - 1);
/*     */       }
/* 277 */       return s;
/*     */     } 
/* 279 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatList(List<?> list) {
/* 289 */     List<String> aux = new ArrayList<>();
/*     */     
/* 291 */     if (list != null) {
/* 292 */       for (Object obj : list) {
/* 293 */         String s = (obj == null) ? "" : String.valueOf(obj);
/* 294 */         aux.add(s);
/*     */       } 
/* 296 */       return formatList(aux);
/*     */     } 
/* 298 */     return null;
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
/*     */   public static List<String> concatenaAfixo(List<?> list, String prefixo, String sufixo) {
/* 311 */     String prefixoAux = (prefixo == null) ? "" : prefixo;
/* 312 */     String sufixoAux = (sufixo == null) ? "" : sufixo;
/*     */     
/* 314 */     List<String> result = new ArrayList<>();
/*     */     
/* 316 */     for (Object obj : list) {
/* 317 */       String strObj = (obj == null) ? "" : String.valueOf(obj);
/* 318 */       result.add(prefixoAux + strObj + sufixoAux);
/*     */     } 
/* 320 */     return result;
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
/*     */   public static String formatFields(String pattern, Object value) throws ParseException {
/* 334 */     MaskFormatter mask = new MaskFormatter(pattern);
/* 335 */     mask.setValueContainsLiteralCharacters(false);
/* 336 */     return mask.valueToString(value);
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
/*     */   public static <T> T[] convertToArray(List<T> list, Class<T> clazz) {
/* 349 */     T[] result = (T[])Array.newInstance(clazz, (list != null) ? list.size() : 0);
/*     */     
/* 351 */     return (list != null) ? list.<T>toArray(result) : result;
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
/*     */   public static boolean isEmpty(String... strings) {
/* 370 */     for (String s : strings) {
/* 371 */       if (StringUtils.isBlank(s)) {
/* 372 */         return true;
/*     */       }
/*     */     } 
/* 375 */     return false;
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
/* 388 */     String result = "";
/* 389 */     if (str == null) {
/* 390 */       str = "";
/*     */     }
/* 392 */     else if (str.length() > len) {
/* 393 */       str = str.substring(str.length() - len);
/*     */     } 
/* 395 */     for (int i = 0; i < len - str.trim().length(); i++) {
/* 396 */       result = result.concat("0");
/*     */     }
/* 398 */     result = result.concat(str);
/* 399 */     return result.substring(0, len);
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
/* 413 */     return formatWithZero(String.valueOf(value), len);
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
/* 427 */     return formatWithZero(String.valueOf(value), len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date toDate(String dta) {
/* 437 */     if (dta == null || dta.trim().length() == 0) {
/* 438 */       return null;
/*     */     }
/*     */     try {
/* 441 */       return DATE_FMT_DMA.parse(dta);
/* 442 */     } catch (ParseException e) {
/* 443 */       return null;
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
/*     */   public static String getNovoToken() {
/* 455 */     return getRandomNumber(20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRandomNumber(int len) {
/* 464 */     Random random = new Random();
/* 465 */     String alfa = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*     */     
/* 467 */     StringBuilder sb = new StringBuilder(len);
/* 468 */     for (int i = 0; i < len; i++) {
/* 469 */       sb.append(alfa.charAt(random.nextInt(alfa.length())));
/*     */     }
/* 471 */     return sb.toString();
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
/*     */   public static boolean isLongNullOrZero(Long s) {
/* 489 */     return (s == null || s.longValue() == 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String formatarMoeda(BigDecimal valor) {
/* 494 */     String valorFormatado = "";
/* 495 */     if (valor != null) {
/* 496 */       DecimalFormat formatar = new DecimalFormat("#,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
/* 497 */       valorFormatado = formatar.format(valor);
/*     */     } else {
/* 499 */       valorFormatado = String.valueOf(new BigDecimal(0));
/*     */     } 
/* 501 */     return valorFormatado;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigDecimal isBigDecimalOrZero(BigDecimal valor) {
/* 507 */     if (valor == null || valor == BigDecimal.ZERO) {
/* 508 */       return BigDecimal.ZERO;
/*     */     }
/* 510 */     return valor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String isNullOrEmpty(String texto) {
/* 516 */     String vazio = "";
/* 517 */     if (texto == null || texto.isEmpty()) {
/* 518 */       return vazio;
/*     */     }
/* 520 */     return texto;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto-local.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\Utils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */