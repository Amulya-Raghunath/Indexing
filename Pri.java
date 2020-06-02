package library;


	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.io.RandomAccessFile;
	import java.util.Scanner;
	import java.util.StringTokenizer;

	public class Pri {
	private MIndex[] sI = new MIndex[100600];
		
	    private String book_id,b_Name,a_Name,stuUsn,stuName;
		int reccount = 0;

	public void getData(){
	    		@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter Book id: ");
			book_id = scanner.next();
			System.out.println("Enter the Book Name: ");
			 b_Name = scanner.next();
			System.out.println("Enter the Author Name: ");
			a_Name= scanner.next();
			System.out.println("Enter the USN: ");
			stuUsn= scanner.next();
			System.out.println("Enter the Name: ");
			stuName = scanner.next();
			
	}
	public void add(){
	        String data=book_id+","+ b_Name +","+ a_Name +","+ stuUsn +","+ stuName ;

	 try{			
				RandomAccessFile recordfile = new RandomAccessFile ("original1.csv","rw");
				recordfile.seek(recordfile.length());
				long pos = recordfile.getFilePointer();
				recordfile.writeBytes(data+"\n");
				recordfile.close();
				
				RandomAccessFile indexfile = new RandomAccessFile ("index.txt","rw");
				indexfile.seek(indexfile.length());
				indexfile.writeBytes(book_id+","+pos+"\n");
				indexfile.close();
			}
			catch(IOException e){
				System.out.println(e);
			}
			
	 
	}                     
	    @SuppressWarnings("resource")
	    public void priIndex(){

			String line,book_id= null,pos = null;
			int count = 0;
			int sIIndex = 0;
			reccount=0;
			RandomAccessFile indexfile;
			 long starttime =System.nanoTime();
			try {
				indexfile = new RandomAccessFile("index.txt","rw");

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
					    book_id = st.nextToken();
						pos = st.nextToken();       
					    }
						sI[sIIndex] = new MIndex();
						sI[sIIndex].setRecPos(pos);
						sI[sIIndex].setbook_id(book_id);
						reccount++;
						sIIndex++;
	                                        if(sIIndex==100600)
	                                        {
	                                            break;
	                                        }
	                                }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} //true tells to append data.
			
			System.out.println("total records" + reccount);
			long endtime =System.nanoTime();
	        long totaltime=endtime-starttime;       
	        System.out.println(totaltime/1000000+"msec");
			if (reccount==1) { return;}
			sortIndex();
		}
		
		
		
		public void sortIndex() {
			MIndex temp = new MIndex();
			
			for(int i=0; i<reccount; i++)
			    {	
					for(int j=i+1; j<reccount; j++) {
						if(sI[i].getbook_id().compareTo(sI[j].getbook_id())  > 0)
			            {
			                temp.setbook_id(sI[i].getbook_id()); 
					        temp.setRecPos(sI[i].getRecPos());
					
				        	sI[i].setbook_id(sI[j].getbook_id());
				        	sI[i].setRecPos(sI[j].getRecPos());
					
				        	sI[j].setbook_id(temp.getbook_id());
				        	sI[j].setRecPos(temp.getRecPos());
			            }
					}
						
				}	
			
		}
	        public void search(){
	            System.out.println("Enter the Book id to search: ");
						@SuppressWarnings("resource")
						Scanner scanner = new Scanner(System.in);
						String Identifier = scanner.next();
						System.out.println(reccount);
						int pos = binarySearch(sI, 0, reccount-1, Identifier);
	                                        
						
						if (pos == -1) {
							System.out.println("Record not found in the record file");
							return ;
						}
						
						RandomAccessFile recordfile;
						try {
							recordfile = new RandomAccessFile ("original1.csv","rw");
							try {
								recordfile.seek(Long.parseLong(sI[pos].getRecPos()));
								String record = recordfile.readLine();
								StringTokenizer st = new StringTokenizer(record,",");
								
								int count = 0;
	                                                        
			               	    
			                	while (st.hasMoreTokens()){
			                		     count+=1;
			                  	    	 if(count==1){
			                  	    	 String tmp_Identifier= st.nextToken();
	                                                 if(tmp_Identifier.contains("*"))
	                                                 {
	                                                     System.out.println("it has been deleted");
	                                                     break;
	                                                 }
							System.out.println("Book id: "+tmp_Identifier);
			                  	         this.book_id = tmp_Identifier;
			                  	    	
			                  	          String tmp_Name= st.nextToken();
			                     	      System.out.println(" Book NAME: "+tmp_Name);
			                     	      this.b_Name = tmp_Name;
			                     	       
			                     	       String tmp_Gender = st.nextToken();
			                     	       System.out.println("Author name: "+tmp_Gender);
			                     	       this.a_Name = tmp_Gender;
			                  	    	 
			                     	        String tmp_specialization = st.nextToken();
			                     	        System.out.println("USN: "+tmp_specialization);
			                     	        this.stuUsn = tmp_specialization;
			                     	      
			                     	        String tmp_NOServices = st.nextToken();
			                     	        System.out.println("Name: "+tmp_NOServices);
			                     	        this.stuName= tmp_NOServices;
			                     	     
			                     	           	 }

			                  	    	 else
			                  	    		 break;
			                       }
			                	
							} 
								catch (NumberFormatException e) {
								
								e.printStackTrace();
							} 
							catch (IOException e) {
								
								e.printStackTrace();
							}
							
							
							}
													
		                	catch (FileNotFoundException e) {
							
							e.printStackTrace();
						}
	        }
	        int binarySearch(MIndex s[], int l, int r, String b_id) {
	    	
	    	int mid;
	    	while (l<=r) {
	            
	    		mid = (l+r)/2;
	    		if(s[mid].getbook_id().compareTo(b_id)==0) {return mid;}
	    		if(s[mid].getbook_id().compareTo(b_id)<0) l = mid + 1;
	    		if(s[mid].getbook_id().compareTo(b_id)>0) r = mid - 1;
	    	}
	    	return -1;
	    	
	    }

	  public  void indexing() 
	  {
	         try{
	        RandomAccessFile hey=new RandomAccessFile("original1.csv","rw");
	  
	    			

	        RandomAccessFile indexfile=new RandomAccessFile("index.txt","rw");
	        String line;
	 long       pos=hey.getFilePointer();
	        while((line = hey.readLine())!=null)
	        {
	            if(line.contains("*")) {
		                		continue;
		                	}

	            String  columns[] = line.split(",");
	          indexfile.writeBytes(columns[0]  +","+pos+"\n");
	            pos=hey.getFilePointer();
	        } indexfile.close();
	        hey.close();
	                
	        
	         
	       
	         }
	    
	    catch(IOException e)
	    {
	        System.out.println(e);
	    }
	  }

	 public   void delete() throws IOException {
	         System.out.println("Enter the Book_id to delete record ");
						@SuppressWarnings("resource")
						Scanner scanner = new Scanner(System.in);
						String Identifier = scanner.next();
	        int pos = binarySearch(sI, 0, reccount-1, Identifier);
						
						if (pos == -1) {
							System.out.println("Record not found in the record file");
							return ;
						}
	                                        RandomAccessFile recordfile;
	                                        
						
							recordfile = new RandomAccessFile ("original1.csv","rw");
							try {
								recordfile.seek(Long.parseLong(sI[pos].getRecPos()));
	                                                        recordfile.writeBytes("*");
	                                                        recordfile.close();
	                                                
	                                                        }    
	                                                            
	                                             catch (NumberFormatException e) {
								
								e.printStackTrace();
							} 
							catch (IOException e) {
								
								e.printStackTrace();
							}
							
							
							}
	 public void modify()
	 {
		 System.out.println("Enter the Book-id to search: ");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String search_usn = scanner.next();
			int countrec=0;
			long pointer;
			try{
				RandomAccessFile reader = new RandomAccessFile ("original1.csv","rw");
	 		String line;
	 		try{
	           
	             while((line = reader.readLine())!= null){
	             	if(line.contains("*")) {
	             		continue;
	             	}
	             	int count=0;
	             	countrec+=1;
	             	String tmp_usn;
	             	int length = line.length();
	             	pointer = reader.getFilePointer();
	             	//System.out.println("offset: "+ pointer);
	             	StringTokenizer st = new StringTokenizer(line,",");
	             	tmp_usn = st.nextToken().toString();
	             	tmp_usn = tmp_usn.replace("\n","");
	             	if(search_usn.equals(tmp_usn)){
	             		
	             		while (st.hasMoreTokens()){
	               	    	 count += 1;
	               	    	 if(count==1){
	               	         System.out.println("Book_id: "+tmp_usn);
	               	         this.book_id = tmp_usn;
	               	    	 
	               	    	 }
	               	    	 else if(count==2){
	               	    		 String tmp_bname = st.nextToken();
	                  	         System.out.println("Book Name: "+tmp_bname);
	                  	         this.b_Name = tmp_bname;
	                  	     
	               	    	 }
	               	    	 else if(count==3){
	               	    		String tmp_branch = st.nextToken();
	                  	         System.out.println("Author Name: "+tmp_branch);
	                  	         this.a_Name = tmp_branch;
	               	    	 }
	               	    	 else if(count==4){
	               	    		String tmp_sem = st.nextToken();
	                  	         System.out.println("USN: "+tmp_sem);
	                  	         this.stuUsn = tmp_sem;
	                  	     
	               	    	 }
	               	    	 else if(count==5){
	               	    		String tmp_ph = st.nextToken();
	                  	         System.out.println("Name: "+tmp_ph);
	                  	         this.stuName = tmp_ph;
	                  	     
	               	    	 }
	               	    	
	               	    	 else
	               	    		 break;
	                    }
	             		System.out.println("Want to modify?(y/n)");
	             		String ans = scanner.next();
	             		if(ans.equals("y")){
	             			System.out.println("What do you want to modify? \n1.Book_id\n2.Book_Name\n3.Author Name\n4.USN\n5.Name\n");
	             			int menu = scanner.nextInt();
	             			String s;
	             			switch(menu){
	             			case 1: System.out.println("Enter the book_id: ");
	             					s = scanner.next();
	             					this.book_id = s;
	             					break;
	             					
	             			case 2: System.out.println("Enter the book_name: ");
	     							s = scanner.next();
	             					this.b_Name = s;
	             					break;
	             					
	             			case 3: System.out.println("Enter the author_name: ");
										s = scanner.next();
	             					this.a_Name = s;
	             					break;
	             					
	             			case 4: System.out.println("Enter the USN: ");
										s = scanner.next();
	             					this.stuUsn= s;
										break;
										
	             			case 5: System.out.println("Enter the Name: ");
										s = scanner.next();
										this.stuName = s;
										break;
										
	             			
	              			}
	             			
	        					ans = book_id+","+b_Name+","+a_Name+","+stuUsn+","+stuName;
	        					if (ans.length() > length) {
	        						System.out.println("Cannot insert in the original location.. Will be adding at the end of the file..!");
	        						System.out.println("Will be deleting the original record..!");
	        						if(countrec==1) {
	            						pointer = 0;
	            					}
	            					else {
	            						pointer = pointer-(length+1);
	            					}
	        						reader.seek(pointer);
	 							reader.writeBytes("*");
	 							reader.seek(reader.length());
	        						reader.writeBytes(ans);
	        						reader.close();	
	        					}
	        					else {
	        						if(countrec==1) {
	            						pointer = 0;
	            					}
	            					else {
	            						pointer = pointer-(length+1);
	            					}
	            					System.out.println("i will be writing here: "+ pointer);
	         					reader.seek(pointer);
	 							reader.writeBytes(ans);
	 							reader.close();	
	 						}
	        					
	             		}
	             	}
	             		
	             }
	             }
	             catch(Exception e){return;}
	 		
	 		}
				catch(IOException e){
					return;
				}
								


	}}




