package sample;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Squats")
public class SquatsWrapper extends Squats {
    private List<Squats> list;

    @XmlElement(name = "Squat")
    public List<Squats> getList() {
        return list;
    }
    public void setList(List<Squats> list) {
        this.list = list;
    }
}
