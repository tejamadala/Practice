package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	//All the helper methods that will be used
	private static int TrieHelper(TrieNode ptr, String curr, String oth, int x, String[] allWords, TrieNode root)
	{
		String pref = prefixor(curr, oth);
		if(ptr.firstChild != null)
		{	
			String fpref = curr.substring(0, pref.length()+1);
			String opref = oth.substring(0, pref.length()+1);
			if(!fpref.equals(opref))
			{	
				ptr = ptr.firstChild;
				while(ptr.sibling != null)
				{
					fpref = curr.substring(0, pref.length()+1);
					opref = oth.substring(0, pref.length()+1);
					ptr = ptr.sibling;
					oth = allWords[ptr.substr.wordIndex];
					if(fpref.equals(pref))
					{
						brancher(ptr, fpref, curr, oth, x, allWords);
						break;
					}
				}
			}
			else
			{
				brancher(ptr, fpref, curr, oth, x, allWords);
			}
			short n = 0;
			int nend = curr.length()-1;
			short nsend = (short) (nend);
			Indexes newin = new Indexes(x, n, nsend);
			TrieNode newn = new TrieNode(newin, null, null);
			ptr.sibling = newn;
		}
		else 
		{			
			int prefl = pref.length()-1;				//change variables from ints to short for other parts of code
			short sprefl = (short) (prefl);
			int p1 = pref.length();
			short sp1 = (short) (p1);
			int len = allWords[ptr.substr.wordIndex].length() - 1;
			short slen = (short) (len);
			int len2 = curr.length()-1;
			short slen2 = (short) (len2);
			Indexes one = new Indexes(ptr.substr.wordIndex, sp1, slen);
			TrieNode otrie = new TrieNode(one, null, null);
			Indexes two = new Indexes(x, sp1, slen2);
			TrieNode ttrie = new TrieNode(two, null, null);
			ptr.substr.endIndex = sprefl;
			ptr.firstChild = otrie;
			otrie.sibling = ttrie;
		}
		return x;
	}
	
	private static void brancher(TrieNode ptr, String fpref, String curr, String oth, int x, String[] allWords)
	{
		int prefl = fpref.length()-1;			//change variables from ints to short for other parts of code
		short sprefl = (short) (prefl);
		int str = allWords[ptr.substr.wordIndex].length() - 1;
		short sstr = (short) (str);
		int p1 = fpref.length();
		short sp1 = (short) (p1);
		int st = curr.length()-1;
		short sst = (short) (st);
		Indexes one = new Indexes(ptr.substr.wordIndex, sp1, sstr);
		TrieNode otrie = new TrieNode(one, null, null);
		Indexes two = new Indexes(x, sp1, sst);
		TrieNode ttrie = new TrieNode(two, null, null);
		ptr.substr.endIndex = sprefl;
		ptr.firstChild = otrie;
		otrie.sibling = ttrie;
	}
	
	private static String prefixor(String curr, String oth)
	{
		int lessl;
		String pref;
		String less = "";
		if(curr.length() != oth.length())
		{
			less = SmallString(curr, oth);	
			lessl = less.length();
			pref = Character.toString(curr.charAt(0));
			for(int x = 1; x < lessl; x++)
			{
				if(curr.charAt(x) != oth.charAt(x))
				{
					break;
				}
				else
				{
					pref = pref + less.charAt(x);
				}
			}
		}
		else
		{
			pref = Character.toString(curr.charAt(0));
			for(int x = 1; x < oth.length(); x++)
			{
				if(curr.charAt(x) != oth.charAt(x))
				{
					break;
				}
				else
				{
					pref = pref + curr.charAt(x);
				}
			}
		}
		return pref;
	}
	
	private static boolean comparing(TrieNode ptr, String pref, String[] allWords)
	{

	String oth = allWords[ptr.substr.wordIndex];
		if(pref.charAt(0) != oth.charAt(0) || pref.charAt(pref.length()-1)!=oth.charAt(pref.length()-1))
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
	
	private static String SmallString(String curr, String oth)
	{
		String small = "";
		int currlen = curr.length();
		int othlen = oth.length();
		if(currlen > othlen)
		{
			small = oth;
		}
		else
		{
			small = curr;
		}
		return small;
	}
	
	private static ArrayList<TrieNode> correct(String[] allWords, TrieNode ptr, String s)
	{
		ArrayList<TrieNode> list = new ArrayList<TrieNode>();
		ptr = ptr.firstChild;
		while(ptr != null)
		{
			if(ptr.firstChild != null)
			{
				if(comparing(ptr, s, allWords))
				{ 
					list.addAll(correct(allWords, ptr, s));
				}
			} 
			else 
			{ 
				if(comparing(ptr, s, allWords))
				{ 
					list.add(ptr);
				}
			}
			ptr = ptr.sibling; 
		}
		return list;
	}
	

	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	
	
	public static TrieNode buildTrie(String[] allWords) {
		TrieNode start = new TrieNode(null, null, null);
		String str = allWords[0];
		short beg = 0;
		int end = str.length()-1;
		short e = (short) (end);
		Indexes first = new Indexes(0, beg, e);
		TrieNode firstn = new TrieNode(first, null, null);
		start.firstChild = firstn;
		TrieNode ptr = firstn;		
		if(allWords.length == 1)
		{
			return start;
		}
		String oth = allWords[ptr.substr.wordIndex];
		for(int i = 1; i < allWords.length; i++)
		{
			int x = 0;
			String curr = allWords[i];
			if(!(curr.substring(0,2).equals(oth.substring(0,2))))
			{
				if(ptr.sibling == null)
				{
					short a = 0;
					int nend = curr.length()-1;
					short nsend = (short) (nend);
					Indexes newin = new Indexes(i, a, nsend);
					TrieNode newn = new TrieNode(newin, null, null);
					ptr.sibling = newn;
				}
				else
				{
					while(ptr.sibling!=null)
					{
						ptr = ptr.sibling;					
						if(allWords[ptr.substr.wordIndex].substring(0,2).equals(curr.substring(0,2)))
						{
							x = TrieHelper(ptr, curr, allWords[ptr.substr.wordIndex], i, allWords, start);
							break;							
						}
					}
					if(ptr.sibling == null)
					{
						short b = 0;
						int nend = curr.length()-1;
						short nsend = (short) (nend);
						Indexes newin = new Indexes(i, b, nsend);
						TrieNode newn = new TrieNode(newin, null, null);
						int thin = newn.substr.wordIndex;
						if(x == thin)
						{
							ptr.sibling = null;
							newn = null;
						}
							ptr.sibling = newn;
					}		
				}
			}
			else
			{
					TrieHelper(ptr, curr, oth, i, allWords,start);	
			}
			ptr = firstn;
		}
		return start;
	}
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix)
	{
		ArrayList<TrieNode> list = new ArrayList<TrieNode>();
		TrieNode ptr = root.firstChild;
		char p = prefix.charAt(0);
		char w = allWords[ptr.substr.wordIndex].charAt(0);
		if(ptr.firstChild == null && ptr.sibling == null)
		{ 

			if(p == w)
			{ 
				list.add(ptr);
				return list;
			}
			else 
			{ 
				list = null;
				return list;
			}
		}
		do
		{
			if(ptr==null)
			{
				break;
			}
			if(ptr.firstChild != null)
			{
				if(comparing(ptr, prefix, allWords))
				{
					list.addAll(correct(allWords, ptr, prefix));
				}
			}
			else
			{
				if(comparing(ptr, prefix, allWords))
				{
					list.add(ptr);
				}
			}
			ptr = ptr.sibling;
		}while(ptr != null);
		
		if(list.isEmpty()==true)
		{
			return null;
		}
		return list;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
