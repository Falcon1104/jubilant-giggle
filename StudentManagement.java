/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement;

/**
 *
 * @author kulka
 */

    
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

class StudentDB  extends DefaultTableModel{

    Object db[][]=new Object[50][3];
    int curCol=0; 
    public void addData(String name,int roll_no,int attendance){
        System.out.println("Function called!!"+curCol);
        db[curCol][0]=name; 
        db[curCol][1]=roll_no;
        db[curCol][2]=attendance;
        
        System.out.println(db[curCol][0]+""+db[curCol][1]+" "+db[curCol][2]);
        curCol++; 
    
    }
    

    
    
    

} 



class Gui extends JFrame{
	
	Container cp;
	JTextField name,Roll,Att,sroll,rollSearch; 
	JButton push; 
	JTabbedPane jtab;
	JPanel entry,display,search; 
	JList jl;
        JLabel fname,froll,fattendance;
         
	Gui(){
		
		super("Input");
		cp=getContentPane(); 
		setSize(getMaximumSize());
		setVisible(true);
		setLayout(new GridLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jtab=new JTabbedPane();
		jtab.setSize(getMaximumSize());
		add(jtab); 
		
		
		//entry panel 
		entry=new JPanel(); 
		
		entry.setSize(1500,1500);
		entry.setVisible(true);
		entry.setLayout(new GridLayout(4,1,25,25));
                
		
		name=new JTextField("NAME");
                
		name.setSize(50,50);
		
		entry.add(name); 
		
		jtab.add(entry,"ENTRY ");
		
		
		
		//roll no 
		Roll=new JTextField("ROLL-NO should be unique ");
		Roll.setSize(50,50);
		entry.add(Roll);
		
		
		//Attendance
		Att= new JTextField("Attendace"); 
		Att.setSize(getPreferredSize());
		entry.add(Att);
		
		//	push 
		push=new JButton("ENTER");
		push.setSize(getPreferredSize());
		entry.add(push);
		
		push.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Function called");
					
				String name1= name.getText();
				System.out.println(name1);
				
				int roll=Integer.parseInt( Roll.getText());
				int attendance=Integer.parseInt(Att.getText()); 
				System.out.println("data fetched");
				System.out.println("Name :"+name1+" Roll-no :"+roll+" Attendance : "+attendance);
				
				Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","123456");
				System.out.println("Connection succeded");
				
				PreparedStatement ps=con.prepareStatement("insert into student values(?,?,?)");
				ps.setString(1,name1);
				ps.setInt(2, roll);
				ps.setInt(3, attendance); 
				
				int i=ps.executeUpdate();
				System.out.println("rows affected "+i);
				con.close(); 
				
				
				
				
				}
				catch(Exception exp){
					
					System.out.println(exp);
					
				}
				
			}
			
			
		});
		
		
		
		
		//search
		
		search=new JPanel(); 
		jtab.add(search,"SEARCH");
		search.setLayout(new FlowLayout(FlowLayout.CENTER,50,50));
                rollSearch=new JTextField("Enter Roll Number");
                rollSearch.setSize(rollSearch.getPreferredSize()); 
                
                search.add(rollSearch,BorderLayout.NORTH);
                JButton enter=new JButton(" ENTER ");
               // enter.setSize(enter.getPreferredSize());
                search.add(enter,BorderLayout.SOUTH);
                fname=new JLabel(" NAME ");
               // fname.setSize(fname.getPreferredSize());
                search.add(fname,BorderLayout.EAST);
                
                froll=new JLabel(" ROLL NO ");
               // froll.setSize(froll.getPreferredSize());
                search.add(froll,BorderLayout.CENTER);
                
                fattendance=new JLabel( " Attendance " );
               // fattendance.setSize(fattendance.getPreferredSize());
                search.add(fattendance,BorderLayout.WEST);
                
                
                
                enter.addActionListener(new ActionListener(){
                
                    public void actionPerformed(ActionEvent ae){
                        try{
                            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","123456");
                            PreparedStatement ps=con.prepareStatement("select * from student where roll_no=?");
                            int r=Integer.parseInt(rollSearch.getText());
                            ps.setInt(1,r);
                            ResultSet rs=ps.executeQuery();
                            System.out.println("Query executed");
                            rs.next();
                            fname.setText(rs.getString("name"));
                            froll.setText(" "+rs.getInt("roll_no"));
                            fattendance.setText(""+rs.getInt("attendance"));
                        
                        
                        }
                        catch(Exception e){
                            
                            
                                
                                fname.setText("Data not found ");
                            
                            
                        
                            System.out.println(e);
                            
                        }
                        
                    }
                
                }); 
                
                // display 
               display=new JPanel(); 
               display.setLayout(new FlowLayout());
               JButton jb=new JButton("Fetch");
               display.add(jb);
               
               Object head[]={"NAME","ROLL_NO","ATTENDANCE"};
               Object data[][]={{"XXX",111,100}};
               JTable table=new JTable();
               table.setModel(new DefaultTableModel(data,head));
               table.setVisible(true);
               display.add(table); 
               
               jtab.add(display,"DISPLAY"); 
		
               jb.addActionListener(new ActionListener(){
                   
                   public void actionPerformed(ActionEvent ae){
                       try{
                           
                           Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","123456");
                           Statement st=con.createStatement();
                           ResultSet rs=st.executeQuery("SELECT * FROM student"); 
                           DefaultTableModel dtm=(DefaultTableModel)table.getModel();
                           
                           Object row[]=new Object[3]; 
                           
                           while(rs.next()){
                               row[0]=rs.getString("name");
                               row[1]=rs.getInt("roll_no");
                               row[2]=rs.getInt("attendance");
                               dtm.addRow(row);
                           }
                       
                       }
                       catch(Exception e)
                       {
                           System.out.println(e);
                       }
                   
                   
                   }
                   
               
               
               
               });
               
		
	}
	
	
	
	
}
public class StudentManagement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Gui();
        
    }
    
}


