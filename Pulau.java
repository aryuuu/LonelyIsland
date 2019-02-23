import java.io.*;
import java.util.*;

public class Pulau{
	//member data
	private int nPulau;
	private int nJembatan;
	private int start;
	private int[][] Jembatan;
	//daftar pulau dimana pemain bisa terjebak
	private Set<Integer> DeadEnd;

	//ctor
	Pulau(String fileName) throws Exception{
		File sauce = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(sauce));

		//mengolah data dari baris pertama 
		String temp = br.readLine();
		String[] firstLine = temp.split("\\s+");
		int nPulau = Integer.parseInt(firstLine[0]);
		int nJembatan = Integer.parseInt(firstLine[1]);
		int start = Integer.parseInt(firstLine[2]);


		// File sauce = new File(fileName);
		// BufferedReader br = new BufferedReader(new FileReader(sauce));

		// String temp = br.readLine();
		// String[] firstLine = temp.split("\\s+");
		// int nPulau = Integer.parseInt(firstLine[0]);
		// int nJembatan = Integer.parseInt(firstLine[1]);
		// int start = Integer.parseInt(firstLine[2]);




		this.nPulau = nPulau;
		this.nJembatan = nJembatan;
		this.start = start;
		this.DeadEnd = new HashSet<Integer>();

		this.Jembatan = new int[nPulau+1][nPulau+1];
		for (int i = 1; i <= nPulau; i++) {
			for (int j = 1; j <= nPulau; j++) {
				this.Jembatan[i][j] = 0;
			}
		}

		int row, col;
		for(int i = 0; i < nJembatan; i++){
			temp = br.readLine();
			String[] line = temp.split("\\s+");
			row = Integer.parseInt(line[0]);
			col = Integer.parseInt(line[1]);

			this.Jembatan[row][col] = 1;
		}
	}

	//mengembalikan true jika pulau s terhubung ke pulau d
	boolean isConnected(int s, int d){
		return this.Jembatan[s][d] == 1;
	}

	public int getnPulau(){
		return this.nPulau;
	}

	public void getDeadEnd(){
		if(!this.DeadEnd.isEmpty()){
			System.out.println("Ini dia daftar pulau yang bisa bikin kamu terjebak.");
			System.out.println("Nomor 3 bikin KAGET!!!");
			System.out.println(this.DeadEnd);
		}
	}

	public void Telusuri(int start, int dest, LinkedList<Integer> visited){
		//update daftar pulau yang sudah dikunjungi
		visited.add(start);

		if(this.isConnected(start,dest) && !visited.contains(dest)){
			Telusuri(dest, 1, visited);
		} else {
			if(dest < this.nPulau){
				Telusuri(start, dest+1, visited);
			} else {
				this.DeadEnd.add(start);
				if(start < nPulau){
					Telusuri(start+1, 1, visited);
				}
			}

		}
	}

	public void Caller(){
		LinkedList<Integer> visited = new LinkedList<Integer>();
		this.Telusuri(1,1,visited);
	}


	public static void main(String[] args) throws Exception{
		Pulau java = new Pulau(args[0]);
		System.out.println("Jumlah pulau " + java.getnPulau());
		java.Caller();
		java.getDeadEnd();
	}

}