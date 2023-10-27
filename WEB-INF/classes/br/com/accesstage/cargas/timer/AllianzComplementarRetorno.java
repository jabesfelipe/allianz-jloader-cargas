package WEB-INF.classes.br.com.accesstage.cargas.timer;

import br.com.accesstage.cargas.timer.AbstractTimer;
import br.com.accesstage.loader.util.vo.cargas.allianz.debito.complementar.AllianzDebitoComplementarVO;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class AllianzComplementarRetorno extends AbstractTimer {
  public void execute(HashMap<Integer, AllianzDebitoComplementarVO> mapCompl) {}
}


/* Location:              D:\accesstage\projetos\allianz\arquivos\war\prod\jloader-cargas-20200729.war!\WEB-INF\classes\br\com\accesstage\cargas\timer\AllianzComplementarRetorno.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */