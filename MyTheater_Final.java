package theater;
import java.util.Scanner;

public class MyTheater_Final {

	public static void main(String[] args) {
				
		String layou []= new String [5] ;
		int layou2 [] []= new int [5] [4];
		String s;
		String delims = "[ ]";
		int i = 0, j = 0, k = 0, totalseat = 0, numbers=0;
		String str, str1;
		String ticketrequestName [] = new String [15];;
		int ticketrequestNumber [] = new int [15];

		System.out.println("The maximum capaity of theater is 5 rows and can have maximum of 4 sections in each of them \n");
		//System.out.println("Enter Theater Layout \n");
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in); 
		
		//Do-While loop to get the Theater Layout from user.
		do
		{
			s=input.nextLine();
			
			if(i<=4)
				layou [i]=s;
			else if(s.length()!=0)
				System.out.println("Only 5 rows supported in theater layout capacity.\n");
			
			i++;			
		} while (s.length()!=0);
		k=i-1;						//"k" will hold the count of rows entered by user
		
		//FOR loop to parse the Theater Layout values entered by user.
		for (i=0; i<k; i++)
		{
			String section[] = layou[i].split(delims);		//Parse each row into Section Array
			int sectionLength = section.length;				//Number of Sections in a Row

			for (j=0; j<sectionLength; j++)
			{ 
				if(section[j].matches("[0-9]+"))			//Check if Section values are numeric	
				{
					if (layou2 [i] [j] < 0)					//Check if Section values are greater than 0
					{
						System.out.println("Incorrect Layout: Section value cannot have negative capacity.");
						System.out.println("Syntax: Each row in theater layout contains must have section values (adjacent seats) separated by spaces.");
						System.exit(0);
					}
					else
					{
						layou2 [i] [j] = Integer.parseInt(section[j]); 
						totalseat = totalseat + layou2 [i] [j];
					}
				}
				else
				{
					System.out.println("Incorrect Layout: Section value entered is non-numeric.");
					System.out.println("Syntax: Each row in theater layout contains must have section values (adjacent seats) separated by spaces.");
					System.exit(0);
				}
			}
		}

		s = null;
		i=0;
		String trequest []= new String [15] ;
		//System.out.println("Enter list of ticket requests \n");

		//Do-While loop to get the Booking Requests from user.
		do
		{
			trequest[i]=input.nextLine();
			s = trequest [i];
			i++;
		}while (s.length()!=0);
		k=i-1;						//"k" will hold the count of requests entered by user
		
		//FOR loop to parse the requested tickets data by user.
		for(i=0; i<k; i++)
		{
					str = trequest[i].substring(trequest[i].lastIndexOf(' ') + 1); //Last value from the requested row
					str1 = trequest[i].substring(0,trequest[i].lastIndexOf(' '));
					
					if(str.matches("[0-9]+"))
					{
						numbers=Integer.parseInt(str);
						if( numbers < 1)
						{
							System.out.println("Incorrect Request: Ticket Number is should be greater than 0.");
							System.out.println("Syntax: The ticket request must have a Name followed by a space and requested ticket number (positive).");
							System.exit(0);
						}
					}
					else
					{
						System.out.println("Incorrect Request: Ticket Number is not numeric value.");
						System.out.println("Syntax: The ticket request must have a Name followed by a space and requested ticket number.");
						System.exit(0);
					}

					ticketrequestName[i] = str1;
					ticketrequestNumber [i] = numbers; 
		}
			
		int rownumber = 0;
		int sectionnumber = 0;
		int l = 0;
		String flag1 = null;
		
		//FOR loop to process the requested tickets and make possible reservations
		for(l=0; l<k; l++)								//"l" loop for ticket requests
		{
			if(ticketrequestNumber [l]> 0)						//Check to confirm the requests tickets are greater than 0
			{
				for(i=0; i<5; i++)						//"i" loop for scanning theater rows
				{
					for(j=0; j<4; j++)					//"j" loop for scanning theater row-sections
					{	
						//Check if requested seats are higher than current capacity
						if(ticketrequestNumber [l] > totalseat)
							{
								System.out.println(ticketrequestName[l] + " Sorry, we can't handle your party");
								flag1 = "true";
								break;
							}
						
						if(layou2[i] [j] == 0)
							break;
						
						//Check if requested seats exactly matches the section size
						if(layou2[i] [j] == ticketrequestNumber [l])
						{	
						rownumber = i+1;
						sectionnumber = j+1;
						System.out.println(ticketrequestName[l] + " Row " + rownumber + " Section " + sectionnumber );
						layou2[i][j] = -1;
						totalseat = totalseat - ticketrequestNumber [l];
						flag1 = "true";
						break;
						}	 
					
						//Check if requested seats are less than section size
						if(layou2[i] [j] > ticketrequestNumber [l])
						{
							for(int x=i;x<i+2;x++)			//Better check current and next row for exact match first
							{
								for(int y=0;y<4;y++)		//Better check sections from current and next row for exact match

								{
									if(layou2[x][y] == ticketrequestNumber [l])
									{
										rownumber = x+1;
										sectionnumber = y+1;
										System.out.println(ticketrequestName[l] + " Row " + rownumber + " Section " + sectionnumber );
										layou2[x][y] = -1;
										totalseat = totalseat - ticketrequestNumber [l];
										flag1 = "true";
										break;
									}
								}
								if(flag1 == "true")
									break;
							}
							if(flag1 != "true")
							{
								rownumber = i+1;
								sectionnumber = j+1;
								System.out.println(ticketrequestName[l] + " Row " + rownumber + " Section " + sectionnumber );
								layou2[i] [j] = layou2[i] [j] - ticketrequestNumber [l];
								totalseat = totalseat - ticketrequestNumber [l];
								flag1 = "true";
								break;
							}
						}
					}
					if(flag1 == "true")
					break;

				}
				
				//If no one sections have the requested seats available, Call party for split booking.
				if(flag1 != "true")
					System.out.println(ticketrequestName[l] + " Call to split party." );
				
				flag1 = null; 
				//System.out.println("\n"); 
			}
		}			
	}
}

