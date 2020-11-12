package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn;

public class MainController implements Initializable{
	
	@FXML
	private TableView<user> table_user;

	@FXML
	private TableColumn<user, Integer> col_id;

	@FXML
	private TableColumn<user, String> col_username;

	@FXML
    private TableColumn<user, String> col_password;

	@FXML
    private TableColumn<user, String> col_email;

    @FXML
    private TableColumn<user, String> col_type;
    
    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_type;
    
    @FXML
    private TextField txt_id;
    
    ObservableList<user> listM;
    
    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    
    public void Add_user() {
    	conn = sqlconnect.dbconnect();
    	try {
			ps = conn.prepareStatement("insert into user (username, password, email, type) values(?,?,?,?)");
			ps.setString(1,txt_username.getText());
			ps.setString(2, txt_password.getText());
			ps.setString(3, txt_email.getText());
			ps.setString(4, txt_type.getText());
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Successfully inserted");
			
			Update();
			
			txt_id.setText("");
			txt_username.setText("");
			txt_password.setText("");
			txt_email.setText("");
			txt_type.setText("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    public void getSelected(MouseEvent event) {
    	index = table_user.getSelectionModel().getSelectedIndex();
    	if(index <= -1) {
    		return;
    	}
    	txt_id.setText(col_id.getCellData(index).toString());
    	txt_username.setText(col_username.getCellData(index).toString());
    	txt_password.setText(col_password.getCellData(index).toString());
    	txt_email.setText(col_email.getCellData(index).toString());
    	txt_type.setText(col_type.getCellData(index).toString());
    }
    
    public void Edit(ActionEvent event) {
    	try {
    		conn = sqlconnect.dbconnect();
    		String id = txt_id.getText();
    		String username = txt_username.getText();
    		String password = txt_password.getText();
    		String email = txt_email.getText();
    		String type = txt_type.getText();
    		
    		String sql = "update user set id = "+ id +", username = '"+ username +"', password = '"+ password 
    				+"', email = '"+ email +"', type = '"+ type +"' where  id = "+ id+" ";
    		ps = conn.prepareStatement(sql);
    		ps.execute();
    		
    		JOptionPane.showMessageDialog(null, "Successfully Updated");
    		
    		Update();
    		
    		txt_id.setText("");
			txt_username.setText("");
			txt_password.setText("");
			txt_email.setText("");
			txt_type.setText("");
			
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, e);
    	}
    }
    
    @FXML
    public void Delete_user(ActionEvent event) {
    	conn = sqlconnect.dbconnect();
    	
    	try {
    		
			ps = conn.prepareStatement("delete from user where id = ?");
			ps.setInt(1, col_id.getCellData(index));			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "DeletedSuccessfully");
			
			Update();
			
			txt_id.setText("");
			txt_username.setText("");
			txt_password.setText("");
			txt_email.setText("");
			txt_type.setText("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    public void Update() {
    	col_id.setCellValueFactory(new PropertyValueFactory<user, Integer>("id"));
		col_username.setCellValueFactory(new PropertyValueFactory<user, String>("username"));
		col_password.setCellValueFactory(new PropertyValueFactory<user, String>("password"));
		col_email.setCellValueFactory(new PropertyValueFactory<user, String>("email"));
		col_type.setCellValueFactory(new PropertyValueFactory<user, String>("type"));
		
		listM = sqlconnect.getDatauser();
		table_user.setItems(listM);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Update();
	}

}
