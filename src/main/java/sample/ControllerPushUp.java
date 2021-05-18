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

public class ControllerPushUp {
    private ObservableList<PushUps> list = FXCollections.observableArrayList();
    @FXML
    private TableView<PushUps> tbvPushUp;
    @FXML
    private TableColumn<PushUps, Integer> colTime;
    @FXML
    private TableColumn<PushUps, Integer> colCalories;
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



    int second = 0;
    int minute = 0;
    boolean isStoped;

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
                    //e.printStackTrace();
                    JOptionPane.showMessageDialog(null,e);
                    break;
                }

            }
        }
    };

    public int timer = (minute*60)+second;
    public int calories = 25;
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
            //tbvPushUp.setItems(list);
            addRow();
            lblResTime.setText("--");
            lblResCal.setText("--");
        });
        btnOpen.setOnAction(event -> {
            try {
                File file = new File("PushUp.xml");
                JAXBContext context = JAXBContext.newInstance(PushUpWrapper.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                PushUpWrapper pushUpWrapper = (PushUpWrapper) unmarshaller.unmarshal(file);
                tbvPushUp.setItems(list);
                list.addAll(pushUpWrapper.getList());
            } catch (JAXBException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e);
            }
        });
        btnSum.setOnAction(event -> {
            int total = 0 ;
            for (PushUps item : tbvPushUp.getItems()) {
                total = total + item.getCalories();
            }
            lblSum.setText(String.valueOf(total));
        });
    }


    public void addRow(){
        try {
            PushUps pushUps = new PushUps();
            pushUps.setTime(Integer.parseInt(lblResTime.getText()));
            pushUps.setCalories(Integer.parseInt(lblResCal.getText()));
            tbvPushUp.getItems().add(pushUps);

            PushUpWrapper pushUpWrapper = new PushUpWrapper();
            pushUpWrapper.setList(tbvPushUp.getItems());

            File file = new File("PushUp.xml");
            JAXBContext context = JAXBContext.newInstance(PushUpWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(pushUpWrapper, file);
            marshaller.marshal(pushUpWrapper,System.out);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e);
        }

    }
}
