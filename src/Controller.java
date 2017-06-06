import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import cn.dsna.util.images.*;
import java.awt.image.*;


/**
 * Created by Prophet on 6/5/2017.
 */
public class Controller {
    private static UserInterface userInterface;
    private static Controller controller;
    public static ArrayList<User> Users = new ArrayList<User>();
    public static User currentUser;
    public static String verificationCode;




    static void init(){
        UserInterface ui = new UserInterface();
        Controller.controller = new Controller();
        ui.addUserButton.setEnabled(false);
        Controller.userInterface = ui;
        AddButtonListener addButtonListener = new AddButtonListener();
        addButtonListener.userInterface = ui;
        ui.addUserButton.addActionListener(addButtonListener);
        LoginButtonListener loginButtonListener = new LoginButtonListener();
        loginButtonListener.userInterface = ui;
        ui.loginButton.addActionListener(loginButtonListener);
        PlayButtonListener playButtonListener = new PlayButtonListener();
        playButtonListener.userInterface = ui;
        ui.PLAYButton.addActionListener(playButtonListener);
        Users.add(new User("admin", "123456", "Computer Center Staff"));
        LogOutButtonListener logOutButtonListener = new LogOutButtonListener();
        logOutButtonListener.userInterface = ui;
        ui.logOutButton.addActionListener(logOutButtonListener);
        verificationCode = controller.ValidateCodeGenerator();
        ImageIcon pic1 = new ImageIcon("src/VerificationCode.png");
        ui.vlable.setIcon(pic1);
        ChangeButtonListener changeButtonListener = new ChangeButtonListener();
        changeButtonListener.userInterface = ui;
        ui.changeButton.addActionListener(changeButtonListener);
    }

    public static Controller getController(){
        return controller;
    }

    public String ValidateCodeGenerator(){
        ValidateCode codeGen = new ValidateCode(160,40,4,100);
        String code = codeGen.getCode();
        try{
            codeGen.write("src/VerificationCode.png");
        }catch (IOException e){
            e.printStackTrace();
        }
        return code;
    }



}

class AddButtonListener implements ActionListener {
    public UserInterface userInterface;
    @Override
    public void actionPerformed(ActionEvent e){
        Controller controller = Controller.getController();
        String username = userInterface.Add_Username.getText();
        String password = String.valueOf(userInterface.Add_pwd.getPassword());
        String userType = userInterface.add_userType.getSelectedItem().toString();
        for (User user: controller.Users) {
            if (username.equals(user.username)){
                JOptionPane.showMessageDialog(null,"User Already Exist!", "ERROR", JOptionPane.ERROR_MESSAGE);
                userInterface.Add_Username.setText("");
                userInterface.Add_pwd.setText("");
                return;
            }
        }
        User newUser = new User(username, password, userType);
        String[] newUserInfo = new String[3];
        newUserInfo[0] = newUser.username;
        newUserInfo[1] = newUser.getUserType();
        newUserInfo[2] = newUser.getId();
        ((DefaultTableModel) (userInterface.table1.getModel())).addRow(newUserInfo);
        controller.Users.add(new User(username, password, userType));
        userInterface.Add_Username.setText("");
        userInterface.Add_pwd.setText("");
    }
}

class LoginButtonListener implements  ActionListener {
    public UserInterface userInterface;
    @Override
    public void actionPerformed(ActionEvent e){
        Controller controller = Controller.getController();
        String username = userInterface.username.getText();
        String password = String.valueOf(userInterface.pwd.getPassword());
        String verificationCode = userInterface.vcodeString.getText();
        if(verificationCode.equals(controller.verificationCode)){
            for (User user: controller.Users) {
                if(username.equals(user.username)){
                    if(password.equals(user.getPassword())){
                        JOptionPane.showMessageDialog(null, "Login Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        if(user.getUserType().equals("Computer Center Staff")) userInterface.addUserButton.setEnabled(true);
                        else if(user.getUserType().equals("Faculty members")) userInterface.addUserButton.setEnabled(false);
                        else userInterface.addUserButton.setEnabled(false);
                        userInterface.UserLable.setText(user.username);
                        userInterface.UserGroup.setText(user.getUserType());
                        controller.currentUser = user;
                        userInterface.username.setText("");
                        userInterface.pwd.setText("");
                        userInterface.vcodeString.setText("");
                        controller.verificationCode = controller.ValidateCodeGenerator();
                        ImageIcon pic2 = new ImageIcon("src/VerificationCode.png");
                        pic2.getImage().flush();
                        userInterface.vlable.setIcon(pic2);
                        return;
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Wrong password! Please check your input!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        userInterface.username.setText("");
                        userInterface.pwd.setText("");
                        userInterface.vcodeString.setText("");
                        controller.verificationCode = controller.ValidateCodeGenerator();
                        ImageIcon pic2 = new ImageIcon("src/VerificationCode.png");
                        pic2.getImage().flush();
                        userInterface.vlable.setIcon(pic2);
                        return;
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Wrong Validate Code! Please input again!", "ERROR", JOptionPane.ERROR_MESSAGE);
            userInterface.vcodeString.setText("");
            controller.verificationCode = controller.ValidateCodeGenerator();
            ImageIcon pic2 = new ImageIcon("src/VerificationCode.png");
            pic2.getImage().flush();
            userInterface.vlable.setIcon(pic2);
            return;
        }

        JOptionPane.showMessageDialog(null,"No Such User! Please check your input!", "ERROR", JOptionPane.ERROR_MESSAGE);
        userInterface.username.setText("");
        userInterface.pwd.setText("");
        controller.verificationCode = controller.ValidateCodeGenerator();
        ImageIcon pic2 = new ImageIcon("src/VerificationCode.png");
        pic2.getImage().flush();
        userInterface.vlable.setIcon(pic2);
        return;
    }
}

class PlayButtonListener implements ActionListener{
    public  UserInterface userInterface;
    @Override
    public void actionPerformed(ActionEvent e){
        Controller controller = Controller.getController();
        try{
            if(controller.currentUser.getUserType().equals("Computer Center Staff")){
                JOptionPane.showMessageDialog(null, "Congratulations! You can play all the times!", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(controller.currentUser.getUserType().equals("Faculty members")){
                JOptionPane.showMessageDialog(null, "Oops! You can play from 5P.M. to 8A.M!", "INFO", JOptionPane.INFORMATION_MESSAGE);

            }
            else{
                JOptionPane.showMessageDialog(null, "Sorry! You can only play from 10P.M. to 6A.M!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                userInterface.addUserButton.setEnabled(false);
            }
        }catch(NullPointerException e1){
            JOptionPane.showMessageDialog(null,"Please login before you play!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class LogOutButtonListener implements ActionListener{
    public  UserInterface userInterface;
    @Override
    public void actionPerformed(ActionEvent e){
        Controller controller = Controller.getController();
        controller.currentUser = null;
        userInterface.UserLable.setText("Not Logined");
        userInterface.UserGroup.setText("Unknown");
        userInterface.addUserButton.setEnabled(false);
    }
}

class ChangeButtonListener implements ActionListener{
    public  UserInterface userInterface;
    @Override
    public void actionPerformed(ActionEvent e){
        Controller controller = Controller.getController();
        controller.verificationCode = controller.ValidateCodeGenerator();
        ImageIcon pic2 = new ImageIcon("src/VerificationCode.png");
        pic2.getImage().flush();
        userInterface.vlable.setIcon(pic2);
        userInterface.vcodeString.setText("");
    }
}