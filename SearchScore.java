/*
Program Description: Search through a file and give it a score in relationship to a search term
Author: Liam McCormick
Java version:1.8.0_162_b12
*/

package com.assignment1.search;

class SearchScore
{
	private static int i,j;
	private static int score;

	static void getSearchScore(FileString myFileString, String[] SearchTerm)
	{
		score=0;
		//make all search terms lowercase
		for(i=0; i<SearchTerm.length; i++)
		{
			SearchTerm[i]=SearchTerm[i].toLowerCase();
		}

		for(i=0; i<myFileString.getSizeofContents(); i++)
		{
				for(j=0; j<SearchTerm.length; j++)
					recursiveCheck(myFileString,SearchTerm,0);

				/* find the point that best matches the search term and records it
				   if two or more points score the same then the one to occur first
				   shall be recorded */
				if(score>myFileString.getHighscore())
				{
					myFileString.setHighpoint(i);
					myFileString.setHighscore(score);
				}
		}
	}

	//a version of the other getsearchscore that converts a string argument to a string[]
	static void getSearchScore(FileString myFileString, String SearchTerm)
	{
		String[] splitted=SearchTerm.split("\\s+");
		getSearchScore(myFileString,splitted);
	}

	static void recursiveCheck(FileString myFileString, String[] SearchTerm, int k)	//circle through the next few search terms, each consecutive 
	{
		String[] wildCards=WildCards.getWildCards(SearchTerm[j+k]);
		int l;

		if((i+k)<myFileString.getSizeofContents() && (k+1)<SearchTerm.length-j)	//keep from going out of bounds
		{
			if(myFileString.getContents(i+k).equals(SearchTerm[j+k]))
			{
				myFileString.incresePriority(100+(50*k));
				recursiveCheck(myFileString,SearchTerm,k+1);
			}

			//check for root word wildcard
			else if(myFileString.getContents(i+k).equals(WildCards.normalise(SearchTerm[j+k])))
			{
				myFileString.incresePriority(25+(50*k));
				recursiveCheck(myFileString,SearchTerm,k+1);
			}

			for(l=0; l<wildCards.length; l++)
			{
				if(myFileString.getContents(i+k).equals(wildCards[l]))
				{
					myFileString.incresePriority(25+(50*k));
					break;
				}
			}
		}

		else if(k>score)
			score=k;	//record how deep the recursion went
	}
}
