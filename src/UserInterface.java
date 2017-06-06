import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Created by Prophet on 6/5/2017.
 */
public class UserInterface
{
    public JTable table1;
    public JButton loginButton;
    public JButton addUserButton;
    public JButton PLAYButton;
    public JLabel UserLable;
    public JScrollPane UserTable;
    public JPanel MainPanel;
    public JTextField username;
    public JComboBox add_userType;
    public JTextField Add_Username;
    public JPasswordField pwd;
    public JPasswordField Add_pwd;
    public JLabel UserGroup;
    public JButton logOutButton;
    public JPanel VerificationPanel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String[] columnNames = {"UserName", "UserType", "userID"};
        User admin = new User("admin", "123456", "Computer Center Staff");
        String[] UserInfo = new String[3];
        UserInfo[0] = admin.username;
        UserInfo[1] = admin.getUserType();
        UserInfo[2] = admin.getId();
        Object[][] userTable = {UserInfo};
        DefaultTableModel UserModel = new DefaultTableModel(userTable, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1 = new JTable(UserModel);
        DefaultTableCellRenderer r   =   new   DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table1.setDefaultRenderer(Object.class,   r);
    }

    public UserInterface(){
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(this.MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
