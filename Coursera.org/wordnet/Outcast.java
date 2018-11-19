public class Outcast {
    private WordNet wordnet;
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    public String outcast(String[] nouns) {
        int max = 0;
        int id = 0;
        for(int i = 0; i < nouns.length; i++) {
            int sum = 0;
            for(int j = 0; j < nouns.length; j++)
                sum += wordnet.distance(nouns[i], nouns[j]);
            if(max < sum) {
                max = sum;
                id = i;
            }
        }
        return nouns[id];
    }
}
