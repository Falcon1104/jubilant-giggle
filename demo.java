package student1;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;



class Gui extends JFrame{
	
	Container cp;
	JTextField name,Roll,Att,sroll; 
	JButton push; 
	JTabbedPane jtab;
	JPanel entry,display,search; 
	JList jl;
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
		entry.setLayout(new GridLayout(4,1));
		
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
				
				Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","root");
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
		search.setLayout(new FlowLayout());
		
		
	}
	
	
	
	
}


public class demo  {
	
	public static void main(String args[])
	{
	   new Gui();
	}
}
