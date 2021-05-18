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

public class ControllerSquats {
    private ObservableList<Squats> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Squats> tbvSquats;
    @FXML
    private TableColumn<Squats,Integer> colTime;
    @FXML
    private TableColumn<Squats,Integer> colCalories;
    @FXML
    private TextField txtTimer;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnReset;
    @FXML
    private Label lblResTime;
    @FXML
    private Label lblResCal;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnOpen;
    @FXML
    private Button btnSum;
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
    public int calories = 20;
    public int  getStatistics(){
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
            //tbvSquats.setItems(list);
            addRow();
            lblResTime.setText("--");
            lblResCal.setText("--");
        });
        btnOpen.setOnAction(event -> {
            try {
                File file = new File("Squats.xml");
                JAXBContext context = JAXBContext.newInstance(SquatsWrapper.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                SquatsWrapper squatsWrapper1 = (SquatsWrapper) unmarshaller.unmarshal(file);
                tbvSquats.setItems(list);
                list.addAll(squatsWrapper1.getList());
            } catch (JAXBException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e);
            }
        });
        btnSum.setOnAction(event -> {
            int total = 0 ;
            for (Squats item : tbvSquats.getItems()) {
                total = total + item.getCalories();
            }
            lblSum.setText(String.valueOf(total));
        });
    }

    public void addRow(){
        try{
            Squats squats = new Squats();
            squats.setTime(Integer.parseInt(lblResTime.getText()));
            squats.setCalories(Integer.parseInt(lblResCal.getText()));
            tbvSquats.getItems().add(squats);

            SquatsWrapper squatsWrapper = new SquatsWrapper();
            squatsWrapper.setList(tbvSquats.getItems());

            File file = new File("Squats.xml");
            JAXBContext context = JAXBContext.newInstance(SquatsWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(squatsWrapper, file);
            marshaller.marshal(squatsWrapper,System.out);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e);
        }
    }
}
