package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ControllerJump {
    private ObservableList<Jupm> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Jupm> tbvJump;
    @FXML
    private TableColumn<Jupm, Integer> colTime;
    @FXML
    private TableColumn<Jupm, Integer> colCalories;
    @FXML
    private TextField txtTimer;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnOpen;
    @FXML
    private Button btnSum;
    @FXML
    private Label lblResTime;
    @FXML
    private Label lblResCal;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblSum;


    public int second = 0;
    public int minute = 0;
    public boolean isStoped;

    Runnable runnable = new Runnable() {
        @Override
        public synchronized void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if(isStoped == false) {
                        second++;
                        if(second == 60){
                            minute++;
                            second = 0;
                        }
                        txtTimer.setText(minute+":"+second);
                    }else {
                        throw  new InterruptedException();
                    }
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null,e);
                    break;
                }
            }
        }
    };

    public int timer = (minute*60)+second;
    public  int calories = 15;
    public int getStatistics(){
        calories = calories * timer;
        return calories;
    }

    public void  addStat(){
        int t,c;
        t = (minute*60)+second;
        c = 25 * t;
        if(isStoped){
            if(t!=0){
                lblResTime.setText(String.valueOf(t));
                lblResCal.setText(String.valueOf(c));
            }
        }
    }


    @FXML
    void initialize(){
        colTime.setCellValueFactory(cellData -> cellData.getValue().timeProperty().asObject());
        colCalories.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty().asObject());

        btnStart.setOnAction(event -> {
            Thread thread = new Thread(runnable);
            thread.start();
            isStoped = false;
            lblResTime.setText("--");
            lblResCal.setText("--");
        });
        btnStop.setOnAction(event -> {
            isStoped = true;
            getStatistics();
            addStat();
        });
        btnReset.setOnAction(event -> {
            second = 0;
            minute = 0;
            txtTimer.setText(minute+":"+second);
            lblResTime.setText("--");
            lblResCal.setText("--");
        });
        btnSave.setOnAction(event -> {
            isStoped = true;
            colTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
            colCalories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
            //tbvJump.setItems(list);
            addRow();
            lblResTime.setText("--");
            lblResCal.setText("--");
        });
        btnOpen.setOnAction(event -> {
            try {
                File file = new File("Jump.xml");
                JAXBContext context = JAXBContext.newInstance(JumpWrapper.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                JumpWrapper jumpWrapper = (JumpWrapper) unmarshaller.unmarshal(file);
                tbvJump.setItems(list);
                list.addAll(jumpWrapper.getList());
            } catch (JAXBException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e);
            }
        });
        btnSum.setOnAction(event -> {
            int total = 0 ;
            for (Jupm item : tbvJump.getItems()) {
                total = total + item.getCalories();
            }
            lblSum.setText(String.valueOf(total));
        });
    }

    public void addRow(){
        try {
            Jupm jupm = new Jupm();
            jupm.setTime(Integer.parseInt(lblResTime.getText()));
            jupm.setCalories(Integer.parseInt(lblResCal.getText()));
            tbvJump.getItems().add(jupm);

            JumpWrapper jumpWrapper = new JumpWrapper();
            jumpWrapper.setList(tbvJump.getItems());

            File file = new File("Jump.xml");
            JAXBContext context = JAXBContext.newInstance(JumpWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(jumpWrapper, file);
            marshaller.marshal(jumpWrapper,System.out);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e);
        }
    }
}
