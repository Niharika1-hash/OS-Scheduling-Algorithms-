package RoundRobin;
import java.util.*;
public class Main 
{
	public static void main(String args[])
	{
		int tq,n;
		ArrayList <Process> gantt = new ArrayList<Process>();
		Queue <Process> ready_queue = new LinkedList<>(); 
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Time Quantum:");
		tq = sc.nextInt();
		System.out.println("Enter the number of processes:");
		n = sc.nextInt();
		Process P[] = new Process[n];
		for(int i =0;i<n;i++)
		{
			P[i]=new Process();
		}
		System.out.println("Enter Process details:");
		for(int i = 0;i<n;i++)
		{
			System.out.println("Process "+(i+1));
			P[i].pno = i;
			System.out.println("Arrival Time:");
			P[i].at = sc.nextInt();
			System.out.println("Burst Time:");
			P[i].bt=sc.nextInt();
			P[i].BT = P[i].bt;
		}
		sort(P,n);
//		System.out.println("sorted:");
//		for(int i = 0;i<n;i++)
//		{
//			System.out.println(P[i].pno+" "+P[i].at+" "+P[i].bt);
//		}  
		ready_queue.add(P[0]);
		P[0].flag = 1;
		P[0].fst = 0;
		Process temp;
		System.out.println("Gantt Chart");
		System.out.println("Start Time"+"\t"+"Process"+"\t"+"End Time");
		while(!ready_queue.isEmpty())
		{
			//display_queue(ready_queue);
			//display_gantt(gantt);
			temp = ready_queue.poll();
			
			if(temp.bt>0)
			{
				/*if(temp.flag==0)
				{
					if(gantt.size()>0)
					{
						Process last = gantt.get(gantt.size()-1);
						temp.fst = last.ct;
					}
					else
					{
						temp.fst = 0;
					}
				} */
				update(temp,gantt,tq);
				gantt.add(temp);
				enqueue(temp,ready_queue,P,n,gantt);
			
			}
			
		}
		
		for(int i = 0;i<n;i++)
		{
			P[i].tt = P[i].ct - P[i].at;
			P[i].wt = P[i].tt - P[i].BT;
			P[i].rt = P[i].fst - P[i].at;
		}
		
		System.out.println("Process details:");
		System.out.println("PNo."+"\t"+"AT"+"\t"+"BT"+"\t"+"CT"+"\t"+"TT"+"\t"+"WT"+"\t"+"RT");
		for(int i = 0;i<n;i++)
		{
			System.out.println(P[i].pno+"\t"+P[i].at+"\t"+P[i].BT+"\t"+P[i].ct+"\t"+P[i].tt+"\t"+P[i].wt+"\t"+P[i].rt);
		}
		
		//calculate average times
		float avg_ct=0,avg_tt=0,avg_wt=0,avg_rt=0;
		for(int i = 0;i<n;i++)
		{
			avg_ct = avg_ct + P[i].ct;
			avg_tt = avg_tt + P[i].tt;
			avg_wt = avg_wt + P[i].wt;
			avg_rt = avg_rt + P[i].rt;
			
		}
		avg_ct /=n;
		avg_tt /=n;
		avg_wt /=n;
		avg_rt /=n;
		
		System.out.println("Average CT: "+avg_ct);
		System.out.println("Average TT: "+avg_tt);
		System.out.println("Average WT: "+avg_wt);
		System.out.println("Average RT: "+avg_rt);
	
		
	}
	
	static void display_queue(Queue<Process> q)
	{
		System.out.println("Ready Queue");
		for(Process p : q)
		{
			System.out.println(p.pno);
		}
	}
	
	static void display_gantt(ArrayList<Process> p)
	{
		System.out.println("Gantt Chart");
		System.out.println("Start Time"+"\t"+"Process"+"\t"+"End Time");
		for(Process temp:p)
		{
			System.out.println(temp.st+"\t"+"P"+temp.pno+"\t"+temp.ct);
		}
	}
	
	static void sort(Process P[],int n)
	{
		Process temp ;
		for(int i = 1;i<n;i++)
		{
			for(int j = 0;j<n-i;j++)
			{
				if(P[j].at>P[j+1].at)
				{
					temp = P[j];
					P[j] = P[j+1];
					P[j+1] = temp;
				}
			}
		}
	}
	
	static void update(Process p,ArrayList<Process> gantt,int tq)
	{
	
		if(gantt.size()>0)
		{
			Process last = gantt.get(gantt.size()-1);
			p.st = last.ct;
			if(p.bt>=tq)
			{
				p.bt=p.bt-tq;
				p.ct=last.ct+tq;
				
			}
			else
			{
				p.ct = last.ct + p.bt;
				p.bt = 0;
			}
		}
		else
		{
			p.st = 0;
			if(p.bt>=tq)
				if(p.bt>=tq)
				{
					p.bt=p.bt-tq;
					p.ct=p.ct+tq;
					
				}
				else
				{
					p.ct = p.ct+p.bt;
					p.bt = 0;
				}
		}
		
//		System.out.println("Added in Gantt:");
//		System.out.println("PNo"+" "+" ST"+" "+"CT");
//		System.out.println(p.pno+" "+p.st+" "+p.ct);
		System.out.println(p.st+"\t"+"P"+p.pno+"\t"+p.ct);
	}
	
	static void enqueue(Process cur,Queue<Process> ready_queue,Process []p,int n,ArrayList<Process>gantt)
	{
		
		int start = cur.st;
		int end = cur.ct;
		for(int i = 0;i<n;i++)
		{
			if(p[i].flag==1)
			{
				continue;
			}
			else
			{
				if(p[i].at>=start && p[i].at<=end)
				{
					ready_queue.add(p[i]);
					p[i].flag = 1;
					
				}
			}
		}
		if(cur.bt>0)
		{
			ready_queue.add(cur);
		}
	}

}






