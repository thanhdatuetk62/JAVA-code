import java.util.*;
public class Person {
	private ArrayList<String> word;
	Person() {
		word = new ArrayList<String>();
		word.add("Start");
	}
	public void addWord(String w) {

		int Lo = 0;
		int Hi = word.size()-1;
		
		//Binary Search! :)

		while(Lo<Hi) {
			int mid = ((Hi+Lo)/2);
			if(w.compareToIgnoreCase(word.get(mid))>0) {
				Lo = mid+1;
			}
			else {
				Hi = mid-1;
			}
		}
		if(w.compareToIgnoreCase(word.get(Lo))>0)
			word.add(Lo+1, w);
		else if(w.compareToIgnoreCase(word.get(Lo))<0)
			word.add(Lo, w);
		else 
			System.out.println("This word had already been in dictionary!");

	}
	public ArrayList<String> getArray() {
		return word;
	}
	public static void main(String[] args) {
		Person test = new Person();
		test.addWord("Abstract");
		test.addWord("BigBoobs");
		test.addWord("Blowjob");
		test.addWord("Babes");
		test.addWord("POV");
		test.addWord("Groupsex");
		test.addWord("VRporn");
		test.addWord("Contest");
		test.addWord("pO");
		test.addWord("Asus");
		ArrayList<String> t = new ArrayList<String>(test.getArray());
		System.out.println(t);
	}
}