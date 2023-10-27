package WEB-INF.classes.br.com.accesstage.cargas.dao;

import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Detalhe;
import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Header;
import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.Trailler;
import java.util.List;

public interface Report240DAO {
  List<Detalhe> findCobRegister();
  
  Header getComplHeader(int paramInt1, int paramInt2);
  
  Trailler getComplTrailler(int paramInt1, int paramInt2);
  
  Long getNroSequencialArquivo();
  
  void update(long paramLong);
  
  void checkNossoNumero(String paramString);
  
  String geTipoCobranca(String paramString1, String paramString2);
}


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\Report240DAO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */