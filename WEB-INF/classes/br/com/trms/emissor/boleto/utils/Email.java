/*     */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*     */ 
/*     */ import br.com.trms.emissor.boleto.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.FileDataSource;
/*     */ import javax.mail.Address;
/*     */ import javax.mail.Authenticator;
/*     */ import javax.mail.BodyPart;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.Multipart;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.AddressException;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class Email {
/*  27 */   private String to = null;
/*  28 */   private String from = null;
/*  29 */   private String host = null;
/*  30 */   private String subject = null;
/*  31 */   private String text = null;
/*     */   private boolean auth = false;
/*  33 */   private String username = null;
/*  34 */   private String password = null;
/*  35 */   private String port = "25";
/*     */   private boolean starttls = false;
/*  37 */   private List<String> attaches = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTo() {
/*  44 */     return this.to;
/*     */   }
/*     */   
/*     */   public void setTo(String to) {
/*  48 */     this.to = to;
/*     */   }
/*     */   
/*     */   public String getFrom() {
/*  52 */     return this.from;
/*     */   }
/*     */   
/*     */   public void setFrom(String from) {
/*  56 */     this.from = from;
/*     */   }
/*     */   
/*     */   public String getHost() {
/*  60 */     return this.host;
/*     */   }
/*     */   
/*     */   public void setHost(String host) {
/*  64 */     this.host = host;
/*     */   }
/*     */   
/*     */   public String getSubject() {
/*  68 */     return this.subject;
/*     */   }
/*     */   
/*     */   public void setSubject(String subject) {
/*  72 */     this.subject = subject;
/*     */   }
/*     */   
/*     */   public String getText() {
/*  76 */     return this.text;
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/*  80 */     this.text = text;
/*     */   }
/*     */   
/*     */   public boolean isAuth() {
/*  84 */     return this.auth;
/*     */   }
/*     */   
/*     */   public void setAuth(boolean auth) {
/*  88 */     this.auth = auth;
/*     */   }
/*     */   
/*     */   public String getUsername() {
/*  92 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setUsername(String username) {
/*  96 */     this.username = username;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/* 100 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/* 104 */     this.password = password;
/*     */   }
/*     */   
/*     */   public String getPort() {
/* 108 */     return this.port;
/*     */   }
/*     */   
/*     */   public void setPort(String port) {
/* 112 */     this.port = port;
/*     */   }
/*     */   
/*     */   public boolean isStarttls() {
/* 116 */     return this.starttls;
/*     */   }
/*     */   
/*     */   public void setStarttls(boolean starttls) {
/* 120 */     this.starttls = starttls;
/*     */   }
/*     */   
/*     */   public List<String> getAttaches() {
/* 124 */     return this.attaches;
/*     */   }
/*     */   
/*     */   public void setAttaches(List<String> attaches) {
/* 128 */     this.attaches = attaches;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static br.com.trms.emissor.boleto.utils.Email getMailProperties() {
/* 137 */     br.com.trms.emissor.boleto.utils.Email email = new br.com.trms.emissor.boleto.utils.Email();
/* 138 */     Properties properties = Utils.loadProperties();
/* 139 */     if (properties != null) {
/* 140 */       email.setHost(properties.getProperty("email_host"));
/* 141 */       email.setFrom(properties.getProperty("email_from"));
/*     */     } 
/* 143 */     email.setAuth(Boolean.FALSE.booleanValue());
/* 144 */     return email;
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
/*     */   public static br.com.trms.emissor.boleto.utils.Email getGMailProperties() {
/* 156 */     Properties prop = Utils.loadProperties();
/*     */     
/* 158 */     br.com.trms.emissor.boleto.utils.Email email = new br.com.trms.emissor.boleto.utils.Email();
/*     */     
/* 160 */     email.setHost(prop.getProperty("email_host"));
/* 161 */     email.setFrom(prop.getProperty("gs_email_from"));
/* 162 */     email.setPort(prop.getProperty("gs_email_port"));
/* 163 */     email.setUsername(prop.getProperty("gs_email_user"));
/* 164 */     email.setPassword(prop.getProperty("gs_email_password"));
/*     */     
/* 166 */     email.setAuth(Boolean.TRUE.booleanValue());
/* 167 */     email.setStarttls(Boolean.TRUE.booleanValue());
/*     */     
/* 169 */     return email;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceTemplateText(Map<String, String> map) {
/* 178 */     for (Map.Entry<String, String> entry : map.entrySet()) {
/* 179 */       this.text = this.text.replace(entry.getKey(), entry.getValue());
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
/*     */   public boolean send() throws AddressException, MessagingException {
/* 192 */     if (StringUtils.isEmpty(this.to) || StringUtils.isEmpty(this.from) || 
/* 193 */       StringUtils.isEmpty(this.host) || StringUtils.isEmpty(this.subject)) {
/* 194 */       return false;
/*     */     }
/*     */     
/* 197 */     MimeMessage message = getMessage(getSession(getProperties()));
/*     */ 
/*     */     
/* 200 */     Transport.send((Message)message);
/*     */     
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Properties getProperties() {
/* 210 */     Properties properties = System.getProperties();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     properties.put("mail.smtp.host", this.host);
/* 216 */     properties.put("mail.smtp.port", this.port);
/*     */     
/* 218 */     return properties;
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
/*     */   private MimeMessage getMessage(Session session) throws AddressException, MessagingException {
/* 232 */     MimeMessage message = new MimeMessage(session);
/*     */ 
/*     */ 
/*     */     
/* 236 */     message.setFrom((Address)new InternetAddress(this.from));
/*     */ 
/*     */     
/* 239 */     message.setRecipients(Message.RecipientType.TO, 
/* 240 */         (Address[])InternetAddress.parse(this.to));
/*     */ 
/*     */     
/* 243 */     message.setSubject(this.subject, "UTF-8");
/*     */     
/* 245 */     MimeMultipart content = new MimeMultipart("mixed");
/*     */     
/* 247 */     MimeBodyPart mimeBodyPart = new MimeBodyPart();
/* 248 */     mimeBodyPart.setContent(this.text, "text/html; charsert=UTF-8");
/*     */     
/* 250 */     content.addBodyPart((BodyPart)mimeBodyPart);
/*     */ 
/*     */     
/* 253 */     if (getAttaches() != null) {
/* 254 */       for (String attach : this.attaches) {
/* 255 */         MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
/* 256 */         FileDataSource source = new FileDataSource(attach);
/* 257 */         mimeBodyPart1.setDataHandler(new DataHandler(source));
/* 258 */         mimeBodyPart1.setFileName(source.getName());
/* 259 */         content.addBodyPart((BodyPart)mimeBodyPart1);
/*     */       } 
/*     */     }
/*     */     
/* 263 */     message.setContent((Multipart)content);
/*     */     
/* 265 */     message.setHeader("X-Sender", "CV Mailer");
/* 266 */     message.setHeader("X-Priority", "1");
/* 267 */     message.setHeader("X-MS-Priority", "High");
/* 268 */     message.addHeader("charset", "UTF-8");
/* 269 */     message.setSentDate(new Date());
/* 270 */     message.saveChanges();
/*     */     
/* 272 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Session getSession(Properties properties) {
/* 281 */     Session session = null;
/* 282 */     if (this.auth) {
/* 283 */       session = Session.getInstance(properties, 
/* 284 */           (Authenticator)new Object(this));
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 291 */       session = Session.getDefaultInstance(getProperties());
/*     */     } 
/*     */     
/* 294 */     return session;
/*     */   }
/*     */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\Email.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */