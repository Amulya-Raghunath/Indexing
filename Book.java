package library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Book {
	BookName[] bI=new BookName[100600];
	private String book_id,b_Name,a_Name,stuUsn,stuName;
	int reccount = 0;

	

public void getData(){
    		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the Book_id: ");
		book_id = scanner.next();
		System.out.println("enter the book name");
		b_Name = scanner.next();
		System.out.println("Enter the Author Name: ");
		a_Name = scanner.next();
		System.out.println("Enter the USN: ");
		stuUsn = scanner.next();
		System.out.println("Enter the Name: ");
		stuName = scanner.next();
		
		
}
public void add(){
        String data=book_id +","+ b_Name +","+  a_Name +","+ stuUsn +","+ stuName;

 try{			
			RandomAccessFile recordfile = new RandomAccessFile ("original1.csv","rw");
			recordfile.seek(recordfile.length());
			long pos = recordfile.getFilePointer();
			recordfile.writeBytes(data+"\n");
			recordfile.close();
			
			RandomAccessFile indexfile = new RandomAccessFile ("second.txt","rw");
			indexfile.seek(indexfile.length());
			indexfile.writeBytes(b_Name+","+pos+"\r\n");
			indexfile.close();
		}
		catch(IOException e){
			System.out.println(e);
		}
		
 
}                     
    @SuppressWarnings("resource")
	public void secIndex(){

		String line
                        ,bkName = null,pos = null;
		int count = 0;
		int sIIndex = 0;
		reccount=0;
		RandomAccessFile indexfile;
		 long starttime =System.nanoTime();
		try {
			indexfile = new RandomAccessFile("second.txt","rw");

			try {
				
				while((line = indexfile.readLine())!= null){
                                     if(line.contains("*")) {
	                		continue;
	                	}
					count = 0;
                                                 
                                       
	          
					
					StringTokenizer st = new StringTokenizer(line,",");
					while (st.hasMoreTokens()){
					 count+=1;
					 if(count==1)
				    bkName = st.nextToken();
					 pos = st.nextToken();
                                         
				    }
					bI[sIIndex] = new BookName();
					bI[sIIndex].setRecPos(pos);
					bI[sIIndex].setbName(bkName);
					reccount++;
					sIIndex++;
                                        if(sIIndex==100600)
                                        {
                                            break;
                                        }
                                }
			} catch (IOException e) {
				
				e.printStackTrace();
				return;
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return;
		} 
		
		System.out.println("total records" + reccount);
		long endtime =System.nanoTime();
        long totaltime=endtime-starttime;       
        System.out.println(totaltime/1000000+"msec");
         
		if (reccount==1) { return;}
		sortIndex();
	}
	
	
	public void sortIndex() {
		BookName temp = new BookName();
		
		for(int i=0; i<reccount; i++)
		    {	
				for(int j=i+1; j<reccount; j++) {
					if(bI[i].getbName().compareTo(bI[j].getbName())  > 0)
		            {
		                temp.setbName(bI[i].getbName()); 
				        temp.setRecPos(bI[i].getRecPos());
				
			        	bI[i].setbName(bI[j].getbName());
			        	bI[i].setRecPos(bI[j].getRecPos());
				
			        	bI[j].setbName(temp.getbName());
			        	bI[j].setRecPos(temp.getRecPos());
		            }
				}
					
			}	
		
	}
        @SuppressWarnings("resource")
		public void search(){
        	 System.out.println("Enter the Book Name to search: ");
             Scanner scanner = new Scanner(System.in);
             String bName = scanner.next();
             
             
             
             int oripos = binarySearch(bI, 0, reccount-1, bName);
             
             if (oripos == -1) {
                 System.out.println("Record not found in the record file");
                 return ;
             }
             
             int pos = oripos;
            
             
             do {
                 readFile(pos);
                 pos--;
                 if (pos < 0) { break;}
             }while(bI[pos].getbName().compareTo(bName)==0);
             
             pos = oripos + 1 ;
          // if (pos > reccount-1) { return;}
             
             while(bI[pos].getbName().compareTo(bName)==0 && pos > reccount-1) {
                 readFile(pos);
                 pos++;
                // if (pos > reccount-1) { break;}
             }
            
        }
 public void readFile(int pos) {
            
	 if (pos == -1) {
			System.out.println("Record not found in the record file");
			return ;}
            RandomAccessFile recordfile;
            try {
                recordfile = new RandomAccessFile ("original1.csv","rw");
                try {
                	String jl=bI[pos].getRecPos().trim();
                    recordfile.seek(Long.parseLong(jl));
                    String record = recordfile.readLine();
                    StringTokenizer st = new StringTokenizer(record,",");
                    
                    int count = 0;
                       
                    while (st.hasMoreTokens()){
                             count += 1;
                               if(count==1){
                               String tmp_book_id = st.nextToken();
                               System.out.println("Book_id: "+tmp_book_id);
                               this.book_id = tmp_book_id;
                               
                               String tmp_Name = st.nextToken();
                               System.out.println("Book Name: "+tmp_Name);
                               this.b_Name = tmp_Name;
                              
                                String tmp_A_Name = st.nextToken();
                               System.out.println("Author Name: "+tmp_A_Name);
                               this.a_Name = tmp_A_Name;
                                
                                String tmp_stuUsn = st.nextToken();
                                System.out.println("USN: "+tmp_stuUsn);
                                this.stuUsn = tmp_stuUsn;
                               
                                 String tmp_stuName = st.nextToken();
                                 System.out.println("Student Name "+tmp_stuName);
                                 this.stuName = tmp_stuName;
                               
                                
                                   
                                 
                                   
                               }
                               else
                                   break;
                       }
                    
                    recordfile.close();
                } 
                    catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                
                }
                                        
                catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
 }

        int binarySearch(BookName s[], int l, int r, String bName) {
    	
    	int mid;
    	while (l<=r) {
            
    		mid = (l+r)/2;
    		if(s[mid].getbName().compareTo(bName)==0)
    		{
    			return mid;
    			}
    		if(s[mid].getbName().compareTo(bName)<0)
    		{
    			l = mid + 1;
    		}
    			if(s[mid].getbName().compareTo(bName)>0) 
    		{
    				r = mid - 1;
    				}
    	}
    	return -1;
    }

  public  void indexing() 
  {
	  long starttime =System.nanoTime();
  
  
 
         try{
        RandomAccessFile hey=new RandomAccessFile("original1.csv","rw");
        RandomAccessFile indexfile=new RandomAccessFile("second.txt","rw");
        String line;
 long       pos=hey.getFilePointer();
        while((line = hey.readLine())!=null)
        {
            if(line.contains("*")) {
	                		continue;
	                	}

            String[] columns=line.split(",");
            indexfile.writeBytes(columns[1]+","+pos+" \n");
            pos=hey.getFilePointer();
        } indexfile.close();
        hey.close();
        long endtime =System.nanoTime();
        long totaltime=endtime-starttime;       
        System.out.println(totaltime/1000000+"msec");
         
       
         }
    
    catch(IOException e)
    {
        System.out.println(e);
    }
  }

 @SuppressWarnings("resource")
public   void delete() throws IOException {
	 indexing();
     
     System.out.println("Enter the Book Name to delete: ");
     Scanner scanner = new Scanner(System.in);
     String book_Name = scanner.next();
     String ans = "n";
     int pos;
     
     int oripos = binarySearch(bI, 0, reccount-1, book_Name);
     
     if (oripos == -1) {
         System.out.println("Record not found in the record file");
         return ;
     }
     
     pos = oripos;
     
     do {
         readFile(pos);
         
         System.out.println("Do You Want To delete This Record ?(y/n) ");
         ans = scanner.next();
         
         if (ans.compareTo("y")==0) {
             markDelete(book_Name, pos);
         }
         pos--;
         if (pos < 0) { break;}
     }while(bI[pos].getbName().compareTo(book_Name)==0);
         
     
     pos = oripos + 1 ;
     
   //  if (pos > reccount-1) { return;}
     while(bI[pos].getbName().compareTo(book_Name)==0 && pos > reccount-1){
         readFile(pos);
         
         System.out.println("Do You Want To delete This Record ?(y/n) ");
         ans = scanner.next();
         
         if (ans.compareTo("y")==0) {
             markDelete(book_Name,pos);
             break;
         }
         pos++;
         if (pos > reccount-1) { break;}
     }
}
 @SuppressWarnings("resource")
public void markDelete(String b_Name,int pos) {
     try {
         RandomAccessFile recordfilee = new RandomAccessFile ("original1.csv","rw");
         RandomAccessFile indexfilee = new RandomAccessFile ("second.txt","rw");
     String s=bI[pos].getRecPos().trim();
             recordfilee.seek(Long.parseLong(s));
             recordfilee.writeBytes("*");
             System.out.println("Done");
             recordfilee.close();
             String line;
             String indexName;
             long indexPos = 0;
             long delPosi;
             //delPosi = indexfilee.getFilePointer();
             while((line = indexfilee.readLine())!=null) {
                 if(line.contains("*"))
                     continue;
                 StringTokenizer st = new StringTokenizer(line,",");
               delPosi = indexfilee.getFilePointer();
                delPosi = delPosi - (line.length()+2);
                 
                // System.out.println("Delposi: " + delPosi);
                 
                 while(st.hasMoreTokens()) {
                     indexName=st.nextToken();
                     String s1=st.nextToken().trim();
                     indexPos= Long.parseLong(s1);
                  //   System.out.println("indexPos: " + indexPos);
                    // System.out.println("getrecpos: " + Long.parseLong(sI[pos].getRecPos()));
                     String t=bI[pos].getRecPos().trim();
                     if(indexName.equals(b_Name) && indexPos == Long.parseLong(t) ) {
                         indexfilee.seek(delPosi);
                         indexfilee.writeBytes("*");
                         indexfilee.close();
                         System.out.println("Deleted");
                         indexing();
                         return;
                     }
                 }
             }
             }
         
         catch (Exception e) {
             e.printStackTrace();
         }
 }

}




