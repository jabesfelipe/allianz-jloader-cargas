/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ public class CalculadorDAC
/*    */ {
/*    */   public static final String dacMod11(String field) {
/*  6 */     int sum = 0;
/*  7 */     int multiplier = 2;
/*  8 */     int res = 0;
/*    */     
/* 10 */     for (int i = field.length(); i > 0; i--) {
/* 11 */       int digit = Character.getNumericValue(field.charAt(i - 1));
/* 12 */       sum += digit * multiplier++;
/* 13 */       if (multiplier > 9)
/* 14 */         multiplier = 2; 
/*    */     } 
/* 16 */     res = 11 - sum % 11;
/* 17 */     if (res == 0 || res == 10 || res == 11)
/* 18 */       res = 1; 
/* 19 */     return Integer.toString(res);
/*    */   }
/*    */   
/*    */   public static final String dacMod10(String field) {
/* 23 */     int sum = 0;
/* 24 */     boolean two = true;
/*    */     
/* 26 */     for (int i = field.length(); i > 0; i--) {
/* 27 */       int digit = Character.getNumericValue(field.charAt(i - 1));
/* 28 */       if (two) {
/* 29 */         int tmp = digit * 2;
/* 30 */         if (tmp > 9)
/* 31 */           tmp = tmp / 10 + tmp % 10; 
/* 32 */         sum += tmp;
/* 33 */         two = false;
/*    */       } else {
/* 35 */         sum += digit;
/* 36 */         two = true;
/*    */       } 
/*    */     } 
/* 39 */     int res = 10 - sum % 10;
/* 40 */     if (res == 10)
/* 41 */       res = 0; 
/* 42 */     return Integer.toString(res);
/*    */   }
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\CalculadorDAC.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */