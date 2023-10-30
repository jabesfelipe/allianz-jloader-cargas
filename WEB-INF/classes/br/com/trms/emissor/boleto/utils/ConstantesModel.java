/*    */ package WEB-INF.classes.br.com.trms.emissor.boleto.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantesModel
/*    */ {
/*    */   public static final String MSG_ERRO_BUSCA_PERFIL = "A busca pelo perfil através da descrição {0} não retornou resultado.";
/*    */   public static final String MSG_ERRO_BUSCA_CODIGO_PERFIL = "Erro ao buscar código do perfil por descrição: ";
/*    */   public static final String MSG_ERRO_BUSCA_USUARIOS_EXPIRADOS = "A busca por usuários expirados em {0} não retornou resultado.";
/*    */   public static final String MSG_ERRO_SALVAR_USUARIO = "Não foi possível salvar as informações do usuário.";
/*    */   public static final String MSG_ERRO_CADASTRO_USUARIO = "Não foi possível cadastrar o usuário.";
/*    */   public static final String MSG_ERRO_USUARIO_NAO_ENCONTRADO = "Não foi possível encontrar o usuário.";
/*    */   public static final String MSG_ERRO_CARREGAR_USUARIO = "Não foi possível buscar as informações do usuário.";
/*    */   public static final String MSG_USUARIO_CADASTRO_SUCESSO = "Usuário cadastrado com sucesso.";
/*    */   public static final String MSG_USUARIO_EDICAO_SUCESSO = "Usuário editado com sucesso.";
/*    */   public static final String MSG_ERRO_DESBLOQUEAR_USUARIO = "Não foi possível desbloquear o usuário.";
/*    */   public static final String MSG_ERRO_REMOVER_USUARIO = "Não foi possível remover o usuário.";
/*    */   public static final String MSG_USUARIO_EXISTENTE = "Usuário já cadastrado. Efetue o login para o CPF correspondente.";
/*    */   public static final String MSG_USUARIO_INEXISTENTE = "Não foi identificado usuário com esse CPF.";
/*    */   public static final String MSG_CONVENIO_INEXISTENTE = "Não foi identificado convênio associado.";
/*    */   public static final String MSG_ERRO_CADASTRO_PEDIDO = "Não foi possível cadastrar o pedido de empréstimo.";
/*    */   public static final String MSG_ERRO_PEDIDO_NEGADO = "Pedido de Empréstimo negado pela política de crédito do banco e/ou convênio";
/*    */   public static final String MSG_ERRO_CARREGAR_PEDIDO = "Ao carregar detalhes do pedido.";
/*    */   public static final String MSG_ERRO_PEDIDO_VAZIO = "Não foi encontrado nenhum pedido.";
/*    */   public static final String MSG_ERRO_ALTERAR_PROTOCOLO_RECEBIMENTO_PEDIDO = "Ao alterar protocolo de recebimento.";
/*    */   public static final String MSG_ERRO_ALTERAR_ENDERECO_COLETA_PEDIDO = "Ao alterar as informações do endereço de coleta.";
/*    */   public static final String MSG_ERRO_ALTERAR_STATUS_PEDIDO = "Ao alterar status do pedido.";
/*    */   public static final String MSG_ERRO_ALTERAR_INFO_COMPLEMENTARES_PEDIDO = "Ao alterar informações complementares do endereço de coleta.";
/*    */   public static final String MSG_ERRO_CARREGAR_ARQUIVO_PEDIDO = "Não foi possível carregar o arquivo.";
/*    */   public static final String MSG_ERRO_EXCLUIR_ARQUIVO_PEDIDO = "Ao excluir registro.";
/*    */   public static final String MSG_ERRO_CARREGAR_CONT_LOTES = "Ao carregar controle de lotes.";
/*    */   public static final String MSG_ERRO_ALTERAR_SENHA = "Ao alterar senha.";
/*    */   public static final String MSG_SENHA_ALTERADA = "Senha alterada com sucesso.";
/*    */   public static final String MSG_LOGIN_INVALIDO = "Login inválido para esse módulo do sistema.";
/*    */   public static final String MSG_USUARIO_CONVENIO_INVALIDO = "Login inválido para esse convênio";
/*    */   public static final String MSG_ERRO_FECHAR_LOTE = "Não foi possível fechar o lote.";
/*    */   public static final String MSG_LOTE_FECHADO = "Lote fechado com sucesso.";
/*    */   public static final String MSG_LOTE_NENHUM_SELECIONADO = "Nenhum pedido foi selecionado.";
/*    */   public static final String MSG_EMAIL_NAO_ENCONTRADO = "E-mail não cadastrado para o CPF informado.";
/*    */   public static final String MSG_EMAIL_ENVIADO_OK = "E-mail enviado com sucesso.";
/*    */   public static final String MSG_SUCESSO_ARQUIVO_UPLOAD = "Arquivo salvo com sucesso.";
/*    */   public static final String MSG_CAMPOS_OBRIGATORIOS_ARQUIVO_UPLOAD_PEDIDO = "Campos com * são obrigatórios no envio de um arquivo.";
/*    */   public static final String MSG_CAMPOS_OBRIGATORIOS_END_COLETA_PEDIDO = "Campos com * são obrigatórios no endereço da coleta.";
/*    */   public static final String MSG_SUCESSO_END_COLETA = "Endereço de coleta alterado com sucesso.";
/*    */   public static final String MSG_CAMPOS_OBRIGATORIOS_PROTOCOLO_PEDIDO = "Campos com * são obrigatórios nos dados de recebimento do contrato pelo banco.";
/*    */   public static final String MSG_SUCESSO_PROTOCOLO_PEDIDO = "Dados de recebimento do contrato pelo banco alterados com sucesso.";
/*    */   public static final String MSG_CAMPOS_OBRIGATORIOS_INFO_COMP_PEDIDO = "Campos com * são obrigatórios nas informações complementares.";
/*    */   public static final String MSG_SUCESSO_INFO_COMP_PEDIDO = "Dados das informações complementares do endereço de coleta alterados com sucesso.";
/*    */   public static final String MSG_ERRO_ALTERAR_LOGISTICA_PEDIDO = "Ao alterar logística do pedido.";
/*    */   public static final String MSG_SUCESSO_ALTERAR_LOGISTICA_PEDIDO = "Logística alterada com sucesso.";
/*    */   public static final String MSG_USUARIO_REMOCAO_SUCESSO = "Usuário removido com sucesso";
/*    */   public static final String MSG_USUARIO_BLOQUEADO_SUCESSO = "Usuário bloqueado com sucesso";
/*    */   public static final String MSG_USUARIO_DESBLOQUEADO_SUCESSO = "Usuário desbloqueado com sucesso";
/*    */   public static final String MSG_ERRO_FECHAR_LOTE_DATA = "A data prevista para envio do lote é inválida.";
/*    */   public static final String MSG_ERRO_CONSULTAR_LOTE = "Erro ao consultar lote.";
/*    */   public static final String MSG_ERRO_BUSCA_AUDITORIA_PEDIDO = "Erro ao buscar histórico do Pedido.";
/*    */   public static final String MSG_ERRO_CONSULTAR_PEDIDO = "Ao Consultar Pedidos.";
/*    */   public static final String MSG_ERRO_CONSULTAR_USUARIO = "Ao Consultar Usuário logado.";
/*    */   public static final String MSG_ERRO_CONFIRMAR_RECEBIMENTO = "Erro confirmar recebimento do lote.";
/*    */   public static final String MSG_RECEBIMENTO_CONFIRMADO = "Recebimento confirmado para os pedidos.";
/*    */   public static final String MSG_NENHUM_PEDIDO_SELECIONADO = "Nenhum pedido selecionado.";
/*    */   public static final String MSG_USUARIO_LOGAR_CONSULTA_PEDIDO = "Por favor faça o login no sistema e tente novamente.";
/*    */   public static final String MSG_ERRO_EXPORT_CSV = "Não foi possível exportar o relatório.";
/*    */   public static final String MSG_CAMPO_NAO_HISTORIADO = "Campo não precisa ser historiado no pedido";
/*    */   public static final String MSG_ERRO_CHECKLIST_INCOMPLETO = "Check List incompleto para marcar como recebidos";
/*    */   public static final String MSG_SUCESSO_STATUS_PEDIDO = "Sucesso ao alterar o Status do Pedido";
/*    */   public static final String MSG_TIPO_OPERACAO_INVALIDO = "Tipo de operação \"%s\" inválido!";
/*    */   public static final String MSG_ERRO_DOWNLOAD_ARQUIVO = "Ao fazer o download do arquivo.";
/*    */   public static final String MSG_INFO_ENVIO_DESATIVADO = "Importante: O envio de emails está desativado, verifique  propriedade block_mailing do emailSendLote.properties";
/*    */   public static final String MSG_INFO_SMS_DESATIVADO = "Importante: O envio de sms está desativado, verifique  propriedade block_sms do smsSendLote.properties";
/*    */   public static final String MSG_ERRO_ENVIO_EMAILS_TITULO = "Falha ao enviar emails dos titulos";
/*    */   public static final String MSG_ERRO_ENVIO_SMS_TITULO = "Falha ao enviar sms dos titulos";
/*    */   public static final String CONST_BOLETO_CUSTOM = "BOLETOCUSTOM";
/*    */   public static final String CONST_BOLETO_NORMAL = "BOLETO";
/*    */   public static final String URL_DESK_BOLETO = "deskASBoleto";
/* 80 */   public static final Long ENVIO_2A_VIA_COD_TIPO_TEMPLATE = Long.valueOf(1L);
/* 81 */   public static final Long AVISO_COBRANCA_COD_TIPO_TEMPLATE = Long.valueOf(2L);
/*    */   public static final String ENVIO_EMAIL_LOTE = "L";
/*    */   public static final String ENVIO_EMAIL_MANUAL = "M";
/*    */   public static final String GS_SUBJECT_MSG_NOVA_SENHA = "Accesstage Boleto Eletrônico";
/*    */   public static final String GS_BODY_MSG_NOVA_SENHA = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/></head><body><DIV>&nbsp;</DIV> <DIV>&nbsp;</DIV> <DIV> Notificamos que foi efetuado seu cadastro como usu&aacute;rio do Sistema de Boleto Eletr&ocirc;nico da Accesstage. Seu login &eacute; <%NME_LOGIN%>. Para proceder crie sua senha atrav&eacute;s do link abaixo: <br> <br>LINK: <a href=\"<%LINK%>/gerar-senha?token=<%TOKEN%>\"><%LINK%>/gerar-senha?token=<%TOKEN%></a> </DIV> <br> <br> <br> <br> <DIV> Atenciosamente, <br> <br> <span style=\"color: #686868;\"><b>Accesstage Tecnologia Ltda.</b> </span> <br><a href=\"mailto:suporte@accesstage.com.br\">suporte@accesstage.com.br</a> <br>S&atilde;o Paulo e Grande S&atilde;o Paulo: (11) 3549-6570 <br>Fora de S&atilde;o Paulo: 0800-7736633 FREE <br><a href=\"www.accesstage.com.br\">www.accesstage.com.br</a> </DIV></body></html>";
/*    */   public static final String GS_BODY_MSG_NOVA_SENHA_BODY = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/></head><body><DIV>&nbsp;</DIV> <DIV>&nbsp;</DIV> <DIV> <%BODY%> <br> <br> <br> Notificamos que foi efetuado seu cadastro como usu&aacute;rio do Sistema de Boleto Eletr&ocirc;nico da Accesstage. Seu login &eacute; <%NME_LOGIN%>. Para proceder crie sua senha atrav&eacute;s do link abaixo: <br> <br> LINK: <a href=\"<%LINK%>/gerar-senha?token=<%TOKEN%>\"><%LINK%>/gerar-senha?token=<%TOKEN%></a> </DIV> <br> <br> <br> <br> <DIV> Atenciosamente, <br> <br> <span style=\"color: #686868;\"><b>Accesstage Tecnologia Ltda.</b> </span> <br><a href=\"mailto:suporte@accesstage.com.br\">suporte@accesstage.com.br</a> <br>S&atilde;o Paulo e Grande S&atilde;o Paulo: (11) 3549-6570 <br>Fora de S&atilde;o Paulo: 0800-7736633 FREE <br><a href=\"www.accesstage.com.br\">www.accesstage.com.br</a> </DIV></body></html>";
/*    */ }


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\bolet\\utils\ConstantesModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */