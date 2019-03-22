// -----------------------------------------------------
// Assignment (3)
// Question: (include question/part number, if applicable)
// Written by: (Najim Ghafourzadeh 40064350)
// -----------------------------------------------------


import java.io.*;
import java.util.Scanner;


/**
 * BibCreator Class.
 * 
 * The main task of this tool is read and process a given .bib file (which has one or more articles) and create 
 * 3 different files with the correct reference formats for IEEE, ACM and NJ.
 * 
 * @author Najim Ghafourzadeh
 *
 */
public class BibCreator {
	
	//This variable is used to get the index of an array.
	private static int index = 0;
	//This variable is used for storing the number of articles.
	private static int numArticles = 0;
	//An array of Scanners which is a copy of the scan[] array.
	private static Scanner[] copy = new Scanner[10];
	
	/*
	 * The following are array used to store the appropriate information collected from reading input .bib files.
	 */
	private static String[] author;
	private static String[] journal;
	private static String[] title;
	private static String[] year;
	private static String[] volume;
	private static String[] number;
	private static String[] pages;
	private static String[] doi;
	private static String[] month;
	
	//This variable is used for storing the amount of deleted output files.
	private static int countDeletedFiles = 0;
	
	
	/**
	 * processFilesFor Validation method verifies if the input Files (bib files) are valid.
	 * 
	 * This method validates input files. The validation process is determined by whether or not a field value is empty.
	 * If the input file does not have an empty field value, then the file is valid, otherwise it is invalid.
	 * If the bib file is valid, the method will then create 3 corresponding JSON files.
	 * These files are in the IEEE, ACM and NJ formats.
	 * If the file is not valid, the method will throw a FileInvalidException and delete the corresponding output files.
	 * 
	 * @param aScan An array of Scanner objects.
	 * @param aFile An array of File input objects(bib files).
	 * @param Fout  An array of File output objects(empty output files).
	 * @param write	An array of Printwriter objects.
	 */
	public static void processFilesForValidation(Scanner[] aScan, File[] aFile, File[] Fout, PrintWriter[] write) {
		
		for(int i = 0; i < 10; i++) {
			
			numArticles = 0;
			
			/*
			 * The following while-loop checks if the file has a next Line.
			 * If it does it reads a file line by line.
			 */
			while(aScan[i].hasNextLine()) {
				
				//Initializing str to the next line.
				String str = aScan[i].nextLine();
				
				/*
				 * If the variable str does not equal a "}", an empty line, a space or an integer as its first character,
				 * then if the variable str equals @Article, numArticles will be incremented.
				 * Otherwise numArticles is not incremented.
				 */
				if(!str.equals("}")) {
					
					if(!str.equals("")) {
						
						if(!str.equals(" ")) {
					
							if(str.charAt(0) != '0' && str.charAt(0) != '1' && str.charAt(0) != '2' && str.charAt(0) != '4' && str.charAt(0) != '5'
									&& str.charAt(0) != '6' && str.charAt(0) != '7' && str.charAt(0) != '8' && str.charAt(0) != '9') {
				
								
								
								if(str.substring(0, str.indexOf("{")).equals("@ARTICLE") || str.substring(0, str.indexOf("{")).equals("﻿@ARTICLE")) {
								
									numArticles++;
									//System.out.println(numArticles);
								}
							
							}
						}
					}
				}
			}
			aScan[i].close();
			
			
			for(int c = 0; c < 10; c++) {
				try {
					copy[c] = new Scanner(new FileInputStream("Latex"+ (c+1) + ".bib"));
				} catch (FileNotFoundException e) {
				
				}
			}
			
			
			/*
			 * Initializing every array length to the numArticles (number of articles per file).
			 */
			author = new String[numArticles];
			journal = new String[numArticles];
			title = new String[numArticles];
			year = new String[numArticles];
			volume = new String[numArticles];
			number = new String[numArticles];
			pages = new String[numArticles];
			doi = new String[numArticles];
			month = new String[numArticles];
			
			//This variable will store the name of the field which is empty.
			String field ="";
			
			
			try {
			
				for(int j = 0; j < numArticles; j++) {
				
					/*
					 * While the file has a next line, Read the next line and initialize it to str2.
					 * Then, if str2 does not equal to "}", a empty line, a space or an integer as its first character,
					 * the following will checks for equality compared to some fields.
					 */
					while(copy[i].hasNextLine()) {
						
						String str2 = copy[i].nextLine();;
						
								
									if(str2.equals("}")) {
										break;
									}else {
									
										if(!str2.equals("")) {

											if(!str2.equals(" ")) {
									
												if(str2.charAt(0) != '0' && str2.charAt(0) != '1' && str2.charAt(0) != '2' && str2.charAt(0) != '4' && str2.charAt(0) != '5'
														&& str2.charAt(0) != '6' && str2.charAt(0) != '7' && str2.charAt(0) != '8' && str2.charAt(0) != '9') {
												
													
													/*
													 * Checking for equality compared to some fields
													 */
													
													if(str2.substring(0, str2.indexOf("{")).equals("author=")) {
													
														field = "author";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														
														}else {
															
															author[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
															
														}	
													}
													
													
													if(str2.substring(0, str2.indexOf("{")).equals("journal=")) {
														
														field = "journal";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														}else {
															
															journal[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
														}
													}
													
													
													if(str2.substring(0, str2.indexOf("{")).equals("title=")) {
														
														field = "title";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														}else {
															
															title[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
														}
													}
													
													
													if(str2.substring(0, str2.indexOf("{")).equals("year=")) {
														
														field = "year";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														}else {
															
															year[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
														}
													}
													
													
													if(str2.substring(0, str2.indexOf("{")).equals("volume=")) {
														
														field = "volume";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														}else {
															
															volume[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
														}
													}
													
													
													if(str2.substring(0, str2.indexOf("{")).equals("number=")) {
														
														field = "number";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														}else {
															
															number[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
														}
													}
													
													
													if(str2.substring(0, str2.indexOf("{")).equals("pages=")) {
														
														field = "pages";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														}else {
															
															pages[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
														}
													}
													
													
													if(str2.substring(0, str2.indexOf("{")).equals("doi=")) {
														
														field = "doi";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														}else {
															
															doi[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
														}
													}
													
													
													if(str2.substring(0, str2.indexOf("{")).equals("month=")) {
														
														field = "month";
														
														if(str2.substring(str2.indexOf("{"),str2.indexOf("}")+1).equals("{}")) {
															
															throw new FileInvalidException("\nDetected empty field!\n====================\n");
														}else {
															
															month[j] = str2.substring(str2.indexOf("{")+1,str2.indexOf("}"));
														}
													}
											
									
												}
											
											}	
										}
					
									}
						//END OF WHILE LOOP
						}	
					//END OF FOR LOOP	
					}
				
				/*
				 * CREATING THE CORRESPONDING FILES.
				 */
				
				//This variable will store the name of each author by the use of the split() function.
				String [] splitAuthor;
				
				switch(i) {
				
					case 0: 
							/*
							 * IEEE FORMAT
							 */
							for(int k = 0; k < numArticles; k++) {
								
								splitAuthor = author[k].split(" and ");
								
								
								for(int v = 0; v < splitAuthor.length-1; v++) {
									
									write[0].print(splitAuthor[v] + ", ");
								}
			
								
								write[0].print(splitAuthor[splitAuthor.length-1] + ". ");
								
								write[0].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
												+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
								write[0].println("");
								
							}
							
							/*
							 * ACM FORMAT
							 */
							for(int k = 0; k < numArticles; k++) {
								
								splitAuthor = author[k].split(" and ");
								
								if(splitAuthor.length > 1) {
									
									write[10].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
								}else
									write[10].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
								
								write[10].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
												+ number[k] + "(" + year[k] + "), " + pages[k] + 
												". DOI:https://doi.org/" + doi[k] + ".\n");
								write[10].println(" ");
							}
							
							
							/*
							 * NJ FORMAT
							 */
							for(int k = 0; k < numArticles; k++) {
								
								splitAuthor = author[k].split(" and ");
								
								for(int v = 0; v < splitAuthor.length-1; v++) {
									
									write[20].print(splitAuthor[v] + " & ");
								}
								write[20].print(splitAuthor[splitAuthor.length-1] + ". ");
								
								write[20].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
												"(" + year[k] + ").");
								write[20].println(" ");
							}
							
							//CLOSING FILES.
							write[0].close();
							write[10].close();
							write[20].close();
							
							break;
							
					case 1:	
					
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[1].print(splitAuthor[v] + ", ");
							}
		
							
							write[1].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[1].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[1].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[11].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[11].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[11].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[11].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[21].print(splitAuthor[v] + " & ");
							}
							write[21].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[21].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[21].println(" ");
						}
						
						//CLOSING FILES.
						write[1].close();
						write[11].close();
						write[21].close();
						
						break;
						
						
					case 2: 
						
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[2].print(splitAuthor[v] + ", ");
							}
		
							
							write[2].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[2].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[2].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[12].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[12].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[12].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[12].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[22].print(splitAuthor[v] + " & ");
							}
							write[22].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[22].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[22].println(" ");
						}
						
						//CLOSING FILES.
						write[2].close();
						write[12].close();
						write[22].close();
						
						break;
						
					case 3: 
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[3].print(splitAuthor[v] + ", ");
							}
		
							
							write[3].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[3].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[3].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[13].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[13].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[13].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[13].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[23].print(splitAuthor[v] + " & ");
							}
							write[23].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[23].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[23].println(" ");
						}
						
						//CLOSING FILES.
						write[3].close();
						write[13].close();
						write[23].close();
						
						break;
						
					case 4: 
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[4].print(splitAuthor[v] + ", ");
							}
		
							
							write[4].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[4].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[4].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[14].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[14].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[14].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[14].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[24].print(splitAuthor[v] + " & ");
							}
							write[24].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[24].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[24].println(" ");
						}
						
						//CLOSING FILES.
						write[4].close();
						write[14].close();
						write[24].close();
						
						break;
						
					case 5: 
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[5].print(splitAuthor[v] + ", ");
							}
		
							
							write[5].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[5].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[5].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[15].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[15].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[15].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[15].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[25].print(splitAuthor[v] + " & ");
							}
							write[25].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[25].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[25].println(" ");
						}
						
						//CLOSING FILES.
						write[5].close();
						write[15].close();
						write[25].close();
						
						break;
							
						
					case 6:
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[6].print(splitAuthor[v] + ", ");
							}
		
							
							write[6].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[6].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[6].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[16].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[16].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[16].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[16].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[26].print(splitAuthor[v] + " & ");
							}
							write[26].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[26].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[26].println(" ");
						}
						
						//CLOSING FILES.
						write[6].close();
						write[16].close();
						write[26].close();
						
						break;
						
					case 7: 
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[7].print(splitAuthor[v] + ", ");
							}
		
							
							write[7].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[7].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[7].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[17].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[17].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[17].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[17].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[27].print(splitAuthor[v] + " & ");
							}
							write[27].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[27].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[27].println(" ");
						}
						
						//CLOSING FILES.
						write[7].close();
						write[17].close();
						write[27].close();
						
						break;
							
					case 8: 
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[8].print(splitAuthor[v] + ", ");
							}
		
							
							write[8].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[8].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[8].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[18].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[18].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[18].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[18].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[28].print(splitAuthor[v] + " & ");
							}
							write[28].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[28].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[28].println(" ");
						}
						
						//CLOSING FILES.
						write[8].close();
						write[18].close();
						write[28].close();
						
						break;
						
					case 9: 
						/*
						 * IEEE FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[9].print(splitAuthor[v] + ", ");
							}
		
							
							write[9].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[9].print("\"" + title[k] + "\", " + journal[k] + ", vol. " + volume[k] + ", no. "
											+ number[k] + ", p. " + pages[k] + ", " + month[k] + " " + year[k] + ".");
							write[9].println("");
							
						}
						
						/*
						 * ACM FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							if(splitAuthor.length > 1) {
								
								write[19].print("[" + (k+1) + "]\t" + splitAuthor[0] + " et al. ");
							}else
								write[19].print("[" + (k+1) + "]\t" + splitAuthor[0] + ". ");
							
							write[19].print(year[k] + ". " + title[k] + ". " + journal[k] + ". " + volume[k] + ", "
											+ number[k] + "(" + year[k] + "), " + pages[k] + 
											". DOI:https://doi.org/" + doi[k] + ".\n");
							write[19].println(" ");
						}
						
						
						/*
						 * NJ FORMAT
						 */
						for(int k = 0; k < numArticles; k++) {
							
							splitAuthor = author[k].split(" and ");
							
							for(int v = 0; v < splitAuthor.length-1; v++) {
								
								write[29].print(splitAuthor[v] + " & ");
							}
							write[29].print(splitAuthor[splitAuthor.length-1] + ". ");
							
							write[29].print(title[k] + ". " + journal[k] + ". " + volume[k] + ", " + pages[k] + 
											"(" + year[k] + ").");
							write[29].println(" ");
						}
						
						//CLOSING FILES.
						write[9].close();
						write[19].close();
						write[29].close();
						break;
				//END OF SWITCH		
				}
				
				
			
			}catch(FileInvalidException e) {
				
				countDeletedFiles ++;
				
				System.out.println(e.getMessage());
				
				System.out.println("Problem detected with input file: " + aFile[i].getName());
				System.out.println("File is invalid: Field \"" + field + "\" is empty. Processing stopped at this point. " +
									"Other empty fields may be present as well!");
				/*
				 * Closing and Deleting the files that are Invalid.
				 */
				switch(i) {
				
					case 0:
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 1: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 2: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 3: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 4: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 5: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 6: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 7: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 8: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;
					case 9: 
						write[i].close();
						write[i+10].close();
						write[i+20].close();
						
						Fout[i].delete();
						Fout[i+10].delete();
						Fout[i+20].delete();
						break;		
				}	
			}	
		}
		
		//Printing how many files got deleted and how many have been created.
		System.out.println("\nA total of " + (3*countDeletedFiles) + " files were invalid, and could not be processed." +
							" All other " + (30-(3*countDeletedFiles)) + " \"valid\" files have been created.");
	}
	
	
	
	
	

	public static void main(String[] args) {
		
		
		System.out.println("Welcome to BibCreator! (By Bajim Ghafourzadeh) \n");
		
		//Declaring an array of 10 scanner objects.
		Scanner[] scan = new Scanner[10];
		
		//Initializing the array of Scanner objects to null.
		for(int i = 0; i < 10; i++) {
			scan[i] = null;
		}
		
		//Declaring an array of 10 File objects.
		File[] F_in = new File[10];
		
		
		try {
			
			for(int i = 0; i < 10; i++) {
				
				index = i;
				//Initializing the File array.
				F_in[i] = new File("Latex"+ (i+1) + ".bib");
				//Initializing the array of Scanner objects.
				scan[i] = new Scanner(new FileInputStream("Latex"+ (i+1) + ".bib"));
				

			}
			
		}
		catch(FileNotFoundException e) {
			
			System.out.println("Could not open input file " + F_in[index].getName() + " for reading.\n\n" + 
							"Please check if file exists! Program will terminate after closing any opened files.");
			
			//Closing all files.
			for(int i = 0; i < index; i++) {
				scan[i].close();			
			}
			
			//Exiting program.
			System.exit(0);
		}
		
		
		
		/*
		 * Use while(hasnextLine) and a switch statement for searching and determining which field is equal to the string.
		 */
		
		//Declaring an array of PrinWriter objects.
		PrintWriter[] out = new PrintWriter[30];
		
		//Initializing PrintWriter array to null;
		for(int j = 0; j < 30; j++) {
			out[j] = null;
		}
		
		//Declaring array of 30 File objects.
		File[] F_out = new File[30];
		
		index = 0;
		
		try {

			
			for(int i = 0; i < 10; i++) {
				
				index = i;
				
				//Initializing the File array.
				F_out[i] = new File("IEEE" + (i+1) + ".json");
				//Creating/opening files IEEE1.json to IEEE10.json
				out[i] = new PrintWriter(new FileOutputStream("IEEE" + (i+1) + ".json",true));
				
			}
			
			for(int i = 10; i < 20; i++) {
				
				index = i;
				
				//Initializing the File array.
				F_out[i] = new File("ACM" + (i-9) + ".json");
				//Creating/opening files ACM1.json to ACM10.json
				out[i] = new PrintWriter(new FileOutputStream("ACM" + (i-9) + ".json",true));
			}
			
			for(int i = 20; i < 30; i++) {
				
				index = i;
				
				//Initializing the File array.
				F_out[i] = new File("NJ" + (i-19) + ".json");
				//Creating/opening files NJ1.json to NJ10.json
				out[i] = new PrintWriter(new FileOutputStream("NJ" + (i-19) + ".json",true));
			}
			
			
			
		}catch(FileNotFoundException e) {
			
			System.out.println(F_out[index].getName() + "could not be opened/created");
			
			//Deleting all opened/created files.
			for(int i = 0; i < index; i++) {
				F_out[i].delete();
			}
			
			//Closing all opened input files.
			for(int i = 0; i < 10; i++) {
				scan[i].close();
			}
			
			//Exiting Program
			System.exit(0);	
		}
		
		
		processFilesForValidation(scan, F_in, F_out, out);
	
		//Declaring and initializing Scanner object for reading from the keyboard.
		Scanner keyboard = new Scanner(System.in);
		
		String name = "";
		
		BufferedReader in = null;
		
		boolean done = false;
		
		//Variable that keeps track of the amount of time the user inputs an invalid file name.
		int countInput = 0;
		
		/*
		 * The following will ask the user to input the name of a file he/she wants to review.
		 * If the user types an invalid file name, the FileNotFoundException is thrown, incrementing
		 * the variable countInput and informing the user he/she typed an invalid file name but can still type another 
		 * file name as a second chance. If the user still does not type a valid file name then the system will exit.
		 */
		
		while(true) {
				
			System.out.println("\nPlease enter the name of one of the file you need to review: ");	
			
			name =  keyboard.nextLine();
			
			int k = 0;
			
			boolean match = false;
			
			//Checking the equality of the user's input with a file output name.
			while(!match) {
				
				if(name.equals(F_out[k].getName())) {
					
					match = true;
				}else {
					match = false;
					k++;	
				}
				
			}
						
			try {
				
			/*
			 * If the file name matches one of the existing output file name then 
			 * the output file is read and is printed. After which, the file is closed and 
			 * the system exits.	
			 */
			if(F_out[k].exists() && countInput <= 2) {
				
				in = new BufferedReader(new FileReader(F_out[k].getName()));
				
				String line;
				
				try {
				
				System.out.println("Here are the contents of the succesfully created JSON file: " + F_out[k].getName());	
					
				while((line = in.readLine()) != null) {
					
					System.out.println(line);
				}
				
				in.close();
				
				System.out.println("\nGoodbye! hope you have enjoyed creating the needed files using BibCreator.");
				
				System.exit(0);
				
				}catch(IOException e) {
					System.out.println("Error reading the file!");
				}
				
				
			}else{
				countInput++;
				
				if(countInput == 2) {
					System.out.println("\nCould not open input file again! Either file does not exist or could not be created.");
					System.out.println("Sorry! I am unable to display your desired files! Program will exit.");
					System.exit(0);
				}else
					throw new FileNotFoundException();	

			}
			
			}catch(FileNotFoundException e) {
				
				System.out.println("Could not open input file; File does not exist; possibly it could not be created!");
				
				System.out.println("\nHowever you will be allowed another chance to enter another file name.");
			}

			
		}	

	}

}
