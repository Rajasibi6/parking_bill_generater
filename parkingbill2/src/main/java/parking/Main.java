package parking;
import java.util.*;
public class Main {
	public static void main(String[]args) throws Exception{
		Scanner sc=new Scanner(System.in);
		vd ob=new vd();
		parking_main obj=new parking_main();
		System.out.print("Enter a option with menu 1.Entry 2.Exit 3.ViewCount:");
		String op=sc.next();
		if(op.equals("Entry")) {
			System.out.println("Enter a vechical no:");
			ob.setV_no(sc.next());
			System.out.println("Enter a vechical enter time formate(hh:mm:ss):");
			ob.setEntertime(sc.next());
			System.out.println("Enter a vechical Type:");
			ob.set_type(sc.next());
			//System.out.print(ob.getEntertime());
			obj.insert_Entry(ob);
		}
		else if(op.equals("Exit")) {
			System.out.println("Enter a vechical no:");
			String no=sc.next();
			System.out.println("Enter a vechical exit time formate(hh:mm:ss):");
			ob.setExittime(sc.next());
			obj.exit_Entry(ob,no);
		}
		else if(op.equals("ViewCount")) {
			System.out.println("Count of parked Vechical is : "+obj.count(ob));
		}
	}

}
