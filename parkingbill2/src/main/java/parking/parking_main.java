package parking;
import java.sql.*;
public class parking_main implements fun_interface {
    private Connection con=null;
    private PreparedStatement ps=null;
    private ResultSet res=null;
    parking_main() throws Exception{
    	Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
    }
	@Override
	public void insert_Entry(vd ob)throws Exception {
		// TODO Auto-generated method stub
		ps=con.prepareStatement("insert into V_DETAIL (VECHICAL_NO,VECHICAL_NAME,ENTRY_TIME) values(?,?,?)");
		String t=ob.getEntertime();
		Time time = Time.valueOf(t);
		ps.setString(1, ob.getV_no());
		ps.setString(2, ob.get_type());
		ps.setTime(3,time);
		int rs=ps.executeUpdate();
		if(rs>0) {
			System.out.print("Parking Sucess");
		}
		
		
		
	}

	@Override
	public void exit_Entry(vd ob,String no)throws Exception {
		// TODO Auto-generated method stub
		ps=con.prepareStatement("update V_DETAIL set EXIT_TIME=? where VECHICAL_NO=?");
		Time t=Time.valueOf(ob.getExittime());
		ps.setTime(1, t);
		ps.setString(2,no);
		int rs=ps.executeUpdate();
		if(rs>0) {
			generatebill(ob,no);
		}
		
		
		
	}

	@Override
	public int count(vd ob)throws Exception {
		// TODO Auto-generated method stub
		int count=0;
		ps=con.prepareStatement("select count(VECHICAL_NO) as coun from V_DETAIL");
		res=ps.executeQuery();
		while(res.next()) {
			 count=res.getInt("coun");
			
		}
		return count;
	}

	@Override
	
		// TODO Auto-generated method stub
		public void generatebill(vd ob, String no) throws Exception {
		    // Select ENTRY_TIME and EXIT_TIME for the specified vehicle number
		    ps = con.prepareStatement("SELECT ENTRY_TIME, EXIT_TIME FROM V_DETAIL WHERE VECHICAL_NO = ?");
		    ps.setString(1, no);
		    res = ps.executeQuery();

		    if (res.next()) {
		        Time entryTime = res.getTime("ENTRY_TIME");
		        Time exitTime = res.getTime("EXIT_TIME");

		        if (entryTime != null && exitTime != null) {
		            // Calculate the difference in milliseconds
		            long diffInMillis = exitTime.getTime() - entryTime.getTime();
		            
		            // Convert milliseconds to hours, for example
		            long diffInHours = diffInMillis / (1000 * 60 * 60);
		            
		            // Calculate the bill (assuming a rate of 10 units per hour)
		            int ratePerHour = 10;
		            long billAmount = diffInHours * ratePerHour;
		            System.out.println("BillGenerating......");
		            System.out.println("Vechical no: "+no);
		            System.out.println("Entry time: "+entryTime);
		            System.out.println("Exit Time : "+exitTime);

		            System.out.println("Bill Amount: " + billAmount);
		            ps=con.prepareStatement("Delete from V_DETAIL where VECHICAL_NO = ? ");
		            ps.setString(1, no);
		            ps.executeUpdate();
		        } else {
		            System.out.println("Entry or Exit time is null.");
		        }
		    } else {
		        System.out.println("Vehicle not found.");
		    }
		}
	

}
