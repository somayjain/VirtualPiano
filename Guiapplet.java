import javax.sound.midi.*;
import javax.swing.*;
import java.applet.Applet;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Guiapplet extends JApplet
{
	long time1 = System.currentTimeMillis();
	static Gui gui;
	private int  width = Toolkit.getDefaultToolkit().getScreenSize().width;
	
	public void start()
	{
		gui= new Gui();
		try 
		{
            SwingUtilities.invokeAndWait(new Runnable() 
            {
                public void run() 
                {
                	gui.setOpaque(true); 
                	setSize(width, 300);
                    setContentPane(gui);
                }
            });
        } 
		catch (Exception e) 
		{ 
            System.err.println("createGUI didn't complete successfully");
        }
		
	}
  	
	//	MouseAdapter my= new MouseAdapter();
		/*  Playback logic after recording.
		for(i=0; i<5; i++)
		{
			gui.play(60+i);
		}
		*/
		//play(60, 4, 0);
	
public class Gui extends JPanel
{
	int count=0;
	JLabel oval;
	JButton pl;
	JToggleButton rec;
	JButton[] array;
	JButton[] black;
	JButton demo;
	Sequencer player;
	Sequence seq;
	Track track;
	int flag=-1; //  means not recording. 
	private JLayeredPane pane;
	Queue<Integer> qe;
	private int num;
	Gui()
	{
			super();
			this.pane= new JLayeredPane();
			pane.setPreferredSize(new Dimension(width, 300));
			pane.setBackground(Color.BLACK);
			pane.setOpaque(true);
			
			this.num = width/32;
			
			array=new JButton[num];
			black=new JButton[num];

			int i;
			for(i=0; i<num; i++)
			{
				array[i] = new JButton();
				array[i].setOpaque(true);
				array[i].setBorderPainted(false);
				array[i].setBackground(Color.WHITE);
				array[i].setBounds( 32*i, 30,  30, 300 );
				pane.add(array[i], (Integer)15);
				array[i].addMouseListener(new white());
			}
			
			for(i=0; i<num; i++)
			{
				int octave = i/5;
				int note=i%5;
				if(note>1)
					note++;
				black[i] = new JButton();
				black[i].setOpaque(true);
				black[i].setBorderPainted(false);
				black[i].setBackground(Color.BLACK);
				black[i].setBounds( 7*octave*32 + 19+32*note, 30,  15, 175 );
				pane.add(black[i], (Integer)20);
				black[i].addMouseListener(new black());
			}
			demo= new JButton("Demo");
			demo.setBounds(100,0, 250, 30 );
			pane.add(demo, (Integer) 0);
			demo.addActionListener(new demo());
			

			rec= new JToggleButton("Record");
			rec.setBounds(450,0, 250, 30 );
			pane.add(rec, (Integer) 0);
			rec.addActionListener(new record());
			
			pl= new JButton("Play");
			pl.setBounds(850,0, 250, 30 );
			pane.add(pl, (Integer) 0);
			pl.addActionListener(new play());
		
			this.add(pane);
			
			try
			{
				player=MidiSystem.getSequencer();
				player.open();
				seq=new Sequence(Sequence.PPQ, 4);
				track=seq.createTrack();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			


	}
	
	class record implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			if(flag==-1)
			{
				qe= new LinkedList<Integer>();
				
				
			}
			flag=flag*-1;
		}
	}
	class play implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			Iterator it=qe.iterator();
			while(it.hasNext())
	        {
	            int iteratorValue=(Integer) it.next();
	            gui.play(iteratorValue);
	        }
		}
	}

	class white implements MouseListener
	{
		public void mousePressed(MouseEvent event)
		{
			int i;
			//System.out.println("presed"+ event.getSource());
			for(i=0; i<num; i++)
			{
				if(event.getSource()==array[i])
				{
					array[i].setBackground(Color.RED);
					int octave=i/7;
					int note= i%7;
					int offset=2*note;
					if(note>2)
						offset--;
					gui.play(36+12*octave+offset);
					if(flag==1)
					{
						qe.add((Integer)36+12*octave+offset);
					}
			//		System.out.println(System.currentTimeMillis()/1000);
				}
			}
		}
		public void mouseClicked(MouseEvent arg0) 
		{
			
		}
		public void mouseEntered(MouseEvent arg0) 
		{
		}
		public void mouseExited(MouseEvent arg0) 
		{
		}
		public void mouseReleased(MouseEvent event) 
		{
			int i;
			for(i=0; i<num; i++)
			{
				if(event.getSource()==array[i])
					array[i].setBackground(Color.WHITE);
			}
		}
	}
	
	class demo implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			gui.play(60);gui.play(60);gui.play(62);gui.play(60);gui.play(65);gui.play(64);
			count++;
			gui.play(60);gui.play(60);gui.play(62);gui.play(60);gui.play(67);gui.play(65);
			count++;
			gui.play(60);gui.play(60);gui.play(69);gui.play(67);gui.play(65);gui.play(64);gui.play(62);
			count++;
			gui.play(69);gui.play(69);gui.play(67);gui.play(64);gui.play(67);gui.play(65);
		}
	}
	class black implements MouseListener
	{
		public void mousePressed(MouseEvent event)
		{
			int i;
			for(i=0; i<num; i++)
			{
				if(event.getSource()==black[i])
				{
					black[i].setBackground(Color.RED);
					int octave = i/5;
					int note = i%5;
					int offset= 2*note;
					if(note >1)
						offset++;
					gui.play(37+12*octave+offset);
					if(flag==1)
					{
						qe.add((Integer)37+12*octave+offset);
					}
				}
			}
		}
		public void mouseClicked(MouseEvent arg0) 
		{
		}
		public void mouseEntered(MouseEvent arg0) 
		{
		}
		public void mouseExited(MouseEvent arg0) 
		{
		}
		public void mouseReleased(MouseEvent event) {
			int i;
			for(i=0; i<num; i++)
			{
				if(event.getSource()==black[i])
					black[i].setBackground(Color.BLACK);
			}
		}
	}
	
	
	public void play(int note)
	{
		try
		{
			ShortMessage a=new ShortMessage();
			a.setMessage(144, 1, note, 127);
			MidiEvent noteOn = new MidiEvent(a, 0+count*4);
			track.add(noteOn);
			
			ShortMessage b =new ShortMessage();
			b.setMessage(128, 1, note, 127); 
			MidiEvent noteOff = new MidiEvent(b, 4+count*4); 
			track.add(noteOff);
			count++;
			player.setSequence(seq);
			player.start();
			
//			System.out.println(note);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
}



