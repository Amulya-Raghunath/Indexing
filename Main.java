package library;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws IOException{
		@SuppressWarnings("resource")
	
	
		Scanner scanner = new Scanner(System.in);
		Pri pi = new Pri();
		Second si = new Second();
		Book bi=new Book();
		si.indexing();
		si.secIndex();
        pi.indexing();
        pi.priIndex();
        bi.indexing();
        bi.secIndex();
                while (true){
                		System.out.println("WELCOME");
                       
	                System.out.println("ENTER YOUR CHOICE"); 
                       
			System.out.println("1>Enter the details: \n"
					+ "2>Enter the ID to Search: \n"
					+ "3>Enter the Author Name to Search: \n"
					+ "4>Enter the Book Name to search: \n"
					+ "5>To Build Index using primary key: \n"
					+ "6>To Build Index secondary key based on Author Name: \n"
					+ "7>To Build Index based on Book Name: \n"
					+ "8>Enter the primary key to be Deleted: \n"
					+ "9>Enter the secondary key to be Deleted: \n"
					+ "10>Enter the book_Name to be deleted:\n"
					+ "11>Enter the Book_id to modify:\n"
					+ "12>Exit");
		 int choice = scanner.nextInt();
		
			switch(choice){

				case 1: long starttime =System.nanoTime();
                                      pi.getData();
                                      pi.add();
                                      pi.priIndex();
                                      si.secIndex();
                                      long endtime =System.nanoTime();
                                      long totaltime=endtime-starttime;
                                      System.out.println(totaltime/1000000+"msec");
						break;
				case 2: long starttime1 =System.nanoTime();
                                  pi.search();
                                  long endtime1 =System.nanoTime();
                                      long totaltime1=endtime1-starttime1;
                                      System.out.println(totaltime1/1000000+"msec");
				    break;
				case 3: long starttime2 =System.nanoTime();
                		si.search();
                		long endtime2 =System.nanoTime();
                		long totaltime2=endtime2-starttime2;
                		System.out.println(totaltime2/1000000+"msec");
                		break;    
				case 4:long starttime8 =System.nanoTime();
        		bi.search();
        		long endtime8 =System.nanoTime();
        		long totaltime8=endtime8-starttime8;
        		System.out.println(totaltime8/1000000+"msec");
        		break; 
					
				case 5:long starttime3 =System.currentTimeMillis();
                                  pi.indexing();
                                  long endtime3 =System.currentTimeMillis();
                                      long totaltime3=endtime3-starttime3;
                                      System.out.println(totaltime3+"msec");
						break;
				case 6:long starttime4 =System.nanoTime();
                		si.indexing();
                		long endtime4 =System.nanoTime();
                		long totaltime4=endtime4-starttime4;
                    	System.out.println(totaltime4/1000000+"msec");
                    	break;
				case 7:long starttime9 =System.nanoTime();
        		bi.indexing();
        		long endtime9 =System.nanoTime();
        		long totaltime9=endtime9-starttime9;
            	System.out.println(totaltime9/1000000+"msec");
            	break;
					
                case 8:long starttime5 =System.nanoTime();
						pi.delete();
				        long endtime5=System.nanoTime();
                        long totaltime5=endtime5-starttime5;
                        System.out.println(totaltime5/1000000+"msec");
                        break;
                case 9:long starttime6 =System.nanoTime();
						si.delete();
						long endtime6=System.nanoTime();
                        long totaltime6=endtime6-starttime6;
                        System.out.println(totaltime6/1000000+"msec");
                        break;   
                case 10:long starttime10 =System.nanoTime();
				bi.delete();
				long endtime10=System.nanoTime();
                long totaltime10=endtime10-starttime10;
                System.out.println(totaltime10/1000000+"msec");
                break; 
                case 11:long starttime7 =System.nanoTime();
                		pi.modify();
				long endtime7=System.nanoTime();
                long totaltime7=endtime7-starttime7;
                System.out.println(totaltime7/1000000+"msec");
                break; 
				case 12: System.out.println("Exiting..");
						return ;
						
				default : System.out.println("Enter a valid choice!");
			}	
			}
		
			}                 		
	}
	

