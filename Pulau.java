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

	public void Telusuri(int start, int dest, Stack currentPath, Stack whitelist){
		//update daftar pulau yang sudah dikunjungi
		// visited.add(start);
		// currentPath.push(start);
		// System.out.println("currently visiting " + dest);

		if(this.isConnected(start,dest) && !currentPath.contains(dest)){
			//catat path
			// path += " " + Integer.toString(dest);
			// visited.push(dest);
			//update path
			currentPath.push(dest);
			//update whitelist
			whitelist.push(start);
			//white supremacy
			
			// System.out.println(currentPath);
			// System.out.println(visited);

			Telusuri(dest, 1, currentPath, whitelist);

		} else {
			if(dest < this.nPulau){
				Telusuri(start, dest+1, currentPath, whitelist);
			} else {
				if(!whitelist.contains(start)){
					this.DeadEnd.add(start);
					System.out.println("Oooops stuck in " + start);
					System.out.println("Let's see the path we've taken >> " + currentPath);
				}
				
				// System.out.println("we have visited "+visited);
				// System.out.println("Current path " + path);
				int prevDest = (int) currentPath.pop();
				// visited.pop();
				
				
				
				// if(!currentPath.isEmpty()){
					// int prevRoot = (int) currentPath.peek();
					// System.out.println("backtracking to "+prevRoot);
					// System.out.println("prevDest "+prevDest);
					// System.out.println("nPulau "+this.nPulau);

					// if(!(prevRoot == this.start && prevDest < this.nPulau)){
					// 	Telusuri(prevRoot, prevDest+1, visited, currentPath);						
					// }
					while(!currentPath.isEmpty()){
						int prevRoot = (int) currentPath.peek();

						//debugging part
						// System.out.println("backtracking to "+prevRoot);
						// System.out.println("whitelist : " + whitelist);

						if(prevDest == (int) whitelist.peek()){
								whitelist.pop();
								// System.out.println("whitelist "+whitelist);
								//congrats, you just lost your white supremacy
							}
						
						if(prevDest < this.nPulau){
							Telusuri(prevRoot, prevDest+1, currentPath, whitelist);
						} else {
							prevDest = (int) currentPath.pop();
							// visited.pop();
							// prevRoot = currentPath.peek();
							
						}

					}

					// Telusuri(prevRoot, prevDest+1, visited, currentPath);
				// }
			}

		}
	}

	public void Caller(){

		// Stack<Integer> visited = new Stack<Integer>();
		Stack<Integer> currentPath = new Stack<Integer>();
		Stack<Integer> whitelist = new Stack<Integer>();
		// visited.add(this.start);
		currentPath.push(this.start);
		// whitelist.push(this.start);
		// String path = Integer.toString(this.start);
		this.Telusuri(this.start, 1, currentPath, whitelist);

	}


	public static void main(String[] args) throws Exception{
		Pulau java = new Pulau(args[0]);
		// System.out.println("Jumlah pulau " + java.getnPulau());
		java.Caller();
		java.getDeadEnd();
	}

}