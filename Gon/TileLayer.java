import javax.swing.ImageIcon;


public class TileLayer {
	public static enum Elementos {TERRENO, OBSTACULO_ARRIBA, OBSTACULO_CENTRO, OBSTACULO_ABAJO, ROBOT ,OBSTACULO};
	private int[][] map;
	private int n;
	private int m;
	private ImageIcon[] texturas;

	public TileLayer(int[][] existingMap){
		n = existingMap.length;
		m = existingMap[0].length;
		map = new int[existingMap.length][existingMap[0].length];

		for(int y=0; y < map.length; y++){

			for(int x = 0; x < map[y].length; x++){

				map[y][x] = existingMap[y][x];

			}

		}
	}

	public TileLayer(int n, int m) {
		this.n = n;
		this.m = m;
		map = new int[n][m];
	}
	
	public void reiniciar(int n, int m) {
		this.n = n;
		this.m = m;
		map = new int[n][m];
	}

	public int[][] getMap() {
		return map;
	}
	

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}
	
}
