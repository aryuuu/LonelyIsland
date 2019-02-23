import java.io.*;
import java.util.*;


public class Archipelago{
	//data member 
	private int nPulau;
	private int nJembatan;
	private int start;
	private LinkedList<Integer> Jembatan[];
	//himpunan pulau dimana pemain bisa terjebak
	private Set<Integer> DeadEnd; 

	//ctor
	Archipelago(String fileName) throws Exception{
		File sauce = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(sauce));

		//mengolah data dari baris pertama 
		String temp = br.readLine();
		String[] firstLine = temp.split("\\s+");
		int nPulau = Integer.parseInt(firstLine[0]);
		int nJembatan = Integer.parseInt(firstLine[1]);
		int start = Integer.parseInt(firstLine[2]);


		this.nPulau = nPulau;
		this.nJembatan = nJembatan;
		this.start = start;
		this.DeadEnd = new HashSet<Integer>();

		this.Jembatan = new LinkedList[nPulau+1];
		for(int i = 1; i <= nPulau; i++){
			this.Jembatan[i] = new LinkedList();
		}
		//memasang jembatan
		for(int i = 0; i < nJembatan; i++){
			temp = br.readLine();
			String[] line = temp.split("\\s+");
			int s = Integer.parseInt(line[0]);
			int d = Integer.parseInt(line[1]);

			this.AddJembatan(s,d);
		}

	}

	//menambahkan jembatan dari pulau s ke pulau d
	void AddJembatan(int s, int d){
		this.Jembatan[s].add(d);
	}

	void getDeadEnd(){
		if(!this.DeadEnd.isEmpty()){
			System.out.println("Ini dia daftar pulau yang bisa bikin kamu terjebak.");
			System.out.println("Nomor 2 bikin KAGET!!!");
			System.out.println(">> "+ this.DeadEnd);
		} else {
			System.out.println("This archipelago is super safe");
		}
	}
	

	//prosedur untuk penelusuran dengan DFS
	public void DFS(int start, LinkedList<Integer> visited){
		//catat pulau start sebagai pulau yang sudah dikunjungi
		visited.add(start);
		System.out.print(start+ " ");

		Iterator<Integer> i = this.Jembatan[start].listIterator();
		while(i.hasNext()){
			int n = i.next();
			if(!visited.contains(n)){
				DFS(n, visited);
			}
			// System.out.println(n + " " + visited);

		}
		if( visited.getLast() == start && this.Jembatan[visited.getLast()].isEmpty() ){
			// System.out.print(" << ");
			DeadEnd.add(start);
		}
	
	}

	public void Telusur(){
		
		//list pulau yang sudah dikunjungi
		LinkedList visited = new LinkedList();

		DFS(this.start, visited);
		System.out.println();
		//
	}

	

	public static void main(String[] args) throws Exception{
	
		//membangun kepulauan 
		Archipelago arc = new Archipelago(args[0]);
		//memasang jembatan-jembatan pada pulau
		arc.Telusur();
		arc.getDeadEnd();



	}
}