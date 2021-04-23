package Court_Module;

import DatabaseConnector_TableCreator.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Court_Opposition_Update {
    static ResultSet rs;
    static ResultSet case_details;
    static int n;
    static int advocate_id;
    static String advocate_name="";
    static String party_name="";
    static String party_phone="";

    Court_Opposition_Update(ResultSet rs,ResultSet case_details,int n){
        Court_Opposition_Update.rs=rs;
        Court_Opposition_Update.case_details=case_details;
        Court_Opposition_Update.n=n;
    }

    void court_opposition_update_display(){

        final JFrame f=new JFrame("Opposition Update");
        f.setLayout(null);
        f.setResizable(false);
        f.setBounds(420,170,740,520);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel heading=new JLabel("Opposition Update");
        heading.setBounds(280,10+50,150,20);
        f.add(heading);

        JLabel cnr=new JLabel("CNR NO");
        cnr.setBounds(50,50+50,120,20);
        f.add(cnr);

        JTextField get_cnr=new JTextField();
        get_cnr.setBounds(110,50+50,120,20);
        get_cnr.setEditable(false);
        f.add(get_cnr);


        JLabel casetype=new JLabel("CASE TYPE");
        casetype.setBounds(250,50+50,120,20);
        f.add(casetype);

        JTextField get_casetype=new JTextField();
        get_casetype.setBounds(330,50+50,120,20);
        get_casetype.setEditable(false);
        f.add(get_casetype);

        JLabel courtid=new JLabel("COURT ID");
        courtid.setBounds(470,50+50,120,20);
        f.add(courtid);

        JTextField get_cid=new JTextField();
        get_cid.setBounds(540,50+50,120,20);
        get_cid.setEditable(false);
        f.add(get_cid);

        JLabel hdate=new JLabel("HEARING DATE");
        hdate.setBounds(110,100+50,120,20);
        f.add(hdate);

        JTextField get_hdate=new JTextField();
        get_hdate.setBounds(250,100+50,120,20);
        get_hdate.setEditable(false);
        f.add(get_hdate);

        JLabel password=new JLabel("PASSWORD ");
        password.setBounds(400,100+50,120,20);
        f.add(password);

        JTextField get_password=new JTextField();
        get_password.setBounds(500,100+50,120,20);
        get_password.setEditable(false);
        f.add(get_password);

        JLabel partyname=new JLabel("PARTY NAME");
        partyname.setBounds(220,160+50,120,20);
        f.add(partyname);

        final JTextField get_partyname=new JTextField();
        get_partyname.setBounds(330,160+50,120,20);
        f.add(get_partyname);

        JLabel partyphone=new JLabel("PARTY PHONE");
        partyphone.setBounds(220,200+50,120,20);
        f.add(partyphone);

        final JTextField get_partyphone=new JTextField();
        get_partyphone.setBounds(330,200+50,120,20);
        f.add(get_partyphone);

        JLabel adv_id=new JLabel("ADVOCATE ID");
        adv_id.setBounds(220,240+50,120,20);
        f.add(adv_id);

        final JTextField get_adv_id=new JTextField();
        get_adv_id.setBounds(330,240+50,120,20);
        f.add(get_adv_id);

        JLabel adv_name=new JLabel("ADVOCATE NAME");
        adv_name.setBounds(220,280+50,120,20);
        f.add(adv_name);

        final JTextField get_adv_name=new JTextField();
        get_adv_name.setBounds(330,280+50,120,20);
        get_adv_name.setEditable(false);
        f.add(get_adv_name);


        JButton back=new JButton("BACK");
        back.setBounds(20,20,70,20);
        f.add(back);

        JButton update=new JButton("UPDATE");
        update.setBounds(260,320+50,160,20);
        f.add(update);

        try{
            get_cnr.setText("CNR"+case_details.getInt("case_id"));
            get_cid.setText(""+case_details.getInt("court_id"));
            get_password.setText(case_details.getString("case_password"));
            get_hdate.setText(case_details.getString("next_hearing"));
            get_casetype.setText(case_details.getString("case_type"));
        }
        catch (Exception e){
            //do nothing
        }

        f.setVisible(true);

        get_adv_id.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                try {
                    get_adv_name.setText(new Court_Get_Advocate().get_advocate(get_adv_id.getText()));
                }
                catch (Exception ex){
                    System.out.println(ex);
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new Court_Get_CNR(rs,n).court_get_cnr_display();
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    advocate_id = Integer.parseInt(get_adv_id.getText());
                    advocate_name = get_adv_name.getText();
                    party_name = get_partyname.getText();
                    party_phone = get_partyphone.getText();
                    if (get_adv_id.getText().equals("")) {
                        JOptionPane.showMessageDialog(f, "Advocate ID Empty", "Alert", JOptionPane.WARNING_MESSAGE);
                    } else if (case_details.getInt("advocate_id") == advocate_id) {
                        JOptionPane.showMessageDialog(f, "Same Advocate cannot Oppose", "Alert", JOptionPane.WARNING_MESSAGE);
                    } else if (party_name.equals("")) {
                        JOptionPane.showMessageDialog(f, "Party Name Empty", "Alert", JOptionPane.WARNING_MESSAGE);
                    } else if (party_phone.equals("")) {
                        JOptionPane.showMessageDialog(f, "Party Phone Empty", "Alert", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int a = JOptionPane.showConfirmDialog(f, "Are you sure, Do you want to Update Case?");
                        if (a == JOptionPane.YES_OPTION) {
                            new Court_Opposition_Update(rs, case_details,2).court_opposition_update_database();
                            JOptionPane.showMessageDialog(f, " Opposition has been Updated");
                            f.dispose();
                            new Court_Case_Copy(rs, case_details,2).court_case_copy_display();
                        }
                    }
                }
                catch(Exception ex){
                    System.out.println(ex);
                }
            }
        });

    }

    void court_opposition_update_database(){

        Connection con=new Connector().establish_connection();
        try{
            int r;
            String query="UPDATE cases SET opp_party_name='"+party_name+"',"+
                    "opp_party_phone='"+party_phone+"',"+
                    "opp_advocate_name='"+advocate_name+"',"+
                    "opp_advocate_id="+advocate_id+" WHERE case_id="+case_details.getInt("case_id");
            Statement smt=con.createStatement();
            smt.executeUpdate(query);


            query="SELECT advocate_total_cases FROM advocates WHERE advocate_id="+advocate_id;
            ResultSet adv;
            adv=smt.executeQuery(query);
            adv.next();
            r=adv.getInt("advocate_total_cases");
            r=r+1;
            query="UPDATE advocates SET advocate_total_cases="+r+" WHERE advocate_id="+advocate_id;
            r=smt.executeUpdate(query);

            query="SELECT * FROM cases WHERE case_id="+case_details.getInt("case_id");
            case_details=smt.executeQuery(query);
            case_details.next();
            System.out.println(case_details.getString("opp_party_name"));
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
