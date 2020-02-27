
package me.sankalpchauhan.marsplayassignment.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Journal {

    @SerializedName("response")
    @Expose
    private JournalResponse response;

    public JournalResponse getResponse() {
        return response;
    }

    public void setResponse(JournalResponse response) {
        this.response = response;
    }

}
