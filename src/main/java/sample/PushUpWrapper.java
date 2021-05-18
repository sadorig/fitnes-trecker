package sample;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "PushUps")
public class PushUpWrapper {
        private List<PushUps> list;

        @XmlElement(name = "PushUp")
        public List<PushUps> getList() {
            return list;
        }
        public void setList(List<PushUps> list) {
            this.list = list;
        }
}
