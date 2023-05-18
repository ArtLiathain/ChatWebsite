import java.util.ArrayList;

import javax.xml.crypto.Data;

class SharedData {
    private ArrayList<String> sharedResults;

    public SharedData() {
        sharedResults = new ArrayList<>();
    }

    public ArrayList<String> getSharedResults() {
        return sharedResults;
    }

    public void addData(String value) {
        sharedResults.add(value);
    }
}