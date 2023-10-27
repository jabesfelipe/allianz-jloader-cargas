package WEB-INF.classes.br.com.accesstage.cargas.dao;

import br.com.accesstage.loader.util.vo.cargas.allianz.relatorio.RelatorioNaoCadastrado;
import br.com.accesstage.loader.util.vo.cargas.allianz.relatorio.RelatorioRejeitado;
import java.util.List;

public interface RelatoriosDAO {
  List<RelatorioNaoCadastrado> findRelatorioNaoCadastrado();
  
  List<RelatorioRejeitado> findRelatorioRejeitado();
  
  void updateRelatorioNaoCadastrado(long paramLong);
  
  void updateRelatorioRejeitado(long paramLong);
}


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\RelatoriosDAO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */