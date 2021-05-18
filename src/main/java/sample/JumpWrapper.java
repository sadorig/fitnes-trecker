package sample;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Jump")
public class JumpWrapper {private List<Jupm> list;
    @XmlElement(name = "Jum")
    public List<Jupm> getList() {
        return list;
    }
    public void setList(List<Jupm> list) {
        this.list = list;
    }

}
