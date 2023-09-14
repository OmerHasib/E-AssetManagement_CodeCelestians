package TestApp;
import java.util.Scanner;
import java.util.*;

import business.UserBusinessImpl;
import business.UserBusinessIntf;
import exception.OperationUnsuccessful;
import model.Asset;
import model.Category;
import model.User;
import business.AdminBusinessImpl;
import business.AdminBusinessIntf;
public class View {
	
    static User user;
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Welcome to E-Asset Management Portal");
		System.out.println("Press \n 1- For Admin Login\n 2- For User Login");
		int n=sc.nextInt();
		if(n==1)
		{
			//For Admin testing all the methods
			
			AdminBusinessIntf adminBusiness = new AdminBusinessImpl(); // Replace with your actual implementation

	        try {
	            // Testing the addNewAsset method
	            Asset assetToAdd = new Asset(/* Initialize asset properties */);
	            adminBusiness.addNewAsset(assetToAdd);
	            System.out.println("Asset added successfully!");

	            // Testing the getDifferentCategory method
	            Set<String> categories = adminBusiness.getDifferentCategory();
	            System.out.println("Different Categories: " + categories);

	            // Testing the overDueAssets method
	            Map overDueAssets = adminBusiness.overDueAssets();
	            System.out.println("Overdue Assets: " + overDueAssets);

	            // Testing the addMsg method
	            int userId = 1; 
	            String messageToAdd = "Test Message";
	            adminBusiness.addMsg(userId, messageToAdd);
	            System.out.println("Message added successfully!");

	            // Testing the getUserRecords method
	            List userRecords = adminBusiness.getUserRecords();
	            System.out.println("User Records: " + userRecords);

	            // Testing the addNewCategory method
	            //Hard coding values for now
	            //Later the values will be from the front end
	            Category categoryToAdd = new Category("Book",15,300,10);
	            adminBusiness.addNewCategory(categoryToAdd);
	            System.out.println("Category added successfully!");

	            // Testing the Login method
	            String adminId = "admin"; // Replace with an actual admin ID
	            String adminPassword = "admin123"; // Replace with an actual admin password
	            boolean isAdminAuthenticated = adminBusiness.Login(adminId, adminPassword);
	            if (isAdminAuthenticated) {
	                System.out.println("Admin authenticated successfully!");
	            } else {
	                System.out.println("Invalid admin credentials.");
	            }
	        } catch (OperationUnsuccessful e) {
	            System.out.println("Operation unsuccessful: " + e.getMessage());
	        }		
		}
		else if(n==2)
		{
			//For User testing all the methods 
			
			UserBusinessIntf userbusinessimpl=new UserBusinessImpl();
			System.out.println("Press \n 1-To Register \n 2-Login");
			n=sc.nextInt();
			if(n==1)
			{
				System.out.println("Enter Employee Id");
				int eid=sc.nextInt();
				System.out.print("Enter name");
				String name=sc.nextLine();
				System.out.print("Enter role");
				String role=sc.nextLine();
				System.out.print("Enter telephone");
				long tele=sc.nextLong();
				System.out.print("Enter mail");
				String mail=sc.nextLine();
				System.out.print("Enter username");
				String user=sc.nextLine();
				System.out.print("Enter password");
				String pass=sc.nextLine();
				User u1=new User();
				try {
					userbusinessimpl.register(u1);
				} catch (OperationUnsuccessful e) {
					
					e.printStackTrace();
				}
				
			}
			else if(n==2)
			{
				boolean b=false,b2=false;
						System.out.println("Press \n 1-For usernameLogin \n 2-Email Login");
						n=sc.nextInt();
					 if(n==1)
					 {
						System.out.println("Enter username");
						String u=sc.nextLine();
						System.out.println("Enter password");
						String p=sc.nextLine();
						try {
							user=userbusinessimpl.loginByUsername(u,p);
						} catch (OperationUnsuccessful e) {
							
							e.printStackTrace();
						}
						
					}
					 else if(n==2)
					 {
						System.out.println("Enter email");
						String u=sc.nextLine();
						System.out.println("Enter password");
						String p=sc.nextLine();
					try {
						user=userbusinessimpl.loginByEmail(u,p);
					} catch (OperationUnsuccessful e) {
						
						e.printStackTrace();
					}
					 
				 
			 }
			
			if(user!=null)
			{
				System.out.println("Welcome to HomePage");
				char ch;
				do
				{
				System.out.println("Press \n 1- View Profile \n 2- Search Asset"
						+ "\n 3- To Display Asset Borrowed \n 4-Borrow Asset");
				n=sc.nextInt();
				switch(n)
				{
				case 1:
					userbusinessimpl.userProfile(user);
					break;
				case 2:
					System.out.println("Which asset you want Laptop, Mobile, Tablet");
					String type=sc.nextLine();
					try {
						userbusinessimpl.searchAsset(type);
					} catch (OperationUnsuccessful e) {
						
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						userbusinessimpl.borrowedItems(user.getUid());
					} catch (OperationUnsuccessful e) {
						
						e.printStackTrace();
					}
					break;
				case 4:
					System.out.println("Enter Asset id");
					int aid=sc.nextInt();
					try {
						userbusinessimpl.borrow(user.getUid(), aid);
					} catch (OperationUnsuccessful e) {
						
						e.printStackTrace();
					}
					break;
				}
				System.out.println("Do you wish to continue? Press 'y'- yes 'n' for no");
				ch=sc.next().charAt(0);
				}while(ch=='y');
			}
			
			
			
			
			
		}
	}
	}
}


