package WEB-INF.classes.br.com.accesstage.cargas.dao;

import br.com.accesstage.loader.util.vo.FileOutVO;
import br.com.accesstage.loader.util.vo.TokenVO;
import br.com.accesstage.loader.util.vo.cargas.allianz.ConfigVO;
import java.util.List;

public interface ExportDAO {
  void updateStatusExportacao(TokenVO paramTokenVO) throws Exception;
  
  List<TokenVO> getTokenPending() throws Exception;
  
  List<TokenVO> getTokenPending(String paramString) throws Exception;
  
  List<FileOutVO> getFileOut(TokenVO paramTokenVO) throws Exception;
  
  List<ConfigVO> getConfig(String paramString) throws Exception;
}


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\dao\ExportDAO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */