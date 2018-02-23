import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.*; import java.io.*;
@SuppressWarnings("serial")
public class JPanelTest extends JFrame{
	
	public static void main(String[] args) throws IOException{ 
		ArrayList<Integer> numberPerRow = new ArrayList<Integer>();
		ArrayList<String> className = new ArrayList<String>();
	    ArrayList<ArrayList<String>> attributeName = new ArrayList<ArrayList<String>>();
	    ArrayList<ArrayList<String>> methodName = new ArrayList<ArrayList<String>>();
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);
	    
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JFrame frameOne = new JFrame("Path to Analysis");
				JFrame frameErr = new JFrame("Path Error!!");frameErr.setSize(600, 200); frameErr.add(new JButton("Path is INCORECT!!"));
				frameErr.setVisible(false);
				JPanel jp2 = new JPanel();
				JButton button = new JButton("Choose File ... ") ;
				
				frameOne.setSize(1366, 720); frameOne.setVisible(true);
				frameOne.setLayout(new GridLayout(2, 1));
				frameOne.setDefaultCloseOperation(EXIT_ON_CLOSE);
				jp2.setLayout(null);
				button.setBounds(625, 10, 110, 40);
				frameOne.add(new ImgInsert("./test/img01.jpg"));
				jp2.add(button);
 				
				frameOne.add(jp2);
				button.addActionListener(new ActionListener()
					{
					public void actionPerformed(ActionEvent a) {
						JFileChooser jfc = new JFileChooser();
						jfc.setAcceptAllFileFilterUsed(false);
						jfc.setCurrentDirectory(new File("C:/Users/Admin/workspace/"));
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int value = jfc.showOpenDialog(null);
						if (value == JFileChooser.APPROVE_OPTION) {
							File getDirectory = jfc.getCurrentDirectory(); 
							frameOne.setVisible(false);
							System.out.println(getDirectory.toString());
							ProjectAnalysis test1 = new ProjectAnalysis();
							test1.setProjectPath(getDirectory);
							System.out.println(getDirectory.toString());
							test1.analyseProject(className, attributeName, methodName, numberPerRow);
							TestPaintComponent test = new TestPaintComponent();
							test.setData(className, attributeName, methodName, className.size(), numberPerRow);
							test.setFontSize(10);
							test.setyD(); test.setpXpY(25);
							JFrame frameTwo = new JFrame("Diagram");
							frameTwo.setSize(1366, 720);
							JScrollPane jsp = new JScrollPane();
							jsp.setViewportView(test);
							jsp.setSize(500,500); jsp.setVisible(true);
							frameTwo.setVisible(true); frameTwo.setSize(1920, 1010); frameTwo.setDefaultCloseOperation(EXIT_ON_CLOSE);
							frameTwo.setLocationRelativeTo(null);
							frameTwo.add(jsp);
							
						}
					
					
					}	
				});		
			}
		});
	}
}
//C:/Users/Admin/workspace/Tuan08