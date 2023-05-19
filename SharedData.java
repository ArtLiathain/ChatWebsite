
import java.util.ArrayList;

class SharedData {
    private ArrayList<String> sharedResults;

    public SharedData() {
        sharedResults = new ArrayList<>();
    }

    public String getSharedResults(int i) {
        return sharedResults.get(i);
    }

    public void addData(String value) {
        sharedResults.add(value);
    }

}