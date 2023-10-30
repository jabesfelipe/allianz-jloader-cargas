package WEB-INF.classes.br.com.trms.emissor.boleto.dao;

import br.com.trms.emissor.boleto.vo.Boleto;
import java.util.List;

public interface BoletoDAO {
  List<Boleto> getBoletos();
  
  void udpateBoleto(String paramString);
  
  Long getSequencialBoleto();
}


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\decompile\emissor-boleto_20200225_RMS_01.war!\WEB-INF\classes\br\com\trms\emissor\boleto\dao\BoletoDAO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */