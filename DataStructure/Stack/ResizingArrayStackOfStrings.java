public class ResizingArrayStacOfString
{
	private String[] s;
	private int N = 0;
	public ResizingArrayStacOfString()
	{
		s = new String[1];
	}
	private void resize(int capacity)
	{
		String[] replace = new String[capacity*2];
		for (int i=0; i<N; i++)
			replace[i] = s[i];
		s = replace;
	}
	public void push(String item)
	{
		if (N==s.length) resize(s.length*2);
		s[N++] = item;
	}
	public void pop()
	{
		String item = s[--N]; 
		s[N] = null;
		if (N==s.length/4) resize(s.length/2);
		return item;
	}

}