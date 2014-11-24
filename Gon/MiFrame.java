import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MiFrame extends JFrame {
	private DrawPanel drawPanel = new DrawPanel(0, 0);
	private ButtonPanel buttonPanel = new ButtonPanel();
	
	public MiFrame() {
		addCaracteristicas();
	}

	public void addCaracteristicas() {
		setSize(new Dimension(800,600));
		setLocation(new Point(200,200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(drawPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
	}

	public class DrawPanel extends JPanel {
		private TileLayer layer;

		public DrawPanel(int n, int m) {
			layer = new TileLayer(n, m);
			addMouseListener(new NewEventoRaton());
		}


		@Override 
		public void paint(Graphics g){
			super.paintComponent(g);
			DrawLayer(g);
		}

		public void DrawLayer(Graphics g){

			for(int y = 0; y < layer.getMap().length; y++){

				for(int x=0; x < layer.getMap()[y].length; x++){

					if(layer.getMap()[y][x] == TileLayer.Elementos.TERRENO.ordinal()){

						g.drawImage(new ImageIcon("terreno.jpg").getImage(),x*32,y*32,32,32,null);

					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBSTACULO_ARRIBA.ordinal()){

						g.drawImage(new ImageIcon("obstaculoArriba.jpg").getImage(),x*32,y*32,32,32,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBSTACULO_CENTRO.ordinal()){

						g.drawImage(new ImageIcon("obstaculoCentro.jpg").getImage(),x*32,y*32,32,32,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBSTACULO_ABAJO.ordinal()){

						g.drawImage(new ImageIcon("obstaculoAbajo.jpg").getImage(),x*32,y*32,32,32,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.ROBOT.ordinal()){

						g.drawImage(new ImageIcon("robot.jpg").getImage(),x*32,y*32,32,32,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBSTACULO.ordinal()){

						g.drawImage(new ImageIcon("obstaculo.jpg").getImage(),x*32,y*32,32,32,null);
					}

				}
			}

		}


		public TileLayer getLayer() {
			return layer;
		}


		public void setLayer(TileLayer layer) {
			this.layer = layer;
		}
	}
	
	public class ButtonPanel extends JPanel implements ActionListener {
		private JLabel etiquetaFilas = new JLabel("Filas");
		private JLabel etiquetaColumnas = new JLabel("Columnas");
		private JSpinner filas = new JSpinner(new SpinnerNumberModel(1,1,1000,1));
		private JSpinner columnas = new JSpinner(new SpinnerNumberModel(1,1,1000,1));
		
		private JButton generar = new JButton("Generar");
		
		private String[] elementos = {"Obstáculo", "Obstáculo arriba", "Obstáculo abajo", "Obstáculo centro", "Robot", "Terreno"};
		private JComboBox<String> obstaculos = new JComboBox(elementos);
		
		public ButtonPanel() {
			setVisible(true);
			addActionListener();
			addComponents();
		}
		
		public void addComponents() {
			add(etiquetaFilas);
			add(filas);
			add(etiquetaColumnas);
			add(columnas);
			add(generar);
			add(obstaculos);
		}
		
		public void addActionListener() {
			generar.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource() == generar) {
				drawPanel.getLayer().reiniciar((int)filas.getValue(), (int)columnas.getValue());
				drawPanel.repaint();
			}
		}
		
		public JComboBox<String> getComboBox() {
			return obstaculos;
		}
	}
	
	class NewEventoRaton implements MouseListener, MouseMotionListener {


		public NewEventoRaton() {

		}
		public void mouseClicked(MouseEvent e) {
			if((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
				// TODO Auto-generated method stub

				int i = e.getX(); // Obtenemso la coordena x
				int j = e.getY(); // Obtenemso la coordena y
				switch(buttonPanel.getComboBox().getSelectedIndex()) {
				case 0:
					/* Añadimos un obstaculo */
					drawPanel.getLayer().getMap()[j/32][i/32] = TileLayer.Elementos.OBSTACULO.ordinal();
					break;
				case 5:
					/* Añadimos un terreno */
					drawPanel.getLayer().getMap()[j/32][i/32] = TileLayer.Elementos.TERRENO.ordinal();
					break;
				}
				drawPanel.repaint();
			}
		}
		public void mouseDragged(MouseEvent arg0) {}
		public void mouseMoved(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}

	
}
