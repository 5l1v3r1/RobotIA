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
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;

public class MiFrame extends JFrame {

	private DrawPanel drawPanel = new DrawPanel(0, 0);
	private ButtonPanel buttonPanel = new ButtonPanel();
	private double sca = 1.0;

	public MiFrame() {
		addCaracteristicas();
	}

	public void addCaracteristicas() {
		setSize(new Dimension(800,600));
		setLocation(new Point(200,200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane scrollPane = new JScrollPane(drawPanel);
		add(scrollPane);
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

			int tS = (int)(32 * sca);
			

			for(int y = 0; y < layer.getMap().length; y++){

				for(int x=0; x < layer.getMap()[y].length; x++){

					if(layer.getMap()[y][x] == TileLayer.Elementos.TERRENO.ordinal()){
						g.drawImage(new ImageIcon("img/tierra.png").getImage(),x*tS,y*tS,tS,tS,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBSTACULO_ARRIBA.ordinal()){
						g.drawImage(new ImageIcon("obstaculoArriba.jpg").getImage(),x*tS,y*tS,tS,tS,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBSTACULO_CENTRO.ordinal()){
						g.drawImage(new ImageIcon("obstaculoCentro.jpg").getImage(),x*tS,y*tS,tS,tS,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBSTACULO_ABAJO.ordinal()){
						g.drawImage(new ImageIcon("obstaculoAbajo.jpg").getImage(),x*tS,y*tS,tS,tS,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.ROBOT.ordinal()){
						g.drawImage(new ImageIcon("robot.jpg").getImage(),x*tS,y*tS,tS,tS,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBSTACULO.ordinal()){
						g.drawImage(new ImageIcon("cocheLlamas.png").getImage(),x*tS,y*tS,tS,tS,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBS_COR_DW_R.ordinal()){
						g.drawImage(new ImageIcon("obstaculoEsquinaInfDer.jpg").getImage(),x*tS,y*tS,tS,tS,null);
					}


					if(layer.getMap()[y][x] == TileLayer.Elementos.OBS_COR_DW_L.ordinal()){
						g.drawImage(new ImageIcon("obstaculoEsquinaInfIzq.jpg").getImage(),x*tS,y*tS,tS,tS,null);
					}

					if(layer.getMap()[y][x] == TileLayer.Elementos.OBS_COR_UP_L.ordinal()){
						g.drawImage(new ImageIcon("obstaculoEsquinaSup.jpg").getImage(),x*tS,y*tS,tS,tS,null);
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
		private JLabel etiquetaEscala = new JLabel("Escala");

		private JSpinner filas = new JSpinner(new SpinnerNumberModel(1,1,1000,1));
		private JSpinner columnas = new JSpinner(new SpinnerNumberModel(1,1,1000,1));
		private JSpinner escala = new JSpinner(new SpinnerNumberModel(1.0,0.0,1.0,0.1));
		
		private JButton generar = new JButton("Generar");
		private JButton comenzar = new JButton("Comenzar");
		
		private String[] elementos = {"Obstáculo", "Obstáculo arriba", "Obstáculo abajo", "Obstáculo centro", "Robot", "Terreno",
		"Esquina Inf Der","Esquina Inf Izq", "Esquina Sup Izq"};
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
			add(etiquetaEscala);
			add(escala);
			add(obstaculos);
			add(comenzar);
		}
		
		public void addActionListener() {
			generar.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if(arg0.getSource() == generar) {

				double auxSca = drawPanel.getLayer().getSca();

				drawPanel.getLayer().setSca((double)escala.getValue());
				sca = drawPanel.getLayer().getSca();

				if(auxSca == sca)
					drawPanel.getLayer().reiniciar((int)filas.getValue(), (int)columnas.getValue());

				drawPanel.repaint();

			}

			if(arg0.getSource() == comenzar){

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
				

				int i = e.getX(); // Obteneoms la coordena x
				int j = e.getY(); // Obtenemos la coordena y

				int tS = (int)(32 * sca); //tileSize
				

				switch(buttonPanel.getComboBox().getSelectedIndex()) {
				case 0:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBSTACULO.ordinal();
					break;
				case 1:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBSTACULO_ARRIBA.ordinal();
					break;
				case 2:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBSTACULO_ABAJO.ordinal();
					break;
				case 3:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBSTACULO_CENTRO.ordinal();
					break;
				case 4:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.ROBOT.ordinal();
					break;
				case 5:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.TERRENO.ordinal();
					break;
				case 6:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBS_COR_DW_R.ordinal();
					break;
				case 7:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBS_COR_DW_L.ordinal();
					break;
				case 8:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBS_COR_UP_L.ordinal();
					break;
				}

				drawPanel.repaint();
			}
		}


		//Funciones agregadas por NetBeans, no son necesarias para nuestro caso, pero conviene tenerlas por si las moscas
		public void mouseDragged(MouseEvent e) {

			if((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
				

				int i = e.getX(); // Obteneoms la coordena x
				int j = e.getY(); // Obtenemos la coordena y

				int tS = (int)(32 * sca); //tileSize
				

				switch(buttonPanel.getComboBox().getSelectedIndex()) {
				case 0:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBSTACULO.ordinal();
					break;
				case 1:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBSTACULO_ARRIBA.ordinal();
					break;
				case 2:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBSTACULO_ABAJO.ordinal();
					break;
				case 3:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBSTACULO_CENTRO.ordinal();
					break;
				case 4:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.ROBOT.ordinal();
					break;
				case 5:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.TERRENO.ordinal();
					break;
				case 6:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBS_COR_DW_R.ordinal();
					break;
				case 7:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBS_COR_DW_L.ordinal();
					break;
				case 8:
					drawPanel.getLayer().getMap()[j/tS][i/tS] = TileLayer.Elementos.OBS_COR_UP_L.ordinal();
					break;
				}

				drawPanel.repaint();
			}



		}
		public void mouseMoved(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}

	
}
