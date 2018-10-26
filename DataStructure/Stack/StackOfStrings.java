public class StackOfStrings
{			//API for linked-list
	private Node first = null;
	private class Node()
	{
		String item;
		Node next;
	}
	public void push(String item)
	{
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
	}
	public String pop()
	{
		String item = first.item;
		first = first.next;
		return item;
	}
	public boolean isEmpty()
		{return first == null;}
	public int size()
	{
		int count = 0;
		Node count = first;
		while(count==null)
		{
			Node count = first.next;
			count++;
		}
		return count;
	}
	public static void main(String[] args)
	{
		StackOfStrings stack = new StackOfStrings();
		while (!StdIn.isEmpty())
		{
			String s = StdIn.readString();
			if (s.equals("-"))
				StdOut.print(stack.pop())
			else
				stack.push(s);

		}
	}
}